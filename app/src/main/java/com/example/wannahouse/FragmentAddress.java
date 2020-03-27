package com.example.wannahouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class FragmentAddress extends Fragment {
    private ViewGroup viewGroup_district;
    private ViewGroup viewGroup_city;
    public boolean isChooseCity = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        viewGroup_city = view.findViewById(R.id.viewGroup_city);
        viewGroup_district = view.findViewById(R.id.viewGroup_district);

        viewGroup_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog = new SingleChoiceDialog(R.array.choose_city);
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getFragmentManager(), "Choose City");
                isChooseCity = true;
            }
        });

        viewGroup_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment singleChoiceDialog = new SingleChoiceDialog(R.array.choose_district_HaNoi);
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getFragmentManager(), "Choose District");
            }
        });
        return view;
    }
}
