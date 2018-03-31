package com.example.gmol_android;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gmol_android.model.APIResponse;
import com.example.gmol_android.model.SPreference;
import com.example.gmol_android.model.User;
import com.example.gmol_android.service.APIClient;
import com.example.gmol_android.service.BaseRestService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button button_login;
    EditText username, password;
    private SPreference sPreference;
    LinearLayout layout;
    View viewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPreference = new SPreference(this);

        if (sPreference.getCek_Login()){
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }

        button_login = findViewById(R.id.button_login);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        layout = findViewById(R.id.layout);
        viewLoading = findViewById(R.id.loading);


        String strUsername = username.getText().toString();
        String strPass = password.getText().toString();

        /*if(TextUtils.isEmpty(strUsername)) {
            username.setError("Gabole kosong");
            return;
        }

        if(TextUtils.isEmpty(strPass)) {
            password.setError("Gabole kosong");
            return;
        }*/

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //halaman input data kesehatan
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.trim().length() > 0 && pass.trim().length() > 0) {
                   cekLogin();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cekLogin(){
        APIClient apiClient = new BaseRestService().initializeRetrofit().create(APIClient.class);

        layout.setVisibility(View.GONE);
        viewLoading.setVisibility(View.VISIBLE);
        /*Log.d(" ", "cekLogin: abyan");
        Log.d("as", "cekLogin: " +username.getText().toString() );
        Log.d("as", "cekLogin: " +password.getText().toString() );*/
        Call<APIResponse> result = apiClient.loginApi(username.getText().toString(), password.getText().toString());
        result.enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                //Log.d("lalala", "onResponse: " + response.code() + response.message());
                if (response.code()==200){

                    Gson gson = new Gson();
                    User user = gson.fromJson(gson.toJson(response.body().getData()), User.class);

                    sPreference.saveSPString(SPreference.SP_Name, user.getName());
                    sPreference.saveSPString(SPreference.SP_IDPetugas, user.getId_petugas());
                    sPreference.saveSPString(SPreference.SP_GmolToken, user.getGmol_token());

                    sPreference.saveSPBoolean(SPreference.Cek_Login, true);

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();


                } else if (response.code()==201){
                    viewLoading.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Password yang anda masukkan salah.", Toast.LENGTH_SHORT).show();
                } else if(response.code()==202) {
                    viewLoading.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Akun anda belum terdaftar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                viewLoading.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Jaringan anda bermasalah.", Toast.LENGTH_SHORT).show();
                Log.d("Test", "onFailure: " + t.getMessage());
            }
        });
    }


}
