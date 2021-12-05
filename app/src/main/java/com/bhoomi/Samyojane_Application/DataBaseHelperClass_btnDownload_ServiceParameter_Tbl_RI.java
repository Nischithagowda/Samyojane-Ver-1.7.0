package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI extends SQLiteOpenHelper {

    static int DATABASE_VERSION=1;
    public static String DATABASE_NAME= "ServiceTranTable_RI.db";
    public static String TABLE_NAME="ServiceTranTbl";
    public static String TABLE_NAME_1="ServiceParameterTbl";

    //ST Table Values
    public static String GSCNo = "GSCNo";
    public static String Service_Code = "FacilityCode";
    public static String District_Code = "DistrictCode";
    public static String Taluk_Code = "TalukCode";
    public static String Hobli_Code = "HobliCode";
    public static String Village_Code = "VillageCode";
    public static String villageCircle_Code = "villageCircleCode";
    public static String Town_Code = "TownCode";
    public static String Ward_Code = "WardNo";
    public static String ApplicationDate = "ApplicationDate";
    public static String ApplicantTiitle = "ApplicantTiitle";
    public static String Applicant_Name = "ApplicantName";
    public static String BinCom = "BinCom";
    public static String RelationTitle = "RelationTitle";
    public static String FatherName = "FatherName";
    public static String MotherName = "MotherName";
    public static String Address1 = "Address1";
    public static String Address2 = "Address2";
    public static String Address3 = "Address3";
    public static String PinCode = "Pincode";
    public static String Mobile_No = "MobileNo";
    public static String ID_TYPE = "IDType";
    public static String IDNo = "IDNo";
    public static String Raised_Location = "RaisedLocation";
    public static String ST_applicant_photo = "ApplicantPhoto";
    public static String ST_Eng_Certificate = "EnglishOrKannada";
    public static String ReservationCategory = "ReservationCategory";
    public static String Caste = "Caste";
    public static String AnnualIncome = "AnnualIncome";
    public static String Due_Date = "DueDate";
    public static String GST_No_Mths_Applied = "NoOfMonths_Applied";
    public static String GST_No_Years_Applied = "NoOfYears_Applied";
    public static String Service_Name = "Service_Name";
    public static String Service_Name_k = "Service_Name_k";
    public static String DataUpdateFlag = "DataUpdateFlag";
    public static String Push_Flag = "ST_Push_Flag";

    //Updated ST Table Values
    public static String GSCNo1 = "GscNo1";
    public static String LoginID = "LoginID";
    //    public static String Service_Code = "FacilityCode";
    public static String DesignationCode = "DesignationCode";
    public static String DifferFromAppinformation = "DifferFromApplicant";
    public static String Can_Certificate_Given = "CanbeIssued";
    public static String Remarks = "Remarks";
    public static String Report_No = "ReportNo";
    public static String ReportDate = "ReportDate";
    public static String AppTitle = "AppTitle";
    //    public static String BinCom = "BinCom";
    public static String FatTitle = "FatTitle";
    //    public static String FatherName = "FatherName";
//    public static String MotherName = "MotherName";
    public static String Upd_MobileNumber = "MobileNumber";
    //    public static String Address1 = "Address1";
//    public static String Address2 = "Address2";
//    public static String Address3 = "Address3";
//    public static String PinCode = "Pincode";
    public static String Applicant_Category = "ResCatCode";
    public static String Applicant_Caste = "CasteCode";
    public static String CasteSl = "CasteSl";
    public static String Income = "Income";
    public static String Total_No_Years_10 = "NoofYears";
    public static String NO_Months_10 = "NoofMonths";
    public static String App_Father_Category_8 = "FatherCategory";
    public static String App_Mother_Category_8 = "MotherCategory";
    public static String APP_Father_Caste_8 = "FatherCaste";
    public static String APP_Mother_Caste_8 = "MotherCaste";
    public static String Belongs_Creamy_Layer_6 = "CreamyLayer";
    public static String Reason_for_Creamy_Layer_6 = "ReasonCreamyLayer";
    public static String Reside_At_Stated_Address_10 = "ResAddress";
    public static String Place_Match_With_RationCard_10 = "PlaceMatchWithRationCard";
    public static String Photo = "Photo";
    public static String vLat = "vLat";
    public static String vLong = "vLong";
    public static String UploadedDate = "UploadedDate";
    //    public static String DataUpdateFlag = "DataUpdateFlag";
    public static String Updated_By_VA_IMEI = "IMEI";
    public static String Updated_By_VA_Name = "VARIName";

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

    //Additional Parameters for RI
    public static String RI_Can_Certificate_Given_as_RI = "RI_Can_Certificate_Given_as_RI";
    public static String RI_Reason_for_Rejection_as_RI = "RI_Reason_for_Rejection_as_RI";

    String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME +"("
            + District_Code+" int,"+Taluk_Code+" int,"+Hobli_Code+" int,"+Village_Code+" int,"+villageCircle_Code+" int,"
            + Town_Code+" int," + Ward_Code+" int,"+ApplicationDate+" TEXT,"+Service_Code+" int,"+ Service_Name+" TEXT,"
            + Service_Name_k+" TEXT,"+ GSCNo +" bigint," + ApplicantTiitle+ " int,"+ Applicant_Name+" TEXT,"
            + Due_Date+" datetime,"+Raised_Location+" TEXT,"+ BinCom+ " int,"+ RelationTitle+ " int,"
            + FatherName +" TEXT,"+ MotherName +" TEXT,"+ ID_TYPE +" int,"+ IDNo +" TEXT," + Mobile_No+" decimal(10,0),"
            + Address1+" TEXT,"+Address2+" TEXT,"+Address3+" TEXT,"+ PinCode +" int,"+ ST_applicant_photo+" TEXT, "
            + ST_Eng_Certificate+" TEXT," + ReservationCategory+ " int,"+ Caste+ " int,"+ GST_No_Mths_Applied+ " int,"
            + GST_No_Years_Applied+ " int," + AnnualIncome + " TEXT," + Push_Flag + " TEXT," + DataUpdateFlag+" int)";

    String CREATE_TABLE_1 ="CREATE TABLE " + TABLE_NAME_1 +"("
            + GSCNo1 +" TEXT," + LoginID+" TEXT," + Service_Code +" int," + DesignationCode +" int,"
            + DifferFromAppinformation +" TEXT,"//character
            + Can_Certificate_Given +" TEXT,"//character
            + Remarks +" TEXT," + Report_No +" TEXT," + ReportDate +" datetime," + AppTitle +" int," + BinCom +" int,"
            + FatTitle +" int," + FatherName +" TEXT," + MotherName +" TEXT," + Upd_MobileNumber +" decimal(10,0),"
            + Address1 +" TEXT," + Address2 +" TEXT," + Address3 +" TEXT," + PinCode +" int," + Applicant_Category +" int,"
            + Applicant_Caste +" int," + CasteSl +" int," + Income +" int," + Total_No_Years_10 +" int,"
            + NO_Months_10 +" int," + App_Father_Category_8 +" int," + App_Mother_Category_8 +" int,"
            + APP_Father_Caste_8 +" int," + APP_Mother_Caste_8 +" int," + Belongs_Creamy_Layer_6 +" TEXT,"
            + Reason_for_Creamy_Layer_6 +" int," + Reside_At_Stated_Address_10 +" TEXT,"
            + Place_Match_With_RationCard_10 +" TEXT," + Photo +" int," + vLat +" double," + vLong +" double,"
            + UploadedDate +" datetime," + DataUpdateFlag +" int," + Updated_By_VA_IMEI +" bigint,"
            + Updated_By_VA_Name +" TEXT)";

    public DataBaseHelperClass_btnDownload_ServiceParameter_Tbl_RI(Context context) {
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
        db.execSQL(CREATE_TABLE);
    }
}
