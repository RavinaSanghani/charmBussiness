package com.app.charmbusiness.retrofit;

import com.app.charmbusiness.responseModel.EmployeeLogoutResponse;
import com.app.charmbusiness.responseModel.RegisterEmployeeResponse;
import com.app.charmbusiness.responseModel.ResetEmployeePasswordResponse;
import com.app.charmbusiness.responseModel.EmployeeStatusResponse;
import com.app.charmbusiness.responseModel.RegisterOwnerResponse;
import com.app.charmbusiness.responseModel.VerificationCodeResponse;
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
    Call<RegisterEmployeeResponse> employeeLogin(@Body JsonObject jsonObject);

    @POST("employeelogout")
    Call<EmployeeLogoutResponse> employeeLogout(@Body JsonObject jsonObject);

}