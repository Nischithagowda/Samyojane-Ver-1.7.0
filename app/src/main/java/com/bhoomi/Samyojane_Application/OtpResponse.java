
package com.bhoomi.Samyojane_Application;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponse {

    @SerializedName("FN_SEND_OTPResult")
    @Expose
    private String fNGETSENDOTPResult;

    @SerializedName("FN_VALIDATE_OTPResult")
    @Expose
    private String FN_VALIDATE_OTPResult;

    @SerializedName("FN_GET_SEND_OTPResult")
    @Expose
    private String FN_GET_SEND_OTPResult;

    public String getFN_GET_SEND_OTPResult() {
        return FN_GET_SEND_OTPResult;
    }

    public void setFN_GET_SEND_OTPResult(String FN_GET_SEND_OTPResult) {
        this.FN_GET_SEND_OTPResult = FN_GET_SEND_OTPResult;
    }

    public String getFN_VALIDATE_OTPResult() {
        return FN_VALIDATE_OTPResult;
    }

    String getFNGETSENDOTPResult() {
        return fNGETSENDOTPResult;
    }

    public void setFNGETSENDOTPResult(String fNGETSENDOTPResult) {
        this.fNGETSENDOTPResult = fNGETSENDOTPResult;
    }

}
