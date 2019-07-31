package com.iremys.triplog.ui.inout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.UnitVo;

import java.util.List;

public class InoutUnitActivity extends AppCompatActivity {

    private InoutUnitAdapter inoutUnitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inout_unit_activity);

        inoutUnitAdapter = new InoutUnitAdapter(getApplicationContext()) ;

        List<UnitVo> unitList = MainApp.Dao.getListUnit();
        inoutUnitAdapter.addAll(unitList);
        Log.d("madoo", unitList.toString());


        ListView lvUintList = (ListView) findViewById(R.id.lvUintList) ;
        lvUintList.setAdapter(inoutUnitAdapter) ;

        lvUintList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UnitVo unitVo = (UnitVo)parent.getItemAtPosition(position);

                Log.d("madoo", unitVo.toString() + "    선택");

                Intent intent = new Intent();
                intent.putExtra("unitVo", unitVo);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }

}
