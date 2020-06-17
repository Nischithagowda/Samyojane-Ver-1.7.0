package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Adarsh on 14-Jun-19.
 */

public class DataBaseHelperClass_btnDownload_VersionCode extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    private static String DATABASE_NAME= "Version_Code.db";
    public static String TABLE_NAME="M_App_Version";
    public static String SlNo = "SlNo";
    public static String VersionCode = "V_CODE";
    public static String Status = "App_STATUS";

    private static String CREATE_TABLE = "Create Table "+ TABLE_NAME +"("+ SlNo + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +VersionCode+" TEXT,"+Status+" TEXT)";

    public DataBaseHelperClass_btnDownload_VersionCode(Context context) {
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
    }
}
