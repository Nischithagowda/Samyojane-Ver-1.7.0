package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    public static String DATABASE_NAME= "FacilityMaster.db";
    public static String TABLE_NAME="FacilityMaster";
    public static String SlNo = "SlNo";
    public static String FM_facility_code = "FM_facility_code";
    public static String FM_facility_edesc = "FM_facility_edesc";
    public static String FM_facility_kdesc = "FM_facility_kdesc";
    public static String FM_acronym_on_doc_eng = "FM_acronym_on_doc_eng";
    public static String FM_designated_officer = "FM_designated_officer";
    public static String FM_gsc_no_days = "FM_gsc_no_days";
    public static String FM_facility_display = "FM_facility_display";
    public static String FM_sakala_service = "FM_sakala_service";
    public static String FM_OTC_Charges = "FM_OTC_Charges";

    private static String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + "("+ SlNo+" float,"
            + FM_facility_code+" float,"+ FM_facility_edesc+" TEXT,"+ FM_facility_kdesc+" TEXT,"
            + FM_acronym_on_doc_eng +" TEXT,"+ FM_designated_officer +" float,"
            + FM_gsc_no_days+" float,"+ FM_facility_display+" TEXT,"+ FM_sakala_service+" TEXT,"
            +FM_OTC_Charges+" float)";

    public DataBaseHelperClass_btnDownload_NewRequest_FacilityMaster(Context context) {
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