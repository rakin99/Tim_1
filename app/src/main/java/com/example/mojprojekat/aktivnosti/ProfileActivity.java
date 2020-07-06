package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderUser;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.fragmenti.FragmentProfile;
import com.example.mojprojekat.model.Account;
import com.example.mojprojekat.model.User;
import com.example.mojprojekat.service.AccountService;
import com.example.mojprojekat.service.UserService;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.FragmentTransition;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    DBContentProviderUser dbUser;
    private SharedPreferences sharedPreferences;
    private Button btnNewAccount;
    private Button btnRemoveAccount;
    private Spinner spiner;
    private ArrayAdapter<String> adp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.setTitle(R.string.my_profile);

        FragmentTransition.to(FragmentProfile.newInstance(), this, true, R.id.profileContent);

        Toolbar tbCreateEmail=findViewById(R.id.tbProfile);
        setSupportActionBar(tbCreateEmail);
        btnNewAccount=findViewById(R.id.btnNewAccount);
        btnRemoveAccount=findViewById(R.id.btnRemoveAccount);

        fillData();
    }

    private void fillData() {
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_SMTP, ReviewerSQLiteHelper.COLUMN_POP3_IMAP, ReviewerSQLiteHelper.COLUMN_DISPLAY, ReviewerSQLiteHelper.COLUMN_USERNAME,
                ReviewerSQLiteHelper.COLUMN_PASSWORD};

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
        String login=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
        login=Data.userAccount("email",login);
        //String selectionClause=ReviewerSQLiteHelper.COLUMN_USERNAME +" LIKE ?";
       // String[] selectionArgs={login};
       /* Cursor cursor=getContentResolver().query(dbUser.CONTENT_URI_USER,allColumns,selectionClause,selectionArgs,null);

        cursor.moveToFirst();
        Account account = createUser(cursor);*/

        EditText etUsername=findViewById(R.id.etUsername);
        EditText etPassword=findViewById(R.id.etPass);
        EditText etFirst=findViewById(R.id.etFirstname);
        EditText etLast=findViewById(R.id.etLastname);
        spiner=(Spinner) findViewById(R.id.spinnerAccount);
        adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getEmailsFromAccounts());
        adp1.clear();
        adp1.addAll(getEmailsFromAccounts());
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adp1);


        spiner.setSelection(adp1.getPosition(login));
        User u=Data.user;
        etUsername.setText(u.getUsername());
        etPassword.setText(u.getPassword());
        etFirst.setText(u.getFirst());
        etLast.setText(u.getLast());

        //cursor.close();
    }

    public static ArrayList<String> getEmailsFromAccounts(){
        ArrayList<String> emails=new ArrayList<String>();
        for (Account a:Data.accounts
             ) {
            emails.add(a.getUsername()+"@"+a.getSmtpAddress());
        }
        return emails;
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
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save: {
                Intent i=new Intent(ProfileActivity.this, UserService.class);
                i.putExtra("option","update");
                EditText etUsername=findViewById(R.id.etUsername);
                EditText etPassword=findViewById(R.id.etPass);
                EditText etFirst=findViewById(R.id.etFirstname);
                EditText etLast=findViewById(R.id.etLastname);
                Data.user.setFirst(etFirst.getText().toString());
                Data.user.setLast(etLast.getText().toString());
                Data.user.setPassword(etPassword.getText().toString());
                startService(i);
                Toast.makeText(this, "Sačuvane su izmene", Toast.LENGTH_SHORT).show();
                Intent i2=new Intent(ProfileActivity.this,EmailsActivity.class);
                startActivity(i2);
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
        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ProfileActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                String ulogovani=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
                ulogovani=Data.userAccount("username",ulogovani);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(getString(R.string.login),ulogovani+"|"+spiner.getSelectedItem().toString());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnRemoveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account=findByEmail(spiner.getSelectedItem().toString());
                Data.accounts.remove(account);
                adp1.remove(spiner.getSelectedItem().toString());
                if(Data.accounts.size()>0){
                    spiner.setSelection(0);
                }else{
                    String ulogovani=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
                    ulogovani=Data.userAccount("username",ulogovani);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(getString(R.string.login),ulogovani+"|");
                    editor.commit();
                }
                Intent i=new Intent(ProfileActivity.this, AccountService.class);
                i.putExtra("id",String.valueOf(account.getId()));
                i.putExtra("option","delete");
                startService(i);
            }
        });
    }

    private static Account findByEmail(String email){
        for (Account a:Data.accounts
             ) {
            if((a.getUsername()+"@"+a.getSmtpAddress()).equals(email))
            {
                return a;
            }
        }
        return null;
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
