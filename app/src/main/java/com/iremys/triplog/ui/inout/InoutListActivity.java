package com.iremys.triplog.ui.inout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.InOutVo;
import com.iremys.triplog.core.vo.TripVo;
import com.iremys.triplog.ui.balance.BalanceListActivity;
import com.iremys.triplog.ui.marker.MarkerMapActivity;
import com.iremys.triplog.ui.trip.TripListActivity;
import com.iremys.triplog.ui.trip.TripWriteActivity;

import java.util.List;

public class InoutListActivity extends AppCompatActivity implements View.OnClickListener{

    /////////////////////////////////////////////////////////////
    // 위젯변수
    /////////////////////////////////////////////////////////////
    //여행정보
    public TripVo tripVo;


    private Button btnGoInout;
    private Button btnGoBalance;
    private Button btnGoMap;
    private Button btnGoOutWrite;

    private InoutListAdapter inoutListAdapter;

    //스크롤 위치 저장 변수
    private Parcelable state;

    private ListView lvInoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inout_list_activity);

        //////////////////////////////////////////////////////////////////////////////////
        //여행정보/////////////////////////////////////////////////////////////////////
        tripVo = (TripVo)getIntent().getSerializableExtra("tripVo");


        //////////////////////////////////////////////////////////////////////////////////
        //액션바/////////////////////////////////////////////////////////////////////
        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setTitle("가계부 리스트") ;


        //위젯생성
        btnGoInout = (Button) findViewById(R.id.btnGoInout);
        btnGoBalance = (Button) findViewById(R.id.btnGoBalance);
        btnGoMap = (Button) findViewById(R.id.btnGoMap);
        btnGoOutWrite = (Button) findViewById(R.id.btnGoOutWrite);
        lvInoutList = (ListView) findViewById(R.id.lvInoutList);


        //리스너등록
        btnGoInout.setOnClickListener(this);
        btnGoBalance.setOnClickListener(this);
        btnGoMap.setOnClickListener(this);
        btnGoOutWrite.setOnClickListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("madoo ", "가계부리스트 업데이트");

        //가계부리스트정보 가져오기
        List<InOutVo> inOutList =  MainApp.Dao.getListInout(tripVo.getTripNo());

        //어댑터 생성
        inoutListAdapter = new InoutListAdapter(this  );

        //어댑터 클리어
        inoutListAdapter.clear();


        //여행정보리스트 어댑터에 추가
        inoutListAdapter.addAll(inOutList);
        Log.d("madoo", inOutList.toString());

        //리스트뷰에 장착
        lvInoutList.setAdapter(inoutListAdapter);

        lvInoutList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InOutVo inOutVo = (InOutVo)parent.getItemAtPosition(position);

                Log.d("madoo", inOutVo.getInoutType() + "    가계부 수정으로 이동");
                state = lvInoutList.onSaveInstanceState(); // 리스트뷰 스크롤 위치 저장


                //inoutType 별로 이동한다.
                Intent intent;
                switch (inOutVo.getInoutType()) {
                    case "IN":
                        Log.d("madoo ", inOutVo.getInoutNo() +  "  번 가계부 수입 수정 이동");
                        intent = new Intent(InoutListActivity.this, InoutEditInActivity.class);
                        intent.putExtra("inoutNo", inOutVo.getInoutNo());
                        startActivity(intent);
                        break;

                    case "OUT":
                        Log.d("madoo ", inOutVo.getInoutNo() +  "  번가계부 지출 수정 이동");
                        intent = new Intent(InoutListActivity.this, InoutEditOutActivity.class);
                        intent.putExtra("inoutNo", inOutVo.getInoutNo());
                        startActivity(intent);
                        break;

                    case "CHANGE":
                        Log.d("madoo ", inOutVo.getInoutNo() +  "가계부 환전 수정 이동");
                        intent = new Intent(InoutListActivity.this, InoutEditChangeActivity.class);
                        intent.putExtra("inoutNo", inOutVo.getInoutNo());
                        startActivity(intent);
                        break;

                    case "WITHROW":
                        Log.d("madoo ", inOutVo.getInoutNo() +  "가계부 인출 수정 이동");
                        intent = new Intent(InoutListActivity.this, InoutEditWithrowActivity.class);
                        intent.putExtra("inoutNo", inOutVo.getInoutNo());
                        startActivity(intent);
                        break;

                }

            }
        });

        if (state != null) { // 리스트뷰 상태가 있는 경우
            lvInoutList.onRestoreInstanceState(state); // 리스트뷰 스크롤 위치 복구
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.btnGoInout:
                Log.d("madoo ", "사용내역으로 이동");
                /*intent = new Intent(this, InoutListActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();*/
                break;

            case R.id.btnGoBalance:
                Log.d("madoo ", "화폐별잔액으로 이동");
                intent = new Intent(this, BalanceListActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                break;

            case R.id.btnGoMap:
                Log.d("madoo ", "지도보기로 이동");
                intent = new Intent(this, MarkerMapActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                break;

            case R.id.btnGoOutWrite:
                Log.d("madoo ", "지출 작성으로 이동");
                intent = new Intent(this, InoutWriteOutActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                state = null; // 리스트뷰 스크롤 최상단 위치 저장
                break;



        }
    }
}
