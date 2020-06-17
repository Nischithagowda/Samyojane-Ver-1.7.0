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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class E_Kshana_Member_Details_VA_Second extends AppCompatActivity {

    String SOAP_ACTION = "http://tempuri.org/KannadatoEnglish";
    public final String OPERATION_NAME = "KannadatoEnglish";  //Method_name

    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";  // NAMESPACE
    String SOAP_ADDRESS = "http://164.100.133.47/nk_namesearch/SearchRecordsByName_RCOTC.asmx";

    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String nameFlag = "Failed", nameFlag_All = "Failed";

    APIInterface apiInterface;
    AutoCompleteTextView autoSearchCaste;
    Spinner spGender, spCategory, spReligion, spReasons;
    EditText tvPinCode, etDOB, etMobile, etAnnualIncome, etYear, tvRemarks, etAddress1, etAddress2, etAddress3;
    RadioGroup radioGroup, radioGroup3;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton33;
    Button btnUpload;
    SqlLiteOpenHelper_Class_Kan sqlLiteOpenHelper_class_kan;
    String option="ಹೌದು", option_Flag;
    ArrayAdapter<CharSequence> adapter_reason;
    String strReason, strCategory, strSearchCaste;
    int getCatCode=0, getCasteCode=0;
    int posReason;
    int reason_Code_1=0;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    String religionName, address1;
    int religionCode=0, genderCode=0;
    String str_add1=null, address1_Eng=null, str_add2=null, address2_Eng=null, str_add3=null, address3_Eng=null, pincode=null,
            dob=null, age=null, genderName=null, mobile=null, income=null, remarks=null, residingYear=null;
    String uName_get;

    ProgressDialog dialog;

    SQLiteAssetHelper_Masters sqLiteAssetHelper_masters;
    List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Gender = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Religion = new ArrayList<>();
    ArrayAdapter<AutoCompleteTextBox_Object> adapter, adapter_Gender, adapter_Religion;
    Activity activity;
    String member_ID, rc_num, memberName, memberName_Eng;
    int districtCode, search_taluk_Code, search_hobli_Code, search_town_Code, search_ward_Code;
    int bincom_Code, salutation_Code, hof_relaCode;
    String father_husband_Name=null, father_husband_Name_Eng=null, motheName=null, motheName_Eng=null, txt_HOF_Name=null, hofName_Eng=null;
    String distCode_ass, talCode_ass, hobCode_ass;

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

    private InputFilter filter_Kan_Add = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "abcefgijklmnopqsuvwxyz";
        String l2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String op1 = "~`!@$%^&*()_-''+={}[]:?><.\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶";
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
    int income_len, income_Value;
    Character code, code_Value='5';
    int len;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_kshana_member_details_second);

        option="ಹೌದು";

        etAddress1 = findViewById(R.id.etAddress1);
        etAddress2 = findViewById(R.id.etAddress2);
        etAddress3 = findViewById(R.id.etAddress3);
        tvPinCode = findViewById(R.id.tvPinCode);
        etDOB = findViewById(R.id.etDOB);
        spGender = findViewById(R.id.spGender);
        etMobile = findViewById(R.id.etMobile);
        etAnnualIncome = findViewById(R.id.etAnnualIncome);
        spCategory = findViewById(R.id.spCategory);
        autoSearchCaste = findViewById(R.id.autoSearchCaste);
        spReligion = findViewById(R.id.spReligion);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        spReasons = findViewById(R.id.spReasons);
        etYear = findViewById(R.id.etYear);
        tvRemarks = findViewById(R.id.tvRemarks);
        btnUpload = findViewById(R.id.btnUpload);

        spReasons.setVisibility(View.GONE);
        autoSearchCaste.setVisibility(View.GONE);

        PhoneNumberUtils.formatNumber(etMobile.getText().toString());
        etMobile.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)}); // 10 is max digits

        PhoneNumberUtils.formatNumber(tvPinCode.getText().toString());
        tvPinCode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});

        PhoneNumberUtils.formatNumber(tvPinCode.getText().toString());
        etDOB.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});

        PhoneNumberUtils.formatNumber(etYear.getText().toString());
        etYear.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)}); // 3 is max digits

        PhoneNumberUtils.formatNumber(tvRemarks.getText().toString());
        tvRemarks.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        tvRemarks.setFilters(new InputFilter[] { filter_Kan });
        etAddress1.setFilters(new InputFilter[] { filter_Kan_Add });
        etAddress2.setFilters(new InputFilter[] { filter_Kan_Add });
        etAddress3.setFilters(new InputFilter[] { filter_Kan_Add });

        tvRemarks.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });
        etAddress1.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });
        etAddress2.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });
        etAddress3.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });

        etDOB.setHint(getString(R.string.enter_age));
        etDOB.setFocusable(true);

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(this);
        sqlLiteOpenHelper_class_kan.open_Reasons_Master_Tbl();

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();

        openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(this);
        database = openHelper.getReadableDatabase();

        GetGender();
        GetReligion();
        GetCategory();

        Intent i = getIntent();
        rc_num = i.getStringExtra("rc_num");
        member_ID = i.getStringExtra("member_ID");
        memberName = i.getStringExtra("memberName");
        districtCode = i.getIntExtra("districtCode", 0);
        search_taluk_Code = i.getIntExtra("search_taluk_Code", 0);
        search_hobli_Code = i.getIntExtra("search_hobli_Code",0);
        search_town_Code = i.getIntExtra("search_town_Code",0);
        search_ward_Code = i.getIntExtra("search_ward_Code",0);
        bincom_Code = i.getIntExtra("bincom_Code",0);
        salutation_Code = i.getIntExtra("salutation_Code",0);
        father_husband_Name = i.getStringExtra("father_husband_Name");
        motheName = i.getStringExtra("motheName");
        txt_HOF_Name = i.getStringExtra("txt_HOF_Name");
        hof_relaCode = i.getIntExtra("hof_relaCode",0);
        distCode_ass = i.getStringExtra("distCode_ass");
        talCode_ass = i.getStringExtra("talCode_ass");
        hobCode_ass = i.getStringExtra("hobCode_ass");
        uName_get = i.getStringExtra("uName_get");

        Log.d("member_ID",""+member_ID+", rc_num:"+rc_num+", memberName:"+memberName);
        Log.d("districtCode",""+districtCode+", search_taluk_Code:"+search_taluk_Code+", search_hobli_Code:"+search_hobli_Code);
        Log.d("search_town_Code",""+search_town_Code+", search_ward_Code:"+search_ward_Code);
        Log.d("bincom_Code",""+bincom_Code+", salutation_Code:"+salutation_Code);
        Log.d("father_husband_Name",""+father_husband_Name+", motheName:"+motheName);
        Log.d("txt_HOF_Name",""+txt_HOF_Name+", hof_relaCode:"+hof_relaCode);
        Log.d("uName_get", uName_get);

        if (isNetworkAvailable()) {
            new E_Kshana_Member_Details_VA_Second.GetEngName().execute();
        }else {
            buildAlert_Internet();
            //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
        }

        @SuppressLint("Recycle")
        Cursor cursor1 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                +" where "+DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID+"='"+rc_num+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.RC_Member_ID+"="+member_ID,null);

        if (cursor1.getCount()>0){
            if (cursor1.moveToNext()){
                Log.d("Loop", "Enter Freeze cursor>0");
                str_add1 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress1));
                str_add2 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress2));
                str_add3 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress3));
                pincode = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_PIN));
                age = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Age));
                genderCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Gender));
                mobile = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_MobileNo));
                income = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_FamilyIncome_VA));
                getCatCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_app_Reservation_VA));
                getCasteCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_App_Caste_VA));
                religionCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Religion));
                residingYear = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_ResidentYears_VA));
            }
        }

        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
        strSearchCaste  = sqlLiteOpenHelper_class_kan.GetCaste_BY_Code(getCatCode, getCasteCode);
        Log.d("strSearchCaste", ""+strSearchCaste);

        etAddress1.setText(str_add1);
        etAddress2.setText(str_add2);
        etAddress3.setText(str_add3);
        tvPinCode.setText(pincode);
        etDOB.setText(age);
        spGender.setSelection(genderCode);
        etMobile.setText(mobile);
        etAnnualIncome.setText(income);
        spCategory.setSelection(getCatCode);
        autoSearchCaste.setText(strSearchCaste);
        spReligion.setSelection(religionCode);
        etYear.setText(residingYear);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton1) {
                option = "ಹೌದು";
                spReasons.setVisibility(View.GONE);
                spReasons.setSelection(0);
            } else if (checkedId == R.id.radioButton2) {
                option = "ಇಲ್ಲ";
                spReasons.setVisibility(View.VISIBLE);
            }
        });

        adapter_reason = ArrayAdapter.createFromResource(this, R.array.Reason_array_kan, R.layout.spinner_item_color);
        adapter_reason.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spReasons.setAdapter(adapter_reason);

        spReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strReason = String.valueOf(spReasons.getSelectedItem());
                posReason = position;
                sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(E_Kshana_Member_Details_VA_Second.this);
                sqlLiteOpenHelper_class_kan.open_Reasons_Master_Tbl();
                reason_Code_1 = sqlLiteOpenHelper_class_kan.Get_CreamyLayerReasons(strReason, "Reasons_k");
                Log.d("Number", ""+ reason_Code_1);
                Log.d("Item_Position", ""+ position);
                Log.d("Spinner_Value", ""+strReason);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderName = ((AutoCompleteTextBox_Object) spGender.getSelectedItem()).getValue();
                genderCode = Integer.parseInt(((AutoCompleteTextBox_Object) spGender.getSelectedItem()).getId());
                Log.d("Selected Item", ""+genderName+", ID:"+genderCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCategory = ((SpinnerObject) spCategory.getSelectedItem()).getValue();
                Log.d("Selected Item", ""+strCategory);
                sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                getCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strCategory);
                Log.d("Category_Code", ""+ getCatCode);
                if (!strCategory.equals("-- ಆಯ್ಕೆ --")) {
                    autoSearchCaste.setVisibility(View.VISIBLE);
                    GetCaste(getCatCode);
                }
                else {
                    autoSearchCaste.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCategory.setOnTouchListener((v, event) -> {
            autoSearchCaste.setText("");
            autoSearchCaste.setError(null);
            return false;
        });

        spReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                religionName = ((AutoCompleteTextBox_Object) spReligion.getSelectedItem()).getValue();
                religionCode = Integer.parseInt(((AutoCompleteTextBox_Object) spReligion.getSelectedItem()).getId());
                Log.d("Selected Item", ""+religionName+", ID:"+religionCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        autoSearchCaste.setOnTouchListener((v, event) -> {
            autoSearchCaste.showDropDown();
            autoSearchCaste.setError(null);
            return false;
        });

        btnUpload.setOnClickListener(v -> {
            str_add1 = etAddress1.getText().toString();
            str_add2 = etAddress2.getText().toString();
            str_add3 = etAddress3.getText().toString();
            pincode = tvPinCode.getText().toString();
            age = etDOB.getText().toString();
            mobile = etMobile.getText().toString();
            income = etAnnualIncome.getText().toString();
            residingYear = etYear.getText().toString();
            remarks = tvRemarks.getText().toString();

            if (isNetworkAvailable()) {
                if (address1_Eng==null && address2_Eng==null) {
                    new E_Kshana_Member_Details_VA_Second.GetEngName_All().execute();
                }
            }else {
                buildAlert_Internet();
                //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
            }

            Log.d("Data","str_add1:"+str_add1+", str_add2:"+str_add2+", str_add3:"+str_add3+"pincode:"+pincode+"");
            Log.d("Data","dob:"+dob+", mobile:"+mobile+", income:"+income+", residingYear:"+residingYear+", remarks"+remarks);
            Log.d("Data","getCatCode:"+getCatCode+", getCasteCode:"+getCasteCode+", religionCode:"+religionCode+", income:"+income+", residingYear:"+residingYear+", remarks"+remarks);

            if (!TextUtils.isEmpty(str_add1)){
                if (!TextUtils.isEmpty(str_add2)){
                    if (!TextUtils.isEmpty(pincode)){
                        code = pincode.charAt(0);
                        len = pincode.length();
                        Log.d("code",""+code);
                        if (code.equals(code_Value) && len == 6){
                            if (!TextUtils.isEmpty(age)){
//                                Date date = new Date(dob);
//                                Age = calculateAge(date);
                                if (genderCode!=0){
                                    if (!TextUtils.isEmpty(mobile)){
                                        if (isValidMobile(mobile)) {
                                            if (!TextUtils.isEmpty(income)){
                                                income_len = income.length();
                                                income_Value = Integer.parseInt(income);
                                                Log.d("Income value", ""+income+", Length: "+income_len+", Value: "+ income_Value);
                                                if (income_len >= 4 && income_Value>=1000) {
                                                    if (getCatCode!=0){
                                                        if (getCasteCode!=0){
                                                            if (religionCode!=0) {
                                                                Runnable please_try_again = () -> Toast.makeText(getApplicationContext(), "Please Try again", Toast.LENGTH_SHORT).show();
                                                                if (option.equals("ಹೌದು")) {
                                                                    option_Flag = "Y";
                                                                    if (!TextUtils.isEmpty(residingYear) && Integer.parseInt(residingYear) > 0 && Integer.parseInt(residingYear) <= 130) {
                                                                        if (!TextUtils.isEmpty(remarks)) {
                                                                            if (isNetworkAvailable()) {
                                                                                if (nameFlag.equals("Success") && memberName_Eng!=null && hofName_Eng!=null && father_husband_Name_Eng!=null && motheName_Eng!=null) {
                                                                                    if (nameFlag_All.equals("Success") && address1_Eng!=null && address2_Eng!=null) {
                                                                                        dialog.show();
                                                                                        Log.d("Value", "Complete");
                                                                                        String values1;
                                                                                        values1 = "{"
                                                                                                + "\"DistCode\":" + districtCode + ","
                                                                                                + "\"TalukCode\":" + search_taluk_Code + ","
                                                                                                + "\"HobliCode\":" + search_hobli_Code + ","
                                                                                                + "\"VillageCode\":99999,"
                                                                                                + "\"HabitationCode\":255,"
                                                                                                + "\"TownCode\":" + search_town_Code + ","
                                                                                                + "\"WardCode\":" + search_ward_Code + ","
                                                                                                + "\"ISUrban\":\"Y\","
                                                                                                + "\"ApplicationNo\":\"" + rc_num + "\","
                                                                                                + "\"MemID\":" + member_ID + ","
                                                                                                + "\"ApplicantEName\":\"" + memberName_Eng + "\","
                                                                                                + "\"ApplicantKName\":\"" + memberName + "\","
                                                                                                + "\"Salutation\":" + salutation_Code + ","
                                                                                                + "\"AppFEName\":\"" + father_husband_Name_Eng + "\","
                                                                                                + "\"AppFKName\":\"" + father_husband_Name + "\","
                                                                                                + "\"AppMEName\":\"" + motheName_Eng + "\","
                                                                                                + "\"AppMKName\":\"" + motheName + "\","
                                                                                                + "\"AppKHOF\":\"" + txt_HOF_Name + "\","
                                                                                                + "\"AppEHOF\":\"" + hofName_Eng + "\","
                                                                                                + "\"HOFRelation\":" + hof_relaCode + ","
                                                                                                + "\"AppEAddress1\":\"" + address1_Eng + "\","
                                                                                                + "\"AppEAddress2\":\"" + address2_Eng + "\","
                                                                                                + "\"AppEAddress3\":\"" + address3_Eng + "\","
                                                                                                + "\"AppKAddress1\":\"" + str_add1 + "\","
                                                                                                + "\"AppKAddress2\":\"" + str_add2 + "\","
                                                                                                + "\"AppKAddress3\":\"" + str_add3 + "\","
                                                                                                + "\"PIN\":" + pincode + ","
                                                                                                + "\"MobileNo\":\"" + mobile + "\","
                                                                                                + "\"age\":" + age + ","
                                                                                                + "\"gender\":" + genderCode + ","
                                                                                                + "\"FamilyIncomeVA\":" + income + ","
                                                                                                + "\"FamilyIncomeRI\":" + income + ","
                                                                                                + "\"Religion\":" + religionCode + ","
                                                                                                + "\"ReservationVA\":" + getCatCode + ","
                                                                                                + "\"ReservationRI\":" + getCatCode + ","
                                                                                                + "\"casteVA\":" + getCasteCode + ","
                                                                                                + "\"casteRI\":" + getCasteCode + ","
                                                                                                + "\"Creamy\":\"" + option_Flag + "\","
                                                                                                + "\"CreamyReportVA\":\"" + reason_Code_1 + "\","
                                                                                                + "\"CreamyReportRI\":\"" + reason_Code_1 + "\","
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
                                                                                                if (multipleResource.equals("success")) {
                                                                                                    dialog.dismiss();
                                                                                                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                                                                                    Intent i = new Intent(E_Kshana_Member_Details_VA_Second.this, E_Kshana_MainScreen_VA.class);
                                                                                                    i.putExtra("rc_num", rc_num);
                                                                                                    i.putExtra("districtCode", distCode_ass);
                                                                                                    i.putExtra("talukCode", talCode_ass);
                                                                                                    i.putExtra("hobliCode", hobCode_ass);
                                                                                                    i.putExtra("uName_get", uName_get);
                                                                                                    startActivity(i);
                                                                                                    finish();
                                                                                                } else {

                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<String> call, Throwable t) {
                                                                                                Log.d("data1_Throwable", t.getMessage() + "");
                                                                                            }
                                                                                        });
                                                                                    } else {
                                                                                        new E_Kshana_Member_Details_VA_Second.GetEngName_All().execute();
                                                                                        runOnUiThread(please_try_again);
                                                                                    }
                                                                                } else {
                                                                                    new E_Kshana_Member_Details_VA_Second.GetEngName().execute();
                                                                                    runOnUiThread(please_try_again);
                                                                                }
                                                                            }else {
                                                                                buildAlert_Internet();
                                                                                //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {
                                                                            tvRemarks.setError(getString(R.string.enter_remarks));
                                                                        }
                                                                    } else {
                                                                        etYear.setError("ತಪ್ಪಾಗಿರುವ ಮೌಲ್ಯ");
                                                                    }
                                                                } else {
                                                                    option_Flag = "N";
                                                                    if (reason_Code_1 != 0) {
                                                                        if (!TextUtils.isEmpty(residingYear) && Integer.parseInt(residingYear) > 0 && Integer.parseInt(residingYear) <= 130) {
                                                                            if (!TextUtils.isEmpty(remarks)) {
                                                                                if (isNetworkAvailable()) {
                                                                                    if (nameFlag.equals("Success") && memberName_Eng!=null && hofName_Eng!=null && father_husband_Name_Eng!=null && motheName_Eng!=null) {
                                                                                        if (nameFlag_All.equals("Success") && address1_Eng!=null && address2_Eng!=null) {
                                                                                            dialog.show();
                                                                                            Log.d("Value", "Complete");
                                                                                            String values1;
                                                                                            values1 = "{"
                                                                                                    + "\"DistCode\":" + districtCode + ","
                                                                                                    + "\"TalukCode\":" + search_taluk_Code + ","
                                                                                                    + "\"HobliCode\":" + search_hobli_Code + ","
                                                                                                    + "\"VillageCode\":99999,"
                                                                                                    + "\"HabitationCode\":255,"
                                                                                                    + "\"TownCode\":" + search_town_Code + ","
                                                                                                    + "\"WardCode\":" + search_ward_Code + ","
                                                                                                    + "\"ISUrban\":\"Y\","
                                                                                                    + "\"ApplicationNo\":\"" + rc_num + "\","
                                                                                                    + "\"MemID\":" + member_ID + ","
                                                                                                    + "\"ApplicantEName\":\"" + memberName_Eng + "\","
                                                                                                    + "\"ApplicantKName\":\"" + memberName + "\","
                                                                                                    + "\"Salutation\":" + salutation_Code + ","
                                                                                                    + "\"AppFEName\":\"" + father_husband_Name_Eng + "\","
                                                                                                    + "\"AppFKName\":\"" + father_husband_Name + "\","
                                                                                                    + "\"AppMEName\":\"" + motheName_Eng + "\","
                                                                                                    + "\"AppMKName\":\"" + motheName + "\","
                                                                                                    + "\"AppKHOF\":\"" + txt_HOF_Name + "\","
                                                                                                    + "\"AppEHOF\":\"" + hofName_Eng + "\","
                                                                                                    + "\"HOFRelation\":" + hof_relaCode + ","
                                                                                                    + "\"AppEAddress1\":\"" + address1_Eng + "\","
                                                                                                    + "\"AppEAddress2\":\"" + address2_Eng + "\","
                                                                                                    + "\"AppEAddress3\":\"" + address3_Eng + "\","
                                                                                                    + "\"AppKAddress1\":\"" + str_add1 + "\","
                                                                                                    + "\"AppKAddress2\":\"" + str_add2 + "\","
                                                                                                    + "\"AppKAddress3\":\"" + str_add3 + "\","
                                                                                                    + "\"PIN\":" + pincode + ","
                                                                                                    + "\"MobileNo\":\"" + mobile + "\","
                                                                                                    + "\"age\":" + age + ","
                                                                                                    + "\"gender\":" + genderCode + ","
                                                                                                    + "\"FamilyIncomeVA\":" + income + ","
                                                                                                    + "\"FamilyIncomeRI\":" + income + ","
                                                                                                    + "\"Religion\":" + religionCode + ","
                                                                                                    + "\"ReservationVA\":" + getCatCode + ","
                                                                                                    + "\"ReservationRI\":" + getCatCode + ","
                                                                                                    + "\"casteVA\":" + getCasteCode + ","
                                                                                                    + "\"casteRI\":" + getCasteCode + ","
                                                                                                    + "\"Creamy\":\"" + option_Flag + "\","
                                                                                                    + "\"CreamyReportVA\":\"" + reason_Code_1 + "\","
                                                                                                    + "\"CreamyReportRI\":\"" + reason_Code_1 + "\","
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
                                                                                                            Intent i1 = new Intent(E_Kshana_Member_Details_VA_Second.this, E_Kshana_MainScreen_VA.class);
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
                                                                                                        Log.e("NullPointerException", "" + e.getMessage());
                                                                                                    }
                                                                                                }

                                                                                                @Override
                                                                                                public void onFailure(Call<String> call, Throwable t) {
                                                                                                    Log.d("data1_Throwable", t.getMessage() + "");
                                                                                                }
                                                                                            });
                                                                                        } else {
                                                                                            new E_Kshana_Member_Details_VA_Second.GetEngName_All().execute();
                                                                                            runOnUiThread(please_try_again);
                                                                                        }
                                                                                    } else {
                                                                                        new E_Kshana_Member_Details_VA_Second.GetEngName().execute();
                                                                                        runOnUiThread(please_try_again);
                                                                                    }
                                                                                }else {
                                                                                    buildAlert_Internet();
                                                                                    //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            } else {
                                                                                tvRemarks.setError(getString(R.string.enter_remarks));
                                                                            }
                                                                        } else {
                                                                            etYear.setError("ತಪ್ಪಾಗಿರುವ ಮೌಲ್ಯ");
                                                                        }
                                                                    } else {
                                                                        ((TextView) spReasons.getSelectedView()).setError(getString(R.string.select_title));
                                                                        Toast.makeText(getApplicationContext(), getString(R.string.select_title), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            }else {
                                                                ((TextView) spReligion.getSelectedView()).setError(getString(R.string.select_title));
                                                                Toast.makeText(getApplicationContext(), getString(R.string.select_title), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }else{
                                                            autoSearchCaste.setError("ಜಾತಿಯ ಹೆಸರು ಅಮಾನ್ಯವಾಗಿರುತ್ತದೆ");
                                                        }
                                                    }else {
                                                        ((TextView)spCategory.getSelectedView()).setError(getString(R.string.select_title));
                                                        Toast.makeText(getApplicationContext(), getString(R.string.select_title), Toast.LENGTH_SHORT).show();
                                                    }
                                                }else {
                                                    etAnnualIncome.setError("ತಪ್ಪಾಗಿರುವ ಮೌಲ್ಯ");
                                                }
                                            }else {
                                                etAnnualIncome.setError(getString(R.string.enter_total_income));
                                            }
                                        }else{
                                            etMobile.setError("ಅಸ್ತಿತ್ವದಲ್ಲಿರುವ ದೂರವಾಣಿ ಸಂಖ್ಯೆಯನ್ನು ನಮೂದಿಸಿ");
                                        }
                                    }else {
                                        etMobile.setError(getString(R.string.enter_mobile_no));
                                    }
                                }else {
                                    ((TextView)spGender.getSelectedView()).setError(getString(R.string.select_title));
                                    Toast.makeText(getApplicationContext(), getString(R.string.select_title), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                etDOB.setError(getString(R.string.select_date));
                            }
                        }else {
                            tvPinCode.setError("ಸರಿಯಾದ ಪಿನ್ ಕೋಡ್ ನಮೂದಿಸಿ");
                        }
                    }else {
                        tvPinCode.setError(getString(R.string.enter_pincode));
                    }
                }else {
                    etAddress2.setError(getString(R.string.enter_address));
                }
            }else {
                etAddress1.setError(getString(R.string.enter_address));
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    class GetEngName extends AsyncTask<String, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

                request.addProperty("kanstr", memberName);

                Log.d("Request","" + request);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
                Log.d("URL: ",""+ SOAP_ADDRESS);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                Log.i("Result", ""+resultString);
                memberName_Eng = String.valueOf(resultString);
                Log.d("memberName_Eng", ""+memberName_Eng);

                SoapObject request1 = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
                request1.addProperty("kanstr", txt_HOF_Name);

                Log.d("Request","" + request1);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request1);

                androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
                Log.d("URL: ",""+ SOAP_ADDRESS);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                Log.i("Result", ""+resultString);
                hofName_Eng = String.valueOf(resultString);
                Log.d("hofName_Eng", ""+hofName_Eng);

                SoapObject request3 = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

                request3.addProperty("kanstr", father_husband_Name);

                Log.d("Request","" + request3);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request3);

                androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
                Log.d("URL: ",""+ SOAP_ADDRESS);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                Log.i("Result", ""+resultString);
                father_husband_Name_Eng = String.valueOf(resultString);
                Log.d("father_husband_Name_Eng", ""+father_husband_Name_Eng);

                SoapObject request4 = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
                request4.addProperty("kanstr", motheName);

                Log.d("Request","" + request4);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request4);

                androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
                Log.d("URL: ",""+ SOAP_ADDRESS);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                Log.i("Result", ""+resultString);
                motheName_Eng = String.valueOf(resultString);
                Log.d("motheName_Eng", ""+motheName_Eng);
                nameFlag = "Success";

            } catch (Exception e) {
                nameFlag = "Failed";
                e.printStackTrace();
                Log.i("Error1", e.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show();
                    Log.d("Error1", "Server Exception Occurred");
                    dialog.dismiss();
                });
            }

            return nameFlag;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        protected void onProgressUpdate(Integer... a) {

        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetEngName_All extends AsyncTask<String, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {

            try {
                SoapObject request2 = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
                request2.addProperty("kanstr", str_add1);

                Log.d("Request","" + request2);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request2);

                androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
                Log.d("URL: ",""+ SOAP_ADDRESS);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                Log.i("Result", ""+resultString);
                address1_Eng = String.valueOf(resultString);
                Log.d("address1_Eng", ""+address1_Eng);

                SoapObject request3 = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
                request3.addProperty("kanstr", str_add2);

                Log.d("Request","" + request3);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request3);

                androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
                Log.d("URL: ",""+ SOAP_ADDRESS);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                Log.i("Result", ""+resultString);
                address2_Eng = String.valueOf(resultString);
                Log.d("address2_Eng", ""+address2_Eng);

                SoapObject request4 = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
                request4.addProperty("kanstr", str_add3);

                Log.d("Request","" + request4);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request4);

                androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
                Log.d("URL: ",""+ SOAP_ADDRESS);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                resultString = (SoapPrimitive) envelope.getResponse();
                Log.i("Result", ""+resultString);
                address3_Eng = String.valueOf(resultString);
                Log.d("address3_Eng", ""+address3_Eng);

                nameFlag_All = "Success";

            } catch (Exception e) {
                nameFlag_All = "Failed";
                e.printStackTrace();
                Log.i("Error1", e.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), getString(R.string.server_exception) , Toast.LENGTH_SHORT).show();
                    Log.d("Error1", "Server Exception Occurred");
                    dialog.dismiss();
                });
            }

            return nameFlag_All;
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
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isValidMobile(String phone) {
        boolean check;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() == 10;
        } else {
            check=false;
        }
        return check;
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

    public void GetGender(){
        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();
        objects_Gender = sqLiteAssetHelper_masters.Get_Gender();
        adapter_Gender = new ArrayAdapter<>(this, R.layout.spinner_item_color, objects_Gender);
        adapter_Gender.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spGender.setAdapter(adapter_Gender);
    }

    public void GetReligion(){
        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();
        objects_Religion = sqLiteAssetHelper_masters.Get_Religion();
        adapter_Religion = new ArrayAdapter<>(this, R.layout.spinner_item_color, objects_Religion);
        adapter_Religion.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spReligion.setAdapter(adapter_Religion);
    }

    @SuppressLint("ShowToast")
    public void GetCategory() {
        try {
            List<SpinnerObject> labels;
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            labels = sqlLiteOpenHelper_class_kan.Get_Category_NK();

            ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_color, labels);
            dataAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spCategory.setAdapter(dataAdapter);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_creating_table), Toast.LENGTH_LONG);
        }
    }

    public void GetCaste(int num){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class_kan.GetCaste(num);

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchCaste.setAdapter(adapter);
        autoSearchCaste.setOnItemClickListener((parent, view, position, id) -> {
            strSearchCaste = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchCaste);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(strSearchCaste);
            Log.d("Selected_casteCode", ""+ getCasteCode);
        });
    }

    private  void buildAlert_Back() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Alert")
                .setMessage("Do you want to discard the changes?")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, id) -> {
                    Intent i1 = new Intent(E_Kshana_Member_Details_VA_Second.this, E_Kshana_MainScreen_VA.class);
                    i1.putExtra("rc_num", rc_num);
                    i1.putExtra("districtCode", distCode_ass);
                    i1.putExtra("talukCode", talCode_ass);
                    i1.putExtra("hobliCode", hobCode_ass);
                    i1.putExtra("uName_get", uName_get);
                    startActivity(i1);
                    finish();
                })
                .setNegativeButton("NO", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(18);
    }

    @Override
    public void onBackPressed() {
        buildAlert_Back();
    }
}
