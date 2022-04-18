package com.softuni.fitshop.model.custom;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class FitShopUser extends User {
    private String experienceLevel;

    public FitShopUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String experienceLevel) {
        super(username, password, authorities);
        this.experienceLevel = experienceLevel;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public FitShopUser setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
        return this;
    }
}