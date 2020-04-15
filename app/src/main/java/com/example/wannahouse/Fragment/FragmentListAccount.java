package com.example.wannahouse.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.wannahouse.Adapter.AccountAdapter;
import com.example.wannahouse.Class_Java.Account;
import com.example.wannahouse.Class_Java.Data;
import com.example.wannahouse.R;

import java.util.ArrayList;

public class FragmentListAccount extends Fragment {
    View view;
    GridView gridView;
    AccountAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_account, container, false);

        gridView = view.findViewById(R.id.gridView_account);
        adapter = new AccountAdapter( Data.liveDataOwner.getValue(), getContext());
        gridView.setAdapter(adapter);

        Data.liveDataOwner.observe(getActivity(), new Observer<ArrayList<Account>>() {
            @Override
            public void onChanged(ArrayList<Account> accounts) {
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
