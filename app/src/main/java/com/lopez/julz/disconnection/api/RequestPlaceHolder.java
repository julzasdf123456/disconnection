package com.lopez.julz.disconnection.api;

import com.lopez.julz.disconnection.dao.DisconnectionList;
import com.lopez.julz.disconnection.objects.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestPlaceHolder {
    @POST("login")
    Call<Login> login(@Body Login login);

    /**
     * DISCONNECTION
     */
    @GET("get-disconnection-list")
    Call<List<DisconnectionList>> getDisconnectionList();

    @POST("receive-disconnection-uploads")
    Call<Void> uploadDisconnection(@Body DisconnectionList disconnectionList);
}
