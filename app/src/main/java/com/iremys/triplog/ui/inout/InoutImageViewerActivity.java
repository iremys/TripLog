package com.iremys.triplog.ui.inout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.TripVo;

public class InoutImageViewerActivity extends AppCompatActivity implements View.OnClickListener{

    /////////////////////////////////////////////////////////////
    // 위젯변수
    /////////////////////////////////////////////////////////////

    private ImageView ivBigImg;
    private Button btnImgDel;
    private Button btnViewerClose;

    private Intent intent;
    private Uri uri;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inout_image_viewer_activity);

        //////////////////////////////////////////////////////////////////////////////////
        //넘겨받은 정보/////////////////////////////////////////////////////////////////////
        intent = getIntent();
        uri = (Uri)intent.getParcelableExtra("uri");
        index = (int)intent.getIntExtra("index", -1);
        Log.d("madoo", "uri viewr-> "   +  uri.toString());
        Log.d("madoo", "index viewr-> "   +  index);


        //////////////////////////////////////////////////////////////////////////////////
        //액션바/////////////////////////////////////////////////////////////////////
        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setTitle("이미지 보기") ;


        //////////////////////////////////////////////////////////////////////////////////
        //이미지뷰어/////////////////////////////////////////////////////////////////////
        ivBigImg = (ImageView)findViewById(R.id.ivBigImg);
        btnImgDel = (Button)findViewById(R.id.btnImgDel);
        btnViewerClose = (Button)findViewById(R.id.btnViewerClose);

        ivBigImg.setOnClickListener(this);
        btnImgDel.setOnClickListener(this);
        btnViewerClose.setOnClickListener(this);

        ivBigImg.setImageURI(uri);

    }


    /////////////////////////////////////////////////////////////
    //클릭이벤트
    /////////////////////////////////////////////////////////////
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnImgDel: //삭제버튼
                Log.d("madoo ", "선택이미지 삭제");
                intent.putExtra("index", index);

                setResult(RESULT_OK, intent);
                finish();

                break;

            case R.id.btnViewerClose: //닫기버튼
                Log.d("madoo ", "닫기버튼 클릭");
                finish();
                break;
        }

    }
}
