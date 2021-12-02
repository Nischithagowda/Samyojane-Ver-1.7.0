package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SQLiteAssetHelper_Masters extends SQLiteAssetHelper {

    static final int DATABASE_VERSION = 1;
    static final String DB_PATH_SUFFIX = "/databases/";

    static final String DATABASE_Name_NK_Master = "NK_Master.db";

    static final String DATABASE_Name_Title_MASTER = "Title_MASTER.db";

    static String Table_District_Master = "DISTRICT_MASTER";
    static String DM_district_code = "DM_district_code";
    static String DM_district_kname = "DM_district_kname";
    static String DM_district_ename = "DM_district_ename";

    static String Table_Taluk_Master = "TALUK_MASTER";
    static String TM_district_code = "TM_district_code";
    static String TM_taluk_code = "TM_taluk_code";
    static String TM_taluk_kname = "TM_taluk_kname";
    static String TM_taluk_ename = "TM_taluk_ename";

    static String Table_Hobli_Master = "HOBLI_MASTER";
    static String HBM_district_code = "HBM_district_code";
    static String HBM_taluk_code = "HBM_taluk_code";
    static String HBM_hobli_code = "HBM_hobli_code";
    static String HBM_hobli_kname = "HBM_hobli_kname";
    static String HBM_hobli_ename = "HBM_hobli_ename";

    static String Table_BINCOM_MASTER = "BINCOM_MASTER";
    static String BM_bincom_code = "BM_bincom_code";
    static String BM_bincom_kdesc = "BM_bincom_kdesc";
    static String BM_bincom_edesc = "BM_bincom_edesc";

    static String Table_RELATIONSHIP_MASTER = "RELATIONSHIP_MASTER";
    static String RLM_relationship_code = "RLM_relationship_code";
    static String RLM_relationship_kdesc = "RLM_relationship_kdesc";
    static String RLM_relationship_edesc = "RLM_relationship_edesc";

    static String Table_GENDER_MASTER = "GENDER_MASTER";
    static String Gender_Code = "Gender_Code";
    static String Gender_KDesc = "Gender_KDesc";
    static String Gender_EDesc = "Gender_EDesc";

    static String Table_RELIGION_MASTER = "RELIGION_MASTER";
    static String RGM_religion_code = "RGM_religion_code";
    static String RGM_religion_kdesc = "RGM_religion_kdesc";
    static String RGM_religion_edesc = "RGM_religion_edesc";
    static String RGM_Minority = "RGM_Minority";

    static String Table_Salutation_Master = "Salutation_Master";
    static String SM_Salutation_Code = "SM_Salutation_Code";
    static String SM_Salutation_ename = "SM_Salutation_ename";
    static String SM_Salutation_kname = "SM_Salutation_kname";

    SQLiteDatabase database;
    Cursor cursor;

    @SuppressLint("StaticFieldLeak")
    static Context ctx;

    public SQLiteAssetHelper_Masters(Context context){
        super(context, DATABASE_Name_NK_Master, null, DATABASE_VERSION);
        ctx = context;
    }

    public SQLiteAssetHelper_Masters(Context context, Activity activity){
        super(context, DATABASE_Name_Title_MASTER, null, DATABASE_VERSION);
        ctx = context;
    }

    void CopyNK_MasterDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_Name_NK_Master);

        // Path to the just created empty db
        String outFileName = getNK_MASTERDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
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

    void CopyTitle_MASTERDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_Name_Title_MASTER);

        // Path to the just created empty db
        String outFileName = getTitle_MASTERDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
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

    static String getNK_MASTERDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_Name_NK_Master;
    }

    static String getTitle_MASTERDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_Name_Title_MASTER;
    }

    public void open_NK_MASTER_Tbl() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_Name_NK_Master);

        if (!dbFile.exists()) {
            try {
                CopyNK_MasterDataBaseFromAsset();
                System.out.println("Copying success from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public void open_Title_MASTER_Tbl() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_Name_Title_MASTER);

        if (!dbFile.exists()) {
            try {
                CopyTitle_MASTERDataBaseFromAsset();
                System.out.println("Copying success from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public String Get_DistName_NK_Master(int distCode){
        String objects=null;
        Log.d("dist_Name", "Get_DistName_RC_Master enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_District_Master+" where "
                    +DM_district_code+"="+distCode, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getString(cursor.getColumnIndexOrThrow(DM_district_kname));
                }
            }
            else {
                Log.d("dist_Name", "cursor count not greater than 0");
            }
            Log.d("dist_Name", ""+objects);
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    public String Get_TalukName_NK_Master(int distCode, int talukCode){
        String objects=null;
        Log.d("Taluk_Name", "Get_TalukName_NK_Master enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_Taluk_Master+" where "
                    +TM_district_code+"="+distCode+" and "
                    +TM_taluk_code+"="+talukCode, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getString(cursor.getColumnIndexOrThrow(TM_taluk_kname));
                }
            }
            else {
                Log.d("Taluk_Name", "cursor count not greater than 0");
            }
            Log.d("Taluk_Name", ""+objects);
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    public List<AutoCompleteTextBox_Object> Get_TalukName_NK_Master(int dist_code){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("taluk_Name", "Get_TalukName_NK_Master enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_Taluk_Master+" where "+TM_district_code+"="+dist_code
                    +" order by "+TM_taluk_kname, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndex(TM_taluk_code)),
                                cursor.getString(cursor.getColumnIndex(TM_taluk_kname))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("taluk_Name", "cursor count not greater than 0");
            }
            Log.d("taluk_Name", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    String Get_HobliName_NK_Master(int distCode, int talukCode, int hobliCode){
        String objects=null;
        Log.d("Hobli_Name", "Get_HobliName_NK_Master enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_Hobli_Master+" where "
                    +HBM_district_code+"="+distCode+" and "
                    +HBM_taluk_code+"="+talukCode+" and "
                    +HBM_hobli_code+"="+hobliCode, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getString(cursor.getColumnIndexOrThrow(HBM_hobli_kname));
                }
            }
            else {
                Log.d("Hobli_Name", "cursor count not greater than 0");
            }
            Log.d("Hobli_Name", ""+objects);
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    List<AutoCompleteTextBox_Object> Get_HobliName_NK_Master(int dist_code, int talukCode){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("hobli_Name", "Get_HobliName_NK_Master enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_Hobli_Master+" where "
                    + HBM_district_code+"="+dist_code
                    + " and "+HBM_taluk_code+"="+talukCode
                    + " and "+HBM_hobli_code+"!=255"
                    +" order by "+HBM_hobli_kname, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndex(HBM_hobli_code)),
                                cursor.getString(cursor.getColumnIndex(HBM_hobli_kname))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("hobli_Name", "cursor count not greater than 0");
            }
            Log.d("hobli_Name", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    List<AutoCompleteTextBox_Object> Get_BinCome(){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("Bincom_Name", "Get_BinCome enter");
        String eng_str;
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_BINCOM_MASTER, null);
            if (cursor.getCount()>0){
                objects.add(new AutoCompleteTextBox_Object("0", "-- ಆಯ್ಕೆ --"));
                if(cursor.moveToNext()) {
                    do {
                        eng_str = cursor.getString(cursor.getColumnIndexOrThrow(BM_bincom_edesc));
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndex(BM_bincom_code)),
                                cursor.getString(cursor.getColumnIndex(BM_bincom_kdesc))+"-"+eng_str));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("Bincom_Name", "cursor count not greater than 0");
            }
            Log.d("Bincom_Name", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    String Get_BinCom_By_Code(int Code, String bincom_desc_name){
        String objects=null;
        Log.d("BinCom_Name", "GetEng_BinCom_By_Code enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_BINCOM_MASTER+" where "+BM_bincom_code+"="+Code, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getString(cursor.getColumnIndexOrThrow(bincom_desc_name));
                }
            }
            else {
                Log.d("BinCom_Name", "cursor count not greater than 0");
            }
            Log.d("BinCom_Name", ""+objects);
            cursor.close();
            database.close();

        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    int Get_BinCom_By_Name(String str_val, String bincom_desc_name){
        int objects=0;
        Log.d("BinCom_Code", "Get_BinCom_By_Name enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_BINCOM_MASTER+" where "+bincom_desc_name+"='"+str_val+"'", null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getInt(cursor.getColumnIndexOrThrow(BM_bincom_code));
                }
            }
            else {
                Log.d("BinCom_Code", "cursor count not greater than 0");
            }
            Log.d("BinCom_Code", ""+objects);
            cursor.close();
            database.close();

        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    List<AutoCompleteTextBox_Object> Get_Gender(){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("Gender", "Get_Gender enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_GENDER_MASTER, null);
            if (cursor.getCount()>0){
                objects.add(new AutoCompleteTextBox_Object("0", "-- ಆಯ್ಕೆ --"));
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndex(Gender_Code)),
                                cursor.getString(cursor.getColumnIndex(Gender_KDesc))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("Gender", "cursor count not greater than 0");
            }
            Log.d("Gender", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    String GetGender_By_Code(int Code){
        String objects=null;
        Log.d("Gender", "GetGender_By_Code enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_GENDER_MASTER
                    +" where "+Gender_Code+"="+Code, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getString(cursor.getColumnIndexOrThrow(Gender_KDesc));
                }
            }
            else {
                Log.d("Gender", "cursor count not greater than 0");
            }
            Log.d("Gender", ""+objects);
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    List<AutoCompleteTextBox_Object> Get_Relationship(){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("Relationship_Name", "Get_Relationship enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_RELATIONSHIP_MASTER, null);
            if (cursor.getCount()>0){
                objects.add(new AutoCompleteTextBox_Object("0", "-- ಆಯ್ಕೆ --"));
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndex(RLM_relationship_code)),
                                cursor.getString(cursor.getColumnIndex(RLM_relationship_kdesc))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("Relationship_Name", "cursor count not greater than 0");
            }
            Log.d("Relationship_Name", String.valueOf(objects));
            cursor.close();
            database.close();

        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    String GetHOF_RelationName_By_Code(int Code){
        String objects=null;
        Log.d("Rel_HOF_Name", "GetHOF_RelationName_By_Code enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_RELATIONSHIP_MASTER
                    +" where "+RLM_relationship_code+"="+Code, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getString(cursor.getColumnIndexOrThrow(RLM_relationship_kdesc));
                }
            }
            else {
                Log.d("Rel_HOF_Name", "cursor count not greater than 0");
            }
            Log.d("Rel_HOF_Name", ""+objects);
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    List<AutoCompleteTextBox_Object> Get_Religion(){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("Religion_Name", "Get_Religion enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_RELIGION_MASTER, null);
            if (cursor.getCount()>0){
                objects.add(new AutoCompleteTextBox_Object("0", "-- ಆಯ್ಕೆ --"));
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndex(RGM_religion_code)),
                                cursor.getString(cursor.getColumnIndex(RGM_religion_kdesc))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("Religion_Name", "cursor count not greater than 0");
            }
            Log.d("Religion_Name", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    String GetReligion_By_Code(int Code){
        String objects=null;
        Log.d("Religion_Name", "GetReligion_By_Code enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_RELIGION_MASTER
                    + " where "+RGM_religion_code+"="+Code, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getString(cursor.getColumnIndexOrThrow(RGM_religion_kdesc));
                }
            }
            else {
                Log.d("Religion_Name", "cursor count not greater than 0");
            }
            Log.d("Religion_Name", ""+objects);
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    String Get_Title_By_Code(int Code, String title_desc_name){
        String objects=null;
        Log.d("title_desc_name", "Get_Title_By_Code enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_Salutation_Master+" where "+SM_Salutation_Code+"="+Code, null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getString(cursor.getColumnIndexOrThrow(title_desc_name));
                }
            }
            else {
                Log.d("title_desc_name", "cursor count not greater than 0");
            }
            Log.d("title_desc_name", ""+objects);
            cursor.close();
            database.close();

        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    int Get_Title_By_NAME(String str_val, String title_desc_name){
        int objects=0;
        Log.d("title_desc_Code", "Get_Title_By_NAME enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_Salutation_Master+" where "+title_desc_name+"='"+str_val+"'", null);
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    objects = cursor.getInt(cursor.getColumnIndexOrThrow(SM_Salutation_Code));
                }
            }
            else {
                Log.d("title_desc_Code", "cursor count not greater than 0");
            }
            Log.d("title_desc_Code", ""+objects);
            cursor.close();
            database.close();

        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    public List<AutoCompleteTextBox_Object> Get_Salutation_title(String title_desc_name){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("Salutation_Name", "Get_Salutation enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_Salutation_Master+" order by "+title_desc_name, null);
            if (cursor.getCount()>0){
                objects.add(new AutoCompleteTextBox_Object("0", "-- ಆಯ್ಕೆ --"));
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndex(SM_Salutation_Code)),
                                cursor.getString(cursor.getColumnIndex(title_desc_name))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("Salutation_Name", "cursor count not greater than 0");
            }
            Log.d("Salutation_Name", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

}
