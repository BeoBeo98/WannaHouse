package com.example.wannahouse.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.wannahouse.Activity.ListYourSpaceActivity;
import com.example.wannahouse.Activity.LoginActivity;
import com.example.wannahouse.Activity.MainActivity;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.wannahouse.Activity.ListYourSpaceActivity.houseNew;
import static com.example.wannahouse.Activity.MainActivity.accountNew;
import static com.example.wannahouse.Activity.MainActivity.isLogin;

public class Fragment_Profile extends Fragment {

    View view;
    CircleImageView circleImageView;
    TextView txtName;
    TextView txtReport;
    TextView txtApproved;

    Button button_signOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        button_signOut = view.findViewById(R.id.button_signOut);

        txtName = view.findViewById(R.id.profile_name2);
        txtReport = view.findViewById(R.id.textView_totalReport);
        txtApproved = view.findViewById(R.id.textView_totalApproved);
        circleImageView = view.findViewById(R.id.profile_pic2);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        txtName.setText(user.getDisplayName());
        txtReport.setText("Total unit Report: " + accountNew.getReport());

        if( user.getUid().equals(Data.ADMIN) ) {
            txtApproved.setVisibility(View.GONE);
            txtReport.setVisibility(View.GONE);
        }

        int totalApproved = 0;
        for(House house : Data.arrayListHouse) {
            if( house.getOwner_id().equals(user.getUid()) && house.isVerify() == true ) {
                totalApproved++;
            }
        }

        txtApproved.setText("Total unit approved: " + totalApproved);
        Glide.with(this).load(changeImageToHighQuality(user.getPhotoUrl())).into(circleImageView);


        button_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                isLogin = false;
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    public static Uri changeImageToHighQuality(Uri photoUri) {
        //workaround to get higer res profile picture
        if (photoUri != null) {
            String str = photoUri.toString();
            if (str.contains("google")) {
                str.replace("s96-c", "s400-c");
            } else if (str.contains("facebook")) {
                str += "?type=large";
            }
            Uri result = Uri.parse(str);
            return result;
        } else return null;
    }
}