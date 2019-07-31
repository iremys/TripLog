package com.iremys.triplog.ui.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.TripVo;

public class TripWriteActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etTripTitle;
    private EditText etStartDate;
    private EditText etEndDate;

    private Button btnTripSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_write_activity);

        Log.d("madoo", "TripWriteActivity onCreate()");

        //위젯생성
        etTripTitle = (EditText) findViewById(R.id.etTripTitle);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);

        btnTripSave = (Button) findViewById(R.id.btnTripSave);

        //리스너등록
        btnTripSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnTripSave:
                Log.d("madoo ", "여행정보등록버튼 클릭");

                String tripTitle = ""+etTripTitle.getText();
                String startDate = ""+etStartDate.getText();
                String endDate =   ""+etEndDate.getText();


                TripVo tripVo = new TripVo();
                tripVo.setTripTitle(tripTitle);
                tripVo.setStartDate(startDate);
                tripVo.setEndDate(endDate);
                MainApp.Dao.inserTrip(tripVo);
                finish();
                break;
        }
    }

}
