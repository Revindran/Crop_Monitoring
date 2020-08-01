package com.example.cropmonitoring.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cropmonitoring.R;
import com.example.cropmonitoring.User.UserDashboard;

public class SplashScreen extends AppCompatActivity {


    SharedPreferences OnBoardingScreen;


    private static int SPLASH_TIMER=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                OnBoardingScreen= getSharedPreferences("OnBoardingScreen",MODE_PRIVATE);
                boolean FirstTime = OnBoardingScreen.getBoolean("firstTime",true);

                if (FirstTime){

                    SharedPreferences.Editor editor = OnBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(intent);
                    finish();

                }

                else {
                    Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIMER);
    }
}
