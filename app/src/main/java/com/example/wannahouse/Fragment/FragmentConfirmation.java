package com.example.wannahouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Activity.EditHouseActivity;
import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Activity.MainActivity.accountNew;

public class FragmentConfirmation extends Fragment {

    private TextInputLayout textInput_phoneNumber;
    private TextInputLayout textInput_titleOfThePost;
    private TextInputLayout textInput_roomDescription;

    private Button button_next4;
    private Button button_testList;

    public static int FINISH_REQUEST_CODE = 1003;
    long maxID = 0;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirmation, container, false);

        textInput_phoneNumber = view.findViewById(R.id.text_input_phoneNumber);
        textInput_titleOfThePost = view.findViewById(R.id.text_input_titleOfThePost);
        textInput_titleOfThePost.getEditText().setText( houseNew.getRoomStyle() + " " + houseNew.getWard() + " " + houseNew.getDistrict() );
        textInput_roomDescription = view.findViewById(R.id.text_input_roomDescription);
        button_next4 = view.findViewById(R.id.button_next4);

        takeTotalItem();
        button_next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next4(v);
            }
        });

        button_testList = view.findViewById(R.id.button_testList);
        button_testList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upNewOwnerToDatabase(accountNew);
                upNewHosueToDatabase(houseNew);
                Intent intent = new Intent( getActivity(), EditHouseActivity.class);
                intent.putExtra("Position_", houseNew);
                getActivity().startActivityForResult(intent, FINISH_REQUEST_CODE );
                getActivity().finish();
            }
        });

        return view;
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

    public void next4(View v) {
        if( !validate_phoneNumber() | !validate_titleOfThePost() | !validate_roomDescription() ) {
            return;
        }
        else {
            savingData();
            upNewOwnerToDatabase(accountNew);
            upNewHosueToDatabase(houseNew);
            Intent intent = new Intent( getActivity(), EditHouseActivity.class);
            intent.putExtra("Position_", houseNew);
            getActivity().startActivityForResult(intent, FINISH_REQUEST_CODE );
            getActivity().finish();
            Log.d("KEYBB", houseNew.toString());
        }
    }

    private void savingData(){
        houseNew.setRoom_id("house00" + Long.valueOf(maxID + 1));
        houseNew.setPhone(  textInput_phoneNumber.getEditText().getText().toString().trim() );
        houseNew.setTitleOfTheRoom(  textInput_titleOfThePost.getEditText().getText().toString().trim() );
        Log.d("KEYAA", houseNew.getTitleOfTheRoom());
        houseNew.setRoomDescription(  textInput_roomDescription.getEditText().getText().toString().trim() );

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        houseNew.setPostingDate( currentDate );
    }

    void upNewOwnerToDatabase(final Account account) {
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

    void upNewHosueToDatabase(final House house) {
        final DatabaseReference houseDB = FirebaseDatabase.getInstance().getReference().child("house");
        Log.d("KEYQQ", "maxId " + maxID );
        houseDB.child(maxID+1+"").setValue(house);
        Log.d("KEYQQ", "maxId " + maxID );
        houseDB.child(maxID+1+"").setValue(house);
    }

    void takeTotalItem() {
        final DatabaseReference houseDB = FirebaseDatabase.getInstance().getReference().child("house");
        houseDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxID = dataSnapshot.getChildrenCount();
                Log.d("KEYQQ", "maxId ddd " + maxID );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
