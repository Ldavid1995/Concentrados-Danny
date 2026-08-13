package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    // Método para obtener solo categorías activas
    List<Categoria> findByActivo(Integer activo);
}