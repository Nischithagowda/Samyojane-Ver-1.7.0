package com.bhoomi.Samyojane_Application;

import android.app.Activity;
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

public class RI_VA_Circle_List_Adapter extends BaseAdapter implements Filterable {

    private Context context;
    ArrayList<String> SlNo;
    ArrayList<String> VA_Circle_Name;
    ArrayList<String> TotalCount;
    ArrayList<String> VA_Circle_Code;
    ArrayList<String> Dist_Code;
    ArrayList<String> Tal_Code;
    ArrayList<String> Hob_Code;
    String total;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code;

    RI_VA_Circle_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> va_Circle_Name,
                              ArrayList<String> totalCount, ArrayList<String> va_Circle_Code, ArrayList<String> dist_Code,
                              ArrayList<String> tal_Code, ArrayList<String> hob_Code){
        this.context = context;
        this.SlNo = slNo;
        this.VA_Circle_Name = va_Circle_Name;
        this.TotalCount = totalCount;
        this.VA_Circle_Code = va_Circle_Code;
        this.Dist_Code = dist_Code;
        this.Tal_Code = tal_Code;
        this.Hob_Code = hob_Code;
    }
    @Override
    public int getCount() {
        return VA_Circle_Name.size();
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
        final RI_VA_Circle_ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_ri_va_circle,parent, false);
            viewHolder=new RI_VA_Circle_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(RI_VA_Circle_ViewHolder) convertView.getTag();
        }

//        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

        viewHolder.sl_No.setText(SlNo.get(position));
        viewHolder.tvVillageCircleName.setText(VA_Circle_Name.get(position));
        viewHolder.tvTotalPendency.setText(TotalCount.get(position));
        viewHolder.tvVillageCircleCode.setText(VA_Circle_Code.get(position));
        viewHolder.tvDistCode.setText(Dist_Code.get(position));
        viewHolder.tvTalukCode.setText(Tal_Code.get(position));
        viewHolder.tvHobliCode.setText(Hob_Code.get(position));
        total = TotalCount.get(position);

        viewHolder.btnEdit.setOnClickListener(view -> {
            district_Code = viewHolder.tvDistCode.getText().toString();
            taluk_Code = viewHolder.tvTalukCode.getText().toString();
            hobli_Code = viewHolder.tvHobliCode.getText().toString();
            va_Circle_Code = viewHolder.tvVillageCircleCode.getText().toString();

            Intent i = new Intent(context, RI_Village_wise_report.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("district_Code", district_Code);
            i.putExtra("taluk_Code", taluk_Code);
            i.putExtra("hobli_Code", hobli_Code);
            i.putExtra("va_Circle_Code", va_Circle_Code);
            context.startActivity(i);
            ((Activity) context).finish();

            Log.d("va_Circle_Code_VA",""+va_Circle_Code);

        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

class RI_VA_Circle_ViewHolder{
    TextView sl_No, tvVillageCircleName, tvTotalPendency,btnEdit, tvVillageCircleCode, tvDistCode, tvTalukCode, tvHobliCode;
    RI_VA_Circle_ViewHolder(View view){
        sl_No = view.findViewById(R.id.sl_No);
        tvVillageCircleName = view.findViewById(R.id.tvVillageCircleName);
        tvTotalPendency = view.findViewById(R.id.tvTotalPendency);
        btnEdit = view.findViewById(R.id.btnEdit);
        tvVillageCircleCode = view.findViewById(R.id.tvVillageCircleCode);
        tvDistCode = view.findViewById(R.id.tvDistCode);
        tvTalukCode = view.findViewById(R.id.tvTalukCode);
        tvHobliCode = view.findViewById(R.id.tvHobliCode);
    }
}
