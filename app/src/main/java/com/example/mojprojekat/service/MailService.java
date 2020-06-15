package com.example.mojprojekat.service;

import com.example.mojprojekat.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

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
}
