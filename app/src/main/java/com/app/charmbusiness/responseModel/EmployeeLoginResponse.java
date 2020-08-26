package com.app.charmbusiness.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeLoginResponse {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("regCompleted")
    @Expose
    private Boolean regCompleted;
    @SerializedName("regStep")
    @Expose
    private Long regStep;
    @SerializedName("owner")
    @Expose
    private Boolean owner;
    @SerializedName("oldSesison")
    @Expose
    private Boolean oldSesison;
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

    public Boolean getRegCompleted() {
        return regCompleted;
    }

    public void setRegCompleted(Boolean regCompleted) {
        this.regCompleted = regCompleted;
    }

    public Long getRegStep() {
        return regStep;
    }

    public void setRegStep(Long regStep) {
        this.regStep = regStep;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Boolean getOldSesison() {
        return oldSesison;
    }

    public void setOldSesison(Boolean oldSesison) {
        this.oldSesison = oldSesison;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    @Override
    public String toString() {
        return "EmployeeLoginResponse{" +
                "code='" + code + '\'' +
                ", message=" + message +
                ", regCompleted=" + regCompleted +
                ", regStep=" + regStep +
                ", owner=" + owner +
                ", oldSesison=" + oldSesison +
                ", loginToken='" + loginToken + '\'' +
                '}';
    }
}
