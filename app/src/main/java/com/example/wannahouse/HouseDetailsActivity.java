package com.example.wannahouse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.Serializable;

public class HouseDetailsActivity extends AppCompatActivity {

    private TextView roomStyle;
    private TextView numberOfRoom;
    private TextView rentalPrice;
    private TextView address;
    private TextView titleOfTheRoom;

    private TextView roomArea;
    private TextView deposit;
    private TextView electricCost;
    private TextView waterCost;
    private TextView parkingCost;
    private TextView internetCost;
    private TextView phoneNumber;
    private TextView postingDate;
    private TextView roomDescription;
    private TextView capacity;
    private TextView roomOwnerName;
    private ImageView avatarOwner;
    private ViewGroup vgCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);
        mapping();
        Intent intent = getIntent();
        final House house = (House) intent.getSerializableExtra("Position_");
        dataLinking(house);
        imageLinking(house);

    }

    @SuppressLint("WrongViewCast")
    protected void mapping() {
//        imageView = (ImageView) findViewById(R.id.viewPager);
        titleOfTheRoom = findViewById(R.id.titleOfThePostDetails);
        capacity = findViewById(R.id.capacity);
        roomStyle = findViewById(R.id.roomStyleDetails);
        rentalPrice = findViewById(R.id.rentalPriceDetails);
        address = findViewById(R.id.addressDetails);

        roomArea = findViewById(R.id.roomArea);
        deposit = findViewById(R.id.deposit);
        electricCost = findViewById(R.id.electricCost);
        waterCost = findViewById(R.id.waterCost);
        parkingCost = findViewById(R.id.parkingCost);
        internetCost = findViewById(R.id.internetCost);
        phoneNumber = findViewById(R.id.phoneNumber);
        postingDate = findViewById(R.id.postingDate);
        roomDescription = findViewById(R.id.roomDescription);

        roomOwnerName = findViewById(R.id.roomOwnerName);
        avatarOwner = findViewById(R.id.avatarOwner);
        numberOfRoom = findViewById(R.id.numberOfRoomDetails);
        vgCall = findViewById(R.id.viewGroupCall);
    }

    protected void dataLinking(final House house) {
        titleOfTheRoom.setText( house.getTitleOfTheRoom() );
        roomStyle.setText( house.getRoomStyle() );
        String gender = "";
        switch (house.getGender()) {
            case 1: gender = " ♂"; break;
            case 0: gender = " ♂/♀"; break;
            case -1: gender = " ♀"; break;
        }
        capacity.setText(house.getCapacity() + gender );
        rentalPrice.setText( String.valueOf(house.getRentalPrice()));
        roomArea.setText( String.valueOf(house.getRoomArea()) + "m2");
        deposit.setText( String.valueOf(house.getDeposit()) + " month");
        electricCost.setText( String.valueOf(house.getElectricityCost()) + "k");

        waterCost.setText( String.valueOf(house.getWaterCost()) + "k");
        parkingCost.setText( String.valueOf(house.getParkingCost() + "k"));
        internetCost.setText( String.valueOf(house.getInternetCost()) + "k");
        address.setText( house.getHouseNumber() + ", " + house.getStreet() + ", " + house.getWard() + ", " + house.getDistrict());
        phoneNumber.setText( "✆ " + house.getPhone());
        postingDate.setText( "" + house.getPostingDate());
        roomDescription.setText(( "" + house.getRoomDescription()));

        roomOwnerName.setText( house.getRoomOwnerName() );
        numberOfRoom.setText( house.getNumberOfRoom() + " room(s)");

        Glide.with(HouseDetailsActivity.this).load( house.getAvatar()).into(avatarOwner);
        vgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText( HouseDetailsActivity.this,"Click", Toast.LENGTH_LONG).show();
                call(house);
            }
        });
    }


    protected void imageLinking(final House house) {
        TextView count = findViewById(R.id.count);
        count.setText("+" + (house.getImage().size() - 4));
        if( house.getImage().size()-4 == 0 ) {
            count.setText("");
            count.setBackground(null);
        }

        ImageView image0 = findViewById(R.id.image0);
        ImageView image1 = findViewById(R.id.image1);
        ImageView image2 = findViewById(R.id.image2);
        ImageView image3 = findViewById(R.id.image3);
        Picasso.get().load(house.getImage().get(0)).into(image0);
        Picasso.get().load(house.getImage().get(1)).into(image1);
        Picasso.get().load(house.getImage().get(2)).into(image2);
        Picasso.get().load(house.getImage().get(3)).into(image3);

        image0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HouseDetailsActivity.this, ImageDetailActivity.class);
                intent.putExtra("Position_2", house);
                intent.putExtra("PositionImage_", 0);
                startActivity(intent);
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HouseDetailsActivity.this, ImageDetailActivity.class);
                intent.putExtra("Position_2", house);
                intent.putExtra("PositionImage_", 1);
                startActivity(intent);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HouseDetailsActivity.this, ImageDetailActivity.class);
                intent.putExtra("Position_2", house);
                intent.putExtra("PositionImage_", 2);
                startActivity(intent);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HouseDetailsActivity.this, ImageDetailActivity.class);
                intent.putExtra("Position_2", house);
                intent.putExtra("PositionImage_", 3);
                startActivity(intent);
            }
        });
    }

    protected void call(final House house) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("You want to call " + house.getPhone() );
        alertDialog.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setPositiveButton("OK CALL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", house.getPhone(), null));
                startActivity(intent);
            }
        });

        alertDialog.show();
    }

}
