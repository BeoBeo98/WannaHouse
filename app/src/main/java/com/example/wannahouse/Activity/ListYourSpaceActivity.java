package com.example.wannahouse.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Fragment.FragmentAddress;
import com.example.wannahouse.Fragment.FragmentAmenities;
import com.example.wannahouse.Fragment.FragmentConfirmation;
import com.example.wannahouse.Fragment.FragmentInformation;
import com.example.wannahouse.R;
import com.example.wannahouse.Dialog.SingleChoiceDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.wannahouse.Fragment.FragmentAmenities.CAMERA_REQUEST_CODE;
import static com.example.wannahouse.Fragment.FragmentAmenities.PICK_IMAGE_REQUEST_CODE;
import static com.example.wannahouse.Fragment.FragmentAmenities.UPLOAD_IMAGE_REQUEST_CODE;
import static com.example.wannahouse.Fragment.FragmentAmenities.photoPath;

public class ListYourSpaceActivity extends AppCompatActivity implements SingleChoiceDialog.SingleChoiceListener {

    private ViewGroup lysInformation;
    private ViewGroup lysAddress;
    private ViewGroup lysAmenities;
    private ViewGroup lysConfirmation;
    FragmentInformation information = new FragmentInformation();
    FragmentAddress address = new FragmentAddress();
    FragmentAmenities amenities = new FragmentAmenities();
    FragmentConfirmation confirmation = new FragmentConfirmation();

    private TextView textView_city;
    private TextView textView_district;

    public static House houseNew = new House();
    public static StorageReference foder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_your_space);

        mapping();
        addFragment(lysInformation);

        foder = FirebaseStorage.getInstance().getReference().child("ImageHouse");
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
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
            textView_district.setText(null);
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

        if (resultCode == RESULT_OK) {
//                Log.d("KEYAA", "done" + resultCode + " " + requestCode + " " + data.toString());
            Log.d("KEYAA", "123");
            FragmentManager fm = getSupportFragmentManager();
            FragmentAmenities fragment = (FragmentAmenities) fm.findFragmentByTag("FRAGMENT_TAG");
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                ArrayList<Uri> arrayList = data.getParcelableArrayListExtra("LISTURI");
                Log.d("KEYAA", arrayList.toString() + " " + arrayList.size());
                fragment.uploadImageToCloud(arrayList);
            }
            else if (requestCode == CAMERA_REQUEST_CODE) {
                ArrayList<Uri> arrayList = new ArrayList<>();
                arrayList.add(photoPath);
                fragment.uploadImageToCloud(arrayList);
            }
            fragment.showImageInGridView(data);
        }
        Log.d("KEYAA", "temp");
    }
}