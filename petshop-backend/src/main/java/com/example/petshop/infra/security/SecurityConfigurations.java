package com.example.petshop.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                    authorize -> authorize
                //authentication endpoints
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()

                //usuarios podem ver e alterar seus proprios dados
                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/me").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/clientes/me").authenticated()

                .requestMatchers(HttpMethod.GET, "/api/v1/pets/me").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/pets/me/**").authenticated()

                .requestMatchers(HttpMethod.GET, "/api/v1/contatos/me").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/contatos/me/**").authenticated()

                .requestMatchers(HttpMethod.GET, "/api/v1/enderecos/me").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/enderecos/me/**").authenticated()
                .anyRequest().hasAuthority("ROLE_ADMIN")
        )
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

