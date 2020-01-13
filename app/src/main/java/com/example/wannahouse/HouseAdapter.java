package com.example.wannahouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.ViewHolder> {
    private List<House> listHouse;
    private Context context;

    public HouseAdapter(List<House> listHouse, Context context) {
        this.listHouse = listHouse;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house_layout, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        House house = listHouse.get(position);
        Picasso.get().load( house.getImage().get(0) ).into(holder.imageView);
        holder.roomStyle.setText( String.valueOf(house.getRoomStyle()));
        holder.numberOfRoom.setText( String.valueOf((house.getNumberOfRoom())));
        holder.rentalPrice.setText( String.valueOf((house.getRentalPrice())));
        holder.address.setText( house.getHouseNumber() + ", " + house.getStreet() + ", " + house.getWard() + ", " + house.getDistrict());
        holder.titleOfTheRoom.setText( house.getTitleOfTheRoom() );
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick( int position) {
                Intent intent = new Intent(context, HouseDetailsActivity.class);
                intent.putExtra("Position_", (Serializable) Data.arrayListHouse.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.arrayListHouse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView roomStyle;
        TextView numberOfRoom;
        TextView rentalPrice;
        TextView address;
        TextView titleOfTheRoom;
        ItemClickListener mItemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            titleOfTheRoom = (TextView) itemView.findViewById(R.id.titleOfThePost);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            roomStyle = (TextView) itemView.findViewById(R.id.roomStyle);
            numberOfRoom = (TextView) itemView.findViewById(R.id.numberOfRoom);
            rentalPrice = (TextView) itemView.findViewById(R.id.rentalPrice);
            address = (TextView) itemView.findViewById(R.id.address);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener( ItemClickListener itemClickListener) {
            mItemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick( getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
