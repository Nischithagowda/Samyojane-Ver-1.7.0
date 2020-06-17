package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelperClass_btnDownload_E_Kshana extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    private static String DATABASE_NAME= "E_Kshana.db";

    static String TABLE_NAME_member_id = "RCMembers";
    static String TABLE_NAME_MemberDetails="RCMemberDetails";
    static String TABLE_NAME_UpdatedMemberDetails="UpdatedRCMemberDetails";
    static String TABLE_RC_Numbers_VA = "RCNumbersList";
    static String TABLE_GET_Assistant = "GETAssistant";

    static String RC_SLNo = "RC_SLNo";
    static String RC_DistCode = "RC_DistCode";
    static String RC_TalukCode = "RC_TalukCode";
    static String RC_HobliCode = "RC_HobliCode";
    static String RC_Num_List = "RAT_ACK_ID";

    static String RC_Num = "RC_Num";
    static String Member_name = "Member_name";
    static String otc_member_id = "otc_member_id";
    static String isDataEntered = "isDataEntered";
    static String VAUpdated = "VAUpdated";
    static String status = "status";

    static String RAT_ACK_ID = "RAT_ACK_ID";
    static String RAT_Member_ID = "RAT_Member_ID";
    static String RAT_Old_RC_NO = "RAT_Old_RC_NO";
    static String RAT_HoF_ID = "RAT_HoF_ID";
    static String RAT_HOF_Name = "RAT_HOF_Name";
    static String RAT_HOF_KName = "RAT_HOF_KName";
    static String RAT_Member_Name = "RAT_Member_Name";
    static String RAT_Member_KName = "RAT_Member_KName";
    static String RAT_Member_FMH_Name = "RAT_Member_FMH_Name";
    static String RAT_Member_FMH_KName = "RAT_Member_FMH_KName";
    static String RAT_Member_Gender = "RAT_Member_Gender";
    static String RAT_Member_BinCom = "RAT_Member_BinCom";
    static String RAT_Member_DOB = "RAT_Member_DOB";
    static String RAT_Member_YOB = "RAT_Member_YOB";
    static String RAT_Member_OccupationID = "RAT_Member_OccupationID";
    static String RAT_Member_Occupation_Desc = "RAT_Member_Occupation_Desc";
    static String RAT_Member_Occupation_KDesc = "RAT_Member_Occupation_KDesc";
    static String RAT_Member_Aadhaar_No = "RAT_Member_Aadhaar_No";
    static String RAT_Member_MobileNo = "RAT_Member_MobileNo";
    static String RAT_Member_Photo = "RAT_Member_Photo";
    static String RAT_Member_Address = "RAT_Member_Address";
    static String RAT_Member_KAddress = "RAT_Member_KAddress";
    static String RAT_Member_Pincode = "RAT_Member_Pincode";
    static String RAT_Relation_Id = "RAT_Relation_Id";
    static String RAT_Relation_With_HOH = "RAT_Relation_With_HOH";
    static String RAT_KRelation_With_HOH = "RAT_KRelation_With_HOH";
    static String RAT_District_Code_NK = "RAT_District_Code_NK";
    static String RAT_Taluk_Code_NK = "RAT_Taluk_Code_NK";
    static String RAT_Hobli_Code_NK = "RAT_Hobli_Code_NK";
    static String RAT_Town_Code_NK = "RAT_Town_Code_NK";
    static String RAT_Ward_No_NK = "RAT_Ward_No_NK";
    static String RAT_Village_Code_NK = "RAT_Village_Code_NK";
    static String RAT_Habitation_Code = "RAT_Habitation_Code";
    static String RAT_RC_NO = "RAT_RC_NO";
    static String RAT_TotFamily_Members = "RAT_TotFamily_Members";
    static String RAT_RDS_Cert_Caste = "RAT_RDS_Cert_Caste";
    static String RAT_RDS_Cert_Income = "RAT_RDS_Cert_Income";
    static String RAT_RDS_Cert_Res = "RAT_RDS_Cert_Res";
    static String RAT_BALANCE_OTC = "RAT_BALANCE_OTC";
    static String OTC_FAMILY_INCOME = "OTC_FAMILY_INCOME";
    static String OTC_MEMB_ID_TITLE = "OTC_MEMB_ID_TITLE";
    static String OTC_MEMB_BINCOM = "OTC_MEMB_BINCOM";
    static String OTC_MEMB_FATH_NAME = "OTC_MEMB_FATH_NAME";
    static String OTC_MEMB_MOTH_NAME = "OTC_MEMB_MOTH_NAME";
    static String OTC_MEMB_CAT = "OTC_MEMB_CAT";
    static String OTC_MEMB_CAST = "OTC_MEMB_CAST";
    static String RAT_RI_Field_Enq_Printed = "RAT_RI_Field_Enq_Printed";
    static String RAT_SendToRIDate = "RAT_SendToRIDate";
    static String RAT_ReceiveFromRIDate = "RAT_ReceiveFromRIDate";
    static String RAT_DataEntryDate = "RAT_DataEntryDate";
    static String RAT_LoginID_ReceiveFromRI = "RAT_LoginID_ReceiveFromRI";
    static String RAT_IsInactive = "RAT_IsInactive";
    static String RAT_InactiveReason = "RAT_InactiveReason";
    static String RAT_Caste_Address_NK = "RAT_Caste_Address_NK";
    static String RAT_Income_NK = "RAT_Income_NK";
    static String RAT_Income_Address_NK = "RAT_Income_Address_NK";
    static String RAT_isCasteCert_Available = "RAT_isCasteCert_Available";
    static String RAT_isIncomeCert_Available = "RAT_isIncomeCert_Available";

    static String RC_Dist_Code = "RC_Dist_Code";
    static String RC_Taluk_Code = "RC_Taluk_Code";
    static String RC_Hobli_code = "RC_Hobli_code";
    static String RC_Village_Code = "RC_Village_Code";
    static String RC_Habitation_Code = "RC_Habitation_Code";
    static String RC_Town_Code = "RC_Town_Code";
    static String RC_Ward_No = "RC_Ward_No";
    static String RC_Appl_RefNo = "RC_Appl_RefNo";
    static String RC_Application_ID = "RC_Application_ID";
    static String RC_Member_ID = "RC_Member_ID";
    static String RC_Salutation = "RC_Salutation";
    static String RC_Applicant_EName = "RC_Applicant_EName";
    static String RC_Applicant_KName = "RC_Applicant_KName";
    static String RC_Father_EName = "RC_Father_EName";
    static String RC_Father_KName = "RC_Father_KName";
    static String RC_Mother_EName = "RC_Mother_EName";
    static String RC_Mother_KName = "RC_Mother_KName";
    static String RC_Gender = "RC_Gender";
    static String RC_HOF_EName = "RC_HOF_EName";
    static String RC_HOF_KName = "RC_HOF_KName";
    static String RC_RelationWithHOF = "RC_RelationWithHOF";
    static String RC_ResidentYears_VA = "RC_ResidentYears_VA";
    static String RC_ResidentYears_RI = "RC_ResidentYears_RI";
    static String RC_EAddress1 = "RC_EAddress1";
    static String RC_EAddress2 = "RC_EAddress2";
    static String RC_EAddress3 = "RC_EAddress3";
    static String RC_KAddress1 = "RC_KAddress1";
    static String RC_KAddress2 = "RC_KAddress2";
    static String RC_KAddress3 = "RC_KAddress3";
    static String RC_PIN = "RC_PIN";
    static String RC_MobileNo = "RC_MobileNo";
    static String RC_Age = "RC_Age";
    static String RC_Religion = "RC_Religion";
    static String RC_app_Reservation_RI = "RC_app_Reservation_RI";
    static String RC_app_Reservation_VA = "RC_app_Reservation_VA";
    static String RC_CreamyLayer = "RC_CreamyLayer";
    static String RC_CreamyLayer_REPORT_VA = "RC_CreamyLayer_REPORT_VA";
    static String RC_CreamyLayer_REPORT_RI = "RC_CreamyLayer_REPORT_RI";
    static String RC_App_Caste_RI = "RC_App_Caste_RI";
    static String RC_App_Caste_VA = "RC_App_Caste_VA";
    static String RC_VAComments = "RC_VAComments";
    static String RC_RIComments = "RC_RIComments";
    static String RC_DataEntryDate = "RC_DataEntryDate";
    static String RC_IsUrban = "RC_IsUrban";
    static String RC_SendToNextLevel = "RC_SendToNextLevel";
    static String RC_FamilyIncome_RI = "RC_FamilyIncome_RI";
    static String RC_FamilyIncome_VA = "RC_FamilyIncome_VA";
    static String RC_GSCNo = "RC_GSCNo";
    static String RC_TahSigned = "RC_TahSigned";
    static String RC_DTSigned = "RC_DTSigned";
    static String RC_AddressFlag = "RC_AddressFlag";
    static String RC_IncomeFlag = "RC_IncomeFlag";
    static String RC_KBinCom = "RC_KBinCom";
    static String RC_EBinCom = "RC_EBinCom";

    static String A_Dist_code = "A_Dist_code";
    static String A_Taluk_code = "A_Taluk_code";
    static String A_Hobli_code = "A_Hobli_code";
    static String A_VA_circle_code = "A_VA_circle_code";
    static String A_Asst_Name = "A_Asst_Name";
    static String A_Asst_MobileNo = "A_Asst_MobileNo";

    private static String CREATE_TABLE_member_id = "CREATE TABLE "+ TABLE_NAME_member_id
            +"("+RC_Num+" TEXT,"+Member_name +" TEXT,"+otc_member_id+" INTEGER,"+isDataEntered+" TEXT,"
            + VAUpdated+" TEXT,"+status+" TEXT)";

    private static String CREATE_Table_Member_Details = "CREATE TABLE "+TABLE_NAME_MemberDetails
            +"("+RAT_ACK_ID+" TEXT,"
            + RAT_Member_ID+" INTEGER,"
            + RAT_District_Code_NK+" INTEGER,"
            + RAT_Taluk_Code_NK+" INTEGER,"
            + RAT_Hobli_Code_NK + " INTEGER,"
            + RAT_Town_Code_NK + " INTEGER,"
            + RAT_Ward_No_NK + " INTEGER,"
            + RAT_Village_Code_NK + " INTEGER,"
            + RAT_Habitation_Code + " INTEGER,"
            + RAT_HoF_ID + " TEXT,"
            + RAT_HOF_Name + " TEXT,"
            + RAT_HOF_KName + " TEXT,"
            + RAT_Member_Name + " TEXT,"
            + RAT_Member_KName + " TEXT,"
            + RAT_Member_Gender + " TEXT,"
            + RAT_Member_BinCom + " TEXT,"
            + RAT_Member_DOB + " TEXT,"
            + RAT_Member_YOB + " TEXT,"
            + RAT_Member_MobileNo + " TEXT,"
            + RAT_Member_Address + " TEXT,"
            + RAT_Member_KAddress + " TEXT,"
            + RAT_Member_Pincode + " TEXT,"
            + RAT_Relation_Id + " TEXT,"
            + RAT_Relation_With_HOH + " TEXT,"
            + RAT_KRelation_With_HOH + " TEXT,"
            + RAT_TotFamily_Members + " TEXT,"
            + RAT_RDS_Cert_Caste + " TEXT,"
            + RAT_RDS_Cert_Res + " TEXT,"
            + RAT_BALANCE_OTC + " TEXT,"
            + OTC_FAMILY_INCOME + " TEXT,"
            + OTC_MEMB_ID_TITLE + " TEXT,"
            + OTC_MEMB_BINCOM + " TEXT,"
            + OTC_MEMB_FATH_NAME + " TEXT,"
            + OTC_MEMB_MOTH_NAME + " TEXT,"
            + OTC_MEMB_CAT + " TEXT,"
            + OTC_MEMB_CAST + " TEXT,"
            + RAT_DataEntryDate + " TEXT,"
            + RAT_IsInactive + " TEXT,"
            + RAT_InactiveReason + " TEXT"
            +")";

    private static String CREATE_Table_Updated_Member_Details = "CREATE TABLE "+TABLE_NAME_UpdatedMemberDetails
            +"("+RC_Dist_Code+" TEXT,"
            +RC_Taluk_Code+" TEXT,"
            +RC_Hobli_code+" TEXT,"
            +RC_Village_Code+" TEXT,"
            +RC_Habitation_Code+" TEXT,"
            +RC_Town_Code+" TEXT,"
            +RC_Ward_No+" TEXT,"
            +RC_Appl_RefNo+" TEXT,"
            +RC_Application_ID+" TEXT,"
            +RC_Member_ID+" TEXT,"
            +RC_Salutation+" TEXT,"
            +RC_Applicant_EName+" TEXT,"
            +RC_Applicant_KName+" TEXT,"
            +RC_Father_EName+" TEXT,"
            +RC_Father_KName+" TEXT,"
            +RC_Mother_EName+" TEXT,"
            +RC_Mother_KName+" TEXT,"
            +RC_Gender+" TEXT,"
            +RC_HOF_EName+" TEXT,"
            +RC_HOF_KName+" TEXT,"
            +RC_RelationWithHOF+" TEXT,"
            +RC_ResidentYears_VA+" TEXT,"
            +RC_ResidentYears_RI+" TEXT,"
            +RC_EAddress1+" TEXT,"
            +RC_EAddress2+" TEXT,"
            +RC_EAddress3+" TEXT,"
            +RC_KAddress1+" TEXT,"
            +RC_KAddress2+" TEXT,"
            +RC_KAddress3+" TEXT,"
            +RC_PIN+" TEXT,"
            +RC_MobileNo+" TEXT,"
            +RC_Age+" TEXT,"
            +RC_Religion+" TEXT,"
            +RC_app_Reservation_RI+" TEXT,"
            +RC_app_Reservation_VA+" TEXT,"
            +RC_CreamyLayer+" TEXT,"
            +RC_CreamyLayer_REPORT_VA+" TEXT,"
            +RC_CreamyLayer_REPORT_RI+" TEXT,"
            +RC_App_Caste_RI+" TEXT,"
            +RC_App_Caste_VA+" TEXT,"
            +RC_VAComments+" TEXT,"
            +RC_RIComments+" TEXT,"
            +RC_DataEntryDate+" TEXT,"
            +RC_IsUrban+" TEXT,"
            +RC_SendToNextLevel+" TEXT,"
            +RC_FamilyIncome_RI+" TEXT,"
            +RC_FamilyIncome_VA+" TEXT,"
            +RC_GSCNo+" TEXT,"
            +RC_TahSigned+" TEXT,"
            +RC_DTSigned+" TEXT,"
            +RC_AddressFlag+" TEXT,"
            +RC_IncomeFlag+" TEXT,"
            +RC_KBinCom+" TEXT,"
            +RC_EBinCom+" TEXT"
            +")";

    private static String CREATE_TABLE_RC_Numbers_VA = "CREATE TABLE "+ TABLE_RC_Numbers_VA
            +"("+RC_SLNo+" INTEGER,"+RC_DistCode+" INTEGER,"+RC_TalukCode+" INTEGER,"+RC_HobliCode+" INTEGER,"
            + RC_Num_List +" TEXT)";

    private static String CREATE_TABLE_Get_Assistant = "CREATE TABLE "+TABLE_GET_Assistant
            +"("+A_Dist_code+" INTEGER,"+A_Taluk_code+" INTEGER,"+A_Hobli_code+" INTEGER,"+A_VA_circle_code+" INTEGER,"
            + A_Asst_Name+" TEXT,"+A_Asst_MobileNo+" TEXT)";

    public DataBaseHelperClass_btnDownload_E_Kshana(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("Note", "DataBase Opened");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_member_id);
        db.execSQL(CREATE_Table_Member_Details);
        db.execSQL(CREATE_Table_Updated_Member_Details);
        db.execSQL(CREATE_TABLE_RC_Numbers_VA);
        db.execSQL(CREATE_TABLE_Get_Assistant);
        Log.i("Note", TABLE_NAME_member_id +" Table Created");
        Log.i("Note", TABLE_NAME_MemberDetails +" Table Created");
        Log.i("Note", TABLE_NAME_UpdatedMemberDetails +" Table Created");
        Log.i("Note", TABLE_RC_Numbers_VA + "Table Created");
        Log.i("Note", TABLE_GET_Assistant + "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_member_id);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_MemberDetails);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_UpdatedMemberDetails);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_RC_Numbers_VA);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GET_Assistant);
        Log.i("Note","Table Upgraded");
    }
}