package com.example.wannahouse.Class_Java;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wannahouse.Repository.Repo;

import java.util.ArrayList;

public class HouseViewModel extends ViewModel {
    MutableLiveData<ArrayList<House>> houses;

    public void init() {
        if( houses != null) return;
        houses = Repo.getInstance().getHouses();
    }
    public LiveData<ArrayList<House>> getLiveHouses() {
        return houses;
    }
}
