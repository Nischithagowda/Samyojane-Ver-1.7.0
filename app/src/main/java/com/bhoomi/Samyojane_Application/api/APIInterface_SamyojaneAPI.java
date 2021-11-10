package com.bhoomi.Samyojane_Application.api;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Nischitha on 08,November,2021
 **/
public interface APIInterface_SamyojaneAPI {
    @POST("Get_Version/{flag1}/{flag2}")
    Call<String> doGet_Version(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2
    );
    @POST("Fn_Validate/{flag1}/{flag2}/{MobNum}/{IMEI}")
    Call<String> doFn_Validate(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("MobNum") String MobNum,
            @Path("IMEI") String IMEI
    );

    @POST("Fn_Get_Facility_Services/{flag1}/{flag2}")
    Call<String> doFn_Get_Facility_Services(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2
    );

    @POST("Fn_Get_Caste/{flag1}/{flag2}")
    Call<String> doFn_Get_Caste(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2
    );

    @POST("Fn_Get_OBC_Caste/{flag1}/{flag2}")
    Call<String> doFn_Get_OBC_Caste(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2
    );

    @POST("Fn_Get_Category/{flag1}/{flag2}")
    Call<String> doFn_Get_Category(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2
    );

    @POST("Fn_Get_Village_Name/{flag1}/{flag2}/{District_Code}/{Taluk_Code}/{Hobli_Code}/{VA_Cicle_Code}")
    Call<String> doFn_Get_Village_Name(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("District_Code") int District_Code,
            @Path("Taluk_Code") int Taluk_Code,
            @Path("Hobli_Code") int Hobli_Code,
            @Path("VA_Cicle_Code") int VA_Cicle_Code
    );

    @POST("Fn_Get_Village_Name_For_RI/{flag1}/{flag2}/{District_Code}/{Taluk_Code}/{Hobli_Code}/{IMEI}")
    Call<String> doFn_Get_Village_Name_For_RI(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("District_Code") int District_Code,
            @Path("Taluk_Code") int Taluk_Code,
            @Path("Hobli_Code") int Hobli_Code,
            @Path("IMEI") String IMEI
    );

    @POST("Get_Village_name_RuralUrban/{flag1}/{flag2}/{District_Code}/{Taluk_Code}/{Hobli_Code}/{VA_Cicle_Code}")
    Call<String> doGet_Village_name_RuralUrban(
            @Path("flag1") String flag1,
            @Path("flag2") String flag2,
            @Path("District_Code") int District_Code,
            @Path("Taluk_Code") int Taluk_Code,
            @Path("Hobli_Code") int Hobli_Code
    );
}
