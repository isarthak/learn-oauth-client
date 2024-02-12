package com.example.learnoauthclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception{
        return http
                .authorizeExchange(req->{
                    req.pathMatchers("/public/**").permitAll();
                    req.anyExchange().authenticated();
                })
                .oauth2Login(Customizer.withDefaults())
                .build();
    }
}
