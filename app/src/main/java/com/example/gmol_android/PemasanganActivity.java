package com.example.gmol_android;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.gmol_android.model.APIResponse;
import com.example.gmol_android.model.Customer;
import com.example.gmol_android.model.SPreference;
import com.example.gmol_android.service.APIClient;
import com.example.gmol_android.service.BaseRestService;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;
import retrofit2.Response;

public class PemasanganActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private SPreference sPreference;

    private ScrollView viewData;
    private RelativeLayout layoutCustomer, layoutData;
    private EditText customerID;
    private TextView customerName, customerLocation, customerDaya, customerGardu;
    private EditText kwhNo, kwhMerk, kwhType, kwhSegel, kwhInisial, kwhStand, dataPenyebab, dataPerbaikanSementara;
    private Button searchCustomer, nextForm, sendForm;
    private ImageView image;

    private Customer customer;
    private ProgressDialog progressDialog;

    //camera;
    private ImageView resultImage;
    private boolean cameraPermission;
    private File result;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.example.gmol_android.fileprovider";
    private String mCurrentPhotoPath;

    //lokasi
    //private Button getLocation;
    private TextView latitudeText, longitudeText, addressText;
    private LocationManager locationManager;
    private Location mLastKnownLocation;
    private int REQUEST_CHECK_SETTINGS = 21;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelepasan);
        sPreference = new SPreference(this);
        requestPermission();
        cameraPermission = false;
        resultImage = findViewById(R.id.result_image);

        getCameraPermission();

        setID();

        setListener();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            cameraPermission = true;
            getPhoto();


        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE);

        }
    }

    public void getPhoto()
    {
        File path = new File(PemasanganActivity.this.getFilesDir(), "hasilupload");
        if (!path.exists()) path.mkdirs();
        result = new File(path, Calendar.getInstance().getTimeInMillis() + ".jpg");
        Uri imageUri = FileProvider.getUriForFile(PemasanganActivity.this, CAPTURE_IMAGE_FILE_PROVIDER, result);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

    }

    public void requestPermission() {
        //current location handler
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 20
            );
        } else {
            displayLocationSettingsRequest();
        }
    }

    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            displayLocationSettingsRequest();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE}, 20);

        }
    }

    private void displayLocationSettingsRequest() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        com.google.android.gms.common.api.PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("requestlocation", "All location settings are satisfied.");
                        getDeviceLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("requestlocation", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(PemasanganActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("requestlocation", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("requestlocation", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    private void getDeviceLocation() {

        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                        if (mLastKnownLocation!=null)

                            // addressText.setText(getAddress(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()));
                            latitudeText.setText("Latitude : " + mLastKnownLocation.getLatitude());
                        longitudeText.setText("Longitude : " + mLastKnownLocation.getLongitude());
//
//                            mapku.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(mLastKnownLocation.getLatitude(),
//                                            mLastKnownLocation.getLongitude()), 12));

                    } else {
                        Log.d("getdevicelocation", "Current location is null. Using defaults.");
                        Log.e("getdevicelocation", "Exception: %s", task.getException());

                    }
                }
            });
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                addPhoto();
            }
            else
            {
                Toast.makeText(this, "Aktifitas dibatalkan", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        requestPermission();


    }


    public String getAddress(double lat, double lng) {
        String fullAdd = null;
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            fullAdd = ex.getMessage();
        }
        return fullAdd;

    }

    private void addPhoto() {
        Glide.with(this).load(result).into(resultImage);
    }

    private void setListener() {

        searchCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerID.getText().toString().isEmpty())
                    Toast.makeText(PemasanganActivity.this, "Mohon isi ID pelanggan terlebih dahulu", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 20: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    displayLocationSettingsRequest();


                }
            }

            case CAMERA_REQUEST_CODE:
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getPhoto();
                    cameraPermission = true;

                }
                else finish();
                break;
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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
        retrofit2.Call<APIResponse> result = apiClient.fixData(sPreference.getSP_GmolToken(), customer.getId_pelanggan(),
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
                    Toast.makeText(PemasanganActivity.this, "Berhasil menambahkan data gangguan", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(response.code() == 201)
                {
                    Toast.makeText(PemasanganActivity.this, "Akun anda tersambung di perangkat lain", Toast.LENGTH_SHORT).show();
                    sPreference.logout();
                }
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(retrofit2.Call<APIResponse> call, Throwable t) {
                Toast.makeText(PemasanganActivity.this, "Terjadi kesalahan terhadap jaringan anda", Toast.LENGTH_SHORT).show();
                Log.d("logbyan", "onFailure: " + t.getMessage());
                progressDialog.dismiss();


            }
        });

    }

    private void setID() {
        viewData = findViewById(R.id.scrollPemasangan);

        layoutCustomer = findViewById(R.id.customer_layout);
        layoutData = findViewById(R.id.data_layout_pasang);

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
        latitudeText = findViewById(R.id.latitude);
        longitudeText = findViewById(R.id.longitude);

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
                    Toast.makeText(PemasanganActivity.this, "Pelanggan tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(retrofit2.Call<APIResponse> call, Throwable t) {
                Log.d("logbyan", "onResponse: " + t.getMessage());
                Toast.makeText(PemasanganActivity.this, "Jaringan anda bermasalah", Toast.LENGTH_SHORT).show();
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
