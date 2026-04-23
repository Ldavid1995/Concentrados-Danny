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
                // 1. Recursos estáticos (Acceso total)
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                
                // 2. Rutas Públicas (Index, Login y Registro básico)
                .requestMatchers("/", "/index", "/login", "/registro/nuevo", "/registro/crear").permitAll()
                .requestMatchers("/producto/listado/**", "/producto/calculadora").permitAll()
                .requestMatchers("/carrito/**").permitAll()

                // 3. Permisos para VENDEDOR y ADMIN (Gestión de ventas y stock)
                .requestMatchers("/reporte/**").hasAnyRole("ADMIN", "VENDEDOR")
                .requestMatchers("/producto/nuevo", "/producto/guardar", "/producto/modificar/**").hasAnyRole("ADMIN", "VENDEDOR")
                
                // 4. Permisos EXCLUSIVOS de ADMIN (Seguridad y borrado)
                .requestMatchers("/registro/usuarios", "/registro/asignarRol").hasAuthority("ADMIN")
                .requestMatchers("/producto/eliminar/**").hasAuthority("ADMIN")
                
                // 5. Cualquier otra ruta de productos requiere estar logueado
                .requestMatchers("/producto/**").authenticated()
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
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
        // Mantenemos NoOp para tus pruebas con claves en texto plano (123, 456)
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