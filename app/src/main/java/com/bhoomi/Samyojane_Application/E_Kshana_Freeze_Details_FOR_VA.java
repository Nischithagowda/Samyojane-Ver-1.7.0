package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_Kshana_Freeze_Details_FOR_VA extends AppCompatActivity {

    TextView txtMemberName, txtDistrict, txtTaluk, txtHobli, txtTown, txtWard, txtAddress1, txtAddress2, txtAddress3,
            txtPIN, txtTotalIncome, txtCreamyLayer, txtHOF_Name, txtRelationship_HOF, txtFatherHusbandName,
            txtMotherName, txtAge, txtGender, txtMobile, txtCategory, txtCaste, txtReligion, txtYear;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    String districtName, talukName, hobliName, townName, wardName;
    String memberName, memberName_Eng, hofName, hofName_Eng, bincom_Code;
    String rel_HOF_Code, rel_HOF_Name, fatherHusName, fatherHusName_Eng, motherName, motherName_Eng, age, gender_Code, gender_Name, mobileNum
            , category_Code, category_name, caste_Code, caste_name, religion_Code, religion_Name, residingYear, remarks;
    String address1, address1_Eng, address2, address2_Eng, address3, address3_Eng, pin, totalIncome, creamyLayer, reasonCode_creamy;
    SQLiteAssetHelper_Masters sqLiteAssetHelper_masters;
    String member_ID, rc_num;
    String distCode_ass, talCode_ass, hobCode_ass, uName_get;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    int districtCode, talukCode, hobliCode, townCode, wardCode, villageCode, habitationCode, salutation_Code;
    Activity activity;
    Button btnUpload, btnEdit;
    APIInterface apiInterface;
    ProgressDialog dialog;
    EditText tvRemarks;
    String dataEntry, Updated_MemberID;

    private InputFilter filter_Kan = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "abcdefghijklmnopqrstuvwxyz";
        String l2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String op1 = "~`!@#$%^&*()_-''+={}[]:/?><,.\\\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶1234567890೧೨೩೪೫೬೭೮೯೦";
        String blockCharacterSet = l1+l2+op1;

        for (int i = start; i < end; i++) {
            Log.d("source.charAt(i)",""+i+" : "+source.charAt(i));
            if (source != null && blockCharacterSet.contains(("" + source.charAt(i)))) {
                Log.d("Blocked",""+source);
                return "";
            }
        }
        Log.d("Filter_valid","Not blocked");
        return null;
    };

    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_kshana_freeze_details_for_va);

        txtMemberName = findViewById(R.id.txtMemberName);
        txtDistrict = findViewById(R.id.txtDistrict);
        txtTaluk = findViewById(R.id.txtTaluk);
        txtHobli = findViewById(R.id.txtHobli);
        txtTown= findViewById(R.id.txtTown);
        txtWard= findViewById(R.id.txtWard);
        txtAddress1 = findViewById(R.id.txtAddress1);
        txtAddress2 = findViewById(R.id.txtAddress2);
        txtAddress3 = findViewById(R.id.txtAddress3);
        txtPIN = findViewById(R.id.txtPIN);
        txtTotalIncome = findViewById(R.id.txtTotalIncome);
        txtCreamyLayer = findViewById(R.id.txtCreamyLayer);
        txtHOF_Name = findViewById(R.id.txtHOF_Name);
        txtRelationship_HOF = findViewById(R.id.txtRelationship_HOF);
        txtFatherHusbandName = findViewById(R.id.txtFatherHusbandName);
        txtMotherName = findViewById(R.id.txtMotherName);
        txtAge = findViewById(R.id.txtAge);
        txtGender = findViewById(R.id.txtGender);
        txtMobile = findViewById(R.id.txtMobile);
        txtCategory = findViewById(R.id.txtCategory);
        txtCaste = findViewById(R.id.txtCaste);
        txtReligion = findViewById(R.id.txtReligion);
        txtYear = findViewById(R.id.txtYear);
        btnUpload = findViewById(R.id.btnUpload);
        tvRemarks = findViewById(R.id.tvRemarks);
        btnEdit = findViewById(R.id.btnEdit);

        PhoneNumberUtils.formatNumber(tvRemarks.getText().toString());
        tvRemarks.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        tvRemarks.setFilters(new InputFilter[] { filter_Kan });

        tvRemarks.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        Intent i = getIntent();
        rc_num = i.getStringExtra("rc_num");
        member_ID = i.getStringExtra("member_ID");
        distCode_ass = i.getStringExtra("distCode_ass");
        talCode_ass = i.getStringExtra("talCode_ass");
        hobCode_ass = i.getStringExtra("hobCode_ass");
        uName_get = i.getStringExtra("uName_get");

        Log.d("member_ID",""+member_ID);
        Log.d("rc_num",""+rc_num);
        Log.d("distCode_ass", ""+distCode_ass);
        Log.d("talCode_ass", ""+talCode_ass);
        Log.d("hobCode_ass", ""+hobCode_ass);
        Log.d("uName_get", uName_get);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Freeze_Details_FOR_VA.this);
        sqlLiteOpenHelper_class.open_Reasons_Master_Tbl();

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(E_Kshana_Freeze_Details_FOR_VA.this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();

        openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(this);
        database = openHelper.getReadableDatabase();

        Cursor cursor1 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_member_id
                +" where "+ DataBaseHelperClass_btnDownload_E_Kshana.RC_Num+"='"+rc_num+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.VAUpdated+"='Y'", null);
        if (cursor1.getCount()>0){
            if (cursor1.moveToNext()){
                dataEntry = "Y";
                Updated_MemberID = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id));
            }
        }else {
            cursor1.close();
            dataEntry = "N";
        }

        Log.d("dataEntry_VA",""+dataEntry);
        Log.d("Updated_MemberID",""+Updated_MemberID);

        if (dataEntry.equals("N")) {

            Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                    + " where " + DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID + "='" + rc_num + "' and "
                    + DataBaseHelperClass_btnDownload_E_Kshana.RC_Member_ID + "=" + member_ID, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    memberName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Applicant_KName));
                    memberName_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Applicant_EName));
                    districtCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Dist_Code));
                    talukCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Taluk_Code));
                    hobliCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Hobli_code));
                    villageCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Village_Code));
                    habitationCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Habitation_Code));
                    townCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Town_Code));
                    wardCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Ward_No));
                    salutation_Code = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Salutation));
                    hofName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_HOF_KName));
                    hofName_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_HOF_EName));
                    address1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress1));
                    address1_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress1));
                    address2 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress2));
                    address2_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress2));
                    address3 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress3));
                    address3_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress3));
                    pin = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_PIN));
                    totalIncome = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_FamilyIncome_VA));
                    creamyLayer = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer));
                    reasonCode_creamy = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer_REPORT_VA));
                    rel_HOF_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_RelationWithHOF));
                    fatherHusName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Father_KName));
                    fatherHusName_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Father_EName));
                    motherName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Mother_KName));
                    motherName_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Mother_EName));
                    age = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Age));
                    gender_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Gender));
                    mobileNum = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_MobileNo));
                    category_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_app_Reservation_VA));
                    caste_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_App_Caste_VA));
                    religion_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Religion));
                    residingYear = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_ResidentYears_VA));
                    bincom_Code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EBinCom));
                }
            } else {
                cursor.close();
            }
        }else if (dataEntry.equals("Y")){

            Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                    + " where " + DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID + "='" + rc_num + "' and "
                    + DataBaseHelperClass_btnDownload_E_Kshana.RC_Member_ID + "=" + Updated_MemberID, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    districtCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Dist_Code));
                    talukCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Taluk_Code));
                    hobliCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Hobli_code));
                    villageCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Village_Code));
                    habitationCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Habitation_Code));
                    townCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Town_Code));
                    wardCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Ward_No));
                    hofName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_HOF_KName));
                    hofName_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_HOF_EName));
                    address1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress1));
                    address1_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress1));
                    address2 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress2));
                    address2_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress2));
                    address3 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress3));
                    address3_Eng = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress3));
                    pin = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_PIN));
                    totalIncome = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_FamilyIncome_VA));
                    creamyLayer = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer));
                    reasonCode_creamy = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer_REPORT_VA));
                }
            } else {
                cursor.close();
            }

            Cursor cursor2 = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                    + " where " + DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID + "='" + rc_num + "' and "
                    + DataBaseHelperClass_btnDownload_E_Kshana.RC_Member_ID + "=" + member_ID, null);
            if (cursor2.getCount() > 0) {
                if (cursor2.moveToNext()) {
                    memberName = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Applicant_KName));
                    memberName_Eng = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Applicant_EName));
                    salutation_Code = cursor2.getInt(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Salutation));
                    rel_HOF_Code = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_RelationWithHOF));
                    fatherHusName = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Father_KName));
                    fatherHusName_Eng = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Father_EName));
                    motherName = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Mother_KName));
                    motherName_Eng = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Mother_EName));
                    age = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Age));
                    gender_Code = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Gender));
                    mobileNum = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_MobileNo));
                    category_Code = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_app_Reservation_VA));
                    caste_Code = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_App_Caste_VA));
                    religion_Code = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Religion));
                    residingYear = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_ResidentYears_VA));
                    bincom_Code = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EBinCom));
                }
            } else {
                cursor2.close();
            }
        }

        Log.d("memberName",""+memberName);
        Log.d("districtCode",""+ districtCode +" talukCode "+talukCode+" hobliCode "
                +hobliCode+" townCode "+townCode+" wardCode "+wardCode);
        Log.d("hofName",""+hofName);

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();
        districtName = sqLiteAssetHelper_masters.Get_DistName_NK_Master(districtCode);
        talukName = sqLiteAssetHelper_masters.Get_TalukName_NK_Master(districtCode, talukCode);
        hobliName = sqLiteAssetHelper_masters.Get_HobliName_NK_Master(districtCode, talukCode, hobliCode);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Freeze_Details_FOR_VA.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        townName = sqlLiteOpenHelper_class.Get_TownName(districtCode, talukCode, townCode);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Freeze_Details_FOR_VA.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        wardName = sqlLiteOpenHelper_class.Get_WardName_one(districtCode, talukCode, townCode, wardCode);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class();
        sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
        category_name = sqlLiteOpenHelper_class.GetCategory_BY_Code(Integer.parseInt(category_Code));
        caste_name = sqlLiteOpenHelper_class.GetCaste_BY_Code(Integer.parseInt(category_Code), Integer.parseInt(caste_Code));

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();
        rel_HOF_Name = sqLiteAssetHelper_masters.GetHOF_RelationName_By_Code(Integer.parseInt(rel_HOF_Code));
        gender_Name = sqLiteAssetHelper_masters.GetGender_By_Code(Integer.parseInt(gender_Code));
        religion_Name = sqLiteAssetHelper_masters.GetReligion_By_Code(Integer.parseInt(religion_Code));

        txtMemberName.setText(memberName);
        txtDistrict.setText(districtName);
        txtTaluk.setText(talukName);
        txtHobli.setText(hobliName);
        txtTown.setText(townName);
        txtWard.setText(wardName);
        txtHOF_Name.setText(hofName);
        txtAddress1.setText(address1);
        txtAddress2.setText(address2);
        txtAddress3.setText(address3);
        txtPIN.setText(pin);
        txtTotalIncome.setText(totalIncome);
        txtCreamyLayer.setText(creamyLayer);
        txtRelationship_HOF.setText(rel_HOF_Name);
        txtFatherHusbandName.setText(fatherHusName);
        txtMotherName.setText(motherName);
        txtAge.setText(age);
        txtGender.setText(gender_Name);
        txtMobile.setText(mobileNum);
        txtCategory.setText(category_name);
        txtCaste.setText(caste_name);
        txtReligion.setText(religion_Name);
        txtYear.setText(residingYear);

        btnUpload.setOnClickListener(v -> {

            remarks = tvRemarks.getText().toString();
            Log.d("tvRemarks", remarks);

            if (TextUtils.isEmpty(remarks)){
                tvRemarks.setError(getString(R.string.field_canno_null));
            }else {
                if (isNetworkAvailable()) {
                    dialog.show();
                    Log.d("Value", "Complete");
                    String values1;
                    values1 = "{"
                            + "\"DistCode\":" + districtCode + ","
                            + "\"TalukCode\":" + talukCode + ","
                            + "\"HobliCode\":" + hobliCode + ","
                            + "\"VillageCode\":" + villageCode + ","
                            + "\"HabitationCode\":" + habitationCode + ","
                            + "\"TownCode\":" + townCode + ","
                            + "\"WardCode\":" + wardCode + ","
                            + "\"ISUrban\":\"Y\","
                            + "\"ApplicationNo\":\"" + rc_num + "\","
                            + "\"MemID\":" + member_ID + ","
                            + "\"ApplicantEName\":\"" + memberName_Eng + "\","
                            + "\"ApplicantKName\":\"" + memberName + "\","
                            + "\"Salutation\":" + salutation_Code + ","
                            + "\"AppFEName\":\"" + fatherHusName_Eng + "\","
                            + "\"AppFKName\":\"" + fatherHusName + "\","
                            + "\"AppMEName\":\"" + motherName_Eng + "\","
                            + "\"AppMKName\":\"" + motherName + "\","
                            + "\"AppKHOF\":\"" + hofName + "\","
                            + "\"AppEHOF\":\"" + hofName_Eng + "\","
                            + "\"HOFRelation\":" + rel_HOF_Code + ","
                            + "\"AppEAddress1\":\"" + address1_Eng + "\","
                            + "\"AppEAddress2\":\"" + address2_Eng + "\","
                            + "\"AppEAddress3\":\"" + address3_Eng + "\","
                            + "\"AppKAddress1\":\"" + address1 + "\","
                            + "\"AppKAddress2\":\"" + address2 + "\","
                            + "\"AppKAddress3\":\"" + address3 + "\","
                            + "\"PIN\":" + pin + ","
                            + "\"MobileNo\":\"" + mobileNum + "\","
                            + "\"age\":" + age + ","
                            + "\"gender\":" + gender_Code + ","
                            + "\"FamilyIncomeVA\":" + totalIncome + ","
                            + "\"FamilyIncomeRI\":" + totalIncome + ","
                            + "\"Religion\":" + religion_Code + ","
                            + "\"ReservationVA\":" + category_Code + ","
                            + "\"ReservationRI\":" + category_Code + ","
                            + "\"casteVA\":" + caste_Code + ","
                            + "\"casteRI\":" + caste_Code + ","
                            + "\"Creamy\":\"" + creamyLayer + "\","
                            + "\"CreamyReportVA\":\"" + reasonCode_creamy + "\","
                            + "\"CreamyReportRI\":\"" + reasonCode_creamy + "\","
                            + "\"ResidentVA\":" + residingYear + ","
                            + "\"ResidentRI\":" + residingYear + ","
                            + "\"VAComments\":\"" + remarks + "\","
                            + "\"RIComments\":\"" + remarks + "\","
                            + "\"UserID\":\"" + uName_get + "\","
                            + "\"KBincom\":" + bincom_Code + ","
                            + "\"EBincom\":" + bincom_Code + ","
                            + "\"AddressFlag\":\"C\","
                            + "\"IncomeFlag\":\"N\""
                            + "}";
                    Log.d("String_values1", "" + values1);

                    apiInterface = APIClient.getClient(getString(R.string.VA_Update_url)).create(APIInterface.class);

                    JsonObject jsonObject = new JsonParser().parse(values1).getAsJsonObject();
                    Log.d("jsonObject", "" + jsonObject);

                    //GET List Resources
                    Call<String> call = apiInterface.doGetUploadResponseFromVA(getString(R.string.flag1), getString(R.string.flag2), jsonObject);

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("TAG", response.code() + "");
                            String multipleResource = response.body();
                            Log.d("multipleResource", multipleResource + "");
                            Runnable runnable = () -> Toast.makeText(getApplicationContext(), "Cannot Update: " + response.code(), Toast.LENGTH_SHORT).show();
                            try {
                                if (multipleResource.equals("success") && multipleResource != null && !multipleResource.equals("0")) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i1 = new Intent(E_Kshana_Freeze_Details_FOR_VA.this, E_Kshana_MainScreen_VA.class);
                                    i1.putExtra("rc_num", rc_num);
                                    i1.putExtra("districtCode", distCode_ass);
                                    i1.putExtra("talukCode", talCode_ass);
                                    i1.putExtra("hobliCode", hobCode_ass);
                                    i1.putExtra("uName_get", uName_get);
                                    startActivity(i1);
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    runOnUiThread(runnable);
                                }
                            } catch (NullPointerException e) {
                                dialog.dismiss();
                                runOnUiThread(runnable);
                                e.printStackTrace();
                                runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
                                Log.e("NullPointerException", "" + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("data1_Throwable", t.getMessage() + "");
                        }
                    });
                } else {
                    buildAlert_Internet();
                    //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEdit.setOnClickListener(v -> {
            Intent i1;
            if (dataEntry.equals("Y")){
                i1 = new Intent(E_Kshana_Freeze_Details_FOR_VA.this, E_Kshana_Freeze_Member_Details_VA.class);
                i1.putExtra("rc_num", rc_num);
                i1.putExtra("member_ID", member_ID);
                i1.putExtra("distCode_ass",distCode_ass);
                i1.putExtra("talCode_ass",talCode_ass);
                i1.putExtra("hobCode_ass",hobCode_ass);
                i1.putExtra("uName_get", uName_get);
                i1.putExtra("Updated_MemberID", Updated_MemberID);
                startActivity(i1);
                finish();
            }else if (dataEntry.equals("N")){
                i1 = new Intent(E_Kshana_Freeze_Details_FOR_VA.this, E_Kshana_Member_Details_VA.class);
                i1.putExtra("rc_num", rc_num);
                i1.putExtra("member_ID", member_ID);
                i1.putExtra("distCode_ass",distCode_ass);
                i1.putExtra("talCode_ass",talCode_ass);
                i1.putExtra("hobCode_ass",hobCode_ass);
                i1.putExtra("uName_get", uName_get);
                i1.putExtra("Updated_MemberID", Updated_MemberID);
                startActivity(i1);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void check_Kannada_Key_lang(){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        ims = imm.getCurrentInputMethodSubtype();
        String locale = ims.getLocale();
        Locale locale2 = new Locale(locale);
        String currentLanguage = locale2.getDisplayLanguage();
        Log.d("lang:", "" + currentLanguage);
        if (!Objects.equals(currentLanguage, "kn_in")) {
            Toast.makeText(getApplicationContext(), "ದಯವಿಟ್ಟು ಕನ್ನಡ ಭಾಷೆಗೆ ಬದಲಾಯಿಸಿ", Toast.LENGTH_SHORT).show();
            return_Value = searchPackage();
            Log.d("return_Value", "" +return_Value);
            if(!return_Value){
                Log.d("Enter", "!return_value");
                buildAlertMessage();
            }else {
                imm.showInputMethodPicker();
            }
        }
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
            if(s1.contains("Samyojane")){
                get=true;
            }
        }
        Log.d("search", String.valueOf(get));
        return get;

    }

    private  void buildAlertMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("ಕೀ ಬೋರ್ಡ್ ಭಾಷೆ ಕನ್ನಡ ಭಾಷೆಯಲ್ಲಿ ಇರತಕ್ಕದ್ದು, ಭಾಷೆಯನ್ನು ಬದಲಾವಣೆ ಮಾಡಲು ಬಯಸುವಿರಾ?")
                .setCancelable(false)
                .setPositiveButton("ಹೌದು", (dialog, id) -> {
                    startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
                    onBackPressed();
                })
                .setNegativeButton("ಇಲ್ಲ", (dialog, id) -> buildAlert());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private  void buildAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("ಪ್ರಸ್ತುತ ಭಾಷೆಯಲ್ಲಿ ಮುಂದುವರೆಸಲು ಆಗುವುದಿಲ್ಲ. ಭಾಷೆಯನ್ನು ಕನ್ನಡದಲ್ಲಿ ಬದಲಾಯಿಸಿ.")
                .setCancelable(false)
                .setPositiveButton("ಸರಿ", (dialog, id) -> {
                    startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
                    onBackPressed();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
}
