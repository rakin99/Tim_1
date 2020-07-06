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

import com.example.mojprojekat.R;
import com.example.mojprojekat.service.UserService;

public class RegistrationUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        Toolbar tbRegistration=findViewById(R.id.tbRegistrationUser);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                EditText txtFirst = (EditText) findViewById(R.id.etFirstName);
                EditText txtLast = (EditText) findViewById(R.id.etLast);
                EditText txtUsername = (EditText) findViewById(R.id.etUsername);
                EditText txtPassword = (EditText) findViewById(R.id.psPass);
                String first = txtFirst.getText().toString();
                String last = txtLast.getText().toString();
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if (username.equals("") && password.equals("")) {
                    Toast.makeText(this, "Morate uneti korisničko ime i lozinku!", Toast.LENGTH_SHORT).show();
                } else if (username.equals("")) {
                    Toast.makeText(this, "Morate uneti korisnčko ime!", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(this, "Morate uneti lozinku!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Poceo komunikaciju sa servisom!", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(this, UserService.class);
                    intent2.putExtra("first", first);
                    intent2.putExtra("last", last);
                    intent2.putExtra("username", username);
                    intent2.putExtra("password", password);
                    intent2.putExtra("option", "add");
                    startService(intent2);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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