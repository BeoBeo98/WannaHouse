package com.example.wannahouse.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Adapter.HouseAdapter;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Class_Java.HouseViewModel;
import com.example.wannahouse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import static com.example.wannahouse.Activity.LoginActivity.valueEventListener;

public class ExploreActivity extends AppCompatActivity {

    private GridView gridViewHouse;
    private HouseAdapter houseAdapter;
    private TextView numberHouse;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_activity);

        Query query = FirebaseDatabase.getInstance().getReference().child("house")
                .orderByChild("verify").equalTo(true);
        query.addValueEventListener(valueEventListener);

        gridViewHouse = findViewById(R.id.gridView_house);
        houseAdapter = new HouseAdapter( Data.liveDataHouse.getValue() , this);
        gridViewHouse.setAdapter(houseAdapter);

        numberHouse = findViewById(R.id.numberHouse);
        numberHouse.setText( Data.liveDataHouse.getValue().size() + "" );

        Data.liveDataHouse.observe(this, new Observer<ArrayList<House>>() {
            @Override
            public void onChanged(ArrayList<House> houses) {
                houseAdapter.notifyDataSetChanged();
                numberHouse.setText( Data.liveDataHouse.getValue().size() + "" );
            }
        });

        gridViewHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( ExploreActivity.this , HouseDetailsActivity.class);
                intent.putExtra("Position_", Data.liveDataHouse.getValue().get(position));
                startActivity(intent);
            }
        });

        setGridViewHeightBasedOnChildren(houseAdapter, gridViewHouse);
        gridViewHouse.setFocusable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setGridViewHeightBasedOnChildren(HouseAdapter matchAdapter, GridView gridView) {

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
        params.height = totalHeight + (gridView.getVerticalSpacing() * matchAdapter.getCount());
        gridView.setLayoutParams(params);
    }
}