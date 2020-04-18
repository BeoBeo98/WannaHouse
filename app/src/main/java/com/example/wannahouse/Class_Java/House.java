package com.example.wannahouse.Class_Java;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class House implements Serializable {
    private String room_id;
    private String owner_id;
    private String roomStyle;
    private int numberOfRoom;
    private int capacity;
    private int gender;
    private int roomArea;
    private int rentalPrice;
    private int deposit;
    private float electricityCost;
    private int waterCost;
    private int internetCost;
    private int parkingCost;
    private String city;
    private String district;
    private String ward;
    private String street;
    private String houseNumber;
    private String roomDescription;
    private String titleOfTheRoom;
    private ArrayList<String> image = new ArrayList<>();
    private String name;
    private String phone;
    private String avatar;
    private String postingDate;

    private boolean parkingLot;
    private boolean privateWC;
    private boolean window;
    private boolean security;
    private boolean internet;
    private boolean noCurfew;
    private boolean noOwner;
    private boolean airConditioner;
    private boolean waterHeater;
    private boolean cook;
    private boolean fridge;
    private boolean washing;
    private boolean loft;
    private boolean bed;
    private boolean wardrobe;
    private boolean television;

    private boolean verify;
    private boolean publicRoom;
    private int report;

    public House() {
        room_id= "";
        owner_id = "";
        roomStyle = "";
        numberOfRoom = 0;
        capacity = 0;
        gender = 0;
        roomArea = 0;
        rentalPrice = 0;
        deposit = 0;
        electricityCost = 0;
        waterCost = 0;
        parkingCost = 0;
        city = "";
        district = "";
        ward = "";
        street = "";
        houseNumber = "";
        roomDescription = "";
        titleOfTheRoom = "";
        image = null;
        name = "";
        phone = "";
        avatar = "";
        postingDate = "";
        parkingLot = false;
        privateWC = false;
        window = false;
        security = false;
        internet = false;
        noCurfew = false;
        noOwner = false;
        airConditioner = false;
        waterHeater = false;
        cook = false;
        fridge = false;
        washing =false;
        loft = false;
        bed = false;
        wardrobe = false;
        television = false;

        verify = false;
        publicRoom = true;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public String getRoomStyle() {
        return roomStyle;
    }

    public void setRoomStyle(String roomStyle) {
        this.roomStyle = roomStyle;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(int roomArea) {
        this.roomArea = roomArea;
    }

    public int getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(int rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public float getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(float electricityCost) {
        this.electricityCost = electricityCost;
    }

    public int getWaterCost() {
        return waterCost;
    }

    public void setWaterCost(int waterCost) {
        this.waterCost = waterCost;
    }

    public int getInternetCost() {
        return internetCost;
    }

    public void setInternetCost(int internetCost) {
        this.internetCost = internetCost;
    }

    public boolean isParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(boolean parkingLot) {
        this.parkingLot = parkingLot;
    }

    public int getParkingCost() {
        return parkingCost;
    }

    public void setParkingCost(int parkingCost) {
        this.parkingCost = parkingCost;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String getTitleOfTheRoom() {
        return titleOfTheRoom;
    }

    public void setTitleOfTheRoom(String titleOfTheRoom) {
        this.titleOfTheRoom = titleOfTheRoom;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
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

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public boolean isPrivateWC() {
        return privateWC;
    }

    public void setPrivateWC(boolean privateWC) {
        this.privateWC = privateWC;
    }

    public boolean isWindow() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window = window;
    }

    public boolean isSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }

    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public boolean isNoCurfew() {
        return noCurfew;
    }

    public void setNoCurfew(boolean noCurfew) {
        this.noCurfew = noCurfew;
    }

    public boolean isNoOwner() {
        return noOwner;
    }

    public void setNoOwner(boolean noOwner) {
        this.noOwner = noOwner;
    }

    public boolean isAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(boolean airConditioner) {
        this.airConditioner = airConditioner;
    }

    public boolean isWaterHeater() {
        return waterHeater;
    }

    public void setWaterHeater(boolean waterHeater) {
        this.waterHeater = waterHeater;
    }

    public boolean isCook() {
        return cook;
    }

    public void setCook(boolean cook) {
        this.cook = cook;
    }

    public boolean isFridge() {
        return fridge;
    }

    public void setFridge(boolean fridge) {
        this.fridge = fridge;
    }

    public boolean isWashing() {
        return washing;
    }

    public void setWashing(boolean washing) {
        this.washing = washing;
    }

    public boolean isLoft() {
        return loft;
    }

    public void setLoft(boolean loft) {
        this.loft = loft;
    }

    public boolean isBed() {
        return bed;
    }

    public void setBed(boolean bed) {
        this.bed = bed;
    }

    public boolean isWardrobe() {
        return wardrobe;
    }

    public void setWardrobe(boolean wardrobe) {
        this.wardrobe = wardrobe;
    }

    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
        this.television = television;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public boolean isPublicRoom() {
        return publicRoom;
    }

    public void setPublicRoom(boolean publicRoom) {
        this.publicRoom = publicRoom;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    @NonNull
    @Override
    public String toString() {
//        String result = getOwner_id() + " " + getName() + " " + getPhone() + " " + getAvatar() + " "
//                + getRoomStyle() + " " + getNumberOfRoom() + " "
//                + getCapacity() + " " + getGender() + " " + getRoomArea() + " " + getRentalPrice() + " "
//                + getDeposit() + " " + getElectricityCost() + " " + getWaterCost() + " "
//                + getInternetCost() + " " + getParkingCost() + " " + getImage().toString() + " "
//                + getCity() + " " + getDistrict() + " " + getWard() + " " + getStreet() + " "
//                + getHouseNumber();
//        return result;
        return super.toString();
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("room_id", room_id);
        result.put("owner_id", owner_id);
        result.put("roomStyle", roomStyle);
        result.put("numberOfRoom", numberOfRoom);
        result.put("capacity", capacity);
        result.put("gender", gender);
        result.put("roomArea", roomArea);
        result.put("rentalPrice", rentalPrice);
        result.put("deposit", deposit);
        result.put("electricityCost", electricityCost);
        result.put("waterCost", waterCost);
        result.put("internetCost", internetCost);
        result.put("parkingCost", parkingCost);
        result.put("city", city);
        result.put("district", district);
        result.put("ward", ward);
        result.put("street", street);
        result.put("houseNumber", houseNumber);
        result.put("roomDescription", roomDescription);
        result.put("titleOfTheRoom", titleOfTheRoom);
        result.put("image", image);
        result.put("privateWC", privateWC);
        result.put("parkingLot", parkingLot);
        result.put("window", window);
        result.put("security", security);
        result.put("internet", internet);
        result.put("noCurfew", noCurfew);
        result.put("noOwner", noOwner);
        result.put("airConditioner", airConditioner);
        result.put("waterHeater", waterHeater);
        result.put("cook", cook);
        result.put("fridge", fridge);
        result.put("washing", washing);
        result.put("loft", loft);
        result.put("bed", bed);
        result.put("wardrobe", wardrobe);
        result.put("television", television);
        result.put("postingDate", postingDate);
        result.put("verify", verify);
        result.put("publicRoom", publicRoom);
        return result;
    }
}
