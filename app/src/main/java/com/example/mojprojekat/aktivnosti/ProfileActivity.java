package com.example.mojprojekat.aktivnosti;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderUser;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.fragmenti.FragmentProfile;
import com.example.mojprojekat.model.User;
import com.example.mojprojekat.tools.FragmentTransition;

public class ProfileActivity extends AppCompatActivity {

    DBContentProviderUser dbUser;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FragmentTransition.to(FragmentProfile.newInstance(), this, true, R.id.profileContent);

        Toolbar tbCreateEmail=findViewById(R.id.tbProfile);
        setSupportActionBar(tbCreateEmail);

        fillData();
    }

    private void fillData() {
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_FIRST, ReviewerSQLiteHelper.COLUMN_LAST, ReviewerSQLiteHelper.COLUMN_DISPLAY, ReviewerSQLiteHelper.COLUMN_EMAIL,
                ReviewerSQLiteHelper.COLUMN_PASSWORD};

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
        String login=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
        String selectionClause=ReviewerSQLiteHelper.COLUMN_EMAIL+" LIKE ?";
        String[] selectionArgs={login};
        Cursor cursor=getContentResolver().query(dbUser.CONTENT_URI_USER,allColumns,selectionClause,selectionArgs,null);

        cursor.moveToFirst();
        User user = createUser(cursor);

        TextView first = (TextView)findViewById(R.id.etFirst);
        TextView last = (TextView)findViewById(R.id.etLast);
        TextView email = (TextView)findViewById(R.id.etEmail);
        TextView password = (TextView)findViewById(R.id.etPass);

        first.setText(user.getFirst());
        last.setText(user.getLast());
        email.setText(user.getEmail());
        password.setText(user.getPassword());

        cursor.close();
    }

        public static User createUser(Cursor cursor) {
            User user = new User();
            if(cursor.getCount()!=0){
                user.setId(cursor.getLong(0));
                user.setFirst(cursor.getString(1));
                user.setLast(cursor.getString(2));
                user.setDisplay(cursor.getString(3));
                user.setEmail(cursor.getString(4));
                user.setPassword(cursor.getString(5));
            }
            return user;
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
                        ReviewerSQLiteHelper.COLUMN_FIRST, ReviewerSQLiteHelper.COLUMN_LAST, ReviewerSQLiteHelper.COLUMN_DISPLAY, ReviewerSQLiteHelper.COLUMN_EMAIL,
                        ReviewerSQLiteHelper.COLUMN_PASSWORD};

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                Log.d("Ulogovani: ", sharedPreferences.getString(getString(R.string.login), "Nema ulogovanog"));
                String login = sharedPreferences.getString(getString(R.string.login), "Nema ulogovanog");
                String selectionClause = ReviewerSQLiteHelper.COLUMN_EMAIL + " LIKE ?";
                String[] selectionArgs = {login};
                Cursor cursor = getContentResolver().query(dbUser.CONTENT_URI_USER, allColumns, selectionClause, selectionArgs, null);

                cursor.moveToFirst();
                User user = createUser(cursor);

                TextView first = (TextView) findViewById(R.id.etFirst);
                TextView last = (TextView) findViewById(R.id.etLast);
                TextView password = (TextView) findViewById(R.id.etPass);

                user.setFirst(first.getText().toString());
                user.setLast(last.getText().toString());
                user.setPassword(password.getText().toString());

                ReviewerSQLiteHelper dbHelperUser = new ReviewerSQLiteHelper(this);
                SQLiteDatabase dbUsers = dbHelperUser.getWritableDatabase();
                {
                    ContentValues entryUser = new ContentValues();
                    entryUser.put(ReviewerSQLiteHelper.COLUMN_FIRST, user.getFirst());
                    entryUser.put(ReviewerSQLiteHelper.COLUMN_LAST, user.getLast());
                    entryUser.put(ReviewerSQLiteHelper.COLUMN_DISPLAY, user.getDisplay());
                    entryUser.put(ReviewerSQLiteHelper.COLUMN_EMAIL, user.getEmail());
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
