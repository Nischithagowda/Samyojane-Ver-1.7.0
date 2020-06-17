package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Adarsh on 29-May-19.
 */

public class DataBaseHelperClass_RandomNum extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    public static String DATABASE_NAME= "Random_Num.db";
    public static String TABLE_NAME ="RandomNum";
    public static String RandomNum = "RandomNum";

    private static String  CREATE_TABLE="Create table "+TABLE_NAME+"(RandomNum TEXT)";

    public DataBaseHelperClass_RandomNum(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i("Note", TABLE_NAME +" Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        Log.i("Note","Table Upgraded");
    }
}
