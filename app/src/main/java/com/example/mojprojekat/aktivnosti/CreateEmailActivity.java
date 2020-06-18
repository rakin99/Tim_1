package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderEmail;
import com.example.mojprojekat.fragmenti.FragmentCreateEmail;
import com.example.mojprojekat.model.Contact;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.service.MessageService;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.DateUtil;
import com.example.mojprojekat.tools.FragmentTransition;
import com.example.mojprojekat.tools.Util;

import java.text.ParseException;

public class CreateEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_email);
        this.setTitle(R.string.kreiraj_e_mail);
        FragmentTransition.to(FragmentCreateEmail.newInstance(), this, true, R.id.createEmailContent);

        EditText etFrom=findViewById(R.id.etFrom);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        etFrom.setText(sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
        Toolbar tbCreateEmail=findViewById(R.id.tbCreateEmail);
        setSupportActionBar(tbCreateEmail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_back:
                Intent intent = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_cancel:
                Intent intent1 = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.action_send:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                EditText etFrom=findViewById(R.id.etFrom);
                EditText etTo=findViewById(R.id.etTo);
                EditText etSubject=findViewById(R.id.etSubject);
                EditText etContent=findViewById(R.id.etContent);
                Message message=new Message();
                message.setId(Data.messages.get(Data.messages.size()).getId());
                System.out.println("\nNovi ID je: "+message.getId()+"<-----------------------------------\n");
                Contact c1=new Contact(1,"Mika","Mikic","mika@gmail.com");
                Contact c2=new Contact(2,"Zika","Zikic","mika@gmail.com");
                message.setFrom(c1.getEmail());
                message.setTo(c2.getEmail());
                message.setSubject(etSubject.getText().toString());
                message.setContent(etContent.getText().toString());
                try {
                    message.setDateTime(DateUtil.formatTimeWithSecond(DateUtil.getNow()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("\n\nVreme slanja poruke: ",String.valueOf(message.getDateTime()));
                message.setCc("cc");
                message.setBcc("bcc");
                try {
                    Uri retVal = getContentResolver().insert(DBContentProviderEmail.CONTENT_URI_EMAIL, Util.createContentValues(this, message));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Data.messages.add(message);
                Intent intent2 = new Intent(this, MessageService.class);
                intent2.putExtra("id",message.getId());
                intent2.putExtra("option","send");
                startService(intent2);
                Toast.makeText(this, "Uspešno ste poslali poruku!",Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                startActivity(intent3);
                finish();
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
