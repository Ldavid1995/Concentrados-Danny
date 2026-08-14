package com.concentrados.Danny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlsqlService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // =========================================================================
    // 1. GUARDAR / ACTUALIZAR PRODUCTO
    // =========================================================================
    public void guardarProducto(String nombre, String marca, String especie, double precio, int stock, String unidadMedida) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_GUARDAR_PRODUCTO"); // Nombre de tu SP en Oracle

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_NOMBRE", nombre);
        inParams.put("P_MARCA", marca);
        inParams.put("P_ESPECIE", especie);
        inParams.put("P_PRECIO", precio);
        inParams.put("P_STOCK", stock);
        inParams.put("P_UNIDAD_MEDIDA", unidadMedida);

        jdbcCall.execute(inParams);
    }

    // =========================================================================
    // 2. OBTENER TOTAL DE UN PEDIDO (Función PL/SQL)
    // =========================================================================
    public double getTotalPedido(int idPedido) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("FN_TOTAL_PEDIDO");

        return jdbcCall.executeFunction(Double.class, idPedido);
    }

    // =========================================================================
    // 3. OBTENER NOMBRE COMPLETO DE USUARIO (Función PL/SQL)
    // =========================================================================
    public String getNombreCompleto(int idUsuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("FN_NOMBRE_COMPLETO_USUARIO");

        return jdbcCall.executeFunction(String.class, idUsuario);
    }
    
    // =========================================================================
    // 4. ELIMINAR PRODUCTO (Procedimiento PL/SQL)
    // =========================================================================
    public void eliminarProducto(int idProducto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_ELIMINAR_PRODUCTO");

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_ID_PRODUCTO", idProducto);

        jdbcCall.execute(inParams);
    }


    // 5. VERIFICAR STOCK (Función PL/SQL)

    public int verificarStock(int idProducto, int cantidadSolicitada) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName("FN_VERIFICAR_STOCK");

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_ID_PRODUCTO", idProducto);
        inParams.put("P_CANTIDAD", cantidadSolicitada);

        return jdbcCall.executeFunction(Integer.class, inParams);
    }
    //Procedimiento actualizar
    public void actualizarStock(int idProducto, int cantidad) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_ACTUALIZAR_STOCK");

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_ID_PRODUCTO", idProducto);
        inParams.put("P_CANTIDAD", cantidad);

        jdbcCall.execute(inParams);
    }
}