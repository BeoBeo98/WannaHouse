package com.example.wannahouse.Class_Java;

import java.util.ArrayList;

public class Account {
    private String id;
    private String name;
    private String phone;
    private String avatar;
    private ArrayList<String> listNotification;
    private int report;

    public Account() {
        id = "";
        name = "";
        phone = "";
        avatar = "";
        report = 0;
    }

    public Account(int Bon4so9) {
        id = "cvafaskfasf";
        name = "DDitj mej mayu";
        phone = "9999";
        avatar = "adfhasdflpsafhnlnasdfddfb;s";
        listNotification = null;
        report = 0;
    }

    public Account(String id, String name, String phone, String avatar, ArrayList<String> listNotification, int report) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;
        this.listNotification = listNotification;
        this.report = report;
    }

    public ArrayList<String> getListNotification() {
        return listNotification;
    }

    public void setListNotification(ArrayList<String> listNotification) {
        this.listNotification = listNotification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }
}
