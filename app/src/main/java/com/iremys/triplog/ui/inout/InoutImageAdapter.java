package com.iremys.triplog.ui.inout;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.iremys.triplog.R;

public class InoutImageAdapter extends ArrayAdapter<Uri> {

    public ImageView ivImg;

    public InoutImageAdapter(Context context) {
        super(context, R.layout.inout_img_item);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Uri uri = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inout_img_item, null);
        }
        Log.d("madoo","position" + position);
        Log.d("madoo","getItem(position) -->" + getItem(position).toString());

        ivImg = (ImageView) convertView.findViewById(R.id.ivImg);

        ivImg.setImageURI(uri);


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
