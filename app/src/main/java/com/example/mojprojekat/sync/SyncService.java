package com.example.mojprojekat.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.mojprojekat.tools.ReviewerTools;

public class SyncService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int status = ReviewerTools.getConnectivityStatus(getApplicationContext());
        //ima konekcije ka netu skini sta je potrebno i sinhronizuj bazu
        if(status == ReviewerTools.TYPE_WIFI || status == ReviewerTools.TYPE_MOBILE){
            new SyncTask(getApplicationContext()).execute();
        }
        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
