package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mojprojekat.R;
import com.example.mojprojekat.service.AccountService;
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

    }
    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(this, "onStart()",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onResume(){
        super.onResume();
        final String username=sharedPreferences.getString(getString(R.string.login1),"Nema ulogovanog");
        int status = ReviewerTools.getConnectivityStatus(getApplicationContext());

        if(status == ReviewerTools.TYPE_WIFI || status == ReviewerTools.TYPE_MOBILE){
            int SPLASH_TIME_OUT=2000;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if(username.equals("") || username.equals("Nema ulogovanog")){
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }else if(!username.equals("") && !username.equals("Nema ulogovanog")){
                        Intent intent=new Intent(SplashActivity.this, AccountService.class);
                        intent.putExtra("option","getAccountByUsername");
                        startService(intent);
                        startActivity(new Intent(SplashActivity.this, EmailsActivity.class));
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        }else{
            Toast.makeText(this, "Uređaj nije povezan na internet", Toast.LENGTH_SHORT).show();
        }

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
