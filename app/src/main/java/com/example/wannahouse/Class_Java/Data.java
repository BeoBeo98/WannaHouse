package com.example.wannahouse.Class_Java;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static ArrayList<House> arrayListHouse = new ArrayList<>();
    public static ArrayList<Account> arrayOwner = new ArrayList<>();
    public static MutableLiveData<ArrayList<House>> liveDataHouse = new MutableLiveData<>();
    public static MutableLiveData<ArrayList<Account>> liveDataOwner = new MutableLiveData<>();
    public static int index = 0;
    public static String ADMIN = "odVJNPmzGHXSdjX7jpkxTf2ipfA2";
}
