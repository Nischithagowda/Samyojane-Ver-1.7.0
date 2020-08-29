package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PushToAnotherVA extends AppCompatActivity {

    Button btnBack, btnSubmit;
    TextView tvHobli, tvTaluk, tvVA_Name, tvVillageName, tv_V_T_Name;
    ArrayList<String> arrayList;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database, database1;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Applicant_Name = new ArrayList<>();
    ArrayList<String> GSC_FirstPart = new ArrayList<>();
    ArrayList<String> Applicant_ID = new ArrayList<>();
    ListView listView;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code, town_Name, ward_Name, town_code, ward_code;
    String villageCode, service_name, village_name, habitationCode, option_Flag;
    PushToAnotherVA_List_Adapter list_adapter;
    AutoCompleteTextView autoSearchVillageCircle, autoSearchVillage, autoSearchTown, autoSearchWard;
    LinearLayout l_Rural, l_VACircle, l_Village, l_Urban, l_town, l_ward;
    List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
    List<SpinnerObject_new> objects_Village = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Town = new ArrayList<>();
    String P_Village_Circle_Code = null, P_village_code = null, P_Habitation_Code = null;
    String P_VA_CircleName, P_village_name;
    String hab_Va_Circle_Code, hab_Village_Code, hab_Habitation_Code;
    private List<AutoCompleteTextBox_Object> SearchVillageCircleName = new ArrayList<>();
    private List<SpinnerObject_new> SearchVillageName = new ArrayList<>();
    ProgressDialog p_dialog;

    @SuppressLint("ClickableViewAccessibility")
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
        tvVillageName = findViewById(R.id.tvVillageName);
        tv_V_T_Name = findViewById(R.id.tv_V_T_Name);
        autoSearchVillageCircle = findViewById(R.id.autoSearchVillageCircle);
        autoSearchVillage = findViewById(R.id.autoSearchVillage);
        autoSearchTown = findViewById(R.id.autoSearchTown);
        autoSearchWard = findViewById(R.id.autoSearchWard);
        l_Urban = findViewById(R.id.l_Urban);
        l_Rural = findViewById(R.id.l_Rural);
        l_town = findViewById(R.id.l_town);
        l_ward = findViewById(R.id.l_ward);
        l_Village = findViewById(R.id.l_Village);
        l_VACircle = findViewById(R.id.l_VACircle);

        arrayList = new ArrayList<>();

        Intent i = getIntent();
        arrayList = i.getStringArrayListExtra("selected_items");
        district = i.getStringExtra("districtCode");
        taluk = i.getStringExtra("taluk");
        hobli = i.getStringExtra("hobli");
        VA_Name = i.getStringExtra("VA_Name");
        district_Code = i.getStringExtra("district_Code");
        taluk_Code = i.getStringExtra("taluk_Code");
        hobli_Code = i.getStringExtra("hobli_Code");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        service_name = i.getStringExtra("strSearchServiceName");
        villageCode = i.getStringExtra("villageCode");
        habitationCode = i.getStringExtra("habitationCode");
        village_name = i.getStringExtra("strSearchVillageName");
        town_code = i.getStringExtra("town_code");
        town_Name = i.getStringExtra("town_Name");
        ward_code = i.getStringExtra("ward_code");
        ward_Name = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");
        Log.d("arrayListArrayList", ""+arrayList);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(PushToAnotherVA.this);
        database = openHelper.getWritableDatabase();

        openHelper=new DataBaseHelperClass_VillageNames_DTH(PushToAnotherVA.this);
        database1=openHelper.getWritableDatabase();

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);
        displayData_AfterItemSelected(arrayList);

        if(!Objects.equals(villageCode, "99999")){
            Log.d("Data","Rural");
            String str = getString(R.string.village_name)+" : ";
            tv_V_T_Name.setText(str);
            tvVillageName.setText(village_name);
            l_Rural.setVisibility(View.VISIBLE);
            l_VACircle.setVisibility(View.VISIBLE);
            l_Village.setVisibility(View.GONE);
            l_Urban.setVisibility(View.GONE);
            GetVillageCircleName();
        }else if(!Objects.equals(town_code, "9999")){
            Log.d("Data","Urban");
            String str = getString(R.string.town_name)+"                : "
                    +"\n"+getString(R.string.ward_name_num)+"    : ";
            tv_V_T_Name.setText(str);
            String str1 = town_Name+"\n"+ward_Name;
            tvVillageName.setText(str1);
            l_Rural.setVisibility(View.GONE);
            l_town.setVisibility(View.VISIBLE);
            l_ward.setVisibility(View.GONE);
            l_Urban.setVisibility(View.VISIBLE);
        }

        p_dialog = new ProgressDialog(this, R.style.CustomDialog);
        p_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p_dialog.setMessage(getString(R.string.loading));
        p_dialog.setIndeterminate(true);
        p_dialog.setCanceledOnTouchOutside(false);

        autoSearchVillageCircle.setOnTouchListener((v, event) -> {
            autoSearchVillageCircle.showDropDown();
            autoSearchVillage.setText("");
            l_Village.setVisibility(View.GONE);
            return false;
        });

        autoSearchVillage.setOnTouchListener((v, event) -> {
            autoSearchVillage.showDropDown();
            return false;
        });

        autoSearchTown.setOnTouchListener((v, event) -> {
            autoSearchTown.showDropDown();
            autoSearchWard.setText("");
            l_ward.setVisibility(View.GONE);
            return false;
        });

        autoSearchWard.setOnTouchListener((v, event) -> {
            autoSearchWard.showDropDown();
            return false;
        });

        btnSubmit.setOnClickListener(v -> {
            P_VA_CircleName = autoSearchVillageCircle.getText().toString();
            P_village_name = autoSearchVillage.getText().toString();

            if (!P_VA_CircleName.isEmpty() && !P_Village_Circle_Code.isEmpty()){
             if (!P_village_name.isEmpty() && !P_village_code.isEmpty() && !P_Habitation_Code.isEmpty()){
                 buildAlert_Push(arrayList);
             } else {
                 autoSearchVillage.setError(getString(R.string.select));
             }
            } else {
                autoSearchVillageCircle.setError(getString(R.string.select));
            }
        });

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    public void GetVillageCircleName() {
        objects.clear();
        objects = getVillageCircleNameList();

        ArrayAdapter<AutoCompleteTextBox_Object> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchVillageCircle.setAdapter(adapter);
        autoSearchVillageCircle.setOnItemClickListener((parent, view, position, id) -> {
            P_Village_Circle_Code = ((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId();
            l_Village.setVisibility(View.VISIBLE);
            GetVillageName(P_Village_Circle_Code);
        });
    }
    public List<AutoCompleteTextBox_Object> getVillageCircleNameList(){

        Cursor cursor = database1.rawQuery("Select distinct "+getString(R.string.village_table_va_circle_name)+","
                +DataBaseHelperClass_VillageNames_DTH.VCM_va_circle_code+" from "
                +DataBaseHelperClass_VillageNames_DTH.TABLE_NAME+ " order by "+getString(R.string.village_table_va_circle_name), null);
        if (cursor.moveToFirst()) {
            do {
                hab_Va_Circle_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_VillageNames_DTH.VCM_va_circle_code));
                SearchVillageCircleName.add(new AutoCompleteTextBox_Object(hab_Va_Circle_Code, cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_va_circle_name)))));
                Log.d("VillageCircle Names", ""+ SearchVillageCircleName);
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return SearchVillageCircleName;
    }

    public void GetVillageName(String Village_Circle_Code) {
        objects_Village.clear();
        objects_Village = getVillageList(Village_Circle_Code);
        Log.d("adapterValue", "" + objects_Village);
        ArrayAdapter<SpinnerObject_new> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Village);
        adapter.setDropDownViewResource(R.layout.list_item);
        adapter.notifyDataSetChanged();
        autoSearchVillage.setAdapter(adapter);
        autoSearchVillage.setOnItemClickListener((parent, view, position, id) -> {
            P_village_code = ((SpinnerObject_new)parent.getItemAtPosition(position)).getId();
            P_Habitation_Code = ((SpinnerObject_new)parent.getItemAtPosition(position)).getID1();

            Log.d("Village_Code3", ""+ P_village_code);
            Log.d("Habitation_code", ""+ P_Habitation_Code);

            if (P_village_code.equalsIgnoreCase(villageCode) && P_Habitation_Code.equalsIgnoreCase(habitationCode)){
                Toast.makeText(getApplicationContext(), getString(R.string.please_select_other_village), Toast.LENGTH_SHORT).show();
                autoSearchVillage.setText("");
                P_village_code = null;
                P_Habitation_Code = null;
            }
        });
    }
    public List<SpinnerObject_new> getVillageList(String Village_Circle_Code){

        Cursor cursor = database1.rawQuery("Select "+getString(R.string.village_table_habitation_name)+","
                + DataBaseHelperClass_VillageNames_DTH.HM_habitation_code+","
                + DataBaseHelperClass_VillageNames_DTH.HM_village_code+" from "
                + DataBaseHelperClass_VillageNames_DTH.TABLE_NAME+" where "
                + DataBaseHelperClass_VillageNames_DTH.VCM_va_circle_code+"="+Village_Circle_Code+" order by "+getString(R.string.village_table_va_circle_name), null);
        if (cursor.moveToFirst()) {
            do {
                hab_Village_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_VillageNames_DTH.HM_village_code));
                hab_Habitation_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_VillageNames_DTH.HM_habitation_code));
                SearchVillageName.add(new SpinnerObject_new(hab_Village_Code,(cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_habitation_name)))),hab_Habitation_Code));
                Log.d("Village Names", "" + SearchVillageName);
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return SearchVillageName;
    }

    private  void buildAlert_Push(ArrayList<String> arrayList) {
        String str;
        if (arrayList.size()==1){
            str = getString(R.string.are_you_sure_do_you_want_to_push_the_selected)+" "+arrayList.size()+" "+getString(R.string.record)+" to Village : "+ P_village_name;
        } else {
            str = getString(R.string.are_you_sure_do_you_want_to_push_the_selected)+" "+arrayList.size()+" "+getString(R.string.records)+" to Village : "+ P_village_name;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.confirmation_alert))
                .setMessage(str)
                .setIcon(R.drawable.ic_alert_green_24dp)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirm), (dialog, id) -> {
                    dialog.cancel();
                    if (isNetworkAvailable()){

                    } else {
                        buildAlertMessageConnection();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.getClass());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private  void buildAlertMessageConnection() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.no_internet))
                .setMessage(getString(R.string.enable_internet))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    Intent dialogIntent = new Intent(Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel()
                );
        final AlertDialog alert = builder.create();
        alert.show();
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        database.close();
        database1.close();
        finish();
    }
}
