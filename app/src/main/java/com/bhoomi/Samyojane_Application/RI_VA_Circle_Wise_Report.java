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
import android.widget.TextView;

import java.util.ArrayList;

public class RI_VA_Circle_Wise_Report  extends AppCompatActivity {

    TextView tvHobli, tvTaluk, tvVA_Name, pendencyReport;
    static String va_Circle_Name, VA_Name;
    static String district_Code, taluk_Code, hobli_Code, va_Circle_Code, villageCode;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database, database1;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Dist_Code = new ArrayList<>();
    ArrayList<String> Tal_Code = new ArrayList<>();
    ArrayList<String> Hob_Code = new ArrayList<>();
    ArrayList<String> VA_Circle_Name = new ArrayList<>();
    ArrayList<String> TotalCount = new ArrayList<>();
    ArrayList<String> VA_Circle_Code = new ArrayList<>();

    TextView emptyTxt;
    ListView listView;
    RI_VA_Circle_List_Adapter list_adapter;
    LinearLayout linearLayout, listLayout;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_va_circle_wise_report);

        pendencyReport = findViewById(R.id.pendencyReport);
        emptyTxt = findViewById(R.id.emptyTxt);
        listView = findViewById(R.id.list);
        linearLayout = findViewById(R.id.total_Applicants);
        listLayout = findViewById(R.id.listLayout);
        btnBack = findViewById(R.id.btnBack);

        pendencyReport.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        Intent i = getIntent();
        district_Code = i.getStringExtra("district_Code");
        taluk_Code = i.getStringExtra("taluk_Code");
        hobli_Code = i.getStringExtra("hobli_Code");

        displayData_AfterItemSelected();

        btnBack.setOnClickListener(view -> onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    public void displayData_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);

        emptyTxt.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        Log.d("InDisplayElse123", "" + i);
        emptyTxt.setText(getString(R.string.no_da_found));

        SlNo.clear();
        Dist_Code.clear();
        Tal_Code.clear();
        Hob_Code.clear();
        VA_Circle_Name.clear();
        TotalCount.clear();
        VA_Circle_Code.clear();

        openHelper = new DataBaseHelperClass_Credentials(RI_VA_Circle_Wise_Report.this);
        database = openHelper.getWritableDatabase();

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_VA_Circle_Wise_Report.this);
        database1 = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select distinct "+getString(R.string.cre_va_circle_name)
                +","+DataBaseHelperClass_Credentials.VA_circle_Code
                + " from "+DataBaseHelperClass_Credentials.TABLE_NAME
                + " where "+DataBaseHelperClass_Credentials.District_Code+"="+district_Code+" and "
                + DataBaseHelperClass_Credentials.Taluk_Code+"="+taluk_Code+" and "
                + DataBaseHelperClass_Credentials.Hobli_Code+"="+hobli_Code
                +" order by "+getString(R.string.cre_va_circle_name),null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                do {
                    va_Circle_Name = cursor.getString(cursor.getColumnIndexOrThrow(getString(R.string.cre_va_circle_name)));
                    va_Circle_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_circle_Code));

                    Cursor cursor1 = database1.rawQuery("select count(" + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No
                            + ") as TotalCount from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " where "
                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag + " is not null and "
                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag + " is null and "
                            + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.va_Circle_Code + "=" + va_Circle_Code , null);

                    if (cursor1.getCount() > 0) {

                        if (cursor1.moveToFirst()) {
                            do {
                                Log.d("InDisplayIf1", "" + i);
                                String total_Count = cursor1.getString(cursor1.getColumnIndex("TotalCount"));
                                if (!total_Count.equals("0")) {
                                    emptyTxt.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.VISIBLE);
                                    SlNo.add(String.valueOf(i));
                                    VA_Circle_Name.add(va_Circle_Name);
                                    TotalCount.add(cursor1.getString(cursor1.getColumnIndex("TotalCount")));
                                    VA_Circle_Code.add(va_Circle_Code);
                                    Dist_Code.add(district_Code);
                                    Tal_Code.add(taluk_Code);
                                    Hob_Code.add(hobli_Code);
                                    i++;
                                }
                            } while (cursor1.moveToNext());
                        }

                        Log.d("InDisplayIf12", "" + i);
                        list_adapter = new RI_VA_Circle_List_Adapter(RI_VA_Circle_Wise_Report.this, SlNo, VA_Circle_Name, TotalCount, VA_Circle_Code, Dist_Code, Tal_Code, Hob_Code);
                        listView.setAdapter(list_adapter);
                        database.close();

                        //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        cursor1.close();
                        emptyTxt.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                        Log.d("InDisplayElse123", "" + i);
                        emptyTxt.setText(getString(R.string.no_da_found));
                        //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }while (cursor.moveToNext());
            }
        }else{
            emptyTxt.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
