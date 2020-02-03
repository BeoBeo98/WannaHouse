package com.example.wannahouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ImageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_pager);

        Intent intent = getIntent();
        House house = (House) intent.getSerializableExtra("Position_2");

        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(this,house.getImage());
        viewPager.setAdapter(imageViewPagerAdapter);
        int current = intent.getIntExtra("PositionImage_", 0);
        viewPager.setCurrentItem(current);
    }
}
