package com.example.mojprojekat.aktivnosti;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderUser;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.fragmenti.FragmentProfile;
import com.example.mojprojekat.model.Account;
import com.example.mojprojekat.tools.FragmentTransition;

public class ProfileActivity extends AppCompatActivity {

    DBContentProviderUser dbUser;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.setTitle(R.string.my_profile);

        FragmentTransition.to(FragmentProfile.newInstance(), this, true, R.id.profileContent);

        Toolbar tbCreateEmail=findViewById(R.id.tbProfile);
        setSupportActionBar(tbCreateEmail);

        fillData();
    }

    private void fillData() {
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_SMTP, ReviewerSQLiteHelper.COLUMN_POP3_IMAP, ReviewerSQLiteHelper.COLUMN_DISPLAY, ReviewerSQLiteHelper.COLUMN_USERNAME,
                ReviewerSQLiteHelper.COLUMN_PASSWORD};

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
        String login=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
        String selectionClause=ReviewerSQLiteHelper.COLUMN_USERNAME +" LIKE ?";
        String[] selectionArgs={login};
        Cursor cursor=getContentResolver().query(dbUser.CONTENT_URI_USER,allColumns,selectionClause,selectionArgs,null);

        cursor.moveToFirst();
        Account account = createUser(cursor);

        TextView email = (TextView)findViewById(R.id.etEmail);
        TextView password = (TextView)findViewById(R.id.etPass);

        email.setText(account.getUsername());
        password.setText(account.getPassword());

        cursor.close();
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
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_back:
                Intent intent = new Intent(ProfileActivity.this, EmailsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_save: {
                String[] allColumns = {ReviewerSQLiteHelper.COLUMN_ID,
                        ReviewerSQLiteHelper.COLUMN_SMTP, ReviewerSQLiteHelper.COLUMN_POP3_IMAP, ReviewerSQLiteHelper.COLUMN_DISPLAY, ReviewerSQLiteHelper.COLUMN_USERNAME,
                        ReviewerSQLiteHelper.COLUMN_PASSWORD};

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                Log.d("Ulogovani: ", sharedPreferences.getString(getString(R.string.login), "Nema ulogovanog"));
                String login = sharedPreferences.getString(getString(R.string.login), "Nema ulogovanog");
                String selectionClause = ReviewerSQLiteHelper.COLUMN_USERNAME + " LIKE ?";
                String[] selectionArgs = {login};
                Cursor cursor = getContentResolver().query(dbUser.CONTENT_URI_USER, allColumns, selectionClause, selectionArgs, null);

                cursor.moveToFirst();
                Account user = createUser(cursor);

                TextView password = (TextView) findViewById(R.id.etPass);

                user.setPassword(password.getText().toString());

                ReviewerSQLiteHelper dbHelperUser = new ReviewerSQLiteHelper(this);
                SQLiteDatabase dbUsers = dbHelperUser.getWritableDatabase();
                {
                    ContentValues entryUser = new ContentValues();
                    entryUser.put(ReviewerSQLiteHelper.COLUMN_SMTP, user.getSmtp());
                    entryUser.put(ReviewerSQLiteHelper.COLUMN_POP3_IMAP, user.getPop3_imap());
                    entryUser.put(ReviewerSQLiteHelper.COLUMN_USERNAME, user.getUsername());
                    entryUser.put(ReviewerSQLiteHelper.COLUMN_PASSWORD, user.getPassword());

                    this.getContentResolver().update(dbUser.CONTENT_URI_USER, entryUser, selectionClause, selectionArgs);
                }
                dbUsers.close();
                Toast.makeText(this, "Sačuvane su izmene", Toast.LENGTH_SHORT).show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
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
