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
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RI_SecondScreen extends AppCompatActivity {

    String deviceId;
    TextView proceed, RIModule, tvDistrict, tvTaluk, tvHobli, tvRI_Name, tvTimerValue;
    Button btnDownload, btnUpload, btnProceed, btnPendency;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase databaseServTran, databaseFacility, databaseCredential, databaseVilNames;
    String district, taluk, hobli, VA_Circle_Name, RI_Name, VA_Name;
    static int district_Code, taluk_Code, hobli_Code, va_Circle_Code;
    Set_and_Get_Facility_Services set_and_get_facility_services;
    Set_and_Get_Village_Name set_and_get_village_name;
    Set_and_Get_Service_tran_data set_and_get_service_tran_data;
    int fData=0, vData=0, tData=0;
    ProgressDialog dialog;
    GPSTracker gpsTracker;
    double latitude, longitude;
    int serviceCode;
    int villageCircleCode;
    String serviceName, serviceName_k;
    int j=1;

    private long startTime = 0L;
    Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    String IMEI_Num, mob_Num;
    String uName_get;
    APIInterface_SamyojaneAPI apiInterface_samyojaneAPI;
    APIInterface_NIC apiInterface_nic;
    SharedPreferences sharedPreferences;
    int DesiCode;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_second_screen);
        proceed = findViewById(R.id.proceed);
        RIModule = findViewById(R.id.RIModule);
        proceed.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        RIModule.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        btnDownload = findViewById(R.id.btnDownload_RI);
        tvDistrict = findViewById(R.id.district_RI);
        tvTaluk = findViewById(R.id.taluk_RI);
        tvHobli = findViewById(R.id.tv_hobli_RI);
        tvRI_Name = findViewById(R.id.VA_name);
        btnUpload = findViewById(R.id.btnUploadScreen_RI);
        tvTimerValue = findViewById(R.id.tvTimerValue_RI);
        btnProceed = findViewById(R.id.btnProceed);
        btnPendency = findViewById(R.id.btnPendency);

        btnProceed.setOnClickListener(v -> {
            Intent i = new Intent(RI_SecondScreen.this, RI_Field_Report.class);
            i.putExtra("district_Code", district_Code);
            i.putExtra("districtCode", district);
            i.putExtra("taluk_Code", taluk_Code);
            i.putExtra("taluk", taluk);
            i.putExtra("hobli_Code", hobli_Code);
            i.putExtra("hobli", hobli);
            i.putExtra("va_Circle_Code", va_Circle_Code);
            i.putExtra("VA_Circle_Name",VA_Circle_Name);
            i.putExtra("VA_Name", VA_Name);
            i.putExtra("RI_Name", RI_Name);
            startActivity(i);
        });

        btnPendency.setOnClickListener(view -> {
            Intent i = new Intent(RI_SecondScreen.this, RI_VA_Circle_Wise_Report.class);
            i.putExtra("district_Code", district_Code);
            i.putExtra("districtCode", district);
            i.putExtra("taluk_Code", taluk_Code);
            i.putExtra("taluk", taluk);
            i.putExtra("hobli_Code", hobli_Code);
            i.putExtra("hobli", hobli);
            i.putExtra("va_Circle_Code", va_Circle_Code);
            i.putExtra("VA_Circle_Name",VA_Circle_Name );
            i.putExtra("RI_Name", RI_Name);
            startActivity(i);
        });

        //btnProceed.setVisibility(View.GONE);
        //btnPendency.setVisibility(View.GONE);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_SecondScreen.this);
        databaseServTran = openHelper.getWritableDatabase();

        Cursor cursor12 = databaseServTran.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME
                +" where "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is null",null);
        if (cursor12.getCount()>0){
            btnProceed.setVisibility(View.VISIBLE);
        }else {
            cursor12.close();
            btnProceed.setVisibility(View.GONE);
        }

        Intent i = getIntent();
        district = i.getStringExtra("districtCode");
        taluk = i.getStringExtra("taluk");
        hobli = i.getStringExtra("hobli");
        RI_Name = i.getStringExtra("RI_Name");
        deviceId = i.getStringExtra("deviceId");
        IMEI_Num = i.getStringExtra("IMEI_Num");
        mob_Num = i.getStringExtra("mob_Num");

        Log.d("Second_Database_Value", ""+district);
        Log.d("Second_Database_Value", ""+taluk);
        Log.d("Second_Database_Value", ""+hobli);
        Log.d("Second_Database_Value", ""+RI_Name);
        Log.d("Second_Database_Value", ""+deviceId);
        Log.d("IMEI_Num", ""+IMEI_Num);
        Log.d("mob_Num", ""+mob_Num);

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        DesiCode = sharedPreferences.getInt(Constants.DesiCode_RI, 19);
        uName_get = sharedPreferences.getString(Constants.uName_get, "");

        dialog = new ProgressDialog(RI_SecondScreen.this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage(getString(R.string.downloading_please_wait));
        dialog.setIndeterminate(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMax(100);

        gpsTracker = new GPSTracker(getApplicationContext(), this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Log.d("Location", latitude+""+longitude);
        } else {
            buildAlertMessageNoGps();
        }

        openHelper = new DataBaseHelperClass_Credentials(RI_SecondScreen.this);
        databaseCredential = openHelper.getWritableDatabase();

        final Cursor cursor = databaseCredential.rawQuery("select * from "
                + DataBaseHelperClass_Credentials.TABLE_NAME+" where "
                + getString(R.string.cre_district_name)+"='"+district+"' and "
                + getString(R.string.cre_taluk_name)+"='"+taluk+"' and "
                + getString(R.string.cre_hobli_name)+"='"+hobli+"'", null);
        Log.d("cursor_val",""+"select * from "
                + DataBaseHelperClass_Credentials.TABLE_NAME+" where "
                + getString(R.string.cre_district_name)+"='"+district+"' and "
                + getString(R.string.cre_taluk_name)+"='"+taluk+"' and "
                + getString(R.string.cre_hobli_name)+"='"+hobli+"'");
        if(cursor.getCount()>0) {
            if (cursor.moveToNext()) {
                district_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_Credentials.District_Code));
                taluk_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Code));
                hobli_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Code));
                va_Circle_Code = cursor.getInt(cursor.getColumnIndex(DataBaseHelperClass_Credentials.VA_circle_Code));
            }
        } else {
            cursor.close();
        }

        Log.d("VA_Circle_Code", ""+va_Circle_Code);
        Log.d("SecondScreen", ""+district_Code);
        Log.d("SecondScreen", ""+taluk_Code);
        Log.d("SecondScreen", ""+hobli_Code);

        RI_SecondScreen.Global.district_Code1 =district_Code;
        RI_SecondScreen.Global.taluk_Code1 = taluk_Code;

        tvDistrict.setText(": "+district);
        tvTaluk.setText(": "+taluk);
        tvHobli.setText(": "+hobli);
        tvRI_Name.setText(": "+ RI_Name);

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

                openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_SecondScreen.this);
                databaseServTran = openHelper.getWritableDatabase();

                //new Update_RI_ServiceParameterTable().execute();

                GetFacilityServiceFromServer();
//                GetVillageNameFromServer(district_Code, taluk_Code, hobli_Code, deviceId);
//                GetServiceParametersDataFromServer(getString(R.string.fieldVerify_api_flag1), fieldVerify_api_flag2, district_Code, taluk_Code, hobli_Code, uName_get, DesiCode, 0);

            }
            else {
                openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(RI_SecondScreen.this);
                databaseFacility = openHelper.getWritableDatabase();

                Cursor cursor1=databaseFacility.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME, null);

                if (cursor1.getCount()>0){
                    fData=1;
                    Log.d("entry", String.valueOf(fData));
                    openHelper = new DataBaseHelperClass_VillageNames(RI_SecondScreen.this);
                    databaseVilNames = openHelper.getWritableDatabase();

                    Cursor cursor2 = databaseVilNames.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME, null);

                    if(cursor2.getCount()>0) {
                        vData=1;
                        Log.d("entry", String.valueOf(vData));
                        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_SecondScreen.this);
                        databaseServTran = openHelper.getWritableDatabase();

                        Cursor cursor3 = databaseServTran.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME
                                + " where "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.District_Code+"="+district_Code+" and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Taluk_Code+"="+taluk_Code+" and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Hobli_Code+"="+hobli_Code, null);

                        if(cursor3.getCount()>0) {
                            tData=1;
                            Log.d("entry", String.valueOf(tData));
                            btnProceed.setVisibility(View.VISIBLE);
                            //btnPendency.setVisibility(View.VISIBLE);
                            //Toast.makeText(getApplicationContext(), "Data already exist", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            cursor3.close();
                            Log.d("Values", "No records Exists");
                            btnProceed.setVisibility(View.GONE);
                            //btnPendency.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                            tData=0;
                        }
                    }
                    else {
                        cursor2.close();
                        vData=0;
                    }
                }
                else {
                    cursor1.close();
                    fData=0;
                    Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
                }

            }

        });

        btnUpload.setOnClickListener(v -> {
            Intent i1 = new Intent(RI_SecondScreen.this, RI_UploadScreen.class);
            i1.putExtra("IMEI_Num", ""+IMEI_Num);
            i1.putExtra("mob_Num",""+mob_Num);
            i1.putExtra("RI_Name", ""+RI_Name);
            startActivity(i1);
        });


    }

    public static class Global{
        static int district_Code1 = district_Code;
        static int taluk_Code1 = taluk_Code;
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
        builder.setMessage(R.string.want_to_exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    RI_SecondScreen.super.onBackPressed();
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void truncateDatabase_facility(){
        Cursor cursor = databaseFacility.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            databaseFacility.execSQL("Delete from " + DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME);
            Log.d("Database", "FacilityMaster Database Truncated");
        } else {
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
                        openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(RI_SecondScreen.this);
                        databaseFacility = openHelper.getWritableDatabase();

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
                        GetVillageNameFromServer(district_Code, taluk_Code, hobli_Code, deviceId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                        Log.e("OutOfMemoryError", "" + e.toString());
                    } catch (NullPointerException e) {
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
        Cursor cursor = databaseVilNames.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            databaseVilNames.execSQL("Delete from " + DataBaseHelperClass_VillageNames.TABLE_NAME);
            Log.d("Database", "VillageNames Database Truncated");
        } else {
            cursor.close();
        }
    }

    public void GetVillageNameFromServer(int district_Code, int taluk_Code, int hobli_Code, String IMEI_Num){
        apiInterface_samyojaneAPI = APIClient.getClient(getString(R.string.samyojane_API_url)).create(APIInterface_SamyojaneAPI.class);

        Call<String> call = apiInterface_samyojaneAPI.doFn_Get_Village_Name_For_RI(getString(R.string.samyojane_api_flag1),getString(R.string.samyojane_api_flag2), district_Code, taluk_Code, hobli_Code, IMEI_Num);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String response_server = response.body();
                    Log.d("response_server", response_server + "");

                    try {

                        openHelper = new DataBaseHelperClass_VillageNames(RI_SecondScreen.this);
                        databaseVilNames = openHelper.getWritableDatabase();

                        JSONObject jsonObject = new JSONObject(response_server);
                        JSONArray array = jsonObject.getJSONArray("data");

                        truncateDatabase_village_name();

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
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                    } catch (OutOfMemoryError e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        Log.e("OutOfMemoryError", "" + e.toString());
                    } catch (NullPointerException e) {
                        dialog.dismiss();
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

    public void truncateDatabase_Service_Tran_data(){
        Cursor cursor = databaseServTran.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            databaseServTran.execSQL("Delete from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME);
            Log.d("Database", "ServiceParametersTable Database Truncated");
        } else {
            cursor.close();
        }
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
                        Log.d("response_server", jsonPrimitive + "");
                        response_server = jsonPrimitive.toString();
                    } else {
                        JsonObject jsonObject2 = jsonObject1.getAsJsonObject("StatusMessage");
                        Log.d("response_server", jsonObject2 + "");
                        response_server = jsonObject2.toString();
                    }
                    if (response_server.contains("No Records to Process") || StatusCode.equalsIgnoreCase("2")) {
                        Log.d("Values", "No records Exists");
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
                                set_and_get_service_tran_data.setDistrict_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.District_Code));
                                set_and_get_service_tran_data.setTaluk_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Taluk_Code));
                                set_and_get_service_tran_data.setHobli_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Hobli_Code));
                                set_and_get_service_tran_data.setVillage_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code));
                                set_and_get_service_tran_data.setTown_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code));
                                set_and_get_service_tran_data.setWard_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code));
                                set_and_get_service_tran_data.setService_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Code));
                                set_and_get_service_tran_data.setST_applicant_photo(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_applicant_photo));
                                set_and_get_service_tran_data.setGSCNo(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo));
                                set_and_get_service_tran_data.setApplicant_Name(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name));
                                set_and_get_service_tran_data.setDue_Date(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Due_Date));
                                set_and_get_service_tran_data.setRaised_Location(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Raised_Location));
                                set_and_get_service_tran_data.setFather_Name(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.FatherName));
                                set_and_get_service_tran_data.setMother(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.MotherName));
                                set_and_get_service_tran_data.setIDNo(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.IDNo));
                                set_and_get_service_tran_data.setMobile_No(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Mobile_No));
                                set_and_get_service_tran_data.setAddress1(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address1));
                                set_and_get_service_tran_data.setAddress2(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address2));
                                set_and_get_service_tran_data.setAddress3(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address3));
                                set_and_get_service_tran_data.setAdd_Pin(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.PinCode));
                                set_and_get_service_tran_data.setST_ID_TYPE(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ID_TYPE));
                                set_and_get_service_tran_data.setEng_Certify(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_Eng_Certificate));
                                set_and_get_service_tran_data.setApplicantTiitle(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ApplicantTiitle));
                                set_and_get_service_tran_data.setBinCom(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.BinCom));
                                set_and_get_service_tran_data.setRelationTitle(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RelationTitle));
                                set_and_get_service_tran_data.setReservationCategory(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ReservationCategory));
                                set_and_get_service_tran_data.setCaste(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Caste));
                                set_and_get_service_tran_data.setAnnualIncome(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.AnnualIncome));
                                set_and_get_service_tran_data.setGST_No_Mths_Applied(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Mths_Applied));
                                set_and_get_service_tran_data.setGST_No_Years_Applied(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Years_Applied));
                                set_and_get_service_tran_data.setVA_IMEI_Num(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VA_IMEI));
                                set_and_get_service_tran_data.setVAName(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VA_Name));
                                set_and_get_service_tran_data.setPush_Flag(object.getString(DataBaseHelperClass_btnDownload_ServiceTranTable.Push_Flag));

                                serviceCode = object.getInt(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Code);
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

                                int villCode = object.getInt(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code);
                                Cursor cursor1 = databaseVilNames.rawQuery("Select * from " + DataBaseHelperClass_VillageNames.TABLE_NAME
                                        + " where " + DataBaseHelperClass_VillageNames.HM_village_code + "=" + villCode, null);
                                if (cursor1.getCount() > 0) {
                                    if (cursor1.moveToNext()) {
                                        villageCircleCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_VillageNames.VCM_va_circle_code));
                                    }
                                } else {
                                    cursor1.close();
                                }

                                set_and_get_service_tran_data.setService_Name(serviceName);
                                set_and_get_service_tran_data.setService_Name_k(serviceName_k);

                                databaseServTran.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME
                                        + "("
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.District_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Taluk_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Hobli_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VillageCircle_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Code + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Name + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Name_k + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_applicant_photo + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Due_Date + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Raised_Location + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.FatherName + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.MotherName + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.IDNo + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Mobile_No + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address1 + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address2 + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Address3 + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.PinCode + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ID_TYPE + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_Eng_Certificate + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ApplicantTiitle + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.BinCom + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RelationTitle + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ReservationCategory + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Caste + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.AnnualIncome + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Mths_Applied + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Years_Applied + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VA_IMEI + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.VA_Name + ","
                                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Push_Flag + ")"
                                        + " values (" + set_and_get_service_tran_data.getDistrict_Code() + ","
                                        + set_and_get_service_tran_data.getTaluk_Code() + ","
                                        + set_and_get_service_tran_data.getHobli_Code() + ","
                                        + set_and_get_service_tran_data.getVillage_Code() + ","
                                        + villageCircleCode + ","
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
                                        + set_and_get_service_tran_data.getCaste() + ",'"
                                        + set_and_get_service_tran_data.getAnnualIncome() + "',"
                                        + set_and_get_service_tran_data.getGST_No_Mths_Applied() + ","
                                        + set_and_get_service_tran_data.getGST_No_Years_Applied() + ",'"
                                        + set_and_get_service_tran_data.getVA_IMEI_Num() + "','"
                                        + set_and_get_service_tran_data.getVAName() + "','"
                                        + set_and_get_service_tran_data.getPush_Flag() + "')");

                                Log.d("Database", "ServiceTranTable Database Inserted " + j);
                                j++;

                            }

                            runOnUiThread(() -> {
                                timeSwapBuff += timeInMilliseconds;
                                customHandler.removeCallbacks(updateTimerThread);
                                Cursor cursor3 = databaseServTran.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME, null);
                                if (cursor3.getCount() > 0) {
                                    tData = 1;
                                    btnProceed.setVisibility(View.VISIBLE);
                                    //btnPendency.setVisibility(View.VISIBLE);
                                    btnDownload.setText(R.string.download);
                                    //Toast.makeText(getApplicationContext(), "Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    cursor3.close();
                                    tData = 0;
                                    Log.d("Values", "No records Exists");
                                    Toast.makeText(getApplicationContext(), R.string.no_data_to_verify, Toast.LENGTH_SHORT).show();
                                    btnDownload.setText(R.string.download);
                                    btnProceed.setVisibility(View.GONE);
                                    //btnPendency.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
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

    Runnable updateTimerThread = new Runnable() {

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private  void buildAlertForOutOfMemory() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.memory_full))
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    RI_SecondScreen.super.onBackPressed();
                    finish();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        MainActivity.pwd.setText("");
        buildAlertMessageGoingBack();
    }
}
