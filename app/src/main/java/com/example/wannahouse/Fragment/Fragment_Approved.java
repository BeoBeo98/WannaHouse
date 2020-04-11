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

import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Fragment_Approved extends Fragment_Pending {

    View view;
    ArrayList<House> listHouse = new ArrayList<>();
    MutableLiveData<ArrayList<House>> liveDataHouses = new MutableLiveData<>();
    DatabaseReference databaseHouse = FirebaseDatabase.getInstance().getReference().child("house");
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
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final House house = dataSnapshot.getValue(House.class);
            if (house.isVerify() == true && house.getOwner_id().equals(user.getUid())) {
                listHouse.add(house);
            }
            liveDataHouses.postValue(listHouse);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final House house = dataSnapshot.getValue(House.class);
            if (house.isVerify() == true && house.getOwner_id().equals(id)) {
                listHouse.add(house);
            }
            liveDataHouses.postValue(listHouse);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
