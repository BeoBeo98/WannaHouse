package com.example.wannahouse.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Repo {

    static Repo instance;
    ArrayList<House> listHouse = new ArrayList<>();
    MutableLiveData<ArrayList<House>> houses = new MutableLiveData<>();

    public static Repo getInstance() {
        if( instance == null ) instance = new Repo();

        return instance;
    }

    public MutableLiveData<ArrayList<House>> getHouses() {
        if( listHouse.size() == 0 ) {
            loadHouse();
        }

        houses.setValue(listHouse);
        return houses;
    }

    private void loadHouse() {
        final DatabaseReference databaseHouse = FirebaseDatabase.getInstance().getReference().child("house");
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
                    if( house.isVerify() ) {
                        listHouse.add(house);
                    }
                }
                houses.postValue(listHouse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
