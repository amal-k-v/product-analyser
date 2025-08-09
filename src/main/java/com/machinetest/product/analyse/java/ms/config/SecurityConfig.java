package com.machinetest.product.analyse.java.ms.config;

import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import org.springdoc.api.OpenApiCustomiser;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springdoc.core.customizers.*;

import java.util.Set;

@Configuration
public class SecurityConfig {
    private static final Set<String> ALLOWED_IPS = Set.of(
            "192.168.1.10",  // example allowed IPs
            "127.0.0.1"
    );

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("user")
                .password("{noop}123")
                .roles("PUBLIC")
                .build();

        var admin = User.withUsername("admin")
                .password("{noop}123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

//    @Bean
//    public IpAddressFilter ipAddressFilter() {
//        return new IpAddressFilter(ALLOWED_IPS);
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                       // .requestMatchers("/**").hasRole("PUBLIC")
                        .requestMatchers("/product/analyse/v1/swagger-ui/user").hasRole("PUBLIC")
                        .requestMatchers("/**").hasAnyRole( "ADMIN")
                        .requestMatchers("/public/**").hasAnyRole( "PUBLIC")
                        .requestMatchers("/swagger-ui/index.html/**").hasAnyRole( "PUBLIC")
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**")
                        .authenticated()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }








}

