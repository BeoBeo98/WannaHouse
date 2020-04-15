package com.example.wannahouse.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.House;
import com.example.wannahouse.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AccountAdapter extends BaseAdapter {
    ArrayList<Account> accounts;
    Context context;

    public AccountAdapter(ArrayList<Account> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderAccount viewHolder;
        if ( convertView == null ) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner, parent, false);
            viewHolder = new ViewHolderAccount();

            viewHolder.imageView = convertView.findViewById(R.id.avatarOwner);
            viewHolder.roomOwnerName = convertView.findViewById(R.id.roomOwnerName);
            viewHolder.phoneNumber = convertView.findViewById(R.id.phoneNumber);
            viewHolder.report = convertView.findViewById(R.id.report);
            viewHolder.totalRoom = convertView.findViewById(R.id.totalRoom);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolderAccount) convertView.getTag();
        }

        Picasso.get().load( accounts.get(position).getAvatar() ).into(viewHolder.imageView);
        viewHolder.roomOwnerName.setText(accounts.get(position).getName());
        viewHolder.phoneNumber.setText(accounts.get(position).getPhone());
        viewHolder.report.setText("report: " + accounts.get(position).getReport()+"");

        return convertView;
    }

    static class ViewHolderAccount{
        ImageView imageView;
        TextView roomOwnerName;
        TextView phoneNumber;
        TextView report;
        TextView totalRoom;
    }
}
