package com.iremys.triplog.core.util;


import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTimeUtil {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public DateTimeUtil(){
        GregorianCalendar calendar = new GregorianCalendar();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH)+1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
    };

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getYear(String date) {
        String year = date.substring(0, 4);
        return Integer.parseInt(year);
    }

    public int getMonth(String date) {
        String month = date.substring(5, 7);
        return Integer.parseInt(month);
    }

    public int getDay(String date) {
        String day = date.substring(8, 10);
        return Integer.parseInt(day);
    }

    public int getHour(String time) {
        String hour = time.substring(0, 2);
        return Integer.parseInt(hour);
    }

    public int getMinute(String time) {
        String minute = time.substring(time.length()-2, time.length());
        return Integer.parseInt(minute);
    }

    public String getDateStr(){
        return make2Str(this.year) + "-" + make2Str(this.month) + "-" + make2Str(this.day);
     }

    public String getDateStr(int year, int month, int day){
        return make2Str(year) + "-" + make2Str(month) + "-" + make2Str(day);
    }

    public String getDateStr(String dateTime){
        return dateTime.substring(0, 10);
    }

    public String getTimeStr(){
        return make2Str(this.hour) + ":" + make2Str(this.minute);
    }

    public String getTimeStr(int hour, int minute){
        return make2Str(hour) + ":" + make2Str(minute);
    }

    public String getTimeStr(String dateTime){
        dateTime.length();
        return dateTime.substring(dateTime.length()-5,  dateTime.length());
    }

    //두자리로 만들기
    private String make2Str(int source){
        String str ="";

        if(source<10){
            str = "0"+ source;
        } else {
            str = "" + source;
        }
        return str;
    }

    @Override
    public String toString() {
        return "DateTime{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }

}
