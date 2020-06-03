package com.example.wannahouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Class_Java.Notify;
import com.example.wannahouse.Dialog.SingleChoiceDialog;
import com.example.wannahouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.wannahouse.Fragment.FragmentConfirmation.maxID_notifyAdmin;
import static com.example.wannahouse.Fragment.FragmentConfirmation.takeTotalNotify_Admin;
import static com.example.wannahouse.Fragment.FragmentConfirmation.updateHouse;

public class HouseDetailsActivity extends AppCompatActivity implements SingleChoiceDialog.SingleChoiceListener {

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
    private TextView availableDate;
    private TextView roomDescription;
    private TextView capacity;
    private TextView roomOwnerName;
    private ImageView avatarOwner;

    private ViewGroup vgCall;
    private ViewGroup vgReport;
    private ViewGroup vgVerify;
    private TextView txVerify;
    private ViewGroup vgPublic;
    private TextView txPublic;
    private ImageButton imageButton_back;
    private House house = new House();
    private Notify notifyReport = new Notify();
    int maxID_notifyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);
        mapping();
        Intent intent = getIntent();
        house = (House) intent.getSerializableExtra("Position_");
        imageLinking(house);
        itemAmenitiesLinking(house);
        dataLinking(house);
        setVgCall(house);
        back();

        takeTotalNotify_Owner(house);

        takeTotalNotify_Admin();
        validateAdmin();
        
        setVgReport(house);
        setVgPublic(house);
        setVgVerify(house);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("WrongViewCast")
    void mapping() {
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
        availableDate = findViewById(R.id.availableDate);
        roomDescription = findViewById(R.id.roomDescription);

        roomOwnerName = findViewById(R.id.roomOwnerName);
        avatarOwner = findViewById(R.id.avatarOwner);
        numberOfRoom = findViewById(R.id.numberOfRoomDetails);
        vgCall = findViewById(R.id.viewGroupCall);
        vgReport = findViewById(R.id.viewGroup_report);
        vgVerify = findViewById(R.id.viewGroup_verify);
        vgPublic = findViewById(R.id.viewGroup_public);
        txVerify = findViewById(R.id.textView_valueVerify);
        txPublic = findViewById(R.id.textView_valuePublic);

        imageButton_back = findViewById(R.id.imageButton_back);
    }

    void dataLinking(final House house) {
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
        roomArea.setText(house.getRoomArea() + "m2");
        if( house.getDeposit() <= 12 ) {
            deposit.setText(house.getDeposit() + " month");
        }
        else {
            deposit.setText(house.getDeposit() + " VND");
        }

        electricCost.setText(house.getElectricityCost() + "k");

        waterCost.setText(house.getWaterCost() + "k");
        parkingCost.setText(house.getParkingCost() + "k");
        internetCost.setText(house.getInternetCost() + "k");
        address.setText( house.getHouseNumber() + ", " + house.getStreet() + ", " + house.getWard() + ", " + house.getDistrict());
        phoneNumber.setText( "✆ " + house.getPhone());
        postingDate.setText( "" + house.getPostingDate());
        availableDate.setText("" + house.getAvailableDate());
        roomDescription.setText(( "" + house.getRoomDescription()));

        roomOwnerName.setText( house.getName() );
        numberOfRoom.setText( house.getNumberOfRoom() + " room(s)");

        Glide.with(HouseDetailsActivity.this).load( house.getAvatar()).into(avatarOwner);
    }

    void setVgCall(final House house) {
        vgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText( HouseDetailsActivity.this,"Click", Toast.LENGTH_LONG).show();
                call(house);
            }
        });
    }


     void imageLinking(final House house) {
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

     void call(final House house) {
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

    void back() {
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void itemAmenitiesLinking(House house) {

        ViewGroup air = findViewById(R.id.amenitiesAir);
        ViewGroup privateWC = findViewById(R.id.amenitiesPrivateWC);
        ViewGroup parking = findViewById(R.id.amenitiesParking);
        ViewGroup internet = findViewById(R.id.amenitiesInternet);
        ViewGroup security = findViewById(R.id.amenitiesSecurity);
        ViewGroup noOwner = findViewById(R.id.amenitiesNoOwner);
        ViewGroup noCurfew = findViewById(R.id.amenitiesNoCurfew);
        ViewGroup cooking = findViewById(R.id.amenitiesCooking);
        ViewGroup bed = findViewById(R.id.amenitiesBed);
        ViewGroup window = findViewById(R.id.amenitiesWindow);
        ViewGroup waterHeater = findViewById(R.id.amenitiesWaterHeater);
        ViewGroup washing = findViewById(R.id.amenitiesWashing);
        ViewGroup wardrobe = findViewById(R.id.amenitiesWardrobe);
        ViewGroup fridge = findViewById(R.id.amenitiesFridge);
        ViewGroup loft = findViewById(R.id.amenitiesLoft);
        ViewGroup television = findViewById(R.id.amenitiesTelevision);

        float alpha = (float) 0.15;

        if( !house.isAirConditioner() ) air.setAlpha(alpha);
        if( !house.isPrivateWC() ) privateWC.setAlpha(alpha);
        if( !house.isParkingLot() ) parking.setAlpha(alpha);
        if( !house.isInternet() ) internet.setAlpha(alpha);
        if( !house.isSecurity() ) security.setAlpha(alpha);
        if( !house.isNoOwner() ) noOwner.setAlpha(alpha);
        if( !house.isNoCurfew() ) noCurfew.setAlpha(alpha);
        if( !house.isCook() ) cooking.setAlpha(alpha);
        if( !house.isBed() ) bed.setAlpha(alpha);
        if( !house.isWindow() ) window.setAlpha(alpha);
        if( !house.isWaterHeater() ) waterHeater.setAlpha(alpha);
        if( !house.isWashing() ) washing.setAlpha(alpha);
        if( !house.isWardrobe() ) wardrobe.setAlpha(alpha);
        if( !house.isFridge() ) fridge.setAlpha(alpha);
        if( !house.isLoft() ) loft.setAlpha(alpha);
        if( !house.isTelevision() ) television.setAlpha(alpha);
    }

    void validateAdmin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if( user.getUid().equals(Data.ADMIN)) {
            vgVerify.setVisibility(View.VISIBLE);
            vgPublic.setVisibility(View.VISIBLE);
            vgReport.setVisibility(View.GONE);
            vgCall.setVisibility(View.GONE);
        }
        else {
            vgVerify.setVisibility(View.GONE);
            vgPublic.setVisibility(View.GONE);

            vgReport.setVisibility(View.VISIBLE);
            vgCall.setVisibility(View.VISIBLE);
        }

        if( house.isVerify() == true ) {
            txVerify.setText("TRUE");
            txVerify.setTextColor(Color.parseColor("#009933"));
        }
        else {
            txVerify.setText("FALSE");
            txVerify.setTextColor(Color.parseColor("#ff0000"));
        }

        if( house.isPublicRoom() == true ) {
            txPublic.setText("TRUE");
            txPublic.setTextColor(Color.parseColor("#009933"));
        }
        else {
            txPublic.setText("FALSE");
            txPublic.setTextColor(Color.parseColor("#ff0000"));
        }

    }

    void setVgReport(final House house) {
        vgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog = new SingleChoiceDialog(R.array.report_house);
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getSupportFragmentManager(), "Why don't you like this room");
            }
        });
    }

    void verify(final House house) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Verify this Room");
        alertDialog.setMessage("You really want to VERIFY " + house.getTitleOfTheRoom() );
        alertDialog.setNegativeButton("FALSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house.setVerify(false);
                updateHouse(house);

                if( house.isVerify() == true ) {
                    txVerify.setText("TRUE");
                    txVerify.setTextColor(Color.parseColor("#009933"));
                }
                else {
                    txVerify.setText("FALSE");
                    txVerify.setTextColor(Color.parseColor("#ff0000"));
                }
            }
        });
        alertDialog.setPositiveButton("TRUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house.setVerify(true);
                updateHouse(house);

                if( house.isVerify() == true ) {
                    txVerify.setText("TRUE");
                    txVerify.setTextColor(Color.parseColor("#009933"));
                }
                else {
                    txVerify.setText("FALSE");
                    txVerify.setTextColor(Color.parseColor("#ff0000"));
                }

                Notify notifyApproved = new Notify();
                notifyApproved.setNotify_id("notify00" + Integer.valueOf(maxID_notifyUser+1));
                notifyApproved.setType(-2);
                notifyApproved.setOwner_id(house.getOwner_id());
                notifyApproved.setHouse_id(house.getRoom_id());

                Calendar calendar = Calendar.getInstance();
                String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                notifyApproved.setTime(hour + ":" + minute + " " + date);

                DatabaseReference notifyUser = FirebaseDatabase.getInstance().getReference()
                        .child("notify").child(house.getOwner_id())
                        .child(notifyApproved.getNotify_id().replace("notify00",""));
                notifyUser.setValue(notifyApproved);
            }
        });

        alertDialog.show();
    }

    void setVgVerify(final House house ) {
        vgVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify(house);
            }
        });
    }

    void publicRoom(final House house) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Public this Room");
        alertDialog.setMessage("You want to Public this room " + house.getTitleOfTheRoom() );
        alertDialog.setNegativeButton("FALSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house.setPublicRoom(false);
                updateHouse(house);

                if( house.isPublicRoom() == true ) {
                    txPublic.setText("TRUE");
                    txPublic.setTextColor(Color.parseColor("#009933"));
                }
                else {
                    txPublic.setText("FALSE");
                    txPublic.setTextColor(Color.parseColor("#ff0000"));
                }

                Notify notifyUnpublicRoom = new Notify();
                notifyUnpublicRoom.setNotify_id("notify00" + Integer.valueOf(maxID_notifyUser+1));
                notifyUnpublicRoom.setType(0);
                notifyUnpublicRoom.setOwner_id(house.getOwner_id());
                notifyUnpublicRoom.setHouse_id(house.getRoom_id());

                Calendar calendar = Calendar.getInstance();
                String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                notifyUnpublicRoom.setTime(hour + ":" + minute + " " + date);

                DatabaseReference notifyUser = FirebaseDatabase.getInstance().getReference()
                        .child("notify").child(house.getOwner_id())
                        .child(notifyUnpublicRoom.getNotify_id().replace("notify00",""));
                notifyUser.setValue(notifyUnpublicRoom);

                house.setReport( house.getReport()+1 );

                DatabaseReference accountDB = FirebaseDatabase.getInstance().getReference()
                        .child("account").child("roomOwner").child(house.getOwner_id());

                HashMap<String, Object> result = new HashMap<>();
                result.put("report", house.getReport() );
                accountDB.updateChildren(result);

                Log.d("TEMM", house.getReport() + "");
            }
        });
        alertDialog.setPositiveButton("TRUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                house.setPublicRoom(true);
                updateHouse(house);

                if( house.isPublicRoom() == true ) {
                    txPublic.setText("TRUE");
                    txPublic.setTextColor(Color.parseColor("#009933"));
                }
                else {
                    txPublic.setText("FALSE");
                    txPublic.setTextColor(Color.parseColor("#ff0000"));
                }

            }
        });

        alertDialog.show();
    }

    void setVgPublic(final House house ) {
        vgPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicRoom(house);
            }
        });
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        notifyReport.setNotify_id("notify00" + Long.valueOf(maxID_notifyAdmin + 1));
        notifyReport.setType(1);
        notifyReport.setOwner_id(house.getOwner_id());
        notifyReport.setReport_id(user.getUid());
        notifyReport.setReason(list[position]);
        notifyReport.setHouse_id(house.getRoom_id());

        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        notifyReport.setTime(hour + ":" + minute + " " + date);

        final DatabaseReference notifyAdmin = FirebaseDatabase.getInstance().getReference()
                .child("notify").child(Data.ADMIN)
                .child(notifyReport.getNotify_id().replace("notify00",""));
        notifyAdmin.setValue(notifyReport);
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    void takeTotalNotify_Owner(House house) {
        DatabaseReference notifyUser = FirebaseDatabase.getInstance().getReference().child("notify").child(house.getOwner_id());
        notifyUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxID_notifyUser = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
