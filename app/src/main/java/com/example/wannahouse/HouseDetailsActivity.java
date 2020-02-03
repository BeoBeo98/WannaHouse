package com.example.wannahouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;

public class HouseDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewImage;
    private TextView roomStyle;
    private TextView numberOfRoom;
    private TextView rentalPrice;
    private TextView address;
    private TextView titleOfTheRoom;
    private ViewPager imageViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);
        mapping();
        final Intent intent = getIntent();
        final House house = (House) intent.getSerializableExtra("Position_");
        dataLinking(house);

        TextView count = findViewById(R.id.count);
        count.setText("+" + String.valueOf( house.getImage().size()-4));
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
                intent.putExtra("Position_2", (Serializable) house);
                intent.putExtra("PositionImage_", 0);
                startActivity(intent);
            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HouseDetailsActivity.this, ImageDetailActivity.class);
                intent.putExtra("Position_2", (Serializable) house);
                intent.putExtra("PositionImage_", 1);
                startActivity(intent);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HouseDetailsActivity.this, ImageDetailActivity.class);
                intent.putExtra("Position_2", (Serializable) house);
                intent.putExtra("PositionImage_", 2);
                startActivity(intent);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( HouseDetailsActivity.this, ImageDetailActivity.class);
                intent.putExtra("Position_2", (Serializable) house);
                intent.putExtra("PositionImage_", 3);
                startActivity(intent);
            }
        });

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
