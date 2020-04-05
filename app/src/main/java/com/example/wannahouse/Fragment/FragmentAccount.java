package com.example.wannahouse.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wannahouse.Activity.MainActivity;
import com.example.wannahouse.R;

import static com.example.wannahouse.Activity.MainActivity.accountNew;


public class FragmentAccount extends Fragment {
    View view;
    private ImageView imageView_profile;
    private TextView textView_profile;

    public FragmentAccount() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account,container,false);
        loadProfile();


        return view;
    }

    private void loadProfile() {
        imageView_profile = view.findViewById(R.id.profile_pic);
        textView_profile = view.findViewById(R.id.profile_name);

        Glide.with(this).load(accountNew.getAvatar()).into(imageView_profile);
        textView_profile.setText(accountNew.getName());
    }
}
