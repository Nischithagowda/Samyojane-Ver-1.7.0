package com.bhoomi.Samyojane_Application;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceView;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT;

/**
 * Created by Nischitha on 08-07-2019.
 */

public class RI_Field_Report_Caste_sc_st_certi_Parameters_Kan extends AppCompatActivity {

    HashMap<String, String> hashMap_Down_Docs;

    TextView tvHobli, tvTaluk, tvVA_Name, tvServiceName, tv_V_T_Name, txt_ReportNo;
    String RI_Name,district, taluk, hobli, VA_Circle_Name, VA_Name;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code, applicant_Id, applicant_name;
    String village_name, service_name;
    String villageCode, serviceCode, habitationCode, town_Name, ward_Name, town_code, ward_code, option_Flag;
    EditText tvIncome, tvRemarks;
    Button btnSave, btnBack, btnDownDocs, btnViewDocs;
    TextView txt_raiseLoc, title, RI_Recommendation, ApplicantID, ApplicantName, NoYears, FatherCategory,FatherCaste, MotherCategory, MotherCaste, ApplicantCategory, ApplicantCaste, AnnualIncome, ReservationGiven, ReasonForRejection, Remarks;
    TableRow lRejection;
    Spinner spYears,spFatherCategory, spMotherCategory, spAPPCategory_VA, spRejectReason1;
    AutoCompleteTextView autoSearchFatherCaste, autoSearchMotherCaste, autoSearchAPPCaste_VA;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    SqlLiteOpenHelper_Class_Kan sqlLiteOpenHelper_class_kan, sqlLiteOpenHelper_class_kan1;
    String appID, appName, noYears, fatherCategory, fatherCaste, motherCategory, motherCaste, appCategory, appCaste, appAnnualIncome, appReservationGiven, appRejectionReason, remarks;
    String n_fatherCategory, n_fatherCaste, n_motherCategory, n_motherCaste, n_appCategory, n_appCaste, n_appRejectionReason;
    TextView tvVillageName,tvNoYears, tvFatherCategory, tvFatherCaste, tvMotherCategory, tvMotherCaste, tvAppCategory, tvAppCaste, tvAnnualIncome, tvRemarksColor;
    int income_len, income_Value;
    RadioGroup radioGroup2, radioGroup3;
    RadioButton radioButton2, radioButton22, radioButton3, radioButton33;
    String option2, option3;
    TableRow TableFatherCaste, TableMotherCaste,TableAppCaste,TableReasonForRejection, TableCasteReservation;
    ArrayAdapter<CharSequence> adapter_rejection_reason, adapter_Year;
    String strRejectionReason, strYear;
    int reason_Code_1;
    int posRejectionReason;
    int getFatherCatCode=0, getFatherCasteCode=0, getMotherCatCode=0, getMotherCasteCode=0, getAppCatCode_VA=0, getAppCasteCode_VA=0;
    Activity activity;
    String strSearchServiceName, strSearchVillageName, strFatherCategory, strSearchFatherCaste,
            strMotherCategory, strSearchMotherCaste, strAppCategory_VA, strSearchAppCaste_VA;
    GPSTracker gpsTracker;
    double latitude, longitude;
    String strIncome, strRemarks;
    ProgressDialog dialog;
    int FCategory, FCaste, MCategory, MCaste, Total_No_Years_10, NO_Months_10;
    String photo, getNoYears;
    String appImage;
    ImageView iv_scst;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;
    String raisedLoc, eng_certi, GSC_FirstPart;

    final int min = 1111;
    final int max = 9999;
    int random;
    String report_no;


    int getCatCode, getCasteCode;
    String reason_Code_2, codePurpose;
    String option, option1,option5;

    private InputFilter filter_Eng = (source, start, end, dest, dstart, dend) -> {
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

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility", "HardwareIds"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_field_report_caste_sc_st_certi_parameters);

        strYear = getString(R.string.select_spinner);
        option2=getString(R.string.yes);
        option3=getString(R.string.yes);
        strFatherCategory=getString(R.string.select_category_spinner);
        strSearchFatherCaste=getString(R.string.select_caste_spinner);
        strMotherCategory=getString(R.string.select_category_spinner);
        strSearchMotherCaste=getString(R.string.select_caste_spinner);
        strAppCategory_VA=getString(R.string.select_category_spinner);
        strSearchAppCaste_VA=getString(R.string.select_caste_spinner);

        tvTaluk = findViewById(R.id.taluk);
        tvHobli = findViewById(R.id.hobli);
        tvVA_Name = findViewById(R.id.VA_name);

        btnBack = findViewById(R.id.btnBack);
        txt_raiseLoc = findViewById(R.id.txt_raiseLoc);
        tvServiceName = findViewById(R.id.tvServiceName);
        title = findViewById(R.id.title);
        RI_Recommendation = findViewById(R.id.RI_Recommendation);
        tvVillageName = findViewById(R.id.tvVillageName);
        ApplicantID = findViewById(R.id.ApplicantID);
        ApplicantName = findViewById(R.id.ApplicantName);
        NoYears= findViewById(R.id.NoYears);
        FatherCategory= findViewById(R.id.FatherCategory);
        FatherCaste = findViewById(R.id.FatherCaste);
        MotherCategory = findViewById(R.id.MotherCategory);
        MotherCaste = findViewById(R.id.MotherCaste);
        ApplicantCategory = findViewById(R.id.ApplicantCategory);
        ApplicantCaste = findViewById(R.id.ApplicantCaste);
        AnnualIncome = findViewById(R.id.AnnualIncome);
        ReservationGiven = findViewById(R.id.ReservationGiven);
        ReasonForRejection = findViewById(R.id.ReasonForRejection);
        Remarks = findViewById(R.id.Remarks);
        tvIncome = findViewById(R.id.tvIncome);
        tvRemarks = findViewById(R.id.tvRemarks);
        lRejection = findViewById(R.id.lRejection);
        btnSave = findViewById(R.id.btnSave);
        spYears = findViewById(R.id.spYears);
        spFatherCategory = findViewById(R.id.spFatherCategory);
        spMotherCategory = findViewById(R.id.spMotherCategory);
        spAPPCategory_VA = findViewById(R.id.spAPPCategory_VA);
        autoSearchFatherCaste = findViewById(R.id.autoSearchFatherCaste);
        autoSearchMotherCaste = findViewById(R.id.autoSearchMotherCaste);
        autoSearchAPPCaste_VA = findViewById(R.id.autoSearchAPPCaste_VA);
        spRejectReason1 = findViewById(R.id.spRejectReason1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton22 = findViewById(R.id.radioButton22);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton33 = findViewById(R.id.radioButton33);
        TableFatherCaste = findViewById(R.id.TableFatherCaste);
        TableMotherCaste = findViewById(R.id.TableMotherCaste);
        TableAppCaste = findViewById(R.id.TableAppCaste);
        TableReasonForRejection = findViewById(R.id.TableReasonForRejection);
        TableCasteReservation = findViewById(R.id.TableCasteReservation);
        iv_scst = findViewById(R.id.iv_scst);
        tv_V_T_Name = findViewById(R.id.tv_V_T_Name);
        btnDownDocs = findViewById(R.id.btnDownDocs);
        btnViewDocs = findViewById(R.id.btnViewDocs);
        txt_ReportNo = findViewById(R.id.txt_ReportNo);

        tvNoYears = findViewById(R.id.tvNoYears);
        tvFatherCategory = findViewById(R.id.tvFatherCategory);
        tvFatherCaste = findViewById(R.id.tvFatherCaste);
        tvMotherCategory = findViewById(R.id.tvMotherCategory);
        tvMotherCaste = findViewById(R.id.tvMotherCaste);
        tvAppCategory = findViewById(R.id.tvAppCategory);
        tvAppCaste = findViewById(R.id.tvAppCaste);
        tvAnnualIncome = findViewById(R.id.tvAnnualIncome);
        tvRemarksColor = findViewById(R.id.tvRemarksColor);

        btnViewDocs.setVisibility(View.GONE);

        NoYears.setVisibility(View.VISIBLE);
        FatherCategory.setVisibility(View.VISIBLE);
        FatherCaste.setVisibility(View.VISIBLE);
        MotherCategory.setVisibility(View.VISIBLE);
        MotherCaste.setVisibility(View.VISIBLE);
        ApplicantCategory.setVisibility(View.VISIBLE);
        ApplicantCaste.setVisibility(View.VISIBLE);
        ReservationGiven.setVisibility(View.VISIBLE);
        ReasonForRejection.setVisibility(View.VISIBLE);
        AnnualIncome.setVisibility(View.VISIBLE);
        Remarks.setVisibility(View.VISIBLE);
        tvIncome.setVisibility(View.GONE);
        tvRemarks.setVisibility(View.GONE);
        lRejection.setVisibility(View.GONE);
        spYears.setVisibility(View.GONE);
        spFatherCategory.setVisibility(View.GONE);
        spMotherCategory.setVisibility(View.GONE);
        spAPPCategory_VA.setVisibility(View.GONE);
        autoSearchFatherCaste.setVisibility(View.GONE);
        autoSearchMotherCaste.setVisibility(View.GONE);
        autoSearchAPPCaste_VA.setVisibility(View.GONE);
        TableFatherCaste.setVisibility(View.VISIBLE);
        TableMotherCaste.setVisibility(View.VISIBLE);
        TableAppCaste.setVisibility(View.VISIBLE);

        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
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

        if(report_no == null){
            Date c1 = Calendar.getInstance().getTime();

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
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

        Log.d("RI_Caste_sc_st", ""+district_Code);
        Log.d("RI_Caste_sc_st", ""+taluk_Code);
        Log.d("RI_Caste_sc_st",""+hobli_Code+" VA_Name :"+VA_Name+" VillageName :"+village_name+"ServiceName:"+service_name+"\nva_Circle_Code"+va_Circle_Code);
        Log.d("RI_Caste_sc_st", ""+applicant_Id);
        Log.d("RI_Caste_sc_st", ""+habitationCode);
        Log.d( "town_code: ",""+town_code);
        Log.d("town_Name: ",""+town_Name);
        Log.d( "ward_code: ",""+ward_code);
        Log.d( "ward_Name: ",""+ward_Name);
        Log.d("option_Flag: ",""+ option_Flag);

        tvTaluk.setText(taluk);
        tvHobli.setText(hobli);
        tvVA_Name.setText(RI_Name);
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

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1+" where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No+"="+applicant_Id, null);
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                eng_certi = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_Eng_Certificate));
                GSC_FirstPart = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_GSCFirstPart));
                raisedLoc = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Raised_Location));
                appID = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No));
                appName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name));
                noYears = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Num_Years_8));
                fatherCategory = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Father_Category_8));
                fatherCaste = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Father_Caste_8));
                motherCategory = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Mother_Category_8));
                motherCaste = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Mother_Caste_8));
                appCategory = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Category));
                appCaste = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Caste));
                appAnnualIncome = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Annual_Income));
                appReservationGiven = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Can_Certificate_Given));
                appRejectionReason = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reason_for_Rejection));
                remarks = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Remarks));
                appImage = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_applicant_photo));
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

        Log.d("dbValues", "App_ID "+appID);
        Log.d("dbValues", "appName "+appName);
        Log.d("dbValues", "noYears "+noYears);
        Log.d("dbValues", "fatherCategory "+fatherCategory);
        Log.d("dbValues", "fatherCaste "+fatherCaste);
        Log.d("dbValues", "motherCategory "+motherCategory);
        Log.d("dbValues", "motherCaste "+motherCaste);
        Log.d("dbValues", "appCategory "+appCategory);
        Log.d("dbValues", "appCaste "+appCaste);
        Log.d("dbValues", "appAnnualIncome "+appAnnualIncome);
        Log.d("dbValues", "appReservationGiven "+appReservationGiven);
        Log.d("dbValues", "appRejectionReason "+appRejectionReason);
        Log.d("dbValues", "remarks "+remarks);
        Log.d("dbValues",""+appImage);
//        if(serviceCode.equals("8")){
//            if(appImage!=null) {
//                iv_scst.setVisibility(View.VISIBLE);
//                byte im_bytes[] = Base64.decode(appImage, Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(im_bytes, 0, im_bytes.length);
//                iv_scst.setImageBitmap(bitmap);
//            }else {
//                iv_scst.setVisibility(View.GONE);
//            }
//        }else if(serviceCode.equals("7")){
//            iv_scst.setVisibility(View.GONE);
//        }

        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
        n_fatherCategory = sqlLiteOpenHelper_class_kan.GetCategory_BY_Code(Integer.parseInt(fatherCategory));
        n_fatherCaste = sqlLiteOpenHelper_class_kan.GetCaste_BY_Code(Integer.parseInt(fatherCategory), Integer.parseInt(fatherCaste));

        n_motherCategory = sqlLiteOpenHelper_class_kan.GetCategory_BY_Code(Integer.parseInt(motherCategory));
        n_motherCaste = sqlLiteOpenHelper_class_kan.GetCaste_BY_Code(Integer.parseInt(motherCategory), Integer.parseInt(motherCaste));

        n_appCategory = sqlLiteOpenHelper_class_kan.GetCategory_BY_Code(Integer.parseInt(appCategory));
        n_appCaste = sqlLiteOpenHelper_class_kan.GetCaste_BY_Code(Integer.parseInt(appCategory), Integer.parseInt(appCaste));

        sqlLiteOpenHelper_class_kan1 = new SqlLiteOpenHelper_Class_Kan(this);
        sqlLiteOpenHelper_class_kan1.open_Reasons_Master_Tbl();
        n_appRejectionReason = sqlLiteOpenHelper_class_kan1.Get_CertificateRejectionReason_BY_Code(Integer.parseInt(appRejectionReason), getString(R.string.reasons_tbl_reason_name));

        ApplicantID.setText(appID);
        ApplicantName.setText(appName);
        NoYears.setText(noYears);
        FatherCategory.setText(n_fatherCategory);
        FatherCaste.setText(n_fatherCaste);
        MotherCategory.setText(n_motherCategory);
        MotherCaste.setText(n_motherCaste);
        ApplicantCategory.setText(n_appCategory);
        ApplicantCaste.setText(n_appCaste);
        AnnualIncome.setText(appAnnualIncome);
        ReservationGiven.setText(appReservationGiven);
        ReasonForRejection.setText(n_appRejectionReason);
        Remarks.setText(remarks);

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

        if(appReservationGiven.equals("YES")){
            TableReasonForRejection.setVisibility(View.GONE);
        }else {
            TableReasonForRejection.setVisibility(View.VISIBLE);
        }

        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton2) {
                option2 = getString(R.string.yes);
                NoYears.setVisibility(View.VISIBLE);
                FatherCategory.setVisibility(View.VISIBLE);
                FatherCaste.setVisibility(View.VISIBLE);
                MotherCategory.setVisibility(View.VISIBLE);
                MotherCaste.setVisibility(View.VISIBLE);
                ApplicantCategory.setVisibility(View.VISIBLE);
                ApplicantCaste.setVisibility(View.VISIBLE);
                ReservationGiven.setVisibility(View.VISIBLE);
                ReasonForRejection.setVisibility(View.VISIBLE);
                AnnualIncome.setVisibility(View.VISIBLE);
                Remarks.setVisibility(View.VISIBLE);
                tvIncome.setVisibility(View.GONE);
                tvRemarks.setVisibility(View.GONE);
                spYears.setVisibility(View.GONE);
                spFatherCategory.setVisibility(View.GONE);
                spMotherCategory.setVisibility(View.GONE);
                spAPPCategory_VA.setVisibility(View.GONE);
                autoSearchFatherCaste.setVisibility(View.GONE);
                autoSearchMotherCaste.setVisibility(View.GONE);
                autoSearchAPPCaste_VA.setVisibility(View.GONE);
                TableFatherCaste.setVisibility(View.VISIBLE);
                TableMotherCaste.setVisibility(View.VISIBLE);
                TableAppCaste.setVisibility(View.VISIBLE);
                TableCasteReservation.setVisibility(View.VISIBLE);
                if(appReservationGiven.equals("YES")){
                    TableReasonForRejection.setVisibility(View.GONE);
                }else {
                    TableReasonForRejection.setVisibility(View.VISIBLE);
                }

                tvNoYears.setTextColor(Color.parseColor("#000000"));
                tvFatherCategory.setTextColor(Color.parseColor("#000000"));
                tvFatherCaste.setTextColor(Color.parseColor("#000000"));
                tvMotherCategory.setTextColor(Color.parseColor("#000000"));
                tvMotherCaste.setTextColor(Color.parseColor("#000000"));
                tvAppCategory.setTextColor(Color.parseColor("#000000"));
                tvAppCaste.setTextColor(Color.parseColor("#000000"));
                tvAnnualIncome.setTextColor(Color.parseColor("#000000"));
                tvRemarksColor.setTextColor(Color.parseColor("#000000"));
            } else if (checkedId == R.id.radioButton22) {
                option2 = getString(R.string.no);
                NoYears.setVisibility(View.GONE);
                FatherCategory.setVisibility(View.GONE);
                FatherCaste.setVisibility(View.GONE);
                MotherCategory.setVisibility(View.GONE);
                MotherCaste.setVisibility(View.GONE);
                ApplicantCategory.setVisibility(View.GONE);
                ApplicantCaste.setVisibility(View.GONE);
                ReservationGiven.setVisibility(View.GONE);
                ReasonForRejection.setVisibility(View.GONE);
                AnnualIncome.setVisibility(View.GONE);
                Remarks.setVisibility(View.GONE);
                tvIncome.setVisibility(View.VISIBLE);
                tvRemarks.setVisibility(View.VISIBLE);
                spYears.setVisibility(View.VISIBLE);
                spFatherCategory.setVisibility(View.VISIBLE);
                spMotherCategory.setVisibility(View.VISIBLE);
                spAPPCategory_VA.setVisibility(View.VISIBLE);
                autoSearchFatherCaste.setVisibility(View.VISIBLE);
                autoSearchMotherCaste.setVisibility(View.VISIBLE);
                autoSearchAPPCaste_VA.setVisibility(View.VISIBLE);
                TableCasteReservation.setVisibility(View.GONE);
                TableReasonForRejection.setVisibility(View.GONE);
                TableFatherCaste.setVisibility(View.GONE);
                TableMotherCaste.setVisibility(View.GONE);
                TableAppCaste.setVisibility(View.GONE);

                if (!strFatherCategory.equals(getString(R.string.select_category_spinner))) {
                    autoSearchFatherCaste.setVisibility(View.VISIBLE);
                    TableFatherCaste.setVisibility(View.VISIBLE);
                    GetFatherCaste(getFatherCatCode);
                }
                else {
                    TableFatherCaste.setVisibility(View.GONE);
                    autoSearchFatherCaste.setVisibility(View.GONE);
                }

                if (!strMotherCategory.equals(getString(R.string.select_category_spinner))) {
                    TableMotherCaste.setVisibility(View.VISIBLE);
                    autoSearchMotherCaste.setVisibility(View.VISIBLE);
                    GetMotherCaste(getMotherCatCode);
                }
                else {
                    TableMotherCaste.setVisibility(View.GONE);
                    autoSearchMotherCaste.setVisibility(View.GONE);
                }

                if (!strAppCategory_VA.equals(getString(R.string.select_category_spinner))) {
                    TableAppCaste.setVisibility(View.VISIBLE);
                    autoSearchAPPCaste_VA.setVisibility(View.VISIBLE);
                    GetAppCaste_VA(getAppCatCode_VA);
                }
                else {
                    TableAppCaste.setVisibility(View.GONE);
                    autoSearchAPPCaste_VA.setVisibility(View.GONE);
                }

                tvNoYears.setTextColor(Color.parseColor("#0000ff"));
                tvFatherCategory.setTextColor(Color.parseColor("#0000ff"));
                tvFatherCaste.setTextColor(Color.parseColor("#0000ff"));
                tvMotherCategory.setTextColor(Color.parseColor("#0000ff"));
                tvMotherCaste.setTextColor(Color.parseColor("#0000ff"));
                tvAppCategory.setTextColor(Color.parseColor("#0000ff"));
                tvAppCaste.setTextColor(Color.parseColor("#0000ff"));
                tvAnnualIncome.setTextColor(Color.parseColor("#0000ff"));
                tvRemarksColor.setTextColor(Color.parseColor("#0000ff"));
            }
        });

        radioGroup3.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton3) {
                //Toast.makeText(getApplicationContext(), "choice: male", Toast.LENGTH_SHORT).show();
                option3 = getString(R.string.yes);
                if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)) {
                    option3 = getString(R.string.yes);
                    radioButton3.setChecked(true);
                    lRejection.setVisibility(View.GONE);
                    spRejectReason1.setSelection(0);
                    autoSearchAPPCaste_VA.setError(null);
                }else {
                    option3=getString(R.string.no);
                    radioButton33.setChecked(true);
                    lRejection.setVisibility(View.VISIBLE);
                    autoSearchAPPCaste_VA.setError(getString(R.string.must_match_father_mother));
                }
            } else if (checkedId == R.id.radioButton33) {
                option3 = getString(R.string.no);
                lRejection.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), "choice: Female", Toast.LENGTH_SHORT).show();
            }
            Log.d("option3", ""+option3);
        });

        adapter_rejection_reason = ArrayAdapter.createFromResource(this, R.array.RejectionReason, R.layout.spinner_item_color);
        adapter_rejection_reason.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spRejectReason1.setAdapter(adapter_rejection_reason);

        spRejectReason1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strRejectionReason = String.valueOf(spRejectReason1.getSelectedItem());
                posRejectionReason = position;
                sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
                sqlLiteOpenHelper_class_kan.open_Reasons_Master_Tbl();
                reason_Code_1 = sqlLiteOpenHelper_class_kan.Get_CertificateRejectionReason(strRejectionReason, getString(R.string.reasons_tbl_reason_name));
                Log.d("Number", ""+ reason_Code_1);
                Log.d("Item_Position", ""+ position);
                Log.d("Spinner_Value", ""+strRejectionReason);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        autoSearchAPPCaste_VA.setVisibility(View.GONE);
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

        if (serviceCode.equals("7")) {
            GetCategory_Cat_1();
        }
        else if(serviceCode.equals("8"))
        {
            GetCategory_SCST();
        }
        spFatherCategory.setOnItemSelectedListener( new GetFatherCategorySelected());
        spMotherCategory.setOnItemSelectedListener( new GetMotherCategorySelected());
        spAPPCategory_VA.setOnItemSelectedListener( new GetAppCategorySelected_VA());

        btnDownDocs.setOnClickListener(v -> {
            if (isNetworkAvailable()) {

                dialog.show();
                //dialog1.setProgress(0);

                Log.d("Docs", "Applicant_ID:"+applicant_Id+"\nGSC_FirstPart:"+GSC_FirstPart);
                openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
                database = openHelper.getWritableDatabase();

                //8186126549
                hashMap_Down_Docs.put("GSC_No", applicant_Id);
                hashMap_Down_Docs.put("GSC_First_Part", GSC_FirstPart);
                Log.d("hashMap_Down_Docs", ""+hashMap_Down_Docs+", URL:"+getString(R.string.url_Down_Docs));
                new GetDocsFromServer().execute(getString(R.string.url_Down_Docs));
                //http://164.100.133.30/NK_MobileApp/WebService.asmx/Get_Docs?GSC_No=8966150768&GSC_First_Part=3
                //GSC_No=8966150768&GSC_First_Part=3
            }
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
            }
        });

        btnViewDocs.setOnClickListener(v -> {
            Intent i12 = new Intent(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this, View_Docs.class);
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
                if(option2.equals(getString(R.string.no))){

                    strIncome = tvIncome.getText().toString();
                    strRemarks = tvRemarks.getText().toString();
                    Log.d("Income value", ""+strRemarks);

                    strFatherCategory = ((SpinnerObject) spFatherCategory.getSelectedItem()).getValue();
                    Log.d("Selected_Item1", ""+strFatherCategory);
                    sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                    sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                    getFatherCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strFatherCategory);
                    Log.d("Category_Code1", ""+ getFatherCatCode);
                    if (!strFatherCategory.equals(getString(R.string.select_category_spinner))) {

                        String caste_name = autoSearchFatherCaste.getText().toString();
                        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                        getFatherCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(caste_name, getFatherCatCode);
                        Log.d("Caste_Code1",""+getFatherCasteCode);

                    }

                    strMotherCategory = ((SpinnerObject) spMotherCategory.getSelectedItem()).getValue();
                    Log.d("Selected_Item1", ""+strMotherCategory);
                    sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                    sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                    getMotherCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strMotherCategory);
                    Log.d("Category_Code1", ""+ getMotherCatCode);
                    if (!strMotherCategory.equals(getString(R.string.select_category_spinner))) {

                        String caste_name = autoSearchMotherCaste.getText().toString();
                        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                        getMotherCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(caste_name, getMotherCatCode);
                        Log.d("Caste_Code1",""+getMotherCasteCode);

                    }

                    strAppCategory_VA = ((SpinnerObject) spAPPCategory_VA.getSelectedItem()).getValue();
                    Log.d("Selected_Item1", ""+strAppCategory_VA);
                    sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                    sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                    getAppCatCode_VA = sqlLiteOpenHelper_class_kan.GetCategoryCode(strAppCategory_VA);
                    Log.d("Category_Code1", ""+ getAppCatCode_VA);
                    if (!strAppCategory_VA.equals(getString(R.string.select_category_spinner))) {

                        String caste_name = autoSearchAPPCaste_VA.getText().toString();
                        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                        getAppCasteCode_VA = sqlLiteOpenHelper_class_kan.GetCasteCode(caste_name, getAppCatCode_VA);
                        Log.d("Caste_Code1",""+getAppCasteCode_VA);

                    }

                    if (!strYear.equals(getString(R.string.select_spinner))) {
                        if(!strFatherCategory.equals(getString(R.string.select_category_spinner))) {
                            if(!strSearchFatherCaste.equals(getString(R.string.select_caste_spinner))) {
                                if (getFatherCasteCode!=0) {
                                    if (!strMotherCategory.equals(getString(R.string.select_category_spinner))) {
                                        if (!strSearchMotherCaste.equals(getString(R.string.select_caste_spinner))) {
                                            if (getMotherCasteCode!=0) {
                                                if (!strAppCategory_VA.equals(getString(R.string.select_category_spinner))) {
                                                    if (!strSearchAppCaste_VA.equals(getString(R.string.select_caste_spinner))) {
                                                        if (getAppCasteCode_VA!=0) {
                                                            if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)) {
                                                                if (TextUtils.isEmpty(strIncome)) {
                                                                    tvIncome.setError(getString(R.string.field_canno_null));
                                                                } else {

                                                                    income_len = strIncome.length();
                                                                    income_Value = Integer.parseInt(strIncome);
                                                                    Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                                                                    if (income_len >= 4 && income_Value>=1000) {
                                                                        if (option3.equals(getString(R.string.no))) {
                                                                            if (!strRejectionReason.equals(getString(R.string.reason_spinner))) {
                                                                                if (TextUtils.isEmpty(strRemarks)) {
                                                                                    tvRemarks.setError(getString(R.string.field_canno_null));
                                                                                } else {
                                                                                    StoreData_in_DB_When_Wrong();
                                                                                }
                                                                            } else {
                                                                                ((TextView) spRejectReason1.getSelectedView()).setError("Select Reason");
                                                                                Toast.makeText(getApplicationContext(), "Select Reason", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {
                                                                            if (TextUtils.isEmpty(strRemarks)) {
                                                                                tvRemarks.setError(getString(R.string.field_canno_null));
                                                                            } else {
                                                                                StoreData_in_DB_When_Wrong();
                                                                            }
                                                                        }
                                                                    } else {
                                                                        tvIncome.setError(getString(R.string.incorrect_value));
                                                                    }
                                                                }
                                                            } else {
                                                                radioButton33.setChecked(true);
                                                                if (TextUtils.isEmpty(strIncome)) {
                                                                    tvIncome.setError(getString(R.string.field_canno_null));
                                                                } else {

                                                                    income_len = strIncome.length();
                                                                    income_Value = Integer.parseInt(strIncome);
                                                                    Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                                                                    if (income_len >= 4 && income_Value>=1000) {
                                                                        if (option3.equals(getString(R.string.no))) {
                                                                            if (!strRejectionReason.equals(getString(R.string.reason_spinner))) {
                                                                                if (TextUtils.isEmpty(strRemarks)) {
                                                                                    tvRemarks.setError(getString(R.string.field_canno_null));
                                                                                } else {
                                                                                    StoreData_in_DB_When_Wrong();
                                                                                }
                                                                            } else {
                                                                                ((TextView) spRejectReason1.getSelectedView()).setError("Select Reason");
                                                                                Toast.makeText(getApplicationContext(), "Select Reason", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {
                                                                            if (TextUtils.isEmpty(strRemarks)) {
                                                                                tvRemarks.setError(getString(R.string.field_canno_null));
                                                                            } else {
                                                                                StoreData_in_DB_When_Wrong();
                                                                            }
                                                                        }
                                                                    } else {
                                                                        tvIncome.setError(getString(R.string.incorrect_value));
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            autoSearchAPPCaste_VA.setError(getString(R.string.invalid_caste));
                                                        }
                                                    } else {
                                                        autoSearchAPPCaste_VA.setError(getString(R.string.select_caste));
                                                    }
                                                } else {
                                                    ((TextView) spAPPCategory_VA.getSelectedView()).setError(getString(R.string.select_category));
                                                    Toast.makeText(getApplicationContext(), getString(R.string.select_category), Toast.LENGTH_SHORT).show();
                                                }
                                            }else {
                                                autoSearchMotherCaste.setError(getString(R.string.invalid_caste));
                                            }
                                        } else {
                                            autoSearchMotherCaste.setError(getString(R.string.select_caste));
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
                    if(option3.equals(getString(R.string.yes))){
                        StoreData_in_DB_When_Correct();
                    }
                    else {
                        if(!strRejectionReason.equals(getString(R.string.reason_spinner))){
                            StoreData_in_DB_When_Correct();
                        }
                        else {
                            ((TextView) spRejectReason1.getSelectedView()).setError(getString(R.string.reason_for_rejection));
                            Toast.makeText(getApplicationContext(), getString(R.string.reason_for_rejection), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            else {
                runOnUiThread(() -> {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
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

        if(option2.equals(getString(R.string.no))){
            option2="NO";
        }else if (option2.equals(getString(R.string.yes))){
            option2 = "YES";
        }

        if(option3.equals(getString(R.string.no))){
            option3="NO";
        }else if (option3.equals(getString(R.string.yes))){
            option3 = "YES";
        }

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "="+applicant_Id, null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                getNoYears = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Num_Years_8));
                FCategory = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Father_Category_8));
                FCaste = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Father_Caste_8));
                MCategory = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.App_Mother_Category_8));
                MCaste = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.APP_Mother_Caste_8));
                strIncome = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Annual_Income));
                strRemarks = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Remarks));
                photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Photo));
                getCatCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Category));
                getCasteCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Caste));
                option5 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Belongs_Creamy_Layer_6));
                reason_Code_2 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reason_for_Creamy_Layer_6));
                Total_No_Years_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Total_No_Years_10));
                NO_Months_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.NO_Months_10));
                option = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reside_At_Stated_Address_10));
                option1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Place_Match_With_RationCard_10));
                codePurpose = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Pur_for_Cert_Code_10));
                Log.d("Data_Fetched", "StoreData_in_DB_When_Correct");
            }
            database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " set "
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Num_Years_8+"='"+getNoYears+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Father_Category_8+"="+FCategory+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Father_Caste_8+"="+FCaste+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Mother_Category_8+"="+MCategory+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Mother_Caste_8+"="+MCaste+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Annual_Income+"='"+strIncome+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Remarks+"='"+strRemarks+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Category + "="+getCatCode+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Caste+"="+getCasteCode+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Belongs_Creamy_Layer_6+"='"+option5+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Creamy_Layer_6+"='"+reason_Code_2+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Total_No_Years_10+"="+Total_No_Years_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_NO_Months_10+"="+NO_Months_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reside_At_Stated_Address_10 + "='" + option + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Place_Match_With_RationCard_10 + "='" + option1 + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Pur_for_Cert_Code_10 + "='" + codePurpose + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLat + "=" + latitude + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLong + "=" + longitude + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Can_Certificate_Given_as_RI + "='" + option3 + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Rejection_as_RI + "=" + reason_Code_1 + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Accepted_VA_information + "='"+option2+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Report_No + "='"+report_no+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag + "=1"
                    + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "="+applicant_Id);

            Intent i = new Intent(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this, RI_Field_Report_FirstScreen.class);
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
        }
        else {
            cursor.close();
            Log.d("Data", "StoreData_in_DB_When_Correct : Update_Failed");
            return "Update_Failed";
        }
    }

    public String StoreData_in_DB_When_Wrong() {

        if(option2.equals(getString(R.string.no))){
            option2="NO";
        }else if (option2.equals(getString(R.string.yes))){
            option2 = "YES";
        }

        if(option3.equals(getString(R.string.no))){
            option3="NO";
        }else if (option3.equals(getString(R.string.yes))){
            option3 = "YES";
        }

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
        database = openHelper.getWritableDatabase();
        getNoYears = strYear;
        FCategory = getFatherCatCode;
        FCaste = getFatherCasteCode;
        MCategory = getMotherCatCode;
        MCaste = getMotherCasteCode;
        getCatCode = getAppCatCode_VA;
        getCasteCode = getAppCasteCode_VA;

        Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "=" + applicant_Id, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                option5 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Belongs_Creamy_Layer_6));
                reason_Code_2 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reason_for_Creamy_Layer_6));
                Total_No_Years_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Total_No_Years_10));
                NO_Months_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.NO_Months_10));
                option = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reside_At_Stated_Address_10));
                option1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Place_Match_With_RationCard_10));
                codePurpose = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Pur_for_Cert_Code_10));
                photo = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Photo));
            }
            database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " set "
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Num_Years_8+"='"+getNoYears+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Father_Category_8+"="+FCategory+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Father_Caste_8+"="+FCaste+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Mother_Category_8+"="+MCategory+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Mother_Caste_8+"="+MCaste+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Annual_Income+"='"+strIncome+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Remarks+"='"+strRemarks+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Category+ "="+getCatCode+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Caste+"="+getCasteCode+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Belongs_Creamy_Layer_6+"='"+option5+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Creamy_Layer_6+"='"+reason_Code_2+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Total_No_Years_10+"="+Total_No_Years_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_NO_Months_10+"="+NO_Months_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reside_At_Stated_Address_10 + "='" + option + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Place_Match_With_RationCard_10 + "='" + option1 + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Pur_for_Cert_Code_10 + "='" + codePurpose + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLat + "=" + latitude + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLong + "=" + longitude + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Can_Certificate_Given_as_RI + "='" + option3 + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Rejection_as_RI + "=" + reason_Code_1 + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Accepted_VA_information + "='"+option2+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Report_No + "='"+report_no+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag + "=1"
                    + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "="+applicant_Id);

            Intent i = new Intent(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this, RI_Field_Report_FirstScreen.class);
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
        }
        else {
            cursor.close();
            Log.d("Data", "StoreData_in_DB_When_Wrong : Update_Failed");
            return "Update_Failed";
        }
    }

    @SuppressLint("ShowToast")
    public void GetCategory_SCST() {
        try {
            Log.d("GetCat", "Enter GetCat Function");
            String str="SCST";
            List<SpinnerObject> labels;
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            labels = sqlLiteOpenHelper_class_kan.Get_Category(str, getString(R.string.select_category_spinner));

            ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_color, labels);
            dataAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spFatherCategory.setAdapter(dataAdapter);
            spMotherCategory.setAdapter(dataAdapter);
            spAPPCategory_VA.setAdapter(dataAdapter);


        } catch (Exception e) {
            Log.d("Catch", String.valueOf(e));
            Toast.makeText(getApplicationContext(), getString(R.string.error_creating_table), Toast.LENGTH_LONG);
        }
    }

    @SuppressLint("ShowToast")
    public void GetCategory_Cat_1() {
        try {
            Log.d("GetCat", "Enter GetCat Function");
            String str="Category_1";
            List<SpinnerObject> labels;
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            labels = sqlLiteOpenHelper_class_kan.Get_Category(str, getString(R.string.select_category_spinner));

            ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_color, labels);
            dataAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spFatherCategory.setAdapter(dataAdapter);
            spMotherCategory.setAdapter(dataAdapter);
            spAPPCategory_VA.setAdapter(dataAdapter);


        } catch (Exception e) {
            Log.d("Catch", String.valueOf(e));
            Toast.makeText(getApplicationContext(), getString(R.string.error_creating_table), Toast.LENGTH_LONG);
        }
    }

    public void GetFatherCaste(int num){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, this);
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class_kan.GetCaste(num);

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchFatherCaste.setAdapter(adapter);
        autoSearchFatherCaste.setOnItemClickListener((parent, view, position, id) -> {
            strSearchFatherCaste = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchFatherCaste);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getFatherCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(strSearchFatherCaste, num);
            Log.d("Selected_casteCode", ""+ getFatherCasteCode);
        });
    }

    public class GetFatherCategorySelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strFatherCategory = ((SpinnerObject) spFatherCategory.getSelectedItem()).getValue();
            Log.d("Selected Item", ""+strFatherCategory);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getFatherCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strFatherCategory);
            Log.d("Category_Code", ""+ getFatherCatCode);
            if (!strFatherCategory.equals(getString(R.string.select_category_spinner))) {
                autoSearchFatherCaste.setVisibility(View.VISIBLE);
                TableFatherCaste.setVisibility(View.VISIBLE);
                GetFatherCaste(getFatherCatCode);
            }
            else {
                TableFatherCaste.setVisibility(View.GONE);
                autoSearchFatherCaste.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void GetMotherCaste(int num){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, this);
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class_kan.GetCaste(num);

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchMotherCaste.setAdapter(adapter);
        autoSearchMotherCaste.setOnItemClickListener((parent, view, position, id) -> {
            strSearchMotherCaste = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchMotherCaste);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getMotherCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(strSearchMotherCaste, num);
            Log.d("Selected_casteCode", ""+ getMotherCasteCode);
        });
    }

    public class GetMotherCategorySelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strMotherCategory = ((SpinnerObject) spMotherCategory.getSelectedItem()).getValue();
            Log.d("Selected Item", ""+strMotherCategory);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getMotherCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strMotherCategory);
            Log.d("Category_Code", ""+ getMotherCatCode);
            if (!strMotherCategory.equals(getString(R.string.select_category_spinner))) {
                TableMotherCaste.setVisibility(View.VISIBLE);
                autoSearchMotherCaste.setVisibility(View.VISIBLE);
                GetMotherCaste(getMotherCatCode);
            }
            else {
                TableMotherCaste.setVisibility(View.GONE);
                autoSearchMotherCaste.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void GetAppCaste_VA(int num){
        List<SpinnerObject> labels;
        sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, this);
        sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
        labels = sqlLiteOpenHelper_class_kan.GetCaste(num);

        ArrayAdapter<SpinnerObject> adapter = new ArrayAdapter<>(this, R.layout.list_item, labels);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchAPPCaste_VA.setAdapter(adapter);
        autoSearchAPPCaste_VA.setOnItemClickListener((parent, view, position, id) -> {
            strSearchAppCaste_VA = parent.getItemAtPosition(position).toString();
            Log.d("Selected_Item", ""+strSearchAppCaste_VA);
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getAppCasteCode_VA = sqlLiteOpenHelper_class_kan.GetCasteCode(strSearchAppCaste_VA, num);
            if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)) {
                option3 = getString(R.string.yes);
                radioButton3.setChecked(true);
                lRejection.setVisibility(View.GONE);
                spRejectReason1.setSelection(0);
                autoSearchAPPCaste_VA.setError(null);
            }else {
                option3=getString(R.string.no);
                radioButton33.setChecked(true);
                lRejection.setVisibility(View.VISIBLE);
                autoSearchAPPCaste_VA.setError(getString(R.string.must_match_father_mother));
            }
            Log.d("Selected_casteCode", ""+ getAppCasteCode_VA);
        });
    }

    public class GetAppCategorySelected_VA implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strAppCategory_VA = ((SpinnerObject) spAPPCategory_VA.getSelectedItem()).getValue();
            Log.d("Selected Item", ""+strAppCategory_VA);
            if (strSearchAppCaste_VA.equals(strSearchFatherCaste) || strSearchAppCaste_VA.equals(strSearchMotherCaste)){
                radioButton3.setChecked(true);
            }
            else {
                radioButton33.setChecked(true);
            }
            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(activity, RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
            getAppCatCode_VA = sqlLiteOpenHelper_class_kan.GetCategoryCode(strAppCategory_VA);
            Log.d("Category_Code", ""+ getAppCatCode_VA);
            if (!strAppCategory_VA.equals(getString(R.string.select_category_spinner))) {
                TableAppCaste.setVisibility(View.VISIBLE);
                autoSearchAPPCaste_VA.setVisibility(View.VISIBLE);
                GetAppCaste_VA(getAppCatCode_VA);
            }
            else {
                TableAppCaste.setVisibility(View.GONE);
                autoSearchAPPCaste_VA.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetDocsFromServer extends AsyncTask<String, Void, JSONObject> {
        JSONObject jsonObject;

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JParserAdv jParserAdv = new JParserAdv();
                jsonObject = jParserAdv.makeHttpRequest(getString(R.string.url_Down_Docs), "POST", hashMap_Down_Docs);
            }catch (NullPointerException e){
                runOnUiThread(() -> dialog.dismiss());
                Log.e("NullPointerException", ""+e.toString());
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            try {
                //dialog1.setProgress(20);
                openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
                database = openHelper.getWritableDatabase();

                JSONArray array = jsonObject.getJSONArray("data");

                truncateDatabase_Docs();

                //dialog1.setProgress(10);
                Type listType = new TypeToken<List<Set_and_Get_Down_Docs>>(){}.getType();
                List<Set_and_Get_Down_Docs> myModelList = new Gson().fromJson(array.toString(), listType);
                for(Set_and_Get_Down_Docs set_and_get_down_docs:myModelList){

                    //dialog1.setProgress(10);
                    database.execSQL("insert into " + DataBaseHelperClass_btnDownload_Docs.TABLE_NAME + "("
                            + DataBaseHelperClass_btnDownload_Docs.UDT_GSC_No+","
                            + DataBaseHelperClass_btnDownload_Docs.UDT_GSCFirstPart+","
                            + DataBaseHelperClass_btnDownload_Docs.UDT_Document_Id+","
                            + DataBaseHelperClass_btnDownload_Docs.UDT_File+") values ("
                            + set_and_get_down_docs.getUDT_GSC_No() +","
                            + set_and_get_down_docs.getUDT_GSCFirstPart()+","
                            + set_and_get_down_docs.getUDT_Document_Id() +",'"
                            + Base64.encodeToString(set_and_get_down_docs.getUDT_File(),Base64.DEFAULT)+"')");

                    Log.d("Database", "Down_Docs Database Inserted");
                    //dialog1.setProgress(30);
                    //Toast.makeText(getApplicationContext(), "Docs Downloaded successfully", Toast.LENGTH_SHORT).show();
                }
                database.close();
                //dialog1.setProgress(10);
                runOnUiThread(() -> {
                    openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
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
                        Toast.makeText(getApplicationContext(),getString(R.string.document_not_found), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                database.close();
            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
            }catch (NullPointerException e){
                runOnUiThread(() -> dialog.dismiss());
                Log.e("NullPointerException", ""+e.toString());
            }
            //Toast.makeText(getApplicationContext(), "VillageNames Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void truncateDatabase_Docs(){
        //dialog1.setProgress(20);
        openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this);
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

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.discard_changes))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.super.onBackPressed();
                    Intent i = new Intent(RI_Field_Report_Caste_sc_st_certi_Parameters_Kan.this, RI_Field_Report_FirstScreen.class);
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

