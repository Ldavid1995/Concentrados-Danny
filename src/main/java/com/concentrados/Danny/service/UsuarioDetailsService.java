package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService{
    private final UsuarioRepository usuarioRepository;
    private final HttpSession session;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository, HttpSession session) {
        this.usuarioRepository = usuarioRepository;
        this.session = session;
    }

@Override
@Transactional(readOnly = true)
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByUsernameAndActivoTrue(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

    var roles = usuario.getRoles().stream()
          .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
            .collect(Collectors.toSet());

    // ESTO TE DIRÁ EL ERROR REAL EN LA CONSOLA
    System.out.println("USUARIO LOCALIZADO: " + username);
    System.out.println("CLAVE EN DB: " + usuario.getPassword());
    System.out.println("ROLES CARGADOS: " + roles);

    return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
