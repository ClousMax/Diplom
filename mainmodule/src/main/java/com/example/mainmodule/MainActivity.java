package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

//Создание списка
    RecyclerView recyclerView;
//    ArrayList<photoData> photoDt = new ArrayList<photoData>();
    private FotoViewModel mFotoViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

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

    Bundle bundle = new Bundle();
//                bundle.putSerializable("photoData", photoDt);
                intent.putExtras(bundle);
                startActivity(intent);
                    
            }
        });

        }
}
