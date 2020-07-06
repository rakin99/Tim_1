package com.example.mojprojekat.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.mojprojekat.aktivnosti.EmailsActivity;
import com.example.mojprojekat.aktivnosti.LoginActivity;
import com.example.mojprojekat.model.User;
import com.example.mojprojekat.tools.ReviewerTools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";
    private SharedPreferences sharedPreferences;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("Usao sam u UserService! <-------------------------------");
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
                case "add":{
                    String first=intent.getStringExtra("first");
                    String last=intent.getStringExtra("last");
                    String username=intent.getStringExtra("username");
                    String password=intent.getStringExtra("password");
                    User user=new User();
                    user.setFirst(first);
                    user.setLast(last);
                    user.setUsername(username);
                    user.setPassword(password);
                    Call<User> call = ServiceUtils.mailService.addUser(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 201) {
                                Log.d("REZ", "Account created");
                                Toast.makeText(UserService.this,"Uspešno ste se registrovali!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UserService.this, LoginActivity.class);
                                startActivity(i);
                            }else if(response.code() == 204){
                                Toast.makeText(UserService.this,"Korisnik sa ovim korisničkim imenom vec postoji!\nPokušajte ponovo!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("REZ", "Account created: " + response.code());
                            }
                            sendBroadcast(ints);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                        }
                    });
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
