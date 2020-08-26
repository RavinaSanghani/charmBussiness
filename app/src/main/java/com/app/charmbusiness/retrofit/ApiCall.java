package com.app.charmbusiness.retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.app.charmbusiness.DialogForgotPassword;
import com.app.charmbusiness.DialogVerificationCode;
import com.app.charmbusiness.HomeActivity;
import com.app.charmbusiness.LoginActivity;
import com.app.charmbusiness.MainActivity;
import com.app.charmbusiness.OwnerRegisterActivity;
import com.app.charmbusiness.PrefManager;
import com.app.charmbusiness.RegistrationStepsActivity;
import com.app.charmbusiness.SplashActivity;
import com.app.charmbusiness.Utility;
import com.app.charmbusiness.responseModel.EmployeeLoginResponse;
import com.app.charmbusiness.responseModel.EmployeeLogoutResponse;
import com.app.charmbusiness.responseModel.RegisterEmployeeResponse;
import com.app.charmbusiness.responseModel.ResetEmployeePasswordResponse;
import com.app.charmbusiness.responseModel.VerificationCodeResponse;
import com.app.charmbusiness.responseModel.EmployeeStatusResponse;
import com.app.charmbusiness.responseModel.RegisterOwnerResponse;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.RequiresApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ApiCall {

    private static final String TAG = "ApiCall";
    private static ApiInterface apiInterface;

    private static PrefManager prefManager;

    public static void employeeStatus(final Activity activity, String loginToken) {
        prefManager = new PrefManager(activity);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<EmployeeStatusResponse> employeeStatusCall = apiInterface.employeeStatus(loginToken);
        employeeStatusCall.enqueue(new Callback<EmployeeStatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<EmployeeStatusResponse> call, @NotNull Response<EmployeeStatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Utility.printLog(TAG, "employeeStatus:onResponse:" + response.body());
                        if (response.body().getCode().equals("100")) {
                            if (response.body().getIsOwner()) {
                                if (response.body().getRegCompleted()) {
                                    Utility.startActivity(activity, MainActivity.class, false);
                                } else {
                                    prefManager.putInteger(PrefManager.KEY_REGISTRATION_STEP, (int) (response.body().getRegStep() + 1));
                                    Utility.startActivity(activity, RegistrationStepsActivity.class, false);
                                }
                            } else {
                                Utility.startActivity(activity, MainActivity.class, false);
                            }

                        } else {
                            Utility.startActivity(activity, HomeActivity.class, false);
                        }
                    }
                } else {
                    Utility.showMessageDialog(activity, response.message());
                }
                ((SplashActivity) activity).hideProgressBar();
            }

            @Override
            public void onFailure(@NotNull Call<EmployeeStatusResponse> call, @NotNull Throwable t) {
                ((SplashActivity) activity).hideProgressBar();
                Utility.printLog(TAG, "employeeStatus:onFailure:Error:" + t.getMessage());
            }
        });
    }

    public static void verificationCode(final Activity activity, JsonObject jsonObject) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VerificationCodeResponse> verificationCodeCall = apiInterface.verificationCode(jsonObject);
        verificationCodeCall.enqueue(new Callback<VerificationCodeResponse>() {
            @Override
            public void onResponse(@NotNull Call<VerificationCodeResponse> call, @NotNull Response<VerificationCodeResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Utility.printLog(TAG, "verificationCode:onResponse:" + response.body());
                        if (response.body().getCode().equals("100")) {
                            DialogVerificationCode code = new DialogVerificationCode(activity, response.body().getVerificationCode());
                            code.show();
                            code.setCanceledOnTouchOutside(false);
                        } else {
                            Utility.showMessageDialog(activity, response.body().getMessage());
                        }
                    }
                } else {
                    Utility.showMessageDialog(activity, response.message());
                }
                ((OwnerRegisterActivity) activity).hideProgressBar();
            }

            @Override
            public void onFailure(@NotNull Call<VerificationCodeResponse> call, @NotNull Throwable t) {
                ((OwnerRegisterActivity) activity).hideProgressBar();
                Utility.printLog(TAG, "verificationCode:onFailure:Error:" + t.getMessage());
            }
        });
    }

    public static void registerOwner(final Activity activity, JsonObject jsonObject) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegisterOwnerResponse> registerOwnerCall = apiInterface.registerOwner(jsonObject);
        registerOwnerCall.enqueue(new Callback<RegisterOwnerResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegisterOwnerResponse> call, @NotNull Response<RegisterOwnerResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Utility.printLog(TAG, "registerOwner:onResponse:" + response.body());
                        if (response.body().getCode().equals("100")) {
                            prefManager.putInteger(PrefManager.KEY_EMPLOYEE_ID, response.body().getEmployeeID().intValue());
                            prefManager.setString(PrefManager.KEY_LOGIN_TOKEN, response.body().getLoginToken());
                            prefManager.putInteger(PrefManager.KEY_REGISTRATION_STEP, 2);
                            Utility.startActivity(activity, RegistrationStepsActivity.class, false);
                        } else {
                            Utility.showMessageDialog(activity, String.valueOf(response.body().getMessage()));
                        }
                    }
                } else {
                    Utility.showMessageDialog(activity, response.message());
                }

                ((OwnerRegisterActivity) activity).hideProgressBar();
            }

            @Override
            public void onFailure(@NotNull Call<RegisterOwnerResponse> call, @NotNull Throwable t) {
                ((OwnerRegisterActivity) activity).hideProgressBar();
                Utility.printLog(TAG, "registerOwner:onFailure:Error:" + t.getMessage());
            }
        });
    }

    public static void resetEmployeePassword(final Activity activity, JsonObject jsonObject) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResetEmployeePasswordResponse> verificationCodeCall = apiInterface.resetEmployeePassword(jsonObject);
        verificationCodeCall.enqueue(new Callback<ResetEmployeePasswordResponse>() {
            @Override
            public void onResponse(@NotNull Call<ResetEmployeePasswordResponse> call, @NotNull Response<ResetEmployeePasswordResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Utility.printLog(TAG, "resetEmployeePassword:onResponse:ResponseCode:" + response.body().getCode());
                        if (response.body().getCode().equals("100")) {
                            ((LoginActivity)activity).forgotPasswordDialogDismiss();
                        }else {
                            Utility.showMessageDialog(activity, response.body().getMessage());
                            ((LoginActivity)activity).forgotPasswordDialogDismiss();
                        }
                    }
                } else {
                    Utility.showMessageDialog(activity, response.message());
                }
                ((LoginActivity)activity).hideProgressBar();
            }

            @Override
            public void onFailure(@NotNull Call<ResetEmployeePasswordResponse> call, @NotNull Throwable t) {
                ((LoginActivity)activity).hideProgressBar();
                Utility.printLog(TAG, "resetEmployeePassword:onFailure:Error:" + t.getMessage());
            }
        });
    }

    public static void employeeLogin(final Activity activity, JsonObject jsonObject) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<EmployeeLoginResponse> employeeLoginCall = apiInterface.employeeLogin(jsonObject);
        employeeLoginCall.enqueue(new Callback<EmployeeLoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<EmployeeLoginResponse> call, @NotNull Response<EmployeeLoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Utility.printLog(TAG, "employeeLogin:onResponse:" + response.body());
                        if (response.body().getCode().equals("100")) {
                            prefManager.setString(PrefManager.KEY_LOGIN_TOKEN, response.body().getLoginToken());
                            if (response.body().getOwner()){
                                if (response.body().getRegCompleted() && (!prefManager.getString(PrefManager.KEY_LOGIN_TOKEN,"").isEmpty())){
                                    Utility.startActivity(activity,MainActivity.class,false);
                                }else {
                                    prefManager.putInteger(PrefManager.KEY_REGISTRATION_STEP, (int) (response.body().getRegStep() + 1));
                                    Utility.startActivity(activity, RegistrationStepsActivity.class, false);
                                }
                            }else {
                                if (!prefManager.getString(PrefManager.KEY_LOGIN_TOKEN,"").isEmpty()){
                                    Utility.startActivity(activity,MainActivity.class,false);
                                }
                            }

                        }else {
                            Utility.showMessageDialog(activity, response.body().getMessage());
                        }
                    }
                } else {
                    Utility.showMessageDialog(activity, response.message());
                }
                ((LoginActivity)activity).hideProgressBar();
            }

            @Override
            public void onFailure(@NotNull Call<EmployeeLoginResponse> call, @NotNull Throwable t) {
                ((LoginActivity)activity).hideProgressBar();
                Utility.printLog(TAG, "employeeLogin:onFailure:Error:" + t.getMessage());
            }
        });
    }

    public static void employeeLogout(final Activity activity, JsonObject jsonObject) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<EmployeeLogoutResponse> employeeLogoutCall = apiInterface.employeeLogout(jsonObject);
        employeeLogoutCall.enqueue(new Callback<EmployeeLogoutResponse>() {
            @Override
            public void onResponse(@NotNull Call<EmployeeLogoutResponse> call, @NotNull Response<EmployeeLogoutResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Utility.printLog(TAG, "employeeLogout:onResponse:" + response.body());
                        if (response.body().getCode().equals("100")) {
                            Utility.startActivity(activity, HomeActivity.class, false);
                        }else {
                            if (response.body().getCode().equals("1014")) {
                                Utility.startActivity(activity, HomeActivity.class, false);
                            }
                        }
                    }
                } else {
                    Utility.showMessageDialog(activity, response.message());
                }
                ((RegistrationStepsActivity) activity).hideProgressBar();

            }

            @Override
            public void onFailure(@NotNull Call<EmployeeLogoutResponse> call, @NotNull Throwable t) {
                ((RegistrationStepsActivity) activity).hideProgressBar();
                Utility.printLog(TAG, "employeeLogout:onFailure:Error:" + t.getMessage());
            }
        });
    }
}
