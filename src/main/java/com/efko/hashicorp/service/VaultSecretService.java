package com.efko.hashicorp.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class VaultSecretService {

    private static final String SECRET_KEY = "app.example-secret";
    private static final String DEFAULT_VALUE = "secret is not available";

    private final Environment environment;

    public VaultSecretService(Environment environment) {
        this.environment = environment;
    }

    public String readExampleSecret() {
        return environment.getProperty(SECRET_KEY, DEFAULT_VALUE);
    }
}
