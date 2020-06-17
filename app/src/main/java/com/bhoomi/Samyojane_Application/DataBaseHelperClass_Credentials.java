package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperClass_Credentials extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    public static String DATABASE_NAME= "Credentials.db";
    public static String TABLE_NAME ="credentials";
    public static String VA_IMEI_1 ="IMEI_1";
    public static String VA_IMEI_2 ="IMEI_2";
    public static String VA_UserName = "UserName";
    public static String VA_Pwrd = "Pwrd";
    public static String District_Code = "District_Code";
    public static String District_Name = "District";
    public static String District_Name_k = "District_k";
    public static String Taluk_Code = "Taluk_Code";
    public static String Taluk_Name = "Taluk";
    public static String Taluk_Name_k = "Taluk_k";
    public static String Hobli_Code = "Hobli_Code";
    public static String Hobli_Name = "Hobli";
    public static String Hobli_Name_k = "Hobli_k";
    public static String VA_circle_Code = "VA_circle_Code";
    public static String VA_Circle_Name = "VA_circle_ename";
    public static String VA_Circle_Name_k = "VA_circle_kname";
    public static String VA_Name = "VA_name";
    public static String e_kshana = "e_kshana";

    public static String RI_Name = "RI_name";
    public static String RI_IMEI_1 ="RI_IMEI_1";
    public static String RI_IMEI_2 ="RI_IMEI_2";
    public static String RI_UserName = "RI_UserName";
    public static String RI_Pwrd = "RI_Pwrd";

    public static String flag = "flag";

    private static String CREATE_TABLE_VA ="CREATE TABLE " + TABLE_NAME + "("+ RI_Name+" TEXT,"
            + RI_IMEI_1 +" decimal(15,0),"+ RI_IMEI_2+" decimal(15,0),"+ RI_UserName +" TEXT,"+ RI_Pwrd+" TEXT,"
            + VA_IMEI_1 +" decimal(15,0),"+ VA_IMEI_2 +" decimal(15,0),"+ VA_UserName +" TEXT,"+ VA_Pwrd +" TEXT,"
            + District_Code +" int,"+ District_Name +" TEXT,"+ District_Name_k +" TEXT,"
            + Taluk_Code +" int,"+Taluk_Name+" TEXT,"+Taluk_Name_k+" TEXT,"
            + Hobli_Code +" int,"+Hobli_Name +" TEXT,"+Hobli_Name_k +" TEXT,"
            +VA_circle_Code +" int,"+VA_Circle_Name+" TEXT,"+VA_Circle_Name_k+" TEXT,"
            +VA_Name+" TEXT,"+e_kshana+" int,"+flag+" int)";

    public DataBaseHelperClass_Credentials(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_VA);
        Log.i("Note", TABLE_NAME +" Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        Log.i("Note","Table Upgraded");
    }
}
