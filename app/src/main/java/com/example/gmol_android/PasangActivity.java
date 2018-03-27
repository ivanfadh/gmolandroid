package com.example.gmol_android;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PasangActivity extends AppCompatActivity {

    Button buttonLanjut;
    private LinearLayout layone, laytwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasang);

        layone = findViewById(R.id.layPasOne);
        laytwo = findViewById(R.id.layPasTwo);
        buttonLanjut = findViewById(R.id.button_lanjut1);

        buttonLanjut.setOnClickListener(new View.OnClickListener() {
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
