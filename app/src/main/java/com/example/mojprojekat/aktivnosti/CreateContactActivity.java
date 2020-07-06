package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.fragmenti.FragmentCreateContact;
import com.example.mojprojekat.model.Contact;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.FragmentTransition;
import com.example.mojprojekat.tools.Util;

import java.text.ParseException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class CreateContactActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        this.setTitle("Kreiraj kontakt");
        FragmentTransition.to(FragmentCreateContact.newInstance(), this, true, R.id.createContactContent);

        EditText etFirst=findViewById(R.id.etFirst);
        EditText etLast=findViewById(R.id.etLast);
        EditText etEmail=findViewById(R.id.etEmail);
        Toolbar tbCreateContact=findViewById(R.id.tbCreateContact);
        setSupportActionBar(tbCreateContact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_cancel:
                Intent intent = new Intent(CreateContactActivity.this, ContactsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_save:
                EditText etFirst=findViewById(R.id.etFirst);
                EditText etLast=findViewById(R.id.etLast);
                EditText etEmail=findViewById(R.id.etEmail);
                String mail = etEmail.getText().toString();
                Contact contact = new Contact();
                contact.setFirst(etFirst.getText().toString());
                contact.setLast(etLast.getText().toString());
                contact.setEmail(etEmail.getText().toString());

                if(etFirst.equals("") || etLast.equals("") || etEmail.equals("")){
                    Toast.makeText(this, "Morate popuniti sva polja!",Toast.LENGTH_SHORT).show();
                }else if(!isValidEmailAddress(mail)){
                    Toast.makeText(this, "Format e-mail adrese je pogrešan!\nPokušajte ponovo!",Toast.LENGTH_SHORT).show();
                }else {
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

                    String username=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog!");
                    username=Data.userAccount("email",username);
                    //String username = "Golub Nemanja";

                    Data.contacts.add(contact);
                    try {
                        Util.insertContact(CreateContactActivity.this,contact);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Intent in = new Intent(CreateContactActivity.this, ContactsActivity.class);
                    startActivity(in);
                    finish();
                }
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
    protected void onStart(){ super.onStart();}
    @Override
    protected  void onResume(){ super.onResume(); }
    @Override
    protected void onPause(){ super.onPause(); }
    @Override
    protected void onStop(){ super.onStop(); }
    @Override
    protected void onDestroy(){ super.onDestroy(); }
}
