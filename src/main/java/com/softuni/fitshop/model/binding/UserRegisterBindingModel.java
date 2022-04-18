package com.softuni.fitshop.model.binding;

import com.softuni.fitshop.model.entity.enums.UserExperienceEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {
    @NotBlank
    @Size(min = 3, max = 25, message = "Username should be between 3 and 25 characters.")
    private String username;
    @Email
    @NotBlank
    @Size(min = 7, max = 50, message = "Email should be between 7 and 50 characters.")
    private String email;
    @NotNull
    private UserExperienceEnum experienceLevel;
    @NotNull
    @NotBlank
    @Size(min = 4, max = 30)
    private String password;
    @NotNull
    @NotBlank
    @Size(min = 4, max = 30)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserExperienceEnum getExperienceLevel() {
        return experienceLevel;
    }

    public UserRegisterBindingModel setExperienceLevel(UserExperienceEnum experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }


}