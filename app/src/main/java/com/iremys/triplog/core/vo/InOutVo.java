package com.iremys.triplog.core.vo;

public class InOutVo {

    private int inoutNo;
    private int tripNo;
    private int cateNo;
    private String cateName;
    private String inoutType;
    private double pPrice;
    private int pUnitNo;
    private String pUnitName;
    private String pMethod;
    private double mPrice;
    private int mUnitNo;
    private String mUnitName;
    private String mMethod;
    private String title;
    private String useDate;
    private double lat;
    private double lon;
    private float accuracy;
    private String country;
    private String city;
    private String memo;
    private String sync;
    private double cardPrice;
    private int cardUnitNo;
    private String cardUnitName;

    public InOutVo() {
    }

    public int getInoutNo() {
        return inoutNo;
    }

    public void setInoutNo(int inoutNo) {
        this.inoutNo = inoutNo;
    }

    public int getTripNo() {
        return tripNo;
    }

    public void setTripNo(int tripNo) {
        this.tripNo = tripNo;
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

    public String getInoutType() {
        return inoutType;
    }

    public void setInoutType(String inoutType) {
        this.inoutType = inoutType;
    }

    public double getpPrice() {
        return pPrice;
    }

    public void setpPrice(double pPrice) {
        this.pPrice = pPrice;
    }

    public int getpUnitNo() {
        return pUnitNo;
    }

    public void setpUnitNo(int pUnitNo) {
        this.pUnitNo = pUnitNo;
    }

    public String getpUnitName() {
        return pUnitName;
    }

    public void setpUnitName(String pUnitName) {
        this.pUnitName = pUnitName;
    }

    public String getpMethod() {
        return pMethod;
    }

    public void setpMethod(String pMethod) {
        this.pMethod = pMethod;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public int getmUnitNo() {
        return mUnitNo;
    }

    public void setmUnitNo(int mUnitNo) {
        this.mUnitNo = mUnitNo;
    }

    public String getmUnitName() {
        return mUnitName;
    }

    public void setmUnitName(String mUnitName) {
        this.mUnitName = mUnitName;
    }

    public String getmMethod() {
        return mMethod;
    }

    public void setmMethod(String mMethod) {
        this.mMethod = mMethod;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public double getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(double cardPrice) {
        this.cardPrice = cardPrice;
    }

    public int getCardUnitNo() {
        return cardUnitNo;
    }

    public void setCardUnitNo(int cardUnitNo) {
        this.cardUnitNo = cardUnitNo;
    }

    public String getCardUnitName() {
        return cardUnitName;
    }

    public void setCardUnitName(String cardUnitName) {
        this.cardUnitName = cardUnitName;
    }

    @Override
    public String toString() {
        return "InOutVo{" +
                "inoutNo=" + inoutNo +
                ", tripNo=" + tripNo +
                ", cateNo=" + cateNo +
                ", cateName='" + cateName + '\'' +
                ", inoutType='" + inoutType + '\'' +
                ", pPrice=" + pPrice +
                ", pUnitNo=" + pUnitNo +
                ", pUnitName='" + pUnitName + '\'' +
                ", pMethod='" + pMethod + '\'' +
                ", mPrice=" + mPrice +
                ", mUnitNo=" + mUnitNo +
                ", mUnitName='" + mUnitName + '\'' +
                ", mMethod='" + mMethod + '\'' +
                ", title='" + title + '\'' +
                ", useDate='" + useDate + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", accuracy=" + accuracy +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", memo='" + memo + '\'' +
                ", sync='" + sync + '\'' +
                ", cardPrice=" + cardPrice +
                ", cardUnitNo=" + cardUnitNo +
                ", cardUnitName='" + cardUnitName + '\'' +
                '}';
    }
}
