package com.example.wannahouse.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wannahouse.Adapter.ViewPagerAdapter;
import com.example.wannahouse.Fragment.FragmentAccount;
import com.example.wannahouse.Fragment.FragmentHome;
import com.example.wannahouse.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class HomeScreenActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tabLayout = findViewById(R.id.tabLayout_home);
        viewPager = findViewById(R.id.viewPager_home);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),1000);

        FragmentHome home = new FragmentHome();
        FragmentAccount account = new FragmentAccount();

        adapter.addFragment( home, "HOME");
        adapter.addFragment( account, "ACCOUNT");
        Log.d("KEYBB", adapter.toString()+"");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
