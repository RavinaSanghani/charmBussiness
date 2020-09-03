package com.charm.business.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerStatusResponse {
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
    @SerializedName("oldSession")
    @Expose
    private Boolean oldSession;
    @SerializedName("isOwner")
    @Expose
    private Boolean isOwner;

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

    public Boolean getOldSession() {
        return oldSession;
    }

    public void setOldSession(Boolean oldSession) {
        this.oldSession = oldSession;
    }

    public Boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }


    @Override
    public String toString() {
        return "EmployeeStatusResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", regCompleted=" + regCompleted +
                ", regStep=" + regStep +
                ", oldSession=" + oldSession +
                ", isOwner=" + isOwner +
                '}';
    }
}
