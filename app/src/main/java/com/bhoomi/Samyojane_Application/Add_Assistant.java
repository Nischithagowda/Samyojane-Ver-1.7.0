package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class Add_Assistant extends AppCompatActivity {

    ProgressDialog dialog;
    String district, taluk, hobli, VA_Circle_Name, localeName, VA_Name, isVA, districtCode, talukCode, hobliCode, va_Circle_Code;
    EditText etAssName, etAssMobile, etAssPassword;
    Button btnAdd, btnViewAss;
    String assName, assMobile, assPass, uName_get;
    String serv_OTP, serv_MSG, serv_CODE;
    int dCode, tCode, hCode, vcCode;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    Set_and_Get_E_Kshana set_and_get_e_kshana;

    APIInterface apiInterface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_assistant);

        etAssName = findViewById(R.id.etAssName);
        etAssMobile = findViewById(R.id.etAssMobile);
        etAssPassword = findViewById(R.id.etAssPassword);
        btnAdd = findViewById(R.id.btnAdd);
        btnViewAss = findViewById(R.id.btnViewAss);

        Intent i = getIntent();
        district = i.getStringExtra("district");
        taluk = i.getStringExtra("taluk");
        hobli = i.getStringExtra("hobli");
        districtCode = i.getStringExtra("districtCode");
        talukCode = i.getStringExtra("talukCode");
        hobliCode = i.getStringExtra("hobliCode");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Name = i.getStringExtra("VA_Name");
        localeName = i.getStringExtra("localeName");
        isVA = i.getStringExtra("isVA");
        uName_get = i.getStringExtra("uName_get");

        if (districtCode!=null && talukCode!=null && hobliCode!=null && va_Circle_Code!=null) {
            dCode = Integer.parseInt(districtCode);
            tCode = Integer.parseInt(talukCode);
            hCode = Integer.parseInt(hobliCode);
            vcCode = Integer.parseInt(va_Circle_Code);
        }

        Log.d("districtCode", ""+districtCode);
        Log.d("talukCode", ""+talukCode);
        Log.d("hobliCode", ""+hobliCode);

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

        openHelper = new DataBaseHelperClass_btnDownload_E_Kshana(this);
        database = openHelper.getReadableDatabase();

        btnAdd.setOnClickListener(v -> {

            assName = etAssName.getText().toString();
            assMobile = etAssMobile.getText().toString();
            assPass = etAssPassword.getText().toString();

            int passLength;
            if (TextUtils.isEmpty(assName)){
                etAssName.setError(getString(R.string.field_canno_null));
            } else {
              if (TextUtils.isEmpty(assMobile)){
                  etAssMobile.setError(getString(R.string.field_canno_null));
              } else {
                  if (!isValidMobile(assMobile)) {
                      etAssMobile.setError(getString(R.string.enter_valid_phone_num));
                  } else {
                      if (TextUtils.isEmpty(assPass)){
                          etAssPassword.setError(getString(R.string.enter_valid_phone_num));
                      }else {
                          passLength = assPass.length();
                          if (passLength>=4){
                              if (isNetworkAvailable()){
                                  dialog.show();
                                  getOTP(assMobile);
                              } else {
                                  buildAlert_Internet();
                              }
                          } else {
                              etAssPassword.setError(getString(R.string.incorrect_value));
                          }
                      }
                  }
              }
            }
        });

        btnViewAss.setOnClickListener(v -> {
            if (isNetworkAvailable()){
                dialog.show();
                getAssistant();
            }else {
                buildAlert_Internet();
            }
        });
    }

    public void getAssistant(){
        apiInterface = APIClient.getClient(getString(R.string.add_assistant_url)).create(APIInterface.class);
        Call<String> call = apiInterface.doGetAssistant(dCode, tCode, hCode, vcCode);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                Log.d("TAG", response.code() + "");

                String multipleResource = response.body();
                Log.d("MemberDetails", multipleResource + "");

                if (multipleResource!=null && !multipleResource.equals("0")) {
                    try {
                        truncateDatabase_GetAssistant();
                        JSONObject jsonObject = new JSONObject(multipleResource);
                        Log.d("jObj_output", "" + jsonObject);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("jsonArray_output", "" + jsonArray);

                        int count = jsonArray.length();

                        int increase=1;
                        if (count != 0) {
                            for (int i = 0; i < count; i++) {
                                Log.d("increase",""+increase);
                                JSONObject object = jsonArray.getJSONObject(i);

                                set_and_get_e_kshana = new Set_and_Get_E_Kshana();
                                set_and_get_e_kshana.setA_Dist_code(""+dCode);
                                set_and_get_e_kshana.setA_Taluk_code(""+tCode);
                                set_and_get_e_kshana.setA_Hobli_code(""+hCode);
                                set_and_get_e_kshana.setA_VA_circle_code(""+vcCode);
                                set_and_get_e_kshana.setA_Asst_Name(object.getString(DataBaseHelperClass_btnDownload_E_Kshana.A_Asst_Name));
                                set_and_get_e_kshana.setA_Asst_MobileNo(object.getString(DataBaseHelperClass_btnDownload_E_Kshana.A_Asst_MobileNo));

                                database.execSQL("insert into " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_GET_Assistant
                                        + "("+DataBaseHelperClass_btnDownload_E_Kshana.A_Dist_code+","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.A_Taluk_code+","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.A_Hobli_code+","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.A_VA_circle_code+","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.A_Asst_Name+","
                                        + DataBaseHelperClass_btnDownload_E_Kshana.A_Asst_MobileNo+") values ('"
                                        + set_and_get_e_kshana.getA_Dist_code() + "','"
                                        + set_and_get_e_kshana.getA_Taluk_code() + "','"
                                        + set_and_get_e_kshana.getA_Hobli_code() + "','"
                                        + set_and_get_e_kshana.getA_VA_circle_code() + "','"
                                        + set_and_get_e_kshana.getA_Asst_Name() + "','"
                                        + set_and_get_e_kshana.getA_Asst_MobileNo()+"')");
                                if (increase!=count) {
                                    increase++;
                                }
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), getString(R.string.could_not_found_assistants), Toast.LENGTH_SHORT).show();
                        }
                        //displayData_AfterItemSelected(rc_num);
                        if (increase==count){
                            Log.d("GetAssistant", "Got Complete Assistant List");
                            dialog.dismiss();
                            Intent i = new Intent(Add_Assistant.this, View_Assistant.class);
                            i.putExtra("district", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("hobli", hobli);
                            i.putExtra("districtCode", districtCode);
                            i.putExtra("talukCode", talukCode);
                            i.putExtra("hobliCode", hobliCode);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("isVA", "VA");
                            i.putExtra("localeName",""+localeName);
                            i.putExtra("uName_get", uName_get);
                            startActivity(i);
                        }

                    } catch (JSONException | NullPointerException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), getString(R.string.cannot_get_the_data_please_retry), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.could_not_found_assistants), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("multipleResource", "" + t.getMessage());
                dialog.dismiss();
                call.cancel();
            }
        });
    }

    public void truncateDatabase_GetAssistant(){

        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_E_Kshana.TABLE_GET_Assistant, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_GET_Assistant);
            Log.d("Database", "GetAssistant Table Truncated");
        }

    }

    public void getOTP(String assMobile1){

        apiInterface = APIClient.getClient(getString(R.string.otp_generate_url)).create(APIInterface.class);
        Call<OtpResponse> call = apiInterface.FnSendOtp(Constants.dept_user_name, Constants.password, assMobile1, getString(R.string.SMS_Text));

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                dialog.dismiss();
                Log.d("TAG", response.code() + "");
                try {
                    OtpResponse otpResponse = response.body();
                    Log.d("otpResponse", ""+otpResponse);
                    String otpres = otpResponse.getFNGETSENDOTPResult().trim();
                    Log.d("otpres", ""+otpres);

                    if (otpres.equals("INVALID Mobile Number")){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Add_Assistant.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.alert))
                                .setMessage(getString(R.string.inavlid_mobile_nnum))
                                .setIcon(R.drawable.ic_error_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    }else {
                        JSONObject jsonObject = new JSONObject(otpres);
                        Log.d("jObj_output", "" + jsonObject);

                        serv_OTP = jsonObject.getString("OTP");
                        serv_MSG = jsonObject.getString("MSG");
                        serv_CODE = jsonObject.getString("CODE");

                        if (serv_OTP != null && !serv_OTP.isEmpty()) {
                            Intent i = new Intent(Add_Assistant.this, OTP_Assistant.class);
                            i.putExtra("district", district);
                            i.putExtra("taluk", taluk);
                            i.putExtra("hobli", hobli);
                            i.putExtra("districtCode", districtCode);
                            i.putExtra("talukCode", talukCode);
                            i.putExtra("hobliCode", hobliCode);
                            i.putExtra("VA_Circle_Name", VA_Circle_Name);
                            i.putExtra("va_Circle_Code", va_Circle_Code);
                            i.putExtra("VA_Name", VA_Name);
                            i.putExtra("isVA", isVA);
                            i.putExtra("localeName", "" + localeName);
                            i.putExtra("assName", assName);
                            i.putExtra("assMobile", assMobile);
                            i.putExtra("assPass", assPass);
                            i.putExtra("serv_OTP", serv_OTP);
                            i.putExtra("uName_get", uName_get);
                            startActivity(i);
                            finish();
                        } else if (serv_CODE.equals("800")) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Add_Assistant.this, R.style.MyDialogTheme);
                            builder.setTitle(getString(R.string.alert))
                                    .setMessage(getString(R.string.this_person_was_already_added_as_your_ass))
                                    .setIcon(R.drawable.ic_error_black_24dp)
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.ok), (dialog, id) -> dialog.cancel());
                            final AlertDialog alert = builder.create();
                            alert.show();
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.cannot_add_please_retry), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), getString(R.string.cannot_add_please_retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Log.d("multipleResource", "" + t.getMessage());
                dialog.dismiss();
                call.cancel();
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

    private boolean isValidMobile(String phone) {
        boolean check;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() == 10;
        } else {
            check=false;
        }
        return check;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
