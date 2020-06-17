package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class Selection_Module_VA extends AppCompatActivity {

    RadioGroup radioGroup;
    public RadioButton rbRegular, rbEkshana;
    String option_Flag;
    String district, taluk, hobli, districtCode, talukCode, hobliCode, VA_Circle_Name, va_Circle_Code, localeName, VA_Name;
    AlertDialog alert;
    Button btnAddAssistant;
    String uName_get;
    String IMEI_Num, mob_Num;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_module_va);

        radioGroup = findViewById(R.id.radioGroup);
        rbRegular = findViewById(R.id.rbRegular);
        rbEkshana = findViewById(R.id.rbEkshana);
        btnAddAssistant = findViewById(R.id.btnAddAssistant);

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
        uName_get = i.getStringExtra("uName_get");
        IMEI_Num = i.getStringExtra("IMEI_Num");
        mob_Num = i.getStringExtra("mob_Num");

        Log.d("Second_Database_Value", ""+district);
        Log.d("Second_Database_Value", ""+taluk);
        Log.d("Second_Database_Value", ""+hobli);
        Log.d("Second_Database_Value", ""+VA_Circle_Name);
        Log.d("Second_Database_Value",""+va_Circle_Code);
        Log.d("Second_Database_Value", ""+VA_Name);
        Log.d("Second_Database_Value",""+localeName);
        Log.d("Second_DB_Val",""+getString(R.string.cre_district_name)
                +","+getString(R.string.cre_taluk_name)
                +","+getString(R.string.cre_hobli_name)
                +","+getString(R.string.cre_va_circle_name));

        Log.d("districtCode", ""+districtCode);
        Log.d("talukCode", ""+talukCode);
        Log.d("hobliCode", ""+hobliCode);
        Log.d("IMEI_Num", ""+IMEI_Num);
        Log.d("mob_Num", ""+mob_Num);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.rbRegular) {
                option_Flag = getString(R.string.regular);
                rbRegular.setChecked(false);
                Intent i1 = new Intent(Selection_Module_VA.this, SecondScreen.class);
                i1.putExtra("district", district);
                i1.putExtra("taluk", taluk);
                i1.putExtra("hobli", hobli);
                i1.putExtra("districtCode", districtCode);
                i1.putExtra("talukCode", talukCode);
                i1.putExtra("hobliCode", hobliCode);
                i1.putExtra("VA_Circle_Name", VA_Circle_Name);
                i1.putExtra("va_Circle_Code", va_Circle_Code);
                i1.putExtra("VA_Name", VA_Name);
                i1.putExtra("localeName",""+localeName);
                i1.putExtra("uName_get", uName_get);
                i1.putExtra("IMEI_Num", ""+IMEI_Num);
                i1.putExtra("mob_Num",""+mob_Num);
                startActivity(i1);
            }
            else if (checkedId == R.id.rbEkshana) {
                option_Flag = getString(R.string.e_kshana);
                rbEkshana.setChecked(false);
                //Toast.makeText(getApplicationContext(), getString(R.string.service_not_availabel), Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(Selection_Module_VA.this, E_Kshana_Display_RC_Updated_VA.class);
                i1.putExtra("district", district);
                i1.putExtra("taluk", taluk);
                i1.putExtra("hobli", hobli);
                i1.putExtra("districtCode", districtCode);
                i1.putExtra("talukCode", talukCode);
                i1.putExtra("hobliCode", hobliCode);
                i1.putExtra("VA_Circle_Name", VA_Circle_Name);
                i1.putExtra("va_Circle_Code", va_Circle_Code);
                i1.putExtra("VA_Name", VA_Name);
                i1.putExtra("localeName",""+localeName);
                i1.putExtra("uName_get", uName_get);
                i1.putExtra("IMEI_Num", ""+IMEI_Num);
                i1.putExtra("mob_Num",""+mob_Num);
                startActivity(i1);
            }
            Log.d("option_Flag", ""+option_Flag);
        });

        btnAddAssistant.setOnClickListener(v -> {
            Intent i1 = new Intent(Selection_Module_VA.this, Add_Assistant.class);
            i1.putExtra("district", district);
            i1.putExtra("taluk", taluk);
            i1.putExtra("hobli", hobli);
            i1.putExtra("districtCode", districtCode);
            i1.putExtra("talukCode", talukCode);
            i1.putExtra("hobliCode", hobliCode);
            i1.putExtra("VA_Circle_Name", VA_Circle_Name);
            i1.putExtra("va_Circle_Code", va_Circle_Code);
            i1.putExtra("VA_Name", VA_Name);
            i1.putExtra("isVA", "VA");
            i1.putExtra("localeName",""+localeName);
            i1.putExtra("uName_get", uName_get);
            i1.putExtra("IMEI_Num", ""+IMEI_Num);
            i1.putExtra("mob_Num",""+mob_Num);
            startActivity(i1);
        });
    }

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.want_to_exit))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    alert.dismiss();
                    Selection_Module_VA.super.onBackPressed();
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
        alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
//        MainActivity.userName.setText("");
        MainActivity.pwd.setText("");
        buildAlertMessageGoingBack();
    }
}
