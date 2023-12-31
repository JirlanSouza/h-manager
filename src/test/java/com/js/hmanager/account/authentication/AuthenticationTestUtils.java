package com.js.hmanager.account.authentication;

import com.js.hmanager.account.database.JpaUserRepository;
import com.js.hmanager.account.database.UserModel;
import com.js.hmanager.account.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationTestUtils {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    public void createUser(String email, String password) {
        User user = User.create("Joe", "Jho", email, password, "MANAGER");
        this.jpaUserRepository.save(UserModel.from(user));
    }
}
