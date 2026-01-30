package com.thegym.warehousemanagementsystem.config;

import com.thegym.warehousemanagementsystem.enums.Role;
import com.thegym.warehousemanagementsystem.filters.JwtAuthenticationFilter;
import com.thegym.warehousemanagementsystem.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration()
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private AuthService authService;
    private JwtAuthenticationFilter  jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
     }

     @Bean
     public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider(authService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
     }

     @Bean
     public AuthenticationManager  authenticationManager(AuthenticationConfiguration conf) {
        return conf.getAuthenticationManager();
     }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement( c->
                        c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(c->c.disable())
                .authorizeHttpRequests( c -> c
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/warehouses").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/warehouses/*").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/api/warehouses/{warehouseId}/locations").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/api/carton-headers/**").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/carton-headers/**").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(c->{
                    c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    c.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                    });
                });

        return http.build();
    }

}
