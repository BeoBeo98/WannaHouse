package com.example.wannahouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.wannahouse.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> listImage;

    public ImageViewPagerAdapter(Context context, List<String> listImage) {
        this.context = context;
        this.listImage = listImage;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_detail, null);
        ImageView imageView = view.findViewById(R.id.imageDetail);
        Picasso.get().load(listImage.get(position)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
