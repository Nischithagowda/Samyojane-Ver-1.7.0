package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by user on 28-Dec-18.
 */

public class RI_Field_Report extends AppCompatActivity {

    Button btnBack;
    TextView tvHobli, tvTaluk, tvRI_Name;
    static String RI_Name,district, taluk, hobli, VA_Circle_Name, VA_Name;
    static String district_Code, taluk_Code, hobli_Code, va_Circle_Code;
    AutoCompleteTextView autoSearchVillageCircle, autoSearchVillage, autoSearchTown, autoSearchWard;
    String village_name, service_name;
    List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Town = new ArrayList<>();
    static String strSearchVillageCircleName, strSearchVillageName, strSearchTownName, strSearchWardName, town_Code_1, ward_Code_1;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    static String get_Village_Circle_Code;
    static String get_village_code;
    List<AutoCompleteTextBox_Object> SearchVillageCircleName = new ArrayList<>();
    List<AutoCompleteTextBox_Object> SearchVillageName = new ArrayList<>();
    LinearLayout lLayoutVillage;
    LinearLayout linearLayout, listLayout, l_Rural, l_Urban, l_town, l_ward;
    TextView emptyTxt;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Service_Name = new ArrayList<>();
    ArrayList<String> TotalCount = new ArrayList<>();
    ArrayList<String> VACircle_Name = new ArrayList<>();
    ArrayList<String> VA_Circle_Code = new ArrayList<>();
    ArrayList<String> VillageName = new ArrayList<>();
    ArrayList<String> VillageCode = new ArrayList<>();
    ArrayList<String> Option_Flag = new ArrayList<>();
    ArrayList<String> TownName = new ArrayList<>();
    ArrayList<String> TownCode = new ArrayList<>();
    ArrayList<String> WardName = new ArrayList<>();
    ArrayList<String> WardCode = new ArrayList<>();
    RI_List_Adapter ri_list_adapter;
    RI_UR_List_Adapter ri_ur_list_adapter;
    ListView listView;
    TextView totalPending;
    int item_Position;
    String hab_Va_Circle_Code;
    String hab_Village_Code;
    int totalCount_va_Cir, totalCount_vill, town_Code, ward_Code;
    RadioGroup radioGroup;
    RadioButton radioButton_rural, radioButton_urban;
    String option_Flag;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_field_report);

        option_Flag = getString(R.string.rural);

        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvRI_Name = findViewById(R.id.VA_name);

        btnBack = findViewById(R.id.btnBack);
        autoSearchVillageCircle = findViewById(R.id.autoSearchVillageCircle);
        autoSearchVillage = findViewById(R.id.autoSearchVillage);
        lLayoutVillage = findViewById(R.id.lLayoutVillage);
        linearLayout = findViewById(R.id.total_Applicants);
        listLayout = findViewById(R.id.listLayout);
        emptyTxt = findViewById(R.id.emptyTxt);
        listView = findViewById(R.id.list);
        totalPending = findViewById(R.id.totalPending);
        autoSearchTown = findViewById(R.id.autoSearchTown);
        autoSearchWard = findViewById(R.id.autoSearchWard);
        l_Urban = findViewById(R.id.l_Urban);
        l_Rural = findViewById(R.id.l_Rural);
        l_town = findViewById(R.id.l_town);
        l_ward = findViewById(R.id.l_ward);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton_rural = findViewById(R.id.radioButton_rural);
        radioButton_urban = findViewById(R.id.radioButton_urban);

        lLayoutVillage.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        listLayout.setVisibility(View.GONE);
        emptyTxt.setVisibility(View.GONE);
        l_Urban.setVisibility(View.GONE);

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
        village_name = i.getStringExtra("strSearchVillageName");
        service_name = i.getStringExtra("strSearchServiceName");
        get_Village_Circle_Code = i.getStringExtra("va_Circle_Code");
        get_village_code = i.getStringExtra("villageCode");
        town_Code_1 = i.getStringExtra("town_code");
        strSearchTownName = i.getStringExtra("town_Name");
        ward_Code_1 = i.getStringExtra("ward_code");
        strSearchWardName = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");

        Log.d("RI_F_va_Circle_Code", va_Circle_Code);

        Global.district_Code1 =district_Code;
        Global.district_Name1 = district;
        Global.taluk_Code1 = taluk_Code;
        Global.taluk_Name1 = taluk;
        Global.hobli_Code1 = hobli_Code;
        Global.hobli_Name1 = hobli;
        Global.VA_Circle_Code1 = va_Circle_Code;
        Global.VA_Circle_Name1 = VA_Circle_Name;
        Global.RI_Name1 = RI_Name;

        Log.d("RI_Field_Report_Values","dist: "+district_Code
                                                +"\ntaluk: "+taluk
                                                +"\nhobli: "+hobli
                                                +"\nVA_Circle_Name:"+VA_Circle_Name
                                                +"\nRI_va_Circle_code"+va_Circle_Code
                                                +"\nRI_Name:"+RI_Name );

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvRI_Name.setText(RI_Name);

        openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
        database=openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+ " where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null and "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"=9999", null);
        int count;
        if (cursor.getCount()>0){
            count = cursor.getCount();
            Log.d("Count", String.valueOf(count));
            totalPending.setText(String.valueOf(count));
        }
        else {
            cursor.close();
            totalPending.setText("0");
        }

        if(Objects.equals(option_Flag, getString(R.string.rural))) {
            radioButton_rural.setChecked(true);
            radioButton_urban.setChecked(false);

            openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
            database=openHelper.getWritableDatabase();

            Cursor cursor1 = database.rawQuery("select * from "
                    +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+ " where "
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null and "
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"=9999", null);
            int count1;
            if (cursor1.getCount()>0){
                count1 = cursor1.getCount();
                Log.d("Count", String.valueOf(count1));
                totalPending.setText(String.valueOf(count1));
            }
            else {
                cursor1.close();
                totalPending.setText("0");
            }

            GetVillageCircleName();

            if(VA_Circle_Name!=null) {
                if (village_name != null) {
                    autoSearchVillageCircle.setText(VA_Circle_Name);
                    autoSearchVillage.setText(village_name);
                    lLayoutVillage.setVisibility(View.VISIBLE);
                    listLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    displayData_AfterItemSelected(get_village_code);
                }
                else {
                    Log.d("VillageName", "Village Name was null");
                }
            }
            else {
                Log.d("VillageCircleName", "VillageCircle Name was null");
            }
        }else if(Objects.equals(option_Flag, getString(R.string.urban))){
            radioButton_rural.setChecked(false);
            radioButton_urban.setChecked(true);

            l_Rural.setVisibility(View.GONE);
            l_Urban.setVisibility(View.VISIBLE);
            listLayout.setVisibility(View.GONE);
            autoSearchVillage.setText("");

            GetTownName();

            openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
            database=openHelper.getWritableDatabase();

            Cursor cursor1 = database.rawQuery("select * from "
                    +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+ " where "
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"=99999"+" and "
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null " , null);
            int count1;
            if (cursor1.getCount()>0){
                count1 = cursor1.getCount();
                Log.d("Count", String.valueOf(count1));
                totalPending.setText(String.valueOf(count1));
            }
            else {
                cursor1.close();
                totalPending.setText("0");
            }

            if (strSearchTownName!=null){
                Log.d("TownName", ""+strSearchTownName);
                autoSearchTown.setText(strSearchTownName);
                if (strSearchWardName!=null){
                    Log.d("WardName", ""+strSearchWardName);
                    l_ward.setVisibility(View.VISIBLE);
                    autoSearchWard.setText(strSearchWardName);
                    Log.d("",""+town_Code_1);
                    Log.d("ward_Code",""+ward_Code_1);
                    listLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    display_Urban_Data_AfterItemSelected(Integer.parseInt(town_Code_1), Integer.parseInt(ward_Code_1));
                }
                else{
                    Log.d("WardName", "Ward Name was null");
                }
            }else{
                Log.d("TownName", "Town Name was null");
            }
        }else if(option_Flag==null) {
            option_Flag = getString(R.string.rural);
            GetVillageCircleName();
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton_rural) {
                option_Flag = getString(R.string.rural);

                l_Rural.setVisibility(View.VISIBLE);
                l_Urban.setVisibility(View.GONE);
                listLayout.setVisibility(View.GONE);
                autoSearchTown.setText("");
                autoSearchWard.setText("");

                openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
                database=openHelper.getWritableDatabase();

                Cursor cursor12 = database.rawQuery("select * from "
                        +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+ " where "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"=9999 and "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code+"=255 and "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null " , null);
                int count12;
                if (cursor12.getCount()>0){
                    count12 = cursor12.getCount();
                    Log.d("Count", ""+ count12);
                    totalPending.setText(String.valueOf(count12));
                }
                else {
                    cursor12.close();
                    totalPending.setText("0");
                }

                if(VA_Circle_Name!=null) {
                    if (village_name != null) {
                        autoSearchVillageCircle.setText(VA_Circle_Name);
                        autoSearchVillage.setText(village_name);
                        lLayoutVillage.setVisibility(View.VISIBLE);
                        listLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        displayData_AfterItemSelected(get_village_code);
                    }
                    else {
                        Log.d("VillageName", "Village Name was null");
                    }
                }
                else {
                    Log.d("VillageCircleName", "VillageCircle Name was null");
                }

                tvTaluk.setText(taluk);
                tvHobli.setText(hobli);
                tvRI_Name.setText(RI_Name);
                GetVillageCircleName();

            }
            else if (checkedId == R.id.radioButton_urban) {
                option_Flag = getString(R.string.urban);
                l_Rural.setVisibility(View.GONE);
                l_Urban.setVisibility(View.VISIBLE);
                l_ward.setVisibility(View.GONE);
                listLayout.setVisibility(View.GONE);
                autoSearchVillage.setText("");

                openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
                database=openHelper.getWritableDatabase();

                Cursor cursor12 = database.rawQuery("select * from "
                        +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+ " where "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"=99999"+" and "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null " , null);
                int count12;
                if (cursor12.getCount()>0){
                    count12 = cursor12.getCount();
                    Log.d("Count", String.valueOf(count12));
                    totalPending.setText(String.valueOf(count12));
                }
                else {
                    cursor12.close();
                    totalPending.setText("0");
                }
                tvTaluk.setText(taluk);
                tvHobli.setText(hobli);
                tvRI_Name.setText(RI_Name);
                GetTownName();
            }
            Log.d("option_Flag", ""+option_Flag);
        });

        autoSearchVillageCircle.setOnTouchListener((v, event) -> {
            autoSearchVillageCircle.showDropDown();
            lLayoutVillage.setVisibility(View.GONE);
            autoSearchVillage.setText("");
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

        btnBack.setOnClickListener(v -> onBackPressed());

    }

    public static class Global{
        static String district_Code1 = district_Code;
        static String taluk_Code1 = taluk_Code;
        static String hobli_Code1 = hobli_Code;
        static String district_Name1 = district;
        static String taluk_Name1 = taluk;
        static String hobli_Name1 = hobli;
        static String VA_Circle_Code1 = va_Circle_Code;
        static String VA_Circle_Name1 = VA_Circle_Name;
        static String RI_Name1 = RI_Name;

    }

    public List<AutoCompleteTextBox_Object> getVillageCircleNameList(){
        openHelper=new DataBaseHelperClass_VillageNames(RI_Field_Report.this);
        database=openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select distinct "+getString(R.string.village_table_va_circle_name)+","
                +DataBaseHelperClass_VillageNames.VCM_va_circle_code+" from "
                +DataBaseHelperClass_VillageNames.TABLE_NAME+ " order by "+getString(R.string.village_table_va_circle_name), null);
        if (cursor.moveToFirst()) {
            do {
                hab_Va_Circle_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_VillageNames.VCM_va_circle_code));

                openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
                database=openHelper.getWritableDatabase();

                Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+" where "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VillageCircle_Code+"="+hab_Va_Circle_Code+" and "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null and "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"=9999 and "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code+"=255",null);
                if (cursor1.getCount()>0){
                    totalCount_va_Cir = cursor1.getCount();
                    Log.d("totalCount_va_Cir", String.valueOf(totalCount_va_Cir));
                    SearchVillageCircleName.add(new AutoCompleteTextBox_Object(hab_Va_Circle_Code,(cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_va_circle_name)))+"-("+totalCount_va_Cir+")")));
                }else {
                    cursor1.close();
                    SearchVillageCircleName.add(new AutoCompleteTextBox_Object(hab_Va_Circle_Code,(cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_va_circle_name))))));
                }
                Log.d("Village Names", ""+ SearchVillageCircleName);

            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }

        return SearchVillageCircleName;
    }

    public List<AutoCompleteTextBox_Object> getVillageList(String Village_Circle_Code){
        openHelper=new DataBaseHelperClass_VillageNames(RI_Field_Report.this);
        database=openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("Select "+getString(R.string.village_table_habitation_name)+","
                + DataBaseHelperClass_VillageNames.HM_village_code+" from "
                + DataBaseHelperClass_VillageNames.TABLE_NAME+" where "
                + DataBaseHelperClass_VillageNames.VCM_va_circle_code+"="+Village_Circle_Code+" order by "+getString(R.string.village_table_va_circle_name), null);
        if (cursor.moveToFirst()) {
            do {
                hab_Village_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_VillageNames.HM_village_code));

                openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
                database=openHelper.getWritableDatabase();

                Cursor cursor1 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME
                        +" where "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"="+hab_Village_Code+" and "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null",null);
                if (cursor1.getCount()>0){
                    totalCount_vill = cursor1.getCount();
                    Log.d("totalCount_vill", String.valueOf(totalCount_vill));
                    SearchVillageName.add(new AutoCompleteTextBox_Object(hab_Village_Code,(cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_habitation_name)))+"-("+totalCount_vill+")")));
                }else {
                    cursor1.close();
                    SearchVillageName.add(new AutoCompleteTextBox_Object(hab_Village_Code,(cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_habitation_name))))));
                }
                Log.d("Village Names", ""+ SearchVillageName);
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }

        return SearchVillageName;
    }

    public void GetVillageCircleName(){
        objects.clear();
        objects = getVillageCircleNameList();

        ArrayAdapter<AutoCompleteTextBox_Object> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchVillageCircle.setAdapter(adapter);
        autoSearchVillageCircle.setOnItemClickListener((parent, view, position, id) -> {
            // fetch the user selected value

            String get_str = parent.getItemAtPosition(position).toString();
            Log.d("get_str",get_str);
            String[] split_str = get_str.split("-");
            for (String s : split_str) {
                Log.d("SPLIT_STR", s);
            }
            strSearchVillageCircleName = split_str[0];
            item_Position = position+1;
            Log.d("item_Position", String.valueOf(item_Position));
            Log.d("strSearchVilCircleName", strSearchVillageCircleName);

            get_Village_Circle_Code = ((SpinnerObject)parent.getItemAtPosition(position)).getId();

            lLayoutVillage.setVisibility(View.VISIBLE);
            GetVillageName(get_Village_Circle_Code);

            VA_Circle_Name = strSearchVillageCircleName;
            Global.VA_Circle_Name1 = VA_Circle_Name;
            Global.VA_Circle_Code1 = String.valueOf(get_Village_Circle_Code);
            Log.d("RI_VA_Circle_Name",""+VA_Circle_Name);
            // create Toast with user selected value
            //Toast.makeText(New_Request_FirstScreen.this, "Selected Item is: \t" + strSearchVillageName, Toast.LENGTH_LONG).show();
        });
    }

    @SuppressLint("ResourceType")
    public void GetVillageName(String Village_Circle_Code){
        objects = getVillageList(Village_Circle_Code);
        Log.d("adapterValue", ""+ objects);
        ArrayAdapter<AutoCompleteTextBox_Object> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects);
        adapter.setDropDownViewResource(R.layout.list_item);
        adapter.notifyDataSetChanged();
        autoSearchVillage.setAdapter(adapter);
        autoSearchVillage.setOnItemClickListener((parent, view, position, id) -> {
            // fetch the user selected value

            String get_str = ((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getValue();
            Log.d("get_str",get_str);
            String[] split_str = get_str.split("-");
            for (String s : split_str) {
                Log.d("SPLIT_STR", s);
            }
            strSearchVillageName = split_str[0];
            item_Position = position+1;
            Log.d("item_Position", String.valueOf(item_Position));
            Log.d("strSearchVilCircleName", strSearchVillageName);

            get_village_code = ((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId();

            Log.d("Village_Code3", ""+ get_village_code);
            listLayout.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            displayData_AfterItemSelected(get_village_code);

            village_name = strSearchVillageName;
        });
    }

    public void GetTownName(){
        objects_Town.clear();
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(RI_Field_Report.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        objects_Town = sqlLiteOpenHelper_class.Get_TownName_RI(Integer.parseInt(district_Code), Integer.parseInt(taluk_Code), getString(R.string.town_master_town_name));
        Log.d("AdapterValue", String.valueOf(objects_Town));
        ArrayAdapter<AutoCompleteTextBox_Object> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Town);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchTown.setAdapter(adapter);
        autoSearchTown.setOnItemClickListener((parent, view, position, id) -> {
            // fetch the user selected value
            String get_str = parent.getItemAtPosition(position).toString();
            Log.d("get_str",get_str);
            String[] split_str = get_str.split("-");
            for (String s : split_str) {
                Log.d("SPLIT_STR", s);
            }
            strSearchTownName = split_str[0];
            item_Position = position+1;
            Log.d("item_Position", String.valueOf(item_Position));
            Log.d("strSearchTownName", strSearchTownName);
            //town_Code = sqlLiteOpenHelper_class.Get_TownCode(strSearchTownName);
            town_Code = Integer.parseInt(((SpinnerObject)parent.getItemAtPosition(position)).getId());
            Log.d("town_Code1",""+town_Code);
            l_ward.setVisibility(View.VISIBLE);
            GetWardName(town_Code);
        });
    }

    public void GetWardName(final int town_Code){
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(RI_Field_Report.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        objects_Town = sqlLiteOpenHelper_class.Get_WardName_RI(Integer.parseInt(district_Code), Integer.parseInt(taluk_Code), town_Code, getString(R.string.town_master_ward_name));

        ArrayAdapter<AutoCompleteTextBox_Object> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Town);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchWard.setAdapter(adapter);
        autoSearchWard.setOnItemClickListener((parent, view, position, id) -> {
            // fetch the user selected value
            String get_str = parent.getItemAtPosition(position).toString();
            Log.d("get_str",get_str);
            String[] split_str = get_str.split(":");
            for (String s : split_str) {
                Log.d("SPLIT_STR", s);
            }
            strSearchWardName = split_str[0];
            item_Position = position+1;
            Log.d("item_Position", String.valueOf(item_Position));
            Log.d("strSearchWardName", strSearchWardName);
            Log.d("town_Code_W",""+town_Code);
//                ward_Code = sqlLiteOpenHelper_class.Get_WardCode(town_Code, strSearchWardName);
            ward_Code = Integer.parseInt(((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId());
            Log.d("ward_Code",""+ward_Code);
            listLayout.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            display_Urban_Data_AfterItemSelected(town_Code, ward_Code);
        });
    }

    public void displayData_AfterItemSelected(String village_Code) {
        int i=1;
        Log.d("InDisplay", ""+ i);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select "+getString(R.string.ser_tran_service_name)+", count("+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo
                +") as TotalCount from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+" where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null and "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"=9999 and "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"='"+village_Code
                +"' group by "+getString(R.string.ser_tran_service_name), null);

        SlNo.clear();
        Service_Name.clear();
        TotalCount.clear();
        VACircle_Name.clear();
        VA_Circle_Code.clear();
        VillageName.clear();
        VillageCode.clear();
        Option_Flag.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Service_Name.add(cursor.getString(cursor.getColumnIndex(getString(R.string.ser_tran_service_name))));
                    TotalCount.add(cursor.getString(cursor.getColumnIndex("TotalCount")));
                    VACircle_Name.add(strSearchVillageCircleName);
                    VA_Circle_Code.add(String.valueOf(get_Village_Circle_Code));
                    VillageName.add(strSearchVillageName);
                    VillageCode.add(String.valueOf(get_village_code));
                    Option_Flag.add(option_Flag);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            ri_list_adapter = new RI_List_Adapter(RI_Field_Report.this, SlNo, Service_Name, TotalCount, VACircle_Name, VA_Circle_Code, VillageName, VillageCode, Option_Flag);
            listView.setAdapter(ri_list_adapter);
            database.close();
            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
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
    public void display_Urban_Data_AfterItemSelected(int town_Code, int ward_Code) {
        int i=1;
        Log.d("InDisplay", ""+ i);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select "+getString(R.string.ser_tran_service_name)+", count("+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo
                +") as TotalCount from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+" where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"="+town_Code+" and "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code+"="+ward_Code+" and "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null and "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"='99999"
                +"' group by "+getString(R.string.ser_tran_service_name), null);

        SlNo.clear();
        Service_Name.clear();
        TotalCount.clear();
        VACircle_Name.clear();
        VA_Circle_Code.clear();
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
                    Service_Name.add(cursor.getString(cursor.getColumnIndex(getString(R.string.ser_tran_service_name))));
                    TotalCount.add(cursor.getString(cursor.getColumnIndex("TotalCount")));
                    VACircle_Name.add(strSearchVillageCircleName);
                    VA_Circle_Code.add(String.valueOf(get_Village_Circle_Code));
                    TownName.add(strSearchTownName);
                    TownCode.add(String.valueOf(town_Code));
                    WardName.add(strSearchWardName);
                    WardCode.add(String.valueOf(ward_Code));
                    Option_Flag.add(option_Flag);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            ri_ur_list_adapter = new RI_UR_List_Adapter(RI_Field_Report.this, SlNo, Service_Name, TotalCount, VACircle_Name, VA_Circle_Code, TownName, TownCode, WardName, WardCode, Option_Flag);
            listView.setAdapter(ri_ur_list_adapter);
            database.close();
            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
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
        finish();
    }
}
