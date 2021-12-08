package com.bhoomi.Samyojane_Application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface_NIC;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadScreen extends AppCompatActivity {

    Button btnUpload, btnok;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    ProgressDialog dialog;
    TextView tvTotalUpload, tvAlreadyUploaded, tvNotUploaded, tvAfterUploaded;
    int count_TotalCaptured=0, count_AfterUpload=0, count_BalanceRecord;
    String resultFromServer;

    String Updated_By_VA_Name, Updated_By_VA_IMEI;
    APIInterface_NIC apiInterface_nic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_screen);

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
        dialog.setMessage(getString(R.string.loading));
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        Intent i = getIntent();
        Updated_By_VA_Name = i.getStringExtra("VA_Name");
        Updated_By_VA_IMEI = i.getStringExtra("IMEI_Num");
        Log.d("Updated_By_VA_Name", ""+Updated_By_VA_Name);
        Log.d("Updated_By_VA_IMEI", ""+Updated_By_VA_IMEI);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(UploadScreen.this);
        database = openHelper.getWritableDatabase();

        final Cursor cursor = database.rawQuery("SELECT * FROM " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1+" SP left join "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" ST on ST."+ DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo +"= SP."+DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_GSCNo
                +" where (ST."+ DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1 and SP."
                + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1) or SP."+ DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag+"=1", null);

//        final Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_member_id+" where " + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag
//                +"=1", null);

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
                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(UploadScreen.this);
                database = openHelper.getWritableDatabase();

                UploadFieldVerificationData();
                //new UpdateServiceTranTable_Server().execute();
                //new InsertServiceParameterTable_Server().execute();
            }
            else{
                buildAlertMessageConnection();
                //Toast.makeText(getApplicationContext(), "Internet Connection was not Available\nPlease try again", Toast.LENGTH_SHORT).show();
            }
        });

        btnok.setOnClickListener(v -> onBackPressed());

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
                .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                    tvAfterUploaded.setText(getString(R.string.internet_not_avail));
                    tvAfterUploaded.setVisibility(View.VISIBLE);
                    dialog.cancel();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void UploadFieldVerificationData(){

        String Applicant_Id;
        UpdateStatusCLASS updateStatusCLASS;
        dialog.show();
        apiInterface_nic = APIClient.getClient(getString(R.string.MobAPI_New_NIC)).create(APIInterface_NIC.class);

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(UploadScreen.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1+" where "
                +DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DataUpdateFlag+"=1", null);
        try {
            if (cursor.getCount() > 0) {

                if (cursor.moveToFirst()) {
                    do {
                        Log.d("Loop_entering_here", "");

                        try {
                            Applicant_Id = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_GSCNo));
                            updateStatusCLASS = new UpdateStatusCLASS();
                            updateStatusCLASS.setGSCNo1(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_GSCNo)));
                            updateStatusCLASS.setLoginID(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_LoginID)));
                            updateStatusCLASS.setService_Code(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Service_Code)));
                            updateStatusCLASS.setDesignationCode(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DesignationCode)));
                            updateStatusCLASS.setDifferFromAppinformation(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DifferFromAppinformation)));
                            updateStatusCLASS.setCan_Certificate_Given(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Can_Certificate_Given)));
                            updateStatusCLASS.setRemarks(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Remarks)));
                            updateStatusCLASS.setReport_No(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Report_No)));
                            updateStatusCLASS.setAppTitle(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_AppTitle)));
                            updateStatusCLASS.setBinCom(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_BinCom)));
                            updateStatusCLASS.setFatTitle(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_FatTitle)));
                            updateStatusCLASS.setFatherName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_FatherName)));
                            updateStatusCLASS.setMotherName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_MotherName)));
                            updateStatusCLASS.setUpd_MobileNumber(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_MobileNumber)));
                            updateStatusCLASS.setAddress1(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address1)));
                            updateStatusCLASS.setAddress2(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address2)));
                            updateStatusCLASS.setAddress3(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address3)));
                            updateStatusCLASS.setPinCode(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_PinCode)));
                            updateStatusCLASS.setApplicant_Category(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Applicant_Category)));
                            updateStatusCLASS.setApplicant_Caste(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Applicant_Caste)));
                            updateStatusCLASS.setCasteSl(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_CasteSl)));
                            updateStatusCLASS.setIncome(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Income)));
                            updateStatusCLASS.setTotal_No_Years_10(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Total_No_Years_10)));
                            updateStatusCLASS.setNO_Months_10(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_NO_Months_10)));
                            updateStatusCLASS.setApp_Father_Category_8(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_App_Father_Category_8)));
                            updateStatusCLASS.setApp_Mother_Category_8(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_App_Mother_Category_8)));
                            updateStatusCLASS.setAPP_Father_Caste_8(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_APP_Father_Caste_8)));
                            updateStatusCLASS.setAPP_Mother_Caste_8(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_APP_Mother_Caste_8)));
                            updateStatusCLASS.setBelongs_Creamy_Layer_6("Y");
                            updateStatusCLASS.setReason_for_Creamy_Layer_6(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Reason_for_Creamy_Layer_6)));
                            updateStatusCLASS.setReside_At_Stated_Address_10(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Reside_At_Stated_Address_10)));
                            updateStatusCLASS.setPlace_Match_With_RationCard_10(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Place_Match_With_RationCard_10)));
                            updateStatusCLASS.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Photo)));
                            updateStatusCLASS.setvLat(cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_vLat)));
                            updateStatusCLASS.setvLong(cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_vLong)));
                            updateStatusCLASS.setDataUpdateFlag(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DataUpdateFlag)));
                            updateStatusCLASS.setReportDate(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_ReportDate)));
                            updateStatusCLASS.setUploadedDate(Calendar.getInstance().getTime());
                            updateStatusCLASS.setUpdated_By_VA_IMEI(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_VA_RI_IMEI)));
                            updateStatusCLASS.setUpdated_By_VA_Name(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_VA_RI_Name)));


                            Call<JsonObject> call = apiInterface_nic.UpdateStatus(updateStatusCLASS);
                            String finalApplicant_Id = Applicant_Id;
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    JsonObject jsonObject1 = response.body();
                                    Log.d("response_server",jsonObject1 + "");
                                    if (Objects.requireNonNull(jsonObject1).isJsonNull()){
                                        Toast.makeText(getApplicationContext(), ""+response.message() , Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        JsonPrimitive jsonObject2 = jsonObject1.getAsJsonPrimitive("StatusMessage");
                                        JsonPrimitive jsonObject3 = jsonObject1.getAsJsonPrimitive("StatusCode");
                                        String StatusMessage = jsonObject2.toString();
                                        String StatusCode = jsonObject3.toString();
                                        Log.d("StatusCode", StatusCode + ", StatusMessage: " + StatusMessage);

                                        resultFromServer = StatusCode;
                                        if (resultFromServer.equals("0")) {
                                            runOnUiThread(() -> {
                                                //Toast.makeText(getApplicationContext(), "Data Uploaded Successfully" , Toast.LENGTH_SHORT).show();
                                                Log.d("Request_", "UpdateServiceParameterTable" + "Data Uploaded Successfully");
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
                                            dialog.dismiss();
                                            database.execSQL("delete from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1
                                                    + " where " + DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo + "='" + finalApplicant_Id + "'");
                                            Log.d("Local_Result", "A row deleted Successfully");
                                        } else {
                                            Log.d("Request_", "UpdateServiceParameterTable" + " Data not uploaded");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    dialog.dismiss();
                                    t.printStackTrace();
                                    Toast.makeText(getApplicationContext(), ""+t.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                                }
                            });

//                            androidHttpTransport = new HttpTransportSE(getString(R.string.SOAP_ADDRESS));
//                            Log.d("URL: ",""+ getString(R.string.SOAP_ADDRESS));
//                            androidHttpTransport.call(SOAP_ACTION2, envelope);
//                            resultString = (SoapPrimitive) envelope.getResponse();
//                            Log.i("Result", ""+resultString);


                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("Error1", e.getMessage());
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show();
                                Log.d("InsertServiceParaTable", "Server Exception Occurred");
                                dialog.dismiss();
                            });
                        }
                    }while (cursor.moveToNext());
                }

            } else {
                cursor.close();
                runOnUiThread(() -> {
                    //Toast.makeText(getApplicationContext(), "There is no Updated data to Upload in Server " , Toast.LENGTH_SHORT).show();
                    Log.d("InsertServiceParaTable", "There is no Updated data to Upload in Server");
                    dialog.dismiss();
                });
            }

        } catch (Exception e) {
            Log.d("InsertServiceParaTable1", e.getMessage());
            dialog.dismiss();
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show());
        }
    }
//    @SuppressLint("StaticFieldLeak")
//    class InsertServiceParameterTable_Server extends AsyncTask<String, Integer, String> {
//
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        @Override
//        protected String doInBackground(String... params) {
//            openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(UploadScreen.this);
//            database = openHelper.getWritableDatabase();
//
//            Cursor cursor = database.rawQuery("select "+DataBaseHelperClass_btnDownload_ServiceTranTable.District_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Taluk_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Hobli_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo +","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.DifferFromAppinformation +","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.FatherName +","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.MotherName +","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Upd_MobileNumber +","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address1+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address2+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Address3+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Report_No+","
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
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.AnnualIncome+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Photo+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.vLat+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.vLong+","
//                    + DataBaseHelperClass_btnDownload_ServiceTranTable.Can_Certificate_Given+","
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
//                                Village_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code));
//                                Town_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code));
//                                Ward_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code));
//                                Service_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code));
//                                Applicant_Id = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo));
//                                VA_Accepts_Applicant_information = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.DifferFromAppinformation));
//                                name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Name));
//                                fatherName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.FatherName));
//                                motherName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.MotherName));
//                                Mobile_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Upd_MobileNumber));
//                                RationCard_No = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.U_RationCard_No));
//                                Address1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address1));
//                                Address2 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address2));
//                                Address3 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Address3));
//                                PinCode = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.PinCode));
//                                Eng_Certif = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_Eng_Certificate));
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
//                                Annual_Income= cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.AnnualIncome));
//                                Photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Photo));
//                                vLat = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.vLat));
//                                vLong = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.vLong));
//                                Can_Certificate_Given = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Can_Certificate_Given));
//                                Reason_for_Rejection = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Rejection));
//                                DataUpdateFlag = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag));
//
//                                Cursor cursor2 = database.rawQuery("select "+DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_photo+" from "
//                                        +DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1+ " where "
//                                        + DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo +"='"+Applicant_Id+"'", null);
//                                if (cursor2.getCount()>0){
//                                    if (cursor2.moveToFirst()){
//                                        ST_applicant_photo = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_photo));
//                                    }
//                                    else {
//                                        ST_applicant_photo = null;
//                                    }
//                                } else {
//                                    cursor2.close();
//                                }
//
//                                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME2);
//
//                                request.addProperty("District_Code", District_Code);
//                                request.addProperty("Taluk_Code", Taluk_Code);
//                                request.addProperty("Hobli_Code", Hobli_Code);
//                                request.addProperty("Village_Code", Village_Code);
//                                request.addProperty("Town_Code",Town_Code);
//                                request.addProperty("Ward_Code",Ward_Code);
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
//                                request.addProperty("Updated_By_VA_IMEI", Updated_By_VA_IMEI);
//                                request.addProperty("Updated_By_VA_MobileNum",Updated_By_VA_MobileNum);
//                                request.addProperty("Updated_By_VA_Name", Updated_By_VA_Name);
//
//                                Log.d("Request","" + request);
//
//                                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//                                envelope.dotNet = true;
//                                envelope.setOutputSoapObject(request);
//
//                                androidHttpTransport = new HttpTransportSE(getString(R.string.SOAP_ADDRESS));
//                                Log.d("URL: ",""+ getString(R.string.SOAP_ADDRESS));
//                                androidHttpTransport.call(SOAP_ACTION2, envelope);
//                                resultString = (SoapPrimitive) envelope.getResponse();
//                                Log.i("Result", ""+resultString);
//                                resultFromServer = String.valueOf(resultString);
//                                if(resultFromServer.equals("0")) {
//                                    runOnUiThread(() -> {
//                                        //Toast.makeText(getApplicationContext(), "Data Uploaded Successfully" , Toast.LENGTH_SHORT).show();
//                                        Log.d("Request_", "UpdateServiceParameterTable" + "Data Uploaded Successfully");
//                                        count_AfterUpload++;
//                                        count_BalanceRecord--;
//                                        tvTotalUpload.setText(String.valueOf(count_TotalCaptured));
//                                        tvAlreadyUploaded.setText(String.valueOf(count_AfterUpload));
//                                        tvNotUploaded.setText(String.valueOf(count_BalanceRecord));
//                                        Log.d("Count_of_Records", "count_TotalCaptured : " + count_TotalCaptured + "\ncount_AfterUpload : " + count_AfterUpload + "\ncount_AfterUpload : " + count_BalanceRecord);
//                                        if (count_TotalCaptured == count_AfterUpload && count_BalanceRecord == 0) {
//                                            tvAfterUploaded.setVisibility(View.VISIBLE);
//                                            btnok.setVisibility(View.VISIBLE);
//                                            btnUpload.setVisibility(View.GONE);
//                                        }
//
//                                    });
//
//                                    database.execSQL("delete from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where " + DataBaseHelperClass_btnDownload_ServiceTranTable.GSCNo + "='" + Applicant_Id + "'");
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
//                                    dialog.dismiss();
//                                });
//                            }
//                        }while (cursor.moveToNext());
//                        dialog.dismiss();
//                    }
//                    dialog.dismiss();
//
//                } else {
//                    cursor.close();
//                    runOnUiThread(() -> {
//                        //Toast.makeText(getApplicationContext(), "There is no Updated data to Upload in Server " , Toast.LENGTH_SHORT).show();
//                        Log.d("InsertServiceParaTable", "There is no Updated data to Upload in Server");
//                        dialog.dismiss();
//                    });
//                }
//
//            } catch (Exception e) {
//                Log.d("InsertServiceParaTable1", e.getMessage());
//                dialog.dismiss();
//                runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show());
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
}
