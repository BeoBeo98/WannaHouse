package com.example.wannahouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.wannahouse.Activity.ExploreActivity;
import com.example.wannahouse.Activity.FilterActivity;
import com.example.wannahouse.Activity.HouseDetailsActivity;
import com.example.wannahouse.Activity.ListYourSpaceActivity;
import com.example.wannahouse.Adapter.DistrictAdapter;
import com.example.wannahouse.Adapter.HouseAdapter;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.wannahouse.Activity.FilterActivity.hideSoftKeyboard;

public class FragmentHome extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;

    ViewGroup viewGroup_listYourSpace;
    ViewGroup viewGroup_nearBy;

    Button button_explore;
    Button button_filter;

    DistrictAdapter districtAdapter;
    GridView gridView_district;

    ArrayList<Integer> listImage = new ArrayList<>();
    List<String> listName = new ArrayList<>();

    Spinner spinner_city;
    ArrayAdapter adapter_city;

    HouseAdapter houseAdapter;
    GridView gridView_totalHouse;

    ArrayList<House> arraylist;
    MutableLiveData<ArrayList<House>> liveDataTotalHouse;
    int city_id = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        button_explore = view.findViewById(R.id.button_explore);
        button_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExploreActivity.class);
                startActivity(intent);
            }
        });



        button_filter = view.findViewById(R.id.button_filter);
        button_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });

        viewGroup_nearBy = view.findViewById(R.id.viewGroup_nearby);
        viewGroup_nearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });

        viewGroup_listYourSpace = view.findViewById(R.id.viewGroup_listYourSpace);
        viewGroup_listYourSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListYourSpaceActivity.class);
                getActivity().startActivity(intent);
            }
        });

        gridView_district = view.findViewById(R.id.gridView_district);

        spinner_city = view.findViewById(R.id.spinner_cityHere);

        adapter_city = ArrayAdapter.createFromResource(
                getContext(), R.array.choose_city,
                R.layout.color_spinner_layout
        );
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(adapter_city);
        spinner_city.setOnItemSelectedListener(this);

        if( spinner_city.getSelectedItemId() == 0 ) {
            listImage = new ArrayList<>();
            listImage.add(R.drawable.bactuliem);
            listImage.add(R.drawable.caugiay);
            listImage.add(R.drawable.dongda);
            listImage.add(R.drawable.hadong);
            listImage.add(R.drawable.habaitrung);
            listImage.add(R.drawable.hoangmai);
            listImage.add(R.drawable.hoankiem);
            listImage.add(R.drawable.namtuliem);
            listImage.add(R.drawable.tayho);

            listName = Arrays.asList(getResources().getStringArray(R.array.district_HaNoi));

            districtAdapter = new DistrictAdapter( listImage, listName, getActivity());
            gridView_district.setAdapter(districtAdapter);
        }
        else if( spinner_city.getSelectedItemId() == 1 ) {
            listImage.add(R.drawable.binhthanh);
            listImage.add(R.drawable.hocmon);
            listImage.add(R.drawable.quan1);
            listImage.add(R.drawable.quan2);
            listImage.add(R.drawable.quan3);
            listImage.add(R.drawable.quan4);
            listImage.add(R.drawable.quan7);
            listImage.add(R.drawable.quan10);
            listImage.add(R.drawable.thuduc);

            listName = Arrays.asList(getResources().getStringArray(R.array.district_HCM));

            districtAdapter = new DistrictAdapter( listImage, listName, getActivity());
            gridView_district.setAdapter(districtAdapter);
        }

        gridView_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( getActivity() , FilterActivity.class);
                intent.putExtra("District_Filter", listName.get(position));
                intent.putExtra("City_Filter", city_id );
                startActivity(intent);
            }
        });

        setGridViewHeightBasedOnChildren(districtAdapter, gridView_district);

        liveDataTotalHouse = new MutableLiveData<>();
        arraylist = new ArrayList<>();
        gridView_totalHouse = view.findViewById(R.id.gridView_totalHouse);

        queryHouse();
        liveDataTotalHouse.setValue(arraylist);
        houseAdapter = new HouseAdapter( liveDataTotalHouse.getValue(), getContext());
        gridView_totalHouse.setAdapter(houseAdapter);

        liveDataTotalHouse.observe(getActivity(), new Observer<ArrayList<House>>() {
            @Override
            public void onChanged(ArrayList<House> arrayList) {
                houseAdapter.notifyDataSetChanged();
            }
        });

        gridView_totalHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( getActivity() , HouseDetailsActivity.class);
                intent.putExtra("Position_", liveDataTotalHouse.getValue().get(position));
                startActivity(intent);
            }
        });

        setGridView1Column(houseAdapter,gridView_totalHouse);

        return view;
    }

    private void queryHouse() {
        arraylist.clear();
        for( House house : Data.liveDataHouse.getValue() ) {
            if( house.isVerify() && house.isPublicRoom() ) {
                arraylist.add(house);
            }
        }
        liveDataTotalHouse.postValue(arraylist);
    }

    private void setGridViewHeightBasedOnChildren(DistrictAdapter matchAdapter, GridView gridView) {

        if (matchAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(gridView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < matchAdapter.getCount(); i++) {
            view = matchAdapter.getView(i, view, gridView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        totalHeight /= 3 ;
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + (gridView.getVerticalSpacing() * matchAdapter.getCount() / 3);
        gridView.setLayoutParams(params);
    }

    public static void setGridView1Column(HouseAdapter matchAdapter, GridView gridView) {

        if (matchAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(gridView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < matchAdapter.getCount(); i++) {
            view = matchAdapter.getView(i, view, gridView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + (gridView.getVerticalSpacing() * (matchAdapter.getCount())) + 200;
        Log.d("AAAA", "height" + params.height + " space " + matchAdapter.getCount());
        gridView.setLayoutParams(params);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if( spinner_city.getSelectedItemId() == 0 ) {
            city_id = 0;

            listImage = new ArrayList<>();
            listImage.add(R.drawable.bactuliem);
            listImage.add(R.drawable.caugiay);
            listImage.add(R.drawable.dongda);
            listImage.add(R.drawable.hadong);
            listImage.add(R.drawable.habaitrung);
            listImage.add(R.drawable.hoangmai);
            listImage.add(R.drawable.hoankiem);
            listImage.add(R.drawable.namtuliem);
            listImage.add(R.drawable.tayho);

            listName = Arrays.asList(getResources().getStringArray(R.array.district_HaNoi));

            districtAdapter = new DistrictAdapter( listImage, listName, getActivity());
            gridView_district.setAdapter(districtAdapter);
        }
        else if( spinner_city.getSelectedItemId() == 1 ) {
            city_id = 1;

            listImage = new ArrayList<>();
            listImage.add(R.drawable.binhthanh);
            listImage.add(R.drawable.hocmon);
            listImage.add(R.drawable.quan1);
            listImage.add(R.drawable.quan2);
            listImage.add(R.drawable.quan3);
            listImage.add(R.drawable.quan4);
            listImage.add(R.drawable.quan7);
            listImage.add(R.drawable.quan10);
            listImage.add(R.drawable.thuduc);

            listName = Arrays.asList(getResources().getStringArray(R.array.district_HCM));

            districtAdapter = new DistrictAdapter( listImage, listName, getActivity());
            gridView_district.setAdapter(districtAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
