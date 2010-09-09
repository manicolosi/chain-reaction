package com.boomshine;

import android.graphics.Canvas;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoomshineView extends View 
{
    private static final String TAG = "Boomshine";

    private Blip[] blips;

    public BoomshineView(Context context)
    {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.d(TAG, String.format("onSizeChanged(%1$d, %2$d, %3$d, %4$d)",
                                 w, h, oldw, oldh));

        int num_blips = 1;
        blips = new Blip[num_blips + 1];

        for (int i = 0; i < (blips.length - 1); i++) {
            blips[i] = new Blip(w, h);
        }
        blips[num_blips] = null;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        for (int i = 0; i < blips.length; i++) {
            if (blips[i] == null) continue;

            Path path = new Path();
            Paint foreground = new Paint();
            foreground.setColor(blips[i].color);
            path.addCircle(blips[i].x, blips[i].y, blips[i].radius, Direction.CW);
            canvas.drawPath(path, foreground);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        Blip blip = new Blip();
        blip.x = (int) event.getX();
        blip.y = (int) event.getY();

        blips[blips.length-1] = blip;

        for (int i = 0; i < blips.length; i++) {
            if (blips[i] == null) continue; // Remove soon!
            blips[i].step();
        }

        invalidate();

        return true;
    }
}
