package com.js.hmanager.account.application;

import com.js.hmanager.account.domain.User;

public record CreateUserDto(String firstName, String lastName, String email, String password, String userType) {
    User toUser() {
        return User.create(firstName, lastName, email, password, userType);
    }
}
