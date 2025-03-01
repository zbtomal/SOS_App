package com.example.sosapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button hospitalButton = findViewById(R.id.btn_hospital);
        hospitalButton.setOnClickListener(v -> {
            HospitalBottomSheet bottomSheet = new HospitalBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });

        Button policeButton = findViewById(R.id.btn_police);
        policeButton.setOnClickListener(v -> {
            PoliceBottomSheet bottomSheet = new PoliceBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });

        Button fireServiceButton = findViewById(R.id.btn_fireservice);
        fireServiceButton.setOnClickListener(v -> {
            FireBottomSheet bottomSheet = new FireBottomSheet();
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SaveContactsActivity.class);
            startActivity(intent);
        });
    }
}
