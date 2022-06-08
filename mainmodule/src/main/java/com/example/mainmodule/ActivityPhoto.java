package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        getSupportActionBar().setTitle("Окно настройки");
    }
}