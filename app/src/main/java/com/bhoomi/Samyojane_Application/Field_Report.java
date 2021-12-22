package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
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

public class Field_Report extends AppCompatActivity {
    Button btnBack;
    TextView tvHobli, tvTaluk, tvVA_Name, fieldReport;
    static String district, taluk, hobli, VA_Circle_Name, VA_Name;
    static int district_Code, taluk_Code, hobli_Code, va_Circle_Code;
    String service_name;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database, database1;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Service_Name = new ArrayList<>();
    ArrayList<String> TotalCount = new ArrayList<>();
    ArrayList<String> VillageName = new ArrayList<>();
    ArrayList<String> VillageCode = new ArrayList<>();
    ArrayList<String> Option_Flag = new ArrayList<>();
    ArrayList<String> TownName = new ArrayList<>();
    ArrayList<String> TownCode = new ArrayList<>();
    ArrayList<String> WardName = new ArrayList<>();
    ArrayList<String> WardCode = new ArrayList<>();
    TextView emptyTxt;
    ListView listView;
    List_Adapter list_adapter;
    UR_List_Adapter ur_list_adapter;
    AutoCompleteTextView autoSearchVillage, autoSearchTown, autoSearchWard;
    List<AutoCompleteTextBox_Object> objects_Village = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Town = new ArrayList<>();
    static String strSearchVillageName, strSearchServiceName, strSearchTownName, strSearchWardName, town_Code_1, ward_Code_1;
    static String get_village_code;
    List<AutoCompleteTextBox_Object> SearchVillageName = new ArrayList<>();
    LinearLayout linearLayout, listLayout, l_Rural, l_Urban, l_town, l_ward;
    TextView totalPending;
    int town_Code, ward_Code;
    int totalCount_vill;
    int hab_Village_Code;
    int item_Position;
    String habitaion_ename_1=null;
    RadioGroup radioGroup;
    RadioButton radioButton_rural, radioButton_urban;
    String option_Flag;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    ArrayAdapter<AutoCompleteTextBox_Object> adapter;
    ArrayAdapter<AutoCompleteTextBox_Object> adapter_village;

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_report);

        option_Flag = getString(R.string.rural);

        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvVA_Name = findViewById(R.id.VA_name);

        btnBack = findViewById(R.id.btnBack);
        fieldReport = findViewById(R.id.fieldReport);
        emptyTxt = findViewById(R.id.emptyTxt);
        listView = findViewById(R.id.list);
        autoSearchVillage = findViewById(R.id.autoSearchVillage);
        linearLayout = findViewById(R.id.total_Applicants);
        listLayout = findViewById(R.id.listLayout);
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

        linearLayout.setVisibility(View.GONE);
        listLayout.setVisibility(View.GONE);
        emptyTxt.setVisibility(View.GONE);
        l_Urban.setVisibility(View.GONE);

        fieldReport.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        Intent i = getIntent();
        district_Code = i.getIntExtra("district_Code", 0);
        district = i.getStringExtra("district");
        taluk_Code = i.getIntExtra("taluk_Code", 0);
        taluk = i.getStringExtra("taluk");
        hobli_Code = i.getIntExtra("hobli_Code", 0);
        hobli = i.getStringExtra("hobli");
        va_Circle_Code = i.getIntExtra("va_Circle_Code", 0);
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        VA_Name = i.getStringExtra("VA_Name");
        strSearchVillageName = i.getStringExtra("strSearchVillageName");
        service_name = i.getStringExtra("strSearchServiceName");
        get_village_code = i.getStringExtra("villageCode");
        town_Code_1 = i.getStringExtra("town_code");
        strSearchTownName = i.getStringExtra("town_Name");
        ward_Code_1 = i.getStringExtra("ward_code");
        strSearchWardName = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");

        Log.d("Second_Database_Value", ""+district);
        Log.d("Second_Database_Value", ""+taluk);
        Log.d("Second_Database_Value", ""+hobli);
        Log.d("Second_Database_Value", ""+VA_Circle_Name);
        Log.d("Second_Database_Value", ""+VA_Name);
        Log.d("Second_Database_Value", ""+strSearchVillageName);
        Log.d("Second_Database_Value:", "!"+option_Flag);

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);

        openHelper=new DataBaseHelperClass_btnDownload_ServiceTranTable(Field_Report.this);
        database=openHelper.getWritableDatabase();

        openHelper=new DataBaseHelperClass_VillageNames(Field_Report.this);
        database1=openHelper.getWritableDatabase();

        Cursor cursor_V = database1.rawQuery("select * from "+DataBaseHelperClass_VillageNames.TABLE_NAME
                +" where "+DataBaseHelperClass_VillageNames.VCM_va_circle_code+"="+va_Circle_Code,null);
        if (cursor_V.getCount()>0) {

            Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME + " where "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + " is null" + " and "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code + "=9999", null);
            int count;
            if (cursor.getCount() > 0) {
                count = cursor.getCount();
                Log.d("Count", ""+ count);
                totalPending.setText(String.valueOf(count));
            } else {
                cursor.close();
                totalPending.setText("0");
            }
        }else {
            cursor_V.close();
            totalPending.setText("0");
        }

        if(Objects.equals(option_Flag, getString(R.string.rural))){
            Log.d("option_Flag1:", ""+option_Flag);
            radioButton_rural.setChecked(true);
            radioButton_urban.setChecked(false);

            openHelper=new DataBaseHelperClass_btnDownload_ServiceTranTable(Field_Report.this);
            database=openHelper.getWritableDatabase();

            Cursor cursor1 = database1.rawQuery("select * from "+DataBaseHelperClass_VillageNames.TABLE_NAME
                    +" where "+DataBaseHelperClass_VillageNames.VCM_va_circle_code+"="+va_Circle_Code,null);
            if (cursor1.getCount()>0) {
                Cursor cursor2 = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME + " where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + " is null" + " and "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code + "=9999", null);
                int count1;
                if (cursor2.getCount() > 0) {
                    count1 = cursor2.getCount();
                    Log.d("Count", String.valueOf(count1));
                    totalPending.setText("" + count1);
                } else {
                    cursor2.close();
                    totalPending.setText("0");
                }
            } else {
                cursor1.close();
                totalPending.setText("0");
            }

            GetVillageName(va_Circle_Code);

            if(strSearchVillageName!=null){
                Log.d("Second_Database_Value", "not null"+strSearchVillageName);
                autoSearchVillage.setText(strSearchVillageName);

                Log.d("Village_Code_n", ""+ get_village_code);
                listLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                displayData_AfterItemSelected(get_village_code);

                Log.d("VillageName", ""+strSearchVillageName);
            }
            else{
                Log.d("Second_Database_Value", "null");
                Log.d("VillageName", "Village Name was null");
            }

            Log.d("New_Request_FirstScreen", ""+district_Code);
            Log.d("New_Request_FirstScreen", ""+taluk_Code);
            Log.d("New_Request_FirstScreen",""+hobli_Code
                    +"\nVillageCircleCode :"+va_Circle_Code
                    +"\nvillage_code: "+get_village_code
                    +"\nVillageName :"+strSearchVillageName
                    +"\nServiceName:"+strSearchServiceName);

        }else if(Objects.equals(option_Flag, getString(R.string.urban))){
            Log.d("option_Flag1:", ""+option_Flag);
            radioButton_rural.setChecked(false);
            radioButton_urban.setChecked(true);

            l_Rural.setVisibility(View.GONE);
            l_Urban.setVisibility(View.VISIBLE);
            listLayout.setVisibility(View.GONE);
            autoSearchVillage.setText("");

            GetTownName();

            openHelper=new DataBaseHelperClass_btnDownload_ServiceTranTable(Field_Report.this);
            database=openHelper.getWritableDatabase();

            Cursor cursor3 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+ " where "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null"+ " and "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+"=99999", null);
            int count3;
            if (cursor3.getCount()>0){
                count3 = cursor3.getCount();
                Log.d("Count", String.valueOf(count3));
                totalPending.setText(""+count3);
            }
            else {
                cursor3.close();
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
        }
        else if(option_Flag==null) {
            option_Flag = getString(R.string.rural);
            Log.d("option_Flag1", ""+option_Flag);
            GetVillageName(va_Circle_Code);
        }

        Global.district_Code1 =district_Code;
        Global.district_Name1 = district;
        Global.taluk_Code1 = taluk_Code;
        Global.taluk_Name1 = taluk;
        Global.hobli_Code1 = hobli_Code;
        Global.hobli_Name1 = hobli;
        Global.VA_Circle_Code1 = va_Circle_Code;
        Global.VA_Circle_Name1 = VA_Circle_Name;
        Global.VA_Name1 = VA_Name;

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(Field_Report.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton_rural) {
                option_Flag = getString(R.string.rural);

                l_Rural.setVisibility(View.VISIBLE);
                l_Urban.setVisibility(View.GONE);
                listLayout.setVisibility(View.GONE);
                autoSearchTown.setText("");
                autoSearchWard.setText("");

                GetVillageName(va_Circle_Code);

                if(strSearchVillageName!=null){
                    autoSearchVillage.setText(strSearchVillageName);
                    openHelper = new DataBaseHelperClass_VillageNames(Field_Report.this);
                    database = openHelper.getWritableDatabase();

                    Cursor cursor1 = database.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME
                            +" where "+getString(R.string.village_table_habitation_name)+"='"+strSearchVillageName+"'", null);
                    if(cursor1.getCount()>0) {
                        if(cursor1.moveToFirst()){
                            get_village_code = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_VillageNames.HM_village_code));
                            Log.d("Village_Code_n", ""+ get_village_code);
                            listLayout.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.VISIBLE);
                            displayData_AfterItemSelected(get_village_code);
                        }
                    } else {
                        cursor1.close();
                    }
                    Log.d("VillageName", ""+strSearchVillageName);
                }
                else{
                    Log.d("VillageName", "Village Name was null");
                }

                openHelper=new DataBaseHelperClass_btnDownload_ServiceTranTable(Field_Report.this);
                database=openHelper.getWritableDatabase();

                Cursor cursor1 = database1.rawQuery("select * from "+DataBaseHelperClass_VillageNames.TABLE_NAME
                        +" where "+DataBaseHelperClass_VillageNames.VCM_va_circle_code+"="+va_Circle_Code,null);
                if (cursor1.getCount()>0) {

                    Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME + " where "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + " is null" + " and "
                            + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code + "=9999", null);
                    int count;
                    if (cursor.getCount() > 0) {
                        count = cursor.getCount();
                        Log.d("Count", String.valueOf(count));
                        totalPending.setText("" + count);
                    } else {
                        cursor.close();
                        totalPending.setText("0");
                    }
                } else {
                    cursor1.close();
                    totalPending.setText("0");
                }

            }
            else if (checkedId == R.id.radioButton_urban) {
                option_Flag = getString(R.string.urban);
                l_Rural.setVisibility(View.GONE);
                l_Urban.setVisibility(View.VISIBLE);
                l_ward.setVisibility(View.GONE);
                listLayout.setVisibility(View.GONE);
                autoSearchVillage.setText("");

                GetTownName();

                openHelper=new DataBaseHelperClass_btnDownload_ServiceTranTable(Field_Report.this);
                database=openHelper.getWritableDatabase();

                Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+ " where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null"+ " and "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+"=99999", null);
                int count;
                if (cursor.getCount()>0){
                    count = cursor.getCount();
                    Log.d("Count", String.valueOf(count));
                    totalPending.setText(""+count);
                }
                else {
                    cursor.close();
                    totalPending.setText("0");
                }

            }
            Log.d("option_Flag", ""+option_Flag);
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

    public List<AutoCompleteTextBox_Object> getVillageList(int va_Circle_Code){
        openHelper=new DataBaseHelperClass_VillageNames(Field_Report.this);
        database=openHelper.getWritableDatabase();


        Cursor cursor = database.rawQuery("Select distinct "+getString(R.string.village_table_habitation_name)+","
                + DataBaseHelperClass_VillageNames.HM_village_code
                +" from "+DataBaseHelperClass_VillageNames.TABLE_NAME +" where "
                +DataBaseHelperClass_VillageNames.VCM_va_circle_code+"="+va_Circle_Code+" order by "+getString(R.string.village_table_habitation_name), null);
        if (cursor.moveToFirst()) {
            do {
                hab_Village_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_VillageNames.HM_village_code));
                habitaion_ename_1 = cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_habitation_name)));

                openHelper=new DataBaseHelperClass_btnDownload_ServiceTranTable(Field_Report.this);
                database=openHelper.getWritableDatabase();

                Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                        +" where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+"="+ hab_Village_Code+" and "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null", null);
                if (cursor1.moveToFirst()){
                    totalCount_vill = cursor1.getCount();
                    Log.d("Village_Vise_Count", String.valueOf(totalCount_vill));
                    SearchVillageName.add(new AutoCompleteTextBox_Object((String.valueOf(hab_Village_Code)),(habitaion_ename_1+"-("+totalCount_vill+")")));
                }
                else {
                    cursor1.close();
                    SearchVillageName.add(new AutoCompleteTextBox_Object(String.valueOf(hab_Village_Code),habitaion_ename_1));
                }
                Log.d("Village Names", ""+ SearchVillageName);
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }

        return SearchVillageName;
    }

    @SuppressLint("ResourceType")
    public void GetVillageName(int va_Circle_Code){
        objects_Village.clear();
        objects_Village = getVillageList(va_Circle_Code);

        adapter_village = new ArrayAdapter<>(this, R.layout.list_item, objects_Village);
        adapter_village.setDropDownViewResource(R.layout.list_item);
        autoSearchVillage.setAdapter(adapter_village);
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
            Log.d("strSearchVillageName", strSearchVillageName);

            get_village_code = ((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId();

            Log.d("Village_Code_n", ""+ get_village_code);
            listLayout.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            displayData_AfterItemSelected(get_village_code);

            Global.VillageName1 = strSearchVillageName;
        });
    }

    public void GetTownName(){
        objects_Town.clear();
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(Field_Report.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        objects_Town = sqlLiteOpenHelper_class.Get_TownName(district_Code, taluk_Code, getString(R.string.town_master_town_name));

        adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Town);
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
            town_Code = Integer.parseInt(((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId());
            Log.d("town_Code1",""+town_Code);
            l_ward.setVisibility(View.VISIBLE);
            GetWardName(town_Code);
        });
    }

    public void GetWardName(final int town_Code){
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(Field_Report.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        objects_Town = sqlLiteOpenHelper_class.Get_WardName(district_Code, taluk_Code, town_Code, getString(R.string.town_master_ward_name));

        adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Town);
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
            //ward_Code = sqlLiteOpenHelper_class.Get_WardCode(town_Code, strSearchWardName);
            ward_Code = Integer.parseInt(((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId());
            Log.d("ward_Code_W",""+ward_Code);
            listLayout.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            display_Urban_Data_AfterItemSelected(town_Code, ward_Code);
        });
    }

    public static class Global{
        static int district_Code1 = district_Code;
        static int taluk_Code1 = taluk_Code;
        static int hobli_Code1 = hobli_Code;
        static String district_Name1 = district;
        static String taluk_Name1 = taluk;
        static String hobli_Name1 = hobli;
        static int VA_Circle_Code1 = va_Circle_Code;
        static String VA_Circle_Name1 = VA_Circle_Name;
        static String VA_Name1 =VA_Name;
        static String VillageName1 = strSearchVillageName;
    }

    @SuppressLint("SetTextI18n")
    public void displayData_AfterItemSelected(String village_Code) {
        int i=1;
        Log.d("InDisplay", ""+ i);
        Log.d("village_Code_la", String.valueOf(village_Code));
        Log.d("get_village_code_la", String.valueOf(get_village_code));
        Log.d("option_Flag", ""+option_Flag);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(Field_Report.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select "+getString(R.string.ser_tran_service_name)+", count("+DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo
                +") as TotalCount from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+"='"+village_Code
                +"' group by "+getString(R.string.ser_tran_service_name), null);

        SlNo.clear();
        Service_Name.clear();
        TotalCount.clear();
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
                    VillageName.add(strSearchVillageName);
                    VillageCode.add(String.valueOf(get_village_code));
                    Option_Flag.add(option_Flag);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter = new List_Adapter(Field_Report.this, SlNo, Service_Name, TotalCount, VillageName, VillageCode, Option_Flag);
            listView.setAdapter(list_adapter);
            database.close();
            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.close();
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText("No Data Found");
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("SetTextI18n")
    public void display_Urban_Data_AfterItemSelected(int townCode, int wardCode) {
        int i=1;
        Log.d("InDisplay", ""+ i);
        Log.d("townCode_UR", String.valueOf(townCode));
        Log.d("wardCode_UR", String.valueOf(wardCode));
        Log.d("option_Flag_UR", option_Flag);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(Field_Report.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select "+getString(R.string.ser_tran_service_name)+", count("+DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo
                +") as TotalCount from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "+ DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"="+townCode+" and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"="+wardCode + " and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+"=99999"
                +" group by "+getString(R.string.ser_tran_service_name), null);

        SlNo.clear();
        Service_Name.clear();
        TotalCount.clear();
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
                    TownName.add(strSearchTownName);
                    TownCode.add(String.valueOf(townCode));
                    WardName.add(strSearchWardName);
                    WardCode.add(String.valueOf(wardCode));
                    Option_Flag.add(option_Flag);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            ur_list_adapter = new UR_List_Adapter(Field_Report.this, SlNo, Service_Name, TotalCount, TownName, TownCode, WardName, WardCode, Option_Flag);
            listView.setAdapter(ur_list_adapter);
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
//        Intent i = new Intent(Field_Report.this, SecondScreen.class);
//        i.putExtra("district_Code", district_Code);
//        i.putExtra("districtCode", districtCode);
//        i.putExtra("taluk_Code", taluk_Code);
//        i.putExtra("taluk", taluk);
//        i.putExtra("hobli_Code", hobli_Code);
//        i.putExtra("hobli", hobli);
//        i.putExtra("va_Circle_Code", va_Circle_Code);
//        i.putExtra("VA_Circle_Name", VA_Circle_Name);
//        i.putExtra("VA_Name", VA_Name);
//        i.putExtra("strSearchServiceName", service_name);
//        i.putExtra("villageCode", get_village_code);
//        i.putExtra("strSearchVillageName", strSearchVillageName);
//        startActivity(i);
        finish();
    }
}
