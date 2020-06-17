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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class E_Kshana_MainScreen_VA extends AppCompatActivity {

    TextView txtRCNum;
    String rc_num, districtCode, talukCode, hobliCode, uName_get;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    Set_and_Get_E_Kshana set_and_get_e_kshana;
    ProgressDialog dialog;
    APIInterface apiInterface;
    int dCode, tCode, hCode;
    TextView emptyTxt;
    ListView listView;
    Button btnBack;
    RC_VA_Member_List_Adapter list_adapter;
    private ArrayList<String> SlNo = new ArrayList<>();
    private ArrayList<String> Member_Name = new ArrayList<>();
    private ArrayList<String> Member_Id = new ArrayList<>();
    private ArrayList<String> Rc_num = new ArrayList<>();
    private ArrayList<String> DataEntry = new ArrayList<>();
    private ArrayList<String> Status = new ArrayList<>();
    private ArrayList<String> DistCode_ass = new ArrayList<>();
    private ArrayList<String> TalCode_ass = new ArrayList<>();
    private ArrayList<String> HobCode_ass = new ArrayList<>();
    private ArrayList<String> uName_get_Array = new ArrayList<>();
    int j=0, increase=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_kshana_main_screen_va);

        txtRCNum = findViewById(R.id.txtRCNum);
        emptyTxt = findViewById(R.id.emptyTxt);
        listView = findViewById(R.id.list);
        btnBack = findViewById(R.id.btnBack);

        Intent i = getIntent();
        rc_num = i.getStringExtra("rc_num");
        districtCode = i.getStringExtra("districtCode");
        talukCode = i.getStringExtra("talukCode");
        hobliCode = i.getStringExtra("hobliCode");
        uName_get = i.getStringExtra("uName_get");

        if (districtCode != null && talukCode != null && hobliCode != null){
            dCode = Integer.parseInt(districtCode);
            tCode = Integer.parseInt(talukCode);
            hCode = Integer.parseInt(hobliCode);
        }

        Log.d("rc_num_ass",""+rc_num);
        Log.d("districtCode", ""+districtCode);
        Log.d("talukCode", ""+talukCode);
        Log.d("hobliCode", ""+hobliCode);
        Log.d("uName_get",""+uName_get);

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(this);
        database = openHelper.getReadableDatabase();

        txtRCNum.setText(rc_num);
        if (isNetworkAvailable()) {
            getRCData();
        }else {
            displayData_AfterItemSelected(rc_num);
            buildAlert_Internet();
        }

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    public void getRCData(){

        if (isNetworkAvailable()) {
            dialog.show();
            apiInterface = APIClient.getClient(getString(R.string.main_url_E_Kshana)).create(APIInterface.class);

            //GET List Resources
            Call<String> call = apiInterface.doGetListResources(getString(R.string.flag1),getString(R.string.flag2), dCode, tCode, hCode, rc_num);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("TAG",response.code()+"");


                    String multipleResource = response.body();
                    Log.d("List_Member",multipleResource + "");

                    if (multipleResource!=null && !multipleResource.equals("0") && !multipleResource.equals("Data is not found for the given Details")) {

                        Runnable runnable = () -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                        try {
                            truncateDatabase_MemberID();
                            truncateDatabase_UpdatedRCMemberDetails();
                            JSONObject jsonObject = new JSONObject(multipleResource);
                            Log.d("jObj_output", "" + jsonObject);

                            JSONArray jsonArray = jsonObject.getJSONArray("Table");
                            Log.d("jsonArray_output", "" + jsonArray);

                            try {

                                int count = jsonArray.length();
                                j=count;
                                if (count != 0) {
                                    for (int i = 0; i < count; i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        set_and_get_e_kshana = new Set_and_Get_E_Kshana();
                                        set_and_get_e_kshana.setRC_Num(rc_num);
                                        set_and_get_e_kshana.setOtc_member_id(object.getString(DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id));
                                        set_and_get_e_kshana.setMember_name(object.getString(DataBaseHelperClass_btnDownload_E_Kshana.Member_name));
                                        set_and_get_e_kshana.setIsDataEntered(object.getString(DataBaseHelperClass_btnDownload_E_Kshana.isDataEntered));
                                        set_and_get_e_kshana.setVAUpdated(object.getString(DataBaseHelperClass_btnDownload_E_Kshana.VAUpdated));
                                        set_and_get_e_kshana.setStatus(object.getString(DataBaseHelperClass_btnDownload_E_Kshana.status));

                                        int otc_member_id = object.getInt(DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id);
                                        String Member_name = object.getString(DataBaseHelperClass_btnDownload_E_Kshana.Member_name);
                                        String DataEntry = object.getString(DataBaseHelperClass_btnDownload_E_Kshana.isDataEntered);
                                        String VAUpdated = object.getString(DataBaseHelperClass_btnDownload_E_Kshana.VAUpdated);
                                        String status = object.getString(DataBaseHelperClass_btnDownload_E_Kshana.status);

                                        database.execSQL("insert into " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_member_id
                                                + "("+DataBaseHelperClass_btnDownload_E_Kshana.RC_Num+" , "
                                                + DataBaseHelperClass_btnDownload_E_Kshana.Member_name+" , "
                                                + DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id+" , "
                                                + DataBaseHelperClass_btnDownload_E_Kshana.isDataEntered+" , "
                                                + DataBaseHelperClass_btnDownload_E_Kshana.VAUpdated+" , "
                                                + DataBaseHelperClass_btnDownload_E_Kshana.status+") values ('"
                                                + set_and_get_e_kshana.getRC_Num() + "','"
                                                + set_and_get_e_kshana.getMember_name() + "','"
                                                + set_and_get_e_kshana.getOtc_member_id() + "','"
                                                + set_and_get_e_kshana.getIsDataEntered() + "','"
                                                + set_and_get_e_kshana.getVAUpdated() + "','"
                                                + set_and_get_e_kshana.getStatus()+"')");

                                            getAll_Updated_FamilyDetails_by_RC(rc_num, otc_member_id);

                                        Log.d("GetRCMembers_data", "Member_ID: " + otc_member_id+", Name: "+Member_name
                                                +", DataEntry: "+DataEntry+", VAUpdated:"+VAUpdated+", status:"+status);

                                    }
                                }
                                displayData_AfterItemSelected(rc_num);
                                Log.d("GetRCMembers", "Got the RC Members");

                            } catch (JSONException e) {
                                dialog.dismiss();
                                e.printStackTrace();
                                runOnUiThread(runnable);
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            Log.e("JSON Parser", "Error parsing data " + e.toString());
                        }catch (NullPointerException e){
                            dialog.dismiss();
                            runOnUiThread(runnable);
                            e.printStackTrace();
                            Log.e("NullPointerException", ""+e.getMessage());
                        }
                    }else {
                        dialog.dismiss();
//                        displayAddMember(rc_num);
//                        displayData_AfterItemSelected(rc_num);
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), ""+multipleResource+": "+response.code(), Toast.LENGTH_SHORT).show());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("multipleResource",""+t.getMessage());
                    dialog.dismiss();
                    call.cancel();
                }
            });
        }else {
            Log.d("Internet_Error","No Connection");
            buildAlert_Internet();
            //Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
        }

    }

    public void truncateDatabase_MemberID(){

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_member_id, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_member_id);
            Log.d("Database", "MemberID Table Truncated");
        }
    }

    public void truncateDatabase_UpdatedRCMemberDetails(){

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails);
            Log.d("Database", "UpdatedRCMemberDetails Table Truncated");
        }

    }

    public void getAll_Updated_FamilyDetails_by_RC(String RC_Num, int Membet_ID){

        apiInterface = APIClient.getClient(getString(R.string.main_url_E_Kshana)).create(APIInterface.class);
        Call<String> call = apiInterface.doGetListResources_Mem_details(getString(R.string.flag1), getString(R.string.flag2), "" + RC_Num, "" + Membet_ID);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG", response.code() + "");

                String multipleResource = response.body();
                Log.d("MemberDetails", multipleResource + "");

                if (multipleResource!=null && !multipleResource.equals("0")) {
                    try {
                        JSONObject jsonObject = new JSONObject(multipleResource);
                        Log.d("jObj_output", "" + jsonObject);

                        JSONArray jsonArray = jsonObject.getJSONArray("Table1");
                        Log.d("jsonArray_output", "" + jsonArray);

                        int count = jsonArray.length();
                        if (count != 0) {

                            Type listType = new TypeToken<List<Set_and_Get_MemberDetails>>() {}.getType();
                            List<Set_and_Get_MemberDetails> myModelList = new Gson().fromJson(jsonArray.toString(), listType);

                            for (Set_and_Get_MemberDetails set_and_get_memberDetails : myModelList) {
                                database.execSQL("insert into " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                                        + "(" + DataBaseHelperClass_btnDownload_E_Kshana.RC_Dist_Code + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Taluk_Code + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Hobli_code + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Village_Code + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Habitation_Code + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Town_Code + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Ward_No + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Appl_RefNo + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Member_ID + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Salutation + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Applicant_EName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Applicant_KName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Father_EName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Father_KName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Mother_EName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Mother_KName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Gender+ ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_HOF_EName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_HOF_KName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_RelationWithHOF + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_ResidentYears_VA + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_ResidentYears_RI + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress1 + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress2 + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress3 + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress1 + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress2 +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress3 + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_PIN + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_MobileNo + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Age +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_Religion + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_app_Reservation_RI + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_app_Reservation_VA + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer_REPORT_VA + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer_REPORT_RI + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_App_Caste_RI + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_App_Caste_VA +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_VAComments + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_RIComments + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_DataEntryDate + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_IsUrban +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_SendToNextLevel +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_FamilyIncome_RI + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_FamilyIncome_VA + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_GSCNo + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_TahSigned +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_DTSigned +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_AddressFlag +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_IncomeFlag +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_KBinCom +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_EBinCom
                                        + ") values ('"
                                        + set_and_get_memberDetails.getRC_Dist_Code() + "','"
                                        + set_and_get_memberDetails.getRC_Taluk_Code() + "','"
                                        + set_and_get_memberDetails.getRC_Hobli_code() + "','"
                                        + set_and_get_memberDetails.getRC_Village_Code() + "','"
                                        + set_and_get_memberDetails.getRC_Habitation_Code() + "','"
                                        + set_and_get_memberDetails.getRC_Town_Code() + "','"
                                        + set_and_get_memberDetails.getRC_Ward_No() + "','"
                                        + set_and_get_memberDetails.getRC_Appl_RefNo() + "','"
                                        + set_and_get_memberDetails.getRC_Application_ID() + "','"
                                        + set_and_get_memberDetails.getRC_Member_ID() + "','"
                                        + set_and_get_memberDetails.getRC_Salutation() + "','"
                                        + set_and_get_memberDetails.getRC_Applicant_EName() + "','"
                                        + set_and_get_memberDetails.getRC_Applicant_KName() + "','"
                                        + set_and_get_memberDetails.getRC_Father_EName() + "','"
                                        + set_and_get_memberDetails.getRC_Father_KName() + "','"
                                        + set_and_get_memberDetails.getRC_Mother_EName() + "','"
                                        + set_and_get_memberDetails.getRC_Mother_KName() + "','"
                                        + set_and_get_memberDetails.getRC_Gender() + "','"
                                        + set_and_get_memberDetails.getRC_HOF_EName() + "','"
                                        + set_and_get_memberDetails.getRC_HOF_KName() + "','"
                                        + set_and_get_memberDetails.getRC_RelationWithHOF() + "','"
                                        + set_and_get_memberDetails.getRC_ResidentYears_VA() + "','"
                                        + set_and_get_memberDetails.getRC_ResidentYears_RI() + "','"
                                        + set_and_get_memberDetails.getRC_EAddress1() + "','"
                                        + set_and_get_memberDetails.getRC_EAddress2() + "','"
                                        + set_and_get_memberDetails.getRC_EAddress3() + "','"
                                        + set_and_get_memberDetails.getRC_KAddress1() + "','"
                                        + set_and_get_memberDetails.getRC_KAddress2() + "','"
                                        + set_and_get_memberDetails.getRC_KAddress3() + "','"
                                        + set_and_get_memberDetails.getRC_PIN() + "','"
                                        + set_and_get_memberDetails.getRC_MobileNo() + "','"
                                        + set_and_get_memberDetails.getRC_Age() + "','"
                                        + set_and_get_memberDetails.getRC_Religion() + "','"
                                        + set_and_get_memberDetails.getRC_app_Reservation_RI() + "','"
                                        + set_and_get_memberDetails.getRC_app_Reservation_VA() + "','"
                                        + set_and_get_memberDetails.getRC_CreamyLayer() + "','"
                                        + set_and_get_memberDetails.getRC_CreamyLayer_REPORT_VA() + "','"
                                        + set_and_get_memberDetails.getRC_CreamyLayer_REPORT_RI() + "','"
                                        + set_and_get_memberDetails.getRC_App_Caste_RI() + "','"
                                        + set_and_get_memberDetails.getRC_App_Caste_VA() + "','"
                                        + set_and_get_memberDetails.getRC_VAComments() + "','"
                                        + set_and_get_memberDetails.getRC_RIComments() + "','"
                                        + set_and_get_memberDetails.getRC_DataEntryDate() + "','"
                                        + set_and_get_memberDetails.getRC_IsUrban() + "','"
                                        + set_and_get_memberDetails.getRC_SendToNextLevel() + "','"
                                        + set_and_get_memberDetails.getRC_FamilyIncome_RI() + "','"
                                        + set_and_get_memberDetails.getRC_FamilyIncome_VA() + "','"
                                        + set_and_get_memberDetails.getRC_GSCNo() + "','"
                                        + set_and_get_memberDetails.getRC_TahSigned() + "','"
                                        + set_and_get_memberDetails.getRC_DTSigned() + "','"
                                        + set_and_get_memberDetails.getRC_AddressFlag() + "','"
                                        + set_and_get_memberDetails.getRC_IncomeFlag() + "','"
                                        + set_and_get_memberDetails.getRC_KBinCom() + "','"
                                        + set_and_get_memberDetails.getRC_EBinCom()
                                        + "');");
                            }
                            //dialog.dismiss();
                            increase++;
                            Log.d("increase",""+increase);
                            Log.d("Enter", "Inserted");

                            if (increase==j){
                                Log.d("increase",""+increase);
                                dialog.dismiss();
                                //displayAddMember(rc_num);
                            }
                        }
                    } catch (JSONException e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), getString(R.string.no_da_found), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("multipleResource", "" + t.getMessage());
                dialog.dismiss();
                call.cancel();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void displayData_AfterItemSelected(String rc_num) {
        int i=1;
        Log.d("InDisplay", ""+ i);
        Log.d("rc_num", rc_num);

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_member_id+" where "
                + DataBaseHelperClass_btnDownload_E_Kshana.RC_Num+"='"+rc_num+"'", null);

        SlNo.clear();
        Member_Name.clear();
        Member_Id.clear();
        Rc_num.clear();
        DataEntry.clear();
        Status.clear();
        DistCode_ass.clear();
        TalCode_ass.clear();
        HobCode_ass.clear();
        uName_get_Array.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            dialog.dismiss();
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Member_Name.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.Member_name)));
                    Member_Id.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id)));
                    Rc_num.add(rc_num);
                    DataEntry.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.VAUpdated)));
                    Status.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.status)));
                    DistCode_ass.add(""+dCode);
                    TalCode_ass.add(""+tCode);
                    HobCode_ass.add(""+hCode);
                    uName_get_Array.add(uName_get);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter = new RC_VA_Member_List_Adapter(E_Kshana_MainScreen_VA.this, SlNo, Member_Name, Member_Id, Rc_num, DataEntry, Status, DistCode_ass, TalCode_ass, HobCode_ass, uName_get_Array);
            listView.setAdapter(list_adapter);

            //

            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            dialog.dismiss();
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    private  void buildAlert_Internet() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.internet_not_avail))
                .setMessage(getString(R.string.switch_on_internet))
                .setIcon(R.drawable.ic_error_black_24dp)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
