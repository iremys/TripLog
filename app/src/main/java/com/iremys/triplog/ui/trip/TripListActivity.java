package com.iremys.triplog.ui.trip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.TripVo;
import com.iremys.triplog.ui.inout.InoutListActivity;

import java.util.List;

public class TripListActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnGoTripWrite;
    private TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_list_activity);
        Log.d("madoo", "TripListActivity onCreate()");

        //위젯생성
        btnGoTripWrite = (Button) findViewById(R.id.btnGoTripWrite);

        //리스너등록
        btnGoTripWrite.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("madoo", "TripListActivity onStart()");

        //어댑터 생성
        tripAdapter = new TripAdapter(getApplicationContext());

        //어댑터 클리어
        tripAdapter.clear();

        //여행정보 가져오기
        List<TripVo> tripList =  MainApp.Dao.getTripList();

        //여행정보리스트 어댑터에 추가
        tripAdapter.addAll(tripList);
        Log.d("madoo", tripList.toString());

        ListView lvTripList = (ListView) findViewById(R.id.lvTripList);
        lvTripList.setAdapter(tripAdapter);

        lvTripList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TripVo tripVo = (TripVo)parent.getItemAtPosition(position);

                Log.d("madoo", tripVo.getTripNo() + "    여행가계부로 이동");
                Intent intent = new Intent(TripListActivity.this, InoutListActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGoTripWrite:
                Log.d("madoo ", "여행등록 폼으로 이동 버튼 클릭");
                Intent intent = new Intent(this, TripWriteActivity.class);
                startActivity(intent);
                break;


        }
    }
}
