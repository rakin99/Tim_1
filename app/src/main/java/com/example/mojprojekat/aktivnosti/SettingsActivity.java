package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;

import com.example.mojprojekat.R;
import com.example.mojprojekat.tools.FragmentTransition;

public class SettingsActivity extends AppCompatActivity {

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerVreme);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerSortiranje);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vreme, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,
                R.array.sortiranje, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        FragmentTransition.to(PrefsFragment.newInstance(), this,true, R.id.settingsContent);

        Toolbar tbCreateEmail=findViewById(R.id.tbSettings);
        setSupportActionBar(tbCreateEmail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_back_settings:
                Intent intent = new Intent(SettingsActivity.this, EmailsActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.activity_item_settings, menu);
        return super.onCreateOptionsMenu(menu);
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

    public static class PrefsFragment extends PreferenceFragmentCompat {

        private static PrefsFragment newInstance() {
            Bundle args = new Bundle();

            PrefsFragment fragment = new PrefsFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
