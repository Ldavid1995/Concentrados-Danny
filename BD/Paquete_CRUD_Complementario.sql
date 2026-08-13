/*==============================================================
            PAQUETE CRUD COMPLEMENTARIO
==============================================================*/

CREATE OR REPLACE PACKAGE pkg_crud_complementario AS


    /*==========================================================
                         1. ROL
    ==========================================================*/

    PROCEDURE crear_rol(
        p_nombre_rol IN VARCHAR2,
        p_id_rol OUT NUMBER
    );

    PROCEDURE consultar_rol(
        p_id_rol IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_rol(
        p_id_rol IN NUMBER,
        p_nombre_rol IN VARCHAR2
    );

    PROCEDURE eliminar_rol(
        p_id_rol IN NUMBER
    );


    /*==========================================================
                         2. ESPECIE
    ==========================================================*/

    PROCEDURE crear_especie(
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_id_especie OUT NUMBER
    );

    PROCEDURE consultar_especie(
        p_id_especie IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_especie(
        p_id_especie IN NUMBER,
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2
    );

    PROCEDURE desactivar_especie(
        p_id_especie IN NUMBER
    );


    /*==========================================================
                    3. PRODUCTO_ESPECIE
    ==========================================================*/

    PROCEDURE crear_producto_especie(
        p_id_producto IN NUMBER,
        p_id_especie IN NUMBER
    );

    PROCEDURE consultar_producto_especie(
        p_id_producto IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_producto_especie(
        p_id_producto IN NUMBER,
        p_id_especie_anterior IN NUMBER,
        p_id_especie_nueva IN NUMBER
    );

    PROCEDURE eliminar_producto_especie(
        p_id_producto IN NUMBER,
        p_id_especie IN NUMBER
    );


    /*==========================================================
                     4. ZONA_COBERTURA
    ==========================================================*/

    PROCEDURE crear_zona(
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_costo_envio IN NUMBER,
        p_id_zona OUT NUMBER
    );

    PROCEDURE consultar_zona(
        p_id_zona IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_zona(
        p_id_zona IN NUMBER,
        p_nombre IN VARCHAR2,
        p_descripcion IN VARCHAR2,
        p_costo_envio IN NUMBER,
        p_disponible IN NUMBER
    );

    PROCEDURE eliminar_zona(
        p_id_zona IN NUMBER
    );


    /*==========================================================
                    5. HORARIO_ENTREGA
    ==========================================================*/

    PROCEDURE crear_horario(
        p_nombre IN VARCHAR2,
        p_hora_inicio IN VARCHAR2,
        p_hora_fin IN VARCHAR2,
        p_cupo_maximo IN NUMBER,
        p_id_horario OUT NUMBER
    );

    PROCEDURE consultar_horario(
        p_id_horario IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_horario(
        p_id_horario IN NUMBER,
        p_nombre IN VARCHAR2,
        p_hora_inicio IN VARCHAR2,
        p_hora_fin IN VARCHAR2,
        p_cupo_maximo IN NUMBER
    );

    PROCEDURE desactivar_horario(
        p_id_horario IN NUMBER
    );


    /*==========================================================
                         6. CUPON
    ==========================================================*/

    PROCEDURE crear_cupon(
        p_codigo IN VARCHAR2,
        p_descuento IN NUMBER,
        p_fecha_vence IN DATE,
        p_id_cupon OUT NUMBER
    );

    PROCEDURE consultar_cupon(
        p_id_cupon IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_cupon(
        p_id_cupon IN NUMBER,
        p_codigo IN VARCHAR2,
        p_descuento IN NUMBER,
        p_fecha_vence IN DATE
    );

    PROCEDURE desactivar_cupon(
        p_id_cupon IN NUMBER
    );


    /*==========================================================
                      7. REGLA_RACION
    ==========================================================*/

    PROCEDURE crear_regla_racion(
        p_id_especie IN NUMBER,
        p_peso_min IN VARCHAR2,
        p_peso_max IN VARCHAR2,
        p_edad_min IN VARCHAR2,
        p_edad_max IN VARCHAR2,
        p_cantidad_animales IN VARCHAR2,
        p_porcion_diaria IN VARCHAR2,
        p_observaciones IN VARCHAR2,
        p_id_regla OUT NUMBER
    );

    PROCEDURE consultar_regla_racion(
        p_id_regla IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_regla_racion(
        p_id_regla IN NUMBER,
        p_peso_min IN VARCHAR2,
        p_peso_max IN VARCHAR2,
        p_edad_min IN VARCHAR2,
        p_edad_max IN VARCHAR2,
        p_cantidad_animales IN VARCHAR2,
        p_porcion_diaria IN VARCHAR2,
        p_observaciones IN VARCHAR2
    );

    PROCEDURE desactivar_regla_racion(
        p_id_regla IN NUMBER
    );


    /*==========================================================
                         8. RESENA
    ==========================================================*/

    PROCEDURE crear_resena(
        p_id_usuario IN NUMBER,
        p_id_producto IN NUMBER,
        p_calificacion IN NUMBER,
        p_comentario IN VARCHAR2,
        p_id_resena OUT NUMBER
    );

    PROCEDURE consultar_resena(
        p_id_resena IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_resena(
        p_id_resena IN NUMBER,
        p_calificacion IN NUMBER,
        p_comentario IN VARCHAR2
    );

    PROCEDURE eliminar_resena(
        p_id_resena IN NUMBER
    );


    /*==========================================================
                     9. ESTADO_CUENTA
    ==========================================================*/

    PROCEDURE crear_estado_cuenta(
        p_id_usuario IN NUMBER,
        p_saldo IN NUMBER,
        p_id_estado OUT NUMBER
    );

    PROCEDURE consultar_estado_cuenta(
        p_id_usuario IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_estado_cuenta(
        p_id_estado IN NUMBER,
        p_saldo IN NUMBER
    );

    PROCEDURE eliminar_estado_cuenta(
        p_id_estado IN NUMBER
    );


    /*==========================================================
                      10. LISTA_DESEOS
    ==========================================================*/

    PROCEDURE consultar_lista_deseos(
        p_id_usuario IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );


    /*==========================================================
                      11. ALERTA_STOCK
    ==========================================================*/

    PROCEDURE crear_alerta(
        p_id_producto IN NUMBER,
        p_mensaje IN VARCHAR2,
        p_id_alerta OUT NUMBER
    );

    PROCEDURE consultar_alerta(
        p_id_alerta IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_alerta(
        p_id_alerta IN NUMBER,
        p_mensaje IN VARCHAR2,
        p_estado IN VARCHAR2
    );

    PROCEDURE eliminar_alerta(
        p_id_alerta IN NUMBER
    );


    /*==========================================================
                  12. HISTORIAL_PRECIO
    ==========================================================*/

    PROCEDURE consultar_historial_precio(
        p_id_producto IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );


    /*==========================================================
                  13. PRODUCTO_PROMOCION
    ==========================================================*/

    PROCEDURE consultar_producto_promocion(
        p_id_producto IN NUMBER,
        p_cursor OUT SYS_REFCURSOR
    );

    PROCEDURE actualizar_producto_promocion(
        p_id_producto IN NUMBER,
        p_id_promocion_anterior IN NUMBER,
        p_id_promocion_nueva IN NUMBER
    );

    PROCEDURE eliminar_producto_promocion(
        p_id_producto IN NUMBER,
        p_id_promocion IN NUMBER
    );

END pkg_crud_complementario;
/

--------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE BODY pkg_crud_complementario AS


/*==============================================================
                            ROL
==============================================================*/

PROCEDURE crear_rol(
    p_nombre_rol IN VARCHAR2,
    p_id_rol OUT NUMBER
) AS
BEGIN

    INSERT INTO rol(nombre_rol)
    VALUES(p_nombre_rol)
    RETURNING id_rol INTO p_id_rol;

END crear_rol;


PROCEDURE consultar_rol(
    p_id_rol IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM rol
        WHERE id_rol = p_id_rol;

END consultar_rol;


PROCEDURE actualizar_rol(
    p_id_rol IN NUMBER,
    p_nombre_rol IN VARCHAR2
) AS
BEGIN

    UPDATE rol
    SET nombre_rol = p_nombre_rol
    WHERE id_rol = p_id_rol;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20201,'El rol no existe.');
    END IF;

END actualizar_rol;


PROCEDURE eliminar_rol(
    p_id_rol IN NUMBER
) AS
BEGIN

    DELETE FROM rol
    WHERE id_rol = p_id_rol;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20202,'El rol no existe.');
    END IF;

END eliminar_rol;



/*==============================================================
                          ESPECIE
==============================================================*/

PROCEDURE crear_especie(
    p_nombre IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_id_especie OUT NUMBER
) AS
BEGIN

    INSERT INTO especie(
        nombre_especie,
        descripcion,
        activo
    )
    VALUES(
        p_nombre,
        p_descripcion,
        1
    )
    RETURNING id_especie INTO p_id_especie;

END crear_especie;


PROCEDURE consultar_especie(
    p_id_especie IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM especie
        WHERE id_especie = p_id_especie;

END consultar_especie;


PROCEDURE actualizar_especie(
    p_id_especie IN NUMBER,
    p_nombre IN VARCHAR2,
    p_descripcion IN VARCHAR2
) AS
BEGIN

    UPDATE especie
    SET nombre_especie = p_nombre,
        descripcion = p_descripcion
    WHERE id_especie = p_id_especie;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20203,'La especie no existe.');
    END IF;

END actualizar_especie;


PROCEDURE desactivar_especie(
    p_id_especie IN NUMBER
) AS
BEGIN

    UPDATE especie
    SET activo = 0
    WHERE id_especie = p_id_especie;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20204,'La especie no existe.');
    END IF;

END desactivar_especie;



/*==============================================================
                     PRODUCTO - ESPECIE
==============================================================*/

PROCEDURE crear_producto_especie(
    p_id_producto IN NUMBER,
    p_id_especie IN NUMBER
) AS
BEGIN

    INSERT INTO producto_especie(
        id_producto,
        id_especie
    )
    VALUES(
        p_id_producto,
        p_id_especie
    );

END crear_producto_especie;


PROCEDURE consultar_producto_especie(
    p_id_producto IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM producto_especie
        WHERE id_producto = p_id_producto;

END consultar_producto_especie;


PROCEDURE actualizar_producto_especie(
    p_id_producto IN NUMBER,
    p_id_especie_anterior IN NUMBER,
    p_id_especie_nueva IN NUMBER
) AS
BEGIN

    UPDATE producto_especie
    SET id_especie = p_id_especie_nueva
    WHERE id_producto = p_id_producto
      AND id_especie = p_id_especie_anterior;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20205,
            'La relación producto-especie no existe.'
        );
    END IF;

END actualizar_producto_especie;


PROCEDURE eliminar_producto_especie(
    p_id_producto IN NUMBER,
    p_id_especie IN NUMBER
) AS
BEGIN

    DELETE FROM producto_especie
    WHERE id_producto = p_id_producto
      AND id_especie = p_id_especie;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20206,
            'La relación producto-especie no existe.'
        );
    END IF;

END eliminar_producto_especie;



/*==============================================================
                     ZONA COBERTURA
==============================================================*/

PROCEDURE crear_zona(
    p_nombre IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_costo_envio IN NUMBER,
    p_id_zona OUT NUMBER
) AS
BEGIN

    INSERT INTO zona_cobertura(
        nombre_zona,
        descripcion,
        costo_envio,
        disponible
    )
    VALUES(
        p_nombre,
        p_descripcion,
        p_costo_envio,
        1
    )
    RETURNING id_zona INTO p_id_zona;

END crear_zona;


PROCEDURE consultar_zona(
    p_id_zona IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM zona_cobertura
        WHERE id_zona = p_id_zona;

END consultar_zona;


PROCEDURE actualizar_zona(
    p_id_zona IN NUMBER,
    p_nombre IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_costo_envio IN NUMBER,
    p_disponible IN NUMBER
) AS
BEGIN

    UPDATE zona_cobertura
    SET nombre_zona = p_nombre,
        descripcion = p_descripcion,
        costo_envio = p_costo_envio,
        disponible = p_disponible
    WHERE id_zona = p_id_zona;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20207,'La zona no existe.');
    END IF;

END actualizar_zona;


PROCEDURE eliminar_zona(
    p_id_zona IN NUMBER
) AS
BEGIN

    UPDATE zona_cobertura
    SET disponible = 0
    WHERE id_zona = p_id_zona;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20208,'La zona no existe.');
    END IF;

END eliminar_zona;



/*==============================================================
                    HORARIO ENTREGA
==============================================================*/

PROCEDURE crear_horario(
    p_nombre IN VARCHAR2,
    p_hora_inicio IN VARCHAR2,
    p_hora_fin IN VARCHAR2,
    p_cupo_maximo IN NUMBER,
    p_id_horario OUT NUMBER
) AS
BEGIN

    INSERT INTO horario_entrega(
        nombre_horario,
        hora_inicio,
        hora_fin,
        cupo_maximo,
        activo
    )
    VALUES(
        p_nombre,
        p_hora_inicio,
        p_hora_fin,
        p_cupo_maximo,
        1
    )
    RETURNING id_horario INTO p_id_horario;

END crear_horario;


PROCEDURE consultar_horario(
    p_id_horario IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM horario_entrega
        WHERE id_horario = p_id_horario;

END consultar_horario;


PROCEDURE actualizar_horario(
    p_id_horario IN NUMBER,
    p_nombre IN VARCHAR2,
    p_hora_inicio IN VARCHAR2,
    p_hora_fin IN VARCHAR2,
    p_cupo_maximo IN NUMBER
) AS
BEGIN

    UPDATE horario_entrega
    SET nombre_horario = p_nombre,
        hora_inicio = p_hora_inicio,
        hora_fin = p_hora_fin,
        cupo_maximo = p_cupo_maximo
    WHERE id_horario = p_id_horario;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20209,'El horario no existe.');
    END IF;

END actualizar_horario;


PROCEDURE desactivar_horario(
    p_id_horario IN NUMBER
) AS
BEGIN

    UPDATE horario_entrega
    SET activo = 0
    WHERE id_horario = p_id_horario;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20210,'El horario no existe.');
    END IF;

END desactivar_horario;



/*==============================================================
                           CUPON
==============================================================*/

PROCEDURE crear_cupon(
    p_codigo IN VARCHAR2,
    p_descuento IN NUMBER,
    p_fecha_vence IN DATE,
    p_id_cupon OUT NUMBER
) AS
BEGIN

    INSERT INTO cupon(
        codigo,
        descuento,
        fecha_vence,
        activo
    )
    VALUES(
        p_codigo,
        p_descuento,
        p_fecha_vence,
        1
    )
    RETURNING id_cupon INTO p_id_cupon;

END crear_cupon;


PROCEDURE consultar_cupon(
    p_id_cupon IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM cupon
        WHERE id_cupon = p_id_cupon;

END consultar_cupon;


PROCEDURE actualizar_cupon(
    p_id_cupon IN NUMBER,
    p_codigo IN VARCHAR2,
    p_descuento IN NUMBER,
    p_fecha_vence IN DATE
) AS
BEGIN

    UPDATE cupon
    SET codigo = p_codigo,
        descuento = p_descuento,
        fecha_vence = p_fecha_vence
    WHERE id_cupon = p_id_cupon;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20211,'El cupón no existe.');
    END IF;

END actualizar_cupon;


PROCEDURE desactivar_cupon(
    p_id_cupon IN NUMBER
) AS
BEGIN

    UPDATE cupon
    SET activo = 0
    WHERE id_cupon = p_id_cupon;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20212,'El cupón no existe.');
    END IF;

END desactivar_cupon;



/*==============================================================
                      REGLA RACION
==============================================================*/

PROCEDURE crear_regla_racion(
    p_id_especie IN NUMBER,
    p_peso_min IN VARCHAR2,
    p_peso_max IN VARCHAR2,
    p_edad_min IN VARCHAR2,
    p_edad_max IN VARCHAR2,
    p_cantidad_animales IN VARCHAR2,
    p_porcion_diaria IN VARCHAR2,
    p_observaciones IN VARCHAR2,
    p_id_regla OUT NUMBER
) AS
BEGIN

    INSERT INTO regla_racion(
        id_especie,
        peso_min,
        peso_max,
        edad_min,
        edad_max,
        cantidad_animales,
        porcion_diaria,
        observaciones,
        activo
    )
    VALUES(
        p_id_especie,
        p_peso_min,
        p_peso_max,
        p_edad_min,
        p_edad_max,
        p_cantidad_animales,
        p_porcion_diaria,
        p_observaciones,
        1
    )
    RETURNING id_regla INTO p_id_regla;

END crear_regla_racion;


PROCEDURE consultar_regla_racion(
    p_id_regla IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM regla_racion
        WHERE id_regla = p_id_regla;

END consultar_regla_racion;


PROCEDURE actualizar_regla_racion(
    p_id_regla IN NUMBER,
    p_peso_min IN VARCHAR2,
    p_peso_max IN VARCHAR2,
    p_edad_min IN VARCHAR2,
    p_edad_max IN VARCHAR2,
    p_cantidad_animales IN VARCHAR2,
    p_porcion_diaria IN VARCHAR2,
    p_observaciones IN VARCHAR2
) AS
BEGIN

    UPDATE regla_racion
    SET peso_min = p_peso_min,
        peso_max = p_peso_max,
        edad_min = p_edad_min,
        edad_max = p_edad_max,
        cantidad_animales = p_cantidad_animales,
        porcion_diaria = p_porcion_diaria,
        observaciones = p_observaciones
    WHERE id_regla = p_id_regla;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20213,'La regla no existe.');
    END IF;

END actualizar_regla_racion;


PROCEDURE desactivar_regla_racion(
    p_id_regla IN NUMBER
) AS
BEGIN

    UPDATE regla_racion
    SET activo = 0
    WHERE id_regla = p_id_regla;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20214,'La regla no existe.');
    END IF;

END desactivar_regla_racion;



/*==============================================================
                           RESENA
==============================================================*/

PROCEDURE crear_resena(
    p_id_usuario IN NUMBER,
    p_id_producto IN NUMBER,
    p_calificacion IN NUMBER,
    p_comentario IN VARCHAR2,
    p_id_resena OUT NUMBER
) AS
BEGIN

    IF p_calificacion < 1 OR p_calificacion > 5 THEN
        RAISE_APPLICATION_ERROR(
            -20215,
            'La calificación debe estar entre 1 y 5.'
        );
    END IF;

    INSERT INTO resena(
        id_usuario,
        id_producto,
        calificacion,
        comentario
    )
    VALUES(
        p_id_usuario,
        p_id_producto,
        p_calificacion,
        p_comentario
    )
    RETURNING id_resena INTO p_id_resena;

END crear_resena;


PROCEDURE consultar_resena(
    p_id_resena IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM resena
        WHERE id_resena = p_id_resena;

END consultar_resena;


PROCEDURE actualizar_resena(
    p_id_resena IN NUMBER,
    p_calificacion IN NUMBER,
    p_comentario IN VARCHAR2
) AS
BEGIN

    IF p_calificacion < 1 OR p_calificacion > 5 THEN
        RAISE_APPLICATION_ERROR(
            -20216,
            'La calificación debe estar entre 1 y 5.'
        );
    END IF;

    UPDATE resena
    SET calificacion = p_calificacion,
        comentario = p_comentario
    WHERE id_resena = p_id_resena;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20217,'La reseña no existe.');
    END IF;

END actualizar_resena;


PROCEDURE eliminar_resena(
    p_id_resena IN NUMBER
) AS
BEGIN

    DELETE FROM resena
    WHERE id_resena = p_id_resena;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20218,'La reseña no existe.');
    END IF;

END eliminar_resena;



/*==============================================================
                    ESTADO DE CUENTA
==============================================================*/

PROCEDURE crear_estado_cuenta(
    p_id_usuario IN NUMBER,
    p_saldo IN NUMBER,
    p_id_estado OUT NUMBER
) AS
BEGIN

    INSERT INTO estado_cuenta(
        id_usuario,
        saldo_actual
    )
    VALUES(
        p_id_usuario,
        p_saldo
    )
    RETURNING id_estado INTO p_id_estado;

END crear_estado_cuenta;


PROCEDURE consultar_estado_cuenta(
    p_id_usuario IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM estado_cuenta
        WHERE id_usuario = p_id_usuario;

END consultar_estado_cuenta;


PROCEDURE actualizar_estado_cuenta(
    p_id_estado IN NUMBER,
    p_saldo IN NUMBER
) AS
BEGIN

    UPDATE estado_cuenta
    SET saldo_actual = p_saldo
    WHERE id_estado = p_id_estado;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20219,
            'El estado de cuenta no existe.'
        );
    END IF;

END actualizar_estado_cuenta;


PROCEDURE eliminar_estado_cuenta(
    p_id_estado IN NUMBER
) AS
BEGIN

    DELETE FROM estado_cuenta
    WHERE id_estado = p_id_estado;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20220,
            'El estado de cuenta no existe.'
        );
    END IF;

END eliminar_estado_cuenta;



/*==============================================================
                     LISTA DE DESEOS
==============================================================*/

PROCEDURE consultar_lista_deseos(
    p_id_usuario IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM lista_deseos
        WHERE id_usuario = p_id_usuario;

END consultar_lista_deseos;



/*==============================================================
                      ALERTA STOCK
==============================================================*/

PROCEDURE crear_alerta(
    p_id_producto IN NUMBER,
    p_mensaje IN VARCHAR2,
    p_id_alerta OUT NUMBER
) AS
BEGIN

    INSERT INTO alerta_stock(
        id_producto,
        mensaje,
        estado
    )
    VALUES(
        p_id_producto,
        p_mensaje,
        'PENDIENTE'
    )
    RETURNING id_alerta INTO p_id_alerta;

END crear_alerta;


PROCEDURE consultar_alerta(
    p_id_alerta IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM alerta_stock
        WHERE id_alerta = p_id_alerta;

END consultar_alerta;


PROCEDURE actualizar_alerta(
    p_id_alerta IN NUMBER,
    p_mensaje IN VARCHAR2,
    p_estado IN VARCHAR2
) AS
BEGIN

    UPDATE alerta_stock
    SET mensaje = p_mensaje,
        estado = p_estado
    WHERE id_alerta = p_id_alerta;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20221,'La alerta no existe.');
    END IF;

END actualizar_alerta;


PROCEDURE eliminar_alerta(
    p_id_alerta IN NUMBER
) AS
BEGIN

    DELETE FROM alerta_stock
    WHERE id_alerta = p_id_alerta;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20222,'La alerta no existe.');
    END IF;

END eliminar_alerta;



/*==============================================================
                    HISTORIAL PRECIO
==============================================================*/

PROCEDURE consultar_historial_precio(
    p_id_producto IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM historial_precio
        WHERE id_producto = p_id_producto
        ORDER BY fecha_cambio DESC;

END consultar_historial_precio;



/*==============================================================
                  PRODUCTO PROMOCION
==============================================================*/

PROCEDURE consultar_producto_promocion(
    p_id_producto IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
) AS
BEGIN

    OPEN p_cursor FOR
        SELECT *
        FROM producto_promocion
        WHERE id_producto = p_id_producto;

END consultar_producto_promocion;


PROCEDURE actualizar_producto_promocion(
    p_id_producto IN NUMBER,
    p_id_promocion_anterior IN NUMBER,
    p_id_promocion_nueva IN NUMBER
) AS
BEGIN

    UPDATE producto_promocion
    SET id_promocion = p_id_promocion_nueva
    WHERE id_producto = p_id_producto
      AND id_promocion = p_id_promocion_anterior;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20223,
            'La relación producto-promoción no existe.'
        );
    END IF;

END actualizar_producto_promocion;


PROCEDURE eliminar_producto_promocion(
    p_id_producto IN NUMBER,
    p_id_promocion IN NUMBER
) AS
BEGIN

    DELETE FROM producto_promocion
    WHERE id_producto = p_id_producto
      AND id_promocion = p_id_promocion;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(
            -20224,
            'La relación producto-promoción no existe.'
        );
    END IF;

END eliminar_producto_promocion;


END pkg_crud_complementario;
/



