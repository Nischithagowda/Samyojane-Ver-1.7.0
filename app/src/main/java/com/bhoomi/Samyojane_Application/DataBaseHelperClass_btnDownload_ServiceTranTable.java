package com.bhoomi.Samyojane_Application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperClass_btnDownload_ServiceTranTable extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    @SuppressLint("SdCardPath")
    static final String DB_PATH = "/sdcard/Samyojane/databases/";
    public static String DATABASE_NAME= "ServiceTranTable.db";
    public static String TABLE_NAME="ServiceTranTable";
    public static String TABLE_NAME_1="ServiceParameters";
    public static String District_Code = "ST_district_code";
    public static String Taluk_Code = "ST_taluk_code";
    public static String Hobli_Code = "ST_hobli_code";
    public static String va_Circle_Code = "ST_va_Circle_Code";
    public static String Village_Code = "ST_village_code";
    public static String Habitation_code = "ST_habitation_code";
    public static String Town_Code = "ST_town_code";
    public static String Ward_Code = "ST_ward_no";
    public static String Service_Code = "ST_facility_code";
    public static String RD_No = "ST_GSC_No";
    public static String Applicant_Name = "ST_applicant_name";
    public static String Due_Date = "ST_DueDate";
    public static String Raised_Location = "ST_Raised_Location";
    public static String Father_Name = "ST_father_name";
    public static String Mother = "ST_mother_name";
    public static String RationCard_No = "ST_ID_NUMBER";
    public static String Aadhar_NO = "ST_Aadhaar_EID_No";
    public static String Mobile_No = "ST_mobile_no";
    public static String Address1 = "ST_applicant_caddress1";
    public static String Address2 = "ST_applicant_caddress2";
    public static String Address3 = "ST_applicant_caddress3";
    public static String ST_applicant_cadd_pin = "ST_applicant_cadd_pin";
    public static String ST_applicant_photo = "ST_applicant_photo";
    public static String ID_TYPE = "ST_ID_TYPE";
    public static String ST_Eng_Certificate = "ST_Eng_Certificate";
    public static String ST_GSCFirstPart = "ST_GSCFirstPart";
    public static String GSCFirstPart_Name = "GSCFirstPart_Name";
    public static String CST_res_category = "CST_res_category";
    public static String CST_caste_as_per_app = "CST_caste_as_per_app";
    public static String CST_annual_income = "CST_annual_income";
    public static String SCOT_caste_app = "SCOT_caste_app";
    public static String SCOT_annual_income = "SCOT_annual_income";
    public static String GST_No_Years_Applied = "GST_No_Years_Applied";
    public static String GST_No_Mths_Applied = "GST_No_Mths_Applied";
    public static String vLat = "vLat";
    public static String vLong = "vLong";
    public static String Photo = "Photo";
    public static String DataUpdateFlag = "DataUpdateFlag";
    public static String Service_Name = "Service_Name";
    public static String Service_Name_k = "Service_Name_k";

    public static String U_RationCard_No = "ST_Upd_ID_NUMBER";
    public static String U_Mobile_No = "ST_Upd_mobile_no";
    public static String PinCode = "ST_PinCode";
    public static String Report_No = "Report_No";

    public static String VA_Accepts_Applicant_information = "VA_Accepts_Applicant_information";
    public static String Applicant_Category = "Applicant_Category";
    public static String Applicant_Caste = "Applicant_Caste";
    public static String Can_Certificate_Given = "Can_Certificate_Given";
    public static String Reason_for_Rejection = "Reason_for_Rejection";
    public static String Annual_Income = "Annual_Income";
    public static String UID = "UID";
    public static String AadhaarPhoto = "AadhaarPhoto";

    //Service Parameters of service_code-6
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

    //Update By
    public static String Updated_By_VA_IMEI = "Updated_By_VA_IMEI";
    public static String Updated_By_VA_MobileNum = "Updated_By_VA_MobileNum";
    public static String Updated_By_VA_Name = "Updated_By_VA_Name";

    //RI Module

    public static String RI_vLat = "RI_vLat";
    public static String RI_vLong = "RI_vLong";
    public static String RI_DataUpdateFlag = "RI_DataUpdateFlag";

    public static String RI_Applicant_Category = "RI_Applicant_Category";
    public static String RI_Applicant_Caste = "RI_Applicant_Caste";
    public static String RI_Accepted_VA_information = "RI_Accepted_VA_information";
    public static String RI_Annual_Income = "RI_Annual_Income";

    //Service Parameters of service_code-6
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

    //Update By
    public static String Updated_By_RI_IMEI = "Updated_By_RI_IMEI";
    public static String Updated_By_RI_MobileNum = "Updated_By_RI_MobileNum";
    public static String Updated_By_RI_Name = "Updated_By_RI_Name";

    //Additional Parameters for RI
    public static String RI_Can_Certificate_Given_as_RI = "RI_Can_Certificate_Given_as_RI";
    public static String RI_Reason_for_Rejection_as_RI = "RI_Reason_for_Rejection_as_RI";

    private static String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME +"("+District_Code+" int,"+Taluk_Code+" int,"+Hobli_Code+" int,"+Village_Code+" int,"+ Habitation_code +" int,"+ Town_Code+" int,"+Ward_Code+" int,"+Service_Code+" int,"+ Service_Name+" TEXT,"+ Service_Name_k+" TEXT,"+RD_No+" int,"
            + Applicant_Name+" TEXT,"+Due_Date+" datetime,"+Raised_Location+" TEXT,"+ Father_Name +" TEXT,"+Mother+" TEXT,"+ ID_TYPE +" int,"+RationCard_No+" TEXT,"+Aadhar_NO+" decimal(12,0),"
            + Mobile_No+" decimal(10,0),"+Address1+" TEXT,"+Address2+" TEXT,"+Address3+" TEXT,"+ ST_applicant_cadd_pin+" int,"+ ST_applicant_photo+" TEXT, "+ ST_Eng_Certificate+" TEXT,"+ ST_GSCFirstPart+" int,"+ GSCFirstPart_Name+" TEXT,"
            + CST_res_category+ " TEXT,"+ CST_caste_as_per_app+ " TEXT,"
            + CST_annual_income + " TEXT,"+ SCOT_caste_app + " TEXT,"+ SCOT_annual_income + " TEXT,"+ GST_No_Years_Applied +" TEXT,"
            + GST_No_Mths_Applied + " TEXT," + DataUpdateFlag+" int)";

    private static String CREATE_TABLE_1 ="CREATE TABLE " + TABLE_NAME_1 +"("+District_Code+" int,"+Taluk_Code+" int,"+Hobli_Code+" int,"+va_Circle_Code+" int,"+Village_Code+ " int,"+ Habitation_code +" int,"+ Town_Code+" int,"+Ward_Code+" int,"+Service_Code+" int,"+RD_No+" bigInt,"
            + Applicant_Name+" TEXT,"+ Father_Name +" TEXT,"+Mother+" TEXT,"+ U_Mobile_No+" decimal(10,0),"+U_RationCard_No+" TEXT,"+Address1+" TEXT,"
            + Address2+" TEXT,"+Address3+" TEXT,"+ PinCode+" int,"+ST_applicant_photo+" TEXT,"+ ST_Eng_Certificate+" TEXT,"+ ST_GSCFirstPart+" int,"+ VA_Accepts_Applicant_information +" TEXT,"
            + UID+" TEXT,"+AadhaarPhoto +" TEXT,"+Due_Date+" datetime,"+ Report_No+" TEXT,"
            + Applicant_Category +" int,"+Applicant_Caste +" int,"+ Belongs_Creamy_Layer_6 +" TEXT,"+ Reason_for_Creamy_Layer_6 +" int," + Annual_Income +" float,"
            + Num_Years_8+" TEXT," + App_Father_Category_8+ " int," + APP_Father_Caste_8+ " int," + App_Mother_Category_8+ " int," + APP_Mother_Caste_8+ " int,"
            + Remarks+" TEXT,"
            + Total_No_Years_10+" int,"+ NO_Months_10+" int," +Reside_At_Stated_Address_10+" TEXT,"+ Place_Match_With_RationCard_10+" TEXT," + Pur_for_Cert_Code_10+" int,"
            + Photo+" TEXT,"+vLat+" float,"+vLong+" float,"+ Can_Certificate_Given +" TEXT," + Reason_for_Rejection +" int,"
            + Updated_By_VA_IMEI + " TEXT,"+ Updated_By_VA_MobileNum+" TEXT,"+Updated_By_VA_Name+" TEXT,"
            + DataUpdateFlag+" int,"
            + RI_Applicant_Category +" int,"+RI_Applicant_Caste +" int,"+ RI_Belongs_Creamy_Layer_6 +" TEXT,"+ RI_Reason_for_Creamy_Layer_6 +" int," + RI_Annual_Income +" float,"
            + RI_Num_Years_8+" TEXT," + RI_App_Father_Category_8+ " int," + RI_APP_Father_Caste_8+ " int," + RI_App_Mother_Category_8+ " int," + RI_APP_Mother_Caste_8+ " int,"
            + RI_Remarks+" TEXT,"
            + RI_Total_No_Years_10+" int,"+ RI_NO_Months_10+" int,"+ RI_Reside_At_Stated_Address_10+" TEXT,"+ RI_Place_Match_With_RationCard_10+" TEXT," + RI_Pur_for_Cert_Code_10+" int,"
            + RI_vLat+" float,"+RI_vLong+" float,"+ RI_Accepted_VA_information +" TEXT,"
            + Updated_By_RI_IMEI + " TEXT,"+ Updated_By_RI_MobileNum+" TEXT,"+Updated_By_RI_Name+" TEXT,"
//            + RI_H_Phone_Number_1 + " TEXT,"+ RI_H_Phone_Number_2 + " TEXT,"+ RI_H_SP_SIM_1 +" TEXT,"+ RI_H_SP_SIM_2 +" TEXT,"+ RI_H_SIM_1_Tower_Loc +" TEXT,"
//            + RI_H_CellLocation_Id + " TEXT,"+ RI_H_Network_Operator + " TEXT,"+ RI_H_MCC + " TEXT,"+ RI_H_MNC + " TEXT,"+ RI_H_HiddenPhoto +" TEXT,"
            + RI_Can_Certificate_Given_as_RI+" TEXT,"+RI_Reason_for_Rejection_as_RI+" int,"
            + RI_DataUpdateFlag+" int)";

    public DataBaseHelperClass_btnDownload_ServiceTranTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("Note", "DataBase Opened");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_1);
        Log.i("Note",TABLE_NAME+" Table Created");
        Log.i("Note",TABLE_NAME_1+" Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);
        Log.i("Note","Table Upgraded");
        //db.execSQL(CREATE_TABLE);
    }
}