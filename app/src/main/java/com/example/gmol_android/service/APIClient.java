package com.example.gmol_android.service;

import com.example.gmol_android.model.APIResponse;

import java.util.function.BinaryOperator;
import java.util.function.LongBinaryOperator;

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
    @POST("api/customer/get")
    Call<APIResponse> customerApi(@Field("gmol_token") String gmol_token, @Field("id_pelanggan") String id_pelanggan);

    @FormUrlEncoded
    @POST("api/gangguan/add")
    Call<APIResponse> addData(
            @Field("gmol_token") String gmol_token,
            @Field("id_pelanggan") String id_pelanggan,
            @Field("no_meter") String no_meter,
            @Field("merk_meter") String merk_meter,
            @Field("tipe_meter") String tipe_meter,
            @Field("no_segel") String no_segel,
            @Field("inisial") String inisial,
            @Field("stand_meter") String stand_meter,
            @Field("id_petugas") String id_petugas,
            @Field("penyebab_gangguan") String penyebab_gangguan,
            @Field("perbaikan_sementara") String perbaikan_sementara);

    @FormUrlEncoded
    @POST("api/gangguan/fix")
    Call<APIResponse> fixData(
            //@Field("image") BinaryOperator image,
            @Field("gmol_token") String gmol_token,
            @Field("id_pelanggan") String id_pelanggan,
            @Field("no_meter") String no_meter,
            @Field("merk_meter") String merk_meter,
            @Field("tipe_meter") String tipe_meter,
            @Field("no_segel") String no_segel,
            @Field("inisial") String inisial,
            @Field("stand_meter") String stand_meter,
            @Field("id_petugas") String id_petugas,
            @Field("penyebab_gangguan") String penyebab_gangguan,
            @Field("perbaikan_sementara") String perbaikan_sementara);
}
