package com.example.mojprojekat.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.mojprojekat.R;
import com.example.mojprojekat.aktivnosti.EmailsActivity;
import com.example.mojprojekat.aktivnosti.LoginActivity;
import com.example.mojprojekat.model.Account;
import com.example.mojprojekat.tools.ReviewerTools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";
    private SharedPreferences sharedPreferences;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("Usao sam u AccountService! <-------------------------------");
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
                    String email=intent.getStringExtra("email_adress");
                    String password=intent.getStringExtra("password");
                    String[] splitEmail=email.split("@");
                    String domen=splitEmail[1];
                    String username=splitEmail[0];
                    Account account=new Account();
                    account.setPassword(password);
                    account.setSmtpAddress(domen);
                    account.setUsername(username);
                    account.setInServerAddress("pop3");
                    Call<Account> call = ServiceUtils.mailService.add(account);
                    call.enqueue(new Callback<Account>() {
                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            if (response.code() == 201) {
                                Log.d("REZ", "Account created");
                                Toast.makeText(AccountService.this,"Uspešno ste se registrovali!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AccountService.this, LoginActivity.class);
                                startActivity(i);
                            }else if(response.code() == 204){
                                Toast.makeText(AccountService.this,"Korisnik sa ovom e-mail adresom vec postoji!\nPokušajte ponovo!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("REZ", "Account created: " + response.code());
                            }
                            sendBroadcast(ints);
                        }

                        @Override
                        public void onFailure(Call<Account> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                        }
                    });
                    break;
                }
                case "login":{
                    String email=intent.getStringExtra("email_adress");
                    String password=intent.getStringExtra("password");
                    String username=email.split("@")[0];
                    Call<Account> call = ServiceUtils.mailService.getAccount(username,password);
                    call.enqueue(new Callback<Account>() {
                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            if (response.code() == 200) {
                                Log.d("REZ", "Get account: ");
                                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AccountService.this);
                                Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                Account account = response.body();
                                Log.d("-----","-----");
                                Log.d("Email1: ",account.getUsername());
                                Log.d("Password: ",account.getPassword());
                                editor.putString(getString(R.string.login),account.getUsername());
                                editor.commit();
                                Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                                Intent i = new Intent(AccountService.this, EmailsActivity.class);
                                startActivity(i);
                            } else {
                                Log.d("REZ", "Get account: " + response.code());
                                Toast.makeText(AccountService.this,"E-mail adresa ili lozinka su pogrešni!\nPokušajte ponovo!", Toast.LENGTH_SHORT).show();

                            }
                            sendBroadcast(ints);
                        }

                        @Override
                        public void onFailure(Call<Account> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                        }
                    });
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
