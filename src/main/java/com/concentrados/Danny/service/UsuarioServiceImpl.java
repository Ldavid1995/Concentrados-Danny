package com.concentrados.Danny.service.impl;

import com.concentrados.Danny.repository.UsuarioRepository; 
import com.concentrados.Danny.repository.RolRepository;     
import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.domain.Rol;
import com.concentrados.Danny.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Antes usuarioDao
    
    @Autowired
    private RolRepository rolRepository;         // Antes rolDao

    @Override
    @Transactional
    public void save(Usuario usuario, boolean crearRolUser) {
        // 1. Guardamos el usuario (Spring Data JPA retorna el objeto con su nuevo ID)
        usuario = usuarioRepository.save(usuario);
        
        // 2. Si es un registro nuevo, le asignamos el rol de CLIENTE
        if (crearRolUser) {
            Rol rol = new Rol();
            rol.setNombre("ROLE_USER");
            rol.setIdUsuario(usuario.getIdUsuario());
            rolRepository.save(rol);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(String username) {

        return usuarioRepository.findByUsernameAndActivoTrue(username).orElse(null);
    }
}