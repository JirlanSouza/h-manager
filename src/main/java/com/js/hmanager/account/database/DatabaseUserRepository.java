package com.js.hmanager.account.database;

import com.js.hmanager.account.domain.User;
import com.js.hmanager.account.domain.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DatabaseUserRepository implements UserRepository {

    private final JpaUserRepository jpauserRepository;
    @Override
    public boolean existsByEmail(String email) {
        return this.jpauserRepository.existsByEmail(email);
    }

    @Override
    public void save(User user) {
        this.jpauserRepository.save(UserModel.from(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
