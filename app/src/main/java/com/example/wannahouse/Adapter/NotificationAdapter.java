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

    public static ArrayList<Account> listNotify_Account = new ArrayList<>();
    public static MutableLiveData<ArrayList<House>> liveDataNotify_Account = new MutableLiveData<>();

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
            viewHolder.textView_titleHouse = convertView.findViewById(R.id.textView_titleHouse);
            viewHolder.image_styleNotification = convertView.findViewById(R.id.image_styleNotification);
            viewHolder.textView_human = convertView.findViewById(R.id.textView_human);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderNotify) convertView.getTag();
        }

        if( arrayListNotify.get(position).getType() == 1 ) {
            viewHolder.image_styleNotification.setBackground(context.getResources().getDrawable(R.drawable.home512px));
            viewHolder.textView_human.setText("just add a unit:");
            viewHolder.textView_titleHouse.setText(liveDataNotify_House.getValue().get(position).getTitleOfTheRoom());
        }
        else {
            viewHolder.image_styleNotification.setBackground(context.getResources().getDrawable(R.drawable.icons8emptyflag100));
            viewHolder.textView_human.setText("just report a unit:");
            viewHolder.textView_titleHouse.setText(liveDataNotify_House.getValue().get(position).getTitleOfTheRoom());
        }

        Log.d("YYY", "index " + listNotify_House.size());
        return convertView;
    }

    static class ViewHolderNotify {
        TextView textView_titleHouse;
        TextView textView_human;
        ImageView image_styleNotification;
    }

    void mapingNotify_House() {
        listNotify_House.clear();
        for( Notify notify : arrayListNotify ) {
            for( House house : Data.liveDataHouse.getValue() ) {
                if( notify.getHouse_id().equals(house.getRoom_id()) ) {
                    listNotify_House.add(house);
                }
            }
        }
        liveDataNotify_House.setValue(listNotify_House);
        liveDataNotify_House.postValue(listNotify_House);
    }
}
