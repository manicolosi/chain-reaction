package com.boomshine;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;
import android.view.View;

public class BoomshineView extends SurfaceView implements SurfaceHolder.Callback
{
    private static final String TAG = "BoomshineView";

    private BoomshineThread thread;

    public BoomshineView(Context context)
    {
        super(context);

        Log.d(TAG, "BoomshineView()");

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        thread = new BoomshineThread(holder);

        setFocusable(true);
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.d(TAG, "surfaceCreated()");
    }

    public void surfaceChanged(SurfaceHolder holder, int format,
            int width, int height)
    {
        Log.d(TAG, "surfaceChanged()");
        thread.setSurfaceSize(width, height);
        thread.doStart();
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed()");

        boolean retry = true;
        //thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
