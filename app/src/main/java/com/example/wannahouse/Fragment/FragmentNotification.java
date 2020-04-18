package com.example.wannahouse.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

import static androidx.core.content.ContextCompat.getSystemService;
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
                if( liveDataNotify_House.getValue().get(position).getOwner_id().isEmpty() ) {
                    Toast.makeText(getActivity(), "Oop, this unit is not available ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), HouseDetailsActivity.class);
                    intent.putExtra("Position_", liveDataNotify_House.getValue().get(position)

                    );
                    startActivity(intent);
                }
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//                alertDialog.setTitle("Delete Notify");
//                alertDialog.setMessage("You want to delete this notify" );
//                alertDialog.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                alertDialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        DatabaseReference notifyDB = FirebaseDatabase.getInstance().getReference()
//                                .child("notify").child(user.getUid());
//                        notifyDB.child(listNotify.get(position).getNotify_id().replace("notify00","")).removeValue();
//                    }
//                });
//
//                alertDialog.show();
//                return true;
                return false;
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


    // android 8 tro len
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notification(Notify notify) {
        NotificationChannel channel = new NotificationChannel("notifyId","notifyName", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(getActivity(), NotificationManager.class);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder =  new NotificationCompat.Builder(getActivity(), "notifyId");
        if( notify.getType() == 2) {
            builder.setContentText("WannaHouse")
                    .setSmallIcon(R.drawable.home512px)
                    .setAutoCancel(true)
                    .setContentText("just add a unit");
        } else if( notify.getType() == -2 ) {
            builder.setContentText("WannaHouse")
                    .setSmallIcon(R.drawable.home512px)
                    .setAutoCancel(true)
                    .setContentText("Approved a unit");
        } else if( notify.getType() == 1 ) {
            builder.setContentText("WannaHouse")
                    .setSmallIcon(R.drawable.home512px)
                    .setAutoCancel(true)
                    .setContentText("just reported a unit:" + "\nReaseon: " + notify.getReason());
        } else if( notify.getType() == 0 ) {
            builder.setContentText("WannaHouse")
                    .setSmallIcon(R.drawable.home512px)
                    .setAutoCancel(true)
                    .setContentText("some one report about unit:" + "\nReason" + notify.getReason());
        }

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(999, builder.build() );
    }
}
