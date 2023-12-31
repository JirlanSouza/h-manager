package com.js.hmanager.account.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetailsModel user = userDetailRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "The user with email: %s does not exists".formatted(email)
                ));

        return user.toUserAuth();
    }
}
