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
import com.example.mojprojekat.tools.FragmentTransition;

public class CreateContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);
        this.setTitle("Kreiraj_kontakt");
        FragmentTransition.to(FragmentCreateContact.newInstance(), this, true, R.id.createContactContent);

        EditText etUsername=findViewById(R.id.etUsername);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        etUsername.setText(sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
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
            case R.id.action_back:
                Intent intent = new Intent(CreateContactActivity.this, ContactsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_cancel:
                Intent intent1 = new Intent(CreateContactActivity.this, ContactsActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.action_save:
                Toast.makeText(this, "Uspešno sacuvano!",Toast.LENGTH_SHORT).show();
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
