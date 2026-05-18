package com.efko.hashicorp.controller;

import com.efko.hashicorp.service.VaultSecretService;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    private final VaultSecretService vaultSecretService;

    public DemoController(VaultSecretService vaultSecretService) {
        this.vaultSecretService = vaultSecretService;
    }

    @GetMapping("/public")
    public Map<String, Object> publicInfo() {
        return Map.of(
            "service", "hashicorp-sso-demo",
            "login", "/oauth2/authorization/keycloak",
            "securedEndpoints", List.of("/api/me", "/api/secret")
        );
    }

    @GetMapping("/me")
    public Map<String, Object> currentUser(Authentication authentication) {
        return Map.of(
            "name", authentication.getName(),
            "authorities", authentication.getAuthorities().stream()
                .map(Object::toString)
                .toList()
        );
    }

    @GetMapping("/secret")
    public Map<String, String> secret() {
        return Map.of("app.example-secret", vaultSecretService.readExampleSecret());
    }
}
