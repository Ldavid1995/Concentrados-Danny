package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final HttpSession session;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository, HttpSession session) {
        this.usuarioRepository = usuarioRepository;
        this.session = session;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos el usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsernameAndActivoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. Mapeamos los roles con limpieza de prefijos
        List<GrantedAuthority> roles = new ArrayList<>();
        if (usuario.getRoles() != null) {
            usuario.getRoles().forEach(rol -> {
                String nombreRaw = rol.getNombre().toUpperCase();
                
                // Normalizamos: Si en la DB dice "ROLE_ADMIN", extraemos "ADMIN"
                String soloNombre = nombreRaw.startsWith("ROLE_") ? nombreRaw.substring(5) : nombreRaw;
                
                // Agregamos la autoridad con el formato estándar ROLE_ para hasRole()
                roles.add(new SimpleGrantedAuthority("ROLE_" + soloNombre));
                
                // Agregamos la autoridad sin prefijo por si acaso usas hasAuthority()
                roles.add(new SimpleGrantedAuthority(soloNombre));
            });
        }

        // 3. LOGS DE SEGURIDAD (Revisa esto en tu consola de NetBeans)
        System.out.println("==================================================");
        System.out.println("USUARIO LOGUEADO: " + username);
        System.out.println("ROLES CARGADOS: " + roles); // Aquí DEBE aparecer ROLE_ADMIN
        System.out.println("==================================================");

        // 4. Datos para la interfaz (Header/Index)
        session.setAttribute("usuarioNombre", usuario.getNombre() + " " + usuario.getApellidos());
        session.setAttribute("usuarioImagen", usuario.getRutaImagen());
        session.setAttribute("username", usuario.getUsername());

        // 5. Retornamos el User (comparación de texto plano)
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                roles
        );
    }
}