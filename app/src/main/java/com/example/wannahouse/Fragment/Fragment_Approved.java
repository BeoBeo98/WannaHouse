package com.example.wannahouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Approved extends Fragment_Pending {

    View view;
    Button button_dataChange;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approved, container, false);

        gridView = view.findViewById(R.id.gridView_houseResult);
        button_dataChange = view.findViewById(R.id.button_dataChange);

        mapping();
        databaseHouse.addChildEventListener(childEventListener);
        liveDataHouses.setValue(listHouse);

        setUpGridView(liveDataHouses);

        button_dataChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                houseAdapter.notifyDataSetChanged();
                Log.d("WWW", "button size " + liveDataHouses.getValue().size());
                Log.d("WWW", "user " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            }
        });

        return view;
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final House house = dataSnapshot.getValue(House.class);

            DatabaseReference accountDB = FirebaseDatabase.getInstance().getReference().child("account").child("roomOwner");
            accountDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for( DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                        Account account = accountSnapshot.getValue(Account.class);
                        if( account.getId().equals(user.getUid())) {
                            house.setPhone( account.getPhone());
                            house.setName( account.getName());
                            house.setAvatar( account.getAvatar());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if (house.isVerify() == true && house.getOwner_id().equals(user.getUid())) {
                listHouse.add(house);
            }
            liveDataHouses.postValue(listHouse);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final House house = dataSnapshot.getValue(House.class);

            DatabaseReference accountDB = FirebaseDatabase.getInstance().getReference().child("account").child("roomOwner");
            accountDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for( DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                        Account account = accountSnapshot.getValue(Account.class);
                        if( account.getId().equals(user.getUid())) {
                            house.setPhone( account.getPhone());
                            house.setName( account.getName());
                            house.setAvatar( account.getAvatar());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if (house.isVerify() == true && house.getOwner_id().equals(user.getUid())) {
                listHouse.add(house);
            }
            liveDataHouses.postValue(listHouse);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final House house = dataSnapshot.getValue(House.class);
            for (int i = 0; i < listHouse.size(); i++) {
                if ( house.getOwner_id().equals(user.getUid()) && house.getRoom_id().equals(listHouse.get(i).getRoom_id())) {
                    listHouse.remove(i);
                    Log.d("KEYHH", "remove done, listhouse " + listHouse.size());
                }
                Log.d("KEYHH", "remove khi thay doi phan tu");
            }
            liveDataHouses.postValue(listHouse);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
