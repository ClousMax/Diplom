package com.example.mainmodule;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {

    String baseUrl;
    Context context;
    OkHttpClient client;

    public HttpRequest(Activity activity) {
        context = activity.getApplicationContext();

        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("baseUrl.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            baseUrl = bufferedReader.readLine();
        }
        catch (IOException ex) {
            Log.d("Request", ex.getLocalizedMessage());
            baseUrl = "http://0.0.0.0:5000";
        }
        client = new OkHttpClient();
    }

    public void executeMethod(String base64Image, String imageExtension, HttpAsyncTask asyncTask) {
        Gson gson = new Gson();
        Base64Image bufImage = new Base64Image();
        bufImage.image = base64Image;
        bufImage.extension = imageExtension;
        String requestData = gson.toJson(bufImage);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), requestData);

        Request request = new Request.Builder()
                .url(baseUrl + "/sendImage")
                .post(body)
                .build();

        Call call = client.newCall(request);
        asyncTask.execute(call);
    }
}

class HttpAsyncTask extends AsyncTask<Call, Void, String> {
    @Override
    public String doInBackground(Call... call) {
        try {
            Response response = call[0].execute();
            if (!response.isSuccessful()) {
                return null;
            }
            return response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Base64Image {
    public String image;
    public String extension;
}