package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class Ward_ListAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<String> SlNo;
    ArrayList<String> TotalCount;
    ArrayList<Integer> TownCode;
    ArrayList<String> WardName;
    ArrayList<Integer> WardCode;
    String total, wardName;
    int townCode, wardCode;

    Ward_ListAdapter(Context context, ArrayList<String> slNo, ArrayList<String> wardName,
                     ArrayList<String> totalCount, ArrayList<Integer> townCode, ArrayList<Integer> wardCode){
        this.context = context;
        this.SlNo = slNo;
        this.WardName = wardName;
        this.TotalCount = totalCount;
        this.TownCode = townCode;
        this.WardCode = wardCode;
    }
    @Override
    public int getCount() {
        return WardCode.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Ward_ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_village,parent, false);
            viewHolder=new Ward_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(Ward_ViewHolder) convertView.getTag();
        }

        viewHolder.sl_No.setText(SlNo.get(position));
        viewHolder.tvVillageName.setText(WardName.get(position));
        viewHolder.tvTotalPendency.setText(TotalCount.get(position));
        viewHolder.tvTownCode.setText(TownCode.get(position));
        viewHolder.tvVillageCode.setText(WardCode.get(position));
        total = TotalCount.get(position);
//        if(total.equals("0"))
//        {
//            viewHolder.btnEdit.setVisibility(View.GONE);
//        }else {
//            viewHolder.btnEdit.setVisibility(View.VISIBLE);
//        }

        viewHolder.btnEdit.setOnClickListener(view -> {
            townCode = Integer.parseInt(viewHolder.tvTownCode.getText().toString());
            wardName = viewHolder.tvVillageName.getText().toString();
            wardCode = Integer.parseInt(viewHolder.tvVillageCode.getText().toString());
            Log.d("townCode",""+townCode);
            Log.d("wardCode",""+wardCode);
            Log.d("wardName",""+wardName);
            Intent i = new Intent(context, Applicant_wise_report.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("villageCode","99999");
            i.putExtra("townCode",townCode);
            i.putExtra("wardCode",wardCode);
            context.startActivity(i);
            //((Activity) context).finish();

        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
class Ward_ViewHolder {
    TextView sl_No, tvTotalPendency, tvTownCode,btnEdit, tvVillageName, tvVillageCode;
    Ward_ViewHolder(View view){
        sl_No = view.findViewById(R.id.sl_No);
        tvTotalPendency = view.findViewById(R.id.tvTotalPendency);
        tvTownCode = view.findViewById(R.id.tvTownCode);
        btnEdit = view.findViewById(R.id.btnEdit);
        tvVillageName = view.findViewById(R.id.tvVillageName);
        tvVillageCode = view.findViewById(R.id.tvVillageCode);
    }
}
