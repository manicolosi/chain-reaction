package com.manico.chain_reaction;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private static final String TAG = "GameView";

    private GameThread thread;

    public GameView(Context context)
    {
        super(context);

        Log.d(TAG, "GameView()");

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        setFocusable(true);
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new GameThread(holder);
        Log.d(TAG, "surfaceCreated()");
    }

    public void surfaceChanged(SurfaceHolder holder, int format,
            int width, int height)
    {
        Log.d(TAG, "surfaceChanged(): " + width + "x" + height);
        thread.setSurfaceSize(width, height);
        thread.doStart();
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed()");

        Log.d(TAG, "surfaceDestroyed(): " + thread.getState());

        thread.setRunning(false);

        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }

        Log.d(TAG, "surfaceDestroyed(): " + thread.getState());
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int) event.getX();
        int y = (int) event.getY();

        thread.onTouch(event.getAction(), x, y);

        return true;
    }
}
