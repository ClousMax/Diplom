package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.opencv.core.Mat;
import java.io.FileOutputStream;


public class Activity_2 extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_GALLERY_PHOTO = 2;
    private ImageView imageView;

    @SuppressLint("StaticFieldLeak")
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

        // Кнопка обработки
        FloatingActionButton editBTN = (FloatingActionButton) findViewById(R.id.editBTN);
        editBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ImageView imag = (ImageView) findViewById(R.id.imageView);
                if (imag.getDrawable() != null){
                    Bitmap bitmap = ((BitmapDrawable)imag.getDrawable()).getBitmap();
                    ImageProcessor imgProcessor = new ImageProcessor(Activity_2.this);
                    Mat img = imgProcessor.bitmapToMat(bitmap);
                    String base64Image =  imgProcessor.cv2ImageTobase64(img, ".jpg");
                    Gson gson = new Gson();
                    HttpRequest req = new HttpRequest(Activity_2.this);
                    req.executeMethod(base64Image, ".jpg",
                            new HttpAsyncTask() {
                                @Override
                                protected void onPostExecute(String result) {
                                    super.onPostExecute(result);
                                    if (result != null) {
                                        Base64Image image = gson.fromJson(result, Base64Image.class);

                                        Mat mat = imgProcessor.base64ToCV2Image(image.image);

                                        Bitmap bmp = imgProcessor.matToBitmap(mat);
                                        ImageView imageView = findViewById(R.id.imageView);
                                        imageView.setImageBitmap(bmp);
                                    }
                                }
                            });}
            }
        });
//Кнопка продолжить и переслать данные
        Button continueBtn = findViewById(R.id.continueBTN);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText name = (TextInputEditText) findViewById(R.id.TextInputName);
                TextInputEditText comment = (TextInputEditText) findViewById(R.id.TextInputComments);
                ImageView imag = (ImageView) findViewById(R.id.imageView);
                if (imag.getDrawable() != null || !TextUtils.isEmpty(name.getText().toString())){
                    //Создаём ViewModel и добавляем в него имя и комментарий
                    FotoViewModel mFotoViewModel = new ViewModelProvider(Activity_2.this).get(FotoViewModel.class);
                    mFotoViewModel.insert(new FotoData(name.getText().toString(), comment.getText().toString()));

                    Intent intent = new Intent(Activity_2.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Фото сделана, извлекаем миниатюру картинки
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


