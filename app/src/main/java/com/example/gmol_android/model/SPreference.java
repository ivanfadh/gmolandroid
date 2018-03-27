package com.example.gmol_android.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.security.Key;

/**
 * Created by User on 3/25/2018.
 */

public class SPreference {
    public static final String SP_User="spUser";

    public static final String SP_Name="spName";
    public static final String SP_IDPetugas="spIDPetugas";
    public static final String SP_GmolToken="spGmolToken";

    private Context context = null;

    public static final String Cek_Login="login";

   SharedPreferences sp;
   SharedPreferences.Editor spEditor;

   public SPreference(Context context){
       this.context = context;
       sp = context.getSharedPreferences(SP_User, Context.MODE_PRIVATE);
       spEditor = sp.edit();
   }

   public void saveSPString(String KeySP, String value){
       spEditor.putString(KeySP, value);
       spEditor.commit();
   }

    public void saveSPBoolean(String KeySP, Boolean value){
        spEditor.putBoolean(KeySP, value);
        spEditor.commit();
    }

    public String getSP_Name(){
        return sp.getString(SP_Name,"");
    }

    public String getSP_IDPetugas(){
        return sp.getString(SP_IDPetugas,"");
    }

    public String getSP_GmolToken(){
        return sp.getString(SP_GmolToken, "");
    }

    public Boolean getCek_Login(){
        return sp.getBoolean(Cek_Login,false);
    }

    public void logout(){
        spEditor.clear();
        spEditor.commit();
        Toast.makeText(context, "Anda berhasil keluar", Toast.LENGTH_SHORT).show();
    }

}
