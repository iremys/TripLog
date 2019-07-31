package com.iremys.triplog.core.vo;

public class BalanceChartVo {

    private int unitNo;
    private String unitCode;
    private String unitName;
    private double bankBalance;
    private double cashBalance;

    public BalanceChartVo() {
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

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    @Override
    public String toString() {
        return "BalanceChartVo{" +
                "unitNo=" + unitNo +
                ", unitCode='" + unitCode + '\'' +
                ", unitName='" + unitName + '\'' +
                ", bankBalance=" + bankBalance +
                ", cashBalance=" + cashBalance +
                '}';
    }
}
