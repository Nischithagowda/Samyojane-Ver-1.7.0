package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

public class RC_VA_Member_List_Adapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<String> SlNo;
    ArrayList<String> Member_Name;
    ArrayList<String> Member_Id;
    ArrayList<String> Rc_num;
    ArrayList<String> DataEntry;
    ArrayList<String> Status;
    ArrayList<String> DistCode_ass;
    ArrayList<String> TalCode_ass;
    ArrayList<String> HobCode_ass;
    ArrayList<String> uName_get_Array;
    String member_ID, rc_num, dataEntry, status, distCode_ass, talCode_ass, hobCode_ass, uName_get;
    ProgressDialog dialog;
    SQLiteDatabase database;
    String inactive;

    RC_VA_Member_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> member_Name,
                           ArrayList<String> member_Id, ArrayList<String> rc_num, ArrayList<String> DataEntry, ArrayList<String> status,
                           ArrayList<String> distCode_ass, ArrayList<String> talCode_ass, ArrayList<String> hobCode_ass,
                           ArrayList<String> uName_get_Array){
        this.context = context;
        this.SlNo = slNo;
        this.Member_Name = member_Name;
        this.Member_Id = member_Id;
        this.Rc_num = rc_num;
        this.DataEntry = DataEntry;
        this.Status = status;
        this.DistCode_ass = distCode_ass;
        this.TalCode_ass = talCode_ass;
        this.HobCode_ass = hobCode_ass;
        this.uName_get_Array = uName_get_Array;
    }
    @Override
    public int getCount() {
        return Member_Id.size();
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
        RC_VA_Member_ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_member_id,parent, false);
            viewHolder=new RC_VA_Member_ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(RC_VA_Member_ViewHolder) convertView.getTag();
        }

        viewHolder.sl_No.setText(SlNo.get(position));
        viewHolder.tvMemberName.setText(Member_Name.get(position));
        viewHolder.tvMemberID.setText(Member_Id.get(position));
        viewHolder.tvRCNum.setText(Rc_num.get(position));
        viewHolder.tvDataEntry.setText(DataEntry.get(position));
        viewHolder.tvStatus.setText(Status.get(position));
        viewHolder.tvDistCode_ass.setText(DistCode_ass.get(position));
        viewHolder.tvTalukCode_ass.setText(TalCode_ass.get(position));
        viewHolder.tvHobCode_ass.setText(HobCode_ass.get(position));
        viewHolder.tvuName_get.setText(uName_get_Array.get(position));

        dialog = new ProgressDialog(context, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(context.getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        SQLiteOpenHelper openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(context);
        database = openHelper.getReadableDatabase();

        viewHolder.btnEdit.setOnClickListener(v -> {
            member_ID = viewHolder.tvMemberID.getText().toString();
            rc_num = viewHolder.tvRCNum.getText().toString();
            dataEntry = viewHolder.tvDataEntry.getText().toString();
            status = viewHolder.tvStatus.getText().toString();
            distCode_ass = viewHolder.tvDistCode_ass.getText().toString();
            talCode_ass = viewHolder.tvTalukCode_ass.getText().toString();
            hobCode_ass = viewHolder.tvHobCode_ass.getText().toString();
            uName_get = viewHolder.tvuName_get.getText().toString();

            Log.d("member_ID",""+member_ID);
            Log.d("rc_num",""+rc_num);
            Log.d("dataEntry",""+dataEntry);
            Log.d("status", ""+status);
            Log.d("distCode_ass1", ""+distCode_ass);
            Log.d("talCode_ass1", ""+talCode_ass);
            Log.d("hobCode_ass1", ""+hobCode_ass);
            Log.d("uName_get",""+uName_get);

            Cursor cursor = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_member_id
                    +" where "+ DataBaseHelperClass_btnDownload_E_Kshana.RC_Num+"='"+rc_num+"' and "
                    + DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id+"="+member_ID+" and "
                    + DataBaseHelperClass_btnDownload_E_Kshana.status+"='In-Active'", null);
            if (cursor.getCount()>0){
                if (cursor.moveToNext()){
                    inactive = "Y";
                }
            }else {
                cursor.close();
                inactive = "N";
            }

            Log.d("inactive",""+inactive);

            if (dataEntry.equals("Y")){
                dialog.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                builder.setTitle("Alert")
                        .setMessage(context.getString(R.string.this_member_data_is_already_upoaded_cannoy_upload_it_again))
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton(context.getString(R.string.ok), (dialog, id) -> dialog.cancel());
                final AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
            }else if (dataEntry.equals("N") && inactive.equals("N")){
                Intent i = new Intent(context, E_Kshana_Freeze_Details_FOR_VA.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("rc_num", rc_num);
                i.putExtra("member_ID", member_ID);
                i.putExtra("distCode_ass",distCode_ass);
                i.putExtra("talCode_ass",talCode_ass);
                i.putExtra("hobCode_ass",hobCode_ass);
                i.putExtra("uName_get", uName_get);
                ((Activity) context).finish();
                context.startActivity(i);
            }else if (inactive.equals("Y")){
                dialog.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                builder.setTitle("Alert")
                        .setMessage(context.getString(R.string.this_member_is_inactive))
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton(context.getString(R.string.ok), (dialog, id) -> dialog.cancel());
                final AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
            }

        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

class RC_VA_Member_ViewHolder{
    TextView sl_No, tvMemberName, tvMemberID, tvRCNum, tvDataEntry, tvDistCode_ass, tvTalukCode_ass, tvHobCode_ass,
            tvuName_get, tvStatus;
    Button btnEdit;
    RC_VA_Member_ViewHolder(View view){
        sl_No = view.findViewById(R.id.sl_No);
        tvMemberName = view.findViewById(R.id.tvMemberName);
        tvMemberID = view.findViewById(R.id.tvMemberID);
        tvRCNum = view.findViewById(R.id.tvRCNum);
        tvDataEntry = view.findViewById(R.id.tvDataEntry);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvDistCode_ass = view.findViewById(R.id.tvDistCode_ass);
        tvTalukCode_ass = view.findViewById(R.id.tvTalukCode_ass);
        tvHobCode_ass = view.findViewById(R.id.tvHobCode_ass);
        tvuName_get = view.findViewById(R.id.tvuName_get);
        btnEdit = view.findViewById(R.id.btnEdit);
    }
}