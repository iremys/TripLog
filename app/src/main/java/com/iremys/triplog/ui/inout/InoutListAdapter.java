package com.iremys.triplog.ui.inout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.iremys.triplog.MainApp;
import com.iremys.triplog.R;
import com.iremys.triplog.core.http.SafeAsyncTask;
import com.iremys.triplog.core.vo.FileVo;
import com.iremys.triplog.core.vo.InOutVo;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.List;

public class InoutListAdapter extends ArrayAdapter<InOutVo> {

    private LinearLayout llInoutItem;
    private TextView tvUseDate;
    private TextView tvTitle;
    private TextView tvBtnSync;
    private TextView tvInoutType;
    private TextView tvCateName;
    private TextView tvMPrice;
    private TextView tvMUnitName;
    private TextView tvMMethod;
    private TextView tvPPrice;
    private TextView tvPUnitName;
    private TextView tvPMethod;
    private TextView tvLat;
    private TextView tvLon;


    private String serverAddr = "http://13.209.242.217:8080/triplog/api";
    //private String serverAddr = "localhost:8088/triplog/api";

    public InoutListAdapter(Context context) {
        super(context, R.layout.inout_list_item);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final InOutVo inOutVo = getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.inout_list_item, parent, false);
        }
        Log.d("madoo","parent " + parent);
        Log.d("madoo","position " + position);
        Log.d("madoo","getItem(position) -->" + getItem(position).toString());


        tvUseDate = (TextView)convertView.findViewById(R.id.tvUseDate);
        tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        tvBtnSync = (TextView)convertView.findViewById(R.id.tvBtnSync);

        tvInoutType = (TextView)convertView.findViewById(R.id.tvInoutType);
        tvCateName = (TextView)convertView.findViewById(R.id.tvCateName);
        tvMPrice = (TextView)convertView.findViewById(R.id.tvMPrice);
        tvMUnitName = (TextView)convertView.findViewById(R.id.tvMUnitName);
        tvMMethod = (TextView)convertView.findViewById(R.id.tvMMethod);
        tvPPrice = (TextView)convertView.findViewById(R.id.tvPPrice);
        tvPUnitName = (TextView)convertView.findViewById(R.id.tvPUnitName);
        tvPMethod = (TextView)convertView.findViewById(R.id.tvPMethod);
        tvLat = (TextView)convertView.findViewById(R.id.tvLat);
        tvLon = (TextView)convertView.findViewById(R.id.tvLon);


        tvUseDate.setText(inOutVo.getUseDate());
        tvTitle.setText(inOutVo.getTitle()+"/"+inOutVo.getInoutNo());

        if("-1".equals(inOutVo.getSync())){
            tvBtnSync.setText("[동기]");
        }else{
            tvBtnSync.setText("[성공]");
        }



        if("IN".equals(inOutVo.getInoutType())){
            tvInoutType.setText("수입");
        }else if("OUT".equals(inOutVo.getInoutType())){
            tvInoutType.setText("지출");
        }else if("CHANGE".equals(inOutVo.getInoutType())){
            tvInoutType.setText("환전");
        }else if("WITHROW".equals(inOutVo.getInoutType())){
            tvInoutType.setText("인출");
        }



        tvCateName.setText(inOutVo.getCateName());

        tvMPrice.setText("" +(-1*inOutVo.getmPrice())); //지출은 -로 표시
        tvMUnitName.setText(inOutVo.getmUnitName());

        tvMMethod.setText(inOutVo.getmMethod());


        tvPPrice.setText("" +inOutVo.getpPrice());
        tvPUnitName.setText(inOutVo.getpUnitName());

        tvPMethod.setText(inOutVo.getpMethod());

        tvLat.setText("위:" +inOutVo.getLat());
        tvLon.setText("경:" +inOutVo.getLon());


        tvBtnSync.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("madoo ", "onClick()------>" + inOutVo.toString());
                new InoutSyncTask(inOutVo).execute();
            }
        });


        return convertView;
    }

    private class InoutSyncTask extends SafeAsyncTask<String> {

        private InOutVo inoutVo;
        private List<FileVo> fileList;

        public InoutSyncTask(InOutVo inoutVo) {
            this.inoutVo = inoutVo;
            fileList = MainApp.Dao.getListFile(inoutVo.getInoutNo());

        }

        @Override
        public String call() throws Exception {
            Log.d("madoo ", "InoutSyncTask----->" + inoutVo.toString());
            Log.d("madoo ", "InoutSyncTask----->" + fileList.toString());

            //준비

            Gson gson = new Gson();
            String sendInoutUrl = serverAddr+"/sendInout";
            String sendFileInfoUrl = serverAddr+"/sendFileInfo";
            String sendFileUrl = serverAddr+"/sendFile";



            //게시물 서버 보내기
            String inOutVoJson = gson.toJson(inoutVo);

            HttpRequest inoutRequest  = HttpRequest.post(sendInoutUrl);
            inoutRequest.contentType( HttpRequest.CONTENT_TYPE_JSON );
            inoutRequest.accept( HttpRequest.CONTENT_TYPE_JSON );
            inoutRequest.send(inOutVoJson);
            int inoutResponseCode = inoutRequest.code();
            int inoutCnt = Integer.parseInt(inoutRequest.body());
            Log.d("madoo ", "inout 서버글 저장 또는 수정 요청 inoutResponseCode ---->" + inoutResponseCode);
            Log.d("madoo ", "inout 서버글 저장 또는 수정 갯수 inoutCnt ---->" + inoutCnt);
            if ( (inoutResponseCode != HttpURLConnection.HTTP_OK )|| inoutCnt !=1 ) {
                return ""+inoutVo.getInoutNo();
            }


            //파일정보, 파일 서버 보내기
            for(FileVo vo : fileList){
                //파일정보
                String fileVoJson = gson.toJson(vo);
                HttpRequest fileInfoRequest  = HttpRequest.post(sendFileInfoUrl);
                fileInfoRequest.contentType( HttpRequest.CONTENT_TYPE_JSON );
                fileInfoRequest.accept( HttpRequest.CONTENT_TYPE_JSON );
                fileInfoRequest.send(fileVoJson);
                int fileInfoResponseCode = fileInfoRequest.code();
                int fileCnt = Integer.parseInt(fileInfoRequest.body());
                Log.d("madoo ", "파일저장 요청 fileInfoResponseCode ---->" + fileInfoResponseCode);
                Log.d("madoo ", "파일저장 갯수 fileCnt ---->" + fileCnt);
                if ( fileInfoResponseCode != HttpURLConnection.HTTP_OK || fileCnt != 1  ) {
                    return ""+inoutVo.getInoutNo();
                }

                //파일
                HttpRequest fileRequest  = HttpRequest.post(sendFileUrl);
                fileRequest.part("file", vo.getSaveFileName(), "multipart/form-data", new File(vo.getFilePath()));
                fileRequest.part("file", vo.getSaveFileName(), "multipart/form-data", new File(vo.getFilePath()));
                int fileRequestCode =fileRequest.code();
                Log.d("madoo ", "fileRequestCode ------>" + fileRequestCode);
                if ( fileRequestCode != HttpURLConnection.HTTP_OK  ) {
                    return ""+inoutVo.getInoutNo();
                }

            }

            return "0";
        }

       @Override
        protected void onException(Exception e) throws RuntimeException {
            super.onException(e);
            Log.d("madoo ", "onException(e) ------>" + e.toString());
        }

        @Override
        protected void onSuccess(String result) throws Exception {
            super.onSuccess( result );
            if(Integer.parseInt(result) != 0){
                Log.d("madoo ", "onSuccess() ------>" + result.toString() + "관련글 삭제");
                String sendRollbackInoutUrl = serverAddr+"/rollbackInout";
                HttpRequest rollbackRequest  = HttpRequest.post(sendRollbackInoutUrl);
                int code = rollbackRequest.send("inoutNo=" + inoutVo.getInoutNo()).code();
                Log.d("madoo ", "onSuccess() ------>" + code);
            }else {
                Log.d("madoo ", "onSuccess() ------>" + "동기화 성공");
                String dt = MainApp.Dao.updateSync(inoutVo.getInoutNo());
                Toast.makeText(getContext(), inoutVo.getTitle()+"  동기화 성공", Toast.LENGTH_LONG).show();
            }
        }


    }


}
