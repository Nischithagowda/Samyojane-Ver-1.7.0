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
import android.text.InputFilter;
import android.text.TextUtils;
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
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_Kshana_Freeze_Member_Details_VA extends AppCompatActivity {

    TextView txtMemberName, txtDistrict, txtTaluk, txtHobli, txtTown, txtWard, txtAddress1, txtAddress2, txtAddress3,
            txtPIN, txtTotalIncome, txtCreamyLayer, txtHOF_Name;
    EditText etFatherHusbanName, etMotherName, etNONExistReason;
    Spinner spBincom,spRelationship_HOF, spNonexistenceReasons;
    Button btnSave, btnUpload;
    TableLayout tl_table, layout_Inactive;
    RadioGroup radioGroup3;
    RadioButton radioButton3, radioButton33;
    String option="NO";
    SQLiteAssetHelper_Masters sqLiteAssetHelper_masters;
    List<AutoCompleteTextBox_Object> objects_bincom = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Relationship = new ArrayList<>();
    ArrayAdapter<AutoCompleteTextBox_Object> adapter, adapter_bincom, adapter_Relationship;
    ArrayAdapter<CharSequence> adapter_Non_Exist;
    Activity activity;
    String bincom_Name=null, hof_relName=null, non_Exist_Reason=null;
    int bincom_Code=0, salutation_Code=0, hof_relaCode=0;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    String member_ID, rc_num;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    int districtCode, talukCode=0, hobliCode=0, townCode=0, wardCode=0, villageCode=99999, habitationCode=255;
    String districtName, talukName, hobliName, townName, wardName;
    String memberName, memberName_Eng, hofName, hofName_Eng;
    String address1, address1_Eng, address2, address2_Eng, address3, address3_Eng, pin, totalIncome, creamyLayer, reasonCode_creamy;
    String other_non_exist_reason;
    ProgressDialog dialog;
    String father_husband_Name, motheName;
    String distCode_ass, talCode_ass, hobCode_ass, uName_get, Updated_MemberID;

    APIInterface apiInterface;

    private InputFilter filter_Kan = (source, start, end, dest, dstart, dend) -> {
        Log.d("Source",""+source);
        String l1 = "abcefgijklmnopqsuvwxyz";
        String l2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String op1 = "~`!@$%^&*()_-''+={}[]:?><.\"\";£€¢¥₩§|×÷¿■□♤♡◇♧°•○●☆▪¤《》¡₹Π℅®©™∆√¶1234567890೧೨೩೪೫೬೭೮೯೦";
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_kshana_freeze_member_details);

        etFatherHusbanName = findViewById(R.id.etFatherHusbanName);
        etMotherName = findViewById(R.id.etMotherName);
        etNONExistReason = findViewById(R.id.etNONExistReason);
        spRelationship_HOF = findViewById(R.id.spRelationship_HOF);
        spBincom = findViewById(R.id.spBincom);
        spNonexistenceReasons = findViewById(R.id.spNonexistenceReasons);
        btnSave = findViewById(R.id.btnSave);
        radioGroup3 =findViewById(R.id.radioGroup3);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton33 = findViewById(R.id.radioButton33);
        btnUpload = findViewById(R.id.btnUpload);
        tl_table = findViewById(R.id.tl_table);
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
        layout_Inactive = findViewById(R.id.layout_Inactive);

        spNonexistenceReasons.setVisibility(View.GONE);
        etNONExistReason.setVisibility(View.GONE);
        tl_table.setVisibility(View.VISIBLE);
        btnUpload.setVisibility(View.GONE);
        layout_Inactive.setVisibility(View.GONE);

        etFatherHusbanName.setFilters(new InputFilter[] { filter_Kan });
        etMotherName.setFilters(new InputFilter[] { filter_Kan });
        //etNONExistReason.setFilters(new InputFilter[] { filter_Kan });

        etFatherHusbanName.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });
        etMotherName.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });
        etNONExistReason.setOnTouchListener((v, event) -> {
            check_English_Key_lang();
            return false;
        });

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Freeze_Member_Details_VA.this);
        sqlLiteOpenHelper_class.open_Reasons_Master_Tbl();

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(E_Kshana_Freeze_Member_Details_VA.this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();

        Intent i = getIntent();
        rc_num = i.getStringExtra("rc_num");
        member_ID = i.getStringExtra("member_ID");
        distCode_ass = i.getStringExtra("distCode_ass");
        talCode_ass = i.getStringExtra("talCode_ass");
        hobCode_ass = i.getStringExtra("hobCode_ass");
        uName_get = i.getStringExtra("uName_get");
        Updated_MemberID = i.getStringExtra("Updated_MemberID");

        Log.d("member_ID",""+member_ID);
        Log.d("rc_num",""+rc_num);
        Log.d("distCode_ass", ""+distCode_ass);
        Log.d("talCode_ass", ""+talCode_ass);
        Log.d("hobCode_ass", ""+hobCode_ass);
        Log.d("uName_get", ""+uName_get);
        Log.d("Updated_MemberID", ""+Updated_MemberID);

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();
        GetBincom();
        GetNon_Exist_Reason();
        GetRelationship_HOF();

        openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(this);
        database = openHelper.getReadableDatabase();

        Cursor cursor2 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                +" where "+DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID+"='"+rc_num+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.RC_Member_ID+"="+member_ID,null);
        if (cursor2.getCount()>0) {
            if (cursor2.moveToNext()) {
                memberName = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Applicant_KName));
                memberName_Eng = cursor2.getString(cursor2.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Applicant_EName));
            }
        } else {
            cursor2.close();
        }

        Cursor cursor1 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                +" where "+DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID+"='"+rc_num+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.RC_Member_ID+"="+Updated_MemberID,null);

        if (cursor1.getCount()>0){
            if (cursor1.moveToNext()){
                Log.d("Loop", "Enter Freeze cursor>0");
                districtCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Dist_Code));
                talukCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Taluk_Code));
                hobliCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Hobli_code));
                townCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Town_Code));
                wardCode = cursor1.getInt(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Ward_No));
                hofName = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_HOF_KName));
                hofName_Eng = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_HOF_EName));
                address1 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress1));
                address1_Eng = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress1));
                address2 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress2));
                address2_Eng = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress2));
                address3 = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KAddress3));
                address3_Eng = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_EAddress3));
                pin = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_PIN));
                totalIncome = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_FamilyIncome_VA));
                creamyLayer = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer));
                reasonCode_creamy = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_CreamyLayer_REPORT_VA));
            }
        } else {
            cursor1.close();
        }

        Cursor cursor3 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                +" where "+DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID+"='"+rc_num+"' and "
                + DataBaseHelperClass_btnDownload_E_Kshana.RC_Member_ID+"="+member_ID,null);

        if (cursor3.getCount()>0) {
            if (cursor3.moveToNext()) {
                hof_relaCode = cursor3.getInt(cursor3.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_RelationWithHOF));
                bincom_Code = cursor3.getInt(cursor3.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_KBinCom));
                father_husband_Name = cursor3.getString(cursor3.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Father_KName));
                motheName = cursor3.getString(cursor3.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Mother_KName));
            }
        } else {
            cursor3.close();
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

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Freeze_Member_Details_VA.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        townName = sqlLiteOpenHelper_class.Get_TownName(districtCode, talukCode, townCode);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Freeze_Member_Details_VA.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        wardName = sqlLiteOpenHelper_class.Get_WardName_one(districtCode, talukCode, townCode, wardCode);

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
        spRelationship_HOF.setSelection(hof_relaCode);
        spBincom.setSelection(bincom_Code);
        etFatherHusbanName.setText(father_husband_Name);
        etMotherName.setText(motheName);

        radioGroup3.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radioButton3) {
                option = "YES";
                spNonexistenceReasons.setVisibility(View.VISIBLE);
                tl_table.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                btnUpload.setVisibility(View.VISIBLE);

            } else if (checkedId == R.id.radioButton33) {
                option = "NO";
                spNonexistenceReasons.setVisibility(View.GONE);
                tl_table.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                btnUpload.setVisibility(View.GONE);
                spNonexistenceReasons.setSelection(0);
                etNONExistReason.setVisibility(View.GONE);
                etNONExistReason.setError(null);
            }
            Log.d("option",""+option);
        });

        spBincom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bincom_Name = ((AutoCompleteTextBox_Object) spBincom.getSelectedItem()).getValue();
                bincom_Code = Integer.parseInt(((AutoCompleteTextBox_Object) spBincom.getSelectedItem()).getId());
                Log.d("Selected_Item", ""+bincom_Name+", ID:"+bincom_Code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNonexistenceReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                non_Exist_Reason = String.valueOf(spNonexistenceReasons.getSelectedItem());
                Log.d("Selected_Reason",""+non_Exist_Reason);
                if (non_Exist_Reason.equals("Other")){
                    etNONExistReason.setVisibility(View.VISIBLE);
                }else {
                    etNONExistReason.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spRelationship_HOF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hof_relName = ((AutoCompleteTextBox_Object) spRelationship_HOF.getSelectedItem()).getValue();
                hof_relaCode = Integer.parseInt(((AutoCompleteTextBox_Object) spRelationship_HOF.getSelectedItem()).getId());
                Log.d("Selected_Item", ""+hof_relName+", ID:"+hof_relaCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(v -> {

            father_husband_Name = etFatherHusbanName.getText().toString();
            motheName = etMotherName.getText().toString();

            if (hof_relaCode != 0){
                if (bincom_Code != 0) {
                    if (!TextUtils.isEmpty(father_husband_Name)) {
                        if (!TextUtils.isEmpty(motheName)) {
                            Intent intent = new Intent(E_Kshana_Freeze_Member_Details_VA.this, E_Kshana_Freeze_Member_Details_VA_Second.class);
                            intent.putExtra("rc_num", rc_num);
                            intent.putExtra("member_ID", member_ID);
                            intent.putExtra("memberName", memberName);
                            intent.putExtra("memberName_Eng", memberName_Eng);
                            intent.putExtra("districtCode", districtCode);
                            intent.putExtra("search_taluk_Code", talukCode);
                            intent.putExtra("search_hobli_Code", hobliCode);
                            intent.putExtra("search_town_Code", townCode);
                            intent.putExtra("search_ward_Code", wardCode);
                            intent.putExtra("bincom_Code", bincom_Code);
                            intent.putExtra("salutation_Code", salutation_Code);
                            intent.putExtra("father_husband_Name", father_husband_Name);
                            intent.putExtra("motheName", motheName);
                            intent.putExtra("txt_HOF_Name", hofName);
                            intent.putExtra("hofName_Eng", hofName_Eng);
                            intent.putExtra("hof_relaCode", hof_relaCode);
                            intent.putExtra("str_add1", address1);
                            intent.putExtra("address1_Eng", address1_Eng);
                            intent.putExtra("str_add2", address2);
                            intent.putExtra("address2_Eng", address2_Eng);
                            intent.putExtra("str_add3", address3);
                            intent.putExtra("address3_Eng", address3_Eng);
                            intent.putExtra("pincode", pin);
                            intent.putExtra("income", totalIncome);
                            intent.putExtra("creamyLayer", creamyLayer);
                            intent.putExtra("reasonCode_creamy", reasonCode_creamy);
                            intent.putExtra("distCode_ass",distCode_ass);
                            intent.putExtra("talCode_ass",talCode_ass);
                            intent.putExtra("hobCode_ass",hobCode_ass);
                            intent.putExtra("uName_get", uName_get);

                            startActivity(intent);
                            finish();
                        } else {
                            etMotherName.setError(getString(R.string.enter_name));
                        }
                    } else {
                        etFatherHusbanName.setError(getString(R.string.enter_name));
                    }
                } else {
                    ((TextView) spBincom.getSelectedView()).setError(getString(R.string.select_title));
                    Toast.makeText(getApplicationContext(), getString(R.string.select_title), Toast.LENGTH_SHORT).show();
                }
            } else {
                ((TextView) spRelationship_HOF.getSelectedView()).setError(getString(R.string.select_title));
                Toast.makeText(getApplicationContext(), getString(R.string.select_title), Toast.LENGTH_SHORT).show();
            }
        });

        btnUpload.setOnClickListener(v -> {
            other_non_exist_reason = etNONExistReason.getText().toString();
            Log.d("other_non_exist_reason",""+other_non_exist_reason);
            if (option.equals("YES")) {
                if (non_Exist_Reason.equals("-- Select --")) {
                    ((TextView) spNonexistenceReasons.getSelectedView()).setError(getString(R.string.select_title));
                } else {
                    if (non_Exist_Reason.equals("Other")) {
                        if (TextUtils.isEmpty(other_non_exist_reason)) {
                            etNONExistReason.setError("Please Enter Reason");
                        } else {
                            UploadInactiveMember(other_non_exist_reason);
                        }
                    }else {
                        UploadInactiveMember(non_Exist_Reason);
                    }
                }
            }
        });
    }

    public void UploadInactiveMember(String reason){
        if (isNetworkAvailable()) {
            dialog.show();
            Log.d("Value", "Complete");

            apiInterface = APIClient.getClient(getString(R.string.inactive_member_url)).create(APIInterface.class);

            //GET List Resources
            Call<String> call = apiInterface.doUploadNotExistMember(getString(R.string.flag1), getString(R.string.flag2),rc_num, member_ID, uName_get, reason);

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
                            Intent i = new Intent(E_Kshana_Freeze_Member_Details_VA.this, E_Kshana_MainScreen_VA.class);
                            i.putExtra("rc_num", rc_num);
                            i.putExtra("distCode_ass",distCode_ass);
                            i.putExtra("talCode_ass",talCode_ass);
                            i.putExtra("hobCode_ass",hobCode_ass);
                            i.putExtra("uName_get", uName_get);
                            startActivity(i);
                            finish();
                        } else {
                            dialog.dismiss();
                            runOnUiThread(runnable);
                        }
                    }catch (NullPointerException e){
                        dialog.dismiss();
                        runOnUiThread(runnable);
                        e.printStackTrace();
                        Log.e("NullPointerException", ""+e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("data1_Throwable", t.getMessage() + "");
                }
            });

        }else {
            buildAlert_Internet();
            //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
        }
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

    public void GetBincom(){
        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();
        objects_bincom = sqLiteAssetHelper_masters.Get_BinCome();
        adapter_bincom = new ArrayAdapter<>(this, R.layout.spinner_item_color, objects_bincom);
        adapter_bincom.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spBincom.setAdapter(adapter_bincom);
    }

    public void GetNon_Exist_Reason(){
        adapter_Non_Exist = ArrayAdapter.createFromResource(this, R.array.Non_Exist_Reason, R.layout.spinner_item_color);
        adapter_Non_Exist.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spNonexistenceReasons.setAdapter(adapter_Non_Exist);
    }

    public void GetRelationship_HOF(){
        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();
        objects_Relationship = sqLiteAssetHelper_masters.Get_Relationship();
        adapter_Relationship = new ArrayAdapter<>(this, R.layout.spinner_item_color, objects_Relationship);
        adapter_Relationship.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spRelationship_HOF.setAdapter(adapter_Relationship);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Alert")
                .setMessage("Do you want to discard the changes?")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, id) -> {
                    Intent i1 = new Intent(E_Kshana_Freeze_Member_Details_VA.this, E_Kshana_MainScreen_VA.class);
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
}
