package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí Spring ya te regala el CRUD: 
    // save(), findAll(), findById(), deleteById(), etc.
}