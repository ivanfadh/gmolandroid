package com.example.gmol_android;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmol_android.model.APIResponse;
import com.example.gmol_android.model.Customer;
import com.example.gmol_android.model.SPreference;
import com.example.gmol_android.service.APIClient;
import com.example.gmol_android.service.BaseRestService;
import com.google.gson.Gson;

import retrofit2.Callback;
import retrofit2.Response;

public class PelepasanActivity extends AppCompatActivity {

    private SPreference sPreference;

    private ScrollView viewData;
    private RelativeLayout layoutCustomer, layoutData;
    private EditText customerID;
    private TextView customerName, customerLocation, customerDaya, customerGardu;
    private EditText kwhNo, kwhMerk, kwhType, kwhSegel, kwhInisial, kwhStand, dataPenyebab, dataPerbaikanSementara;
    private Button searchCustomer, nextForm, sendForm;

    private Customer customer;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasang);
        sPreference = new SPreference(this);

        setID();

        setListener();



    }

    private void setListener() {

        searchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerID.getText().toString().isEmpty())
                    Toast.makeText(PelepasanActivity.this, "Mohon isi ID pelanggan terlebih dahulu", Toast.LENGTH_SHORT).show();
                else cariId();
            }
        });

        nextForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewData.setVisibility(View.VISIBLE);
                layoutCustomer.setVisibility(View.GONE);
                layoutData.setVisibility(View.VISIBLE);
            }
        });
        sendForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

    }

    private void sendData() {

        if(kwhStand.getText().toString().isEmpty() || kwhInisial.getText().toString().isEmpty() || kwhSegel.getText().toString().isEmpty()
                || kwhType.getText().toString().isEmpty() || kwhMerk.getText().toString().isEmpty() || kwhNo.getText().toString().isEmpty() ||
                dataPenyebab.getText().toString().isEmpty() || dataPenyebab.getText().toString().isEmpty())
        {
            Toast.makeText(this, "harap isi semua form", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();


        APIClient apiClient = new BaseRestService().initializeRetrofit().create(APIClient.class);
        Log.d("logbyan", "cariId: " + sPreference.getSP_GmolToken());
        retrofit2.Call<APIResponse> result = apiClient.addData(sPreference.getSP_GmolToken(), customer.getId_pelanggan(),
                kwhNo.getText().toString(), kwhMerk.getText().toString(), kwhType.getText().toString(), kwhSegel.getText().toString(),
                kwhInisial.getText().toString(), kwhStand.getText().toString(), sPreference.getSP_IDPetugas(), dataPenyebab.getText().toString(),
                dataPerbaikanSementara.getText().toString());

        result.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(retrofit2.Call<APIResponse> call, Response<APIResponse> response) {
                Log.d("logbyan", "onResponse: " + response.message());
                Log.d("logbyan", "onResponse: " + response.code());
                if(response.code() == 200)
                {
                    Toast.makeText(PelepasanActivity.this, "Berhasil menambahkan data gangguan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(response.code() == 201)
                {
                    Toast.makeText(PelepasanActivity.this, "Akun anda tersambung di perangkat lain", Toast.LENGTH_SHORT).show();
                    sPreference.logout();
                }
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(retrofit2.Call<APIResponse> call, Throwable t) {
                Toast.makeText(PelepasanActivity.this, "Terjadi kesalahan terhadap jaringan anda", Toast.LENGTH_SHORT).show();
                Log.d("logbyan", "onFailure: " + t.getMessage());
                progressDialog.dismiss();


            }
        });

    }

    private void setID() {
        viewData = findViewById(R.id.scrollPelepasan);

        layoutCustomer = findViewById(R.id.customer_layout);
        layoutData = findViewById(R.id.data_layout);

        customerID = findViewById(R.id.customer_id);
        customerName = findViewById(R.id.customer_name);
        customerLocation = findViewById(R.id.customer_location);
        customerDaya = findViewById(R.id.customer_power_source);
        customerGardu = findViewById(R.id.customer_gardu);

        kwhNo = findViewById(R.id.kwh_meter);
        kwhMerk = findViewById(R.id.kwh_merk);
        kwhType = findViewById(R.id.kwh_type);
        kwhSegel = findViewById(R.id.kwh_no_segel);
        kwhInisial = findViewById(R.id.kwh_inisial);
        kwhStand = findViewById(R.id.kwh_stand_meter);

        dataPenyebab = findViewById(R.id.data_penyebab);
        dataPerbaikanSementara = findViewById(R.id.data_perbaikan_sementara);

        searchCustomer = findViewById(R.id.search_customer);
        nextForm = findViewById(R.id.next_input);
        sendForm = findViewById(R.id.send_data);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Harap menunggu");
        progressDialog.setCancelable(false);

    }

    private void cariId(){
        progressDialog.show();
        APIClient apiClient = new BaseRestService().initializeRetrofit().create(APIClient.class);
        Log.d("logbyan", "cariId: " + sPreference.getSP_GmolToken());
        retrofit2.Call<APIResponse> result = apiClient.customerApi(sPreference.getSP_GmolToken(), customerID.getText().toString());
        result.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(retrofit2.Call<APIResponse> call, Response<APIResponse> response) {

                Log.d("logbyan", "onResponse: " + response.message());
                Log.d("logbyan", "onResponse: " + response.code());

                if(response.code()==200){

                    Gson gson = new Gson();
                    customer = gson.fromJson(gson.toJson(response.body().getData()), Customer.class);

                    customerName.setText(customer.getNama_pelanggan());
                    customerLocation.setText(customer.getLokasi());
                    customerDaya.setText(customer.getTarifdaya());
                    customerGardu.setText(customer.getGardu());
                    nextForm.setVisibility(View.VISIBLE);
                }
                else if(response.code() == 201)
                {
                    sPreference.logout();
                }
                else if(response.code() == 202)
                {
                    Toast.makeText(PelepasanActivity.this, "Pelanggan tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(retrofit2.Call<APIResponse> call, Throwable t) {
                Log.d("logbyan", "onResponse: " + t.getMessage());
                Toast.makeText(PelepasanActivity.this, "Jaringan anda bermasalah", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }


    @Override
    public void onBackPressed() {

        if(layoutCustomer.getVisibility() == View.VISIBLE)
        {
            super.onBackPressed();
        }
        else
        {
            nextForm.setVisibility(View.GONE);
            layoutData.setVisibility(View.GONE);
            layoutCustomer.setVisibility(View.VISIBLE);
        }
    }
}
