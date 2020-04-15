package com.example.wannahouse.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wannahouse.Activity.MainActivity;
import com.example.wannahouse.Adapter.ViewPagerAdapter;
import com.example.wannahouse.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.wannahouse.Activity.LoginActivity.isOwner;
import static com.example.wannahouse.Activity.MainActivity.accountNew;
import static com.example.wannahouse.Activity.MainActivity.callbackManager;
import static com.example.wannahouse.Activity.MainActivity.isLogin;


public class FragmentAccount extends Fragment {
    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static Fragment_Profile profile = new Fragment_Profile();
    public static Fragment_Approved approved = new Fragment_Approved();
    public static Fragment_Pending pending = new Fragment_Pending();


    public FragmentAccount() {
    }

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        tabLayout = view.findViewById(R.id.tabLayout_account);
        viewPager = view.findViewById(R.id.viewPager_account);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 1000);

        adapter.addFragment(profile, "PROFILE");
        adapter.addFragment(approved, "APPROVED");
        adapter.addFragment(pending, "PENDING");

        Log.d("KEYBB", adapter.getCount() + "");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

//        final View touchView = view.findViewById(R.id.viewPager_account);
//        touchView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("WWW", "kill account");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("WWW", "kill account");
    }
}
