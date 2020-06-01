package com.example.wannahouse.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wannahouse.R;

import java.util.ArrayList;
import java.util.List;

public class DistrictAdapter extends BaseAdapter {
    List<Integer> image = new ArrayList<>();
    List<String> name = new ArrayList<>();
    Context context;

    public DistrictAdapter(List<Integer> image, List<String> name, Context context) {
        this.image = image;
        this.name = name;
        this.context = context;
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_district, parent, false);

        ImageView image_district = convertView.findViewById(R.id.image_district);
        TextView name_district = convertView.findViewById(R.id.name_district);

//        image_district.setBackground(context.getResources().getDrawable(image.get(position)));

        Drawable d = context.getResources().getDrawable(image.get(position));
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();

        image_district.setImageBitmap(bitmap);

        name_district.setText(name.get(position));

        return convertView;
    }
}
