package com.example.wannahouse.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.wannahouse.R;

import java.util.ArrayList;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Fragment.FragmentAmenities.PICK_IMAGE_REQUEST_CODE;

public class EditHouseActivity extends HouseDetailsActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_house);
//        mapping();
//        Intent intent = getIntent();
//        dataLinking(houseNew);
//        imageLinking(houseNew);
//        itemAmenitiesLinking(houseNew);
    }
}
