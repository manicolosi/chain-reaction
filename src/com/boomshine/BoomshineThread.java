package com.boomshine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.Log;
import android.view.SurfaceHolder;

public class BoomshineThread extends Thread
{
    private static final String TAG = "BoomshineThread";

    private Blip[] mBlips;

    private SurfaceHolder mSurfaceHolder;
    private Paint background;
    private Paint foreground;

    private boolean mRun = false;
    private long mLastTime;

    public BoomshineThread(SurfaceHolder surfaceHolder)
    {
        Log.d(TAG, "BoomshineThread()");

        background = new Paint();
        background.setColor(0xff000000);

        foreground = new Paint();
        foreground.setColor(0xffeeeeec);
        foreground.setAntiAlias(true);

        mSurfaceHolder = surfaceHolder;
    }

    public void doStart()
    {
        synchronized (mSurfaceHolder) {
            Log.d(TAG, "start()");

            createBlips();

            mLastTime = System.currentTimeMillis() + 100;
            mRun = true;
        }
    }

    public void createBlips()
    {
        Log.d(TAG, "createBlips()");

        int num_blips = 10;
        mBlips = new Blip[num_blips];

        // FIXME!
        int w = 300;
        int h = 400;

        for (int i = 0; i < mBlips.length; i++) {
            mBlips[i] = new Blip(w, h);
        }
    }

    public void setRunning(boolean b)
    {
        mRun = b;
    }

    @Override
    public void run()
    {
        while(mRun) {
            Canvas c = null;
            try {
                c = mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    updatePhysics();
                    updateScreen(c);
                }
            } finally {
                if (c != null) {
                    mSurfaceHolder.unlockCanvasAndPost(c);
                }
            }

            //mRun = false; // XXX: Run once!
        }
    }

    public void updatePhysics()
    {
        for (int i = 0; i < mBlips.length; i++) {
            if (mBlips[i] == null) continue; // Remove soon!
            mBlips[i].step();
        }

    }

    public void updateScreen(Canvas canvas)
    {
        // Optimization: drawColor() ?
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(),
                        background);

        for (int i = 0; i < mBlips.length; i++) {
            if (mBlips[i] == null) continue;

            Path path = new Path();
            path.addCircle((int)mBlips[i].x, (int)mBlips[i].y, mBlips[i].radius, Direction.CW);

            canvas.drawPath(path, foreground);
        }
    }
}
