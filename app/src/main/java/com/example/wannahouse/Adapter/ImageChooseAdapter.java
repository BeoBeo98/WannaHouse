package com.example.wannahouse.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.wannahouse.R;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

public class ImageChooseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Bitmap> arrayList;
    public ImageChooseAdapter(Context context, ArrayList<Bitmap> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.image_choose, parent,false);

        ImageView imageView = convertView.findViewById(R.id.imageChoose);
        imageView.setImageBitmap(arrayList.get(position));
        return convertView;
    }
}
