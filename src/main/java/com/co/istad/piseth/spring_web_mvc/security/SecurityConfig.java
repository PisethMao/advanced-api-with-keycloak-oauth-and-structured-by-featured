package com.co.istad.piseth.spring_web_mvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults()));
//        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/orders/**").permitAll()
                .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/v3/api-docs/swagger-config",
                        "/api/v1/files/**",
                        "/file/**",
                        "/api/v1/auth/register"
                ).permitAll()
                .requestMatchers("/scalar/**", "/scalar", "/api/v1/files/**").permitAll()
                .anyRequest().authenticated());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtConverter = jwt -> {
            Map<String, Collection<String>> realms = jwt.getClaim("realm_access");
            assert realms != null;
            Collection<String> roles = realms.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_ " + role))
                    .collect(Collectors.toList());
        };
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtConverter);
        return jwtAuthenticationConverter;
    }
}
