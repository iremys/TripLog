package com.iremys.triplog.ui.inout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.CateVo;
import com.iremys.triplog.core.vo.UnitVo;

public class InoutCateAdapter extends ArrayAdapter<CateVo> {

    public TextView tvCateNo;
    public TextView tvCateName;
    public TextView tvUseFlag;
    public TextView tvInoutType;

    public InoutCateAdapter(Context context) {
        super(context, R.layout.inout_cate_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CateVo cateVo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inout_cate_item, null);
        }
        Log.d("madoo","position" + position);
        Log.d("madoo","getItem(position) -->" + getItem(position).toString());

        tvCateNo = (TextView)convertView.findViewById(R.id.tvCateNo);
        tvCateName = (TextView)convertView.findViewById(R.id.tvCateName);
        tvUseFlag = (TextView)convertView.findViewById(R.id.tvUseFlag);
        tvInoutType = (TextView)convertView.findViewById(R.id.tvInoutType);

        tvCateNo.setText(""+cateVo.getCateNo());
        tvCateName.setText(cateVo.getCateName());
        tvUseFlag.setText(cateVo.getUseFlag());
        tvInoutType.setText(cateVo.getInoutType());

/*

        tvGoTripEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("madoo", tripVo.getTripNo() + "   여행수정으로 이동");
                Intent intent = new Intent(getContext(), TripEditActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tripNo", tripVo.getTripNo() );
                getContext().startActivity(intent);
            }
        });
*/


        return convertView;
    }


}
