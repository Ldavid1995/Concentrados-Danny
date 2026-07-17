CREATE OR REPLACE PACKAGE pkg_productos AS

    PROCEDURE crear_producto(
        p_id_categoria IN NUMBER,
        p_nombre IN VARCHAR2,
        p_marca IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_unidad_medida IN VARCHAR2,
        p_precio IN NUMBER,
        p_stock IN NUMBER,
        p_stock_minimo IN NUMBER,
        p_id_producto OUT NUMBER
    );

    PROCEDURE actualizar_producto(
        p_id_producto IN NUMBER,
        p_id_categoria IN NUMBER,
        p_nombre IN VARCHAR2,
        p_marca IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_unidad_medida IN VARCHAR2,
        p_precio IN NUMBER,
        p_stock IN NUMBER,
        p_stock_minimo IN NUMBER
    );

    PROCEDURE desactivar_producto(
        p_id_producto IN NUMBER
    );

    FUNCTION obtener_precio(
        p_id_producto IN NUMBER
    ) RETURN NUMBER;

END pkg_productos;
/

CREATE OR REPLACE PACKAGE BODY pkg_productos AS

    PROCEDURE crear_producto(
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
        INSERT INTO producto(
            id_categoria,nombre,marca,descripcion,unidad_medida,
            precio,stock,stock_minimo,activo
        )
        VALUES(
            p_id_categoria,p_nombre,p_marca,p_descripcion,p_unidad_medida,
            p_precio,p_stock,p_stock_minimo,1
        )
        RETURNING id_producto INTO p_id_producto;
    END crear_producto;

    PROCEDURE actualizar_producto(
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
        SET id_categoria=p_id_categoria,
            nombre=p_nombre,
            marca=p_marca,
            descripcion=p_descripcion,
            unidad_medida=p_unidad_medida,
            precio=p_precio,
            stock=p_stock,
            stock_minimo=p_stock_minimo
        WHERE id_producto=p_id_producto;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20020,'El producto no existe.');
        END IF;
    END actualizar_producto;

    PROCEDURE desactivar_producto(
        p_id_producto IN NUMBER
    ) AS
    BEGIN
        UPDATE producto
        SET activo=0
        WHERE id_producto=p_id_producto;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20021,'El producto no existe.');
        END IF;
    END desactivar_producto;

    FUNCTION obtener_precio(
        p_id_producto IN NUMBER
    ) RETURN NUMBER AS
        v_precio NUMBER;
    BEGIN
        SELECT precio
        INTO v_precio
        FROM producto
        WHERE id_producto=p_id_producto;

        RETURN v_precio;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END obtener_precio;

END pkg_productos;
/

/*==============================================================
                    PAQUETE USUARIOS
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_usuarios AS

    FUNCTION verificar_usuario(
        p_username IN VARCHAR2,
        p_password IN VARCHAR2
    ) RETURN NUMBER;

    PROCEDURE generar_token(
        p_id_usuario IN NUMBER,
        p_token OUT VARCHAR2
    );

END pkg_usuarios;
/

CREATE OR REPLACE PACKAGE BODY pkg_usuarios AS

    FUNCTION verificar_usuario(
        p_username IN VARCHAR2,
        p_password IN VARCHAR2
    ) RETURN NUMBER AS
        v_valido NUMBER;
    BEGIN

        SELECT COUNT(*)
        INTO v_valido
        FROM usuario
        WHERE username=p_username
          AND password=p_password
          AND activo=1;

        RETURN v_valido;

    END verificar_usuario;

    PROCEDURE generar_token(
        p_id_usuario IN NUMBER,
        p_token OUT VARCHAR2
    ) AS
    BEGIN

        p_token:=RAWTOHEX(SYS_GUID());

        INSERT INTO token_recuperacion(
            id_usuario,token,fecha_expiracion,usado
        )
        VALUES(
            p_id_usuario,p_token,SYSTIMESTAMP+INTERVAL '1' DAY,0
        );

    END generar_token;

END pkg_usuarios;
/

/*==============================================================
                    PAQUETE CATEGORÍAS
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_categorias AS

    PROCEDURE crear_categoria(
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_id_categoria OUT NUMBER
    );

    PROCEDURE actualizar_categoria(
        p_id_categoria IN NUMBER,
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2
    );

    PROCEDURE desactivar_categoria(
        p_id_categoria IN NUMBER
    );

    FUNCTION obtener_nombre_categoria(
        p_id_categoria IN NUMBER
    ) RETURN VARCHAR2;

END pkg_categorias;
/

CREATE OR REPLACE PACKAGE BODY pkg_categorias AS

    PROCEDURE crear_categoria(
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_id_categoria OUT NUMBER
    ) AS
    BEGIN
        INSERT INTO categoria(nombre_categoria,descripcion,activo)
        VALUES(p_nombre,p_descripcion,1)
        RETURNING id_categoria INTO p_id_categoria;
    END crear_categoria;


    PROCEDURE actualizar_categoria(
        p_id_categoria IN NUMBER,
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2
    ) AS
    BEGIN
        UPDATE categoria
        SET nombre_categoria=p_nombre,
            descripcion=p_descripcion
        WHERE id_categoria=p_id_categoria;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20030,'La categoría no existe.');
        END IF;
    END actualizar_categoria;


    PROCEDURE desactivar_categoria(
        p_id_categoria IN NUMBER
    ) AS
    BEGIN
        UPDATE categoria
        SET activo=0
        WHERE id_categoria=p_id_categoria;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20031,'La categoría no existe.');
        END IF;
    END desactivar_categoria;


    FUNCTION obtener_nombre_categoria(
        p_id_categoria IN NUMBER
    ) RETURN VARCHAR2 AS
        v_nombre VARCHAR2(100);
    BEGIN
        SELECT nombre_categoria
        INTO v_nombre
        FROM categoria
        WHERE id_categoria=p_id_categoria;

        RETURN v_nombre;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN NULL;
    END obtener_nombre_categoria;

END pkg_categorias;
/

/*==============================================================
                    PAQUETE DIRECCIONES
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_direcciones AS

    PROCEDURE crear_direccion(
        p_id_usuario IN NUMBER,
        p_direccion IN VARCHAR2,
        p_provincia IN VARCHAR2,
        p_canton IN VARCHAR2,
        p_distrito IN VARCHAR2,
        p_codigo_postal IN VARCHAR2,
        p_referencia IN VARCHAR2,
        p_id_direccion OUT NUMBER
    );

    PROCEDURE actualizar_direccion(
        p_id_direccion IN NUMBER,
        p_direccion IN VARCHAR2,
        p_provincia IN VARCHAR2,
        p_canton IN VARCHAR2,
        p_distrito IN VARCHAR2,
        p_codigo_postal IN VARCHAR2,
        p_referencia IN VARCHAR2
    );

    PROCEDURE desactivar_direccion(
        p_id_direccion IN NUMBER
    );

    FUNCTION contar_direcciones_usuario(
        p_id_usuario IN NUMBER
    ) RETURN NUMBER;

END pkg_direcciones;
/

CREATE OR REPLACE PACKAGE BODY pkg_direcciones AS

    PROCEDURE crear_direccion(
        p_id_usuario IN NUMBER,
        p_direccion IN VARCHAR2,
        p_provincia IN VARCHAR2,
        p_canton IN VARCHAR2,
        p_distrito IN VARCHAR2,
        p_codigo_postal IN VARCHAR2,
        p_referencia IN VARCHAR2,
        p_id_direccion OUT NUMBER
    ) AS
    BEGIN
        INSERT INTO direccion(id_usuario,direccion,provincia,canton,distrito,codigo_postal,referencia,activo)
        VALUES(p_id_usuario,p_direccion,p_provincia,p_canton,p_distrito,p_codigo_postal,p_referencia,1)
        RETURNING id_direccion INTO p_id_direccion;
    END crear_direccion;


    PROCEDURE actualizar_direccion(
        p_id_direccion IN NUMBER,
        p_direccion IN VARCHAR2,
        p_provincia IN VARCHAR2,
        p_canton IN VARCHAR2,
        p_distrito IN VARCHAR2,
        p_codigo_postal IN VARCHAR2,
        p_referencia IN VARCHAR2
    ) AS
    BEGIN
        UPDATE direccion
        SET direccion=p_direccion,
            provincia=p_provincia,
            canton=p_canton,
            distrito=p_distrito,
            codigo_postal=p_codigo_postal,
            referencia=p_referencia
        WHERE id_direccion=p_id_direccion;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20040,'La dirección no existe.');
        END IF;
    END actualizar_direccion;


    PROCEDURE desactivar_direccion(
        p_id_direccion IN NUMBER
    ) AS
    BEGIN
        UPDATE direccion
        SET activo=0
        WHERE id_direccion=p_id_direccion;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20041,'La dirección no existe.');
        END IF;
    END desactivar_direccion;


    FUNCTION contar_direcciones_usuario(
        p_id_usuario IN NUMBER
    ) RETURN NUMBER AS
        v_total NUMBER;
    BEGIN
        SELECT COUNT(*)
        INTO v_total
        FROM direccion
        WHERE id_usuario=p_id_usuario
          AND activo=1;

        RETURN v_total;
    END contar_direcciones_usuario;

END pkg_direcciones;
/

/*==============================================================
                    PAQUETE PEDIDOS
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_pedidos AS

    PROCEDURE crear_pedido(
        p_id_usuario IN NUMBER,
        p_id_direccion IN NUMBER,
        p_id_horario IN NUMBER,
        p_id_zona IN NUMBER,
        p_id_cupon IN NUMBER,
        p_fecha_entrega IN DATE,
        p_hora_entrega IN VARCHAR2,
        p_id_pedido OUT NUMBER
    );

    PROCEDURE agregar_detalle_pedido(
        p_id_pedido IN NUMBER,
        p_id_producto IN NUMBER,
        p_id_promocion IN NUMBER,
        p_cantidad IN NUMBER,
        p_precio_unitario IN NUMBER,
        p_descuento IN NUMBER
    );

    PROCEDURE actualizar_estado_pedido(
        p_id_pedido IN NUMBER,
        p_estado IN VARCHAR2
    );

    FUNCTION obtener_total_pedido(
        p_id_pedido IN NUMBER
    ) RETURN NUMBER;

END pkg_pedidos;
/

CREATE OR REPLACE PACKAGE BODY pkg_pedidos AS

    PROCEDURE crear_pedido(
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
        INSERT INTO pedido(id_usuario,id_direccion,id_horario,id_zona,id_cupon,fecha_entrega,hora_entrega,estado,total)
        VALUES(p_id_usuario,p_id_direccion,p_id_horario,p_id_zona,p_id_cupon,p_fecha_entrega,p_hora_entrega,'PENDIENTE',0)
        RETURNING id_pedido INTO p_id_pedido;
    END crear_pedido;


    PROCEDURE agregar_detalle_pedido(
        p_id_pedido IN NUMBER,
        p_id_producto IN NUMBER,
        p_id_promocion IN NUMBER,
        p_cantidad IN NUMBER,
        p_precio_unitario IN NUMBER,
        p_descuento IN NUMBER
    ) AS
        v_subtotal NUMBER;
    BEGIN
        v_subtotal:=(p_cantidad*p_precio_unitario)-NVL(p_descuento,0);

        INSERT INTO detalle_pedido(id_pedido,id_producto,id_promocion,cantidad,precio_unitario,descuento,subtotal)
        VALUES(p_id_pedido,p_id_producto,p_id_promocion,p_cantidad,p_precio_unitario,NVL(p_descuento,0),v_subtotal);
    END agregar_detalle_pedido;


    PROCEDURE actualizar_estado_pedido(
        p_id_pedido IN NUMBER,
        p_estado IN VARCHAR2
    ) AS
    BEGIN
        UPDATE pedido
        SET estado=UPPER(p_estado)
        WHERE id_pedido=p_id_pedido;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20050,'El pedido no existe.');
        END IF;
    END actualizar_estado_pedido;


    FUNCTION obtener_total_pedido(
        p_id_pedido IN NUMBER
    ) RETURN NUMBER AS
        v_total NUMBER;
    BEGIN
        SELECT total
        INTO v_total
        FROM pedido
        WHERE id_pedido=p_id_pedido;

        RETURN v_total;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END obtener_total_pedido;

END pkg_pedidos;
/

/*==============================================================
                    PAQUETE PAGOS
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_pagos AS

    PROCEDURE registrar_pago(
        p_id_pedido IN NUMBER,
        p_metodo_pago IN VARCHAR2,
        p_monto IN NUMBER,
        p_referencia_bancaria IN VARCHAR2,
        p_autorizacion IN VARCHAR2,
        p_numero_transaccion IN VARCHAR2,
        p_id_pago OUT NUMBER
    );

    PROCEDURE actualizar_estado_pago(
        p_id_pago IN NUMBER,
        p_estado_pago IN VARCHAR2
    );

    FUNCTION obtener_monto_pagado(
        p_id_pedido IN NUMBER
    ) RETURN NUMBER;

END pkg_pagos;
/

CREATE OR REPLACE PACKAGE BODY pkg_pagos AS

    PROCEDURE registrar_pago(
        p_id_pedido IN NUMBER,
        p_metodo_pago IN VARCHAR2,
        p_monto IN NUMBER,
        p_referencia_bancaria IN VARCHAR2,
        p_autorizacion IN VARCHAR2,
        p_numero_transaccion IN VARCHAR2,
        p_id_pago OUT NUMBER
    ) AS
    BEGIN
        IF p_monto<=0 THEN
            RAISE_APPLICATION_ERROR(-20060,'El monto debe ser mayor que cero.');
        END IF;

        INSERT INTO pago(id_pedido,metodo_pago,monto,estado_pago,referencia_bancaria,autorizacion,numero_transaccion)
        VALUES(p_id_pedido,p_metodo_pago,p_monto,'PENDIENTE',p_referencia_bancaria,p_autorizacion,p_numero_transaccion)
        RETURNING id_pago INTO p_id_pago;
    END registrar_pago;


    PROCEDURE actualizar_estado_pago(
        p_id_pago IN NUMBER,
        p_estado_pago IN VARCHAR2
    ) AS
    BEGIN
        UPDATE pago
        SET estado_pago=UPPER(p_estado_pago)
        WHERE id_pago=p_id_pago;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20061,'El pago no existe.');
        END IF;
    END actualizar_estado_pago;


    FUNCTION obtener_monto_pagado(
        p_id_pedido IN NUMBER
    ) RETURN NUMBER AS
        v_monto NUMBER;
    BEGIN
        SELECT NVL(SUM(monto),0)
        INTO v_monto
        FROM pago
        WHERE id_pedido=p_id_pedido
          AND estado_pago='APROBADO';

        RETURN v_monto;
    END obtener_monto_pagado;

END pkg_pagos;
/

/*==============================================================
                    PAQUETE FACTURAS
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_facturas AS

    PROCEDURE generar_factura(
        p_id_pedido IN NUMBER,
        p_numero_factura IN VARCHAR2,
        p_id_factura OUT NUMBER
    );

    PROCEDURE anular_factura(
        p_id_factura IN NUMBER
    );

    FUNCTION obtener_total_factura(
        p_id_pedido IN NUMBER
    ) RETURN NUMBER;

END pkg_facturas;
/

CREATE OR REPLACE PACKAGE BODY pkg_facturas AS

    PROCEDURE generar_factura(
        p_id_pedido IN NUMBER,
        p_numero_factura IN VARCHAR2,
        p_id_factura OUT NUMBER
    ) AS
        v_total NUMBER;
    BEGIN
        SELECT total INTO v_total
        FROM pedido
        WHERE id_pedido=p_id_pedido;

        INSERT INTO factura(id_pedido,numero_factura,total,estado)
        VALUES(p_id_pedido,p_numero_factura,v_total,'EMITIDA')
        RETURNING id_factura INTO p_id_factura;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20070,'El pedido no existe.');
    END generar_factura;


    PROCEDURE anular_factura(
        p_id_factura IN NUMBER
    ) AS
    BEGIN
        UPDATE factura
        SET estado='ANULADA'
        WHERE id_factura=p_id_factura;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20071,'La factura no existe.');
        END IF;
    END anular_factura;


    FUNCTION obtener_total_factura(
        p_id_pedido IN NUMBER
    ) RETURN NUMBER AS
        v_total NUMBER;
    BEGIN
        SELECT total INTO v_total
        FROM factura
        WHERE id_pedido=p_id_pedido
          AND estado<>'ANULADA';

        RETURN v_total;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN TOO_MANY_ROWS THEN
            SELECT NVL(SUM(total),0) INTO v_total
            FROM factura
            WHERE id_pedido=p_id_pedido
              AND estado<>'ANULADA';

            RETURN v_total;
    END obtener_total_factura;

END pkg_facturas;
/

/*==============================================================
                    PAQUETE PROMOCIONES
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_promociones AS

    PROCEDURE crear_promocion(
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_porcentaje IN NUMBER,
        p_fecha_inicio IN DATE,
        p_fecha_fin IN DATE,
        p_id_promocion OUT NUMBER
    );

    PROCEDURE asociar_producto_promocion(
        p_id_producto IN NUMBER,
        p_id_promocion IN NUMBER
    );

    PROCEDURE desactivar_promocion(
        p_id_promocion IN NUMBER
    );

    FUNCTION obtener_descuento_promocion(
        p_id_promocion IN NUMBER
    ) RETURN NUMBER;

END pkg_promociones;
/

CREATE OR REPLACE PACKAGE BODY pkg_promociones AS

    PROCEDURE crear_promocion(
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_porcentaje IN NUMBER,
        p_fecha_inicio IN DATE,
        p_fecha_fin IN DATE,
        p_id_promocion OUT NUMBER
    ) AS
    BEGIN
        IF p_porcentaje<0 OR p_porcentaje>100 THEN
            RAISE_APPLICATION_ERROR(-20080,'El porcentaje debe estar entre 0 y 100.');
        END IF;

        IF p_fecha_fin<p_fecha_inicio THEN
            RAISE_APPLICATION_ERROR(-20081,'La fecha final no puede ser menor que la inicial.');
        END IF;

        INSERT INTO promocion(nombre,descripcion,porcentaje_descuento,fecha_inicio,fecha_fin,activo)
        VALUES(p_nombre,p_descripcion,p_porcentaje,p_fecha_inicio,p_fecha_fin,1)
        RETURNING id_promocion INTO p_id_promocion;
    END crear_promocion;


    PROCEDURE asociar_producto_promocion(
        p_id_producto IN NUMBER,
        p_id_promocion IN NUMBER
    ) AS
    BEGIN
        INSERT INTO producto_promocion(id_producto,id_promocion)
        VALUES(p_id_producto,p_id_promocion);
    END asociar_producto_promocion;


    PROCEDURE desactivar_promocion(
        p_id_promocion IN NUMBER
    ) AS
    BEGIN
        UPDATE promocion
        SET activo=0
        WHERE id_promocion=p_id_promocion;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20082,'La promoción no existe.');
        END IF;
    END desactivar_promocion;


    FUNCTION obtener_descuento_promocion(
        p_id_promocion IN NUMBER
    ) RETURN NUMBER AS
        v_porcentaje NUMBER;
    BEGIN
        SELECT porcentaje_descuento INTO v_porcentaje
        FROM promocion
        WHERE id_promocion=p_id_promocion;

        RETURN v_porcentaje;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END obtener_descuento_promocion;

END pkg_promociones;
/

/*==============================================================
                    PAQUETE COTIZACIONES
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_cotizaciones AS

    PROCEDURE crear_cotizacion(
        p_id_usuario IN NUMBER,
        p_fecha_vencimiento IN DATE,
        p_id_cotizacion OUT NUMBER
    );

    PROCEDURE agregar_detalle_cotizacion(
        p_id_cotizacion IN NUMBER,
        p_id_producto IN NUMBER,
        p_cantidad IN NUMBER,
        p_precio_unitario IN NUMBER
    );

    PROCEDURE actualizar_estado_cotizacion(
        p_id_cotizacion IN NUMBER,
        p_estado IN VARCHAR2
    );

    FUNCTION obtener_total_cotizacion(
        p_id_cotizacion IN NUMBER
    ) RETURN NUMBER;

END pkg_cotizaciones;
/

CREATE OR REPLACE PACKAGE BODY pkg_cotizaciones AS

    PROCEDURE crear_cotizacion(
        p_id_usuario IN NUMBER,
        p_fecha_vencimiento IN DATE,
        p_id_cotizacion OUT NUMBER
    ) AS
    BEGIN
        INSERT INTO cotizacion(id_usuario,fecha_vencimiento,subtotal,impuesto,descuento,total,estado)
        VALUES(p_id_usuario,p_fecha_vencimiento,0,0,0,0,'GENERADA')
        RETURNING id_cotizacion INTO p_id_cotizacion;
    END crear_cotizacion;


    PROCEDURE agregar_detalle_cotizacion(
        p_id_cotizacion IN NUMBER,
        p_id_producto IN NUMBER,
        p_cantidad IN NUMBER,
        p_precio_unitario IN NUMBER
    ) AS
        v_subtotal NUMBER;
    BEGIN
        IF p_cantidad<=0 THEN
            RAISE_APPLICATION_ERROR(-20090,'La cantidad debe ser mayor que cero.');
        END IF;

        v_subtotal:=p_cantidad*p_precio_unitario;

        INSERT INTO cotizacion_detalle(id_cotizacion,id_producto,cantidad,precio_unitario,subtotal)
        VALUES(p_id_cotizacion,p_id_producto,p_cantidad,p_precio_unitario,v_subtotal);

        UPDATE cotizacion
        SET subtotal=NVL(subtotal,0)+v_subtotal,
            total=NVL(total,0)+v_subtotal
        WHERE id_cotizacion=p_id_cotizacion;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20091,'La cotización no existe.');
        END IF;
    END agregar_detalle_cotizacion;


    PROCEDURE actualizar_estado_cotizacion(
        p_id_cotizacion IN NUMBER,
        p_estado IN VARCHAR2
    ) AS
    BEGIN
        UPDATE cotizacion
        SET estado=UPPER(p_estado)
        WHERE id_cotizacion=p_id_cotizacion;

        IF SQL%ROWCOUNT=0 THEN
            RAISE_APPLICATION_ERROR(-20092,'La cotización no existe.');
        END IF;
    END actualizar_estado_cotizacion;


    FUNCTION obtener_total_cotizacion(
        p_id_cotizacion IN NUMBER
    ) RETURN NUMBER AS
        v_total NUMBER;
    BEGIN
        SELECT total INTO v_total
        FROM cotizacion
        WHERE id_cotizacion=p_id_cotizacion;

        RETURN v_total;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END obtener_total_cotizacion;

END pkg_cotizaciones;
/