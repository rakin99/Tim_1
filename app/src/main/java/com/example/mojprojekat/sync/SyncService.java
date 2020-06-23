package com.example.mojprojekat.sync;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

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

    private static int notificationID = 1;
    public static String RESULT_CODE = "RESULT_CODE";
    private SharedPreferences sharedPreferences;
    private static String channelId = "My_Chan_Id";
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
                @RequiresApi(api = Build.VERSION_CODES.O)
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
                            MessageDTO messageDTO = response.body().get(i);
                            try {
                                Message message = new Message(messageDTO.getId(),messageDTO.getFrom(),messageDTO.getTo(),messageDTO.getCc(),messageDTO.getBcc(),
                                        DateUtil.convertFromDMYHMS(messageDTO.getDateTime()),messageDTO.getSubject(),messageDTO.getContent(),messageDTO.isUnread(),messageDTO.isActive());
                                Data.addMessage(SyncService.this, message);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        for (Message m:Data.newMessages
                             ) {
                            System.out.println("Tu sam....<<<<<<<<<-------------!!!!!!!");
                            Intent intent = new Intent(SyncService.this, EmailsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(SyncService.this, 0, intent, 0);
                            NotificationManager mNotificationManager =
                                    (NotificationManager) SyncService.this.getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                CharSequence name = "Moje ime";// The user-visible name of the channel.
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
                                Notification notification =
                                        new Notification.Builder(SyncService.this)
                                                .setSmallIcon(R.drawable.email)
                                                .setContentTitle("Dobili ste novu poruku!")
                                                .setContentText(Data.newMessages.size()+" "+vratiTekst(String.valueOf(Data.newMessages.size())))
                                                .setContentIntent(pendingIntent)
                                                .setAutoCancel(true)
                                                .setChannelId(channelId).build();
                                System.out.println("Sistem je veci od O<<<<----------------------------\n\n");
                                mNotificationManager.createNotificationChannel(mChannel);
                                mNotificationManager.notify(notificationID , notification);
                            }else{
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SyncService.this, channelId);
                                mBuilder.setContentTitle("Dobili ste novu poruku!");
                                mBuilder.setContentText(Data.newMessages.size()+" "+vratiTekst(String.valueOf(Data.newMessages.size())));
                                mBuilder.setSmallIcon(R.drawable.email);
                                mBuilder.setChannelId(channelId);
                                mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                mBuilder.setContentIntent(pendingIntent);
                                mBuilder.setAutoCancel(true);
                                mNotificationManager.notify(notificationID, mBuilder.build());
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

    private static String vratiTekst(String br){
        String poruka="";
        int duzina=br.length();
        String poslednjiBr=br.substring(duzina-1);
        if(Integer.parseInt(br)>9 && Integer.parseInt(br)<101){
            poruka = "nepročitanih poruka.";
        }
        else if(poslednjiBr.equals("1")){
            poruka = "nepročitana poruka.";
        }else if(poslednjiBr.equals("2") || poslednjiBr.equals("3") || poslednjiBr.equals("4")){
            poruka = "nepročitane poruke.";
        }else if(poslednjiBr.equals("5") || poslednjiBr.equals("6") || poslednjiBr.equals("7") || poslednjiBr.equals("8") || poslednjiBr.equals("9")){
            poruka = "nepročitanih poruka.";
        }
        return poruka;
    }
}
