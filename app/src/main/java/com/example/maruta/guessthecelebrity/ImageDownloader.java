package com.example.maruta.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    Bitmap image;

    @Override
    protected Bitmap doInBackground(String... strings) {

        try{

            URL url = new URL(strings[0]);

            HttpURLConnection urlCon = (HttpURLConnection)url.openConnection();

            urlCon.connect();

            InputStream in = urlCon.getInputStream();

            image = BitmapFactory.decodeStream(in);

            return image;

        }catch(Exception ex){

            ex.printStackTrace();
            return null;
        }

    }
}
