package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Objects;

public class RI_SecondScreen extends AppCompatActivity {

    //private static final String url_facility = "http://164.100.133.30/NK_MobileApp/WebService.asmx/Get_Facility_Services";
    //private static final String url_village_name = "http://164.100.133.30/NK_MobileApp/WebService.asmx/Get_Village_Name_For_RI_Live";
    //private static final String url_service_Parameter_Data = "http://164.100.133.30/NK_MobileApp/WebService.asmx/Get_Service_Parameters_data_For_RI_Live";
    HashMap<String, String> hashMap_facility;
    HashMap<String, String> hashMap_village_name;
    HashMap<String, String> hashMap_service_Parameter_Data;

    String SOAP_ACTION2 = "http://tempuri.org/Update_RI_ServiceParameterTable";
    public final String OPERATION_NAME2 = "Update_RI_ServiceParameterTable";  //Method_name
    public final String WSDL_TARGET_NAMESPACE2 = "http://tempuri.org/";  // NAMESPACE
    //String SOAP_ADDRESS2 = "http://164.100.133.30/NK_MobileApp/WebService.asmx";

    String deviceId;
    TextView proceed, RIModule, tvDistrict, tvTaluk, tvHobli, tvRI_Name, tvTimerValue;
    Button btnDownload, btnUpload, btnProceed, btnPendency;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database, database3, database1;
    String district, taluk, hobli, VA_Circle_Name, RI_Name, VA_Name;
    static  String district_Code, taluk_Code, hobli_Code, va_Circle_Code;
//    private static final String url_service_Tran_data_RI = "http://164.100.133.123:9600/Bhoomi_download/WebService.asmx/Get_Service_tran_data_For_RI";
//    HashMap<String, String> hashMap_service_Tran_data;
    Set_and_Get_Facility_Services set_and_get_facility_services;
    Set_and_Get_Village_Name set_and_get_village_name;
    Set_and_Get_Service_Parameter set_and_get_service_parameter;
    int fData=0, vData=0, tData=0;
    ProgressDialog dialog;
    GPSTracker gpsTracker;
    double latitude, longitude;
    int serviceCode;
    String serviceName, serviceName_k;

    private long startTime = 0L;
    Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    String RI_DataUpdateFlag, District_Code, Taluk_Code, Hobli_Code, Village_Circle_code, Village_Code, Town_Code, Ward_Code, Service_Code,Applicant_Id;
    String RI_Annual_Income, RI_vLat, RI_vLong, RI_Accepted_VA_information;


    String RI_Can_Certificate_Given, RI_Reason_for_Rejection;
    //Service Parameters of service_code-6
    String RI_Applicant_Category, RI_Applicant_Caste, RI_Belongs_Creamy_Layer_6, RI_Reason_for_Creamy_Layer_6;
    //Service Parameters of service_code-8
    String RI_Num_Years_8, RI_App_Father_Category_8, RI_APP_Father_Caste_8, RI_App_Mother_Category_8, RI_APP_Mother_Caste_8, RI_Remarks;
    //Service Parameters of service_code-10
    String RI_Total_No_Years_10, RI_NO_Months_10, RI_Reside_At_Stated_Address_10, RI_Place_Match_With_RationCard_10, RI_Pur_for_Cert_Code_10;

    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String resultFromServer;

    String IMEI_Num, mob_Num;

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
        database3 = openHelper.getWritableDatabase();

        Cursor cursor12 = database3.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1
                +" where "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag+" is null",null);
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
        database = openHelper.getWritableDatabase();

        final Cursor cursor = database.rawQuery("select * from "
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
                district_Code = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_Credentials.District_Code));
                taluk_Code = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Code));
                hobli_Code = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Code));
                va_Circle_Code = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_Credentials.VA_circle_Code));
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

        hashMap_facility = new HashMap<>();
        hashMap_village_name = new HashMap<>();
        //hashMap_service_Tran_data = new HashMap<>();
        hashMap_service_Parameter_Data = new HashMap<>();

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
                database3 = openHelper.getWritableDatabase();
                new Update_RI_ServiceParameterTable().execute();

                new GetFacilityServiceFromServer().execute(getString(R.string.url_facility));

                hashMap_village_name.put("District_Code", district_Code);
                hashMap_village_name.put("Taluk_Code", taluk_Code);
                hashMap_village_name.put("Hobli_Code", hobli_Code);
                hashMap_village_name.put("IMEI", deviceId);
                Log.d("hashMap_village_name",""+hashMap_village_name+", URL:"+getString(R.string.url_village_name_RI));
                new GetVillageNameFromServer().execute(getString(R.string.url_village_name_RI));


                hashMap_service_Parameter_Data.put("District_Code", district_Code);
                hashMap_service_Parameter_Data.put("Taluk_Code", taluk_Code);
                hashMap_service_Parameter_Data.put("Hobli_Code", hobli_Code);
                hashMap_service_Parameter_Data.put("IMEI", deviceId);
                Log.d("hashMap_ser_Para_Data",""+hashMap_service_Parameter_Data+", URL:"+getString(R.string.url_service_Parameter_Data));
                new GetServiceParametersDataFromServer().execute(getString(R.string.url_service_Parameter_Data));

                final int totalProgressTime = 100;
                final Thread t = new Thread() {
                    @Override
                    public void run() {
                        int jumpTime = 0;

                        while(jumpTime < totalProgressTime) {
                            try {
                                sleep(200);
                                jumpTime += 1;

                                if(jumpTime>75){
                                    sleep(3000);
                                }else {
                                    sleep(0);
                                }

                                dialog.setProgress(jumpTime);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                };
                t.start();

            }
            else {
                openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(RI_SecondScreen.this);
                database = openHelper.getWritableDatabase();

                Cursor cursor1=database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME, null);

                if (cursor1.getCount()>0){
                    fData=1;
                    Log.d("entry", String.valueOf(fData));
                    openHelper = new DataBaseHelperClass_VillageNames(RI_SecondScreen.this);
                    database = openHelper.getWritableDatabase();

                    Cursor cursor2 = database.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME, null);

                    if(cursor2.getCount()>0) {
                        vData=1;
                        Log.d("entry", String.valueOf(vData));
                        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_SecondScreen.this);
                        database = openHelper.getWritableDatabase();

                        Cursor cursor3 = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1
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
        static String district_Code1 = district_Code;
        static String taluk_Code1 = taluk_Code;
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
        //dialog.incrementProgressBy(10);
        openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(RI_SecondScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME);
            Log.d("Database", "FacilityMaster Database Truncated");
        } else {
            cursor.close();
        }

    }

    @SuppressLint("StaticFieldLeak")
    class GetFacilityServiceFromServer extends AsyncTask<String, Void, JSONObject> {
        JSONObject jsonObject;

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JParserAdv jParserAdv = new JParserAdv();
                jsonObject = jParserAdv.makeHttpRequest(getString(R.string.url_facility), "GET", hashMap_facility);
            }catch (OutOfMemoryError e){
                runOnUiThread(() -> {
                    dialog.dismiss();
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    btnDownload.setText(R.string.download);
                    buildAlertForOutOfMemory();
                    //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                });
                Log.e("OutOfMemoryError", ""+e.toString());
            } catch (NullPointerException e){
                runOnUiThread(() -> {
                    dialog.dismiss();
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    btnDownload.setText(R.string.download);
                });
                Log.e("NullPointerException", ""+e.toString());
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {

                //dialog.incrementProgressBy(10);
                openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(RI_SecondScreen.this);
                database = openHelper.getWritableDatabase();

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


                    database.execSQL("insert into " + DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME + "(SlNo, FM_facility_code, FM_facility_edesc, FM_facility_kdesc, FM_acronym_on_doc_eng, FM_designated_officer" +
                            ", FM_gsc_no_days, FM_facility_display, FM_sakala_service, FM_OTC_Charges) values (" + set_and_get_facility_services.getSlNo() +","+ set_and_get_facility_services.getFM_facility_code()
                            + ",'" + set_and_get_facility_services.getFM_facility_edesc() + "','" + set_and_get_facility_services.getFM_facility_kdesc() + "','" + set_and_get_facility_services.getFM_acronym_on_doc_eng() + "','" + set_and_get_facility_services.getFM_designated_officer()
                            + "'," + set_and_get_facility_services.getFM_gsc_no_days() + ",'" + set_and_get_facility_services.getFM_facility_display()+ "','" + set_and_get_facility_services.getFM_sakala_service()
                            +"'," + set_and_get_facility_services.getFM_OTC_Charges()+ ")");
                    Log.d("Database", "FacilityMaster Database Inserted");


                }
                //dialog.incrementProgressBy(10);
                //database.close();
            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
            }catch (OutOfMemoryError e){
                Log.e("OutOfMemoryError", ""+e.toString());
            }catch (NullPointerException e){
                Log.e("NullPointerException", ""+e.toString());
            }
            //Toast.makeText(getApplicationContext(), "FacilityMaster Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void truncateDatabase_village_name(){
        //dialog.incrementProgressBy(10);
        openHelper = new DataBaseHelperClass_VillageNames(RI_SecondScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_VillageNames.TABLE_NAME);
            Log.d("Database", "VillageNames Database Truncated");
        } else {
            cursor.close();
        }

    }

    @SuppressLint("StaticFieldLeak")
    class GetVillageNameFromServer extends AsyncTask<String, Void, JSONObject> {
        JSONObject jsonObject;

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JParserAdv jParserAdv = new JParserAdv();
                jsonObject = jParserAdv.makeHttpRequest(getString(R.string.url_village_name_RI), "POST", hashMap_village_name);
            }catch (OutOfMemoryError e){
                runOnUiThread(() -> {
                    dialog.dismiss();
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    btnDownload.setText(R.string.download);
                    buildAlertForOutOfMemory();
                    //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                });
                Log.e("OutOfMemoryError", ""+e.toString());
            } catch (NullPointerException e){
                runOnUiThread(() -> {
                    dialog.dismiss();
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    btnDownload.setText(R.string.download);
                });
                Log.e("NullPointerException", ""+e.toString());
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {

                //dialog.incrementProgressBy(20);
                openHelper = new DataBaseHelperClass_VillageNames(RI_SecondScreen.this);
                database = openHelper.getWritableDatabase();

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


                    database.execSQL("insert into " + DataBaseHelperClass_VillageNames.TABLE_NAME
                            + "(VCM_va_circle_code,VCM_va_circle_ename, VCM_va_circle_kname,HM_village_code, HM_habitation_ename, HM_habitation_kname) values ("
                            + set_and_get_village_name.getVCM_va_circle_code() +",'"+set_and_get_village_name.getVCM_va_circle_ename()+"','"+set_and_get_village_name.getVCM_va_circle_kname()+"',"
                            + set_and_get_village_name.getHM_village_code() +",'"+ set_and_get_village_name.getHM_habitation_ename()+"','"
                            + set_and_get_village_name.getHM_habitation_kname()+"')");
                    Log.d("Database", "VillageNames Database Inserted");

                }
                //dialog.incrementProgressBy(20);
                //database.close();
            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
            }catch (OutOfMemoryError e){
                Log.e("OutOfMemoryError", ""+e.toString());
            }catch (NullPointerException e){
                Log.e("NullPointerException", ""+e.toString());
            }
            //Toast.makeText(getApplicationContext(), "VillageNames Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void truncateDatabase_Service_Parameters_tbl_data(){
        //dialog.incrementProgressBy(10);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_SecondScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1);
            Log.d("Database", "ServiceParametersTable Database Truncated");
        } else {
            cursor.close();
        }

    }

    @SuppressLint("StaticFieldLeak")
    class GetServiceParametersDataFromServer extends AsyncTask<String, Integer, JSONObject> {
        JSONObject jsonObject;

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JParserAdv jParserAdv = new JParserAdv();
                jsonObject = jParserAdv.makeHttpRequest(getString(R.string.url_service_Parameter_Data), "POST", hashMap_service_Parameter_Data);
            }catch (OutOfMemoryError e){
                dialog.dismiss();
                Log.e("OutOfMemoryError1", ""+e.toString());
                runOnUiThread(() -> {
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    btnDownload.setText(R.string.download);
                    dialog.dismiss();
                    buildAlertForOutOfMemory();
                    //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                });
            }catch (NullPointerException e){
                runOnUiThread(() -> {
                    dialog.dismiss();
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    btnDownload.setText(R.string.download);
                });
                Log.e("NullPointerException", ""+e.toString());
            }
            return jsonObject;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                int j=1;
                //dialog.incrementProgressBy(20);
                JSONArray array = jsonObject.getJSONArray("data");
                openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_SecondScreen.this);
                database = openHelper.getWritableDatabase();
                truncateDatabase_Service_Parameters_tbl_data();

                openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(RI_SecondScreen.this);
                database1 = openHelper.getWritableDatabase();

                int count = array.length();
                for (int i = 0; i < count; i++) {

                    JSONObject object = array.getJSONObject(i);

                    set_and_get_service_parameter = new Set_and_Get_Service_Parameter();
                    set_and_get_service_parameter.setDistrict_Code(object.getInt(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.District_Code));
                    set_and_get_service_parameter.setTaluk_Code(object.getInt(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Taluk_Code));
                    set_and_get_service_parameter.setHobli_Code(object.getInt(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Hobli_Code));
                    set_and_get_service_parameter.setVa_Circle_Code(object.getInt(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.va_Circle_Code));
                    set_and_get_service_parameter.setVillage_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code));
                    set_and_get_service_parameter.setTown_Code(object.getInt(DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code));
                    set_and_get_service_parameter.setWard_Code(object.getInt(DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code));
                    set_and_get_service_parameter.setService_Code(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Code));
                    set_and_get_service_parameter.setRD_No(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No));
                    set_and_get_service_parameter.setEng_Certify(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_Eng_Certificate));
                    set_and_get_service_parameter.setApplicant_Name(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name));
                    set_and_get_service_parameter.setDue_Date(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Due_Date));
                    set_and_get_service_parameter.setRaised_Location(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Raised_Location));
                    set_and_get_service_parameter.setST_applicant_photo(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_applicant_photo));
                    set_and_get_service_parameter.setAPP_Category_6(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Category));
                    set_and_get_service_parameter.setApp_Caste_6(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Caste));
                    set_and_get_service_parameter.setRbOption_6(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Belongs_Creamy_Layer_6));
                    set_and_get_service_parameter.setSpReason_6(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reason_for_Creamy_Layer_6));
                    set_and_get_service_parameter.setAnnual_Income(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Annual_Income));
                    set_and_get_service_parameter.setNo_Years_8(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Num_Years_8));
                    set_and_get_service_parameter.setApp_Father_Category_8(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Father_Category_8));
                    set_and_get_service_parameter.setAPP_Father_Caste_8(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Father_Caste_8));
                    set_and_get_service_parameter.setApp_Mother_Category_8(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Mother_Category_8));
                    set_and_get_service_parameter.setAPP_Mother_Caste_8(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Mother_Caste_8));
                    set_and_get_service_parameter.setRemarks_8(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Remarks));
                    set_and_get_service_parameter.setTotal_No_Year_10(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Total_No_Years_10));
                    set_and_get_service_parameter.setNO_Months_10(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.NO_Months_10));
                    set_and_get_service_parameter.setRbStated_Address_10(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reside_At_Stated_Address_10));
                    set_and_get_service_parameter.setRbAddress_RationCard_10(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Place_Match_With_RationCard_10));
                    set_and_get_service_parameter.setSpPurpose_10(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Pur_for_Cert_Code_10));
                    set_and_get_service_parameter.setRbIssue_Cert(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Can_Certificate_Given));
                    set_and_get_service_parameter.setSpRejectionReason(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reason_for_Rejection));
                    set_and_get_service_parameter.setDataUpdateFlag(object.getString(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag));

                    serviceCode = object.getInt(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code);
                    Log.d("serviceCode", "" + serviceCode);

                    Cursor cursor = database1.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME+" where "
                            +DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code+"="+serviceCode, null);
                    if (cursor.getCount()>0) {
                        if (cursor.moveToNext()) {
                            serviceName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_edesc));
                            serviceName_k = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_kdesc));
                            Log.d("serviceName", serviceName+", "+ serviceName_k);
                        }
                    } else {
                        cursor.close();
                    }

//                    if(serviceCode==6){
//                        serviceName = "Caste and Income Certficate";
//                        serviceName_k = "ಜಾತಿ ಮತ್ತು ಆದಾಯ ಪ್ರಮಾಣ ಪತ್ರ ";
//                    }else if(serviceCode==7){
//                        serviceName="Caste Certificate (Cat-A)";
//                        serviceName_k = "ಜಾತಿ ಪ್ರಮಾಣ ಪತ್ರ (ಪ್ರವರ್ಗ-ಎ)";
//                    }else if(serviceCode==8){
//                        serviceName = "Caste Certificate (SC/ST)";
//                        serviceName_k ="ಜಾತಿ ಪ್ರಮಾಣ ಪತ್ರ (ಅ.ಜಾ/ಅ.ಪಂ)";
//                    }else if(serviceCode==9){
//                        serviceName = "OBC Certificate (Central)";
//                        serviceName_k= "ಹಿಂದುಳಿದ ವರ್ಗ ಪ್ರ.ಪತ್ರ (ಕೇ.ಸ)";
//                    }else if (serviceCode==10){
//                        serviceName = "Residence Certificate";
//                        serviceName_k = "ವಾಸ ಸ್ಥಳ ಪ್ರಮಾಣ ಪತ್ರ";
//                    }else {
//                        serviceName=null;
//                        serviceName_k=null;
//                    }

                    set_and_get_service_parameter.setService_Name(serviceName);
                    set_and_get_service_parameter.setService_Name_k(serviceName_k);

                    database.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + "(ST_district_code, ST_taluk_code, ST_hobli_code, ST_va_Circle_Code, ST_village_code, ST_town_code, ST_ward_no, ST_facility_code, Service_Name, Service_Name_k, ST_GSC_No,ST_Eng_Certificate," +
                            " ST_applicant_name, ST_DueDate, ST_Raised_Location, ST_applicant_photo, Applicant_Category, Applicant_Caste, Belongs_Creamy_Layer_6, Reason_for_Creamy_Layer_6, Annual_Income," +
                            " Num_Years_8, App_Father_Category_8, APP_Father_Caste_8, App_Mother_Category_8, APP_Mother_Caste_8, Remarks, " +
                            " Total_No_Years_10, NO_Months_10, Reside_At_Stated_Address_10, Place_Match_With_RationCard_10, Pur_for_Cert_Code_10, Can_Certificate_Given, Reason_for_Rejection, DataUpdateFlag)" +
                            " values ("
                            + set_and_get_service_parameter.getDistrict_Code() + ","
                            + set_and_get_service_parameter.getTaluk_Code() + ","
                            + set_and_get_service_parameter.getHobli_Code() + ","
                            + set_and_get_service_parameter.getVa_Circle_Code() + ","
                            + set_and_get_service_parameter.getVillage_Code()+",'"
                            + set_and_get_service_parameter.getTown_Code()+"','"
                            + set_and_get_service_parameter.getWard_Code()+"','"
                            + set_and_get_service_parameter.getService_Code() + "','"
                            + set_and_get_service_parameter.getService_Name() + "','"
                            + set_and_get_service_parameter.getService_Name_k() + "',"
                            + set_and_get_service_parameter.getRD_No() +",'"
                            + set_and_get_service_parameter.getEng_Certify()+"','"
                            + set_and_get_service_parameter.getApplicant_Name() + "','"
                            + set_and_get_service_parameter.getDue_Date() + "','"
                            + set_and_get_service_parameter.getRaised_Location() +"','"
                            + set_and_get_service_parameter.getST_applicant_photo() + "','"
                            + set_and_get_service_parameter.getAPP_Category_6()+"','"
                            + set_and_get_service_parameter.getApp_Caste_6() + "','"
                            + set_and_get_service_parameter.getRbOption_6() + "','"
                            + set_and_get_service_parameter.getSpReason_6() + "','"
                            + set_and_get_service_parameter.getAnnual_Income() + "','"
                            + set_and_get_service_parameter.getNo_Years_8() + "','"
                            + set_and_get_service_parameter.getApp_Father_Category_8() + "','"
                            + set_and_get_service_parameter.getAPP_Father_Caste_8() + "','"
                            + set_and_get_service_parameter.getApp_Mother_Category_8() + "','"
                            + set_and_get_service_parameter.getAPP_Mother_Caste_8() + "','"
                            + set_and_get_service_parameter.getRemarks_8() + "','"
                            + set_and_get_service_parameter.getTotal_No_Year_10()+"','"
                            + set_and_get_service_parameter.getNO_Months_10() + "','"
                            + set_and_get_service_parameter.getRbStated_Address_10() + "','"
                            + set_and_get_service_parameter.getRbAddress_RationCard_10() + "','"
                            + set_and_get_service_parameter.getSpPurpose_10() + "','"
                            + set_and_get_service_parameter.getRbIssue_Cert() + "','"
                            + set_and_get_service_parameter.getSpRejectionReason()+ "','"
                            + set_and_get_service_parameter.getDataUpdateFlag()+"')");
                    Log.d("Database", "ServiceParameter Table Inserted " + j);
                    j++;

                }
                runOnUiThread(() -> {
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);

                    Cursor cursor3 = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1, null);

                    if(cursor3.getCount()>0) {
                        tData=1;
                        Log.d("entry", String.valueOf(tData));
                        btnProceed.setVisibility(View.VISIBLE);
                        //btnPendency.setVisibility(View.VISIBLE);
                        //Toast.makeText(getApplicationContext(), "Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        cursor3.close();
                        Log.d("Values", "No records Exists");
                        Toast.makeText(getApplicationContext(), getString(R.string.no_data_to_verify), Toast.LENGTH_SHORT).show();
                        tData=0;
                        btnProceed.setVisibility(View.GONE);
                        //btnPendency.setVisibility(View.GONE);
                    }
                    btnDownload.setText(R.string.download);
                    dialog.dismiss();
                });
            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                });
            }catch (OutOfMemoryError e){
                runOnUiThread(() -> {
                    dialog.dismiss();
                    database.close();
                    buildAlertForOutOfMemory();
                    //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                });
                Log.e("OutOfMemoryError", ""+e.toString());
            }catch (NullPointerException e){
                Log.e("NullPointerException", ""+e.toString());
            }
        }
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

    @SuppressLint("StaticFieldLeak")
    class Update_RI_ServiceParameterTable extends AsyncTask<String, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {
            openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_SecondScreen.this);
            database = openHelper.getWritableDatabase();
            //dialog.incrementProgressBy(10);

            Cursor cursor = database.rawQuery("select "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.District_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Taluk_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Hobli_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.va_Circle_Code +","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Category+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Caste+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Belongs_Creamy_Layer_6+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Creamy_Layer_6+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Num_Years_8+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Father_Category_8+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Father_Caste_8+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Mother_Category_8+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Mother_Caste_8+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Remarks+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Total_No_Years_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_NO_Months_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reside_At_Stated_Address_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Place_Match_With_RationCard_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Pur_for_Cert_Code_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Annual_Income+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLat+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLong+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Accepted_VA_information+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Can_Certificate_Given_as_RI+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Rejection_as_RI+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag
                    +" from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1+" where "
                    +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag+"=1", null);
            try {
                if (cursor.getCount() > 0) {

                    if (cursor.moveToFirst()) {
                        do {
                            Log.d("Loop_entering_here", "");
                            Applicant_Id = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No));
                            Log.d("From_DataBase", ""+Applicant_Id);

                            try {
                                District_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.District_Code));
                                Taluk_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Taluk_Code));
                                Hobli_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Hobli_Code));
                                Village_Circle_code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.va_Circle_Code));
                                Village_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code));
                                Town_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code));
                                Ward_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code));
                                Service_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Service_Code));
                                Applicant_Id = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No));
                                RI_Applicant_Category = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Category));
                                RI_Applicant_Caste = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Caste));
                                RI_Belongs_Creamy_Layer_6=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Belongs_Creamy_Layer_6));
                                RI_Reason_for_Creamy_Layer_6= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Creamy_Layer_6));
                                RI_Num_Years_8=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Num_Years_8));
                                RI_App_Father_Category_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Father_Category_8));
                                RI_APP_Father_Caste_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Father_Caste_8));
                                RI_App_Mother_Category_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Mother_Category_8));
                                RI_APP_Mother_Caste_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Mother_Caste_8));
                                RI_Remarks=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Remarks));
                                RI_Total_No_Years_10 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Total_No_Years_10));
                                RI_NO_Months_10 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_NO_Months_10));
                                RI_Reside_At_Stated_Address_10=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reside_At_Stated_Address_10));
                                RI_Place_Match_With_RationCard_10=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Place_Match_With_RationCard_10));
                                RI_Pur_for_Cert_Code_10= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Pur_for_Cert_Code_10));
                                RI_Annual_Income= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Annual_Income));
                                RI_vLat = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLat));
                                RI_vLong = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLong));
                                RI_Accepted_VA_information = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Accepted_VA_information));
                                RI_Can_Certificate_Given = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Can_Certificate_Given_as_RI));
                                RI_Reason_for_Rejection = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Rejection_as_RI));
                                RI_DataUpdateFlag = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag));

                                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE2, OPERATION_NAME2);

                                request.addProperty("District_Code", District_Code);
                                request.addProperty("Taluk_Code", Taluk_Code);
                                request.addProperty("Hobli_Code", Hobli_Code);
                                request.addProperty("va_Circle_Code", Village_Circle_code);
                                request.addProperty("Village_Code", Village_Code);
                                request.addProperty("Town_Code",Town_Code);
                                request.addProperty("Ward_Code",Ward_Code);
                                request.addProperty("Service_Code", Service_Code);
                                request.addProperty("Applicant_Id", Applicant_Id);
                                request.addProperty("RI_Applicant_Category", RI_Applicant_Category);
                                request.addProperty("RI_Applicant_Caste", RI_Applicant_Caste);
                                request.addProperty("RI_Belongs_Creamy_Layer_6", RI_Belongs_Creamy_Layer_6);
                                request.addProperty("RI_Reason_for_Creamy_Layer_6", RI_Reason_for_Creamy_Layer_6);
                                request.addProperty("RI_Num_Years_8", RI_Num_Years_8);
                                request.addProperty("RI_App_Father_Category_8", RI_App_Father_Category_8);
                                request.addProperty("RI_APP_Father_Caste_8", RI_APP_Father_Caste_8);
                                request.addProperty("RI_App_Mother_Category_8", RI_App_Mother_Category_8);
                                request.addProperty("RI_APP_Mother_Caste_8", RI_APP_Mother_Caste_8);
                                request.addProperty("RI_Remarks", RI_Remarks);
                                request.addProperty("RI_Total_No_Years_10", RI_Total_No_Years_10);
                                request.addProperty("RI_NO_Months_10", RI_NO_Months_10);
                                request.addProperty("RI_Reside_At_Stated_Address_10", RI_Reside_At_Stated_Address_10);
                                request.addProperty("RI_Place_Match_With_RationCard_10", RI_Place_Match_With_RationCard_10);
                                request.addProperty("RI_Pur_for_Cert_Code_10", RI_Pur_for_Cert_Code_10);
                                request.addProperty("RI_Annual_Income", RI_Annual_Income);
                                request.addProperty("RI_vLat", RI_vLat);
                                request.addProperty("RI_vLong", RI_vLong);
                                request.addProperty("RI_Accepted_VA_information", RI_Accepted_VA_information);
                                request.addProperty("RI_Can_Certificate_Given", RI_Can_Certificate_Given);
                                request.addProperty("RI_Reason_for_Rejection", RI_Reason_for_Rejection);
                                request.addProperty("RI_DataUpdateFlag", RI_DataUpdateFlag);

                                Log.d("Request","" + request);

                                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                                envelope.dotNet = true;
                                envelope.setOutputSoapObject(request);

                                androidHttpTransport = new HttpTransportSE(getString(R.string.SOAP_ADDRESS));
                                androidHttpTransport.call(SOAP_ACTION2, envelope);
                                resultString = (SoapPrimitive) envelope.getResponse();
                                Log.i("Result", ""+resultString);
                                resultFromServer = String.valueOf(resultString);
                                if(resultFromServer.equals("0")) {
                                    runOnUiThread(() -> {
                                        //Toast.makeText(getApplicationContext(), "Data Uploaded Successfully" , Toast.LENGTH_SHORT).show();
                                        Log.d("Request_", "Update_RI_ServiceParameterTable" + "Data Uploaded Successfully");
                                    });
                                    database.execSQL("delete from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "=" + Applicant_Id);
                                    Log.d("Local_Result", "A row deleted Successfully");
                                }
                                else {
                                    Log.d("Request_", "Update_RI_ServiceParameterTable" +" Data not uploaded");
                                }

                            } catch (Exception e) {
                                Log.i("Error1", String.valueOf(e));
                                runOnUiThread(() -> {
                                    Toast.makeText(getApplicationContext(), R.string.server_exception, Toast.LENGTH_SHORT).show();
                                    Log.d("Update_RI_SerParamTable", "Server Exception Occurred");
                                });
                            }
                        }while (cursor.moveToNext());
                    }

                } else {
                    cursor.close();
                    runOnUiThread(() -> {
                        //Toast.makeText(getApplicationContext(), "There is no Updated data to Upload in Server " , Toast.LENGTH_SHORT).show();
                        Log.d("Update_RI_SerParamTable", "There is no Updated data to Upload in Server");
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Update_RI_SerParamTbla", "ExceptionArrived ");
            }
            //Toast.makeText(getApplicationContext(), result , Toast.LENGTH_SHORT).show();

            return "InBackground";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        protected void onProgressUpdate(Integer... a) {

        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.pwd.setText("");
        buildAlertMessageGoingBack();
    }
}
