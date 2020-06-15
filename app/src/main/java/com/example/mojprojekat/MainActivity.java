package com.example.mojprojekat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mojprojekat.aktivnosti.SplashActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Util.initDBEmails(MainActivity.this);
        int SPLASH_TIME_OUT=100;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
