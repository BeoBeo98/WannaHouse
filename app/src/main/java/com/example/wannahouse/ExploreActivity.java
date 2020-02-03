package com.example.wannahouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class ExploreActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHouse;
    private HouseAdapter houseAdapter;
    private TextView numberHouse;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_activity);

        numberHouse = findViewById(R.id.numberHouse);
        numberHouse.setText( String.valueOf( Data.arrayListHouse.size() ));

        // show list house in Explore
        recyclerViewHouse = findViewById(R.id.recyclerView);
        recyclerViewHouse.setLayoutManager( new GridLayoutManager(this,2));
        houseAdapter = new HouseAdapter( Data.arrayListHouse, this);
        recyclerViewHouse.setAdapter(houseAdapter);
    }

}