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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhoomi.Samyojane_Application.api.APIClient;
import com.bhoomi.Samyojane_Application.api.APIInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class E_Kshana_Display_RC_Updated_VA extends AppCompatActivity {

    LinearLayout listLayout;
    Button btnFetchRC_Nums, btnBack;
    ListView listView;
    TextView emptyTxt;
    String district, taluk, hobli, VA_Circle_Name, localeName, VA_Name, isVA, districtCode, talukCode, hobliCode, va_Circle_Code;
    String uName_get;
    ProgressDialog dialog;
    APIInterface apiInterface;
    int dCode, tCode, hCode;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    Set_and_Get_E_Kshana set_and_get_e_kshana;
    private ArrayList<String> SlNo = new ArrayList<>();
    private ArrayList<String> RCList = new ArrayList<>();
    private ArrayList<String> DistCode_ass = new ArrayList<>();
    private ArrayList<String> TalCode_ass = new ArrayList<>();
    private ArrayList<String> HobCode_ass = new ArrayList<>();
    private ArrayList<String> uName_get_Array = new ArrayList<>();
    RC_VA_List_Adapter list_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_kshana_display_rc_updated_va);

        listLayout = findViewById(R.id.listLayout);
        btnFetchRC_Nums = findViewById(R.id.btnFetchRC_Nums);
        listView = findViewById(R.id.list);
        emptyTxt = findViewById(R.id.emptyTxt);
        btnBack = findViewById(R.id.btnBack);

        listLayout.setVisibility(View.GONE);


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

        if (districtCode!=null && talukCode!=null && hobliCode!=null) {
            dCode = Integer.parseInt(districtCode);
            tCode = Integer.parseInt(talukCode);
            hCode = Integer.parseInt(hobliCode);
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

        btnFetchRC_Nums.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                getRCList();
            } else {
                displayRCList();
                Log.d("Internet_Error","No Connection");
                buildAlert_Internet();
                //Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> onBackPressed());
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void getRCList(){

        if (isNetworkAvailable()) {
            dialog.show();
            apiInterface = APIClient.getClient(getString(R.string.main_url_E_Kshana)).create(APIInterface.class);

            //GET List Resources
            Call<String> call = apiInterface.doGetListRCNumbers_VA(getString(R.string.flag1),getString(R.string.flag2), dCode, tCode, hCode);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("TAG",response.code()+"");


                    String multipleResource = response.body();
                    Log.d("List_Member",multipleResource + "");

                    if (multipleResource!=null && !multipleResource.equals("0") && !multipleResource.equals("Data is not found for the given Details")) {

                        Runnable runnable = () -> Toast.makeText(getApplicationContext(), getString(R.string.server_exception), Toast.LENGTH_SHORT).show();
                        try {
                            truncateDatabase_RCList();

                            JSONObject jsonObject = new JSONObject(multipleResource);
                            Log.d("jObj_output", "" + jsonObject);

                            JSONArray jsonArray = jsonObject.getJSONArray("Table");
                            Log.d("jsonArray_output", "" + jsonArray);

                            try {
                                int count = jsonArray.length();
                                Log.d("jsonArray_count", "" + count);
                                int increase=1;
                                if (count != 0) {
                                    for (int i = 0; i < count; i++) {
                                        Log.d("increase",""+increase);
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        set_and_get_e_kshana = new Set_and_Get_E_Kshana();
                                        set_and_get_e_kshana.setRC_SLNo(""+increase);
                                        set_and_get_e_kshana.setRC_DistCode(""+dCode);
                                        set_and_get_e_kshana.setRC_TalukCode(""+tCode);
                                        set_and_get_e_kshana.setRC_HobliCode(""+hCode);
                                        set_and_get_e_kshana.setRC_List(object.getString(DataBaseHelperClass_btnDownload_E_Kshana.RC_Num_List));

                                        database.execSQL("insert into " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_RC_Numbers_VA
                                                + "(RC_SLNo, RC_DistCode, RC_TalukCode, RC_HobliCode, RAT_ACK_ID) values ('"
                                                + set_and_get_e_kshana.getRC_SLNo() + "','"
                                                + set_and_get_e_kshana.getRC_DistCode() + "','"
                                                + set_and_get_e_kshana.getRC_TalukCode() + "','"
                                                + set_and_get_e_kshana.getRC_HobliCode() + "','"
                                                + set_and_get_e_kshana.getRC_List()+"')");
                                        if (increase!=count) {
                                            increase++;
                                        }
                                    }
                                }
                                //displayData_AfterItemSelected(rc_num);
                                if (increase==count){
                                    Log.d("GetRCNumbers", "Got Complete RC List");
                                    dialog.dismiss();
                                    displayRCList();
                                }

                            } catch (JSONException e) {
                                dialog.dismiss();
                                e.printStackTrace();
                                runOnUiThread(runnable);
                            }

                        } catch (JSONException e) {
                            dialog.dismiss();
                            Log.e("JSON Parser", "Error parsing data " + e.toString());
                        }catch (NullPointerException e){
                            dialog.dismiss();
                            runOnUiThread(runnable);
                            e.printStackTrace();
                            Log.e("NullPointerException", ""+e.getMessage());
                        }
                    }else {
                        dialog.dismiss();
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), ""+multipleResource+": "+response.code(), Toast.LENGTH_SHORT).show());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("multipleResource",""+t.getMessage());
                    dialog.dismiss();
                    call.cancel();
                }
            });
        } else {
            Log.d("Internet_Error","No Connection");
            displayRCList();
            buildAlert_Internet();
            //Toast.makeText(getApplicationContext(), getString(R.string.connection_not_available), Toast.LENGTH_SHORT).show();
        }

    }

    public void truncateDatabase_RCList(){

        Cursor cursor = database.rawQuery("select * from "+ DataBaseHelperClass_btnDownload_E_Kshana.TABLE_RC_Numbers_VA, null);
        if(cursor.getCount()>0) {
            database.execSQL("Delete from " + DataBaseHelperClass_btnDownload_E_Kshana.TABLE_RC_Numbers_VA);
            Log.d("Database", "RCList Table Truncated");
        } else {
            cursor.close();
        }

    }

    public void displayRCList(){

        int i=1;
        Log.d("InDisplay", ""+ i);

        Cursor cursor = database.rawQuery("Select * from "+DataBaseHelperClass_btnDownload_E_Kshana.TABLE_RC_Numbers_VA
                +" where " + DataBaseHelperClass_btnDownload_E_Kshana.RC_DistCode+"="+dCode+" and "
                + DataBaseHelperClass_btnDownload_E_Kshana.RC_TalukCode+"="+tCode+" and "
                + DataBaseHelperClass_btnDownload_E_Kshana.RC_HobliCode+"="+hCode+" order by "
                + DataBaseHelperClass_btnDownload_E_Kshana.RC_Num_List, null);

        SlNo.clear();
        RCList.clear();
        DistCode_ass.clear();
        TalCode_ass.clear();
        HobCode_ass.clear();
        uName_get_Array.clear();

        if(cursor.getCount()>0) {
            emptyTxt.setVisibility(View.GONE);
            listLayout.setVisibility(View.VISIBLE);
            dialog.dismiss();
            if (cursor.moveToFirst()) {
                do {
                    Log.d("InDisplayIf", ""+ i);
                    SlNo.add(String.valueOf(i));
                    RCList.add(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelperClass_btnDownload_E_Kshana.RC_Num_List)));
                    DistCode_ass.add(""+dCode);
                    TalCode_ass.add(""+tCode);
                    HobCode_ass.add(""+hCode);
                    uName_get_Array.add(uName_get);
                    i++;
                } while (cursor.moveToNext());
            }
            Log.d("InDisplayIf", ""+ i);
            list_adapter = new RC_VA_List_Adapter(E_Kshana_Display_RC_Updated_VA.this, SlNo, RCList, DistCode_ass, TalCode_ass, HobCode_ass, uName_get_Array);
            listView.setAdapter(list_adapter);
            //Toast.makeText(getApplicationContext(), "Data Displayed Successfully", Toast.LENGTH_SHORT).show();
        } else{
            cursor.close();
            dialog.dismiss();
            listLayout.setVisibility(View.VISIBLE);
            emptyTxt.setVisibility(View.VISIBLE);
            Log.d("InDisplayElse", ""+ i);
            emptyTxt.setText(getString(R.string.no_da_found));
            //Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
