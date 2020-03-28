package com.example.wannahouse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ListYourSpaceActivity extends AppCompatActivity implements SingleChoiceDialog.SingleChoiceListener {

    private ViewGroup lysInformation;
    private ViewGroup lysAddress;
    private ViewGroup lysAmenities;
    private ViewGroup lysConfirmation;
    FragmentInformation information = new FragmentInformation();
    FragmentAddress address = new FragmentAddress();
    FragmentAmenities amenities = new FragmentAmenities();
    FragmentConfirmation confirmation = new FragmentConfirmation();

    private TextInputLayout textInput_numberOfRoom;
    private TextInputLayout textInput_capacity;
    private TextInputLayout textInput_roomArea;
    private TextInputLayout textInput_rentalPrice;
    private TextInputLayout textInput_deposit;
    private TextInputLayout textInput_electricityCost;
    private TextInputLayout textInput_waterCost;
    private TextInputLayout textInput_city;
    private TextInputLayout textInput_district;
    private TextInputLayout textInput_ward;
    private TextInputLayout textInput_streetName;
    private TextInputLayout textInput_houseNumber;


    private RadioGroup radioGroup_roomStyle;
    private RadioGroup radioGroup_gender;

    private TextInputLayout textInput_roomStyle;
    private TextInputLayout textInput_gender;

    private TextView textView_city;
    private TextView textView_district;

    private TextInputEditText editText_numberOfRoom;
    private TextInputEditText editText_capacity;
    private TextInputEditText editText_roomArea;
    private TextInputEditText editText_rentalPrice;
    private TextInputEditText editText_deposit;
    private TextInputEditText editText_electricityCost;
    private TextInputEditText editText_waterCost;
    private boolean internet;
    private TextInputEditText editText_internetCost;
    private boolean parkingLot;
    private TextInputEditText editText_parkingCost;

    private House newHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_your_space);

        mapping();
        addFragment(lysInformation);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void mapping() {
        lysInformation = findViewById(R.id.lysInformation);
        lysAddress = findViewById(R.id.lysAddress);
        lysAmenities = findViewById(R.id.lysAmenities);
        lysConfirmation = findViewById(R.id.lysConfirmation);
    }

    public void addFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.lysInformation:
                fragment = information;
                break;
            case R.id.lysAddress:
                fragment = address;
                break;
            case R.id.lysAmenities:
                fragment = amenities;
                break;
            case R.id.lysConfirmation:
                fragment = confirmation;
                break;
        }
        fragmentTransaction.replace(R.id.lysContent, fragment);
        fragmentTransaction.commit();
    }

    private boolean validate_numberOfRoom() {
        textInput_numberOfRoom = findViewById(R.id.text_input_numberOfRoom);
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
        textInput_capacity = findViewById(R.id.text_input_capacity);
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
        textInput_roomArea = findViewById(R.id.text_input_roomArea);
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
        textInput_rentalPrice = findViewById(R.id.text_input_price);
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
        textInput_deposit = findViewById(R.id.text_input_deposit);
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
        textInput_electricityCost = findViewById(R.id.text_input_electricityCost);
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
        textInput_waterCost = findViewById(R.id.text_input_waterCost);
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
        textInput_roomStyle = findViewById(R.id.text_input_roomStyle);
        radioGroup_roomStyle = findViewById(R.id.radioGroup_roomStyle);
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
        textInput_gender = findViewById(R.id.text_input_gender);
        radioGroup_gender = findViewById(R.id.radioGroup_gender);
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
            radioGroup_roomStyle = findViewById(R.id.radioGroup_roomStyle);
            editText_numberOfRoom = findViewById(R.id.editText_numberOfRoom);
            editText_capacity = findViewById(R.id.editText_capacity);
            radioGroup_gender = findViewById(R.id.radioGroup_gender);
            editText_rentalPrice = findViewById(R.id.editText_rentalPrice);
            editText_deposit = findViewById(R.id.editText_deposit);
            editText_electricityCost = findViewById(R.id.editText_electricityCost);
            editText_waterCost = findViewById(R.id.editText_waterCost);
            editText_internetCost = findViewById(R.id.editText_internetCost);
            editText_parkingCost = findViewById(R.id.editText_parkingCost);

            radioGroup_roomStyle.check( radioGroup_roomStyle.getCheckedRadioButtonId() );
            radioGroup_gender.check( radioGroup_gender.getCheckedRadioButtonId() );
            addFragment(lysAddress);
        }
    }

    private boolean validate_city() {
        textInput_city = findViewById(R.id.text_input_city);
        textView_city = findViewById(R.id.textView_city);
        String city = textView_city.getText().toString().trim();
        Log.d("KEYAABB", city);
        if ( city.isEmpty() ) {
            textInput_city.setError("Press to choose your city");
            return false;
        } else {
            textInput_city.setError(null);
            return true;
        }
    }

    private boolean validate_district() {
        textInput_district = findViewById(R.id.text_input_district);
        textView_district = findViewById(R.id.textView_district);
        String district = textView_district.getText().toString().trim();
        //city = "safsd";
        Log.d("KEYAABB", district);
        if ( district.isEmpty() ) {
            textInput_district.setError("Press to choose your district");
            return false;
        } else {
            textInput_district.setError(null);
            return true;
        }
    }

    private boolean validate_ward() {
        textInput_ward = findViewById(R.id.text_input_ward);
        String ward = textInput_ward.getEditText().getText().toString().trim();
        if (ward.isEmpty()) {
            textInput_ward.setError("Please enter your ward");
            textInput_ward.setHintEnabled(false);
            return false;
        } else {
            textInput_ward.setError(null);
            return true;
        }
    }

    private boolean validate_streetName() {
        textInput_streetName = findViewById(R.id.text_input_streetName);
        String streetName = textInput_streetName.getEditText().getText().toString().trim();
        if (streetName.isEmpty()) {
            textInput_streetName.setError("Please enter your ward");
            textInput_streetName.setHintEnabled(false);
            return false;
        } else {
            textInput_streetName.setError(null);
            return true;
        }
    }

    private boolean validate_houseNumber() {
        textInput_houseNumber = findViewById(R.id.text_input_houseNumber);
        String houseNumber = textInput_houseNumber.getEditText().getText().toString().trim();
        if (houseNumber.isEmpty()) {
            textInput_houseNumber.setError("Please enter your ward");
            textInput_houseNumber.setHintEnabled(false);
            return false;
        } else {
            textInput_houseNumber.setError(null);
            return true;
        }
    }

    public void next2(View v) {
        if( !validate_city() | !validate_district() | !validate_ward() |
                !validate_streetName() | !validate_houseNumber() ) return;
        else {
            addFragment(lysAmenities);
        }
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        textView_city = findViewById(R.id.textView_city);
        textView_district = findViewById(R.id.textView_district);
//        String[] listTemp = getResources().getStringArray(R.array.choose_city);
//        Log.d("LISTAABB", listTemp.equals(list) ? "True":"False" );
        if( "Hà Nội".equals(list[0])) {
            textView_city.setText(list[position]);
        }
        else {
            textView_district.setText(list[position]);
        }
    }


    @Override
    public void onNegativeButtonClicked() {

    }
}