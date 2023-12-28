package com.js.hmanager.account.domain;

public interface UserRepository {
    boolean existsByEmail(String email);
    void save(User user);
}
