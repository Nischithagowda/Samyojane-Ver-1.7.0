package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface_SamyojaneAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    TextView version;
    @SuppressLint("StaticFieldLeak")
    public static EditText userName, pwd;
    String RI_Name,district, taluk, hobli, VA_Circle_Name, VA_Name, e_kshana;
    String district_k, taluk_k, hobli_k, VA_Circle_Name_k;
    String deviceId;
    HashMap<String, String> hashMap, hashMap_assistant;
    Set_and_Get_data set_and_get_data;
    Set_and_Get_Version set_and_get_version;
    String uName_get, pwd_get;
    String uName_db_RI, pwrd_db_RI, RI_IMEI1_db, RI_IMEI2_db;
    String uName_db_VA, pwrd_db_VA, IMEI1_db, IMEI2_db;
    String version_Code, VC_Status;
    String districtCode, talukCode, hobliCode, va_Circle_Code;
    int version_Result;
    Button btnSubmit, btnActivation, btChangeLogin;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    LinearLayout linearLayout;
    ProgressDialog dialog;
    private static final int PERMISSION_REQUEST_CODE_WRITE_STORAGE = 2051;
    TextView tvAlert;
    int getFlag;
    InputMethodManager imm;
    InputMethodSubtype ims;
    int from_DB_distCode, from_DB_talukCode;
    String IMEI_Num_Shared, Mob_Num_Shared;
    APIInterface_SamyojaneAPI apiInterface_samyojaneAPI;

    ListView listLanguage;
    ArrayAdapter<String> adapter_language;
    String[] lang;
    Dialog dialog1;
    AlertDialog alert;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"MissingPermission", "HardwareIds", "ServiceCast", "ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        version = findViewById(R.id.version);
        userName = findViewById(R.id.userName);
        pwd = findViewById(R.id.pwd);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnActivation = findViewById(R.id.btnActivation);
        linearLayout = findViewById(R.id.userLayout);
        tvAlert= findViewById(R.id.tvAlert);
        btChangeLogin = findViewById(R.id.btChangeLogin);

        version_Code = getString(R.string.versionCode);
        Log.d("version_Code11",""+version_Code);
        version.setText(getString(R.string.version)+version_Code);

        if (hasPermissions()) {
            Log.d("Permission","hasPermissions");
        } else {
            requestPerms();
        }

        pwd.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        btnActivation.setVisibility(View.GONE);
        tvAlert.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        pwd.setError(null);


        userName.setOnTouchListener((v, event) -> {
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
            return false;
        });

        btnSubmit.setOnClickListener(v -> {

            uName_get = String.valueOf(userName.getText());
            pwd_get = String.valueOf(pwd.getText());
            Log.d("Entered Value", "" + uName_get);
            Log.d("Entered Value", "" + pwd_get);

            if (TextUtils.isEmpty(uName_get)){
                userName.setError(getString(R.string.field_canno_null));
            }else {
                userName.setError(null);
                if (TextUtils.isEmpty(pwd_get)){
                    pwd.setError(getString(R.string.field_canno_null));
                }else{
                    btnSubmit.setText(getString(R.string.login_c));
                    pwd.setError(null);
                    checkDatabase_new();
                }
            }

        });

        btnActivation.setOnClickListener(v -> {
            if (isNetworkAvailable()) {

                btnActivation.setText(getString(R.string.activating));
                try {
                    dialog.show();
                    GetVersion();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error), LENGTH_LONG).show();
                }
            } else {
                version_Result = check_Version_Code(version_Code);
                if(version_Result==0) {
                    checkDatabase_new();
                    Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
                }else {
                    btnActivation.setVisibility(View.GONE);
                    tvAlert.setVisibility(View.VISIBLE);
                    tvAlert.setText(getString(R.string.version_does_not_match));
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        btChangeLogin.setOnClickListener(v -> buildAlertMessageGoingBack());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        IMEI_Num_Shared = i.getStringExtra("IMEI_Num");
        Mob_Num_Shared = i.getStringExtra("mob_Num");
//        IMEI_Num_Shared = "911623302747388";
//        Mob_Num_Shared = "9141410787";

        //deviceId = "911623302747388";
        deviceId = IMEI_Num_Shared;
        Log.d("Device_IMEI", ""+deviceId);
        Log.d("Mob_Num", ""+Mob_Num_Shared);

        //telephonyManager.getDeviceId()
        //352514086476508
        //358240051111110
        //352514086478413
        //911623302747388
        //862611044444173
        //Toast.makeText(getApplicationContext(), deviceId, Toast.LENGTH_SHORT).show();

        hashMap = new HashMap<>();
        hashMap_assistant = new HashMap<>();

        if (isNetworkAvailable()) {
            //checkDatabase();
            //truncateDatabase();
            try {
                dialog.show();
                GetVersion();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), getString(R.string.error), LENGTH_LONG).show();
            }
        } else {
            version_Result = check_Version_Code(version_Code);
            if(version_Result==0) {
                checkDatabase_new();
                Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
            }else {
                btnActivation.setVisibility(View.GONE);
                tvAlert.setVisibility(View.VISIBLE);
                tvAlert.setText(getString(R.string.version_does_not_match));
                linearLayout.setVisibility(View.GONE);
            }
        }
    }

    public static class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private static class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int check_Version_Code(String V_Code){
        int i =1;
        openHelper = new DataBaseHelperClass_btnDownload_VersionCode(this);
        database = openHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_VersionCode.TABLE_NAME
                +" where " +DataBaseHelperClass_btnDownload_VersionCode.VersionCode+"='"+V_Code+"'", null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                VC_Status = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_VersionCode.Status));
                Log.d("Version_Status",V_Code+": "+VC_Status);
                if (Objects.equals(VC_Status, "Y")){
                    Log.d("Version_Status","Matched");
                    i=0;
                } else {
                    Log.d("Version_Status","Does not Match");
                    i=1;
                }
            }
        }else {
            cursor.close();
            Log.d("Version_Status","Does not Match");
            i=1;
            database.close();
        }
        Log.d("Status:",""+i);
        database.close();
        return i;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public int getFlag(){
        //flag=1 only RI
        //flag=2 only VA
        //flag=3 Both VA and RI
        openHelper = new DataBaseHelperClass_Credentials(MainActivity.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_Credentials.TABLE_NAME, null);
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                getFlag = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.flag)));
            }
        } else {
            cursor.close();
        }
        Log.d("Flag_value",""+ getFlag);
        return getFlag;
    }

    public String checkDatabase_new() {
        String str = "failed";
        int val_flag;
        int k=1;
        val_flag = getFlag();

        uName_get = String.valueOf(userName.getText());
        pwd_get = String.valueOf(pwd.getText());
        Log.d("Entered Value", "" + uName_get);
        Log.d("Entered Value", "" + pwd_get);

        if (uName_get.isEmpty()){
            if(pwd_get.isEmpty()) {
                pwd.setError(null);
            }
        }else {

            openHelper = new DataBaseHelperClass_Credentials(MainActivity.this);
            database = openHelper.getWritableDatabase();

            Cursor cursor = database.rawQuery("select * from " + DataBaseHelperClass_Credentials.TABLE_NAME, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    from_DB_distCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.District_Code));
                    from_DB_talukCode = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.Taluk_Code));
                }
            } else {
                cursor.close();
                from_DB_distCode = 0;
                from_DB_talukCode = 0;
                Log.d("Note2", "Not a valid user");
            }

            Log.d("from_DB_distCode", "" + from_DB_distCode);
            Log.d("from_DB_talukCode", "" + from_DB_talukCode);

            if (from_DB_distCode != 0) {
                if (from_DB_talukCode != 0) {
                    dialog.show();
                    if (val_flag == 2) {
                        //only VA Login.
                        Log.d("val_flag", "" + val_flag);

                        Cursor cursor1 = database.rawQuery("select * from " + DataBaseHelperClass_Credentials.TABLE_NAME
                                + " where " + DataBaseHelperClass_Credentials.District_Code + "=" + from_DB_distCode + " and "
                                + DataBaseHelperClass_Credentials.Taluk_Code + "=" + from_DB_talukCode, null);
                        if (cursor1.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                btnActivation.setVisibility(View.GONE);
                                tvAlert.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                do {
                                    Log.d("Login", "attempts " + k);
                                    uName_db_VA = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_UserName));
                                    pwrd_db_VA = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_Pwrd));
                                    IMEI1_db = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_IMEI_1));
                                    IMEI2_db = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_IMEI_2));
                                    district = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Name));
                                    district_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Name_k));
                                    districtCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Code));
                                    taluk = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Name));
                                    taluk_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Name_k));
                                    talukCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Code));
                                    hobli = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Name));
                                    hobli_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Name_k));
                                    hobliCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Code));
                                    VA_Circle_Name = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.VA_Circle_Name));
                                    VA_Circle_Name_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.VA_Circle_Name_k));
                                    va_Circle_Code = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_circle_Code));
                                    VA_Name = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.VA_Name));
                                    e_kshana = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.e_kshana));

                                    Log.d("Database Value", "" + district);
                                    Log.d("Database Value", "" + district_k);
                                    Log.d("Database Value", "" + taluk);
                                    Log.d("Database Value", "" + taluk_k);
                                    Log.d("Database Value", "" + hobli);
                                    Log.d("Database Value", "" + hobli_k);
                                    Log.d("Database Value", "" + VA_Circle_Name);
                                    Log.d("Database Value", "" + VA_Circle_Name_k);
                                    Log.d("Database Value", "" + va_Circle_Code);

                                    Log.d("Database Value", "" + uName_db_VA);
                                    Log.d("Database Value", "" + pwrd_db_VA);
                                    if (uName_get.toLowerCase().equals(uName_db_VA.toLowerCase()) && pwd_get.toLowerCase().equals(pwrd_db_VA.toLowerCase()) && (deviceId.equals(IMEI1_db) || deviceId.equals(IMEI2_db))) {
                                        Log.d("Login", "VA_LogIn");

                                        pwd.setError(null);
                                        dialog.dismiss();
                                        btnSubmit.setText(getString(R.string.login));
                                        database.close();

                                        dialog1 = new Dialog(MainActivity.this, R.style.CustomDialog_lan);
                                        dialog1.setContentView(R.layout.language_selection);
                                        dialog1.setTitle(getString(R.string.select_lan));
                                        dialog1.setCanceledOnTouchOutside(false);
                                        listLanguage = dialog1.findViewById(R.id.listLanguage);
                                        lang = getResources().getStringArray(R.array.language);
                                        adapter_language = new ArrayAdapter<>(MainActivity.this, R.layout.language_selection_item, R.id.tvlanguageSelection, lang);
                                        listLanguage.setAdapter(adapter_language);
                                        dialog1.show();

                                        listLanguage.setOnItemClickListener((parent, view, position, id) -> {
                                            String code = position == 1 ? "en" : "kn";
                                            Log.d("Selected_Language", "" + code);
                                            SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                                            sp.edit().putString(Constants.LANGUAGE, code).apply();
                                            setLocale(code);
                                        });
                                        //str = "success";
                                        break;
                                    } else {
                                        //str = "failed";
                                        Log.d("Login1", "Login Failed");
                                        pwd.setError(getString(R.string.pwd_error));
                                        dialog.dismiss();
                                        btnSubmit.setText(getString(R.string.login));
                                    }

                                    k++;
                                } while (cursor1.moveToNext());
                            } else {
                                btnActivation.setVisibility(View.VISIBLE);
                                tvAlert.setVisibility(View.VISIBLE);
                                linearLayout.setVisibility(View.GONE);
                            }
                            str = "Database has some value";
                        } else {
                            cursor1.close();
                            str = "Database was NULL";
                            btnActivation.setVisibility(View.VISIBLE);
                            tvAlert.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.GONE);
                        }
                    } else if (val_flag == 1) {
                        //only RI Login
                        Log.d("val_flag", "" + val_flag);

                        Cursor cursor1 = database.rawQuery("select * from " + DataBaseHelperClass_Credentials.TABLE_NAME
                                + " where " + DataBaseHelperClass_Credentials.District_Code + "=" + from_DB_distCode + " and "
                                + DataBaseHelperClass_Credentials.Taluk_Code + "=" + from_DB_talukCode, null);
                        if (cursor1.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                btnActivation.setVisibility(View.GONE);
                                tvAlert.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                do {
                                    Log.d("Login", "attempts " + k);
                                    uName_db_RI = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_UserName));
                                    pwrd_db_RI = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_Pwrd));
                                    RI_IMEI1_db = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_IMEI_1));
                                    RI_IMEI2_db = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_IMEI_2));
                                    district = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Name));
                                    district_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Name_k));
                                    districtCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Code));
                                    taluk = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Name));
                                    taluk_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Name_k));
                                    talukCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Code));
                                    hobli = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Name));
                                    hobli_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Name_k));
                                    hobliCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Code));
                                    RI_Name = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_Name));

                                    Log.d("Database Value", "" + district);
                                    Log.d("Database Value", "" + district_k);
                                    Log.d("Database Value", "" + taluk);
                                    Log.d("Database Value", "" + taluk_k);
                                    Log.d("Database Value", "" + hobli);
                                    Log.d("Database Value", "" + hobli_k);

                                    Log.d("Database Value", "" + uName_db_RI);
                                    Log.d("Database Value", "" + pwrd_db_RI);
                                    if (uName_get.toLowerCase().equals(uName_db_RI.toLowerCase()) && pwd_get.toLowerCase().equals(pwrd_db_RI.toLowerCase()) && (deviceId.equals(RI_IMEI1_db) || deviceId.equals(RI_IMEI2_db))) {
                                        Log.d("Login", "RI_LogIn");

                                        pwd.setError(null);
                                        dialog.dismiss();
                                        btnSubmit.setText(getString(R.string.login));
                                        database.close();

                                        dialog1 = new Dialog(MainActivity.this, R.style.CustomDialog_lan);
                                        dialog1.setContentView(R.layout.language_selection);
                                        dialog1.setTitle(getString(R.string.select_lan));
                                        dialog1.setCanceledOnTouchOutside(false);
                                        listLanguage = dialog1.findViewById(R.id.listLanguage);
                                        lang = getResources().getStringArray(R.array.language);
                                        adapter_language = new ArrayAdapter<>(MainActivity.this, R.layout.language_selection_item, R.id.tvlanguageSelection, lang);
                                        listLanguage.setAdapter(adapter_language);
                                        dialog1.show();

                                        listLanguage.setOnItemClickListener((parent, view, position, id) -> {
                                            String code = position == 1 ? "en" : "kn";
                                            Log.d("Selected_Language", "" + code);
                                            SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                                            sp.edit().putString(Constants.LANGUAGE, code).apply();
                                            setLocale_RI(code);
                                        });
                                        //str = "success";
                                        break;
                                    } else {
                                        //str = "failed";
                                        Log.d("Login1", "Login Failed");
                                        pwd.setError(getString(R.string.pwd_error));
                                        dialog.dismiss();
                                        btnSubmit.setText(getString(R.string.login));
                                    }

                                    k++;
                                } while (cursor1.moveToNext());
                            } else {
                                btnActivation.setVisibility(View.VISIBLE);
                                tvAlert.setVisibility(View.VISIBLE);
                                linearLayout.setVisibility(View.GONE);
                            }
                            str = "Database has some value";
                        } else {
                            cursor1.close();
                            str = "Database was NULL";
                            btnActivation.setVisibility(View.VISIBLE);
                            tvAlert.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.GONE);
                        }

                    } else if (val_flag == 3) {
                        //Both VA and RI login
                        Log.d("val_flag", "" + val_flag);

                        Cursor cursor1 = database.rawQuery("select * from " + DataBaseHelperClass_Credentials.TABLE_NAME
                                + " where " + DataBaseHelperClass_Credentials.District_Code + "=" + from_DB_distCode + " and "
                                + DataBaseHelperClass_Credentials.Taluk_Code + "=" + from_DB_talukCode, null);
                        if (cursor1.getCount() > 0) {
                            if (cursor1.moveToFirst()) {
                                btnActivation.setVisibility(View.GONE);
                                tvAlert.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.VISIBLE);
                                do {
                                    Log.d("Login", "attempts " + k);
                                    uName_db_VA = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_UserName));
                                    pwrd_db_VA = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_Pwrd));
                                    IMEI1_db = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_IMEI_1));
                                    IMEI2_db = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_IMEI_2));
                                    uName_db_RI = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_UserName));
                                    pwrd_db_RI = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_Pwrd));
                                    RI_IMEI1_db = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_IMEI_1));
                                    RI_IMEI2_db = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_IMEI_2));
                                    district = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Name));
                                    district_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Name_k));
                                    districtCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.District_Code));
                                    taluk = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Name));
                                    taluk_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Name_k));
                                    talukCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Taluk_Code));
                                    hobli = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Name));
                                    hobli_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Name_k));
                                    hobliCode = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.Hobli_Code));
                                    VA_Circle_Name = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.VA_Circle_Name));
                                    VA_Circle_Name_k = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.VA_Circle_Name_k));
                                    va_Circle_Code = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.VA_circle_Code));
                                    RI_Name = cursor1.getString(cursor1.getColumnIndexOrThrow(DataBaseHelperClass_Credentials.RI_Name));
                                    VA_Name = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.VA_Name));
                                    e_kshana = cursor1.getString(cursor1.getColumnIndex(DataBaseHelperClass_Credentials.e_kshana));

                                    Log.d("Database Value", "" + district);
                                    Log.d("Database Value", "" + district_k);
                                    Log.d("Database Value", "" + taluk);
                                    Log.d("Database Value", "" + taluk_k);
                                    Log.d("Database Value", "hobli3" + hobli);
                                    Log.d("Database Value", "hobli_k3" + hobli_k);
                                    Log.d("Database Value", "VA_Circle_Name3" + VA_Circle_Name);
                                    Log.d("Database Value", "VA_Circle_Name_k3" + VA_Circle_Name_k);
                                    Log.d("Database Value", "va_Circle_Code3" + va_Circle_Code);

                                    Log.d("Database Value", "uName_db_VA: " + uName_db_VA);
                                    Log.d("Database Value", "pwrd_db_VA: " + pwrd_db_VA);
                                    Log.d("Database Value", "uName_db_RI: " + uName_db_RI);
                                    Log.d("Database Value", "pwrd_db_RI: " + pwrd_db_RI);
                                    if (uName_get.toLowerCase().equals(uName_db_VA.toLowerCase()) && pwd_get.toLowerCase().equals(pwrd_db_VA.toLowerCase()) && (deviceId.equals(IMEI1_db) || deviceId.equals(IMEI2_db))) {
                                        Log.d("Login", "VA_LogIn");

                                        pwd.setError(null);
                                        dialog.dismiss();
                                        btnSubmit.setText(getString(R.string.login));
                                        database.close();

                                        dialog1 = new Dialog(MainActivity.this, R.style.CustomDialog_lan);
                                        dialog1.setContentView(R.layout.language_selection);
                                        dialog1.setTitle(getString(R.string.select_lan));
                                        dialog1.setCanceledOnTouchOutside(false);
                                        listLanguage = dialog1.findViewById(R.id.listLanguage);
                                        lang = getResources().getStringArray(R.array.language);
                                        adapter_language = new ArrayAdapter<>(MainActivity.this, R.layout.language_selection_item, R.id.tvlanguageSelection, lang);
                                        listLanguage.setAdapter(adapter_language);
                                        dialog1.show();

                                        listLanguage.setOnItemClickListener((parent, view, position, id) -> {
                                            String code = position == 1 ? "en" : "kn";
                                            Log.d("Selected_Language", "" + code);
                                            SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                                            sp.edit().putString(Constants.LANGUAGE, code).apply();
                                            setLocale(code);
                                        });
                                        //str = "VA_success";
                                        break;
                                    } else if (uName_get.toLowerCase().equals(uName_db_RI.toLowerCase()) && pwd_get.toLowerCase().equals(pwrd_db_RI.toLowerCase()) && (deviceId.equals(RI_IMEI1_db) || deviceId.equals(RI_IMEI2_db))) {
                                        Log.d("Login", "RI_LogIn");

                                        pwd.setError(null);
                                        dialog.dismiss();
                                        btnSubmit.setText(getString(R.string.login));
                                        database.close();

                                        dialog1 = new Dialog(MainActivity.this, R.style.CustomDialog_lan);
                                        dialog1.setContentView(R.layout.language_selection);
                                        dialog1.setTitle(getString(R.string.select_lan));
                                        dialog1.setCanceledOnTouchOutside(false);
                                        listLanguage = dialog1.findViewById(R.id.listLanguage);
                                        lang = getResources().getStringArray(R.array.language);
                                        adapter_language = new ArrayAdapter<>(MainActivity.this, R.layout.language_selection_item, R.id.tvlanguageSelection, lang);
                                        listLanguage.setAdapter(adapter_language);
                                        dialog1.show();

                                        listLanguage.setOnItemClickListener((parent, view, position, id) -> {
                                            String code = position == 1 ? "en" : "kn";
                                            Log.d("Selected_Language", "" + code);
                                            SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                                            sp.edit().putString(Constants.LANGUAGE, code).apply();
                                            setLocale_RI(code);
                                        });
                                        //str = "RI_success";
                                        break;
                                    } else {
                                        //str = "failed";
                                        Log.d("Login1", "Login Failed");
                                        pwd.setError(getString(R.string.pwd_error));
                                        dialog.dismiss();
                                        btnSubmit.setText(getString(R.string.login));
                                    }

                                    k++;
                                } while (cursor1.moveToNext());
                            } else {
                                btnActivation.setVisibility(View.VISIBLE);
                                tvAlert.setVisibility(View.VISIBLE);
                                linearLayout.setVisibility(View.GONE);
                            }
                            str = "Database has some value";
                        } else {
                            cursor1.close();
                            str = "Database was NULL";
                            btnActivation.setVisibility(View.VISIBLE);
                            tvAlert.setVisibility(View.VISIBLE);
                            linearLayout.setVisibility(View.GONE);
                        }
                    }else {
                        dialog.dismiss();
                    }
                } else {
                    str = "failed";
                }
            }
        }
        Log.d("returned_val", ""+str);
        return str;
    }

    public void truncateDatabase(){
        openHelper = new DataBaseHelperClass_Credentials(MainActivity.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_Credentials.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_Credentials.TABLE_NAME);
            Log.d("Database", "Database Truncated");
        } else {
            cursor.close();
        }
    }

    public void GetVersion(){
        apiInterface_samyojaneAPI = APIClient.getClient(getString(R.string.samyojane_API_url)).create(APIInterface_SamyojaneAPI.class);

        Call<String> call = apiInterface_samyojaneAPI.doGet_Version(getString(R.string.samyojane_api_flag1),getString(R.string.samyojane_api_flag2));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String response_server = response.body();
                Log.d("response_server",response_server + "");

                try {
                    openHelper = new DataBaseHelperClass_btnDownload_VersionCode(MainActivity.this);
                    database = openHelper.getWritableDatabase();

                    JSONObject jsonObject = new JSONObject(response_server);
                    JSONArray array = jsonObject.getJSONArray("data");

                    int count = array.length();
                    if(count!=0) {
                        truncateDatabase_Version();
                        for (int i = 0; i < count; i++) {

                            JSONObject object = array.getJSONObject(i);
                            set_and_get_version = new Set_and_Get_Version();
                            set_and_get_version.setV_code(object.getString(DataBaseHelperClass_btnDownload_VersionCode.VersionCode));
                            set_and_get_version.setStatus(object.getString(DataBaseHelperClass_btnDownload_VersionCode.Status));

                            database.execSQL("insert into "+DataBaseHelperClass_btnDownload_VersionCode.TABLE_NAME+"("
                                    + DataBaseHelperClass_btnDownload_VersionCode.VersionCode+","
                                    + DataBaseHelperClass_btnDownload_VersionCode.Status+") values ('"
                                    + set_and_get_version.getV_code()+"','"
                                    + set_and_get_version.getStatus()+"')");
                        }
                    }
                    database.close();
                    GetLoginDataFromServer(Mob_Num_Shared, deviceId);
                    Log.d("Get Version:", "Got the Application Version");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Log.e("NullPointerException", "" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("multipleResource",""+t.getMessage());
                call.cancel();
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void truncateDatabase_Version(){
        //dialog1.setProgress(20);
        openHelper = new DataBaseHelperClass_btnDownload_VersionCode(MainActivity.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_VersionCode.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_VersionCode.TABLE_NAME);
            Log.d("Database", "Down_Docs Database Truncated");
        } else {
            cursor.close();
        }

    }

    public void GetLoginDataFromServer(String MobNum, String IMEI){
        apiInterface_samyojaneAPI = APIClient.getClient(getString(R.string.samyojane_API_url)).create(APIInterface_SamyojaneAPI.class);

        Call<String> call = apiInterface_samyojaneAPI.doFn_Validate(getString(R.string.samyojane_api_flag1),getString(R.string.samyojane_api_flag2), MobNum, IMEI);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String response_server = response.body();
                Log.d("response_server",response_server + "");

                try {

                    int n=1;
                    openHelper = new DataBaseHelperClass_Credentials(MainActivity.this);
                    database = openHelper.getWritableDatabase();
                    JSONObject jsonObject = new JSONObject(response_server);
                    JSONArray array = jsonObject.getJSONArray("data");

                    int count = array.length();
                    if(count!=0) {
                        truncateDatabase();
                        for (int i = 0; i < count; i++) {

                            JSONObject object = array.getJSONObject(i);
                            set_and_get_data = new Set_and_Get_data();
                            set_and_get_data.setRI_name(object.getString(DataBaseHelperClass_Credentials.RI_Name));
                            set_and_get_data.setRI_IMEI_1(object.getString(DataBaseHelperClass_Credentials.RI_IMEI_1));
                            set_and_get_data.setRI_IMEI_2(object.getString(DataBaseHelperClass_Credentials.RI_IMEI_2));
                            set_and_get_data.setRI_UserName(object.getString(DataBaseHelperClass_Credentials.RI_UserName));
                            set_and_get_data.setRI_Pwd(object.getString(DataBaseHelperClass_Credentials.RI_Pwrd));
                            set_and_get_data.setIMEI_1(object.getString(DataBaseHelperClass_Credentials.VA_IMEI_1));
                            set_and_get_data.setIMEI_2(object.getString(DataBaseHelperClass_Credentials.VA_IMEI_2));
                            set_and_get_data.setUserName(object.getString(DataBaseHelperClass_Credentials.VA_UserName));
                            set_and_get_data.setPwd(object.getString(DataBaseHelperClass_Credentials.VA_Pwrd));
                            set_and_get_data.setDistrict_Code(object.getString(DataBaseHelperClass_Credentials.District_Code));
                            set_and_get_data.setDistrict(object.getString(DataBaseHelperClass_Credentials.District_Name));
                            set_and_get_data.setDistrict_k(object.getString(DataBaseHelperClass_Credentials.District_Name_k));
                            set_and_get_data.setTaluk_Code(object.getString(DataBaseHelperClass_Credentials.Taluk_Code));
                            set_and_get_data.setTaluk(object.getString(DataBaseHelperClass_Credentials.Taluk_Name));
                            set_and_get_data.setTaluk_k(object.getString(DataBaseHelperClass_Credentials.Taluk_Name_k));
                            set_and_get_data.setHobli_Code(object.getString(DataBaseHelperClass_Credentials.Hobli_Code));
                            set_and_get_data.setHobli(object.getString(DataBaseHelperClass_Credentials.Hobli_Name));
                            set_and_get_data.setHobli_k(object.getString(DataBaseHelperClass_Credentials.Hobli_Name_k));
                            set_and_get_data.setVa_Circle_Code(object.getString(DataBaseHelperClass_Credentials.VA_circle_Code));
                            set_and_get_data.setVA_Circle_Name(object.getString(DataBaseHelperClass_Credentials.VA_Circle_Name));
                            set_and_get_data.setVA_Circle_Name_k(object.getString(DataBaseHelperClass_Credentials.VA_Circle_Name_k));
                            set_and_get_data.setVA_Name(object.getString(DataBaseHelperClass_Credentials.VA_Name));
                            set_and_get_data.setE_kshana(object.getString(DataBaseHelperClass_Credentials.e_kshana));
                            set_and_get_data.setFlag(Integer.parseInt(object.getString(DataBaseHelperClass_Credentials.flag)));

                            database.execSQL("insert into " + DataBaseHelperClass_Credentials.TABLE_NAME
                                    + "(RI_name, RI_IMEI_1, RI_IMEI_2, RI_UserName, RI_Pwrd, IMEI_1, IMEI_2, UserName, Pwrd, "
                                    + "District_Code, District, District_k, Taluk_Code, Taluk, Taluk_k, Hobli_Code, Hobli, Hobli_k,"
                                    + "VA_circle_Code, VA_circle_ename, VA_circle_kname, VA_name, e_kshana, flag) values ('"
                                    + set_and_get_data.getRI_name() + "','" + set_and_get_data.getRI_IMEI_1() + "','" + set_and_get_data.getRI_IMEI_2() + "','" + set_and_get_data.getRI_UserName() + "','"
                                    + set_and_get_data.getRI_Pwd() + "','"+ set_and_get_data.getIMEI_1() + "','" + set_and_get_data.getIMEI_2() + "','" + set_and_get_data.getUserName() + "','"
                                    + set_and_get_data.getPwd() + "','" + set_and_get_data.getDistrict_Code() + "','" + set_and_get_data.getDistrict() + "','" + set_and_get_data.getDistrict_k() + "','" + set_and_get_data.getTaluk_Code() + "','"
                                    + set_and_get_data.getTaluk() + "','"+ set_and_get_data.getTaluk_k() + "','"  + set_and_get_data.getHobli_Code() + "','" + set_and_get_data.getHobli() + "','" + set_and_get_data.getHobli_k() + "','" + set_and_get_data.getVa_Circle_Code() + "','"
                                    + set_and_get_data.getVA_Circle_Name() + "','" + set_and_get_data.getVA_Circle_Name_k() + "','" + set_and_get_data.getVA_Name() + "','"+set_and_get_data.getE_kshana()+"','" + set_and_get_data.getFlag() + "')");

                            Log.d("Database", "Database Inserted "+n++);
                        }
                        database.close();
                        dialog.dismiss();

                        version_Result = check_Version_Code(version_Code);
                        if(version_Result==0) {
                            checkDatabase_new();
                        }else {
                            btnActivation.setVisibility(View.GONE);
                            tvAlert.setVisibility(View.VISIBLE);
                            tvAlert.setText(getString(R.string.version_does_not_match));
                            linearLayout.setVisibility(View.GONE);
                        }

                        //Toast.makeText(getApplicationContext(), "Data Retrieved Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        version_Result = check_Version_Code(version_Code);
                        dialog.dismiss();
                        if(version_Result==0) {
                            openHelper = new DataBaseHelperClass_Credentials(MainActivity.this);
                            database = openHelper.getWritableDatabase();
                            Cursor cursor = database.rawQuery("select * from "+DataBaseHelperClass_Credentials.TABLE_NAME, null);
                            if(cursor.getCount()>0) {
                                database.execSQL("delete from " + DataBaseHelperClass_Credentials.TABLE_NAME);
                            }else {
                                cursor.close();
                                Log.d("Database", "No Data Found");
                            }
                            Toast.makeText(getApplicationContext(), getString(R.string.invalid_user), Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(MainActivity.this, MainActivity_Select.class);
                            startActivity(intent);
                            finish();
                        }else {
                            btnActivation.setVisibility(View.GONE);
                            tvAlert.setVisibility(View.VISIBLE);
                            tvAlert.setText(getString(R.string.version_does_not_match));
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    Log.d("Error", String.valueOf(e));
                    runOnUiThread(() -> {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("multipleResource",""+t.getMessage());
                call.cancel();
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean hasPermissions() {

        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int result7 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED &&
                result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED &&
                result6 == PackageManager.PERMISSION_GRANTED && result7 == PackageManager.PERMISSION_GRANTED ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void requestPerms() {

        ActivityCompat.requestPermissions(this, new String[]
                {WRITE_EXTERNAL_STORAGE,
                        READ_PHONE_STATE,
                        ACCESS_FINE_LOCATION,
                        ACCESS_COARSE_LOCATION,
                        ACCESS_NETWORK_STATE,
                        INTERNET,
                        CAMERA}, PERMISSION_REQUEST_CODE_WRITE_STORAGE);
    }

    public void setLocale(String localeName) {
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        dialog1.cancel();
        Log.d("localeName", ""+localeName);
        if(localeName.equals("en")){
            if (e_kshana.equals("1")){
                Log.d("localeName1", "en");
                Intent i = new Intent(MainActivity.this, Selection_Module_VA.class);
                i.putExtra("district", district);
                i.putExtra("taluk", taluk);
                i.putExtra("hobli", hobli);
                i.putExtra("districtCode", districtCode);
                i.putExtra("talukCode", talukCode);
                i.putExtra("hobliCode", hobliCode);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("localeName",""+localeName);
                i.putExtra("IMEI_Num", IMEI_Num_Shared);
                i.putExtra("mob_Num", Mob_Num_Shared);
                startActivity(i);
            }else {
                Log.d("localeName1", "en");
                Intent i = new Intent(MainActivity.this, SecondScreen.class);
                i.putExtra("district", district);
                i.putExtra("taluk", taluk);
                i.putExtra("hobli", hobli);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("localeName",""+localeName);
                i.putExtra("IMEI_Num", IMEI_Num_Shared);
                i.putExtra("mob_Num", Mob_Num_Shared);
                startActivity(i);
            }
        }else if(localeName.equals("kn")){
            if (e_kshana.equals("1")){
                Log.d("localeName1", "kn");
                Intent i = new Intent(MainActivity.this, Selection_Module_VA.class);
                i.putExtra("district", district_k);
                i.putExtra("taluk", taluk_k);
                i.putExtra("hobli", hobli_k);
                i.putExtra("districtCode", districtCode);
                i.putExtra("talukCode", talukCode);
                i.putExtra("hobliCode", hobliCode);
                i.putExtra("VA_Circle_Name", VA_Circle_Name_k);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("localeName",""+localeName);
                i.putExtra("IMEI_Num", IMEI_Num_Shared);
                i.putExtra("mob_Num", Mob_Num_Shared);
                startActivity(i);
            }else {
                Log.d("localeName1", "kn");
                Intent i = new Intent(MainActivity.this, SecondScreen.class);
                i.putExtra("district", district_k);
                i.putExtra("taluk", taluk_k);
                i.putExtra("hobli", hobli_k);
                i.putExtra("VA_Circle_Name", VA_Circle_Name_k);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("localeName",""+localeName);
                i.putExtra("IMEI_Num", IMEI_Num_Shared);
                i.putExtra("mob_Num", Mob_Num_Shared);
                startActivity(i);
            }
        }else {
            if (e_kshana.equals("1")){
                Log.d("localeName1", ""+localeName);
                Intent i = new Intent(MainActivity.this, Selection_Module_VA.class);
                i.putExtra("district", district);
                i.putExtra("taluk", taluk);
                i.putExtra("hobli", hobli);
                i.putExtra("districtCode", districtCode);
                i.putExtra("talukCode", talukCode);
                i.putExtra("hobliCode", hobliCode);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("localeName",""+localeName);
                i.putExtra("IMEI_Num", IMEI_Num_Shared);
                i.putExtra("mob_Num", Mob_Num_Shared);
                startActivity(i);
            }else {
                Log.d("localeName1", ""+localeName);
                Intent i = new Intent(MainActivity.this, SecondScreen.class);
                i.putExtra("district", district);
                i.putExtra("taluk", taluk);
                i.putExtra("hobli", hobli);
                i.putExtra("VA_Circle_Name", VA_Circle_Name);
                i.putExtra("va_Circle_Code", va_Circle_Code);
                i.putExtra("VA_Name", VA_Name);
                i.putExtra("localeName",""+localeName);
                i.putExtra("IMEI_Num", IMEI_Num_Shared);
                i.putExtra("mob_Num", Mob_Num_Shared);
                startActivity(i);
            }
        }
    }

    public void setLocale_RI(String localeName) {
        Locale myLocale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        dialog1.cancel();
        Log.d("localeName", ""+localeName);
        if(localeName.equals("en")){
            Log.d("localeName1", "en");
            Intent i = new Intent(MainActivity.this, RI_SecondScreen.class);
            i.putExtra("districtCode", district);
            i.putExtra("taluk", taluk);
            i.putExtra("hobli", hobli);
            i.putExtra("VA_Circle_Name", VA_Circle_Name);
            i.putExtra("va_Circle_Code", va_Circle_Code);
            i.putExtra("RI_Name", RI_Name);
            i.putExtra("deviceId", deviceId);
            i.putExtra("localeName",""+localeName);
            i.putExtra("IMEI_Num", IMEI_Num_Shared);
            i.putExtra("mob_Num", Mob_Num_Shared);
            startActivity(i);
        }else if(localeName.equals("kn")){
            Log.d("localeName1", "kn");
            Intent i = new Intent(MainActivity.this, RI_SecondScreen.class);
            i.putExtra("districtCode", district_k);
            i.putExtra("taluk", taluk_k);
            i.putExtra("hobli", hobli_k);
            i.putExtra("VA_Circle_Name", VA_Circle_Name_k);
            i.putExtra("va_Circle_Code", va_Circle_Code);
            i.putExtra("RI_Name", RI_Name);
            i.putExtra("deviceId", deviceId);
            i.putExtra("localeName",""+localeName);
            i.putExtra("IMEI_Num", IMEI_Num_Shared);
            i.putExtra("mob_Num", Mob_Num_Shared);
            startActivity(i);
        }else {
            Log.d("localeName1", ""+localeName);
            Intent i = new Intent(MainActivity.this, RI_SecondScreen.class);
            i.putExtra("districtCode", district);
            i.putExtra("taluk", taluk);
            i.putExtra("hobli", hobli);
            i.putExtra("VA_Circle_Name", VA_Circle_Name);
            i.putExtra("va_Circle_Code", va_Circle_Code);
            i.putExtra("RI_Name", RI_Name);
            i.putExtra("deviceId", deviceId);
            i.putExtra("localeName",""+localeName);
            i.putExtra("IMEI_Num", IMEI_Num_Shared);
            i.putExtra("mob_Num", Mob_Num_Shared);
            startActivity(i);
        }
    }

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.want_to_change_login))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, MainActivity_Select.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
        alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }
}
