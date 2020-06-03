package com.example.wannahouse.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.Class_Java.Notify;
import com.example.wannahouse.R;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {

    public static ArrayList<House> listNotify_House = new ArrayList<>();
    public static MutableLiveData<ArrayList<House>> liveDataNotify_House = new MutableLiveData<>();

    ArrayList<Notify> arrayListNotify = new ArrayList<>();
    Context context;

    public NotificationAdapter(ArrayList<Notify> arrayListNotify, Context context) {
        this.arrayListNotify = arrayListNotify;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListNotify.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListNotify.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mapingNotify_House();
        ViewHolderNotify viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            viewHolder = new ViewHolderNotify();
            viewHolder.textView_notify = convertView.findViewById(R.id.textView_notify);
            viewHolder.image_styleNotification = convertView.findViewById(R.id.image_styleNotification);
            viewHolder.textView_reason = convertView.findViewById(R.id.textView_reason);
            viewHolder.textView_time = convertView.findViewById(R.id.textView_time);
            viewHolder.imageView_tick = convertView.findViewById(R.id.image_tick);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderNotify) convertView.getTag();
        }

        if( arrayListNotify.get(position).getType() == 2 ) {
            viewHolder.image_styleNotification.setBackground(context.getResources().getDrawable(R.drawable.home512px));
            viewHolder.textView_reason.setVisibility(View.GONE);
            viewHolder.textView_notify.setText( "just add a unit: " + liveDataNotify_House.getValue().get(position).getTitleOfTheRoom());
        }
        else if( arrayListNotify.get(position).getType() == -2 ) {
            viewHolder.image_styleNotification.setBackground(context.getResources().getDrawable(R.drawable.home512px));
            viewHolder.textView_reason.setVisibility(View.GONE);
            viewHolder.textView_notify.setText( "approved a unit: " + liveDataNotify_House.getValue().get(position).getTitleOfTheRoom());
        }
        else if( arrayListNotify.get(position).getType() == 1 ) {
            viewHolder.image_styleNotification.setBackground(context.getResources().getDrawable(R.drawable.icons8emptyflag100));
            viewHolder.textView_reason.setText( "Reason: " + arrayListNotify.get(position).getReason());
            viewHolder.textView_notify.setText( "just report a unit: " + liveDataNotify_House.getValue().get(position).getTitleOfTheRoom());
        }
        else if( arrayListNotify.get(position).getType() == -1 ) {
            viewHolder.image_styleNotification.setBackground(context.getResources().getDrawable(R.drawable.icons8emptyflag100));
            viewHolder.textView_notify.setText( "report success unit: " + liveDataNotify_House.getValue().get(position).getTitleOfTheRoom());
            viewHolder.textView_reason.setText( "Reason: " + arrayListNotify.get(position).getReason());
        }
        else if( arrayListNotify.get(position).getType() == 0 ) {
            viewHolder.image_styleNotification.setBackground(context.getResources().getDrawable(R.drawable.icons8emptyflag100));
            viewHolder.textView_notify.setText( "some one report about unit: " + liveDataNotify_House.getValue().get(position).getTitleOfTheRoom());
            viewHolder.textView_reason.setText( "Reason: " + arrayListNotify.get(position).getReason());
        }

        if( listNotify_House.get(position).isVerify() == true && arrayListNotify.get(position).getType() == 2 ) {
            viewHolder.imageView_tick.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.imageView_tick.setVisibility(View.GONE);
        }

        if( listNotify_House.get(position).isPublicRoom() == false && arrayListNotify.get(position).getType() == 1 ) {
            viewHolder.imageView_tick.setVisibility(View.VISIBLE);
        }

        viewHolder.textView_time.setText(arrayListNotify.get(position).getTime());
        Log.d("YYY", "index " + listNotify_House.size());
        return convertView;
    }

    static class ViewHolderNotify {
        ImageView image_styleNotification;
        TextView textView_notify;
        TextView textView_reason;
        TextView textView_time;
        ImageView imageView_tick;
    }

    void mapingNotify_House() {
        listNotify_House.clear();
        for( Notify notify : arrayListNotify ) {
            boolean flag = false;
            for( House house : Data.liveDataHouse.getValue() ) {
                if( notify.getHouse_id().equals(house.getRoom_id()) ) {
                    listNotify_House.add(house);
                    flag = true;
                }
            }
            if( flag == false ) {
                listNotify_House.add(new House());
            }
        }
        liveDataNotify_House.setValue(listNotify_House);
        liveDataNotify_House.postValue(listNotify_House);
    }
}
