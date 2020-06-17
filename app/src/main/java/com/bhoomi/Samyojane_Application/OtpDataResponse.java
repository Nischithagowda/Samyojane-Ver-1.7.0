
package com.bhoomi.Samyojane_Application;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpDataResponse {

    @SerializedName("OTP")
    @Expose
    private String oTP;

    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("SMS_RESP")
    @Expose
    private String sMSRESP;

    @SerializedName("STATUS")
    @Expose
    private boolean sTATUS;

    public boolean isVALID() {
        return VALID;
    }

    public void setVALID(boolean VALID) {
        this.VALID = VALID;
    }

    @SerializedName("VALID")
    @Expose
    private boolean VALID;

    @SerializedName("MSG")
    @Expose
    private String mSG;
    @SerializedName("CODE")
    @Expose
    private String cODE;

    public String getFN_GET_SEND_OTPResult() {
        return FN_GET_SEND_OTPResult;
    }

    public void setFN_GET_SEND_OTPResult(String FN_GET_SEND_OTPResult) {
        this.FN_GET_SEND_OTPResult = FN_GET_SEND_OTPResult;
    }

    @SerializedName("FN_GET_SEND_OTPResult")
    @Expose
    private String FN_GET_SEND_OTPResult;

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSMSRESP() {
        return sMSRESP;
    }

    public void setSMSRESP(String sMSRESP) {
        this.sMSRESP = sMSRESP;
    }

    public boolean issTATUS() {
        return sTATUS;
    }

    public void setsTATUS(boolean sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getMSG() {
        return mSG;
    }

    public void setMSG(String mSG) {
        this.mSG = mSG;
    }

    public String getCODE() {
        return cODE;
    }

    public void setCODE(String cODE) {
        this.cODE = cODE;
    }

}
