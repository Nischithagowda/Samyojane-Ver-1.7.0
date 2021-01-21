package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class New_Request_Caste_sc_st_certi_Parameters extends AppCompatActivity {

    TextView tvHobli, tvTaluk, tvVA_Name, tvServiceName, txtFatherCategory, txtMotherCategory, txtAppCategory;
    String district, taluk, hobli, VA_Name,VA_Circle_Name, applicant_Id, rationCardNo, aadharNo, mobileNo, address1;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code, item_position;
    String strSearchServiceName, strSearchVillageName, strFatherCategory, strSearchFatherCaste, strMotherCategory,
            strSearchMotherCaste, strAppCategory_VA, strSearchAppCaste_VA;
    int villageCode, serviceCode;
    String habitationCode;
    Spinner spYears,spFatherCategory, spMotherCategory, spAPPCategory_VA, spRejectReason;
    AutoCompleteTextView autoSearchFatherCaste, autoSearchMotherCaste, autoSearchAPPCaste_VA;
    RadioGroup radioGroup1;
    RadioButton radioButton1, radioButton11;
    EditText tvRemarks, tvIncome;
    ArrayAdapter<CharSequence> adapter_rejection_reason, adapter_Year;
    String strRejectionReason, strYear;
    int posRejectionReason;
    int reason_Code_1;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    String option1;
    LinearLayout lRejection;
    Button btnSave, btnCamera, btnBack;
    private static final int CAMERA_REQUEST = 1;
    byte[] imageInByte;
    static String store=null;
    static String getString=null;
    ImageView imageView;
    String strRemarks;
    int income_len, income_Value;
    GPSTracker gpsTracker;
    double latitude, longitude;
    Set_and_Get_Service_Parameter set_and_get_service_parameter;
    int getFatherCatCode=0, getFatherCasteCode=0, getMotherCatCode=0, getMotherCasteCode=0, getAppCatCode_VA=0, getAppCasteCode_VA=0;
    AppCompatActivity activity;
    String strIncome;
    ProgressDialog dialog;
    String returnStr;
    String service_name, village_name, town_Name, ward_Name, town_code, ward_code, option_Flag;
    String eng_certi, GSC_FirstPart;
    String amount, caste_code, caste_name, category_code, category_name;
    String appImage=null;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;


    InputFilter filter_Eng = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "ಅಆಇಈಉಊಋಎಏಐಒಓಔಅಂಅಃ";
        String l2 = "ಕಕಾಕಿಕೀಕುಕೂಕೃಕೆಕೇಕೈಕೊಕೋಕೌಕಂಕಃಕ್";
        String l3 = "ಖಖಾಖಿಖೀಖುಖೂಖೃಖೆಖೇಖೈಖೊಖೋಖೌಖಂಖಃಖ್";
        String l4 = "ಗಗಾಗಿಗೀಗುಗೂಗೃಗೆಗೇಗೈಗೊಗೋಗೌಗಂಗಃಗ್";
        String l5 = "ಘಘಾಘಿಘೀಘುಘೂಘೃಘೆಘೇಘೈಘೊಘೋಘೌಘಂಘಃಘ್";
        String l6 = "ಙಙಾಙಿಙೀಙುಙೂಙೃಙೆಙೇಙೈಙೊಙೋಙೌಙಂಙಃಙ್";
        String l7 = "ಚಚಾಚಿಚೀಚುಚೂಚೃಚೆಚೇಚೈಚೊಚೋಚೌಚಂಚಃಚ್";
        String l8 = "ಛಛಾಛಿಛೀಛುಛೂಛೃಛೆಛೇಛೈಛೊಛೋಛೌಛಂಛಃಛ್";
        String l9 = "ಜಜಾಜಿಜೀಜುಜೂಜೃಜೆಜೇಜೈಜೊಜೋಜೌಜಂಜಃಜ್";
        String l10 = "ಝಝಾಝಿಝೀಝುಝೂಝೃಝೆಝೇಝೈಝೊಝೋಝೌಝಂಝಃಝ್";
        String l11 = "ಞಞಾಞಿಞೀಞುಞೂಞೃಞೆಞೇಞೈಞೊಞೋಞೌಞಂಞಃಞ್";
        String l12 = "ಟಟಾಟಿಟೀಟುಟೂಟೃಟೆಟೇಟೈಟೊಟೋಟೌಟಂಟಃಟ್";
        String l13 = "ಠಠಾಠಿಠೀಠುಠೂಠೃಠೆಠೇಠೈಠೊಠೋಠೌಠಂಠಃಠ್";
        String l14 = "ಡಡಾಡಿಡೀಡುಡೂಡೃಡೆಡೇಡೈಡೊಡೋಡೌಡಂಡಃಡ್";
        String l15 = "ಢಢಾಢಿಢೀಢುಢೂಢೃಢೆಢೇಢೈಢೊಢೋಢೌಢಂಢಃಢ್";
        String l16 = "ಣಣಾಣಿಣೀಣುಣೂಣೃಣೆಣೇಣೈಣೊಣೋಣೌಣಂಣಃಣ್";
        String l17 = "ತತಾತಿತೀತುತೂತೃತೆತೇತೈತೊತೋತೌತಂತಃತ್";
        String l18 = "ಥಥಾಥಿಥೀಥುಥೂಥೃಥೆಥೇಥೈಥೊಥೋಥೌಥಂಥಃಥ್";
        String l19 = "ದದಾದಿದೀದುದೂದೃದೆದೇದೈದೊದೋದೌದಂದಃದ್";
        String l20 = "ಧಧಾಧಿಧೀಧುಧೂಧೃಧೆಧೇಧೈಧೊಧೋಧೌಧಂಧಃಧ್";
        String l21 = "ನನಾನಿನೀನುನೂನೃನೆನೇನೈನೊನೋನೌನಂನಃನ್";
        String l22 = "ಪಪಾಪಿಪೀಪುಪೂಪೃಪೆಪೇಪೈಪೊಪೋಪೌಪಂಪಃಪ್";
        String l23 = "ಫಫಾಫಿಫೀಫುಫೂಫೃಫೆಫೇಫೈಫೊಫೋಫೌಫಂಫಃಫ್";
        String l24 = "ಬಬಾಬಿಬೀಬುಬೂಬೃಬೆಬೇಬೈಬೊಬೋಬೌಬಂಬಃಬ್";
        String l25 = "ಭಭಾಭಿಭೀಭುಭೂಭೃಭೆಭೇಭೈಭೊಭೋಭೌಭಂಭಃಭ್";
        String l26 = "ಮಮಾಮಿಮೀಮುಮೂಮೃಮೆಮೇಮೈಮೊಮೋಮೌಮಂಮಃಮ್";
        String l27 = "ಯಯಾಯಿಯೀಯುಯೂಯೃಯೆಯೇಯೈಯೊಯೋಯೌಯಂಯಃಯ್";
        String l28 = "ರರಾರಿರೀರುರೂರೃರೆರೇರೈರೊರೋರೌರಂರಃರ್";
        String l29 = "ಱಱಾಱಿಱೀಱುಱೂಱೃಱೆಱೇಱೈಱೊಱೋಱೌಱಂಱಃಱ್";
        String l30 = "ಲಲಾಲಿಲೀಲುಲೂಲೃಲೆಲೇಲೈಲೊಲೋಲೌಲಂಲಃಲ್";
        String l31 = "ವವಾವಿವೀವುವೂವೃವೆವೇವೈವೊವೋವೌವಂವಃವ್";
        String l32 = "ಶಶಾಶಿಶೀಶುಶೂಶೃಶೆಶೇಶೈಶೊಶೋಶೌಶಂಶಃಶ್";
        String l33 = "ಷಷಾಷಿಷೀಷುಷೂಷೃಷೆಷೇಷೈಷೊಷೋಷೌಷಂಷಃಷ್";
        String l34 = "ಸಸಾಸಿಸೀಸುಸೂಸೃಸೆಸೇಸೈಸೊಸೋಸೌಸಂಸಃಸ್";
        String l35 = "ಹಹಾಹಿಹೀಹುಹೂಹೃಹೆಹೇಹೈಹೊಹೋಹೌಹಂಹಃಹ್";
        String l36 = "ಳಳಾಳಿಳೀಳುಳೂಳೃಳೆಳೇಳೈಳೊಳೋಳೌಳಂಳಃಳ್";
        String l37 = "ೞೞಾೞಿೞೀೞುೞೂೞೃೞೆೞೇೞೈೞೊೞೋೞೌೞಂೞಃೞ್";
        String op1 = "~`!@#$%^&*()_-''+={}[]:/?><,.\\\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶1234567890೧೨೩೪೫೬೭೮೯೦";

        String blockCharacterSet = l1+l2+l3+l4+l5+l6+l7+l8+l9+l10+l11+l12+l13+l14+l15+l16+l17+l18+l19+l20
                +l21+l22+l23+l24+l25+l26+l27+l28+l29+l30+l31+l32+l33+l34+l35+l36+l37+op1;
        if (source != null && blockCharacterSet.contains(("" + source))) {
            Log.d("Blocked",""+source);
            return "";
        }
//        for ( int i = start ; i < end ; i++) {
//            String checkMe = String. valueOf (source.charAt(i));
//            //Pattern pattern = Pattern.compile("[\\u0C80-\\u0CFF]");
//            Pattern pattern = Pattern.compile("a-zA-Z");
//            Matcher re = pattern.matcher(checkMe);
//            if (!re.matches()) {
//                Log.d("Filter_valid","blocked");
//                return "";
//            }
//        }
        Log.d("Filter_valid","Not blocked");
        return null;
    };

    InputFilter filter_Kan = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "abcdefghijklmnopqrstuvwxyz";
        String l2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String op1 = "~`!@#$%^&*()_-''+={}[]:/?><,.\\\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶1234567890೧೨೩೪೫೬೭೮೯೦";
        String blockCharacterSet = l1+l2+op1;

        for (int i = start; i < end; i++) {
            Log.d("source.charAt(i)",""+i+" : "+source.charAt(i));
            if (blockCharacterSet.contains("" + source.charAt(i))) {
                Log.d("Blocked",""+source);
                return "";
            }
        }
        Log.d("Filter_valid","Not blocked");
        return null;
    };

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"ClickableViewAccessibility", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_request_caste_sc_st_certi_parameters);

        strSearchFatherCaste = getString(R.string.select_caste_spinner);
        strSearchMotherCaste = getString(R.string.select_caste_spinner);
        strSearchAppCaste_VA = getString(R.string.select_caste_spinner);
        option1 = getString(R.string.yes);
        strYear = getString(R.string.select_spinner);
        strRejectionReason = getString(R.string.reason_spinner);

        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvVA_Name = findViewById(R.id.VA_name);
        tvServiceName = findViewById(R.id.tvServiceName);
        spYears = findViewById(R.id.spYears);
        spFatherCategory = findViewById(R.id.spFatherCategory);
        spMotherCategory = findViewById(R.id.spMotherCategory);
        spAPPCategory_VA = findViewById(R.id.spAPPCategory_VA);
        autoSearchFatherCaste = findViewById(R.id.autoSearchFatherCaste);
        autoSearchMotherCaste = findViewById(R.id.autoSearchMotherCaste);
        autoSearchAPPCaste_VA = findViewById(R.id.autoSearchAPPCaste_VA);
        spRejectReason = findViewById(R.id.spRejectReason);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton11 = findViewById(R.id.radioButton11);
        tvRemarks = findViewById(R.id.tvRemarks);
        tvIncome = findViewById(R.id.tvIncome);
        lRejection = findViewById(R.id.lRejection);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        btnCamera = findViewById(R.id.btnCamera);
        imageView = findViewById(R.id.store_image);
        txtMotherCategory = findViewById(R.id.txtMotherCategory);
        txtFatherCategory = findViewById(R.id.txtFatherCategory);
        txtAppCategory = findViewById(R.id.txtAppCategory);

        lRejection.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        txtMotherCategory.setVisibility(View.GONE);
        txtFatherCategory.setVisibility(View.GONE);
        txtAppCategory.setVisibility(View.GONE);

        Intent i = getIntent();
        district = i.getStringExtra("districtCode");
        taluk = i.getStringExtra("taluk");
        district_Code = i.getStringExtra("district_Code");
        taluk_Code = i.getStringExtra("taluk_Code");
        hobli_Code = i.getStringExtra("hobli_Code");
        hobli = i.getStringExtra("hobli");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        applicant_Id = i.getStringExtra("applicant_Id");
        VA_Name = i.getStringExtra("VA_Name");
        rationCardNo = i.getStringExtra("rationCardNo");
        aadharNo = i.getStringExtra("aadharNo");
        mobileNo = i.getStringExtra("mobileNo");
        address1 = i.getStringExtra("address1");
        item_position = i.getStringExtra("item_position");
        strSearchVillageName = i.getStringExtra("strSearchVillageName");
        strSearchServiceName =i.getStringExtra("strSearchServiceName");
        villageCode = Integer.parseInt(i.getStringExtra("villageCode"));
        habitationCode = i.getStringExtra("habitationCode");
        serviceCode = Integer.parseInt(i.getStringExtra("serviceCode"));
        service_name = i.getStringExtra("strSearchServiceName");
        village_name = i.getStringExtra("strSearchVillageName");
        eng_certi = i.getStringExtra("eng_certi");
        town_code = i.getStringExtra("town_code");
        town_Name = i.getStringExtra("town_Name");
        ward_code = i.getStringExtra("ward_code");
        ward_Name = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");

        Log.d("New_Request_ScSt", ""+district_Code);
        Log.d("New_Request_ScSt..", ""+taluk_Code);
        Log.d("New_Request_ScSt..", ""+hobli_Code);
        Log.d("New_Request_ScSt", ""+va_Circle_Code);
        Log.d("Item_Position", ""+item_position);
        Log.d("Village_NameSCST", ""+strSearchVillageName);
        Log.d("Service_NameSCST", ""+strSearchServiceName);
        Log.d("villageCodeSCST", ""+ villageCode);
        Log.d("serviceCodeSCST", ""+ serviceCode);
        Log.d( "town_code: ",""+town_code);
        Log.d("town_Name: ",""+town_Name);
        Log.d( "ward_code: ",""+ward_code);
        Log.d( "ward_Name: ",""+ward_Name);
        Log.d("option_Flag: ",""+ option_Flag);

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setIndeterminate(true);
        dialog.setProgress(1);

        PhoneNumberUtils.formatNumber(tvRemarks.getText().toString());
        tvRemarks.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});

        if (eng_certi.equals("K")){
            tvRemarks.setFilters(new InputFilter[] { filter_Kan });
        }else if (eng_certi.equals("E")){
            tvRemarks.setFilters(new InputFilter[] { filter_Eng });
        }

        tvRemarks.setOnTouchListener((v, event) -> {
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(this);
        sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();

        openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_Caste_sc_st_certi_Parameters.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor=database.rawQuery("SELECT * FROM "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                +" where "+ DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"='"+serviceCode+"'"+" and "
                + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No+"="+applicant_Id, null);
        if(cursor.getCount()>0) {
            if(cursor.moveToFirst()){
                category_code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.CST_res_category));
                caste_code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.CST_caste_as_per_app));
                amount = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.CST_annual_income));
                GSC_FirstPart = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_GSCFirstPart));
                Log.d("value1", ""+category_code+" "+caste_code+" "+amount);
            }
        } else {
            cursor.close();
        }
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class();
        sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
        category_name = sqlLiteOpenHelper_class.GetCategory_BY_Code(Integer.parseInt(category_code));
        caste_name = sqlLiteOpenHelper_class.GetCaste_BY_Code(Integer.parseInt(category_code), Integer.parseInt(caste_code));

        Log.d("get_Value", ""+category_name+""+caste_name);

        autoSearchFatherCaste.setVisibility(View.GONE);
        autoSearchFatherCaste.setOnTouchListener((v, event) -> {
            autoSearchFatherCaste.showDropDown();
            autoSearchFatherCaste.setError(null);
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });

        spFatherCategory.setOnTouchListener((v, event) -> {
            autoSearchFatherCaste.setText("");
            autoSearchFatherCaste.setError(null);
            return false;
        });

        autoSearchMotherCaste.setVisibility(View.GONE);
        autoSearchMotherCaste.setOnTouchListener((v, event) -> {
            autoSearchMotherCaste.showDropDown();
            autoSearchMotherCaste.setError(null);
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });

        spMotherCategory.setOnTouchListener((v, event) -> {
            autoSearchMotherCaste.setText("");
            autoSearchMotherCaste.setError(null);
            return false;
        });

        autoSearchAPPCaste_VA.setOnTouchListener((v, event) -> {
            autoSearchAPPCaste_VA.showDropDown();
            autoSearchAPPCaste_VA.setError(null);
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });

        spAPPCategory_VA.setOnTouchListener((v, event) -> {
            autoSearchAPPCaste_VA.setText("");
            autoSearchAPPCaste_VA.setError(null);
            return false;
        });

        if (serviceCode == 7) {
            spFatherCategory.setVisibility(View.VISIBLE);
            spMotherCategory.setVisibility(View.VISIBLE);
            spAPPCategory_VA.setVisibility(View.VISIBLE);
            GetCategory_Cat_1();
        }
        else if(serviceCode == 8)
        {
            spFatherCategory.setVisibility(View.VISIBLE);
            spMotherCategory.setVisibility(View.VISIBLE);
            spAPPCategory_VA.setVisibility(View.VISIBLE);
            GetCategory_SCST();
        } else if (serviceCode == 42){
            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class();
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
            category_name = sqlLiteOpenHelper_class.GetCategory_BY_Code(Integer.parseInt(category_code));
            String str = category_name + " " + getString(R.string.category);

            spFatherCategory.setVisibility(View.GONE);
            spMotherCategory.setVisibility(View.GONE);
            spAPPCategory_VA.setVisibility(View.GONE);
            txtMotherCategory.setVisibility(View.VISIBLE);
            txtFatherCategory.setVisibility(View.VISIBLE);
            txtAppCategory.setVisibility(View.VISIBLE);

            txtMotherCategory.setText(str);
            txtFatherCategory.setText(str);
            txtAppCategory.setText(str);

            autoSearchFatherCaste.setVisibility(View.VISIBLE);
            GetFatherCaste(Integer.parseInt(category_code));
            autoSearchMotherCaste.setVisibility(View.VISIBLE);
            GetMotherCaste(Integer.parseInt(category_code));
            autoSearchAPPCaste_VA.setVisibility(View.VISIBLE);
            GetAppCaste_VA(Integer.parseInt(category_code));
        }
        spFatherCategory.setOnItemSelectedListener( new GetFatherCategorySelected());
        spMotherCategory.setOnItemSelectedListener( new GetMotherCategorySelected());
        spAPPCategory_VA.setOnItemSelectedListener( new GetAppCategorySelected_VA());

        tvIncome.setText(amount);

        getAppCatCode_VA = sqlLiteOpenHelper_class.GetCategoryCode(category_name);
        Log.d("Category_Code_set", ""+ getAppCatCode_VA);

        if(getAppCatCode_VA==6) {
            spAPPCategory_VA.setSelection(1);
            Log.d("Category_Code_set", "enter if");
        }else if(getAppCatCode_VA==7){
            spAPPCategory_VA.setSelection(2);
            Log.d("Category_Code_set", "enter else if");
        }else if(getAppCatCode_VA==1){
            spAPPCategory_VA.setSelection(1);
            Log.d("Category_Code_set", "enter else if");
        }
        autoSearchAPPCaste_VA.setText(caste_name);
        strSearchAppCaste_VA = caste_name;

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(New_Request_Caste_sc_st_certi_Parameters.this);
        sqlLiteOpenHelper_class.open_Reasons_Master_Tbl();

        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton1) {
                //Toast.makeText(getApplicationContext(), "choice: male", Toast.LENGTH_SHORT).show();
                option1 = getString(R.string.yes);
                if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)) {
                    option1 = getString(R.string.yes);
                    radioButton1.setChecked(true);
                    lRejection.setVisibility(View.GONE);
                    spRejectReason.setSelection(0);
                    autoSearchAPPCaste_VA.setError(null);
                }else {
                    option1=getString(R.string.no);
                    radioButton11.setChecked(true);
                    lRejection.setVisibility(View.VISIBLE);
                    autoSearchAPPCaste_VA.setError(getString(R.string.must_match_father_mother));
                }
            } else if (checkedId == R.id.radioButton11) {
                option1 = getString(R.string.no);
                lRejection.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), "choice: Female", Toast.LENGTH_SHORT).show();
            }
            Log.d("option1", ""+option1);
        });

        gpsTracker = new GPSTracker(getApplicationContext(), this);

        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Log.d("Location", latitude+""+longitude);
        } else {
            //gpsTracker.showSettingsAlert();
            Toast.makeText(getApplicationContext(), getString(R.string.switch_on_gps), Toast.LENGTH_SHORT).show();
        }

        adapter_Year = ArrayAdapter.createFromResource(this, R.array.years_array, R.layout.spinner_item_color);
        adapter_Year.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spYears.setAdapter(adapter_Year);

        spYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strYear = String.valueOf(spYears.getSelectedItem());
                Log.d("Spinner_Value", ""+strYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter_rejection_reason = ArrayAdapter.createFromResource(this, R.array.RejectionReason, R.layout.spinner_item_color);
        adapter_rejection_reason.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spRejectReason.setAdapter(adapter_rejection_reason);

        spRejectReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strRejectionReason = String.valueOf(spRejectReason.getSelectedItem());
                posRejectionReason = position;
                sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(New_Request_Caste_sc_st_certi_Parameters.this);
                sqlLiteOpenHelper_class.open_Reasons_Master_Tbl();
                reason_Code_1 = sqlLiteOpenHelper_class.Get_CertificateRejectionReason(strRejectionReason,getString(R.string.reasons_tbl_reason_name));
                Log.d("Number", ""+ reason_Code_1);
                Log.d("Item_Position", ""+ position);
                Log.d("Spinner_Value", ""+strRejectionReason);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(VA_Name);
        tvServiceName.setText(strSearchServiceName);

        btnCamera.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        });

//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                spYears.setSelection(0);
//                autoSearchFatherCaste.setError("");
//                spFatherCategory.setSelection(0);
//                autoSearchMotherCaste.setError("");
//                spMotherCategory.setSelection(0);
//                spAPPCategory_VA.setSelection(0);
//                autoSearchAPPCaste_VA.setError("");
//                tvIncome.setText("");
//                spRejectReason.setSelection(0);
//                tvRemarks.setText("");
//                imageView.setVisibility(View.GONE);
//                imageView.setImageResource(R.drawable.ic_person_24dp);
//            }
//        });

        btnSave.setOnClickListener(v -> {

            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                Log.d("Location", latitude+""+longitude);
            } else {
                //gpsTracker.showSettingsAlert();
                Toast.makeText(getApplicationContext(), getString(R.string.switch_on_gps), Toast.LENGTH_SHORT).show();
            }
            strIncome = tvIncome.getText().toString();
            strRemarks = tvRemarks.getText().toString();
            Log.d("Income_value", ""+strIncome+", strRemarks: "+strRemarks);

            if (serviceCode == 7 || serviceCode == 8){
                saveService7_and_8();
            } else if (serviceCode == 42){
                saveService42();
            }
        });

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    public void saveService42(){
        getFatherCatCode = Integer.parseInt(category_code);
        getMotherCatCode = Integer.parseInt(category_code);
        getAppCatCode_VA = Integer.parseInt(category_code);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class();
        sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();

        String father_caste_name = autoSearchFatherCaste.getText().toString();
        getFatherCasteCode = sqlLiteOpenHelper_class.GetCasteCode(father_caste_name, getFatherCatCode);
        Log.d("Caste_CodeF",""+getFatherCasteCode);

        String mother_caste_name = autoSearchMotherCaste.getText().toString();
        getMotherCasteCode = sqlLiteOpenHelper_class.GetCasteCode(mother_caste_name, getMotherCatCode);
        Log.d("Caste_CodeM",""+getMotherCasteCode);

        String caste_name = autoSearchAPPCaste_VA.getText().toString();
        getAppCasteCode_VA = sqlLiteOpenHelper_class.GetCasteCode(caste_name, getAppCatCode_VA);
        Log.d("Caste_CodeA",""+getAppCasteCode_VA);

        Log.d("Spinner_Value", ""+strYear);

        if(latitude!=0.0 && longitude!=0.0) {
            if (!strYear.equals(getString(R.string.select_spinner))) {
                if(!strSearchFatherCaste.equals(getString(R.string.select_caste_spinner))) {
                    if (getFatherCasteCode!=0) {
                        Log.d("Caste_CodeF1",""+getFatherCasteCode);
                        if (!strSearchMotherCaste.equals(getString(R.string.select_caste_spinner))) {
                            if (getMotherCasteCode != 0) {
                                Log.d("Caste_CodeM1",""+getMotherCasteCode);
                                if (!strSearchAppCaste_VA.equals(getString(R.string.select_caste_spinner))) {
                                    if (getAppCasteCode_VA != 0) {
                                        Log.d("Caste_CodeA1",""+getAppCasteCode_VA);
                                        if (!strSearchAppCaste_VA.equals(strSearchFatherCaste) && !strSearchAppCaste_VA.equals(strSearchMotherCaste)) {
                                            radioButton11.setChecked(true);
                                        }
                                        if (TextUtils.isEmpty(strIncome)) {
                                            tvIncome.setError(getString(R.string.field_canno_null));
                                            Toast.makeText(getApplicationContext(),getString(R.string.annual_income) + " " + getString(R.string.field_canno_null), Toast.LENGTH_SHORT).show();
                                        } else {
                                            income_len = strIncome.length();
                                            income_Value = Integer.parseInt(strIncome);

                                            if (income_len >= 4 && income_Value>=1000) {
                                                if (option1.equals(getString(R.string.no))) {
                                                    if (!strRejectionReason.equals(getString(R.string.reason_spinner))) {
                                                        if (TextUtils.isEmpty(strRemarks)) {
                                                            tvRemarks.setError(getString(R.string.field_canno_null));
                                                            Toast.makeText(getApplicationContext(),getString(R.string.remarks) + " " + getString(R.string.field_canno_null), Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            returnStr = StoreData_in_DB();
                                                            Log.d("Data", returnStr);
                                                        }
                                                    } else {
                                                        ((TextView) spRejectReason.getSelectedView()).setError(getString(R.string.select_reason));
                                                        Toast.makeText(getApplicationContext(), getString(R.string.select_reason), Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    if (TextUtils.isEmpty(strRemarks)) {
                                                        tvRemarks.setError(getString(R.string.field_canno_null));
                                                        Toast.makeText(getApplicationContext(),getString(R.string.remarks) + " " + getString(R.string.field_canno_null), Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        returnStr = StoreData_in_DB();
                                                        Log.d("Data", returnStr);
                                                    }
                                                }
                                            } else {
                                                tvIncome.setError(getString(R.string.incorrect_value));
                                                Toast.makeText(getApplicationContext(),getString(R.string.annual_income) + " " + getString(R.string.incorrect_value), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        autoSearchAPPCaste_VA.setError(getString(R.string.invalid_caste));
                                    }
                                } else {
                                    autoSearchAPPCaste_VA.setError(getString(R.string.select_caste));
                                    Toast.makeText(getApplicationContext(), getString(R.string.select_caste), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                autoSearchMotherCaste.setError(getString(R.string.invalid_caste));
                            }
                        } else {
                            autoSearchMotherCaste.setError(getString(R.string.select_caste));
                            Toast.makeText(getApplicationContext(), getString(R.string.select_caste), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        autoSearchFatherCaste.setError(getString(R.string.invalid_caste));
                    }
                } else {
                    autoSearchFatherCaste.setError(getString(R.string.select_caste));
                    Toast.makeText(getApplicationContext(), getString(R.string.select_caste), Toast.LENGTH_SHORT).show();
                }
            } else {
                ((TextView) spYears.getSelectedView()).setError(getString(R.string.select_no_years));
                Toast.makeText(getApplicationContext(), getString(R.string.select_no_years), Toast.LENGTH_SHORT).show();
            }
        } else {
            runOnUiThread(() -> {

                AlertDialog.Builder dialog = new AlertDialog.Builder(New_Request_Caste_sc_st_certi_Parameters.this);
                dialog.setCancelable(false);
                dialog.setTitle(getString(R.string.alert));
                dialog.setMessage(getString(R.string.cannot_get_location));
                dialog.setNegativeButton(getString(R.string.ok), (dialog1, which) -> {
                    //Action for "Cancel".
                    dialog1.cancel();
                });

                final AlertDialog alert = dialog.create();
                alert.show();
            });
            //Toast.makeText(getApplicationContext(), "Please Switch on the GPS", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveService7_and_8(){
        strFatherCategory = ((SpinnerObject) spFatherCategory.getSelectedItem()).getValue();
        Log.d("Selected_Item1", ""+strFatherCategory);
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class();
        sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
        getFatherCatCode = sqlLiteOpenHelper_class.GetCategoryCode(strFatherCategory);
        Log.d("Category_Code1", ""+ getFatherCatCode);
        if (!strFatherCategory.equals(getString(R.string.select_category))) {

            String caste_name = autoSearchFatherCaste.getText().toString();
            getFatherCasteCode = sqlLiteOpenHelper_class.GetCasteCode(caste_name, getFatherCatCode);
            Log.d("Caste_Code1",""+getFatherCasteCode);

        }

        strMotherCategory = ((SpinnerObject) spMotherCategory.getSelectedItem()).getValue();
        Log.d("Selected_Item1", ""+strMotherCategory);
        getMotherCatCode = sqlLiteOpenHelper_class.GetCategoryCode(strMotherCategory);
        Log.d("Category_Code1", ""+ getMotherCatCode);
        if (!strMotherCategory.equals(getString(R.string.select_category))) {

            String caste_name = autoSearchMotherCaste.getText().toString();
            getMotherCasteCode = sqlLiteOpenHelper_class.GetCasteCode(caste_name, getMotherCatCode);
            Log.d("Caste_Code1",""+getMotherCasteCode);

        }

        strAppCategory_VA = ((SpinnerObject) spAPPCategory_VA.getSelectedItem()).getValue();
        Log.d("Selected_Item1", ""+strAppCategory_VA);
        getAppCatCode_VA = sqlLiteOpenHelper_class.GetCategoryCode(strAppCategory_VA);
        Log.d("Category_Code1", ""+ getAppCatCode_VA);
        if (!strAppCategory_VA.equals(getString(R.string.select_category))) {

            String caste_name = autoSearchAPPCaste_VA.getText().toString();
            getAppCasteCode_VA = sqlLiteOpenHelper_class.GetCasteCode(caste_name, getAppCatCode_VA);
            Log.d("Caste_Code1",""+getAppCasteCode_VA);

        }

        Log.d("Spinner_Value", ""+strYear);

        if(latitude!=0.0 && longitude!=0.0) {
            if (!strYear.equals(getString(R.string.select_spinner))) {
                if(!strFatherCategory.equals(getString(R.string.select_category_spinner))) {
                    if(!strSearchFatherCaste.equals(getString(R.string.select_caste_spinner))) {
                        if (getFatherCasteCode!=0) {
                            if (!strMotherCategory.equals(getString(R.string.select_category_spinner))) {
                                if (!strSearchMotherCaste.equals(getString(R.string.select_caste_spinner))) {
                                    if (getMotherCasteCode != 0) {
                                        if (!strAppCategory_VA.equals(getString(R.string.select_category_spinner))) {
                                            if (!strSearchAppCaste_VA.equals(getString(R.string.select_caste_spinner))) {
                                                if (getAppCasteCode_VA != 0) {
                                                    if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)) {
                                                        if (TextUtils.isEmpty(strIncome)) {
                                                            tvIncome.setError(getString(R.string.field_canno_null));
                                                        } else {
                                                            income_len = strIncome.length();
                                                            income_Value = Integer.parseInt(strIncome);
                                                            if (income_len >= 4 && income_Value>=1000) {
                                                                if (option1.equals(getString(R.string.no))) {
                                                                    if (!strRejectionReason.equals(getString(R.string.reason_spinner))) {
                                                                        if (TextUtils.isEmpty(strRemarks)) {
                                                                            tvRemarks.setError(getString(R.string.field_canno_null));
                                                                            Toast.makeText(getApplicationContext(),getString(R.string.remarks) + " " + getString(R.string.field_canno_null), Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            returnStr = StoreData_in_DB();
                                                                            Log.d("Data", returnStr);
                                                                        }
                                                                    } else {
                                                                        ((TextView) spRejectReason.getSelectedView()).setError(getString(R.string.select_reason));
                                                                        Toast.makeText(getApplicationContext(), getString(R.string.select_reason), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    if (TextUtils.isEmpty(strRemarks)) {
                                                                        tvRemarks.setError(getString(R.string.field_canno_null));
                                                                        Toast.makeText(getApplicationContext(),getString(R.string.remarks) + " " + getString(R.string.field_canno_null), Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        returnStr = StoreData_in_DB();
                                                                        Log.d("Data", returnStr);
                                                                    }
                                                                }
                                                            } else {
                                                                tvIncome.setError(getString(R.string.incorrect_value));
                                                            }
                                                        }
                                                    } else {
                                                        radioButton11.setChecked(true);
                                                        if (TextUtils.isEmpty(strIncome)) {
                                                            tvIncome.setError(getString(R.string.field_canno_null));
                                                            Toast.makeText(getApplicationContext(),getString(R.string.annual_income) + " " + getString(R.string.field_canno_null), Toast.LENGTH_SHORT).show();
                                                        } else {

                                                            income_len = strIncome.length();
                                                            income_Value = Integer.parseInt(strIncome);
                                                            Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                                                            if (income_len >= 4 && income_Value>=1000) {
                                                                if (option1.equals(getString(R.string.no))) {
                                                                    if (!strRejectionReason.equals(getString(R.string.reason_spinner))) {
                                                                        if (TextUtils.isEmpty(strRemarks)) {
                                                                            tvRemarks.setError(getString(R.string.field_canno_null));
                                                                            Toast.makeText(getApplicationContext(),getString(R.string.remarks) + " " + getString(R.string.field_canno_null), Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            returnStr = StoreData_in_DB();
                                                                            Log.d("Data", returnStr);
                                                                        }
                                                                    } else {
                                                                        ((TextView) spRejectReason.getSelectedView()).setError(getString(R.string.select_reason));
                                                                        Toast.makeText(getApplicationContext(), getString(R.string.select_reason), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    if (TextUtils.isEmpty(strRemarks)) {
                                                                        tvRemarks.setError(getString(R.string.field_canno_null));
                                                                        Toast.makeText(getApplicationContext(),getString(R.string.remarks) + " " + getString(R.string.field_canno_null), Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        returnStr = StoreData_in_DB();
                                                                        Log.d("Data", returnStr);
                                                                    }
                                                                }
                                                            } else {
                                                                tvIncome.setError(getString(R.string.incorrect_value));
                                                                Toast.makeText(getApplicationContext(),getString(R.string.annual_income) + " " + getString(R.string.incorrect_value), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    autoSearchAPPCaste_VA.setError(getString(R.string.invalid_caste));
                                                }
                                            } else {
                                                autoSearchAPPCaste_VA.setError(getString(R.string.select_caste));
                                                Toast.makeText(getApplicationContext(), getString(R.string.select_caste), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            ((TextView) spAPPCategory_VA.getSelectedView()).setError(getString(R.string.select_category));
                                            Toast.makeText(getApplicationContext(), getString(R.string.select_category), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        autoSearchMotherCaste.setError(getString(R.string.invalid_caste));
                                    }

                                } else {
                                    autoSearchMotherCaste.setError(getString(R.string.select_caste));
                                    Toast.makeText(getApplicationContext(), getString(R.string.select_caste), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                ((TextView) spMotherCategory.getSelectedView()).setError(getString(R.string.select_category));
                                Toast.makeText(getApplicationContext(), getString(R.string.select_category), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            autoSearchFatherCaste.setError(getString(R.string.invalid_caste));
                        }
                    }else {
                        autoSearchFatherCaste.setError(getString(R.string.select_caste));
                        Toast.makeText(getApplicationContext(), getString(R.string.select_caste), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    ((TextView) spFatherCategory.getSelectedView()).setError(getString(R.string.select_category));
                    Toast.makeText(getApplicationContext(), getString(R.string.select_category), Toast.LENGTH_SHORT).show();
                }
            } else {
                ((TextView) spYears.getSelectedView()).setError(getString(R.string.select_no_years));
                Toast.makeText(getApplicationContext(), getString(R.string.select_no_years), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            runOnUiThread(() -> {

                AlertDialog.Builder dialog = new AlertDialog.Builder(New_Request_Caste_sc_st_certi_Parameters.this);
                dialog.setCancelable(false);
                dialog.setTitle(getString(R.string.alert));
                dialog.setMessage(getString(R.string.cannot_get_location) );
                dialog.setNegativeButton(getString(R.string.ok), (dialog1, which) -> {
                    //Action for "Cancel".
                    dialog1.cancel();
                });

                final AlertDialog alert = dialog.create();
                alert.show();
            });
            //Toast.makeText(getApplicationContext(), "Please Switch on the GPS", Toast.LENGTH_SHORT).show();
        }
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
            Toast.makeText(getApplicationContext(), getString(R.string.switch_kannada), Toast.LENGTH_SHORT).show();
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
        builder.setMessage(getString(R.string.keyboard_language))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
                    onBackPressed();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> buildAlert());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private  void buildAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.must_change_keyboard))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                    startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
                    onBackPressed();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.discard_changes))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    New_Request_Caste_sc_st_certi_Parameters.super.onBackPressed();
                    Log.d("on", ""+ villageCode);
                    Intent i = new Intent(New_Request_Caste_sc_st_certi_Parameters.this, New_Request_FirstScreen.class);
                    i.putExtra("applicant_Id", applicant_Id);
                    i.putExtra("district_Code", district_Code);
                    i.putExtra("taluk_Code", taluk_Code);
                    i.putExtra("hobli_Code", hobli_Code);
                    i.putExtra("districtCode", district);
                    i.putExtra("taluk", taluk);
                    i.putExtra("VA_Name", VA_Name);
                    i.putExtra("hobli", hobli);
                    i.putExtra("va_Circle_Code", va_Circle_Code);
                    i.putExtra("VA_Circle_Name", VA_Circle_Name);
                    i.putExtra("strSearchServiceName", service_name);
                    i.putExtra("strSearchVillageName", village_name);
                    i.putExtra("serviceCode", serviceCode);
                    i.putExtra("villageCode", String.valueOf(villageCode));
                    i.putExtra("habitationCode",habitationCode);
                    i.putExtra("option_Flag", option_Flag);
                    i.putExtra("town_Name", town_Name);
                    i.putExtra("town_code", town_code);
                    i.putExtra("ward_Name", ward_Name);
                    i.putExtra("ward_code", ward_code);
                    startActivity(i);
                    finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public String StoreData_in_DB(){

        if(option1.equals(getString(R.string.no))){
            option1="NO";
        }else if (option1.equals(getString(R.string.yes))){
            option1 = "YES";
        }

        String str;
            openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_Caste_sc_st_certi_Parameters.this);
            database = openHelper.getWritableDatabase();


            Cursor cursor1 = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "+ DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No+"="+applicant_Id,null);
            if(cursor1.getCount()>0) {
                if (cursor1.moveToFirst()) {
                    appImage = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_applicant_photo));
                }
            } else {
                appImage=null;
                cursor1.close();
            }


            Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id, null);
            if (cursor.getCount() > 0) {

                database.execSQL("update "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" set "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + "=1 where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "="+ applicant_Id);

                database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " set "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information+"='NO',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Num_Years_8 + "='" + strYear + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.App_Father_Category_8 + "=" + getFatherCatCode + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Father_Caste_8 + "=" + getFatherCasteCode + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.App_Mother_Category_8 + "=" + getMotherCatCode + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.APP_Mother_Caste_8 + "=" + getMotherCasteCode + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Category + "=" + getAppCatCode_VA + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Caste + "=" + getAppCasteCode_VA + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Annual_Income + "='" + strIncome + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.vLat + "=" + latitude + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.vLong + "=" + longitude + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Photo + "='" + store + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Can_Certificate_Given + "='" + option1 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Rejection + "=" + reason_Code_1 + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Remarks + "='" + strRemarks + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + "=1" + " where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id);

                Log.d("Database", "ServiceParameters Database Updated");
                Toast.makeText(getApplicationContext(), getString(R.string.updated_successfully), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(New_Request_Caste_sc_st_certi_Parameters.this, New_Request_FirstScreen.class);
                i.putExtra("applicant_Id", applicant_Id);
                i.putExtra("district_Code", district_Code);
                i.putExtra("taluk_Code", taluk_Code);
                i.putExtra("hobli_Code", hobli_Code);
                i.putExtra("districtCode", district);
                i.putExtra("taluk", taluk);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("hobli", hobli);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("strSearchServiceName", service_name);
                i.putExtra("strSearchVillageName", village_name);
                i.putExtra("serviceCode", serviceCode);
                i.putExtra("villageCode", String.valueOf(villageCode));
                i.putExtra("habitationCode",habitationCode);
                i.putExtra("option_Flag", option_Flag);
                i.putExtra("town_Name", town_Name);
                i.putExtra("town_code", town_code);
                i.putExtra("ward_Name", ward_Name);
                i.putExtra("ward_code", ward_code);
                startActivity(i);
                finish();
                truncateDatabase_Docs();
                str = "Success";
            } else {
                cursor.close();
                set_and_get_service_parameter = new Set_and_Get_Service_Parameter();
                set_and_get_service_parameter.setDistrict_Code(district_Code);
                set_and_get_service_parameter.setTaluk_Code(taluk_Code);
                set_and_get_service_parameter.setHobli_Code(hobli_Code);
                set_and_get_service_parameter.setVa_Circle_Code(va_Circle_Code);
                set_and_get_service_parameter.setVillage_Code(String.valueOf(villageCode));
                set_and_get_service_parameter.setHabitation_code(String.valueOf(habitationCode));
                set_and_get_service_parameter.setTown_Code(town_code);
                set_and_get_service_parameter.setWard_Code(ward_code);
                set_and_get_service_parameter.setService_Code(String.valueOf(serviceCode));
                set_and_get_service_parameter.setRD_No(applicant_Id);
                set_and_get_service_parameter.setEng_Certify(eng_certi);
                set_and_get_service_parameter.setGSC_First_Part(GSC_FirstPart);
                set_and_get_service_parameter.setNo_Years_8(strYear);
                set_and_get_service_parameter.setApp_Father_Category_8(String.valueOf(getFatherCatCode));
                set_and_get_service_parameter.setAPP_Father_Caste_8(String.valueOf(getFatherCasteCode));
                set_and_get_service_parameter.setApp_Mother_Category_8(String.valueOf(getMotherCatCode));
                set_and_get_service_parameter.setAPP_Mother_Caste_8(String.valueOf(getMotherCasteCode));
                set_and_get_service_parameter.setApp_Category_as_VA_8(String.valueOf(getAppCatCode_VA));
                set_and_get_service_parameter.setApplicant_Caste_as_VA_8(String.valueOf(getAppCasteCode_VA));
                set_and_get_service_parameter.setAnnual_Income(strIncome);
                set_and_get_service_parameter.setRemarks_8(strRemarks);
                set_and_get_service_parameter.setPhoto(store);
                set_and_get_service_parameter.setST_applicant_photo(appImage);
                set_and_get_service_parameter.setRbIssue_Cert(option1);
                set_and_get_service_parameter.setSpRejectionReason(String.valueOf(reason_Code_1));
                set_and_get_service_parameter.setvLat(String.valueOf(latitude));
                set_and_get_service_parameter.setvLong(String.valueOf(longitude));

                database.execSQL("update "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" set "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + "=1 where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "="+ applicant_Id);

                database.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1
                        + "(ST_district_code, ST_taluk_code, ST_hobli_code, ST_va_Circle_Code, ST_village_code, ST_habitation_code, ST_town_code, ST_ward_no, ST_facility_code, ST_GSC_No, ST_Eng_Certificate, ST_GSCFirstPart," +
                        " VA_Accepts_Applicant_information, Applicant_Category, Applicant_Caste, Annual_Income," +
                        " Num_Years_8, App_Father_Category_8, APP_Father_Caste_8, App_Mother_Category_8, APP_Mother_Caste_8, Remarks, " +
                        " Photo, ST_applicant_photo, vLat, vLong, Can_Certificate_Given, Reason_for_Rejection, DataUpdateFlag)" +
                        " values ("
                        + set_and_get_service_parameter.getDistrict_Code() + ","
                        + set_and_get_service_parameter.getTaluk_Code() + ","
                        + set_and_get_service_parameter.getHobli_Code() + ","
                        + set_and_get_service_parameter.getVa_Circle_Code() + ","
                        + set_and_get_service_parameter.getVillage_Code() + ","
                        + set_and_get_service_parameter.getHabitation_code()+","
                        + set_and_get_service_parameter.getTown_Code() + ","
                        + set_and_get_service_parameter.getWard_Code() + ","
                        + set_and_get_service_parameter.getService_Code() + ","
                        + set_and_get_service_parameter.getRD_No() + ",'"
                        + set_and_get_service_parameter.getEng_Certify() + "',"
                        + set_and_get_service_parameter.getGSC_First_Part() + ",'"
                        + "NO" + "',"
                        + set_and_get_service_parameter.getApp_Category_as_VA_8() +","
                        + set_and_get_service_parameter.getApplicant_Caste_as_VA_8() +",'"
                        + set_and_get_service_parameter.getAnnual_Income()+"','"
                        + set_and_get_service_parameter.getNo_Years_8() + "',"
                        + set_and_get_service_parameter.getApp_Father_Category_8() + ","
                        + set_and_get_service_parameter.getAPP_Father_Caste_8() + ","
                        + set_and_get_service_parameter.getApp_Mother_Category_8() + ","
                        + set_and_get_service_parameter.getAPP_Mother_Caste_8() + ",'"
                        + set_and_get_service_parameter.getRemarks_8() + "','"
                        + set_and_get_service_parameter.getPhoto()+"','"
                        + set_and_get_service_parameter.getST_applicant_photo()+"',"
                        + set_and_get_service_parameter.getvLat() + ","
                        + set_and_get_service_parameter.getvLong() + ",'"
                        + set_and_get_service_parameter.getRbIssue_Cert() + "',"
                        + set_and_get_service_parameter.getSpRejectionReason() + ", 1)");
                Log.d("Database", "ServiceParameters Database Inserted");
                Toast.makeText(getApplicationContext(), getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(New_Request_Caste_sc_st_certi_Parameters.this, New_Request_FirstScreen.class);
                i.putExtra("applicant_Id", applicant_Id);
                i.putExtra("district_Code", district_Code);
                i.putExtra("taluk_Code", taluk_Code);
                i.putExtra("hobli_Code", hobli_Code);
                i.putExtra("districtCode", district);
                i.putExtra("taluk", taluk);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("hobli", hobli);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("strSearchServiceName", service_name);
                i.putExtra("strSearchVillageName", village_name);
                i.putExtra("serviceCode", serviceCode);
                i.putExtra("villageCode", String.valueOf(villageCode));
                i.putExtra("habitationCode",habitationCode);
                i.putExtra("option_Flag", option_Flag);
                i.putExtra("town_Name", town_Name);
                i.putExtra("town_code", town_code);
                i.putExtra("ward_Name", ward_Name);
                i.putExtra("ward_code", ward_code);
                startActivity(i);
                finish();
                truncateDatabase_Docs();
                str = "success";
            }
            return str;
    }

    public void truncateDatabase_Docs(){
        //dialog1.setProgress(20);
        openHelper = new DataBaseHelperClass_btnDownload_Docs(New_Request_Caste_sc_st_certi_Parameters.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_Docs.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_Docs.TABLE_NAME);
            Log.d("Database", "Down_Docs Database Truncated");
        } else {
            cursor.close();
        }

    }

    public void GetCategory_SCST() {
        try {
            Log.d("GetCat", "Enter GetCat Function");
            String str="SCST";
            List<SpinnerObject> labels;
            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, this);
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
            labels = sqlLiteOpenHelper_class.Get_Category(str,getString(R.string.select_category_spinner));

            ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_color, labels);
            dataAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spFatherCategory.setAdapter(dataAdapter);
            spMotherCategory.setAdapter(dataAdapter);
            spAPPCategory_VA.setAdapter(dataAdapter);


        } catch (Exception e) {
            Log.d("Catch", String.valueOf(e));
            Toast.makeText(getApplicationContext(), getString(R.string.error_creating_table), Toast.LENGTH_LONG).show();
        }
    }

    public void GetCategory_Cat_1() {
        try {
            Log.d("GetCat", "Enter GetCat Function");
            String str="Category_1";
            List<SpinnerObject> labels;
            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, this);
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
            labels = sqlLiteOpenHelper_class.Get_Category(str, getString(R.string.select_category_spinner));

            ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_color, labels);
            dataAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spFatherCategory.setAdapter(dataAdapter);
            spMotherCategory.setAdapter(dataAdapter);
            spAPPCategory_VA.setAdapter(dataAdapter);


        } catch (Exception e) {
            Log.d("Catch", ""+ e);
            Toast.makeText(getApplicationContext(), getString(R.string.error_creating_table), Toast.LENGTH_LONG).show();
        }
    }

    public void GetFatherCaste(int catCode){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, this);
        sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class.GetCaste(catCode);

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchFatherCaste.setAdapter(adapter);
        autoSearchFatherCaste.setOnItemClickListener((parent, view, position, id) -> {
            strSearchFatherCaste = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchFatherCaste);
            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, New_Request_Caste_sc_st_certi_Parameters.this);
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
            getFatherCasteCode = sqlLiteOpenHelper_class.GetCasteCode(strSearchFatherCaste, catCode);
            Log.d("Selected_casteCode", ""+ getFatherCasteCode);
        });
    }

    public class GetFatherCategorySelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strFatherCategory = ((SpinnerObject) spFatherCategory.getSelectedItem()).getValue();
            Log.d("Selected Item", ""+strFatherCategory);
            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, New_Request_Caste_sc_st_certi_Parameters.this);
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
            getFatherCatCode = sqlLiteOpenHelper_class.GetCategoryCode(strFatherCategory);
            Log.d("Category_Code", String.valueOf(getFatherCatCode));
            if (!strFatherCategory.equals(getString(R.string.select_category_spinner))) {
                autoSearchFatherCaste.setVisibility(View.VISIBLE);
                GetFatherCaste(getFatherCatCode);
            }
            else {
                autoSearchFatherCaste.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void GetMotherCaste(int catCode){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, this);
        sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class.GetCaste(catCode);

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchMotherCaste.setAdapter(adapter);
        autoSearchMotherCaste.setOnItemClickListener((parent, view, position, id) -> {
            strSearchMotherCaste = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchMotherCaste);

            if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)) {
                option1 = getString(R.string.yes);
                radioButton1.setChecked(true);
                lRejection.setVisibility(View.GONE);
                spRejectReason.setSelection(0);
                autoSearchAPPCaste_VA.setError(null);
            }else {
                option1=getString(R.string.no);
                radioButton11.setChecked(true);
                lRejection.setVisibility(View.VISIBLE);
                autoSearchAPPCaste_VA.setText("");
                getAppCasteCode_VA = 0;
                autoSearchAPPCaste_VA.setError(getString(R.string.must_match_father_mother));
            }

            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, New_Request_Caste_sc_st_certi_Parameters.this);
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();

            getMotherCasteCode = sqlLiteOpenHelper_class.GetCasteCode(strSearchMotherCaste, catCode);
            Log.d("Selected_casteCode", ""+ getMotherCasteCode);
        });
    }

    public class GetMotherCategorySelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strMotherCategory = ((SpinnerObject) spMotherCategory.getSelectedItem()).getValue();
            Log.d("Selected Item", ""+strMotherCategory);
            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, New_Request_Caste_sc_st_certi_Parameters.this);
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
            getMotherCatCode = sqlLiteOpenHelper_class.GetCategoryCode(strMotherCategory);
            Log.d("Category_Code", ""+ getMotherCatCode);
            if (!strMotherCategory.equals(getString(R.string.select_category_spinner))) {
                autoSearchMotherCaste.setVisibility(View.VISIBLE);
                GetMotherCaste(getMotherCatCode);
            }
            else {
                autoSearchMotherCaste.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void GetAppCaste_VA(int catCode){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, this);
        sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class.GetCaste(catCode);

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchAPPCaste_VA.setAdapter(adapter);
        autoSearchAPPCaste_VA.setOnItemClickListener((parent, view, position, id) -> {
            strSearchAppCaste_VA = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchAppCaste_VA);
            if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)) {
                option1 = getString(R.string.yes);
                radioButton1.setChecked(true);
                lRejection.setVisibility(View.GONE);
                spRejectReason.setSelection(0);
                autoSearchAPPCaste_VA.setError(null);
            }else {
                option1=getString(R.string.no);
                radioButton11.setChecked(true);
                lRejection.setVisibility(View.VISIBLE);
                autoSearchAPPCaste_VA.setText("");
                getAppCasteCode_VA = 0;
                autoSearchAPPCaste_VA.setError(getString(R.string.must_match_father_mother));
            }
            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, New_Request_Caste_sc_st_certi_Parameters.this);
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
            getAppCasteCode_VA = sqlLiteOpenHelper_class.GetCasteCode(strSearchAppCaste_VA, catCode);
            Log.d("Selected_casteCode", ""+ getAppCasteCode_VA);
        });
    }

    public class GetAppCategorySelected_VA implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strAppCategory_VA = ((SpinnerObject) spAPPCategory_VA.getSelectedItem()).getValue();
            Log.d("Selected Item", ""+strAppCategory_VA);
            if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)){
                radioButton1.setChecked(true);
            }
            else {
                radioButton11.setChecked(true);
            }
            sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(activity, New_Request_Caste_sc_st_certi_Parameters.this);
            sqlLiteOpenHelper_class.open_Cat_Caste_Tbl();
            getAppCatCode_VA = sqlLiteOpenHelper_class.GetCategoryCode(strAppCategory_VA);
            Log.d("Category_Code", ""+ getAppCatCode_VA);
            if (!strAppCategory_VA.equals(getString(R.string.select_category_spinner))) {
                autoSearchAPPCaste_VA.setVisibility(View.VISIBLE);
                GetAppCaste_VA(getAppCatCode_VA);
            }
            else {
                autoSearchAPPCaste_VA.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @SuppressLint({"LongLogTag", "SetTextI18n"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        if (requestCode == CAMERA_REQUEST) {
            Bundle extras = data.getExtras();

            if (extras != null) {
                Bitmap yourImage = extras.getParcelable("data");
                // convert bitmap to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();//The ByteArrayOutputStream class stream creates a buffer in memory and all the data sent to the stream is stored in the buffer.
                assert yourImage != null;
                yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);//Specifies the known formats a bitmap can be compressed into 1.JPEG 2.PNG 3.WEBP
                imageInByte = stream.toByteArray();
                int len;
                len = imageInByte.length;
                Log.d("Length_byte", "" + len);
                getString = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                len = getString.length();
                Log.d("Length_str", "" + len);
                Log.d("output before conversion", Arrays.toString(imageInByte));
                store = getString;
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(yourImage);
                btnCamera.setError(null);
                Log.d("Image", store);
                Log.d("Image in bytes", "Image Captured");
            }
        }
    }
    @Override
    public void onBackPressed() {
        buildAlertMessageGoingBack();
    }
}
