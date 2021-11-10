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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class RI_UploadScreen extends AppCompatActivity {

    String SOAP_ACTION2 = "http://tempuri.org/Update_RI_ServiceParameterTable_Auto";
    public final String OPERATION_NAME2 = "Update_RI_ServiceParameterTable_Auto";  //Method_name
    public final String WSDL_TARGET_NAMESPACE2 = "http://tempuri.org/";  // NAMESPACE
    //String SOAP_ADDRESS2 = "http://164.100.133.30/NK_MobileApp/WebService.asmx";

    Button btnUpload, btnok;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    String RI_Report_No, RI_DataUpdateFlag, District_Code, Taluk_Code, Hobli_Code, Village_Circle_code, Village_Code, Town_Code, Ward_Code, Service_Code,Applicant_Id;
    String RI_Annual_Income, RI_vLat, RI_vLong, RI_Accepted_VA_information;
    ProgressDialog dialog;
    TextView tvTotalUpload, tvAlreadyUploaded, tvNotUploaded, tvAfterUploaded;
    int count_TotalCaptured=0, count_AfterUpload=0, count_BalanceRecord;
    int i=0;
    String RI_Can_Certificate_Given, RI_Reason_for_Rejection;
    //Service Parameters of service_code-6
    String RI_Applicant_Category, RI_Applicant_Caste, RI_Belongs_Creamy_Layer_6, RI_Reason_for_Creamy_Layer_6;
    //Service Parameters of service_code-8
    String RI_Num_Years_8, RI_App_Father_Category_8, RI_APP_Father_Caste_8, RI_App_Mother_Category_8, RI_APP_Mother_Caste_8, RI_Remarks;
    //Service Parameters of service_code-10
    String RI_Total_No_Years_10, RI_NO_Months_10,RI_Reside_At_Stated_Address_10, RI_Place_Match_With_RationCard_10, RI_Pur_for_Cert_Code_10;

    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String resultFromServer;

    String Updated_By_RI_Name, Updated_By_RI_IMEI, Updated_By_RI_MobileNum;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_upload_screen);

        btnUpload=findViewById(R.id.btnUpload);
        btnok = findViewById(R.id.btnOk);
        btnok.setVisibility(View.GONE);

        tvTotalUpload = findViewById(R.id.tvTotalUpload);
        tvAlreadyUploaded = findViewById(R.id.tvAlreadyUploaded);
        tvNotUploaded = findViewById(R.id.tvNotUploaded);
        tvAfterUploaded = findViewById(R.id.tvAfterUploaded);
        tvAfterUploaded.setVisibility(View.GONE);

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        Intent i = getIntent();
        Updated_By_RI_Name = i.getStringExtra("RI_Name");
        Updated_By_RI_IMEI = i.getStringExtra("IMEI_Num");
        Updated_By_RI_MobileNum = i.getStringExtra("mob_Num");

        Log.d("Updated_By_RI_Name", ""+Updated_By_RI_Name);
        Log.d("Updated_By_RI_IMEI", ""+Updated_By_RI_IMEI);
        Log.d("Updated_By_RI_MobileNum", ""+Updated_By_RI_MobileNum);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_UploadScreen.this);
        database = openHelper.getWritableDatabase();

        final Cursor cursor = database.rawQuery("SELECT * FROM " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1
                + " where "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag+" is not null and "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag + " is not null ", null);

        count_TotalCaptured=cursor.getCount();
        count_BalanceRecord=cursor.getCount();

        if(count_TotalCaptured>0){
            if(cursor.moveToFirst()){
                do {
                    Log.d("tvTotalUpload", ""+ i);
                }while(cursor.moveToNext());
            }
            tvTotalUpload.setText(String.valueOf(count_TotalCaptured));
            tvAlreadyUploaded.setText(String.valueOf(count_AfterUpload));
            tvNotUploaded.setText(String.valueOf(count_BalanceRecord));
        }
        else {
            cursor.close();
            tvTotalUpload.setText("0");
            tvAlreadyUploaded.setText("0");
            tvNotUploaded.setText("0");
            btnUpload.setVisibility(View.GONE);
            btnok.setVisibility(View.VISIBLE);
            tvAfterUploaded.setText(getString(R.string.no_data_to_upload));
            tvAfterUploaded.setVisibility(View.VISIBLE);
        }

        btnUpload.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                dialog.show();
                tvAfterUploaded.setVisibility(View.GONE);
                openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_UploadScreen.this);
                database = openHelper.getWritableDatabase();

                new Update_RI_ServiceParameterTable().execute();
            }
            else {
                buildAlertMessageConnection();
                //Toast.makeText(getApplicationContext(), "Internet Connection was not Available\nPlease try again", Toast.LENGTH_SHORT).show();
            }
        });

        btnok.setOnClickListener(v -> onBackPressed());

//        try {
//            @SuppressLint("SdCardPath")
//            File sd = new File("/sdcard/Samyojane/databases/");
//            sd.mkdirs();
//
//            File currentFile = new File(Environment.getDataDirectory(), "//data//" + getApplicationContext().getPackageName() + "//databases//" + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DATABASE_NAME);
//
//            File backupFile = new File(sd, DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DATABASE_NAME);
//            backupFile.delete();
//            backupFile.createNewFile();
//
//            FileInputStream fis = new FileInputStream(currentFile);
//            FileOutputStream fos = new FileOutputStream(backupFile);
//
//            ByteStreams.copy(fis, fos);
//
//            fis.close();
//            fos.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
                    Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                    tvAfterUploaded.setText(getString(R.string.internet_not_avail));
                    tvAfterUploaded.setVisibility(View.VISIBLE);
                    dialog.cancel();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @SuppressLint("StaticFieldLeak")
    class Update_RI_ServiceParameterTable extends AsyncTask<String, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {
            openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_UploadScreen.this);
            database = openHelper.getWritableDatabase();

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
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Report_No+","
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
                                RI_Report_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Report_No));
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
                                request.addProperty("RI_Report_No", RI_Report_No);
                                request.addProperty("RI_DataUpdateFlag", RI_DataUpdateFlag);
                                request.addProperty("Updated_By_RI_IMEI", Updated_By_RI_IMEI);
                                request.addProperty("Updated_By_RI_MobileNum",Updated_By_RI_MobileNum);
                                request.addProperty("Updated_By_RI_Name", Updated_By_RI_Name);

                                Log.d("Request","" + request);

                                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                                envelope.dotNet = true;
                                envelope.setOutputSoapObject(request);

                                androidHttpTransport = new HttpTransportSE(getString(R.string.SOAP_ADDRESS));
                                Log.d("URL","URL: "+getString(R.string.SOAP_ADDRESS));
                                androidHttpTransport.call(SOAP_ACTION2, envelope);
                                resultString = (SoapPrimitive) envelope.getResponse();
                                Log.i("Result", ""+resultString);
                                resultFromServer = String.valueOf(resultString);
                                if(resultFromServer.equals("0")) {
                                    runOnUiThread(() -> {
                                        //Toast.makeText(getApplicationContext(), "Data Uploaded Successfully" , Toast.LENGTH_SHORT).show();
                                        Log.d("Request_", "Update_RI_ServiceParameterTable" + "Data Uploaded Successfully");
                                        count_AfterUpload++;
                                        count_BalanceRecord--;
                                        tvTotalUpload.setText(String.valueOf(count_TotalCaptured));
                                        tvAlreadyUploaded.setText(String.valueOf(count_AfterUpload));
                                        tvNotUploaded.setText(String.valueOf(count_BalanceRecord));
                                        Log.d("Count_of_Records", "count_TotalCaptured : " + count_TotalCaptured + "\ncount_AfterUpload : " + count_AfterUpload + "\ncount_AfterUpload : " + count_BalanceRecord);
                                        if (count_TotalCaptured == count_AfterUpload && count_BalanceRecord == 0) {
                                            tvAfterUploaded.setVisibility(View.VISIBLE);
                                            btnok.setVisibility(View.VISIBLE);
                                            btnUpload.setVisibility(View.GONE);
                                        }

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
                                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show();
                                    Log.d("Update_RI_SerParamTable", "Server Exception Occurred");
                                    dialog.dismiss();
                                });
                            }
                        }while (cursor.moveToNext());
                        dialog.dismiss();
                    }
                    dialog.dismiss();

                } else {
                    cursor.close();
                    runOnUiThread(() -> {
                        //Toast.makeText(getApplicationContext(), "There is no Updated data to Upload in Server " , Toast.LENGTH_SHORT).show();
                        Log.d("Update_RI_SerParamTable", "There is no Updated data to Upload in Server");
                        dialog.dismiss();
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Update_RI_SerParamTbla", "ExceptionArrived ");
                dialog.dismiss();
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
}
