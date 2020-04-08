package com.example.wannahouse.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.wannahouse.Activity.MainActivity.accountNew;
import static com.example.wannahouse.Activity.MainActivity.callbackManager;
import static com.example.wannahouse.Activity.MainActivity.isLogin;


public class FragmentAccount extends Fragment {
    View view;

    private LoginButton loginButton;
    private CircleImageView circleImageView;
    private TextView txtName;

    public FragmentAccount() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account,container,false);

        loginButton = view.findViewById(R.id.login_button);
        txtName = view.findViewById(R.id.profile_name2);
        circleImageView = view.findViewById(R.id.profile_pic2);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile"));

        checkLoginStatus();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return view;
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {
            if(currentAccessToken==null)
            {
                txtName.setText("");
                circleImageView.setImageResource(0);
                Toast.makeText(getActivity(),"User Logged out",Toast.LENGTH_LONG).show();
                isLogin = false;
            }
            else {
                isLogin = true;
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+ "/picture?type=normal";

                    accountNew.setId(id);
                    accountNew.setName(first_name + " " + last_name);
                    accountNew.setAvatar(image_url);

                    Log.d( "KEYAA", "" + accountNew.getName());

                    txtName.setText(first_name +" "+last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(getActivity()).load(image_url).into(circleImageView);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void checkLoginStatus()
    {
        if(AccessToken.getCurrentAccessToken()!=null)
        {
            isLogin = true;
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }
}
