package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class RI_Service_List_Adapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<String> SlNo;
    ArrayList<String> Applicant_Name;
    ArrayList<String> Applicant_ID;
    ArrayList<String> DueDate;
    ArrayList<String> ServiceCode;
    ArrayList<String> ServiceName;
    ArrayList<String> VillageCode;
    ArrayList<String> VillageName;
    ArrayList<String> Option_Flag;
    TextView app_Name;
    String applicant_name;
    String applicant_Id;
    String option_Flag, eng_certi;
    String serviceCode;
    String district, taluk, RI_Name, hobli,VA_Circle_Name, VA_Name;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    int district_Code, taluk_Code, hobli_Code, va_Circle_code, villageCode, town_code, ward_code;
    String serviceName, village_name;
    String item_position;
    Intent i;
    Set_and_Get_Service_Parameter set_and_get_service_parameter;
    String uName_get, VA_IMEI;
    int DesiCode;
    SharedPreferences sharedPreferences;

    RI_Service_List_Adapter(Context context, ArrayList<String> slNo, ArrayList<String> applicant_Name, ArrayList<String> rd_No, ArrayList<String> dueDate,
                            ArrayList<String> serviceCode, ArrayList<String> serviceName, ArrayList<String> villageCode,
                            ArrayList<String> villageName, ArrayList<String> option_Flag) {

        this.context = context;
        this.SlNo = slNo;
        this.Applicant_Name = applicant_Name;
        this.Applicant_ID = rd_No;
        this.DueDate = dueDate;
        this.ServiceCode = serviceCode;
        this.ServiceName = serviceName;
        this.VillageCode = villageCode;
        this.VillageName = villageName;
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
        final RI_Service_ViewHolder ri_service_viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.ri_list,parent, false);
            ri_service_viewHolder=new RI_Service_ViewHolder(convertView);
            convertView.setTag(ri_service_viewHolder);
        }
        else {
            ri_service_viewHolder=(RI_Service_ViewHolder) convertView.getTag();
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
                ri_service_viewHolder.app_Id.setTextColor(Color.parseColor("#FFEE0808"));
                ri_service_viewHolder.app_dueDate.setTextColor(Color.parseColor("#FFEE0808"));
                Log.d("Date", "Date1 is after Date2");
            }else{
                ri_service_viewHolder.app_Id.setTextColor(Color.parseColor("#ff000000"));
                ri_service_viewHolder.app_dueDate.setTextColor(Color.parseColor("#ff000000"));
                Log.d("Date", "Date1 is before Date2");
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("ParseException", ""+e.getMessage());
        }

        ri_service_viewHolder.sl_No.setText(SlNo.get(position));
        ri_service_viewHolder.app_Name.setText(Applicant_Name.get(position));
        ri_service_viewHolder.app_Id.setText(Applicant_ID.get(position));
        ri_service_viewHolder.app_dueDate.setText(DueDate.get(position));
        ri_service_viewHolder.app_ServiceCode.setText(ServiceCode.get(position));
        ri_service_viewHolder.app_ServiceName.setText(ServiceName.get(position));
        ri_service_viewHolder.tvVillageCode.setText(VillageCode.get(position));
        ri_service_viewHolder.tvVillageName.setText(VillageName.get(position));
        ri_service_viewHolder.tvOption_Flag.setText(Option_Flag.get(position));

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
            applicant_name = ri_service_viewHolder.app_Name.getText().toString();
            applicant_Id = ri_service_viewHolder.app_Id.getText().toString();
            item_position = String.valueOf(position);
            serviceCode = ri_service_viewHolder.app_ServiceCode.getText().toString();
            serviceName = ri_service_viewHolder.app_ServiceName.getText().toString();
            villageCode = Integer.parseInt(ri_service_viewHolder.tvVillageCode.getText().toString());
            village_name = ri_service_viewHolder.tvVillageName.getText().toString();
            town_code = 9999;
            ward_code = 255;
            option_Flag = ri_service_viewHolder.tvOption_Flag.getText().toString();

            openHelper=new DataBaseHelperClass_Credentials(context);
            database=openHelper.getWritableDatabase();

            Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_Credentials.TABLE_NAME+" where "+ DataBaseHelperClass_Credentials.District_Code+"="+district_Code+" and "
                    + DataBaseHelperClass_Credentials.Taluk_Code+"="+taluk_Code+" and "+DataBaseHelperClass_Credentials.Hobli_Code+"="+hobli_Code+" and "
                    + DataBaseHelperClass_Credentials.VA_circle_Code+"="+va_Circle_code, null);

            if (cursor.getCount()>0){
                if (cursor.moveToNext()){
                    VA_Name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_Name));
                    VA_IMEI = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_IMEI_1));
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
            Log.d("ward_code", ""+ward_code);
            Log.d("option_Flag",""+option_Flag);

            if(applicant_Id!=null) {

                openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(context);
                database = openHelper.getWritableDatabase();

                int AppTitle=0, BinCom = 0, FatTitle=0, Pincode=0, appCat=0, appCaste=0, income = 0, year = 0, month = 0;
                String FatherName = null, MotherName = null, Address1 = null, Address2 = null, Address3 = null, mobNum = null,
                        photo = null, strRemarks = null;

                Cursor cursor1 = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME
                        + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo + "='" + applicant_Id+"'", null);

                if (cursor1.getCount() > 0) {
                    if (cursor1.moveToNext()) {
                        eng_certi = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_Eng_Certificate));
                        AppTitle = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ApplicantTiitle));
                        FatTitle = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RelationTitle));
                        BinCom = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.BinCom));
                        Pincode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.PinCode));
                        FatherName = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.FatherName));
                        MotherName = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.MotherName));
                        Address1 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address1));
                        Address2 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address2));
                        Address3 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address3));
                        mobNum = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Mobile_No));
                        income = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.AnnualIncome));
                        year = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Years_Applied));
                        month = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Mths_Applied));
                        appCat = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ReservationCategory));
                        appCaste = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Caste));
                        photo = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_applicant_photo));
                        strRemarks = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VA_Remarks));

                        Log.d("Service_List", "" + eng_certi);
                    }
                } else {
                    cursor1.close();
                }

                sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                DesiCode = sharedPreferences.getInt(Constants.DesiCode_RI, 19);
                uName_get = sharedPreferences.getString(Constants.uName_get, "");

                Cursor cursor2 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1+" where "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_GSCNo+"='"+applicant_Id+"'", null);
                if (cursor2.getCount()>0){
                    if(cursor2.moveToNext()) {

                        database.execSQL("delete from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where "
                                + DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo + "='" + applicant_Id + "'");

                        Log.d("Database", "ServiceParameterTable delete GSC_No:" + applicant_Id);
                        cursor2.close();
                    }
                }

                set_and_get_service_parameter = new Set_and_Get_Service_Parameter();
                set_and_get_service_parameter.setGSCNo1(applicant_Id);
                set_and_get_service_parameter.setLoginID(uName_get);
                set_and_get_service_parameter.setDesignationCode(DesiCode);
                set_and_get_service_parameter.setService_Code(Integer.parseInt(serviceCode));
                set_and_get_service_parameter.setAppTitle(AppTitle);
                set_and_get_service_parameter.setBinCom(BinCom);
                set_and_get_service_parameter.setFatTitle(FatTitle);
                set_and_get_service_parameter.setFatherName(FatherName);
                set_and_get_service_parameter.setMotherName(MotherName);
                set_and_get_service_parameter.setUpd_MobileNumber(mobNum);
                set_and_get_service_parameter.setAddress1(Address1);
                set_and_get_service_parameter.setAddress2(Address2);
                set_and_get_service_parameter.setAddress3(Address3);
                set_and_get_service_parameter.setPinCode(Pincode);
                set_and_get_service_parameter.setApplicant_Category(appCat);
                set_and_get_service_parameter.setApplicant_Caste(appCaste);
                set_and_get_service_parameter.setCasteSl(0);
                set_and_get_service_parameter.setIncome(income);
                set_and_get_service_parameter.setPhoto(photo);
                set_and_get_service_parameter.setRemarks(strRemarks);
                set_and_get_service_parameter.setTotal_No_Years_10(year);
                set_and_get_service_parameter.setNO_Months_10(month);
                set_and_get_service_parameter.setApp_Father_Category_8(0);
                set_and_get_service_parameter.setAPP_Father_Caste_8(0);
                set_and_get_service_parameter.setApp_Mother_Category_8(0);
                set_and_get_service_parameter.setAPP_Mother_Caste_8(0);
                set_and_get_service_parameter.setReason_for_Creamy_Layer_6(0);
                set_and_get_service_parameter.setUpdated_By_VA_IMEI(VA_IMEI);
                set_and_get_service_parameter.setUpdated_By_VA_Name(VA_Name);
                set_and_get_service_parameter.setDataUpdateFlag(0);

                database.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + "("
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_GSCNo+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_LoginID+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DesignationCode+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Service_Code+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_AppTitle+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_BinCom+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_FatTitle+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_FatherName+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_MotherName+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_MobileNumber +","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address1 +","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address2 +","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address3 +","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_PinCode+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Applicant_Category+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Applicant_Caste+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_CasteSl+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Income+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Photo+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Remarks+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Total_No_Years_10+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_NO_Months_10+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_App_Father_Category_8+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_App_Mother_Category_8+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_APP_Father_Caste_8+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_APP_Mother_Caste_8+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Reason_for_Creamy_Layer_6+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_VA_RI_IMEI+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_VA_RI_Name+","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DataUpdateFlag
                        +") values ('"
                        + set_and_get_service_parameter.getGSCNo1() + "','"
                        + set_and_get_service_parameter.getLoginID() + "',"
                        + set_and_get_service_parameter.getDesignationCode() + ","
                        + set_and_get_service_parameter.getService_Code() + ","
                        + set_and_get_service_parameter.getAppTitle() + ","
                        + set_and_get_service_parameter.getBinCom() + ","
                        + set_and_get_service_parameter.getFatTitle() + ",'"
                        + set_and_get_service_parameter.getFatherName() + "','"
                        + set_and_get_service_parameter.getMotherName() + "','"
                        + set_and_get_service_parameter.getUpd_MobileNumber() + "','"
                        + set_and_get_service_parameter.getAddress1() + "','"
                        + set_and_get_service_parameter.getAddress2() + "','"
                        + set_and_get_service_parameter.getAddress3() + "',"
                        + set_and_get_service_parameter.getPinCode() + ","
                        + set_and_get_service_parameter.getApplicant_Category() + ","
                        + set_and_get_service_parameter.getApplicant_Caste() + ","
                        + set_and_get_service_parameter.getCasteSl() + ","
                        + set_and_get_service_parameter.getIncome() + ",'"
                        + set_and_get_service_parameter.getPhoto() + "','"
                        + set_and_get_service_parameter.getRemarks() + "',"
                        + set_and_get_service_parameter.getTotal_No_Years_10() + ","
                        + set_and_get_service_parameter.getNO_Months_10() + ","
                        + set_and_get_service_parameter.getApp_Father_Category_8() + ","
                        + set_and_get_service_parameter.getApp_Mother_Category_8() + ","
                        + set_and_get_service_parameter.getAPP_Father_Caste_8() + ","
                        + set_and_get_service_parameter.getAPP_Mother_Caste_8() + ","
                        + set_and_get_service_parameter.getReason_for_Creamy_Layer_6() + ",'"
                        + set_and_get_service_parameter.getUpdated_By_VA_IMEI() + "','"
                        + set_and_get_service_parameter.getUpdated_By_VA_Name() + "',"
                        + set_and_get_service_parameter.getDataUpdateFlag() + ")");

                Log.d("Database", "ServiceParameterTable Database Inserted");

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
                        i.putExtra("district", district);
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
                        i.putExtra("town_code", town_code);
                        i.putExtra("ward_code", ward_code);
                        i.putExtra("option_Flag",option_Flag);
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
                        i.putExtra("district", district);
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
                        i.putExtra("town_code", town_code);
                        i.putExtra("ward_code", ward_code);
                        i.putExtra("option_Flag",option_Flag);
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
                        i.putExtra("district", district);
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
                        i.putExtra("town_code", town_code);
                        i.putExtra("ward_code", ward_code);
                        i.putExtra("option_Flag",option_Flag);
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
class RI_Service_ViewHolder{
    TextView sl_No, app_Name, app_Id,app_dueDate, app_ServiceCode, app_ServiceName,
            tvVillageCode, tvVillageName, tvOption_Flag;
    RI_Service_ViewHolder(View view) {
        sl_No = view.findViewById(R.id.sl_No);
        app_Name = view.findViewById(R.id.app_Name);
        app_Id = view.findViewById(R.id.app_Id);
        app_dueDate = view.findViewById(R.id.app_dueDate);
        app_ServiceCode = view.findViewById(R.id.app_ServiceCode);
        app_ServiceName = view.findViewById(R.id.app_ServiceName);
        tvVillageCode = view.findViewById(R.id.tvVillageCode);
        tvVillageName = view.findViewById(R.id.tvVillageName);
        tvOption_Flag = view.findViewById(R.id.tvOption_Flag);
    }
}

