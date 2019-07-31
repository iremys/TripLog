package com.iremys.triplog.ui.inout;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.iremys.triplog.R;

public class InoutMapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

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
        setContentView(R.layout.inout_map_activity);

        //////////////////////////////////////////////////////////////////////////////////
        //액션바/////////////////////////////////////////////////////////////////////
        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setTitle("위치 선택") ;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //위치선택 버튼
        btnPositionSave = (Button) findViewById(R.id.btnPositionSave);
        btnPositionSave.setOnClickListener(this);

        //디버깅용
        tvXYInfo = (TextView) findViewById(R.id.tvXYInfo);
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

        //로딩시 위치 설정(이전 위치)
        Intent intent = getIntent();
        double loadLat = intent.getDoubleExtra("lat", 0);
        double loadLon = intent.getDoubleExtra("lon", 0);
        String title = intent.getStringExtra("title");

        //로딩시 마커 위치
        LatLng loadPosition = new LatLng(loadLat, loadLon);
        mMap.addMarker(new MarkerOptions().position(loadPosition).title(title)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loadPosition, 15));
        Log.d("madoo ", loadLat + "    " +loadLat + "   " + title);


        //지도 이동 이벤트 (이동시 중앙 좌표값을 구한다)
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                lat =  mMap.getCameraPosition().target.latitude;
                lon =  mMap.getCameraPosition().target.longitude;

                tvXYInfo.setText("위도: " + lat + "\n경도: "+lon);
                Log.d("madoo ", lat + "    " +lon );
            }
        });

    }


    //클릭 이벤트
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnPositionSave: //현재위치 저장
                Log.d("madoo ", "지도에서 현재위치 저장 클릭");
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                setResult(RESULT_OK, intent);
                finish();
                break;

        }
    }

}
