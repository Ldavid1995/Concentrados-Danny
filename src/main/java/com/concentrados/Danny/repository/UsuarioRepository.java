package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Fíjate bien en las mayúsculas: Username, Activo, True
    Optional<Usuario> findByUsernameAndActivoTrue(String username);
}