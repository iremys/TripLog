package com.iremys.triplog.ui.inout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.CateVo;
import com.iremys.triplog.core.vo.UnitVo;

import java.util.List;

public class InoutCateActivity extends AppCompatActivity {

    private InoutCateAdapter inoutCateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inout_cate_activity);

        inoutCateAdapter = new InoutCateAdapter(getApplicationContext()) ;
        Intent intent = getIntent();
        String inoutType  = intent.getStringExtra("inoutType");
        List<CateVo> cateList = MainApp.Dao.getListCate(inoutType);
        inoutCateAdapter.addAll(cateList);
        Log.d("madoo", cateList.toString());


        ListView lvCateList = (ListView) findViewById(R.id.lvCateList) ;
        lvCateList.setAdapter(inoutCateAdapter) ;

        lvCateList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CateVo cateVo = (CateVo)parent.getItemAtPosition(position);

                Log.d("madoo", cateVo.toString() + "    선택");

                Intent intent = new Intent();
                intent.putExtra("cateVo", cateVo);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }

}
