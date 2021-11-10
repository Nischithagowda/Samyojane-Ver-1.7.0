package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    @SuppressLint("SdCardPath")
    private static final String DB_PATH = "/sdcard/Samyojane/databases/";
    public static String DATABASE_NAME= "ServiceParameter_RI.db";
    public static String TABLE_NAME_1="ServiceParameters";

    public static String District_Code = "ST_district_code";
    public static String Taluk_Code = "ST_taluk_code";
    public static String Hobli_Code = "ST_hobli_code";
    public static String va_Circle_Code = "ST_va_Circle_Code";
    public static String Village_Code = "ST_village_code";
    public static String Town_Code = "ST_town_code";
    public static String Ward_Code = "ST_ward_no";
    public static String Service_Code = "ST_facility_code";
    public static String RD_No = "ST_GSC_No";
    public static String ST_applicant_photo = "ST_applicant_photo";
    public static String ST_Eng_Certificate = "ST_Eng_Certificate";
    public static String Applicant_Name = "ST_applicant_name";
    public static String Due_Date = "ST_DueDate";
    public static String Raised_Location = "ST_Raised_Location";

    public static String vLat = "vLat";
    public static String vLong = "vLong";
    public static String Photo = "Photo";
    public static String DataUpdateFlag = "DataUpdateFlag";

    public static String Service_Name = "Service_Name";
    public static String Service_Name_k = "Service_Name_k";

    public static String Can_Certificate_Given = "Can_Certificate_Given";
    public static String Reason_for_Rejection = "Reason_for_Rejection";
    public static String Annual_Income = "Annual_Income";

    //Service Parameters of service_code-6
    public static String Applicant_Category = "Applicant_Category";
    public static String Applicant_Caste = "Applicant_Caste";
    public static String Belongs_Creamy_Layer_6 = "Belongs_Creamy_Layer_6";
    public static String Reason_for_Creamy_Layer_6 = "Reason_for_Creamy_Layer_6";

    //Service Parameters of service_code-8
    public static String Num_Years_8 = "Num_Years_8";
    public static String App_Father_Category_8 = "App_Father_Category_8";
    public static String APP_Father_Caste_8 = "APP_Father_Caste_8";
    public static String App_Mother_Category_8 = "App_Mother_Category_8";
    public static String APP_Mother_Caste_8 = "APP_Mother_Caste_8";
    public static String Remarks = "Remarks";

    //Service Parameters of service_code-10
    public static String Total_No_Years_10 = "Total_No_Years_10";
    public static String NO_Months_10 = "NO_Months_10";
    public static String Reside_At_Stated_Address_10 = "Reside_At_Stated_Address_10";
    public static String Place_Match_With_RationCard_10 = "Place_Match_With_RationCard_10";
    public static String Pur_for_Cert_Code_10 = "Pur_for_Cert_Code_10";

//    //Hidden Details
//    public static String H_Phone_Number_1 = "H_Phone_Number_1";
//    public static String H_Phone_Number_2 = "H_Phone_Number_2";
//    public static String H_SP_SIM_1 = "H_SP_SIM_1";
//    public static String H_SP_SIM_2 = "H_SP_SIM_2";
//    public static String H_SIM_1_Tower_Loc = "H_SIM_1_Tower_Loc";
//    public static String H_CellLocation_Id = "H_CellLocation_Id";
//    public static String H_Network_Operator = "H_Network_Operator";
//    public static String H_MCC = "H_MCC";
//    public static String H_MNC = "H_MNC";
//    public static String H_HiddenPhoto = "H_HiddenPhoto";

    //Update By
    public static String Updated_By_VA_IMEI = "Updated_By_VA_IMEI";
    public static String Updated_By_VA_MobileNum = "Updated_By_VA_MobileNum";
    public static String Updated_By_VA_Name = "Updated_By_VA_Name";

    //RI Module
    public static String RI_vLat = "RI_vLat";
    public static String RI_vLong = "RI_vLong";
    public static String RI_Report_No = "RI_Report_No";
    public static String RI_DataUpdateFlag = "RI_DataUpdateFlag";

    public static String RI_Accepted_VA_information = "RI_Accepted_VA_information";
    public static String RI_Annual_Income = "RI_Annual_Income";

    //Service Parameters of service_code-6
    public static String RI_Applicant_Category = "RI_Applicant_Category";
    public static String RI_Applicant_Caste= "RI_Applicant_Caste";
    public static String RI_Belongs_Creamy_Layer_6 = "RI_Belongs_Creamy_Layer_6";
    public static String RI_Reason_for_Creamy_Layer_6 = "RI_Reason_for_Creamy_Layer_6";

    //Service Parameters of service_code-8
    public static String RI_Num_Years_8 = "RI_Num_Years_8";
    public static String RI_App_Father_Category_8 = "RI_App_Father_Category_8";
    public static String RI_APP_Father_Caste_8 = "RI_APP_Father_Caste_8";
    public static String RI_App_Mother_Category_8 = "RI_App_Mother_Category_8";
    public static String RI_APP_Mother_Caste_8 = "RI_APP_Mother_Caste_8";
    public static String RI_Remarks = "RI_Remarks";

    //Service Parameters of service_code-10
    public static String RI_Total_No_Years_10 = "RI_Total_No_Years_10";
    public static String RI_NO_Months_10 = "RI_NO_Months_10";
    public static String RI_Reside_At_Stated_Address_10 = "RI_Reside_At_Stated_Address_10";
    public static String RI_Place_Match_With_RationCard_10 = "RI_Place_Match_With_RationCard_10";
    public static String RI_Pur_for_Cert_Code_10 = "RI_Pur_for_Cert_Code_10";

//    //Hidden Details
//    public static String RI_H_Phone_Number_1 = "RI_H_Phone_Number_1";
//    public static String RI_H_Phone_Number_2 = "RI_H_Phone_Number_2";
//    public static String RI_H_SP_SIM_1 = "RI_H_SP_SIM_1";
//    public static String RI_H_SP_SIM_2 = "RI_H_SP_SIM_2";
//    public static String RI_H_SIM_1_Tower_Loc = "RI_H_SIM_1_Tower_Loc";
//    public static String RI_H_CellLocation_Id = "RI_H_CellLocation_Id";
//    public static String RI_H_Network_Operator = "RI_H_Network_Operator";
//    public static String RI_H_MCC = "RI_H_MCC";
//    public static String RI_H_MNC = "RI_H_MNC";
//    public static String RI_H_HiddenPhoto = "RI_H_HiddenPhoto";

    //Update By
    public static String Updated_By_RI_IMEI = "Updated_By_RI_IMEI";
    public static String Updated_By_RI_MobileNum = "Updated_By_RI_MobileNum";
    public static String Updated_By_RI_Name = "Updated_By_RI_Name";

    //Additional Parameters for RI
    public static String RI_Can_Certificate_Given_as_RI = "RI_Can_Certificate_Given_as_RI";
    public static String RI_Reason_for_Rejection_as_RI = "RI_Reason_for_Rejection_as_RI";

    static String CREATE_TABLE_1 ="CREATE TABLE " + TABLE_NAME_1 +"("+District_Code+" int,"+Taluk_Code+" int,"+Hobli_Code+" int,"+va_Circle_Code+" int,"+Village_Code+" int,"+ Town_Code+" int,"+Ward_Code+" int,"
            +Service_Code+" int,"+ Service_Name+" TEXT,"+ Service_Name_k+" TEXT,"+RD_No+" bigInt,"+ST_applicant_photo+" TEXT,"+ST_Eng_Certificate+" TEXT,"+ Applicant_Name+" TEXT,"
            + Due_Date +" datetime,"+ Raised_Location +" TEXT,"
            + Applicant_Category +" int,"+Applicant_Caste +" int,"+ Belongs_Creamy_Layer_6 +" TEXT,"+ Reason_for_Creamy_Layer_6 +" int," + Annual_Income +" float,"
            + Num_Years_8+" TEXT," + App_Father_Category_8+ " int," + APP_Father_Caste_8+ " int," + App_Mother_Category_8+ " int," + APP_Mother_Caste_8+ " int,"
            + Remarks+" TEXT,"
            + Total_No_Years_10+" int,"+ NO_Months_10+" int," +Reside_At_Stated_Address_10+" TEXT,"+ Place_Match_With_RationCard_10+" TEXT," + Pur_for_Cert_Code_10+" int,"
            + Photo+" TEXT,"+vLat+" float,"+vLong+" float,"+ Can_Certificate_Given +" TEXT," + Reason_for_Rejection +" int,"
            + Updated_By_VA_IMEI + " TEXT,"+ Updated_By_VA_MobileNum+" TEXT,"+Updated_By_VA_Name+" TEXT,"
//            + H_Phone_Number_1 + " TEXT,"+ H_Phone_Number_2 + " TEXT,"+ H_SP_SIM_1 +" TEXT,"+ H_SP_SIM_2 +" TEXT,"+ H_SIM_1_Tower_Loc +" TEXT,"
//            + H_CellLocation_Id + " TEXT,"+ H_Network_Operator + " TEXT,"+ H_MCC + " TEXT,"+ H_MNC + " TEXT,"+ H_HiddenPhoto +" TEXT,"
            + DataUpdateFlag+" int,"
            + RI_Applicant_Category +" int,"+RI_Applicant_Caste +" int,"+ RI_Belongs_Creamy_Layer_6 +" TEXT,"+ RI_Reason_for_Creamy_Layer_6 +" int," + RI_Annual_Income +" float,"
            + RI_Num_Years_8+" TEXT," + RI_App_Father_Category_8+ " int," + RI_APP_Father_Caste_8+ " int," + RI_App_Mother_Category_8+ " int," + RI_APP_Mother_Caste_8+ " int,"
            + RI_Remarks+" TEXT,"
            + RI_Total_No_Years_10+" int,"+ RI_NO_Months_10+" int,"+ RI_Reside_At_Stated_Address_10+" TEXT,"+ RI_Place_Match_With_RationCard_10+" TEXT," + RI_Pur_for_Cert_Code_10+" int,"
            + RI_vLat+" float,"+RI_vLong+" float,"+ RI_Accepted_VA_information +" TEXT,"
            + Updated_By_RI_IMEI + " TEXT,"+ Updated_By_RI_MobileNum+" TEXT,"+Updated_By_RI_Name+" TEXT,"
//            + RI_H_Phone_Number_1 + " TEXT,"+ RI_H_Phone_Number_2 + " TEXT,"+ RI_H_SP_SIM_1 +" TEXT,"+ RI_H_SP_SIM_2 +" TEXT,"+ RI_H_SIM_1_Tower_Loc +" TEXT,"
//            + RI_H_CellLocation_Id + " TEXT,"+ RI_H_Network_Operator + " TEXT,"+ RI_H_MCC + " TEXT,"+ RI_H_MNC + " TEXT,"+ RI_H_HiddenPhoto +" TEXT,"
            + RI_Can_Certificate_Given_as_RI+" TEXT,"+RI_Reason_for_Rejection_as_RI+" int,"+ RI_Report_No+" TEXT,"
            + RI_DataUpdateFlag+" int)";

    public DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("Note", "DataBase Opened");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_1);
        Log.i("Note",TABLE_NAME_1+" Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);
        Log.i("Note","Table Upgraded");
        //db.execSQL(CREATE_TABLE);
    }
}
