package com.manico.chain_reaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        final Context context = this;

        View.OnClickListener not_implemented = new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                    .setTitle("Not Implemented")
                    .setMessage("This feature has not be implemented yet!")
                    .show();
            }
        };

        Button button;

        button = (Button) findViewById(R.id.play_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame(1);
            }
        });

        button = (Button) findViewById(R.id.share_button);
        button.setOnClickListener(not_implemented);

        button = (Button) findViewById(R.id.achievements_button);
        button.setOnClickListener(not_implemented);

        button = (Button) findViewById(R.id.levels_button);
        button.setOnClickListener(not_implemented);
    }

    private void startGame(int lvl)
    {
        Log.d(TAG, "Starting game @ lvl " + lvl);

        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra(GameActivity.KEY_LEVEL, lvl);
        startActivity(intent);
    }
}
