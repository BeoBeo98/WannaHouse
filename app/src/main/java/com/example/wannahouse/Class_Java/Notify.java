package com.example.wannahouse.Class_Java;

public class Notify {
    private String notify_id;
    private int type;
    private String owner_id;
    private String report_id;
    private String reason;
    private String house_id;
    private String time;

    public Notify() {
        notify_id = "";
        type = -1;
        owner_id = "";
        report_id = "";
        reason = "";
        house_id = "";
        time = "";
    }

    public Notify(String notify_id, int type, String owner_id, String report_id, String reason, String house_id, String time) {
        this.notify_id = notify_id;
        this.type = type;
        this.owner_id = owner_id;
        this.report_id = report_id;
        this.reason = reason;
        this.house_id = house_id;
        this.time = time;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
