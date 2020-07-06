package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mojprojekat.R;
import com.example.mojprojekat.service.UserService;
import com.example.mojprojekat.tools.ReviewerTools;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String username= sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
        int status = ReviewerTools.getConnectivityStatus(getApplicationContext());

        if(status == ReviewerTools.TYPE_WIFI || status == ReviewerTools.TYPE_MOBILE){
            int SPLASH_TIME_OUT=5000;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if(username.equals("") || username.equals("Nema ulogovanog")){
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }else if(!username.equals("") && !username.equals("Nema ulogovanog")){
                        Intent i = new Intent(SplashActivity.this, EmailsActivity.class);
                        Intent intent2=new Intent(SplashActivity.this, UserService.class);
                        intent2.putExtra("option","getUserByUsername");
                        startService(intent2);
                        startActivity(i);
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        }else{
            Toast.makeText(this, "Uređaj nije povezan na internet", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(this, "onStart()",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onResume(){
        super.onResume();

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
