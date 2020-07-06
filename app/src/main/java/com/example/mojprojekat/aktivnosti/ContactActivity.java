package com.example.mojprojekat.aktivnosti;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mojprojekat.R;
import com.example.mojprojekat.database.DBContentProviderContact;
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.fragmenti.FragmentContact;
import com.example.mojprojekat.model.Contact;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.FragmentTransition;

import java.text.ParseException;

public class ContactActivity extends AppCompatActivity {

    /*private static Contact c1=new Contact(1,"Mika","Mikic","mika@gmail.com");
    private static Contact c2=new Contact(2,"Zika","Zikic","mika@gmail.com");*/
    private Uri todoUri;
    private Contact contact;

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
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_Id,
                ReviewerSQLiteHelper.COLUMN_FIRST, ReviewerSQLiteHelper.COLUMN_LAST, ReviewerSQLiteHelper.COLUMN_EMAIL };

        Cursor cursor = getContentResolver().query(todoUri, allColumns, null, null,
                null);

        cursor.moveToFirst();
        Contact contact = Data.createContact(cursor);

        todoUri = Uri.parse(DBContentProviderContact.CONTENT_URI_CONTACT + "/" + id);
        contact= Data.getContactById(id);

        TextView tvFirstName = (TextView)findViewById(R.id.tvFirstName);
        TextView tvLastName = (TextView)findViewById(R.id.tvLastName);
        TextView tvEmail = (TextView)findViewById(R.id.tvEmail);

        tvFirstName.setText(contact.getFirst());
        tvLastName.setText(contact.getLast());
        tvEmail.setText(contact.getEmail());


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
            case R.id.action_delete:
                contact.setActive(false);
                Data.contacts.remove(contact);
                ContentValues entryUser=new ContentValues();
                entryUser.put(ReviewerSQLiteHelper.COLUMN_ACTIVE,contact.isActive());
                int update = getContentResolver().update(todoUri,entryUser, null, null);
                Toast.makeText(this, "Uspesno ste obrisali kontakt!",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ContactActivity.this, ContactsActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.action_save:
                TextView tvFirstName = (TextView)findViewById(R.id.tvFirstName);
                TextView tvLastName = (TextView)findViewById(R.id.tvLastName);
                TextView tvEmail = (TextView)findViewById(R.id.tvEmail);

                contact.setFirst(tvFirstName.getText().toString());
                contact.setLast(tvLastName.getText().toString());
                contact.setEmail(tvEmail.getText().toString());

                contact.setActive(false);
                Data.contacts.remove(contact);
                ContentValues entryUser1=new ContentValues();
                entryUser1.put(ReviewerSQLiteHelper.COLUMN_ACTIVE,contact.isActive());
                int update1 = getContentResolver().update(todoUri,entryUser1, null, null);
                Toast.makeText(this, "Uspesno ste azurirali kontakt!",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ContactActivity.this, ContactsActivity.class);
                startActivity(intent2);
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
