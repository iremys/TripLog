package com.iremys.triplog.core.vo;

import java.io.Serializable;

public class UnitVo implements Serializable {

    private int unitNo;
    private String unitCode;
    private String unitName;
    private double price;
    private String method;


    public UnitVo() {
    }

    public int getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(int unitNo) {
        this.unitNo = unitNo;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "UnitVo{" +
                "unitNo=" + unitNo +
                ", unitCode='" + unitCode + '\'' +
                ", unitName='" + unitName + '\'' +
                ", price=" + price +
                ", method='" + method + '\'' +
                '}';
    }
}
