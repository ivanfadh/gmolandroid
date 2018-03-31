package com.example.gmol_android.model;

/**
 * Created by User on 3/30/2018.
 */

public class Customer {
    private int id, id_rayon;
    private String id_pelanggan,
            nama_pelanggan,
            lokasi,
            tarifdaya,
            gardu,
            latitude,
            longtitude,
            created_at,
            updated_at;

    public Customer(int id, int id_rayon, String id_pelanggan, String nama_pelanggan, String lokasi, String tarifdaya, String gardu, String latitude, String longtitude, String created_at, String updated_at) {
        this.id = id;
        this.id_rayon = id_rayon;
        this.id_pelanggan = id_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.lokasi = lokasi;
        this.tarifdaya = tarifdaya;
        this.gardu = gardu;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_rayon() {
        return id_rayon;
    }

    public void setId_rayon(int id_rayon) {
        this.id_rayon = id_rayon;
    }

    public String getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(String id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTarifdaya() {
        return tarifdaya;
    }

    public void setTarifdaya(String tarifdaya) {
        this.tarifdaya = tarifdaya;
    }

    public String getGardu() {
        return gardu;
    }

    public void setGardu(String gardu) {
        this.gardu = gardu;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

