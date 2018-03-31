package com.example.gmol_android;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmol_android.model.APIResponse;
import com.example.gmol_android.model.Customer;
import com.example.gmol_android.model.SPreference;
import com.example.gmol_android.service.APIClient;
import com.example.gmol_android.service.BaseRestService;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import retrofit2.Callback;
import retrofit2.Response;

public class PasangActivity extends AppCompatActivity {

    Button buttonLanjut, buttonSimpan, buttonCari;
    private LinearLayout layone, laytwo;
    private SPreference sPreference;
//    EditText ID, nama, lokasi, penyebab, merk, noMeter, tipeMeter, noSegel, inisial, standMeter, petugas, perbaikanSementara;
    EditText ID;
    TextView nama_pelanggan, lokasi, tarif, gardu ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasang);

        ID = findViewById(R.id.editTextID);
        nama_pelanggan = findViewById(R.id.viewNama);
        lokasi = findViewById(R.id.viewLokasi);
        tarif = findViewById(R.id.viewTarif);
        gardu = findViewById(R.id.viewGardu);
        sPreference = new SPreference(this);

        sPreference.getSP_GmolToken();
        /*ID = findViewById(R.id.editTextID);
        nama = findViewById(R.id.editTextNama);
        lokasi = findViewById(R.id.editTextLokasi);
        penyebab = findViewById(R.id.editTextPenyebabGangguan);
        merk = findViewById(R.id.editTextMerkMeter);
        noMeter = findViewById(R.id.editTextNoMeter);
        tipeMeter = findViewById(R.id.editTipeMeter);
        noSegel = findViewById(R.id.editTextNoSegel);
        inisial = findViewById(R.id.editTextInisial);
        standMeter = findViewById(R.id.editTextStandMeter);
        petugas = findViewById(R.id.editTextPetugas);
        perbaikanSementara = findViewById(R.id.editTextPerbaikan);*/

        layone = findViewById(R.id.layPasOne);
        laytwo = findViewById(R.id.layPasTwo);
        buttonLanjut = findViewById(R.id.button_lanjut1);
        buttonSimpan = findViewById(R.id.button_simpan1);
        buttonCari = findViewById(R.id.button_cari1);

        buttonLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layone.setVisibility(View.GONE);
                laytwo.setVisibility(View.VISIBLE);

            }


        });

        buttonCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cariId();


            }

            private void cariId(){
                APIClient apiClient = new BaseRestService().initializeRetrofit().create(APIClient.class);

                retrofit2.Call<APIResponse> result = apiClient.customerApi(sPreference.getSP_GmolToken(), ID.getText().toString());
                result.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<APIResponse> call, Response<APIResponse> response) {
                        if(response.code()==200){

                            Gson gson = new Gson();
                            Customer customer = gson.fromJson(gson.toJson(response.body().getData()), Customer.class);

                            nama_pelanggan.setText(customer.getNama_pelanggan());
                            lokasi.setText(customer.getLokasi());
                            tarif.setText(customer.getTarif());
                            gardu.setText(customer.getLokasi());

                            buttonCari.setVisibility(View.GONE);
                            buttonLanjut.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(PasangActivity.this, "ID tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(retrofit2.Call<APIResponse> call, Throwable t) {
                        Toast.makeText(PasangActivity.this, "ID tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layone.setVisibility(View.GONE);
                laytwo.setVisibility(View.VISIBLE);

            }


        });

    }


    @Override
    public void onBackPressed()
    {
        if (layone.getVisibility() == View.VISIBLE)
        {
            super.onBackPressed();
        } else {
            layone.setVisibility(View.VISIBLE);
            laytwo.setVisibility(View.GONE);
        }
        //super.onBackPressed();


    }



}
