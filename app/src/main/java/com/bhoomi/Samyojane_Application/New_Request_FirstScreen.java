package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class New_Request_FirstScreen extends AppCompatActivity implements DataTransferInterface {

    Button btnBack, btnPushToAnotherVA;
    TextView tvHobli, tvTaluk, tvVA_Name, tvVillageName, tvServiceName, tv_V_T_Name, txtListPushed;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    String applicant_Id, district_Code, taluk_Code, hobli_Code, va_Circle_Code, town_Name, ward_Name, town_code, ward_code;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database, database1;
    ArrayList<String> SlNo = new ArrayList<>();
    ArrayList<String> Applicant_Name = new ArrayList<>();
    ArrayList<String> GSC_FirstPart = new ArrayList<>();
    ArrayList<String> Applicant_ID = new ArrayList<>();
    ArrayList<String> DueDate = new ArrayList<>();
    ArrayList<String> ServiceCode = new ArrayList<>();
    ArrayList<String> ServiceName = new ArrayList<>();
    ArrayList<String> VillageCode = new ArrayList<>();
    ArrayList<String> HabitationCode = new ArrayList<>();
    ArrayList<String> Option_Flag = new ArrayList<>();
    ArrayList<String> TownName = new ArrayList<>();
    ArrayList<String> TownCode = new ArrayList<>();
    ArrayList<String> WardName = new ArrayList<>();
    ArrayList<String> WardCode = new ArrayList<>();

    ArrayList<String> SlNo1 = new ArrayList<>();
    ArrayList<String> Applicant_Name1 = new ArrayList<>();
    ArrayList<String> GSC_FirstPart1 = new ArrayList<>();
    ArrayList<String> Applicant_ID1 = new ArrayList<>();
    ArrayList<String> DueDate1 = new ArrayList<>();
    ArrayList<String> ServiceCode1 = new ArrayList<>();
    ArrayList<String> ServiceName1 = new ArrayList<>();
    ArrayList<String> VillageCode1 = new ArrayList<>();
    ArrayList<String> HabitationCode1 = new ArrayList<>();
    ArrayList<String> Option_Flag1 = new ArrayList<>();
    ArrayList<String> TownName1 = new ArrayList<>();
    ArrayList<String> TownCode1 = new ArrayList<>();
    ArrayList<String> WardName1 = new ArrayList<>();
    ArrayList<String> WardCode1 = new ArrayList<>();
    ArrayList<String> PushedList = new ArrayList<>();

    ListView listView, list_pushed;
    LinearLayout total_regular_Applicants, total_pushed_Applicants;
    Service_List_Adapter list_adapter, list_adapter_pushed;
    UR_Service_List_Adapter ur_service_list_adapter, ur_service_list_adapter_pushed;
    TextView emptyTxt;
    String villageCode, service_name, village_name, habitationCode, option_Flag;
    int serviceCode, serviceCode_DB;
    ArrayList<String> selected_items;
    HashMap<String, String> hashMap_village_name;
    ProgressDialog dialog;
    Set_and_Get_Village_Name set_and_get_village_name;
    int pushedFlag=0;
    HashMap<String, String> hashMap_service_Tran_data;
    Set_and_Get_Service_tran_data set_and_get_service_tran_data;
    String serviceName, serviceName_k;
    String gsc_firstPart, gsc_firstPart_Name=null;
    int j=1;
    RadioGroup radioGroup;
    RadioButton radioButton_regular, radioButton_pushed;
    String radio_Flag;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"MissingPermission", "HardwareIds", "SetTextI18n", "ClickableViewAccessibility", "SdCardPath"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_request_firstscreen);

        btnBack = findViewById(R.id.btnBack);
        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvVA_Name = findViewById(R.id.VA_name);
        listView = findViewById(R.id.list);
        emptyTxt = findViewById(R.id.emptyTxt);
        tvServiceName = findViewById(R.id.tvServiceName);
        tvVillageName = findViewById(R.id.tvVillageName);
        tv_V_T_Name = findViewById(R.id.tv_V_T_Name);
        btnPushToAnotherVA = findViewById(R.id.btnPushToAnotherVA);
        txtListPushed = findViewById(R.id.txtListPushed);
        list_pushed = findViewById(R.id.list_pushed);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton_regular = findViewById(R.id.radioButton_regular);
        radioButton_pushed = findViewById(R.id.radioButton_pushed);
        total_regular_Applicants = findViewById(R.id.total_regular_Applicants);
        total_pushed_Applicants = findViewById(R.id.total_pushed_Applicants);

        txtListPushed.setVisibility(View.GONE);

        radio_Flag = getString(R.string.regular);

        Intent i = getIntent();
        applicant_Id = i.getStringExtra("applicant_Id");
        district_Code = i.getStringExtra("district_Code");
        district = i.getStringExtra("districtCode");
        taluk_Code = i.getStringExtra("taluk_Code");
        taluk = i.getStringExtra("taluk");
        hobli_Code = i.getStringExtra("hobli_Code");
        hobli = i.getStringExtra("hobli");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        VA_Name = i.getStringExtra("VA_Name");
        service_name = i.getStringExtra("strSearchServiceName");
        villageCode = i.getStringExtra("villageCode");
        habitationCode = i.getStringExtra("habitationCode");
        village_name = i.getStringExtra("strSearchVillageName");
        town_code = i.getStringExtra("town_code");
        town_Name = i.getStringExtra("town_Name");
        ward_code = i.getStringExtra("ward_code");
        ward_Name = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");
        pushedFlag = i.getIntExtra("pushedFlag", 0);

        Log.d("pushedFlag", ""+pushedFlag);

        hashMap_village_name = new HashMap<>();
        hashMap_service_Tran_data = new HashMap<>();

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        openHelper = new DataBaseHelperClass_VillageNames_DTH(New_Request_FirstScreen.this);
        database1 = openHelper.getWritableDatabase();

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME+" where "
                +getString(R.string.facility_table_name)+"='"+service_name+"'", null);
        if (cursor.getCount()>0){
            if (cursor.moveToNext()){
                serviceCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code));
            }
        }

        Log.d("New_Request_FirstScreen", "district_Code: "+district_Code);
        Log.d("New_Request_FirstScreen", "taluk_Code: "+taluk_Code);
        Log.d("New_Request_FirstScreen", "hobli_Code: "+hobli_Code+"\nVillageCircleCode: "+va_Circle_Code+"\nServiceName: "+service_name);
        Log.d("New_Request_FirstScreen", "village_name: "+village_name);
        Log.d("New_Request_FirstScreen", "serviceCode: "+serviceCode);
        Log.d("New_Request_FirstScreen", "villageCode: "+villageCode);
        Log.d("New_Request_FirstScreen", "habitationCode: "+habitationCode);
        Log.d("New_Request_FirstScreen", "town_code: "+town_code);
        Log.d("New_Request_FirstScreen", "town_Name: "+town_Name);
        Log.d("New_Request_FirstScreen", "ward_code: "+ward_code);
        Log.d("New_Request_FirstScreen", "ward_Name: "+ward_Name);
        Log.d("New_Request_FirstScreen", "option_Flag: "+option_Flag);

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);
        tvServiceName.setText(service_name);

        btnBack.setOnClickListener(v -> onBackPressed());

        selected_items = new ArrayList<>();

        if(Objects.equals(radio_Flag, getString(R.string.regular))) {
            radioButton_regular.setChecked(true);
            radioButton_pushed.setChecked(false);
            btnPushToAnotherVA.setVisibility(View.VISIBLE);
            txtListPushed.setVisibility(View.GONE);
            total_regular_Applicants.setVisibility(View.VISIBLE);
            total_pushed_Applicants.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            list_pushed.setVisibility(View.GONE);
            Log.d("radio_Flag:", "" + radio_Flag);
            if(!Objects.equals(villageCode, "99999")){
                Log.d("Data","Rural");
                tv_V_T_Name.setText(getString(R.string.village_name)+" : ");
                tvVillageName.setText(village_name);
                displayData_AfterItemSelected();
            }else if(!Objects.equals(town_code, "9999")){
                Log.d("Data","Urban");
                tv_V_T_Name.setText(getString(R.string.town_name)+"                : "
                        +"\n"+getString(R.string.ward_name_num)+"    : ");
                tvVillageName.setText(town_Name+"\n"+ward_Name);
                display_Urban_Data_AfterItemSelected();
            }
        }else if (Objects.equals(radio_Flag, getString(R.string.pushed))){
            btnPushToAnotherVA.setVisibility(View.GONE);
            txtListPushed.setVisibility(View.VISIBLE);
            total_regular_Applicants.setVisibility(View.GONE);
            total_pushed_Applicants.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            list_pushed.setVisibility(View.VISIBLE);
            Log.d("radio_Flag:", ""+radio_Flag);
            radioButton_regular.setChecked(false);
            radioButton_pushed.setChecked(true);
            if(!Objects.equals(villageCode, "99999")){
                Log.d("Data","Rural");
                tv_V_T_Name.setText(getString(R.string.village_name)+" : ");
                tvVillageName.setText(village_name);
                displayRuralPushedData();
            }else if(!Objects.equals(town_code, "9999")){
                Log.d("Data","Urban");
                tv_V_T_Name.setText(getString(R.string.town_name)+"                : "
                        +"\n"+getString(R.string.ward_name_num)+"    : ");
                tvVillageName.setText(town_Name+"\n"+ward_Name);
                displayUrbanPushedData();
            }
        }else if(radio_Flag==null) {
            radioButton_regular.setChecked(true);
            radioButton_pushed.setChecked(false);
            btnPushToAnotherVA.setVisibility(View.VISIBLE);
            txtListPushed.setVisibility(View.GONE);
            total_regular_Applicants.setVisibility(View.VISIBLE);
            total_pushed_Applicants.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            list_pushed.setVisibility(View.GONE);
            Log.d("radio_Flag:", "" + radio_Flag);
            if(!Objects.equals(villageCode, "99999")){
                Log.d("Data","Rural");
                tv_V_T_Name.setText(getString(R.string.village_name)+" : ");
                tvVillageName.setText(village_name);
                displayData_AfterItemSelected();
            }else if(!Objects.equals(town_code, "9999")){
                Log.d("Data","Urban");
                tv_V_T_Name.setText(getString(R.string.town_name)+"                : "
                        +"\n"+getString(R.string.ward_name_num)+"    : ");
                tvVillageName.setText(town_Name+"\n"+ward_Name);
                display_Urban_Data_AfterItemSelected();
            }
        }

        if (pushedFlag==1){
            if (isNetworkAvailable()){
                dialog.show();
                hashMap_service_Tran_data.put("District_Code", district_Code);
                hashMap_service_Tran_data.put("Taluk_Code", taluk_Code);
                hashMap_service_Tran_data.put("Hobli_Code", hobli_Code);
                hashMap_service_Tran_data.put("VA_circle_code", va_Circle_Code );
                Log.d("hashMap_ser_Tran_data",""+hashMap_service_Tran_data+", URL:"+getString(R.string.url_service_Tran_data));
                new GetServiceTranDataFromServer().execute(getString(R.string.url_service_Tran_data));
            } else {
                buildAlertMessageConnection();
            }
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton_regular) {
                radioButton_regular.setChecked(true);
                radioButton_pushed.setChecked(false);
                btnPushToAnotherVA.setVisibility(View.VISIBLE);
                txtListPushed.setVisibility(View.GONE);
                total_regular_Applicants.setVisibility(View.VISIBLE);
                total_pushed_Applicants.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                list_pushed.setVisibility(View.GONE);
                Log.d("radio_Flag:", "" + radio_Flag);
                if(!Objects.equals(villageCode, "99999")){
                    Log.d("Data","Rural");
                    tv_V_T_Name.setText(getString(R.string.village_name)+" : ");
                    tvVillageName.setText(village_name);
                    displayData_AfterItemSelected();
                }else if(!Objects.equals(town_code, "9999")){
                    Log.d("Data","Urban");
                    tv_V_T_Name.setText(getString(R.string.town_name)+"                : "
                            +"\n"+getString(R.string.ward_name_num)+"    : ");
                    tvVillageName.setText(town_Name+"\n"+ward_Name);
                    display_Urban_Data_AfterItemSelected();
                }
            }
            else if (checkedId == R.id.radioButton_pushed) {
                btnPushToAnotherVA.setVisibility(View.GONE);
                txtListPushed.setVisibility(View.VISIBLE);
                total_regular_Applicants.setVisibility(View.GONE);
                total_pushed_Applicants.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                list_pushed.setVisibility(View.VISIBLE);
                Log.d("radio_Flag:", ""+radio_Flag);
                radioButton_regular.setChecked(false);
                radioButton_pushed.setChecked(true);
                if(!Objects.equals(villageCode, "99999")){
                    Log.d("Data","Rural");
                    tv_V_T_Name.setText(getString(R.string.village_name)+" : ");
                    tvVillageName.setText(village_name);
                    displayRuralPushedData();
                }else if(!Objects.equals(town_code, "9999")){
                    Log.d("Data","Urban");
                    tv_V_T_Name.setText(getString(R.string.town_name)+"                : "
                            +"\n"+getString(R.string.ward_name_num)+"    : ");
                    tvVillageName.setText(town_Name+"\n"+ward_Name);
                    displayUrbanPushedData();
                }
            }
            Log.d("radio_Flag", ""+radio_Flag);
        });

        btnPushToAnotherVA.setOnClickListener(v -> {
            if (selected_items.equals("") || selected_items.size()==0){
                Toast.makeText(getApplicationContext(), getString(R.string.select_any_applicant_to_push), Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor1 = database1.rawQuery("Select distinct "+getString(R.string.village_table_va_circle_name)+","
                        +DataBaseHelperClass_VillageNames_DTH.VCM_va_circle_code+" from "
                        +DataBaseHelperClass_VillageNames_DTH.TABLE_NAME, null);
                if (cursor1.moveToNext()){
                    cursor1.close();
                    Intent intent = new Intent(New_Request_FirstScreen.this, PushToAnotherVA.class);
                    intent.putStringArrayListExtra("selected_items", selected_items);
                    i.putExtra("applicant_Id", applicant_Id);
                    intent.putExtra("district_Code", district_Code);
                    intent.putExtra("taluk_Code", taluk_Code);
                    intent.putExtra("hobli_Code", hobli_Code);
                    intent.putExtra("districtCode", district);
                    intent.putExtra("taluk", taluk);
                    intent.putExtra("hobli", hobli);
                    intent.putExtra("VA_Name", VA_Name);
                    intent.putExtra("va_Circle_Code", va_Circle_Code);
                    intent.putExtra("VA_Circle_Name", VA_Circle_Name);
                    intent.putExtra("strSearchServiceName", service_name);
                    intent.putExtra("strSearchVillageName", village_name);
                    intent.putExtra("serviceCode", serviceCode);
                    intent.putExtra("villageCode", String.valueOf(villageCode));
                    intent.putExtra("habitationCode", habitationCode);
                    intent.putExtra("option_Flag", option_Flag);
                    intent.putExtra("town_Name", town_Name);
                    intent.putExtra("town_code", town_code);
                    intent.putExtra("ward_Name", ward_Name);
                    intent.putExtra("ward_code", ward_code);
                    startActivity(intent);
                    finish();
                } else {
                    if (isNetworkAvailable()) {
                        if (!Objects.equals(villageCode, "99999")) {
                            dialog.show();
                            hashMap_village_name.put("District_Code", district_Code);
                            hashMap_village_name.put("Taluk_Code", taluk_Code);
                            hashMap_village_name.put("Hobli_Code", hobli_Code);
                            Log.d("hashMap_village_name", "" + hashMap_village_name + ", URL:" + getString(R.string.url_village_name_DTH));
                            new GetVillageNameFromServer().execute(getString(R.string.url_village_name_DTH));
                        } else {
                            dialog.dismiss();
                            Intent intent = new Intent(New_Request_FirstScreen.this, PushToAnotherVA.class);
                            intent.putStringArrayListExtra("selected_items", selected_items);
                            intent.putExtra("district_Code", district_Code);
                            intent.putExtra("taluk_Code", taluk_Code);
                            intent.putExtra("hobli_Code", hobli_Code);
                            intent.putExtra("districtCode", district);
                            intent.putExtra("taluk", taluk);
                            intent.putExtra("hobli", hobli);
                            intent.putExtra("VA_Name", VA_Name);
                            intent.putExtra("va_Circle_Code", va_Circle_Code);
                            intent.putExtra("VA_Circle_Name", VA_Circle_Name);
                            intent.putExtra("strSearchServiceName", service_name);
                            intent.putExtra("strSearchVillageName", village_name);
                            intent.putExtra("serviceCode", serviceCode);
                            intent.putExtra("villageCode", String.valueOf(villageCode));
                            intent.putExtra("habitationCode", habitationCode);
                            intent.putExtra("option_Flag", option_Flag);
                            intent.putExtra("town_Name", town_Name);
                            intent.putExtra("town_code", town_code);
                            intent.putExtra("ward_Name", ward_Name);
                            intent.putExtra("ward_code", ward_code);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        buildAlertMessageConnection();
                    }
                }
            }
        });
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

    @SuppressLint("StaticFieldLeak")
    class GetVillageNameFromServer extends AsyncTask<String, Void, JSONObject> {
        JSONObject jsonObject;

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JParserAdv jParserAdv = new JParserAdv();
                jsonObject = jParserAdv.makeHttpRequest(getString(R.string.url_village_name_DTH), "POST", hashMap_village_name);
            }catch (OutOfMemoryError e){
                runOnUiThread(New_Request_FirstScreen.this::buildAlertForOutOfMemory);
                Log.e("OutOfMemoryError", ""+e.toString());
            } catch (NullPointerException e){
                runOnUiThread(() -> dialog.dismiss());
                Log.e("NullPointerException", ""+e.toString());
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {

                //dialog.incrementProgressBy(20);
                openHelper = new DataBaseHelperClass_VillageNames_DTH(New_Request_FirstScreen.this);
                database1 = openHelper.getWritableDatabase();

                JSONArray array = jsonObject.getJSONArray("data");

                truncateDatabase_village_name();

                int count = array.length();
                for (int i = 0; i < count; i++) {

                    JSONObject object = array.getJSONObject(i);

                    set_and_get_village_name = new Set_and_Get_Village_Name();
                    set_and_get_village_name.setVCM_va_circle_code(object.getString(DataBaseHelperClass_VillageNames_DTH.VCM_va_circle_code));
                    set_and_get_village_name.setVCM_va_circle_ename(object.getString(DataBaseHelperClass_VillageNames_DTH.VCM_va_circle_ename));
                    set_and_get_village_name.setVCM_va_circle_kname(object.getString(DataBaseHelperClass_VillageNames_DTH.VCM_va_circle_kname));
                    set_and_get_village_name.setHM_village_code(object.getString(DataBaseHelperClass_VillageNames_DTH.HM_village_code));
                    set_and_get_village_name.setHM_habitation_code(object.getString(DataBaseHelperClass_VillageNames_DTH.HM_habitation_code));
                    set_and_get_village_name.setHM_habitation_ename(object.getString(DataBaseHelperClass_VillageNames_DTH.HM_habitation_ename));
                    set_and_get_village_name.setHM_habitation_kname(object.getString(DataBaseHelperClass_VillageNames_DTH.HM_habitation_kname));


                    database1.execSQL("insert into " + DataBaseHelperClass_VillageNames_DTH.TABLE_NAME
                            + "(VCM_va_circle_code,VCM_va_circle_ename, VCM_va_circle_kname,HM_village_code, HM_habitation_code, HM_habitation_ename, HM_habitation_kname) values ("
                            + set_and_get_village_name.getVCM_va_circle_code() +",'"+set_and_get_village_name.getVCM_va_circle_ename()+"','"+set_and_get_village_name.getVCM_va_circle_kname()+"',"
                            + set_and_get_village_name.getHM_village_code() +","+ set_and_get_village_name.getHM_habitation_code()+",'"+ set_and_get_village_name.getHM_habitation_ename()+"','"
                            + set_and_get_village_name.getHM_habitation_kname()+"')");
                    Log.d("Database", "VillageNames Database Inserted");
                    if (i==count-1){
                        dialog.dismiss();
                        Intent intent = new Intent(New_Request_FirstScreen.this, PushToAnotherVA.class);
                        intent.putStringArrayListExtra("selected_items", selected_items);
                        intent.putExtra("district_Code", district_Code);
                        intent.putExtra("taluk_Code", taluk_Code);
                        intent.putExtra("hobli_Code", hobli_Code);
                        intent.putExtra("districtCode", district);
                        intent.putExtra("taluk", taluk);
                        intent.putExtra("hobli", hobli);
                        intent.putExtra("VA_Name", VA_Name);
                        intent.putExtra("va_Circle_Code", va_Circle_Code);
                        intent.putExtra("VA_Circle_Name", VA_Circle_Name);
                        intent.putExtra("strSearchServiceName", service_name);
                        intent.putExtra("strSearchVillageName", village_name);
                        intent.putExtra("serviceCode", serviceCode);
                        intent.putExtra("villageCode", String.valueOf(villageCode));
                        intent.putExtra("habitationCode", habitationCode);
                        intent.putExtra("option_Flag", option_Flag);
                        intent.putExtra("town_Name", town_Name);
                        intent.putExtra("town_code", town_code);
                        intent.putExtra("ward_Name", ward_Name);
                        intent.putExtra("ward_code", ward_code);
                        startActivity(intent);
                        finish();
                    }
                }
            } catch (JSONException e) {
                dialog.dismiss();
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
            }catch (OutOfMemoryError e){
                dialog.dismiss();
                Log.e("OutOfMemoryError", ""+e.toString());
            }catch (NullPointerException e){
                dialog.dismiss();
                Log.e("NullPointerException", ""+e.toString());
            }
            //Toast.makeText(getApplicationContext(), "VillageNames Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void truncateDatabase_village_name(){
        //dialog.incrementProgressBy(10);
        openHelper = new DataBaseHelperClass_VillageNames_DTH(New_Request_FirstScreen.this);
        database1 = openHelper.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = database1.rawQuery("select * from "+ DataBaseHelperClass_VillageNames_DTH.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database1.execSQL("Delete from " + DataBaseHelperClass_VillageNames_DTH.TABLE_NAME);
            Log.d("Database", "VillageNames Database Truncated");
        }

    }

    private  void buildAlertForOutOfMemory() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.memory_full))
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    super.onBackPressed();
                    finish();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void displayRuralPushedData(){
        int i=1;
        Log.d("InDisplay", ""+ i);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor1 = database.rawQuery("select * from "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "="+villageCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code+"="+habitationCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"=9999 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"=255 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+" ='1'", null);

        SlNo1.clear();
        Applicant_Name1.clear();
        GSC_FirstPart1.clear();
        Applicant_ID1.clear();
        DueDate1.clear();
        ServiceCode1.clear();
        ServiceName1.clear();
        VillageCode1.clear();
        HabitationCode1.clear();
        Option_Flag1.clear();
        PushedList.clear();

        if(cursor1.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            txtListPushed.setVisibility(View.VISIBLE);
            if (cursor1.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo1.add(String.valueOf(i));
                    Applicant_Name1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                    GSC_FirstPart1.add(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                    Applicant_ID1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                    DueDate1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date)));
                    ServiceCode1.add(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code)));
                    ServiceName1.add(service_name);
                    VillageCode1.add(villageCode);
                    HabitationCode1.add(habitationCode);
                    Option_Flag1.add(option_Flag);
                    PushedList.add("1");
                    i++;
                } while (cursor1.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter_pushed = new Service_List_Adapter(New_Request_FirstScreen.this, SlNo1, Applicant_Name1,
                    GSC_FirstPart1, Applicant_ID1, DueDate1, ServiceCode1, ServiceName1, VillageCode1, HabitationCode1,
                    Option_Flag1, PushedList, New_Request_FirstScreen.this, this);
            list_pushed.setAdapter(list_adapter_pushed);
            //Toast.makeText(getApplicationContext(), "Rural Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor1.close();
            emptyTxt.setVisibility(View.VISIBLE);
            emptyTxt.setText(getString(R.string.no_da_found));
            txtListPushed.setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayData_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "="+villageCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code+"="+habitationCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"=9999 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"=255 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and ("
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+" is null or "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+"='')", null);

        SlNo.clear();
        Applicant_Name.clear();
        GSC_FirstPart.clear();
        Applicant_ID.clear();
        DueDate.clear();
        ServiceCode.clear();
        ServiceName.clear();
        VillageCode.clear();
        HabitationCode.clear();
        Option_Flag.clear();
        PushedList.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Applicant_Name.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                    GSC_FirstPart.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                    Applicant_ID.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                    DueDate.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date)));
                    ServiceCode.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code)));
                    ServiceName.add(service_name);
                    VillageCode.add(villageCode);
                    HabitationCode.add(habitationCode);
                    Option_Flag.add(option_Flag);
                    PushedList.add("0");
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter = new Service_List_Adapter(New_Request_FirstScreen.this, SlNo, Applicant_Name, GSC_FirstPart,
                    Applicant_ID, DueDate, ServiceCode, ServiceName, VillageCode, HabitationCode,
                    Option_Flag, PushedList, New_Request_FirstScreen.this, this);
            listView.setAdapter(list_adapter);
            //Toast.makeText(getApplicationContext(), "Rural Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.close();
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayUrbanPushedData(){
        int i=1;
        Log.d("InDisplay", ""+ i);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code +"="+town_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code + "="+ward_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=99999 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+" ='1'", null);

        SlNo1.clear();
        Applicant_Name1.clear();
        GSC_FirstPart1.clear();
        Applicant_ID1.clear();
        DueDate1.clear();
        ServiceCode1.clear();
        ServiceName1.clear();
        TownName1.clear();
        TownCode1.clear();
        WardName1.clear();
        WardCode1.clear();
        Option_Flag1.clear();
        PushedList.clear();

        if(cursor1.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            txtListPushed.setVisibility(View.VISIBLE);
            if (cursor1.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo1.add(String.valueOf(i));
                    Applicant_Name1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                    GSC_FirstPart1.add(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                    Applicant_ID1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                    DueDate1.add(cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date)));
                    ServiceCode1.add(cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code)));
                    ServiceName1.add(service_name);
                    TownName1.add(town_Name);
                    TownCode1.add(town_code);
                    WardName1.add(ward_Name);
                    WardCode1.add(ward_code);
                    Option_Flag1.add(option_Flag);
                    PushedList.add("1");
                    i++;
                } while (cursor1.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            ur_service_list_adapter_pushed = new UR_Service_List_Adapter(New_Request_FirstScreen.this, SlNo1, Applicant_Name1,
                    GSC_FirstPart1, Applicant_ID1, DueDate1, ServiceCode1, ServiceName1, TownName1, TownCode1, WardName1,
                    WardCode1, Option_Flag1, PushedList, New_Request_FirstScreen.this, this);
            list_pushed.setAdapter(ur_service_list_adapter_pushed);
            //Toast.makeText(getApplicationContext(), "Urban Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor1.close();
            emptyTxt.setVisibility(View.VISIBLE);
            emptyTxt.setText(getString(R.string.no_da_found));
            txtListPushed.setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    public void display_Urban_Data_AfterItemSelected() {
        int i=1;
        Log.d("InDisplay", ""+ i);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code +"="+town_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code + "="+ward_code+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + "=99999 and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"="+serviceCode+" and "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null and ("
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+" is null or "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag+"='')", null);

        SlNo.clear();
        Applicant_Name.clear();
        GSC_FirstPart.clear();
        Applicant_ID.clear();
        DueDate.clear();
        ServiceCode.clear();
        ServiceName.clear();
        TownName.clear();
        TownCode.clear();
        WardName.clear();
        WardCode.clear();
        Option_Flag.clear();
        PushedList.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Applicant_Name.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name)));
                    GSC_FirstPart.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCFirstPart_Name)));
                    Applicant_ID.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No)));
                    DueDate.add(cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date)));
                    ServiceCode.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code)));
                    ServiceName.add(service_name);
                    TownName.add(town_Name);
                    TownCode.add(town_code);
                    WardName.add(ward_Name);
                    WardCode.add(ward_code);
                    Option_Flag.add(option_Flag);
                    PushedList.add("0");
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            ur_service_list_adapter = new UR_Service_List_Adapter(New_Request_FirstScreen.this, SlNo, Applicant_Name,
                    GSC_FirstPart, Applicant_ID, DueDate, ServiceCode, ServiceName, TownName, TownCode, WardName, WardCode,
                    Option_Flag, PushedList, New_Request_FirstScreen.this, this);
            listView.setAdapter(ur_service_list_adapter);
            //Toast.makeText(getApplicationContext(), "Urban Data Displayed Successfully", Toast.LENGTH_SHORT).show();
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
        Intent i = new Intent(New_Request_FirstScreen.this, Field_Report.class);
        i.putExtra("district_Code", district_Code);
        i.putExtra("districtCode", district);
        i.putExtra("taluk_Code", taluk_Code);
        i.putExtra("taluk", taluk);
        i.putExtra("hobli_Code", hobli_Code);
        i.putExtra("hobli", hobli);
        i.putExtra("va_Circle_Code", va_Circle_Code);
        i.putExtra("VA_Circle_Name", VA_Circle_Name);
        i.putExtra("VA_Name", VA_Name);
        i.putExtra("strSearchServiceName", service_name);
        i.putExtra("villageCode", villageCode);
        i.putExtra("habitationCode",habitationCode);
        i.putExtra("strSearchVillageName", village_name);
        i.putExtra("town_Name", town_Name);
        i.putExtra("town_code", town_code);
        i.putExtra("ward_Name", ward_Name);
        i.putExtra("ward_code", ward_code);
        i.putExtra("option_Flag", option_Flag);
        startActivity(i);
        finish();
    }

    @Override
    public void setValues(ArrayList<String> al) {
        selected_items = al;
        Log.d("Activity_selected",""+selected_items);
        Log.d("Activity_selectedList",""+selected_items.size());
    }

    @SuppressLint("StaticFieldLeak")
    class GetServiceTranDataFromServer extends AsyncTask<String, Integer, JSONObject> {
        JSONObject jsonObject;

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JParserAdv jParserAdv = new JParserAdv();
                jsonObject = jParserAdv.makeHttpRequest(getString(R.string.url_service_Tran_data), "POST", hashMap_service_Tran_data);

            }catch (OutOfMemoryError e){
                Log.e("OutOfMemoryError1", ""+e.toString());
                runOnUiThread(() -> {
                    dialog.dismiss();
                    buildAlertForOutOfMemory();
                });
            }catch (NullPointerException e){
                dialog.dismiss();
                runOnUiThread(() -> dialog.dismiss());
                Log.e("NullPointerException1", ""+e.toString());
            }
            return jsonObject;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                JSONArray array = jsonObject.getJSONArray("data");

                int count = array.length();
                truncateDatabase_Service_Tran_data();

                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
                database = openHelper.getWritableDatabase();

                openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(New_Request_FirstScreen.this);
                database1 = openHelper.getWritableDatabase();

                for (int i = 0; i < count; i++) {

                    JSONObject object = array.getJSONObject(i);

                    set_and_get_service_tran_data = new Set_and_Get_Service_tran_data();
                    set_and_get_service_tran_data.setDistrict_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code));
                    set_and_get_service_tran_data.setTaluk_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code));
                    set_and_get_service_tran_data.setHobli_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code));
                    set_and_get_service_tran_data.setVillage_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code));
                    set_and_get_service_tran_data.setHabitation_code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code));
                    set_and_get_service_tran_data.setTown_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code));
                    set_and_get_service_tran_data.setWard_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code));
                    set_and_get_service_tran_data.setService_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code));
                    set_and_get_service_tran_data.setST_applicant_photo(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_photo));
                    set_and_get_service_tran_data.setRD_No(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No));
                    set_and_get_service_tran_data.setApplicant_Name(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name));
                    set_and_get_service_tran_data.setDue_Date(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date));
                    set_and_get_service_tran_data.setRaised_Location(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Raised_Location));
                    set_and_get_service_tran_data.setFather_Name(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name));
                    set_and_get_service_tran_data.setMother(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Mother));
                    set_and_get_service_tran_data.setRationCard_No(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.RationCard_No));
                    set_and_get_service_tran_data.setAadhar_NO(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Aadhar_NO));
                    set_and_get_service_tran_data.setMobile_No(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Mobile_No));
                    set_and_get_service_tran_data.setAddress1(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Address1));
                    set_and_get_service_tran_data.setAddress2(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Address2));
                    set_and_get_service_tran_data.setAddress3(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Address3));
                    set_and_get_service_tran_data.setAdd_Pin(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_cadd_pin));
                    set_and_get_service_tran_data.setST_ID_TYPE(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ID_TYPE));
                    set_and_get_service_tran_data.setEng_Certify(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate));
                    set_and_get_service_tran_data.setGSC_First_Part(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_GSCFirstPart));
                    set_and_get_service_tran_data.setCST_res_category(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.CST_res_category));
                    set_and_get_service_tran_data.setCST_caste_as_per_app(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.CST_caste_as_per_app));
                    set_and_get_service_tran_data.setCST_annual_income(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.CST_annual_income));
                    set_and_get_service_tran_data.setSCOT_caste_app(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.SCOT_caste_app));
                    set_and_get_service_tran_data.setSCOT_annual_income(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.SCOT_annual_income));
                    set_and_get_service_tran_data.setGST_No_Years_Applied(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.GST_No_Years_Applied));
                    set_and_get_service_tran_data.setGST_No_Mths_Applied(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.GST_No_Mths_Applied));
                    set_and_get_service_tran_data.setST_Push_Flag(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Push_Flag));

                    serviceCode_DB = object.getInt(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code);

                    if(serviceCode_DB==6){
                        serviceName = "Caste and Income Certficate";
                        serviceName_k = "ಜಾತಿ ಮತ್ತು ಆದಾಯ ಪ್ರಮಾಣ ಪತ್ರ ";
                    }else if(serviceCode_DB==7){
                        serviceName="Caste Certificate (Cat-A)";
                        serviceName_k = "ಜಾತಿ ಪ್ರಮಾಣ ಪತ್ರ (ಪ್ರವರ್ಗ-ಎ)";
                    }else if(serviceCode_DB==8){
                        serviceName = "Caste Certificate (SC/ST)";
                        serviceName_k ="ಜಾತಿ ಪ್ರಮಾಣ ಪತ್ರ (ಅ.ಜಾ/ಅ.ಪಂ)";
                    }else if(serviceCode_DB==9){
                        serviceName = "OBC Certificate (Central)";
                        serviceName_k= "ಹಿಂದುಳಿದ ವರ್ಗ ಪ್ರ.ಪತ್ರ (ಕೇ.ಸ)";
                    }else if (serviceCode_DB==10){
                        serviceName = "Residence Certificate";
                        serviceName_k = "ವಾಸ ಸ್ಥಳ ಪ್ರಮಾಣ ಪತ್ರ";
                    }else {
                        serviceName=null;
                        serviceName_k=null;
                    }
                    Log.d("serviceName", serviceName+", "+ serviceName_k);
                    set_and_get_service_tran_data.setService_Name(serviceName);
                    set_and_get_service_tran_data.setService_Name_k(serviceName_k);

                    gsc_firstPart = set_and_get_service_tran_data.getGSC_First_Part();
                    if(Objects.equals(gsc_firstPart, "3")){
                        gsc_firstPart_Name = "RD003";
                    }else if(Objects.equals(gsc_firstPart, "501")){
                        gsc_firstPart_Name="RD501";
                    }else {
                        gsc_firstPart_Name=null;
                    }
//                    if(j<=1000) {
                    database.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                            + "(ST_district_code, ST_taluk_code, ST_hobli_code, ST_village_code, ST_habitation_code, ST_town_code, ST_ward_no, ST_facility_code, Service_Name, Service_Name_k, ST_GSC_No, ST_applicant_photo, ST_GSCFirstPart, GSCFirstPart_Name" +
                            ", ST_applicant_name, ST_DueDate, ST_Raised_Location, ST_father_name, ST_mother_name, ST_ID_NUMBER, ST_mobile_no, ST_applicant_caddress1, ST_applicant_caddress2, ST_applicant_caddress3,ST_applicant_cadd_pin, ST_ID_TYPE, ST_Eng_Certificate,"
                            + "CST_res_category, CST_caste_as_per_app, CST_annual_income, SCOT_caste_app, SCOT_annual_income, GST_No_Years_Applied, GST_No_Mths_Applied, ST_Push_Flag)"
                            + " values (" + set_and_get_service_tran_data.getDistrict_Code() + ","
                            + set_and_get_service_tran_data.getTaluk_Code() + ","
                            + set_and_get_service_tran_data.getHobli_Code()
                            + "," + set_and_get_service_tran_data.getVillage_Code() + ","
                            + set_and_get_service_tran_data.getHabitation_code()+","
                            + set_and_get_service_tran_data.getTown_Code()+","
                            + set_and_get_service_tran_data.getWard_Code()+","
                            + set_and_get_service_tran_data.getService_Code() + ",'"
                            + set_and_get_service_tran_data.getService_Name() + "','"
                            + set_and_get_service_tran_data.getService_Name_k() + "',"
                            + set_and_get_service_tran_data.getRD_No()+",'"
                            +set_and_get_service_tran_data.getST_applicant_photo()+"',"
                            + set_and_get_service_tran_data.getGSC_First_Part() +",'"
                            + gsc_firstPart_Name + "','"
                            + set_and_get_service_tran_data.getApplicant_Name() + "','"
                            + set_and_get_service_tran_data.getDue_Date() + "','"
                            + set_and_get_service_tran_data.getRaised_Location() + "','"
                            + set_and_get_service_tran_data.getFather_Name() + "','"
                            + set_and_get_service_tran_data.getMother() + "','"
                            + set_and_get_service_tran_data.getRationCard_No() + "',"
                            + set_and_get_service_tran_data.getMobile_No() + ",'"
                            + set_and_get_service_tran_data.getAddress1() + "','"
                            + set_and_get_service_tran_data.getAddress2() + "','"
                            + set_and_get_service_tran_data.getAddress3() +"','"
                            + set_and_get_service_tran_data.getAdd_Pin()+"',"
                            + set_and_get_service_tran_data.getST_ID_TYPE()+",'"
                            + set_and_get_service_tran_data.getEng_Certify()+"','"
                            + set_and_get_service_tran_data.getCST_res_category()+"','"
                            +set_and_get_service_tran_data.getCST_caste_as_per_app() + "','"
                            + set_and_get_service_tran_data.getCST_annual_income()+"','"
                            +set_and_get_service_tran_data.getSCOT_caste_app()+"','"
                            +set_and_get_service_tran_data.getSCOT_annual_income() + "','"
                            +set_and_get_service_tran_data.getGST_No_Years_Applied()+"','"
                            +set_and_get_service_tran_data.getGST_No_Mths_Applied()+"','"
                            +set_and_get_service_tran_data.getST_Push_Flag()+"')");

                    Log.d("Database", "ServiceTranTable Database Inserted " + j);
                    j++;

                }
                runOnUiThread(() -> {
                    openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
                    database = openHelper.getWritableDatabase();
                    Cursor cursor3 = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME, null);
                    if(cursor3.getCount()>0) {
                        dialog.dismiss();
                        radioButton_regular.setChecked(true);
                        radioButton_pushed.setChecked(false);
                        btnPushToAnotherVA.setVisibility(View.VISIBLE);
                        txtListPushed.setVisibility(View.GONE);
                        total_regular_Applicants.setVisibility(View.VISIBLE);
                        total_pushed_Applicants.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        list_pushed.setVisibility(View.GONE);
                        Log.d("radio_Flag:", "" + radio_Flag);
                        if(!Objects.equals(villageCode, "99999")){
                            Log.d("Data","Rural");
                            tv_V_T_Name.setText(getString(R.string.village_name)+" : ");
                            tvVillageName.setText(village_name);
                            displayData_AfterItemSelected();
                        }else if(!Objects.equals(town_code, "9999")){
                            Log.d("Data","Urban");
                            tv_V_T_Name.setText(getString(R.string.town_name)+"                : "
                                    +"\n"+getString(R.string.ward_name_num)+"    : ");
                            tvVillageName.setText(town_Name+"\n"+ward_Name);
                            display_Urban_Data_AfterItemSelected();
                        }
                    }
                    else {
                        cursor3.close();
                        Log.d("Values", "No records Exists");
                        Toast.makeText(getApplicationContext(), R.string.no_data_to_verify, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                });
            }catch (OutOfMemoryError e){
                runOnUiThread(() -> {
                    dialog.dismiss();
                    buildAlertForOutOfMemory();
                    //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                });
                Log.e("OutOfMemoryError2", ""+e.toString());
            }catch (NullPointerException e){
                Log.e("NullPointerException2", ""+e.toString());
            }
        }

    }

    public void truncateDatabase_Service_Tran_data(){
        //dialog.incrementProgressBy(10);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_FirstScreen.this);
        database = openHelper.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME);
            Log.d("Database", "ServiceTranTable Database Truncated");
        }

    }
}
