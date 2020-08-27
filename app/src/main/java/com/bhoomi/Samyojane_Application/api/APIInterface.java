package com.bhoomi.Samyojane_Application.api;

import com.bhoomi.Samyojane_Application.OtpResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("GetRCMembersBasedOnDTHRC/{Flag1}/{Flag2}/{DCode}/{TCode}/{HCode}/{RationCardNo}")
    Call<String> doGetListResources(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("DCode") int DCode,
            @Path("TCode") int TCode,
            @Path("HCode") int HCode,
            @Path("RationCardNo") String RationCardNo);

    @GET("GetRCMemberDetails/{Flag1}/{Flag2}/{RationCardNo}/{MemberID}")
    Call<String> doGetListResources_Mem_details(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("RationCardNo") String RationCardNo,
            @Path("MemberID") String MemberID);

    @POST("POSTAddRCMemberDetails/{Flag1}/{Flag2}/jobject")
    Call<String> doGetUploadResponse(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Body JsonObject jobject);

    @POST("POSTUpdateRCMemberInactive/{Flag1}/{Flag2}/{RationCardNo}/{MemberID}/{userId}/{Reason}")
    Call<String> doUploadNotExistMember(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("RationCardNo") String RationCardNo,
            @Path("MemberID") String MemberID,
            @Path("userId") String userId,
            @Path("Reason") String Reason);
//http://192.168.0.139/RWS_MBEKSHNA/EKSHNA/
    @POST("FN_SEND_OTP/{PSTRUSERNAME}/{PSTRPASSWORD}/{PMOBILENUMBER}/{PSMSTEXT}")
    Call<OtpResponse> FnSendOtp(
            @Path("PSTRUSERNAME") String pstrUserName,
            @Path("PSTRPASSWORD") String pStrPassword,
            @Path("PMOBILENUMBER") String pMobileNumber,
            @Path("PSMSTEXT") String PSMSTEXT);

    @POST("FN_VALIDATE_OTP/{PSTRUSERNAME}/{PSTRPASSWORD}/{PMOBILENUMBER}/{POTP}")
    Call<OtpResponse> FN_VALIDATE_OTP(
            @Path("PSTRUSERNAME") String pstrUserName,
            @Path("PSTRPASSWORD") String pStrPassword,
            @Path("PMOBILENUMBER") String pMobileNumber,
            @Path("POTP") String pOTP);

    @POST("ValidateAssistant/{flag1}/{flag2}/{Username}/{Pass}")
    Call<String> doValidateAssistant(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("Username") String Username,
            @Path("Pass") String Pass);

    @POST("AddAssistant/{flag1}/{flag2}/{A_Dist_code}/{A_Taluk_code}/{A_Hobli_code}/{A_VA_circle_code}/{A_Creator_Name}/{A_Asst_Name}/{A_Asst_MobileNo}/{A_Password}/{A_Created_By}")
    Call<String> doAddMember(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("A_Dist_code") int A_Dist_code,
            @Path("A_Taluk_code") int A_Taluk_code,
            @Path("A_Hobli_code") int A_Hobli_code,
            @Path("A_VA_circle_code") int A_VA_circle_code,
            @Path("A_Creator_Name") String A_Creator_Name,
            @Path("A_Asst_Name") String A_Asst_Name,
            @Path("A_Asst_MobileNo") String A_Asst_MobileNo,
            @Path("A_Password") String A_Password,
            @Path("A_Created_By") String A_Created_By
    );

    @POST("getAssistant/{flag1}/{flag2}/{DCode}/{TCode}/{HCode}/{VCCode}")
    Call<String> doGetAssistant(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("DCode") int DCode,
            @Path("TCode") int TCode,
            @Path("HCode") int HCode,
            @Path("VCCode") int VCCode
    );

    @GET("GetRCNumbersBasedOnDTH/{Flag1}/{Flag2}/{DCode}/{TCode}/{HCode}")
    Call<String> doGetListRCNumbers_VA(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("DCode") int DCode,
            @Path("TCode") int TCode,
            @Path("HCode") int HCode
    );

    @POST("POSTUpdateRCMemberDetailsFromVA/{Flag1}/{Flag2}/jobject")
    Call<String> doGetUploadResponseFromVA(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Body JsonObject jobject);

    @POST("Fn_Validate/{flag1}/{flag2}/{MobNum}/{IMEI}")
    Call<String> doFn_Validate(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("MobNum") String MobNum,
            @Path("IMEI") String IMEI);
}
