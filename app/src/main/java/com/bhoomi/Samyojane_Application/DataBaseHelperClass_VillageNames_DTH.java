package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperClass_VillageNames_DTH  extends SQLiteOpenHelper {

    static int DATABASE_VERSION=1;
    public static String TABLE_NAME = "Village_Names";
    public static String ID = "ID";
    public static String HM_village_code = "HM_village_code";
    public static String HM_habitation_code = "HM_habitation_code";
    public static String HM_habitation_ename = "HM_habitation_ename";
    public static String HM_habitation_kname = "HM_habitation_kname";
    public static String VCM_va_circle_code = "VCM_va_circle_code";
    public static String VCM_va_circle_ename = "VCM_va_circle_ename";
    public static String VCM_va_circle_kname = "VCM_va_circle_kname";
    public static String RuralUrban = "RuralUrban";

    private static final String DB_PATH_SUFFIX = "/databases/";

    private static final String DATABASE_NAME_town_ward = "TOWN_WARD_MASTER.sqlite";

    public static String Table_TOWN_MASTER = "TOWN_MASTER";
    public static String TWM_district_code = "TWM_district_code";
    public static String TWM_taluk_code = "TWM_taluk_code";
    public static String TWM_town_code = "TWM_town_code";
    public static String TWM_town_kname = "TWM_town_kname";
    public static String TWM_town_ename = "TWM_town_ename";

    public static String Table_WARD_MASTER = "WARD_MASTER";
    public static String WM_district_code = "WM_district_code";
    public static String WM_taluk_code = "WM_taluk_code";
    public static String WM_town_code = "WM_town_code";
    public static String WM_ward_no = "WM_ward_no";
    public static String WM_hobli_code = "WM_hobli_code";
    public String WM_va_circle_code = "WM_va_circle_code";
    public String WM_ward_kname = "WM_ward_kname";
    public String WM_ward_ename = "WM_ward_ename";

    public static Context cxt;
    SQLiteDatabase database;
    Cursor cursor;

    public static String CREATE_TABLE ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+ ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +VCM_va_circle_code+" int," +VCM_va_circle_ename+" TEXT,"+VCM_va_circle_kname+" TEXT,"
            + HM_village_code+" int,"+ HM_habitation_code+" int,"+ HM_habitation_ename+" TEXT," +HM_habitation_kname+" TEXT, "+RuralUrban+" TEXT)";

    public DataBaseHelperClass_VillageNames_DTH(Context context) {
        super(context, DATABASE_NAME_town_ward, null, DATABASE_VERSION);
        cxt = context;
        Log.i("Note_Vill",DATABASE_NAME_town_ward);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.i("Note_Vill",TABLE_NAME+" Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        Log.i("Note","Table Upgraded");
        //db.execSQL(CREATE_TABLE);
    }

    public List<AutoCompleteTextBox_Object> Get_TownName_DTH(int distCode, int talukCode, int hobliCode, String town_Name){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        try {
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select distinct " + town_Name + "," + TWM_town_code + " from " + Table_WARD_MASTER
                    + " inner join " + Table_TOWN_MASTER + " on " + WM_town_code + "=" + TWM_town_code + " where " + WM_district_code + "=" + distCode + " and "
                    + WM_taluk_code + "=" + talukCode + " and "
                    + WM_hobli_code + "=" + hobliCode, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    do {
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(TWM_town_code)),cursor.getString(cursor.getColumnIndexOrThrow(town_Name))));
                    } while (cursor.moveToNext());
                }
                return objects;
            }
        } catch (Exception e){
            Log.d("Catch", ""+ e);
        }
        Log.d("Town", ""+ objects);
        return objects;
    }

    public List<AutoCompleteTextBox_Object> Get_WardName_DTH(int dist_Code, int taluk_Code, int townCode, String ward_Name){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        try {
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_WARD_MASTER+" where "
                    +WM_district_code+"="+dist_Code+" and "
                    +WM_taluk_code+"="+taluk_Code+" and "
                    +WM_town_code+"="+townCode, null);
            if(cursor.getCount()>0){
                Log.d("ward_count",""+cursor.getCount());
                if(cursor.moveToNext()){
                    do{
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(WM_ward_no)),(cursor.getString(cursor.getColumnIndexOrThrow(ward_Name)))));
                    }while (cursor.moveToNext());
                }
            }else {
                Log.d("wardCode", "No Ward found");
            }
            cursor.close();
            database.close();
        }catch (Exception e){
            Log.d("Catch", String.valueOf(objects));
        }
        Log.d("Ward", ""+ objects);
        return objects;
    }

    public List<AutoCompleteTextBox_Object> getVillageCircleNameList_DTH(int dist_Code, int taluk_Code, int townCode, int ward_code, String va_circle_name){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        try {
            database = this.getReadableDatabase();
            cursor = database.rawQuery("Select distinct " + va_circle_name + "," + VCM_va_circle_code + " from "
                    + Table_WARD_MASTER + " inner join " + TABLE_NAME + " on " + WM_va_circle_code + "=" + VCM_va_circle_code + " where "
                    + WM_district_code + "=" + dist_Code + " and "
                    + WM_taluk_code + "=" + taluk_Code + " and "
                    + WM_town_code + "=" + townCode + " and "
                    + WM_ward_no + " = " + ward_code + " and "+RuralUrban+"=2 order by " + va_circle_name, null);
            if (cursor.moveToFirst()) {
                do {
                    objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(VCM_va_circle_code)), (cursor.getString(cursor.getColumnIndexOrThrow(va_circle_name)))));
                } while (cursor.moveToNext());
            } else {
                cursor.close();
            }
        } catch (Exception e){
            Log.d("Catch", ""+ e.getMessage());
        }
        Log.d("va_circle_name", ""+ objects);
        return objects;
    }

    private void CopyTownWardDataBaseFromAsset() throws IOException {

        InputStream myInput = cxt.getAssets().open(DATABASE_NAME_town_ward);

        // Path to the just created empty db
        String outFileName = getTownWardDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(cxt.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private static String getTownWardDatabasePath() {
        return cxt.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME_town_ward;
    }

    public void open_Town_Ward_Tbl() throws SQLException {
        File dbFile = cxt.getDatabasePath(DATABASE_NAME_town_ward);

        if (!dbFile.exists()) {
            try {
                CopyTownWardDataBaseFromAsset();
                System.out.println("Copying success from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }
}
