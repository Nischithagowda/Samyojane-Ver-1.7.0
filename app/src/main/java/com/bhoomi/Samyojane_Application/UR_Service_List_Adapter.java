package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Adarsh on 21-Jun-19.
 */

public class UR_Service_List_Adapter extends BaseAdapter implements Filterable {

    private Context context;
    ArrayList<String> SlNo;
    ArrayList<String> Applicant_Name;
    ArrayList<String> GSC_First_Part;
    ArrayList<String> Applicant_ID;
    ArrayList<String> DueDate;
    ArrayList<String> ServiceCode;
    ArrayList<String> ServiceName;
    ArrayList<String> TownName;
    ArrayList<String> TownCode;
    ArrayList<String> WardName;
    ArrayList<String> WardCode;
    ArrayList<String> Option_Flag;
    ArrayList<String> PushedList;
    ArrayList<String> selected_items = new ArrayList<>();
    TextView app_Name;
    String applicant_name;
    String applicant_Id;
    String serviceCode, serviceName, town_code, ward_code, option_Flag;
    String item_position;
    String district, taluk, VA_Name, hobli,VA_Circle_Name, town_Name, ward_Name;
    String district_Code, taluk_Code, hobli_Code, va_Circle_code, villageCode, habitationCode, eng_certi;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    Intent i;
    DataTransferInterface dataTransferInterface;
    Activity activity;
    LayoutInflater inflater;

    UR_Service_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> applicant_Name,
                            ArrayList<String> gsc_firstPart, ArrayList<String> rd_No, ArrayList<String> dueDate,
                            ArrayList<String> serviceCode, ArrayList<String> serviceName, ArrayList<String> townName,
                            ArrayList<String> townCode, ArrayList<String> wardName, ArrayList<String> wardCode,
                            ArrayList<String> option_Flag, ArrayList<String> pushedList,
                            Activity a, DataTransferInterface dtInterface) {

        this.context = context;
        this.SlNo = slNo;
        this.Applicant_Name = applicant_Name;
        this.GSC_First_Part = gsc_firstPart;
        this.Applicant_ID = rd_No;
        this.DueDate = dueDate;
        this.ServiceCode = serviceCode;
        this.ServiceName = serviceName;
        this.TownName = townName;
        this.TownCode = townCode;
        this.WardName = wardName;
        this.WardCode = wardCode;
        this.Option_Flag = option_Flag;
        this.PushedList = pushedList;
        this.activity = a;
        this.dataTransferInterface = dtInterface;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final UR_Service_ViewHolder ur_service_viewHolder;

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list,parent, false);
            ur_service_viewHolder=new UR_Service_ViewHolder(convertView);
            convertView.setTag(ur_service_viewHolder);
        }
        else {
            ur_service_viewHolder = (UR_Service_ViewHolder) convertView.getTag();
        }

        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = df.format(c);

        String due_Date_C = DueDate.get(position);

        try {

            Log.d("formattedDate_Ser", ""+formattedDate);
            Log.d("due_Date_C", ""+due_Date_C);

            Date date1 = df.parse(formattedDate);
            Date date2 = df.parse(due_Date_C);

            if (date1.after(date2) || date1.equals(date2)) {
                //#FFEE0808
                ur_service_viewHolder.gsc_first_part.setTextColor(Color.parseColor("#FFEE0808"));
                ur_service_viewHolder.app_Id.setTextColor(Color.parseColor("#FFEE0808"));
                ur_service_viewHolder.app_dueDate.setTextColor(Color.parseColor("#FFEE0808"));
                Log.d("Date", "Date1 is after Date2");
            }else{
                ur_service_viewHolder.gsc_first_part.setTextColor(Color.parseColor("#ff000000"));
                ur_service_viewHolder.app_Id.setTextColor(Color.parseColor("#ff000000"));
                ur_service_viewHolder.app_dueDate.setTextColor(Color.parseColor("#ff000000"));
                Log.d("Date", "Date1 is before Date2");
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ParseException", ""+e.getMessage());
        }

        ur_service_viewHolder.sl_No.setText(SlNo.get(position));
        ur_service_viewHolder.app_Name.setText(Applicant_Name.get(position));
        ur_service_viewHolder.gsc_first_part.setText(GSC_First_Part.get(position));
        ur_service_viewHolder.app_Id.setText(Applicant_ID.get(position));
        ur_service_viewHolder.app_dueDate.setText(DueDate.get(position));
        ur_service_viewHolder.app_ServiceCode.setText(ServiceCode.get(position));
        ur_service_viewHolder.app_ServiceName.setText(ServiceName.get(position));
        ur_service_viewHolder.tvTownName.setText(TownName.get(position));
        ur_service_viewHolder.tvTownCode.setText(TownCode.get(position));
        ur_service_viewHolder.tvWardName.setText(WardName.get(position));
        ur_service_viewHolder.tvWardCode.setText(WardCode.get(position));
        ur_service_viewHolder.tvOption_Flag.setText(Option_Flag.get(position));
        Log.d("PushedList.get", ""+PushedList.get(position));
        if (PushedList.get(position).equals("1")){
            ur_service_viewHolder.checkbox.setVisibility(View.GONE);
        } else {
            ur_service_viewHolder.checkbox.setVisibility(View.VISIBLE);
        }
        district_Code = Field_Report.Global.district_Code1;
        district = Field_Report.Global.district_Name1;
        taluk_Code = Field_Report.Global.taluk_Code1;
        taluk = Field_Report.Global.taluk_Name1;
        hobli_Code = Field_Report.Global.hobli_Code1;
        hobli = Field_Report.Global.hobli_Name1;
        va_Circle_code = Field_Report.Global.VA_Circle_Code1;
        VA_Circle_Name = Field_Report.Global.VA_Circle_Name1;
        VA_Name = Field_Report.Global.VA_Name1;

        app_Name = convertView.findViewById(R.id.app_Name);
        app_Name.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        app_Name.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                applicant_name = ur_service_viewHolder.app_Name.getText().toString();
                applicant_Id = ur_service_viewHolder.app_Id.getText().toString();
                item_position = String.valueOf(position);
                serviceCode = ur_service_viewHolder.app_ServiceCode.getText().toString();
                serviceName = ur_service_viewHolder.app_ServiceName.getText().toString();
                villageCode = "99999";
                habitationCode = "255";
                town_Name = ur_service_viewHolder.tvTownName.getText().toString();
                town_code = ur_service_viewHolder.tvTownCode.getText().toString();
                ward_Name = ur_service_viewHolder.tvWardName.getText().toString();
                ward_code = ur_service_viewHolder.tvWardCode.getText().toString();
                option_Flag = ur_service_viewHolder.tvOption_Flag.getText().toString();

                Log.d("Applicant_Name", ""+applicant_name);
                Log.d("Applicant_Id", ""+applicant_Id);
                Log.d("Item_Position", ""+item_position);
                Log.d("serviceCode", ""+serviceCode);
                Log.d("serviceName", ""+serviceName);
                Log.d("villageCode", ""+villageCode);
                Log.d("habitationCode", ""+habitationCode);
                Log.d("town_Name", ""+town_Name);
                Log.d("town_code", ""+town_code);
                Log.d("ward_Name", ward_Name);
                Log.d("ward_code", ward_code);
                Log.d("va_Circle_Code", va_Circle_code);
                Log.d("option_Flag", option_Flag);

                if(applicant_Id!=null) {

                    openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(context);
                    database = openHelper.getWritableDatabase();

                    Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                            + " where " + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id, null);

                    if (cursor.getCount() > 0) {
                        if (cursor.moveToNext()) {
                            eng_certi = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate));
                            Log.d("Service_List", "" + eng_certi);
                        }
                    } else {
                        cursor.close();
                    }
                    if (Objects.equals(eng_certi, "E")) {
                        i = new Intent(context, New_Request.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {
                        i = new Intent(context, New_Request_Kan.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    i.putExtra("applicant_name", applicant_name);
                    i.putExtra("applicant_Id", applicant_Id);
                    i.putExtra("serviceCode", serviceCode);
                    i.putExtra("strSearchServiceName", serviceName);
                    i.putExtra("district_Code", district_Code);
                    i.putExtra("districtCode", district);
                    i.putExtra("taluk_Code", taluk_Code);
                    i.putExtra("taluk", taluk);
                    i.putExtra("hobli_Code", hobli_Code);
                    i.putExtra("hobli", hobli);
                    i.putExtra("va_Circle_Code", va_Circle_code);
                    i.putExtra("VA_Circle_Name", VA_Circle_Name);
                    i.putExtra("VA_Name", VA_Name);
                    i.putExtra("eng_certi",eng_certi);
                    i.putExtra("villageCode", villageCode);
                    i.putExtra("habitationCode", habitationCode);
                    i.putExtra("town_Name", town_Name);
                    i.putExtra("town_code", town_code);
                    i.putExtra("ward_Name", ward_Name);
                    i.putExtra("ward_code", ward_code);
                    i.putExtra("option_Flag", option_Flag);
                    context.startActivity(i);
                    ((Activity) context).finish();
                    Log.d("Service", "" + serviceCode);
                }
                else{
                    Toast.makeText(context, "Applicant Id Missing", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ur_service_viewHolder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (ur_service_viewHolder.checkbox.isChecked()){
                applicant_Id = ur_service_viewHolder.app_Id.getText().toString();
                Log.d("Checked_applicant_Id", "" + applicant_Id);
                selected_items.add(applicant_Id);
                Log.d("selected_items", "" + selected_items);
            } else {
                applicant_Id = ur_service_viewHolder.app_Id.getText().toString();
                Log.d("UnChecked_applicant_Id", "" + applicant_Id);
                selected_items.remove(applicant_Id);
                Log.d("selected_items", "" + selected_items);
            }
            dataTransferInterface.setValues(selected_items);
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

class UR_Service_ViewHolder{
    TextView sl_No, app_Name, gsc_first_part, app_Id,app_dueDate, app_ServiceCode, app_ServiceName,
            tvTownName, tvTownCode, tvWardName, tvWardCode, tvOption_Flag;
    CheckBox checkbox;
    UR_Service_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        app_Name = view.findViewById(R.id.app_Name);
        gsc_first_part = view.findViewById(R.id.gsc_first_part);
        app_Id = view.findViewById(R.id.app_Id);
        app_dueDate = view.findViewById(R.id.app_dueDate);
        app_ServiceCode = view.findViewById(R.id.app_ServiceCode);
        app_ServiceName = view.findViewById(R.id.app_ServiceName);
        tvTownName = view.findViewById(R.id.tvTownName);
        tvTownCode = view.findViewById(R.id.tvTownCode);
        tvWardName = view.findViewById(R.id.tvWardName);
        tvWardCode = view.findViewById(R.id.tvWardCode);
        tvOption_Flag = view.findViewById(R.id.tvOption_Flag);
        checkbox = view.findViewById(R.id.checkbox);
    }
}
