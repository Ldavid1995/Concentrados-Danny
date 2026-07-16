----------* Vistas *-------------------------------

--1.VW_PRODUCTOS_POR_ESPECIE

CREATE OR REPLACE VIEW vw_productos_por_especie AS
SELECT
    p.id_producto,
    p.nombre, 
    p.marca, 
    p.precio,
    p.stock,
    e.nombre_especie
FROM producto p
JOIN producto_especie pe
    ON p.id_producto = pe.id_producto
JOIN especie e 
    ON pe.id_especie = e.id_especie;


--2.VW_DETALLE_PEDIDOS_ZONAS

CREATE OR REPLACE VIEW vw_detalle_pedidos_zonas AS
SELECT
    ped.id_pedido,
    u.nombre || ' ' || u.apellidos AS cliente, 
    z.nombre_zona, 
    z.costo_envio,
    ped.fecha_entrega,
    ped.hora_entrega,
    ped.estado,
    ped.total
FROM pedido ped
JOIN usuario u 
    ON ped.id_usuario = u.id_usuario
JOIN zona_cobertura z 
    ON ped.id_zona = z.id_zona;

-- 3. VW_PRODUCTOS_STOCK_BAJO
-- Muestra productos cuyo stock es igual o menor al mínimo.

CREATE OR REPLACE VIEW vw_productos_stock_bajo AS
SELECT
    p.id_producto,
    p.nombre,
    p.marca,
    p.stock,
    p.stock_minimo,
    c.nombre_categoria
FROM producto p
JOIN categoria c
    ON p.id_categoria = c.id_categoria
WHERE p.stock <= p.stock_minimo;

-- ACTUALIZAR STOCK PARA PROBAR LA VISTA

UPDATE producto
SET stock = 4
WHERE id_producto = 1;

UPDATE producto
SET stock = 3
WHERE id_producto = 2;

UPDATE producto
SET stock = 8
WHERE id_producto = 3;

UPDATE producto
SET stock = 7
WHERE id_producto = 4;

UPDATE producto
SET stock = 5
WHERE id_producto = 5;

-- 4. VW_PEDIDOS_CLIENTES
-- Muestra los pedidos con los datos del cliente y la dirección.

CREATE OR REPLACE VIEW vw_pedidos_clientes AS
SELECT
    ped.id_pedido,
    u.id_usuario,
    u.nombre || ' ' || u.apellidos AS cliente,
    u.correo,
    d.direccion,
    d.provincia,
    d.canton,
    ped.fecha_pedido,
    ped.fecha_entrega,
    ped.hora_entrega,
    ped.estado,
    ped.total
FROM pedido ped
JOIN usuario u
    ON ped.id_usuario = u.id_usuario
JOIN direccion d
    ON ped.id_direccion = d.id_direccion;

-- 5. VW_DETALLE_COMPLETO_PEDIDOS
-- Muestra los productos incluidos en cada pedido.

CREATE OR REPLACE VIEW vw_detalle_completo_pedidos AS
SELECT
    dp.id_detalle,
    dp.id_pedido,
    p.id_producto,
    p.nombre AS producto,
    p.marca,
    dp.cantidad,
    dp.precio_unitario,
    dp.descuento,
    dp.subtotal,
    pr.nombre AS promocion
FROM detalle_pedido dp
JOIN producto p
    ON dp.id_producto = p.id_producto
LEFT JOIN promocion pr -- se usa porque un producto puede agregarse a un pedido sin tener promocion
    ON dp.id_promocion = pr.id_promocion;

-- 6. VW_PAGOS_PEDIDOS
-- Muestra la información de pago de cada pedido.

CREATE OR REPLACE VIEW vw_pagos_pedidos AS
SELECT
    pa.id_pago,
    pa.id_pedido,
    u.nombre || ' ' || u.apellidos AS cliente,
    pa.metodo_pago,
    pa.monto,
    pa.estado_pago,
    pa.referencia_bancaria,
    pa.autorizacion,
    pa.numero_transaccion,
    pa.fecha_pago
FROM pago pa
JOIN pedido ped
    ON pa.id_pedido = ped.id_pedido
JOIN usuario u
    ON ped.id_usuario = u.id_usuario;

-- 7. VW_LOTES_PROXIMOS_VENCER
-- Muestra lotes que vencen dentro de los próximos 30 días.

CREATE OR REPLACE VIEW vw_lotes_proximos_vencer AS
SELECT
    l.id_lote,
    l.numero_lote,
    p.id_producto,
    p.nombre AS producto,
    l.fecha_ingreso,
    l.fecha_vencimiento,
    l.cantidad,
    l.activo
FROM lote l
JOIN producto p
    ON l.id_producto = p.id_producto
WHERE l.fecha_vencimiento BETWEEN TRUNC(SYSDATE)
                              AND TRUNC(SYSDATE) + 30;

-- 8. VW_PROMOCIONES_PRODUCTOS
-- Muestra las promociones asociadas con cada producto.

CREATE OR REPLACE VIEW vw_promociones_productos AS
SELECT
    p.id_producto,
    p.nombre AS producto,
    p.precio,
    pr.id_promocion,
    pr.nombre AS promocion,
    pr.porcentaje_descuento,
    pr.fecha_inicio,
    pr.fecha_fin,
    pr.activo
FROM producto_promocion pp
JOIN producto p
    ON pp.id_producto = p.id_producto
JOIN promocion pr
    ON pp.id_promocion = pr.id_promocion;

-- 9. VW_RESENAS_PRODUCTOS
-- Muestra las reseñas con el cliente y el producto evaluado.

CREATE OR REPLACE VIEW vw_resenas_productos AS
SELECT
    r.id_resena,
    p.id_producto,
    p.nombre AS producto,
    u.id_usuario,
    u.nombre || ' ' || u.apellidos AS cliente,
    r.calificacion,
    r.comentario,
    r.fecha_resena
FROM resena r
JOIN producto p
    ON r.id_producto = p.id_producto
JOIN usuario u
    ON r.id_usuario = u.id_usuario;

-- 10. VW_COTIZACIONES_DETALLE
-- Muestra la información completa de las cotizaciones.

CREATE OR REPLACE VIEW vw_cotizaciones_detalle AS
SELECT
    c.id_cotizacion,
    u.nombre || ' ' || u.apellidos AS cliente,
    c.fecha_cotizacion,
    c.fecha_vencimiento,
    p.nombre AS producto,
    cd.cantidad,
    cd.precio_unitario,
    cd.subtotal AS subtotal_producto,
    c.subtotal AS subtotal_cotizacion,
    c.impuesto,
    c.descuento,
    c.total,
    c.estado
FROM cotizacion c
JOIN usuario u
    ON c.id_usuario = u.id_usuario
JOIN cotizacion_detalle cd
    ON c.id_cotizacion = cd.id_cotizacion
JOIN producto p
    ON cd.id_producto = p.id_producto;


-- PRUEBAS
SELECT * FROM vw_productos_por_especie;
SELECT * FROM vw_detalle_pedidos_zonas;
SELECT * FROM vw_productos_stock_bajo;
SELECT * FROM vw_pedidos_clientes;
SELECT * FROM vw_detalle_completo_pedidos;
SELECT * FROM vw_pagos_pedidos;
SELECT * FROM vw_lotes_proximos_vencer;
SELECT * FROM vw_promociones_productos;
SELECT * FROM vw_resenas_productos;
SELECT * FROM vw_cotizaciones_detalle;





















































