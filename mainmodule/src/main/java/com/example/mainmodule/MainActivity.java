package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//Создание списка
//    ArrayList<photoData> photoDt = new ArrayList<photoData>();
    private FotoViewModel mFotoViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Главный экран"); // for set actionbar title

        // Создание RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerOnMain);
        final FotoListAdapter foto_adapter = new FotoListAdapter(new FotoListAdapter.WordDiff());
        recyclerView.setAdapter(foto_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Задачи ViewModel
        mFotoViewModel = new ViewModelProvider(this).get(FotoViewModel.class);
        //Observer для LiveData
        mFotoViewModel.getAllFoto().observe(this, foto -> {
            // Update the cached copy of the words in the adapter.
            foto_adapter.submitList(foto);
        });
//кнопк
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            FotoData foto = new FotoData(data.getStringExtra(Activity_2.EXTRA_REPLY1),data.getStringExtra(Activity_2.EXTRA_REPLY2));
            mFotoViewModel.insert(foto);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
//        if(bundle!=null){
//            photoDt = (ArrayList<photoData>) bundle.getSerializable("value");
//        }
//        RecyclerView recyclerView = findViewById(R.id.recyclerOnMain);
//        photoAdapter adapter = new photoAdapter(this, photoDt);
//        recyclerView.setAdapter(adapter);
    }
}