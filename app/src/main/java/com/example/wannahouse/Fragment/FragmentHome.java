package com.example.wannahouse.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wannahouse.Activity.ExploreActivity;
import com.example.wannahouse.Activity.ListYourSpaceActivity;
import com.example.wannahouse.Activity.MainActivity;
import com.example.wannahouse.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.api.Authentication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.wannahouse.Activity.MainActivity.isLogin;

public class FragmentHome extends Fragment {
    View view;

    ViewGroup viewGroup_listYourSpace;
    Button button_explore;

    public FragmentHome() {
    }

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

        return view;
    }
}
