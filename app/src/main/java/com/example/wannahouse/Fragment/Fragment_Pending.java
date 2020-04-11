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
import androidx.lifecycle.Observer;

import com.example.wannahouse.Activity.EditHouseActivity;
import com.example.wannahouse.Adapter.HouseAdapter;
import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Class_Java.HouseViewModel;
import com.example.wannahouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Pending extends Fragment {
    View view;
    GridView gridView;
    HouseAdapter houseAdapter;

    ArrayList<House> listHouse;
    MutableLiveData<ArrayList<House>> liveDataHouses;
    DatabaseReference databaseHouse;
    Button button_dataChange;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pending, container, false);

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

    void mapping() {
        databaseHouse = FirebaseDatabase.getInstance().getReference().child("house");
        listHouse = new ArrayList<>();
        liveDataHouses = new MutableLiveData<>();
    }

    void setUpGridView(final MutableLiveData<ArrayList<House>> liveDataHouses) {
        houseAdapter = new HouseAdapter(liveDataHouses.getValue(), getActivity());
        gridView.setAdapter(houseAdapter);

        liveDataHouses.observe(getActivity(), new Observer<ArrayList<House>>() {
            @Override
            public void onChanged(ArrayList<House> houses) {
                Log.d("KEYHH", "size live " + liveDataHouses.getValue().size());
                houseAdapter.notifyDataSetChanged();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EditHouseActivity.class);
                intent.putExtra("Position_", liveDataHouses.getValue().get(position));
                getActivity().startActivityForResult(intent, 1);
            }
        });
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("account").child("roomOwner");
            Log.d("KEYHH", "user " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            final House house = dataSnapshot.getValue(House.class);
            if (house.isVerify() == false && house.getOwner_id().equals(user.getUid())) {
                listHouse.add(house);
            }
            Log.d("KEYHH", "add khi them phan tu");
            liveDataHouses.postValue(listHouse);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final House house = dataSnapshot.getValue(House.class);
            for (int i = 0; i < listHouse.size(); i++) {
                if (house.isVerify() == true && house.getOwner_id().equals(user.getUid()) && house.getRoom_id().equals(listHouse.get(i).getRoom_id())) {
                    listHouse.remove(i);
                    Log.d("KEYHH", "remove done, listhouse " + listHouse.size());
                }
                Log.d("KEYHH", "remove khi thay doi phan tu");
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