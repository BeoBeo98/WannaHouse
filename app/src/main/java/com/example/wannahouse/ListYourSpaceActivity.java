package com.example.wannahouse;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

public class ListYourSpaceActivity extends AppCompatActivity {

    private ViewGroup lysInformation;
    private ViewGroup lysAddress;
    private ViewGroup lysAmenities;
    private ViewGroup lysConfirmation;
    FragmentInformation information = new FragmentInformation();
    FragmentAddress address = new FragmentAddress();
    FragmentAmenities amenities = new FragmentAmenities();
    FragmentConfirmation confirmation = new FragmentConfirmation();

    private TextInputLayout textInputNumberOfRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_your_space);

        mapping();
        addFragment(lysInformation);

    }

    private void mapping() {
        lysInformation = findViewById(R.id.lysInformation);
        lysAddress =  findViewById(R.id.lysAddress);
        lysAmenities = findViewById(R.id.lysAmenities);
        lysConfirmation = findViewById(R.id.lysConfirmation);
    }

    public void addFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.lysInformation: fragment = information; break;
            case R.id.lysAddress: fragment = address; break;
            case R.id.lysAmenities: fragment = amenities; break;
            case R.id.lysConfirmation: fragment = confirmation; break;
        }
        fragmentTransaction.replace(R.id.lysContent, fragment);
        fragmentTransaction.commit();
    }

    private boolean check() {
        textInputNumberOfRoom = findViewById(R.id.text_input_numberOfRoom);
        String numberOfRoom = textInputNumberOfRoom.getEditText().getText().toString().trim();
        if( numberOfRoom.isEmpty() ) {
            textInputNumberOfRoom.setError("Please enter the number of unit/address");
            return false;
        } else {
            textInputNumberOfRoom.setError(null);
            return true;
        }
    }

    public void next1( View v ) {
        if( !check() ) return;
    }
}
