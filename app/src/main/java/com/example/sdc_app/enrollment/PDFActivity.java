package com.example.sdc_app.enrollment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sdc_app.R;
import com.github.barteksc.pdfviewer.PDFView;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.IOException;
import java.io.InputStream;

public class PDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfactivity);

        PDFView pdfView = findViewById(R.id.show_pdf);

        try {
            // Load PDF from assets
            InputStream inputStream = getAssets().open("MyCollections.pdf");
            pdfView.fromStream(inputStream)
                    .load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
