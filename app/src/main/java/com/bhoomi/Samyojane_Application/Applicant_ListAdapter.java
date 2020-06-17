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

public class Applicant_ListAdapter extends BaseAdapter implements Filterable {

    private Context context;
    ArrayList<String> SlNo;
    ArrayList<String> GSC_No;
    ArrayList<String> ApplicantID;
    ArrayList<String> ApplicantName;
    ArrayList<String> ServiceName;
    ArrayList<String> ServiceCode;

    Applicant_ListAdapter(Context context, ArrayList<String> slNo, ArrayList<String> gsc, ArrayList<String> applicantID,
                          ArrayList<String> applicantName, ArrayList<String> serviceName, ArrayList<String> serviceCode){
        this.context = context;
        this.SlNo = slNo;
        this.GSC_No = gsc;
        this.ApplicantID = applicantID;
        this.ApplicantName = applicantName;
        this.ServiceName = serviceName;
        this.ServiceCode = serviceCode;
    }
    @Override
    public int getCount() {
        return ApplicantID.size();
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
        final Applicant_ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_applicant,parent, false);
            viewHolder=new Applicant_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(Applicant_ViewHolder) convertView.getTag();
        }

        viewHolder.sl_No.setText(SlNo.get(position));
        viewHolder.tvApplicantID.setText(ApplicantID.get(position));
        viewHolder.tvApplicantName.setText(ApplicantName.get(position));
        viewHolder.tvServiceName.setText(ServiceName.get(position));
        viewHolder.tvServiceCode.setText(ServiceCode.get(position));
        viewHolder.tvGSC.setText(GSC_No.get(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

class Applicant_ViewHolder{
    TextView sl_No, tvApplicantID, tvApplicantName, tvServiceName, tvServiceCode, tvGSC;
    Applicant_ViewHolder(View view){
        sl_No = view.findViewById(R.id.sl_No);
        tvGSC = view.findViewById(R.id.tvGSC);
        tvApplicantID = view.findViewById(R.id.tvApplicantID);
        tvApplicantName = view.findViewById(R.id.tvApplicantName);
        tvServiceName = view.findViewById(R.id.tvServiceName);
        tvServiceCode = view.findViewById(R.id.tvServiceCode);
    }
}

