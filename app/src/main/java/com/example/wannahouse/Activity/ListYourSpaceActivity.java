package com.example.wannahouse.Activity;

import android.annotation.SuppressLint;
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
import androidx.viewpager.widget.ViewPager;

import com.example.wannahouse.Adapter.ViewPagerAdapter;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Fragment.FragmentAccount;
import com.example.wannahouse.Fragment.FragmentAddress;
import com.example.wannahouse.Fragment.FragmentAmenities;
import com.example.wannahouse.Fragment.FragmentConfirmation;
import com.example.wannahouse.Fragment.FragmentHome;
import com.example.wannahouse.Fragment.FragmentInformation;
import com.example.wannahouse.R;
import com.example.wannahouse.Dialog.SingleChoiceDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.wannahouse.Activity.EditHouseActivity.EDIT_ROOM_CODE;
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

    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_your_space);

        houseNew = new House();

        tabLayout = findViewById(R.id.tabLayout_listYourSpace);
        viewPager = findViewById(R.id.viewPager_listYourSpace);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1000);

        adapter.addFragment(information, "INFORMATION");
        adapter.addFragment(address, "ADDRESS");
        adapter.addFragment(amenities, "AMENITIES");
        adapter.addFragment(confirmation, "CONFIRMATION");
        Log.d("KEYBB", adapter.toString() + "");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        foder = FirebaseStorage.getInstance().getReference().child("ImageHouse");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void next11(View v) {
        tabLayout.getTabAt(1).select();
    }

    public void next22(View v) {
        tabLayout.getTabAt(2).select();
    }

    public void next33(View v) {
        tabLayout.getTabAt(3).select();
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

    @SuppressLint("ResourceType")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
//                Log.d("KEYAA", "done" + resultCode + " " + requestCode + " " + data.toString());
                Log.d("KEYAA", "123");
                FragmentManager fm = getSupportFragmentManager();
                FragmentAmenities fragment = (FragmentAmenities) fm.findFragmentByTag("android:switcher:" + R.id.viewPager_listYourSpace + ":" + viewPager.getCurrentItem());
                fragment.showImageInGridView(data);

                ArrayList<Uri> arrayList = data.getParcelableArrayListExtra("LISTURI");
                Log.d("KEYAA", arrayList.toString() + " " + arrayList.size());
                fragment.uploadImageToCloud(arrayList);
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d("KEYAA", "123");
                FragmentManager fm = getSupportFragmentManager();
                FragmentAmenities fragment = (FragmentAmenities) fm.findFragmentByTag("android:switcher:" + R.id.viewPager_listYourSpace + ":" + viewPager.getCurrentItem());
                fragment.showImageInGridView(data);

                ArrayList<Uri> arrayList = new ArrayList<>();
                arrayList.add(photoPath);
                fragment.uploadImageToCloud(arrayList);
            }
        }

        if (requestCode == EDIT_ROOM_CODE) {
            House houseEdit = (House) getIntent().getSerializableExtra("Position_edit");
            Log.d("TTT", "Result " + houseEdit.getRoom_id());
            RadioGroup radioGroup_roomStyle = findViewById(R.id.radioGroup_roomStyle);
            radioGroup_roomStyle.check(2);
            TextInputLayout textInput_numberOfRoom = findViewById(R.id.text_input_numberOfRoom);
            textInput_numberOfRoom.getEditText().setText(houseEdit.getNumberOfRoom());
        }
    }
}