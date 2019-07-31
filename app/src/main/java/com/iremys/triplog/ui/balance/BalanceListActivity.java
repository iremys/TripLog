package com.iremys.triplog.ui.balance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.BalanceChartVo;
import com.iremys.triplog.core.vo.TripVo;
import com.iremys.triplog.core.vo.UnitVo;
import com.iremys.triplog.ui.inout.InoutListActivity;
import com.iremys.triplog.ui.marker.MarkerMapActivity;

import java.util.ArrayList;
import java.util.List;

public class BalanceListActivity extends AppCompatActivity implements View.OnClickListener{

    /////////////////////////////////////////////////////////////
    // 위젯변수
    /////////////////////////////////////////////////////////////
    //여행정보
    public TripVo tripVo;


    private Button btnGoInout;
    private Button btnGoBalance;
    private Button btnGoMap;


    private BalanceListAdapter balanceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_list_activity);

        //////////////////////////////////////////////////////////////////////////////////
        //여행정보/////////////////////////////////////////////////////////////////////
        tripVo = (TripVo)getIntent().getSerializableExtra("tripVo");

        //////////////////////////////////////////////////////////////////////////////////
        //액션바/////////////////////////////////////////////////////////////////////
        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setTitle("화폐별 잔액") ;

        //위젯생성
        btnGoInout = (Button) findViewById(R.id.btnGoInout);
        btnGoBalance = (Button) findViewById(R.id.btnGoBalance);
        btnGoMap = (Button) findViewById(R.id.btnGoMap);

        //리스너등록
        btnGoInout.setOnClickListener(this);
        btnGoBalance.setOnClickListener(this);
        btnGoMap.setOnClickListener(this);



    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("madoo ", "화폐별 잔액 업데이트");

        //데이터 가져오기
        List<UnitVo> unitList = MainApp.Dao.getListBalanceChart(tripVo.getTripNo());

        //화면에 맞게 list 변경
        //화면에 맞게 변형(쿼리로 해결안되었음)
        List<BalanceChartVo> balanceCharList = new ArrayList<BalanceChartVo>();
        int crtIndex = 1;
        for(UnitVo unitVo : unitList){
            if(balanceCharList.size() == 0 ){
                //bVo 생성
                BalanceChartVo balanceChartVo = new BalanceChartVo();
                balanceChartVo.setUnitNo(unitVo.getUnitNo());
                balanceChartVo.setUnitCode(unitVo.getUnitCode());
                balanceChartVo.setUnitName(unitVo.getUnitName());
                //vo세부값을 입력
                if("BANK".equals(unitVo.getMethod())){
                    balanceChartVo.setBankBalance(unitVo.getPrice());
                }else if("CASH".equals(unitVo.getMethod())){
                    balanceChartVo.setCashBalance(unitVo.getPrice());
                }else {
                    Log.d("madoo: ", "BalanceChart Activity method값 없음" );
                }
                //add
                balanceCharList.add(balanceChartVo);
                //size을 가져와서 crtIndex에 입력
                crtIndex = balanceCharList.size()-1;
            }else if((balanceCharList.get(crtIndex).getUnitNo() != unitVo.getUnitNo()) ){
                //bVo 생성
                BalanceChartVo balanceChartVo = new BalanceChartVo();
                balanceChartVo.setUnitNo(unitVo.getUnitNo());
                balanceChartVo.setUnitCode(unitVo.getUnitCode());
                balanceChartVo.setUnitName(unitVo.getUnitName());
                //vo세부값을 입력
                if("BANK".equals(unitVo.getMethod())){
                    balanceChartVo.setBankBalance(unitVo.getPrice());
                }else if("CASH".equals(unitVo.getMethod())){
                    balanceChartVo.setCashBalance(unitVo.getPrice());
                }else {
                    Log.d("madoo: ", "BalanceChart Activity method값 없음" );
                }
                //add
                balanceCharList.add(balanceChartVo);
                //size을 가져와서 crtIndex에 입력
                crtIndex = balanceCharList.size()-1;

            } else{
                //현재(crtIndex)에 값입력
                BalanceChartVo balanceChartVo = balanceCharList.get(crtIndex);
                //vo세부값을 입력
                if("BANK".equals(unitVo.getMethod())){
                    balanceChartVo.setBankBalance(unitVo.getPrice());
                }else if("CASH".equals(unitVo.getMethod())){
                    balanceChartVo.setCashBalance(unitVo.getPrice());
                }else {
                    Log.d("madoo: ", "BalanceChart Activity method값 없음" );
                }
            }
        }


        //어댑터 생성
        balanceListAdapter = new BalanceListAdapter(this  );

        //어댑터 클리어
        balanceListAdapter.clear();


        //여행정보리스트 어댑터에 추가
        balanceListAdapter.addAll(balanceCharList);
        Log.d("madoo", "여기"+ balanceCharList.toString());

        //리스트뷰에 장착
        ListView lvBalanceList = (ListView) findViewById(R.id.lvBalanceList);
        lvBalanceList.setAdapter(balanceListAdapter);

    }


    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.btnGoInout:
                /*
                Log.d("madoo ", "사용내역으로 이동");
                intent = new Intent(this, InoutListActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                */
                finish();
                break;

            case R.id.btnGoBalance:
                Log.d("madoo ", "화폐별잔액으로 이동");
               /* intent = new Intent(this, BalanceListActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();*/
                break;

            case R.id.btnGoMap:
                Log.d("madoo ", "지도보기로 이동");
                intent = new Intent(this, MarkerMapActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();
                break;


        }
    }
}
