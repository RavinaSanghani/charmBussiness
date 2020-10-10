package com.charm.business.retrofit;

import android.app.Activity;
import android.os.Build;

import com.charm.business.Constants;
import com.charm.business.DialogVerificationCode;
import com.charm.business.HomeActivity;
import com.charm.business.LoginActivity;
import com.charm.business.MainActivity;
import com.charm.business.PrefManager;
import com.charm.business.R;
import com.charm.business.RegistrationStepsActivity;
import com.charm.business.Utility;
import com.charm.business.responseModel.OwnerLoginResponse;
import com.charm.business.responseModel.OwnerLogoutResponse;
import com.charm.business.responseModel.ResetOwnerPasswordResponse;
import com.charm.business.responseModel.VerificationCodeResponse;
import com.charm.business.responseModel.OwnerStatusResponse;
import com.charm.business.responseModel.RegisterOwnerResponse;
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
        Call<OwnerStatusResponse> employeeStatusCall = apiInterface.employeeStatus(loginToken);
        employeeStatusCall.enqueue(new Callback<OwnerStatusResponse>() {
            @Override
            public void onResponse(@NotNull Call<OwnerStatusResponse> call, @NotNull Response<OwnerStatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Utility.printLog(TAG, "employeeStatus:onResponse:" + response.body());
                        if (response.body().getCode().equals("100")) {
                            if (response.body().getIsOwner()) {
                                if (response.body().getRegCompleted()) {
                                    Utility.startActivity(activity, MainActivity.class, true);
                                } else {
                                    prefManager.putInteger(PrefManager.KEY_REGISTRATION_STEP, (int) (response.body().getRegStep() + 1));
                                    Utility.startActivity(activity, RegistrationStepsActivity.class, true);
                                }
                            } else {
                                Utility.startActivity(activity, MainActivity.class, true);
                            }

                        } else {
                            Utility.startActivity(activity, HomeActivity.class, true);
                        }
                    }
                } else {
                    if (response.code() == 502){
                        Utility.showDialog(activity, "",activity.getResources().getString(R.string.internal_server_error));
                    }else {
                        Utility.showDialog(activity, Constants.KEY_ALERT,response.message());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<OwnerStatusResponse> call, @NotNull Throwable t) {
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
                            DialogVerificationCode code = new DialogVerificationCode(activity, response.body().getVerificationCode(), R.style.DialogRounded);
                            code.show();
                            code.setCanceledOnTouchOutside(false);
                        } else {
                            Utility.showDialog(activity, Constants.KEY_ALERT,response.body().getMessage());
                        }
                    }
                } else {
                    if (response.code() == 502){
                        Utility.showDialog(activity, "",activity.getResources().getString(R.string.internal_server_error));
                    }else {
                        Utility.showDialog(activity, Constants.KEY_ALERT,response.message());
                    }
                }
                Utility.progressBarDialogDismiss();
            }

            @Override
            public void onFailure(@NotNull Call<VerificationCodeResponse> call, @NotNull Throwable t) {
                Utility.progressBarDialogDismiss();
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
                            Utility.showDialog(activity, Constants.KEY_ALERT,response.body().getMessage());
                        }
                    }
                } else {
                    if (response.code() == 502){
                        Utility.showDialog(activity, "",activity.getResources().getString(R.string.internal_server_error));
                    }else {
                        Utility.showDialog(activity, Constants.KEY_ALERT,response.message());
                    }
                }

                Utility.progressBarDialogDismiss();
            }

            @Override
            public void onFailure(@NotNull Call<RegisterOwnerResponse> call, @NotNull Throwable t) {
                Utility.progressBarDialogDismiss();
                Utility.printLog(TAG, "registerOwner:onFailure:Error:" + t.getMessage());
            }
        });
    }

    public static void resetEmployeePassword(final Activity activity, JsonObject jsonObject) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResetOwnerPasswordResponse> verificationCodeCall = apiInterface.resetEmployeePassword(jsonObject);
        verificationCodeCall.enqueue(new Callback<ResetOwnerPasswordResponse>() {
            @Override
            public void onResponse(@NotNull Call<ResetOwnerPasswordResponse> call, @NotNull Response<ResetOwnerPasswordResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Utility.printLog(TAG, "resetEmployeePassword:onResponse:ResponseCode:" + response.body().getCode());
                        if (response.body().getCode().equals("100")) {
                            ((LoginActivity)activity).forgotPasswordDialogDismiss();
                        }else {
                            Utility.showDialog(activity, Constants.KEY_ALERT,response.body().getMessage());
                            ((LoginActivity)activity).forgotPasswordDialogDismiss();
                        }
                    }
                } else {
                    if (response.code() == 502){
                        Utility.showDialog(activity, "",activity.getResources().getString(R.string.internal_server_error));
                    }else {
                        Utility.showDialog(activity, Constants.KEY_ALERT,response.message());
                    }
                }
                Utility.progressBarDialogDismiss();
            }

            @Override
            public void onFailure(@NotNull Call<ResetOwnerPasswordResponse> call, @NotNull Throwable t) {
                Utility.progressBarDialogDismiss();
                Utility.printLog(TAG, "resetEmployeePassword:onFailure:Error:" + t.getMessage());
            }
        });
    }

    public static void employeeLogin(final Activity activity, JsonObject jsonObject) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<OwnerLoginResponse> employeeLoginCall = apiInterface.employeeLogin(jsonObject);
        employeeLoginCall.enqueue(new Callback<OwnerLoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<OwnerLoginResponse> call, @NotNull Response<OwnerLoginResponse> response) {
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
                            Utility.showDialog(activity, Constants.KEY_ALERT,response.body().getMessage());
                        }
                    }
                } else {
                    Utility.showDialog(activity, Constants.KEY_ALERT,response.message());
                }
                Utility.progressBarDialogDismiss();
            }

            @Override
            public void onFailure(@NotNull Call<OwnerLoginResponse> call, @NotNull Throwable t) {
                Utility.progressBarDialogDismiss();
                Utility.printLog(TAG, "employeeLogin:onFailure:Error:" + t.getMessage());
            }
        });
    }

    public static void employeeLogout(final Activity activity, JsonObject jsonObject) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<OwnerLogoutResponse> employeeLogoutCall = apiInterface.employeeLogout(jsonObject);
        employeeLogoutCall.enqueue(new Callback<OwnerLogoutResponse>() {
            @Override
            public void onResponse(@NotNull Call<OwnerLogoutResponse> call, @NotNull Response<OwnerLogoutResponse> response) {
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
                    if (response.code() == 502){
                        Utility.showDialog(activity, "",activity.getResources().getString(R.string.internal_server_error));
                    }else {
                        Utility.showDialog(activity, Constants.KEY_ALERT,response.message());
                    }
                }
                Utility.progressBarDialogDismiss();
            }

            @Override
            public void onFailure(@NotNull Call<OwnerLogoutResponse> call, @NotNull Throwable t) {
                Utility.progressBarDialogDismiss();
                Utility.printLog(TAG, "employeeLogout:onFailure:Error:" + t.getMessage());
            }
        });
    }


}
