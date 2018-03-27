package com.example.gmol_android.model;

/**
 * Created by User on 3/25/2018.
 */

public class User {
    private int id, id_role;
    private String name, email, gmol_token, id_petugas;

    public User(int id, String id_petugas, int id_role, String name, String email, String gmol_token) {
        this.id = id;
        this.id_petugas = id_petugas;
        this.id_role = id_role;
        this.name = name;
        this.email = email;
        this.gmol_token = gmol_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_petugas() {
        return id_petugas;
    }

    public void setId_petugas(String id_petugas) {
        this.id_petugas = id_petugas;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGmol_token() {
        return gmol_token;
    }

    public void setGmol_token(String gmol_token) {
        this.gmol_token = gmol_token;
    }
}
