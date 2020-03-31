package com.example.wannahouse;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentInformation extends Fragment {
    private TextInputLayout textInput_numberOfRoom;
    private TextInputLayout textInput_capacity;
    private TextInputLayout textInput_roomArea;
    private TextInputLayout textInput_rentalPrice;
    private TextInputLayout textInput_deposit;
    private TextInputLayout textInput_electricityCost;
    private TextInputLayout textInput_waterCost;
    private TextInputLayout textInput_roomStyle;
    private TextInputLayout textInput_gender;

    private RadioGroup radioGroup_roomStyle;
    private RadioGroup radioGroup_gender;

    private Button button_next1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        textInput_numberOfRoom = view.findViewById(R.id.text_input_numberOfRoom);
        textInput_capacity = view.findViewById(R.id.text_input_capacity);
        textInput_roomArea = view.findViewById(R.id.text_input_roomArea);
        textInput_rentalPrice = view.findViewById(R.id.text_input_price);
        textInput_deposit = view.findViewById(R.id.text_input_deposit);
        textInput_electricityCost = view.findViewById(R.id.text_input_electricityCost);
        textInput_waterCost = view.findViewById(R.id.text_input_waterCost);
        textInput_roomStyle = view.findViewById(R.id.text_input_roomStyle);
        textInput_gender = view.findViewById(R.id.text_input_gender);
        radioGroup_roomStyle = view.findViewById(R.id.radioGroup_roomStyle);
        radioGroup_gender = view.findViewById(R.id.radioGroup_gender);

        button_next1 = view.findViewById(R.id.next1);
        button_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next1(v);
            }
        });
        return view;
    }

    private boolean validate_numberOfRoom() {
        String numberOfRoom = textInput_numberOfRoom.getEditText().getText().toString().trim();
        if (numberOfRoom.isEmpty()) {
            textInput_numberOfRoom.setError("Please enter the number of unit/address");
            textInput_numberOfRoom.setHintEnabled(false);
            return false;
        } else {
            textInput_numberOfRoom.setError(null);
            return true;
        }
    }

    private boolean validate_capacity() {
        String numberOfRoom = textInput_capacity.getEditText().getText().toString().trim();
        if (numberOfRoom.isEmpty()) {
            textInput_capacity.setError("Please enter the number of unit/address");
            textInput_capacity.setHintEnabled(false);
            return false;
        } else {
            textInput_capacity.setError(null);
            return true;
        }
    }

    private boolean validate_roomArea() {

        String numberOfRoom = textInput_roomArea.getEditText().getText().toString().trim();
        if (numberOfRoom.isEmpty()) {
            textInput_roomArea.setError("Please enter the room area");
            textInput_roomArea.setHintEnabled(false);
            return false;
        } else {
            textInput_roomArea.setError(null);
            return true;
        }
    }

    private boolean validate_rentalPrice() {
        String numberOfRoom = textInput_rentalPrice.getEditText().getText().toString().trim();
        if (numberOfRoom.isEmpty()) {
            textInput_rentalPrice.setError("Please enter the rental price");
            textInput_rentalPrice.setHintEnabled(false);
            return false;
        } else {
            textInput_rentalPrice.setError(null);
            return true;
        }
    }

    private boolean validate_deposit() {

        String numberOfRoom = textInput_deposit.getEditText().getText().toString().trim();
        if (numberOfRoom.isEmpty()) {
            textInput_deposit.setError("Please enter the deposit");
            textInput_deposit.setHintEnabled(false);
            return false;
        } else {
            textInput_deposit.setError(null);
            return true;
        }
    }

    private boolean validate_electricityCost() {

        String numberOfRoom = textInput_electricityCost.getEditText().getText().toString().trim();
        if (numberOfRoom.isEmpty()) {
            textInput_electricityCost.setError("Please enter the electricity cost");
            textInput_electricityCost.setHintEnabled(false);
            return false;
        } else {
            textInput_electricityCost.setError(null);
            return true;
        }
    }

    private boolean validate_waterCost() {
        String numberOfRoom = textInput_waterCost.getEditText().getText().toString().trim();
        if (numberOfRoom.isEmpty()) {
            textInput_waterCost.setError("Please enter the water cost");
            textInput_waterCost.setHintEnabled(false);
            return false;
        } else {
            textInput_waterCost.setError(null);
            return true;
        }
    }

    private boolean validate_roomStyle() {
        if (radioGroup_roomStyle.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            textInput_roomStyle.setError("Please choose the room style");
            return false;
        } else {
            radioGroup_roomStyle.check( radioGroup_roomStyle.getCheckedRadioButtonId() );
            // one of the radio buttons is checked
            textInput_roomStyle.setError(null);
            return true;
        }
    }

    private boolean validate_gender() {
        if (radioGroup_gender.getCheckedRadioButtonId() == -1) {
            // no radio buttons are checked
            textInput_gender.setError("Please choose the gender");
            return false;
        } else {
            // one of the radio buttons is checked
            textInput_gender.setError(null);
            return true;
        }
    }

    public void next1(View v) {
        if (!validate_numberOfRoom() | !validate_capacity() | !validate_roomArea() |
                !validate_rentalPrice() | !validate_deposit() | !validate_electricityCost() |
                !validate_waterCost() | !validate_roomStyle() | !validate_gender()) return;
        else {
            ((ListYourSpaceActivity) getActivity()).next11(v);
        }
    }
}
