package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Activity_2 extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_GALLERY_PHOTO = 2;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        imageView = findViewById(R.id.imageView);
        getSupportActionBar().setTitle("Второстепенный экран"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//Кнопка сделать изображение
        Button photobtn = (Button) findViewById(R.id.photobtn);
        photobtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try{
                    startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
                }catch (ActivityNotFoundException e){
                    e.printStackTrace(); }
            }

        });

        Button gallerybtn = findViewById(R.id.gallerybtn);
        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent , REQUEST_GALLERY_PHOTO);

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
}


