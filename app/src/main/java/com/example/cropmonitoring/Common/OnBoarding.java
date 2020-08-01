package com.example.cropmonitoring.Common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cropmonitoring.HelperClasses.SliderAdapter;
import com.example.cropmonitoring.R;
import com.example.cropmonitoring.User.UserDashboard;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    Button StartMonitoring;
    LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;
    TextView[] dots;
    Animation animation;
    int currentpos;

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            if (position == 0) {
                StartMonitoring.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                StartMonitoring.setVisibility(View.INVISIBLE);

            } else {
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.slide_anim);
                StartMonitoring.setAnimation(animation);
                StartMonitoring.setVisibility(View.VISIBLE);

                StartMonitoring.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        //Hooks
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dotslayout);
        StartMonitoring = findViewById(R.id.letsgo_btn);


        //Call On Adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);

        viewPager.addOnPageChangeListener(changeListener);
    }

    public  void next(View view){
        viewPager.setCurrentItem(currentpos + 1);

    }

    private void addDots(int position) {

        dots = new TextView[3];
        dotsLayout.removeAllViews();
        currentpos = position;

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);

        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }


    }

}
