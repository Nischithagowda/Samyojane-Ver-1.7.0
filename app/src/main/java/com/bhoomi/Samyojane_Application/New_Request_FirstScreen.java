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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class New_Request_FirstScreen extends AppCompatActivity implements DataTransferInterface {

    Button btnBack, btnPushToAnotherVA;
    TextView tvHobli, tvTaluk, tvVA_Name, tvVillageName, tvServiceName, tv_V_T_Name, txtListPushed;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code, town_Name, ward_Name, town_code, ward_code;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Applicant_Name = new ArrayList<>();
    ArrayList<String> GSC_FirstPart = new ArrayList<>();
    ArrayList<String> Applicant_ID = new ArrayList<>();
    ArrayList<String> DueDate = new ArrayList<>();
    ArrayList<String> ServiceCode = new ArrayList<>();
    ArrayList<String> ServiceName = new ArrayList<>();
    ArrayList<String> VillageCode = new ArrayList<>();
    ArrayList<String> HabitationCode = new ArrayList<>();
    ArrayList<String> Option_Flag = new ArrayList<>();
    ArrayList<String> TownName = new ArrayList<>();
    ArrayList<String> TownCode = new ArrayList<>();
    ArrayList<String> WardName = new ArrayList<>();
    ArrayList<String> WardCode = new ArrayList<>();

    ArrayList<String> SlNo1 = new ArrayList<>();
    ArrayList<String> Applicant_Name1 = new ArrayList<>();
    ArrayList<String> GSC_FirstPart1 = new ArrayList<>();
    ArrayList<String> Applicant_ID1 = new ArrayList<>();
    ArrayList<String> DueDate1 = new ArrayList<>();
    ArrayList<String> ServiceCode1 = new ArrayList<>();
    ArrayList<String> ServiceName1 = new ArrayList<>();
    ArrayList<String> VillageCode1 = new ArrayList<>();
    ArrayList<String> HabitationCode1 = new ArrayList<>();
    ArrayList<String> Option_Flag1 = new ArrayList<>();
    ArrayList<String> TownName1 = new ArrayList<>();
    ArrayList<String> TownCode1 = new ArrayList<>();
    ArrayList<String> WardName1 = new ArrayList<>();
    ArrayList<String> WardCode1 = new ArrayList<>();

    ListView listView, list_pushed;
    Service_List_Adapter list_adapter, list_adapter_pushed;
    UR_Service_List_Adapter ur_service_list_adapter, ur_service_list_adapter_pushed;
    TextView emptyTxt;
    String villageCode, service_name, village_name, habitationCode, option_Flag;
    int serviceCode;
    ArrayList<String> selected_items;
    LinearLayout listLayout_pushed;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"MissingPermission", "HardwareIds", "SetTextI18n", "ClickableViewAccessibility", "SdCardPath"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_request_firstscreen);

        btnBack = findViewById(R.id.btnBack);
        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvVA_Name = findViewById(R.id.VA_name);
        listView = findViewById(R.id.list);
        emptyTxt = findViewById(R.id.emptyTxt);
        tvServiceName = findViewById(R.id.tvServiceName);
        tvVillageName = findViewById(R.id.tvVillageName);
        tv_V_T_Name = findViewById(R.id.tv_V_T_Name);
        btnPushToAnotherVA = findViewById(R.id.btnPushToAnotherVA);
        txtListPushed = findViewById(R.id.txtListPushed);
        listLayout_pushed = findViewById(R.id.listLayout_pushed);
        list_pushed = findViewById(R.id.list_pushed);

        listLayout_pushed.setVisibility(View.GONE);
        txtListPushed.setVisibility(View.GONE);

        Intent i = getIntent();
        district_Code = i.getStringExtra("district_Code");
        district = i.getStringExtra("districtCode");
        taluk_Code = i.getStringExtra("taluk_Code");
        taluk = i.getStringExtra("taluk");
        hobli_Code = i.getStringExtra("hobli_Code");
        hobli = i.getStringExtra("hobli");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        VA_Name = i.getStringExtra("VA_Name");
        service_name = i.getStringExtra("strSearchServiceName");
        villageCode = i.getStringExtra("villageCode");
        habitationCode = i.getStringExtra("habitationCode");
        village_name = i.getStringExtra("strSearchVillageName");
        town_code = i.getStringExtra("town_code");
        town_Name = i.getStringExtra("town_Name");
        ward_code = i.getStringExtra("ward_code");
        ward_Name = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");

        openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME+" where "
                +getString(R.string.facility_table_name)+"='"+service_name+"'", null);
        if (cursor.getCount()>0){
            if (cursor.moveToNext()){
                serviceCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code));
            }
        }

        Log.d("New_Request_FirstScreen", "district_Code: "+district_Code);
        Log.d("New_Request_FirstScreen", "taluk_Code: "+taluk_Code);
        Log.d("New_Request_FirstScreen", "hobli_Code: "+hobli_Code+"\nVillageCircleCode: "+va_Circle_Code+"\nServiceName: "+service_name);
        Log.d("New_Request_FirstScreen", "village_name: "+village_name);
        Log.d("New_Request_FirstScreen", "serviceCode: "+serviceCode);
        Log.d("New_Request_FirstScreen", "villageCode: "+villageCode);
        Log.d("New_Request_FirstScreen", "habitationCode: "+habitationCode);
        Log.d("New_Request_FirstScreen", "town_code: "+town_code);
        Log.d("New_Request_FirstScreen", "town_Name: "+town_Name);
        Log.d("New_Request_FirstScreen", "ward_code: "+ward_code);
        Log.d("New_Request_FirstScreen", "ward_Name: "+ward_Name);
        Log.d("New_Request_FirstScreen", "option_Flag: "+option_Flag);

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

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);
        tvServiceName.setText(service_name);

        btnBack.setOnClickListener(v -> onBackPressed());

        selected_items = new ArrayList<>();

        btnPushToAnotherVA.setOnClickListener(v -> {
            if (selected_items.equals("") || selected_items.size()==0){
                Toast.makeText(getApplicationContext(), getString(R.string.select_any_applicant_to_push), Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, PushToAnotherVA.class);
                intent.putStringArrayListExtra("selected_items", selected_items);
                intent.putExtra("taluk", taluk);
                intent.putExtra("hobli", hobli);
                intent.putExtra("VA_Name", VA_Name);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void displayData_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor1 = database.rawQuery("select * from "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "="+villageCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code+"="+habitationCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"=9999 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"=255 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+" ='1'", null);

        SlNo1.clear();
        Applicant_Name1.clear();
        GSC_FirstPart1.clear();
        Applicant_ID1.clear();
        DueDate1.clear();
        ServiceCode1.clear();
        ServiceName1.clear();
        VillageCode1.clear();
        HabitationCode1.clear();
        Option_Flag1.clear();

        if(cursor1.getCount()>0) {
            listLayout_pushed.setVisibility(View.VISIBLE);
            txtListPushed.setVisibility(View.VISIBLE);
            if (cursor1.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo1.add(String.valueOf(i));
                    Applicant_Name1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                    GSC_FirstPart1.add(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                    Applicant_ID1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                    DueDate1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date)));
                    ServiceCode1.add(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code)));
                    ServiceName1.add(service_name);
                    VillageCode1.add(villageCode);
                    HabitationCode1.add(habitationCode);
                    Option_Flag1.add(option_Flag);
                    i++;
                } while (cursor1.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter_pushed = new Service_List_Adapter(New_Request_FirstScreen.this, SlNo1, Applicant_Name1,
                    GSC_FirstPart1, Applicant_ID1, DueDate1, ServiceCode1, ServiceName1, VillageCode1, HabitationCode1,
                    Option_Flag1, New_Request_FirstScreen.this, this);
            list_pushed.setAdapter(list_adapter_pushed);
            database.close();
            //Toast.makeText(getApplicationContext(), "Rural Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor1.close();
            listLayout_pushed.setVisibility(View.GONE);
            txtListPushed.setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }

        Cursor cursor = database.rawQuery("select * from "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "="+villageCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code+"="+habitationCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"=9999 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"=255 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+" is null", null);

        SlNo.clear();
        Applicant_Name.clear();
        GSC_FirstPart.clear();
        Applicant_ID.clear();
        DueDate.clear();
        ServiceCode.clear();
        ServiceName.clear();
        VillageCode.clear();
        HabitationCode.clear();
        Option_Flag.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Applicant_Name.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                    GSC_FirstPart.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                    Applicant_ID.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                    DueDate.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date)));
                    ServiceCode.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code)));
                    ServiceName.add(service_name);
                    VillageCode.add(villageCode);
                    HabitationCode.add(habitationCode);
                    Option_Flag.add(option_Flag);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter = new Service_List_Adapter(New_Request_FirstScreen.this, SlNo, Applicant_Name, GSC_FirstPart,
                    Applicant_ID, DueDate, ServiceCode, ServiceName, VillageCode, HabitationCode, Option_Flag, New_Request_FirstScreen.this, this);
            listView.setAdapter(list_adapter);
            database.close();
            //Toast.makeText(getApplicationContext(), "Rural Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.close();
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public void display_Urban_Data_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code +"="+town_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code + "="+ward_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=99999 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+" ='1'", null);

        SlNo1.clear();
        Applicant_Name1.clear();
        GSC_FirstPart1.clear();
        Applicant_ID1.clear();
        DueDate1.clear();
        ServiceCode1.clear();
        ServiceName1.clear();
        TownName1.clear();
        TownCode1.clear();
        WardName1.clear();
        WardCode1.clear();
        Option_Flag1.clear();

        if(cursor1.getCount()>0) {
            listLayout_pushed.setVisibility(View.GONE);
            txtListPushed.setVisibility(View.GONE);
            if (cursor1.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo1.add(String.valueOf(i));
                    Applicant_Name1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                    GSC_FirstPart1.add(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                    Applicant_ID1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                    DueDate1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date)));
                    ServiceCode1.add(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code)));
                    ServiceName1.add(service_name);
                    TownName1.add(town_Name);
                    TownCode1.add(town_code);
                    WardName1.add(ward_Name);
                    WardCode1.add(ward_code);
                    Option_Flag1.add(option_Flag);
                    i++;
                } while (cursor1.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            ur_service_list_adapter_pushed = new UR_Service_List_Adapter(New_Request_FirstScreen.this, SlNo1, Applicant_Name1,
                    GSC_FirstPart1, Applicant_ID1, DueDate1, ServiceCode1, ServiceName1, TownName1, TownCode1, WardName1, WardCode1, Option_Flag1,
                    New_Request_FirstScreen.this, this);
            list_pushed.setAdapter(ur_service_list_adapter_pushed);
            database.close();
            //Toast.makeText(getApplicationContext(), "Urban Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor1.close();
            listLayout_pushed.setVisibility(View.GONE);
            txtListPushed.setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }

        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code +"="+town_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code + "="+ward_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=99999 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+" is null", null);

        SlNo.clear();
        Applicant_Name.clear();
        GSC_FirstPart.clear();
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
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Applicant_Name.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                    GSC_FirstPart.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                    Applicant_ID.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                    DueDate.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date)));
                    ServiceCode.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code)));
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
            ur_service_list_adapter = new UR_Service_List_Adapter(New_Request_FirstScreen.this, SlNo, Applicant_Name,
                    GSC_FirstPart, Applicant_ID, DueDate, ServiceCode, ServiceName, TownName, TownCode, WardName, WardCode, Option_Flag,
                    New_Request_FirstScreen.this, this);
            listView.setAdapter(ur_service_list_adapter);
            database.close();
            //Toast.makeText(getApplicationContext(), "Urban Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.close();
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(New_Request_FirstScreen.this, Field_Report.class);
        i.putExtra("district_Code", district_Code);
        i.putExtra("districtCode", district);
        i.putExtra("taluk_Code", taluk_Code);
        i.putExtra("taluk", taluk);
        i.putExtra("hobli_Code", hobli_Code);
        i.putExtra("hobli", hobli);
        i.putExtra("va_Circle_Code", va_Circle_Code);
        i.putExtra("VA_Circle_Name", VA_Circle_Name);
        i.putExtra("VA_Name", VA_Name);
        i.putExtra("strSearchServiceName", service_name);
        i.putExtra("villageCode", villageCode);
        i.putExtra("habitationCode",habitationCode);
        i.putExtra("strSearchVillageName", village_name);
        i.putExtra("town_Name", town_Name);
        i.putExtra("town_code", town_code);
        i.putExtra("ward_Name", ward_Name);
        i.putExtra("ward_code", ward_code);
        i.putExtra("option_Flag", option_Flag);
        startActivity(i);
        finish();
    }

    @Override
    public void setValues(ArrayList<String> al) {
        selected_items = al;
        Log.d("Activity_selected",""+selected_items);
    }
}
