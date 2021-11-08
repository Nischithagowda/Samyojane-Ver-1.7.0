package com.bhoomi.Samyojane_Application.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Nischitha on 08,November,2021
 **/
public interface APIInterface_NIC {
    @GET("GetListForFieldVerification/{Flag1}/{Flag2}/{District}/{Taluk}/{Hobli}/{loginid}/{Desicode}/{VACircleCode}")
    Call<String> GetListForFieldVerification(
            @Path("Flag1") String Flag1,
            @Path("Flag2") String Flag2,
            @Path("District") int District,
            @Path("Taluk") int Taluk,
            @Path("Hobli") int Hobli,
            @Path("loginid") String loginid,
            @Path("Desicode") int Desicode,
            @Path("VACircleCode") int VACircleCode
            );
}
