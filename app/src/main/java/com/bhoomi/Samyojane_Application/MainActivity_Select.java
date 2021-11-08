package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity_Select extends AppCompatActivity {
    CardView card_view;
    RadioGroup radioGroup;
    RadioButton rbVA_RI, rbAssistant;
    String option_Flag;
    EditText etMobNum, etIMEINum;
    Button btnValidate, btnCancel;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    View view;
    LayoutInflater inflater;
    String mob_Num, IMEI_Num;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    HashMap<String, String> hashMap;
    ProgressDialog dialog;
    APIInterface apiInterface;
    TextView version, tvAlert;
    String version_Code, VC_Status;
    int version_Result;
    Set_and_Get_Version set_and_get_version;
    private String received_OTP;
    String ServOTP;
    private static final int PERMISSION_REQUEST_CODE_WRITE_STORAGE = 2051;
    SharedPreferences sharedPreferences;
    String IMEI_Shared, mobile_Shared;
    Boolean LOGIN_STATUS;
    int service_Flag=0, broadcast_Flag=0;
    char [] c1;

    @SuppressLint("InflateParams")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        card_view = findViewById(R.id.card_view);
        radioGroup = findViewById(R.id.radioGroup);
        rbVA_RI = findViewById(R.id.rbVA_RI);
        rbAssistant = findViewById(R.id.rbAssistant);
        version = findViewById(R.id.version);
        tvAlert = findViewById(R.id.tvAlert);

        tvAlert.setVisibility(View.GONE);
        card_view.setVisibility(View.GONE);

        version_Code = getString(R.string.versionCode);
        Log.d("version_Code11",""+version_Code);
        String str = getString(R.string.version)+version_Code;
        version.setText(str);

        if (hasPermissions()) {
            Log.d("Permission","hasPermissions");
        } else {
            requestPerms();
        }

        inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.card_view_alertwindow, null);
        etMobNum = view.findViewById(R.id.etMobNum);
        etIMEINum = view.findViewById(R.id.etIMEINum);
        btnValidate = view.findViewById(R.id.btnValidate);
        btnCancel = view.findViewById(R.id.btnCancel);

        openHelper = new DataBaseHelperClass_Credentials(this);
        database = openHelper.getWritableDatabase();

        hashMap = new HashMap<>();

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        dialog.show();
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.rbVA_RI) {
                option_Flag = getString(R.string.va_ri);
                rbVA_RI.setChecked(false);

                builder = new AlertDialog.Builder(this);
                builder.setView(view)
                        .setCancelable(false);
                alertDialog = builder.create();
                alertDialog.show();

            } else if (checkedId == R.id.rbAssistant) {
                option_Flag = getString(R.string.assistant);
                rbAssistant.setChecked(false);

                Intent i = new Intent(MainActivity_Select.this, Assistant_Login.class);
                startActivity(i);
            }
            Log.d("option_Flag", "" + option_Flag);
        });

        btnCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        });

        btnValidate.setOnClickListener(v -> {
            mob_Num = etMobNum.getText().toString();
            IMEI_Num = etIMEINum.getText().toString();

            if (mob_Num.isEmpty()) {
                etMobNum.setError(getString(R.string.field_canno_null));
            } else if (mob_Num.length() != 10) {
                etMobNum.setError(getString(R.string.incorrect_value));
            } else if (IMEI_Num.isEmpty()) {
                etIMEINum.setError(getString(R.string.field_canno_null));
            } else if (IMEI_Num.length() < 15) {
                etIMEINum.setError(getString(R.string.incorrect_value));
            } else {
                if (isNetworkAvailable()) {
                    dialog.show();
//                    hashMap.put("IMEI", IMEI_Num);
//                    hashMap.put("MobNum", mob_Num);
//                    new GetDataFromServer().execute(getString(R.string.main_url));
                    received_OTP = "";
                    ServOTP = "";
                    ValidateLogin(mob_Num, IMEI_Num);
                } else {
                    buildAlert_Internet();
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
                }
                else {
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

    @SuppressLint("StaticFieldLeak")
    class GetVersion extends AsyncTask<String, Void, JSONObject> {
        JSONObject jsonObject;
        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                JParserAdv jParserAdv = new JParserAdv();
                jsonObject = jParserAdv.makeHttpRequest(getString(R.string.main_url_version), "GET", hashMap);
            } catch (NullPointerException e){
                dialog.dismiss();
                Log.e("NullPointerException", ""+e.toString());
            }
            return jsonObject;
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try{
                openHelper = new DataBaseHelperClass_btnDownload_VersionCode(MainActivity_Select.this);
                database = openHelper.getWritableDatabase();

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
                        if (i==count-1) {
                            dialog.dismiss();
                            Log.d("Database", "VersionCode_Database Inserted");
                            version_Result = check_Version_Code(version_Code);
                            if(version_Result==0) {
                                card_view.setVisibility(View.VISIBLE);
                                tvAlert.setVisibility(View.GONE);
                            }else {
                                card_view.setVisibility(View.GONE);
                                tvAlert.setVisibility(View.VISIBLE);
                                tvAlert.setText(getString(R.string.version_does_not_match));
                            }
                        }
                    }
                }
                database.close();
                Log.d("Get Version:", "Got the Application Version");

            } catch (JSONException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show());
            }
        }
    }

    public void truncateDatabase_Version(){
        //dialog1.setProgress(20);
        openHelper = new DataBaseHelperClass_btnDownload_VersionCode(MainActivity_Select.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_VersionCode.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_VersionCode.TABLE_NAME);
            Log.d("Database", "Down_Docs Database Truncated");
        } else {
            cursor.close();
        }

    }

    public void truncateDatabase(){
        openHelper = new DataBaseHelperClass_Credentials(MainActivity_Select.this);
        database = openHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_Credentials.TABLE_NAME, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_Credentials.TABLE_NAME);
            Log.d("Database", "Database Truncated");
        } else {
            cursor.close();
        }
    }

    public void ValidateLogin(String MobNum, String IMEINum){
        apiInterface = APIClient.getClient(getString(R.string.samyojane_API_url)).create(APIInterface.class);

        Call<String> call = apiInterface.doFn_Validate(getString(R.string.api_flag1),getString(R.string.api_flag2), MobNum, IMEINum);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG",response.code()+"");

                String response_server = response.body();
                Log.d("response_server",response_server + "");

                if(response_server == null){
                    dialog.dismiss();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_Select.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.alert))
                            .setMessage(getString(R.string.invalid_user))
                            .setIcon(R.drawable.ic_error_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
                    TextView msgTxt = alert.findViewById(android.R.id.message);
                    msgTxt.setTextSize(16);
                } else if (response_server.equals(getString(R.string.access_denied_msg))){
                    dialog.dismiss();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_Select.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.alert))
                            .setMessage(getString(R.string.access_denied_msg))
                            .setIcon(R.drawable.ic_error_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
                    TextView msgTxt = alert.findViewById(android.R.id.message);
                    msgTxt.setTextSize(16);
                } else {
                    Runnable runnable = () -> Toast.makeText(getApplicationContext(), getString(R.string.error_500), Toast.LENGTH_SHORT).show();

                    try {

                        truncateDatabase();
                        JSONObject jsonObject = new JSONObject(response_server);
                        //Log.d("jObj_output", "" + jsonObject);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("jsonArray_output", "" + jsonArray);

                        int count = jsonArray.length();
                        if (count != 0) {
                            //Toast.makeText(getApplicationContext(), "calling sendOtpFromServer", Toast.LENGTH_SHORT).show();
                            sendOtpFromServer(MobNum);
                        } else{
                            dialog.dismiss();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_Select.this, R.style.MyDialogTheme);
                            builder.setTitle(getString(R.string.alert))
                                    .setMessage(getString(R.string.invalid_user))
                                    .setIcon(R.drawable.ic_error_black_24dp)
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                            final AlertDialog alert = builder.create();
                            alert.show();
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16);
                            TextView msgTxt = alert.findViewById(android.R.id.message);
                            msgTxt.setTextSize(16);
                        }

                    } catch (JSONException e) {
                        dialog.dismiss();
                        Log.e("JSON Parser", "Error parsing data " + e.toString());
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (NullPointerException e) {
                        dialog.dismiss();
                        runOnUiThread(runnable);
                        e.printStackTrace();
                        Log.e("NullPointerException", "" + e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("multipleResource",""+t.getMessage());
                dialog.dismiss();
                call.cancel();
                Toast.makeText(getApplicationContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendOtpFromServer(String mobileNumber) {
//        APIInterface apiInterface = APIClient.getClient(getResources().getString(R.string.server_url)).create(APIInterface.class);
//        Call<OtpResponse> call = apiInterface.FnSendOtp(Constants.dept_user_name,Constants.password, mobileNumber);

        apiInterface = APIClient.getClient(getString(R.string.otp_generate_url)).create(APIInterface.class);
        Call<OtpResponse> call = apiInterface.FnSendOtp(Constants.dept_user_name, Constants.password, mobileNumber, getString(R.string.SMS_Text_VA_RI));
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(@NotNull Call<OtpResponse> call, @NotNull Response<OtpResponse> response) {

                if (response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "sendOtpFromServer response came", Toast.LENGTH_SHORT).show();
                    OtpResponse otpResponse = response.body();
                    assert otpResponse != null;
                    String otpres = otpResponse.getFNGETSENDOTPResult().trim();
                    Log.d("otpres", otpres + "");
                    OtpDataResponse otpDataResponse = new Gson().fromJson(otpres, OtpDataResponse.class);
                    boolean status = otpDataResponse.issTATUS();
                    String statusCode = otpDataResponse.getCODE().trim();
                    String MSG = otpDataResponse.getMSG();
                    ServOTP = otpDataResponse.getOTP();
                    received_OTP = ServOTP;

                    service_Flag=1;
                    broadcast_Flag = 1;
                    Log.d("Receiver_Flag", "service_Flag: "+service_Flag);
                    //Toast.makeText(getApplicationContext(), "service_Flag :1", Toast.LENGTH_SHORT).show();
                    c1 =  received_OTP.toCharArray();
                    if (broadcast_Flag==1 && ServOTP!=null){
                        dialog.dismiss();
                        alertDialog.dismiss();
                        if (view.getParent() != null) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        }
                        LocalBroadcastManager.getInstance(MainActivity_Select.this).unregisterReceiver(receiver);
                        Intent i = new Intent(MainActivity_Select.this, OtpVerify_New.class);
                        i.putExtra("e1", "" + c1[0]);
                        i.putExtra("e2", "" + c1[1]);
                        i.putExtra("e3", "" + c1[2]);
                        i.putExtra("e4", "" + c1[3]);
                        i.putExtra("mob_Num", mob_Num);
                        i.putExtra("received_OTP", "" + received_OTP);
                        i.putExtra("IMEI_Num", IMEI_Num);
                        startActivity(i);
                        finish();
                    }

//                    if (status && statusCode.equalsIgnoreCase("200")) {
//                        service_Flag=1;
//                        Log.d("Receiver_Flag", "service_Flag: "+service_Flag);
//                        //Toast.makeText(getApplicationContext(), "service_Flag :1", Toast.LENGTH_SHORT).show();
//
//                        if (broadcast_Flag==1 && ServOTP!=null){
//                            dialog.dismiss();
//                            alertDialog.dismiss();
//                            if (view.getParent() != null) {
//                                ((ViewGroup) view.getParent()).removeView(view);
//                            }
//                            LocalBroadcastManager.getInstance(MainActivity_Select.this).unregisterReceiver(receiver);
//                            Intent i = new Intent(MainActivity_Select.this, OtpVerify_New.class);
//                            i.putExtra("e1", "" + c1[0]);
//                            i.putExtra("e2", "" + c1[1]);
//                            i.putExtra("e3", "" + c1[2]);
//                            i.putExtra("e4", "" + c1[3]);
//                            i.putExtra("mob_Num", mob_Num);
//                            i.putExtra("received_OTP", "" + received_OTP);
//                            i.putExtra("IMEI_Num", IMEI_Num);
//                            startActivity(i);
//                            finish();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        String errMsg;
//                        if (statusCode.equalsIgnoreCase("600")) {
//                            errMsg = getString(R.string.invali_mobile_error);
//                            displayAlert(errMsg);
//                        }
//                        else if (statusCode.equalsIgnoreCase("400")) {
//                            errMsg = getString(R.string.sms_serivce_error);
//                            displayAlert(errMsg);
//                        } else if (statusCode.equalsIgnoreCase("500")) {
//                            errMsg = getString(R.string.sms_serivce_error);
//                            displayAlert(errMsg);
//                        } else if (statusCode.equalsIgnoreCase("300")) {
//                            errMsg = getString(R.string.sms_response_empty);
//                            displayAlert(errMsg);
//                        } else if(statusCode.equalsIgnoreCase("800")) {
//                            errMsg = getString(R.string.invalid_user);
//                            displayAlert(errMsg);
//                        } else if (statusCode.equalsIgnoreCase("900")){
//                            displayAlert(MSG);
//                        } else {
//                            Toast.makeText(getApplicationContext(), ""+status+statusCode, Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<OtpResponse> call, @NotNull Throwable throwable) {
                dialog.dismiss();
                String exp = throwable.getLocalizedMessage();
                assert exp != null;
                if (exp.contains("timeout")){
                    displayAlert(getString(R.string.time_out));
                } else {
                    displayAlert(exp+", Please Try Again...");
                }
                //Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void displayAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.alert))
                .setIcon(R.drawable.ic_error_black_24dp)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.requireNonNull(intent.getAction()).equalsIgnoreCase("otp")) {
                String message = intent.getStringExtra("message");
                String senderNum = intent.getStringExtra("senderNum");
                Log.d("message",""+message);
                Log.d("senderNum",""+senderNum);
                assert message != null;
                if (message.contains("SAMYOJANE")){
                    received_OTP = message.replaceAll("[^0-9]","");
                    Log.d("ServOTP","OTP: "+ServOTP);
                    Log.d("received_OTP","OTP: "+received_OTP);
                    c1 =  received_OTP.toCharArray();

                    broadcast_Flag = 1;
                    Log.d("Receiver_Flag","broadcast_Flag: "+broadcast_Flag);
                    //Toast.makeText(getApplicationContext(), "broadcast_Flag :1", Toast.LENGTH_SHORT).show();
                    if (service_Flag==1 && received_OTP!=null) {
                        dialog.dismiss();
                        alertDialog.dismiss();
                        if (view.getParent() != null) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        }
                        LocalBroadcastManager.getInstance(MainActivity_Select.this).unregisterReceiver(receiver);
                        Intent i = new Intent(MainActivity_Select.this, OtpVerify_New.class);
                        i.putExtra("e1", "" + c1[0]);
                        i.putExtra("e2", "" + c1[1]);
                        i.putExtra("e3", "" + c1[2]);
                        i.putExtra("e4", "" + c1[3]);
                        i.putExtra("mob_Num", mob_Num);
                        i.putExtra("received_OTP", "" + received_OTP);
                        i.putExtra("IMEI_Num", IMEI_Num);
                        startActivity(i);
                        finish();
                    }
                }else {
                    Log.d("senderNum","Invalid Message");
                }
            }
        }
    };

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();

        if (hasPermissions()){
            if (sharedPreferences.contains(Constants.IMEI_NUMBER) && sharedPreferences.contains(Constants.MOBILE_NUMBER) && sharedPreferences.contains(Constants.LOGIN_STATUS)) {
                dialog.dismiss();
                Log.d("LOGIN_STATUS", "old one");
                mobile_Shared = sharedPreferences.getString(Constants.MOBILE_NUMBER, "");
                IMEI_Shared = sharedPreferences.getString(Constants.IMEI_NUMBER, "");
                LOGIN_STATUS = sharedPreferences.getBoolean(Constants.LOGIN_STATUS, false);

                Log.d("mobile_Shared", "mobileNum: " + mobile_Shared);
                Log.d("IMEI_Shared", "IMEI_Shared: " + IMEI_Shared);
                Log.d("LOGIN_STATUS", "LOGIN_STATUS: "+ LOGIN_STATUS);

                Intent i = new Intent(MainActivity_Select.this, MainActivity.class);
                i.putExtra("mob_Num",mobile_Shared);
                i.putExtra("IMEI_Num",IMEI_Shared);
                startActivity(i);
                finish();

            } else {
                dialog.dismiss();
                Log.d("LOGIN_STATUS", "Fresh one");
                LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
                if (isNetworkAvailable()) {
                    dialog.show();
                    new GetVersion().execute(getString(R.string.main_url));
                } else {
                    version_Result = check_Version_Code(version_Code);
                    if (version_Result == 0) {
                        card_view.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
                    } else {
                        card_view.setVisibility(View.GONE);
                        tvAlert.setVisibility(View.VISIBLE);
                        tvAlert.setText(getString(R.string.version_does_not_match));
                    }
                }
            }
        } else {
            requestPerms();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean hasPermissions() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result7 = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        int result8 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        int result9 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int result10 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result11 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED &&
                result3 == PackageManager.PERMISSION_GRANTED &&
                result4 == PackageManager.PERMISSION_GRANTED &&
                result5 == PackageManager.PERMISSION_GRANTED &&
                result6 == PackageManager.PERMISSION_GRANTED &&
                result7 == PackageManager.PERMISSION_GRANTED &&
                result8 == PackageManager.PERMISSION_GRANTED &&
                result9 == PackageManager.PERMISSION_GRANTED &&
                result10 == PackageManager.PERMISSION_GRANTED &&
                result11 == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void requestPerms() {

        ActivityCompat.requestPermissions(this, new String[]
                {INTERNET,CAMERA,ACCESS_COARSE_LOCATION,
                        ACCESS_FINE_LOCATION,
                        ACCESS_NETWORK_STATE,
                        READ_PHONE_STATE,
                        RECEIVE_SMS,
                        READ_SMS,
                        SEND_SMS,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE
                }, PERMISSION_REQUEST_CODE_WRITE_STORAGE);
    }
}
