package com.charm.business.retrofit;

import com.charm.business.responseModel.EmployeeLoginResponse;
import com.charm.business.responseModel.EmployeeLogoutResponse;
import com.charm.business.responseModel.ResetEmployeePasswordResponse;
import com.charm.business.responseModel.EmployeeStatusResponse;
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
    Call<EmployeeStatusResponse> employeeStatus(@Header("Authorization") String loginToken);

    @POST("verificationcode")
    Call<VerificationCodeResponse> verificationCode(@Body JsonObject jsonObject);

    @POST("registerowner")
    Call<RegisterOwnerResponse> registerOwner(@Body JsonObject jsonObject);

    @POST("resetemployeepassword")
    Call<ResetEmployeePasswordResponse> resetEmployeePassword(@Body JsonObject jsonObject);

    @POST("employeelogin")
    Call<EmployeeLoginResponse> employeeLogin(@Body JsonObject jsonObject);

    @POST("employeelogout")
    Call<EmployeeLogoutResponse> employeeLogout(@Body JsonObject jsonObject);

}