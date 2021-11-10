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

public class Village_ListAdapter extends BaseAdapter implements Filterable {
    private Context context;
    ArrayList<String> SlNo;
    ArrayList<String> TotalCount;
    ArrayList<String> VillageName;
    ArrayList<String> VillageCode;
    String district, taluk, VA_Name, hobli,VA_Circle_Name, total;
    String district_Code, taluk_Code, hobli_Code, villageName, villageCode;

    Village_ListAdapter(Context context, ArrayList<String> slNo, ArrayList<String> villageName,
                        ArrayList<String> totalCount, ArrayList<String> villageCode){
        this.context = context;
        this.SlNo = slNo;
        this.TotalCount = totalCount;
        this.VillageName = villageName;
        this.VillageCode = villageCode;
    }
    @Override
    public int getCount() {
        return VillageName.size();
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
        final Village_ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_village,parent, false);
            viewHolder=new Village_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(Village_ViewHolder) convertView.getTag();
        }

        viewHolder.sl_No.setText(SlNo.get(position));
        viewHolder.tvTotalPendency.setText(TotalCount.get(position));
        viewHolder.tvVillageName.setText(VillageName.get(position));
        viewHolder.tvVillageCode.setText(VillageCode.get(position));

        total = TotalCount.get(position);
//        if(total.equals("0"))
//        {
//            viewHolder.btnEdit.setVisibility(View.GONE);
//        }else {
//            viewHolder.btnEdit.setVisibility(View.VISIBLE);
//        }

        viewHolder.btnEdit.setOnClickListener(view -> {
            villageName = viewHolder.tvVillageName.getText().toString();
            villageCode = viewHolder.tvVillageCode.getText().toString();

            Log.d("villageCode",""+villageCode);
            Log.d("VillageName",""+VillageName);

            Intent i = new Intent(context, Applicant_wise_report.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("villageCode",villageCode);
            i.putExtra("townCode","9999");
            i.putExtra("wardCode","255");
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

class Village_ViewHolder{
    TextView sl_No, tvTotalPendency,btnEdit, tvVillageName, tvVillageCode;
    Village_ViewHolder(View view){
        sl_No = view.findViewById(R.id.sl_No);
        tvTotalPendency = view.findViewById(R.id.tvTotalPendency);
        btnEdit = view.findViewById(R.id.btnEdit);
        tvVillageName = view.findViewById(R.id.tvVillageName);
        tvVillageCode = view.findViewById(R.id.tvVillageCode);
    }
}
