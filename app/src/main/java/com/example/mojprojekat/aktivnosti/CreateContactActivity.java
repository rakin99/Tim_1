package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.fragmenti.FragmentCreateContact;
import com.example.mojprojekat.tools.FragmentTransition;

public class CreateContactActivity extends AppCompatActivity {

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
                //sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                //Data.contacts.add(contact);
                //Util.insertContact(contact);
                Intent in = new Intent(CreateContactActivity.this, ContactsActivity.class);
                startActivity(in);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
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
