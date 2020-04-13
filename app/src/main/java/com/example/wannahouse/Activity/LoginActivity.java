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
import com.facebook.appevents.AppEventsConstants;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    public static ArrayList<House> arrayListHouse = new ArrayList<>();
    public static MutableLiveData<ArrayList<House>> liveDataHouse = new MutableLiveData<>();
    public static DatabaseReference databaseHouse = FirebaseDatabase.getInstance().getReference().child("house");

    public static boolean isOwner = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        listProviders = Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                //        new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        showSignInOption();
        databaseHouse.addValueEventListener(valueEventListener);
        liveDataHouse.setValue(arrayListHouse);
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
            arrayListHouse.clear();
            liveDataHouse.getValue().clear();
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

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if( user != null && user.getUid().equals(account.getId())) {
                                isOwner = true;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                arrayListHouse.add(house);
                Log.d( "RRR", "size " + arrayListHouse.size());
            }
            liveDataHouse.postValue(arrayListHouse);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
