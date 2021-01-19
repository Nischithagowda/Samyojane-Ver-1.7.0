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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class E_Kshana_Add_Member extends AppCompatActivity {

    String SOAP_ACTION = "http://tempuri.org/KannadatoEnglish";
    public final String OPERATION_NAME = "KannadatoEnglish";  //Method_name

    public final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";  // NAMESPACE
    String SOAP_ADDRESS = "http://164.100.133.47/nk_namesearch/SearchRecordsByName_RCOTC.asmx";

    HttpTransportSE androidHttpTransport;
    SoapSerializationEnvelope envelope;
    SoapPrimitive resultString;
    String nameFlag = "Failed";

    TextView txtDistrict, txtTaluk, txtHobli, txtTown, txtWard, txtAddress1, txtAddress2, txtAddress3,
            txtPIN, txtTotalIncome, txtResidingYear, txtCreamyLayer, txtHOF_Name;
    EditText etMemberName, etFatherHusbanName, etMotherName;
    Spinner spBincom,spRelationship_HOF;
    Button btnSave;
    SQLiteAssetHelper_Masters sqLiteAssetHelper_masters;
    List<AutoCompleteTextBox_Object> objects_bincom = new ArrayList<>();
    List<AutoCompleteTextBox_Object> objects_Relationship = new ArrayList<>();
    ArrayAdapter<AutoCompleteTextBox_Object> adapter, adapter_bincom, adapter_Relationship;
    Activity activity;
    String bincom_Name=null, bincom_Name_Eng=null, hof_relName=null;
    int bincom_Code=0, salutation_Code=1, hof_relaCode=0;
    SqlLiteOpenHelper_Class sqlLiteOpenHelper_class;
    String rc_num, member_ID;
    String distCode_ass, talCode_ass, hobCode_ass, uName_get;
    int inc_mem_ID;
    boolean return_Value;
    InputMethodManager imm;
    InputMethodSubtype ims;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    int districtCode, talukCode=0, hobliCode=0, townCode=0, wardCode=0, villageCode=99999, habitationCode=255;
    String districtName, talukName, hobliName, townName, wardName;
    String memberName, memberName_Eng=null, hofName, hofName_Eng;
    String address1, address1_Eng, address2, address2_Eng, address3, address3_Eng, pin, totalIncome, residing_year, creamyLayer, reasonCode_creamy;
    ProgressDialog dialog;
    String father_husband_Name, motheName;

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

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_kshana_add_member);

        etMemberName = findViewById(R.id.etMemberName);
        etFatherHusbanName = findViewById(R.id.etFatherHusbanName);
        etMotherName = findViewById(R.id.etMotherName);
        spRelationship_HOF = findViewById(R.id.spRelationship_HOF);
        spBincom = findViewById(R.id.spBincom);
        btnSave = findViewById(R.id.btnSave);
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
        txtResidingYear = findViewById(R.id.txtResidingYear);
        txtCreamyLayer = findViewById(R.id.txtCreamyLayer);
        txtHOF_Name = findViewById(R.id.txtHOF_Name);

        etMemberName.setFilters(new InputFilter[] { filter_Kan });
        etFatherHusbanName.setFilters(new InputFilter[] { filter_Kan });
        etMotherName.setFilters(new InputFilter[] { filter_Kan });

        etMemberName.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });

        etFatherHusbanName.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
            return false;
        });
        etMotherName.setOnTouchListener((v, event) -> {
            check_Kannada_Key_lang();
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

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Add_Member.this);
        sqlLiteOpenHelper_class.open_Reasons_Master_Tbl();

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(E_Kshana_Add_Member.this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();

        Intent i = getIntent();
        distCode_ass = i.getStringExtra("distCode_ass");
        talCode_ass = i.getStringExtra("talCode_ass");
        hobCode_ass = i.getStringExtra("hobCode_ass");
        rc_num = i.getStringExtra("rc_num");
        uName_get = i.getStringExtra("uName_get");

        Log.d("rc_num",""+rc_num);
        Log.d("distCode_ass", ""+distCode_ass);
        Log.d("talCode_ass", ""+talCode_ass);
        Log.d("hobCode_ass", ""+hobCode_ass);
        Log.d("uName_get", uName_get);

        Cursor cursor = database.rawQuery("Select MAX("+DataBaseHelperClass_btnDownload_E_Kshana.otc_member_id
                +") as member_Count from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_member_id
                +" where "+ DataBaseHelperClass_btnDownload_E_Kshana.RC_Num+"='"+rc_num+"'", null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                inc_mem_ID = cursor.getInt(cursor.getColumnIndexOrThrow("member_Count")) +1;
                Log.d("inc_mem_ID", ""+inc_mem_ID);
                member_ID = ""+inc_mem_ID;
                Log.d("member_ID", ""+member_ID);
            }
        } else {
            cursor.close();
        }

        Cursor cursor1 = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_NAME_UpdatedMemberDetails
                +" where "+DataBaseHelperClass_btnDownload_E_Kshana.RC_Application_ID+"='"+rc_num+"'",null);

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


        Log.d("districtCode",""+ districtCode +" talukCode "+talukCode+" hobliCode "
                +hobliCode+" townCode "+townCode+" wardCode "+wardCode);
        Log.d("hofName",""+hofName);

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this);
        sqLiteAssetHelper_masters.open_NK_MASTER_Tbl();
        districtName = sqLiteAssetHelper_masters.Get_DistName_NK_Master(districtCode);
        talukName = sqLiteAssetHelper_masters.Get_TalukName_NK_Master(districtCode, talukCode);
        hobliName = sqLiteAssetHelper_masters.Get_HobliName_NK_Master(districtCode, talukCode, hobliCode);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Add_Member.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        townName = sqlLiteOpenHelper_class.Get_TownName(districtCode, talukCode, townCode);

        sqlLiteOpenHelper_class = new SqlLiteOpenHelper_Class(E_Kshana_Add_Member.this,"str","str");
        sqlLiteOpenHelper_class.open_Town_Ward_Tbl();
        wardName = sqlLiteOpenHelper_class.Get_WardName_one(districtCode, talukCode, townCode, wardCode);

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
        txtResidingYear.setText(residing_year);
        txtCreamyLayer.setText(creamyLayer);

        sqLiteAssetHelper_masters = new SQLiteAssetHelper_Masters(this, activity);
        sqLiteAssetHelper_masters.open_Title_MASTER_Tbl();
        GetBincom();
        GetRelationship_HOF();

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

            memberName = etMemberName.getText().toString();
            father_husband_Name = etFatherHusbanName.getText().toString();
            motheName = etMotherName.getText().toString();
            Log.d("memberName",""+memberName);

            Runnable please_try_again = () -> Toast.makeText(getApplicationContext(), "Please Try again", Toast.LENGTH_SHORT).show();

            if (!TextUtils.isEmpty(memberName)) {
                if (isNetworkAvailable()) {
                    if (memberName_Eng==null) {
                        new GetEngName().execute();
                    }
                }else {
                    buildAlert_Internet();
                    //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
                }
                if (hof_relaCode != 0) {
                    if (bincom_Code != 0) {
                            if (!TextUtils.isEmpty(father_husband_Name)) {
                                if (!TextUtils.isEmpty(motheName)) {
                                    if (isNetworkAvailable()) {
                                        if (nameFlag.equals("Success")) {
                                            Intent intent = new Intent(E_Kshana_Add_Member.this, E_Kshana_Freeze_Member_Details_Second.class);
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
                                            new GetEngName().execute();
                                            runOnUiThread(please_try_again);
                                        }
                                    } else {
                                        buildAlert_Internet();
                                        //Toast.makeText(getApplicationContext(), "Please Switch ON Internet", Toast.LENGTH_SHORT).show();
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
                    ((TextView) spRelationship_HOF.getSelectedView()).setError(getString(R.string.select_title));
                    Toast.makeText(getApplicationContext(), getString(R.string.select_title), Toast.LENGTH_SHORT).show();
                }
            }else {
                etMemberName.setError(getString(R.string.enter_name));
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
        super.onBackPressed();
        finish();
    }
}
