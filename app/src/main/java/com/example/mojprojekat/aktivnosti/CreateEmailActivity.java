package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        String option=getIntent().getStringExtra("option");
        switch (option){
            case "send":{
                EditText etFrom=findViewById(R.id.etFrom);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String username=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
                username=Data.userAccount("email",username);
                etFrom.setText(username);
                break;
            }
            case "replay":{
                LinearLayout linCc=findViewById(R.id.linCc);
                ((ViewGroup) linCc.getParent()).removeView(linCc);
                LinearLayout linBcc=findViewById(R.id.linBcc);
                ((ViewGroup) linBcc.getParent()).removeView(linBcc);
                String emailTo=getIntent().getStringExtra("emailTo");
                String subjectReplay=getIntent().getStringExtra("subjectReplay");
                EditText etFrom=findViewById(R.id.etFrom);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String username=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
                username=Data.userAccount("email",username);
                etFrom.setText(username);
                EditText etTo=findViewById(R.id.etTo);
                etTo.setText(emailTo);
                EditText subjet=findViewById(R.id.etSubject);
                subjet.setText(subjectReplay);
                break;
            }
            case "forward":{
                long idMessage=getIntent().getLongExtra("id",0);
                System.out.println("id: "+idMessage);
                try {
                    Message message=Data.getMessageById(Long.valueOf(idMessage));
                    EditText etFrom=findViewById(R.id.etFrom);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    String username=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
                    username=Data.userAccount("email",username);
                    etFrom.setText(username);
                    EditText subjet=findViewById(R.id.etSubject);
                    subjet.setText(message.getSubject());
                    EditText content=findViewById(R.id.etContent);
                    content.setText("---------- Forwarded message ---------\n" +
                            "Od: "+message.getFrom()+"\n" +
                            "Date: "+DateUtil.formatTimeWithSecond(message.getDateTime())+"\n" +
                            "Subject: "+message.getSubject()+"\n" +
                            "To: "+message.getTo()+"\n"+"\n"+message.getContent());
                    break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            case "replayAll":{
                long idMessage=getIntent().getLongExtra("id",0);
                System.out.println("id: "+idMessage);
                try {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    Message message=Data.getMessageById(Long.valueOf(idMessage));
                    EditText etFrom=findViewById(R.id.etFrom);
                    EditText etTo=findViewById(R.id.etTo);
                    EditText etCc=findViewById(R.id.etCc);
                    EditText etBcc=findViewById(R.id.etBcc);
                    if(message.getCc().equals("")){
                        message.setCc(message.getTo());
                    }
                    else if(!message.getCc().equals("")){
                        message.setCc(message.getTo()+","+message.getCc());
                        String[] emails=message.getCc().split(",");
                        String email="";
                        for(int i=0; i<emails.length; i++){
                            if(!(sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog").split("|")[1]).equals(emails[i])){
                                    email=email+emails[i]+",";
                            }
                        }if(email.equals("")){
                            etCc.setText(message.getCc());
                        }else {
                            email=email.substring(0,email.length()-1);
                            etCc.setText(email);
                        }
                    }else{
                        LinearLayout linCc=findViewById(R.id.linCc);
                        ((ViewGroup) linCc.getParent()).removeView(linCc);
                    }
                    message.setTo(message.getFrom());
                    etTo.setText(message.getTo());
                    if(!message.getBcc().equals("")){
                        String[] emails=message.getBcc().split(",");
                        String email="";
                        for(int i=0; i<emails.length; i++){
                            if(!(sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog").split("|")[1]).equals(emails[i])){
                                    email=email+emails[i]+",";
                            }
                        }
                        if(email.equals("")){
                            etBcc.setText(message.getBcc());
                        }else {
                            email=email.substring(0,email.length()-1);
                            etBcc.setText(email);
                        }
                    }else{
                        LinearLayout linBcc=findViewById(R.id.linBcc);
                        ((ViewGroup) linBcc.getParent()).removeView(linBcc);
                    }
                    String username=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
                    username=Data.userAccount("email",username);
                    etFrom.setText(username);
                    EditText subjet=findViewById(R.id.etSubject);
                    subjet.setText(message.getSubject());
                    break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        Toolbar tbCreateEmail=findViewById(R.id.tbCreateEmail);
        setSupportActionBar(tbCreateEmail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_cancel:
                Intent intent1 = new Intent(CreateEmailActivity.this, EmailsActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.action_send:
                EditText etFrom=findViewById(R.id.etFrom);
                EditText etTo=findViewById(R.id.etTo);
                EditText etCc=findViewById(R.id.etCc);
                EditText etBcc=findViewById(R.id.etBcc);
                if(etTo.getText().toString().equals("") && etCc.getText().toString().equals("") && etBcc.getText().toString().equals("")){
                    Toast.makeText(this,"Dodajte bar jednog primaoca!", Toast.LENGTH_SHORT).show();
                }else{
                    EditText etSubject=findViewById(R.id.etSubject);
                    EditText etContent=findViewById(R.id.etContent);
                    Message message=new Message();
                    message.setFrom(etFrom.getText().toString());
                    if(etTo!=null){
                        message.setTo(etTo.getText().toString());
                    }
                    if(etCc!=null){
                        message.setCc(etCc.getText().toString());
                    }
                    if(etBcc!=null){
                        message.setBcc(etBcc.getText().toString());
                    }
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
                }
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
