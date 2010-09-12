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

    private int mCanvasWidth;
    private int mCanvasHeight;

    private boolean mRun = false;
    private long mLastTime;

    public BoomshineThread(SurfaceHolder surfaceHolder)
    {
        background = new Paint();
        background.setColor(0xff000000);

        mSurfaceHolder = surfaceHolder;
    }

    public void setSurfaceSize(int width, int height)
    {
        mCanvasWidth = width;
        mCanvasHeight = height;
    }

    public void doStart()
    {
        synchronized (mSurfaceHolder) {
            createBlips();

            mLastTime = System.currentTimeMillis() + 100;
            mRun = true;
        }
    }

    public void createBlips()
    {
        int num_blips = 10;
        mBlips = new Blip[num_blips];

        for (int i = 0; i < mBlips.length; i++) {
            mBlips[i] = new Blip(mCanvasWidth, mCanvasHeight);
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

            // These paint objects should obviously be cached.
            Paint foreground = new Paint();
            foreground.setColor(mBlips[i].color);
            foreground.setAntiAlias(true);

            Path path = new Path();
            path.addCircle((int)mBlips[i].x, (int)mBlips[i].y, mBlips[i].radius, Direction.CW);

            canvas.drawPath(path, foreground);
        }
    }
}
