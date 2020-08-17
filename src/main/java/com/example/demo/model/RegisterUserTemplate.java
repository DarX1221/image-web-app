package com.example.demo.model;

import org.apache.commons.validator.routines.EmailValidator;

public class RegisterUserTemplate {
    public String username;
    public String email;
    public String password;
    public String passwordRepeat;


    public RegisterUserTemplate() {
    }

    public RegisterUserTemplate(String username, String email,
                                String password, String passwordRepeat) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public boolean checkPasswords() {
        return password.equals(passwordRepeat);
    }

    public boolean checkEmail() {
        return EmailValidator.getInstance().isValid(email);
    }

}