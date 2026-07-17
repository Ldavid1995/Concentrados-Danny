--1. FN_STOCK_DISPONIBLE
CREATE OR REPLACE FUNCTION fn_stock_disponible(p_id_producto NUMBER)
RETURN NUMBER
IS
    v_stock producto.stock%TYPE;
BEGIN
    SELECT stock INTO v_stock
    FROM producto
    WHERE id_producto = p_id_producto;

    RETURN v_stock;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END;
/

--2. FN_PRECIO_ACTUAL
CREATE OR REPLACE FUNCTION fn_precio_actual(p_id_producto NUMBER)
RETURN NUMBER
IS
    v_precio producto.precio%TYPE;
BEGIN
    SELECT precio INTO v_precio
    FROM producto
    WHERE id_producto = p_id_producto;

    RETURN v_precio;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END;
/

--3. FN_TOTAL_PEDIDO
CREATE OR REPLACE FUNCTION fn_total_pedido(p_id_pedido NUMBER)
RETURN NUMBER
IS
    v_total NUMBER;
BEGIN
    SELECT NVL(SUM(subtotal), 0) INTO v_total
    FROM detalle_pedido
    WHERE id_pedido = p_id_pedido;

    RETURN v_total;
END;
/

--4. FN_NOMBRE_COMPLETO_USUARIO
CREATE OR REPLACE FUNCTION fn_nombre_completo_usuario(p_id_usuario NUMBER)
RETURN VARCHAR2
IS
    v_nombre_completo VARCHAR2(101);
BEGIN
    SELECT nombre || ' ' || apellidos INTO v_nombre_completo
    FROM usuario
    WHERE id_usuario = p_id_usuario;

    RETURN v_nombre_completo;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END;
/

--5. FN_PRODUCTO_CRITICO
CREATE OR REPLACE FUNCTION fn_producto_critico(p_id_producto NUMBER)
RETURN VARCHAR2
IS
    v_stock producto.stock%TYPE;
    v_min   producto.stock_minimo%TYPE;
BEGIN
    SELECT stock, stock_minimo INTO v_stock, v_min
    FROM producto
    WHERE id_producto = p_id_producto;

    IF v_stock <= v_min THEN
        RETURN 'S';
    ELSE
        RETURN 'N';
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END;
/

--6. FN_CANTIDAD_PEDIDOS_USUARIO
CREATE OR REPLACE FUNCTION fn_cantidad_pedidos_usuario(p_id_usuario NUMBER)
RETURN NUMBER
IS
    v_total NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_total
    FROM pedido
    WHERE id_usuario = p_id_usuario;

    RETURN v_total;
END;
/

--7. FN_CALIFICACION_PROMEDIO_PRODUCTO
CREATE OR REPLACE FUNCTION fn_calificacion_promedio_producto(p_id_producto NUMBER)
RETURN NUMBER
IS
    v_promedio NUMBER;
BEGIN
    SELECT ROUND(AVG(calificacion), 2) INTO v_promedio
    FROM resena
    WHERE id_producto = p_id_producto;

    RETURN NVL(v_promedio, 0);
END;
/

--8. FN_DIAS_PARA_VENCER_LOTE
CREATE OR REPLACE FUNCTION fn_dias_para_vencer_lote(p_id_lote NUMBER)
RETURN NUMBER
IS
    v_fecha_vencimiento lote.fecha_vencimiento%TYPE;
BEGIN
    SELECT fecha_vencimiento INTO v_fecha_vencimiento
    FROM lote
    WHERE id_lote = p_id_lote;

    RETURN TRUNC(v_fecha_vencimiento) - TRUNC(SYSDATE);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END;
/

--9. FN_CUPON_VALIDO
CREATE OR REPLACE FUNCTION fn_cupon_valido(p_codigo VARCHAR2)
RETURN VARCHAR2
IS
    v_activo      cupon.activo%TYPE;
    v_fecha_vence cupon.fecha_vence%TYPE;
BEGIN
    SELECT activo, fecha_vence INTO v_activo, v_fecha_vence
    FROM cupon
    WHERE codigo = p_codigo;

    IF v_activo = 1 AND v_fecha_vence >= TRUNC(SYSDATE) THEN
        RETURN 'S';
    ELSE
        RETURN 'N';
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'N';
END;
/

--10. FN_COSTO_ENVIO_ZONA
CREATE OR REPLACE FUNCTION fn_costo_envio_zona(p_id_zona NUMBER)
RETURN NUMBER
IS
    v_costo zona_cobertura.costo_envio%TYPE;
BEGIN
    SELECT costo_envio INTO v_costo
    FROM zona_cobertura
    WHERE id_zona = p_id_zona
      AND disponible = 1;

    RETURN v_costo;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN NULL;
END;
/

--11. FN_APLICAR_DESCUENTO
CREATE OR REPLACE FUNCTION fn_aplicar_descuento(
    p_precio NUMBER,
    p_porcentaje_descuento NUMBER
)
RETURN NUMBER
IS
BEGIN
    IF p_porcentaje_descuento IS NULL OR p_porcentaje_descuento = 0 THEN
        RETURN p_precio;
    END IF;

    RETURN ROUND(p_precio - (p_precio * p_porcentaje_descuento / 100), 2);
END;
/

--12. FN_SALDO_PENDIENTE_USUARIO
CREATE OR REPLACE FUNCTION fn_saldo_pendiente_usuario(p_id_usuario NUMBER)
RETURN NUMBER
IS
    v_saldo NUMBER;
BEGIN
    SELECT NVL(SUM(saldo_actual), 0) INTO v_saldo
    FROM estado_cuenta
    WHERE id_usuario = p_id_usuario;

    RETURN v_saldo;
END;
/

--13. FN_PRODUCTO_TIENE_PROMOCION
CREATE OR REPLACE FUNCTION fn_producto_tiene_promocion(p_id_producto NUMBER)
RETURN VARCHAR2
IS
    v_existe NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_existe
    FROM producto_promocion pp
    JOIN promocion pr ON pr.id_promocion = pp.id_promocion
    WHERE pp.id_producto = p_id_producto
      AND pr.activo = 1
      AND TRUNC(SYSDATE) BETWEEN pr.fecha_inicio AND pr.fecha_fin;

    IF v_existe > 0 THEN
        RETURN 'S';
    ELSE
        RETURN 'N';
    END IF;
END;
/

--14. FN_TOTAL_UNIDADES_VENDIDAS
CREATE OR REPLACE FUNCTION fn_total_unidades_vendidas(p_id_producto NUMBER)
RETURN NUMBER
IS
    v_total NUMBER;
BEGIN
    SELECT NVL(SUM(cantidad), 0) INTO v_total
    FROM detalle_pedido
    WHERE id_producto = p_id_producto;

    RETURN v_total;
END;
/

--15. FN_EDAD_CUENTA_USUARIO
CREATE OR REPLACE FUNCTION fn_edad_cuenta_usuario(p_id_usuario NUMBER)
RETURN NUMBER
IS
    v_fecha_primer_pedido pedido.fecha_pedido%TYPE;
BEGIN
    SELECT MIN(fecha_pedido) INTO v_fecha_primer_pedido
    FROM pedido
    WHERE id_usuario = p_id_usuario;

    IF v_fecha_primer_pedido IS NULL THEN
        RETURN NULL;
    END IF;

    RETURN TRUNC(SYSDATE) - TRUNC(v_fecha_primer_pedido);
END;
/