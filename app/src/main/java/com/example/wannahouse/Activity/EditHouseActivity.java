package com.example.wannahouse.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditHouseActivity extends HouseDetailsActivity {

    ViewGroup vg_editRoom;
    ViewGroup vg_deleteRoom;

    public static int EDIT_ROOM_CODE = 1004;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_house);
        Intent intent = getIntent();
        final House posHouse = (House) intent.getSerializableExtra("Position_");
        mapping();
        imageLinking(posHouse);
        itemAmenitiesLinking(posHouse);
        dataLinking(posHouse);
        back();

        progressDialog = new ProgressDialog( EditHouseActivity.this);
        vg_editRoom = findViewById(R.id.viewGroup_edit);
        vg_deleteRoom = findViewById(R.id.viewGroup_delete);

        vg_deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(posHouse);
            }
        });

        vg_editRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                progressDialog.setContentView(R.layout.progress);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                edit(posHouse);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if( progressDialog.isShowing() == true )
            progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void delete(final House house) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Detele");
        alertDialog.setMessage("You want to delele: " + house.getTitleOfTheRoom() );
        alertDialog.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference houseDB = FirebaseDatabase.getInstance().getReference().child("house");
                houseDB.child(house.getRoom_id().replace("house00", "")).removeValue();
                finish();
            }
        });

        alertDialog.show();
    }

    void edit(House house) {
        Intent intent = new Intent(this, ListYourSpaceActivity.class);
        intent.putExtra("Position_edit", house);
        startActivity(intent);
    }
}
