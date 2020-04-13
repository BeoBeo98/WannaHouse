package com.example.wannahouse.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wannahouse.Adapter.HouseAdapter;
import com.example.wannahouse.Adapter.ViewPagerAdapter;
import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Class_Java.HouseViewModel;
import com.example.wannahouse.Fragment.FragmentAccount;
import com.example.wannahouse.Fragment.FragmentAmenities;
import com.example.wannahouse.Fragment.FragmentHome;
import com.example.wannahouse.Fragment.FragmentNotification;
import com.example.wannahouse.Fragment.Fragment_Profile;
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
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.security.Provider;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Activity.LoginActivity.isOwner;
import static com.example.wannahouse.Fragment.Fragment_Profile.changeImageToHighQuality;

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

    List<AuthUI.IdpConfig> listProviders;
    public static int AUTHENTICATION_REQUEST_CODE = 1234;

    FragmentHome home = new FragmentHome();
    FragmentAccount account = new FragmentAccount();
    FragmentNotification notification = new FragmentNotification();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout_home);
        viewPager = findViewById(R.id.viewPager_home);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1000);

        adapter.addFragment(home, "HOME");
        adapter.addFragment(account, "ACCOUNT");
        adapter.addFragment(notification, "NOTIFICATION");
        Log.d("KEYBB", adapter.toString() + "");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        final View touchView = findViewById(R.id.viewPager_home);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        takeAccountLogin();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("WWW", "kill Main");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    void takeAccountLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        accountNew.setId(user.getUid());
        accountNew.setName(user.getDisplayName());
        accountNew.setAvatar(changeImageToHighQuality(user.getPhotoUrl()).toString());

        houseNew.setOwner_id( user.getUid());
        houseNew.setAvatar(accountNew.getAvatar());
        houseNew.setName(accountNew.getName());
    }

    void inputDataFromDatabase() {
        databaseHouse = FirebaseDatabase.getInstance().getReference().child("house");
        databaseHouse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot houseSnapshot : dataSnapshot.getChildren()) {
                    final House house = houseSnapshot.getValue(House.class);

                    String key = house.getOwner_id();
                    Log.d("KEYID", key);
                    DatabaseReference databaseAccount = FirebaseDatabase.getInstance().getReference().child("account").child("roomOwner");

                    databaseAccount.orderByKey().equalTo(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot temp : dataSnapshot.getChildren()) {
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
                    Log.d("KEYVV", s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
