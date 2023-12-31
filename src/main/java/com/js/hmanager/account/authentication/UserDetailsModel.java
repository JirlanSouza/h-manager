package com.js.hmanager.account.authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public record UserDetailsModel(String email, String password, String userType) {

    public User toUserAuth() {
        return new User(this.email, this.password, List.of(new SimpleGrantedAuthority(this.userType)));
    }
}
