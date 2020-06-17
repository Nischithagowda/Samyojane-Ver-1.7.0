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

public class RC_VA_List_Adapter extends BaseAdapter implements Filterable {

    private ArrayList<String> SlNo;
    private ArrayList<String> RCList;
    private ArrayList<String> DistCode_ass;
    private ArrayList<String> TalCode_ass;
    private ArrayList<String> HobCode_ass;
    private ArrayList<String> uName_get_Array;
    private Context context;

    String rc_num, districtCode, talukCode, hobliCode, uName_get;

    RC_VA_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> rcList, ArrayList<String> distCode_ass,
                       ArrayList<String> talCode_ass, ArrayList<String> hobCode_ass, ArrayList<String> uName_get_array){
        this.context = context;
        this.SlNo = slNo;
        this.RCList = rcList;
        this.DistCode_ass = distCode_ass;
        this.TalCode_ass = talCode_ass;
        this.HobCode_ass = hobCode_ass;
        this.uName_get_Array = uName_get_array;
    }

    @Override
    public int getCount() {
        return RCList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RC_VA_List_ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_rc_numbers,parent, false);
            viewHolder=new RC_VA_List_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(RC_VA_List_ViewHolder) convertView.getTag();
        }

        viewHolder.tvSl_No.setText(SlNo.get(position));
        viewHolder.tvRCNum.setText(RCList.get(position));
        viewHolder.tvDistCode_ass.setText(DistCode_ass.get(position));
        viewHolder.tvTalukCode_ass.setText(TalCode_ass.get(position));
        viewHolder.tvHobCode_ass.setText(HobCode_ass.get(position));
        viewHolder.tvuName_get.setText(uName_get_Array.get(position));

        viewHolder.btnEdit.setOnClickListener(v -> {
            rc_num = viewHolder.tvRCNum.getText().toString();
            districtCode = viewHolder.tvDistCode_ass.getText().toString();
            talukCode = viewHolder.tvTalukCode_ass.getText().toString();
            hobliCode = viewHolder.tvHobCode_ass.getText().toString();
            uName_get = viewHolder.tvuName_get.getText().toString();

            Intent i = new Intent(context, E_Kshana_MainScreen_VA.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("rc_num", rc_num);
            i.putExtra("districtCode", districtCode);
            i.putExtra("talukCode",talukCode);
            i.putExtra("hobliCode",hobliCode);
            i.putExtra("uName_get", uName_get);
            context.startActivity(i);

            Log.d("rc_num", ""+rc_num);
            Log.d("districtCode", ""+districtCode);
            Log.d("talukCode", ""+talukCode);
            Log.d("hobliCode", ""+hobliCode);
            Log.d("uName_get",""+uName_get);
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

class RC_VA_List_ViewHolder{
    TextView tvSl_No, tvRCNum, tvDistCode_ass, tvTalukCode_ass, tvHobCode_ass, tvuName_get, btnEdit;
    RC_VA_List_ViewHolder(View view){
        tvSl_No = view.findViewById(R.id.tvSl_No);
        tvRCNum = view.findViewById(R.id.tvRCNum);
        tvDistCode_ass = view.findViewById(R.id.tvDistCode_ass);
        tvTalukCode_ass = view.findViewById(R.id.tvTalukCode_ass);
        tvHobCode_ass = view.findViewById(R.id.tvHobCode_ass);
        tvuName_get = view.findViewById(R.id.tvuName_get);
        btnEdit = view.findViewById(R.id.btnEdit);
    }
}
