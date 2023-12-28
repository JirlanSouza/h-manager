package com.js.hmanager.account.application;

import com.js.hmanager.account.domain.UserRepository;
import com.js.hmanager.common.domainExceptions.ConflictEntityDomainException;

public class CreateUserService {
    private final UserRepository userRepository;

    public CreateUserService(UserRepository repository) {
        this.userRepository = repository;
    }

    void execute(CreateUserDto createUserDto) {
        boolean existUser = userRepository.existsByEmail(createUserDto.email());

        if (existUser) {
            throw new ConflictEntityDomainException("username: %s already exists".formatted(createUserDto.email()));
        }

        var account = createUserDto.toUser();
        userRepository.save(account);
    }
}
