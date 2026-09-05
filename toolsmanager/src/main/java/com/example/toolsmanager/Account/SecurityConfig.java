package com.example.toolsmanager.Account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http

                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers(
                                                                "/index.html",
                                                                "/style.css",
                                                                "/script.js",
                                                                "/accountController/register")
                                                .permitAll()

                                                .anyRequest().authenticated())

                                .formLogin(login -> login
                                                .loginProcessingUrl("/login")
                                                .loginPage("/index.html")
                                                .defaultSuccessUrl("/account_page.html")
                                                .failureUrl("/index.html?error")
                                                .permitAll())

                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .logoutSuccessUrl("/index.html"))
                                // 修正予定
                                .csrf(csrf -> csrf.disable());

                return http.build();
        }
}