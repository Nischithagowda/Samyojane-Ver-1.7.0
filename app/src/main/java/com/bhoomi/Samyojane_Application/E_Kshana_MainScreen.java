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
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class E_Kshana_MainScreen extends AppCompatActivity {

    EditText etRC_Num;
    Button btnFetch, btnAddMember;
    String rc_num=null;
    APIInterface apiInterface;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    Set_and_Get_E_Kshana set_and_get_e_kshana;
    ProgressDialog dialog;
    TextView emptyTxt, tv_e_kshana;
    ListView listView;
    RC_Member_List_Adapter list_adapter;
    LinearLayout linearLayout, listLayout;
    private ArrayList<String> SlNo = new ArrayList<>();
    private ArrayList<String> Member_Name = new ArrayList<>();
    private ArrayList<String> Member_Id = new ArrayList<>();
    private ArrayList<String> Rc_num = new ArrayList<>();
    private ArrayList<String> DataEntry = new ArrayList<>();
    private ArrayList<String> DistCode_ass = new ArrayList<>();
    private ArrayList<String> TalCode_ass = new ArrayList<>();
    private ArrayList<String> HobCode_ass = new ArrayList<>();
    private ArrayList<String> uName_get_Array = new ArrayList<>();
    int getCatCode=0, getCasteCode=0;
    int reason_Code_1=0;
    String option="ಇಲ್ಲ", option_Flag;
    int Count_Inactive=0;
    int j=0, increase=0;
    String distCode_ass, talCode_ass, hobCode_ass, uName_get;
    int dCode, tCode, hCode;

    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;

    AlertDialog alert;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"ClickableViewAccessibility", "SimpleDateFormat"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_kshana_main_screen);

        etRC_Num = findViewById(R.id.etRC_Num);
        btnFetch = findViewById(R.id.btnFetch);
        btnAddMember = findViewById(R.id.btnAddMember);
        emptyTxt = findViewById(R.id.emptyTxt);
        listView = findViewById(R.id.list);
        linearLayout = findViewById(R.id.total_Applicants);
        listLayout = findViewById(R.id.listLayout);
        tv_e_kshana = findViewById(R.id.tv_e_kshana);

        linearLayout.setVisibility(View.GONE);
        listLayout.setVisibility(View.GONE);
        emptyTxt.setVisibility(View.GONE);
        btnAddMember.setVisibility(View.GONE);

        tv_e_kshana.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        etRC_Num.setOnTouchListener((v, event) -> {
            check_English_Key_lang();
            return false;
        });

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(this);
        database = openHelper.getReadableDatabase();

        Intent i = getIntent();
        rc_num = i.getStringExtra("rc_num");
        distCode_ass = i.getStringExtra("distCode_ass");
        talCode_ass = i.getStringExtra("talCode_ass");
        hobCode_ass = i.getStringExtra("hobCode_ass");
        uName_get = i.getStringExtra("uName_get");

        if (distCode_ass != null && talCode_ass != null && hobCode_ass != null){
            dCode = Integer.parseInt(distCode_ass);
            tCode = Integer.parseInt(talCode_ass);
            hCode = Integer.parseInt(hobCode_ass);
        }

        Log.d("distCode_ass1", ""+distCode_ass);
        Log.d("talCode_ass1", ""+talCode_ass);
        Log.d("hobCode_ass1", ""+hobCode_ass);
        Log.d("rc_num_ass",""+rc_num);
        Log.d("uName_get", uName_get);

        if (rc_num!=null){
            etRC_Num.setText(rc_num);
            getRCData();
        }


        btnFetch.setOnClickListener(v -> {
            increase=0;
            Count_Inactive=0;
            emptyTxt.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            listLayout.setVisibility(View.GONE);

            rc_num = String.valueOf(etRC_Num.getText());
            Log.d("rc_num",""+rc_num);

            if (TextUtils.isEmpty(rc_num)){
                etRC_Num.setError(getString(R.string.field_canno_null));
            }else {
                getRCData();
            }

        });

        btnAddMember.setOnClickListener(v -> {
            Intent intent = new Intent(E_Kshana_MainScreen.this, E_Kshana_Add_Member.class);
            intent.putExtra("rc_num", rc_num);
            intent.putExtra("distCode_ass",distCode_ass);
            intent.putExtra("talCode_ass",talCode_ass);
            intent.putExtra("hobCode_ass",hobCode_ass);
            intent.putExtra("uName_get", uName_get);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void check_English_Key_lang(){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        ims = imm.getCurrentInputMethodSubtype();
        String locale = ims.getLocale();
        Locale locale2 = new Locale(locale);
        String currentLanguage = locale2.getDisplayLanguage();
        String[] split_curr = currentLanguage.split("_");
        String cur_value = split_curr[0];
        Log.d("cur_value", cur_value);
        Log.d("lang:", "" + currentLanguage);
        if (!Objects.equals(cur_value, "en")) {
            Toast.makeText(getApplicationContext(), getString(R.string.switch_english), Toast.LENGTH_SHORT).show();
            imm.showInputMethodPicker();
        }
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
                            truncateDatabase_RCMemberDetails();
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

                                         if (DataEntry.equals("Y")){
                                             getAll_Updated_FamilyDetails_by_RC(rc_num, otc_member_id);
                                         }else {
                                             getAllFamilyDetails_by_RC(rc_num, otc_member_id);
                                         }

                                        Log.d("GetRCMembers_data", "Member_ID: " + otc_member_id+", Name: "+Member_name
                                                +", DataEntry: "+DataEntry);

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

    public void truncateDatabase_RCMemberDetails(){

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_MemberDetails, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_MemberDetails);
            Log.d("Database", "RCMemberDetails Table Truncated");
        }

    }

    public void getAllFamilyDetails_by_RC(String RC_Num, int Membet_ID){

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

                        JSONArray jsonArray = jsonObject.getJSONArray("Table");
                        Log.d("jsonArray_output", "" + jsonArray);

                        int count = jsonArray.length();
                        if (count != 0) {

                            Type listType = new TypeToken<List<Set_and_Get_MemberDetails>>() {}.getType();
                            List<Set_and_Get_MemberDetails> myModelList = new Gson().fromJson(jsonArray.toString(), listType);

                            for (Set_and_Get_MemberDetails set_and_get_memberDetails : myModelList) {
                                database.execSQL("insert into " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_MemberDetails
                                        + "(" + DataBaseHelperClass_btnDownload_E_Kshana.RAT_ACK_ID + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_ID + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_HoF_ID + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_HOF_KName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_KName + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_Gender + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_BinCom + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_DOB + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_YOB + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_MobileNo + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_KAddress + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_Pincode + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Relation_Id + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_KRelation_With_HOH + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_District_Code_NK + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Taluk_Code_NK + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Hobli_Code_NK + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Town_Code_NK + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Ward_No_NK + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Village_Code_NK + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_Habitation_Code + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.OTC_FAMILY_INCOME + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.OTC_MEMB_ID_TITLE + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.OTC_MEMB_BINCOM + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.OTC_MEMB_FATH_NAME + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.OTC_MEMB_MOTH_NAME + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.OTC_MEMB_CAT + ","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.OTC_MEMB_CAST +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_DataEntryDate+","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_IsInactive +","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RAT_InactiveReason
                                        + ") values ('"
                                        + set_and_get_memberDetails.getRAT_ACK_ID() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_ID() + "','"
                                        + set_and_get_memberDetails.getRAT_HoF_ID() + "','"
                                        + set_and_get_memberDetails.getRAT_HOF_KName() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_KName() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_Gender() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_BinCom() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_DOB() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_YOB() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_MobileNo() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_KAddress() + "','"
                                        + set_and_get_memberDetails.getRAT_Member_Pincode() + "','"
                                        + set_and_get_memberDetails.getRAT_Relation_Id() + "','"
                                        + set_and_get_memberDetails.getRAT_KRelation_With_HOH() + "','"
                                        + set_and_get_memberDetails.getRAT_District_Code_NK() + "','"
                                        + set_and_get_memberDetails.getRAT_Taluk_Code_NK() + "','"
                                        + set_and_get_memberDetails.getRAT_Hobli_Code_NK() + "','"
                                        + set_and_get_memberDetails.getRAT_Town_Code_NK() + "','"
                                        + set_and_get_memberDetails.getRAT_Ward_No_NK() + "','"
                                        + set_and_get_memberDetails.getRAT_Village_Code_NK() + "','"
                                        + set_and_get_memberDetails.getRAT_Habitation_Code() + "','"
                                        + set_and_get_memberDetails.getOTC_FAMILY_INCOME() + "','"
                                        + set_and_get_memberDetails.getOTC_MEMB_ID_TITLE() + "','"
                                        + set_and_get_memberDetails.getOTC_MEMB_BINCOM() + "','"
                                        + set_and_get_memberDetails.getOTC_MEMB_FATH_NAME() + "','"
                                        + set_and_get_memberDetails.getOTC_MEMB_MOTH_NAME() + "','"
                                        + set_and_get_memberDetails.getOTC_MEMB_CAT() + "','"
                                        + set_and_get_memberDetails.getOTC_MEMB_CAST() + "','"
                                        + set_and_get_memberDetails.getRAT_DataEntryDate() + "','"
                                        + set_and_get_memberDetails.getRAT_IsInactive() + "','"
                                        + set_and_get_memberDetails.getRAT_InactiveReason()
                                        + "');"
                                );
                            }
                            //dialog.dismiss();
                            increase++;
                            Log.d("increase",""+increase);
                            Log.d("Enter", "Inserted");
                            if (increase==j){
                                Log.d("increase",""+increase);
                                dialog.dismiss();
                                displayAddMember(rc_num);
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
                                        + DataBaseHelperClass_btnDownload_E_Kshana.RC_IncomeFlag
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
                                        + set_and_get_memberDetails.getRC_IncomeFlag()
                                        + "');");
                            }
                            //dialog.dismiss();
                            increase++;
                            Log.d("increase",""+increase);
                            Log.d("Enter", "Inserted");

                            if (increase==j){
                                Log.d("increase",""+increase);
                                dialog.dismiss();
                                displayAddMember(rc_num);
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

    public void displayAddMember(String rc_num){
        int mem_ID;

        @SuppressLint("Recycle")
        Cursor cursor12 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_member_id
                +" where "+ DataBaseHelperClass_btnDownload_E_Kshana.RC_Num+"='"+rc_num+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.isDataEntered+"='N'", null);
        if (cursor12.getCount()>0){
            if (cursor12.moveToFirst()) {
                do {
                    mem_ID = cursor12.getInt(cursor12.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id));
                    Log.d("mem_ID", "" + mem_ID);
                    @SuppressLint("Recycle")
                    Cursor cursor2 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_MemberDetails
                    + " where "+DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_ID+"="+mem_ID+" and "
                            + DataBaseHelperClass_btnDownload_E_Kshana.RAT_ACK_ID+"='"+rc_num+"' and "
                            + DataBaseHelperClass_btnDownload_E_Kshana.RAT_IsInactive+"='null'", null);
                    if (cursor2.getCount()>0){
                        Count_Inactive++;
                    }
                } while (cursor12.moveToNext());
            }
        }

        Log.d("Count_Inactive", ""+Count_Inactive);
        if (Count_Inactive==0){
            btnAddMember.setVisibility(View.VISIBLE);
        }else {
            btnAddMember.setVisibility(View.GONE);
        }
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
        DistCode_ass.clear();
        TalCode_ass.clear();
        HobCode_ass.clear();
        uName_get_Array.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            listLayout.setVisibility(View.VISIBLE);
            dialog.dismiss();
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    Member_Name.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.Member_name)));
                    Member_Id.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id)));
                    Rc_num.add(rc_num);
                    DataEntry.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.isDataEntered)));
                    DistCode_ass.add(""+distCode_ass);
                    TalCode_ass.add(""+talCode_ass);
                    HobCode_ass.add(""+hobCode_ass);
                    uName_get_Array.add(uName_get);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter = new RC_Member_List_Adapter(E_Kshana_MainScreen.this, SlNo, Member_Name, Member_Id, Rc_num, DataEntry, DistCode_ass, TalCode_ass, HobCode_ass, uName_get_Array);
            listView.setAdapter(list_adapter);
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

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.want_to_exit))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    alert.dismiss();
                    E_Kshana_MainScreen.super.onBackPressed();
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
        alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Assistant_Login.pwd.setText("");
        buildAlertMessageGoingBack();
    }
}
