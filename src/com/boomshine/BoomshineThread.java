package com.boomshine;

import android.graphics.Canvas;
import android.graphics.Color;
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
    private boolean mTouched = false;

    private long mLastTime;
    private int mFrameCount;

    public BoomshineThread(SurfaceHolder surfaceHolder)
    {
        background = new Paint();
        background.setColor(0xffeeeeec);

        mSurfaceHolder = surfaceHolder;
    }

    public void doStart()
    {
        synchronized (mSurfaceHolder) {
            createBlips();

            mLastTime = System.currentTimeMillis();
            mFrameCount = 0;
            mRun = true;
        }
    }

    @Override
    public void run()
    {
        Log.d(TAG, "run()");
        while(mRun) {
            long now = System.currentTimeMillis();

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

            mFrameCount++;
            int delta = (int)(now - mLastTime);

            if (delta > 5000) {
                double deltaSeconds = delta / 1000.0;
                double fps = mFrameCount / deltaSeconds;
                Log.d(TAG, "FPS: " + fps + " average over " + deltaSeconds + " seconds.");

                mLastTime = System.currentTimeMillis();
                mFrameCount = 0;
            }
        }

        Log.d(TAG, "run(): done");
    }

    public void setSurfaceSize(int width, int height)
    {
        mCanvasWidth = width;
        mCanvasHeight = height;
    }

    public void createBlips()
    {
        // Switch to some type of list instead of array
        int num_blips = 50;
        mBlips = new Blip[num_blips + 1];

        for (int i = 0; i < mBlips.length - 1; i++) {
            mBlips[i] = new Blip(mCanvasWidth, mCanvasHeight);
        }
    }

    public void setRunning(boolean b)
    {
        mRun = b;
    }

    public void updatePhysics()
    {
        for (int i = 0; i < mBlips.length; i++) {
            if (mBlips[i] == null) continue; // Remove soon!
            mBlips[i].step(mBlips);
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
            Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
            foreground.setColor(mBlips[i].color);

            Path path = new Path();
            path.addCircle((int)mBlips[i].x, (int)mBlips[i].y, mBlips[i].radius, Direction.CW);

            canvas.drawPath(path, foreground);
        }
    }

    public void onTouch(int x, int y)
    {
        synchronized (mSurfaceHolder) {
            //if (mTouched) return;

            Blip blip = new Blip();
            blip.x = x;
            blip.y = y;

            blip.explode();
            mBlips[mBlips.length-1] = blip;

            mTouched = true;
        }
    }
}
