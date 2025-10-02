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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configure CORS
                .authorizeHttpRequests(

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

                .requestMatchers(HttpMethod.GET, "/api/v1/atendimentos/me").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/atendimentos/pet/**").authenticated()

                .requestMatchers(HttpMethod.GET, "/api/v1/racas").authenticated()

                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().hasAuthority("ROLE_ADMIN")
        )

        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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

