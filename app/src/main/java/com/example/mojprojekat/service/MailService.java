package com.example.mojprojekat.service;

import com.example.mojprojekat.model.Message;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * Klasa koja opisuje koji tj mapira putanju servisa
 * opisuje koji metod koristimo ali i sta ocekujemo kao rezultat
 * */

public interface MailService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET(ServiceUtils.MESSAGES)
    Call<List<Message>> getMessages();

    @DELETE(ServiceUtils.DELETE)
    Call<ResponseBody> delete(@Path("id") Long id);

    @POST(ServiceUtils.MESSAGES)
    Call<ResponseBody> send(@Body Message message);
}
