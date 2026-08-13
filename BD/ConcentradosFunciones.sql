 /*==============================================================
                    GRUPO 1: CALCULO DE IMPORTES
==============================================================*/

-- 1. FN_CALCULAR_SUBTOTAL
-- Calcula el subtotal de una línea (precio del producto x cantidad)

CREATE OR REPLACE FUNCTION fn_calcular_subtotal(
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
END;
/

-- 2. FN_CALCULAR_TOTAL_PEDIDO
-- Suma todos los subtotales de un pedido

CREATE OR REPLACE FUNCTION fn_calcular_total_pedido(
    p_pedido IN NUMBER
) RETURN NUMBER AS
    v_suma_total NUMBER(10,2);
BEGIN
    SELECT NVL(SUM(subtotal), 0)
    INTO v_suma_total
    FROM detalle_pedido
    WHERE id_pedido = p_pedido;

    RETURN v_suma_total;
END;
/

-- 3. FN_CALCULAR_IMPUESTO
-- Calcula el IVA (13%) sobre un monto

CREATE OR REPLACE FUNCTION fn_calcular_impuesto(
    p_monto IN NUMBER
) RETURN NUMBER AS
    v_monto_iva NUMBER(10,2);
BEGIN
    v_monto_iva := p_monto * 0.13;

    RETURN v_monto_iva;
END;
/

-- 4. FN_CALCULAR_DESCUENTO_CUPON
-- Calcula el monto de descuento si el cupón está vigente

CREATE OR REPLACE FUNCTION fn_calcular_descuento_cupon(
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
END;
/

-- 5. FN_CALCULAR_DESCUENTO_PROMOCION
-- Calcula el monto de descuento si la promoción está vigente

CREATE OR REPLACE FUNCTION fn_calcular_descuento_promocion(
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
END;
/

-- 6. FN_CALCULAR_COSTO_ENVIO
-- Devuelve el costo de envío de una zona (-1 si no está disponible)

CREATE OR REPLACE FUNCTION fn_calcular_costo_envio(
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
END;
/

-- 7. FN_CALCULAR_PRECIO_FINAL
-- Precio de un producto aplicando el descuento de una promoción (si tiene)

CREATE OR REPLACE FUNCTION fn_calcular_precio_final(
    p_producto  IN NUMBER,
    p_promocion IN NUMBER DEFAULT NULL
) RETURN NUMBER AS
    v_precio_base producto.precio%TYPE;
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
END;
/

/*==============================================================
                GRUPO 2: VALIDACIONES DE NEGOCIO
     (retornan 1 = válido/cumple, 0 = inválido/no cumple)
==============================================================*/

-- 8. FN_VERIFICAR_STOCK_DISPONIBLE
-- Verifica si hay suficiente stock para una cantidad solicitada

CREATE OR REPLACE FUNCTION fn_verificar_stock_disponible(
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
END;
/

-- 9. FN_VALIDAR_CUPON_VIGENTE
-- Valida que un cupón exista, esté activo y no haya vencido

CREATE OR REPLACE FUNCTION fn_validar_cupon_vigente(
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
END;
/

-- 10. FN_VALIDAR_PROMOCION_VIGENTE
-- Valida que una promoción exista, esté activa y dentro de su rango de fechas

CREATE OR REPLACE FUNCTION fn_validar_promocion_vigente(
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
END;
/

-- 11. FN_VERIFICAR_USUARIO_ACTIVO
-- Verifica si un usuario está activo en el sistema

CREATE OR REPLACE FUNCTION fn_verificar_usuario_activo(
    p_usuario IN NUMBER
) RETURN NUMBER AS
    v_esta_activo  usuario.activo%TYPE;
    v_es_valido    NUMBER(1);
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
END;
/

-- 12. FN_VERIFICAR_CORREO_EXISTE
-- Verifica si un correo ya está registrado (útil antes de crear usuario)

CREATE OR REPLACE FUNCTION fn_verificar_correo_existe(
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
END;
/

/*==============================================================
              GRUPO 3: VERIFICACION DE DISPONIBILIDAD
==============================================================*/

-- 13. FN_VERIFICAR_PRODUCTO_ACTIVO
-- Verifica si un producto está activo (disponible para la venta)

CREATE OR REPLACE FUNCTION fn_verificar_producto_activo(
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
END;
/

-- 14. FN_OBTENER_STOCK_ACTUAL
-- Devuelve el stock actual de un producto (-1 si no existe)

CREATE OR REPLACE FUNCTION fn_obtener_stock_actual(
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
END;
/

-- 15. FN_DIAS_PARA_VENCER_LOTE
-- Calcula cuántos días faltan para que un lote venza

CREATE OR REPLACE FUNCTION fn_dias_para_vencer_lote(
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
END;
/


-- PRUEBAS (ejecutar después de tener datos con los inserts)
-- SELECT fn_calcular_subtotal(1, 3) FROM dual;
-- SELECT fn_calcular_total_pedido(1) FROM dual;
-- SELECT fn_calcular_impuesto(1000) FROM dual;
-- SELECT fn_calcular_descuento_cupon('CUP001', 5000) FROM dual;
-- SELECT fn_calcular_descuento_promocion(1, 5000) FROM dual;
-- SELECT fn_calcular_costo_envio(1) FROM dual;
-- SELECT fn_calcular_precio_final(1, 1) FROM dual;
-- SELECT fn_verificar_stock_disponible(1, 2) FROM dual;
-- SELECT fn_validar_cupon_vigente('CUP001') FROM dual;
-- SELECT fn_validar_promocion_vigente(1) FROM dual;
-- SELECT fn_verificar_usuario_activo(1) FROM dual;
-- SELECT fn_verificar_correo_existe('admin@danny.com') FROM dual;
-- SELECT fn_verificar_producto_activo(1) FROM dual;
-- SELECT fn_obtener_stock_actual(1) FROM dual;
-- SELECT fn_dias_para_vencer_lote(1) FROM dual;
