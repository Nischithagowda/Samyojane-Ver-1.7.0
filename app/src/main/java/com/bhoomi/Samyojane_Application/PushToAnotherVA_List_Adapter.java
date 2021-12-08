package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class PushToAnotherVA_List_Adapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<String> SlNo;
    ArrayList<String> Applicant_Name;
    ArrayList<String> Applicant_ID;

    PushToAnotherVA_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> applicant_Name, ArrayList<String> rd_No) {
        this.context = context;
        this.SlNo = slNo;
        this.Applicant_Name = applicant_Name;
        this.Applicant_ID = rd_No;
    }

    @Override
    public int getCount() {
        return Applicant_ID.size();
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
        final PushToAnotherVA_ViewHolder viewHolder;

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_push,parent, false);
            viewHolder=new PushToAnotherVA_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (PushToAnotherVA_ViewHolder) convertView.getTag();
        }

        viewHolder.sl_No.setText(SlNo.get(position));
        viewHolder.app_Name.setText(Applicant_Name.get(position));
        viewHolder.app_Id.setText(Applicant_ID.get(position));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
class PushToAnotherVA_ViewHolder{
    TextView sl_No, app_Name, app_Id;
    PushToAnotherVA_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        app_Name = view.findViewById(R.id.app_Name);
        app_Id = view.findViewById(R.id.app_Id);
    }
}
