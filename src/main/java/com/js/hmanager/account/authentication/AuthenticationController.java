package com.js.hmanager.account.authentication;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("/login")
public class AuthenticationController {
    private final AuthenticationProvider authenticationProvider;
    private final JwtEncoder jwtEncoder;

    @PostMapping
    public AuthenticationTokenResponse generateToken(LoginDto loginDto) throws ServletException {
        Authentication authentication = this.authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );


        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("hmanager-backend")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        Jwt jwt = this.jwtEncoder.encode(JwtEncoderParameters.from(claims));

        return new AuthenticationTokenResponse(jwt.getTokenValue(), jwt.getSubject(), jwt.getExpiresAt());
    }
}
