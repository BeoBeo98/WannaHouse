package com.example.wannahouse.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Activity.EditHouseActivity;
import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Activity.MainActivity.accountNew;
import static com.example.wannahouse.Activity.MainActivity.notifyNew;
import static com.example.wannahouse.Fragment.FragmentInformation.houseEdit;

public class FragmentConfirmation extends Fragment {

    private TextInputLayout textInput_phoneNumber;
    private TextInputLayout textInput_titleOfThePost;
    private TextInputLayout textInput_roomDescription;
    private TextInputLayout textInput_availableDate;

    private TextView textView_availableDate;

    private Button button_next4;
    private Button button_save;

    public static int FINISH_REQUEST_CODE = 1003;
    long maxID_house = 0;
    public static long maxID_notifyAdmin = 0;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        textInput_phoneNumber = view.findViewById(R.id.text_input_phoneNumber);
        textInput_titleOfThePost = view.findViewById(R.id.text_input_titleOfThePost);
        textInput_roomDescription = view.findViewById(R.id.text_input_roomDescription);
        textInput_availableDate = view.findViewById(R.id.textInput_availableDate);
        textView_availableDate = view.findViewById(R.id.textView_availableDate);
        button_next4 = view.findViewById(R.id.button_next4);
        button_save = view.findViewById(R.id.button_save);

        takeTotalHouse();
        takeTotalNotify_Admin();
        takeIDforHouse(houseNew);
        button_next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next4(v);
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next4(v);
            }
        });

        textView_availableDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        if( houseEdit != null ) {
            Log.d("QWE", "Result confirm " + houseEdit.getRoom_id());
            button_save.setVisibility(View.VISIBLE);
            button_next4.setVisibility(View.GONE);
            editConfirmation(houseEdit);
        }
        else {
            button_next4.setVisibility(View.VISIBLE);
            button_save.setVisibility(View.GONE);
        }

        return view;
    }

    private void pickDate() {
        final Calendar calendar = Calendar.getInstance();
        final int d = calendar.get(calendar.DATE);
        int m = calendar.get(calendar.MONTH);
        int y = calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);

                String datePick = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                textView_availableDate.setText(datePick);
            }
        }, y,m,d);
        datePickerDialog.show();
    }

    private boolean validate_phoneNumber() {
        String phoneNumber = textInput_phoneNumber.getEditText().getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            textInput_phoneNumber.setError("Please enter your phone Number");
            textInput_phoneNumber.setHintEnabled(false);
            return false;
        } else {
            textInput_phoneNumber.setError(null);
            return true;
        }
    }

    private boolean validate_titleOfThePost() {
        String titleOfThePost = textInput_titleOfThePost.getEditText().getText().toString().trim();
        if (titleOfThePost.isEmpty()) {
            textInput_titleOfThePost.setError("Enter room title");
            textInput_titleOfThePost.setHintEnabled(false);
            return false;
        } else {
            textInput_titleOfThePost.setError(null);
            return true;
        }
    }

    private boolean validate_roomDescription() {
        String roomDescription = textInput_roomDescription.getEditText().getText().toString().trim();
        if (roomDescription.isEmpty()) {
            textInput_roomDescription.setError("Detailed description...");
            textInput_roomDescription.setHintEnabled(false);
            return false;
        } else {
            textInput_roomDescription.setError(null);
            return true;
        }
    }

    private boolean validate_availableDate() {
        String roomAvailableDate = textView_availableDate.getText().toString().trim();
        if (roomAvailableDate.isEmpty()) {
            textInput_availableDate.setError("Detailed description...");
            textInput_availableDate.setHintEnabled(false);
            return false;
        } else {
            textInput_availableDate.setError(null);
            return true;
        }
    }

    public void next4(View v) {
        if( !validate_phoneNumber() | !validate_titleOfThePost() | !validate_roomDescription() | !validate_availableDate() ) {
            return;
        }
        else {
            if( houseEdit == null) {
                savingData(houseNew);
                upNewOwner(accountNew);
                upNewHouse(houseNew);
                upNewNofify(houseNew);
                Intent intent = new Intent(getActivity(), EditHouseActivity.class);
                intent.putExtra("Position_", houseNew);
                getActivity().startActivityForResult(intent, FINISH_REQUEST_CODE);
                getActivity().finish();
                Log.d("KEYBB", houseNew.toString());
            }
            else {
                savingData(houseEdit);
                updateHouse(houseEdit);
                Intent intent = new Intent(getActivity(), EditHouseActivity.class);
                intent.putExtra("Position_", houseEdit);
                getActivity().startActivityForResult(intent, FINISH_REQUEST_CODE);
                getActivity().finish();
                Log.d("KEYBB", houseEdit.toString());
            }
        }
    }

    private void savingData(House houseNew){
        if( houseNew.getRoom_id().isEmpty() ) {
            houseNew.setRoom_id("house00" + Long.valueOf(maxID_house +1));
        }
        houseNew.setPhone(  textInput_phoneNumber.getEditText().getText().toString().trim() );
        houseNew.setTitleOfTheRoom(  textInput_titleOfThePost.getEditText().getText().toString().trim() );
        Log.d("KEYAA", houseNew.getTitleOfTheRoom());
        houseNew.setRoomDescription(  textInput_roomDescription.getEditText().getText().toString().trim() );

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        houseNew.setPostingDate( currentDate );

        houseNew.setAvailableDate(textView_availableDate.getText().toString().trim());

        Log.d("ZZZ", "maxId " + houseNew.getOwner_id() );
        Log.d("ZZZ", "maxId " + houseNew.getName() );
    }

    void upNewOwner(final Account account) {
        final DatabaseReference databaseAccount = FirebaseDatabase.getInstance().getReference().child("account").child("roomOwner");
        databaseAccount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( !dataSnapshot.hasChild( account.getId() ) ) {
                    DatabaseReference newOwner = databaseAccount.child(account.getId());

                    newOwner.setValue(account).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        //    Toast.makeText( getActivity(), "New Owner ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                //    Toast.makeText( getActivity(), "Owner Exist ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void takeIDforHouse(final House house) {
        final DatabaseReference houseDB = FirebaseDatabase.getInstance().getReference().child("house");
        houseDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                while( dataSnapshot.hasChild( maxID_house + "" ) ) {
                    ++maxID_house;
                }
                house.setRoom_id("house00" + maxID_house);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void upNewHouse(final House house) {
        final DatabaseReference houseDB = FirebaseDatabase.getInstance().getReference().child("house");
        Map<String, Object> postValues = house.toMap();
        houseDB.child(house.getRoom_id().replace("house00","")).setValue(postValues);
//        houseDB.child("room_id").setValue(houseNew.getRoom_id());
//        houseDB.child("owner_id").setValue(houseNew.getOwner_id());
//        houseDB.child("roomStyle").setValue(houseNew.getRoomStyle());
//        houseDB.child("numberOfRoom").setValue(houseNew.getNumberOfRoom());
//        houseDB.child("capacity").setValue(houseNew.getCapacity());
//        houseDB.child("gender").setValue(houseNew.getGender());
//        houseDB.child("roomArea").setValue(houseNew.getRoomArea());
//        houseDB.child("rentalPrice").setValue(houseNew.getRentalPrice());
//        houseDB.child("deposit").setValue(houseNew.getDeposit());
//        houseDB.child("electricityCost").setValue(houseNew.getElectricityCost());
//        houseDB.child("waterCost").setValue(houseNew.getWaterCost());
//        houseDB.child("internetCost").setValue(houseNew.getInternetCost());
//        houseDB.child("parkingCost").setValue(houseNew.getParkingCost());
//        houseDB.child("city").setValue(houseNew.getCity());
//        houseDB.child("district").setValue(houseNew.getDistrict());
//        houseDB.child("ward").setValue(houseNew.getWard());
//        houseDB.child("street").setValue(houseNew.getStreet());
//        houseDB.child("houseNumber").setValue(houseNew.getHouseNumber());
//        houseDB.child("roomDescription").setValue(houseNew.getRoomDescription());
//        houseDB.child("titleOfTheRoom").setValue(houseNew.getTitleOfTheRoom());
//        houseDB.child("image").setValue(houseNew.getImage());
//        houseDB.child("privateWC").setValue(houseNew.isPrivateWC());
//        houseDB.child("parkingLot").setValue(houseNew.isParkingLot());
//        houseDB.child("window").setValue(houseNew.isWindow());
//        houseDB.child("security").setValue(houseNew.isSecurity());
//        houseDB.child("internet").setValue(houseNew.isInternet());
//        houseDB.child("noCurfew").setValue(houseNew.isNoCurfew());
//        houseDB.child("noOwner").setValue(houseNew.isNoOwner());
//        houseDB.child("airConditioner").setValue(houseNew.isAirConditioner());
//        houseDB.child("waterHeater").setValue(houseNew.isWaterHeater());
//        houseDB.child("cook").setValue(houseNew.isCook());
//        houseDB.child("fridge").setValue(houseNew.isFridge());
//        houseDB.child("washing").setValue(houseNew.isWashing());
//        houseDB.child("loft").setValue(houseNew.isLoft());
//        houseDB.child("bed").setValue(houseNew.isBed());
//        houseDB.child("wardrobe").setValue(houseNew.isWardrobe());
//        houseDB.child("television").setValue(houseNew.isTelevision());
//        houseDB.child("postingDate").setValue(houseNew.getPostingDate());
//        houseDB.child("report").setValue(houseNew.getReport());
//        houseDB.child("verify").setValue(houseNew.isVerify());
//        houseDB.child("publicRoom").setValue(houseNew.isPublicRoom());
    }

    public static void updateHouse(House house) {
        Log.d("QWE", house.getRoom_id() );
        final DatabaseReference houseDB = FirebaseDatabase.getInstance().getReference().child("house")
                .child(house.getRoom_id().replace("house00",""));
        Map<String, Object> postValues = house.toMap();
        houseDB.updateChildren(postValues);
    }

    void takeTotalHouse() {
        final DatabaseReference houseDB = FirebaseDatabase.getInstance().getReference().child("house");
        houseDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxID_house = dataSnapshot.getChildrenCount();
                Log.d("KEYQQ", "maxId ddd " + maxID_house);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void takeTotalNotify_Admin() {
        final DatabaseReference notifyDB = FirebaseDatabase.getInstance().getReference().child("notify").child("odVJNPmzGHXSdjX7jpkxTf2ipfA2");
        notifyDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxID_notifyAdmin = dataSnapshot.getChildrenCount();
                Log.d("KEYQQ", "maxId notify " + maxID_notifyAdmin);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void upNewNofify(House house) {
        notifyNew.setNotify_id("notify00" + Long.valueOf(maxID_notifyAdmin +1));
        notifyNew.setHouse_id(house.getRoom_id());
        notifyNew.setOwner_id(house.getOwner_id());
        notifyNew.setType(2);

        Calendar calendar = Calendar.getInstance();
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        notifyNew.setTime(hour + ":" + minute + " " + date);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference notifyAdmin = FirebaseDatabase.getInstance().getReference()
                .child("notify").child("odVJNPmzGHXSdjX7jpkxTf2ipfA2")
                .child(notifyNew.getNotify_id().replace("notify00", ""));
        notifyAdmin.setValue(notifyNew);
    }

    void editConfirmation(House houseEdit) {
        textInput_phoneNumber.getEditText().setText(houseEdit.getPhone() + "");
        textInput_titleOfThePost.getEditText().setText(houseEdit.getTitleOfTheRoom() + "");
        textInput_roomDescription.getEditText().setText(houseEdit.getRoomDescription() + "");
    }
}
