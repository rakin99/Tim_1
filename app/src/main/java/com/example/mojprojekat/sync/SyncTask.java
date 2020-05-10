package com.example.mojprojekat.sync;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.mojprojekat.aktivnosti.EmailsActivity;
import com.example.mojprojekat.aktivnosti.SettingsActivity;
import com.example.mojprojekat.tools.ReviewerTools;

public class SyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;


    public static String RESULT_CODE = "RESULT_CODE";

    public SyncTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        //postaviti parametre, pre pokretanja zadatka ako je potrebno
    }

    @Override
    protected Void doInBackground(Void... params) {

        //simulacija posla koji se obavlja u pozadini i traje duze vreme
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        Intent ints = new Intent(EmailsActivity.SYNC_DATA);
        int status = ReviewerTools.getConnectivityStatus(context);
        ints.putExtra(RESULT_CODE, status);
        context.sendBroadcast(ints);
    }
}
