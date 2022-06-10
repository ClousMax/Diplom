package com.example.mainmodule;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.MatOfByte;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessor {

    Context context;

    public ImageProcessor(Activity activity)
    {
        context = activity.getApplicationContext();
        if (OpenCVLoader.initDebug()) {}
    }

    public Mat loadTestCV2Image()
    {
        String imgPath = getPath("test.jpg", context);
        Mat img = Imgcodecs.imread(imgPath, Imgcodecs.IMREAD_COLOR);
        return img;
    }

    public Mat bitmapToMat(Bitmap bmp)
    {
        Mat img = new Mat();
        Utils.bitmapToMat(bmp, img);
        return img;
    }

    public Bitmap matToBitmap(Mat img)
    {
//        Imgproc.cvtColor(seedsImage, tmp, Imgproc.COLOR_GRAY2RGBA, 4);
        Bitmap bmp = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img, bmp);
        return bmp;
    }

    public String getPath(String file, Context context) {
        AssetManager assetManager = context.getAssets();

        BufferedInputStream inputStream = null;
        try {
            // Read data from assets.
            inputStream = new BufferedInputStream(assetManager.open(file));
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();

            // Create copy file in storage.
            File outFile = new File(context.getFilesDir(), file);
            FileOutputStream os = new FileOutputStream(outFile);
            os.write(data);
            os.close();
            // Return a path to file which may be read in common way.
            return outFile.getAbsolutePath();
        } catch (IOException ex) {
            Log.i("OPENCV", "Failed to upload a file");
        }
        return "";
    }

    public String cv2ImageTobase64(Mat image, String extension)
    {
        MatOfByte buf = new MatOfByte();
        if (!Imgcodecs.imencode(extension, image, buf)) {
            //TODO exception processing
        }
        byte[] bytes = buf.toArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    //TODO exception processing
    public Mat base64ToCV2Image(String image)
    {
        byte[] bytes;
        bytes = Base64.decode(image, Base64.DEFAULT);
        Mat mat = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.IMREAD_GRAYSCALE);
        return mat;
    }
}