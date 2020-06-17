package com.bhoomi.Samyojane_Application;

public class Set_and_Get_Facility_Services {

    private String SlNo, FM_facility_code, FM_facility_edesc, FM_facility_kdesc, FM_acronym_on_doc_eng, FM_designated_officer, FM_gsc_no_days, FM_facility_display, FM_sakala_service, FM_OTC_Charges;

    Set_and_Get_Facility_Services(){}

    public void setSlNo(String slNo) {
        SlNo = slNo;
    }
    public String getSlNo() {
        return SlNo;
    }

    public void setFM_facility_code(String FM_facility_code) {
        this.FM_facility_code = FM_facility_code;
    }
    public String getFM_facility_code() {
        return FM_facility_code;
    }

    void setFM_facility_edesc(String FM_facility_edesc) {
        this.FM_facility_edesc = FM_facility_edesc;
    }
    String getFM_facility_edesc() {
        return FM_facility_edesc;
    }

    void setFM_facility_kdesc(String FM_facility_kdesc) {
        this.FM_facility_kdesc = FM_facility_kdesc;
    }
    String getFM_facility_kdesc() {
        return FM_facility_kdesc;
    }

    void setFM_acronym_on_doc_eng(String FM_acronym_on_doc_eng) {
        this.FM_acronym_on_doc_eng = FM_acronym_on_doc_eng;
    }
    String getFM_acronym_on_doc_eng() {
        return FM_acronym_on_doc_eng;
    }

    void setFM_designated_officer(String FM_designated_officer) {
        this.FM_designated_officer = FM_designated_officer;
    }
    String getFM_designated_officer() {
        return FM_designated_officer;
    }

    void setFM_gsc_no_days(String FM_gsc_no_days) {
        this.FM_gsc_no_days = FM_gsc_no_days;
    }
    String getFM_gsc_no_days() {
        return FM_gsc_no_days;
    }

    void setFM_facility_display(String FM_facility_display) {
        this.FM_facility_display = FM_facility_display;
    }
    String getFM_facility_display() {
        return FM_facility_display;
    }

    void setFM_sakala_service(String FM_sakala_service) {
        this.FM_sakala_service = FM_sakala_service;
    }
    String getFM_sakala_service() {
        return FM_sakala_service;
    }

    void setFM_OTC_Charges(String FM_OTC_Charges) {
        this.FM_OTC_Charges = FM_OTC_Charges;
    }
    String getFM_OTC_Charges() {
        return FM_OTC_Charges;
    }
}
