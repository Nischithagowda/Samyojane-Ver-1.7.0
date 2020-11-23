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
import android.graphics.Paint;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SuppressLint("Registered")
public class New_Request_Caste_Income_Parameters_Kan extends AppCompatActivity {

        TextView applicant_infor;
        Spinner spCategory, spReasons, spRejectReason;
        String strReason, strRejectionReason, strCategory, strSearchCaste;
        int posReason, posRejectionReason;
        TextView tvHobli, tvTaluk, tvVA_Name, tvServiceName;
        String district, taluk, hobli, VA_Name,VA_Circle_Name, applicant_Id, rationCardNo, aadharNo, mobileNo, address1;
        String district_Code, taluk_Code, hobli_Code, va_Circle_Code;
        String eng_certi, GSC_FirstPart;
        ArrayAdapter<CharSequence> adapter_rejection_reason, adapter_reason;
        Button btnCamera, btnSave, btnBack;
        private static final int CAMERA_REQUEST = 1;
        byte[] imageInByte;
        static String store=null;
        static String getString=null;
        ImageView imageView;
        EditText tvIncome, tvRemarks;
        int income_len, income_Value;
        String strIncome, strRemarks;
        SQLiteOpenHelper openHelper;
        SQLiteDatabase database;
        GPSTracker gpsTracker;
        double latitude, longitude;
        String item_position;
        String strSearchVillageName, strSearchServiceName, town_Name, ward_Name, town_code, ward_code, option_Flag;
        String villageCode, serviceCode,habitationCode;
        TableRow lRejection;
        RadioGroup radiogroup, radioGroup1;
        RadioButton radioButton1, radioButton2;
        RadioButton radioButton11, radioButton22;
        String option, option1;
        Set_and_Get_Service_Parameter set_and_get_service_parameter;
        SqlLiteOpenHelper_Class_Kan sqlLiteOpenHelper_class_kan;
        int reason_Code_1, reason_Code_2;
        AutoCompleteTextView autoSearchCaste;
        int getCatCode=0, getCasteCode=0;
        ProgressDialog dialog;
        String service_name, village_name;
        String amount, caste_code, caste_name, category_code, category_name;
        boolean return_Value;
        InputMethodManager imm;
        InputMethodSubtype ims;

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
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @SuppressLint({"ClickableViewAccessibility", "HardwareIds"})
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_request_caste_income_parametrs);

            strSearchCaste = getString(R.string.select_caste_spinner);
            option = getString(R.string.yes);
            option1 = getString(R.string.yes);

            tvTaluk = findViewById(R.id.taluk);
            tvHobli = findViewById(R.id.hobli);
            tvVA_Name = findViewById(R.id.VA_name);

            autoSearchCaste = findViewById(R.id.autoSearchCaste);
            spCategory = findViewById(R.id.spCategory);
            spReasons = findViewById(R.id.spReasons);
            spRejectReason = findViewById(R.id.spRejectReason);
            btnCamera = findViewById(R.id.btnCamera);
            btnSave = findViewById(R.id.btnSave);
            tvIncome = findViewById(R.id.tvIncome);
            tvRemarks = findViewById(R.id.tvRemarks);
            imageView = findViewById(R.id.store_image);
            btnBack = findViewById(R.id.btnBack);
            tvServiceName = findViewById(R.id.tvServiceName);
            radiogroup = findViewById(R.id.radioGroup);
            radioGroup1 = findViewById(R.id.radioGroup1);
            radioButton1 = findViewById(R.id.radioButton1);
            radioButton2 = findViewById(R.id.radioButton2);
            radioButton11 = findViewById(R.id.radioButton11);
            radioButton22 = findViewById(R.id.radioButton22);
            lRejection = findViewById(R.id.lRejection);

            applicant_infor= findViewById(R.id.applicant_Information);
            applicant_infor.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(this);
            sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();

            imageView.setVisibility(View.GONE);
            spReasons.setVisibility(View.GONE);
            lRejection.setVisibility(View.GONE);

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
            villageCode = i.getStringExtra("villageCode");
            habitationCode = i.getStringExtra("habitationCode");
            serviceCode = i.getStringExtra("serviceCode");
            service_name = i.getStringExtra("strSearchServiceName");
            village_name = i.getStringExtra("strSearchVillageName");
            eng_certi = i.getStringExtra("eng_certi");
            town_code = i.getStringExtra("town_code");
            town_Name = i.getStringExtra("town_Name");
            ward_code = i.getStringExtra("ward_code");
            ward_Name = i.getStringExtra("ward_Name");
            option_Flag = i.getStringExtra("option_Flag");

            Log.d("New_Request_Sec_Con..", ""+district_Code);
            Log.d("New_Request_Sec_Con..", ""+taluk_Code);
            Log.d("New_Request_Sec_Con..", ""+hobli_Code);
            Log.d("New_Request_Sec_Con..", ""+va_Circle_Code);
            Log.d("Item_Position", ""+item_position);
            Log.d("Village_NameCasteIncome", ""+village_name);
            Log.d("Service_NameCasteIncome", ""+service_name);
            Log.d("villageCodeCasteIncome", ""+villageCode);
            Log.d("HabitationCodeCaste", ""+habitationCode);
            Log.d("serviceCodeCasteIncome", ""+serviceCode);
            Log.d("eng_certi",""+ eng_certi);
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

            autoSearchCaste.setVisibility(View.GONE);
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

            spCategory.setOnTouchListener((v, event) -> {
                autoSearchCaste.setText("");
                autoSearchCaste.setError(null);
                return false;
            });

            tvTaluk.setText(taluk);
            tvHobli.setText(hobli);
            tvVA_Name.setText(VA_Name);
            tvServiceName.setText(strSearchServiceName);

            sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(New_Request_Caste_Income_Parameters_Kan.this);
            sqlLiteOpenHelper_class_kan.open_Reasons_Master_Tbl();

            radiogroup.setOnCheckedChangeListener((group, checkedId) -> {
                // find which radio button is selected
                if (checkedId == R.id.radioButton1) {
                    //Toast.makeText(getApplicationContext(), "choice: male", Toast.LENGTH_SHORT).show();
                    option = "YES";
                    spReasons.setVisibility(View.GONE);
                    spReasons.setSelection(0);
                } else if (checkedId == R.id.radioButton2) {
                    option = "NO";
                    spReasons.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "choice: Female", Toast.LENGTH_SHORT).show();
                }
            });

            radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
                // find which radio button is selected
                if (checkedId == R.id.radioButton11) {
                    //Toast.makeText(getApplicationContext(), "choice: male", Toast.LENGTH_SHORT).show();
                    option1 = getString(R.string.yes);
                    lRejection.setVisibility(View.GONE);
                    spRejectReason.setSelection(0);
                } else if (checkedId == R.id.radioButton22) {
                    option1 = getString(R.string.no);
                    lRejection.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "choice: Female", Toast.LENGTH_SHORT).show();
                }
            });

            adapter_reason = ArrayAdapter.createFromResource(this, R.array.Reason_array, R.layout.spinner_item_color);
            adapter_reason.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spReasons.setAdapter(adapter_reason);

            adapter_rejection_reason = ArrayAdapter.createFromResource(this, R.array.RejectionReason, R.layout.spinner_item_color);
            adapter_rejection_reason.setDropDownViewResource(R.layout.spinner_item_dropdown);
            spRejectReason.setAdapter(adapter_rejection_reason);

            openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_Caste_Income_Parameters_Kan.this);
            database = openHelper.getWritableDatabase();

            if(serviceCode.equals("6")) {
                GetCategory();
                spCategory.setOnItemSelectedListener(new GetCategorySelected());

                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_Caste_Income_Parameters_Kan.this);
                database = openHelper.getWritableDatabase();
                @SuppressLint("Recycle")
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
                }
                sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                category_name = sqlLiteOpenHelper_class_kan.GetCategory_BY_Code(Integer.parseInt(category_code));
                caste_name = sqlLiteOpenHelper_class_kan.GetCaste_BY_Code(Integer.parseInt(category_code), Integer.parseInt(caste_code));

                Log.d("get_Value", ""+category_name+""+caste_name);

                tvIncome.setText(amount);

                getCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(category_name);
                Log.d("Category_Code_set", ""+ getCatCode);
                GetCaste(Integer.parseInt(category_code));
                //getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(caste_name);
                Log.d("Category_Code_set", ""+ getCasteCode);

                spCategory.setSelection(getCatCode-1);
                autoSearchCaste.setText(caste_name);
                strSearchCaste = caste_name;
            }
            else if(serviceCode.equals("9")){
                GetCategory_OBC();
                spCategory.setOnItemSelectedListener(new GetCategory_OBC_Selected());

                openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_Caste_Income_Parameters_Kan.this);
                database = openHelper.getWritableDatabase();
                @SuppressLint("Recycle")
                Cursor cursor=database.rawQuery("SELECT * FROM "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME
                        +" where "+ DataBaseHelperClass_btnDownload_ServiceTranTable.Service_Code+"='"+serviceCode+"'"+" and "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No+"="+applicant_Id, null);
                if(cursor.getCount()>0) {
                    if(cursor.moveToFirst()){
                        category_code = "9";
                        caste_code = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.SCOT_caste_app));
                        amount = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.SCOT_annual_income));
                        GSC_FirstPart = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_ServiceTranTable.ST_GSCFirstPart));
                        Log.d("value1", ""+category_code+" "+caste_code+" "+amount);
                    }
                }
                sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                category_name = "OBC (Central)";
                caste_name = sqlLiteOpenHelper_class_kan.GetCaste_OBC_BY_Code(Integer.parseInt(caste_code));

                Log.d("get_Value", ""+category_name+""+caste_name);

                tvIncome.setText(amount);

                getCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(category_name);
                Log.d("Category_Code", ""+ getCatCode);
                GetCaste_OBC(Integer.parseInt(caste_code));
                //getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode_OBC(caste_name);
                Log.d("Category_Code_OBC", ""+ getCasteCode);

                spCategory.setSelection(1);
                autoSearchCaste.setText(caste_name);
                strSearchCaste = caste_name;
            }

            gpsTracker = new GPSTracker(getApplicationContext(), this);

            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
                Log.d("Location", latitude+""+longitude);
            } else {
                //gpsTracker.showSettingsAlert();
                Toast.makeText(getApplicationContext(), getString(R.string.switch_on_gps), Toast.LENGTH_SHORT).show();
            }

            spReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    strReason = String.valueOf(spReasons.getSelectedItem());
                    posReason = position;
                    sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(New_Request_Caste_Income_Parameters_Kan.this);
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

            spRejectReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    strRejectionReason = String.valueOf(spRejectReason.getSelectedItem());
                    posRejectionReason = position;
                    sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan(New_Request_Caste_Income_Parameters_Kan.this);
                    sqlLiteOpenHelper_class_kan.open_Reasons_Master_Tbl();
                    reason_Code_2 = sqlLiteOpenHelper_class_kan.Get_CertificateRejectionReason(strRejectionReason, getString(R.string.reasons_tbl_reason_name));
                    Log.d("Number", ""+ reason_Code_2);
                    Log.d("Item_Position", ""+ position);
                    Log.d("Spinner_Value", ""+strRejectionReason);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnCamera.setOnClickListener(v -> {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            });
            btnSave.setOnClickListener(v -> {

                if (gpsTracker.canGetLocation()) {
                    latitude = gpsTracker.getLatitude();
                    longitude = gpsTracker.getLongitude();
                    Log.d("Location", ""+latitude+""+longitude);
                } else {
                    //gpsTracker.showSettingsAlert();
                    Toast.makeText(getApplicationContext(), getString(R.string.switch_on_gps), Toast.LENGTH_SHORT).show();
                }

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
                }

                if(latitude!=0.0 && longitude!=0.0) {

                    if(!strCategory.equals(getString(R.string.select_category_spinner))){
                        if(!strSearchCaste.equals(getString(R.string.select_caste_spinner))) {
                            if (getCasteCode != 0) {
                                if (option.equals(getString(R.string.no))) {
                                    if (!strReason.equals(getString(R.string.reason_spinner))) {
                                        if (TextUtils.isEmpty(strIncome)) {
                                            tvIncome.setError(getString(R.string.field_canno_null));
                                        } else {

                                            income_len = strIncome.length();
                                            income_Value = Integer.parseInt(strIncome);
                                            Log.d("Income value", ""+strIncome+", Length: "+income_len+", Value: "+ income_Value);

                                            if (income_len >= 4 && income_Value>=1000) {
                                                if (option1.equals(getString(R.string.no))) {
                                                    if (!strRejectionReason.equals(getString(R.string.reason_spinner))) {
                                                        StoreData_in_DB();
                                                    } else {
                                                        ((TextView) spRejectReason.getSelectedView()).setError(getString(R.string.reason_for_rejection));
                                                        Toast.makeText(getApplicationContext(), getString(R.string.reason_for_rejection), Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    if (TextUtils.isEmpty(strRemarks)) {
                                                        tvRemarks.setError(getString(R.string.field_canno_null));
                                                    } else {
                                                        StoreData_in_DB();
                                                    }
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
                                            if (option1.equals(getString(R.string.no))) {
                                                if (!strRejectionReason.equals(getString(R.string.reason_spinner))) {
                                                    if (TextUtils.isEmpty(strRemarks)) {
                                                        tvRemarks.setError(getString(R.string.field_canno_null));
                                                    } else {
                                                        StoreData_in_DB();
                                                    }
                                                } else {
                                                    ((TextView) spRejectReason.getSelectedView()).setError(getString(R.string.reason_for_rejection));
                                                    Toast.makeText(getApplicationContext(), getString(R.string.reason_for_rejection), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                if (TextUtils.isEmpty(strRemarks)) {
                                                    tvRemarks.setError(getString(R.string.field_canno_null));
                                                } else {
                                                    StoreData_in_DB();
                                                }
                                            }
                                        } else {
                                            tvIncome.setError(getString(R.string.incorrect_value));
                                        }
                                    }
                                }
                            }else {
                                autoSearchCaste.setError(getString(R.string.invalid_caste));
                            }
                        }
                        else {
                            autoSearchCaste.setError(getString(R.string.select_caste));
                        }
                    }
                    else {
                        ((TextView)spCategory.getSelectedView()).setError(getString(R.string.select_category));
                        Toast.makeText(getApplicationContext(), getString(R.string.select_category), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    runOnUiThread(() -> {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(New_Request_Caste_Income_Parameters_Kan.this);
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

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.discard_changes))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                    New_Request_Caste_Income_Parameters_Kan.super.onBackPressed();
                    Intent i = new Intent(New_Request_Caste_Income_Parameters_Kan.this, New_Request_FirstScreen.class);
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

    public void truncateDatabase_Docs(){
            //dialog1.setProgress(20);
            openHelper = new DataBaseHelperClass_btnDownload_Docs(New_Request_Caste_Income_Parameters_Kan.this);
            database = openHelper.getWritableDatabase();
            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_Docs.TABLE_NAME, null);
            if(cursor.getCount()>0) {
                database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_Docs.TABLE_NAME);
                Log.d("Database", "Down_Docs Database Truncated");
            }

    }

        public String StoreData_in_DB(){
            String str;
            dialog.show();
            btnCamera.setError(null);

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

            openHelper = new DataBaseHelperClass_btnDownload_ServiceTranTable(New_Request_Caste_Income_Parameters_Kan.this);
            database = openHelper.getWritableDatabase();

            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " where "
                    + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id, null);
            if (cursor.getCount() > 0) {

                database.execSQL("update "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" set "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + "=1 where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "="+ applicant_Id);

                database.execSQL("update " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1 + " set "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.VA_Accepts_Applicant_information+"='"+"NO"+"',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Category + "="+ getCatCode + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Applicant_Caste + "=" + getCasteCode + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Belongs_Creamy_Layer_6 + "='" + option + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Creamy_Layer_6 + "=" + reason_Code_1 + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Annual_Income + "='" + strIncome + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Photo + "='" + store + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.vLat + "=" + latitude + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.vLong + "=" + longitude + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Can_Certificate_Given + "='" + option1 + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Reason_for_Rejection + "=" + reason_Code_2 + ","
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.Remarks + "='" + strRemarks + "',"
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + "=1" + " where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "=" + applicant_Id);

                Log.d("Database", "ServiceParameters Database Updated");
                Toast.makeText(getApplicationContext(), getString(R.string.updated_successfully), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(New_Request_Caste_Income_Parameters_Kan.this, New_Request_FirstScreen.class);
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
                i.putExtra("habitationCode", habitationCode);
                i.putExtra("option_Flag", option_Flag);
                i.putExtra("town_Name", town_Name);
                i.putExtra("town_code", town_code);
                i.putExtra("ward_Name", ward_Name);
                i.putExtra("ward_code", ward_code);
                startActivity(i);
                finish();
                dialog.dismiss();
                truncateDatabase_Docs();
                str="Success";
            } else {
                set_and_get_service_parameter = new Set_and_Get_Service_Parameter();
                set_and_get_service_parameter.setDistrict_Code(district_Code);
                set_and_get_service_parameter.setTaluk_Code(taluk_Code);
                set_and_get_service_parameter.setHobli_Code(hobli_Code);
                set_and_get_service_parameter.setVa_Circle_Code(va_Circle_Code);
                set_and_get_service_parameter.setVillage_Code(String.valueOf(villageCode));
                set_and_get_service_parameter.setHabitation_code(habitationCode);
                set_and_get_service_parameter.setTown_Code(town_code);
                set_and_get_service_parameter.setWard_Code(ward_code);
                set_and_get_service_parameter.setService_Code(String.valueOf(serviceCode));
                set_and_get_service_parameter.setRD_No(applicant_Id);
                set_and_get_service_parameter.setEng_Certify(eng_certi);
                set_and_get_service_parameter.setGSC_First_Part(GSC_FirstPart);
                set_and_get_service_parameter.setAPP_Category_6(String.valueOf(getCatCode));
                set_and_get_service_parameter.setApp_Caste_6(String.valueOf(getCasteCode));
                set_and_get_service_parameter.setRbOption_6(option);
                set_and_get_service_parameter.setSpReason_6(String.valueOf(reason_Code_1));
                set_and_get_service_parameter.setRbIssue_Cert(option1);
                set_and_get_service_parameter.setSpRejectionReason(String.valueOf(reason_Code_2));
                set_and_get_service_parameter.setAnnual_Income(strIncome);
                set_and_get_service_parameter.setRemarks_8(strRemarks);
                set_and_get_service_parameter.setPhoto(store);
                set_and_get_service_parameter.setvLat(String.valueOf(latitude));
                set_and_get_service_parameter.setvLong(String.valueOf(longitude));

                database.execSQL("update "+ DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" set "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + "=1 where "
                        + DataBaseHelperClass_btnDownload_ServiceTranTable.RD_No + "="+ applicant_Id);

                database.execSQL("insert into " + DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME_1
                        + "(ST_district_code, ST_taluk_code, ST_hobli_code, ST_va_Circle_Code, ST_village_code, ST_habitation_code, ST_town_code, ST_ward_no, ST_facility_code, ST_GSC_No, ST_Eng_Certificate, ST_GSCFirstPart," +
                        " VA_Accepts_Applicant_information, Applicant_Category, Applicant_Caste, Belongs_Creamy_Layer_6, Reason_for_Creamy_Layer_6, Annual_Income, Remarks," +
                        " Photo, vLat, vLong, Can_Certificate_Given, Reason_for_Rejection, DataUpdateFlag)" +
                        " values ("
                        + set_and_get_service_parameter.getDistrict_Code() + ","
                        + set_and_get_service_parameter.getTaluk_Code() + ","
                        + set_and_get_service_parameter.getHobli_Code() + ","
                        + set_and_get_service_parameter.getVa_Circle_Code() + ","
                        + set_and_get_service_parameter.getVillage_Code()+","
                        + set_and_get_service_parameter.getHabitation_code()+","
                        + set_and_get_service_parameter.getTown_Code() + ","
                        + set_and_get_service_parameter.getWard_Code() + ","
                        + set_and_get_service_parameter.getService_Code() + ","
                        + set_and_get_service_parameter.getRD_No() + ",'"
                        + set_and_get_service_parameter.getEng_Certify() + "',"
                        + set_and_get_service_parameter.getGSC_First_Part() + ",'"
                        + "NO" + "',"
                        + set_and_get_service_parameter.getAPP_Category_6()+","
                        + set_and_get_service_parameter.getApp_Caste_6() + ",'"
                        + set_and_get_service_parameter.getRbOption_6() + "',"
                        + set_and_get_service_parameter.getSpReason_6() + ",'"
                        + set_and_get_service_parameter.getAnnual_Income()
                        + set_and_get_service_parameter.getRemarks_8() + "','"
                        + set_and_get_service_parameter.getPhoto() + "',"
                        + set_and_get_service_parameter.getvLat()+ ","
                        + set_and_get_service_parameter.getvLong() + ",'"
                        + set_and_get_service_parameter.getRbIssue_Cert() + "',"
                        + set_and_get_service_parameter.getSpRejectionReason()
                        + ", 1)");
                Log.d("Database", "ServiceParameters Database Inserted");
                Toast.makeText(getApplicationContext(), getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(New_Request_Caste_Income_Parameters_Kan.this, New_Request_FirstScreen.class);
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
                dialog.dismiss();
                truncateDatabase_Docs();
                str="success";
            }
            return str;
        }

        @SuppressLint("ShowToast")
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
                Toast.makeText(getApplicationContext(), getString(R.string.error_creating_table), Toast.LENGTH_LONG);
            }
        }

        @SuppressLint("ShowToast")
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
                getCasteCode = sqlLiteOpenHelper_class_kan.GetCasteCode(strSearchCaste, num);
                Log.d("Selected_casteCode", ""+ getCasteCode);
            });
        }

        public class GetCategorySelected implements AdapterView.OnItemSelectedListener {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCategory = ((SpinnerObject) spCategory.getSelectedItem()).getValue();
                Log.d("Selected_Item1", ""+strCategory);
                sqlLiteOpenHelper_class_kan = new SqlLiteOpenHelper_Class_Kan();
                sqlLiteOpenHelper_class_kan.open_Cat_Caste_Tbl();
                getCatCode = sqlLiteOpenHelper_class_kan.GetCategoryCode(strCategory);
                Log.d("Category_Code1", ""+ getCatCode);
                if (!strCategory.equals(getString(R.string.select_category_spinner))) {
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
                    autoSearchCaste.setVisibility(View.VISIBLE);
                    GetCaste_OBC(getCatCode);
                }
                else {
                    autoSearchCaste.setVisibility(View.GONE);
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
                    getString = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                    //Log.e("output before conversion", Arrays.toString(imageInByte));
                    store = getString;
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(yourImage);
                    btnCamera.setError(null);
                    Log.d("Image in bytes", "Image Captured");
                }
            }
        }

        @Override
        public void onBackPressed() {
            buildAlertMessageGoingBack();
        }

    }