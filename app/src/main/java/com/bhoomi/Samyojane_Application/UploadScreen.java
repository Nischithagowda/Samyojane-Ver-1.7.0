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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
            }
            else{
                buildAlertMessageConnection();
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

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        String formattedDate = df.format(c);

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
                            updateStatusCLASS.setGscNo(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_GSCNo)));
                            updateStatusCLASS.setLoginID(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_LoginID)));
                            updateStatusCLASS.setFacilityCode(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Service_Code)));
                            updateStatusCLASS.setDesignationCode(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DesignationCode)));
                            updateStatusCLASS.setDifferFromApplicant(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DifferFromAppinformation)));
                            updateStatusCLASS.setCanbeIssued(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Can_Certificate_Given)));
                            updateStatusCLASS.setRemarks(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Remarks)));
                            updateStatusCLASS.setReportNo(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Report_No)));
                            updateStatusCLASS.setAppTitle(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_AppTitle)));
                            updateStatusCLASS.setBinCom(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_BinCom)));
                            updateStatusCLASS.setFatTitle(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_FatTitle)));
                            updateStatusCLASS.setFatherName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_FatherName)));
                            updateStatusCLASS.setMotherName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_MotherName)));
                            updateStatusCLASS.setMobileNumber(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_MobileNumber)));
                            updateStatusCLASS.setAddress1(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address1)));
                            updateStatusCLASS.setAddress2(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address2)));
                            updateStatusCLASS.setAddress3(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Address3)));
                            updateStatusCLASS.setPinCode(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_PinCode)));
                            updateStatusCLASS.setResCatCode(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Applicant_Category)));
                            updateStatusCLASS.setCasteCode(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Applicant_Caste)));
                            updateStatusCLASS.setCasteSl(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_CasteSl)));
                            updateStatusCLASS.setIncome(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Income)));
                            updateStatusCLASS.setNoofYears(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Total_No_Years_10)));
                            updateStatusCLASS.setNoofMonths(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_NO_Months_10)));
                            updateStatusCLASS.setFatherCategory(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_App_Father_Category_8)));
                            updateStatusCLASS.setMotherCategory(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_App_Mother_Category_8)));
                            updateStatusCLASS.setFatherCaste(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_APP_Father_Caste_8)));
                            updateStatusCLASS.setMotherCaste(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_APP_Mother_Caste_8)));
                            updateStatusCLASS.setCreamyLayer(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Belongs_Creamy_Layer_6)));
                            updateStatusCLASS.setReasonCreamyLayer(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Reason_for_Creamy_Layer_6)));
                            updateStatusCLASS.setResAddress(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Reside_At_Stated_Address_10)));
                            updateStatusCLASS.setPlaceMatchWithRationCard(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Place_Match_With_RationCard_10)));
                            updateStatusCLASS.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_Photo)));
                            updateStatusCLASS.setvLat(cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_vLat)));
                            updateStatusCLASS.setvLong(cursor.getDouble(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_vLong)));
                            updateStatusCLASS.setDataUpdateFlag(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_DataUpdateFlag)));
                            updateStatusCLASS.setReportDate(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_ReportDate)));
                            updateStatusCLASS.setUploadedDate(df.parse(formattedDate));
                            updateStatusCLASS.setIMEI(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_VA_RI_IMEI)));
                            updateStatusCLASS.setVARIName(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.UPD_VA_RI_Name)));


                            Call<JsonObject> call = apiInterface_nic.UpdateStatus(updateStatusCLASS);
                            String finalApplicant_Id = Applicant_Id;
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if (response.isSuccessful()) {
                                        JsonObject jsonObject1 = response.body();
                                        Log.d("response_server", jsonObject1 + "");
                                        if (jsonObject1.isJsonNull()) {
                                            Toast.makeText(getApplicationContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
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
                                    } else {
                                        Toast.makeText(getApplicationContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    dialog.dismiss();
                                    t.printStackTrace();
                                    Toast.makeText(getApplicationContext(), ""+t.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                                }
                            });

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
}
