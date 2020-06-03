package com.example.wannahouse.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.wannahouse.Adapter.HouseAdapter;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Fragment.FragmentHome;
import com.example.wannahouse.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.example.wannahouse.Fragment.FragmentAmenities.iconViewGroupClick;
import static com.example.wannahouse.Fragment.FragmentAmenities.alpha;

public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tv_cancel;
    TextView tv_totalResult;
    TextView tv_sort;
    TextView tv_noRoomFound;

    AutoCompleteTextView autoCompleteEditText;
    ArrayAdapter<String> adapter;

    Spinner spinner_city;
    ArrayAdapter adapter_city;

    ToggleButton tg_roomStyle;
    ToggleButton tg_price;
    ToggleButton tg_amenities;
    ToggleButton tg_sort;

    ViewGroup vg_roomStyle;
    ViewGroup vg_price;
    ViewGroup vg_amenities;
    ViewGroup vg_sort;

    CrystalRangeSeekbar rangeSeekbar;
    TextView tv_minVND;
    TextView tv_maxVND;

    Button button_apply;
    int minVND = 0;
    int maxVND = 15000;

    ArrayList<House> listHouseFilter = new ArrayList<>();
    GridView gridView_filter;
    HouseAdapter adapterFilter;

    ViewGroup air;
    ViewGroup privateWC;
    ViewGroup parking;
    ViewGroup internet;
    ViewGroup security;
    ViewGroup noOwner;
    ViewGroup noCurfew;
    ViewGroup cooking;
    ViewGroup bed;
    ViewGroup window;
    ViewGroup waterHeater;
    ViewGroup washing;
    ViewGroup wardrobe;
    ViewGroup fridge;
    ViewGroup loft;
    ViewGroup television;

    RadioGroup radioGroup_sort;
    RadioGroup radioGroup_roomStye;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mapping();

        cancel();
        autocompleteText();
        spiner();
        filter();
        rangeseekbar();
        setVg_amenities();
        addHouse();
        adapterFilter = new HouseAdapter(listHouseFilter, this);
        gridView_filter.setAdapter(adapterFilter);
        gridView_filter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( FilterActivity.this , HouseDetailsActivity.class);
                intent.putExtra("Position_", listHouseFilter.get(position));
                startActivity(intent);
            }
        });
        FragmentHome.setGridView1Column(adapterFilter, gridView_filter);

        tv_totalResult.setText("Total Result: " + adapterFilter.getCount());
        RadioButton rb = findViewById(R.id.rb_latest);
        rb.setChecked(true);
        setTv_sort();
        setUpSortBy();
        setButton_apply();
        adapter.notifyDataSetChanged();

        Intent intent = getIntent();
        if( intent != null ) {
            String strDistrict = intent.getStringExtra("District_Filter");
            int temp = intent.getIntExtra("City_Filter", 0);
            Log.d("TEMP", "temp " + temp);
            spinner_city.setSelection(temp);

            autoCompleteEditText.setText(strDistrict,false);

            button_apply.callOnClick();
        }

        gridView_filter.setFocusable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void mapping() {
        tv_cancel = findViewById(R.id.tv_cancelHere);
        autoCompleteEditText = findViewById(R.id.editText_searchHere);
        spinner_city = findViewById(R.id.spinner_cityHere);
        tg_roomStyle = findViewById(R.id.tg_roomStyle);
        tg_price = findViewById(R.id.tg_price);
        tg_amenities = findViewById(R.id.tg_amenities);
        tg_sort = findViewById(R.id.tg_sort);

        vg_roomStyle = findViewById(R.id.vg_roomStyle);
        vg_price = findViewById(R.id.vg_price);
        vg_amenities = findViewById(R.id.vg_amenities);
        vg_sort = findViewById(R.id.vg_sort);
        rangeSeekbar = findViewById(R.id.rangeSeekbar);
        gridView_filter = findViewById(R.id.gridView_filter);

        tv_totalResult = findViewById(R.id.tv_totalResult);

        button_apply = findViewById(R.id.button_apply);
        radioGroup_sort = findViewById(R.id.rg_sort);
        radioGroup_roomStye = findViewById(R.id.rg_roomStyle);
        tv_sort = findViewById(R.id.tv_sort);
        tv_noRoomFound = findViewById(R.id.tv_noRoomFound);
    }

    private void addHouse() {
        listHouseFilter.clear();
        for (House house : Data.liveDataHouse.getValue()) {
            if (house.isVerify() && house.isPublicRoom()) {
                if ( minVND <= house.getRentalPrice() && house.getRentalPrice() <= maxVND ) {
                    listHouseFilter.add(house);
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        autoCompleteEditText.getText().clear();
        if (position == 0) {
            String[] address = getResources().getStringArray(R.array.address_HaNoi);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, address);
            autoCompleteEditText.setAdapter(adapter);
        } else if (position == 1) {
            String[] address = getResources().getStringArray(R.array.address_HCM);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, address);
            autoCompleteEditText.setAdapter(adapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void cancel() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void autocompleteText() {
        String[] address = getResources().getStringArray(R.array.address_HaNoi);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, address);
        autoCompleteEditText.setAdapter(adapter);

        autoCompleteEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideSoftKeyboard(FilterActivity.this);
            }
        });

        autoCompleteEditText.setThreshold(1);

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void spiner() {
        adapter_city = ArrayAdapter.createFromResource(
                this, R.array.choose_city,
                R.layout.color_spinner_layout
        );
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(adapter_city);
        spinner_city.setOnItemSelectedListener(this);
    }

    private void filter() {
        vg_roomStyle.setVisibility(View.GONE);
        vg_price.setVisibility(View.GONE);
        vg_amenities.setVisibility(View.GONE);
        vg_sort.setVisibility(View.GONE);

        tg_roomStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tg_roomStyle.isChecked()) {
                    vg_roomStyle.setVisibility(View.VISIBLE);

                    tg_price.setChecked(false);
                    tg_amenities.setChecked(false);
                    tg_sort.setChecked(false);

                    vg_price.setVisibility(View.GONE);
                    vg_amenities.setVisibility(View.GONE);
                    vg_sort.setVisibility(View.GONE);
                } else {
                    vg_roomStyle.setVisibility(View.GONE);
                }
            }
        });

        tg_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tg_price.isChecked()) {
                    vg_price.setVisibility(View.VISIBLE);

                    tg_roomStyle.setChecked(false);
                    tg_amenities.setChecked(false);
                    tg_sort.setChecked(false);

                    vg_roomStyle.setVisibility(View.GONE);
                    vg_amenities.setVisibility(View.GONE);
                    vg_sort.setVisibility(View.GONE);
                } else {
                    vg_price.setVisibility(View.GONE);
                }
            }
        });

        tg_amenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tg_amenities.isChecked()) {
                    vg_amenities.setVisibility(View.VISIBLE);

                    tg_roomStyle.setChecked(false);
                    tg_price.setChecked(false);
                    tg_sort.setChecked(false);

                    vg_roomStyle.setVisibility(View.GONE);
                    vg_price.setVisibility(View.GONE);
                    vg_sort.setVisibility(View.GONE);
                } else {
                    vg_amenities.setVisibility(View.GONE);
                }
            }
        });

        tg_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tg_sort.isChecked()) {
                    vg_sort.setVisibility(View.VISIBLE);

                    tg_roomStyle.setChecked(false);
                    tg_price.setChecked(false);
                    tg_amenities.setChecked(false);

                    vg_roomStyle.setVisibility(View.GONE);
                    vg_price.setVisibility(View.GONE);
                    vg_amenities.setVisibility(View.GONE);
                } else {
                    vg_sort.setVisibility(View.GONE);
                }
            }
        });
    }

    private void rangeseekbar() {
        tv_minVND = findViewById(R.id.minVND);
        tv_maxVND = findViewById(R.id.maxVND);

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tv_minVND.setText(minValue + " VND");
                tv_maxVND.setText(maxValue + " VND");
                minVND = Integer.parseInt(minValue.toString());
                maxVND = Integer.parseInt(maxValue.toString());
            }
        });
    }

    private void setButton_apply() {
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMoreToFilter();
                adapterFilter.notifyDataSetChanged();
                tv_totalResult.setText("Total Result: " + adapterFilter.getCount());
                setTv_sort();
                FragmentHome.setGridView1Column(adapterFilter, gridView_filter);

                if( adapterFilter.getCount() == 0 ) {
                    tv_noRoomFound.setVisibility(View.VISIBLE);
                } else {
                    tv_noRoomFound.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setVg_amenities() {
        air = findViewById(R.id.amenitiesAir);
        privateWC = findViewById(R.id.amenitiesPrivateWC);
        parking = findViewById(R.id.amenitiesParking);
        internet = findViewById(R.id.amenitiesInternet);
        security = findViewById(R.id.amenitiesSecurity);
        noOwner = findViewById(R.id.amenitiesNoOwner);
        noCurfew = findViewById(R.id.amenitiesNoCurfew);
        cooking = findViewById(R.id.amenitiesCooking);
        bed = findViewById(R.id.amenitiesBed);
        window = findViewById(R.id.amenitiesWindow);
        waterHeater = findViewById(R.id.amenitiesWaterHeater);
        washing = findViewById(R.id.amenitiesWashing);
        wardrobe = findViewById(R.id.amenitiesWardrobe);
        fridge = findViewById(R.id.amenitiesFridge);
        loft = findViewById(R.id.amenitiesLoft);
        television = findViewById(R.id.amenitiesTelevision);

        iconViewGroupClick(air);
        iconViewGroupClick(privateWC);
        iconViewGroupClick(parking);
        iconViewGroupClick(internet);
        iconViewGroupClick(security);
        iconViewGroupClick(noOwner);
        iconViewGroupClick(noCurfew);
        iconViewGroupClick(cooking);
        iconViewGroupClick(bed);
        iconViewGroupClick(window);
        iconViewGroupClick(waterHeater);
        iconViewGroupClick(washing);
        iconViewGroupClick(wardrobe);
        iconViewGroupClick(fridge);
        iconViewGroupClick(loft);
        iconViewGroupClick(television);

        air.setAlpha(alpha);
        privateWC.setAlpha(alpha);
        parking.setAlpha(alpha);
        internet.setAlpha(alpha);
        security.setAlpha(alpha);
        noOwner.setAlpha(alpha);
        noCurfew.setAlpha(alpha);
        cooking.setAlpha(alpha);
        bed.setAlpha(alpha);
        window.setAlpha(alpha);
        waterHeater.setAlpha(alpha);
        washing.setAlpha(alpha);
        wardrobe.setAlpha(alpha);
        fridge.setAlpha(alpha);
        loft.setAlpha(alpha);
        television.setAlpha(alpha);
    }

    private void setTv_sort() {
        int selected = radioGroup_sort.getCheckedRadioButtonId();
        Log.d("ZZZZ", "se " + selected);
        RadioButton radioButton_sort = (RadioButton) findViewById(selected);

        tv_sort.setText("Sort: " + radioButton_sort.getText().toString());
    }

    private void chooseMoreToFilter() {
        int selected = radioGroup_roomStye.getCheckedRadioButtonId();
        Log.d("ZZZZ", "se " + selected);
        RadioButton radioButton_roomStyle = (RadioButton) findViewById(selected);

        String strAddress = autoCompleteEditText.getText().toString().trim();

        if (selected != -1) {
            listHouseFilter.clear();
            for (House house : Data.liveDataHouse.getValue()) {
                if (house.isVerify() && house.isPublicRoom()) {
                    String strRoomStyle = radioButton_roomStyle.getText().toString().trim();
                    if (house.getRoomStyle().equals(strRoomStyle)) {
                        if (minVND <= house.getRentalPrice() && house.getRentalPrice() <= maxVND) {
                            if (strAddress.isEmpty()) {
                                listHouseFilter.add(house);
                            } else {
                                if (house.getStreet().equals(strAddress) || house.getWard().equals(strAddress) || house.getDistrict().equals(strAddress)) {
                                    listHouseFilter.add(house);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            listHouseFilter.clear();
            if (!strAddress.isEmpty()) {
                for (House house : Data.liveDataHouse.getValue()) {
                    if (house.isVerify() && house.isPublicRoom()) {
                        if (minVND <= house.getRentalPrice() && house.getRentalPrice() <= maxVND) {
                            if (house.getStreet().equals(strAddress) || house.getWard().equals(strAddress) || house.getDistrict().equals(strAddress)) {
                                listHouseFilter.add(house);
                            }
                        }
                    }
                }
            }
            if ( strAddress.isEmpty() ) {
                addHouse();
            }
        }

        filterAmenities();
        setUpSortBy();
    }

    private void filterAmenities() {
        if( air.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isAirConditioner() ) listHouseFilter.remove(i);
            }
        }

        if( privateWC.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isPrivateWC() ) listHouseFilter.remove(i);
            }
        }

        if( parking.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isParkingLot() ) listHouseFilter.remove(i);
            }
        }

        if( internet.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isInternet() ) listHouseFilter.remove(i);
            }
        }

        if( security.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isSecurity() ) listHouseFilter.remove(i);
            }
        }

        if( noOwner.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isNoOwner() ) listHouseFilter.remove(i);
            }
        }

        if( noCurfew.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isNoCurfew() ) listHouseFilter.remove(i);
            }
        }

        if( cooking.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isCook() ) listHouseFilter.remove(i);
            }
        }

        if( bed.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isBed() ) listHouseFilter.remove(i);
            }
        }

        if( window.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isWindow() ) listHouseFilter.remove(i);
            }
        }

        if( waterHeater.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isWaterHeater() ) listHouseFilter.remove(i);
            }
        }

        if( washing.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isWashing() ) listHouseFilter.remove(i);
            }
        }

        if( wardrobe.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isWardrobe() ) listHouseFilter.remove(i);
            }
        }

        if( fridge.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isFridge() ) listHouseFilter.remove(i);
            }
        }

        if( loft.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isLoft() ) listHouseFilter.remove(i);
            }
        }

        if( television.getAlpha() == 1 ) {
            for(int i = 0 ; i < listHouseFilter.size() ; i++) {
                if( !listHouseFilter.get(i).isTelevision() ) listHouseFilter.remove(i);
            }
        }
    }

    private void setUpSortBy() {
        RadioButton rb1 = findViewById(R.id.rb_latest);
        RadioButton rb2 = findViewById(R.id.rb_highToLow);
        RadioButton rb3 = findViewById(R.id.rb_lowToHigh);
        if( rb1.isChecked() ) {
            Collections.sort(listHouseFilter, new Comparator<House>() {
                @Override
                public int compare(House o1, House o2) {
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = DateFormat.getDateInstance(DateFormat.FULL).parse(o1.getPostingDate());
                        date2 = DateFormat.getDateInstance(DateFormat.FULL).parse(o2.getPostingDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date1.compareTo(date2);
                }
            });
        }
        else if( rb2.isChecked() ) {
            Collections.sort(listHouseFilter, new Comparator<House>() {
                @Override
                public int compare(House o1, House o2) {
                    return (o1.getRentalPrice() - o2.getRentalPrice());
                }
            });
        }
        else if( rb3.isChecked() ) {
            Collections.sort(listHouseFilter, new Comparator<House>() {
                @Override
                public int compare(House o1, House o2) {
                    return (o2.getRentalPrice() - o1.getRentalPrice());
                }
            });
        }
    }
}
