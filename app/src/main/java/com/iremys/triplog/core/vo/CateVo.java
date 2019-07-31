package com.iremys.triplog.core.vo;

import java.io.Serializable;

public class CateVo implements Serializable {

    private int cateNo;
    private String cateName;
    private String useFlag;
    private String inoutType;

    public CateVo() {
    }

    public CateVo(int cateNo, String cateName, String useFlag, String inoutType) {
        this.cateNo = cateNo;
        this.cateName = cateName;
        this.useFlag = useFlag;
        this.inoutType = inoutType;
    }

    public int getCateNo() {
        return cateNo;
    }

    public void setCateNo(int cateNo) {
        this.cateNo = cateNo;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public String getInoutType() {
        return inoutType;
    }

    public void setInoutType(String inoutType) {
        this.inoutType = inoutType;
    }

    @Override
    public String toString() {
        return "CateVo{" +
                "cateNo=" + cateNo +
                ", cateName='" + cateName + '\'' +
                ", useFlag='" + useFlag + '\'' +
                ", inoutType='" + inoutType + '\'' +
                '}';
    }
}
