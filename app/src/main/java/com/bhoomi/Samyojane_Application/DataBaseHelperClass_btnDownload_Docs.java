package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Adarsh on 03-Jun-19.
 */

public class DataBaseHelperClass_btnDownload_Docs extends SQLiteOpenHelper {

    static int DATABASE_VERSION=1;
    static String DATABASE_NAME= "Down_Docs.db";
    public static String TABLE_NAME="Docs";
    public static String GSCNO = "GSCNO";
    public static String DocumentName = "DocumentName";
    public static String DocumentID = "DocumentID";
    public static String Document = "Document";

    static String CREATE_TABLE = " CREATE TABLE "+ TABLE_NAME +"("+GSCNO+" TEXT,"+DocumentName+" TEXT,"
            + DocumentID+" int,"+Document+" TEXT)";

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
