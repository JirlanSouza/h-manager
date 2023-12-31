package com.js.hmanager.account.domain;

import java.util.Optional;

public interface UserRepository {
    boolean existsByEmail(String email);
    void save(User user);

    Optional<User> findByEmail(String email);
}
