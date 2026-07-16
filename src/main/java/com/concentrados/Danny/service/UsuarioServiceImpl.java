package com.concentrados.Danny.service;

import com.concentrados.Danny.repository.UsuarioRepository; 
import com.concentrados.Danny.repository.RolRepository;     
import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.domain.Rol;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuario(Usuario usuario) {
        return usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Usuario usuario, boolean crearRolUser) {
        usuario = usuarioRepository.save(usuario);
        if (crearRolUser) {
            this.asignarRol(usuario.getIdUsuario().longValue(), "ROLE_USER");
        }
    }

    @Override
    @Transactional
    public void asignarRol(Long idUsuario, String nombreRol) { 
        Rol rol = new Rol();
        rol.setNombre(nombreRol);
        rol.setIdUsuario(idUsuario);
        rolRepository.save(rol);
    }

    @Override
    @Transactional
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsernameAndActivoTrue(username).orElse(null);
    }
}