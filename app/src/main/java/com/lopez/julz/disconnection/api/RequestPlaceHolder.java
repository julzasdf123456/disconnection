package com.lopez.julz.disconnection.api;

import com.lopez.julz.disconnection.objects.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestPlaceHolder {
    @POST("login")
    Call<Login> login(@Body Login login);
}
