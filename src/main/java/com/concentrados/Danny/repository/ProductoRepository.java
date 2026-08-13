package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // 1. Procedimiento para CREAR producto en PKG_PRODUCTOS
    @Procedure(procedureName = "PKG_PRODUCTOS.CREAR_PRODUCTO")
    Long crearProducto(
        @Param("p_id_categoria") Long idCategoria,
        @Param("p_nombre") String nombre,
        @Param("p_marca") String marca,
        @Param("p_descripcion") String descripcion,
        @Param("p_unidad_medida") String unidadMedida,
        @Param("p_precio") BigDecimal precio,
        @Param("p_stock") Integer stock,
        @Param("p_stock_minimo") Integer stockMinimo
    );

    // 2. Procedimiento para ACTUALIZAR producto en PKG_PRODUCTOS
    @Procedure(procedureName = "PKG_PRODUCTOS.ACTUALIZAR_PRODUCTO")
    void actualizarProducto(
        @Param("p_id_producto") Long idProducto,
        @Param("p_id_categoria") Long idCategoria,
        @Param("p_nombre") String nombre,
        @Param("p_marca") String marca,
        @Param("p_descripcion") String descripcion,
        @Param("p_unidad_medida") String unidadMedida,
        @Param("p_precio") BigDecimal precio,
        @Param("p_stock") Integer stock,
        @Param("p_stock_minimo") Integer stockMinimo
    );

    // 3. Procedimiento para DESACTIVAR (Eliminado Lógico) en PKG_PRODUCTOS
    @Procedure(procedureName = "PKG_PRODUCTOS.DESACTIVAR_PRODUCTO")
    void desactivarProducto(@Param("p_id_producto") Long idProducto);

    // 4. Función para OBTENER PRECIO
    @Query(value = "SELECT PKG_PRODUCTOS.OBTENER_PRECIO(:p_id_producto) FROM DUAL", nativeQuery = true)
    BigDecimal obtenerPrecio(@Param("p_id_producto") Long idProducto);
}