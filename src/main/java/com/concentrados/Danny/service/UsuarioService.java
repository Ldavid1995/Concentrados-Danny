package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Usuario;

public interface UsuarioService {
    // Guarda el usuario y gestiona sus roles
    public void save(Usuario usuario, boolean crearRolUser);
    
    // Para verificar si el usuario ya existe antes de registrarlo
    public Usuario getUsuarioPorUsername(String username);
}
