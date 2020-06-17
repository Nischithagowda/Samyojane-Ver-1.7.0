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

public class RI_Ward_ListAdapter extends BaseAdapter implements Filterable {
    private Context context;
    ArrayList<String> SlNo;
    ArrayList<String> TotalCount;
    ArrayList<String> TownCode;
    ArrayList<String> WardName;
    ArrayList<String> WardCode;
    String district, taluk, VA_Name, hobli,VA_Circle_Name, total;
    String district_Code, taluk_Code, hobli_Code, townCode, wardName, wardCode;

    RI_Ward_ListAdapter(Context context, ArrayList<String> slNo, ArrayList<String> wardName,
                     ArrayList<String> totalCount, ArrayList<String> townCode, ArrayList<String> wardCode){
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
            townCode = viewHolder.tvTownCode.getText().toString();
            wardName = viewHolder.tvVillageName.getText().toString();
            wardCode = viewHolder.tvVillageCode.getText().toString();
            Log.d("townCode",""+townCode);
            Log.d("wardCode",""+wardCode);
            Log.d("wardName",""+wardName);
            Intent i = new Intent(context, RI_Applicant_wise_report.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("villageCode","99999");
            i.putExtra("habitationCode","255");
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
class RI_Ward_ViewHolder {
    TextView sl_No, tvTotalPendency, tvTownCode, btnEdit, tvVillageName, tvVillageCode;

    RI_Ward_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        tvTotalPendency = view.findViewById(R.id.tvTotalPendency);
        tvTownCode = view.findViewById(R.id.tvTownCode);
        btnEdit = view.findViewById(R.id.btnEdit);
        tvVillageName = view.findViewById(R.id.tvVillageName);
        tvVillageCode = view.findViewById(R.id.tvVillageCode);
    }
}