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

public class View_Assistant extends AppCompatActivity {

    Button btnBack;
    String district, taluk, hobli, VA_Circle_Name, localeName, VA_Name, isVA, districtCode, talukCode, hobliCode, va_Circle_Code, uName_get;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    TextView emptyTxt;
    ListView listView;
    LinearLayout total_Applicants;
    View_Assistant_Adapter list_adapter;
    private ArrayList<String> SlNo = new ArrayList<>();
    private ArrayList<String> Ass_Name = new ArrayList<>();
    private ArrayList<String> Ass_MobileNum = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant);

        btnBack = findViewById(R.id.btnBack);
        total_Applicants = findViewById(R.id.total_Applicants);
        listView = findViewById(R.id.list);
        emptyTxt = findViewById(R.id.emptyTxt);

        total_Applicants.setVisibility(View.GONE);
        emptyTxt.setVisibility(View.GONE);

        Intent i = getIntent();
        district = i.getStringExtra("district");
        taluk = i.getStringExtra("taluk");
        hobli = i.getStringExtra("hobli");
        districtCode = i.getStringExtra("districtCode");
        talukCode = i.getStringExtra("talukCode");
        hobliCode = i.getStringExtra("hobliCode");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Name = i.getStringExtra("VA_Name");
        localeName = i.getStringExtra("localeName");
        isVA = i.getStringExtra("isVA");
        uName_get = i.getStringExtra("uName_get");

        Log.d("districtCode", ""+districtCode);
        Log.d("talukCode", ""+talukCode);
        Log.d("hobliCode", ""+hobliCode);

        openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(this);
        database = openHelper.getReadableDatabase();
        displayAssList();

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("SetTextI18n")
    public void displayAssList() {
        int i=1;
        Log.d("InDisplay", ""+ i);

        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_GET_Assistant+" where "
                + DataBaseHelperClass_btnDownload_E_Kshana.A_Dist_code+"='"+districtCode+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.A_Taluk_code+"='"+talukCode+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.A_Hobli_code+"='"+hobliCode+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.A_VA_circle_code+"='"+va_Circle_Code+"'", null);

        SlNo.clear();
        Ass_Name.clear();
        Ass_MobileNum.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            total_Applicants.setVisibility(View.VISIBLE);
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Ass_Name.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.A_Asst_Name)));
                    Ass_MobileNum.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.A_Asst_MobileNo)));
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter = new View_Assistant_Adapter(View_Assistant.this, SlNo, Ass_Name, Ass_MobileNum);
            listView.setAdapter(list_adapter);
            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.close();
            total_Applicants.setVisibility(View.GONE);
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
