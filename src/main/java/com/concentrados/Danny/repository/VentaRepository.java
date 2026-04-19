package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Método para jalar el historial específico de un usuario
    List<Venta> findByIdUsuario(Long idUsuario);
}