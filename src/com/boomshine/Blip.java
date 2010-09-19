package com.boomshine;

import android.util.Log;
import java.util.Random;

public class Blip
{
    private static final String TAG = "Boomshine";

    private static final int[] colors = new int[] {
        0xd0edd400,
        0xd073d216,
        0xd0c17d11,
        0xd0f57900,
        0xd075507b,
        0xd0cc0000,
        0xd03465a4,
    };

    public int width;
    public int height;
    public double x;
    public double y;
    public int radius = 10;
    public int color = 0xd02e3436;

    public double rotation = 0; 
    public double velocity_x;
    public double velocity_y;

    public boolean exploding = false;
    public boolean shrinking = false;
    public boolean holding   = false;
    public boolean dead      = false;
    public int hold = 40;

    private static final Random random = new Random();

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

    public void step(Blip[] blips)
    {
        if (dead) return;

        if (exploding) {
            if (holding) {
                hold--;
                if (hold == 0) {
                   holding = false;
                   shrinking = true;
                }
            } else if (shrinking) {
                radius--;
                radius--;
                if (radius < 0) {
                    shrinking = false; holding = false; exploding = false; dead = true;
                }
            } else {
                radius++;
                radius++;
                if (radius > 30) holding = true;
            }
        } else {
            for (int i = 0; i < blips.length; i++) {
                if (blips[i] == null) continue; // Remove soon!

                if (blips[i].exploding) {
                    if (distance(x, y, blips[i].x, blips[i].y) <= (radius + blips[i].radius)) {
                        exploding = true;
                    }
                }
            }

            x += velocity_x;
            y += velocity_y;

            if (x - radius < 0 || x + radius > width) {
                velocity_x *= -1;
            }
            if (y - radius < 0 || y + radius > height) {
                velocity_y *= -1;
            }
        }
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    public void explode()
    {
        exploding = true;
    }

    public void randomColor()
    {
        color = colors[random.nextInt(colors.length)];
    }
}
