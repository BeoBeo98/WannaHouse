package com.example.wannahouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.wannahouse.Activity.ExploreActivity;
import com.example.wannahouse.Activity.HouseDetailsActivity;
import com.example.wannahouse.Adapter.NotificationAdapter;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Class_Java.Notify;
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

import static com.example.wannahouse.Adapter.NotificationAdapter.liveDataNotify_House;

public class FragmentNotification extends Fragment {
    View view;
    GridView gridView;

    ArrayList<Notify> listNotify = new ArrayList<>();
    MutableLiveData<ArrayList<Notify>> liveDataNotify = new MutableLiveData<>();

    NotificationAdapter adapter;
    DatabaseReference notifyDB = FirebaseDatabase.getInstance().getReference().child("notify");

    Button button_dataChange;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        listNotify = new ArrayList<>();
        liveDataNotify = new MutableLiveData<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        notifyDB.child(user.getUid()).addChildEventListener(childNotify);
        liveDataNotify.setValue(listNotify);

        Log.d("PPP" , "size ngoai " + listNotify.size());

        adapter = new NotificationAdapter( liveDataNotify.getValue(), getActivity());
        gridView = view.findViewById(R.id.gridView_notification);
        gridView.setAdapter(adapter);

        liveDataNotify.observe(getActivity(), new Observer<ArrayList<Notify>>() {
            @Override
            public void onChanged(ArrayList<Notify> notifies) {
                adapter = new NotificationAdapter( liveDataNotify.getValue(), getActivity());
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( getActivity() , HouseDetailsActivity.class);
                intent.putExtra("Position_", liveDataNotify_House.getValue().get(position)

                );
                startActivity(intent);
            }
        });

        Log.d("PPP" , "size ngoai " + listNotify.size());




        button_dataChange = view.findViewById(R.id.button_dataChange);
        button_dataChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                Log.d("PPP" , "size trong " + listNotify.size());
            }
        });

        return view;
    }

    ChildEventListener childNotify = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Notify notify = dataSnapshot.getValue(Notify.class);
            listNotify.add(notify);
            Log.d("PPP" , "size " + listNotify.size());
            liveDataNotify.postValue(listNotify);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
