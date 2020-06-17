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
import android.widget.AutoCompleteTextView;
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

@SuppressLint("Registered")
public class E_Kshana_Member_Details extends AppCompatActivity {

    TextView txtMemberName, txtDistrict;
    AutoCompleteTextView autoSearchTaluk, autoSearchHobli, autoSearchTown, autoSearchWard;
    Spinner spBincom,spRelationship_HOF, spNonexistenceReasons;
    EditText etFatherHusbanName, etMotherName, etHOF_Name, etNONExistReason;
    RadioGroup radioGroup3;
    RadioButton radioButton3, radioButton33;
    Button btnSave, btnUpload;
    TableLayout tl_table;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    String member_ID, rc_num;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    int districtCode, talukCode=0, hobliCode=0, townCode=0, wardCode=0;
    String districtName, talukName, hobliName, townName, wardName;
    String memberName, father_husband_Name, motheName, txt_HOF_Name, hofName;
    String option="NO";
    String bincom_Name=null, hof_relName=null, non_Exist_Reason=null;
    int bincom_Code=0, salutation_Code=1, hof_relaCode=0;
    String other_non_exist_reason;
    String distCode_ass, talCode_ass, hobCode_ass, uName_get;

    SQLiteAssetHelper_Masters sqLiteAssetHelper_masters;
    List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_hobli = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Town = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Ward = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_bincom = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Relationship = new ArrayList<>();
    ArrayAdapter<AutoCompleteTextBox_Object> adapter, adapter_bincom, adapter_Relationship;
    ArrayAdapter<CharSequence> adapter_Non_Exist;
    String strSearchTalukName, strSearchHobliName, strSearchTownName, strSearchWardName;
    int search_taluk_Code=0, search_hobli_Code=0, search_town_Code=0, search_ward_Code=0;
    Activity activity;

    APIInterface apiInterface;
    ProgressDialog dialog;

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

    //550700193944
    //550700194324

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_kshana_member_details);

        txtMemberName = findViewById(R.id.txtMemberName);
        txtDistrict = findViewById(R.id.txtDistrict);
        autoSearchTaluk = findViewById(R.id.autoSearchTaluk);
        autoSearchHobli = findViewById(R.id.autoSearchHobli);
        autoSearchTown = findViewById(R.id.autoSearchTown);
        autoSearchWard = findViewById(R.id.autoSearchWard);
        spBincom = findViewById(R.id.spBincom);
        etFatherHusbanName = findViewById(R.id.etFatherHusbanName);
        etMotherName = findViewById(R.id.etMotherName);
        etHOF_Name = findViewById(R.id.etHOF_Name);
        spRelationship_HOF = findViewById(R.id.spRelationship_HOF);
        btnSave = findViewById(R.id.btnSave);
        radioGroup3 =findViewById(R.id.radioGroup3);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton33 = findViewById(R.id.radioButton33);
        spNonexistenceReasons = findViewById(R.id.spNonexistenceReasons);
        btnUpload = findViewById(R.id.btnUpload);
        tl_table = findViewById(R.id.tl_table);
        etNONExistReason = findViewById(R.id.etNONExistReason);

        spNonexistenceReasons.setVisibility(View.GONE);
        etNONExistReason.setVisibility(View.GONE);
        tl_table.setVisibility(View.VISIBLE);
        btnUpload.setVisibility(View.GONE);

        etFatherHusbanName.setFilters(new InputFilter[] { filter_Kan });
        etMotherName.setFilters(new InputFilter[] { filter_Kan });
        etHOF_Name.setFilters(new InputFilter[] { filter_Kan });
        //etNONExistReason.setFilters(new InputFilter[] { filter_Kan });

        etFatherHusbanName.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });
        etMotherName.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });
        etHOF_Name.setOnTouchListener((v, event) -> {
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

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Member_Details.this);
        sqlLiteOpenHelper_class.open_Reasons_Master_Tbl();

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(E_Kshana_Member_Details.this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();

        Intent i = getIntent();
        rc_num = i.getStringExtra("rc_num");
        member_ID = i.getStringExtra("member_ID");
        distCode_ass = i.getStringExtra("distCode_ass");
        talCode_ass = i.getStringExtra("talCode_ass");
        hobCode_ass = i.getStringExtra("hobCode_ass");
        uName_get = i.getStringExtra("uName_get");

        Log.d("member_ID",""+member_ID);
        Log.d("rc_num",""+rc_num);
        Log.d("uName_get", uName_get);

        openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(this);
        database = openHelper.getReadableDatabase();

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_MemberDetails
                +" where "+DataBaseHelperClass_btnDownload_E_Kshana.RAT_ACK_ID+"='"+rc_num+"' and "
                +DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_ID+"="+member_ID, null);
        if (cursor.getCount()>0){
            if (cursor.moveToNext()){
                memberName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RAT_Member_KName));
                districtCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RAT_District_Code_NK));
                talukCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RAT_Taluk_Code_NK));
                hobliCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RAT_Hobli_Code_NK));
                townCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RAT_Town_Code_NK));
                wardCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RAT_Ward_No_NK));
                hofName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RAT_HOF_KName));
            }
        }

        Log.d("memberName",""+memberName);
        Log.d("districtCode",""+ districtCode +" talukCode "+talukCode+" hobliCode "+hobliCode+" townCode "+townCode+" wardCode "+wardCode);
        Log.d("hofName",""+hofName);

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();
        districtName = sqLiteAssetHelper_masters.Get_DistName_NK_Master(districtCode);

        search_taluk_Code = talukCode;
        GetTalukName(districtCode);
        talukName = sqLiteAssetHelper_masters.Get_TalukName_NK_Master(districtCode, talukCode);

        search_hobli_Code = hobliCode;
        GetHobliName(districtCode, talukCode);
        hobliName = sqLiteAssetHelper_masters.Get_HobliName_NK_Master(districtCode, talukCode, hobliCode);

        search_town_Code = townCode;
        GetTownName(districtCode, talukCode);
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Member_Details.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        townName = sqlLiteOpenHelper_class.Get_TownName(districtCode, talukCode, townCode);

        search_ward_Code=wardCode;
        GetWardName(districtCode, talukCode, townCode);
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Member_Details.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        wardName = sqlLiteOpenHelper_class.Get_WardName_one(districtCode, talukCode, townCode, wardCode);

        txtMemberName.setText(memberName);
        txtDistrict.setText(districtName);
        autoSearchTaluk.setText(talukName);
        autoSearchHobli.setText(hobliName);
        autoSearchTown.setText(townName);
        autoSearchWard.setText(wardName);
        etHOF_Name.setText(hofName);

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

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();
        GetBincom();
        GetNon_Exist_Reason();
        GetRelationship_HOF();

        autoSearchTaluk.dismissDropDown();

        autoSearchTaluk.setOnTouchListener((v, event) -> {
            autoSearchTaluk.showDropDown();
            autoSearchTaluk.setError(null);
            GetTalukName(districtCode);
            return false;
        });

        autoSearchHobli.setOnTouchListener((v, event) -> {
            autoSearchHobli.showDropDown();
            autoSearchHobli.setError(null);
            return false;
        });

        autoSearchTown.setOnTouchListener((v, event) -> {
            autoSearchTown.showDropDown();
            autoSearchTown.setError(null);
            return false;
        });

        autoSearchWard.setOnTouchListener((v, event) -> {
            autoSearchWard.showDropDown();
            autoSearchWard.setError(null);
            return false;
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
                non_Exist_Reason = (String) spNonexistenceReasons.getSelectedItem();
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
            txt_HOF_Name = etHOF_Name.getText().toString();

            Log.d("member_ID",""+member_ID+", rc_num:"+rc_num+", memberName:"+memberName);
            Log.d("districtCode",""+districtCode+", search_taluk_Code:"+search_taluk_Code+", search_hobli_Code:"+search_hobli_Code);
            Log.d("search_town_Code",""+search_town_Code+", search_ward_Code:"+search_ward_Code);
            Log.d("bincom_Code",""+bincom_Code+", salutation_Code:"+salutation_Code);
            Log.d("father_husband_Name",""+father_husband_Name+", motheName:"+motheName);
            Log.d("txt_HOF_Name",""+txt_HOF_Name+", hof_relaCode:"+hof_relaCode);

            if(autoSearchTaluk.getText().toString()!=null && search_taluk_Code!=0){
                if (autoSearchHobli.getText().toString()!=null && search_hobli_Code!=0) {
                    if (autoSearchTown.getText().toString() != null && search_town_Code != 0) {
                        if (autoSearchWard.getText().toString() != null && search_ward_Code != 0) {
                            if (bincom_Code != 0) {
                                    if (!TextUtils.isEmpty(father_husband_Name)) {
                                        if (!TextUtils.isEmpty(motheName)) {
                                            if (!TextUtils.isEmpty(txt_HOF_Name)) {
                                                if (hof_relaCode != 0) {

                                                    Intent intent = new Intent(E_Kshana_Member_Details.this, E_Kshana_Member_Details_Second.class);
                                                    intent.putExtra("rc_num", rc_num);
                                                    intent.putExtra("member_ID", member_ID);
                                                    intent.putExtra("memberName", memberName);
                                                    intent.putExtra("districtCode", districtCode);
                                                    intent.putExtra("search_taluk_Code", search_taluk_Code);
                                                    intent.putExtra("search_hobli_Code", search_hobli_Code);
                                                    intent.putExtra("search_town_Code", search_town_Code);
                                                    intent.putExtra("search_ward_Code", search_ward_Code);
                                                    intent.putExtra("bincom_Code", bincom_Code);
                                                    intent.putExtra("salutation_Code", salutation_Code);
                                                    intent.putExtra("father_husband_Name", father_husband_Name);
                                                    intent.putExtra("motheName", motheName);
                                                    intent.putExtra("txt_HOF_Name", txt_HOF_Name);
                                                    intent.putExtra("hof_relaCode", hof_relaCode);
                                                    intent.putExtra("distCode_ass",distCode_ass);
                                                    intent.putExtra("talCode_ass",talCode_ass);
                                                    intent.putExtra("hobCode_ass",hobCode_ass);
                                                    intent.putExtra("uName_get", uName_get);
                                                    startActivity(intent);
                                                    finish();

                                                } else {
                                                    ((TextView) spRelationship_HOF.getSelectedView()).setError(getString(R.string.select_title));
                                                    Toast.makeText(getApplicationContext(), getString(R.string.select_title), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                etHOF_Name.setError(getString(R.string.enter_name));
                                            }
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
                            autoSearchWard.setError(getString(R.string.select_ward));
                        }
                    } else {
                        autoSearchTown.setError(getString(R.string.select_town));
                    }
                }else {
                    autoSearchHobli.setError(getString(R.string.select_hobli));
                }
            }else {
                autoSearchTaluk.setError(getString(R.string.select_taluk));
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
                    try{
                        if (multipleResource.equals("success") && multipleResource!=null && !multipleResource.equals("0")){
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(E_Kshana_Member_Details.this, E_Kshana_MainScreen.class);
                            i.putExtra("rc_num", rc_num);
                            i.putExtra("distCode_ass",distCode_ass);
                            i.putExtra("talCode_ass",talCode_ass);
                            i.putExtra("hobCode_ass",hobCode_ass);
                            i.putExtra("uName_get", uName_get);
                            startActivity(i);
                            finish();
                        }else {
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

    public void GetTalukName(int distCode1){
        objects.clear();
        Log.d("distCode1",""+distCode1);
        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();
        objects = sqLiteAssetHelper_masters.Get_TalukName_NK_Master(distCode1);

        adapter = new ArrayAdapter<>(this, R.layout.list_item, objects);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchTaluk.setAdapter(adapter);
        autoSearchTaluk.setOnItemClickListener((parent, view, position, id) -> {
            strSearchTalukName = parent.getItemAtPosition(position).toString();
            Log.d("strSearchTalukName", strSearchTalukName);
            search_taluk_Code = Integer.parseInt(((AutoCompleteTextBox_Object) parent.getItemAtPosition(position)).getId());
            Log.d("search_taluk_Code", "" + search_taluk_Code);
            if (talukCode!=search_taluk_Code) {
                autoSearchHobli.setText("");
                autoSearchTown.setText("");
                autoSearchWard.setText("");
                objects_hobli.clear();
                objects_Town.clear();
                objects_Ward.clear();
                search_hobli_Code=0;
                search_town_Code=0;
                search_ward_Code=0;
                GetHobliName(distCode1, search_taluk_Code);
                GetTownName(distCode1, search_taluk_Code);
            }else {
                search_hobli_Code = hobliCode;
                search_town_Code=townCode;
                search_ward_Code=wardCode;
                GetHobliName(distCode1, talukCode);
                GetTownName(distCode1, talukCode);
            }
        });
    }

    public void GetHobliName(int distCode1, int talukCode){
        objects_hobli.clear();
        Log.d("distCode1",""+distCode1);
        Log.d("talukCode",""+talukCode);
        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();
        objects_hobli = sqLiteAssetHelper_masters.Get_HobliName_NK_Master(distCode1, talukCode);

        adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_hobli);
        adapter.setDropDownViewResource(R.layout.list_item);
        autoSearchHobli.setAdapter(adapter);
        autoSearchHobli.setOnItemClickListener((parent, view, position, id) -> {
            strSearchHobliName = parent.getItemAtPosition(position).toString();
            Log.d("strSearchHobliName", strSearchHobliName);
            search_hobli_Code = Integer.parseInt(((AutoCompleteTextBox_Object) parent.getItemAtPosition(position)).getId());
            Log.d("search_hobli_Code", "" + search_hobli_Code);
        });
    }

    public void GetTownName(int distCode, int talukCode){
        objects_Town.clear();
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Member_Details.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        objects_Town = sqlLiteOpenHelper_class.Get_TownName_NK(distCode, talukCode);

        if (!objects_Town.isEmpty()) {
            adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Town);
            adapter.setDropDownViewResource(R.layout.list_item);
            autoSearchTown.setAdapter(adapter);
            autoSearchTown.setOnItemClickListener((parent, view, position, id) -> {
                // fetch the user selected value
                strSearchTownName = parent.getItemAtPosition(position).toString();
                Log.d("strSearchTownName", strSearchTownName);

                search_town_Code = Integer.parseInt(((AutoCompleteTextBox_Object) parent.getItemAtPosition(position)).getId());
                Log.d("search_town_Code", "" + search_town_Code);
                if (search_town_Code!=townCode) {
                    autoSearchWard.setText("");
                    objects_Ward.clear();
                    search_ward_Code = 0;
                    GetWardName(distCode, talukCode, search_town_Code);
                }else {
                    search_ward_Code = wardCode;
                    GetWardName(distCode, talukCode, townCode);
                }
            });
        }else {
            autoSearchTaluk.setError(getString(R.string.no_town_found));
        }
    }

    public void GetWardName(int distCode, int talukCode, int townCode){
        objects_Ward.clear();
        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Member_Details.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        objects_Ward = sqlLiteOpenHelper_class.Get_WardName_NK(distCode, talukCode, townCode);

        if (!objects_Ward.isEmpty()) {
            Log.d("objects_Ward",""+objects_Ward);
            adapter = new ArrayAdapter<>(this, R.layout.list_item, objects_Ward);
            adapter.setDropDownViewResource(R.layout.list_item);
            autoSearchWard.setAdapter(adapter);
            //autoSearchWard.showDropDown();
            autoSearchWard.setOnItemClickListener((parent, view, position, id) -> {
                // fetch the user selected value
                strSearchWardName = parent.getItemAtPosition(position).toString();
                Log.d("strSearchWardName", strSearchWardName);

                search_ward_Code = Integer.parseInt(((AutoCompleteTextBox_Object) parent.getItemAtPosition(position)).getId());
                Log.d("search_ward_Code", "" + search_ward_Code);
            });
        }else {
            Log.d("objects_Ward",""+objects_Ward);
            autoSearchTown.setError(getString(R.string.no_ward_found));
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Alert")
                .setMessage("Do you want to discard the changes?")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, id) -> {
                    Intent i = new Intent(E_Kshana_Member_Details.this, E_Kshana_MainScreen.class);
                    i.putExtra("rc_num", rc_num);
                    i.putExtra("distCode_ass",distCode_ass);
                    i.putExtra("talCode_ass",talCode_ass);
                    i.putExtra("hobCode_ass",hobCode_ass);
                    i.putExtra("uName_get", uName_get);
                    startActivity(i);
                    finish();
                })
                .setNegativeButton("NO", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(18);
    }
}
