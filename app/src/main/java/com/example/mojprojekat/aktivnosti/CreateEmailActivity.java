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
import com.example.mojprojekat.fragmenti.FragmentCreateEmail;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.service.MessageService;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.DateUtil;
import com.example.mojprojekat.tools.FragmentTransition;

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
                EditText etFrom=findViewById(R.id.etFrom);
                EditText etTo=findViewById(R.id.etTo);
                EditText etSubject=findViewById(R.id.etSubject);
                EditText etContent=findViewById(R.id.etContent);
                Message message=new Message();
                message.setFrom(etFrom.getText().toString());
                message.setTo(etTo.getText().toString());
                message.setSubject(etSubject.getText().toString());
                message.setContent(etContent.getText().toString());
                try {
                    message.setDateTime(DateUtil.getNow());
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
