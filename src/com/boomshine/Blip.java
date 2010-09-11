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
    public double x;
    public double y;
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
    }

    public void step()
    {
        Log.d(TAG, String.format("Blip's cur position: %f,%f\t" +
                                 "Blip's cur velocity: %f,%f", x, y, velocity_x, velocity_y));

        x += velocity_x;
        y += velocity_y;

        if (x < 0) {
            Log.d(TAG, "hit left edge");
            velocity_x *= -1;
        }
        if (x > width) {
            Log.d(TAG, "hit right edge");
            velocity_x *= -1;
        }
        if (y < 0) {
            Log.d(TAG, "hit top edge");
            velocity_y *= -1;
        }
        if (y > height) {
            Log.d(TAG, "hit bottom edge");
            velocity_y *= -1;
        }
    }

    public void randomColor()
    {
        color = colors[random.nextInt(colors.length)];
    }
}
