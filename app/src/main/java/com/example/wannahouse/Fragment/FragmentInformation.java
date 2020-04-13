package com.example.wannahouse.Fragment;


import android.annotation.SuppressLint;
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
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Fragment.FragmentAmenities.alpha;
import static com.example.wannahouse.Fragment.FragmentAmenities.internet;
import static com.example.wannahouse.Fragment.FragmentAmenities.parking;

public class FragmentInformation extends Fragment {
    TextInputLayout textInput_numberOfRoom;
    TextInputLayout textInput_capacity;
    TextInputLayout textInput_roomArea;
    TextInputLayout textInput_rentalPrice;
    TextInputLayout textInput_deposit;
    TextInputLayout textInput_electricityCost;
    TextInputLayout textInput_waterCost;
    TextInputLayout textInput_roomStyle;
    TextInputLayout textInput_gender;
    TextInputLayout textInput_internet;
    TextInputLayout textInput_parking;

    RadioGroup radioGroup_roomStyle;
    RadioGroup radioGroup_gender;

    CheckBox checkBox_free_electricity;
    CheckBox checkBox_free_water;
    CheckBox checkBox_free_internet;
    CheckBox checkBox_free_parking;
    CheckBox checkBox_internet;
    CheckBox checkBox_parking;

    ViewGroup viewGroup_internet;
    ViewGroup viewGroup_parking;

    Button button_next1;

    float value = (float) 0.15;
    View view;
    public static House houseEdit;

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

        button_next1 = view.findViewById(R.id.next1);
        button_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next1(v);
            }
        });

        houseEdit = (House) getActivity().getIntent().getSerializableExtra("Position_edit");
        if (houseEdit != null) {
            Log.d("QWE", "Result " + houseEdit.getRoom_id());
            editInfomation(houseEdit);
        }

        isClickCheckBox();
        return view;
    }

    boolean validate_numberOfRoom() {
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

    boolean validate_capacity() {
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

    boolean validate_roomArea() {

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

    boolean validate_rentalPrice() {
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

    boolean validate_electricityCost() {

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

    boolean validate_waterCost() {
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

    boolean validate_internet() {
        if (checkBox_internet.isChecked() == false) return true;
        else {
            String internet = textInput_internet.getEditText().getText().toString().trim();
            if (internet.isEmpty()) {
                textInput_internet.setError("Please enter the water cost");
                textInput_internet.setHintEnabled(false);
                return false;
            } else {
                textInput_internet.setError(null);
                return true;
            }
        }
    }

    boolean validate_parking() {
        if (checkBox_parking.isChecked() == false) return true;
        else {
            String internet = textInput_parking.getEditText().getText().toString().trim();
            if (internet.isEmpty()) {
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
            radioGroup_roomStyle.check(radioGroup_roomStyle.getCheckedRadioButtonId());
            // one of the radio buttons is checked
            textInput_roomStyle.setError(null);
            return true;
        }
    }

    boolean validate_gender() {
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
                !validate_internet() | !validate_parking()) return;
        else {
            if (houseEdit == null) {
                savingData(houseNew);
            } else {
                savingData(houseEdit);
            }
            ((ListYourSpaceActivity) getActivity()).next11(v);
        }
    }

    void isClickCheckBox() {
        checkBox_free_electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_free_electricity.isChecked()) {
                    textInput_electricityCost.getEditText().setText("0");
                } else {
                    textInput_electricityCost.getEditText().setText(null);
                }
            }
        });

        checkBox_free_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_free_water.isChecked()) {
                    textInput_waterCost.getEditText().setText("0");
                } else {
                    textInput_waterCost.getEditText().setText(null);
                }
            }
        });

        checkBox_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_internet.isChecked()) {
                    viewGroup_internet.setVisibility(View.VISIBLE);
                    internet.setAlpha(1);
                } else {
                    internet.setAlpha(value);
                    viewGroup_internet.setVisibility(View.GONE);
                }
            }
        });

        if (checkBox_internet.isChecked()) {
            viewGroup_internet.setVisibility(View.VISIBLE);
        } else {
            viewGroup_internet.setVisibility(View.GONE);
        }

        checkBox_free_internet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_free_internet.isChecked()) {
                    textInput_internet.getEditText().setText("0");
                } else {
                    textInput_internet.getEditText().setText(null);
                }
            }
        });

        checkBox_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_parking.isChecked()) {
                    viewGroup_parking.setVisibility(View.VISIBLE);
                    parking.setAlpha(1);
                } else {
                    viewGroup_parking.setVisibility(View.GONE);
                    parking.setAlpha(value);
                }
            }
        });

        if (checkBox_parking.isChecked()) {
            viewGroup_parking.setVisibility(View.VISIBLE);
        } else {
            viewGroup_parking.setVisibility(View.GONE);
        }

        checkBox_free_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_free_parking.isChecked()) {
                    textInput_parking.getEditText().setText("0");
                } else {
                    textInput_parking.getEditText().setText(null);
                }
            }
        });
    }

    void savingData(House houseNew) {
        Log.d("AAA", "" + radioGroup_roomStyle.getCheckedRadioButtonId());

        int selected = radioGroup_roomStyle.getCheckedRadioButtonId();
        RadioButton radioButton_roomStyle = (RadioButton) view.findViewById(selected);
        houseNew.setRoomStyle(radioButton_roomStyle.getText().toString());
        radioButton_roomStyle.setChecked(true);

        houseNew.setNumberOfRoom(Integer.valueOf(textInput_numberOfRoom.getEditText().getText().toString().trim()));
        houseNew.setCapacity(Integer.valueOf(textInput_capacity.getEditText().getText().toString()));

        radioGroup_gender.check(radioGroup_gender.getCheckedRadioButtonId());
        int selected2 = radioGroup_roomStyle.getCheckedRadioButtonId();

        switch (selected2) {
            case 1:
                houseNew.setGender(0);
            case 2:
                houseNew.setGender(1);
            case 3:
                houseNew.setGender(-1);
        }

        houseNew.setRoomArea(Integer.valueOf(textInput_roomArea.getEditText().getText().toString().trim()));
        houseNew.setRentalPrice(Integer.valueOf(textInput_rentalPrice.getEditText().getText().toString().trim()));
        houseNew.setDeposit(Integer.valueOf(textInput_deposit.getEditText().getText().toString().trim()));
        houseNew.setElectricityCost(Float.valueOf(textInput_electricityCost.getEditText().getText().toString().trim()));
        houseNew.setWaterCost(Integer.valueOf(textInput_waterCost.getEditText().getText().toString().trim()));

        if (checkBox_internet.isChecked()) {
            houseNew.setInternet(true);
            houseNew.setInternetCost(Integer.valueOf(textInput_internet.getEditText().getText().toString().trim()));
        } else {
            houseNew.setInternet(false);
            houseNew.setInternetCost(0);
        }

        if (checkBox_parking.isChecked()) {
            houseNew.setParkingLot(true);
            houseNew.setParkingCost(Integer.valueOf(textInput_parking.getEditText().getText().toString().trim()));
        } else {
            houseNew.setParkingLot(false);
            houseNew.setParkingCost(0);
        }
        Log.d("AAA", "" + radioGroup_roomStyle.getCheckedRadioButtonId() + "" + houseNew.toString());
    }


    void editInfomation(House houseEdit) {
        RadioButton b1 = view.findViewById(R.id.rb_roomStyle1);
        RadioButton b2 = view.findViewById(R.id.rb_roomStyle2);
        RadioButton b3 = view.findViewById(R.id.rb_roomStyle3);
        RadioButton b4 = view.findViewById(R.id.rb_roomStyle4);
        RadioButton b5 = view.findViewById(R.id.rb_roomStyle5);
        switch (houseEdit.getRoomStyle()) {
            case "ROOM FOR RENT":
                b1.setChecked(true);
                break;
            case "ROOM FOR SHARE":
                b2.setChecked(true);
                break;
            case "APARTMENT":
                b3.setChecked(true);
                break;
            case "HOUSE":
                b4.setChecked(true);
                break;
            case "DORMITORY":
                b5.setChecked(true);
                break;
        }
        textInput_numberOfRoom.getEditText().setText(houseEdit.getNumberOfRoom() + "");
        textInput_capacity.getEditText().setText(houseEdit.getCapacity() + "");
        RadioButton g1 = view.findViewById(R.id.rb_gender1);
        RadioButton g2 = view.findViewById(R.id.rb_gender2);
        RadioButton g3 = view.findViewById(R.id.rb_gender3);
        switch (houseEdit.getGender()) {
            case 0:
                g1.setChecked(true);
                break;
            case 1:
                g2.setChecked(true);
                break;
            case -1:
                g3.setChecked(true);
                break;
        }
        textInput_roomArea.getEditText().setText(houseEdit.getRoomArea() + "");
        textInput_rentalPrice.getEditText().setText(houseEdit.getRentalPrice() + "");
        textInput_deposit.getEditText().setText(houseEdit.getDeposit() + "");
        textInput_electricityCost.getEditText().setText(houseEdit.getElectricityCost() + "");
        textInput_waterCost.getEditText().setText(houseEdit.getWaterCost() + "");

        if (houseEdit.getElectricityCost() == 0) checkBox_free_electricity.isChecked();
        if (houseEdit.getWaterCost() == 0) checkBox_free_water.isChecked();
        if (houseEdit.isInternet() == true) {
            checkBox_internet.setChecked(true);
            textInput_internet.getEditText().setText(houseEdit.getWaterCost() + "");
            if (houseEdit.getInternetCost() == 0) checkBox_free_internet.setChecked(true);
        }
        if (houseEdit.isParkingLot() == true) {
            checkBox_parking.setChecked(true);
            textInput_parking.getEditText().setText(houseEdit.getParkingCost() + "");
            if (houseEdit.getParkingCost() == 0) checkBox_free_parking.setChecked(true);
        }
    }
}
