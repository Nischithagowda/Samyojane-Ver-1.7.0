package com.bhoomi.Samyojane_Application;

public class Set_and_Get_Service_Parameter {

    private String Village_Code, Service_Code, Service_Name, Service_Name_k,
            RD_No, Applicant_Name, Father_Name, Mother_Name, Due_Date, Raised_Location, Photo, vLat, vLong, RationCard_No, Mobile_No, Address1, Address2, Address3;
    private int District_Code, Taluk_Code, Hobli_Code, va_Circle_Code, Town_Code, Ward_Code;
    private String rbIssue_Cert, spRejectionReason, Annual_Income, PinCode, Report_No, Eng_Certify, ST_applicant_photo;
    private String APP_Category_6, App_Caste_6, rbOption_6, spReason_6; // Service_6 Parameters
    private String No_Years_8, App_Father_Category_8, APP_Father_Caste_8, App_Mother_Category_8, APP_Mother_Caste_8, App_Category_as_VA_8, Applicant_Caste_as_VA_8, remarks_8; // Service_8 Parameters
    private String Total_No_Year_10, NO_Months_10, rbStated_Address_10, rbAddress_RationCard_10, spPurpose_10; // Service_10 Parameters
    private String DataUpdateFlag;

    public Set_and_Get_Service_Parameter(){}

    public int getDistrict_Code() {
        return District_Code;
    }

    public void setDistrict_Code(int district_Code) {
        District_Code = district_Code;
    }

    public int getTaluk_Code() {
        return Taluk_Code;
    }

    public void setTaluk_Code(int taluk_Code) {
        Taluk_Code = taluk_Code;
    }

    public int getHobli_Code() {
        return Hobli_Code;
    }

    public void setHobli_Code(int hobli_Code) {
        Hobli_Code = hobli_Code;
    }

    public int getVa_Circle_Code() {
        return va_Circle_Code;
    }

    public void setVa_Circle_Code(int va_Circle_Code) {
        this.va_Circle_Code = va_Circle_Code;
    }

    public void setVillage_Code(String village_Code) {
        Village_Code = village_Code;
    }
    public String getVillage_Code() {
        return Village_Code;
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

    void setRationCard_No(String rationCard_No) {
        RationCard_No = rationCard_No;
    }
    String getRationCard_No() {
        return RationCard_No;
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

    void setPinCode(String pinCode) {
        PinCode = pinCode;
    }
    String getPinCode() {
        return PinCode;
    }

    public void setEng_Certify(String eng_Certify) {
        Eng_Certify = eng_Certify;
    }
    public String getEng_Certify() {
        return Eng_Certify;
    }

    void setReport_No(String report_No) {
        Report_No = report_No;
    }
    String getReport_No() {
        return Report_No;
    }

    void setApplicant_Name(String applicant_Name) {
        Applicant_Name = applicant_Name;
    }
    String getApplicant_Name() {
        return Applicant_Name;
    }

    void setFather_Name(String father_Name) {
        Father_Name = father_Name;
    }
    String getFather_Name() {
        return Father_Name;
    }

    void setMother_Name(String mother_Name) {
        Mother_Name = mother_Name;
    }
    String getMother_Name() {
        return Mother_Name;
    }

    void setDue_Date(String due_Date) {
        Due_Date = due_Date;
    }
    String getDue_Date() {
        return Due_Date;
    }

    void setAPP_Category_6(String APP_Category_6) {
        this.APP_Category_6 = APP_Category_6;
    }
    String getAPP_Category_6() {
        return APP_Category_6;
    }

    void setApp_Caste_6(String app_Caste_6) {
        App_Caste_6 = app_Caste_6;
    }
    String getApp_Caste_6() {
        return App_Caste_6;
    }

    void setRbOption_6(String rbOption_6) {
        this.rbOption_6 = rbOption_6;
    }
    String getRbOption_6() {
        return rbOption_6;
    }

    void setSpReason_6(String spReason_6) {
        this.spReason_6 = spReason_6;
    }
    String getSpReason_6() {
        return spReason_6;
    }

    void setAnnual_Income(String annual_Income) {
        Annual_Income = annual_Income;
    }
    String getAnnual_Income() {
        return Annual_Income;
    }

    void setNo_Years_8(String no_Years_8) {
        No_Years_8 = no_Years_8;
    }
    String getNo_Years_8() {
        return No_Years_8;
    }

    void setApp_Father_Category_8(String app_Father_Category_8) {
        App_Father_Category_8 = app_Father_Category_8;
    }
    String getApp_Father_Category_8() {
        return App_Father_Category_8;
    }

    public void setAPP_Father_Caste_8(String APP_Father_Caste_8) {
        this.APP_Father_Caste_8 = APP_Father_Caste_8;
    }
    public String getAPP_Father_Caste_8() {
        return APP_Father_Caste_8;
    }

    void setApp_Mother_Category_8(String app_Mother_Category_8) {
        App_Mother_Category_8 = app_Mother_Category_8;
    }
    String getApp_Mother_Category_8() {
        return App_Mother_Category_8;
    }

    public void setAPP_Mother_Caste_8(String APP_Mother_Caste_8) {
        this.APP_Mother_Caste_8 = APP_Mother_Caste_8;
    }
    public String getAPP_Mother_Caste_8() {
        return APP_Mother_Caste_8;
    }

    void setApp_Category_as_VA_8(String app_Category_as_VA_8) {
        App_Category_as_VA_8 = app_Category_as_VA_8;
    }
    String getApp_Category_as_VA_8() {
        return App_Category_as_VA_8;
    }

    void setApplicant_Caste_as_VA_8(String applicant_Caste_as_VA_8) {
        Applicant_Caste_as_VA_8 = applicant_Caste_as_VA_8;
    }
    String getApplicant_Caste_as_VA_8() {
        return Applicant_Caste_as_VA_8;
    }

    public void setRemarks_8(String remarks_8) {
        this.remarks_8 = remarks_8;
    }
    public String getRemarks_8() {
        return remarks_8;
    }

    void setTotal_No_Year_10(String total_No_Year_10) {
        Total_No_Year_10 = total_No_Year_10;
    }
    String getTotal_No_Year_10() {
        return Total_No_Year_10;
    }

    public void setNO_Months_10(String NO_Months_10) {
        this.NO_Months_10 = NO_Months_10;
    }
    public String getNO_Months_10() {
        return NO_Months_10;
    }

    void setRbStated_Address_10(String rbStated_Address_10) {
        this.rbStated_Address_10 = rbStated_Address_10;
    }
    String getRbStated_Address_10() {
        return rbStated_Address_10;
    }

    void setRbAddress_RationCard_10(String rbAddress_RationCard_10) {
        this.rbAddress_RationCard_10 = rbAddress_RationCard_10;
    }
    String getRbAddress_RationCard_10() {
        return rbAddress_RationCard_10;
    }

    void setSpPurpose_10(String spPurpose_10) {
        this.spPurpose_10 = spPurpose_10;
    }
    String getSpPurpose_10() {
        return spPurpose_10;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
    public String getPhoto() {
        return Photo;
    }

    public void setvLat(String vLat) {
        this.vLat = vLat;
    }
    public String getvLat() {
        return vLat;
    }

    public void setvLong(String vLong) {
        this.vLong = vLong;
    }
    public String getvLong() {
        return vLong;
    }

    public void setRbIssue_Cert(String rbIssue_Cert) {
        this.rbIssue_Cert = rbIssue_Cert;
    }
    public String getRbIssue_Cert() {
        return rbIssue_Cert;
    }

    public void setSpRejectionReason(String spRejectionReason) {
        this.spRejectionReason = spRejectionReason;
    }
    public String getSpRejectionReason() {
        return spRejectionReason;
    }

    void setDataUpdateFlag(String dataUpdateFlag) {
        DataUpdateFlag = dataUpdateFlag;
    }
    String getDataUpdateFlag() {
        return DataUpdateFlag;
    }

    public String getST_applicant_photo() {
        return ST_applicant_photo;
    }

    public void setST_applicant_photo(String ST_applicant_photo) {
        this.ST_applicant_photo = ST_applicant_photo;
    }

    public int getTown_Code() {
        return Town_Code;
    }

    public void setTown_Code(int town_Code) {
        Town_Code = town_Code;
    }

    public int getWard_Code() {
        return Ward_Code;
    }

    public void setWard_Code(int ward_Code) {
        Ward_Code = ward_Code;
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
}
