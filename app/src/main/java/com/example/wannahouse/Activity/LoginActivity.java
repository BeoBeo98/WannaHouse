package com.example.wannahouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.wannahouse.Activity.MainActivity.AUTHENTICATION_REQUEST_CODE;

public class LoginActivity extends AppCompatActivity {
    List<AuthUI.IdpConfig> listProviders;

    public static DatabaseReference databaseHouse = FirebaseDatabase.getInstance().getReference().child("house");
    public static DatabaseReference databaseAccount = FirebaseDatabase.getInstance().getReference().child("account").child("roomOwner");

    public static boolean isOwner = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Data.arrayListHouse = new ArrayList<>();
        Data.arrayOwner = new ArrayList<>();
        Data.liveDataHouse = new MutableLiveData<>();
        Data.liveDataOwner = new MutableLiveData<>();

        listProviders = Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                //        new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        showSignInOption();
        databaseHouse.addValueEventListener(valueEventListener);
        Data.liveDataHouse.setValue(Data.arrayListHouse);

        databaseAccount.addValueEventListener(valueAccount);
        Data.liveDataOwner.setValue(Data.arrayOwner);
    }

    public void showSignInOption() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(listProviders).setTheme(R.style.MyTheme).build(), AUTHENTICATION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHENTICATION_REQUEST_CODE) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "error " + idpResponse.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Data.arrayListHouse.clear();
            Data.liveDataHouse.getValue().clear();
            for (DataSnapshot houseSnapshot : dataSnapshot.getChildren()) {
                final House house = houseSnapshot.getValue(House.class);

                String key = house.getOwner_id();
                Log.d("KEYID", key);
                DatabaseReference databaseAccount = FirebaseDatabase.getInstance().getReference().child("account").child("roomOwner");

                databaseAccount.orderByKey().equalTo(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for( DataSnapshot accountSnapshot : dataSnapshot.getChildren() ) {
                            final Account account = accountSnapshot.getValue(Account.class);
                            house.setName(account.getName());
                            house.setPhone(account.getPhone());
                            house.setAvatar(account.getAvatar());
                            Log.d("KEYNAME", account.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                Data.arrayListHouse.add(house);
                Log.d("RRR", "size " + Data.arrayListHouse.size());
            }
            Data.liveDataHouse.postValue(Data.arrayListHouse);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener valueAccount = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Data.arrayOwner.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Account account = snapshot.getValue(Account.class);
                Data.arrayOwner.add(account);
            }
            Data.liveDataOwner.postValue(Data.arrayOwner);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
