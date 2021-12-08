package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperClass_btnDownload_ServiceTranTable extends SQLiteOpenHelper {

    static int DATABASE_VERSION=1;
    public static String DATABASE_NAME= "ServiceTranTable.db";
    public static String TABLE_NAME="ServiceTranTbl";
    public static String TABLE_NAME_1="ServiceParameterTbl";

    //ST Table Values
    public static String GSCNo = "GSCNo";
    public static String Service_Code = "FacilityCode";
    public static String District_Code = "DistrictCode";
    public static String Taluk_Code = "TalukCode";
    public static String Hobli_Code = "HobliCode";
    public static String Village_Code = "VillageCode";
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
    public static String Push_Flag = "isVAModifiedVillage";
    public static String VA_IMEI = "VA_IMEI";
    public static String VA_Name = "VA_Name";

    //Updated ST Table Values
    public static String UPD_GSCNo = "GscNo1";
    public static String UPD_LoginID = "LoginID";
    public static String UPD_Service_Code = "FacilityCode";
    public static String UPD_DesignationCode = "DesignationCode";
    public static String UPD_DifferFromAppinformation = "DifferFromApplicant";
    public static String UPD_Can_Certificate_Given = "CanbeIssued";
    public static String UPD_Remarks = "Remarks";
    public static String UPD_Report_No = "ReportNo";
    public static String UPD_ReportDate = "ReportDate";
    public static String UPD_AppTitle = "AppTitle";
    public static String UPD_BinCom = "BinCom";
    public static String UPD_FatTitle = "FatTitle";
    public static String UPD_FatherName = "FatherName";
    public static String UPD_MotherName = "MotherName";
    public static String UPD_MobileNumber = "MobileNumber";
    public static String UPD_Address1 = "Address1";
    public static String UPD_Address2 = "Address2";
    public static String UPD_Address3 = "Address3";
    public static String UPD_PinCode = "Pincode";
    public static String UPD_Applicant_Category = "ResCatCode";
    public static String UPD_Applicant_Caste = "CasteCode";
    public static String UPD_CasteSl = "CasteSl";
    public static String UPD_Income = "Income";
    public static String UPD_Total_No_Years_10 = "NoofYears";
    public static String UPD_NO_Months_10 = "NoofMonths";
    public static String UPD_App_Father_Category_8 = "FatherCategory";
    public static String UPD_App_Mother_Category_8 = "MotherCategory";
    public static String UPD_APP_Father_Caste_8 = "FatherCaste";
    public static String UPD_APP_Mother_Caste_8 = "MotherCaste";
    public static String UPD_Belongs_Creamy_Layer_6 = "CreamyLayer";
    public static String UPD_Reason_for_Creamy_Layer_6 = "ReasonCreamyLayer";
    public static String UPD_Reside_At_Stated_Address_10 = "ResAddress";
    public static String UPD_Place_Match_With_RationCard_10 = "PlaceMatchWithRationCard";
    public static String UPD_Photo = "Photo";
    public static String UPD_vLat = "vLat";
    public static String UPD_vLong = "vLong";
    public static String UPD_UploadedDate = "UploadedDate";
    public static String UPD_DataUpdateFlag = "DataUpdateFlag";
    public static String UPD_VA_RI_IMEI = "IMEI";
    public static String UPD_VA_RI_Name = "VARIName";

    String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME +"("
            + District_Code+" int,"+Taluk_Code+" int,"+Hobli_Code+" int,"+Village_Code+" int,"+ Town_Code+" int,"
            + Ward_Code+" int,"+ApplicationDate+" TEXT,"+Service_Code+" int,"+ Service_Name+" TEXT,"
            + Service_Name_k+" TEXT,"+ GSCNo +" bigint," + ApplicantTiitle+ " int,"+ Applicant_Name+" TEXT,"
            + Due_Date+" datetime,"+Raised_Location+" TEXT,"+ BinCom+ " int,"+ RelationTitle+ " int,"
            + FatherName +" TEXT,"+ MotherName +" TEXT,"+ ID_TYPE +" int,"+ IDNo +" TEXT," + Mobile_No+" decimal(10,0),"
            + Address1+" TEXT,"+Address2+" TEXT,"+Address3+" TEXT,"+ PinCode +" int,"+ ST_applicant_photo+" TEXT, "
            + ST_Eng_Certificate+" TEXT," + ReservationCategory+ " int,"+ Caste+ " int,"+ GST_No_Mths_Applied+ " int,"
            + GST_No_Years_Applied+ " int," + AnnualIncome + " TEXT," + Push_Flag + " TEXT,"+ VA_IMEI + " TEXT,"
            + VA_Name + " TEXT," + DataUpdateFlag+" int)";

    String CREATE_TABLE_1 ="CREATE TABLE " + TABLE_NAME_1 +"("
            + UPD_GSCNo +" TEXT," + UPD_LoginID+" TEXT," + UPD_Service_Code +" int," + UPD_DesignationCode +" int,"
            + UPD_DifferFromAppinformation +" TEXT,"//character
            + UPD_Can_Certificate_Given +" TEXT,"//character
            + UPD_Remarks +" TEXT," + UPD_Report_No +" TEXT," + UPD_ReportDate +" datetime," + UPD_AppTitle +" int," + UPD_BinCom +" int,"
            + UPD_FatTitle +" int," + UPD_FatherName +" TEXT," + UPD_MotherName +" TEXT," + UPD_MobileNumber +" decimal(10,0),"
            + UPD_Address1 +" TEXT," + UPD_Address2 +" TEXT," + UPD_Address3 +" TEXT," + UPD_PinCode +" int," + UPD_Applicant_Category +" int,"
            + UPD_Applicant_Caste +" int," + UPD_CasteSl +" int," + UPD_Income +" int," + UPD_Total_No_Years_10 +" int,"
            + UPD_NO_Months_10 +" int," + UPD_App_Father_Category_8 +" int," + UPD_App_Mother_Category_8 +" int,"
            + UPD_APP_Father_Caste_8 +" int," + UPD_APP_Mother_Caste_8 +" int," + UPD_Belongs_Creamy_Layer_6 +" TEXT,"
            + UPD_Reason_for_Creamy_Layer_6 +" int," + UPD_Reside_At_Stated_Address_10 +" TEXT,"
            + UPD_Place_Match_With_RationCard_10 +" TEXT," + UPD_Photo +" int," + UPD_vLat +" double," + UPD_vLong +" double,"
            + UPD_UploadedDate +" datetime," + UPD_DataUpdateFlag +" int," + UPD_VA_RI_IMEI +" bigint,"
            + UPD_VA_RI_Name +" TEXT)";

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
        db.execSQL(CREATE_TABLE);
    }
}