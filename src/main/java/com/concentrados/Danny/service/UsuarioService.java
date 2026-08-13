package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Usuario;
import java.util.List;

public interface UsuarioService {

    List<Usuario> getUsuarios();

    Usuario getUsuario(Usuario usuario);

    Usuario getUsuarioPorUsername(String username);

    Usuario getUsuarioPorUsernameOCorreo(String username, String correo);

    void save(Usuario usuario, boolean crearRolUser);

    void delete(Usuario usuario);
    
    void asignarRol(Long idUsuario, String nombreRol);
}