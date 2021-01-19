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
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
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
import android.widget.Button;
import android.widget.EditText;
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

public class RI_Field_Report_Resident_Parameters extends AppCompatActivity {

    HashMap<String, String> hashMap_Down_Docs;

    TextView tvHobli, tvTaluk, tvVA_Name, tvServiceName, tv_V_T_Name, txt_ReportNo;
    String RI_Name,district, taluk, hobli, VA_Circle_Name, VA_Name;
    String district_Code, taluk_Code, hobli_Code, va_Circle_Code, applicant_Id, applicant_name;
    String village_name, service_name;
    String villageCode, serviceCode, habitationCode, town_Name, ward_Name, town_code, ward_code, option_Flag;
    TextView txt_raiseLoc, title, RI_Recommendation, ApplicantID, ApplicantName, ResideAtStatedAddress, PlaceMatch, Purpose, ReservationGiven, ReasonForRejection, TotalYears, Remarks;
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
    RadioButton radioButton1, radioButton11, radioButton2, radioButton22, radioButton3, radioButton33, radioButton4, radioButton44;
    Spinner spPurpose,spRejectReason1, spMonth;
    String option, option1, option2, option3;
    TableRow lReservationGiven, lRejectionReason, lRejection, layoutMonth;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    String appID, appName, resideAtStatedAddress, appPlaceMatch, appPurpose, appReservationGiven, appRejectionReason, year, month, remarks;
    ArrayAdapter<CharSequence> adapter_purpose, adapter_rejection_reason, adapter_Month;
    String strPurpose, strRejectionReason1;
    int reason_Code_3;
    int Total_No_Years_10, NO_Months_10,posPurpose, codePurpose, posRejectionReason1;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class, sqlLiteOpenHelper_class1;
    GPSTracker gpsTracker;
    double latitude, longitude;
    ProgressDialog dialog;
    Button btnSave, btnBack, btnDownDocs, btnViewDocs;
    String photo;
    String getPurposeByCode;
    TextView tvVillageName, tvAppAddress, tvAppPlace, tvPurpose, tvTotalYears, hide, tvRemarksColor;
    String strIncome, strRemarks, strMonth, strYear;
    int reason_Code_1;
    EditText etYear, tvRemarks;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;
    String raisedLoc, eng_certi, GSC_FirstPart;

    final int min = 1111;
    final int max = 9999;
    int random;
    String report_no;

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

    @SuppressLint({"HardwareIds", "SetTextI18n", "ClickableViewAccessibility"})
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_field_report_resident_parameters);

        option = getString(R.string.yes);
        option1 = getString(R.string.yes);
        option2 = getString(R.string.yes);
        option3 = getString(R.string.yes);

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
        TotalYears = findViewById(R.id.TotalYears);
        ResideAtStatedAddress = findViewById(R.id.ResideAtStatedAddress);
        PlaceMatch = findViewById(R.id.placeMatch);
        Purpose = findViewById(R.id.Purpose);
        ReservationGiven = findViewById(R.id.ReservationGiven);
        ReasonForRejection = findViewById(R.id.ReasonForRejection);
        radioGroup1 = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
        radioGroup3 = findViewById(R.id.radioGroup3);
        radioGroup4 = findViewById(R.id.radioGroup4);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton11 = findViewById(R.id.radioButton11);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton22 = findViewById(R.id.radioButton22);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton33 = findViewById(R.id.radioButton33);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton44 = findViewById(R.id.radioButton44);
        spPurpose = findViewById(R.id.spPurpose);
        spRejectReason1 = findViewById(R.id.spRejectReason1);
        lRejection = findViewById(R.id.lRejection);
        lReservationGiven = findViewById(R.id.lReservationGiven);
        lRejectionReason = findViewById(R.id.lRejectionReason);
        btnSave = findViewById(R.id.btnSave);
        tvAppAddress = findViewById(R.id.tvAppAddress);
        tvAppPlace = findViewById(R.id.tvAppPlace);
        tvPurpose= findViewById(R.id.tvPurpose);
        tvTotalYears = findViewById(R.id.tvTotalYears);
        etYear = findViewById(R.id.etYear);
        spMonth = findViewById(R.id.spMonth);
        layoutMonth = findViewById(R.id.layoutMonth);
        hide = findViewById(R.id.hide);
        tv_V_T_Name = findViewById(R.id.tv_V_T_Name);
        Remarks = findViewById(R.id.Remarks);
        tvRemarks = findViewById(R.id.tvRemarks);
        tvRemarksColor = findViewById(R.id.tvRemarksColor);
        btnDownDocs = findViewById(R.id.btnDownDocs);
        btnViewDocs = findViewById(R.id.btnViewDocs);
        txt_ReportNo = findViewById(R.id.txt_ReportNo);

        hide.setVisibility(View.GONE);
        Remarks.setVisibility(View.VISIBLE);
        tvRemarks.setVisibility(View.GONE);
        btnViewDocs.setVisibility(View.GONE);

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
        etYear.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)}); // 14 is max digits

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

        Log.d("RI_Resident_Parameters", ""+district_Code);
        Log.d("RI_Resident_Parameters", ""+taluk_Code);
        Log.d("RI_Resident_Parameters",""+hobli_Code+" VA_Name :"+VA_Name+" VillageName :"+village_name+"ServiceName:"+service_name);
        Log.d("RI_Resident_Parameters", ""+applicant_Id);
        Log.d("RI_Resident_Parameters", ""+habitationCode);
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

        etYear.setVisibility(View.GONE);
        layoutMonth.setVisibility(View.GONE);
        spPurpose.setVisibility(View.GONE);
        radioGroup1.setVisibility(View.GONE);
        radioGroup2.setVisibility(View.GONE);
        TotalYears.setVisibility(View.VISIBLE);
        Purpose.setVisibility(View.VISIBLE);
        ResideAtStatedAddress.setVisibility(View.VISIBLE);
        PlaceMatch.setVisibility(View.VISIBLE);
        lRejection.setVisibility(View.GONE);
        spRejectReason1.setVisibility(View.GONE);

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

        etYear.setOnTouchListener((v, event) -> {
            etYear.setError(null);
            return false;
        });

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1+" where "
                +DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No+"="+applicant_Id, null);
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                remarks = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Remarks));
                eng_certi = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_Eng_Certificate));
                GSC_FirstPart = cursor.getString(cursor.getColumnIndex(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.ST_GSCFirstPart));
                raisedLoc = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Raised_Location));
                appID = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No));
                appName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Applicant_Name));
                year = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Total_No_Years_10));
                month = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.NO_Months_10));
                resideAtStatedAddress = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reside_At_Stated_Address_10));
                appPlaceMatch = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Place_Match_With_RationCard_10));
                appPurpose = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Pur_for_Cert_Code_10));
                appReservationGiven = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Can_Certificate_Given));
                appRejectionReason = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reason_for_Rejection));
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
        Log.d("dbValues", "resideAtStatedAddress "+resideAtStatedAddress);
        Log.d("dbValues", "appPlaceMatch "+appPlaceMatch);
        Log.d("dbValues", "appPurpose "+appPurpose);
        Log.d("dbValues", "appReservationGiven "+appReservationGiven);
        Log.d("dbValues", "appRejectionReason "+appRejectionReason);
        Log.d("dbValues", "remarks "+remarks);

        if(!appReservationGiven.equals("YES")){
            lRejectionReason.setVisibility(View.VISIBLE);
        }
        else {
            lRejectionReason.setVisibility(View.GONE);
        }

        sqlLiteOpenHelper_class1 = new SqlLiteOpenHelper_Class(this);
        sqlLiteOpenHelper_class1.open_Reasons_Master_Tbl();
        getPurposeByCode = sqlLiteOpenHelper_class1.Get_Purpose_BY_Code(Integer.parseInt(appPurpose), getString(R.string.reasons_tbl_purpose_name));

        ApplicantID.setText(appID);
        ApplicantName.setText(appName);
        TotalYears.setText(year+" "+getString(R.string.year_name)+", "+month+" "+getString(R.string.month_name));
        ResideAtStatedAddress.setText(resideAtStatedAddress);
        PlaceMatch.setText(appPlaceMatch);
        Purpose.setText(getPurposeByCode);
        ReservationGiven.setText(appReservationGiven);
        ReasonForRejection.setText(appRejectionReason);
        Remarks.setText(remarks);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(RI_Field_Report_Resident_Parameters.this);
        sqlLiteOpenHelper_class.open_Reasons_Master_Tbl();

        adapter_purpose = ArrayAdapter.createFromResource(this, R.array.Purpose_of_certificate, R.layout.spinner_item_color);
        adapter_purpose.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spPurpose.setAdapter(adapter_purpose);

        adapter_rejection_reason = ArrayAdapter.createFromResource(this, R.array.RejectionReason, R.layout.spinner_item_color);
        adapter_rejection_reason.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spRejectReason1.setAdapter(adapter_rejection_reason);

        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.radioButton1){
                option = getString(R.string.yes);
            }
            else if(checkedId == R.id.radioButton11) {
                option = getString(R.string.no);
            }
            Log.d("option", option);
        });

        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.radioButton2){
                option1 = getString(R.string.yes);
            }
            else if(checkedId == R.id.radioButton22) {
                option1 = getString(R.string.no);
            }
            Log.d("option1", option1);
        });

        radioGroup3.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton3) {
                option2 = getString(R.string.yes);
                TotalYears.setVisibility(View.VISIBLE);
                Purpose.setVisibility(View.VISIBLE);
                ResideAtStatedAddress.setVisibility(View.VISIBLE);
                PlaceMatch.setVisibility(View.VISIBLE);
                lReservationGiven.setVisibility(View.VISIBLE);
                lRejectionReason.setVisibility(View.VISIBLE);
                etYear.setVisibility(View.GONE);
                layoutMonth.setVisibility(View.GONE);
                spPurpose.setVisibility(View.GONE);
                spPurpose.setSelection(0);
                spMonth.setVisibility(View.GONE);
                spMonth.setSelection(0);
                radioGroup1.setVisibility(View.GONE);
                radioGroup2.setVisibility(View.GONE);
                Remarks.setVisibility(View.VISIBLE);
                tvRemarks.setVisibility(View.GONE);
                if(!appReservationGiven.equals("YES")){
                    lRejectionReason.setVisibility(View.VISIBLE);
                }
                else {
                    lRejectionReason.setVisibility(View.GONE);
                }
                tvAppAddress.setTextColor(Color.parseColor("#000000"));
                tvAppPlace.setTextColor(Color.parseColor("#000000"));
                tvPurpose.setTextColor(Color.parseColor("#000000"));
                tvTotalYears.setTextColor(Color.parseColor("#000000"));
                tvRemarksColor.setTextColor(Color.parseColor("#000000"));
            } else if (checkedId == R.id.radioButton33) {
                option2 = getString(R.string.no);
                TotalYears.setVisibility(View.GONE);
                Purpose.setVisibility(View.GONE);
                ResideAtStatedAddress.setVisibility(View.GONE);
                PlaceMatch.setVisibility(View.GONE);
                lReservationGiven.setVisibility(View.GONE);
                lRejectionReason.setVisibility(View.GONE);
                etYear.setVisibility(View.VISIBLE);
                layoutMonth.setVisibility(View.VISIBLE);
                spMonth.setVisibility(View.VISIBLE);
                spPurpose.setVisibility(View.VISIBLE);
                radioGroup1.setVisibility(View.VISIBLE);
                radioGroup2.setVisibility(View.VISIBLE);
                Remarks.setVisibility(View.GONE);
                tvRemarks.setVisibility(View.VISIBLE);

                tvAppAddress.setTextColor(Color.parseColor("#0000ff"));
                tvAppPlace.setTextColor(Color.parseColor("#0000ff"));
                tvPurpose.setTextColor(Color.parseColor("#0000ff"));
                tvTotalYears.setTextColor(Color.parseColor("#0000ff"));
                tvRemarksColor.setTextColor(Color.parseColor("#0000ff"));
            }
        });

        radioGroup4.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton4) {
                option3 = getString(R.string.yes);
                lRejection.setVisibility(View.GONE);
                spRejectReason1.setVisibility(View.GONE);
                spRejectReason1.setSelection(0);
            } else if (checkedId == R.id.radioButton44) {
                option3 = getString(R.string.no);
                lRejection.setVisibility(View.VISIBLE);
                spRejectReason1.setVisibility(View.VISIBLE);
            }
        });

        adapter_Month = ArrayAdapter.createFromResource(this, R.array.month_array, R.layout.spinner_item_color);
        adapter_Month.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spMonth.setAdapter(adapter_Month);

        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strMonth = String.valueOf(spMonth.getSelectedItem());
                Log.d("Spinner_Value", ""+ strMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strPurpose = String.valueOf(spPurpose.getSelectedItem());
                posPurpose = position;
                codePurpose = sqlLiteOpenHelper_class.Get_Purpose(strPurpose, getString(R.string.reasons_tbl_purpose_name));
                Log.d("Number", ""+ codePurpose);
                Log.d("Item_Position", ""+ position);
                Log.d("Spinner_Value", ""+strPurpose);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spRejectReason1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strRejectionReason1 = String.valueOf(spRejectReason1.getSelectedItem());
                posRejectionReason1 = position;
                sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(RI_Field_Report_Resident_Parameters.this);
                sqlLiteOpenHelper_class.open_Reasons_Master_Tbl();
                reason_Code_3 = sqlLiteOpenHelper_class.Get_CertificateRejectionReason(strRejectionReason1, getString(R.string.reasons_tbl_reason_name));
                Log.d("Number", ""+ reason_Code_3);
                Log.d("Item_Position", ""+ position);
                Log.d("Spinner_Value", ""+strRejectionReason1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDownDocs.setOnClickListener(v -> {
            if (isNetworkAvailable()) {

                dialog.show();
                //dialog1.setProgress(0);

                Log.d("Docs", "Applicant_ID:"+applicant_Id+"\nGSC_FirstPart:"+GSC_FirstPart);
                openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_Resident_Parameters.this);
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
            Intent i12 = new Intent(RI_Field_Report_Resident_Parameters.this, View_Docs.class);
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

            strYear = etYear.getText().toString();
            strRemarks = tvRemarks.getText().toString();
            Log.d("Income value", ""+strRemarks);

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
                    if(!TextUtils.isEmpty(strYear)  && Integer.parseInt(strYear)<=129) {
                        if(!strMonth.equals(getString(R.string.month_spinner))) {
                            if (!strPurpose.equals(getString(R.string.select_spinner))) {
                                if(option3.equals(getString(R.string.yes))){
                                    if (TextUtils.isEmpty(strRemarks)) {
                                        tvRemarks.setError(getString(R.string.field_canno_null));
                                    } else {
                                        StoreData_in_DB_When_Wrong();
                                    }
                                }
                                else {
                                    if(!strRejectionReason1.equals(getString(R.string.reason_spinner))){
                                        if (TextUtils.isEmpty(strRemarks)) {
                                            tvRemarks.setError(getString(R.string.field_canno_null));
                                        } else {
                                            StoreData_in_DB_When_Wrong();
                                        }
                                    }
                                    else {
                                        ((TextView) spRejectReason1.getSelectedView()).setError(getString(R.string.select_reason_for_rejection));
                                        Toast.makeText(getApplicationContext(), getString(R.string.select_reason_for_rejection), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                ((TextView) spPurpose.getSelectedView()).setError(getString(R.string.select_purpose));
                                Toast.makeText(getApplicationContext(), getString(R.string.select_purpose), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            ((TextView) spMonth.getSelectedView()).setError(getString(R.string.select_month));
                            Toast.makeText(getApplicationContext(), getString(R.string.select_month), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        etYear.setError(getString(R.string.field_canno_null));
                    }
                }
                else {
                    if(option3.equals(getString(R.string.yes))){
                        StoreData_in_DB_When_Correct();
                    }
                    else {
                        if(!strRejectionReason1.equals(getString(R.string.reason_spinner))){
                            StoreData_in_DB_When_Correct();
                        }
                        else {
                            ((TextView) spRejectReason1.getSelectedView()).setError(getString(R.string.select_reason_for_rejection));
                            Toast.makeText(getApplicationContext(), getString(R.string.select_reason_for_rejection), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            else {
                runOnUiThread(() -> {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(RI_Field_Report_Resident_Parameters.this);
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

//        FileInputStream fis=null;
//        FileOutputStream fos=null;
//
//        try
//        {
////            fis=new FileInputStream("/data/data/" + getPackageName() + "/databases/Village_Names.db");
////            fos=new FileOutputStream("/sdcard/Samyojane/databases/Village_Names.db");
////
////            fis=new FileInputStream("/data/data/" + getPackageName() + "/databases/FacilityMaster.db");
////            fos=new FileOutputStream("/sdcard/Samyojane/databases/FacilityMaster.db");
//
//            fis=new FileInputStream("/data/data/" + getPackageName() + "/databases/ServiceTranTable.db");
//            fos=new FileOutputStream("/sdcard/Samyojane/databases/ServiceTranTable.db");
//
//            while(true)
//            {
//                int ii=fis.read();
//                if(ii!=-1)
//                {fos.write(ii);}
//                else
//                {break;}
//            }
//            fos.flush();
//            Log.d("DB_dump", "OK");
//            //Toast.makeText(this, "DB dump OK", Toast.LENGTH_LONG).show();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            Log.d("DB_dump", "ERROR");
//            //Toast.makeText(this, "DB dump ERROR", Toast.LENGTH_LONG).show();
//        }
//        finally
//        {
//            try
//            {
//                if (fos != null) {
//                    fos.close();
//                }
//                if (fis != null) {
//                    fis.close();
//                }
//            }
//            catch(IOException ioe)
//            {
//                Log.d("Exception", ioe.getMessage());
//            }
//        }
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

        if(option.equals(getString(R.string.no))){
            option="NO";
        }else if (option.equals(getString(R.string.yes))){
            option = "YES";
        }

        if(option1.equals(getString(R.string.no))){
            option1="NO";
        }else if (option1.equals(getString(R.string.yes))){
            option1 = "YES";
        }

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

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report_Resident_Parameters.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "="+applicant_Id, null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                strRemarks = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Remarks));
                Total_No_Years_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Total_No_Years_10));
                NO_Months_10 = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.NO_Months_10));
                option = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Reside_At_Stated_Address_10));
                option1 = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Place_Match_With_RationCard_10));
                codePurpose = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Pur_for_Cert_Code_10)));
                Log.d("Data_Fetched", "StoreData_in_DB_When_Correct");
            }

            database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " set "
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Category + "=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Caste+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Belongs_Creamy_Layer_6+"=null,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Creamy_Layer_6+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Num_Years_8+"=null,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Father_Category_8+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Father_Caste_8+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Mother_Category_8+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Mother_Caste_8+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Total_No_Years_10+"="+Total_No_Years_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_NO_Months_10+"="+NO_Months_10+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reside_At_Stated_Address_10 + "='" + option + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Place_Match_With_RationCard_10 + "='" + option1 + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Pur_for_Cert_Code_10 + "=" + codePurpose + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Annual_Income+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Remarks+"='"+strRemarks+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLat + "=" + latitude + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLong + "=" + longitude + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Can_Certificate_Given_as_RI + "='" + option3 + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Rejection_as_RI + "=" + reason_Code_3 + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Accepted_VA_information + "='"+option2+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Report_No + "='"+report_no+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag + "=1"
                    + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "="+applicant_Id);

            Log.d("Database", "ServiceParameters Database Updated");
            Toast.makeText(getApplicationContext(), getString(R.string.updated_successfully), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(RI_Field_Report_Resident_Parameters.this, RI_Field_Report_FirstScreen.class);
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

            Log.d("Data", "StoreData_in_DB_When_Correct : Update_success");
            return "Update_success";
        }
        else {
            cursor.close();
            Log.d("Data", "StoreData_in_DB_When_Correct : Update_Failed");
            return "Update_Failed";
        }
    }

    public String StoreData_in_DB_When_Wrong(){

        if(option.equals(getString(R.string.no))){
            option="NO";
        }else if (option.equals(getString(R.string.yes))){
            option = "YES";
        }

        if(option1.equals(getString(R.string.no))){
            option1="NO";
        }else if (option1.equals(getString(R.string.yes))){
            option1 = "YES";
        }

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

        openHelper = new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(RI_Field_Report_Resident_Parameters.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " where "
                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "="+applicant_Id, null);
        if(cursor.getCount()>0){

            database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1 + " set "
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Category+ "=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Applicant_Caste+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Belongs_Creamy_Layer_6+"=null,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Creamy_Layer_6+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Num_Years_8+"=null,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Father_Category_8+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Father_Caste_8+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_App_Mother_Category_8+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_APP_Mother_Caste_8+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Total_No_Years_10+"="+strYear+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_NO_Months_10+"="+strMonth+","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reside_At_Stated_Address_10 + "='" + option + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Place_Match_With_RationCard_10 + "='" + option1 + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Pur_for_Cert_Code_10 + "=" + codePurpose + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Annual_Income+"=0,"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Remarks+"='"+strRemarks+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLat + "=" + latitude + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_vLong + "=" + longitude + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Can_Certificate_Given_as_RI + "='" + option3 + "',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Reason_for_Rejection_as_RI + "=" + reason_Code_3 + ","
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Accepted_VA_information + "='"+option2+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_Report_No + "='"+report_no+"',"
                    + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag + "=1"
                    + " where " + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RD_No + "="+applicant_Id);

            Log.d("Database", "ServiceParameters Database Updated");
            Toast.makeText(getApplicationContext(), getString(R.string.updated_successfully), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(RI_Field_Report_Resident_Parameters.this, RI_Field_Report_FirstScreen.class);
            i.putExtra("applicant_Id", applicant_Id);
            i.putExtra("district_Code", district_Code);
            i.putExtra("taluk_Code", taluk_Code);
            i.putExtra("hobli_Code", hobli_Code);
            i.putExtra("districtCode", district);
            i.putExtra("taluk", taluk);
            i.putExtra("hobli", hobli);
            i.putExtra("va_Circle_Code", va_Circle_Code);
            i.putExtra("VA_Circle_Name", VA_Circle_Name);
            i.putExtra("RI_Name", RI_Name);
            i.putExtra("VA_Name", VA_Name);
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

            Log.d("Data", "StoreData_in_DB_When_Wrong : Update_success");
            return "Update_success";
        }
        else {
            cursor.close();
            Log.d("Data", "StoreData_in_DB_When_Wrong : Update_Failed");
            return "Update_Failed";
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
                openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_Resident_Parameters.this);
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
                    openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_Resident_Parameters.this);
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
        openHelper = new DataBaseHelperClass_btnDownload_Docs(RI_Field_Report_Resident_Parameters.this);
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
                    RI_Field_Report_Resident_Parameters.super.onBackPressed();
                    Intent i = new Intent(RI_Field_Report_Resident_Parameters.this, RI_Field_Report_FirstScreen.class);
                    i.putExtra("applicant_Id", applicant_Id);
                    i.putExtra("district_Code", district_Code);
                    i.putExtra("taluk_Code", taluk_Code);
                    i.putExtra("hobli_Code", hobli_Code);
                    i.putExtra("districtCode", district);
                    i.putExtra("taluk", taluk);
                    i.putExtra("hobli", hobli);
                    i.putExtra("va_Circle_Code", va_Circle_Code);
                    i.putExtra("VA_Circle_Name", VA_Circle_Name);
                    i.putExtra("RI_Name", RI_Name);
                    i.putExtra("VA_Name", VA_Name);
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
