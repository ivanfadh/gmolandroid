package com.example.gmol_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    Button simpan;
    private ImageView resultImage;
    private boolean cameraPermission;
    private File result;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.example.gmol_android.fileprovider";
    private String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        cameraPermission = false;
        resultImage = findViewById(R.id.result_image);
        //simpan = findViewById(R.id.simpan_foto);

        getCameraPermission();

     /*   simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //halaman input data kesehatan
                Intent intent = new Intent(CameraActivity.this, EntryActivity.class);
                startActivity(intent);
            }
        });*/



    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED ) {
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
        File path = new File(CameraActivity.this.getFilesDir(), "hasilupload");
        if (!path.exists()) path.mkdirs();
        result = new File(path, Calendar.getInstance().getTimeInMillis() + ".jpg");
        Uri imageUri = FileProvider.getUriForFile(CameraActivity.this, CAPTURE_IMAGE_FILE_PROVIDER, result);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            result = null;
            try {
                result = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (result != null) {
                Log.d("a", "getPhoto: " + result.getPath());
                Uri photoURI = FileProvider.getUriForFile(this,
                        CAPTURE_IMAGE_FILE_PROVIDER,
                        result);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
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

    private void addPhoto() {

        Glide.with(this).load(result).into(resultImage);


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

}
