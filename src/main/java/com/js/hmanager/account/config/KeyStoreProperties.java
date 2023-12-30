package com.js.hmanager.account.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("auth.keystore")
public class KeyStoreProperties {
    @NotNull
    private Resource jksLocation;

    @NotNull
    private String pass;

    @NotNull
    private String keyAlias;
}
