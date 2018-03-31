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

    @FormUrlEncoded
    @POST("api/costumer/get")
    Call<APIResponse> customerApi(@Field("gmol_token") String gmol_token, @Field("id_pelanggan") String id_pelanggan);

    @FormUrlEncoded
    @POST("api/data_gangguan/add")
    Call<APIResponse> pasangApi(
            @Field("gmol_token") String gmol_token,
            @Field("id_pelanggan") String id_pelanggan,
            @Field("nama_pelanggan") String nama_pelanggan,
            @Field("lokasi") String lokasi,
            @Field("penyebab_gangguan") String penyebab_gangguan,
            @Field("merk_meter") String merk_meter,
            @Field("no_meter") String id_meter,
            @Field("tipe_meter") String tipe_meter,
            @Field("no_segel") String no_segel,
            @Field("inisial") String inisial,
            @Field("stand_meter") String stand_meter,
            @Field("petugas") String petugas,
            @Field("perbaikan_sementara") String perbaikan_sementara

    );
}
