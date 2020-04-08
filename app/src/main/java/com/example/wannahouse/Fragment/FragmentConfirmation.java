package com.example.wannahouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Activity.EditHouseActivity;
import com.example.wannahouse.R;
import com.google.android.material.textfield.TextInputLayout;
import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;

public class FragmentConfirmation extends Fragment {

    private TextInputLayout textInput_phoneNumber;
    private TextInputLayout textInput_titleOfThePost;
    private TextInputLayout textInput_roomDescription;

    private Button button_next4;

    public static int FINISH_REQUEST_CODE = 1003;

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

        button_next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next4(v);
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
            Intent intent = new Intent( getActivity(), EditHouseActivity.class);
            getActivity().startActivityForResult(intent, FINISH_REQUEST_CODE );
            Log.d("KEYBB", houseNew.toString());
        }
    }

    private void savingData(){
        houseNew.setPhone(  textInput_phoneNumber.getEditText().getText().toString().trim() );
        houseNew.setTitleOfTheRoom(  textInput_titleOfThePost.getEditText().getText().toString().trim() );
        houseNew.setRoomDescription(  textInput_roomDescription.getEditText().getText().toString().trim() );
    }
}
