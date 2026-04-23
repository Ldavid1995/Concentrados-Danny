package com.concentrados.Danny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityConfig() {
        System.out.println("************************************************");
        System.out.println("¡¡¡EL SECURITY CONFIG SE ESTÁ CARGANDO ACTUALIZADO!!!");
        System.out.println("************************************************");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // 1. Recursos estáticos
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()

                // 2. Rutas Públicas
                .requestMatchers("/", "/index", "/login", "/registro/**").permitAll()
                .requestMatchers("/producto/listado/**", "/producto/calculadora", "/carrito/**").permitAll()

                // 3. Reportes y Cotizaciones (Permitido para USER, VENDEDOR y ADMIN)
                .requestMatchers("/reporte/**").hasAnyRole("ADMIN", "VENDEDOR", "USER")

                // 4. Gestión de Productos (VENDEDOR y ADMIN)
                .requestMatchers("/producto/nuevo", "/producto/guardar", "/producto/modificar/**").hasAnyRole("ADMIN", "VENDEDOR")
                
                // 5. Funciones Críticas (Solo ADMIN)
                .requestMatchers("/producto/eliminar/**", "/usuario/**").hasRole("ADMIN")
                
                // 6. Cualquier otra ruta requiere estar logueado
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}