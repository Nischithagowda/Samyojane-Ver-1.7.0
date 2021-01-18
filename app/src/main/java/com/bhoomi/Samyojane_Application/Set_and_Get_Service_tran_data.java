package com.bhoomi.Samyojane_Application;

public class Set_and_Get_Service_tran_data {
    private String District_Code, Taluk_Code, Hobli_Code, Village_Code, Habitation_code, Town_Code, Ward_Code, Service_Code, Service_Name, Service_Name_k, RD_No, Applicant_Name, Due_Date, Raised_Location, Father_Name, Mother, RationCard_No, Aadhar_NO, Mobile_No;
    private String Address1, Address2, Address3, Add_Pin, Eng_Certify, GSC_First_Part;
    private String UID, AadhaarPhoto;
    private String DataUpdateFlag, ST_applicant_photo, ST_ID_TYPE, ST_Push_Flag;
    private String CST_res_category, CST_caste_as_per_app, CST_annual_income, SCOT_caste_app, SCOT_annual_income, GST_No_Years_Applied, GST_No_Mths_Applied, IST_annual_income;

    Set_and_Get_Service_tran_data(){}

    public void setDistrict_Code(String district_Code) {
        District_Code = district_Code;
    }
    public String getDistrict_Code() {
        return District_Code;
    }

    public void setTaluk_Code(String taluk_Code) {
        Taluk_Code = taluk_Code;
    }
    public String getTaluk_Code() {
        return Taluk_Code;
    }

    public void setHobli_Code(String hobli_Code) {
        Hobli_Code = hobli_Code;
    }
    public String getHobli_Code() {
        return Hobli_Code;
    }

    public void setVillage_Code(String village_Code) {
        Village_Code = village_Code;
    }
    public String getVillage_Code() {
        return Village_Code;
    }

    public void setHabitation_code(String habitation_code) {
        Habitation_code = habitation_code;
    }
    public String getHabitation_code() {
        return Habitation_code;
    }

    public void setService_Code(String service_Code) {
        Service_Code = service_Code;
    }
    public String getService_Code() {
        return Service_Code;
    }

    void setService_Name(String service_Name) {
        Service_Name = service_Name;
    }
    String getService_Name() {
        return Service_Name;
    }

    public void setRD_No(String RD_No) {
        this.RD_No = RD_No;
    }
    public String getRD_No() {
        return RD_No;
    }

    void setApplicant_Name(String applicant_Name) {
        Applicant_Name = applicant_Name;
    }
    String getApplicant_Name() {
        return Applicant_Name;
    }

    void setDue_Date(String due_Date) {
        Due_Date = due_Date;
    }
    String getDue_Date() {
        return Due_Date;
    }

    void setFather_Name(String father_Name) {
        Father_Name = father_Name;
    }
    String getFather_Name() {
        return Father_Name;
    }

    public void setMother(String mother) {
        Mother = mother;
    }
    public String getMother() {
        return Mother;
    }

    void setRationCard_No(String rationCard_No) {
        RationCard_No = rationCard_No;
    }
    String getRationCard_No() {
        return RationCard_No;
    }

    void setAadhar_NO(String aadhar_NO) {
        Aadhar_NO = aadhar_NO;
    }
    public String getAadhar_NO() {
        return Aadhar_NO;
    }

    void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }
    String getMobile_No() {
        return Mobile_No;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }
    public String getAddress1() {
        return Address1;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }
    public String getAddress2() {
        return Address2;
    }

    public void setAddress3(String address3) {
        Address3 = address3;
    }
    public String getAddress3() {
        return Address3;
    }

    void setAdd_Pin(String add_Pin) {
        Add_Pin = add_Pin;
    }
    String getAdd_Pin() {
        return Add_Pin;
    }

    public void setEng_Certify(String eng_Certify) {
        Eng_Certify = eng_Certify;
    }
    public String getEng_Certify() {
        return Eng_Certify;
    }

    public void setGSC_First_Part(String GSC_First_Part) {
        this.GSC_First_Part = GSC_First_Part;
    }
    public String getGSC_First_Part() {
        return GSC_First_Part;
    }

    public void setCST_res_category(String CST_res_category) {
        this.CST_res_category = CST_res_category;
    }
    public String getCST_res_category() {
        return CST_res_category;
    }

    public void setCST_caste_as_per_app(String CST_caste_as_per_app) {
        this.CST_caste_as_per_app = CST_caste_as_per_app;
    }
    public String getCST_caste_as_per_app() {
        return CST_caste_as_per_app;
    }

    public void setCST_annual_income(String CST_annual_income) {
        this.CST_annual_income = CST_annual_income;
    }
    public String getCST_annual_income() {
        return CST_annual_income;
    }

    void setSCOT_caste_app(String SCOT_caste_app) {
        this.SCOT_caste_app = SCOT_caste_app;
    }
    String getSCOT_caste_app() {
        return SCOT_caste_app;
    }

    void setSCOT_annual_income(String SCOT_annual_income) {
        this.SCOT_annual_income = SCOT_annual_income;
    }
    String getSCOT_annual_income() {
        return SCOT_annual_income;
    }

    void setGST_No_Years_Applied(String GST_No_Years_Applied) {
        this.GST_No_Years_Applied = GST_No_Years_Applied;
    }
    String getGST_No_Years_Applied() {
        return GST_No_Years_Applied;
    }

    void setGST_No_Mths_Applied(String GST_No_Mths_Applied) {
        this.GST_No_Mths_Applied = GST_No_Mths_Applied;
    }
    String getGST_No_Mths_Applied() {
        return GST_No_Mths_Applied;
    }

    public String getST_applicant_photo() {
        return ST_applicant_photo;
    }

    public void setST_applicant_photo(String ST_applicant_photo) {
        this.ST_applicant_photo = ST_applicant_photo;
    }

    public String getTown_Code() {
        return Town_Code;
    }

    public void setTown_Code(String town_Code) {
        Town_Code = town_Code;
    }

    public String getWard_Code() {
        return Ward_Code;
    }

    public void setWard_Code(String ward_Code) {
        Ward_Code = ward_Code;
    }

    String getST_ID_TYPE() {
        return ST_ID_TYPE;
    }

    void setST_ID_TYPE(String ST_ID_TYPE) {
        this.ST_ID_TYPE = ST_ID_TYPE;
    }

    String getRaised_Location() {
        return Raised_Location;
    }

    void setRaised_Location(String raised_Location) {
        Raised_Location = raised_Location;
    }

    String getService_Name_k() {
        return Service_Name_k;
    }

    void setService_Name_k(String service_Name_k) {
        Service_Name_k = service_Name_k;
    }

    public String getST_Push_Flag() {
        return ST_Push_Flag;
    }

    public void setST_Push_Flag(String ST_Push_Flag) {
        this.ST_Push_Flag = ST_Push_Flag;
    }

    public String getIST_annual_income() {
        return IST_annual_income;
    }

    public void setIST_annual_income(String IST_annual_income) {
        this.IST_annual_income = IST_annual_income;
    }
}
