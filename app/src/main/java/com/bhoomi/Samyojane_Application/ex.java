package com.bhoomi.Samyojane_Application;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ex extends AppCompatActivity {

    String ServOTP;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        sendOtpFromServer_APTHA();

    }

    private void sendOtpFromServer_APTHA() {
//        APIInterface apiInterface = APIClient.getClient(getResources().getString(R.string.server_url)).create(APIInterface.class);
//        Call<OtpResponse> call = apiInterface.FnSendOtp(Constants.dept_user_name,Constants.password, mobileNumber, msg);

        APIInterface apiInterface = APIClient.getClient("").create(APIInterface.class);
        Call<OtpResponse> call = apiInterface.FnSendOtpApthamitra("apthamitra@2020", "1d9294b9-15ad-43fd-899c-9fb5be9437c5", "9141410787", "hash");

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(@NotNull Call<OtpResponse> call, @NotNull Response<OtpResponse> response) {

                if (response.isSuccessful()) {
                    OtpResponse otpResponse = response.body();
                    Log.d("otpResponse",""+otpResponse);
                    assert otpResponse != null;
                    String otpres = otpResponse.getFN_GET_SEND_OTPResult().trim();
                    Log.d("otpres", otpres + "");
                    OtpDataResponse otpDataResponse = new Gson().fromJson(otpres, OtpDataResponse.class);
                    boolean status = otpDataResponse.issTATUS();
                    String statusCode = otpDataResponse.getCODE().trim();
                    String MSG = otpDataResponse.getMSG();
                    ServOTP = otpDataResponse.getOTP();

                    if (status && statusCode.equalsIgnoreCase("200")) {
                        Log.d("statusCode", ""+statusCode+", OTP: "+ ServOTP);

                    } else {
                        String errMsg;

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<OtpResponse> call, @NotNull Throwable throwable) {
                Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }

        });
    }


}