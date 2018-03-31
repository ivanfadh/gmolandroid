package com.example.gmol_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.gmol_android.model.SPreference;

public class MenuActivity extends AppCompatActivity {

    Button button_entry;
    Button button_pasang;
    private SPreference sPreference;
    ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sPreference = new SPreference(this);

        button_entry = findViewById(R.id.button_entry);
        button_pasang = findViewById(R.id.button_pasang);
        logout = findViewById(R.id.logout_button);

        button_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //halaman input data kesehatan
                Intent intent = new Intent(MenuActivity.this, EntryActivity.class);
                startActivity(intent);
            }
        });

        button_pasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //halaman input data kesehatan
                Intent intent = new Intent(MenuActivity.this, PelepasanActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //halaman input data kesehatan
                sPreference.logout();
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
