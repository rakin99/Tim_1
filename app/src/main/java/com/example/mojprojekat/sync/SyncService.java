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
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Intent ints = new Intent(EmailsActivity.SYNC_DATA);
        int status = ReviewerTools.getConnectivityStatus(getApplicationContext());
        ints.putExtra(RESULT_CODE, status);

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
            String username=sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog");
            System.out.println("\nUsername: "+username+"<--------------------------------\n");
            Call<List<MessageDTO>> call = ServiceUtils.mailService.getMessages(username);
            call.enqueue(new Callback<List<MessageDTO>>() {
                @Override
                public void onResponse(Call<List<MessageDTO>> call, Response<List<MessageDTO>> response) {
                    if (response.code() == 200) {
                        Log.d("REZ", "Meesages recieved, status 200");
                        int lastInResponse=response.body().size();
                        Log.d("lastInResponse: ",String.valueOf(lastInResponse));
                        int lastInDataMessages=Data.messages.size();
                        Log.d("lastInDataMessages: ",String.valueOf(lastInDataMessages));
                        for (int i=0; i<response.body().size(); i++
                        ) {
                            System.out.println("Upisujem "+i+" poruku!<-----------------------------------");
                            MessageDTO messageDTO = response.body().get(i);
                            try {
                                Message message = new Message(messageDTO.getId(),messageDTO.getFrom(),messageDTO.getTo(),messageDTO.getCc(),messageDTO.getBcc(),
                                        DateUtil.convertFromDMYHMS(messageDTO.getDateTime()),messageDTO.getSubject(),messageDTO.getContent());
                                Data.addMessage(SyncService.this, message);
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
