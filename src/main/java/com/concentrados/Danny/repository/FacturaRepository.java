package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByIdUsuario(Long idUsuario);
}