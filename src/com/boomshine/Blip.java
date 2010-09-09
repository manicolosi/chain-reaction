package com.boomshine;

import android.util.Log;
import java.util.Random;

public class Blip
{
    private static final String TAG = "Boomshine";

    private static final int[] colors = new int[] {
        0xa0edd400,
        0xa073d216,
        0xa0c17d11,
        0xa0f57900,
        0xa075507b,
        0xa0cc0000,
        0xa03465a4,
    };

    public int width;
    public int height;
    public int x;
    public int y;
    public int radius = 8;
    public int color = 0xffffffff;
    public double rotation = 0; 
    public double velocity_x;
    public double velocity_y;

    private Random random = new Random();

    public Blip()
    {
    }

    public Blip(int width, int height)
    {
        this.width = width;
        this.height = height;

        x = random.nextInt(width - radius*2) + radius;
        y = random.nextInt(height - radius*2) + radius;

        rotation = random.nextInt(360) * (Math.PI/180);

        velocity_x = 2 * Math.sin(rotation);
        velocity_y = 2 * Math.cos(rotation);

        randomColor();

        Log.d(TAG, String.format("New Blip - x:%d y:%d color:%h rotation:%f",
                                 x, y, color, rotation));
    }

    public void step()
    {
        if ((x - radius < 0) || (x + radius > width)) {
            velocity_x *= -1;
        }
        if ((y - radius < 0) || (y + radius > height)) {
            velocity_y *= -1;
        }

        x += velocity_x;
        y += velocity_y;
    }

    public void randomColor()
    {
        color = colors[random.nextInt(colors.length)];
    }
}
