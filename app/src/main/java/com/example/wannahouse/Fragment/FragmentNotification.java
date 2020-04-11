package com.example.wannahouse.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Adapter.HouseAdapter;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.R;

public class FragmentNotification extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView_test);

        HouseAdapter houseAdapter = new HouseAdapter(Data.arrayListHouse, getActivity());
        gridView.setAdapter(houseAdapter);

        return view;
    }
}
