package com.example.mojprojekat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.mojprojekat.aktivnosti.EmailsActivity;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.modelDTO.MessageDTO;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.DateUtil;
import com.example.mojprojekat.tools.ReviewerTools;

import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("Usao sam u MessageService! <-------------------------------");
        final Intent ints = new Intent(EmailsActivity.SYNC_DATA);
        int status = ReviewerTools.getConnectivityStatus(getApplicationContext());
        ints.putExtra(RESULT_CODE, status);

        //ima konekcije ka netu skini sta je potrebno i sinhronizuj bazu
        if(status == ReviewerTools.TYPE_WIFI || status == ReviewerTools.TYPE_MOBILE) {
            String option=intent.getStringExtra("option");
            /*
             * Poziv REST servisa se odvija u pozadini i mi ne moramo da vodimo racuna o tome
             * Samo je potrebno da registrujemo sta da se desi kada odgovor stigne od nas
             * Taj deo treba da implementiramo dodavajuci Callback<List<Event>> unutar enqueue metode
             *
             * Servis koji pozivamo izgleda:
             * http://<service_ip_adress>:<service_port>/rs.ftn.reviewer.rest/rest/proizvodi/
             * */
            switch (option){
                case "delete":{
                    System.out.println("Id: "+intent.getLongExtra("id",0)+"<---------------");
                    long id=intent.getLongExtra("id",0);
                    System.out.println("Trazim poruku! <-------------------------------");
                    Call<ResponseBody> call = ServiceUtils.mailService.delete(id);
                    System.out.println("Pronasao poruku i saljem ju! Id poruke je: "+id+" <-------------------------------");
                    System.out.println("putanje: "+ServiceUtils.SERVICE_API_PATH+ServiceUtils.DELETE);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 204) {
                                Log.d("REZ", "Meesage deleted");
                            } else {
                                Log.d("REZ", "Meesage deleted: " + response.code());
                            }
                            sendBroadcast(ints);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                        }
                    });
                    break;
                }
                case "send":{
                    System.out.println("Id: "+intent.getLongExtra("id",0)+"<---------------");
                    long id=intent.getLongExtra("id",0);
                    try {
                        Message message= Data.getMessageById(id);
                        Data.messages.clear();
                        System.out.println("\nCC je: "+message.getCc()+"<--------------------------");
                        MessageDTO messageDTO=new MessageDTO(message);
                        Call<MessageDTO> call = ServiceUtils.mailService.send(messageDTO);
                        call.enqueue(new Callback<MessageDTO>() {
                            @Override
                            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                                if (response.code() == 201) {
                                    Log.d("REZ", "Meesage send");
                                    MessageDTO messageDTO1=response.body();
                                    try {
                                        Message message1 = new Message(messageDTO1.getId(),messageDTO1.getFrom(),messageDTO1.getTo(),messageDTO1.getCc(),messageDTO1.getBcc(),
                                            DateUtil.convertFromDMYHMS(messageDTO1.getDateTime()),messageDTO1.getSubject(),messageDTO1.getContent());
                                        Data.addMessage(MessageService.this,message1);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.d("REZ", "Meesage send: " + response.code());
                                }
                                sendBroadcast(ints);
                            }

                            @Override
                            public void onFailure(Call<MessageDTO> call, Throwable t) {
                                Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                            }
                        });
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}