package com.bhoomi.Samyojane_Application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerify_New extends AppCompatActivity {

    EditText etOne, etTwo, etThree, etFour;

    String received_OTP, mob_Num, IMEI_Num;
    ContentLoadingProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        loadingProgress = findViewById(R.id.loadingProgress);
        etOne = findViewById(R.id.etOne);
        etTwo = findViewById(R.id.etTwo);
        etThree = findViewById(R.id.etThree);
        etFour = findViewById(R.id.etFour);

        Intent i = getIntent();
        etOne.setText(i.getStringExtra("e1"));
        etTwo.setText(i.getStringExtra("e2"));
        etThree.setText(i.getStringExtra("e3"));
        etFour.setText(i.getStringExtra("e4"));
        mob_Num = i.getStringExtra("mob_Num");
        IMEI_Num = i.getStringExtra("IMEI_Num");
        received_OTP = i.getStringExtra("received_OTP");

        if (isNetworkAvailable()) {
            //ValidateOtpFromServer(mob_Num, received_OTP);
            SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
            editor.putString(Constants.MOBILE_NUMBER, mob_Num);
            editor.putString(Constants.IMEI_NUMBER, IMEI_Num);
            editor.putString(Constants.OTP, received_OTP);
            editor.putBoolean(Constants.LOGIN_STATUS, true);
            editor.putInt(Constants.DesiCode_VA, 22);
            editor.putInt(Constants.DesiCode_RI, 19);
            editor.putBoolean(Constants.CasteMaster, false);
            editor.apply();

            Intent intent = new Intent(OtpVerify_New.this, MainActivity.class);
            intent.putExtra("mob_Num",mob_Num);
            intent.putExtra("IMEI_Num",IMEI_Num);
            startActivity(intent);
            finish();

        }else {
            buildAlert_Internet();
        }

    }

    private void ValidateOtpFromServer(String mobileNumber, String received_otp) {
        APIInterface apiInterface = APIClient.getClient(getResources().getString(R.string.otp_generate_url)).create(APIInterface.class);
        Call<OtpResponse> call = apiInterface.FN_VALIDATE_OTP(Constants.dept_user_name,Constants.password, mobileNumber, received_otp);

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(@NotNull Call<OtpResponse> call, @NotNull Response<OtpResponse> response) {

                if (response.isSuccessful()) {
                    OtpResponse otpResponse = response.body();
                    assert otpResponse != null;
                    String otpres = otpResponse.getFN_VALIDATE_OTPResult().trim();
                    Log.d("otpValidateResponse", otpres + "");
                    OtpDataResponse otpDataResponse = new Gson().fromJson(otpres, OtpDataResponse.class);
                    boolean status = otpDataResponse.isVALID();
                    String statusCode = otpDataResponse.getCODE().trim();
                    String MSG = otpDataResponse.getMSG();

                    if (statusCode.equalsIgnoreCase("800")){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(OtpVerify_New.this, R.style.MyDialogTheme);
                        builder.setTitle(getString(R.string.alert))
                                .setMessage(getString(R.string.cannot_validate_please_try_later))
                                .setIcon(R.drawable.ic_error_black_24dp)
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), (dialog, id) ->{
                                    dialog.cancel();
                                    finish();
                                    Intent i = new Intent(OtpVerify_New.this, MainActivity_Select.class);
                                    startActivity(i);
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    } else if (status && statusCode.equalsIgnoreCase("200")) {
                        Log.d("status",""+ true);
                        Log.d("statusCode",""+statusCode);
                        Log.d("MSG",""+MSG);

                        SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
                        editor.putString(Constants.MOBILE_NUMBER, mobileNumber);
                        editor.putString(Constants.IMEI_NUMBER, IMEI_Num);
                        editor.putString(Constants.OTP, received_OTP);
                        editor.putBoolean(Constants.LOGIN_STATUS, true);
                        editor.putInt(Constants.DesiCode_VA, 22);
                        editor.putInt(Constants.DesiCode_RI, 19);
                        editor.apply();

                        Intent i = new Intent(OtpVerify_New.this, MainActivity.class);
                        i.putExtra("mob_Num",mob_Num);
                        i.putExtra("IMEI_Num",IMEI_Num);
                        startActivity(i);
                        finish();

                    } else {
                        Log.d("statusCode",""+statusCode);
                        Log.d("MSG",""+MSG);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<OtpResponse> call, @NotNull Throwable throwable) {
                Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();

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

    @Override
    public void onBackPressed() {
    }

}
