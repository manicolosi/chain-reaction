package com.boomshine;

import android.app.Activity;
import android.os.Bundle;

public class BoomshineActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new BoomshineView(this));
    }
}
