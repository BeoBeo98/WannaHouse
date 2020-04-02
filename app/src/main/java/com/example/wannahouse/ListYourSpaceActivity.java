package com.example.wannahouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.wannahouse.FragmentAmenities.PICK_IMAGE_REQUEST_CODE;

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

    private TextInputEditText editText_ward;
    private TextInputEditText editText_streetName;
    private TextInputEditText editText_houseNumber;

    public static House houseNew;

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
        fragmentTransaction.replace(R.id.lysContent, fragment, "FRAGMENT_TAG");
        fragmentTransaction.commit();
    }

    public void next11(View v) {
        addFragment(lysAddress);
    }

    public void next22(View v) {
        addFragment(lysAmenities);
    }

    public void next33(View v) {
        addFragment(lysConfirmation);
    }

    public void next44(View v) {

    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        textView_city = findViewById(R.id.textView_city);
        textView_district = findViewById(R.id.textView_district);
//        String[] listTemp = getResources().getStringArray(R.array.choose_city);
//        Log.d("LISTAABB", listTemp.equals(list) ? "True":"False" );
        if ("Hà Nội".equals(list[0])) {
            textView_city.setText(list[position]);
        } else {
            textView_district.setText(list[position]);
        }
    }


    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("KEYAA", "done" + resultCode + " " + requestCode);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST_CODE) {
            Log.d("KEYAA", "123");
            FragmentManager fm = getSupportFragmentManager();
            FragmentAmenities fragment = (FragmentAmenities) fm.findFragmentByTag("FRAGMENT_TAG");
            fragment.showImageInGridView(data);
        }
    }
}