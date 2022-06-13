package com.example.mainmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import com.squareup.picasso.Picasso;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class Activity_2 extends AppCompatActivity {

    public static final String EXTRA_REPLY1 = "com.example.android.wordlistsql.REPLY1";
    public static final String EXTRA_REPLY2 = "com.example.android.wordlistsql.REPLY2";
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_GALLERY_PHOTO = 2;
    private ImageView imageView;
    //Список
    ArrayList<photoData> photoDat = new ArrayList<photoData>();

    @SuppressLint("StaticFieldLeak")
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
                                        RecognitionResult image = gson.fromJson(result, RecognitionResult.class);

                                        Mat mat = imgProcessor.base64ToCV2Image(image.image);

                                        Bitmap bmp = imgProcessor.matToBitmap(mat);
                                        ImageView imageView = findViewById(R.id.imageView);
                                        imageView.setImageBitmap(bmp);
                                    }
                                }
                            });}
            }
        });
//Кнопка продолжить
        Button continueBtn = findViewById(R.id.continueBTN);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText name = (TextInputEditText) findViewById(R.id.TextInputName);
                TextInputEditText comment = (TextInputEditText) findViewById(R.id.TextInputComments);


              /*  photoData photo = new photoData(name.getText().toString(),comment.getText().toString(),14,R.drawable.eagle);
                photoDat.add(photo);
                //посылка значения в mainactivity И ОТРКРЫТИЕ ТРЕТЬЕЙ АКТИВНОСТИ
                Intent intent = new Intent(Activity_2.this, ActivityPhoto.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", photoDat);
                intent.putExtras(bundle);
                startActivity(intent); */


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


