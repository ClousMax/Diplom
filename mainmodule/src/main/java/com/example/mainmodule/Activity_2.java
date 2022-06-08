package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Activity_2 extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_GALLERY_PHOTO = 2;
    private ImageView imageView;
    //Список
    ArrayList<photoData> photoDat = new ArrayList<photoData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //получение значения из arrayList из mainactivity

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            photoDat = (ArrayList<photoData>) bundle.getSerializable("photoData");
        }

        imageView = findViewById(R.id.imageView);
        //Picasso.get().load(R.drawable.eagle).into(imageView);

        getSupportActionBar().setTitle("Второстепенный экран"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Использование адаптера для RecyclerView


//Кнопка сделать изображение
        Button photobtn = (Button) findViewById(R.id.photobtn);
        photobtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });
        Button gallerybtn = findViewById(R.id.gallerybtn);
        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_GALLERY_PHOTO);

            }
        });
//Кнопка продолжить
        Button continueBtn = findViewById(R.id.continueBTN);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText name = (TextInputEditText) findViewById(R.id.TextInputName);
                TextInputEditText comment = (TextInputEditText) findViewById(R.id.TextInputComments);
                ImageView img = (ImageView) findViewById(R.id.imageView);
                photoData photo = new photoData(name.getText().toString(),comment.getText().toString(),14,R.drawable.eagle);
                photoDat.add(photo);
                //посылка значения в mainactivity
                Intent intent = new Intent(Activity_2.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", photoDat);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем миниатюру картинки
            Bundle extras = data.getExtras();
            Bitmap thumbnailBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(thumbnailBitmap);
        } // Обработка второй кнопки
        else if(requestCode == REQUEST_GALLERY_PHOTO && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);

        }
    }
    //обратока нажатия на кнопку назад в actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveAsBitmap(View view, String filename) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        try {
            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // ставить 85 бесполезно, PNG - это формат сжатия без потерь
            out.close();
        } catch (Exception ignored) {
        }
        bitmap.recycle();
    }
}


