package com.concentrados.Danny.service;

import com.concentrados.Danny.repository.UsuarioRepository;
import com.concentrados.Danny.repository.RolRepository;
import com.concentrados.Danny.domain.Usuario;
import com.concentrados.Danny.domain.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioPorUsernameOCorreo(String username, String correo) {
        return usuarioRepository.findByUsernameOrCorreo(username, correo);
    }

    @Override
    @Transactional
    public void save(Usuario usuario, boolean crearRolUser) {
        if (crearRolUser) {
            Rol rolDefault = rolRepository.findById(2L).orElse(null);
            usuario.setRol(rolDefault);
        }
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    @Override
    @Transactional
    public void asignarRol(Long idUsuario, String nombreRol) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario != null) {
            Rol rol = new Rol();
            rol.setNombre(nombreRol);
            rol = rolRepository.save(rol);
            usuario.setRol(rol);
            usuarioRepository.save(usuario);
        }
    }
}