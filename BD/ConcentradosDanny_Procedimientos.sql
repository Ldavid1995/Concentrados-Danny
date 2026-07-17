--------* PROCEDIMIENTOS *------------------

-- 1. SP_CREAR_PEDIDO

CREATE OR REPLACE PROCEDURE sp_crear_pedido(
    p_id_usuario IN NUMBER,
    p_id_direccion IN NUMBER,
    p_id_horario IN NUMBER,
    p_id_zona IN NUMBER,
    p_id_cupon IN NUMBER,
    p_fecha_entrega IN DATE,
    p_hora_entrega IN VARCHAR2,
    p_id_pedido OUT NUMBER
) AS
BEGIN
    INSERT INTO pedido
    (id_usuario, id_direccion, id_horario, id_zona, id_cupon,
     fecha_entrega, hora_entrega, estado, total)
    VALUES
    (p_id_usuario, p_id_direccion, p_id_horario, p_id_zona, p_id_cupon,
     p_fecha_entrega, p_hora_entrega, 'PENDIENTE', 0)
    RETURNING id_pedido INTO p_id_pedido;
END;
/

-- 2. SP_PROCESAR_PAGO

CREATE OR REPLACE PROCEDURE sp_procesar_pago(
    p_id_pedido IN NUMBER,
    p_metodo_pago IN VARCHAR2,
    p_monto IN NUMBER,
    p_referencia IN VARCHAR2,
    p_autorizacion IN VARCHAR2,
    p_numero_transaccion IN VARCHAR2,
    p_estado OUT VARCHAR2
) AS
BEGIN
    IF p_monto > 0 THEN
        p_estado := 'APROBADO';
    ELSE
        p_estado := 'RECHAZADO';
    END IF;

    INSERT INTO pago
    (id_pedido, metodo_pago, monto, estado_pago,
     referencia_bancaria, autorizacion, numero_transaccion)
    VALUES
    (p_id_pedido, p_metodo_pago, p_monto, p_estado,
     p_referencia, p_autorizacion, p_numero_transaccion);
END;
/

-- 3. SP_CREAR_USUARIO

CREATE OR REPLACE PROCEDURE sp_crear_usuario(
    p_id_rol IN NUMBER,
    p_username IN VARCHAR2,
    p_password IN VARCHAR2,
    p_nombre IN VARCHAR2,
    p_apellidos IN VARCHAR2,
    p_correo IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_id_usuario OUT NUMBER
) AS
BEGIN
    INSERT INTO usuario
    (id_rol, username, password, nombre, apellidos, correo, telefono, activo)
    VALUES
    (p_id_rol, p_username, p_password, p_nombre, p_apellidos,
     p_correo, p_telefono, 1)
    RETURNING id_usuario INTO p_id_usuario;
END;
/

-- 4. SP_ACTUALIZAR_USUARIO

CREATE OR REPLACE PROCEDURE sp_actualizar_usuario(
    p_id_usuario IN NUMBER,
    p_id_rol IN NUMBER,
    p_nombre IN VARCHAR2,
    p_apellidos IN VARCHAR2,
    p_correo IN VARCHAR2,
    p_telefono IN VARCHAR2
) AS
BEGIN
    UPDATE usuario
    SET id_rol = p_id_rol,
        nombre = p_nombre,
        apellidos = p_apellidos,
        correo = p_correo,
        telefono = p_telefono
    WHERE id_usuario = p_id_usuario;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20010, 'El usuario no existe.');
    END IF;
END;
/

-- 5. SP_DESACTIVAR_USUARIO

CREATE OR REPLACE PROCEDURE sp_desactivar_usuario(
    p_id_usuario IN NUMBER
) AS
BEGIN
    UPDATE usuario
    SET activo = 0
    WHERE id_usuario = p_id_usuario;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20011, 'El usuario no existe.');
    END IF;
END;
/

-- 6. SP_CREAR_PRODUCTO

CREATE OR REPLACE PROCEDURE sp_crear_producto(
    p_id_categoria IN NUMBER,
    p_nombre IN VARCHAR2,
    p_marca IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_unidad_medida IN VARCHAR2,
    p_precio IN NUMBER,
    p_stock IN NUMBER,
    p_stock_minimo IN NUMBER,
    p_id_producto OUT NUMBER
) AS
BEGIN
    INSERT INTO producto
    (id_categoria, nombre, marca, descripcion, unidad_medida,
     precio, stock, stock_minimo, activo)
    VALUES
    (p_id_categoria, p_nombre, p_marca, p_descripcion,
     p_unidad_medida, p_precio, p_stock, p_stock_minimo, 1)
    RETURNING id_producto INTO p_id_producto;
END;
/

-- 7. SP_ACTUALIZAR_PRODUCTO

CREATE OR REPLACE PROCEDURE sp_actualizar_producto(
    p_id_producto IN NUMBER,
    p_id_categoria IN NUMBER,
    p_nombre IN VARCHAR2,
    p_marca IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_unidad_medida IN VARCHAR2,
    p_precio IN NUMBER,
    p_stock IN NUMBER,
    p_stock_minimo IN NUMBER
) AS
BEGIN
    UPDATE producto
    SET id_categoria = p_id_categoria,
        nombre = p_nombre,
        marca = p_marca,
        descripcion = p_descripcion,
        unidad_medida = p_unidad_medida,
        precio = p_precio,
        stock = p_stock,
        stock_minimo = p_stock_minimo
    WHERE id_producto = p_id_producto;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20012, 'El producto no existe.');
    END IF;
END;
/

-- 8. SP_DESACTIVAR_PRODUCTO

CREATE OR REPLACE PROCEDURE sp_desactivar_producto(
    p_id_producto IN NUMBER
) AS
BEGIN
    UPDATE producto
    SET activo = 0
    WHERE id_producto = p_id_producto;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20013, 'El producto no existe.');
    END IF;
END;
/

-- 9. SP_AGREGAR_DETALLE_PEDIDO
-- Los triggers descuentan el stock y recalculan el total del pedido.

CREATE OR REPLACE PROCEDURE sp_agregar_detalle_pedido(
    p_id_pedido IN NUMBER,
    p_id_producto IN NUMBER,
    p_id_promocion IN NUMBER,
    p_cantidad IN NUMBER,
    p_precio_unitario IN NUMBER,
    p_descuento IN NUMBER,
    p_id_detalle OUT NUMBER
) AS
    v_subtotal NUMBER(10,2);
BEGIN
    IF p_cantidad <= 0 THEN
        RAISE_APPLICATION_ERROR(-20014, 'La cantidad debe ser mayor que cero.');
    END IF;

    v_subtotal := (p_cantidad * p_precio_unitario) - NVL(p_descuento, 0);

    IF v_subtotal < 0 THEN
        RAISE_APPLICATION_ERROR(-20015, 'El descuento supera el valor del detalle.');
    END IF;

    INSERT INTO detalle_pedido
    (id_pedido, id_producto, id_promocion, cantidad,
     precio_unitario, descuento, subtotal)
    VALUES
    (p_id_pedido, p_id_producto, p_id_promocion, p_cantidad,
     p_precio_unitario, NVL(p_descuento, 0), v_subtotal)
    RETURNING id_detalle INTO p_id_detalle;
END;
/

-- 10. SP_ACTUALIZAR_ESTADO_PEDIDO

CREATE OR REPLACE PROCEDURE sp_actualizar_estado_pedido(
    p_id_pedido IN NUMBER,
    p_estado IN VARCHAR2
) AS
BEGIN
    IF UPPER(p_estado) NOT IN
       ('PENDIENTE', 'PAGADO', 'EN_PROCESO', 'ENTREGADO', 'CANCELADO') THEN
        RAISE_APPLICATION_ERROR(-20016, 'Estado de pedido no válido.');
    END IF;

    UPDATE pedido
    SET estado = UPPER(p_estado)
    WHERE id_pedido = p_id_pedido;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20017, 'El pedido no existe.');
    END IF;
END;
/

-- 11. SP_AGREGAR_LISTA_DESEOS

CREATE OR REPLACE PROCEDURE sp_agregar_lista_deseos(
    p_id_usuario IN NUMBER,
    p_id_producto IN NUMBER,
    p_id_lista OUT NUMBER
) AS
BEGIN
    INSERT INTO lista_deseos
    (id_usuario, id_producto)
    VALUES
    (p_id_usuario, p_id_producto)
    RETURNING id_lista INTO p_id_lista;
END;
/

-- 12. SP_ELIMINAR_LISTA_DESEOS

CREATE OR REPLACE PROCEDURE sp_eliminar_lista_deseos(
    p_id_usuario IN NUMBER,
    p_id_producto IN NUMBER
) AS
BEGIN
    DELETE FROM lista_deseos
    WHERE id_usuario = p_id_usuario
      AND id_producto = p_id_producto;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20018,
            'El producto no está en la lista de deseos del usuario.'
        );
    END IF;
END;
/