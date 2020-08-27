package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("Registered")
public class OTP_Assistant extends AppCompatActivity {

    EditText etOne, etTwo, etThree, etFour;
    String txt_otp_got;
    String assName, assMobile, assPass, uName_get, serv_OTP;
    String districtCode, talukCode, hobliCode, va_Circle_Code;
    String district, taluk, hobli, VA_Circle_Name, localeName, VA_Name, isVA;
    ProgressDialog dialog;
    int dCode, tCode, hCode, vcCode;

    APIInterface apiInterface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_assistant);

        etOne = findViewById(R.id.etOne);
        etTwo = findViewById(R.id.etTwo);
        etThree = findViewById(R.id.etThree);
        etFour = findViewById(R.id.etFour);

        etOne.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)}); // 1 is max digits
        etTwo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        etThree.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
        etFour.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});

        etOne.addTextChangedListener(new GenericTextWatcher(etOne));
        etTwo.addTextChangedListener(new GenericTextWatcher(etTwo));
        etThree.addTextChangedListener(new GenericTextWatcher(etThree));
        etFour.addTextChangedListener(new GenericTextWatcher(etFour));

        dialog = new ProgressDialog(this, R.style.CustomDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.loading));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        dialog.setProgress(0);

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
        assName = i.getStringExtra("assName");
        assMobile = i.getStringExtra("assMobile");
        assPass = i.getStringExtra("assPass");
        serv_OTP = i.getStringExtra("serv_OTP");
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
    }

    public class GenericTextWatcher  implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            txt_otp_got = etOne.getText().toString().trim()+etTwo.getText().toString().trim()
                    +etThree.getText().toString().trim()+etFour.getText().toString().trim();
            Log.d("afterTextChanged",""+text);
            Log.d("txt_otp_got",""+txt_otp_got);
            switch(view.getId())
            {
                case R.id.etOne:
                    if(text.length()==1)
                        etTwo.requestFocus();
                    break;
                case R.id.etTwo:
                    if(text.length()==1)
                        etThree.requestFocus();
                    else if(text.length()==0)
                        etOne.requestFocus();
                    break;
                case R.id.etThree:
                    if(text.length()==1)
                        etFour.requestFocus();
                    else if(text.length()==0)
                        etTwo.requestFocus();
                    break;
                case R.id.etFour:
                    if(text.length()==0)
                        etThree.requestFocus();
                    else if(txt_otp_got.trim().length()==4 && serv_OTP.equalsIgnoreCase(txt_otp_got)){
                        if(isNetworkAvailable()){
                            dialog.show();
                            //new AddMember().execute();
                            AddMember_Rest(VA_Name, assName, assMobile, assPass, isVA);
                        } else {
                            buildAlert_Internet();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Enter valid otp", Toast.LENGTH_LONG).show();
                    }
                    break;
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

    public void AddMember_Rest(String VA_Name, String assName,String assMobile,String assPass,String isVA){

        apiInterface = APIClient.getClient(getString(R.string.add_assistant_url)).create(APIInterface.class);
        Call<String> call = apiInterface.doAddMember(getString(R.string.flag1_ekshana), getString(R.string.flag2_ekshana),dCode, tCode, hCode, vcCode, VA_Name, assName, assMobile, assPass, isVA);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG", response.code() + "");

                String multipleResource = response.body();
                Log.d("MemberDetails", multipleResource + "");

                if (multipleResource!=null && multipleResource.equals("0")) {
                    dialog.dismiss();
                    Intent i = new Intent(OTP_Assistant.this, Add_Assistant.class);
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
                    finish();
                    Toast.makeText(getApplicationContext(), "Successfully Added" , Toast.LENGTH_SHORT).show();
                    Log.d("Request_", "Successfully Added");
                } else {
                    //Toast.makeText(getApplicationContext(), getString(R.string.no_da_found), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(OTP_Assistant.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.alert))
                            .setMessage("This Person was already Added as Your Assistant .\nYou cannot Add again.")
                            .setIcon(R.drawable.ic_error_black_24dp)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                                Intent i = new Intent(OTP_Assistant.this, Add_Assistant.class);
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
                                startActivity(i);
                                finish();
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(18);
                    Log.d("Request_", "Exists");
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
}
