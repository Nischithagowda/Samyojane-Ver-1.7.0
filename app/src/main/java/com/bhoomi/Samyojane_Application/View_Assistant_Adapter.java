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

public class View_Assistant_Adapter extends BaseAdapter implements Filterable {

    private ArrayList<String> SlNo;
    private ArrayList<String> Ass_Name;
    private ArrayList<String> Ass_MobileNum;
    private Context context;

    View_Assistant_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> ass_Name, ArrayList<String> ass_MobileNum){
        this.context = context;
        this.SlNo = slNo;
        this.Ass_Name = ass_Name;
        this.Ass_MobileNum = ass_MobileNum;
    }

    @Override
    public int getCount() {
        return Ass_Name.size();
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

        View_Assistant_ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_assistants,parent, false);
            viewHolder=new View_Assistant_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(View_Assistant_ViewHolder) convertView.getTag();
        }

        viewHolder.tvSl_No.setText(SlNo.get(position));
        viewHolder.tvAssName.setText(Ass_Name.get(position));
        viewHolder.tvAssMobileNum.setText(Ass_MobileNum.get(position));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

class View_Assistant_ViewHolder{
    TextView tvSl_No, tvAssName, tvAssMobileNum;
    View_Assistant_ViewHolder(View view){
        tvSl_No = view.findViewById(R.id.tvSl_No);
        tvAssName = view.findViewById(R.id.tvAssName);
        tvAssMobileNum = view.findViewById(R.id.tvAssMobileNum);
    }
}
