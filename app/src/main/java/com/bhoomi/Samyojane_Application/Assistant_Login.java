package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Assistant_Login extends AppCompatActivity {
    TextView version;
    @SuppressLint("StaticFieldLeak")
    public static EditText userName, pwd;
    String RI_Name,district, taluk, hobli, VA_Circle_Name, VA_Name;
    String uName_get, pwd_get;
    String version_Code;
    String districtCode, talukCode, hobliCode, va_Circle_Code;
    Button btnSubmit_Assistant;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    LinearLayout linearLayout;
    ProgressDialog dialog;
    InputMethodManager imm;
    InputMethodSubtype ims;
    String distCode_ass, talCode_ass, hobCode_ass, VACircleCode_ass;
    String[] lang;
    Dialog dialog1;

    APIInterface apiInterface;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assistant_login);

        version = findViewById(R.id.version);
        userName = findViewById(R.id.userName);
        pwd = findViewById(R.id.pwd);
        linearLayout = findViewById(R.id.userLayout);
        btnSubmit_Assistant = findViewById(R.id.btnSubmit_Assistant);

        version_Code = getString(R.string.versionCode);
        Log.d("version_Code11",""+version_Code);
        String ver = getString(R.string.version)+version_Code;
        version.setText(ver);

        pwd.setTransformationMethod(new AsteriskPasswordTransformationMethod());

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

        btnSubmit_Assistant.setOnClickListener(v -> {

            Log.d("btnSubmit_Assistant","Clicked");
            uName_get = String.valueOf(userName.getText());
            pwd_get = String.valueOf(pwd.getText());
            Log.d("Entered Value", "" + uName_get);
            Log.d("Entered Value", "" + pwd_get);

            if (TextUtils.isEmpty(uName_get)){
                userName.setError(getString(R.string.field_canno_null));
            } else if(uName_get.length()!=10){
                userName.setError(getString(R.string.incorrect_value));
            } else if (TextUtils.isEmpty(pwd_get)){
                pwd.setError(getString(R.string.field_canno_null));
            } else {
                btnSubmit_Assistant.setText(getString(R.string.login_c));
                pwd.setError(null);

                if (isNetworkAvailable()) {
                    dialog.show();
                    ValidateAssistantLogin_Rest(uName_get, pwd_get);
                }else {
                    buildAlert_Internet();
                }
            }

        });
    }

    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new AsteriskPasswordTransformationMethod.PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
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

    public void ValidateAssistantLogin_Rest(String Username, String Pass){

        apiInterface = APIClient.getClient(getString(R.string.add_assistant_url)).create(APIInterface.class);
        Call<String> call = apiInterface.doValidateAssistant(""+Username, ""+Pass);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int responseCode = response.code();
                Log.d("TAG", response.code() + "");

                String multipleResource = response.body();
                Log.d("MemberDetails", multipleResource + "");

                if (multipleResource!=null && !multipleResource.equals("0")) {
                    try {
                        JSONObject jsonObject = new JSONObject(multipleResource);
                        Log.d("jObj_output", "" + jsonObject);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("jsonArray_output", "" + jsonArray);

                        int count = jsonArray.length();

                        if(count!=0) {
                            for (int i = 0; i < count; i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                distCode_ass = object.getString("A_Dist_code");
                                talCode_ass = object.getString("A_Taluk_code");
                                hobCode_ass = object.getString("A_Hobli_code");
                                VACircleCode_ass = object.getString("A_VA_circle_code");

                            }
                            btnSubmit_Assistant.setText(getString(R.string.login));
                            dialog.dismiss();
                            Intent i = new Intent(Assistant_Login.this, E_Kshana_MainScreen.class);
                            i.putExtra("distCode_ass",distCode_ass);
                            i.putExtra("talCode_ass",talCode_ass);
                            i.putExtra("hobCode_ass",hobCode_ass);
                            i.putExtra("VACircleCode_ass",VACircleCode_ass);
                            i.putExtra("uName_get", uName_get);
                            startActivity(i);
                            Log.d("ValidateAssistantLogin", "Assistant Login Success");
                        }else if(responseCode == 404 && multipleResource==null){
                            dialog.dismiss();
                            btnSubmit_Assistant.setText(getString(R.string.login));
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Assistant_Login.this, R.style.MyDialogTheme);
                            builder.setTitle(getString(R.string.alert))
                                    .setMessage(getString(R.string.service_not_avilable_at_this_moment))
                                    .setIcon(R.drawable.ic_error_black_24dp)
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.ok), (dialog, id) ->{
                                        pwd.setText("");
                                        pwd.setError(getString(R.string.pwd_error));
                                        dialog.cancel();
                                    });
                            final AlertDialog alert = builder.create();
                            alert.show();
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                        } else {
                            dialog.dismiss();
                            btnSubmit_Assistant.setText(getString(R.string.login));
                            final AlertDialog.Builder builder = new AlertDialog.Builder(Assistant_Login.this, R.style.MyDialogTheme);
                            builder.setTitle(getString(R.string.alert))
                                    .setMessage(getString(R.string.invalid_username_password))
                                    .setIcon(R.drawable.ic_error_black_24dp)
                                    .setCancelable(false)
                                    .setPositiveButton(getString(R.string.ok), (dialog, id) ->{
                                        pwd.setText("");
                                        pwd.setError(getString(R.string.pwd_error));
                                        dialog.cancel();
                                    });
                            final AlertDialog alert = builder.create();
                            alert.show();
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                        }

                    } catch (JSONException e) {
                        dialog.dismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Assistant_Login.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.alert))
                                .setMessage(getString(R.string.invalid_username_password))
                                .setIcon(R.drawable.ic_error_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) ->{
                                    pwd.setText("");
                                    pwd.setError(getString(R.string.pwd_error));
                                    dialog.cancel();
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                    }catch (NullPointerException e){
                        dialog.dismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Assistant_Login.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.alert))
                                .setMessage(getString(R.string.invalid_username_password))
                                .setIcon(R.drawable.ic_error_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) ->{
                                    pwd.setText("");
                                    pwd.setError(getString(R.string.pwd_error));
                                    dialog.cancel();
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                        Log.e("NullPointerException", ""+e.toString());
                    }
                } else {
                    //Toast.makeText(getApplicationContext(), getString(R.string.no_da_found), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Assistant_Login.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.alert))
                            .setMessage(getString(R.string.invalid_username_password))
                            .setIcon(R.drawable.ic_error_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, id) ->{
                                pwd.setText("");
                                pwd.setError(getString(R.string.pwd_error));
                                dialog.cancel();
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("multipleResource", "" + t.getMessage());
                dialog.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Assistant_Login.this, R.style.MyDialogTheme);
                builder.setTitle(getString(R.string.alert))
                        .setMessage(getString(R.string.invalid_username_password))
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), (dialog, id) ->{
                            pwd.setText("");
                            pwd.setError(getString(R.string.pwd_error));
                            dialog.cancel();
                        });
                final AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                call.cancel();
            }
        });
    }
}
