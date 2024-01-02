package com.js.hmanager.account.authentication;

import com.js.hmanager.account.database.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(DatabaseUserDetailsService.class);

    private final JpaUserRepository jpaUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDetailsModel> userOptional = jpaUserRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            logger.info("User with email: '{}' does not exists", email);

            throw new UsernameNotFoundException("The user with email: %s does not exists".formatted(email));
        }

        return userOptional.get().toUserAuth();
    }
}
