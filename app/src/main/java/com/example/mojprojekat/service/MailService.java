package com.example.mojprojekat.service;

import com.example.mojprojekat.model.Account;
import com.example.mojprojekat.model.User;
import com.example.mojprojekat.modelDTO.MessageDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<MessageDTO>> getMessages(@Path("username") String username);

    @GET(ServiceUtils.USER)
    Call<User> getUser(@Path("username") String username, @Path("password") String password);

    @PUT(ServiceUtils.UPDATEUSER)
    Call<User> getUserByUsername(@Path("username") String username);

    @GET(ServiceUtils.ACCOUNT)
    Call<Account> getAccount(@Path("username") String username,@Path("password") String password);

    @PUT(ServiceUtils.UPDATEACCOUNT)
    Call<Account> getAccountByUsername(@Path("username") String username);

    @POST(ServiceUtils.USERS)
    Call<User> addUser(@Body User user);

    @POST(ServiceUtils.ACCOUNTS)
    Call<Account> add(@Body Account account);

    @DELETE(ServiceUtils.DELETE)
    Call<ResponseBody> delete(@Path("id") Long id);

    @PUT(ServiceUtils.UPDATE)
    Call<MessageDTO> update(@Body MessageDTO messageDTO,@Path("id") Long id);

    @POST(ServiceUtils.SEND)
    Call<MessageDTO> send(@Body MessageDTO message);
}
