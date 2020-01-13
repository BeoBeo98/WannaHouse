package com.example.wannahouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class House implements Serializable {
    private int roomStyle;
    private int numberOfRoom;
    private int capacity;
    private int gender;
    private int roomArea;
    private int rentalPrice;
    private int deposit;
    private int electricityCost;
    private int waterCost;
    private int internetCost;
    private boolean parkingLot;
    private int parkingCost;
    private String city;
    private String district;
    private String ward;
    private String street;
    private String houseNumber;
    private String roomDescription;
    private String titleOfTheRoom;
    private ArrayList<String> image = new ArrayList<>();
    private String roomOwnerName;
    private String phone;
    private String avatar;
    private String postingDate;

    public House() {
    }

    public House(int roomStyle, int numberOfRoom, int capacity, int gender, int roomArea,
                 int rentalPrice, int deposit, int electricityCost, int waterCost, int internetCost,
                 boolean parkingLot, int parkingCost, String city, String district, String ward,
                 String street, String houseNumber, String roomDescription, String titleOfTheRoom,
                 ArrayList<String> image, String roomOwnerName, String phone, String avatar, String postingDate) {
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
        this.parkingLot = parkingLot;
        this.parkingCost = parkingCost;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.houseNumber = houseNumber;
        this.roomDescription = roomDescription;
        this.titleOfTheRoom = titleOfTheRoom;
        this.image = image;
        this.roomOwnerName = roomOwnerName;
        this.phone = phone;
        this.avatar = avatar;
        this.postingDate = postingDate;
    }

    public int getRoomStyle() {
        return roomStyle;
    }

    public void setRoomStyle(int roomStyle) {
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

    public int getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(int electricityCost) {
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

    public String getRoomOwnerName() {
        return roomOwnerName;
    }

    public void setRoomOwnerName(String roomOwnerName) {
        this.roomOwnerName = roomOwnerName;
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
}
