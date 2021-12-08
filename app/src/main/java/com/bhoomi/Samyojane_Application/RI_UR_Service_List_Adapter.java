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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Adarsh on 26-Jun-19.
 */

public class RI_UR_Service_List_Adapter  extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<String> SlNo;
    ArrayList<String> Applicant_Name;
    ArrayList<String> Applicant_ID;
    ArrayList<String> DueDate;
    ArrayList<String> ServiceCode;
    ArrayList<String> ServiceName;
    ArrayList<String> TownName;
    ArrayList<String> TownCode;
    ArrayList<String> WardName;
    ArrayList<String> WardCode;
    ArrayList<String> Option_Flag;
    TextView app_Name;
    String applicant_name;
    String applicant_Id;
    String town_Name, ward_Name;
    String villageCode, town_code, ward_code, option_Flag, eng_certi;
    String serviceCode;
    String district, taluk, RI_Name, hobli,VA_Circle_Name, VA_Name;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    String district_Code, taluk_Code, hobli_Code, va_Circle_code;
    String serviceName, village_name;
    String item_position;
    Intent i;

    RI_UR_Service_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> applicant_Name, ArrayList<String> rd_No, ArrayList<String> dueDate,
                            ArrayList<String> serviceCode, ArrayList<String> serviceName, ArrayList<String> townName,
                            ArrayList<String> townCode, ArrayList<String> wardName, ArrayList<String> wardCode,
                            ArrayList<String> option_Flag) {

        this.context = context;
        this.SlNo = slNo;
        this.Applicant_Name = applicant_Name;
        this.Applicant_ID = rd_No;
        this.DueDate = dueDate;
        this.ServiceCode = serviceCode;
        this.ServiceName = serviceName;
        this.TownName = townName;
        this.TownCode = townCode;
        this.WardName = wardName;
        this.WardCode = wardCode;
        this.Option_Flag = option_Flag;
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


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final RI_UR_Service_ViewHolder ri_ur_service_viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.ri_list,parent, false);
            ri_ur_service_viewHolder=new RI_UR_Service_ViewHolder(convertView);
            convertView.setTag(ri_ur_service_viewHolder);
        }
        else {
            ri_ur_service_viewHolder=(RI_UR_Service_ViewHolder) convertView.getTag();
        }


        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        String formattedDate = df.format(c);

        String due_Date_C = DueDate.get(position);

        try {

            Log.d("formattedDate_Ser", ""+formattedDate);
            Log.d("due_Date_C", ""+due_Date_C);

            Date date1 = df.parse(formattedDate);
            Date date2 = df.parse(due_Date_C);

            assert date1 != null;
            if (date1.after(date2) || date1.equals(date2)) {
                ri_ur_service_viewHolder.app_Id.setTextColor(Color.parseColor("#FFEE0808"));
                ri_ur_service_viewHolder.app_dueDate.setTextColor(Color.parseColor("#FFEE0808"));
                Log.d("Date", "Date1 is after Date2");
            }else{
                ri_ur_service_viewHolder.app_Id.setTextColor(Color.parseColor("#ff000000"));
                ri_ur_service_viewHolder.app_dueDate.setTextColor(Color.parseColor("#ff000000"));
                Log.d("Date", "Date1 is before Date2");
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ParseException", ""+e.getMessage());
        }

        ri_ur_service_viewHolder.sl_No.setText(SlNo.get(position));
        ri_ur_service_viewHolder.app_Name.setText(Applicant_Name.get(position));
        ri_ur_service_viewHolder.app_Id.setText(Applicant_ID.get(position));
        ri_ur_service_viewHolder.app_dueDate.setText(DueDate.get(position));
        ri_ur_service_viewHolder.app_ServiceCode.setText(ServiceCode.get(position));
        ri_ur_service_viewHolder.app_ServiceName.setText(ServiceName.get(position));
        ri_ur_service_viewHolder.tvTownName.setText(TownName.get(position));
        ri_ur_service_viewHolder.tvTownCode.setText(TownCode.get(position));
        ri_ur_service_viewHolder.tvWardName.setText(WardName.get(position));
        ri_ur_service_viewHolder.tvWardCode.setText(WardCode.get(position));
        ri_ur_service_viewHolder.tvOption_Flag.setText(Option_Flag.get(position));

        district_Code = RI_Field_Report.Global.district_Code1;
        district = RI_Field_Report.Global.district_Name1;
        taluk_Code = RI_Field_Report.Global.taluk_Code1;
        taluk = RI_Field_Report.Global.taluk_Name1;
        hobli_Code = RI_Field_Report.Global.hobli_Code1;
        hobli = RI_Field_Report.Global.hobli_Name1;
        va_Circle_code = RI_Field_Report.Global.VA_Circle_Code1;
        VA_Circle_Name = RI_Field_Report.Global.VA_Circle_Name1;
        RI_Name = RI_Field_Report.Global.RI_Name1;


//        Log.d("district_Code", ""+district_Code);
//        Log.d("taluk_Code", ""+taluk_Code);
//        Log.d("hobli", ""+hobli);
//        Log.d("RI_va_Circle_code_se",""+ va_Circle_code);
//        Log.d("RI_VA_Circle_Name_se", ""+VA_Circle_Name);
//        Log.d("RI_Name",""+RI_Name);

        app_Name = convertView.findViewById(R.id.app_Name);
        app_Name.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        app_Name.setOnClickListener(v -> {
            applicant_name = ri_ur_service_viewHolder.app_Name.getText().toString();
            applicant_Id = ri_ur_service_viewHolder.app_Id.getText().toString();
            item_position = String.valueOf(position);
            serviceCode = ri_ur_service_viewHolder.app_ServiceCode.getText().toString();
            serviceName = ri_ur_service_viewHolder.app_ServiceName.getText().toString();
            villageCode = "99999";
            town_Name = ri_ur_service_viewHolder.tvTownName.getText().toString();
            town_code = ri_ur_service_viewHolder.tvTownCode.getText().toString();
            ward_Name = ri_ur_service_viewHolder.tvWardName.getText().toString();
            ward_code = ri_ur_service_viewHolder.tvWardCode.getText().toString();
            option_Flag = ri_ur_service_viewHolder.tvOption_Flag.getText().toString();

            openHelper=new DataBaseHelperClass_Credentials(context);
            database=openHelper.getWritableDatabase();

            Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_Credentials.TABLE_NAME+" where "+ DataBaseHelperClass_Credentials.District_Code+"="+district_Code+" and "
                    + DataBaseHelperClass_Credentials.Taluk_Code+"="+taluk_Code+" and "+DataBaseHelperClass_Credentials.Hobli_Code+"="+hobli_Code+" and "
                    + DataBaseHelperClass_Credentials.VA_circle_Code+"="+va_Circle_code, null);

            if (cursor.getCount()>0){
                if (cursor.moveToNext()){
                    VA_Name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_Name));
                    Log.d("VA_Name:", VA_Name);
                }
            } else {
                cursor.close();
            }

            Log.d("Applicant_Name", ""+applicant_name);
            Log.d("Applicant_Id", ""+applicant_Id);
            Log.d("Item_Position", ""+item_position);
            Log.d("serviceCode", ""+serviceCode);
            Log.d("serviceName", ""+serviceName);
            Log.d("villageCode", ""+villageCode);
            Log.d("strSearchVillageName",""+ village_name);
            Log.d("RI_va_Circle_code_ser",""+ va_Circle_code);
            Log.d("RI_VA_Circle_Name_ser", ""+VA_Circle_Name);
            Log.d("town_code", ""+town_code);
            Log.d("ward_code", ward_code);
            Log.d("option_Flag",option_Flag);

            if(applicant_Id!=null) {

                openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(context);
                database = openHelper.getWritableDatabase();

                Cursor cursor1 = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME
                        + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo + "='" + applicant_Id+"'", null);

                if (cursor1.getCount() > 0) {
                    if (cursor1.moveToNext()) {
                        eng_certi = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_Eng_Certificate));
                        Log.d("Service_List", "" + eng_certi);
                    }
                } else {
                    cursor1.close();
                }

                switch (serviceCode) {
                    case "6":
                    case "9":
                    case "11":
                    case "34":
                    case "37":
                    case "43":
                    {
                        if (Objects.equals(eng_certi, "E")) {
                            i = new Intent(context, RI_Field_Report_caste_income_parameters.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            i = new Intent(context, RI_Field_Report_caste_income_parameters_Kan.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        i.putExtra("applicant_name", applicant_name);
                        i.putExtra("applicant_Id", applicant_Id);
                        i.putExtra("district_Code", district_Code);
                        i.putExtra("districtCode", district);
                        i.putExtra("taluk_Code", taluk_Code);
                        i.putExtra("taluk", taluk);
                        i.putExtra("RI_Name", RI_Name);
                        i.putExtra("VA_Name", VA_Name);
                        i.putExtra("hobli", hobli);
                        i.putExtra("hobli_Code", hobli_Code);
                        i.putExtra("VA_Circle_Name", VA_Circle_Name);
                        i.putExtra("strSearchServiceName", serviceName);
                        i.putExtra("strSearchVillageName", village_name);
                        i.putExtra("serviceCode", serviceCode);
                        i.putExtra("villageCode", villageCode);
                        i.putExtra("va_Circle_Code", va_Circle_code);
                        i.putExtra("town_Name", town_Name);
                        i.putExtra("town_code", town_code);
                        i.putExtra("ward_Name", ward_Name);
                        i.putExtra("ward_code", ward_code);
                        i.putExtra("option_Flag", option_Flag);
                        i.putExtra("eng_certi",eng_certi);
                        context.startActivity(i);
                        ((Activity) context).finish();
                        Log.d("Service", ""+serviceCode);
                        Log.d("villageCode", ""+ villageCode);
                        break;
                    }
                    case "7":
                    case "8":
                    case "42": {
                        if (Objects.equals(eng_certi, "E")) {
                            i = new Intent(context, RI_Field_Report_Caste_sc_st_certi_Parameters.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            i = new Intent(context, RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        i.putExtra("applicant_name", applicant_name);
                        i.putExtra("applicant_Id", applicant_Id);
                        i.putExtra("district_Code", district_Code);
                        i.putExtra("districtCode", district);
                        i.putExtra("taluk_Code", taluk_Code);
                        i.putExtra("taluk", taluk);
                        i.putExtra("RI_Name", RI_Name);
                        i.putExtra("VA_Name", VA_Name);
                        i.putExtra("hobli", hobli);
                        i.putExtra("hobli_Code", hobli_Code);
                        i.putExtra("VA_Circle_Name", VA_Circle_Name);
                        i.putExtra("strSearchServiceName", serviceName);
                        i.putExtra("strSearchVillageName", village_name);
                        i.putExtra("serviceCode", serviceCode);
                        i.putExtra("villageCode", villageCode);
                        i.putExtra("va_Circle_Code", va_Circle_code);
                        i.putExtra("town_Name", town_Name);
                        i.putExtra("town_code", town_code);
                        i.putExtra("ward_Name", ward_Name);
                        i.putExtra("ward_code", ward_code);
                        i.putExtra("option_Flag", option_Flag);
                        i.putExtra("eng_certi",eng_certi);
                        context.startActivity(i);
                        ((Activity) context).finish();
                        Log.d("Service", ""+serviceCode);
                        Log.d("villageCode", ""+ villageCode);
                        break;
                    }
                    case "10": {
                        i = new Intent(context, RI_Field_Report_Resident_Parameters.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("applicant_name", applicant_name);
                        i.putExtra("applicant_Id", applicant_Id);
                        i.putExtra("district_Code", district_Code);
                        i.putExtra("districtCode", district);
                        i.putExtra("taluk_Code", taluk_Code);
                        i.putExtra("taluk", taluk);
                        i.putExtra("RI_Name", RI_Name);
                        i.putExtra("VA_Name", VA_Name);
                        i.putExtra("hobli", hobli);
                        i.putExtra("hobli_Code", hobli_Code);
                        i.putExtra("VA_Circle_Name", VA_Circle_Name);
                        i.putExtra("strSearchServiceName", serviceName);
                        i.putExtra("strSearchVillageName", village_name);
                        i.putExtra("serviceCode", serviceCode);
                        i.putExtra("villageCode", villageCode);
                        i.putExtra("va_Circle_Code", va_Circle_code);
                        i.putExtra("town_Name", town_Name);
                        i.putExtra("town_code", town_code);
                        i.putExtra("ward_Name", ward_Name);
                        i.putExtra("ward_code", ward_code);
                        i.putExtra("option_Flag", option_Flag);
                        i.putExtra("eng_certi",eng_certi);
                        context.startActivity(i);
                        ((Activity) context).finish();
                        Log.d("Service", ""+serviceCode);
                        Log.d("villageCode", ""+ villageCode);
                        break;
                    }
                    default:
                        Toast.makeText(context, "Service not available for this Service", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            else{
                Toast.makeText(context, "Applicant Id Missing", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
class RI_UR_Service_ViewHolder{
    TextView sl_No, app_Name, app_Id,app_dueDate, app_ServiceCode, app_ServiceName,
            tvTownName, tvTownCode, tvWardName, tvWardCode, tvOption_Flag;
    RI_UR_Service_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        app_Name = view.findViewById(R.id.app_Name);
        app_Id = view.findViewById(R.id.app_Id);
        app_dueDate = view.findViewById(R.id.app_dueDate);
        app_ServiceCode = view.findViewById(R.id.app_ServiceCode);
        app_ServiceName = view.findViewById(R.id.app_ServiceName);
        tvTownName = view.findViewById(R.id.tvTownName);
        tvTownCode = view.findViewById(R.id.tvTownCode);
        tvWardName = view.findViewById(R.id.tvWardName);
        tvWardCode = view.findViewById(R.id.tvWardCode);
        tvOption_Flag = view.findViewById(R.id.tvOption_Flag);
    }
}
