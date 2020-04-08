package com.example.wannahouse.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wannahouse.Adapter.ViewPagerAdapter;
import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Fragment.FragmentAccount;
import com.example.wannahouse.Fragment.FragmentHome;
import com.example.wannahouse.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Button button_explore;
    private Button button_home;

    private DatabaseReference databaseHouse;
    public static Account accountNew = new Account();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String s;

    public static CallbackManager callbackManager;
    public static Boolean isLogin = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        inputDatFromDatabase();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void inputDatFromDatabase() {
        databaseHouse = FirebaseDatabase.getInstance().getReference().child("house");
        databaseHouse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot houseSnapshot : dataSnapshot.getChildren() ) {
                    final House house = houseSnapshot.getValue(House.class);

                    String key = house.getOwner_id();
                    Log.d("KEYID", key);
                    DatabaseReference databaseAccount = FirebaseDatabase.getInstance().getReference().child("account").child("roomOwner");

                    databaseAccount.orderByKey().equalTo(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for( DataSnapshot temp : dataSnapshot.getChildren() ) {
                                final Account account = temp.getValue(Account.class);
                                house.setName(account.getName());
                                house.setPhone(account.getPhone());
                                house.setAvatar(account.getAvatar());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                    Data.arrayListHouse.add(house);
                    s = String.valueOf(Data.arrayListHouse.size());
                    Log.d("KEY", s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        int length = Data.arrayListHouse.size();
        String string = String.valueOf(length);
        Log.d("KEY", string + "quan");
    }

}
