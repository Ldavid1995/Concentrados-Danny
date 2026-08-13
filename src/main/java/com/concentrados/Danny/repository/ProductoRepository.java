package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Invocación al procedimiento de creación de tu paquete PL/SQL
    @Procedure(procedureName = "pkg_productos.crear_producto")
    Long crearProducto(
        @Param("p_nombre") String nombre,
        @Param("p_descripcion") String descripcion,
        @Param("p_precio") Double precio,
        @Param("p_stock") Integer stock,
        @Param("p_id_categoria") Long idCategoria
    );
}