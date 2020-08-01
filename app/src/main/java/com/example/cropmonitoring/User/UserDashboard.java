package com.example.cropmonitoring.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cropmonitoring.R;

public class UserDashboard extends AppCompatActivity {

    ImageView imageView, img1, img2,img3,img4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        imageView = (ImageView) findViewById(R.id.my_farm);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyFarmView.class);
                startActivity(intent);
            }
        });

        img1 = findViewById(R.id.humid);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Humidity.class);
                startActivity(intent);
            }
        });
        img2 = findViewById(R.id.temp);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Temprature.class);
                startActivity(intent);
            }
        });
        img3 = findViewById(R.id.moisture);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Moisture.class);
                startActivity(intent);
            }
        });
        img4 = findViewById(R.id.increase);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IncreaseYield.class);
                startActivity(intent);
            }
        });


//        WebView web = (WebView) findViewById(R.id.tempvweb);
//        WebSettings webSettings = web.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        web.loadUrl("https://www.google.in");
//
//        web.setWebViewClient(new WebViewClient());
    }

}



