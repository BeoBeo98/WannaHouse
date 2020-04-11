package com.example.wannahouse.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Fragment.FragmentAmenities.PICK_IMAGE_REQUEST_CODE;

public class EditHouseActivity extends HouseDetailsActivity {

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

    }
}
