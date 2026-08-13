/*==============================================================
                    ESPECIFICACION DEL PAQUETE
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_calculos_negocio AS

    -- Grupo 1: Cálculo de importes
    FUNCTION fn_calcular_subtotal(
        p_producto IN NUMBER,
        p_cantidad IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_calcular_total_pedido(
        p_pedido IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_calcular_impuesto(
        p_monto IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_calcular_descuento_cupon(
        p_codigo IN VARCHAR2,
        p_monto  IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_calcular_descuento_promocion(
        p_promocion IN NUMBER,
        p_monto     IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_calcular_costo_envio(
        p_zona IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_calcular_precio_final(
        p_producto  IN NUMBER,
        p_promocion IN NUMBER DEFAULT NULL
    ) RETURN NUMBER;

    -- Grupo 2: Validaciones de negocio
    FUNCTION fn_verificar_stock_disponible(
        p_producto IN NUMBER,
        p_cantidad IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_validar_cupon_vigente(
        p_codigo IN VARCHAR2
    ) RETURN NUMBER;

    FUNCTION fn_validar_promocion_vigente(
        p_promocion IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_verificar_usuario_activo(
        p_usuario IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_verificar_correo_existe(
        p_correo IN VARCHAR2
    ) RETURN NUMBER;

    -- Grupo 3: Verificación de disponibilidad
    FUNCTION fn_verificar_producto_activo(
        p_producto IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_obtener_stock_actual(
        p_producto IN NUMBER
    ) RETURN NUMBER;

    FUNCTION fn_dias_para_vencer_lote(
        p_lote IN NUMBER
    ) RETURN NUMBER;

END pkg_calculos_negocio;
/

/*==============================================================
                      CUERPO DEL PAQUETE
==============================================================*/

CREATE OR REPLACE PACKAGE BODY pkg_calculos_negocio AS

    -- 1. FN_CALCULAR_SUBTOTAL
    FUNCTION fn_calcular_subtotal(
        p_producto IN NUMBER,
        p_cantidad IN NUMBER
    ) RETURN NUMBER AS
        v_precio_base producto.precio%TYPE;
        v_monto_total  NUMBER(10,2);
    BEGIN
        SELECT precio
        INTO v_precio_base
        FROM producto
        WHERE id_producto = p_producto;

        v_monto_total := v_precio_base * p_cantidad;

        RETURN v_monto_total;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_calcular_subtotal;


    -- 2. FN_CALCULAR_TOTAL_PEDIDO
    FUNCTION fn_calcular_total_pedido(
        p_pedido IN NUMBER
    ) RETURN NUMBER AS
        v_suma_total NUMBER(10,2);
    BEGIN
        SELECT NVL(SUM(subtotal), 0)
        INTO v_suma_total
        FROM detalle_pedido
        WHERE id_pedido = p_pedido;

        RETURN v_suma_total;
    END fn_calcular_total_pedido;


    -- 3. FN_CALCULAR_IMPUESTO
    FUNCTION fn_calcular_impuesto(
        p_monto IN NUMBER
    ) RETURN NUMBER AS
        v_monto_iva NUMBER(10,2);
    BEGIN
        v_monto_iva := p_monto * 0.13;

        RETURN v_monto_iva;
    END fn_calcular_impuesto;


    -- 4. FN_CALCULAR_DESCUENTO_CUPON
    FUNCTION fn_calcular_descuento_cupon(
        p_codigo IN VARCHAR2,
        p_monto  IN NUMBER
    ) RETURN NUMBER AS
        v_porcentaje_desc cupon.descuento%TYPE;
        v_fecha_limite    cupon.fecha_vence%TYPE;
        v_esta_activo     cupon.activo%TYPE;
        v_monto_final     NUMBER(10,2);
    BEGIN
        SELECT descuento, fecha_vence, activo
        INTO v_porcentaje_desc, v_fecha_limite, v_esta_activo
        FROM cupon
        WHERE codigo = p_codigo;

        IF v_esta_activo = 1 AND v_fecha_limite >= TRUNC(SYSDATE) THEN
            v_monto_final := p_monto * v_porcentaje_desc / 100;
        ELSE
            v_monto_final := 0;
        END IF;

        RETURN v_monto_final;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_calcular_descuento_cupon;


    -- 5. FN_CALCULAR_DESCUENTO_PROMOCION
    FUNCTION fn_calcular_descuento_promocion(
        p_promocion IN NUMBER,
        p_monto     IN NUMBER
    ) RETURN NUMBER AS
        v_porcentaje_desc promocion.porcentaje_descuento%TYPE;
        v_fecha_inicio    promocion.fecha_inicio%TYPE;
        v_fecha_final     promocion.fecha_fin%TYPE;
        v_esta_activo     promocion.activo%TYPE;
        v_monto_final     NUMBER(10,2);
    BEGIN
        SELECT porcentaje_descuento, fecha_inicio, fecha_fin, activo
        INTO v_porcentaje_desc, v_fecha_inicio, v_fecha_final, v_esta_activo
        FROM promocion
        WHERE id_promocion = p_promocion;

        IF v_esta_activo = 1
           AND TRUNC(SYSDATE) BETWEEN v_fecha_inicio AND v_fecha_final THEN
            v_monto_final := p_monto * v_porcentaje_desc / 100;
        ELSE
            v_monto_final := 0;
        END IF;

        RETURN v_monto_final;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_calcular_descuento_promocion;


    -- 6. FN_CALCULAR_COSTO_ENVIO
    FUNCTION fn_calcular_costo_envio(
        p_zona IN NUMBER
    ) RETURN NUMBER AS
        v_costo_final     zona_cobertura.costo_envio%TYPE;
        v_esta_disponible zona_cobertura.disponible%TYPE;
    BEGIN
        SELECT costo_envio, disponible
        INTO v_costo_final, v_esta_disponible
        FROM zona_cobertura
        WHERE id_zona = p_zona;

        IF v_esta_disponible = 0 THEN
            RETURN -1;
        END IF;

        RETURN v_costo_final;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN -1;
    END fn_calcular_costo_envio;


    -- 7. FN_CALCULAR_PRECIO_FINAL
    FUNCTION fn_calcular_precio_final(
        p_producto  IN NUMBER,
        p_promocion IN NUMBER DEFAULT NULL
    ) RETURN NUMBER AS
        v_precio_base  producto.precio%TYPE;
        v_precio_final NUMBER(10,2);
        v_monto_rebaja NUMBER(10,2) := 0;
    BEGIN
        SELECT precio
        INTO v_precio_base
        FROM producto
        WHERE id_producto = p_producto;

        IF p_promocion IS NOT NULL THEN
            v_monto_rebaja := fn_calcular_descuento_promocion(p_promocion, v_precio_base);
        END IF;

        v_precio_final := v_precio_base - v_monto_rebaja;

        RETURN v_precio_final;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_calcular_precio_final;


    -- 8. FN_VERIFICAR_STOCK_DISPONIBLE
    FUNCTION fn_verificar_stock_disponible(
        p_producto IN NUMBER,
        p_cantidad IN NUMBER
    ) RETURN NUMBER AS
        v_stock_actual producto.stock%TYPE;
        v_hay_stock     NUMBER(1);
    BEGIN
        SELECT stock
        INTO v_stock_actual
        FROM producto
        WHERE id_producto = p_producto;

        IF v_stock_actual >= p_cantidad THEN
            v_hay_stock := 1;
        ELSE
            v_hay_stock := 0;
        END IF;

        RETURN v_hay_stock;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_verificar_stock_disponible;


    -- 9. FN_VALIDAR_CUPON_VIGENTE
    FUNCTION fn_validar_cupon_vigente(
        p_codigo IN VARCHAR2
    ) RETURN NUMBER AS
        v_esta_activo  cupon.activo%TYPE;
        v_fecha_limite cupon.fecha_vence%TYPE;
        v_es_valido    NUMBER(1);
    BEGIN
        SELECT activo, fecha_vence
        INTO v_esta_activo, v_fecha_limite
        FROM cupon
        WHERE codigo = p_codigo;

        IF v_esta_activo = 1 AND v_fecha_limite >= TRUNC(SYSDATE) THEN
            v_es_valido := 1;
        ELSE
            v_es_valido := 0;
        END IF;

        RETURN v_es_valido;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_validar_cupon_vigente;


    -- 10. FN_VALIDAR_PROMOCION_VIGENTE
    FUNCTION fn_validar_promocion_vigente(
        p_promocion IN NUMBER
    ) RETURN NUMBER AS
        v_esta_activo  promocion.activo%TYPE;
        v_fecha_inicio promocion.fecha_inicio%TYPE;
        v_fecha_final  promocion.fecha_fin%TYPE;
        v_es_valida    NUMBER(1);
    BEGIN
        SELECT activo, fecha_inicio, fecha_fin
        INTO v_esta_activo, v_fecha_inicio, v_fecha_final
        FROM promocion
        WHERE id_promocion = p_promocion;

        IF v_esta_activo = 1
           AND TRUNC(SYSDATE) BETWEEN v_fecha_inicio AND v_fecha_final THEN
            v_es_valida := 1;
        ELSE
            v_es_valida := 0;
        END IF;

        RETURN v_es_valida;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_validar_promocion_vigente;


    -- 11. FN_VERIFICAR_USUARIO_ACTIVO
    FUNCTION fn_verificar_usuario_activo(
        p_usuario IN NUMBER
    ) RETURN NUMBER AS
        v_esta_activo usuario.activo%TYPE;
        v_es_valido   NUMBER(1);
    BEGIN
        SELECT activo
        INTO v_esta_activo
        FROM usuario
        WHERE id_usuario = p_usuario;

        IF v_esta_activo = 1 THEN
            v_es_valido := 1;
        ELSE
            v_es_valido := 0;
        END IF;

        RETURN v_es_valido;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_verificar_usuario_activo;


    -- 12. FN_VERIFICAR_CORREO_EXISTE
    FUNCTION fn_verificar_correo_existe(
        p_correo IN VARCHAR2
    ) RETURN NUMBER AS
        v_total_veces NUMBER;
        v_ya_existe   NUMBER(1);
    BEGIN
        SELECT COUNT(*)
        INTO v_total_veces
        FROM usuario
        WHERE correo = p_correo;

        IF v_total_veces > 0 THEN
            v_ya_existe := 1;
        ELSE
            v_ya_existe := 0;
        END IF;

        RETURN v_ya_existe;
    END fn_verificar_correo_existe;


    -- 13. FN_VERIFICAR_PRODUCTO_ACTIVO
    FUNCTION fn_verificar_producto_activo(
        p_producto IN NUMBER
    ) RETURN NUMBER AS
        v_esta_activo producto.activo%TYPE;
        v_es_valido   NUMBER(1);
    BEGIN
        SELECT activo
        INTO v_esta_activo
        FROM producto
        WHERE id_producto = p_producto;

        IF v_esta_activo = 1 THEN
            v_es_valido := 1;
        ELSE
            v_es_valido := 0;
        END IF;

        RETURN v_es_valido;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END fn_verificar_producto_activo;


    -- 14. FN_OBTENER_STOCK_ACTUAL
    FUNCTION fn_obtener_stock_actual(
        p_producto IN NUMBER
    ) RETURN NUMBER AS
        v_stock_actual producto.stock%TYPE;
    BEGIN
        SELECT stock
        INTO v_stock_actual
        FROM producto
        WHERE id_producto = p_producto;

        RETURN v_stock_actual;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN -1;
    END fn_obtener_stock_actual;


    -- 15. FN_DIAS_PARA_VENCER_LOTE
    FUNCTION fn_dias_para_vencer_lote(
        p_lote IN NUMBER
    ) RETURN NUMBER AS
        v_fecha_vence    lote.fecha_vencimiento%TYPE;
        v_dias_restantes NUMBER;
    BEGIN
        SELECT fecha_vencimiento
        INTO v_fecha_vence
        FROM lote
        WHERE id_lote = p_lote;

        v_dias_restantes := TRUNC(v_fecha_vence) - TRUNC(SYSDATE);

        RETURN v_dias_restantes;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN NULL;
    END fn_dias_para_vencer_lote;

END pkg_calculos_negocio;
/


-- PRUEBAS (ejecutar después de tener datos con los inserts)
-- SELECT pkg_calculos_negocio.fn_calcular_subtotal(1, 3) FROM dual;
-- SELECT pkg_calculos_negocio.fn_calcular_total_pedido(1) FROM dual;
-- SELECT pkg_calculos_negocio.fn_calcular_impuesto(1000) FROM dual;
-- SELECT pkg_calculos_negocio.fn_calcular_descuento_cupon('CUP001', 5000) FROM dual;
-- SELECT pkg_calculos_negocio.fn_calcular_descuento_promocion(1, 5000) FROM dual;
-- SELECT pkg_calculos_negocio.fn_calcular_costo_envio(1) FROM dual;
-- SELECT pkg_calculos_negocio.fn_calcular_precio_final(1, 1) FROM dual;
-- SELECT pkg_calculos_negocio.fn_verificar_stock_disponible(1, 2) FROM dual;
-- SELECT pkg_calculos_negocio.fn_validar_cupon_vigente('CUP001') FROM dual;
-- SELECT pkg_calculos_negocio.fn_validar_promocion_vigente(1) FROM dual;
-- SELECT pkg_calculos_negocio.fn_verificar_usuario_activo(1) FROM dual;
-- SELECT pkg_calculos_negocio.fn_verificar_correo_existe('admin@danny.com') FROM dual;
-- SELECT pkg_calculos_negocio.fn_verificar_producto_activo(1) FROM dual;
-- SELECT pkg_calculos_negocio.fn_obtener_stock_actual(1) FROM dual;
-- SELECT pkg_calculos_negocio.fn_dias_para_vencer_lote(1) FROM dual;
