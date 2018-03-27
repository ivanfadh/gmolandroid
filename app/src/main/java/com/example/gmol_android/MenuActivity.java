package com.example.gmol_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button button_entry;
    Button button_pasang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button_entry = findViewById(R.id.button_entry);
        button_pasang = findViewById(R.id.button_pasang);

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
                Intent intent = new Intent(MenuActivity.this, PasangActivity.class);
                startActivity(intent);
            }
        });
    }
}
