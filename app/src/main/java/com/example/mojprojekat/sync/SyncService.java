package com.example.mojprojekat.sync;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.mojprojekat.R;
import com.example.mojprojekat.aktivnosti.EmailsActivity;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.modelDTO.MessageDTO;
import com.example.mojprojekat.service.ServiceUtils;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.DateUtil;
import com.example.mojprojekat.tools.ReviewerTools;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";
    private SharedPreferences sharedPreferences;
    private String sort;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        final Intent ints = new Intent(EmailsActivity.SYNC_DATA);
        int status = ReviewerTools.getConnectivityStatus(getApplicationContext());
        ints.putExtra(RESULT_CODE, status);

        try {
            System.out.println("\n\nPozvan syncServis u: "+DateUtil.formatTimeWithSecond(DateUtil.getNow()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //ima konekcije ka netu skini sta je potrebno i sinhronizuj bazu
        if(status == ReviewerTools.TYPE_WIFI || status == ReviewerTools.TYPE_MOBILE) {

            /*
             * Poziv REST servisa se odvija u pozadini i mi ne moramo da vodimo racuna o tome
             * Samo je potrebno da registrujemo sta da se desi kada odgovor stigne od nas
             * Taj deo treba da implementiramo dodavajuci Callback<List<Event>> unutar enqueue metode
             *
             * Servis koji pozivamo izgleda:
             * http://<service_ip_adress>:<service_port>/rs.ftn.reviewer.rest/rest/proizvodi/
             * */
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SyncService.this);
            sort = sharedPreferences.getString(getString(R.string.sort), "DESC");
            String ulogovani=Data.userAccount("email",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
            String username=ulogovani;
            System.out.println("\nUsername: "+username+"<--------------------------------\n");
            Call<List<MessageDTO>> call = ServiceUtils.mailService.getMessages(username,sort);
            call.enqueue(new Callback<List<MessageDTO>>() {
                @Override
                public void onResponse(Call<List<MessageDTO>> call, Response<List<MessageDTO>> response) {
                    if (response.code() == 200) {
                        Log.d("REZ", "Meesages recieved, status 200");
                        Data.messages.clear();
                        for (int i=0; i<response.body().size(); i++
                        ) {
                            MessageDTO messageDTO = response.body().get(i);

                            try {
                                Message message = new Message(messageDTO.getId(),messageDTO.getFrom(),messageDTO.getTo(),messageDTO.getCc(),messageDTO.getBcc(),
                                        DateUtil.convertFromDMYHMS(messageDTO.getDateTime()),messageDTO.getSubject(),messageDTO.getContent(),messageDTO.isUnread(),messageDTO.isActive());

                                Data.addMessage(SyncService.this, message,sort);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Log.d("EZ", "Meesages recieved: " + response.code());
                    }
                    sendBroadcast(ints);
                }

                @Override
                public void onFailure(Call<List<MessageDTO>> call, Throwable t) {
                    Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                }
            });
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
