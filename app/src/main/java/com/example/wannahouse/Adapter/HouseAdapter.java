package com.example.wannahouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HouseAdapter extends BaseAdapter {

    private List<House> listHouse;
    private Context context;

    public HouseAdapter(List<House> listHouse, Context context) {
        this.listHouse = listHouse;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listHouse.size();
    }

    @Override
    public Object getItem(int position) {
        return listHouse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if ( convertView == null ) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_house_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleOfTheRoom = convertView.findViewById(R.id.titleOfThePost);
            viewHolder.imageView = convertView.findViewById(R.id.image);
            viewHolder.roomStyle = convertView.findViewById(R.id.roomStyle);
            viewHolder.capacity = convertView.findViewById(R.id.capacity);
            viewHolder.rentalPrice = convertView.findViewById(R.id.rentalPrice);
            viewHolder.address = convertView.findViewById(R.id.address);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        House house = listHouse.get(position);
        Picasso.get().load( house.getImage().get(0) ).into(viewHolder.imageView);
        viewHolder.roomStyle.setText( String.valueOf(house.getRoomStyle()));
        String gender = "";
        switch (house.getGender()) {
            case 1: gender = " ♂"; break;
            case 0: gender = " ♂/♀"; break;
            case -1: gender = " ♀"; break;
        }
        viewHolder.capacity.setText(house.getCapacity() + gender );
        viewHolder.rentalPrice.setText( String.valueOf((house.getRentalPrice())));
        viewHolder.address.setText( house.getHouseNumber() + ", " + house.getStreet() + ", " + house.getWard() + ", " + house.getDistrict());
        viewHolder.titleOfTheRoom.setText( house.getTitleOfTheRoom() );

        return convertView;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView roomStyle;
        TextView capacity;
        TextView rentalPrice;
        TextView address;
        TextView titleOfTheRoom;
    }
}
