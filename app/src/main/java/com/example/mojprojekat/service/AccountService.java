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
import com.example.mojprojekat.aktivnosti.ProfileActivity;
import com.example.mojprojekat.model.Account;
import com.example.mojprojekat.tools.Data;
import com.example.mojprojekat.tools.ReviewerTools;

import java.util.List;

import okhttp3.ResponseBody;
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
                case "getAccountByUsername":{
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AccountService.this);
                    String email=Data.userAccount("email",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                    String username="";
                    if(!email.equals("")){
                        username=email.split("@")[0];
                    }
                    System.out.println("Ussername je: "+username);
                    Call<Account> call = ServiceUtils.mailService.getAccountByUsername(username);
                    call.enqueue(new Callback<Account>() {
                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            if (response.code() == 200) {
                                Log.d("REZ", "Get account: "+response.code());
                                Account account = response.body();
                                Data.account=account;
                            } else {
                                Data.accounts.clear();
                                Log.d("REZ", "Get account: " + response.code());
                                Toast.makeText(AccountService.this,"Za sada nemate ni jedan kreirani e-mail nalog!", Toast.LENGTH_SHORT).show();

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
                case "delete":{
                    long id=Long.parseLong(intent.getStringExtra("id"));
                    Call<ResponseBody> call = ServiceUtils.mailService.deleteAccount(id);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 204) {
                                Log.d("REZ", "Get account: "+response.code());
                                Toast.makeText(AccountService.this, "Uspešno ste uklonili nalog!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("REZ", "Get account: " + response.code());

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
                    account.setUser(Data.user);
                    System.out.println("First: "+account.getUser().getFirst());
                    if(domen.equals("yahoo.mail")){
                        account.setInServerAddress("mail.yahoo.mail");
                    }else{
                        account.setInServerAddress(domen);
                    }
                    Call<Account> call = ServiceUtils.mailService.add(account,Data.user.getUsername());
                    call.enqueue(new Callback<Account>() {
                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            if (response.code() == 201) {
                                Account account1=response.body();
                                Data.accounts.add(account1);
                                Log.d("REZ", "Account created");
                                Toast.makeText(AccountService.this,"Uspešno ste kreirali e-mail nalog!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AccountService.this, ProfileActivity.class);
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
                    String username=intent.getStringExtra("username");
                    Call<List<Account>> call = ServiceUtils.mailService.getAccountsByUsernameUser(username);
                    call.enqueue(new Callback<List<Account>>() {
                        @Override
                        public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                            if (response.code() == 200) {
                                Log.d("REZ", "Get account: ");
                                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AccountService.this);
                                Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                                String email=Data.userAccount("email",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                                System.out.println("\n\nEmial kada ne postoji: "+email);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                List<Account> accounts=response.body();
                                for (Account a:accounts
                                     ) {
                                    Data.accounts.add(a);
                                }
                                if(email.equals("")){
                                    System.out.println("Trazim novi account");
                                    Account account = response.body().get(0);
                                    if(account!=null){
                                        Data.account=account;
                                        Log.d("-----","-----");
                                        Log.d("Email1: ",account.getUsername());
                                        Log.d("Password: ",account.getPassword());
                                        String username=Data.userAccount("username",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                                        editor.putString(getString(R.string.login),username+"|"+account.getUsername()+"@"+account.getSmtpAddress());
                                        editor.commit();
                                        Log.d("Ulogovani: ",sharedPreferences.getString(getString(R.string.login),"Nema ulogovanog"));
                                    }
                                }
                            } else {
                                Data.accounts.clear();
                                Log.d("REZ", "Get account u accountService: " + response.code());
                                Toast.makeText(AccountService.this,"Za sada nemate ni jedan kreirani e-mail nalog!", Toast.LENGTH_SHORT).show();

                            }
                            sendBroadcast(ints);
                        }

                        @Override
                        public void onFailure(Call<List<Account>> call, Throwable t) {
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
