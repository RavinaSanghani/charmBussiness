package com.charm.business.retrofit;

import com.charm.business.responseModel.OwnerLoginResponse;
import com.charm.business.responseModel.OwnerLogoutResponse;
import com.charm.business.responseModel.ResetOwnerPasswordResponse;
import com.charm.business.responseModel.OwnerStatusResponse;
import com.charm.business.responseModel.RegisterOwnerResponse;
import com.charm.business.responseModel.VerificationCodeResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("employeestatus")
    Call<OwnerStatusResponse> employeeStatus(@Header("Authorization") String loginToken);

    @POST("verificationcode")
    Call<VerificationCodeResponse> verificationCode(@Body JsonObject jsonObject);

    @POST("registerowner")
    Call<RegisterOwnerResponse> registerOwner(@Body JsonObject jsonObject);

    @POST("resetemployeepassword")
    Call<ResetOwnerPasswordResponse> resetEmployeePassword(@Body JsonObject jsonObject);

    @POST("employeelogin")
    Call<OwnerLoginResponse> employeeLogin(@Body JsonObject jsonObject);

    @POST("employeelogout")
    Call<OwnerLogoutResponse> employeeLogout(@Body JsonObject jsonObject);

}