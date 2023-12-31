package com.js.hmanager.account.database;

import com.js.hmanager.account.authentication.UserDetailsModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends CrudRepository<UserModel, UUID> {
    boolean existsByEmail(String email);

    @Query("""
                select new com.js.hmanager.account.authentication.UserDetailsModel(u.email, u.password, u.userType)
                from UserModel as u where u.email = :email
            """)
    Optional<UserDetailsModel> findByEmail(String email);
}
