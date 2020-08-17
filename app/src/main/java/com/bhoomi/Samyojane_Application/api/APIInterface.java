package com.bhoomi.Samyojane_Application.api;

import com.bhoomi.Samyojane_Application.OtpResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("http://164.100.133.47/NKMobileAPI/api/OPRC/GetRCMembersBasedOnDTHRC/{Flag1}/{Flag2}/{DCode}/{TCode}/{HCode}/{RationCardNo}")
    Call<String> doGetListResources(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("DCode") int DCode,
            @Path("TCode") int TCode,
            @Path("HCode") int HCode,
            @Path("RationCardNo") String RationCardNo);

    @GET("http://164.100.133.47/NKMobileAPI/api/OPRC/GetRCMemberDetails/{Flag1}/{Flag2}/{RationCardNo}/{MemberID}")
    Call<String> doGetListResources_Mem_details(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("RationCardNo") String RationCardNo,
            @Path("MemberID") String MemberID);

    @POST("http://164.100.133.47/NKMobileAPI/api/OPRC/POSTAddRCMemberDetails/{Flag1}/{Flag2}/jobject")
    Call<String> doGetUploadResponse(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Body JsonObject jobject);

    @POST("http://164.100.133.47/NKMobileAPI/api/OPRC/POSTUpdateRCMemberInactive/{Flag1}/{Flag2}/{RationCardNo}/{MemberID}/{userId}/{Reason}")
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

    @POST("Service29/APTHA/FN_GET_SEND_OTP/{pstrUserName}/{pStrPassword}/{pMobileNumber}/{pSMSText}")
    Call<OtpResponse> FnSendOtpApthamitra(
            @Path("pstrUserName") String pstrUserName,
            @Path("pStrPassword") String pStrPassword,
            @Path("pMobileNumber") String pMobileNumber,
            @Path("pSMSText") String PSMSTEXT);

    @POST("FN_VALIDATE_OTP/{PSTRUSERNAME}/{PSTRPASSWORD}/{PMOBILENUMBER}/{POTP}")
    Call<OtpResponse> FN_VALIDATE_OTP(
            @Path("PSTRUSERNAME") String pstrUserName,
            @Path("PSTRPASSWORD") String pStrPassword,
            @Path("PMOBILENUMBER") String pMobileNumber,
            @Path("POTP") String pOTP);

    @POST("http://164.100.133.47/Mob_Rest_API/api/Values")
    Call<String> doValidateAssistant(
            @Query("Username") String Username,
            @Query("Pass") String Pass
    );

    @POST("http://164.100.133.47/Mob_Rest_API/api/Values")
    Call<String> doAddMember(
            @Query("A_Dist_code") int A_Dist_code,
            @Query("A_Taluk_code") int A_Taluk_code,
            @Query("A_Hobli_code") int A_Hobli_code,
            @Query("A_VA_circle_code") int A_VA_circle_code,
            @Query("A_Creator_Name") String A_Creator_Name,
            @Query("A_Asst_Name") String A_Asst_Name,
            @Query("A_Asst_MobileNo") String A_Asst_MobileNo,
            @Query("A_Password") String A_Password,
            @Query("A_Created_By") String A_Created_By
    );

    @POST("http://164.100.133.47/Mob_Rest_API/api/Values")
    Call<String> doGetAssistant(
            @Query("DCode") int DCode,
            @Query("TCode") int TCode,
            @Query("HCode") int HCode,
            @Query("VCCode") int VCCode
    );

    @GET("http://164.100.133.47/NKMobileAPI/api/OPRC/GetRCNumbersBasedOnDTH/{Flag1}/{Flag2}/{DCode}/{TCode}/{HCode}")
    Call<String> doGetListRCNumbers_VA(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("DCode") int DCode,
            @Path("TCode") int TCode,
            @Path("HCode") int HCode
    );

    @POST("http://164.100.133.47/NKMobileAPI/api/OPRC/POSTUpdateRCMemberDetailsFromVA/{Flag1}/{Flag2}/jobject")
    Call<String> doGetUploadResponseFromVA(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Body JsonObject jobject);

    @POST("http://164.100.133.47/Samyojane_API/api/values/Fn_Validate/{flag1}/{flag2}/{MobNum}/{IMEI}")
    Call<String> doFn_Validate(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("MobNum") String MobNum,
            @Path("IMEI") String IMEI);
}
