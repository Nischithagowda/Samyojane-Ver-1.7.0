package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;
import android.net.ConnectivityManager;
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
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SecondScreen extends AppCompatActivity {


    //private static final String url_facility = "http://164.100.133.30/NK_MobileApp/WebService.asmx/Get_Facility_Services";
//    private static final String url_village_name = "http://164.100.133.30/NK_MobileApp/WebService.asmx/Get_Village_Name";
//    private static final String url_service_Tran_data = "http://164.100.133.30/NK_MobileApp/WebService.asmx/Get_Service_tran_data";
    HashMap<String, String> hashMap_facility;
    HashMap<String, String> hashMap_village_name;
    HashMap<String, String> hashMap_service_Tran_data;

//    String SOAP_ACTION1 = "http://tempuri.org/Update_ServiceTranTable ";
//    public final String OPERATION_NAME1 = "Update_ServiceTranTable";  //Method_name
//    public final String WSDL_TARGET_NAMESPACE1 = "http://tempuri.org/";  // NAMESPACE
//    String SOAP_ADDRESS1 = "http://164.100.133.47/NK_MobileApp/WebService.asmx";

    String SOAP_ACTION2 = "http://tempuri.org/Insert_ServiceParameterTable ";
    public final String OPERATION_NAME2 = "Insert_ServiceParameterTable";  //Method_name
    public final String WSDL_TARGET_NAMESPACE2 = "http://tempuri.org/";  // NAMESPACE
    //String SOAP_ADDRESS2 = "http://164.100.133.30/NK_MobileApp/WebService.asmx";

    TextView proceed, tvDistrict, tvTaluk, tvHobli, tvVA_Circle_Name, tvVA_Name, tvTimerValue, VAModule;
    Button btnDownload, btnPendency, btnUpload, btnProceed;
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database, database1;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code;
    //private static final String url_service_Parameter_Data = "http://164.100.133.123:9600/Bhoomi_download/WebService.asmx/Get_Service_Parameters_data";
    Set_and_Get_Facility_Services set_and_get_facility_services;
    Set_and_Get_Village_Name set_and_get_village_name;
    Set_and_Get_Service_tran_data set_and_get_service_tran_data;
    int fData=0, vData=0, tData=0;
    ProgressDialog dialog;
    String VA_Accepts_Applicant_information, name, fatherName, motherName,RationCard_No, Aadhar_NO, Address1, Address2, Address3, PinCode, Eng_Certif, GSC_FirstPart, Report_No, Aadhaar_Photo, Mobile_No, DataUpdateFlag;
    String District_Code, Taluk_Code, Hobli_Code, Village_Circle_code, Village_Code, Habitation_Code, Town_Code, Ward_Code, Service_Code, Applicant_Id;
    String Annual_Income, Photo, vLat, vLong;
    GPSTracker gpsTracker;
    double latitude, longitude;
    int flag;
    int serviceCode;
    String serviceName, serviceName_k;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;
    String gsc_firstPart, gsc_firstPart_Name=null;
    int j=1;
    AlertDialog alert;
    String localeName;

    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    String Can_Certificate_Given, Reason_for_Rejection, ST_applicant_photo;
    //Service Parameters of service_code-6
    String Applicant_Category, Applicant_Caste, Belongs_Creamy_Layer_6 , Reason_for_Creamy_Layer_6;
    //Service Parameters of service_code-8
    String Num_Years_8, App_Father_Category_8, APP_Father_Caste_8, App_Mother_Category_8, APP_Mother_Caste_8,  Remarks;
    //Service Parameters of service_code-10
    String Total_No_Years_10, NO_Months_10, Reside_At_Stated_Address_10, Place_Match_With_RationCard_10, Pur_for_Cert_Code_10;

    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String resultFromServer;
    String IMEI_Num, mob_Num;

//    String SOAP_ACTION3 = "http://tempuri.org/DeleteDataUploadedFlag ";   //Truncate_Service_Parameters_data
//    public final String OPERATION_NAME3 = "DeleteDataUploadedFlag";  //Method_name
//    public final String WSDL_TARGET_NAMESPACE3 = "http://tempuri.org/";  // NAMESPACE
//    String SOAP_ADDRESS3 = "http://164.100.133.123:9600/Bhoomi_download/WebService.asmx";

    @SuppressLint({"MissingPermission", "HardwareIds", "SetTextI18n"})
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.second_screen);

        Log.d("MissingPermissionPermi", "Enter");

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
                    i.putExtra("districtCode", district);
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
                i.putExtra("districtCode", district);
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
            i.putExtra("districtCode", district);
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
        database = openHelper.getWritableDatabase();

        @SuppressLint("Recycle") Cursor cursor12 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                +" where "+DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+" is null",null);
        if (cursor12.getCount()>0){
            btnProceed.setVisibility(View.VISIBLE);
        }else {
            btnProceed.setVisibility(View.GONE);
        }
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isNetworkAvailable()) {
//                    new Delete_DataUpdatedFlag().execute();
//                    openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(SecondScreen.this);
//                    database = openHelper.getWritableDatabase();
//                    database.execSQL("update "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" set "+DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=null where "
//                            +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1");
//                    database.execSQL("update "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_member_id+" set "+DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=null where "
//                            +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1");
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
//                    openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(SecondScreen.this);
//                    database = openHelper.getWritableDatabase();
//                    database.execSQL("update "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" set "+DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=null where "
//                            +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1");
//                    database.execSQL("update "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_member_id+" set "+DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=null where "
//                            +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1");
//                }
//            }
//        });

        Intent i = getIntent();
        district = i.getStringExtra("district");
        taluk = i.getStringExtra("taluk");
        hobli = i.getStringExtra("hobli");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
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

        openHelper = new DataBaseHelperClass_Credentials(SecondScreen.this);
        database = openHelper.getWritableDatabase();

        @SuppressLint("Recycle")
        final Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_Credentials.TABLE_NAME+" where "
                + getString(R.string.cre_district_name)+"='"+district+"' and "
                + getString(R.string.cre_taluk_name)+"='"+taluk+"' and "
                + getString(R.string.cre_hobli_name)+"='"+hobli+"' and "
                + getString(R.string.cre_va_circle_name)+"='"+VA_Circle_Name+"'", null);
        if(cursor.getCount()>0) {
            if (cursor.moveToNext()) {
                district_Code = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_Credentials.District_Code));
                taluk_Code = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Code));
                hobli_Code = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Code));
                va_Circle_Code = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_Credentials.VA_circle_Code));
                flag = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.flag)));
                database.close();
            }
        }else {
            database.close();
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

        hashMap_facility = new HashMap<>();
        hashMap_village_name = new HashMap<>();
        hashMap_service_Tran_data = new HashMap<>();

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

                new InsertServiceParameterTable_Server().execute();

                new GetFacilityServiceFromServer().execute(getString(R.string.url_facility));

                hashMap_village_name.put("District_Code", district_Code);
                hashMap_village_name.put("Taluk_Code", taluk_Code);
                hashMap_village_name.put("Hobli_Code", hobli_Code);
                hashMap_village_name.put("VA_Cicle_Code", va_Circle_Code );
                Log.d("hashMap_village_name",""+hashMap_village_name+", URL:"+getString(R.string.url_village_name));
                new GetVillageNameFromServer().execute(getString(R.string.url_village_name));

                hashMap_service_Tran_data.put("District_Code", district_Code);
                hashMap_service_Tran_data.put("Taluk_Code", taluk_Code);
                hashMap_service_Tran_data.put("Hobli_Code", hobli_Code);
                hashMap_service_Tran_data.put("VA_circle_code", va_Circle_Code );
                Log.d("hashMap_ser_Tran_data",""+hashMap_service_Tran_data+", URL:"+getString(R.string.url_service_Tran_data));
                new GetServiceTrandataFromServer().execute(getString(R.string.url_service_Tran_data));
            }
            else {
                openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(SecondScreen.this);
                database = openHelper.getWritableDatabase();
                @SuppressLint("Recycle")
                Cursor cursor1=database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME, null);

                if (cursor1.getCount()>0){
                    fData=1;

                    openHelper = new DataBaseHelperClass_VillageNames(SecondScreen.this);
                    database = openHelper.getWritableDatabase();
                    @SuppressLint("Recycle")
                    Cursor cursor2 = database.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME, null);

                    if(cursor2.getCount()>0) {
                        vData=1;
                        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(SecondScreen.this);
                        database = openHelper.getWritableDatabase();
                        @SuppressLint("Recycle")
                        Cursor cursor3 = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME, null);

                        if(cursor3.getCount()>0) {
                            tData=1;
                            btnProceed.setVisibility(View.VISIBLE);
                            //btnPendency.setVisibility(View.VISIBLE);
                            //Toast.makeText(getApplicationContext(), "Data already exist", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
                            database.close();
                        }
                        else {
                            tData=0;
                            Log.d("Values", "No records Exists");
                            btnProceed.setVisibility(View.GONE);
                            //btnPendency.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                            database.close();
                        }
                    }
                    else {
                        vData=0;
                        database.close();
                    }
                }
                else {
                    fData=0;
                    database.close();
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
        openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(SecondScreen.this);
        database = openHelper.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME);
            database.close();
            Log.d("Database", "FacilityMaster Database Truncated");
        }else {
            database.close();
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

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {

                dialog.incrementProgressBy(5);

                truncateDatabase_facility();

                openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(SecondScreen.this);
                database = openHelper.getWritableDatabase();

                JSONArray array = jsonObject.getJSONArray("data");
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
                dialog.incrementProgressBy(2);
                database.close();
            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    database.close();
                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                });
            }catch (OutOfMemoryError e){
                Log.e("OutOfMemoryError", ""+e.toString());
            }catch (NullPointerException e){
                Log.e("NullPointerException", ""+e.toString());
            }
            //Toast.makeText(getApplicationContext(), "FacilityMaster Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void truncateDatabase_village_name(){
        dialog.incrementProgressBy(11);
        openHelper = new DataBaseHelperClass_VillageNames(SecondScreen.this);
        database = openHelper.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_VillageNames.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_VillageNames.TABLE_NAME);
            database.close();
            Log.d("Database", "VillageNames Database Truncated");
        }else {database.close();}

    }

    @SuppressLint("StaticFieldLeak")
    class GetVillageNameFromServer extends AsyncTask<String, Void, JSONObject> {
        JSONObject jsonObject;

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JParserAdv jParserAdv = new JParserAdv();
                jsonObject = jParserAdv.makeHttpRequest(getString(R.string.url_village_name), "POST", hashMap_village_name);
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

                dialog.incrementProgressBy(10);
                truncateDatabase_village_name();
                openHelper = new DataBaseHelperClass_VillageNames(SecondScreen.this);
                database = openHelper.getWritableDatabase();

                JSONArray array = jsonObject.getJSONArray("data");

                int count = array.length();
                for (int i = 0; i < count; i++) {

                    JSONObject object = array.getJSONObject(i);

                    set_and_get_village_name = new Set_and_Get_Village_Name();
                    set_and_get_village_name.setVCM_va_circle_code(object.getString(DataBaseHelperClass_VillageNames.VCM_va_circle_code));
                    set_and_get_village_name.setVCM_va_circle_ename(object.getString(DataBaseHelperClass_VillageNames.VCM_va_circle_ename));
                    set_and_get_village_name.setVCM_va_circle_kname(object.getString(DataBaseHelperClass_VillageNames.VCM_va_circle_kname));
                    set_and_get_village_name.setHM_village_code(object.getString(DataBaseHelperClass_VillageNames.HM_village_code));
                    set_and_get_village_name.setHM_habitation_code(object.getString(DataBaseHelperClass_VillageNames.HM_habitation_code));
                    set_and_get_village_name.setHM_habitation_ename(object.getString(DataBaseHelperClass_VillageNames.HM_habitation_ename));
                    set_and_get_village_name.setHM_habitation_kname(object.getString(DataBaseHelperClass_VillageNames.HM_habitation_kname));


                    database.execSQL("insert into " + DataBaseHelperClass_VillageNames.TABLE_NAME
                            + "(VCM_va_circle_code,VCM_va_circle_ename, VCM_va_circle_kname,HM_village_code, HM_habitation_code, HM_habitation_ename, HM_habitation_kname) values ("
                            + set_and_get_village_name.getVCM_va_circle_code() +",'"+set_and_get_village_name.getVCM_va_circle_ename()+"','"+set_and_get_village_name.getVCM_va_circle_kname()+"',"
                            + set_and_get_village_name.getHM_village_code() +","+ set_and_get_village_name.getHM_habitation_code()+",'"+ set_and_get_village_name.getHM_habitation_ename()+"','"
                            + set_and_get_village_name.getHM_habitation_kname()+"')");
                    Log.d("Database", "VillageNames Database Inserted");

                }
                dialog.incrementProgressBy(10);
                database.close();
                final int totalProgressTime = 100;
                final Thread t = new Thread() {
                    @Override
                    public void run() {
                        int jumpTime = 43;

                        while(jumpTime < totalProgressTime) {
                            try {
                                sleep(2000);
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

            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    database.close();
                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                });
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

    public void truncateDatabase_Service_Tran_data(){
        //dialog.incrementProgressBy(10);
        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(SecondScreen.this);
        database = openHelper.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME);
            Log.d("Database", "ServiceTranTable Database Truncated");
            database.close();
        }else {
            database.close();
        }

    }

    @SuppressLint("StaticFieldLeak")
    class GetServiceTrandataFromServer extends AsyncTask<String, Integer, JSONObject> {
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
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    btnDownload.setText(R.string.download);
                    buildAlertForOutOfMemory();
                    //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                });
            }catch (NullPointerException e){
                dialog.dismiss();
                runOnUiThread(() -> {
                    dialog.dismiss();
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    btnDownload.setText(R.string.download);
                    //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                });
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
                //dialog.incrementProgressBy(30);
                JSONArray array = jsonObject.getJSONArray("data");

                int count = array.length();
//                int old_num = 90;
//                int New_num = count;
//                int Increase;
//                if(old_num<New_num) {
//                    Increase = New_num - old_num;
//                    Log.d("Increase", "+"+Increase+" = "+ New_num+"-"+ old_num);
//                }else {
//                    Increase = old_num-New_num;
//                    Log.d("Increase", "+"+Increase+" = "+ old_num+"-"+ New_num);
//                }
//                double div = ((double)Increase/(double)New_num);
//                Log.d("cal", ""+div+"="+Increase+"/"+New_num);
//                final double Per_Increase = div*100;
//                Log.d("Per_Increase", ""+Per_Increase);

                truncateDatabase_Service_Tran_data();

                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(SecondScreen.this);
                database = openHelper.getWritableDatabase();

                openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(SecondScreen.this);
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

                    serviceCode = object.getInt(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code);

//                    openHelper = new DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(SecondScreen.this);
//                    database = openHelper.getWritableDatabase();

//                    @SuppressLint("Recycle")
//                    Cursor cursor = database1.rawQuery("select * from "+DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.TABLE_NAME+" where "
//                            +DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_code+"="+serviceCode, null);
//                    if (cursor.getCount()>0) {
//                        if (cursor.moveToNext()) {
//                            serviceName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster.FM_facility_edesc));
//                            Log.d("serviceName", serviceName);
//                        }
//                    }
                    if(serviceCode==6){
                        serviceName = "Caste and Income Certficate";
                        serviceName_k = "ಜಾತಿ ಮತ್ತು ಆದಾಯ ಪ್ರಮಾಣ ಪತ್ರ ";
                    }else if(serviceCode==7){
                        serviceName="Caste Certificate (Cat-A)";
                        serviceName_k = "ಜಾತಿ ಪ್ರಮಾಣ ಪತ್ರ (ಪ್ರವರ್ಗ-ಎ)";
                    }else if(serviceCode==8){
                        serviceName = "Caste Certificate (SC/ST)";
                        serviceName_k ="ಜಾತಿ ಪ್ರಮಾಣ ಪತ್ರ (ಅ.ಜಾ/ಅ.ಪಂ)";
                    }else if(serviceCode==9){
                        serviceName = "OBC Certificate (Central)";
                        serviceName_k= "ಹಿಂದುಳಿದ ವರ್ಗ ಪ್ರ.ಪತ್ರ (ಕೇ.ಸ)";
                    }else if (serviceCode==10){
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
                database.close();
                database1.close();
                runOnUiThread(() -> {
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(SecondScreen.this);
                    database = openHelper.getWritableDatabase();
                    @SuppressLint("Recycle")
                    Cursor cursor3 = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME, null);

                    if(cursor3.getCount()>0) {
                        tData=1;
                        btnProceed.setVisibility(View.VISIBLE);
                        //btnPendency.setVisibility(View.VISIBLE);
                        btnDownload.setText(R.string.download);
                        dialog.dismiss();
                        //Toast.makeText(getApplicationContext(), "Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
                        database.close();
                        database1.close();
                    }
                    else {
                        tData=0;
                        Log.d("Values", "No records Exists");
                        Toast.makeText(getApplicationContext(), R.string.no_data_to_verify, Toast.LENGTH_SHORT).show();
                        btnDownload.setText(R.string.download);
                        btnProceed.setVisibility(View.GONE);
                        //btnPendency.setVisibility(View.GONE);
                        dialog.dismiss();
                        database.close();
                        database1.close();
                    }
                });
            }catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    dialog.dismiss();
                    database.close();
                    database1.close();
                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                });
            }catch (OutOfMemoryError e){
                runOnUiThread(() -> {
                    dialog.dismiss();
                    database.close();
                    database1.close();
                    buildAlertForOutOfMemory();
                    //Toast.makeText(getApplicationContext(), "Out of Memory", Toast.LENGTH_SHORT).show();
                });
                Log.e("OutOfMemoryError2", ""+e.toString());
            }catch (NullPointerException e){
                Log.e("NullPointerException2", ""+e.toString());
            }
        }

    }

    private Runnable updateTimerThread = new Runnable() {

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
    class InsertServiceParameterTable_Server extends AsyncTask<String, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {
            openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(SecondScreen.this);
            database = openHelper.getWritableDatabase();
            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery("select "+DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.va_Circle_Code +","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Mother+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.U_Mobile_No+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.U_RationCard_No+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address1+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address2+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address3+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.ST_GSCFirstPart+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Report_No+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.UID+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.AadhaarPhoto+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Category+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Caste+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Belongs_Creamy_Layer_6+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Creamy_Layer_6+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Num_Years_8+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.App_Father_Category_8+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Father_Caste_8+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.App_Mother_Category_8+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Mother_Caste_8+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Remarks+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Total_No_Years_10+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.NO_Months_10+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Reside_At_Stated_Address_10+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Place_Match_With_RationCard_10+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Pur_for_Cert_Code_10+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Annual_Income+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Photo+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.vLat+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.vLong+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Can_Certificate_Given+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Rejection+","
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag
                    +" from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1+" where "
                    +DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1", null);
            try {
                if (cursor.getCount() > 0) {

                    if (cursor.moveToFirst()) {
                        do {
                            Log.d("Loop_entering_here", "");

                            try {
                                District_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code));
                                Taluk_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code));
                                Hobli_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code));
                                Village_Circle_code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.va_Circle_Code));
                                Village_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code));
                                Habitation_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Habitation_code));
                                Town_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code));
                                Ward_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code));
                                Service_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code));
                                Applicant_Id = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No));
                                VA_Accepts_Applicant_information = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information));
                                name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name));
                                fatherName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Father_Name));
                                motherName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Mother));
                                Mobile_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.U_Mobile_No));
                                RationCard_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.U_RationCard_No));
                                Address1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address1));
                                Address2 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address2));
                                Address3 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address3));
                                PinCode = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode));
                                Eng_Certif = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate));
                                GSC_FirstPart = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_GSCFirstPart));
                                Report_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Report_No));
                                Aadhar_NO = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UID));
                                Aadhaar_Photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.AadhaarPhoto));
                                Applicant_Category = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Category));
                                Applicant_Caste = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Caste));
                                Belongs_Creamy_Layer_6=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Belongs_Creamy_Layer_6));
                                Reason_for_Creamy_Layer_6= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Creamy_Layer_6));
                                Num_Years_8=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Num_Years_8));
                                App_Father_Category_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.App_Father_Category_8));
                                APP_Father_Caste_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Father_Caste_8));
                                App_Mother_Category_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.App_Mother_Category_8));
                                APP_Mother_Caste_8= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Mother_Caste_8));
                                Remarks=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Remarks));
                                Total_No_Years_10 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Total_No_Years_10));
                                NO_Months_10 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.NO_Months_10));
                                Reside_At_Stated_Address_10=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Reside_At_Stated_Address_10));
                                Place_Match_With_RationCard_10=cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Place_Match_With_RationCard_10));
                                Pur_for_Cert_Code_10= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Pur_for_Cert_Code_10));
                                Annual_Income= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Annual_Income));
                                Photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Photo));
                                vLat = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.vLat));
                                vLong = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.vLong));
                                Can_Certificate_Given = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Can_Certificate_Given));
                                Reason_for_Rejection = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Rejection));
                                DataUpdateFlag = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag));

                                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE2, OPERATION_NAME2);

                                request.addProperty("District_Code", District_Code);
                                request.addProperty("Taluk_Code", Taluk_Code);
                                request.addProperty("Hobli_Code", Hobli_Code);
                                request.addProperty("va_Circle_Code", Village_Circle_code);
                                request.addProperty("Village_Code", Village_Code);
                                request.addProperty("Habitation_code",Habitation_Code);
                                request.addProperty("Town_Code", Town_Code);
                                request.addProperty("Ward_Code", Ward_Code);
                                request.addProperty("Service_Code", Service_Code);
                                request.addProperty("Applicant_Id", Applicant_Id);
                                request.addProperty("VA_Accepts_Applicant_information",VA_Accepts_Applicant_information);
                                request.addProperty("Applicant_Name", name);
                                request.addProperty("Father_Name", fatherName);
                                request.addProperty("Mother_Name", motherName);
                                request.addProperty("Mobile_No", Mobile_No);
                                request.addProperty("RationCard_No", RationCard_No);
                                request.addProperty("Address1", Address1);
                                request.addProperty("Address2", Address2);
                                request.addProperty("Address3", Address3);
                                request.addProperty("PinCode", PinCode);
                                request.addProperty("ST_Eng_Certificate",Eng_Certif);
                                request.addProperty("ST_GSCFirstPart", GSC_FirstPart);
                                request.addProperty("Report_No", Report_No);
                                request.addProperty("Aadhar_NO", Aadhar_NO);
                                request.addProperty("Aadhaar_Photo", Aadhaar_Photo);
                                request.addProperty("ST_applicant_photo", ST_applicant_photo);
                                request.addProperty("Applicant_Category", Applicant_Category);
                                request.addProperty("Applicant_Caste", Applicant_Caste);
                                request.addProperty("Belongs_Creamy_Layer_6", Belongs_Creamy_Layer_6);
                                request.addProperty("Reason_for_Creamy_Layer_6", Reason_for_Creamy_Layer_6);
                                request.addProperty("Num_Years_8", Num_Years_8);
                                request.addProperty("App_Father_Category_8", App_Father_Category_8);
                                request.addProperty("APP_Father_Caste_8", APP_Father_Caste_8);
                                request.addProperty("App_Mother_Category_8", App_Mother_Category_8);
                                request.addProperty("APP_Mother_Caste_8", APP_Mother_Caste_8);
                                request.addProperty("Remarks", Remarks);
                                request.addProperty("Total_No_Years_10", Total_No_Years_10);
                                request.addProperty("NO_Months_10", NO_Months_10);
                                request.addProperty("Reside_At_Stated_Address_10", Reside_At_Stated_Address_10);
                                request.addProperty("Place_Match_With_RationCard_10", Place_Match_With_RationCard_10);
                                request.addProperty("Pur_for_Cert_Code_10", Pur_for_Cert_Code_10);
                                request.addProperty("Annual_Income", Annual_Income);
                                request.addProperty("Photo", Photo);
                                request.addProperty("vLat", vLat);
                                request.addProperty("vLong", vLong);
                                request.addProperty("Can_Certificate_Given", Can_Certificate_Given);
                                request.addProperty("Reason_for_Rejection", Reason_for_Rejection);
                                request.addProperty("DataUpdateFlag", DataUpdateFlag);

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
                                        Log.d("Request_", "UpdateServiceParameterTable" + "Data Uploaded Successfully");
                                    });

                                    database.execSQL("delete from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where " + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + Applicant_Id);
                                    Log.d("Local_Result", "A row deleted Successfully");
                                }
                                else {
                                    Log.d("Request_", "UpdateServiceParameterTable" +" Data not uploaded");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.i("Error1", e.getMessage());
                                runOnUiThread(() -> {
                                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show();
                                    Log.d("InsertServiceParaTable", "Server Exception Occurred");
                                });
                            }
                        }while (cursor.moveToNext());
                    }else {
                        database.close();
                    }

                } else {
                    runOnUiThread(() -> {
                        //Toast.makeText(getApplicationContext(), "There is no Updated data to Upload in Server " , Toast.LENGTH_SHORT).show();
                        Log.d("InsertServiceParaTable", "There is no Updated data to Upload in Server");
                        database.close();
                    });
                }

            } catch (Exception e) {
                Log.d("InsertServiceParaTable1", e.getMessage());
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
