package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderContact;
import com.example.mojprojekat.fragmenti.FragmentContact;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.tools.FragmentTransition;

import java.text.ParseException;

import static com.example.mojprojekat.tools.Data.getMessageById;

public class ContactActivity extends AppCompatActivity {

    /*private static Contact c1=new Contact(1,"Mika","Mikic","mika@gmail.com");
    private static Contact c2=new Contact(2,"Zika","Zikic","mika@gmail.com");*/
    private Uri todoUri;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        FragmentTransition.to(FragmentContact.newInstance(), this, true, R.id.contactContent);

        Toolbar tbContact=findViewById(R.id.tbContact);
        setSupportActionBar(tbContact);
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

        todoUri = Uri.parse(DBContentProviderContact.CONTENT_URI_CONTACT + "/" + id);
        message= getMessageById(id);

        TextView tvFirstName = (TextView)findViewById(R.id.tvFirstName);
        TextView tvLastName = (TextView)findViewById(R.id.tvLastName);
        TextView tvEmail = (TextView)findViewById(R.id.tvEmail);

        tvFirstName.setText(message.getFrom());
        tvLastName.setText(message.getSubject());
        tvEmail.setText(message.getContent());


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
                Intent intent = new Intent(ContactActivity.this, ContactsActivity.class);
                startActivity(intent);
                finish();
                return true;
            /*case R.id.action_delete:
                Intent intent2 = new Intent(this, MessageService.class);
                intent2.putExtra("id",message.getId());
                intent2.putExtra("option","delete");
                startService(intent2);
                message.setActive(false);
                messages.remove(message);
                ContentValues entryUser=new ContentValues();
                entryUser.put(ReviewerSQLiteHelper.COLUMN_ACTIVE,message.isActive());
                int update = getContentResolver().update(todoUri,entryUser, null, null);
                Log.d("\nBroj azuriranih redova",String.valueOf(update)+"\n");
                Toast.makeText(this, "Uspesno ste obrisali poruku!",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(EmailActivity.this, EmailsActivity.class);
                startActivity(intent1);
                finish();
                return true;*/
            case R.id.action_save:
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
