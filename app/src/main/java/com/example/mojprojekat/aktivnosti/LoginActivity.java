package com.example.mojprojekat.aktivnosti;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderUser;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.tools.Util;

public class LoginActivity extends AppCompatActivity {

    DBContentProviderUser dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Util.initDBUsers(LoginActivity.this);
        Button btnStartEmailsActivity = (Button) findViewById(R.id.btnStartEmailsActivity);
        btnStartEmailsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] projection={ReviewerSQLiteHelper.COLUMN_EMAIL,ReviewerSQLiteHelper.COLUMN_PASSWORD};
                EditText etUserName=(EditText) findViewById(R.id.txtUserName);
                EditText etPassword=(EditText) findViewById(R.id.psPasword);
                String selectionClause=ReviewerSQLiteHelper.COLUMN_EMAIL+" LIKE ? AND "+ReviewerSQLiteHelper.COLUMN_PASSWORD+" LIKE ?";
                String[] selectionArgs={etUserName.getText().toString(),etPassword.getText().toString()};
                Cursor cursor=dbUser.query(dbUser.CONTENT_URI_USER,projection,selectionClause,selectionArgs,null);
                while (cursor.moveToNext())
                {
                    String email=cursor.getString(1);
                    String pass=cursor.getString(2);
                    Log.d("EMAIL: ",email);
                    Log.d("Password: ",pass);
                }
                /*Intent intent = new Intent(LoginActivity.this, EmailsActivity.class);
                startActivity(intent);*/
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(this, "onStart()",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onResume(){
        super.onResume();
        Toast.makeText(this, "onResume()",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(this,"onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"onDestroy()", Toast.LENGTH_SHORT).show();
    }
}
