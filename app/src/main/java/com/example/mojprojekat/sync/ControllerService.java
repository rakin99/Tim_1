package com.example.mojprojekat.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.mojprojekat.R;
import com.example.mojprojekat.tools.ReviewerTools;

public class ControllerService extends Service {

    private SyncReceiver sync;
    private SharedPreferences sharedPreferences;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    public static String SYNC_DATA = "SYNC_DATA";
    private boolean allowSync;
    private String synctime;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId){
        System.out.println("U ControllerService sam");
        setUpReceiver();
        consultPreferences();
        if (manager == null) {
            setUpReceiver();
        }

        if(allowSync){
            System.out.println("U ControllerService sam");
            int interval = ReviewerTools.calculateTimeTillNextSync(Integer.parseInt(synctime));
            manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(SYNC_DATA);

        registerReceiver(sync, filter);
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        System.out.println("onDestroy()");
        Toast.makeText(this,"onDestroy()", Toast.LENGTH_SHORT).show();
        if (manager != null) {
            manager.cancel(pendingIntent);
        }

        //osloboditi resurse
        if(sync != null) {
            unregisterReceiver(sync);
        }
    }

    private void setUpReceiver(){
        System.out.println("Pozvao se: setUpReceiver");
        sync = new SyncReceiver();
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, SyncService.class);
        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
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
        synctime = sharedPreferences.getString(getString(R.string.pref_sync_list), "1");
        System.out.println("\nsynctime: "+synctime+"<-----------------------------------\n");// pola minuta
        allowSync = sharedPreferences.getBoolean(getString(R.string.pref_sync), true);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
