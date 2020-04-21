package com.example.mojprojekat.aktivnosti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mojprojekat.R;
import com.example.mojprojekat.adapteri.DrawerListAdapter;
import com.example.mojprojekat.fragmenti.MyFragment;
import com.example.mojprojekat.model.NavItem;
import com.example.mojprojekat.tools.FragmentTransition;

import java.util.ArrayList;

public class EmailsActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerPane;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emails);

        prepareMenu(mNavItems);

        mTitle=getTitle();
        mDrawerLayout=findViewById(R.id.drawerLayout);
        mDrawerList = findViewById(R.id.navList);

        mDrawerPane=findViewById(R.id.drawerPane);
        DrawerListAdapter adapter=new DrawerListAdapter(this,mNavItems);

        // postavljamo senku koja preklama glavni sadrzaj
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // dodajemo listener koji ce reagovati na klik pojedinacnog elementa u listi
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
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

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }

    private void selectItemFromDrawer(int position) {
        if(position == 0){
            FragmentTransition.to(MyFragment.newInstance(), this, false, R.id.mainContent);
        }else if(position == 1){
            Intent intent = new Intent(EmailsActivity.this, SettingsActivity.class);
            startActivity(intent);
        }else if(position == 2){
            Intent intent = new Intent(EmailsActivity.this, FoldersActivity.class);
            startActivity(intent);
        }else if(position == 3){
            //..
        }else if(position == 4){
            //..
        }else if(position == 5){
            //...
        }else{
            Log.e("DRAWER", "Nesto van opsega!");
        }

        mDrawerList.setItemChecked(position, true);
        if(position != 5) // za sve osim za sync
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
