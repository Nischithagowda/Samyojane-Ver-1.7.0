package com.bhoomi.Samyojane_Application.api;

import com.bhoomi.Samyojane_Application.GetDocRequestClass;
import com.bhoomi.Samyojane_Application.UpdateStatusCLASS;
import com.bhoomi.Samyojane_Application.UpdateVillageTownWardCLASS;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Nischitha on 08,November,2021
 **/
public interface APIInterface_NIC {
    @GET("GetListForFieldVerification/{Flag1}/{Flag2}/{District}/{Taluk}/{Hobli}/{loginid}/{Desicode}/{VACircleCode}")
    Call<JsonObject> GetListForFieldVerification(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("District") int District,
            @Path("Taluk") int Taluk,
            @Path("Hobli") int Hobli,
            @Path("loginid") String loginid,
            @Path("Desicode") int Desicode,
            @Path("VACircleCode") int VACircleCode
            );

    @Headers("Content-Type: application/json")
    @POST("GetDocs/")
    Call<JsonObject> GetDocs(
            @Body GetDocRequestClass getDocRequestClass
            );

    @POST("UpdateVillageTownWard")
    Call<JsonObject> UpdateVillageTownWard(
            @Body UpdateVillageTownWardCLASS updateVillageTownWardCLASS
            );

    @POST("UpdateStatus")
    Call<JsonObject> UpdateStatus(
            @Body UpdateStatusCLASS updateStatusCLASS
            );
}
