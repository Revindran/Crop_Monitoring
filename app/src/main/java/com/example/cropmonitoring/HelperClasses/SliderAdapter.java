package com.example.cropmonitoring.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cropmonitoring.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    int images[] = {

            R.drawable.monitor_crop_img,
            R.drawable.proper_planting_img,
            R.drawable.high_output_img

    };

    int titles[] = {

            R.string.first_title,
            R.string.second_title,
            R.string.third_title
    };
    int descriptions[] = {
            R.string.first_desc,
            R.string.second_desc,
            R.string.third_desc
    };


    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        //Hooks
        ImageView imageView = view.findViewById(R.id.sliderimg);
        TextView title = view.findViewById(R.id.slidertitle);
        TextView desc = view.findViewById(R.id.sliderdesc);


        imageView.setImageResource(images[position]);
        title.setText(titles[position]);
        desc.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
