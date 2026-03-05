package com.asset.AssetManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.POST, "/asset").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/asset/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/asset/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/asset/upload").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/asset/assign/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/asset/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                        .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
