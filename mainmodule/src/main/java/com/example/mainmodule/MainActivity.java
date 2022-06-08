package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//Создание списка
    ArrayList<photoData> photoDt = new ArrayList<photoData>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Главный экран"); // for set actionbar title

        // Создание списка

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_2.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("photoData", photoDt);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            photoDt = (ArrayList<photoData>) bundle.getSerializable("value");
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerOnMain);
        photoAdapter adapter = new photoAdapter(this, photoDt);
        recyclerView.setAdapter(adapter);
    }
}