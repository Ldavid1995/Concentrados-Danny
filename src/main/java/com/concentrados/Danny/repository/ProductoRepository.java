package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Producto;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; 
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE " +
           "p.nombre LIKE %:keyword% OR " +
           "p.marca LIKE %:keyword% OR " +
           "p.especie LIKE %:keyword%") 
    List<Producto> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT p.marca, SUM(p.existencias) FROM Producto p GROUP BY p.marca")
    List<Object[]> obtenerStockPorMarca();

    @Query("SELECT SUM(p.precio * p.existencias) FROM Producto p")
    Double obtenerValorTotalInventario();
    
}