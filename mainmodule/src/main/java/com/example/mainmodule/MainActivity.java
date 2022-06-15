package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//Создание списка
    FotoViewModel mFotoViewModel;
    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Главный экран"); // for set actionbar title

        // Создание RecyclerView
         recyclerView = findViewById(R.id.recyclerOnMain);
        final FotoListAdapter foto_adapter = new FotoListAdapter(new FotoListAdapter.WordDiff());
        recyclerView.setAdapter(foto_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFotoViewModel = new ViewModelProvider(this).get(FotoViewModel.class);

        mFotoViewModel.getAllFoto().observe(this, foto -> {
            // Обновление сохранённой копии в адаптере
            foto_adapter.submitList(foto);

        });


        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Activity_2.class);
                startActivity(intent);

            }
        });
        }
}