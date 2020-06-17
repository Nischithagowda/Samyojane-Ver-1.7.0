package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Adarsh on 03-Jun-19.
 */

public class DataBaseHelperClass_btnDownload_Docs extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    private static String DATABASE_NAME= "Down_Docs.db";
    public static String TABLE_NAME="Docs";
    public static String UDT_GSC_No = "UDT_GSC_No";
    public static String UDT_GSCFirstPart = "UDT_GSCFirstPart";
    public static String UDT_Document_Id = "UDT_Document_Id";
    public static String UDT_File = "UDT_File";
    private static String In_String = "In_String";

    private static String CREATE_TABLE = " CREATE TABLE "+ TABLE_NAME +"("+UDT_GSC_No+" bigint,"+UDT_GSCFirstPart+" int,"
            + UDT_Document_Id+" int,"+UDT_File+" TEXT , "+In_String+" TEXT)";

    DataBaseHelperClass_btnDownload_Docs(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("Note", "DataBase Opened");
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
