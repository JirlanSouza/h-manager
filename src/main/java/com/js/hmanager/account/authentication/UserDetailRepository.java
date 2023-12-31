package com.js.hmanager.account.authentication;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailRepository extends CrudRepository<UserDetailsModel, UUID> {
    Optional<UserDetailsModel> findByEmail(String email);
}
