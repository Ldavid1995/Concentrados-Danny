package com.concentrados.Danny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            // Permitimos solo los recursos estáticos (CSS, imágenes) y el registro
            .requestMatchers("/css/**", "/js/**", "/images/**", "/registro/**").permitAll()
            // Cualquier otra ruta (incluyendo "/") requiere estar logueado
            .anyRequest().authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/login") // Esta es tu vista de login
            .permitAll()
            .defaultSuccessUrl("/", true) // Al loguearse bien, lo manda al index
        )
        .logout((logout) -> logout.permitAll());

    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Esto permite que el sistema no te exija encriptación por ahora
        return NoOpPasswordEncoder.getInstance();
    }
}