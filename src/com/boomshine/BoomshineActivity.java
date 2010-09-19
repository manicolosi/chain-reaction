package com.boomshine;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BoomshineActivity extends Activity
{
    private static final String TAG = "BoomshineActivity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate()");

        setContentView(new BoomshineView(this));
    }
}
