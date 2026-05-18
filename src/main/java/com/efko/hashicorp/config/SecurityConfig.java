package com.efko.hashicorp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http, Environment environment) throws Exception {
        var oauth2ClientEnabled = environment.containsProperty(
            "spring.security.oauth2.client.registration.keycloak.client-id"
        );
        var resourceServerEnabled = environment.containsProperty(
            "spring.security.oauth2.resourceserver.jwt.issuer-uri"
        ) || environment.containsProperty(
            "spring.security.oauth2.resourceserver.jwt.jwk-set-uri"
        );

        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/api/public", "/error").permitAll()
                .anyRequest().authenticated()
            );

        if (oauth2ClientEnabled) {
            http.oauth2Login(Customizer.withDefaults());
        }

        if (resourceServerEnabled) {
            http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        }

        return http.build();
    }
}
