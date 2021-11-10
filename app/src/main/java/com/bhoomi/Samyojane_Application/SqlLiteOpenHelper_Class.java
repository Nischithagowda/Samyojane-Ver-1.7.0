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

public class SqlLiteOpenHelper_Class extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DB_PATH_SUFFIX = "/databases/";

    private SQLiteOpenHelper openHelper;
    SQLiteDatabase database;
    Cursor cursor;
    int townCode, wardCode;
    int town_totalCount, ward_totalCount;

    private static final String DATABASE_NAME_reasons = "Reasons_Master.db";
    String Table_1_re = "CreamyLayerReasons";
    String Table_2_re = "CertificateRejectionReason";
    String Table_3_re = "Purpose_of_Certificate";
//    private String Reasons_e = "Reasons";
//    private String Reasons_k = "Reasons_k";
//    private String Purpose_e = "Purpose";
//    private String Purpose_k = "Purpose_k";
    String SlNo = "SlNo";

    private static final String DATABASE_NAME_cat_caste = "CATEGORY_CASTE_MASTER.sqlite";
    static String Table_CAT_MASTER = "CAT_MASTER";
    static String Table_CASTE_EXCEPT_OBC = "CASTE_EXCEPT_OBC";
    static String Table_CASTE_OBC = "CASTE_OBC";

    private static final String DATABASE_NAME_docs_type = "DOCUMENT_TYPE_MASTER.db";
    public static String Table_DOCS_Type = "DOCUMENT_TYPE_MASTER";
    public static String Doc_Code = "DTM_document_code";
    public static String Doc_Name_E = "DTM_document_edesc";
    public static String Doc_Name_K = "DTM_document_kdesc";

    private static final String DATABASE_NAME_town_ward = "TOWN_WARD_MASTER.db";

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
    //public String WM_va_circle_code = "WM_va_circle_code";
    public String WM_ward_kname = "WM_ward_kname";
    public String WM_ward_ename = "WM_ward_ename";

    private static final String DATABASE_Name_ID_Type = "ID_MASTER.db";
    static String ID_Master_tbl="ID_MASTER";
    static String ID_Code = "ID_Code";
//    static String ID_Kname = "ID_Kname";
//    static String ID_Ename = "ID_Ename";

    @SuppressLint("StaticFieldLeak")
    private static Context ctx;

    public SqlLiteOpenHelper_Class(Context context) {
        super(context, DATABASE_NAME_reasons, null, DATABASE_VERSION);
        ctx = context;
    }

    public SqlLiteOpenHelper_Class(){
        super(ctx, DATABASE_NAME_cat_caste, null, DATABASE_VERSION);
    }

    public SqlLiteOpenHelper_Class(Activity activity, Context context){
        super(context, DATABASE_NAME_cat_caste, null, DATABASE_VERSION);
        ctx = context;
    }

    public SqlLiteOpenHelper_Class(Context context, String str){
        super(context, DATABASE_NAME_docs_type, null, DATABASE_VERSION);
        ctx = context;
    }

    public SqlLiteOpenHelper_Class(Context context, String str, String str1){
        super(context, DATABASE_NAME_town_ward, null, DATABASE_VERSION);
        ctx = context;
    }

    public SqlLiteOpenHelper_Class(Context context, String str, String str1, String str2){
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


    public List<SpinnerObject> Get_Category(String str, String add){
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
                        objects.add(new SpinnerObject(cursor.getString(cursor.getColumnIndex("RTM_res_category_code")), cursor.getString(cursor.getColumnIndex("RTM_res_category_edesc"))));
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
        cursor = database.rawQuery("select * from "+ Table_CAT_MASTER+ " where RTM_res_category_code="+catCode, null);
        if(cursor.getCount()>0){
            if(cursor.moveToFirst()){
                str = cursor.getString(cursor.getColumnIndexOrThrow("RTM_res_category_edesc"));
            }
            return str;
        }
        Log.d("Category", str);
        return str;
    }


    public int GetCategoryCode(String str){
        int num=0;
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+ Table_CAT_MASTER+" where RTM_res_category_edesc='"+str+"'", null);
            if(cursor.getCount()>0){
                if(cursor.moveToNext()){
                    num = cursor.getInt(cursor.getColumnIndex("RTM_res_category_code"));
                }
                return num;
            }
        Log.d("Category_Code", String.valueOf(num));
            return num;
    }

    public List<SpinnerObject> GetCaste(int num){
        List<SpinnerObject> objects = new ArrayList<>();
        try {
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+ Table_CASTE_EXCEPT_OBC+" where CM_res_category_code='"+num+"' order by CM_caste_edesc", null);
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

    public List<SpinnerObject> GetCaste_EWS(){
        List<SpinnerObject> objects = new ArrayList<>();
        try {
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+ Table_CASTE_EXCEPT_OBC+" where CM_isEWS='Y' order by CM_caste_edesc", null);
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
            Log.d("Catch", ""+ e);
        }
        Log.d("Caste", ""+ objects);
        return objects;
    }

    public List<AutoCompleteTextBox_Object> Get_TownName(int dist_Code, int taluk_Code, String town_Name){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("dist_Code", ""+ dist_Code);
        Log.d("taluk_Code", ""+ taluk_Code);
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_TOWN_MASTER+" where "
                    + TWM_district_code+"="+dist_Code+" and "
                    + TWM_taluk_code+"="+taluk_Code+" order by "+town_Name, null);
            if(cursor.getCount()>0){
                if(cursor.moveToNext()){
                    do{
                        townCode = cursor.getInt(cursor.getColumnIndexOrThrow(TWM_town_code));

                        openHelper=new DataBaseHelperClass_btnDownload_ServiceTranTable(ctx);
                        database=openHelper.getWritableDatabase();
                        Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                                + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"="+townCode + " and "
                                + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+"=99999" + " and "
                                + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + " is null ",null);
                        if(cursor1.getCount()>0){
                            town_totalCount = cursor1.getCount();
                            Log.d("Town_TotalCount", String.valueOf(town_totalCount));
                            objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(TWM_town_code)), (cursor.getString(cursor.getColumnIndexOrThrow(town_Name))+"-("+town_totalCount+")")));
                        }else {
                            cursor1.close();
                            objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(TWM_town_code)),cursor.getString(cursor.getColumnIndexOrThrow(town_Name))));
                        }
                    }while (cursor.moveToNext());
                }
            }
            cursor.close();
            database.close();
        }
        catch (Exception e){
            Log.d("Catch", ""+ e);
        }
        Log.d("Town", ""+ objects);
        return objects;
    }

    public List<AutoCompleteTextBox_Object> Get_TownName_NK(int dist_Code, int taluk_Code){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("dist_Code", ""+ dist_Code);
        Log.d("taluk_Code", ""+ taluk_Code);
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_TOWN_MASTER+" where "
                    + TWM_district_code+"="+dist_Code+" and "
                    + TWM_taluk_code+"="+taluk_Code+" order by "+TWM_town_kname, null);
            if(cursor.getCount()>0){
                if(cursor.moveToNext()){
                    do{
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(TWM_town_code)),cursor.getString(cursor.getColumnIndexOrThrow(TWM_town_kname))));
                    }while (cursor.moveToNext());
                }
            }
            cursor.close();
            database.close();
        }
        catch (Exception e){
            Log.d("Catch", ""+ e);
        }
        Log.d("Town", ""+ objects);
        return objects;
    }

    public List<AutoCompleteTextBox_Object> Get_TownName_RI(int dist_Code, int taluk_Code, String town_Name){
        List<AutoCompleteTextBox_Object> objects = new ArrayList<>();
        Log.d("dist_Code", ""+ dist_Code);
        Log.d("taluk_Code", ""+ taluk_Code);
        try{
            database = this.getReadableDatabase();
            cursor = database.rawQuery("select * from "+Table_TOWN_MASTER+" where "
                    + TWM_district_code+"="+dist_Code+" and "
                    + TWM_taluk_code+"="+taluk_Code+" order by "+town_Name, null);
            if(cursor.getCount()>0){
                if(cursor.moveToNext()){
                    do{
                        townCode = cursor.getInt(cursor.getColumnIndexOrThrow(TWM_town_code));

                        openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(ctx);
                        database=openHelper.getWritableDatabase();
                        Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1+" where "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"="+townCode + " and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"=99999" + " and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag + "=1 and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag+" is null ",null);
                        if(cursor1.getCount()>0){
                            town_totalCount = cursor1.getCount();
                            Log.d("Town_TotalCount", String.valueOf(town_totalCount));
                            objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(TWM_town_code)), (cursor.getString(cursor.getColumnIndexOrThrow(town_Name))+"-("+town_totalCount+")")));
                        }else {
                            cursor1.close();
                            objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(TWM_town_code)),cursor.getString(cursor.getColumnIndexOrThrow(town_Name))));
                        }
                    }while (cursor.moveToNext());
                }
            }
            cursor.close();
            database.close();
        }
        catch (Exception e){
            Log.d("Catch", ""+ e);
        }
        Log.d("Town", ""+ objects);
        return objects;
    }

    public int Get_TownCode(String townName){
        int townCode=0;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+Table_TOWN_MASTER+" where "+TWM_town_ename+"='"+townName+"'",null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                townCode = cursor.getInt(cursor.getColumnIndexOrThrow(TWM_town_code));
            }
            return townCode;
        }
        Log.d("townCode", ""+ townCode);
        return townCode;
    }

    public String Get_TownName(int distCode, int talukCode, int townCode){
        String townName=null;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+Table_TOWN_MASTER
                +" where "+TWM_district_code+"="+distCode+" and "
                +TWM_taluk_code+"="+talukCode+" and "
                +TWM_town_code+"="+townCode,null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                townName = cursor.getString(cursor.getColumnIndexOrThrow(TWM_town_kname));
            }
            return townName;
        }
        Log.d("townName", ""+ townName);
        return townName;
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

    public String Get_WardName_one(int distCode, int talukCode, int townCode, int wardCode){
        String wardName=null;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+Table_WARD_MASTER
                +" where "+WM_district_code+"="+distCode+" and "
                +WM_taluk_code+"="+talukCode+" and "
                +WM_town_code+"="+townCode+" and "
                +WM_ward_no+"="+wardCode,null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                wardName = cursor.getString(cursor.getColumnIndexOrThrow(WM_ward_kname));
            }
            return wardName;
        }
        Log.d("wardName", ""+ wardName);
        return wardName;
    }

    public List<AutoCompleteTextBox_Object> Get_WardName(int dist_Code, int taluk_Code, int townCode, String ward_name){
        Log.d("dist_Code", ""+ dist_Code);
        Log.d("taluk_Code", ""+ taluk_Code);
        Log.d("townCode", ""+ townCode);

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
                        wardCode = cursor.getInt(cursor.getColumnIndexOrThrow(WM_ward_no));

                        openHelper=new DataBaseHelperClass_btnDownload_ServiceTranTable(ctx);
                        database=openHelper.getWritableDatabase();

                        Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceTranTable.TABLE_NAME+" where "
                                + DataBaseHelperClass_btnDownload_ServiceTranTable.Town_Code+"="+townCode+" and "
                                + DataBaseHelperClass_btnDownload_ServiceTranTable.Ward_Code+"="+wardCode+" and "
                                + DataBaseHelperClass_btnDownload_ServiceTranTable.Village_Code+"=99999"+" and "
                                + DataBaseHelperClass_btnDownload_ServiceTranTable.DataUpdateFlag + " is null ",null);
                        if(cursor1.getCount()>0){
                            ward_totalCount = cursor1.getCount();
                            Log.d("wardCode", String.valueOf(wardCode));
                            Log.d("Town_TotalCount", String.valueOf(ward_totalCount));
                            objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(WM_ward_no)) , (cursor.getString(cursor.getColumnIndexOrThrow(ward_name))+":("+ward_totalCount+")")));
                        }else {
                            cursor1.close();
                            objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(WM_ward_no)) ,cursor.getString(cursor.getColumnIndexOrThrow(ward_name))));
                        }

                    }while (cursor.moveToNext());
                }
            }else {
                Log.d("wardCode", "No Ward found");
            }
            cursor.close();
            database.close();
        }catch (Exception e){
            Log.d("Catch", ""+ objects);
        }
        Log.d("Ward", ""+ objects);
        return objects;
    }

    public List<AutoCompleteTextBox_Object> Get_WardName_NK(int dist_Code, int taluk_Code, int townCode){
        Log.d("dist_Code", ""+ dist_Code);
        Log.d("taluk_Code", ""+ taluk_Code);
        Log.d("townCode", ""+ townCode);

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
                        objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(WM_ward_no)) ,cursor.getString(cursor.getColumnIndexOrThrow(WM_ward_kname))));
                    }while (cursor.moveToNext());
                }
            }else {
                Log.d("wardCode", "No Ward found");
            }
            cursor.close();
            database.close();
        }catch (Exception e){
            Log.d("Catch", ""+ objects);
        }
        Log.d("Ward_Names", ""+ objects);
        return objects;
    }

    public List<AutoCompleteTextBox_Object> Get_WardName_RI(int dist_Code, int taluk_Code, int townCode, String ward_Name){
        Log.d("dist_Code", String.valueOf(dist_Code));
        Log.d("taluk_Code", String.valueOf(taluk_Code));
        Log.d("townCode", String.valueOf(townCode));

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
                        wardCode = cursor.getInt(cursor.getColumnIndexOrThrow(WM_ward_no));
                        Log.d("wardCode", String.valueOf(wardCode));

                        openHelper=new DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(ctx);
                        database=openHelper.getWritableDatabase();

                        Cursor cursor1 = database.rawQuery("select * from "+DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.TABLE_NAME_1+" where "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Town_Code+"="+townCode+" and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Ward_Code+"="+wardCode+" and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.Village_Code+"=99999"+" and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.DataUpdateFlag + "=1 and "
                                + DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI.RI_DataUpdateFlag+" is null ",null);
                        if(cursor1.getCount()>0){
                            ward_totalCount = cursor1.getCount();
                            Log.d("Town_TotalCount", String.valueOf(ward_totalCount));
                            objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(WM_ward_no)),(cursor.getString(cursor.getColumnIndexOrThrow(ward_Name))+":("+ward_totalCount+")")));
                        }else {
                            cursor1.close();
                            objects.add(new AutoCompleteTextBox_Object(cursor.getString(cursor.getColumnIndexOrThrow(WM_ward_no)),(cursor.getString(cursor.getColumnIndexOrThrow(ward_Name)))));
                        }

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

    public int Get_WardCode(int townCode, String wardName){
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+Table_WARD_MASTER+" where "
                +WM_town_code+"="+townCode+""+" and "
                +WM_ward_ename+"='"+wardName+"'",null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                wardCode = cursor.getInt(cursor.getColumnIndexOrThrow(WM_ward_no));
            }
            return wardCode;
        }
        Log.d("townCode", ""+ wardCode);
        return wardCode;
    }

    public String GetCaste_BY_Code(int CatCode,int casteCode){
        String str=null;
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from "+Table_CASTE_EXCEPT_OBC+" where CM_res_category_code="+CatCode+" and CM_CASTE_ID="+casteCode +" order by CM_caste_edesc", null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                str = cursor.getString(cursor.getColumnIndexOrThrow("CM_caste_edesc"));
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
        cursor = database.rawQuery("select * from "+ Table_CASTE_EXCEPT_OBC+" where CM_caste_edesc='"+str+"' and CM_res_category_code = "+catCode+" order by CM_caste_edesc", null);
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                num = cursor.getInt(cursor.getColumnIndex("CM_CASTE_ID"));
            }
            return num;
        }
        Log.d("Caste_Code", ""+ num);
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
        Log.d("Caste", ""+str);
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
        Log.d("Caste_Code", ""+ num);
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
