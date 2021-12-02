package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adarsh on 12-Jun-19.
 */

public class Docs_List_Adapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<String> SlNo;
    ArrayList<String> App_ID;
    ArrayList<Integer> Docs_ID;
    ArrayList<String> Docs_Name;
    String document_Name, Applicant_Id;
    int document_ID;

    Docs_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> app_ID, ArrayList<Integer> docs_ID, ArrayList<String> docs_Name){
        this.context=context;
        this.SlNo = slNo;
        this.App_ID = app_ID;
        this.Docs_ID = docs_ID;
        this.Docs_Name = docs_Name;
    }

    @Override
    public int getCount() {
        return Docs_Name.size();
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
        final Docs_ViewHolder docs_viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_docs, parent,false);
            docs_viewHolder = new Docs_ViewHolder(convertView);
            convertView.setTag(docs_viewHolder);
        }else {
            docs_viewHolder = (Docs_ViewHolder) convertView.getTag();
        }

        String Docs_ID_str = Docs_ID.get(position).toString();

        docs_viewHolder.sl_No.setText(SlNo.get(position));
        docs_viewHolder.tvAppID.setText(App_ID.get(position));
        docs_viewHolder.tvDocsID.setText(Docs_ID_str);
        docs_viewHolder.tvDocsName.setText(Docs_Name.get(position));

        Button btnView = convertView.findViewById(R.id.btnView);
        btnView.setOnClickListener(v -> {
            Applicant_Id = docs_viewHolder.tvAppID.getText().toString();
            document_ID = Integer.parseInt(docs_viewHolder.tvDocsID.getText().toString());
            document_Name = docs_viewHolder.tvDocsName.getText().toString();

            Intent i = new Intent(context, PDF_Viewer.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("Applicant_Id", Applicant_Id);
            i.putExtra("document_ID",document_ID);
            i.putExtra("document_Name", document_Name);
            context.startActivity(i);
            Log.d("Applicant_Id", ""+Applicant_Id);
            Log.d("document_ID",""+document_ID);
            Log.d("document_Name",""+document_Name);
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

class Docs_ViewHolder{
    TextView sl_No, tvAppID, tvDocsID, tvDocsName;
    Docs_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        tvAppID = view.findViewById(R.id.tvAppID);
        tvDocsID = view.findViewById(R.id.tvDocsID);
        tvDocsName = view.findViewById(R.id.tvDocsName);
    }
}
