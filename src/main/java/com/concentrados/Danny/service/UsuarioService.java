package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Usuario;
import java.util.List;

public interface UsuarioService {
    
    // Obtener la lista de todos los usuarios para la gestión de roles
    public List<Usuario> getUsuarios();
    
    // Obtener un usuario por su ID
    public Usuario getUsuario(Usuario usuario);
    
    // Guarda el usuario y gestiona sus roles
    public void save(Usuario usuario, boolean crearRolUser);
    
    // Para verificar si el usuario ya existe antes de registrarlo
    public Usuario getUsuarioPorUsername(String username);
    
    // Borrar un usuario
    public void delete(Usuario usuario);

    public void asignarRol(Integer idUsuario, String nombreRol);
}