package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Usuario findByUsernameOrCorreo(String username, String correo);

    boolean existsByUsername(String username);

    boolean existsByCorreo(String correo);
    
    Usuario findByUsernameAndActivoTrue(String username);
}