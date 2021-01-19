package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Applicant_wise_report extends AppCompatActivity {

    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> GSC_No = new ArrayList<>();
    ArrayList<String> ApplicantID = new ArrayList<>();
    ArrayList<String> ApplicantName = new ArrayList<>();
    ArrayList<String> ServiceName = new ArrayList<>();
    ArrayList<String> ServiceCode = new ArrayList<>();
    String villageCode, habitationCode, townCode, wardCode, gsc_no, applicantId, applicantName, serviceName, serviceCode;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    TextView emptyTxt;
    ListView listView;
    LinearLayout linearLayout;
    Applicant_ListAdapter listAdapter;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applicant_wise_report);

        emptyTxt = findViewById(R.id.emptyTxt);
        listView = findViewById(R.id.list);
        linearLayout = findViewById(R.id.total_Applicants);
        btnBack = findViewById(R.id.btnBack);

        Intent i = getIntent();
        villageCode = i.getStringExtra("villageCode");
        habitationCode = i.getStringExtra("habitationCode");
        townCode = i.getStringExtra("townCode");
        wardCode = i.getStringExtra("wardCode");
        Log.d("villageCode", ""+villageCode);
        Log.d("habitationCode", ""+habitationCode);
        i.putExtra("villageCode","99999");
        i.putExtra("habitationCode","255");
        i.putExtra("townCode",townCode);
        i.putExtra("wardCode",wardCode);

        if (!villageCode.equals("99999")) {
            displayData_AfterItemSelected();
        }else if(villageCode.equals("99999")) {
            displayUrbanData_AfterItemSelected();
        }

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    public void displayData_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);

        SlNo.clear();
        GSC_No.clear();
        ApplicantID.clear();
        ApplicantName.clear();
        ServiceName.clear();
        ServiceCode.clear();

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(Applicant_wise_report.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor1 = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                + " where " + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + " is null and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=" + villageCode + " and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code + "=" + habitationCode + " and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"=9999 and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"=255", null);
        if (cursor1.getCount()>0){
            emptyTxt.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            if (cursor1.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", "" + i);
                    Log.d("Applicant_Wise","Enter1");
                    gsc_no = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name));
                    applicantId = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No));
                    applicantName = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name));
                    serviceName = cursor1.getString(cursor1.getColumnIndexOrThrow(getString(R.string.ser_tran_service_name)));
                    serviceCode = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code));

                    SlNo.add(String.valueOf(i));
                    GSC_No.add(gsc_no);
                    ApplicantID.add(applicantId);
                    ApplicantName.add(applicantName);
                    ServiceName.add(serviceName);
                    ServiceCode.add(serviceCode);
                    i++;

                    Log.d("applicantId", "" + applicantId+", applicantName:"+applicantName+", serviceName:"+serviceName+", serviceCode:"+serviceCode);

                } while (cursor1.moveToNext());
            }
            Log.d("InDisplayIf", "" + i);
            listAdapter = new Applicant_ListAdapter(Applicant_wise_report.this, SlNo, GSC_No, ApplicantID, ApplicantName, ServiceName, ServiceCode);
            listView.setAdapter(listAdapter);
            database.close();
        }else{
            cursor1.close();
            Log.d("RI_Village_Wise","EnterElse1");
            emptyTxt.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public void displayUrbanData_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);

        SlNo.clear();
        GSC_No.clear();
        ApplicantID.clear();
        ApplicantName.clear();
        ServiceName.clear();
        ServiceCode.clear();

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(Applicant_wise_report.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor1 = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                + " where " + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + " is null and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code + "=" + townCode + " and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code + "=" + wardCode + " and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=99999 and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code + "=255", null);
        if (cursor1.getCount()>0){
            emptyTxt.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            if (cursor1.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", "" + i);
                    gsc_no = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name));
                    applicantId = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No));
                    applicantName = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name));
                    serviceName = cursor1.getString(cursor1.getColumnIndexOrThrow(getString(R.string.ser_tran_service_name)));
                    serviceCode = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code));

                    SlNo.add(String.valueOf(i));
                    GSC_No.add(gsc_no);
                    ApplicantID.add(applicantId);
                    ApplicantName.add(applicantName);
                    ServiceName.add(serviceName);
                    ServiceCode.add(serviceCode);
                    i++;

                    Log.d("applicantId", "" + applicantId+", applicantName:"+applicantName+", serviceName:"+serviceName+", serviceCode:"+serviceCode);

                } while (cursor1.moveToNext());
            }
            Log.d("InDisplayIf", "" + i);
            listAdapter = new Applicant_ListAdapter(Applicant_wise_report.this, SlNo, GSC_No, ApplicantID, ApplicantName, ServiceName, ServiceCode);
            listView.setAdapter(listAdapter);
            database.close();
        }else{
            cursor1.close();
            emptyTxt.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
