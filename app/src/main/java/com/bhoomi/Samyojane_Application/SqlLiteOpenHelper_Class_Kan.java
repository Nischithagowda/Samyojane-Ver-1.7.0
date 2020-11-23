package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SqlLiteOpenHelper_Class_Kan extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DB_PATH_SUFFIX = "/databases/";

    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    Cursor cursor;
    int townCode, wardCode;
    int town_totalCount, ward_totalCount;

    private static final String DATABASE_NAME_reasons = "Reasons_Master.db";
    private String Table_1_re = "CreamyLayerReasons";
    private String Table_2_re = "CertificateRejectionReason";
    private String Table_3_re = "Purpose_of_Certificate";
//    private String Reasons_re = "Reasons";
//    private String Purpose_re = "Purpose";
    private String SlNo = "SlNo";

    private static final String DATABASE_NAME_cat_caste = "CATEGORY_CASTE_MASTER.sqlite";
    private static String Table_CAT_MASTER = "CAT_MASTER";
    private static String Table_CASTE_EXCEPT_OBC = "CASTE_EXCEPT_OBC";
    private static String Table_CASTE_OBC = "CASTE_OBC";

    private static String DATABASE_NAME_docs_type = "DOCUMENT_TYPE_MASTER.db";
    private static String Table_DOCS_Type = "DOCUMENT_TYPE_MASTER";
    private static String Doc_Code = "DTM_document_code";
    private static String Doc_Name_E = "DTM_document_edesc";
    private static String Doc_Name_K = "DTM_document_kdesc";

    private static String DATABASE_NAME_town_ward = "TOWN_WARD_MASTER.db";

    private static String Table_TOWN_MASTER = "TOWN_MASTER";
    private static String TWM_district_code = "TWM_district_code";
    private static String TWM_taluk_code = "TWM_taluk_code";
    private static String TWM_town_code = "TWM_town_code";
    private static String TWM_town_kname = "TWM_town_kname";
    private static String TWM_town_ename = "TWM_town_ename";

    private static String Table_WARD_MASTER = "WARD_MASTER";
    private static String WM_district_code = "WM_district_code";
    private static String WM_taluk_code = "WM_taluk_code";
    private static String WM_town_code = "WM_town_code";
    private static String WM_ward_no = "WM_ward_no";
    private static String WM_hobli_code = "WM_hobli_code";
    private static String WM_va_circle_code = "WM_va_circle_code";
    private static String WM_ward_kname = "WM_ward_kname";
    private static String WM_ward_ename = "WM_ward_ename";

    private static String DATABASE_Name_ID_Type = "ID_MASTER.db";

    private static String ID_Master_tbl="ID_MASTER";
    private static String ID_Code = "ID_Code";
    private static String ID_Kname = "ID_Kname";
    private static String ID_Ename = "ID_Ename";

    @SuppressLint("StaticFieldLeak")
    private static Context ctx;

    public SqlLiteOpenHelper_Class_Kan(Context context) {
        super(context, DATABASE_NAME_reasons, null, DATABASE_VERSION);
        ctx = context;
    }

    public SqlLiteOpenHelper_Class_Kan(){
        super(ctx, DATABASE_NAME_cat_caste, null, DATABASE_VERSION);
    }

    public SqlLiteOpenHelper_Class_Kan(Activity activity, Context context){
        super(context, DATABASE_NAME_cat_caste, null, DATABASE_VERSION);
        ctx = context;
    }

    public SqlLiteOpenHelper_Class_Kan(Context context, String str){
        super(context, DATABASE_NAME_docs_type, null, DATABASE_VERSION);
        ctx = context;
    }

    public SqlLiteOpenHelper_Class_Kan(Context context, String str, String str1){
        super(context, DATABASE_NAME_town_ward, null, DATABASE_VERSION);
        ctx = context;
    }

    public SqlLiteOpenHelper_Class_Kan(Context context, String str, String str1, String str2){
        super(context, DATABASE_Name_ID_Type, null, DATABASE_VERSION);
        ctx = context;
    }

    public String Get_K_DocsName(int docs_ID){
        String docs_Name=null;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+ Table_DOCS_Type +" where "+ Doc_Code+"="+docs_ID, null);
        if(cursor.getCount()>0){
            if (cursor.moveToNext()){
                docs_Name = cursor.getString(cursor.getColumnIndexOrThrow(Doc_Name_K));
            }
            return docs_Name;
        }
        else return docs_Name;
    }

    public String Get_E_DocsName(int docs_ID){
        String docs_Name=null;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+ Table_DOCS_Type +" where "+ Doc_Code+"="+docs_ID, null);
        if(cursor.getCount()>0){
            if (cursor.moveToNext()){
                docs_Name = cursor.getString(cursor.getColumnIndexOrThrow(Doc_Name_E));
            }
            return docs_Name;
        }
        else return docs_Name;
    }

    public String GetCreamyLayerReason_BY_Code(int code, String col_name){
        String str="";
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+ Table_1_re + " where "+SlNo+"="+code, null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                str = cursor.getString(cursor.getColumnIndexOrThrow(col_name));
            }
            return str;
        }
        else return str;
    }
    @SuppressLint("Recycle")
    public int Get_CreamyLayerReasons(String str, String col_name){
        int num=0;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("Select SlNo from "+ Table_1_re +" where "+ col_name +"='"+str+"'" , null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()) {
                num = cursor.getInt(cursor.getColumnIndex(SlNo));
            }
            return num;
        }
        else
            return num;
    }

    public String Get_CertificateRejectionReason_BY_Code(int code, String col_name){
        String str="";
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+Table_2_re+" where "+ SlNo+"="+code, null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                str = cursor.getString(cursor.getColumnIndexOrThrow(col_name));
            }
            return str;
        }
        else return str;
    }

    @SuppressLint("Recycle")
    public int Get_CertificateRejectionReason(String str, String col_name){
        int num=0;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("Select * from "+ Table_2_re +" where "+ col_name +"='"+str+"'" , null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()) {
                num = cursor.getInt(cursor.getColumnIndex(SlNo));
            }
            return num;
        }
        else
            return num;
    }

    public String Get_Purpose_BY_Code(int code, String col_name){
        String str="";
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+Table_3_re+ " where "+ SlNo+"="+code, null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                str = cursor.getString(cursor.getColumnIndexOrThrow(col_name));
            }
            return str;
        }
        else return str;
    }

    @SuppressLint("Recycle")
    public int Get_Purpose(String str, String col_name){
        int num=0;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("Select * from "+Table_3_re+" where "+ col_name +"='"+str+"'" , null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()) {
                num = cursor.getInt(cursor.getColumnIndex(SlNo));
            }
            return num;
        }
        else
            return num;
    }

    @SuppressLint("Recycle")
    public List<SpinnerObject> Get_Category(String str, String add){
        List<SpinnerObject> objects = new ArrayList<>();
        Log.d("Category1", "Get_Category enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_CAT_MASTER+" where General_SCST='"+str+"'", null);
            objects.add ( new SpinnerObject( "0" , add ) );
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new SpinnerObject(cursor.getString(cursor.getColumnIndex("RTM_res_category_code")), cursor.getString(cursor.getColumnIndex("RTM_res_category_kdesc"))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("Category1", "cursor count not greater than 0");
            }
            Log.d("Category1", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    @SuppressLint("Recycle")
    public List<SpinnerObject> Get_Category_OBC(String str, String add){
        List<SpinnerObject> objects = new ArrayList<>();
        Log.d("Category1", "Get_Category enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_CAT_MASTER+" where General_SCST='"+str+"'", null);
            objects.add ( new SpinnerObject( "0" , add) );
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new SpinnerObject(cursor.getString(cursor.getColumnIndex("RTM_res_category_code")), cursor.getString(cursor.getColumnIndex("RTM_res_category_edesc"))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("Category1", "cursor count not greater than 0");
            }
            Log.d("Category1", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    public String GetCategory_BY_Code(int catCode){
        String str = "";
        database = this.getReadableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = database.rawQuery("select * from "+ Table_CAT_MASTER+ " where RTM_res_category_code="+catCode, null);
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                str = cursor.getString(cursor.getColumnIndexOrThrow("RTM_res_category_kdesc"));
            }
            return str;
        }
        Log.d("Category", str);
        return str;
    }

    @SuppressLint("Recycle")
    public int GetCategoryCode(String str){
        int num=0;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+ Table_CAT_MASTER+" where RTM_res_category_kdesc='"+str+"'", null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                num = cursor.getInt(cursor.getColumnIndex("RTM_res_category_code"));
            }
            return num;
        }
        Log.d("Category_Code", String.valueOf(num));
        return num;
    }

    @SuppressLint("Recycle")
    public List<SpinnerObject> Get_Category_NK(){
        List<SpinnerObject> objects = new ArrayList<>();
        Log.d("Category_NK", "Get_Category enter");
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_CAT_MASTER, null);
            objects.add ( new SpinnerObject( "0" , "-- ಆಯ್ಕೆ --") );
            if (cursor.getCount()>0){
                if(cursor.moveToNext()) {
                    do {
                        objects.add(new SpinnerObject(cursor.getString(cursor.getColumnIndex("RTM_res_category_code")), cursor.getString(cursor.getColumnIndex("RTM_res_category_kdesc"))));
                    } while (cursor.moveToNext());
                }
            }
            else {
                Log.d("Category_NK", "cursor count not greater than 0");
            }
            Log.d("Category_NK", String.valueOf(objects));
            cursor.close();
            database.close();


        }catch(Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        return objects;
    }

    public List<SpinnerObject> GetCaste(int num){
        List<SpinnerObject> objects = new ArrayList<>();
        try {
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+ Table_CASTE_EXCEPT_OBC+" where CM_res_category_code='"+num+"' order by CM_caste_kdesc", null);
            if(cursor.getCount()>0){
                if(cursor.moveToNext()){
                    do{
                        objects.add(new SpinnerObject(cursor.getString(cursor.getColumnIndex("CM_res_category_code")), cursor.getString(cursor.getColumnIndex("CM_caste_kdesc"))));
                    }while (cursor.moveToNext());
                }
            }
            cursor.close();
            database.close();
        }
        catch (Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        Log.d("Caste", String.valueOf(objects));
        return objects;
    }

    public List<SpinnerObject> GetCaste_OBC(int num){
        List<SpinnerObject> objects = new ArrayList<>();
        try {
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+ Table_CASTE_OBC+" where CM_res_category_code='"+num+"' order by CM_caste_edesc", null);
            if(cursor.getCount()>0){
                if(cursor.moveToNext()){
                    do{
                        objects.add(new SpinnerObject(cursor.getString(cursor.getColumnIndex("CM_res_category_code")), cursor.getString(cursor.getColumnIndex("CM_caste_edesc"))));
                    }while (cursor.moveToNext());
                }
            }
            cursor.close();
            database.close();
        }
        catch (Exception e){
            Log.d("Catch", String.valueOf(e));
        }
        Log.d("Caste", String.valueOf(objects));
        return objects;
    }

    public String GetCaste_BY_Code(int CatCode,int casteCode){
        String str=null;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+Table_CASTE_EXCEPT_OBC+" where CM_res_category_code="+CatCode+" and CM_CASTE_ID="+casteCode +" order by CM_caste_kdesc", null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                str = cursor.getString(cursor.getColumnIndexOrThrow("CM_caste_kdesc"));
            }
            return str;
        }
        else {
            //Log.d("Caste_Name", str);
            return str;
        }
    }

    public int GetCasteCode(String str, int catCode){
        int num=0;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+ Table_CASTE_EXCEPT_OBC+" where CM_caste_kdesc='"+str+"' and CM_res_category_code = "+catCode+" order by CM_caste_kdesc", null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                num = cursor.getInt(cursor.getColumnIndex("CM_CASTE_ID"));
            }
            return num;
        }
        Log.d("Caste_Code", String.valueOf(num));
        return num;
    }

    public String GetCaste_OBC_BY_Code(int casteCode){
        String str="";
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+ Table_CASTE_OBC +" where OBC_OCM_CASTE_ID="+casteCode+" order by CM_caste_edesc", null);
        if(cursor.getCount()>0){
            if (cursor.moveToNext()){
                str = cursor.getString(cursor.getColumnIndexOrThrow("CM_caste_edesc"));
            }
            return str;
        }
        Log.d("Caste", str);
        return str;
    }

    public int GetCasteCode_OBC(String str){
        int num=0;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+ Table_CASTE_OBC+" where CM_caste_edesc='"+str+"' order by CM_caste_edesc", null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                num = cursor.getInt(cursor.getColumnIndex("OBC_OCM_CASTE_ID"));
            }
            return num;
        }
        Log.d("Caste_Code", String.valueOf(num));
        return num;
    }

    public String GetID_Name(int Id_Code, String col_name){
        Log.d("ID_Code: ",""+Id_Code);
        String str=null;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+ ID_Master_tbl + " where "+ID_Code+"="+Id_Code, null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                str = cursor.getString(cursor.getColumnIndexOrThrow(col_name));
            }
            return str;
        }
        Log.d("ID_Name: ",""+str);
        return str;
    }

    private void CopyReasonDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME_reasons);

        // Path to the just created empty db
        String outFileName = getReasonDatabasePath();

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

    private void CopyCatCasteDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME_cat_caste);

        // Path to the just created empty db
        String outFileName = getCatCasteDatabasePath();

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

    private void CopyDocTypeDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME_docs_type);

        // Path to the just created empty db
        String outFileName = getDocsTypeDatabasePath();

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

    private void CopyTownWardDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_NAME_town_ward);

        // Path to the just created empty db
        String outFileName = getTownWardDatabasePath();

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

    private void CopyIDTypeDataBaseFromAsset() throws IOException {

        InputStream myInput = ctx.getAssets().open(DATABASE_Name_ID_Type);

        // Path to the just created empty db
        String outFileName = getIDTypeDatabasePath();

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

    private static String getReasonDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME_reasons;
    }

    private static String getCatCasteDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME_cat_caste;
    }

    private static String getDocsTypeDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME_docs_type;
    }

    private static String getTownWardDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME_town_ward;
    }

    private static String getIDTypeDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_Name_ID_Type;
    }

    public void open_Reasons_Master_Tbl() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME_reasons);

        if (!dbFile.exists()) {
            try {
                CopyReasonDataBaseFromAsset();
                System.out.println("Copying success from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public void open_Cat_Caste_Tbl() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME_cat_caste);

        if (!dbFile.exists()) {
            try {
                CopyCatCasteDataBaseFromAsset();
                System.out.println("Copying success from Assets folder1");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database1", e);
            }
        }

        SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public void open_Docs_Type_Tbl() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME_docs_type);

        if (!dbFile.exists()) {
            try {
                CopyDocTypeDataBaseFromAsset();
                System.out.println("Copying success from Assets folder1");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database1", e);
            }
        }

        SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public void open_Town_Ward_Tbl() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME_town_ward);

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

    public void open_ID_Type_Tbl() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_Name_ID_Type);

        if (!dbFile.exists()) {
            try {
                CopyIDTypeDataBaseFromAsset();
                System.out.println("Copying success from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }
}
