package com.example.mojprojekat.aktivnosti;

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
import com.example.mojprojekat.database.ReviewerSQLiteHelper;
import com.example.mojprojekat.fragmenti.FragmentEmail;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.tools.FragmentTransition;

public class EmailActivity extends AppCompatActivity {

    private Uri todoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        FragmentTransition.to(FragmentEmail.newInstance(), this, true, R.id.emailContent);

        Toolbar toolbar=findViewById(R.id.tbEmail);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

        todoUri = extras. getParcelable("id");
        fillData(todoUri);

    }

    private void fillData(Uri todoUri) {
        String[] allColumns = { ReviewerSQLiteHelper.COLUMN_ID,
                ReviewerSQLiteHelper.COLUMN_FROM, ReviewerSQLiteHelper.COLUMN_TO, ReviewerSQLiteHelper.COLUMN_CC, ReviewerSQLiteHelper.COLUMN_BCC,
                ReviewerSQLiteHelper.COLUMN_DATE_TIME,  ReviewerSQLiteHelper.COLUMN_SUBJECT, ReviewerSQLiteHelper.COLUMN_CONTENT };

        Cursor cursor = getContentResolver().query(todoUri, allColumns, null, null,
                null);

        cursor.moveToFirst();
        Message message = createMessage(cursor);

        TextView tvFrom = (TextView)findViewById(R.id.tvFrom);
        TextView tvSubject = (TextView)findViewById(R.id.tvSubject);
        TextView tvContent = (TextView)findViewById(R.id.tvContent);

        tvFrom.setText(message.getFrom());
        tvSubject.setText(message.getSubject());
        tvContent.setText(message.getContent());

        cursor.close();
    }

    public static Message createMessage(Cursor cursor) {
        Message message = new Message();
        message.setId(cursor.getLong(0));
        message.setFrom(cursor.getString(1));
        message.setTo(cursor.getString(2));
        message.setCc(cursor.getString(3));
        message.setBcc(cursor.getString(4));
        message.setDateTime(cursor.getString(5));
        message.setSubject(cursor.getString(6));
        message.setContent(cursor.getString(7));
        return message;
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(this, "onStart()",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onResume(){
        super.onResume();
        Toast.makeText(this, "onResume()",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(this,"onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(this,"onDestroy()", Toast.LENGTH_SHORT).show();
    }
}
