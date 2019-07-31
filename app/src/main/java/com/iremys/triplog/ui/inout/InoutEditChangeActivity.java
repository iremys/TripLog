package com.iremys.triplog.ui.inout;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import com.iremys.triplog.core.vo.FileVo;
import com.iremys.triplog.core.vo.InOutVo;
import com.iremys.triplog.core.vo.UnitVo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.iremys.triplog.core.util.StaticValue.IMG_DIR;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_EDIT_CHANGE_GALLERY_POPUP;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_EDIT_CHANGE_IMGVIEWER_POPUP;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_EDIT_CHANGE_MAP_POPUP;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_EDIT_CHANGE_UNIT_POPUP_M;
import static com.iremys.triplog.core.util.StaticValue.REQUEST_CODE_EDIT_CHANGE_UNIT_POPUP_P;


public class InoutEditChangeActivity extends AppCompatActivity implements View.OnClickListener{

    /////////////////////////////////////////////////////////////
    // 위젯변수
    /////////////////////////////////////////////////////////////
    //사용내용정보
    private int inoutNo;  //DB호출용
    private InOutVo inoutVo; //쿼리 결과

    //입출금타입
    private TextView tvInoutType;

    //사용시간
    private DateTimeUtil dateTimeUtil = new DateTimeUtil();  //날짜 시간 관련 유틸클래스
    private TextView tvUseDate;
    private TextView tvUseTime;

    //국가

    //지출금액, 화폐
    private EditText etMPrice;
    private TextView tvSelectMUnit;
    private UnitVo mUnitVo; //팝업에서 전달 받을때 사용

    //받은금액, 화폐
    private EditText etPPrice;
    private TextView tvSelectPUnit;
    private UnitVo pUnitVo; //팝업에서 전달 받을때 사용

    //사용내역(환전소 명)
    private EditText etTitle;

    //메모
    private EditText etMemo;

    //위치
    private LinearLayout llGoMap; //지도팝업 호출
    private TextView tvGPSLat;
    private TextView tvGPSLon;

    private TextView tvGPSInit;


    //파일
    private GridView gvImglist;
    private TextView tvGoCamera;
    private TextView tvGoGallery;

    private List<Uri> imgList;
    private InoutImageAdapter inoutImageAdapter;



    //삭제버튼
    private Button btnChangeDel;

    //저장버튼
    private Button btnChangeEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inout_edit_change_activity);

        Log.d("madoo: ", "inoutNo##  "+ inoutNo );

        //////////////////////////////////////////////////////////////////////////////////
        //데이터 조회 /////////////////////////////////////////////////////////////////////
        inoutNo = getIntent().getIntExtra("inoutNo", -1);
        inoutVo = MainApp.Dao.getInout(inoutNo);


        //////////////////////////////////////////////////////////////////////////////////
        //액션바/////////////////////////////////////////////////////////////////////
        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setTitle("환전 수정") ;

        //////////////////////////////////////////////////////////////////////////////////
        //입출금타입/////////////////////////////////////////////////////////////////////
        tvInoutType = (TextView) findViewById(R.id.tvInoutType);

        //입출금타입 출력
        if("CHANGE".equals(inoutVo.getInoutType())){
            tvInoutType.setText("환전");
        }else {
            tvInoutType.setText("타입오류 관리자 문의");
        }

        ///////////////////////////////////////////////////////////////////////////////
        //사용시간/////////////////////////////////////////////////////////////////////
        tvUseTime = (TextView) findViewById(R.id.tvUseTime);
        tvUseDate = (TextView) findViewById(R.id.tvUseDate);

        //사용시간 출력
        tvUseDate.setText(dateTimeUtil.getDateStr(inoutVo.getUseDate()));
        tvUseTime.setText(dateTimeUtil.getTimeStr(inoutVo.getUseDate()));

        //사용시간 리스너
        tvUseDate.setOnClickListener(this);
        tvUseTime.setOnClickListener(this);


        ///////////////////////////////////////////////////////////////////////////////
        //지불금액//////////////////////////////////////////////////////////////////////////
        etMPrice = (EditText) findViewById(R.id.etMPrice);
        tvSelectMUnit = (TextView) findViewById(R.id.tvSelectMUnit);

        //지출금액 출력
        etMPrice.setText(""+inoutVo.getmPrice()); //금액을 양수로 변경해서 화면에 출력
        tvSelectMUnit.setText(inoutVo.getmUnitName());

        //지출금액 리스너
        tvSelectMUnit.setOnClickListener(this);


        ///////////////////////////////////////////////////////////////////////////////
        //받은금액//////////////////////////////////////////////////////////////////////////
        etPPrice = (EditText) findViewById(R.id.etPPrice);
        tvSelectPUnit = (TextView) findViewById(R.id.tvSelectPUnit);

        //받은금액 출력
        etPPrice.setText(""+inoutVo.getpPrice());
        tvSelectPUnit.setText(inoutVo.getpUnitName());

        //받은금액 리스너
        tvSelectPUnit.setOnClickListener(this);


        ///////////////////////////////////////////////////////////////////////////////
        //사용내역(환전소명)//////////////////////////////////////////////////////////////////////////////////////////////////////
        etTitle = (EditText) findViewById(R.id.etTitle);
        etTitle.setText(inoutVo.getTitle());


        ///////////////////////////////////////////////////////////////////////////////
        //메모//////////////////////////////////////////////////////////////////////////
        etMemo = (EditText) findViewById(R.id.etMemo);

        //메모 출력
        etMemo.setText(inoutVo.getMemo());

        ///////////////////////////////////////////////////////////////////////////////
        //위치정보//////////////////////////////////////////////////////////////////////////
        llGoMap = (LinearLayout) findViewById(R.id.llGoMap);
        tvGPSLat = (TextView) findViewById(R.id.tvGPSLat);
        tvGPSLon = (TextView) findViewById(R.id.tvGPSLon);
        tvGPSInit = (TextView) findViewById(R.id.tvGPSInit);

        //위치정보 출력
        tvGPSLat.setText(""+inoutVo.getLat());
        tvGPSLon.setText(""+inoutVo.getLon());

        //위치정보 리스너
        llGoMap.setOnClickListener(this);

        //위치정보 초기화(필요없을때)
        tvGPSInit.setOnClickListener(this);

        ///////////////////////////////////////////////////////////////////////////////
        //사진정보//////////////////////////////////////////////////////////////////////////

        gvImglist = (GridView) findViewById(R.id.gvImglist);
        tvGoCamera = (TextView) findViewById(R.id.tvGoCamera);
        tvGoGallery = (TextView) findViewById(R.id.tvGoGallery);

        //사진정보 출력
        //-사진정보에서 uri정보만 추추
        List<FileVo> fileList = MainApp.Dao.getListFile(inoutVo.getInoutNo());
        Log.d("madoo ", "fileList-->" + fileList.toString());

        imgList  = new ArrayList<Uri>();

        for(FileVo vo : fileList){
            imgList.add(Uri.fromFile(new File(vo.getFilePath())));
        }
        Log.d("madoo ", "imgList-->" + imgList.toString());

        //-어댑터 생성
        inoutImageAdapter = new InoutImageAdapter(this  );

        //-여행정보리스트 어댑터에 추가
        inoutImageAdapter.clear();
        inoutImageAdapter.addAll(imgList);

        //-리스트뷰에 장착
        gvImglist.setAdapter(inoutImageAdapter);


        //이미지 리스너
        tvGoCamera.setOnClickListener(this);
        tvGoGallery.setOnClickListener(this);

        checkPermission();

        //이미지 클릭할때
        gvImglist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = (Uri)parent.getItemAtPosition(position);

                Log.d("madoo", "uri-> "   +  uri.toString());
                Log.d("madoo", "position-> "   +  position);
                Intent intent = new Intent(InoutEditChangeActivity.this, InoutImageViewerActivity.class);
                intent.putExtra("uri", uri);
                intent.putExtra("index", position);
                startActivityForResult(intent, REQUEST_CODE_EDIT_CHANGE_IMGVIEWER_POPUP);
            }
        });



        ///////////////////////////////////////////////////////////////////////////////
        //저장버튼//////////////////////////////////////////////////////////////////////////
        btnChangeEdit = (Button) findViewById(R.id.btnChangeEdit);

        btnChangeEdit.setOnClickListener(this);

        ///////////////////////////////////////////////////////////////////////////////
        //삭제버튼//////////////////////////////////////////////////////////////////////////
        btnChangeDel = (Button) findViewById(R.id.btnChangeDel);

        btnChangeDel.setOnClickListener(this);

    }

    /////////////////////////////////////////////////////////////
    //클릭이벤트
    /////////////////////////////////////////////////////////////
    @Override  //클릭이벤트
    public void onClick(View view) {
        Intent intent ;
        switch (view.getId()) {
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

            case R.id.tvSelectMUnit: //화폐팝업 마이너스
                Log.d("madoo ", "화폐단위 선택 클릭");
                intent = new Intent(this, InoutUnitActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EDIT_CHANGE_UNIT_POPUP_M);
                break;

            case R.id.tvSelectPUnit: //화폐팝업 플러스
                Log.d("madoo ", "화폐단위 선택 클릭");
                intent = new Intent(this, InoutUnitActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EDIT_CHANGE_UNIT_POPUP_P);
                break;

            case R.id.llGoMap: //지도팝업 호출
                Log.d("madoo ", "지도이동 선택 클릭");
                intent = new Intent(this, InoutMapActivity.class);
                intent.putExtra("lat", inoutVo.getLat());
                intent.putExtra("lon", inoutVo.getLon());
                intent.putExtra("title", etTitle.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_EDIT_CHANGE_MAP_POPUP);
                break;

            case R.id.tvGPSInit: //GPS초기화 호출
                Log.d("madoo ", "GPS 초기화");
                intent = new Intent(this, InoutMapActivity.class);

                inoutVo.setLat(0);
                inoutVo.setLon(0);

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
                startActivityForResult(intent, REQUEST_CODE_EDIT_CHANGE_GALLERY_POPUP);
                break;


            case R.id.btnChangeEdit: //환전등록 수정 버튼 클릭
                Log.d("madoo ", "환전등록 수정 버튼 클릭");
                //수정값 정리///////////////////////////////////////////////////
                //쿼리로 받은 inoutVo 에 값을 세팅한다.

                //여행번호
                //사용구분

                //사용날짜 사용시간
                inoutVo.setUseDate(tvUseDate.getText() + " "  + tvUseTime.getText());

                //지불금액
                String mPrice = etMPrice.getText().toString();
                if(mPrice == null || mPrice.equals("")){
                    inoutVo.setmPrice(0);
                }else {
                    inoutVo.setmPrice(Double.parseDouble(mPrice));
                }

                //지불금액 화폐
                if(mUnitVo != null){ //선택을 안하면 기존값 그대로
                    inoutVo.setmUnitNo(mUnitVo.getUnitNo());
                }

                //받은금액
                String pPrice = etPPrice.getText().toString();
                if(pPrice == null || pPrice.equals("")){
                    inoutVo.setpPrice(0);
                }else {
                    inoutVo.setpPrice(Double.parseDouble(pPrice));
                }


                //받은금액 화폐
                if(pUnitVo != null){ //선택을 안하면 기존값 그대로
                    inoutVo.setpUnitNo(pUnitVo.getUnitNo());
                }

                //사용내역(환전소 명)
                inoutVo.setTitle(etTitle.getText().toString());

                //메모
                inoutVo.setMemo(etMemo.getText().toString());

                //위도 경도
                //onActivityResult 결과에서 화면출력과 inoutVo 에 바로 세팅한다.

                //싱크 기본값
                inoutVo.setSync("-1");

                //글수정
                Log.d("madoo ", "수정된 inout change" + inoutVo.toString());
                MainApp.Dao.updateInout(inoutVo);

                //파일수정
                Bitmap bitmap;

                //파일삭제

                //DB삭제
                MainApp.Dao.deleteFile(inoutNo);
                Log.d("madoo ", "imgList" + imgList.toString());
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
                        Log.d("madoo ", "bitmap width" + bitmap.getWidth());

                        bitmap = rotateImage(bitmap, degree);

                        saveBitmaptoJpeg(bitmap, inoutVo.getInoutNo());
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

            case R.id.btnChangeDel: //수입등록 삭제 버튼 클릭
                Log.d("madoo ", "수입등록 삭제 버튼 클릭");
                MainApp.Dao.deleteInout(inoutNo);
                finish();
                break;

        }
    }


    /////////////////////////////////////////////////////////////
    //팝업 호출결과
    /////////////////////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_EDIT_CHANGE_UNIT_POPUP_M: //화폐팝업 결과 마이너스
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    mUnitVo = (UnitVo) data.getSerializableExtra("unitVo");
                    Log.d("madoo", "선택한Unit " + mUnitVo.toString());
                    tvSelectMUnit.setText(mUnitVo.getUnitName());
                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;

            case REQUEST_CODE_EDIT_CHANGE_UNIT_POPUP_P: //화폐팝업 결과 플러스
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    pUnitVo = (UnitVo) data.getSerializableExtra("unitVo");
                    Log.d("madoo", "선택한Unit " + pUnitVo.toString());
                    tvSelectPUnit.setText(pUnitVo.getUnitName());
                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;

            case REQUEST_CODE_EDIT_CHANGE_MAP_POPUP: //지도팝업 결과
                if (resultCode == RESULT_OK) {
                    Log.d("madoo ", "성공");
                    inoutVo.setLat(data.getDoubleExtra("lat", 0));
                    inoutVo.setLon(data.getDoubleExtra("lon", 0));

                    tvGPSLat.setText("위도:  " + inoutVo.getLat());
                    tvGPSLon.setText("경도:  " + inoutVo.getLon());
                } else {   // RESULT_CANCEL
                    Log.d("madoo ", "실패");
                }
                break;


            case REQUEST_CODE_EDIT_CHANGE_GALLERY_POPUP: //갤러리팝업 결과 사진선택
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


            case REQUEST_CODE_EDIT_CHANGE_IMGVIEWER_POPUP: //이미지뷰어-->삭제
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

        }

}



    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //날짜 시간 팝업관련
    //////////////////////////////////////////////////////////////////////////////////////////////////////
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

