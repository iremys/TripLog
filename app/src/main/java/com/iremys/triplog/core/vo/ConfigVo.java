package com.iremys.triplog.core.vo;

public class ConfigVo {

    private String userID;

    public ConfigVo() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "ConfigVo{" +
                "userID='" + userID + '\'' +
                '}';
    }
}
