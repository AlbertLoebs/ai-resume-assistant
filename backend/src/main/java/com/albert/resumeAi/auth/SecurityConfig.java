package com.albert.resumeAi.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration for the application.
 *
 * Annotate with @Configuration and @EnableWebSecurity.
 *
 * Beans to define:
 *
 *   SecurityFilterChain filterChain(HttpSecurity http)
 *     Configure the security rules:
 *       - Disable CSRF (stateless REST API — no session cookies).
 *       - Session management: STATELESS (JWT carries all state).
 *       - Permit all:
 *           /api/v1/auth/**          (register + login are public)
 *           /api/v1/health           (health check stays open)
 *       - All other requests require authentication.
 *       - Register JwtAuthFilter BEFORE UsernamePasswordAuthenticationFilter
 *         so it runs first and populates the SecurityContext.
 *
 *   PasswordEncoder passwordEncoder()
 *     Return new BCryptPasswordEncoder().
 *     Declaring it as a @Bean makes it injectable by AuthService without
 *     any circular-dependency risk.
 *
 * Things NOT to do:
 *   - Don't extend WebSecurityConfigurerAdapter — it's removed in Spring Security 6.
 *   - Don't use http.authorizeRequests() — use http.authorizeHttpRequests() instead.
 *   - Don't wire UserDetailsService unless you need form-login or Basic auth.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

        // TODO: disable CSRF
        // TODO: set session policy to STATELESS
        // TODO: permit /api/v1/auth/**, /api/v1/health; authenticate everything else
        // TODO: add jwtAuthFilter before UsernamePasswordAuthenticationFilter
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/api/v1/health").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
