package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderUser;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.model.Account;
import com.example.mojprojekat.tools.Util;

public class LoginActivity extends AppCompatActivity {

    DBContentProviderUser dbUser;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Util.initDBUsers(LoginActivity.this);
        Button btnStartEmailsActivity = (Button) findViewById(R.id.btnStartEmailsActivity);
        final String[] projection={ReviewerSQLiteHelper.COLUMN_ID, ReviewerSQLiteHelper.COLUMN_SMTP,
                ReviewerSQLiteHelper.COLUMN_POP3_IMAP, ReviewerSQLiteHelper.COLUMN_DISPLAY, ReviewerSQLiteHelper.COLUMN_USERNAME,
                ReviewerSQLiteHelper.COLUMN_PASSWORD};
        final EditText etUserName=(EditText) findViewById(R.id.txtUserName);
        final EditText etPassword=(EditText) findViewById(R.id.psPasword);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        btnStartEmailsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectionClause=ReviewerSQLiteHelper.COLUMN_USERNAME +" LIKE ? AND "+ReviewerSQLiteHelper.COLUMN_PASSWORD+" LIKE ?";
                String[] selectionArgs={etUserName.getText().toString(),etPassword.getText().toString()};
                Cursor cursor=getContentResolver().query(dbUser.CONTENT_URI_USER,projection,selectionClause,selectionArgs,null);
                cursor.moveToFirst();
                Account account = createUser(cursor);
                Log.d("-----","-----");
                Log.d("Email1: ",account.getUsername());
                Log.d("Password: ",account.getPassword());
                if(account.getUsername().equals(etUserName.getText().toString()) && account.getPassword().equals(etPassword.getText().toString())){
                    editor.putString(getString(R.string.login),account.getUsername());
                    editor.commit();
                    Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                    Intent intent = new Intent(LoginActivity.this, EmailsActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Pogrešili ste korisničko ili lozinku! \n Pokušajte ponovo!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Account createUser(Cursor cursor) {
        Account account = new Account();
        if(cursor.getCount()!=0){
            account.setId(cursor.getLong(0));
            account.setSmtp(cursor.getString(1));
            account.setPop3_imap(cursor.getString(2));
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
        Toast.makeText(this, "onResume()",Toast.LENGTH_SHORT).show();
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
