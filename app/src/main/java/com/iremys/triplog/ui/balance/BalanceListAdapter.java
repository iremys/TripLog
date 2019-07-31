package com.iremys.triplog.ui.balance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iremys.triplog.R;
import com.iremys.triplog.core.vo.BalanceChartVo;
import com.iremys.triplog.core.vo.InOutVo;

public class BalanceListAdapter extends ArrayAdapter<BalanceChartVo> {

    public TextView tvUnitInfo;
    public TextView tvbankBalance;
    public TextView tvbankCash;
    public TextView tvMoneySum;



    public BalanceListAdapter(Context context) {
        super(context, R.layout.balance_list_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final BalanceChartVo balanceChartVo = getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.balance_list_item, parent, false);
        }
        Log.d("madoo","position " + position);
        Log.d("madoo","getItem(position) -->" + getItem(position).toString());

        tvUnitInfo = (TextView)convertView.findViewById(R.id.tvUnitInfo);
        tvbankBalance = (TextView)convertView.findViewById(R.id.tvbankBalance);
        tvbankCash = (TextView)convertView.findViewById(R.id.tvbankCash);
        tvMoneySum = (TextView)convertView.findViewById(R.id.tvMoneySum);


        tvUnitInfo.setText(balanceChartVo.getUnitName() + " (" + balanceChartVo.getUnitCode()+ " )" + "  : " + balanceChartVo.getUnitNo());
        tvbankBalance.setText(""+balanceChartVo.getBankBalance());
        tvbankCash.setText(""+balanceChartVo.getCashBalance());

        double sum = balanceChartVo.getBankBalance() + balanceChartVo.getCashBalance();
        tvMoneySum.setText(""+sum);


        return convertView;
    }


}
