package com.app.charmbusiness.responseModel;

public class EmployeeLoginResponse {
    private String password,phone,userType;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "EmployeeLoginResponse{" +
            "password='" + password + '\'' +
            ", phone='" + phone + '\'' +
            ", userType='" + userType + '\'' +
            '}';
    }
}
