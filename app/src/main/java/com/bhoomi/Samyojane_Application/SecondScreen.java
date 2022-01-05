package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface_NIC;
import com.bhoomi.Samyojane_Application.api.APIInterface_SamyojaneAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondScreen extends AppCompatActivity {

    TextView proceed, tvDistrict, tvTaluk, tvHobli, tvVA_Circle_Name, tvVA_Name, tvTimerValue, VAModule;
    Button btnDownload, btnPendency, btnUpload, btnProceed;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase databaseServTran, databaseFacility, databaseCredential, databaseVilNames;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    int district_Code, taluk_Code, hobli_Code, va_Circle_Code;
    Set_and_Get_Facility_Services set_and_get_facility_services;
    Set_and_Get_Village_Name set_and_get_village_name;
    Set_and_Get_Service_tran_data set_and_get_service_tran_data;
    int fData=0, vData=0, tData=0;
    ProgressDialog dialog;
    GPSTracker gpsTracker;
    double latitude, longitude;
    int flag;
    int serviceCode;
    String serviceName, serviceName_k;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;
    int j=1;
    AlertDialog alert;
    String localeName;

    private long startTime = 0L;
    private final Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    String IMEI_Num, mob_Num, uName_get;
    APIInterface_SamyojaneAPI apiInterface_samyojaneAPI;
    APIInterface_NIC apiInterface_nic;
    SharedPreferences sharedPreferences;
    int DesiCode;

    @SuppressLint("SetTextI18n")
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.second_screen);

            proceed = findViewById(R.id.proceed);
            VAModule = findViewById(R.id.VAModule);
            proceed.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            VAModule.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            btnDownload = findViewById(R.id.btnDownload);
            btnPendency = findViewById(R.id.btnPendency);
            tvDistrict = findViewById(R.id.district);
            tvTaluk = findViewById(R.id.taluk);
            tvHobli = findViewById(R.id.tv_hobli);
            tvVA_Circle_Name = findViewById(R.id.tvVA_Circle);
            tvVA_Name = findViewById(R.id.VA_name);
            btnUpload = findViewById(R.id.btnUploadScreen);
            tvTimerValue = findViewById(R.id.tvTimerValue);
            btnProceed = findViewById(R.id.btnProceed);

            btnProceed.setOnClickListener(v -> {
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                ims = imm.getCurrentInputMethodSubtype();
                String locale = ims.getLocale();
                Locale locale2 = new Locale(locale);
                String currentLanguage = locale2.getDisplayLanguage();
                Log.d("lang:", "" + currentLanguage);
                if (!Objects.equals(currentLanguage, "kn_in")) {
                    return_Value = searchPackage();
                    Log.d("return_Value", "" +return_Value);
                    if(!return_Value){
                        buildAlertMessage();
                    }else {
                        Intent i = new Intent(SecondScreen.this, Field_Report.class);
                        i.putExtra("district_Code", district_Code);
                        i.putExtra("district", district);
                        i.putExtra("taluk_Code", taluk_Code);
                        i.putExtra("taluk", taluk);
                        i.putExtra("hobli_Code", hobli_Code);
                        i.putExtra("hobli", hobli);
                        i.putExtra("va_Circle_Code", va_Circle_Code);
                        i.putExtra("VA_Circle_Name",VA_Circle_Name );
                        i.putExtra("VA_Name", VA_Name);
                        startActivity(i);
                        //imm.showInputMethodPicker();
                    }
                }
                else {
                    Intent i = new Intent(SecondScreen.this, Field_Report.class);
                    i.putExtra("district_Code", district_Code);
                    i.putExtra("district", district);
                    i.putExtra("taluk_Code", taluk_Code);
                    i.putExtra("taluk", taluk);
                    i.putExtra("hobli_Code", hobli_Code);
                    i.putExtra("hobli", hobli);
                    i.putExtra("va_Circle_Code", va_Circle_Code);
                    i.putExtra("VA_Circle_Name",VA_Circle_Name );
                    i.putExtra("VA_Name", VA_Name);
                    startActivity(i);
                }
            });

            btnPendency.setOnClickListener(view -> {
                Intent i = new Intent(SecondScreen.this, Village_wise_report.class);
                i.putExtra("district_Code", district_Code);
                i.putExtra("district", district);
                i.putExtra("taluk_Code", taluk_Code);
                i.putExtra("taluk", taluk);
                i.putExtra("hobli_Code", hobli_Code);
                i.putExtra("hobli", hobli);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Circle_Name",VA_Circle_Name );
                i.putExtra("VA_Name", VA_Name);
                startActivity(i);
            });

            //btnProceed.setVisibility(View.GONE);
            //btnPendency.setVisibility(View.GONE);

            gpsTracker = new GPSTracker(getApplicationContext(), this);
            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                Log.d("Location", latitude+""+longitude);
            } else {
                buildAlertMessageNoGps();
            }

            openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(SecondScreen.this);
            databaseServTran = openHelper.getWritableDatabase();

            Cursor cursor12 = databaseServTran.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                    +" where "+DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null",null);
            if (cursor12.getCount()>0){
                btnProceed.setVisibility(View.VISIBLE);
            }else {
                cursor12.close();
                btnProceed.setVisibility(View.GONE);
            }

            sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            DesiCode = sharedPreferences.getInt(Constants.DesiCode_VA, 22);
            uName_get = sharedPreferences.getString(Constants.uName_get, "");

            Intent i = getIntent();
            district = i.getStringExtra("district");
            taluk = i.getStringExtra("taluk");
            hobli = i.getStringExtra("hobli");
            VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
            va_Circle_Code = Integer.parseInt(i.getStringExtra("va_Circle_Code"));
            VA_Name = i.getStringExtra("VA_Name");
            localeName = i.getStringExtra("localeName");
            IMEI_Num = i.getStringExtra("IMEI_Num");
            mob_Num = i.getStringExtra("mob_Num");

            Log.d("Second_Database_Value", ""+district);
            Log.d("Second_Database_Value", ""+taluk);
            Log.d("Second_Database_Value", ""+hobli);
            Log.d("Second_Database_Value", ""+VA_Circle_Name);
            Log.d("Second_Database_Value",""+va_Circle_Code);
            Log.d("Second_Database_Value", ""+VA_Name);
            Log.d("Second_Database_Value",""+localeName);
            Log.d("Second_DB_Val",""+getString(R.string.cre_district_name)
                    +","+getString(R.string.cre_taluk_name)
                    +","+getString(R.string.cre_hobli_name)
                    +","+getString(R.string.cre_va_circle_name));
            Log.d("IMEI_Num", ""+IMEI_Num);
            Log.d("mob_Num", ""+mob_Num);

            dialog = new ProgressDialog(SecondScreen.this, R.style.CustomDialog);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage(getString(R.string.downloading_please_wait));
            dialog.setIndeterminate(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMax(100);

            openHelper = new DataBaseHelperClass_VillageNames(SecondScreen.this);
            databaseVilNames = openHelper.getWritableDatabase();

            openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(SecondScreen.this);
            databaseFacility = openHelper.getWritableDatabase();

            openHelper = new DataBaseHelperClass_Credentials(SecondScreen.this);
            databaseCredential = openHelper.getWritableDatabase();

            final Cursor cursor = databaseCredential.rawQuery("select * from "+ DataBaseHelperClass_Credentials.TABLE_NAME+" where "
                    + getString(R.string.cre_district_name)+"='"+district+"' and "
                    + getString(R.string.cre_taluk_name)+"='"+taluk+"' and "
                    + getString(R.string.cre_hobli_name)+"='"+hobli+"' and "
                    + getString(R.string.cre_va_circle_name)+"='"+VA_Circle_Name+"'", null);
            if(cursor.getCount()>0) {
                if (cursor.moveToNext()) {
                    district_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_Credentials.District_Code));
                    taluk_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Code));
                    hobli_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Code));
                    va_Circle_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_Credentials.VA_circle_Code));
                    flag = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.flag));
                    databaseCredential.close();
                }
            }else {
                cursor.close();
                databaseCredential.close();
            }

            Log.d("VA_Circle_Code", ""+va_Circle_Code);
            Log.d("SecondScreen", ""+district_Code);
            Log.d("SecondScreen", ""+taluk_Code);
            Log.d("SecondScreen", ""+hobli_Code);

            tvDistrict.setText(": "+district);
            tvTaluk.setText(": "+taluk);
            tvHobli.setText(": "+hobli);
            tvVA_Circle_Name.setText(": "+VA_Circle_Name);
            tvVA_Name.setText(": "+VA_Name);

            btnDownload.setOnClickListener(v -> {
                startTime = 0L;
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updatedTime = 0L;
                tvTimerValue.setText("00:00:00");

                if (isNetworkAvailable()) {
                    dialog.show();
                    dialog.setProgress(0);

                    btnDownload.setText(R.string.downloading);
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);

                    //new UpdateServiceTranTable_Server().execute();

    //                new InsertServiceParameterTable_Server().execute();

                    GetFacilityServiceFromServer();
                }
                else {
                    Cursor cursor1= databaseFacility.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME, null);

                    if (cursor1.getCount()>0){
                        fData=1;

                        Cursor cursor2 = databaseVilNames.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME, null);

                        if(cursor2.getCount()>0) {
                            vData=1;

                            Cursor cursor3 = databaseServTran.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME, null);

                            if(cursor3.getCount()>0) {
                                tData=1;
                                btnProceed.setVisibility(View.VISIBLE);
                                //btnPendency.setVisibility(View.VISIBLE);
                                //Toast.makeText(getApplicationContext(), "Data already exist", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                cursor3.close();
                                tData=0;
                                Log.d("Values", "No records Exists");
                                btnProceed.setVisibility(View.GONE);
                                //btnPendency.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            cursor2.close();
                            vData=0;
                        }
                        databaseServTran.close();
                    } else {
                        fData=0;
                        cursor1.close();
                        databaseServTran.close();
                        Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
                    }

                }

            });

            btnUpload.setOnClickListener(v -> {
                Intent i1 = new Intent(SecondScreen.this, UploadScreen.class);
                i1.putExtra("IMEI_Num", ""+IMEI_Num);
                i1.putExtra("mob_Num",""+mob_Num);
                i1.putExtra("VA_Name", ""+VA_Name);
                startActivity(i1);
            });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean searchPackage(){

        InputMethodInfo inputMethodInfo;
        PackageManager packageManager = getPackageManager();
        List<InputMethodInfo> str;
        str = imm.getEnabledInputMethodList();
        System.out.println(str);
        List<String> list = new ArrayList<>();

        for (int i = 0; i < str.size(); i++) {
            System.out.println(str.get(i));
            inputMethodInfo = imm.getEnabledInputMethodList().get(i);
            String str2 = inputMethodInfo.loadLabel(packageManager).toString();
            Log.d("Package_List", str2);
            list.add(str2);
        }
        Log.d("Print_List", String.valueOf(list));

        boolean get = false;

        for(String s1 : list){
            if (s1.contains("Samyojane")) {
                get = true;
                break;
            }
        }
        Log.d("search", String.valueOf(get));
        return get;

    }

    private  void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.gpe_is_off))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.want_to_exit))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    alert.dismiss();
                    SecondScreen.super.onBackPressed();
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
        alert = builder.create();
        alert.show();
    }

    private  void buildAlertMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.keyboard_language))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)))
                .setNegativeButton(R.string.no, (dialog, id) -> buildAlert());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private  void buildAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.must_change_keyboard))
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, id) -> startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)));
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private  void buildAlertForOutOfMemory() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.memory_full))
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    SecondScreen.super.onBackPressed();
                    finish();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void truncateDatabase_facility(){
        dialog.incrementProgressBy(5);
        Cursor cursor = databaseFacility.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            databaseFacility.execSQL("Delete from " + DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME);
            Log.d("Database", "FacilityMaster Database Truncated");
        }else {
            cursor.close();
        }

    }

    public void GetFacilityServiceFromServer(){
        apiInterface_samyojaneAPI = APIClient.getClient(getString(R.string.samyojane_API_url)).create(APIInterface_SamyojaneAPI.class);

        Call<String> call = apiInterface_samyojaneAPI.doFn_Get_Facility_Services(getString(R.string.samyojane_api_flag1),getString(R.string.samyojane_api_flag2));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String response_server = response.body();
                    Log.d("response_server", response_server + "");
                    try {

                        dialog.incrementProgressBy(5);

                        JSONObject jsonObject = new JSONObject(response_server);
                        JSONArray array = jsonObject.getJSONArray("data");

                        truncateDatabase_facility();

                        int count = array.length();

                        for (int i = 0; i < count; i++) {

                            JSONObject object = array.getJSONObject(i);

                            set_and_get_facility_services = new Set_and_Get_Facility_Services();
                            set_and_get_facility_services.setSlNo(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.SlNo));
                            set_and_get_facility_services.setFM_facility_code(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code));
                            set_and_get_facility_services.setFM_facility_edesc(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_edesc));
                            set_and_get_facility_services.setFM_facility_kdesc(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_kdesc));
                            set_and_get_facility_services.setFM_acronym_on_doc_eng(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_acronym_on_doc_eng));
                            set_and_get_facility_services.setFM_designated_officer(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_designated_officer));
                            set_and_get_facility_services.setFM_gsc_no_days(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_gsc_no_days));
                            set_and_get_facility_services.setFM_facility_display(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_display));
                            set_and_get_facility_services.setFM_sakala_service(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_sakala_service));
                            set_and_get_facility_services.setFM_OTC_Charges(object.getString(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_OTC_Charges));


                            databaseFacility.execSQL("insert into " + DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME + "(SlNo, FM_facility_code, FM_facility_edesc, FM_facility_kdesc, FM_acronym_on_doc_eng, FM_designated_officer" +
                                    ", FM_gsc_no_days, FM_facility_display, FM_sakala_service, FM_OTC_Charges) values (" + set_and_get_facility_services.getSlNo() + ",'" + set_and_get_facility_services.getFM_facility_code()
                                    + "','" + set_and_get_facility_services.getFM_facility_edesc() + "','" + set_and_get_facility_services.getFM_facility_kdesc() + "','" + set_and_get_facility_services.getFM_acronym_on_doc_eng() + "','" + set_and_get_facility_services.getFM_designated_officer()
                                    + "','" + set_and_get_facility_services.getFM_gsc_no_days() + "','" + set_and_get_facility_services.getFM_facility_display() + "','" + set_and_get_facility_services.getFM_sakala_service()
                                    + "','" + set_and_get_facility_services.getFM_OTC_Charges() + "')");
                            Log.d("Database", "FacilityMaster Database Inserted");

                        }
                        dialog.incrementProgressBy(2);
                        GetVillageNameFromServer(district_Code, taluk_Code, hobli_Code, va_Circle_Code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                        Log.e("OutOfMemoryError", "" + e.toString());
                    } catch (NullPointerException e) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                        e.printStackTrace();
                        Log.e("NullPointerException", "" + e.toString());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("multipleResource",""+t.getMessage());
                call.cancel();
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void truncateDatabase_village_name(){
        dialog.incrementProgressBy(11);

        Cursor cursor = databaseVilNames.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            databaseVilNames.execSQL("Delete from " + DataBaseHelperClass_VillageNames.TABLE_NAME);
            Log.d("Database", "VillageNames Database Truncated");
        }else {
            cursor.close();}

    }

    public void GetVillageNameFromServer(int district_Code, int taluk_Code, int hobli_Code, int va_Circle_Code){
        apiInterface_samyojaneAPI = APIClient.getClient(getString(R.string.samyojane_API_url)).create(APIInterface_SamyojaneAPI.class);

        Call<String> call = apiInterface_samyojaneAPI.doFn_Get_Village_Name(getString(R.string.samyojane_api_flag1),getString(R.string.samyojane_api_flag2), district_Code, taluk_Code, hobli_Code, va_Circle_Code);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String response_server = response.body();
                    Log.d("response_server", response_server + "");

                    try {

                        dialog.incrementProgressBy(10);
                        truncateDatabase_village_name();

                        JSONObject jsonObject = new JSONObject(response_server);
                        JSONArray array = jsonObject.getJSONArray("data");

                        int count = array.length();
                        for (int i = 0; i < count; i++) {

                            JSONObject object = array.getJSONObject(i);

                            set_and_get_village_name = new Set_and_Get_Village_Name();
                            set_and_get_village_name.setVCM_va_circle_code(object.getString(DataBaseHelperClass_VillageNames.VCM_va_circle_code));
                            set_and_get_village_name.setVCM_va_circle_ename(object.getString(DataBaseHelperClass_VillageNames.VCM_va_circle_ename));
                            set_and_get_village_name.setVCM_va_circle_kname(object.getString(DataBaseHelperClass_VillageNames.VCM_va_circle_kname));
                            set_and_get_village_name.setHM_village_code(object.getString(DataBaseHelperClass_VillageNames.HM_village_code));
                            set_and_get_village_name.setHM_habitation_ename(object.getString(DataBaseHelperClass_VillageNames.HM_habitation_ename));
                            set_and_get_village_name.setHM_habitation_kname(object.getString(DataBaseHelperClass_VillageNames.HM_habitation_kname));


                            databaseVilNames.execSQL("insert into " + DataBaseHelperClass_VillageNames.TABLE_NAME
                                    + "(VCM_va_circle_code,VCM_va_circle_ename, VCM_va_circle_kname,HM_village_code, HM_habitation_ename, HM_habitation_kname) values ("
                                    + set_and_get_village_name.getVCM_va_circle_code() + ",'" + set_and_get_village_name.getVCM_va_circle_ename() + "','" + set_and_get_village_name.getVCM_va_circle_kname() + "',"
                                    + set_and_get_village_name.getHM_village_code() + ",'" + set_and_get_village_name.getHM_habitation_ename() + "','"
                                    + set_and_get_village_name.getHM_habitation_kname() + "')");
                            Log.d("Database", "VillageNames Database Inserted");

                        }
                        dialog.incrementProgressBy(10);
                        String username = uName_get.substring(0, 3);//First three characters of username
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd", Locale.ENGLISH);
                        String day_num = df.format(c);//Current Day
                        SimpleDateFormat df1 = new SimpleDateFormat("yy", Locale.ENGLISH);
                        String year_num = df1.format(c);//last two digits of the year
                        String app_name = "Samyojane";

                        String fieldVerify_api_flag2 = username + day_num + app_name + year_num;

                        GetServiceTrandataFromServer(getString(R.string.fieldVerify_api_flag1), fieldVerify_api_flag2, district_Code, taluk_Code, hobli_Code, uName_get, DesiCode, va_Circle_Code);
//                    final int totalProgressTime = 100;
//                    final Thread t = new Thread() {
//                        @Override
//                        public void run() {
//                            int jumpTime = 43;
//
//                            while(jumpTime < totalProgressTime) {
//                                try {
//                                    sleep(2000);
//                                    jumpTime += 1;
//                                    if(jumpTime>75){
//                                        sleep(3000);
//                                    }else {
//                                        sleep(0);
//                                    }
//                                    dialog.setProgress(jumpTime);
//                                } catch (InterruptedException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    };
//                    t.start();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                    } catch (OutOfMemoryError e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                        Log.e("OutOfMemoryError", "" + e.toString());
                    } catch (NullPointerException e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                        Log.e("NullPointerException", "" + e.toString());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("multipleResource",""+t.getMessage());
                call.cancel();
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void truncateDatabase_Service_Tran_data(){
        Cursor cursor = databaseServTran.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            databaseServTran.execSQL("Delete from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME);
            Log.d("Database", "ServiceTranTable Database Truncated");
        }
        cursor.close();

    }

    public void GetServiceTrandataFromServer(String flag1, String flag2, int district_Code, int taluk_Code, int hobli_Code, String loginID, int desiCode, int va_Circle_Code){
        apiInterface_nic = APIClient.getClient(getString(R.string.MobAPI_New_NIC)).create(APIInterface_NIC.class);

        Call<JsonObject> call = apiInterface_nic.GetListForFieldVerification(flag1, flag2, district_Code, taluk_Code, hobli_Code, loginID, desiCode, va_Circle_Code);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject1 = response.body();
                    Log.d("response_server", jsonObject1 + "");
                    assert jsonObject1 != null;
                    JsonPrimitive jsonObject3 = jsonObject1.getAsJsonPrimitive("StatusCode");
                    String StatusCode = jsonObject3.toString();
                    String response_server;
                    if (StatusCode.equalsIgnoreCase("2")){
                        JsonPrimitive jsonPrimitive = jsonObject1.getAsJsonPrimitive("StatusMessage");
                        response_server = jsonPrimitive.toString();
                    } else {
                        JsonObject jsonObject2 = jsonObject1.getAsJsonObject("StatusMessage");
                        response_server = jsonObject2.toString();
                    }
                    if (response_server.contains("No Records to Process") || StatusCode.equalsIgnoreCase("2")) {
                        Log.d("Values", "No records Exists");
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), R.string.no_data_to_verify, Toast.LENGTH_SHORT).show();
                        btnDownload.setText(R.string.download);
                        btnProceed.setVisibility(View.GONE);
                    } else {
                        try {
                            dialog.incrementProgressBy(10);

                            JSONObject jsonObject = new JSONObject(response_server);
                            JSONArray array = jsonObject.getJSONArray("Table");

                            int count = array.length();
                            truncateDatabase_Service_Tran_data();

                            for (int i = 0; i < count; i++) {

                                JSONObject object = array.getJSONObject(i);

                                set_and_get_service_tran_data = new Set_and_Get_Service_tran_data();
                                set_and_get_service_tran_data.setDistrict_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code));
                                set_and_get_service_tran_data.setTaluk_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code));
                                set_and_get_service_tran_data.setHobli_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code));
                                set_and_get_service_tran_data.setVillage_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code));
                                set_and_get_service_tran_data.setTown_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code));
                                set_and_get_service_tran_data.setWard_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code));
                                set_and_get_service_tran_data.setService_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code));
                                set_and_get_service_tran_data.setST_applicant_photo(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_photo));
                                set_and_get_service_tran_data.setGSCNo(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo));
                                set_and_get_service_tran_data.setApplicant_Name(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name));
                                set_and_get_service_tran_data.setDue_Date(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date));
                                set_and_get_service_tran_data.setRaised_Location(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Raised_Location));
                                set_and_get_service_tran_data.setFather_Name(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.FatherName));
                                set_and_get_service_tran_data.setMother(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.MotherName));
                                set_and_get_service_tran_data.setIDNo(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.IDNo));
                                set_and_get_service_tran_data.setMobile_No(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Mobile_No));
                                set_and_get_service_tran_data.setAddress1(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Address1));
                                set_and_get_service_tran_data.setAddress2(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Address2));
                                set_and_get_service_tran_data.setAddress3(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Address3));
                                set_and_get_service_tran_data.setAdd_Pin(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode));
                                set_and_get_service_tran_data.setST_ID_TYPE(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ID_TYPE));
                                set_and_get_service_tran_data.setEng_Certify(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate));
                                set_and_get_service_tran_data.setApplicantTiitle(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ApplicantTiitle));
                                set_and_get_service_tran_data.setBinCom(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.BinCom));
                                set_and_get_service_tran_data.setRelationTitle(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.RelationTitle));
                                set_and_get_service_tran_data.setReservationCategory(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.ReservationCategory));
                                set_and_get_service_tran_data.setCaste(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Caste));
                                set_and_get_service_tran_data.setSCOT_caste_app(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.SCOT_caste_app));
                                set_and_get_service_tran_data.setAnnualIncome(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.AnnualIncome));
                                set_and_get_service_tran_data.setGST_No_Mths_Applied(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.GST_No_Mths_Applied));
                                set_and_get_service_tran_data.setGST_No_Years_Applied(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.GST_No_Years_Applied));
                                set_and_get_service_tran_data.setPush_Flag(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Push_Flag));
                                set_and_get_service_tran_data.setVA_RI_IMEI_Num(IMEI_Num);
                                set_and_get_service_tran_data.setVA_RI_Name(VA_Name);

                                serviceCode = object.getInt(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code);
                                Log.d("serviceCode", "" + serviceCode);

                                Cursor cursor = databaseFacility.rawQuery("select * from " + DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME + " where "
                                        + DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code + "=" + serviceCode, null);
                                if (cursor.getCount() > 0) {
                                    if (cursor.moveToNext()) {
                                        serviceName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_edesc));
                                        serviceName_k = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_kdesc));
                                        Log.d("serviceName", serviceName + ", " + serviceName_k);
                                    }
                                } else {
                                    cursor.close();
                                }

                                set_and_get_service_tran_data.setService_Name(serviceName);
                                set_and_get_service_tran_data.setService_Name_k(serviceName_k);

                                databaseServTran.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                                        + "("
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Name + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Name_k + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_photo + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Due_Date + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Raised_Location + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.FatherName + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.MotherName + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.IDNo + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Mobile_No + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Address1 + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Address2 + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Address3 + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.ID_TYPE + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.ApplicantTiitle + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.BinCom + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RelationTitle + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.ReservationCategory + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Caste + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.SCOT_caste_app + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.AnnualIncome + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.GST_No_Mths_Applied + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.GST_No_Years_Applied + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Push_Flag + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_IMEI + ","
                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Name + ")"
                                        + " values (" + set_and_get_service_tran_data.getDistrict_Code() + ","
                                        + set_and_get_service_tran_data.getTaluk_Code() + ","
                                        + set_and_get_service_tran_data.getHobli_Code()
                                        + "," + set_and_get_service_tran_data.getVillage_Code() + ","
                                        + set_and_get_service_tran_data.getTown_Code() + ","
                                        + set_and_get_service_tran_data.getWard_Code() + ","
                                        + set_and_get_service_tran_data.getService_Code() + ",'"
                                        + set_and_get_service_tran_data.getService_Name() + "','"
                                        + set_and_get_service_tran_data.getService_Name_k() + "','"
                                        + set_and_get_service_tran_data.getGSCNo() + "','"
                                        + set_and_get_service_tran_data.getST_applicant_photo() + "','"
                                        + set_and_get_service_tran_data.getApplicant_Name() + "','"
                                        + set_and_get_service_tran_data.getDue_Date() + "','"
                                        + set_and_get_service_tran_data.getRaised_Location() + "','"
                                        + set_and_get_service_tran_data.getFather_Name() + "','"
                                        + set_and_get_service_tran_data.getMother() + "','"
                                        + set_and_get_service_tran_data.getIDNo() + "',"
                                        + set_and_get_service_tran_data.getMobile_No() + ",'"
                                        + set_and_get_service_tran_data.getAddress1() + "','"
                                        + set_and_get_service_tran_data.getAddress2() + "','"
                                        + set_and_get_service_tran_data.getAddress3() + "','"
                                        + set_and_get_service_tran_data.getAdd_Pin() + "',"
                                        + set_and_get_service_tran_data.getST_ID_TYPE() + ",'"
                                        + set_and_get_service_tran_data.getEng_Certify() + "',"
                                        + set_and_get_service_tran_data.getApplicantTiitle() + ","
                                        + set_and_get_service_tran_data.getBinCom() + ","
                                        + set_and_get_service_tran_data.getRelationTitle() + ","
                                        + set_and_get_service_tran_data.getReservationCategory() + ","
                                        + set_and_get_service_tran_data.getCaste() + ","
                                        + set_and_get_service_tran_data.getSCOT_caste_app() + ",'"
                                        + set_and_get_service_tran_data.getAnnualIncome() + "',"
                                        + set_and_get_service_tran_data.getGST_No_Mths_Applied() + ","
                                        + set_and_get_service_tran_data.getGST_No_Years_Applied() + ",'"
                                        + set_and_get_service_tran_data.getPush_Flag() + "','"
                                        + set_and_get_service_tran_data.getVA_RI_IMEI_Num() + "','"
                                        + set_and_get_service_tran_data.getVA_RI_Name() + "')");

                                Log.d("Database", "ServiceTranTable Database Inserted " + j);
                                j++;

                            }

                            runOnUiThread(() -> {
                                timeSwapBuff += timeInMilliseconds;
                                customHandler.removeCallbacks(updateTimerThread);
                                Cursor cursor3 = databaseServTran.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME, null);
                                if (cursor3.getCount() > 0) {
                                    tData = 1;
                                    List<String> GSCNo_List = new ArrayList<>();
                                    int i=0;
                                    if (cursor3.moveToNext()){
                                        do {
                                            Log.d("Cursor_count", ""+cursor3.getCount()+", i:"+i);
                                            GSCNo_List.add(i, cursor3.getString(cursor3.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo)));
                                            if (cursor3.getCount()==i+1){
                                                if (GSCNo_List.size()>0) {
                                                    Set_and_Get_AckForData set_and_get_ackForData = new Set_and_Get_AckForData();
                                                    set_and_get_ackForData.setGscNoList(GSCNo_List.toString().replaceAll("\\[", "").replaceAll("\\]",""));
                                                    set_and_get_ackForData.setDesignationCode(DesiCode);
                                                    Call<JsonObject> call1 = apiInterface_nic.AckForData(set_and_get_ackForData);
                                                    call1.enqueue(new Callback<JsonObject>() {
                                                        @Override
                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                            if (response.isSuccessful()){
                                                                btnProceed.setVisibility(View.VISIBLE);
                                                                btnDownload.setText(R.string.download);
                                                                Log.d("response", ""+response.body());
                                                                dialog.dismiss();
                                                            } else {
                                                                btnProceed.setVisibility(View.GONE);
                                                                Toast.makeText(getApplicationContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                                                                dialog.dismiss();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                                            t.printStackTrace();
                                                            Toast.makeText(getApplicationContext(), ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                }
                                            } else {
                                                i++;
                                            }
                                        } while (cursor3.moveToNext());
                                    }
                                } else {
                                    cursor3.close();
                                    tData = 0;
                                    Log.d("Values", "No records Exists");
                                    Toast.makeText(getApplicationContext(), R.string.no_data_to_verify, Toast.LENGTH_SHORT).show();
                                    btnDownload.setText(R.string.download);
                                    btnProceed.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                            runOnUiThread(() -> {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                            });
                        } catch (OutOfMemoryError e) {
                            runOnUiThread(() -> {
                                dialog.dismiss();
                                buildAlertForOutOfMemory();
                                //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                            });
                            Log.e("OutOfMemoryError2", "" + e.toString());
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                            Log.e("NullPointerException2", "" + e.toString());
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("multipleResource",""+t.getMessage());
                call.cancel();
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private final Runnable updateTimerThread = new Runnable() {

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            tvTimerValue.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    @SuppressLint("StaticFieldLeak")
//    class InsertServiceParameterTable_Server extends AsyncTask<String, Integer, String> {
//
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        @Override
//        protected String doInBackground(String... params) {
//            Cursor cursor = databaseServTran.rawQuery("select "+DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.va_Circle_Code +","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Mother+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.U_Mobile_No+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.U_RationCard_No+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address1+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address2+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address3+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.ST_GSCFirstPart+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Report_No+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.UID+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.AadhaarPhoto+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Category+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Caste+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Belongs_Creamy_Layer_6+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Creamy_Layer_6+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Num_Years_8+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.App_Father_Category_8+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Father_Caste_8+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.App_Mother_Category_8+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Mother_Caste_8+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Remarks+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Total_No_Years_10+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.NO_Months_10+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Reside_At_Stated_Address_10+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Place_Match_With_RationCard_10+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Pur_for_Cert_Code_10+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Annual_Income+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Photo+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.vLat+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.vLong+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Can_Certificate_Given+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Rejection+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag
//                    +" from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1+" where "
//                    +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1", null);
//            try {
//                if (cursor.getCount() > 0) {
//
//                    if (cursor.moveToFirst()) {
//                        do {
//                            Log.d("Loop_entering_here", "");
//
//                            try {
//                                District_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code));
//                                Taluk_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code));
//                                Hobli_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code));
//                                Village_Circle_code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.va_Circle_Code));
//                                Village_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code));
//                                Town_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code));
//                                Ward_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code));
//                                Service_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code));
//                                Applicant_Id = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No));
//                                VA_Accepts_Applicant_information = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information));
//                                name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name));
//                                fatherName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name));
//                                motherName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Mother));
//                                Mobile_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.U_Mobile_No));
//                                RationCard_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.U_RationCard_No));
//                                Address1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address1));
//                                Address2 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address2));
//                                Address3 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address3));
//                                PinCode = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode));
//                                Eng_Certif = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate));
//                                GSC_FirstPart = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_GSCFirstPart));
//                                Report_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Report_No));
//                                Aadhar_NO = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UID));
//                                Aadhaar_Photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.AadhaarPhoto));
//                                Applicant_Category = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Category));
//                                Applicant_Caste = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Caste));
//                                Belongs_Creamy_Layer_6=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Belongs_Creamy_Layer_6));
//                                Reason_for_Creamy_Layer_6= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Creamy_Layer_6));
//                                Num_Years_8=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Num_Years_8));
//                                App_Father_Category_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.App_Father_Category_8));
//                                APP_Father_Caste_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Father_Caste_8));
//                                App_Mother_Category_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.App_Mother_Category_8));
//                                APP_Mother_Caste_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Mother_Caste_8));
//                                Remarks=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Remarks));
//                                Total_No_Years_10 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Total_No_Years_10));
//                                NO_Months_10 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.NO_Months_10));
//                                Reside_At_Stated_Address_10=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Reside_At_Stated_Address_10));
//                                Place_Match_With_RationCard_10=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Place_Match_With_RationCard_10));
//                                Pur_for_Cert_Code_10= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Pur_for_Cert_Code_10));
//                                Annual_Income= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Annual_Income));
//                                Photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Photo));
//                                vLat = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.vLat));
//                                vLong = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.vLong));
//                                Can_Certificate_Given = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Can_Certificate_Given));
//                                Reason_for_Rejection = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Rejection));
//                                DataUpdateFlag = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag));
//
//                                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE2, OPERATION_NAME2);
//
//                                request.addProperty("District_Code", District_Code);
//                                request.addProperty("Taluk_Code", Taluk_Code);
//                                request.addProperty("Hobli_Code", Hobli_Code);
//                                request.addProperty("va_Circle_Code", Village_Circle_code);
//                                request.addProperty("Village_Code", Village_Code);
//                                request.addProperty("Town_Code", Town_Code);
//                                request.addProperty("Ward_Code", Ward_Code);
//                                request.addProperty("Service_Code", Service_Code);
//                                request.addProperty("Applicant_Id", Applicant_Id);
//                                request.addProperty("VA_Accepts_Applicant_information",VA_Accepts_Applicant_information);
//                                request.addProperty("Applicant_Name", name);
//                                request.addProperty("Father_Name", fatherName);
//                                request.addProperty("Mother_Name", motherName);
//                                request.addProperty("Mobile_No", Mobile_No);
//                                request.addProperty("RationCard_No", RationCard_No);
//                                request.addProperty("Address1", Address1);
//                                request.addProperty("Address2", Address2);
//                                request.addProperty("Address3", Address3);
//                                request.addProperty("PinCode", PinCode);
//                                request.addProperty("ST_Eng_Certificate",Eng_Certif);
//                                request.addProperty("ST_GSCFirstPart", GSC_FirstPart);
//                                request.addProperty("Report_No", Report_No);
//                                request.addProperty("Aadhar_NO", Aadhar_NO);
//                                request.addProperty("Aadhaar_Photo", Aadhaar_Photo);
//                                request.addProperty("ST_applicant_photo", ST_applicant_photo);
//                                request.addProperty("Applicant_Category", Applicant_Category);
//                                request.addProperty("Applicant_Caste", Applicant_Caste);
//                                request.addProperty("Belongs_Creamy_Layer_6", Belongs_Creamy_Layer_6);
//                                request.addProperty("Reason_for_Creamy_Layer_6", Reason_for_Creamy_Layer_6);
//                                request.addProperty("Num_Years_8", Num_Years_8);
//                                request.addProperty("App_Father_Category_8", App_Father_Category_8);
//                                request.addProperty("APP_Father_Caste_8", APP_Father_Caste_8);
//                                request.addProperty("App_Mother_Category_8", App_Mother_Category_8);
//                                request.addProperty("APP_Mother_Caste_8", APP_Mother_Caste_8);
//                                request.addProperty("Remarks", Remarks);
//                                request.addProperty("Total_No_Years_10", Total_No_Years_10);
//                                request.addProperty("NO_Months_10", NO_Months_10);
//                                request.addProperty("Reside_At_Stated_Address_10", Reside_At_Stated_Address_10);
//                                request.addProperty("Place_Match_With_RationCard_10", Place_Match_With_RationCard_10);
//                                request.addProperty("Pur_for_Cert_Code_10", Pur_for_Cert_Code_10);
//                                request.addProperty("Annual_Income", Annual_Income);
//                                request.addProperty("Photo", Photo);
//                                request.addProperty("vLat", vLat);
//                                request.addProperty("vLong", vLong);
//                                request.addProperty("Can_Certificate_Given", Can_Certificate_Given);
//                                request.addProperty("Reason_for_Rejection", Reason_for_Rejection);
//                                request.addProperty("DataUpdateFlag", DataUpdateFlag);
//
//                                Log.d("Request","" + request);
//
//                                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//                                envelope.dotNet = true;
//                                envelope.setOutputSoapObject(request);
//
//                                androidHttpTransport = new HttpTransportSE(getString(R.string.SOAP_ADDRESS));
//                                androidHttpTransport.call(SOAP_ACTION2, envelope);
//                                resultString = (SoapPrimitive) envelope.getResponse();
//                                Log.i("Result", ""+resultString);
//                                resultFromServer = String.valueOf(resultString);
//                                if(resultFromServer.equals("0")) {
//                                    runOnUiThread(() -> {
//                                        //Toast.makeText(getApplicationContext(), "Data Uploaded Successfully" , Toast.LENGTH_SHORT).show();
//                                        Log.d("Request_", "UpdateServiceParameterTable" + "Data Uploaded Successfully");
//                                    });
//
//                                    databaseServTran.execSQL("delete from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where " + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + Applicant_Id);
//                                    Log.d("Local_Result", "A row deleted Successfully");
//                                }
//                                else {
//                                    Log.d("Request_", "UpdateServiceParameterTable" +" Data not uploaded");
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                Log.i("Error1", e.getMessage());
//                                runOnUiThread(() -> {
//                                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show();
//                                    Log.d("InsertServiceParaTable", "Server Exception Occurred");
//                                });
//                            }
//                        }while (cursor.moveToNext());
//                    }
//
//                } else {
//                    runOnUiThread(() -> {
//                        //Toast.makeText(getApplicationContext(), "There is no Updated data to Upload in Server " , Toast.LENGTH_SHORT).show();
//                        Log.d("InsertServiceParaTable", "There is no Updated data to Upload in Server");
//                        cursor.close();
//                    });
//                }
//
//            } catch (Exception e) {
//                Log.d("InsertServiceParaTable1", e.getMessage());
//            }
//            //Toast.makeText(getApplicationContext(), result , Toast.LENGTH_SHORT).show();
//
//            return "InBackground";
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//        }
//
//        protected void onProgressUpdate(Integer... a) {
//
//        }
//    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if (connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;

        }else if (
                connectivityManager.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connectivityManager.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED){
            return false;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
//        MainActivity.userName.setText("");
        MainActivity.pwd.setText("");
        buildAlertMessageGoingBack();
    }
}
