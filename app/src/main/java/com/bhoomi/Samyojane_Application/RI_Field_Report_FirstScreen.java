package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class RI_Field_Report_FirstScreen extends AppCompatActivity {

    Button btnBack;
    TextView tvHobli, tvTaluk, tvRI_Name, tvVillageName, tvServiceName, tv_V_T_Name;
    String RI_Name,district, taluk, hobli, VA_Circle_Name, VA_Name;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Appication_Name = new ArrayList<>();
    ArrayList<String> Applicant_ID = new ArrayList<>();
    ArrayList<String> DueDate = new ArrayList<>();
    ArrayList<String> ServiceCode = new ArrayList<>();
    ArrayList<String> ServiceName = new ArrayList<>();
    ArrayList<String> VillageCode = new ArrayList<>();
    ArrayList<String> VillageName = new ArrayList<>();
    ArrayList<String> Option_Flag = new ArrayList<>();
    ArrayList<String> TownName = new ArrayList<>();
    ArrayList<String> TownCode = new ArrayList<>();
    ArrayList<String> WardName = new ArrayList<>();
    ArrayList<String> WardCode = new ArrayList<>();
    ListView listView;
    RI_Service_List_Adapter ri_service_list_adapter;
    RI_UR_Service_List_Adapter ri_ur_service_list_adapter;
    LinearLayout linearLayout;
    TextView emptyTxt;
    String village_name, service_name, town_Name, ward_Name, town_code, ward_code,option_Flag;
    String serviceCode, villageCode;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"MissingPermission", "HardwareIds", "SetTextI18n", "ClickableViewAccessibility", "SdCardPath"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_field_report_firstscreen);

        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvRI_Name = findViewById(R.id.RI_name);

        btnBack = findViewById(R.id.btnBack);
        listView = findViewById(R.id.list);
        linearLayout = findViewById(R.id.total_Applicants);
        emptyTxt = findViewById(R.id.emptyTxt);
        tvServiceName = findViewById(R.id.tvServiceName);
        tvVillageName = findViewById(R.id.tvVillageName);
        tv_V_T_Name = findViewById(R.id.tv_V_T_Name);

        linearLayout.setVisibility(View.GONE);

        Intent i = getIntent();
        district_Code = i.getStringExtra("district_Code");
        district = i.getStringExtra("districtCode");
        taluk_Code = i.getStringExtra("taluk_Code");
        taluk = i.getStringExtra("taluk");
        hobli_Code = i.getStringExtra("hobli_Code");
        hobli = i.getStringExtra("hobli");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        RI_Name = i.getStringExtra("RI_Name");
        VA_Name = i.getStringExtra("VA_Name");
        villageCode = i.getStringExtra("villageCode");
        village_name = i.getStringExtra("strSearchVillageName");
        service_name = i.getStringExtra("strSearchServiceName");
        town_code = i.getStringExtra("town_code");
        town_Name = i.getStringExtra("town_Name");
        ward_code = i.getStringExtra("ward_code");
        ward_Name = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");

        openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(RI_Field_Report_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME+" where "
                + getString(R.string.facility_table_name)+"='"+service_name+"'", null);
        if (cursor.getCount()>0){
            if (cursor.moveToNext()){
                serviceCode = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code));
            }
        } else {
            cursor.close();
        }

        Log.d("RI_FirstScreen", ""+district_Code);
        Log.d("RI_FirstScreen", ""+taluk_Code);
        Log.d("RI_FirstScreen",""+hobli_Code+"RI_Name :"+RI_Name+" VA_Name :"+VA_Name+" VillageName :"+village_name+"ServiceName:"+service_name);
        Log.d("RI_FirstScreen", "serviceCode: "+serviceCode);
        Log.d("RI_FirstScreen", "villageCode: "+villageCode+"\nVA_Circle_Name :"+VA_Circle_Name+"\nva_Circle_Code"+va_Circle_Code);
        Log.d("RI_FirstScreen", "town_code: "+town_code);
        Log.d("RI_FirstScreen", "town_Name: "+town_Name);
        Log.d("RI_FirstScreen", "ward_code: "+ward_code);
        Log.d("RI_FirstScreen", "ward_Name: "+ward_Name);
        Log.d("RI_FirstScreen", "option_Flag: "+option_Flag);
        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvRI_Name.setText(RI_Name);
        tvServiceName.setText(service_name);

        if(!Objects.equals(villageCode, "99999")){
            Log.d("Data","Rural");
            tv_V_T_Name.setText(getString(R.string.village_name)+" : ");
            tvVillageName.setText(village_name);
            displayData_AfterItemSelected();
        }else if(!Objects.equals(town_code, "9999")){
            Log.d("Data","Urban");
            tv_V_T_Name.setText(getString(R.string.town_name)+"                : "
                    +"\n"+getString(R.string.ward_name_num)+"    : ");
            tvVillageName.setText(town_Name+"\n"+ward_Name);
            display_Urban_Data_AfterItemSelected();
        }

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    public void displayData_AfterItemSelected() {
        int i=1;
        Log.d("For_Display_Data", district_Code+" "+taluk_Code+" "+hobli_Code+" "+va_Circle_Code+" "+villageCode+" "+serviceCode);
        Log.d("InDisplay", ""+ i);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name+","
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo+","
                +" substr(ST_DueDate,0,11) as ST_DueDate from "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.District_Code+"="+district_Code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Taluk_Code+"="+taluk_Code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Hobli_Code+"="+hobli_Code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VillageCircle_Code+"="+va_Circle_Code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"="+villageCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"=9999 and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code+"=255 and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null", null);

        SlNo.clear();
        Appication_Name.clear();
        Applicant_ID.clear();
        DueDate.clear();
        ServiceCode.clear();
        ServiceName.clear();
        VillageCode.clear();
        VillageName.clear();
        Option_Flag.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Appication_Name.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name)));
                    Applicant_ID.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo)));
                    DueDate.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Due_Date)));
                    ServiceCode.add(String.valueOf(serviceCode));
                    ServiceName.add(service_name);
                    VillageCode.add(String.valueOf(villageCode));
                    VillageName.add(village_name);
                    Option_Flag.add(option_Flag);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            ri_service_list_adapter = new RI_Service_List_Adapter(RI_Field_Report_FirstScreen.this, SlNo, Appication_Name, Applicant_ID, DueDate, ServiceCode, ServiceName, VillageCode, VillageName, Option_Flag);
            listView.setAdapter(ri_service_list_adapter);
            database.close();
            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.close();
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            linearLayout.setVisibility(View.VISIBLE);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public void display_Urban_Data_AfterItemSelected() {
        int i=1;
        Log.d("For_Display_Data", district_Code+" "+taluk_Code+" "+hobli_Code+" "+villageCode+" "+serviceCode+" "+town_code+" "+ward_code);
        Log.d("InDisplay", ""+ i);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name+","
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo+","
                +" substr(ST_DueDate,0,11) as ST_DueDate from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.District_Code+"="+district_Code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Taluk_Code+"="+taluk_Code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Hobli_Code+"="+hobli_Code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"=99999 and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"="+town_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code+"="+ward_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null", null);

        SlNo.clear();
        Appication_Name.clear();
        Applicant_ID.clear();
        DueDate.clear();
        ServiceCode.clear();
        ServiceName.clear();
        TownName.clear();
        TownCode.clear();
        WardName.clear();
        WardCode.clear();
        Option_Flag.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Appication_Name.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name)));
                    Applicant_ID.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo)));
                    DueDate.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Due_Date)));
                    ServiceCode.add(String.valueOf(serviceCode));
                    ServiceName.add(service_name);
                    TownName.add(town_Name);
                    TownCode.add(town_code);
                    WardName.add(ward_Name);
                    WardCode.add(ward_code);
                    Option_Flag.add(option_Flag);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            ri_ur_service_list_adapter = new RI_UR_Service_List_Adapter(RI_Field_Report_FirstScreen.this, SlNo, Appication_Name, Applicant_ID, DueDate, ServiceCode, ServiceName, TownName, TownCode, WardName, WardCode, Option_Flag);
            listView.setAdapter(ri_ur_service_list_adapter);
            database.close();
            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.close();
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            linearLayout.setVisibility(View.VISIBLE);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RI_Field_Report_FirstScreen.this, RI_Field_Report.class);
        i.putExtra("district_Code", district_Code);
        i.putExtra("districtCode", district);
        i.putExtra("taluk_Code", taluk_Code);
        i.putExtra("taluk", taluk);
        i.putExtra("hobli_Code", hobli_Code);
        i.putExtra("hobli", hobli);
        i.putExtra("va_Circle_Code", va_Circle_Code);
        i.putExtra("VA_Circle_Name", VA_Circle_Name);
        i.putExtra("VA_Name", VA_Name);
        i.putExtra("RI_Name",RI_Name );
        i.putExtra("serviceCode", serviceCode);
        i.putExtra("strSearchServiceName", service_name);
        i.putExtra("strSearchVillageName", village_name);
        i.putExtra("villageCode", villageCode);
        i.putExtra("town_Name", town_Name);
        i.putExtra("town_code", town_code);
        i.putExtra("ward_Name", ward_Name);
        i.putExtra("ward_code", ward_code);
        i.putExtra("option_Flag", option_Flag);
        startActivity(i);
        finish();
    }
}
