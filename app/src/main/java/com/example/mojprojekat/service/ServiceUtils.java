package com.example.mojprojekat.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUtils {

    //EXAMPLE: http://192.168.43.73:8080/rs.ftn.reviewer.rest/rest/proizvodi/
    //ipconfig ako se promeni ip adresa
    public static final String SERVICE_API_PATH = "http://192.168.115.1:8080/api/";
    public static final String MESSAGES = "messages/{username}";
    public static final String SEND ="messages";
    public static final String UPDATE="messages/{id}";
    public static final String DELETE="messages/{id}";
    public static final String DELETE_ACCOUNT="accounts/{id}";
    public static final String ACCOUNTS="accounts/{username}";
    public static final String UPDATEACCOUNT="accounts/{username}";
    public static final String USER="users/{username}/{password}";
    public static final String USERS="users";
    public static final String GETUSER="users/{username}";

    /*
     * Ovo ce nam sluziti za debug, da vidimo da li zahtevi i odgovoru idu
     * odnosno dolaze i kako izgeldaju.
     * */
    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    private static int getId(int id){
        return id;
    }
    /*
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(test())
            .build();

    /*
     * Definisemo konkretnu instancu servisa na intnerntu sa kojim
     * vrsimo komunikaciju
     * */
    public static MailService mailService = retrofit.create(MailService.class);
}
