package com.concentrados.Danny.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    public SecurityConfig() {
    System.out.println("************************************************");
    System.out.println("¡¡¡EL SECURITY CONFIG SE ESTÁ CARGANDO!!!");
    System.out.println("************************************************");
}

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(requests -> requests
            // LIBERAMOS TODO: Si con esto no entra, el problema es el PasswordEncoder fijo
            .anyRequest().permitAll() 
        )
        .formLogin(form -> form
            .loginPage("/login")
            .permitAll()
        );

    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Desactiva BCrypt para permitir texto plano ("123")
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
public org.springframework.security.authentication.dao.DaoAuthenticationProvider authenticationProvider(
        UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    var authProvider = new org.springframework.security.authentication.dao.DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
}

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build,
            @Lazy PasswordEncoder passwordEncoder,
            @Lazy UserDetailsService userDetailsService) throws Exception {
        // Esta es la llave: le dice a Spring que use TU NoOpPasswordEncoder y TU Service
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
  
}