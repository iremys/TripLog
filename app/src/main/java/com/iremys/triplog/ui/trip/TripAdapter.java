package com.iremys.triplog.ui.trip;

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

public class TripAdapter extends ArrayAdapter<TripVo> {

    public TextView tvTripTitle;
    public TextView tvTripStartDate;
    public TextView tvTripEndtDate;
    public TextView tvGoTripEdit;

    public TripAdapter(Context context) {
        super(context, R.layout.trip_list_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TripVo tripVo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_list_item, null);
        }
        Log.d("madoo","position" + position);
        Log.d("madoo","getItem(position) -->" + getItem(position).toString());

        tvTripTitle = (TextView)convertView.findViewById(R.id.tvTripTitle);
        tvTripStartDate = (TextView)convertView.findViewById(R.id.tvTripStartDate);
        tvTripEndtDate = (TextView)convertView.findViewById(R.id.tvTripEndtDate);
        tvGoTripEdit = (TextView)convertView.findViewById(R.id.tvGoTripEdit);

        tvTripTitle.setText(tripVo.getTripNo() + ".   " + tripVo.getTripTitle());
        tvTripStartDate.setText(tripVo.getStartDate());
        tvTripEndtDate.setText(tripVo.getEndDate());


        tvGoTripEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("madoo", tripVo.getTripNo() + "   여행수정으로 이동");
                Intent intent = new Intent(getContext(), TripEditActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tripNo", tripVo.getTripNo() );
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }


}
