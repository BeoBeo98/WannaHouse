package com.example.wannahouse.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Activity.ExploreActivity;
import com.example.wannahouse.Activity.ListYourSpaceActivity;
import com.example.wannahouse.Activity.MainActivity;
import com.example.wannahouse.Adapter.DistrictAdapter;
import com.example.wannahouse.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.wannahouse.Activity.MainActivity.cityName;

public class FragmentHome extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;

    ViewGroup viewGroup_listYourSpace;
    Button button_explore;

    DistrictAdapter districtAdapter;
    GridView gridView_district;

    ArrayList<Integer> listImage = new ArrayList<>();
    List<String> listName = new ArrayList<>();

    Spinner spinner_city;
    ArrayAdapter adapter_city;

        @SuppressLint("ResourceType")
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

        setGridViewHeightBasedOnChildren(districtAdapter, gridView_district);
        return view;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
