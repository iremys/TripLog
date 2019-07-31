package com.iremys.triplog.core.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iremys.triplog.MainApp;
import com.iremys.triplog.core.util.DateTimeUtil;
import com.iremys.triplog.core.vo.BalanceChartVo;
import com.iremys.triplog.core.vo.CateVo;
import com.iremys.triplog.core.vo.ConfigVo;
import com.iremys.triplog.core.vo.FileVo;
import com.iremys.triplog.core.vo.InOutVo;
import com.iremys.triplog.core.vo.TripVo;
import com.iremys.triplog.core.vo.UnitVo;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "TripLog.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("madoo", "DBHelper DBHelper()-----> " + " DB_NAME: " + DB_NAME  + "   DB_VERSION: " + DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("madoo", "DBHelper onCreate()----->" );
        dropInout(db);
        dropUnit(db);
        dropCate(db);
        dropFile(db);
        dropTrip(db);
        dropConfig(db);

        createTrip(db);
        createFile(db);
        createCate(db);
        createUnit(db);
        createInout(db);
        createConfig(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("madoo", "DBHelper onUpgrade()" );
    }





    ////////////////////////////////////////////////////////////////////////////////
    //테이블생성
    ////////////////////////////////////////////////////////////////////////////////
    //tbl_trip 여행정보
    private void createTrip(SQLiteDatabase db) {
        String SQL_create_tbl_trip =
                "CREATE TABLE tbl_trip ( " +
                        " tripNo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " tripTitle TEXT, " +
                        " startDate TEXT, " +
                        " endDate TEXT, " +
                        " regDate TEXT " +
                        " );" ;

        db.execSQL(SQL_create_tbl_trip);
        Log.d("madoo", "SQL_create_tbl_trip## "+ SQL_create_tbl_trip );
    }

    //tbl_cate 카테고리
    private void createCate(SQLiteDatabase db) {
        String SQL_create_tbl_cate =
                "CREATE TABLE tbl_cate ( " +
                        " cateNo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " cateName TEXT, " +
                        " useFlag TEXT, " +
                        " inoutType INTEGER " +
                        " );" ;

        db.execSQL(SQL_create_tbl_cate);
        Log.d("madoo", "SQL_create_tbl_cate## "+ SQL_create_tbl_cate );
    }

    //tbl_unit 화폐단위
    private void createUnit(SQLiteDatabase db) {
        String SQL_create_tbl_unit =
                "CREATE TABLE tbl_unit ( " +
                        " unitNo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " unitCode TEXT, " +
                        " unitName TEXT " +
                        " );" ;

        db.execSQL(SQL_create_tbl_unit);
        Log.d("madoo", "SQL_create_tbl_unit## "+ SQL_create_tbl_unit );
    }


    //tbl_inout 사용내역
    private void createInout(SQLiteDatabase db) {
        String SQL_create_tbl_inout =
                "CREATE TABLE tbl_inout ( " +
                        " inoutNo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " tripNo INTEGER NOT NULL, " +
                        " cateNo INTEGER NOT NULL, " +
                        " inoutType TEXT, " +
                        " pPrice REAL, " +
                        " pUnitNo INTEGER, " +
                        " pMethod TEXT, " +
                        " mPrice REAL, " +
                        " mUnitNo INTEGER, " +
                        " mMethod TEXT, " +
                        " title TEXT, " +
                        " useDate TEXT, " +
                        " lat REAL, " +
                        " lon REAL, " +
                        " accuracy REAL, " +
                        " country TEXT, " +
                        " city TEXT, " +
                        " memo TEXT, " +
                        " sync TEXT, " +

                        " cardPrice REAL, " +
                        " cardUnitNo INTEGER, " +

                        " FOREIGN KEY(tripNo) REFERENCES tbl_trip(tripNo), " +
                        " FOREIGN KEY(cateNo) REFERENCES tbl_cate(cateNo), " +
                        " FOREIGN KEY(pUnitNo) REFERENCES tbl_unit(unitNo), " +
                        " FOREIGN KEY(mUnitNo) REFERENCES tbl_unit(unitNo) " +
                        " ); ";

        db.execSQL(SQL_create_tbl_inout);
        Log.d("madoo", "SQL_create_tbl_inout## "+ SQL_create_tbl_inout );

    }

    //tbl_file 이미지파일
    private void createFile(SQLiteDatabase db) {
        String SQL_create_tbl_file =
                "CREATE TABLE tbl_file ( " +
                        " fileNo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " inoutNo INTEGER , " +
                        " saveFileName TEXT , " +
                        " filePath TEXT ," +
                        " FOREIGN KEY(inoutNo) REFERENCES tbl_inout(inoutNo) " +
                        " );" ;

        db.execSQL(SQL_create_tbl_file);
        Log.d("madoo", "SQL_create_tbl_file## "+ SQL_create_tbl_file );
    }


    //tbl_config 환경설정
    private void createConfig(SQLiteDatabase db) {
        String SQL_create_tbl_config =
                "CREATE TABLE tbl_config ( " +
                        " userID TEXT " +
                        " );" ;

        db.execSQL(SQL_create_tbl_config);
        Log.d("madoo", "SQL_create_tbl_config## "+ SQL_create_tbl_config );
    }

    ////////////////////////////////////////////////////////////////////////////////
    //테이블 삭제
    ////////////////////////////////////////////////////////////////////////////////
    //tbl_trip 여행정보 테이블 삭제
    private void dropTrip(SQLiteDatabase db) {
        String SQL_drop_tbl_trip = " DROP TABLE if exists tbl_trip ; " ;
        db.execSQL(SQL_drop_tbl_trip);
        Log.d("madoo", "SQL_drop_tbl_trip## "+ SQL_drop_tbl_trip );
    }

    //tbl_cate 카테고리
    private void dropCate(SQLiteDatabase db) {
        String SQL_drop_tbl_cate = " DROP TABLE if exists tbl_cate ; " ;
        db.execSQL(SQL_drop_tbl_cate);
        Log.d("madoo", "SQL_drop_tbl_cate## "+ SQL_drop_tbl_cate );
    }

    //tbl_unit 화폐단위
    private void dropUnit(SQLiteDatabase db) {
        String SQL_drop_tbl_unit = " DROP TABLE if exists tbl_unit ; " ;
        db.execSQL(SQL_drop_tbl_unit);
        Log.d("madoo", "SQL_drop_tbl_unit## "+ SQL_drop_tbl_unit );
    }


    //tbl_inout 사용내역
    private void dropInout(SQLiteDatabase db) {
        String SQL_drop_tbl_inout = " DROP TABLE if exists tbl_inout ; " ;
        db.execSQL(SQL_drop_tbl_inout);
        Log.d("madoo", "SQL_drop_tbl_inout## "+ SQL_drop_tbl_inout );
    }


    //tbl_config 환경설정
    private void dropConfig(SQLiteDatabase db) {
        String SQL_drop_tbl_config = " DROP TABLE if exists tbl_config ; " ;
        db.execSQL(SQL_drop_tbl_config);
        Log.d("madoo", "SQL_drop_tbl_config## "+ SQL_drop_tbl_config );
    }

    //tbl_config 환경설정
    private void dropFile(SQLiteDatabase db) {
        String SQL_drop_tbl_file = " DROP TABLE if exists tbl_file ; " ;
        db.execSQL(SQL_drop_tbl_file);
        Log.d("madoo", "SQL_drop_tbl_config## "+ SQL_drop_tbl_file );
    }



    ////////////////////////////////////////////////////////////////////////////////
    //여행 관련
    ////////////////////////////////////////////////////////////////////////////////

    //여행 등록
    public void inserTrip(TripVo tripVo){
        String SQL_insertTrip =
                "INSERT INTO tbl_trip (tripTitle, startDate, endDate, regDate) " +
                        "values ( " +
                        " '" + tripVo.getTripTitle() + "', " +
                        " '" + tripVo.getStartDate() + "', " +
                        " '" + tripVo.getEndDate() + "', " +
                        " datetime('now' , 'localtime' ) " +
                        " ); " ;

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_insertTrip);

        db.close();
        Log.d("madoo: ", "SQL_insertTrip## "+ SQL_insertTrip );
    }

    //여행 리스트 가져오기
    public List<TripVo> getTripList() {

        // 쿼리생성
        String sql_getTripList =
                " SELECT tripNo, tripTitle, startDate, endDate, regDate " +
                        " FROM tbl_trip " +
                        " ORDER BY regDate asc ; " ;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_getTripList, null);

        Log.d("madoo: ", "sql_getTripList## "+ sql_getTripList );


        List<TripVo> tripList = new ArrayList<TripVo>();

        while (cursor.moveToNext()) {
            TripVo tripVo =  new TripVo();
            tripVo.setTripNo(cursor.getInt(0));
            tripVo.setTripTitle(cursor.getString(1));
            tripVo.setStartDate(cursor.getString(2));
            tripVo.setEndDate(cursor.getString(3));
            tripVo.setRegDate(cursor.getString(4));
            tripList.add(tripVo);
        }

        db.close();
        Log.d("madoo: ", "sql_getTripList result## "+ tripList.toString() );
        return tripList;
    }



    ////////////////////////////////////////////////////////////////////////////////
    //카테고리 관련
    ////////////////////////////////////////////////////////////////////////////////

    //카테고리 등록
    public void insertCate(CateVo cateVo){

        String SQL_insertCate =
                "INSERT INTO tbl_cate (cateName, useFlag, inoutType) " +
                        "values ( " +
                        " '" + cateVo.getCateName() + "', " +
                        " '" + cateVo.getUseFlag() + "', " +
                        " '" + cateVo.getInoutType() + "' " +
                        " ); " ;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_insertCate);

        db.close();
        Log.d("madoo: ", "SQL_insertTrip## "+ SQL_insertCate );

    }


    //카테고리리스트 가져오기
    public List<CateVo> getListCate(String inoutType) {

        // 쿼리생성
        String sql_getListCateByTripNo=
                " SELECT cateNo, cateName, useFlag, inoutType " +
                        " FROM tbl_cate " +
                        " WHERE inoutType = '" + inoutType + "' " +
                        " ORDER BY cateNo asc ; " ;


        //읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_getListCateByTripNo, null);
        Log.d("madoo: ", "sql_getListCateByTripNo## "+ sql_getListCateByTripNo );

        List<CateVo> cateList = new ArrayList<CateVo>();

        while (cursor.moveToNext()) {
            CateVo cateVo =  new CateVo();
            cateVo.setCateNo(cursor.getInt(0));
            cateVo.setCateName(cursor.getString(1));
            cateVo.setUseFlag(cursor.getString(2));
            cateVo.setInoutType(cursor.getString(3));
            cateList.add(cateVo);
        }
        db.close();

        Log.d("remysDB: ", "sql_getListCateByTripNo result## "+ cateList.toString() );
        return cateList;
    }








    ////////////////////////////////////////////////////////////////////////////////
    //화폐 관련
    ////////////////////////////////////////////////////////////////////////////////

    //화폐 등록
    public void insertUnit(UnitVo unitVo) {

        String SQL_insertUnit =
                "INSERT INTO tbl_unit (unitCode, unitName) " +
                        "values ( " +
                        " '" + unitVo.getUnitCode() + "', " +
                        " '" + unitVo.getUnitName() + "' " +
                        " ); " ;

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_insertUnit);

        db.close();
        Log.d("madoo: ", "SQL_insertUnit## "+ SQL_insertUnit );
    }


    //화폐정보 가져오기
    public List<UnitVo> getListUnit() {

        // 쿼리생성
        String sql_getListUnit=
                " SELECT unitNo, unitCode, unitName " +
                        " FROM tbl_unit "+
                        " ORDER BY unitNo asc ; " ;


        //읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_getListUnit, null);

        Log.d("madoo: ", "sql_getListUnit## "+ sql_getListUnit );

        List<UnitVo> unitList = new ArrayList<UnitVo>();

        while (cursor.moveToNext()) {
            UnitVo unitVo =  new UnitVo();
            unitVo.setUnitNo(cursor.getInt(0));
            unitVo.setUnitCode(cursor.getString(1));
            unitVo.setUnitName(cursor.getString(2));
            unitList.add(unitVo);
        }

        db.close();
        Log.d("madoo: ", "sql_getListUnit result## "+ unitList.toString() );
        return unitList;
    }




    ////////////////////////////////////////////////////////////////////////////////
    //입출금 관련
    ////////////////////////////////////////////////////////////////////////////////
    //입출금 저장
    public int insertInout(InOutVo inOutVo){

        SQLiteDatabase db = getWritableDatabase();

        String SQL_insertInout =
                "INSERT INTO tbl_inout (tripNo, cateNo, inoutType, pPrice, pUnitNo, pMethod, mPrice, mUnitNo, mMethod, title, useDate, lat, lon, accuracy, country, city, memo, sync, cardPrice, cardUnitNo)" +
                        " values ( " +
                        inOutVo.getTripNo() + ", " +
                        inOutVo.getCateNo() + ", " +
                        " '" +inOutVo.getInoutType() + "', " +
                        inOutVo.getpPrice() + ", " +
                        inOutVo.getpUnitNo() + ", " +
                        " '" +inOutVo.getpMethod() + "', " +
                        inOutVo.getmPrice() + ", " +
                        inOutVo.getmUnitNo() + ", " +
                        " '" +inOutVo.getmMethod() + "', " +
                        " '" +inOutVo.getTitle() + "', " +
                        " '" +inOutVo.getUseDate() + "', " +
                        inOutVo.getLat() + ", " +
                        inOutVo.getLon() + ", " +
                        inOutVo.getAccuracy() + ", " +
                        " '" +inOutVo.getCountry() + "', " +
                        " '" +inOutVo.getCity() + "', " +
                        " '" +inOutVo.getMemo() + "', " +
                        " '" +inOutVo.getSync() + "', " +
                        inOutVo.getCardPrice() + ", " +
                        inOutVo.getCardUnitNo() + " " +
                        " );" ;

        db.execSQL(SQL_insertInout);

        Log.d("madoo: ", "SQL_insertInout## "+ SQL_insertInout );

        //////////////////////////////////////////////////////////
        //마지막 id값 가져오기
        int no = 0;
        String SQL_last_insert_rowid = "SELECT last_insert_rowid();" ;
        Cursor cursor = db.rawQuery(SQL_last_insert_rowid, null);

        while (cursor.moveToNext()) {
            no =  cursor.getInt(0);
        }

        Log.d("madoo: ", "SQL_last_insert_rowid## "+ SQL_last_insert_rowid );


        db.close();

        return no;

    }


    //입금출금 리스트 가져오기
    public List<InOutVo> getListInout(int tripNo) {

        Log.d("madoo", "리스트 getList()" );

        SQLiteDatabase db = getReadableDatabase();

        String sql_getListInout =
                " SELECT ct.inoutNo, ct.tripNo, " +
                "        ct.cateNo, ct.cateName cateName, " +
                "        ct.inoutType, " +
                "        ct.pPrice, ct.pUnitNo, ct.pMethod, ct.pUnitName, " +
                "        ct.mPrice, ct.mUnitNo, ct.mMethod, ct.mUnitName, " +
                "        ct.title, ct.useDate, " +
                "        ct.lat, ct.lon, ct.accuracy, " +
                "        ct.country, " +
                "        ct.city, ct.memo, ct.sync," +
                "        ct.cardPrice, ct.cardUnitNo, ca.unitName cardUnitName " +
                " FROM " +
                "       (SELECT mt.inoutNo, mt.tripNo, " +
                "               mt.cateNo, c.cateName cateName, " +
                "               mt.inoutType, " +
                "               mt.pPrice, mt.pUnitNo, mt.pMethod, mt.pUnitName, " +
                "               mt.mPrice, mt.mUnitNo, mt.mMethod, mt.mUnitName, " +
                "               mt.title, mt.useDate, " +
                "               mt.lat, mt.lon, mt.accuracy, " +
                "               mt.country, " +
                "               mt.city, mt.memo, mt.sync, " +
                "               mt.cardPrice, mt.cardUnitNo " +
                "        FROM " +
                "                (SELECT pt.inoutNo, pt.tripNo, " +
                "                        pt.cateNo, " +
                "                        pt.inoutType, " +
                "                        pt.pPrice, pt.pUnitNo, pt.pMethod, pt.pUnitName, " +
                "                        pt.mPrice, pt.mUnitNo, pt.mMethod, m.unitName mUnitName, " +
                "                        pt.title, pt.useDate, " +
                "                        pt.lat, pt.lon, pt.accuracy, " +
                "                        pt.country, " +
                "                        pt.city, pt.memo, pt.sync, " +
                "                        pt.cardPrice, pt.cardUnitNo " +
                "                 FROM " +
                "                           (SELECT io.inoutNo, io.tripNo, " +
                "                                   io.cateNo, " +
                "                                   io.inoutType, " +
                "                                   io.pPrice, io.pUnitNo, io.pMethod, p.unitName pUnitName, " +
                "                                   io.mPrice, io.mUnitNo, io.mMethod," +
                "                                   io.title, io.useDate, " +
                "                                   io.lat, io.lon, io.accuracy, " +
                "                                   io.country, " +
                "                                   io.city, io.memo, io.sync, " +
                "                                   io.cardPrice, io.cardUnitNo " +
                "                            FROM " +
                "                                   (SELECT inoutNo, tripNo, " +
                "                                           cateNo, " +
                "                                           inoutType, " +
                "                                           pPrice, pUnitNo, pMethod," +
                "                                           mPrice, mUnitNo, mMethod, " +
                "                                           title, useDate, " +
                "                                           lat, lon, accuracy, " +
                "                                           country, " +
                "                                           city, memo, sync, " +
                "                                           cardPrice, cardUnitNo " +
                "                                     FROM tbl_inout " +
                "                                     WHERE tripNo = " + tripNo + " " +
                "                                    ) io " +
                "                                      left outer join tbl_unit p " +
                "                                      ON io.pUnitNo = p.unitNo " +
                "                            ) pt " +
                "                              left outer join tbl_unit m " +
                "                              ON pt.mUnitNo = m.unitNo " +
                "                  ) mt " +
                "                    left outer join tbl_cate c " +
                "                    ON mt.cateNo = c.cateNo " +
                "        ) ct " +
                "          left outer join tbl_unit ca " +
                "          ON ct.cardUnitNo = ca.unitNo " +
                " ORDER BY ct.useDate desc ; " ;

        List<InOutVo> inOutList = new ArrayList<InOutVo>();
        Log.d("madoo: ", "sql_getListInout result## "+ sql_getListInout );
        Cursor cursor = db.rawQuery(sql_getListInout, null);

        while (cursor.moveToNext()) {

            InOutVo inOutVo = new InOutVo();
            inOutVo.setInoutNo(cursor.getInt(0));
            inOutVo.setTripNo(cursor.getInt(1));
            inOutVo.setCateNo(cursor.getInt(2));
            inOutVo.setCateName(cursor.getString(3));
            inOutVo.setInoutType(cursor.getString(4));
            inOutVo.setpPrice(cursor.getDouble(5));
            inOutVo.setpUnitNo(cursor.getInt(6));
            inOutVo.setpMethod(cursor.getString(7));
            inOutVo.setpUnitName(cursor.getString(8));
            inOutVo.setmPrice(cursor.getDouble(9));
            inOutVo.setmUnitNo(cursor.getInt(10));
            inOutVo.setmMethod(cursor.getString(11));
            inOutVo.setmUnitName(cursor.getString(12));
            inOutVo.setTitle(cursor.getString(13));
            inOutVo.setUseDate(cursor.getString(14));
            inOutVo.setLat(cursor.getDouble(15));
            inOutVo.setLon(cursor.getDouble(16));
            inOutVo.setAccuracy(cursor.getFloat(17));
            inOutVo.setCountry(cursor.getString(18));
            inOutVo.setCity(cursor.getString(19));
            inOutVo.setMemo(cursor.getString(20));
            inOutVo.setSync(cursor.getString(21));
            inOutVo.setCardPrice(cursor.getDouble(22));
            inOutVo.setCardUnitNo(cursor.getInt(23));
            inOutVo.setCardUnitName(cursor.getString(24));

            inOutList.add(inOutVo);
        }

        db.close();
        Log.d("madoo: ", "sql_getListInout result## "+ inOutList.toString() );


        return inOutList;
    }


    //입금출금 가져오기 한개
    public InOutVo getInout(int inoutNo) {

        Log.d("madoo", "입출금 1개 getInout()" );

        SQLiteDatabase db = getReadableDatabase();

        String sql_getInout =
                " SELECT ct.inoutNo, ct.tripNo, " +
                "        ct.cateNo, ct.cateName cateName, " +
                "        ct.inoutType, " +
                "        ct.pPrice, ct.pUnitNo, ct.pMethod, ct.pUnitName, " +
                "        ct.mPrice, ct.mUnitNo, ct.mMethod, ct.mUnitName, " +
                "        ct.title, ct.useDate, " +
                "        ct.lat, ct.lon, ct.accuracy, " +
                "        ct.country, " +
                "        ct.city, ct.memo, ct.sync," +
                "        ct.cardPrice, ct.cardUnitNo, ca.unitName cardUnitName " +
                " FROM " +
                "       (SELECT mt.inoutNo, mt.tripNo, " +
                "               mt.cateNo, c.cateName cateName, " +
                "               mt.inoutType, " +
                "               mt.pPrice, mt.pUnitNo, mt.pMethod, mt.pUnitName, " +
                "               mt.mPrice, mt.mUnitNo, mt.mMethod, mt.mUnitName, " +
                "               mt.title, mt.useDate, " +
                "               mt.lat, mt.lon, mt.accuracy, " +
                "               mt.country, " +
                "               mt.city, mt.memo, mt.sync, " +
                "               mt.cardPrice, mt.cardUnitNo " +
                "        FROM " +
                "                (SELECT pt.inoutNo, pt.tripNo, " +
                "                        pt.cateNo, " +
                "                        pt.inoutType, " +
                "                        pt.pPrice, pt.pUnitNo, pt.pMethod, pt.pUnitName, " +
                "                        pt.mPrice, pt.mUnitNo, pt.mMethod, m.unitName mUnitName, " +
                "                        pt.title, pt.useDate, " +
                "                        pt.lat, pt.lon, pt.accuracy, " +
                "                        pt.country, " +
                "                        pt.city, pt.memo, pt.sync, " +
                "                        pt.cardPrice, pt.cardUnitNo " +
                "                 FROM " +
                "                           (SELECT io.inoutNo, io.tripNo, " +
                "                                   io.cateNo, " +
                "                                   io.inoutType, " +
                "                                   io.pPrice, io.pUnitNo, io.pMethod, p.unitName pUnitName, " +
                "                                   io.mPrice, io.mUnitNo, io.mMethod," +
                "                                   io.title, io.useDate, " +
                "                                   io.lat, io.lon, io.accuracy, " +
                "                                   io.country, " +
                "                                   io.city, io.memo, io.sync, " +
                "                                   io.cardPrice, io.cardUnitNo " +
                "                            FROM " +
                "                                   (SELECT inoutNo, tripNo, " +
                "                                           cateNo, " +
                "                                           inoutType, " +
                "                                           pPrice, pUnitNo, pMethod," +
                "                                           mPrice, mUnitNo, mMethod, " +
                "                                           title, useDate, " +
                "                                           lat, lon, accuracy, " +
                "                                           country, " +
                "                                           city, memo, sync, " +
                "                                           cardPrice, cardUnitNo " +
                "                                     FROM tbl_inout " +
                "                                     WHERE inoutNo = " + inoutNo + " " +
                "                                    ) io " +
                "                                      left outer join tbl_unit p " +
                "                                      ON io.pUnitNo = p.unitNo " +
                "                            ) pt " +
                "                              left outer join tbl_unit m " +
                "                              ON pt.mUnitNo = m.unitNo " +
                "                  ) mt " +
                "                    left outer join tbl_cate c " +
                "                    ON mt.cateNo = c.cateNo " +
                "        ) ct " +
                "          left outer join tbl_unit ca " +
                "          ON ct.cardUnitNo = ca.unitNo " +
                " ORDER BY ct.inoutNo desc ; " ;


        InOutVo inOutVo = new InOutVo();
        Log.d("madoo: ", "sql_getInout result## "+ sql_getInout );
        Cursor cursor = db.rawQuery(sql_getInout, null);

        while (cursor.moveToNext()) {

            inOutVo.setInoutNo(cursor.getInt(0));
            inOutVo.setTripNo(cursor.getInt(1));
            inOutVo.setCateNo(cursor.getInt(2));
            inOutVo.setCateName(cursor.getString(3));
            inOutVo.setInoutType(cursor.getString(4));
            inOutVo.setpPrice(cursor.getDouble(5));
            inOutVo.setpUnitNo(cursor.getInt(6));
            inOutVo.setpMethod(cursor.getString(7));
            inOutVo.setpUnitName(cursor.getString(8));
            inOutVo.setmPrice(cursor.getDouble(9));
            inOutVo.setmUnitNo(cursor.getInt(10));
            inOutVo.setmMethod(cursor.getString(11));
            inOutVo.setmUnitName(cursor.getString(12));
            inOutVo.setTitle(cursor.getString(13));
            inOutVo.setUseDate(cursor.getString(14));
            inOutVo.setLat(cursor.getDouble(15));
            inOutVo.setLon(cursor.getDouble(16));
            inOutVo.setAccuracy(cursor.getFloat(17));
            inOutVo.setCountry(cursor.getString(18));
            inOutVo.setCity(cursor.getString(19));
            inOutVo.setMemo(cursor.getString(20));
            inOutVo.setSync(cursor.getString(21));
            inOutVo.setCardPrice(cursor.getDouble(22));
            inOutVo.setCardUnitNo(cursor.getInt(23));
            inOutVo.setCardUnitName(cursor.getString(24));
        }

        db.close();
        Log.d("madoo: ", "sql_getInout result## "+ inOutVo.toString() );


        return inOutVo;
    }


    //입출금 수정
    public void updateInout(InOutVo inOutVo){

        SQLiteDatabase db = getWritableDatabase();

        String SQL_updateInout =
                " UPDATE tbl_inout " +
                        " SET cateNo = " + inOutVo.getCateNo() + ", " +
                        "     pPrice = " + inOutVo.getpPrice() + ", " +
                        "     pUnitNo = " + inOutVo.getpUnitNo() + ", " +
                        "     pMethod = '" + inOutVo.getpMethod() + "', " +
                        "     mPrice = " + inOutVo.getmPrice() + ", " +
                        "     mUnitNo = " + inOutVo.getmUnitNo() + ", " +
                        "     inoutNo = " + inOutVo.getInoutNo() + ", " +
                        "     mMethod = '" + inOutVo.getmMethod() + "', " +
                        "     title = '" + inOutVo.getTitle() + "', " +
                        "     useDate = '" + inOutVo.getUseDate() + "', " +
                        "     lat = " + inOutVo.getLat() + ", " +
                        "     lon = " + inOutVo.getLon() + ", " +
                        "     accuracy = " + inOutVo.getAccuracy() + ", " +
                        "     country = '" + inOutVo.getCountry() + "', " +
                        "     city = '" + inOutVo.getCity() + "', " +
                        "     memo = '" + inOutVo.getMemo() + "', " +
                        "     sync = '" + inOutVo.getSync() + "', " +
                        "     cardPrice = " + inOutVo.getCardPrice() + ", " +
                        "     cardUnitNo = " + inOutVo.getCardUnitNo() + " " +
                        " WHERE inoutNo = " + inOutVo.getInoutNo() + " ;" ;

        db.execSQL(SQL_updateInout);

        db.close();
        Log.d("madoo: ", "SQL_updateInout## "+ SQL_updateInout );

    }

    //입출금 삭제
    public void deleteInout(int inoutNo){

        SQLiteDatabase db = getWritableDatabase();

        String SQL_deleteInout =
                " DELETE FROM tbl_inout " +
                        " WHERE inoutNo = " + inoutNo + " ;" ;

        db.execSQL(SQL_deleteInout);

        db.close();
        Log.d("madoo: ", "SQL_deleteInout## "+ SQL_deleteInout );

    }



    ////////////////////////////////////////////////////////////////////////////////
    //파일
    ////////////////////////////////////////////////////////////////////////////////

    //파일 등록
    public void insertFile(FileVo fileVo) {

        String SQL_insertFile =
                "INSERT INTO tbl_file (inoutNo, saveFileName, filePath) " +
                "values ( " +
                + fileVo.getInoutNo() + ", " +
                " '" + fileVo.getSaveFileName() + "', " +
                " '" + fileVo.getFilePath() + "' " +
                " ); " ;

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_insertFile);

        db.close();
        Log.d("madoo: ", "SQL_insertFile## "+ SQL_insertFile );
    }


    //파일 삭제
    public void deleteFile(int inoutNo){

        SQLiteDatabase db = getWritableDatabase();

        String SQL_deleteFile =
                " DELETE FROM tbl_file " +
                " WHERE inoutNo = " + inoutNo + " ;" ;

        db.execSQL(SQL_deleteFile);

        db.close();
        Log.d("madoo: ", "SQL_deleteFile## "+ SQL_deleteFile );

    }


    //파일 리스트 가져오기
    public List<FileVo> getListFile(int inoutNo) {

        // 쿼리생성
        String sql_getListFile=
                " SELECT fileNo, inoutNo, saveFileName, filePath " +
                        " FROM tbl_file "+
                        " WHERE inoutNo = " + inoutNo +
                        " ORDER BY fileNo asc ; " ;


        //읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_getListFile, null);

        Log.d("madoo: ", "sql_getListFile## "+ sql_getListFile );

        List<FileVo> fileList = new ArrayList<FileVo>();

        while (cursor.moveToNext()) {
            FileVo fileVo =  new FileVo();
            fileVo.setFileNo(cursor.getInt(0));
            fileVo.setInoutNo(cursor.getInt(1));
            fileVo.setSaveFileName(cursor.getString(2));
            fileVo.setFilePath(cursor.getString(3));
            fileList.add(fileVo);
        }

        db.close();
        Log.d("madoo: ", "sql_getListFile result## "+ fileList.toString() );
        return fileList;
    }




    ////////////////////////////////////////////////////////////////////////////////
    //환경설정(현재는 userID)
    ////////////////////////////////////////////////////////////////////////////////

    //환경설정 등록
    public void insertConfig(ConfigVo config) {

        String SQL_insertConfig =
                "INSERT INTO tbl_config (userID) " +
                        "values ( " +
                        " '" + config.getUserID() + "' " +
                        " ); " ;

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_insertConfig);

        db.close();
        Log.d("madoo: ", "SQL_insertConfig## "+ SQL_insertConfig );
    }


    //userID 가져오기
    public ConfigVo getConfig() {

        // 쿼리생성
        String sql_getConfig=
                " SELECT userID " +
                        " FROM tbl_config ; " ;


        //읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_getConfig, null);

        Log.d("madoo: ", "sql_getConfig## "+ sql_getConfig );

        ConfigVo configVo = new ConfigVo();

        while (cursor.moveToNext()) {
            configVo.setUserID(cursor.getString(0));
        }

        db.close();
        Log.d("madoo: ", "sql_getConfig result## "+ configVo.toString() );
        return configVo;
    }



    ////////////////////////////////////////////////////////////////////////////////
    //기타
    ////////////////////////////////////////////////////////////////////////////////

    //rowid 가져오기
    public int getSelectKey(){
        int no = 0;
        String SQL_last_insert_rowid = "SELECT last_insert_rowid();" ;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(SQL_last_insert_rowid, null);

        while (cursor.moveToNext()) {
            no =  cursor.getInt(0);
        }

        db.close();
        Log.d("madoo: ", "SQL_last_insert_rowid## "+ SQL_last_insert_rowid );
        return no;
    }


    //싱크후 업데이트 inout
    public String updateSync(int inoutNo){

        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        String dt = dateTimeUtil.getDateStr();

        String SQL_updateSync =
                " UPDATE tbl_inout " +
                " SET " +
                "     sync =  '" + dt + "'  " +
                " WHERE inoutNo = " + inoutNo + "; ";


        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_updateSync);

        db.close();
        Log.d("madoo: ", "SQL_updateSync## "+ SQL_updateSync );


        return dt;
    }




    ////////////////////////////////////////////////////////////////////////////////
    //통계 관련
    ////////////////////////////////////////////////////////////////////////////////
    //화폐별 통계
    public List<UnitVo> getListBalanceChart(int tripNo) {

        Log.d("madoo", "리스트 getListBalanceChart()" );

        SQLiteDatabase db = getReadableDatabase();

        String sql_getListBalanceChart =
                " SELECT gt.unitNo, u.unitCode, u.unitName, gt.price, gt.method " +
                        " FROM ( " +
                        "       SELECT unitNo, sum(price) price, method " +
                        "       FROM( " +
                        "               SELECT inoutNo, pUnitNo unitNo, pPrice price, pMethod method " +
                        "               FROM tbl_inout " +
                        "               WHERE price != 0.0 " +
                        "               AND tripNo = " + tripNo  +

                        "               UNION ALL " +

                        "               SELECT inoutNo, mUnitNo unitNo, -1*mPrice price, mMethod method " +
                        "               FROM tbl_inout " +
                        "               WHERE price != 0.0 " +
                        "               AND tripNo = " + tripNo  +
                        "           ) " +
                        "       GROUP BY unitNo, method " +
                        "      ) gt " +
                        "        left outer join tbl_unit u " +
                        "        ON gt.unitNo = u.unitNo " +
                        " ORDER BY gt.unitNo ASC ;" ;


        List<UnitVo> unitList = new ArrayList<UnitVo>();
        Log.d("madoo: ", "sql_getListBalanceChart## "+ sql_getListBalanceChart );
        Cursor cursor = db.rawQuery(sql_getListBalanceChart, null);

        while (cursor.moveToNext()) {

            UnitVo unitVo = new UnitVo();
            unitVo.setUnitNo(cursor.getInt(0));
            unitVo.setUnitCode(cursor.getString(1));
            unitVo.setUnitName(cursor.getString(2));
            unitVo.setPrice(cursor.getDouble(3));
            unitVo.setMethod(cursor.getString(4));

            unitList.add(unitVo);
        }

        db.close();
        Log.d("madoo: ", "sql_getListBalanceChart result## "+ unitList.toString());


        return unitList;
    }


    ////////////////////////////////////////////////////////////////////////////////
    //기타 유틸 초기화관련
    ////////////////////////////////////////////////////////////////////////////////
    //카테고리
    public void initCate() {
        ////////////////////////////////////////
        //카테고리 정리 관리용 예약카테고리(1~10번)
        ////////////////////////////////////////

        //---------------------------------------------------------------
        //카테고리 기본값 등록(1~10번 예약번호)
        CateVo cateVo = new CateVo();
        cateVo.setUseFlag("USE");

        cateVo.setInoutType("IN"); //1번 수입
        cateVo.setCateName("카테고리선택 ▼");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("OUT"); //2번 지출
        cateVo.setCateName("카테고리선택 ▼");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("CHANGE"); //3번 환전
        cateVo.setCateName("카테고리선택 ▼");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("WITHROW"); //4번 인출
        cateVo.setCateName("카테고리선택 ▼");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("ETC"); //5번
        cateVo.setCateName("");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("ETC"); //6번
        cateVo.setCateName("");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("ETC"); //7번
        cateVo.setCateName("");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("ETC");  //8번
        cateVo.setCateName("");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("ETC"); //9번
        cateVo.setCateName("");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setInoutType("ETC"); //10번
        cateVo.setCateName("");
        MainApp.Dao.insertCate(cateVo);

        //---------------------------------------------------------------
        //수입 카테고리
        cateVo.setUseFlag("USE");
        cateVo.setInoutType("OUT");

        cateVo.setCateName("식비");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("숙박");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("교통(원거리)");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("교통(단거리)");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("관광");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("액티비티");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("쇼핑");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("기타");
        MainApp.Dao.insertCate(cateVo);



        //---------------------------------------------------------------
        //지출 카테고리
        cateVo.setUseFlag("USE");
        cateVo.setInoutType("IN");

        cateVo.setCateName("예산");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("판매");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("급여");
        MainApp.Dao.insertCate(cateVo);

        cateVo.setCateName("기타");
        MainApp.Dao.insertCate(cateVo);
    }


    //화폐
    public void initUnit() {
        ////////////////////////////////////////
        //화폐
        ////////////////////////////////////////

        //---------------------------------------------------------------
        //화폐 기본값 등록
        UnitVo unitVo = new UnitVo();

        unitVo.setUnitCode("DEF");  //화폐기본값 1번
        unitVo.setUnitName("화폐선택 ▼");
        MainApp.Dao.insertUnit(unitVo);

        unitVo.setUnitCode("USD");
        unitVo.setUnitName("US 달러");
        MainApp.Dao.insertUnit(unitVo);

        unitVo.setUnitCode("KRW");
        unitVo.setUnitName("원");
        MainApp.Dao.insertUnit(unitVo);

        unitVo.setUnitCode("THB");
        unitVo.setUnitName("태국 바트");
        MainApp.Dao.insertUnit(unitVo);

        unitVo.setUnitCode("LAK");
        unitVo.setUnitName("라오스 킵");
        MainApp.Dao.insertUnit(unitVo);

        unitVo.setUnitCode("VND");
        unitVo.setUnitName("베트남 동");
        MainApp.Dao.insertUnit(unitVo);

        unitVo.setUnitCode("KHR");
        unitVo.setUnitName("캄보디아 릴");
        MainApp.Dao.insertUnit(unitVo);

        unitVo.setUnitCode("EUR");
        unitVo.setUnitName("유럽연합 유로");
        MainApp.Dao.insertUnit(unitVo);
    }
}
