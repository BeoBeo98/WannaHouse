package com.example.wannahouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class HouseDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView roomStyle;
    private TextView numberOfRoom;
    private TextView rentalPrice;
    private TextView address;
    private TextView titleOfTheRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);
        mapping();
        Intent intent = getIntent();
        House house = (House) intent.getSerializableExtra("Position_");
        dataLinking(house);
    }

    protected void mapping() {
//        imageView = (ImageView) findViewById(R.id.viewPager);
        titleOfTheRoom = (TextView) findViewById(R.id.titleOfThePostDetails);
        roomStyle = (TextView) findViewById(R.id.roomStyleDetails);
        numberOfRoom = (TextView) findViewById(R.id.numberOfRoomDetails);
        rentalPrice = (TextView) findViewById(R.id.rentalPriceDetails);
        address = (TextView) findViewById(R.id.addressDetails);
    }

    protected void dataLinking(House house) {
        titleOfTheRoom.setText( house.getTitleOfTheRoom() );
        roomStyle.setText( String.valueOf(house.getRoomStyle()));
        numberOfRoom.setText( String.valueOf(house.getNumberOfRoom()));
        rentalPrice.setText( String.valueOf(house.getRentalPrice()));
        address.setText( house.getHouseNumber() + ", " + house.getStreet() + ", " + house.getWard() + ", " + house.getDistrict());
    }
}
