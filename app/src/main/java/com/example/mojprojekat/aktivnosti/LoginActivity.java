package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderUser;
import com.example.mojprojekat.model.Account;
import com.example.mojprojekat.service.UserService;

public class LoginActivity extends AppCompatActivity {

    private Button btnStartEmailsActivity;
    private Button btnRegistration;

    DBContentProviderUser dbUser;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnStartEmailsActivity = (Button) findViewById(R.id.btnStartEmailsActivity);
        btnRegistration=(Button) findViewById((R.id.btnRegistration));
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegistrationUserActivity.class);
                startActivity(i);
            }
        });
    }

    public static Account createUser(Cursor cursor) {
        Account account = new Account();
        if(cursor.getCount()!=0){
            account.setId(cursor.getLong(0));
            account.setSmtpAddress(cursor.getString(1));
            account.setInServerAddress(cursor.getString(2));
            account.setUsername(cursor.getString(4));
            account.setPassword(cursor.getString(5));
        }
        return account;
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Toast.makeText(this, "onStart()",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onResume(){
        super.onResume();
       /* Util.initDBUsers(LoginActivity.this);
        final String[] projection={ReviewerSQLiteHelper.COLUMN_ID, ReviewerSQLiteHelper.COLUMN_SMTP,
                ReviewerSQLiteHelper.COLUMN_POP3_IMAP, ReviewerSQLiteHelper.COLUMN_DISPLAY, ReviewerSQLiteHelper.COLUMN_USERNAME,
                ReviewerSQLiteHelper.COLUMN_PASSWORD};*/
        final EditText etUserName=(EditText) findViewById(R.id.txtUserName);
        final EditText etPassword=(EditText) findViewById(R.id.psPasword);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        System.out.println("Ulogovani: "+sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
        btnStartEmailsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getText().toString().equals("") && etUserName.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Unesite e-mail adresu i lozinku ime!",Toast.LENGTH_SHORT).show();
                }
                else if(etUserName.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Unesite korisničko ime!",Toast.LENGTH_SHORT).show();
                }
                else if(etPassword.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Unesite lozinku!",Toast.LENGTH_SHORT).show();
                }
                else if(!(etUserName.getText().toString().equals("")) && !(etPassword.getText().toString().equals(""))) {
                    Intent intent2 = new Intent(LoginActivity.this, UserService.class);
                    intent2.putExtra("username", etUserName.getText().toString());
                    intent2.putExtra("password", etPassword.getText().toString());
                    intent2.putExtra("option", "login");
                    startService(intent2);
                }
            }
        });
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
