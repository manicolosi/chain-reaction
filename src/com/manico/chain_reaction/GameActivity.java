package com.manico.chain_reaction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity
{
    private static final String TAG = "GameActivity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate()");

        setContentView(new GameView(this));
    }
}
