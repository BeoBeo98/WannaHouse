package com.example.wannahouse.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Activity.ListYourSpaceActivity;
import com.example.wannahouse.R;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;

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
    private TextInputLayout textInput_internet;
    private TextInputLayout textInput_parking;

    private RadioGroup radioGroup_roomStyle;
    private RadioGroup radioGroup_gender;

    private CheckBox checkBox_free_electricity;
    private CheckBox checkBox_free_water;
    private CheckBox checkBox_free_internet;
    private CheckBox checkBox_free_parking;
    private CheckBox checkBox_internet;
    private CheckBox checkBox_parking;

    private ViewGroup viewGroup_internet;
    private ViewGroup viewGroup_parking;

    private Button button_next1;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_information, container, false);
        textInput_numberOfRoom = view.findViewById(R.id.text_input_numberOfRoom);
        textInput_capacity = view.findViewById(R.id.text_input_capacity);
        textInput_roomArea = view.findViewById(R.id.text_input_roomArea);
        textInput_rentalPrice = view.findViewById(R.id.text_input_price);
        textInput_deposit = view.findViewById(R.id.text_input_deposit);
        textInput_electricityCost = view.findViewById(R.id.text_input_electricityCost);
        textInput_waterCost = view.findViewById(R.id.text_input_waterCost);
        textInput_internet = view.findViewById(R.id.text_input_internetCost);
        textInput_parking = view.findViewById(R.id.text_input_parkingCost);

        textInput_roomStyle = view.findViewById(R.id.text_input_roomStyle);
        textInput_gender = view.findViewById(R.id.text_input_gender);
        radioGroup_roomStyle = view.findViewById(R.id.radioGroup_roomStyle);
        radioGroup_gender = view.findViewById(R.id.radioGroup_gender);

        checkBox_free_electricity = view.findViewById(R.id.checkbox_free_electricity);
        checkBox_free_water = view.findViewById(R.id.checkbox_free_water);
        checkBox_free_internet = view.findViewById(R.id.checkbox_free_internet);
        checkBox_free_parking = view.findViewById(R.id.checkbox_free_parking);
        checkBox_internet = view.findViewById(R.id.checkbox_internet);
        checkBox_parking = view.findViewById(R.id.checkbox_parking);

        viewGroup_internet = view.findViewById(R.id.viewGroup_internet);
        viewGroup_parking = view.findViewById(R.id.viewGroup_parking);

        isClickCheckBox();

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
        String capacity = textInput_capacity.getEditText().getText().toString().trim();
        if (capacity.isEmpty()) {
            textInput_capacity.setError("Please enter the number of unit/address");
            textInput_capacity.setHintEnabled(false);
            return false;
        } else {
            textInput_capacity.setError(null);
            return true;
        }
    }

    private boolean validate_roomArea() {

        String roomArea = textInput_roomArea.getEditText().getText().toString().trim();
        if (roomArea.isEmpty()) {
            textInput_roomArea.setError("Please enter the room area");
            textInput_roomArea.setHintEnabled(false);
            return false;
        } else {
            textInput_roomArea.setError(null);
            return true;
        }
    }

    private boolean validate_rentalPrice() {
        String rentalPrice = textInput_rentalPrice.getEditText().getText().toString().trim();
        if (rentalPrice.isEmpty()) {
            textInput_rentalPrice.setError("Please enter the rental price");
            textInput_rentalPrice.setHintEnabled(false);
            return false;
        } else {
            textInput_rentalPrice.setError(null);
            return true;
        }
    }

    private boolean validate_deposit() {

        String deposit = textInput_deposit.getEditText().getText().toString().trim();
        if (deposit.isEmpty()) {
            textInput_deposit.setError("Please enter the deposit");
            textInput_deposit.setHintEnabled(false);
            return false;
        } else {
            textInput_deposit.setError(null);
            return true;
        }
    }

    private boolean validate_electricityCost() {

        String electricityCost = textInput_electricityCost.getEditText().getText().toString().trim();
        if (electricityCost.isEmpty()) {
            textInput_electricityCost.setError("Please enter the electricity cost");
            textInput_electricityCost.setHintEnabled(false);
            return false;
        } else {
            textInput_electricityCost.setError(null);
            return true;
        }
    }

    private boolean validate_waterCost() {
        String waterCost = textInput_waterCost.getEditText().getText().toString().trim();
        if (waterCost.isEmpty()) {
            textInput_waterCost.setError("Please enter the water cost");
            textInput_waterCost.setHintEnabled(false);
            return false;
        } else {
            textInput_waterCost.setError(null);
            return true;
        }
    }

    private  boolean validate_internet() {
        if( checkBox_internet.isChecked() == false ) return true;
        else {
            String internet = textInput_internet.getEditText().getText().toString().trim();
            if( internet.isEmpty() ) {
                textInput_internet.setError("Please enter the water cost");
                textInput_internet.setHintEnabled(false);
                return false;
            } else {
                textInput_internet.setError(null);
                return true;
            }
        }
    }

    private  boolean validate_parking() {
        if( checkBox_parking.isChecked() == false ) return true;
        else {
            String internet = textInput_parking.getEditText().getText().toString().trim();
            if( internet.isEmpty() ) {
                textInput_parking.setError("Please enter the water cost");
                textInput_parking.setHintEnabled(false);
                return false;
            } else {
                textInput_parking.setError(null);
                return true;
            }
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
                !validate_waterCost() | !validate_roomStyle() | !validate_gender() |
                !validate_internet() | !validate_parking() ) return;
        else {
            ((ListYourSpaceActivity) getActivity()).next11(v);
            savingData();
        }
    }

    private void isClickCheckBox() {
        checkBox_free_electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkBox_free_electricity.isChecked() ) {
                    textInput_electricityCost.getEditText().setText("0");
                }
                else {
                    textInput_electricityCost.getEditText().setText(null);
                }
            }
        });

        checkBox_free_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkBox_free_water.isChecked() ) {
                    textInput_waterCost.getEditText().setText("0");
                }
                else {
                    textInput_waterCost.getEditText().setText(null);
                }
            }
        });

        checkBox_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkBox_internet.isChecked() ) {
                    viewGroup_internet.setVisibility(View.VISIBLE);
                }
                else {
                    viewGroup_internet.setVisibility(View.GONE);
                }
            }
        });

        checkBox_free_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkBox_free_internet.isChecked() ) {
                    textInput_internet.getEditText().setText("0");
                }
                else {
                    textInput_internet.getEditText().setText(null);
                }
            }
        });

        checkBox_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkBox_parking.isChecked() ) {
                    viewGroup_parking.setVisibility(View.VISIBLE);
                }
                else {
                    viewGroup_parking.setVisibility(View.GONE);
                }
            }
        });

        checkBox_free_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( checkBox_free_parking.isChecked() ) {
                    textInput_parking.getEditText().setText("0");
                }
                else {
                    textInput_parking.getEditText().setText(null);
                }
            }
        });
    }

    private void savingData() {
        Log.d("AAA", "" + radioGroup_roomStyle.getCheckedRadioButtonId());

        int selected = radioGroup_roomStyle.getCheckedRadioButtonId();
        RadioButton radioButton_roomStyle = (RadioButton) view.findViewById(selected);
        houseNew.setRoomStyle(radioButton_roomStyle.getText().toString());
        radioButton_roomStyle.setChecked(true);

        houseNew.setNumberOfRoom(Integer.valueOf(textInput_numberOfRoom.getEditText().getText().toString().trim()));
        houseNew.setCapacity( Integer.valueOf(textInput_capacity.getEditText().getText().toString()) );

        radioGroup_gender.check( radioGroup_gender.getCheckedRadioButtonId() );
        int selected2 = radioGroup_roomStyle.getCheckedRadioButtonId();

        switch (selected2) {
            case 1: houseNew.setGender(0);
            case 2: houseNew.setGender(1);
            case 3: houseNew.setGender(-1);
        }

        houseNew.setRoomArea( Integer.valueOf(textInput_roomArea.getEditText().getText().toString().trim()) );
        houseNew.setRentalPrice( Integer.valueOf(textInput_rentalPrice.getEditText().getText().toString().trim()) );
        houseNew.setDeposit( Integer.valueOf(textInput_deposit.getEditText().getText().toString().trim()) );
        houseNew.setElectricityCost( Integer.valueOf(textInput_electricityCost.getEditText().getText().toString().trim()) );
        houseNew.setWaterCost( Integer.valueOf(textInput_waterCost.getEditText().getText().toString().trim()) );

        if( checkBox_internet.isChecked() ) {
            houseNew.setInternet(true);
            houseNew.setInternetCost( Integer.valueOf(textInput_internet.getEditText().getText().toString().trim()) );
        }
        else {
            houseNew.setInternet(false);
            houseNew.setInternetCost(0);
        }

        if( checkBox_parking.isChecked() ) {
            houseNew.setParkingLot(true);
            houseNew.setParkingCost( Integer.valueOf(textInput_parking.getEditText().getText().toString().trim()) );
        }
        else {
            houseNew.setParkingLot(false);
            houseNew.setParkingCost(0);
        }
        Log.d("AAA", "" + radioGroup_roomStyle.getCheckedRadioButtonId() + "" + houseNew.toString());
    }
}
