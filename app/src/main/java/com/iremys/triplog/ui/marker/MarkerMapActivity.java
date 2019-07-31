package com.iremys.triplog.ui.marker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.InOutVo;
import com.iremys.triplog.core.vo.TripVo;
import com.iremys.triplog.ui.balance.BalanceListActivity;
import com.iremys.triplog.ui.inout.InoutListActivity;

import java.util.List;

public class MarkerMapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {


    //여행정보
    public TripVo tripVo;

    //버튼정보
    private Button btnGoInout;
    private Button btnGoBalance;
    private Button btnGoMap;


    //구글맵 객체
    private GoogleMap mMap;

    //위치선택 버튼
    private Button btnPositionSave;

    //선택한 위치
    double lat;
    double lon;

    //디버깅용
    private TextView tvXYInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_map_activity);

        //////////////////////////////////////////////////////////////////////////////////
        //여행정보/////////////////////////////////////////////////////////////////////
        tripVo = (TripVo)getIntent().getSerializableExtra("tripVo");


        //////////////////////////////////////////////////////////////////////////////////
        //액션바/////////////////////////////////////////////////////////////////////
        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setTitle("지도 리스트") ;


        //위젯생성
        btnGoInout = (Button) findViewById(R.id.btnGoInout);
        btnGoBalance = (Button) findViewById(R.id.btnGoBalance);
        btnGoMap = (Button) findViewById(R.id.btnGoMap);

        //리스너등록
        btnGoInout.setOnClickListener(this);
        btnGoBalance.setOnClickListener(this);
        btnGoMap.setOnClickListener(this);


        //////////////////////////////////////////////////////////////////////////////////
        //마커관련/////////////////////////////////////////////////////////////////////
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        //현재위치 이동 버튼 보이게 하기
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        //+,- 버튼 보이게 하기
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //구글맵 객체
        mMap = googleMap;


        List<InOutVo> inoutList = MainApp.Dao.getListInout(tripVo.getTripNo());
        Log.d("madoo ", inoutList.toString());
        Log.d("madoo ", ""+inoutList.size());

        for(int i=0; i<inoutList.size(); i++){
            //마커생성 추가
            InOutVo inoutVo = inoutList.get(i);
            String inoutType = inoutVo.getInoutType();

            String title ="";
            String snippet ="";
            Marker focusMarker;

            if("IN".equals(inoutType)){
                title = "[수입]"+inoutVo.getUseDate();
                snippet = "["+inoutVo.getTitle()+"]  " + inoutVo.getpPrice() + " "+ inoutVo.getpUnitName();

            } else if("OUT".equals(inoutType)){
                title = "[지출]"+inoutVo.getUseDate();
                snippet = "["+inoutVo.getTitle()+"]  " + inoutVo.getmPrice() + " "+ inoutVo.getmUnitName();

            } else if("CHANGE".equals(inoutType)){
                title = "[환전]"+inoutVo.getUseDate();
                snippet = "["+inoutVo.getTitle()+"]  " + inoutVo.getmPrice() + " "+ inoutVo.getmUnitName() + " ---> " + inoutVo.getpPrice() + " "+ inoutVo.getpUnitName();

            } else if("WITHROW".equals(inoutType)) {
                title = "[인출]"+inoutVo.getUseDate();
                snippet = "["+inoutVo.getTitle()+"]  " + inoutVo.getmPrice() + " "+ inoutVo.getmUnitName() + " ---> " + inoutVo.getpPrice() + " "+ inoutVo.getpUnitName();
            } else {
                Log.d("madoo ", "마커리스트 마커데이터 출력 오류");
                title = "[마커데이터 출력 오류]";
            }

            LatLng loadPosition = new LatLng(inoutVo.getLat(), inoutVo.getLon());

            if(i == 0){
                mMap.addMarker(new MarkerOptions().position(loadPosition).title(title).snippet(snippet)).showInfoWindow();
                Log.d("madoo  ", i+"마커생성");
            }else {
                mMap.addMarker(new MarkerOptions().position(loadPosition).title(title).snippet(snippet));
                Log.d("madoo  ", i+"마커생성");
            }



        }

        LatLng loadPosition = new LatLng(inoutList.get(0).getLat(), inoutList.get(0).getLon());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loadPosition, 11));

    }


    //클릭 이벤트
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
                intent = new Intent(this, BalanceListActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();
                break;

            case R.id.btnGoMap:
               /* Log.d("madoo ", "지도보기로 이동");
                intent = new Intent(this, MarkerMapActivity.class);
                intent.putExtra("tripVo", tripVo);
                startActivity(intent);
                finish();*/
                break;


        }
    }

}
