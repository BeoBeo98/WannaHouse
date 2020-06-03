package com.example.wannahouse.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.wannahouse.Adapter.ViewPagerAdapter;
import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Class_Java.Notify;
import com.example.wannahouse.Fragment.FragmentAccount;
import com.example.wannahouse.Fragment.FragmentHome;
import com.example.wannahouse.Fragment.FragmentListAccount;
import com.example.wannahouse.Fragment.FragmentNotification;
import com.example.wannahouse.R;
import com.facebook.CallbackManager;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Fragment.Fragment_Profile.changeImageToHighQuality;

public class MainActivity extends AppCompatActivity {

    private Button button_explore;
    private Button button_home;

    private DatabaseReference databaseHouse;
    public static Account accountNew = new Account();
    public static Notify notifyNew = new Notify();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String s;
    public static String cityName = "Hà Nội";

    public static CallbackManager callbackManager;
    public static Boolean isLogin = false;

    List<AuthUI.IdpConfig> listProviders;
    public static int AUTHENTICATION_REQUEST_CODE = 1234;

    FragmentHome home = new FragmentHome();
    FragmentAccount account = new FragmentAccount();
    FragmentListAccount listAccount = new FragmentListAccount();
    FragmentNotification notification = new FragmentNotification();
    public ViewPagerAdapter adapter_main;

    TextView textViewHere;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout_home);
        viewPager = findViewById(R.id.viewPager_home);

        adapter_main = new ViewPagerAdapter(getSupportFragmentManager(), 1000);

        adapter_main.addFragment(home, "HOME");
        adapter_main.addFragment(account, "ACCOUNT");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if( user.getUid().equals("odVJNPmzGHXSdjX7jpkxTf2ipfA2")) {
            adapter_main.addFragment(listAccount, "MANAGE");
        }

        adapter_main.addFragment(notification, "NOTIFY");
        Log.d("KEYBB", adapter_main.toString() + "");
        viewPager.setAdapter(adapter_main);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
//        final View touchView = findViewById(R.id.viewPager_home);
//        touchView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        writeAccountToDatabase();
    }


    @SuppressLint("WrongConstant")
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

    void writeAccountToDatabase() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        accountNew.setId(user.getUid());
        accountNew.setName(user.getDisplayName());
        accountNew.setAvatar(changeImageToHighQuality(user.getPhotoUrl()).toString());

        houseNew.setOwner_id( user.getUid());
        houseNew.setName(accountNew.getName());
        houseNew.setAvatar(accountNew.getAvatar());
        Log.d("ABC", user.getUid() + " " + user.getDisplayName() + " " + user.getPhotoUrl());
        Log.d("ABC", houseNew.getOwner_id());
        final DatabaseReference guestDB = FirebaseDatabase.getInstance().getReference().child("account").child("guest");
        guestDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( !dataSnapshot.hasChild( user.getUid())) {
                    DatabaseReference guestNew = guestDB.child(user.getUid());
                    guestNew.setValue(accountNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("XXX", "guest " + user.getUid() );
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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