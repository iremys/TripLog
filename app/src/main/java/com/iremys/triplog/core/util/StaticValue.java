package com.iremys.triplog.core.util;

import android.os.Environment;

public class StaticValue {


    ////////////////////////////////////////////////////////////////////
    //requestCode
    ////////////////////////////////////////////////////////////////////


    //입력 <--> 카테고리 팝업
    public static final int REQUEST_CODE_WRITE_IN_CATE_POPUP = 11;
    public static final int REQUEST_CODE_WRITE_OUT_CATE_POPUP = 12;

    //수정 <--> 카테고리 팝업
    public static final int REQUEST_CODE_EDIT_IN_CATE_POPUP = 21;
    public static final int REQUEST_CODE_EDIT_OUT_CATE_POPUP = 22;


    //입력 <--> 화폐단위 팝업
    public static final int REQUEST_CODE_WRITE_IN_UNIT_POPUP = 110;
    public static final int REQUEST_CODE_WRITE_OUT_UNIT_POPUP = 120;
    public static final int REQUEST_CODE_WRITE_OUT_UNIT_POPUP_CARD = 121;
    public static final int REQUEST_CODE_WRITE_CHANGE_UNIT_POPUP_M = 130;
    public static final int REQUEST_CODE_WRITE_CHANGE_UNIT_POPUP_P = 131;
    public static final int REQUEST_CODE_WRITE_WITHROW_UNIT_POPUP_M = 140;
    public static final int REQUEST_CODE_WRITE_WITHROW_UNIT_POPUP_P = 141;

    //수정 <--> 화폐단위 팝업
    public static final int REQUEST_CODE_EDIT_IN_UNIT_POPUP = 210;
    public static final int REQUEST_CODE_EDIT_OUT_UNIT_POPUP = 220;
    public static final int REQUEST_CODE_EDIT_OUT_UNIT_POPUP_CARD = 221;
    public static final int REQUEST_CODE_EDIT_CHANGE_UNIT_POPUP_M = 230;
    public static final int REQUEST_CODE_EDIT_CHANGE_UNIT_POPUP_P = 231;
    public static final int REQUEST_CODE_EDIT_WITHROW_UNIT_POPUP_M = 240;
    public static final int REQUEST_CODE_EDIT_WITHROW_UNIT_POPUP_P = 241;


    //입력 <--> 지도
    public static final int REQUEST_CODE_WRITE_IN_MAP_POPUP = 1010;
    public static final int REQUEST_CODE_WRITE_OUT_MAP_POPUP = 1020;
    public static final int REQUEST_CODE_WRITE_CHANGE_MAP_POPUP = 1030;
    public static final int REQUEST_CODE_WRITE_WITHROW_MAP_POPUP = 1040;

    //수정 <--> 지도
    public static final int REQUEST_CODE_EDIT_IN_MAP_POPUP = 2010;
    public static final int REQUEST_CODE_EDIT_OUT_MAP_POPUP = 2020;
    public static final int REQUEST_CODE_EDIT_CHANGE_MAP_POPUP = 2030;
    public static final int REQUEST_CODE_EDIT_WITHROW_MAP_POPUP = 2040;



    //입력 <-->갤러리
    public static final int REQUEST_CODE_WRITE_IN_GALLERY_POPUP = 3010;
    public static final int REQUEST_CODE_WRITE_OUT_GALLERY_POPUP = 3020;
    public static final int REQUEST_CODE_WRITE_CHANGE_GALLERY_POPUP = 3030;
    public static final int REQUEST_CODE_WRITE_WITHROW_GALLERY_POPUP = 3040;

    //입력 <-->뷰어
    public static final int REQUEST_CODE_WRITE_IN_IMGVIEWER_POPUP = 3050;
    public static final int REQUEST_CODE_WRITE_OUT_IMGVIEWER_POPUP = 3060;
    public static final int REQUEST_CODE_WRITE_CHANGE_IMGVIEWER_POPUP = 3070;
    public static final int REQUEST_CODE_WRITE_WITHROW_IMGVIEWER_POPUP = 3080;




    //수정 <--> 갤러리
    public static final int REQUEST_CODE_EDIT_IN_GALLERY_POPUP = 4010;
    public static final int REQUEST_CODE_EDIT_OUT_GALLERY_POPUP = 4020;
    public static final int REQUEST_CODE_EDIT_CHANGE_GALLERY_POPUP = 4030;
    public static final int REQUEST_CODE_EDIT_WITHROW_GALLERY_POPUP = 4040;

    //수정 <-->뷰어
    public static final int REQUEST_CODE_EDIT_IN_IMGVIEWER_POPUP = 4050;
    public static final int REQUEST_CODE_EDIT_OUT_IMGVIEWER_POPUP = 4060;
    public static final int REQUEST_CODE_EDIT_CHANGE_IMGVIEWER_POPUP = 4070;
    public static final int REQUEST_CODE_EDIT_WITHROW_IMGVIEWER_POPUP = 4080;



    //입력 <-->카메라
    public static final int REQUEST_CODE_WRITE_IN_CAMERA_POPUP = 5010;
    public static final int REQUEST_CODE_WRITE_OUT_CAMERA_POPUP = 5020;
    public static final int REQUEST_CODE_WRITE_CHANGE_CAMERA_POPUP = 5030;
    public static final int REQUEST_CODE_WRITE_WITHROW_CAMERA_POPUP = 5040;


    //수정 <--> 카메라
    public static final int REQUEST_CODE_EDIT_IN_CAMERA_POPUP = 6010;
    public static final int REQUEST_CODE_EDIT_OUT_CAMERA_POPUP = 6020;
    public static final int REQUEST_CODE_EDIT_CHANGE_CAMERA_POPUP = 6030;
    public static final int REQUEST_CODE_EDIT_WITHROW_CAMERA_POPUP = 6040;






    //이미지 저장위치
    public static final String IMG_DIR =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+
            Environment.DIRECTORY_PICTURES + "/TripLog" ;



    //위치 기본값
    public static final double LAT = 37.565581;
    public static final double LON = 126.977992;

    //카테고리 기본값
    public static final int CATE_DEFAULT_IN = 1; //수입
    public static final int CATE_DEFAULT_OUT = 2; //지출
    public static final int CATE_DEFAULT_CHANGE = 3; //환전
    public static final int CATE_DEFAULT_WITHROW = 4; //인출


    //화폐 기본값
    public static final int UNIT_DEFAULT = 1; //화폐기본값
}
