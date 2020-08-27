package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PushToAnotherVA extends AppCompatActivity {

    Button btnBack, btnSubmit;
    TextView tvHobli, tvTaluk, tvVA_Name;
    ArrayList<String> arrayList;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Applicant_Name = new ArrayList<>();
    ArrayList<String> GSC_FirstPart = new ArrayList<>();
    ArrayList<String> Applicant_ID = new ArrayList<>();
    ListView listView;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    PushToAnotherVA_List_Adapter list_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_to_another_va);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnBack = findViewById(R.id.btnBack);
        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvVA_Name = findViewById(R.id.VA_name);
        listView = findViewById(R.id.list);

        arrayList = new ArrayList<>();

        Intent i = getIntent();
        arrayList = i.getStringArrayListExtra("selected_items");
        taluk = i.getStringExtra("taluk");
        hobli = i.getStringExtra("hobli");
        VA_Name = i.getStringExtra("VA_Name");
        Log.d("arrayListArrayList", ""+arrayList);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(PushToAnotherVA.this);
        database = openHelper.getWritableDatabase();

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);
        displayData_AfterItemSelected(arrayList);

        btnSubmit.setOnClickListener(v -> {
            buildAlert_Push(arrayList);
        });
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private  void buildAlert_Push(ArrayList<String> arrayList) {
        String str;
        if (arrayList.size()==1){
            str = getString(R.string.are_you_sure_do_you_want_to_push_the_selected)+" "+arrayList.size()+" "+getString(R.string.record);
        } else {
            str = getString(R.string.are_you_sure_do_you_want_to_push_the_selected)+" "+arrayList.size()+" "+getString(R.string.records);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.confirmation_alert))
                .setMessage(str)
                .setIcon(R.drawable.ic_alert_green_24dp)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirm), (dialog, id) -> {

                    dialog.cancel();
                });
        final AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
    }

    public void displayData_AfterItemSelected(ArrayList<String> arrayList) {
        int i=1;
        Log.d("InDisplay", ""+ i);

        SlNo.clear();
        Applicant_Name.clear();
        GSC_FirstPart.clear();
        Applicant_ID.clear();

        for (int j=0;j<arrayList.size();j++) {
            String appID = arrayList.get(j);
            Log.d("InDisplay_appID", ""+ appID);
            Cursor cursor = database.rawQuery("select * from "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME + " where "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + appID, null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Log.d("InDisplayIf", "" + i);
                        SlNo.add(String.valueOf(i));
                        Applicant_Name.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                        GSC_FirstPart.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                        Applicant_ID.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                        i++;
                    } while (cursor.moveToNext());
                }
                Log.d("InDisplayIf", "" + i);
                list_adapter = new PushToAnotherVA_List_Adapter(PushToAnotherVA.this, SlNo, Applicant_Name, GSC_FirstPart, Applicant_ID);
                listView.setAdapter(list_adapter);
            } else {
                cursor.close();
                Log.d("InDisplayElse", "" + i);
            }
        }
        database.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
