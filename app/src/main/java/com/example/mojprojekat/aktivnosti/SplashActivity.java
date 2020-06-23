package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mojprojekat.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String username=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
        System.out.println("\n\nUlogovani: "+username);
        int SPLASH_TIME_OUT=2000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(username.equals("") || username.equals("Nema ulogovanog")){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }else if(!username.equals("") && !username.equals("Nema ulogovanog")){
                    startActivity(new Intent(SplashActivity.this, EmailsActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(this, "onStart()",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onResume(){
        super.onResume();
        //Toast.makeText(this, "onResume()",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Toast.makeText(this,"onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //Toast.makeText(this,"onDestroy()", Toast.LENGTH_SHORT).show();
    }
}
