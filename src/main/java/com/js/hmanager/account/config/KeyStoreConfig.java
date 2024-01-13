package com.js.hmanager.account.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class KeyStoreConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtEncoder jwtEncoder(KeyStoreProperties keyStoreProperties) throws Exception {
        RSAKey rsaKey = loadRSAKey(keyStoreProperties);
        ImmutableJWKSet<SecurityContext> jwkSet = new ImmutableJWKSet<>(new JWKSet(rsaKey));

        return new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyStoreProperties keyStoreProperties) throws Exception {
        RSAKey rsaKey = loadRSAKey(keyStoreProperties);

        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    private RSAKey loadRSAKey(KeyStoreProperties keyStoreProperties) throws Exception {
        InputStream keyStoreInputStream = keyStoreProperties.getJksLocation().getInputStream();
        char[] keystorePass = keyStoreProperties.getPass().toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");

        keyStore.load(keyStoreInputStream, keystorePass);
        return RSAKey.load(keyStore, keyStoreProperties.getKeyAlias(), keystorePass);
    }
}
