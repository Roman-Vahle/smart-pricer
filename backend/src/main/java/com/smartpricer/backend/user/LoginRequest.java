package com.smartpricer.backend.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    private String userPassword;

    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.userEmail = email;
        this.userPassword = password;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String email) {
        this.userEmail = email;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String password) {
        this.userPassword = password;
    }


}
