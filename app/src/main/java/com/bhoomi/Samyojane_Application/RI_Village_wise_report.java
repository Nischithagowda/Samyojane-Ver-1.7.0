package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class RI_Village_wise_report extends AppCompatActivity {
    TextView pendencyReport;
    static int district_Code, taluk_Code, hobli_Code, va_Circle_Code;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database, database1, database_Asset;
    String villageName, villageCode, townCode, townName, wardCode, wardName;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> TotalCount = new ArrayList<>();
    ArrayList<String> VillageName = new ArrayList<>();
    ArrayList<String> VillageCode = new ArrayList<>();
    ArrayList<String> SlNo_T = new ArrayList<>();
    ArrayList<String> TotalCount_T = new ArrayList<>();
    ArrayList<String> TownCode = new ArrayList<>();
    ArrayList<String> WardName = new ArrayList<>();
    ArrayList<String> WardCode = new ArrayList<>();
    TextView emptyTxt;
    ListView listView;
    RI_Village_ListAdapter list_adapter;
    RI_Ward_ListAdapter ward_listAdapter;
    LinearLayout linearLayout, listLayout;
    TextView emptyTxt1, txtPendingName;
    ListView listView1;
    LinearLayout linearLayout1, listLayout1;
    Button btnBack;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    RadioGroup radioGroup;
    RadioButton radioButton_rural, radioButton_urban;
    String option_Flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_village_wise_report);

        option_Flag = getString(R.string.rural);

        pendencyReport = findViewById(R.id.pendencyReport);
        emptyTxt = findViewById(R.id.emptyTxt);
        listView = findViewById(R.id.list);
        linearLayout = findViewById(R.id.total_Applicants);
        btnBack = findViewById(R.id.btnBack);
        emptyTxt1 = findViewById(R.id.emptyTxt1);
        listView1 = findViewById(R.id.list1);
        linearLayout1 = findViewById(R.id.total_Applicants1);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton_rural = findViewById(R.id.radioButton_rural);
        radioButton_urban = findViewById(R.id.radioButton_urban);
        txtPendingName = findViewById(R.id.txtPendingName);
        listLayout = findViewById(R.id.listLayout);
        listLayout1 = findViewById(R.id.listLayout1);

        pendencyReport.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        Intent i = getIntent();
        district_Code = i.getIntExtra("district_Code", 0);
        taluk_Code = i.getIntExtra("taluk_Code",0);
        hobli_Code = i.getIntExtra("hobli_Code",0);
        va_Circle_Code = i.getIntExtra("va_Circle_Code",0);

        Log.d("Second_Database_Value", ""+district_Code);
        Log.d("Second_Database_Value", ""+taluk_Code);
        Log.d("Second_Database_Value", ""+hobli_Code);
        Log.d("Second_Database_Value", ""+va_Circle_Code);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(RI_Village_wise_report.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();


        if(Objects.equals(option_Flag, getString(R.string.rural))) {
            Log.d("option_Flag1:", "" + option_Flag);
            radioButton_rural.setChecked(true);
            radioButton_urban.setChecked(false);
            txtPendingName.setText(getString(R.string.village_wise_pending_status));
            listLayout.setVisibility(View.VISIBLE);
            listLayout1.setVisibility(View.GONE);
            displayData_AfterItemSelected();
        }else if (Objects.equals(option_Flag, getString(R.string.urban))){
            Log.d("option_Flag1:", ""+option_Flag);
            radioButton_rural.setChecked(false);
            radioButton_urban.setChecked(true);
            txtPendingName.setText(getString(R.string.ward_wise_pending_status));
            listLayout.setVisibility(View.GONE);
            listLayout1.setVisibility(View.VISIBLE);
            displayUrbanData_AfterItemSelected();
        }else if(option_Flag==null) {
            option_Flag = getString(R.string.rural);
            Log.d("option_Flag1", ""+option_Flag);
            txtPendingName.setText(getString(R.string.village_wise_pending_status));
            listLayout.setVisibility(View.VISIBLE);
            listLayout.setVisibility(View.GONE);
            displayData_AfterItemSelected();
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton_rural) {
                option_Flag = getString(R.string.rural);
                txtPendingName.setText(getString(R.string.village_wise_pending_status));
                listLayout.setVisibility(View.VISIBLE);
                listLayout1.setVisibility(View.GONE);
                displayData_AfterItemSelected();
            }
            else if (checkedId == R.id.radioButton_urban) {
                option_Flag = getString(R.string.urban);
                txtPendingName.setText(getString(R.string.ward_wise_pending_status));
                listLayout.setVisibility(View.GONE);
                listLayout1.setVisibility(View.VISIBLE);
                displayUrbanData_AfterItemSelected();
            }
            Log.d("option_Flag", ""+option_Flag);
        });

        btnBack.setOnClickListener(view -> onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    public void displayData_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);

        emptyTxt.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        Log.d("InDisplayElse", "" + i);
        emptyTxt.setText(getString(R.string.no_da_found));

        SlNo.clear();
        TotalCount.clear();
        VillageName.clear();
        VillageCode.clear();

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Village_wise_report.this);
        database1 = openHelper.getWritableDatabase();

        openHelper = new DataBaseHelperClass_VillageNames(RI_Village_wise_report.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_VillageNames.TABLE_NAME
                +" where "+DataBaseHelperClass_VillageNames.VCM_va_circle_code+"="+va_Circle_Code
                + " order by "+getString(R.string.village_table_habitation_name),null);
        if (cursor1.getCount()>0){
            if (cursor1.moveToFirst()) {
                do {
                    villageCode = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_VillageNames.HM_village_code));
                    villageName = cursor1.getString(cursor1.getColumnIndexOrThrow(getString(R.string.village_table_habitation_name)));

                    Log.d("villageCode_l", "" + villageCode);

                    if (!villageCode.equals("99999")){
                    Cursor cursor = database1.rawQuery("select count(" + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo
                            + ") as TotalCount from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME + " where "
                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag + " is null and "
                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code + "=" + villageCode , null);

                    if (cursor.getCount() > 0) {

                        if (cursor.moveToFirst()) {
                            do {
                                Log.d("InDisplayIf", "" + i);
                                String total_Count = cursor.getString(cursor.getColumnIndex("TotalCount"));
                                if (!total_Count.equals("0")) {
                                    emptyTxt.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.VISIBLE);
                                    SlNo.add(String.valueOf(i));
                                    VillageName.add(villageName);
                                    TotalCount.add(cursor.getString(cursor.getColumnIndex("TotalCount")));
                                    VillageCode.add(String.valueOf(villageCode));
                                    i++;
                                }
                            } while (cursor.moveToNext());
                        }
                        Log.d("InDisplayIf", "" + i);
                        list_adapter = new RI_Village_ListAdapter(RI_Village_wise_report.this, SlNo, VillageName, TotalCount, VillageCode);
                        listView.setAdapter(list_adapter);
                        database.close();
                    }else {
                        cursor.close();
                        emptyTxt.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        Log.d("InDisplayElse", "" + i);
                        emptyTxt.setText(getString(R.string.no_da_found));
                    }
                        //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        emptyTxt.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        Log.d("InDisplayElse", "" + i);
                        emptyTxt.setText(getString(R.string.no_da_found));
                        //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                } while (cursor1.moveToNext());
            }
        }else{
            cursor1.close();
            emptyTxt.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

//    @SuppressLint("SetTextI18n")
//    public void displayUrbanData_AfterItemSelected() {
//        int i=1;
//        Log.d("InDisplay", ""+ i);
//
//        SlNo_T.clear();
//        TotalCount_T.clear();
//        TownName.clear();
//        TownCode.clear();
//
//        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(RI_Village_wise_report.this,"str","str");
//        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
//        database_Asset = sqlLiteOpenHelper_class.getReadableDatabase();
//
//        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Village_wise_report.this);
//        database1 = openHelper.getWritableDatabase();
//
//
//        @SuppressLint("Recycle")
//        Cursor cursor1 = database_Asset.rawQuery("select * from "+SqlLiteOpenHelper_Class.Table_TOWN_MASTER
//                +" where "+SqlLiteOpenHelper_Class.TWM_district_code+"="+district_Code+" and "
//                +SqlLiteOpenHelper_Class.TWM_taluk_code+"="+taluk_Code,null);
//        if (cursor1.getCount()>0){
//            if (cursor1.moveToFirst()) {
//                do {
//                    townCode = cursor1.getString(cursor1.getColumnIndexOrThrow(SqlLiteOpenHelper_Class.TWM_town_code));
//                    townName = cursor1.getString(cursor1.getColumnIndexOrThrow(getString(R.string.town_master_town_name)));
//
//                    Log.d("villageCode_l", "" + townCode);
//                    @SuppressLint("Recycle")
//                    Cursor cursor = database1.rawQuery("select count(" + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No
//                            + ") as TotalCount from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_member_id + " where "
//                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag + " is not null and "
//                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag+" is null and "
//                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.va_Circle_Code+"="+va_Circle_Code+" and "
//                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code + "=" + townCode + " and "
//                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code + "=99999", null);
//
//                    if (cursor.getCount() > 0) {
//                        emptyTxt1.setVisibility(View.GONE);
//                        listView1.setVisibility(View.VISIBLE);
//                        linearLayout1.setVisibility(View.VISIBLE);
//                        if (cursor.moveToFirst()) {
//                            do {
//                                Log.d("InDisplayIf", "" + i);
//                                String total_Count = cursor.getString(cursor.getColumnIndex("TotalCount"));
//                                if (!total_Count.equals("0")) {
//                                    SlNo_T.add(String.valueOf(i));
//                                    TownName.add(townName);
//                                    TotalCount_T.add(cursor.getString(cursor.getColumnIndex("TotalCount")));
//                                    TownCode.add(String.valueOf(townCode));
//                                }
//                                i++;
//                            } while (cursor.moveToNext());
//                        }
//                        Log.d("InDisplayIf", "" + i);
//                        ward_listAdapter = new RI_Ward_ListAdapter(RI_Village_wise_report.this, SlNo_T, TownName, TotalCount_T, TownCode);
//                        listView1.setAdapter(ward_listAdapter);
//                        //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        emptyTxt1.setVisibility(View.VISIBLE);
//                        listView1.setVisibility(View.GONE);
//                        Log.d("InDisplayElse", "" + i);
//                        emptyTxt1.setText(getString(R.string.no_da_found));
//                        //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
//                    }
//
//                } while (cursor1.moveToNext());
//            }
//        }else{
//            emptyTxt1.setVisibility(View.VISIBLE);
//            listView1.setVisibility(View.GONE);
//            Log.d("InDisplayElse", ""+ i);
//            emptyTxt1.setText(getString(R.string.no_da_found));
//            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
//        }
//    }

    @SuppressLint("SetTextI18n")
    public void displayUrbanData_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);

        emptyTxt1.setVisibility(View.VISIBLE);
        listView1.setVisibility(View.GONE);
        Log.d("InDisplayElseIf", "" + i);
        emptyTxt1.setText(getString(R.string.no_da_found));

        SlNo_T.clear();
        TotalCount_T.clear();
        TownCode.clear();
        WardName.clear();
        WardCode.clear();

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(RI_Village_wise_report.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        database_Asset = sqlLiteOpenHelper_class.getReadableDatabase();

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Village_wise_report.this);
        database1 = openHelper.getWritableDatabase();

        Cursor cursor1 = database_Asset.rawQuery("select * from "+SqlLiteOpenHelper_Class.Table_TOWN_MASTER
                +" where "+SqlLiteOpenHelper_Class.TWM_district_code+"="+district_Code+" and "
                +SqlLiteOpenHelper_Class.TWM_taluk_code+"="+taluk_Code,null);
        if (cursor1.getCount()>0){
            if (cursor1.moveToFirst()) {
                do {
                    Log.d("RI_Village_Wise","Enter1");
                    townCode = cursor1.getString(cursor1.getColumnIndexOrThrow(SqlLiteOpenHelper_Class.TWM_town_code));
                    townName = cursor1.getString(cursor1.getColumnIndexOrThrow(getString(R.string.town_master_town_name)));

                    Log.d("townCode_l", "" + townCode);

                    Cursor cursor2 = database_Asset.rawQuery("select * from "+SqlLiteOpenHelper_Class.Table_WARD_MASTER
                            +" where "+SqlLiteOpenHelper_Class.WM_district_code+"="+district_Code+" and "
                            + SqlLiteOpenHelper_Class.WM_taluk_code+"="+taluk_Code+" and "
                            + SqlLiteOpenHelper_Class.WM_town_code+"="+townCode,null);

                    if (cursor2.getCount()>0) {
                        if (cursor2.moveToFirst()) {
                            do {
                                Log.d("RI_Village_Wise","Enter2");
                                wardCode = cursor2.getString(cursor2.getColumnIndexOrThrow(SqlLiteOpenHelper_Class.WM_ward_no));
                                wardName = cursor2.getString(cursor2.getColumnIndexOrThrow(getString(R.string.town_master_ward_name)));
                                Log.d("wardCode_1", "" + wardCode);

                                Cursor cursor = database1.rawQuery("select count(" + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo
                                        + ") as TotalCount from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME + " where "
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag + " is null and "
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VillageCircle_Code+"="+va_Circle_Code+" and "
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code + "=" + townCode + " and "
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code + "=" + wardCode + " and "
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code + "=99999", null);

                                if (cursor.getCount() > 0) {

                                    if (cursor.moveToFirst()) {
                                        do {
                                            Log.d("RI_Village_Wise","Enter3");
                                            Log.d("InDisplayIf", "" + i);
                                            String total_Count = cursor.getString(cursor.getColumnIndex("TotalCount"));
                                            if (!total_Count.equals("0")) {
                                                emptyTxt1.setVisibility(View.GONE);
                                                listView1.setVisibility(View.VISIBLE);
                                                linearLayout1.setVisibility(View.VISIBLE);
                                                SlNo_T.add(String.valueOf(i));
                                                TownCode.add(townCode);
                                                WardName.add(wardName);
                                                TotalCount_T.add(cursor.getString(cursor.getColumnIndex("TotalCount")));
                                                WardCode.add(String.valueOf(wardCode));
                                                i++;
                                            }
                                        } while (cursor.moveToNext());
                                    }
                                    Log.d("InDisplayIf", "" + i);
                                    ward_listAdapter = new RI_Ward_ListAdapter(RI_Village_wise_report.this, SlNo_T, WardName, TotalCount_T, TownCode, WardCode);
                                    listView1.setAdapter(ward_listAdapter);
                                    //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    cursor.close();
                                    Log.d("RI_Village_Wise","EnterElse1");
                                    emptyTxt1.setVisibility(View.VISIBLE);
                                    listView1.setVisibility(View.GONE);
                                    Log.d("InDisplayElse", "" + i);
                                    emptyTxt1.setText(getString(R.string.no_da_found));
                                    //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                                }

                            } while (cursor2.moveToNext());
                        }
                    }else{
                        cursor2.close();
                        Log.d("RI_Village_Wise","EnterElse2");
                        emptyTxt1.setVisibility(View.VISIBLE);
                        listView1.setVisibility(View.GONE);
                        Log.d("InDisplayElse", ""+ i);
                        emptyTxt1.setText(getString(R.string.no_da_found));
                        //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                } while (cursor1.moveToNext());
            }
        }else{
            cursor1.close();
            Log.d("RI_Village_Wise","EnterElse3");
            emptyTxt1.setVisibility(View.VISIBLE);
            listView1.setVisibility(View.GONE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt1.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed(){
        Intent i = new Intent(RI_Village_wise_report.this, RI_VA_Circle_Wise_Report.class);
        i.putExtra("district_Code", district_Code);
        i.putExtra("taluk_Code", taluk_Code);
        i.putExtra("hobli_Code", hobli_Code);
        startActivity(i);
        finish();
    }
}
