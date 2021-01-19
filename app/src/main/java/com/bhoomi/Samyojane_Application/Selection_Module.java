package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class Selection_Module extends AppCompatActivity {

    RadioGroup radioGroup;
    public static RadioButton rbRegular, rbEkshana;
    String option_Flag;
    String district, taluk, hobli, VA_Circle_Name, VA_Name;
    String va_Circle_Code;
    String localeName;
    AlertDialog alert;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_module);

        radioGroup = findViewById(R.id.radioGroup);
        rbRegular = findViewById(R.id.rbRegular);
        rbEkshana = findViewById(R.id.rbEkshana);

        Intent i = getIntent();
        district = i.getStringExtra("district");
        taluk = i.getStringExtra("taluk");
        hobli = i.getStringExtra("hobli");
        VA_Circle_Name = i.getStringExtra("VA_Circle_Name");
        va_Circle_Code = i.getStringExtra("va_Circle_Code");
        VA_Name = i.getStringExtra("VA_Name");
        localeName = i.getStringExtra("localeName");

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

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.rbRegular) {
                option_Flag = getString(R.string.regular);
                radioGroup.clearCheck();
                Intent i1 = new Intent(Selection_Module.this, SecondScreen.class);
                i1.putExtra("district", district);
                i1.putExtra("taluk", taluk);
                i1.putExtra("hobli", hobli);
                i1.putExtra("VA_Circle_Name", VA_Circle_Name);
                i1.putExtra("va_Circle_Code", va_Circle_Code);
                i1.putExtra("VA_Name", VA_Name);
                i1.putExtra("localeName",""+localeName);
                startActivity(i1);
            }
            else if (checkedId == R.id.rbEkshana) {
                option_Flag = getString(R.string.e_kshana);
                radioGroup.clearCheck();
                Intent i1 = new Intent(Selection_Module.this, E_Kshana_MainScreen.class);
                i1.putExtra("district", district);
                i1.putExtra("taluk", taluk);
                i1.putExtra("hobli", hobli);
                i1.putExtra("VA_Circle_Name", VA_Circle_Name);
                i1.putExtra("va_Circle_Code", va_Circle_Code);
                i1.putExtra("VA_Name", VA_Name);
                i1.putExtra("localeName",""+localeName);
                startActivity(i1);
            }
            Log.d("option_Flag", ""+option_Flag);
        });
    }

    private  void buildAlertMessageGoingBack() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.want_to_exit))
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    alert.dismiss();
                    Selection_Module.super.onBackPressed();
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
