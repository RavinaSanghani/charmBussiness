package com.app.charmbusiness.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterOwnerResponse {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("employeeID")
    @Expose
    private Long employeeID;
    @SerializedName("loginToken")
    @Expose
    private String loginToken;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    @Override
    public String toString() {
        return "RegisterOwnerResponse{" +
                "code='" + code + '\'' +
                ", message=" + message +
                ", employeeID=" + employeeID +
                ", loginToken='" + loginToken + '\'' +
                '}';
    }
}
