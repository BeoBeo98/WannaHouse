package com.example.wannahouse.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Adapter.HouseAdapter;
import com.example.wannahouse.R;

public class ExploreActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHouse;
    private GridView gridViewHouse;
    private HouseAdapter houseAdapter;
    private TextView numberHouse;
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_activity);

        numberHouse = findViewById(R.id.numberHouse);
        numberHouse.setText( String.valueOf( Data.arrayListHouse.size() ));

        // show list house in Explore
        gridViewHouse = findViewById(R.id.gridView_house);
        houseAdapter = new HouseAdapter( Data.arrayListHouse, this);
        gridViewHouse.setAdapter(houseAdapter);
        gridViewHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent( ExploreActivity.this , HouseDetailsActivity.class);
                intent.putExtra("Position_", Data.arrayListHouse.get(position));
                startActivity(intent);
            }
        });

        setGridViewHeightBasedOnChildren(houseAdapter, gridViewHouse);
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
        totalHeight /= 2 ;
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + (gridView.getVerticalSpacing() * matchAdapter.getCount() + 200);
        gridView.setLayoutParams(params);
    }
}