package com.iremys.triplog;

import android.app.Application;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;
import com.iremys.triplog.core.db.DBHelper;
import com.iremys.triplog.core.util.DateTimeUtil;

public class MainApp extends Application {
    public static DBHelper Dao;          //DB관련객체       MainApp.Dao

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("madoo", "MainApp onCreate()" );
        Dao = new DBHelper(getApplicationContext());




    }

    public DBHelper getDao() {
        return Dao;
    }
}
