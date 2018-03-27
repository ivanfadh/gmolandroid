package com.example.gmol_android.service;

import com.example.gmol_android.model.APIResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by User on 3/25/2018.
 */

public interface APIClient {

    @FormUrlEncoded
    @POST("api/login")
    Call<APIResponse> loginApi (@Field("email") String email, @Field("password") String password);

}
