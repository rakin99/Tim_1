package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.mojprojekat.R;
import com.example.mojprojekat.fragmenti.PasswordDialog;
import com.example.mojprojekat.service.AccountService;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar tbRegistration=findViewById(R.id.tbRegistration);
        setSupportActionBar(tbRegistration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.activity_item_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                EditText txtEmail=(EditText) findViewById(R.id.txtRgUserName);
                EditText txtPassword=(EditText) findViewById(R.id.psRgPasword);
                String email=txtEmail.getText().toString();
                String password=txtPassword.getText().toString();
                if(email.equals("") && password.equals("")){
                    Toast.makeText(this, "Morate uneti e-mail adresu i lozinku!",Toast.LENGTH_SHORT).show();
                }
                else if(email.equals("")){
                    Toast.makeText(this, "Morate uneti e-mail adresu!",Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    Toast.makeText(this, "Morate uneti lozinku!",Toast.LENGTH_SHORT).show();
                }else if(!isValidEmailAddress(email)){
                    Toast.makeText(this, "Format e-mail adrese je pogrešan!\nPokušajte ponovo!",Toast.LENGTH_SHORT).show();
                }else{
                    String e=email.split("@")[1];
                    if(e.equals("gmail.com") || e.equals("yahoo.com")){
                        DialogFragment newFragment = new PasswordDialog();
                        newFragment.show(getSupportFragmentManager(), "password");
                    }else{
                        Intent intent2 = new Intent(this, AccountService.class);
                        intent2.putExtra("email_adress",email);
                        intent2.putExtra("password",password);
                        intent2.putExtra("option","add");
                        startService(intent2);
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected  void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}