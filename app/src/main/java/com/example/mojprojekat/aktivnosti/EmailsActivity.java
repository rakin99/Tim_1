package com.example.mojprojekat.aktivnosti;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mojprojekat.R;
import com.example.mojprojekat.adapteri.DrawerListAdapter;
import com.example.mojprojekat.fragmenti.FragmentEmails;
import com.example.mojprojekat.model.NavItem;
import com.example.mojprojekat.sync.SyncReceiver;
import com.example.mojprojekat.sync.SyncService;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.FragmentTransition;
import com.example.mojprojekat.tools.ReviewerTools;

import java.text.ParseException;
import java.util.ArrayList;

import static java.lang.Integer.*;
import static java.lang.System.*;

public class EmailsActivity extends AppCompatActivity {

    private String username;
    private ImageView profile;
    private TextView profileUserName;
    private RelativeLayout profileBox;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerPane;
    private CharSequence mTitle;
    private AlertDialog dialog;


    public static String SYNC_DATA = "SYNC_DATA";
    private SyncReceiver sync;

    private PendingIntent pendingIntent;

    private AlarmManager manager;

    private boolean allowSync;

    private String sort;
    private SharedPreferences sharedPreferences;
    private String synctime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emails);
        prepareMenu(mNavItems);

        mTitle=getTitle();
        mDrawerLayout=findViewById(R.id.drawerLayout);
        mDrawerList = findViewById(R.id.navList);
        profile=findViewById(R.id.avatar);
        profileUserName=findViewById(R.id.userName);

        profileBox=findViewById(R.id.profileBox);

        mDrawerPane=findViewById(R.id.drawerPane);
        DrawerListAdapter adapter=new DrawerListAdapter(this,mNavItems);

        // postavljamo senku koja preklama glavni sadrzaj
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // dodajemo listener koji ce reagovati na klik pojedinacnog elementa u listi
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        profileBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailsActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        // drawer-u postavljamo unapred definisan adapter
        mDrawerList.setAdapter(adapter);

        Toolbar tbEmails=findViewById(R.id.tbEmails);
        setSupportActionBar(tbEmails);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.drawer);
        }

        /*
         * drawer-u specificiramo za koju referencu toolbar-a da se veze
         * Specificiramo sta ce da pise unutar Toolbar-a kada se drawer otvori/zatvori
         * i specificiramo sta ce da se desava kada se drawer otvori/zatvori.
         * */
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                tbEmails,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("iReviewer");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Izborom na neki element iz liste, pokrecemo akciju
        if (savedInstanceState == null) {

            selectItemFromDrawer(0);
        }

        setUpReceiver();
        consultPreferences();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }

    private void selectItemFromDrawer(int position) {
        if(position == 0){
            FragmentTransition.to(FragmentEmails.newInstance(), this, false, R.id.mainContent);
        }else if(position == 1){
            Intent intent = new Intent(EmailsActivity.this, SettingsActivity.class);
            startActivity(intent);
        }else if(position == 2){
            Intent intent = new Intent(EmailsActivity.this, FoldersActivity.class);
            startActivity(intent);
        }else if(position == 3){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(getString(R.string.login),"");
            editor.commit();
            Intent intent = new Intent(EmailsActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Log.e("DRAWER", "Nesto van opsega!");
        }

        mDrawerList.setItemChecked(position, true);
        if(position != 2)
        {
            setTitle(mNavItems.get(position).getmTitle());
        }
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void prepareMenu(ArrayList<NavItem> mNavItems){
        mNavItems.add(new NavItem(getString(R.string.poruke),R.drawable.email));
        mNavItems.add(new NavItem(getString(R.string.settings),R.drawable.ic_settings_applications_black_24dp));
        mNavItems.add(new NavItem(getString(R.string.all_folders),R.drawable.folders));
        mNavItems.add(new NavItem(getString(R.string.logout),R.drawable.exit));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:{
                Intent intent = new Intent(EmailsActivity.this, CreateEmailActivity.class);
                startActivity(intent);
            }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    private void setUpReceiver(){
        sync = new SyncReceiver();

        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, SyncService.class);
        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    protected  void onResume(){
        super.onResume();

        Data.newMessages.clear();
        try {
            Data.readMessages(this,sort,username);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        out.println("Broj poruka: "+ Data.messages.size() +"<---------------------------------------------");

        //Za slucaj da referenca nije postavljena da se izbegne problem sa androidom!
        if (manager == null) {
            setUpReceiver();
        }

        if(allowSync){
            int interval = ReviewerTools.calculateTimeTillNextSync(parseInt(synctime));
            manager.setRepeating(AlarmManager.RTC_WAKEUP, currentTimeMillis(), interval, pendingIntent);
            /*Intent alarmIntent = new Intent(this, SyncService.class);
            startService(alarmIntent);*/
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(SYNC_DATA);

        registerReceiver(sync, filter);
    }

    private void consultPreferences(){

        /*
         * getDefaultSharedPreferences():
         * koristi podrazumevano ime preference-file-a.
         * Podrzazumevani fajl je setovan na nivou aplikacije tako da sve aktivnosti u istom context-u mogu da mu pristupe jednostavnije
         * getSharedPreferences(name,mode):
         * trazi da se specificira ime preference file-a requires i mod u kome se radi (e.g. private, world_readable, etc.)
         */
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * Koristeci parove kljuc-vrednost iz shared preferences mozemo da dobijemo
         * odnosno da zapisemo nekakve vrednosti. Te vrednosti mogu da budu iskljucivo
         * prosti tipovi u Javi.
         * Kao prvi parametar prosledjujemo kljuc, a kao drugi podrazumevanu vrednost,
         * ako nesto pod tim kljucem se ne nalazi u storage-u, da dobijemo podrazumevanu
         * vrednost nazad, i to nam je signal da nista nije sacuvano pod tim kljucem.
         * */
        username=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog!");
        synctime = sharedPreferences.getString(getString(R.string.pref_sync_list), "1");
        System.out.println("\nsynctime: "+synctime+"<-----------------------------------\n");// pola minuta
        allowSync = sharedPreferences.getBoolean(getString(R.string.pref_sync), true);
        sort = sharedPreferences.getString(getString(R.string.sort), "DESC");
        System.out.println("\nsort: "+sort+"<-----------------------------------\n");
    }

    @Override
    protected void onPause(){
        if (manager != null) {
            manager.cancel(pendingIntent);
        }

        //osloboditi resurse
        if(sync != null){
            unregisterReceiver(sync);
        }

        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Toast.makeText(this,"onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //Toast.makeText(this,"onDestroy()", Toast.LENGTH_SHORT).show();
    }
}
