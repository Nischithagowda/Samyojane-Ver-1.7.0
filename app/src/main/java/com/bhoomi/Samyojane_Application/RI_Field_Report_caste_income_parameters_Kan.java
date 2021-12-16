package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface_NIC;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nischitha on 08-07-2019.
 */

public class RI_Field_Report_caste_income_parameters_Kan  extends AppCompatActivity {

    HashMap<String, String> hashMap_Down_Docs;

    TextView tvHobli, tvTaluk, tvRI_Name, tvServiceName, tv_V_T_Name, txt_ReportNo;
    String RI_Name,district, taluk, hobli, VA_Circle_Name, VA_Name;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code, applicant_Id, applicant_name;
    String village_name, service_name;
    String villageCode, serviceCode, habitationCode, town_Name, ward_Name, town_code, ward_code, option_Flag;
    TextView txt_raiseLoc, title, RI_Recommendation, ApplicantID, ApplicantName, ApplicantCategory, ApplicantCaste, CreamyLayer, ReasonForCreamyLayer, AnnualIncome, ReservationGiven, Remarks;
    String appID, appName, appCategory, appCaste, appCreamyLayer, appReasonForCreamyLayer, appAnnualIncome, appReservationGiven, remarks;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    SqlLiteOpenHelper_Class_Kan sqlLiteOpenHelper_class_kan, sqlLiteOpenHelper_class_kan1;
    String getCategory, getCaste, getReasonCreamyLayer;
    RadioGroup radioGroup, radioGroup2, radioGroup3;
    RadioButton radioButton1, radioButton11, radioButton2, radioButton22, radioButton3, radioButton33;
    String option, option2, option3, option5, option1;
    Spinner spCategory, spReasons;
    AutoCompleteTextView autoSearchCaste;
    TableRow TableApplicantCategory, TableApplicantCaste, TableCasteReservation, TableReasonForCreamyLayer;
    EditText tvIncome, tvRemarks;
    int income_len, income_Value;
    String strReason, strCategory, strSearchCaste;
    int getCatCode, getCasteCode;
    int reason_Code_1;
    int posReason;
    ArrayAdapter<CharSequence> adapter_reason;
    Button btnSave, btnBack, btnDownDocs, btnViewDocs;
    String strIncome;
    GPSTracker gpsTracker;
    double latitude, longitude;
    ProgressDialog dialog;
    String photo;
    TextView tvVillageName, tvAppCategory, tvAppCaste, tvCreamyLayer, tvReasonForCreamyLayer, tvAnnualIncome, tvRemarksColor;
    String strRemarks;
    int FCategory, FCaste, MCategory, MCaste, Total_No_Years_10, NO_Months_10;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;
    String raisedLoc, eng_certi;

    final int min = 1111;
    final int max = 9999;
    int random;
    String report_no;
    APIInterface_NIC apiInterface_nic;
    String uName_get;
    int DesiCode;
    SharedPreferences sharedPreferences;

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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_field_report_caste_income_parameters);

        option=getString(R.string.yes);
        option2=getString(R.string.yes);
        option3=getString(R.string.yes);
        strSearchCaste=getString(R.string.select_caste_spinner);

        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvRI_Name = findViewById(R.id.VA_name);

        btnBack = findViewById(R.id.btnBack);
        txt_raiseLoc = findViewById(R.id.txt_raiseLoc);
        tvServiceName = findViewById(R.id.tvServiceName);
        title = findViewById(R.id.title);
        RI_Recommendation = findViewById(R.id.RI_Recommendation);
        tvVillageName = findViewById(R.id.tvVillageName);
        ApplicantID = findViewById(R.id.ApplicantID);
        ApplicantName = findViewById(R.id.ApplicantName);
        ApplicantCategory = findViewById(R.id.ApplicantCategory);
        ApplicantCaste = findViewById(R.id.ApplicantCaste);
        CreamyLayer = findViewById(R.id.CreamyLayer);
        ReasonForCreamyLayer = findViewById(R.id.ReasonForCreamyLayer);
        AnnualIncome = findViewById(R.id.AnnualIncome);
        ReservationGiven = findViewById(R.id.ReservationGiven);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton11 = findViewById(R.id.radioButton11);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton22 = findViewById(R.id.radioButton22);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton33 = findViewById(R.id.radioButton33);
        autoSearchCaste = findViewById(R.id.autoSearchCaste);
        spCategory = findViewById(R.id.spCategory);
        spReasons = findViewById(R.id.spReasons);
        TableApplicantCategory = findViewById(R.id.TableApplicantCategory);
        TableApplicantCaste = findViewById(R.id.TableApplicantCaste);
        TableCasteReservation = findViewById(R.id.TableCasteReservation);
        TableReasonForCreamyLayer = findViewById(R.id.TableReasonForCreamyLayer);
        Remarks = findViewById(R.id.Remarks);
        tvIncome = findViewById(R.id.tvIncome);
        tvRemarks = findViewById(R.id.tvRemarks);
        btnSave = findViewById(R.id.btnSave);
        tvAppCategory = findViewById(R.id.tvAppCategory);
        tvAppCaste = findViewById(R.id.tvAppCaste);
        tvCreamyLayer = findViewById(R.id.tvCreamyLayer);
        tvReasonForCreamyLayer = findViewById(R.id.tvReasonForCreamyLayer);
        tvAnnualIncome = findViewById(R.id.tvAnnualIncome);
        tv_V_T_Name = findViewById(R.id.tv_V_T_Name);
        tvRemarksColor = findViewById(R.id.tvRemarksColor);
        btnDownDocs = findViewById(R.id.btnDownDocs);
        btnViewDocs = findViewById(R.id.btnViewDocs);
        txt_ReportNo = findViewById(R.id.txt_ReportNo);

        btnViewDocs.setVisibility(View.GONE);

        autoSearchCaste.setVisibility(View.GONE);
        spCategory.setVisibility(View.GONE);
        spReasons.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        ApplicantCategory.setVisibility(View.VISIBLE);
        ApplicantCaste.setVisibility(View.VISIBLE);
        CreamyLayer.setVisibility(View.VISIBLE);
        ReasonForCreamyLayer.setVisibility(View.VISIBLE);
        ReservationGiven.setVisibility(View.VISIBLE);
        AnnualIncome.setVisibility(View.VISIBLE);
        TableCasteReservation.setVisibility(View.VISIBLE);
        TableApplicantCaste.setVisibility(View.VISIBLE);
        TableReasonForCreamyLayer.setVisibility(View.GONE);
        Remarks.setVisibility(View.VISIBLE);
        tvIncome.setVisibility(View.GONE);
        tvRemarks.setVisibility(View.GONE);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        String formattedDate = df.format(c);

        Log.d("formattedDate", "" + formattedDate);

        random = new Random().nextInt((max - min) + 1) + min;
        Log.d("Random_Num", String.valueOf(+random));

        report_no = formattedDate + "\\" + random;
        Log.d("report_no", "" + report_no);

        Intent i = getIntent();
        district_Code = i.getStringExtra("district_Code");
        district = i.getStringExtra("districtCode");
        taluk_Code = i.getStringExtra("taluk_Code");
        taluk = i.getStringExtra("taluk");
        hobli_Code = i.getStringExtra("hobli_Code");
        hobli = i.getStringExtra("hobli");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        applicant_Id = i.getStringExtra("applicant_Id");
        applicant_name = i.getStringExtra("applicant_name");
        RI_Name = i.getStringExtra("RI_Name");
        VA_Name = i.getStringExtra("VA_Name");
        village_name = i.getStringExtra("strSearchVillageName");
        service_name = i.getStringExtra("strSearchServiceName");
        villageCode = i.getStringExtra("villageCode");
        habitationCode = i.getStringExtra("habitationCode");
        serviceCode = i.getStringExtra("serviceCode");
        town_code = i.getStringExtra("town_code");
        town_Name = i.getStringExtra("town_Name");
        ward_code = i.getStringExtra("ward_code");
        ward_Name = i.getStringExtra("ward_Name");
        option_Flag = i.getStringExtra("option_Flag");
        eng_certi = i.getStringExtra("eng_certi");
        report_no = i.getStringExtra("report_No");

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        DesiCode = sharedPreferences.getInt(Constants.DesiCode_RI, 19);
        uName_get = sharedPreferences.getString(Constants.uName_get, "");

        if(report_no == null){
            Date c1 = Calendar.getInstance().getTime();

            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
            String formattedDate1 = df1.format(c1);

            Log.d("formattedDate", "" + formattedDate1);

            random = new Random().nextInt((max - min) + 1) + min;
            Log.d("Random_Num", String.valueOf(+random));

            report_no = formattedDate1 + "\\" + random;
            Log.d("report_no", "" + report_no);
        }

        title.setText(getString(R.string.shri_smt)+" "+VA_Name+", "+getString(R.string.village_accountant));
        title.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        RI_Recommendation.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        hashMap_Down_Docs  = new HashMap<>();

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

        Log.d("RI_caste_income", ""+district_Code);
        Log.d("RI_caste_income", ""+taluk_Code);
        Log.d("RI_caste_income",hobli_Code+" RI_Name :"+RI_Name+" VA_Name :"+VA_Name
                +" VillageName :"+village_name+"ServiceName:"+service_name);
        Log.d("RI_caste_income", ""+applicant_Id);
        Log.d("RI_caste_income", ""+habitationCode);
        Log.d( "town_code: ",""+town_code);
        Log.d("town_Name: ",""+town_Name);
        Log.d( "ward_code: ",""+ward_code);
        Log.d( "ward_Name: ",""+ward_Name);
        Log.d("option_Flag: ",""+ option_Flag);

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvRI_Name.setText(RI_Name);
        tvServiceName.setText(service_name);
        txt_ReportNo.setText(report_no);

        if(!Objects.equals(villageCode, "99999")){
            Log.d("Data","Rural");
            tv_V_T_Name.setText(getString(R.string.village_name)+" : ");
            tvVillageName.setText(village_name);
        }else if(!Objects.equals(town_code, "9999")){
            Log.d("Data","Urban");
            tv_V_T_Name.setText(getString(R.string.ward_name_num)+" : ");
            tvVillageName.setText(ward_Name);
        }

        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(this);
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME+" where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo+"='"+applicant_Id+"'", null);
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                eng_certi = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_Eng_Certificate));
                raisedLoc = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Raised_Location));
                appID = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo));
                appName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name));
                appCategory = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ReservationCategory));
                appCaste = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Caste));
                appCreamyLayer = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Belongs_Creamy_Layer_6));
                appReasonForCreamyLayer = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reason_for_Creamy_Layer_6));
                appAnnualIncome = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.AnnualIncome));
                appReservationGiven = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Can_Certificate_Given));
                remarks = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Remarks));
            }
        } else {
            cursor.close();
        }

        Log.d("Eng_Certi", eng_certi);
        Log.d("Raised_Location: ",""+raisedLoc);

        if(Objects.equals(raisedLoc, "N") || Objects.equals(raisedLoc, "S")){
            txt_raiseLoc.setText(getString(R.string.nadakacheri_raised));
        }else if(Objects.equals(raisedLoc, "P") || Objects.equals(raisedLoc, "B") || Objects.equals(raisedLoc, "K") || Objects.equals(raisedLoc, "G") || Objects.equals(raisedLoc, "I")){
            txt_raiseLoc.setText(getString(R.string.online));
        }else if(Objects.equals(raisedLoc, "E")){
            txt_raiseLoc.setText(getString(R.string.seva_sindhu));
        }else if(Objects.equals(raisedLoc, "W")){
            txt_raiseLoc.setText(getString(R.string.wallet));
        }else {
            txt_raiseLoc.setText(getString(R.string.not_specified));
        }

//        getCatCode = Integer.parseInt(appCategory);
//        getCasteCode = Integer.parseInt(appCaste);
        Log.d("dbValues", "App_ID "+appID);
        Log.d("dbValues", "appName "+appName);
        Log.d("dbValues", "appCategory "+appCategory);
        Log.d("dbValues", "appCaste "+appCaste);
        Log.d("dbValues", "appCreamyLayer "+appCreamyLayer);
        Log.d("dbValues", "appReasonForCreamyLayer "+appReasonForCreamyLayer);
        Log.d("dbValues", "appAnnualIncome "+appAnnualIncome);
        Log.d("dbValues", "appReservationGiven "+appReservationGiven);
        Log.d("dbValues", "remarks "+remarks);

        gpsTracker = new GPSTracker(getApplicationContext(), this);

        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            Log.d("Location", ""+latitude+""+longitude);
        } else {
            //gpsTracker.showSettingsAlert();
            Toast.makeText(getApplicationContext(), getString(R.string.switch_on_gps), Toast.LENGTH_SHORT).show();
        }

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setIndeterminate(true);
        dialog.setProgress(1);

        sqlLiteOpenHelper_class_kan1 = new SqlLiteOpenHelper_Class_Kan(this);
        sqlLiteOpenHelper_class_kan1.open_Reasons_Master_Tbl();
        getReasonCreamyLayer = sqlLiteOpenHelper_class_kan1.GetCreamyLayerReason_BY_Code(Integer.parseInt(appReasonForCreamyLayer), getString(R.string.reasons_tbl_reason_name));

        if(serviceCode.equals("9")){
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getCategory = sqlLiteOpenHelper_class_kan.GetCategory_BY_Code(Integer.parseInt(appCategory));
            getCaste = sqlLiteOpenHelper_class_kan.GetCaste_OBC_BY_Code(Integer.parseInt(appCaste));
        } else {
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getCategory = sqlLiteOpenHelper_class_kan.GetCategory_BY_Code(Integer.parseInt(appCategory));
            getCaste = sqlLiteOpenHelper_class_kan.GetCaste_BY_Code(Integer.parseInt(appCategory), Integer.parseInt(appCaste));
        }

        if(appCreamyLayer.equals("NO")){
            TableReasonForCreamyLayer.setVisibility(View.VISIBLE);
            ReasonForCreamyLayer.setVisibility(View.VISIBLE);
        }
        else{
            TableReasonForCreamyLayer.setVisibility(View.GONE);
            ReasonForCreamyLayer.setVisibility(View.GONE);
        }

        ApplicantID.setText(applicant_Id);
        ApplicantName.setText(appName);
        ApplicantCategory.setText(getCategory);
        ApplicantCaste.setText(getCaste);
        CreamyLayer.setText(appCreamyLayer);
        ReasonForCreamyLayer.setText(getReasonCreamyLayer);
        AnnualIncome.setText(appAnnualIncome);
        ReservationGiven.setText(appReservationGiven);
        Remarks.setText(remarks);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton1) {
                option = getString(R.string.yes);
                TableReasonForCreamyLayer.setVisibility(View.GONE);
                spReasons.setVisibility(View.GONE);
                spReasons.setSelection(0);
            } else if (checkedId == R.id.radioButton11) {
                option = getString(R.string.no);
                TableReasonForCreamyLayer.setVisibility(View.VISIBLE);
                spReasons.setVisibility(View.VISIBLE);
            }
        });

        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton3) {
                option2 = getString(R.string.yes);
                autoSearchCaste.setVisibility(View.GONE);
                spCategory.setVisibility(View.GONE);
                spReasons.setVisibility(View.GONE);
                Remarks.setVisibility(View.VISIBLE);
                tvIncome.setVisibility(View.GONE);
                tvRemarks.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
                TableApplicantCategory.setVisibility(View.VISIBLE);
                TableApplicantCaste.setVisibility(View.VISIBLE);
                ApplicantCategory.setVisibility(View.VISIBLE);
                ApplicantCaste.setVisibility(View.VISIBLE);
                CreamyLayer.setVisibility(View.VISIBLE);
                ReservationGiven.setVisibility(View.VISIBLE);
                AnnualIncome.setVisibility(View.VISIBLE);
                TableCasteReservation.setVisibility(View.VISIBLE);
                if(appCreamyLayer.equals("NO")){
                    TableReasonForCreamyLayer.setVisibility(View.VISIBLE);
                    ReasonForCreamyLayer.setVisibility(View.VISIBLE);
                }
                else{
                    TableReasonForCreamyLayer.setVisibility(View.GONE);
                    ReasonForCreamyLayer.setVisibility(View.GONE);
                }

                if (serviceCode.equals("11") || serviceCode.equals("34") || serviceCode.equals("37")){
                    TableApplicantCategory.setVisibility(View.GONE);
                    TableApplicantCaste.setVisibility(View.GONE);
                }
                tvAppCategory.setTextColor(Color.parseColor("#000000"));
                tvAppCaste.setTextColor(Color.parseColor("#000000"));
                tvCreamyLayer.setTextColor(Color.parseColor("#000000"));
                tvReasonForCreamyLayer.setTextColor(Color.parseColor("#000000"));
                tvAnnualIncome.setTextColor(Color.parseColor("#000000"));
                tvRemarksColor.setTextColor(Color.parseColor("#000000"));
            } else if (checkedId == R.id.radioButton33) {
                option2 = getString(R.string.no);
                autoSearchCaste.setVisibility(View.VISIBLE);
                spCategory.setVisibility(View.VISIBLE);
                spReasons.setVisibility(View.VISIBLE);
                Remarks.setVisibility(View.GONE);
                tvIncome.setVisibility(View.VISIBLE);
                tvRemarks.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                TableCasteReservation.setVisibility(View.GONE);
                TableApplicantCaste.setVisibility(View.GONE);
                ApplicantCategory.setVisibility(View.GONE);
                ApplicantCaste.setVisibility(View.GONE);
                CreamyLayer.setVisibility(View.GONE);
                ReservationGiven.setVisibility(View.GONE);
                AnnualIncome.setVisibility(View.GONE);
                TableReasonForCreamyLayer.setVisibility(View.GONE);
                ReasonForCreamyLayer.setVisibility(View.GONE);
                if(option.equals(getString(R.string.yes))){
                    TableReasonForCreamyLayer.setVisibility(View.GONE);
                    spReasons.setVisibility(View.GONE);
                }
                else{
                    TableReasonForCreamyLayer.setVisibility(View.VISIBLE);
                    spReasons.setVisibility(View.VISIBLE);
                }
                if (serviceCode.equals("43")){
                    TableApplicantCategory.setVisibility(View.GONE);
                    TableApplicantCaste.setVisibility(View.VISIBLE);
                } else if (serviceCode.equals("11") || serviceCode.equals("34") || serviceCode.equals("37")){
                    TableApplicantCategory.setVisibility(View.GONE);
                    TableApplicantCaste.setVisibility(View.GONE);
                }

                tvAppCategory.setTextColor(Color.parseColor("#0000ff"));
                tvAppCaste.setTextColor(Color.parseColor("#0000ff"));
                tvCreamyLayer.setTextColor(Color.parseColor("#0000ff"));
                tvReasonForCreamyLayer.setTextColor(Color.parseColor("#0000ff"));
                tvAnnualIncome.setTextColor(Color.parseColor("#0000ff"));
                tvRemarksColor.setTextColor(Color.parseColor("#0000ff"));
            }
        });

        radioGroup3.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton2) {
                option3 = getString(R.string.yes);
            } else if (checkedId == R.id.radioButton22) {
                option3 = getString(R.string.no);
            }
        });

        autoSearchCaste.setOnTouchListener((v, event) -> {
            autoSearchCaste.showDropDown();
            autoSearchCaste.setError(null);
            if(Objects.equals(eng_certi, "K")){
                check_Kannada_Key_lang();
            }
            else if(Objects.equals(eng_certi, "E")) {
                check_English_Key_lang();
            }
            return false;
        });
        switch (serviceCode) {
            case "6":
                GetCategory();
                spCategory.setOnItemSelectedListener(new GetCategorySelected());
                break;
            case "9":
                GetCategory_OBC();
                spCategory.setOnItemSelectedListener(new GetCategory_OBC_Selected());
                break;
            case "43":
                GetCaste_EWS();
                break;
            case "11":
            case "34":
            case "37": {
                TableApplicantCategory.setVisibility(View.GONE);
                TableApplicantCaste.setVisibility(View.GONE);
                break;
            }
        }
        spCategory.setOnTouchListener((v, event) -> {
            autoSearchCaste.setText("");
            autoSearchCaste.setError(null);
            return false;
        });

        adapter_reason = ArrayAdapter.createFromResource(this, R.array.Reason_array, R.layout.spinner_item_color);
        adapter_reason.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spReasons.setAdapter(adapter_reason);

        spReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strReason = String.valueOf(spReasons.getSelectedItem());
                posReason = position;
                sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(RI_Field_Report_caste_income_parameters_Kan.this);
                sqlLiteOpenHelper_class_kan.open_Reasons_Master_Tbl();
                reason_Code_1 = sqlLiteOpenHelper_class_kan.Get_CreamyLayerReasons(strReason, getString(R.string.reasons_tbl_reason_name));
                Log.d("Number", ""+ reason_Code_1);
                Log.d("Item_Position", ""+ position);
                Log.d("Spinner_Value", ""+strReason);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDownDocs.setOnClickListener(v -> {
            if (isNetworkAvailable()) {

                dialog.show();
                //dialog1.setProgress(0);

                Log.d("Docs", "Applicant_ID:"+applicant_Id);
                openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_caste_income_parameters_Kan.this);
                database = openHelper.getWritableDatabase();

                String username = uName_get.substring(0, 3);//First three characters of username
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
                String day_num = dateFormat.format(date);//Current Day
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yy", Locale.ENGLISH);
                String year_num = dateFormat1.format(date);//last two digits of the year
                String app_name = "Samyojane";

                String fieldVerify_api_flag2 = username + day_num + app_name + year_num;

                GetDocRequestClass getDocRequestClass = new GetDocRequestClass();
                getDocRequestClass.setFlag1(getString(R.string.fieldVerify_api_flag1));
                getDocRequestClass.setFlag2(fieldVerify_api_flag2);
                getDocRequestClass.setLoginId(uName_get);
                getDocRequestClass.setDesignationCode(DesiCode);
                getDocRequestClass.setGscNoList(applicant_Id);
                GetDocsFromServer(getDocRequestClass);
            }
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
            }
        });

        btnViewDocs.setOnClickListener(v -> {
            Intent i12 = new Intent(RI_Field_Report_caste_income_parameters_Kan.this, View_Docs.class);
            i12.putExtra("district_Code", district_Code);
            i12.putExtra("districtCode", district);
            i12.putExtra("taluk_Code", taluk_Code);
            i12.putExtra("taluk", taluk);
            i12.putExtra("hobli_Code", hobli_Code);
            i12.putExtra("hobli", hobli);
            i12.putExtra("va_Circle_Code", va_Circle_Code);
            i12.putExtra("VA_Circle_Name", VA_Circle_Name);
            i12.putExtra("VA_Name", VA_Name);
            i12.putExtra("strSearchServiceName", service_name);
            i12.putExtra("villageCode", villageCode);
            i12.putExtra("habitationCode", habitationCode);
            i12.putExtra("strSearchVillageName", village_name);
            i12.putExtra("applicant_name", applicant_name);
            i12.putExtra("applicant_Id", applicant_Id);
            i12.putExtra("report_No", report_no);
            startActivity(i12);
        });


        btnSave.setOnClickListener(v -> {

            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                Log.d("Location", latitude+""+longitude);
            } else {
                //gpsTracker.showSettingsAlert();
                Toast.makeText(getApplicationContext(), getString(R.string.switch_on_gps), Toast.LENGTH_SHORT).show();
            }

            if(latitude!=0.0 && longitude!=0.0) {

                if (option2.equals(getString(R.string.no))){

                    strIncome = tvIncome.getText().toString();
                    strRemarks = tvRemarks.getText().toString();
                    Log.d("Income value", ""+strRemarks);

                    if(Objects.equals(serviceCode, "6")){

                        strCategory = ((SpinnerObject) spCategory.getSelectedItem()).getValue();
                        Log.d("Selected_Item1", ""+strCategory);
                        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                        getCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strCategory);
                        Log.d("Category_Code1", ""+ getCatCode);
                        if (!strCategory.equals(getString(R.string.select_category_spinner))) {

                            String caste_name = autoSearchCaste.getText().toString();
                            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                            getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(caste_name, getCatCode);
                            Log.d("Caste_Code1",""+getCasteCode);

                        }

                        saveService_6_and_9();

                    }else if(Objects.equals(serviceCode, "9")) {

                        strCategory = ((SpinnerObject) spCategory.getSelectedItem()).getValue();
                        Log.d("Selected Item", ""+strCategory);
                        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                        getCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strCategory);
                        Log.d("Category_Code1", ""+ getCatCode);
                        if (!strCategory.equals(getString(R.string.select_category_spinner))) {

                            String caste_name = autoSearchCaste.getText().toString();
                            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                            getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode_OBC(caste_name);
                            Log.d("Caste_Code1",""+getCasteCode);

                        }
                        Log.d("Caste_Code1",""+getCasteCode);

                        saveService_6_and_9();

                    } else if (serviceCode.equals("43")){
                        String caste_name = autoSearchCaste.getText().toString();
                        if (!caste_name.equals(getString(R.string.select_caste_spinner))) {
                            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                            getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(caste_name, getCatCode);
                            Log.d("Caste_Code1", "" + getCasteCode);

                        }

                        saveService_43();
                    } else if (serviceCode.equals("11") || serviceCode.equals("34") || serviceCode.equals("37")){
                        saveService_11_34_and_37();
                    }
                }
                else {
                    StoreData_in_DB_When_Correct();
                }
            }
            else {
                runOnUiThread(() -> {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(RI_Field_Report_caste_income_parameters_Kan.this);
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
        });

        btnBack.setOnClickListener(v -> onBackPressed());

    }

    public void saveService_11_34_and_37(){
        if (option.equals(getString(R.string.no))) {
            if (!strReason.equals(getString(R.string.reason_spinner))) {
                if (TextUtils.isEmpty(strIncome)) {
                    tvIncome.setError(getString(R.string.field_canno_null));
                } else {

                    income_len = strIncome.length();
                    income_Value = Integer.parseInt(strIncome);
                    Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                    if (income_len >= 4 && income_Value>=1000) {
                        if (TextUtils.isEmpty(strRemarks)) {
                            tvRemarks.setError(getString(R.string.field_canno_null));
                        } else {
                            StoreData_in_DB_When_Wrong();
                        }
                    } else {
                        tvIncome.setError(getString(R.string.incorrect_value));
                    }
                }
            } else {
                ((TextView) spReasons.getSelectedView()).setError(getString(R.string.select_reason));
                Toast.makeText(getApplicationContext(), getString(R.string.select_reason), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (TextUtils.isEmpty(strIncome)) {
                tvIncome.setError(getString(R.string.field_canno_null));
            } else {

                income_len = strIncome.length();
                income_Value = Integer.parseInt(strIncome);
                Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                if (income_len >= 4 && income_Value>=1000) {
                    if (TextUtils.isEmpty(strRemarks)) {
                        tvRemarks.setError(getString(R.string.field_canno_null));
                    } else {
                        StoreData_in_DB_When_Wrong();
                    }
                } else {
                    tvIncome.setError(getString(R.string.incorrect_value));
                }
            }
        }
    }
    public void saveService_43(){
        if (!strSearchCaste.equals(getString(R.string.select_caste_spinner))) {
            if (getCasteCode!=0) {
                if (option.equals(getString(R.string.no))) {
                    if (!strReason.equals(getString(R.string.reason_spinner))) {
                        if (TextUtils.isEmpty(strIncome)) {
                            tvIncome.setError(getString(R.string.field_canno_null));
                        } else {

                            income_len = strIncome.length();
                            income_Value = Integer.parseInt(strIncome);
                            Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                            if (income_len >= 4 && income_Value>=1000) {
                                if (TextUtils.isEmpty(strRemarks)) {
                                    tvRemarks.setError(getString(R.string.field_canno_null));
                                } else {
                                    StoreData_in_DB_When_Wrong();
                                }
                            } else {
                                tvIncome.setError(getString(R.string.incorrect_value));
                            }
                        }
                    } else {
                        ((TextView) spReasons.getSelectedView()).setError(getString(R.string.select_reason));
                        Toast.makeText(getApplicationContext(), getString(R.string.select_reason), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (TextUtils.isEmpty(strIncome)) {
                        tvIncome.setError(getString(R.string.field_canno_null));
                    } else {

                        income_len = strIncome.length();
                        income_Value = Integer.parseInt(strIncome);
                        Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                        if (income_len >= 4 && income_Value>=1000) {
                            if (TextUtils.isEmpty(strRemarks)) {
                                tvRemarks.setError(getString(R.string.field_canno_null));
                            } else {
                                StoreData_in_DB_When_Wrong();
                            }
                        } else {
                            tvIncome.setError(getString(R.string.incorrect_value));
                        }
                    }
                }
            }else {
                autoSearchCaste.setError(getString(R.string.invalid_caste));
            }
        } else {
            autoSearchCaste.setError(getString(R.string.select_caste));
        }
    }

    public void saveService_6_and_9(){
        if (!strCategory.equals(getString(R.string.select_category_spinner))) {
            if (!strSearchCaste.equals(getString(R.string.select_caste_spinner))) {
                if (getCasteCode!=0) {
                    if (option.equals(getString(R.string.no))) {
                        if (!strReason.equals(getString(R.string.reason_spinner))) {
                            if (TextUtils.isEmpty(strIncome)) {
                                tvIncome.setError(getString(R.string.field_canno_null));
                            } else {

                                income_len = strIncome.length();
                                income_Value = Integer.parseInt(strIncome);
                                Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                                if (income_len >= 4 && income_Value>=1000) {
                                    if (TextUtils.isEmpty(strRemarks)) {
                                        tvRemarks.setError(getString(R.string.field_canno_null));
                                    } else {
                                        StoreData_in_DB_When_Wrong();
                                    }
                                } else {
                                    tvIncome.setError(getString(R.string.incorrect_value));
                                }
                            }
                        } else {
                            ((TextView) spReasons.getSelectedView()).setError(getString(R.string.select_reason));
                            Toast.makeText(getApplicationContext(), getString(R.string.select_reason), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (TextUtils.isEmpty(strIncome)) {
                            tvIncome.setError(getString(R.string.field_canno_null));
                        } else {

                            income_len = strIncome.length();
                            income_Value = Integer.parseInt(strIncome);
                            Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                            if (income_len >= 4 && income_Value>=1000) {
                                if (TextUtils.isEmpty(strRemarks)) {
                                    tvRemarks.setError(getString(R.string.field_canno_null));
                                } else {
                                    StoreData_in_DB_When_Wrong();
                                }
                            } else {
                                tvIncome.setError(getString(R.string.incorrect_value));
                            }
                        }
                    }
                }else {
                    autoSearchCaste.setError(getString(R.string.invalid_caste));
                }
            } else {
                autoSearchCaste.setError(getString(R.string.select_caste));
            }
        } else {
            ((TextView) spCategory.getSelectedView()).setError(getString(R.string.select_category));
            Toast.makeText(getApplicationContext(), getString(R.string.select_category), Toast.LENGTH_SHORT).show();
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

    public String StoreData_in_DB_When_Correct(){

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        String currDate = dateFormat.format(date);

        if(option.equals(getString(R.string.no))){
            option="N";
        }else if (option.equals(getString(R.string.yes))){
            option = "Y";
        }

        if(option2.equals(getString(R.string.no))){
            option2="Y";
        }else if (option2.equals(getString(R.string.yes))){
            option2 = "N";
        }

        if(option3.equals(getString(R.string.no))){
            option3="N";
        }else if (option3.equals(getString(R.string.yes))){
            option3 = "Y";
        }

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report_caste_income_parameters_Kan.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME + " where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo + "='"+applicant_Id+"'", null);
        if(cursor.getCount()>0){
            try {
                if (cursor.moveToNext()) {
                    getCatCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ReservationCategory));
                    getCasteCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Caste));
                    option = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Belongs_Creamy_Layer_6));
                    reason_Code_1 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reason_for_Creamy_Layer_6));
                    strIncome = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.AnnualIncome));
                    photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_applicant_photo));
                    FCategory = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Father_Category_8));
                    FCaste = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Father_Caste_8));
                    MCategory = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Mother_Category_8));
                    MCaste = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Mother_Caste_8));
                    strRemarks = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Remarks));
                    Total_No_Years_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Years_Applied));
                    NO_Months_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Mths_Applied));
                    option5 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reside_At_Stated_Address_10));
                    option1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Place_Match_With_RationCard_10));
                    Log.d("getData", "" + FCategory + " " + FCaste + " " + MCategory + " " + MCaste + " " + strRemarks + " " + " " + Total_No_Years_10 + " " + NO_Months_10 + " " + option5 + " " + option1);
                    Log.d("Data_Fetched", "StoreData_in_DB_When_Correct");
                }
                database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " set "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Applicant_Category + "='" + getCatCode + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Applicant_Caste + "='" + getCasteCode + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Belongs_Creamy_Layer_6 + "='" + option + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Reason_for_Creamy_Layer_6 + "='" + reason_Code_1 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_App_Father_Category_8 + "=" + FCategory + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_APP_Father_Caste_8 + "=" + FCaste + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_App_Mother_Category_8 + "=" + MCategory + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_APP_Mother_Caste_8 + "=" + MCaste + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Total_No_Years_10 + "=" + Total_No_Years_10 + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_NO_Months_10 + "=" + NO_Months_10 + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Reside_At_Stated_Address_10 + "='" + option5 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Place_Match_With_RationCard_10 + "='" + option1 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Income + "='" + strIncome + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Remarks + "='" + strRemarks + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_vLat + "=" + latitude + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_vLong + "=" + longitude + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Can_Certificate_Given + "='" + option3 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_DifferFromAppinformation + "='" + option2 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Report_No + "='" + report_no + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_ReportDate + "='" + currDate + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_DataUpdateFlag + "=1"
                        + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_GSCNo + "='" + applicant_Id + "'");

                Intent i = new Intent(RI_Field_Report_caste_income_parameters_Kan.this, RI_Field_Report_FirstScreen.class);
                i.putExtra("applicant_Id", applicant_Id);
                i.putExtra("district_Code", district_Code);
                i.putExtra("taluk_Code", taluk_Code);
                i.putExtra("hobli_Code", hobli_Code);
                i.putExtra("districtCode", district);
                i.putExtra("taluk", taluk);
                i.putExtra("RI_Name", RI_Name);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("hobli", hobli);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("strSearchServiceName", service_name);
                i.putExtra("strSearchVillageName", village_name);
                i.putExtra("serviceCode", serviceCode);
                i.putExtra("villageCode", villageCode);
                i.putExtra("habitationCode", habitationCode);
                i.putExtra("option_Flag", option_Flag);
                i.putExtra("town_Name", town_Name);
                i.putExtra("town_code", town_code);
                i.putExtra("ward_Name", ward_Name);
                i.putExtra("ward_code", ward_code);
                startActivity(i);
                finish();

                Log.d("Database", "ServiceParameters Database Updated");
                Toast.makeText(getApplicationContext(), getString(R.string.updated_successfully), Toast.LENGTH_SHORT).show();
                Log.d("Data", "StoreData_in_DB_When_Correct : Update_success");
                return "Update_success";
            } catch (Exception e){
                e.printStackTrace();
                return "Failed";
            }
        }
        else {
            cursor.close();
            Log.d("Data", "StoreData_in_DB_When_Correct : Update_Failed");
            return "Update_Failed";
        }
    }
    public String StoreData_in_DB_When_Wrong(){

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        String currDate = dateFormat.format(date);

        if(option.equals(getString(R.string.no))){
            option="N";
        }else if (option.equals(getString(R.string.yes))){
            option = "Y";
        }

        if(option2.equals(getString(R.string.no))){
            option2="Y";
        }else if (option2.equals(getString(R.string.yes))){
            option2 = "N";
        }

        if(option3.equals(getString(R.string.no))){
            option3="N";
        }else if (option3.equals(getString(R.string.yes))){
            option3 = "Y";
        }

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report_caste_income_parameters_Kan.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME + " where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GSCNo + "='"+applicant_Id+"'", null);
        if(cursor.getCount()>0){
            try {
                if (cursor.moveToNext()) {
                    FCategory = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Father_Category_8));
                    FCaste = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Father_Caste_8));
                    MCategory = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Mother_Category_8));
                    MCaste = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Mother_Caste_8));
                    Total_No_Years_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Years_Applied));
                    NO_Months_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.GST_No_Mths_Applied));
                    option5 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reside_At_Stated_Address_10));
                    option1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Place_Match_With_RationCard_10));
                    photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_applicant_photo));
                    Log.d("getData", "" + FCategory + " " + FCaste + " " + MCategory + " " + MCaste + " " + option5 + " " + option1);
                }

                database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " set "
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Applicant_Category + "=" + getCatCode + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Applicant_Caste + "=" + getCasteCode + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Belongs_Creamy_Layer_6 + "='" + option + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Reason_for_Creamy_Layer_6 + "='" + reason_Code_1 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_App_Father_Category_8 + "=" + FCategory + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_APP_Father_Caste_8 + "=" + FCaste + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_App_Mother_Category_8 + "=" + MCategory + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_APP_Mother_Caste_8 + "=" + MCaste + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Total_No_Years_10 + "=" + Total_No_Years_10 + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_NO_Months_10 + "=" + NO_Months_10 + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Reside_At_Stated_Address_10 + "='" + option5 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Place_Match_With_RationCard_10 + "='" + option1 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Income + "='" + strIncome + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Remarks + "='" + strRemarks + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_vLat + "=" + latitude + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_vLong + "=" + longitude + ","
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Can_Certificate_Given + "='" + option3 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_DifferFromAppinformation + "='" + option2 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_Report_No + "='" + report_no + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_ReportDate + "='" + currDate + "',"
                        + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_DataUpdateFlag + "=1"
                        + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.UPD_GSCNo + "='" + applicant_Id + "'");

                Intent i = new Intent(RI_Field_Report_caste_income_parameters_Kan.this, RI_Field_Report_FirstScreen.class);
                i.putExtra("applicant_Id", applicant_Id);
                i.putExtra("district_Code", district_Code);
                i.putExtra("taluk_Code", taluk_Code);
                i.putExtra("hobli_Code", hobli_Code);
                i.putExtra("districtCode", district);
                i.putExtra("taluk", taluk);
                i.putExtra("RI_Name", RI_Name);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("hobli", hobli);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("strSearchServiceName", service_name);
                i.putExtra("strSearchVillageName", village_name);
                i.putExtra("serviceCode", serviceCode);
                i.putExtra("villageCode", villageCode);
                i.putExtra("habitationCode", habitationCode);
                i.putExtra("option_Flag", option_Flag);
                i.putExtra("town_Name", town_Name);
                i.putExtra("town_code", town_code);
                i.putExtra("ward_Name", ward_Name);
                i.putExtra("ward_code", ward_code);
                startActivity(i);
                finish();

                Log.d("Database", "ServiceParameters Database Updated");
                Toast.makeText(getApplicationContext(), getString(R.string.updated_successfully), Toast.LENGTH_SHORT).show();
                Log.d("Data", "StoreData_in_DB_When_Wrong : Update_success");
                return "Update_success";
            } catch (Exception e){
                e.printStackTrace();
                return "Failed";
            }
        }
        else {
            cursor.close();
            Log.d("Data", "StoreData_in_DB_When_Wrong : Update_Failed");
            return "Update_Failed";
        }
    }


    public void GetDocsFromServer(GetDocRequestClass getDocRequestClass){
        apiInterface_nic = APIClient.getClient(getString(R.string.MobAPI_New_NIC)).create(APIInterface_NIC.class);

        Call<JsonObject> call = apiInterface_nic.GetDocs(getDocRequestClass);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject1 = response.body();
                    Log.d("response_server", jsonObject1 + "");
                    assert jsonObject1 != null;
                    JsonObject jsonObject2 = jsonObject1.getAsJsonObject("StatusMessage");
                    Log.d("response_server", jsonObject2 + "");
                    String response_server = jsonObject2.toString();
                    try {
                        openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_caste_income_parameters_Kan.this);
                        database = openHelper.getWritableDatabase();

                        JSONObject jsonObject = new JSONObject(response_server);
                        JSONArray array = jsonObject.getJSONArray("Table");

                        truncateDatabase_Docs();

                        //dialog1.setProgress(10);
                        Type listType = new TypeToken<List<Set_and_Get_Down_Docs>>() {
                        }.getType();
                        List<Set_and_Get_Down_Docs> myModelList = new Gson().fromJson(array.toString(), listType);
                        for (Set_and_Get_Down_Docs set_and_get_down_docs : myModelList) {

                            //dialog1.setProgress(10);
                            database.execSQL("insert into " + DataBaseHelperClass_btnDownload_Docs.TABLE_NAME + "("
                                    + DataBaseHelperClass_btnDownload_Docs.GSCNO + ","
                                    + DataBaseHelperClass_btnDownload_Docs.DocumentName + ","
                                    + DataBaseHelperClass_btnDownload_Docs.DocumentID + ","
                                    + DataBaseHelperClass_btnDownload_Docs.Document + ") values ('"
                                    + set_and_get_down_docs.getGSCNO() + "','"
                                    + set_and_get_down_docs.getDocumentName() + "',"
                                    + set_and_get_down_docs.getDocumentID() + ",'"
                                    + set_and_get_down_docs.getDocument() + "')");

                            Log.d("Database", "Down_Docs Database Inserted");
                            //dialog1.setProgress(30);
                            //Toast.makeText(getApplicationContext(), "Docs Downloaded successfully", Toast.LENGTH_SHORT).show();
                        }
                        database.close();
                        //dialog1.setProgress(10);
                        runOnUiThread(() -> {
                            openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_caste_income_parameters_Kan.this);
                            database = openHelper.getWritableDatabase();

                            Cursor cursor3 = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_Docs.TABLE_NAME, null);

                            if (cursor3.getCount() > 0) {
                                dialog.dismiss();
                                btnViewDocs.setVisibility(View.VISIBLE);
                                //Toast.makeText(getApplicationContext(), "Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                cursor3.close();
                                Log.d("Values", "No records Exists");
                                btnViewDocs.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), getString(R.string.document_not_found), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        database.close();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("JSONException", "" + e);
                        btnViewDocs.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), getString(R.string.document_not_found), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                        Log.d("JsonParseException", "" + e);
                        btnViewDocs.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), getString(R.string.document_not_found), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Log.d("NullPointerException", "" + e);
                        btnViewDocs.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), getString(R.string.document_not_found), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("multipleResource",""+t.getMessage());
                call.cancel();
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void truncateDatabase_Docs(){
        //dialog1.setProgress(20);
        openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_caste_income_parameters_Kan.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_Docs.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_Docs.TABLE_NAME);
            Log.d("Database", "Down_Docs Database Truncated");
        } else {
            cursor.close();
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void GetCategory() {
        try {
            String str="CI";
            List<SpinnerObject> labels;
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            labels = sqlLiteOpenHelper_class_kan.Get_Category(str, getString(R.string.select_category_spinner));

            ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_color, labels);
            dataAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spCategory.setAdapter(dataAdapter);


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_creating_table), Toast.LENGTH_LONG).show();
        }
    }
    public void GetCategory_OBC() {
        try {
            String str="OBC";
            List<SpinnerObject> labels;
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            labels = sqlLiteOpenHelper_class_kan.Get_Category_OBC(str, getString(R.string.select_category_spinner));

            ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_color, labels);
            dataAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spCategory.setAdapter(dataAdapter);


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_creating_table), Toast.LENGTH_LONG).show();
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
            getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(strSearchCaste, num);
            Log.d("Selected_casteCode", ""+ getCasteCode);
        });
    }

    public void GetCaste_EWS(){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class_kan.GetCaste_EWS();

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchCaste.setAdapter(adapter);
        autoSearchCaste.setOnItemClickListener((parent, view, position, id) -> {
            String CatCode;
            CatCode = ((SpinnerObject)parent.getItemAtPosition(position)).getId();
            getCatCode = Integer.parseInt(CatCode);
            strSearchCaste = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchCaste);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(strSearchCaste, getCatCode);
            Log.d("Selected_casteCode", "getCatCode = "+ getCatCode +", getCasteCode = "+ getCasteCode);
        });
    }

    public class GetCategorySelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strCategory = ((SpinnerObject) spCategory.getSelectedItem()).getValue();
            Log.d("Selected Item", ""+strCategory);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strCategory);
            Log.d("Category_Code", ""+ getCatCode);
            if (!strCategory.equals(getString(R.string.select_category_spinner))) {
                TableApplicantCaste.setVisibility(View.VISIBLE);
                autoSearchCaste.setVisibility(View.VISIBLE);
                GetCaste(getCatCode);
            }
            else {
                TableApplicantCaste.setVisibility(View.GONE);
                autoSearchCaste.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    public void GetCaste_OBC(int num){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class_kan.GetCaste_OBC(num);

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchCaste.setAdapter(adapter);
        autoSearchCaste.setOnItemClickListener((parent, view, position, id) -> {
            strSearchCaste = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchCaste);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode_OBC(strSearchCaste);
            Log.d("Selected_casteCode", ""+ getCasteCode);
        });
    }
    public class GetCategory_OBC_Selected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strCategory = ((SpinnerObject) spCategory.getSelectedItem()).getValue();
            Log.d("Selected Item", ""+strCategory);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strCategory);
            Log.d("Category_Code", ""+ getCatCode);
            if (!strCategory.equals(getString(R.string.select_category_spinner))) {
                TableApplicantCaste.setVisibility(View.VISIBLE);
                autoSearchCaste.setVisibility(View.VISIBLE);
                GetCaste_OBC(getCatCode);
            }
            else {
                TableApplicantCaste.setVisibility(View.GONE);
                autoSearchCaste.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.discard_changes))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    RI_Field_Report_caste_income_parameters_Kan.super.onBackPressed();
                    Intent i = new Intent(RI_Field_Report_caste_income_parameters_Kan.this, RI_Field_Report_FirstScreen.class);
                    i.putExtra("applicant_Id", applicant_Id);
                    i.putExtra("district_Code", district_Code);
                    i.putExtra("taluk_Code", taluk_Code);
                    i.putExtra("hobli_Code", hobli_Code);
                    i.putExtra("districtCode", district);
                    i.putExtra("taluk", taluk);
                    i.putExtra("RI_Name", RI_Name);
                    i.putExtra("VA_Name", VA_Name);
                    i.putExtra("hobli", hobli);
                    i.putExtra("va_Circle_Code", va_Circle_Code);
                    i.putExtra("VA_Circle_Name", VA_Circle_Name);
                    i.putExtra("strSearchServiceName", service_name);
                    i.putExtra("strSearchVillageName", village_name);
                    i.putExtra("serviceCode", serviceCode);
                    i.putExtra("villageCode", villageCode);
                    i.putExtra("habitationCode", habitationCode);
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

    @Override
    public void onBackPressed() {
        buildAlertMessageGoingBack();
    }
}
