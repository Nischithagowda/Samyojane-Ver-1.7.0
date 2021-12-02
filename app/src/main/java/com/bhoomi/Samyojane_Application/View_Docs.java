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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Adarsh on 06-Jun-19.
 */

public class View_Docs extends AppCompatActivity {

    Button btnBack;
    TextView tvHobli, tvTaluk, tvVA_Name, tvServiceName,tvAppName, txt_ReportNo;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    int district_Code, taluk_Code, hobli_Code, va_Circle_Code;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    String applicant_name;
    String applicant_Id, report_no;
    TextView emptyTxt;
    ListView listView;
    Docs_List_Adapter list_adapter;
    String villageCode, service_name, village_name;
    int serviceCode;
    String docs_Name;
    int docs_ID;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> App_ID = new ArrayList<>();
    ArrayList<Integer> Docs_ID = new ArrayList<>();
    ArrayList<String> Docs_Name = new ArrayList<>();

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_docs);

        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvVA_Name = findViewById(R.id.VA_name);
        txt_ReportNo = findViewById(R.id.txt_ReportNo);

        btnBack = findViewById(R.id.btnBack);
        listView = findViewById(R.id.list);
        emptyTxt = findViewById(R.id.emptyTxt);
        tvServiceName = findViewById(R.id.tvServiceName);
        tvAppName = findViewById(R.id.tvAppName);

        Intent i = getIntent();
        district_Code = i.getIntExtra("district_Code", 0);
        district = i.getStringExtra("districtCode");
        taluk_Code = i.getIntExtra("taluk_Code", 0);
        taluk = i.getStringExtra("taluk");
        hobli_Code = i.getIntExtra("hobli_Code", 0);
        hobli = i.getStringExtra("hobli");
        va_Circle_Code = i.getIntExtra("va_Circle_Code", 0);
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        VA_Name = i.getStringExtra("VA_Name");
        service_name = i.getStringExtra("strSearchServiceName");
        villageCode = i.getStringExtra("villageCode");
        village_name = i.getStringExtra("strSearchVillageName");
        applicant_name = i.getStringExtra("applicant_name");
        applicant_Id = i.getStringExtra("applicant_Id");
        report_no = i.getStringExtra("report_no");

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(this, "str");
        sqlLiteOpenHelper_class.open_Docs_Type_Tbl();

        openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(View_Docs.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_edesc+"='"+service_name+"'", null);
        if (cursor.getCount()>0){
            if (cursor.moveToNext()){
                serviceCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code));
            }
        } else {
            cursor.close();
        }

        Log.d("New_Request_FirstScreen", "district_Code: "+district_Code);
        Log.d("New_Request_FirstScreen", "taluk_Code: "+taluk_Code);
        Log.d("New_Request_FirstScreen", "hobli_Code: "+hobli_Code+"\nVillageCircleCode: "+va_Circle_Code+"\nServiceName: "+service_name);
        Log.d("New_Request_FirstScreen", "village_name: "+village_name);
        Log.d("New_Request_FirstScreen", "serviceCode: "+serviceCode);
        Log.d("New_Request_FirstScreen", "villageCode: "+villageCode);
        Log.d("New_Request_FirstScreen","report_no: "+ report_no);

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);
        tvServiceName.setText(service_name);
        tvAppName.setText(applicant_name);
        txt_ReportNo.setText(report_no);

        displayData();

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    public void displayData(){
        int i=1;

        openHelper = new DataBaseHelperClass_btnDownload_Docs(this);
        database = openHelper.getWritableDatabase();

        Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_Docs.TABLE_NAME, null);

        SlNo.clear();
        App_ID.clear();
        Docs_ID.clear();
        Docs_Name.clear();

        if(cursor1.getCount()>0){
            emptyTxt.setVisibility(View.GONE);
            if (cursor1.moveToNext()){
                do{
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(i +".");

                    docs_ID = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_Docs.DocumentID));
                    Log.d("docs_ID",""+docs_ID+", Index:"+i);
                    docs_Name = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_Docs.DocumentName));
                    Log.d("docs_Name",""+i+docs_Name);

                    App_ID.add(applicant_Id);
                    Docs_ID.add(docs_ID);
                    Docs_Name.add(docs_Name);

                    i++;
                } while (cursor1.moveToNext());
            }

            list_adapter = new Docs_List_Adapter(View_Docs.this, SlNo, App_ID, Docs_ID, Docs_Name);
            listView.setAdapter(list_adapter);
            database.close();
            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor1.close();
            emptyTxt.setVisibility(View.VISIBLE);
            emptyTxt.setText("No Data Found");
            Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
