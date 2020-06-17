package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperClass_VillageNames extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    public static String DATABASE_NAME = "Village_Names.db";
    public static String TABLE_NAME = "Village_Names";
    public static String ID = "ID";
    public static String HM_village_code = "HM_village_code";
    public static String HM_habitation_code = "HM_habitation_code";
    public static String HM_habitation_ename = "HM_habitation_ename";
    public static String HM_habitation_kname = "HM_habitation_kname";
    public static String VCM_va_circle_code = "VCM_va_circle_code";
    public static String VCM_va_circle_ename = "VCM_va_circle_ename";
    public static String VCM_va_circle_kname = "VCM_va_circle_kname";

    private static String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + "("+ ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +VCM_va_circle_code+" int," +VCM_va_circle_ename+" TEXT,"+VCM_va_circle_kname+" TEXT,"
            + HM_village_code+" int,"+ HM_habitation_code+" int," + HM_habitation_ename+" TEXT," +HM_habitation_kname+" TEXT)";

    public DataBaseHelperClass_VillageNames(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i("Note",TABLE_NAME+" Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        Log.i("Note","Table Upgraded");
        //db.execSQL(CREATE_TABLE);
    }
}
