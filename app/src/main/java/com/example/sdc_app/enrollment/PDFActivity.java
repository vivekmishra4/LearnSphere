package com.example.sdc_app.enrollment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sdc_app.R;
import com.github.barteksc.pdfviewer.PDFView;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfactivity);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#001F3F")));
        PDFView pdfView = findViewById(R.id.show_pdf);
        Intent intent=getIntent();
        String url=intent.getStringExtra("contentLink");
        String topicName=intent.getStringExtra("topicName");
        TextView textView=findViewById(R.id.content_title);
        textView.setText(topicName);
        Log.i("my url",url);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            InputStream inputStream=null;
            @Override
            public void run() {

                //Background work here
                try{
                    URL url2=new URL(url);
                    HttpURLConnection urlConnection=(HttpURLConnection) url2.openConnection();
                    if(urlConnection.getResponseCode()==200){
                        inputStream=new BufferedInputStream(urlConnection.getInputStream());

                    }
                } catch (IOException e){
                    e.printStackTrace();

                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (inputStream != null) {
                            pdfView.fromStream(inputStream).load();
                        } else {
                            // Handle the case where inputStream is null (e.g., show an error message)
                            Toast.makeText(PDFActivity.this, "Unable to Display Content", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

}
