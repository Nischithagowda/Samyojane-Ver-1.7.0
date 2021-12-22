package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface_NIC;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushToAnotherVA extends AppCompatActivity {

    Button btnBack, btnSubmit;
    TextView tvHobli, tvTaluk, tvVA_Name, tvVillageName, tv_V_T_Name;
    ArrayList<String> arrayList;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database, database1;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Applicant_Name = new ArrayList<>();
    ArrayList<String> Applicant_ID = new ArrayList<>();
    ListView listView;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    String applicant_Id, town_Name, ward_Name;
    int district_Code, taluk_Code, hobli_Code, va_Circle_Code, town_code, ward_code;
    String service_name, villageCode, village_name, option_Flag;
    PushToAnotherVA_List_Adapter list_adapter;
    AutoCompleteTextView autoSearchVillageCircle, autoSearchVillage, autoSearchTown, autoSearchWard, autoSearchVillageCircle_urban;
    LinearLayout l_Rural, l_VACircle, l_Village, l_Urban, l_town, l_ward, l_VACircle_urban;
    List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
    List<SpinnerObject> objects_Village = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Village_Urban = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Town = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_CheckTown = new ArrayList<>();
    String P_Village_Circle_Code = "", P_Village_Circle_Code_Urban = "";
    int P_town_village_code =0, P_ward_Code=0, serviceCode;
    String P_VA_CircleName="", P_village_name="", P_town_name="", P_word_name="", P_VA_CircleNameUrban="";
    String hab_Va_Circle_Code, hab_Village_Code;
    List<AutoCompleteTextBox_Object> SearchVillageCircleName = new ArrayList<>();
    List<SpinnerObject> SearchVillageName = new ArrayList<>();
    ProgressDialog p_dialog;
    DataBaseHelperClass_VillageNames_DTH dataBaseHelperClass_villageNames_dth;
    SharedPreferences sharedPreferences;
    String mobile_Shared=null;
    int count, ser_count=0;
    RadioGroup radioGroup;
    RadioButton radioButton_rural, radioButton_urban;
    String urbanRuralFlag;
    String uName_get;
    APIInterface_NIC apiInterface_nic;
    int DesiCode;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_to_another_va);

        urbanRuralFlag = getString(R.string.rural);

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
        autoSearchVillageCircle_urban = findViewById(R.id.autoSearchVillageCircle_urban);
        l_Urban = findViewById(R.id.l_Urban);
        l_Rural = findViewById(R.id.l_Rural);
        l_town = findViewById(R.id.l_town);
        l_ward = findViewById(R.id.l_ward);
        l_Village = findViewById(R.id.l_Village);
        l_VACircle = findViewById(R.id.l_VACircle);
        l_VACircle_urban = findViewById(R.id.l_VACircle_urban);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton_rural = findViewById(R.id.radioButton_rural);
        radioButton_urban = findViewById(R.id.radioButton_urban);

        arrayList = new ArrayList<>();

        Intent i = getIntent();
        arrayList = i.getStringArrayListExtra("selected_items");
        district = i.getStringExtra("district");
        taluk = i.getStringExtra("taluk");
        hobli = i.getStringExtra("hobli");
        VA_Name = i.getStringExtra("VA_Name");
        district_Code = i.getIntExtra("district_Code", 0);
        taluk_Code = i.getIntExtra("taluk_Code", 0);
        hobli_Code = i.getIntExtra("hobli_Code", 0);
        va_Circle_Code = i.getIntExtra("va_Circle_Code", 0);
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        service_name = i.getStringExtra("strSearchServiceName");
        serviceCode = i.getIntExtra("serviceCode", 0);
        villageCode = i.getStringExtra("villageCode");
        village_name = i.getStringExtra("strSearchVillageName");
        town_code = i.getIntExtra("town_code", 0);
        town_Name = i.getStringExtra("town_Name");
        ward_code = i.getIntExtra("ward_code", 0);
        ward_Name = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");
        Log.d("arrayListArrayList", ""+arrayList);

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Constants.IMEI_NUMBER) && sharedPreferences.contains(Constants.MOBILE_NUMBER) && sharedPreferences.contains(Constants.LOGIN_STATUS)) {
            mobile_Shared = sharedPreferences.getString(Constants.MOBILE_NUMBER, "");
            DesiCode = sharedPreferences.getInt(Constants.DesiCode_VA, 22);
            uName_get = sharedPreferences.getString(Constants.uName_get, "");
        }

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(PushToAnotherVA.this);
        database = openHelper.getWritableDatabase();

        openHelper=new DataBaseHelperClass_VillageNames_DTH(PushToAnotherVA.this);
        database1=openHelper.getWritableDatabase();

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);
        displayData_AfterItemSelected(arrayList);

        if(Objects.equals(option_Flag, getString(R.string.rural))){
            Log.d("Data","Rural");
            String str = getString(R.string.village_name)+" : ";
            tv_V_T_Name.setText(str);
            tvVillageName.setText(village_name);
            objects_CheckTown.clear();
            dataBaseHelperClass_villageNames_dth = new DataBaseHelperClass_VillageNames_DTH(PushToAnotherVA.this);
            dataBaseHelperClass_villageNames_dth.open_Town_Ward_Tbl();
            objects_CheckTown = dataBaseHelperClass_villageNames_dth.Get_TownName_DTH(district_Code, taluk_Code, hobli_Code, getString(R.string.town_master_town_name));
            if(objects_CheckTown.size()==0){
                radioGroup.setVisibility(View.GONE);
                l_Rural.setVisibility(View.VISIBLE);
                l_VACircle.setVisibility(View.VISIBLE);
                l_Village.setVisibility(View.GONE);
                l_Urban.setVisibility(View.GONE);
                GetVillageCircleName();
            }
        }else if(Objects.equals(option_Flag, getString(R.string.urban))){
            Log.d("Data","Urban");
            String str = getString(R.string.town_name)+"                : "
                    +"\n"+getString(R.string.ward_name_num)+"    : ";
            tv_V_T_Name.setText(str);
            String str1 = town_Name+"\n"+ward_Name;
            tvVillageName.setText(str1);
        }

        if(Objects.equals(urbanRuralFlag, getString(R.string.rural))){
            Log.d("Data","Rural");
            l_Rural.setVisibility(View.VISIBLE);
            l_VACircle.setVisibility(View.VISIBLE);
            l_Village.setVisibility(View.GONE);
            l_Urban.setVisibility(View.GONE);
            GetVillageCircleName();
        }else if(Objects.equals(urbanRuralFlag, getString(R.string.urban))){
            Log.d("Data","Urban");
            l_Rural.setVisibility(View.GONE);
            l_town.setVisibility(View.VISIBLE);
            l_ward.setVisibility(View.GONE);
            l_VACircle_urban.setVisibility(View.GONE);
            l_Urban.setVisibility(View.VISIBLE);
            GetTownName();
        } else if (urbanRuralFlag == null){
            Log.d("Data","Rural1");
            l_Rural.setVisibility(View.VISIBLE);
            l_VACircle.setVisibility(View.VISIBLE);
            l_Village.setVisibility(View.GONE);
            l_Urban.setVisibility(View.GONE);
            GetVillageCircleName();
        }

        p_dialog = new ProgressDialog(this, R.style.CustomDialog);
        p_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p_dialog.setMessage(getString(R.string.loading));
        p_dialog.setIndeterminate(true);
        p_dialog.setCanceledOnTouchOutside(false);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButton_rural){
                urbanRuralFlag = getString(R.string.rural);
                Log.d("Data","Rural");
                l_Rural.setVisibility(View.VISIBLE);
                l_VACircle.setVisibility(View.VISIBLE);
                l_Village.setVisibility(View.GONE);
                l_Urban.setVisibility(View.GONE);
                GetVillageCircleName();
                autoSearchTown.setText("");
                autoSearchWard.setText("");
                autoSearchVillageCircle_urban.setText("");
                P_town_village_code =0;
                P_ward_Code=0;
                P_Village_Circle_Code_Urban = null;
            }else if (checkedId == R.id.radioButton_urban){
                urbanRuralFlag = getString(R.string.urban);
                Log.d("Data","Urban");
                l_Rural.setVisibility(View.GONE);
                l_town.setVisibility(View.VISIBLE);
                l_ward.setVisibility(View.GONE);
                l_VACircle_urban.setVisibility(View.GONE);
                l_Urban.setVisibility(View.VISIBLE);
                GetTownName();
                autoSearchVillageCircle.setText("");
                autoSearchVillage.setText("");
                P_Village_Circle_Code = null;
                P_town_village_code = 0;
            }
        });

        autoSearchVillageCircle.setOnTouchListener((v, event) -> {
            autoSearchVillageCircle.showDropDown();
            autoSearchVillageCircle.setError(null);
            autoSearchVillage.setText("");
            l_Village.setVisibility(View.GONE);
            return false;
        });

        autoSearchVillage.setOnTouchListener((v, event) -> {
            autoSearchVillage.showDropDown();
            autoSearchVillage.setError(null);
            return false;
        });

        autoSearchTown.setOnTouchListener((v, event) -> {
            autoSearchTown.showDropDown();
            autoSearchTown.setError(null);
            autoSearchWard.setText("");
            autoSearchVillageCircle_urban.setText("");
            l_ward.setVisibility(View.GONE);
            return false;
        });

        autoSearchWard.setOnTouchListener((v, event) -> {
            autoSearchWard.showDropDown();
            autoSearchWard.setError(null);
            autoSearchVillageCircle_urban.setText("");
            l_VACircle_urban.setVisibility(View.GONE);
            return false;
        });

        autoSearchVillageCircle_urban.setOnTouchListener((v, event) -> {
            autoSearchVillageCircle_urban.showDropDown();
            autoSearchVillageCircle_urban.setError(null);
            return false;
        });

        btnSubmit.setOnClickListener(v -> {
            if (!mobile_Shared.isEmpty()) {
                if (Objects.equals(urbanRuralFlag, getString(R.string.rural))) {
                    P_VA_CircleName = autoSearchVillageCircle.getText().toString();
                    P_village_name = autoSearchVillage.getText().toString();

                    if (!Objects.equals(option_Flag, urbanRuralFlag)){
                        P_town_village_code = 99999;
                    } else {
                        P_ward_Code = 255;
                    }

                    if (!P_VA_CircleName.isEmpty() && !P_Village_Circle_Code.isEmpty()) {
                        if (!P_village_name.isEmpty() && P_town_village_code != 0) {
                            for (int ii=0; ii<arrayList.size();ii++) {
                                Log.d("ser_count",""+ser_count);
                                Log.d("NEW_Town_Village_Code",""+ P_town_village_code);
                                Log.d("NEW_Ward_Code", ""+P_ward_Code);
                                Log.d("OLD_Village_Code", ""+villageCode);
                                Log.d("OLD_Town_Code", ""+town_code);
                                Log.d("OLD_Ward_Code", ""+ward_code);
                                Log.d("Applicant_Id", ""+arrayList.get(ii));
                                Log.d("Updated_By_VA_MobileNum", ""+mobile_Shared);
                            }
                            buildAlert_Push(arrayList, getString(R.string.village), "" + P_village_name);
                        } else {
                            autoSearchVillage.setError(getString(R.string.select));
                        }
                    } else {
                        autoSearchVillageCircle.setError(getString(R.string.select));
                    }
                } else if (Objects.equals(urbanRuralFlag, getString(R.string.urban))) {
                    P_town_name = autoSearchTown.getText().toString();
                    P_word_name = autoSearchWard.getText().toString();
                    P_VA_CircleNameUrban = autoSearchVillageCircle_urban.getText().toString();

                    if (!Objects.equals(option_Flag, urbanRuralFlag)){
                        P_town_village_code = 9999;
                        P_ward_Code = 255;
                    }

                    if (!P_town_name.isEmpty() && P_town_village_code != 0) {
                        if (P_ward_Code!=0 && !P_word_name.isEmpty()) {
                            if (!P_Village_Circle_Code_Urban.isEmpty() && !P_VA_CircleNameUrban.isEmpty()) {
                                buildAlert_Push(arrayList, getString(R.string.ward_name_num), "" + P_ward_Code);
                                for (int ii=0; ii<arrayList.size();ii++) {
                                    Log.d("ser_count",""+ser_count);
                                    Log.d("NEW_Village_Code",""+ P_town_village_code);
                                    Log.d("NEW_Town_Code", ""+ P_town_village_code);
                                    Log.d("NEW_Ward_Code", ""+P_ward_Code);
                                    Log.d("OLD_Village_Code", ""+villageCode);
                                    Log.d("OLD_Town_Code", ""+town_code);
                                    Log.d("OLD_Ward_Code", ""+ward_code);
                                    Log.d("Applicant_Id", ""+arrayList.get(ii));
                                    Log.d("Updated_By_VA_MobileNum", ""+mobile_Shared);
                                }
                            } else {
                                autoSearchVillageCircle_urban.setError(getString(R.string.select));
                            }
                        } else {
                            autoSearchWard.setError(getString(R.string.select));
                        }
                    } else {
                        autoSearchTown.setError(getString(R.string.select));
                    }
                }
            } else {
                Toast.makeText(this, getString(R.string.error_with_login), Toast.LENGTH_SHORT).show();
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
                +DataBaseHelperClass_VillageNames_DTH.TABLE_NAME+ " where "+DataBaseHelperClass_VillageNames_DTH.RuralUrban+"=1 order by "+getString(R.string.village_table_va_circle_name), null);
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

    public void GetTownName(){
        objects_Town.clear();
        dataBaseHelperClass_villageNames_dth = new DataBaseHelperClass_VillageNames_DTH(PushToAnotherVA.this);
        dataBaseHelperClass_villageNames_dth.open_Town_Ward_Tbl();
        objects_Town = dataBaseHelperClass_villageNames_dth.Get_TownName_DTH(district_Code, taluk_Code, hobli_Code, getString(R.string.town_master_town_name));
        Log.d("AdapterValue", String.valueOf(objects_Town));
        ArrayAdapter<AutoCompleteTextBox_Object> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Town);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchTown.setAdapter(adapter);
        autoSearchTown.setOnItemClickListener((parent, view, position, id) -> {
            P_town_village_code = Integer.parseInt(((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId());
            Log.d("P_town_Code",""+ P_town_village_code);
            l_ward.setVisibility(View.VISIBLE);
            GetWardName(P_town_village_code);
        });
    }

    public void GetWardName(final int town_Code){
        dataBaseHelperClass_villageNames_dth = new DataBaseHelperClass_VillageNames_DTH(PushToAnotherVA.this);
        dataBaseHelperClass_villageNames_dth.open_Town_Ward_Tbl();
        objects_Town = dataBaseHelperClass_villageNames_dth.Get_WardName_DTH(district_Code, taluk_Code, town_Code, getString(R.string.town_master_ward_name));

        ArrayAdapter<AutoCompleteTextBox_Object> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Town);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchWard.setAdapter(adapter);
        autoSearchWard.setOnItemClickListener((parent, view, position, id) -> {
            P_ward_Code = Integer.parseInt(((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId());
            Log.d("ward_Code",""+P_ward_Code);

            if (ward_code == P_ward_Code && town_code == P_town_village_code){
                Toast.makeText(getApplicationContext(), getString(R.string.select_other_ward), Toast.LENGTH_SHORT).show();
                autoSearchWard.setText("");
                P_word_name = null;
                P_ward_Code = 0;
            } else {
                l_VACircle_urban.setVisibility(View.VISIBLE);
                GetVillageCircleName_Urban(P_town_village_code, P_ward_Code);
            }
        });
    }

    public void GetVillageCircleName_Urban(int p_town_Code, int p_ward_Code) {
        objects_Village_Urban.clear();
        objects_Village_Urban = dataBaseHelperClass_villageNames_dth.getVillageCircleNameList_DTH(district_Code, taluk_Code, p_town_Code, p_ward_Code, getString(R.string.village_table_va_circle_name));

        ArrayAdapter<AutoCompleteTextBox_Object> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Village_Urban);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchVillageCircle_urban.setAdapter(adapter);
        autoSearchVillageCircle_urban.setOnItemClickListener((parent, view, position, id) -> {
            P_Village_Circle_Code_Urban = ((AutoCompleteTextBox_Object)parent.getItemAtPosition(position)).getId();

            Log.d("Village_Code3", ""+ P_town_village_code);
            Log.d("P_VA_Circle_Code_Urban", ""+P_Village_Circle_Code_Urban);
            Log.d("town_code", ""+ town_code);
            Log.d("ward_code", ""+ ward_code);
        });
    }

    public void GetVillageName(String Village_Circle_Code) {
        objects_Village.clear();
        objects_Village = getVillageList(Village_Circle_Code);
        Log.d("adapterValue", "" + objects_Village);
        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Village);
        adapter.setDropDownViewResource(R.layout.list_item);
        adapter.notifyDataSetChanged();
        autoSearchVillage.setAdapter(adapter);
        autoSearchVillage.setOnItemClickListener((parent, view, position, id) -> {
            P_town_village_code = Integer.parseInt(((SpinnerObject)parent.getItemAtPosition(position)).getId());

            Log.d("villageCode", ""+ villageCode);
            Log.d("Village_Code3", ""+ P_town_village_code);
            Log.d("P_Village_Circle_Code", ""+ P_Village_Circle_Code);
            Log.d("town_code", ""+ town_code);
            Log.d("ward_code", ""+ ward_code);

            if (villageCode.equalsIgnoreCase(String.valueOf(P_town_village_code))){
                Toast.makeText(getApplicationContext(), getString(R.string.please_select_other_village), Toast.LENGTH_SHORT).show();
                autoSearchVillage.setText("");
                P_town_village_code = 0;
            }
        });
    }

    public List<SpinnerObject> getVillageList(String Village_Circle_Code){

        Cursor cursor = database1.rawQuery("Select "+getString(R.string.village_table_habitation_name)+","
                + DataBaseHelperClass_VillageNames_DTH.HM_habitation_code+","
                + DataBaseHelperClass_VillageNames_DTH.HM_village_code+" from "
                + DataBaseHelperClass_VillageNames_DTH.TABLE_NAME+" where "
                + DataBaseHelperClass_VillageNames_DTH.VCM_va_circle_code+"="+Village_Circle_Code+ " and "+DataBaseHelperClass_VillageNames_DTH.RuralUrban+"=1 order by "+getString(R.string.village_table_va_circle_name), null);
        if (cursor.moveToFirst()) {
            do {
                hab_Village_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_VillageNames_DTH.HM_village_code));
                SearchVillageName.add(new SpinnerObject(hab_Village_Code,(cursor.getString(cursor.getColumnIndex(getString(R.string.village_table_habitation_name))))));
                Log.d("Village Names", "" + SearchVillageName);
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return SearchVillageName;
    }

    private  void buildAlert_Push(ArrayList<String> arrayList, String vill_ward, String P_vill_Town_name) {
        String str;
        if (arrayList.size()==1){
            str = getString(R.string.are_you_sure_do_you_want_to_push_the_selected)+" "+arrayList.size()+" "+getString(R.string.record)+" "+getString(R.string.to)+" "+vill_ward+" : "+ P_vill_Town_name;
        } else {
            str = getString(R.string.are_you_sure_do_you_want_to_push_the_selected)+" "+arrayList.size()+" "+getString(R.string.records)+" "+getString(R.string.to)+" "+vill_ward+" : "+ P_vill_Town_name;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.confirmation_alert))
                .setMessage(str)
                .setIcon(R.drawable.ic_alert_green_24dp)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.confirm), (dialog, id) -> {
                    dialog.cancel();
                    if (isNetworkAvailable()){
                        dialog.cancel();
                        push(arrayList);
                    } else {
                        buildAlertMessageConnection();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.getClass());
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public void push(ArrayList<String> arrayList){
        count = arrayList.size();
        ser_count = 0;
        p_dialog.show();
        for (int i=0; i<arrayList.size();i++) {

            String RuralOrUrban = null;
            if (urbanRuralFlag.equalsIgnoreCase(getString(R.string.urban))){
                RuralOrUrban = "U";
            } else if (urbanRuralFlag.equalsIgnoreCase(getString(R.string.rural))){
                RuralOrUrban = "R";
            }

            Log.d("ser_count",""+ser_count);
            Log.d("uName_get",""+uName_get);
            Log.d("Applicant_Id_Int", ""+arrayList.get(i));
            Log.d("DesiCode", ""+DesiCode);
            Log.d("urbanRuralFlag", ""+urbanRuralFlag);
            Log.d("RuralOrUrban", ""+RuralOrUrban);
            Log.d("NEW_TownVillage_Code",""+ P_town_village_code);
            Log.d("NEW_Ward_Code", ""+P_ward_Code);
            Log.d("OLD_Village_Code", ""+villageCode);
            Log.d("OLD_Town_Code", ""+town_code);
            Log.d("OLD_Ward_Code", ""+ward_code);
            Log.d("Updated_By_VA_MobileNum", ""+mobile_Shared);

            UpdateVillageTownWardCLASS updateVillageTownWardCLASS = new UpdateVillageTownWardCLASS();
            updateVillageTownWardCLASS.setLoginID(uName_get);
            updateVillageTownWardCLASS.setGscNo(arrayList.get(i));
            updateVillageTownWardCLASS.setDesignationCode(DesiCode);
            updateVillageTownWardCLASS.setRuralOrUrban(RuralOrUrban);
            updateVillageTownWardCLASS.setNewTownVillageCode(P_town_village_code);
            updateVillageTownWardCLASS.setNewWardNo(P_ward_Code);

            pushApplicationToVA(updateVillageTownWardCLASS);
        }
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
        Applicant_ID.clear();

        for (int j=0;j<arrayList.size();j++) {
            String appID = arrayList.get(j);
            Log.d("InDisplay_appID", ""+ appID);
            Cursor cursor = database.rawQuery("select * from "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME + " where "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo + "='" + appID+"'", null);

            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Log.d("InDisplayIf", "" + i);
                        SlNo.add(String.valueOf(i));
                        Applicant_Name.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                        Applicant_ID.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo)));
                        i++;
                    } while (cursor.moveToNext());
                }
                Log.d("InDisplayIf", "" + i);
                list_adapter = new PushToAnotherVA_List_Adapter(PushToAnotherVA.this, SlNo, Applicant_Name, Applicant_ID);
                listView.setAdapter(list_adapter);
            } else {
                cursor.close();
                Log.d("InDisplayElse", "" + i);
            }
        }
    }

    public void pushApplicationToVA(UpdateVillageTownWardCLASS updateVillageTownWardCLASS){
        apiInterface_nic = APIClient.getClient(getString(R.string.MobAPI_New_NIC)).create(APIInterface_NIC.class);

        Call<JsonObject> call = apiInterface_nic.UpdateVillageTownWard(updateVillageTownWardCLASS);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", response.code() + "");
                    JsonObject response_server = response.body();
                    Log.d("response_server", response_server + "");
                    assert response_server != null;
                    JsonPrimitive jsonObject2 = response_server.getAsJsonPrimitive("StatusMessage");
                    String StatusMessage = jsonObject2.toString();
                    Log.d("StatusMessage", StatusMessage + "");
                    JsonPrimitive jsonObject3 = response_server.getAsJsonPrimitive("StatusCode");
                    String StatusCode = jsonObject3.toString();
                    Log.d("StatusCode", StatusCode + "");
                    if (StatusMessage.isEmpty()) {
                        p_dialog.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.coultnt_push_the_data_pls_try_again), Toast.LENGTH_SHORT).show();
                    } else if (StatusMessage.equals(getString(R.string.access_denied_msg))) {
                        p_dialog.dismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PushToAnotherVA.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.alert))
                                .setMessage(getString(R.string.access_denied_msg))
                                .setIcon(R.drawable.ic_error_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
                        TextView msgTxt = alert.findViewById(android.R.id.message);
                        msgTxt.setTextSize(16);
                    } else if (StatusCode.equalsIgnoreCase("0")) {
                        p_dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "" + StatusMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        try {
//                        JSONObject jsonObject = new JSONObject(StatusMessage);
//                        Log.d("jsonObject1", "" + jsonObject);
//                        //jsonObject = jsonObject.getJSONObject("data");
//                        int data = jsonObject.getInt("data");
//                        Log.d("jsonObject2", "" + data);
//                        response_server = String.valueOf(data);
                            if (StatusMessage.equalsIgnoreCase("Updated")) {
                                ser_count++;
                                Log.d("ser_count", "" + ser_count);
                                if (count == ser_count) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.pushed_successfully), Toast.LENGTH_SHORT).show();
                                    database.close();
                                    database1.close();
                                    Intent i = new Intent(PushToAnotherVA.this, New_Request_FirstScreen.class);
                                    i.putExtra("applicant_Id", applicant_Id);
                                    i.putExtra("district_Code", district_Code);
                                    i.putExtra("taluk_Code", taluk_Code);
                                    i.putExtra("hobli_Code", hobli_Code);
                                    i.putExtra("district", district);
                                    i.putExtra("taluk", taluk);
                                    i.putExtra("VA_Name", VA_Name);
                                    i.putExtra("hobli", hobli);
                                    i.putExtra("va_Circle_Code", va_Circle_Code);
                                    i.putExtra("VA_Circle_Name", VA_Circle_Name);
                                    i.putExtra("strSearchServiceName", service_name);
                                    i.putExtra("strSearchVillageName", village_name);
                                    i.putExtra("serviceCode", serviceCode);
                                    i.putExtra("villageCode", String.valueOf(villageCode));
                                    i.putExtra("option_Flag", option_Flag);
                                    i.putExtra("town_Name", town_Name);
                                    i.putExtra("town_code", town_code);
                                    i.putExtra("ward_Name", ward_Name);
                                    i.putExtra("ward_code", ward_code);
                                    i.putExtra("pushedFlag", 1);
                                    startActivity(i);
                                    finish();
                                }
                            } else if (StatusMessage.equalsIgnoreCase("NOT Updated")) {
                                p_dialog.dismiss();
                                Toast.makeText(getApplicationContext(), getString(R.string.coultnt_push_the_data_pls_try_again), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            p_dialog.dismiss();
                            Log.d("JSONException", "" + e.getMessage());
                            Toast.makeText(getApplicationContext(), getString(R.string.coultnt_push_the_data_pls_try_again), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    p_dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("multipleResource",""+t.getMessage());
                p_dialog.dismiss();
                call.cancel();
                //Toast.makeText(getApplicationContext(), getString(R.string.coultnt_push_the_data_pls_try_again), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        database.close();
        database1.close();
        Intent i = new Intent(PushToAnotherVA.this, New_Request_FirstScreen.class);
        i.putExtra("applicant_Id", applicant_Id);
        i.putExtra("district_Code", district_Code);
        i.putExtra("taluk_Code", taluk_Code);
        i.putExtra("hobli_Code", hobli_Code);
        i.putExtra("district", district);
        i.putExtra("taluk", taluk);
        i.putExtra("VA_Name", VA_Name);
        i.putExtra("hobli", hobli);
        i.putExtra("va_Circle_Code", va_Circle_Code);
        i.putExtra("VA_Circle_Name", VA_Circle_Name);
        i.putExtra("strSearchServiceName", service_name);
        i.putExtra("strSearchVillageName", village_name);
        i.putExtra("serviceCode", serviceCode);
        i.putExtra("villageCode", String.valueOf(villageCode));
        i.putExtra("option_Flag", option_Flag);
        i.putExtra("town_Name", town_Name);
        i.putExtra("town_code", town_code);
        i.putExtra("ward_Name", ward_Name);
        i.putExtra("ward_code", ward_code);
        startActivity(i);
        finish();
    }
}
