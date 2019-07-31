package com.iremys.triplog.ui.inout;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.TripVo;
import com.iremys.triplog.core.vo.UnitVo;
import com.iremys.triplog.ui.trip.TripEditActivity;

public class InoutUnitAdapter extends ArrayAdapter<UnitVo> {

    public TextView tvUnitNo;
    public TextView tvUnitCode;
    public TextView tvUnitName;

    public InoutUnitAdapter(Context context) {
        super(context, R.layout.inout_unit_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final UnitVo unitVo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inout_unit_item, null);
        }
        Log.d("madoo","position" + position);
        Log.d("madoo","getItem(position) -->" + getItem(position).toString());

        tvUnitNo = (TextView)convertView.findViewById(R.id.tvUnitNo);
        tvUnitCode = (TextView)convertView.findViewById(R.id.tvUnitCode);
        tvUnitName = (TextView)convertView.findViewById(R.id.tvUnitName);

        tvUnitNo.setText(""+unitVo.getUnitNo());
        tvUnitCode.setText(unitVo.getUnitCode());
        tvUnitName.setText(unitVo.getUnitName());

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
