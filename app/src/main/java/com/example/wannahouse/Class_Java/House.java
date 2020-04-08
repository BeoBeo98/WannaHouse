package com.example.wannahouse.Class_Java;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class House implements Serializable {
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
    private ArrayList<String> report = new ArrayList<>();
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

    public House() {
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
        report = null;
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
    }

    public House(String owner_id, String roomStyle, int numberOfRoom, int capacity, int gender, int roomArea, int rentalPrice, int deposit, float electricityCost, int waterCost, int internetCost, int parkingCost, String city, String district, String ward, String street, String houseNumber, String roomDescription, String titleOfTheRoom, ArrayList<String> image, ArrayList<String> report, String name, String phone, String avatar, String postingDate, boolean parkingLot, boolean privateWC, boolean window, boolean security, boolean internet, boolean noCurfew, boolean noOwner, boolean airConditioner, boolean waterHeater, boolean cook, boolean fridge, boolean washing, boolean loft, boolean bed, boolean wardrobe, boolean television) {
        this.owner_id = owner_id;
        this.roomStyle = roomStyle;
        this.numberOfRoom = numberOfRoom;
        this.capacity = capacity;
        this.gender = gender;
        this.roomArea = roomArea;
        this.rentalPrice = rentalPrice;
        this.deposit = deposit;
        this.electricityCost = electricityCost;
        this.waterCost = waterCost;
        this.internetCost = internetCost;
        this.parkingCost = parkingCost;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.houseNumber = houseNumber;
        this.roomDescription = roomDescription;
        this.titleOfTheRoom = titleOfTheRoom;
        this.image = image;
        this.report = report;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;
        this.postingDate = postingDate;
        this.parkingLot = parkingLot;
        this.privateWC = privateWC;
        this.window = window;
        this.security = security;
        this.internet = internet;
        this.noCurfew = noCurfew;
        this.noOwner = noOwner;
        this.airConditioner = airConditioner;
        this.waterHeater = waterHeater;
        this.cook = cook;
        this.fridge = fridge;
        this.washing = washing;
        this.loft = loft;
        this.bed = bed;
        this.wardrobe = wardrobe;
        this.television = television;
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

    public ArrayList<String> getReport() {
        return report;
    }

    public void setReport(ArrayList<String> report) {
        this.report = report;
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
}
