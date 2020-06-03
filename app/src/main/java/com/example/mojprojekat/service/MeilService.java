package com.example.mojprojekat.service;

import com.example.mojprojekat.model.TagToSend;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/*
 * Klasa koja opisuje koji tj mapira putanju servisa
 * opisuje koji metod koristimo ali i sta ocekujemo kao rezultat
 * */

public interface MeilService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST(ServiceUtils.ADD)
    Call<ResponseBody> add(@Body TagToSend tag);
}
