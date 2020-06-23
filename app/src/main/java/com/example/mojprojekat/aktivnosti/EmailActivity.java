package com.example.mojprojekat.aktivnosti;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderEmail;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.fragmenti.FragmentEmail;
import com.example.mojprojekat.model.Contact;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.service.MessageService;
import com.example.mojprojekat.tools.FragmentTransition;

import java.text.ParseException;

import static com.example.mojprojekat.tools.Data.getMessageById;
import static com.example.mojprojekat.tools.Data.messages;

public class EmailActivity extends AppCompatActivity {

    private static Contact c1=new Contact(1,"Mika","Mikic","mika@gmail.com");
    private static Contact c2=new Contact(2,"Zika","Zikic","mika@gmail.com");
    private Uri todoUri;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        FragmentTransition.to(FragmentEmail.newInstance(), this, true, R.id.emailContent);

        Toolbar toolbar=findViewById(R.id.tbEmail);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();

        Long id = extras. getLong("id");
        try {
            fillData(id);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void fillData(Long id) throws ParseException {
       /* String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_FROM, ReviewerSQLiteHelper.COLUMN_TO, ReviewerSQLiteHelper.COLUMN_CC, ReviewerSQLiteHelper.COLUMN_BCC,
                ReviewerSQLiteHelper.COLUMN_DATE_TIME,  ReviewerSQLiteHelper.COLUMN_SUBJECT, ReviewerSQLiteHelper.COLUMN_CONTENT };

        Cursor cursor = getContentResolver().query(todoUri, allColumns, null, null,
                null);

        cursor.moveToFirst();
        Message message = createMessage(cursor);*/

        todoUri = Uri.parse(DBContentProviderEmail.CONTENT_URI_EMAIL + "/" + id);
        message= getMessageById(id);

        TextView tvFrom = (TextView)findViewById(R.id.tvFrom);
        TextView tvSubject = (TextView)findViewById(R.id.tvSubject);
        TextView tvContent = (TextView)findViewById(R.id.tvContent);

        tvFrom.setText(message.getFrom());
        tvSubject.setText(message.getSubject());
        tvContent.setText(message.getContent());


        //cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_back:
                Intent intent = new Intent(EmailActivity.this, EmailsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_delete:
                Intent intent2 = new Intent(this, MessageService.class);
                intent2.putExtra("id",message.getId());
                intent2.putExtra("option","delete");
                startService(intent2);
                message.setActive(false);
                messages.remove(message);
                ContentValues entryMessage=new ContentValues();
                entryMessage.put(ReviewerSQLiteHelper.COLUMN_ACTIVE,message.isActive());
                int update = getContentResolver().update(todoUri,entryMessage, null, null);
                Log.d("\nBroj azuriranih redova",String.valueOf(update)+"\n");
                Toast.makeText(this, "Uspesno ste obrisali poruku!",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(EmailActivity.this, EmailsActivity.class);
                startActivity(intent1);
                finish();
                return true;
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
        if(message.isUnread()){
            Toast.makeText(this,"Poruka je otvorena!", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(this, MessageService.class);
            intent2.putExtra("id",message.getId());
            intent2.putExtra("option","update");
            startService(intent2);
        }
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
