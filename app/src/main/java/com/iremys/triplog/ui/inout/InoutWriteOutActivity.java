package com.iremys.triplog.ui.inout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.util.DateTimeUtil;
import com.iremys.triplog.core.vo.CateVo;
import com.iremys.triplog.core.vo.FileVo;
import com.iremys.triplog.core.vo.InOutVo;
import com.iremys.triplog.core.vo.TripVo;
import com.iremys.triplog.core.vo.UnitVo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.iremys.triplog.core.util.StaticValue.CATE_DEFAULT_OUT;
import static com.iremys.triplog.core.util.StaticValue.IMG_DIR;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_WRITE_OUT_CATE_POPUP;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_WRITE_OUT_GALLERY_POPUP;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_WRITE_OUT_IMGVIEWER_POPUP;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_WRITE_OUT_MAP_POPUP;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_WRITE_OUT_UNIT_POPUP;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_WRITE_OUT_UNIT_POPUP_CARD;
import static com.iremys.triplog.core.util.StaticValue.UNIT_DEFAULT;

public class InoutWriteOutActivity extends AppCompatActivity implements View.OnClickListener{

    /////////////////////////////////////////////////////////////
    // 위젯변수
    /////////////////////////////////////////////////////////////
    //여행정보
    private TripVo tripVo;


    //입출금타입
    private RadioButton rbtnGoInWrite;
    private RadioButton rbtnGoOutWrite;
    private RadioButton rbtnGoChangeWrite;
    private RadioButton rbtnGoWithrowWrite;


    //사용시간
    private DateTimeUtil dateTimeUtil = new DateTimeUtil(); //날짜 시간 관련 유틸클래스
    private TextView tvUseDate;
    private TextView tvUseTime;


    //국가

    //지출금액, 화폐
    private EditText etMPrice;
    private TextView tvSelectMUnit;
    private UnitVo mUnitVo; //팝업에서 전달 받을때 사용


    //지출방법
    private RadioGroup rgMMethod;

    //사용내역
    private EditText etTitle;

    //카테고리
    private TextView tvSelectCate;
    private CateVo cateVo; //팝업에서 전달 받을때 사용

    //메모
    private EditText etMemo;

    //위치
    private LocationManager locationManager;
    private LinearLayout llGoMap; //지도팝업 호출
    private TextView tvGPSLat;
    private TextView tvGPSLon;
    private Double lat, lon ;

    private TextView tvGPSInit;


    //사진
    private GridView gvImglist;
    private TextView tvGoCamera;
    private TextView tvGoGallery;

    private List<Uri> imgList;
    private InoutImageAdapter inoutImageAdapter;


    //저장버튼
    private Button btnInSave;


    //***************************************************************//
    //지출>카드에만 적용
    private LinearLayout llCardBox;
    private EditText etCardPrice;
    private TextView tvSelectCardUnit;
    private UnitVo cardUnitVo; //팝업에서 전달 받을때 사용


    private RadioButton rbtMCash;
    private RadioButton rbtnMCard;
    //***************************************************************//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inout_write_out_activity);

        //////////////////////////////////////////////////////////////////////////////////
        //여행정보/////////////////////////////////////////////////////////////////////
        tripVo = (TripVo)getIntent().getSerializableExtra("tripVo");

        //////////////////////////////////////////////////////////////////////////////////
        //액션바/////////////////////////////////////////////////////////////////////
        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setTitle("지출 등록") ;


        //////////////////////////////////////////////////////////////////////////////////
        //입출금타입/////////////////////////////////////////////////////////////////////
        rbtnGoInWrite = (RadioButton) findViewById(R.id.rbtnGoInWrite);
        rbtnGoOutWrite = (RadioButton) findViewById(R.id.rbtnGoOutWrite);
        rbtnGoChangeWrite = (RadioButton) findViewById(R.id.rbtnGoChangeWrite);
        rbtnGoWithrowWrite = (RadioButton) findViewById(R.id.rbtnGoWithrowWrite);

        //입출금타입 리스너
        rbtnGoInWrite.setOnClickListener(this);
        rbtnGoOutWrite.setOnClickListener(this);
        rbtnGoChangeWrite.setOnClickListener(this);
        rbtnGoWithrowWrite.setOnClickListener(this);


        ///////////////////////////////////////////////////////////////////////////////
        //사용시간/////////////////////////////////////////////////////////////////////
        tvUseTime = (TextView) findViewById(R.id.tvUseTime);
        tvUseDate = (TextView) findViewById(R.id.tvUseDate);

        //사용시간 출력
        tvUseDate.setText(dateTimeUtil.getDateStr());
        tvUseTime.setText(dateTimeUtil.getTimeStr());

        //사용시간 리스너
        tvUseDate.setOnClickListener(this);
        tvUseTime.setOnClickListener(this);


        ///////////////////////////////////////////////////////////////////////////////
        //지출금액//////////////////////////////////////////////////////////////////////////
        etMPrice = (EditText) findViewById(R.id.etMPrice);
        tvSelectMUnit = (TextView) findViewById(R.id.tvSelectMUnit);

        //지출금액 리스너
        tvSelectMUnit.setOnClickListener(this);


        ///////////////////////////////////////////////////////////////////////////////
        //지출방법//////////////////////////////////////////////////////////////////////////
        rgMMethod = (RadioGroup) findViewById(R.id.rgMMethod); //현금(CASH), 카드(BANK)


        ///////////////////////////////////////////////////////////////////////////////
        //사용내역//////////////////////////////////////////////////////////////////////////
        etTitle = (EditText) findViewById(R.id.etTitle);


        ///////////////////////////////////////////////////////////////////////////////
        //카테고리//////////////////////////////////////////////////////////////////////////
        tvSelectCate = (TextView) findViewById(R.id.tvSelectCate);

        //카테고리 리스너
        tvSelectCate.setOnClickListener(this);


        ///////////////////////////////////////////////////////////////////////////////
        //메모//////////////////////////////////////////////////////////////////////////
        etMemo = (EditText) findViewById(R.id.etMemo);


        ///////////////////////////////////////////////////////////////////////////////
        //위치정보//////////////////////////////////////////////////////////////////////////
        llGoMap = (LinearLayout) findViewById(R.id.llGoMap);
        tvGPSLat = (TextView) findViewById(R.id.tvGPSLat);
        tvGPSLon = (TextView) findViewById(R.id.tvGPSLon);
        tvGPSInit = (TextView) findViewById(R.id.tvGPSInit);

        //위치정보 리스너
        llGoMap.setOnClickListener(this); //클릭하면 지도화면으로 이동

        startLocationService(); //GPS 작동


        //위치정보 초기화(필요없을때)
        tvGPSInit.setOnClickListener(this);

        ///////////////////////////////////////////////////////////////////////////////
        //사진정보//////////////////////////////////////////////////////////////////////////
        gvImglist = (GridView) findViewById(R.id.gvImglist);
        tvGoCamera = (TextView) findViewById(R.id.tvGoCamera);
        tvGoGallery = (TextView) findViewById(R.id.tvGoGallery);

        //사진정보 리스너
        tvGoCamera.setOnClickListener(this); //클릭하면 카메라화면으로 이동
        tvGoGallery.setOnClickListener(this); //클릭하면 지도화면으로 이동

        imgList = new ArrayList<Uri>();
        //어댑터 생성
        inoutImageAdapter = new InoutImageAdapter(this  );


        checkPermission();


        //이미지 클릭할때
        gvImglist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = (Uri)parent.getItemAtPosition(position);

                Log.d("madoo", "uri-> "   +  uri.toString());
                Log.d("madoo", "position-> "   +  position);
                Intent intent = new Intent(InoutWriteOutActivity.this, InoutImageViewerActivity.class);
                intent.putExtra("uri", uri);
                intent.putExtra("index", position);
                startActivityForResult(intent, REQUEST_CODE_WRITE_OUT_IMGVIEWER_POPUP);
            }
        });


        ///////////////////////////////////////////////////////////////////////////////
        //저장버튼//////////////////////////////////////////////////////////////////////////
        btnInSave = (Button) findViewById(R.id.btnOutSave);

        btnInSave.setOnClickListener(this);


        ///////////////////////////////////////////////////////////////////////////////
        //카드 화폐단위//////////////////////////////////////////////////////////////////////////
        //***************************************************************//
        //지출>카드에만 적용
        llCardBox = (LinearLayout) findViewById(R.id.llCardBox);
        etCardPrice = (EditText) findViewById(R.id.etCardPrice);
        tvSelectCardUnit = (TextView) findViewById(R.id.tvSelectCardUnit);

        rbtMCash = (RadioButton) findViewById(R.id.rbtMCash);
        rbtnMCard = (RadioButton) findViewById(R.id.rbtnMCard);

        tvSelectCardUnit.setOnClickListener(this);
        rbtMCash.setOnClickListener(this);
        rbtnMCard.setOnClickListener(this);


        llCardBox.setVisibility(View.GONE);
        //***************************************************************//


    }


    /////////////////////////////////////////////////////////////
    //클릭이벤트
    /////////////////////////////////////////////////////////////
    @Override //클릭이벤트
    public void onClick(View view) {
        Intent intent ;
        switch (view.getId()) {
            case R.id.rbtnGoInWrite: //수입등록 이동
                Log.d("madoo ", "수입등록 이동");
                intent = new Intent(this, InoutWriteInActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();
                break;

            case R.id.rbtnGoOutWrite: //지출등록 클릭
                Log.d("madoo ", "지출등록 이동");
                intent = new Intent(this, InoutWriteOutActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();
                break;

            case R.id.rbtnGoChangeWrite: //환전등록 클릭
                Log.d("madoo ", "환전등록 이동");
                intent = new Intent(this, InoutWriteChangeActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();
                break;

            case R.id.rbtnGoWithrowWrite: //인출등록 클릭
                Log.d("madoo ", "인출등록 이동");
                intent = new Intent(this, InoutWriteWithrowActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();
                break;

            case R.id.tvUseDate: //날짜팝업
                Log.d("madoo ", "날짜클릭");
                new DatePickerDialog(
                        this,
                        dateSetListener,
                        dateTimeUtil.getYear(),
                        dateTimeUtil.getMonth()-1,
                        dateTimeUtil.getDay()).show();
                break;

            case R.id.tvUseTime: //시간팝업
                Log.d("madoo ", "시간클릭");
                new TimePickerDialog(
                        this,
                        timeSetListener,
                        dateTimeUtil.getHour(),
                        dateTimeUtil.getMinute(),
                        true).show();
                break;

            case R.id.tvSelectMUnit: //화폐팝업
                Log.d("madoo ", "화폐단위 선택 클릭");
                intent = new Intent(this, InoutUnitActivity.class);
                startActivityForResult(intent, REQUEST_CODE_WRITE_OUT_UNIT_POPUP);
                break;

            //지출방법
            //내역

            case R.id.tvSelectCate: //카테고리팝업
                Log.d("madoo ", "카테고리 선택 클릭");
                intent = new Intent(this, InoutCateActivity.class);
                intent.putExtra("inoutType", "OUT");
                startActivityForResult(intent, REQUEST_CODE_WRITE_OUT_CATE_POPUP);
                break;

            case R.id.llGoMap: //지도팝업 호출
                Log.d("madoo ", "지도이동 선택 클릭");
                intent = new Intent(this, InoutMapActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("title", etTitle.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_WRITE_OUT_MAP_POPUP);
                break;

            case R.id.tvGPSInit: //GPS초기화 호출
                Log.d("madoo ", "GPS 초기화");
                intent = new Intent(this, InoutMapActivity.class);
                lat = null;
                lon = null;
                tvGPSLat.setText("");
                tvGPSLon.setText("");
                break;

            case R.id.tvGoCamera: //카메라팝업 호출
                Log.d("madoo ", "카메라 선택 클릭");
                Toast.makeText(this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvGoGallery: //갤러리팝업 호출
                Log.d("madoo ", "갤러리 선택 클릭");
                intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_WRITE_OUT_GALLERY_POPUP);
                break;

            case R.id.btnOutSave: //지출등록 저장 버튼 클릭
                Log.d("madoo ", "지출등록 저장 버튼 클릭");

                //저장값 정리///////////////////////////////////////////////////
                InOutVo inoutVo = new InOutVo();

                //여행번호
                inoutVo.setTripNo(tripVo.getTripNo());

                //사용구분
                inoutVo.setInoutType("OUT");

                //사용날짜 사용시간
                inoutVo.setUseDate(tvUseDate.getText() + " "  + tvUseTime.getText());



                //***************************************************************//
                //지출>카드에만 적용
                //금액
                String cardPrice = etCardPrice.getText().toString();
                if(cardPrice == null || cardPrice.equals("")){
                    inoutVo.setCardPrice(0);
                }else {
                    inoutVo.setCardPrice(Double.parseDouble(cardPrice));
                }


                //화폐
                if(cardUnitVo !=  null) {
                    inoutVo.setCardUnitNo(cardUnitVo.getUnitNo());
                }else {
                    inoutVo.setCardUnitNo(UNIT_DEFAULT);
                }
                //***************************************************************//


                //금액
                String mPrice = etMPrice.getText().toString();
                if(mPrice == null || mPrice.equals("")){
                    inoutVo.setmPrice(0);
                }else {
                    inoutVo.setmPrice(Double.parseDouble(mPrice));
                }


                //화폐
                if(mUnitVo !=  null) {
                    inoutVo.setmUnitNo(mUnitVo.getUnitNo());
                }else {
                    inoutVo.setmUnitNo(UNIT_DEFAULT);
                }



                //지출방법
                RadioButton rb = (RadioButton) findViewById(rgMMethod.getCheckedRadioButtonId());
                if("현금".equals(rb.getText().toString())){
                    inoutVo.setmMethod("CASH");
                }else if("카드".equals(rb.getText().toString())){
                    inoutVo.setmMethod("BANK");
                }

                //사용내역
                inoutVo.setTitle(etTitle.getText().toString());

                //카테고리
                if(cateVo !=  null){
                    inoutVo.setCateNo(cateVo.getCateNo());
                }else {
                    inoutVo.setCateNo(CATE_DEFAULT_OUT);
                }

                //메모
                inoutVo.setMemo(etMemo.getText().toString());

                //위도 경도
                if(lat != null && lon != null) {
                    inoutVo.setLat(lat);
                    inoutVo.setLon(lon);
                }

                //싱크 기본값
                inoutVo.setSync("-1");

                //글저장
                Log.d("madoo ", inoutVo.toString());
                int inoutNo = MainApp.Dao.insertInout(inoutVo);


                //파일저장
                Bitmap bitmap;

                for(Uri uri : imgList){
                    try {

                        String imagePath = getRealPathFromURI(uri);
                        Log.d("madoo ", "uri-->imagePath: " + inoutVo.toString());
                        ExifInterface exif = new ExifInterface(imagePath);
                        Log.d("madoo ", "ExifInterface(): " + exif.toString());
                        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        Log.d("madoo ", "exifOrientation: " + exifOrientation);
                        int degree = exifOrientationToDegrees(exifOrientation);
                        Log.d("madoo ", "degree: " + degree);




                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                        bitmap = rotateImage(bitmap, degree);


                        saveBitmaptoJpeg(bitmap, inoutNo);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                finish();

                break;


            //***************************************************************//
            //지출>카드에만 적용
            case R.id.tvSelectCardUnit: //화폐팝업
                Log.d("madoo ", "화폐단위 선택 클릭");
                intent = new Intent(this, InoutUnitActivity.class);
                startActivityForResult(intent, REQUEST_CODE_WRITE_OUT_UNIT_POPUP_CARD);
                break;

            case R.id.rbtMCash: //지불방법 현금클릭
                Log.d("madoo ", "지불방법 현금클릭");
                etCardPrice.setText("");
                tvSelectCardUnit.setText("화폐선택 ▼");
                cardUnitVo.setUnitNo(UNIT_DEFAULT);
                llCardBox.setVisibility(View.GONE);
                break;

            case R.id.rbtnMCard: //지불방법 카드클릭
                Log.d("madoo ", "지불방법 현금클릭");
                llCardBox.setVisibility(View.VISIBLE);
                break;

            //***************************************************************//


        }
    }

    /////////////////////////////////////////////////////////////
    //팝업 호출결과
    /////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_WRITE_OUT_UNIT_POPUP: //화폐팝업 결과
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    mUnitVo=(UnitVo)data.getSerializableExtra("unitVo");
                    Log.d("madoo", "선택한Unit "+mUnitVo.toString());
                    tvSelectMUnit.setText(mUnitVo.getUnitName());
                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;

            case REQUEST_CODE_WRITE_OUT_CATE_POPUP: //카테고리팝업 결과
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    cateVo=(CateVo)data.getSerializableExtra("cateVo");
                    Log.d("madoo", "선택한Cate "+cateVo.toString());
                    tvSelectCate.setText(cateVo.getCateName());
                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;

            case REQUEST_CODE_WRITE_OUT_MAP_POPUP: //지도팝업 결과
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    lat = data.getDoubleExtra("lat", 0);
                    lon = data.getDoubleExtra("lon", 0);

                    tvGPSLat.setText("위도:  "+lat);
                    tvGPSLon.setText("경도:  "+lon);
                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;


            case REQUEST_CODE_WRITE_OUT_GALLERY_POPUP: //갤러리팝업 결과 사진선택
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    try {
                        Uri uri = (Uri)data.getData();
                        imgList.add(uri);

                        //여행정보리스트 어댑터에 추가
                        inoutImageAdapter.clear();
                        inoutImageAdapter.addAll(imgList);
                        Log.d("madoo", imgList.toString());

                        //리스트뷰에 장착
                        gvImglist.setAdapter(inoutImageAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;

            case REQUEST_CODE_WRITE_OUT_IMGVIEWER_POPUP: //이미지뷰어-->삭제
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    int index = data.getIntExtra("index", -1);
                    imgList.remove(index);

                    //여행정보리스트 어댑터에 추가
                    inoutImageAdapter.clear();
                    inoutImageAdapter.addAll(imgList);
                    Log.d("madoo", imgList.toString());

                    //리스트뷰에 장착
                    gvImglist.setAdapter(inoutImageAdapter);


                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;

            //***************************************************************//
            //지출>카드에만 적용
            case REQUEST_CODE_WRITE_OUT_UNIT_POPUP_CARD: //화폐팝업 결과
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    cardUnitVo=(UnitVo)data.getSerializableExtra("unitVo");
                    Log.d("madoo", "선택한Unit "+cardUnitVo.toString());
                    tvSelectCardUnit.setText(cardUnitVo.getUnitName());
                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;
            //***************************************************************//
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //날자 팝업관련

    //Date Picker
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String str = dateTimeUtil.getDateStr(year, monthOfYear+1,  dayOfMonth);
            tvUseDate.setText(str);
        }
    };

    //Time Picker
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            String str = dateTimeUtil.getTimeStr(hourOfDay, minute);
            tvUseTime.setText(str);
        }
    };


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //위치 관련
    private void startLocationService() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("madoo", "퍼미션 없음");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();

            tvGPSLat.setText("위도:  "+lat);
            tvGPSLon.setText("경도:  "+lon);

            Log.d("madoo", "lat " + lat);
            Log.d("madoo", "log " + lon);

            locationManager.removeUpdates(locationListener); //1번만 값 받음
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //첨부파일 관련
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public void checkPermission(){
        int permissioninfo = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissioninfo == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"SDCard 쓰기 권한 있음",Toast.LENGTH_SHORT).show();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getApplicationContext(),"권한의 필요성 설명",Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String str = null;
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                str = "SD Card 쓰기권한 승인";
            } else {
                str = "SD Card 쓰기권한 거부";
            }

            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //파일저장 +  DB저장
    public void saveBitmaptoJpeg(Bitmap bitmap, int pNo){

        DateTimeUtil dateTimeUtil = new DateTimeUtil();


        String ex_storage =IMG_DIR;
        String foler_name = "/"+dateTimeUtil.getDateStr()+"/";
        String file_name = pNo+"_"+System.currentTimeMillis ( )+".jpg";
        String string_path = ex_storage+foler_name;


        //파일저장
        File file_path = new File(string_path);
        Log.d("madoo", "file_path============================> "+file_path.toString());
        if(!file_path.isDirectory()){
            file_path.mkdirs();
            Log.d("madoo", "mkdirs()============================>");
        }

        try{
            FileOutputStream out = new FileOutputStream(string_path+file_name);

            int height=bitmap.getHeight();
            int width=bitmap.getWidth();

            int maxResolution = 960;
            int newWidth = width;
            int newHeight = height;
            float rate = 0.0f;

            if(width > height)
            {
                if(maxResolution < width)
                {
                    rate = maxResolution / (float) width;
                    newHeight = (int) (height * rate);
                    newWidth = maxResolution;
                }
            }
            else
            {
                if(maxResolution < height)
                {
                    rate = maxResolution / (float) height;
                    newWidth = (int) (width * rate);
                    newHeight = maxResolution;
                }
            }




            bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.close();
        }catch(FileNotFoundException  exception){
            Log.e("FileNotFoundException", exception.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("madoo", "bitmap.getWidth()============================> "+bitmap.getWidth());


        //DB저장
        FileVo fileVo = new FileVo();
        fileVo.setInoutNo(pNo);
        fileVo.setSaveFileName(file_name);
        fileVo.setFilePath(string_path+file_name);

        MainApp.Dao.insertFile(fileVo);
        Log.d("madoo", "FileVo============================> "+fileVo.toString());

    }

    //특정폴더 전체삭제하기
    public static void removeDir(String mRootPath) {
        File file = new File(mRootPath);
        File[] childFileList = file.listFiles();

        if(childFileList != null){
            for(File childFile : childFileList)
            {
                if(childFile.isDirectory()) {
                    removeDir(childFile.getAbsolutePath());    //하위 디렉토리
                }
                else {
                    childFile.delete();    //하위 파일
                }
            }

            file.delete();    //root 삭제
        }

    }


    //uri --> filpath
    public String getRealPathFromURI(Uri contentUri) {
        String path;
        String[] tmpArr = contentUri.toString().split(":");
        if("content".equals(tmpArr[0])){
            String[] proj = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
            cursor.moveToNext();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            Uri uri = Uri.fromFile(new File(path));

            Log.d("madoo", "getRealPathFromURI(), path : " + uri.toString());

            cursor.close();
        }else {

            path = contentUri.toString().split("file:///")[1];
        }

        return path;
    }



    //exif 회전값 구하기
    public int exifOrientationToDegrees(int exifOrientation) {
        if(exifOrientation ==ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if(exifOrientation ==ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if(exifOrientation ==ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        } return 0;
    }


    public Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


}
