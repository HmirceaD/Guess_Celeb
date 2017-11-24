package com.example.maruta.guessthecelebrity;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataDownloader extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {

        URL url = null;
        String rs = "";
        HttpURLConnection urlCon = null;

        try{

            url = new URL(strings[0]);
            urlCon = (HttpURLConnection)url.openConnection();

            InputStream in = urlCon.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while(data != -1){

                char crr = (char)data;

                rs += crr;

                data = reader.read();

            }

            return rs;

        }catch (Exception e){

            e.printStackTrace();
            return "";
        }

    }
}
