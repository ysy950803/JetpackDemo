package com.ysy.jetpackdemo;

import android.os.Bundle;
import android.util.Log;

import com.ysy.jetpackdemo.ui.main.MainFragment;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TEST-1", "MainActivity onDestroy");
    }
}
