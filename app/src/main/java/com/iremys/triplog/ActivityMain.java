package com.iremys.triplog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.kevinsawicki.http.HttpRequest;
import com.iremys.triplog.core.http.SafeAsyncTask;
import com.iremys.triplog.core.vo.ConfigVo;
import com.iremys.triplog.ui.trip.TripListActivity;

import java.io.File;
import java.net.HttpURLConnection;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{

    private TextView tvUserID; //userID
    private Button btnGoTripList; //여행리스트 가기 버튼



    private EditText etUserID; //아이디입력창
    private Button btnJoin; //회원가입(DB도 세팅됨)

    private LinearLayout llExitIDYes;
    private LinearLayout llExitIDNo;

    private Button btnHttp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위젯생성
        tvUserID = (TextView) findViewById(R.id.tvUserID);
        btnGoTripList = (Button) findViewById(R.id.btnGoTripList);
        etUserID = (EditText) findViewById(R.id.etUserID);
        btnJoin = (Button) findViewById(R.id.btnJoin);
        llExitIDYes = (LinearLayout) findViewById(R.id.llExitIDYes);
        llExitIDNo = (LinearLayout) findViewById(R.id.llExitIDNo);

        //btnHttp = (Button) findViewById(R.id.btnHttp);


        //리스너등록
        tvUserID.setOnClickListener(this);
        btnGoTripList.setOnClickListener(this);
        etUserID.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
        llExitIDYes.setOnClickListener(this);
        llExitIDNo.setOnClickListener(this);
      /*  btnHttp.setOnClickListener(this);*/

        //configDB확인
        try{
            ConfigVo vo = MainApp.Dao.getConfig();
            if(vo.getUserID() == null) {
                //id없으면 회원가입 메세지 출력
                tvUserID.setText("회원가입을 해주세요");

            }else {
                //id 있으면 id출력
                tvUserID.setText(vo.getUserID());
            }
        }catch (Exception e){
            Log.d("madoo", "퍼미션체크");
            tvUserID.setText("회원가입을 해주세요");
        }



    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnJoin:
                Log.d("madoo ", "회원가입 초기화버튼 클릭");
                Log.d("madoo", MainApp.Dao.getDatabaseName());
                File f = new File(MainApp.Dao.getWritableDatabase().getPath());
                if (f.delete()) {
                    Log.d("madoo", "file remove = " + f.getName() + ", 삭제 성공");
                } else {
                    Log.i("banana", "file remove = " + f.getName() + ", 삭제 실패");
                }

                ConfigVo configVo = new ConfigVo();
                configVo.setUserID(etUserID.getText().toString());

                MainApp.Dao.getWritableDatabase();
                MainApp.Dao.close();
                MainApp.Dao.initCate();
                MainApp.Dao.initUnit();
                MainApp.Dao.insertConfig(configVo);

                intent = new Intent(this, ActivityMain.class);
                startActivity(intent);
                break;

            case R.id.btnGoTripList:
                Log.d("madoo", "여행리스트가기버튼 클릭");

                //configDB확인
                ConfigVo vo = MainApp.Dao.getConfig();
                if(vo.getUserID() == null) {
                    //id없으면 회원가입 메세지 출력
                    tvUserID.setText("회원가입을 해주세요");

                }else {
                    //id 있으면 여행리스트로 이동
                    intent = new Intent(this, TripListActivity.class);
                    startActivity(intent);
                }

                break;


/*
            case R.id.btnHttp:
                Log.d("madoo ", "http");




                break;
*/
        }



    }









}
