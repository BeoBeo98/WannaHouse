package com.example.wannahouse;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHouse;
    private HouseAdapter houseAdapter;
    private TextView numberHouse;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_activity_house);

        numberHouse = findViewById(R.id.numberHouse);
        numberHouse.setText( String.valueOf( Data.arrayListHouse.size() ));

        // show list house in Explore
        recyclerViewHouse = findViewById(R.id.recyclerView);
        recyclerViewHouse.setLayoutManager( new GridLayoutManager(this,2));
        houseAdapter = new HouseAdapter( Data.arrayListHouse, this);
        recyclerViewHouse.setAdapter(houseAdapter);
    }

    private void connectLayout() {
        LinearLayout layout1,layout2;
        Button button1;

        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);

        layout1.setVisibility(LinearLayout.VISIBLE);
        layout2.setVisibility(LinearLayout.GONE);

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            layout2.setVisibility(LinearLayout.VISIBLE);
            layout1.setVisibility(LinearLayout.GONE);
        }
    }
    }
}