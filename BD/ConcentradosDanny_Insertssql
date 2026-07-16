----------------------------------------
        -- INSERTS DE PRUEBA 
----------------------------------------


----------------------------------------
-- 1. ROLES
----------------------------------------

INSERT INTO rol (nombre_rol) VALUES ('ADMINISTRADOR');
INSERT INTO rol (nombre_rol) VALUES ('VENDEDOR');
INSERT INTO rol (nombre_rol) VALUES ('CLIENTE');
INSERT INTO rol (nombre_rol) VALUES ('INVENTARIO');
INSERT INTO rol (nombre_rol) VALUES ('SOPORTE');
INSERT INTO rol (nombre_rol) VALUES ('REPARTIDOR');
INSERT INTO rol (nombre_rol) VALUES ('SUPERVISOR');

----------------------------------------
-- 2. USUARIOS
----------------------------------------

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(1, 'admin', 'Admin123', 'Luis David', 'Averruz Chavarria','admin@danny.com', '88881111', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(2, 'vendedor1', 'Venta123', 'Nairyth', 'Araya Torres','ventas1@danny.com', '88882222', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(3, 'juanperez', 'Juan123', 'Juan', 'Perez Lopez','juan@correo.com', '88883333', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(4, 'inventario1', 'Inv123', 'Carlos', 'Sanchez Rojas','inventario@danny.com', '88884444', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(5, 'soporte1', 'Sop123', 'Maria', 'Jimenez Castro','soporte@danny.com', '88885555', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(6, 'repartidor1', 'Rep123', 'Pedro', 'Ramirez Diaz','repartidor@danny.com', '88886666', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(7, 'supervisor1', 'Sup123', 'Laura', 'Fernandez Soto','supervisor@danny.com', '88887777', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(3, 'cliente2', 'Cliente123', 'Andrea', 'Mora Campos','andrea@correo.com', '88888888', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(3, 'cliente3', 'Cliente456', 'Sofia', 'Navarro Ruiz','sofia@correo.com', '88889999', 1);

INSERT INTO usuario(id_rol, username, password, nombre, apellidos, correo, telefono, activo)
VALUES(2, 'vendedor2', 'Venta456', 'Andres', 'Castro Solano','ventas2@danny.com', '88880000', 1);

----------------------------------------
-- 3. TOKENS DE RECUPERACIÓN
----------------------------------------

INSERT INTO token_recuperacion (id_usuario, token, fecha_expiracion, usado)
VALUES(1, 'TOKEN001', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion (id_usuario, token, fecha_expiracion, usado)
VALUES(2, 'TOKEN002', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion(id_usuario, token, fecha_expiracion, usado)
VALUES(3, 'TOKEN003', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion(id_usuario, token, fecha_expiracion, usado)
VALUES(4, 'TOKEN004', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion(id_usuario, token, fecha_expiracion, usado)
VALUES(5, 'TOKEN005', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion(id_usuario, token, fecha_expiracion, usado)
VALUES (6, 'TOKEN006', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion(id_usuario, token, fecha_expiracion, usado)
VALUES(7, 'TOKEN007', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion(id_usuario, token, fecha_expiracion, usado)
VALUES(8, 'TOKEN008', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion(id_usuario, token, fecha_expiracion, usado)
VALUES(9, 'TOKEN009', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

INSERT INTO token_recuperacion(id_usuario, token, fecha_expiracion, usado)
VALUES(10, 'TOKEN010', SYSTIMESTAMP + INTERVAL '1' DAY, 0);

----------------------------------------
-- 4. DIRECCIONES
----------------------------------------

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(1, 'Barrio Escalante, Avenida Central', 'San José', 'San José', 'Carmen', '10101', 'Frente al Parque Francia', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(2, 'San Pedro, Calle 59', 'San José', 'Montes de Oca', 'San Pedro', '11501', '200 metros este de la UCR', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(3, 'Centro de Alajuela', 'Alajuela', 'Alajuela', 'Alajuela', '20101', 'Frente al Parque Central', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(4, 'San Francisco', 'Heredia', 'Heredia', 'San Francisco', '40103', 'Contiguo al supermercado', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(5, 'Centro de Cartago', 'Cartago', 'Cartago', 'Oriental', '30101', '100 metros norte de las Ruinas', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(6, 'Liberia Centro', 'Guanacaste', 'Liberia', 'Liberia', '50101', 'Frente al Banco Nacional', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(7, 'Barrio El Carmen', 'Puntarenas', 'Puntarenas', 'Puntarenas', '60101', 'Cerca del Paseo de los Turistas', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(8, 'Centro de Limón', 'Limón', 'Limón', 'Limón', '70101', 'Diagonal al Mercado Municipal', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(9, 'Desamparados Centro', 'San José', 'Desamparados', 'Desamparados', '10301', 'Frente a la Iglesia Católica', 1);

INSERT INTO direccion(id_usuario, direccion, provincia, canton, distrito, codigo_postal, referencia, activo)
VALUES(10, 'San Ramón Centro', 'Alajuela', 'San Ramón', 'San Ramón', '20201', 'Costado oeste del Parque Central', 1);

----------------------------------------
-- 5. CATEGORÍAS
----------------------------------------

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Alimento para Perros', 'Concentrados para perros de todas las edades', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Alimento para Gatos', 'Concentrados para gatos domésticos', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Alimento para Cerdos', 'Concentrados para crecimiento y engorde de cerdos', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Alimento para Aves', 'Concentrados para pollos, gallinas y otras aves', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Alimento para Ganado', 'Concentrados para ganado bovino y lechero', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Alimento para Caballos', 'Concentrados y suplementos para equinos', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Alimento para Conejos', 'Alimentos balanceados para conejos', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Alimento para Peces', 'Alimentos para peces de acuario y estanque', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Suplementos Nutricionales', 'Vitaminas, minerales y suplementos para animales', 1);

INSERT INTO categoria(nombre_categoria, descripcion, activo)
VALUES('Accesorios para Alimentación', 'Comederos, bebederos y accesorios relacionados', 1);

----------------------------------------
-- 6. ESPECIES
----------------------------------------

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Perros', 'Especie canina doméstica', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Gatos', 'Especie felina doméstica', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Cerdos', 'Especie porcina para producción', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Gallinas', 'Aves ponedoras y de producción', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Pollos', 'Aves de engorde', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Ganado Bovino', 'Ganado para carne y leche', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Caballos', 'Especie equina', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Conejos', 'Especie cunícola', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Peces', 'Especies de acuario y estanque', 1);

INSERT INTO especie(nombre_especie, descripcion, activo)
VALUES('Cabras', 'Especie caprina', 1);

----------------------------------------
-- 7. PRODUCTOS
----------------------------------------

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(1, 'Concentrado Adulto Dogui', 'Dogui', 'Alimento para perros adultos', 'Saco 18 kg', 12500, 20, 5, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(2, 'Whiskas Gatitos', 'Whiskas', 'Alimento para gatos cachorros', 'Saco 10 kg', 8500, 15, 4, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(3, 'Concentrado Cerdo Premium', 'AgroPlus', 'Alimento para cerdos en crecimiento', 'Saco 40 kg', 16800, 30, 8, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(4, 'Alimento Gallina Ponedora', 'NutriAve', 'Concentrado para gallinas ponedoras', 'Saco 40 kg', 15200, 25, 7, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(5, 'Ganado Lechero Plus', 'Ganamax', 'Concentrado para ganado lechero', 'Saco 45 kg', 18500, 18, 6, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(6, 'Equino Premium', 'EquiFood', 'Concentrado energético para caballos', 'Saco 35 kg', 21000, 12, 4, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(7, 'Pellets Conejo', 'Conejina', 'Pellets para conejos', 'Saco 20 kg', 9800, 22, 5, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(8, 'Aqua Feed Premium', 'AquaFeed', 'Alimento para peces', 'Saco 15 kg', 11500, 14, 4, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(9, 'Multivitamínico Animal', 'AnimalVit', 'Suplemento vitamínico para animales', 'Envase 1 kg', 7200, 35, 10, 1);

INSERT INTO producto(id_categoria, nombre, marca, descripcion, unidad_medida, precio, stock, stock_minimo, activo)
VALUES(10, 'Comedero Metálico', 'PetHome', 'Comedero para mascotas', 'Unidad', 4500, 40, 8, 1);

COMMIT;

----------------------------------------
-- 8. PRODUCTO_ESPECIE
----------------------------------------
INSERT INTO producto_especie (id_producto, id_especie)VALUES (1, 1);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (2, 2);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (3, 3);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (4, 4);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (5, 6);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (6, 7);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (7, 8);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (8, 9);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (9, 1);
INSERT INTO producto_especie (id_producto, id_especie)VALUES (10, 2);

----------------------------------------
-- 9. LOTES
----------------------------------------

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(1, 'LOT-001', DATE '2026-07-01', DATE '2027-07-01', 20, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(2, 'LOT-002', DATE '2026-07-02', DATE '2027-07-02', 15, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(3, 'LOT-003', DATE '2026-07-03', DATE '2027-07-03', 30, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(4, 'LOT-004', DATE '2026-07-04', DATE '2027-07-04', 25, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(5, 'LOT-005', DATE '2026-07-05', DATE '2027-07-05', 18, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(6, 'LOT-006', DATE '2026-07-06', DATE '2027-07-06', 12, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(7, 'LOT-007', DATE '2026-07-07', DATE '2027-07-07', 22, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(8, 'LOT-008', DATE '2026-07-08', DATE '2027-07-08', 14, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(9, 'LOT-009', DATE '2026-07-09', DATE '2027-07-09', 35, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(10, 'LOT-010', DATE '2026-07-10', DATE '2027-07-10', 40, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(1, 'LOT-011', DATE '2026-07-15', DATE '2026-07-25', 10, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(2, 'LOT-012', DATE '2026-07-16', DATE '2026-07-28', 8, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(3, 'LOT-013', DATE '2026-07-17', DATE '2026-07-30', 12, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(4, 'LOT-014', DATE '2026-07-18', DATE '2026-08-02', 15, 1);

INSERT INTO lote(id_producto, numero_lote, fecha_ingreso, fecha_vencimiento, cantidad, activo)
VALUES(5, 'LOT-015', DATE '2026-07-19', DATE '2026-08-05', 9, 1);

COMMIT;

----------------------------------------
-- 10. ZONA_COBERTURA
----------------------------------------

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('San José Centro', 'Cobertura para el centro de San José', 1500, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Heredia', 'Cobertura para la provincia de Heredia', 1800, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Alajuela', 'Cobertura para la provincia de Alajuela', 2000, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Cartago', 'Cobertura para la provincia de Cartago', 2200, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Guanacaste', 'Cobertura para la provincia de Guanacaste', 3500, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Puntarenas', 'Cobertura para la provincia de Puntarenas', 3200, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Limón', 'Cobertura para la provincia de Limón', 3800, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Zona Rural Norte', 'Cobertura para zonas rurales del norte', 4500, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Zona Rural Sur', 'Cobertura para zonas rurales del sur', 4500, 1);

INSERT INTO zona_cobertura(nombre_zona, descripcion, costo_envio, disponible)
VALUES('Cobertura Especial', 'Cobertura para entregas especiales', 6000, 1);

COMMIT;

----------------------------------------
-- 11. HORARIO_ENTREGA
----------------------------------------

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Mañana Temprano', '08:00', '10:00', 10, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Media Mañana', '10:00', '12:00', 10, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Mediodía', '12:00', '13:00', 8, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Primera Tarde', '13:00', '15:00', 10, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Media Tarde', '15:00', '17:00', 10, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Última Tarde', '17:00', '19:00', 8, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Entrega Nocturna', '19:00', '21:00', 6, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Entrega Express AM', '09:00', '11:00', 5, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Entrega Express PM', '14:00', '16:00', 5, 1);

INSERT INTO horario_entrega(nombre_horario, hora_inicio, hora_fin, cupo_maximo, activo)
VALUES('Horario Especial', '18:00', '20:00', 4, 1);

COMMIT;

----------------------------------------
-- 12. CUPONES
----------------------------------------

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('BIENVENIDA10', 10, DATE '2027-12-31', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('CLIENTE15', 15, DATE '2027-12-31', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('VERANO20', 20, DATE '2027-12-31', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('MASCOTAS25', 25, DATE '2027-12-31', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('DANNY5', 5, DATE '2027-12-31', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('PROMO30', 30, DATE '2027-12-31', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('BLACK15', 15, DATE '2027-11-30', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('NAVIDAD20', 20, DATE '2027-12-25', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('ENVIO10', 10, DATE '2027-10-31', 1);

INSERT INTO cupon(codigo, descuento, fecha_vence, activo)
VALUES('FIDELIDAD35', 35, DATE '2027-12-31', 1);

COMMIT;

----------------------------------------
-- 13. PEDIDOS
----------------------------------------

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(1, 1, 1, 1, 1, DATE '2026-08-01', '09:00', 'PENDIENTE', 12750);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(2, 2, 2, 2, 2, DATE '2026-08-02', '10:30', 'PAGADO', 9025);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(3, 3, 3, 3, 3, DATE '2026-08-03', '12:00', 'EN_PROCESO', 15440);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(4, 4, 4, 4, 4, DATE '2026-08-04', '14:00', 'ENTREGADO', 13200);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(5, 5, 5, 5, 5, DATE '2026-08-05', '15:30', 'PENDIENTE', 19775);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(6, 6, 6, 6, 6, DATE '2026-08-06', '17:00', 'PAGADO', 18200);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(7, 7, 7, 7, 7, DATE '2026-08-07', '18:30', 'EN_PROCESO', 11530);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(8, 8, 8, 8, 8, DATE '2026-08-08', '09:30', 'ENTREGADO', 13000);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(9, 9, 9, 9, 9, DATE '2026-08-09', '15:00', 'PENDIENTE', 8180);

INSERT INTO pedido
(id_usuario, id_direccion, id_horario, id_zona, id_cupon, fecha_entrega, hora_entrega, estado, total)
VALUES
(10, 10, 10, 10, 10, DATE '2026-08-10', '18:00', 'PAGADO', 5325);

COMMIT;

----------------------------------------
-- 14. PROMOCIONES
----------------------------------------

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Bienvenida', 'Descuento para nuevos clientes', 10, DATE '2026-01-01', DATE '2027-12-31', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Verano', 'Promoción de temporada', 15, DATE '2026-01-01', DATE '2027-12-31', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Cliente Frecuente', 'Beneficio para clientes frecuentes', 20, DATE '2026-01-01', DATE '2027-12-31', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Fin de Semana', 'Promoción válida sábados y domingos', 25, DATE '2026-01-01', DATE '2027-12-31', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Navidad', 'Promoción navideña', 30, DATE '2026-12-01', DATE '2026-12-31', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Black Friday', 'Descuento especial', 35, DATE '2026-11-20', DATE '2026-11-30', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Aniversario', 'Promoción por aniversario', 12, DATE '2026-01-01', DATE '2027-12-31', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Mascotas Felices', 'Descuento en productos seleccionados', 18, DATE '2026-01-01', DATE '2027-12-31', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Compra Inteligente', 'Descuento por compras mayores', 22, DATE '2026-01-01', DATE '2027-12-31', 1);

INSERT INTO promocion(nombre, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo)
VALUES('Oferta Especial', 'Promoción exclusiva', 40, DATE '2026-01-01', DATE '2027-12-31', 1);

COMMIT;

----------------------------------------
-- 15. DETALLE_PEDIDO
----------------------------------------

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(1, 1, 1, 1, 12500, 1250, 11250);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(2, 2, 2, 1, 8500, 1275, 7225);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(3, 3, 3, 1, 16800, 3360, 13440);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(4, 4, 4, 1, 15200, 3800, 11400);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(5, 5, 5, 1, 18500, 5550, 12950);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(6, 6, 6, 1, 21000, 7350, 13650);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(7, 7, 7, 1, 9800, 1176, 8624);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(8, 8, 8, 1, 11500, 2070, 9430);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(9, 9, 9, 1, 7200, 1584, 5616);

INSERT INTO detalle_pedido(id_pedido, id_producto, id_promocion, cantidad, precio_unitario, descuento, subtotal)
VALUES(10, 10, 10, 1, 4500, 1800, 2700);

COMMIT;

----------------------------------------
-- 16. PAGOS
----------------------------------------

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(1, 'TARJETA', 12750, 'PENDIENTE', 'REF001', 'AUT001', 'TXN001');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(2, 'TRANSFERENCIA', 9025, 'APROBADO', 'REF002', 'AUT002', 'TXN002');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(3, 'SINPE', 15440, 'APROBADO', 'REF003', 'AUT003', 'TXN003');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(4, 'TARJETA', 13200, 'APROBADO', 'REF004', 'AUT004', 'TXN004');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(5, 'EFECTIVO', 19775, 'PENDIENTE', NULL, NULL, 'TXN005');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(6, 'TRANSFERENCIA', 18200, 'APROBADO', 'REF006', 'AUT006', 'TXN006');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(7, 'SINPE', 11530, 'PENDIENTE', 'REF007', 'AUT007', 'TXN007');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(8, 'TARJETA', 13000, 'APROBADO', 'REF008', 'AUT008', 'TXN008');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(9, 'EFECTIVO', 8180, 'PENDIENTE', NULL, NULL, 'TXN009');

INSERT INTO pago(id_pedido, metodo_pago, monto, estado_pago, referencia_bancaria, autorizacion, numero_transaccion)
VALUES(10, 'TRANSFERENCIA', 5325, 'APROBADO', 'REF010', 'AUT010', 'TXN010');

COMMIT;

----------------------------------------
-- 17. PRODUCTO_PROMOCION
----------------------------------------

INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (1, 1);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (2, 2);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (3, 3);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (4, 4);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (5, 5);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (6, 6);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (7, 7);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (8, 8);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (9, 9);
INSERT INTO producto_promocion (id_producto, id_promocion)VALUES (10, 10);
COMMIT;

----------------------------------------
-- 18. REGLAS DE RACIÓN
----------------------------------------

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(1, '1 kg', '10 kg', '2 meses', '12 meses', '1 perro', '250 gramos', 'Perro cachorro', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(2, '500 gramos', '8 kg', '2 meses', '12 meses', '1 gato', '180 gramos', 'Gato cachorro', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(3, '20 kg', '80 kg', '3 meses', '24 meses', '1 cerdo', '2200 gramos', 'Cerdo en crecimiento', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(4, '1 kg', '5 kg', '4 meses', '24 meses', '20 gallinas', '120 gramos', 'Gallinas ponedoras', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(5, '1 kg', '4 kg', '1 mes', '6 meses', '25 pollos', '100 gramos', 'Pollos de engorde', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(6, '200 kg', '700 kg', '1 año', '10 años', '1 res', '8500 gramos', 'Ganado lechero', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(7, '250 kg', '600 kg', '2 años', '15 años', '1 caballo', '7000 gramos', 'Caballo adulto', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(8, '1 kg', '4 kg', '2 meses', '2 años', '5 conejos', '150 gramos', 'Conejos domésticos', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(9, '100 gramos', '3 kg', '1 mes', '2 años', '100 peces', '15 gramos', 'Peces de estanque', 1);

INSERT INTO regla_racion
(id_especie, peso_min, peso_max, edad_min, edad_max, cantidad_animales, porcion_diaria, observaciones, activo)
VALUES
(10, '20 kg', '60 kg', '6 meses', '10 años', '1 cabra', '1800 gramos', 'Cabras adultas', 1);

COMMIT;

----------------------------------------
-- 19. RESEÑAS
----------------------------------------

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(1, 1, 5, 'Excelente producto, buena calidad y rendimiento.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(2, 2, 4, 'Buen alimento para gatos, lo recomiendo.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(3, 3, 5, 'El producto funcionó muy bien para el crecimiento.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(4, 4, 4, 'Buena calidad para gallinas ponedoras.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(5, 5, 5, 'Buen rendimiento en ganado lechero.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(6, 6, 4, 'Producto de buena calidad para caballos.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(7, 7, 5, 'Los conejos aceptaron muy bien el alimento.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(8, 8, 4, 'Buen alimento para peces de estanque.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(9, 9, 5, 'El suplemento es útil y de buena calidad.');

INSERT INTO resena
(id_usuario, id_producto, calificacion, comentario)
VALUES
(10, 10, 4, 'Comedero resistente y fácil de limpiar.');

COMMIT;

----------------------------------------
-- 20. FACTURAS
----------------------------------------

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(1, 'FAC-0001', 12750, 'EMITIDA');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(2, 'FAC-0002', 9025, 'EMITIDA');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(3, 'FAC-0003', 15440, 'EMITIDA');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(4, 'FAC-0004', 13200, 'EMITIDA');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(5, 'FAC-0005', 19775, 'PENDIENTE');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(6, 'FAC-0006', 18200, 'EMITIDA');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(7, 'FAC-0007', 11530, 'PENDIENTE');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(8, 'FAC-0008', 13000, 'EMITIDA');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(9, 'FAC-0009', 8180, 'PENDIENTE');

INSERT INTO factura
(id_pedido, numero_factura, total, estado)
VALUES
(10, 'FAC-0010', 5325, 'EMITIDA');

COMMIT;

----------------------------------------
-- 21. ESTADO_CUENTA
----------------------------------------

INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (1, 0);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (2, 9025);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (3, 0);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (4, 5200);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (5, 4775);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (6, 1000);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (7, 3530);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (8, 0);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (9, 3180);
INSERT INTO estado_cuenta (id_usuario, saldo_actual)VALUES (10, 0);
COMMIT;

----------------------------------------
-- 22. LISTA_DESEOS
----------------------------------------

INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(1, 2);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(2, 1);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(3, 5);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(4, 3);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(5, 7);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(6, 4);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(7, 9);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(8, 6);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(9, 10);
INSERT INTO lista_deseos(id_usuario, id_producto)VALUES(10, 8);
COMMIT;

----------------------------------------
-- 23. ALERTAS DE STOCK
----------------------------------------
INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (1, 'El producto alcanzó el stock mínimo.', 'PENDIENTE');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (2, 'El producto tiene existencias bajas.', 'PENDIENTE');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (3, 'Se recomienda realizar un nuevo pedido.', 'PENDIENTE');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (4, 'Stock por debajo del mínimo establecido.', 'ATENDIDA');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (5, 'El inventario requiere reposición.', 'PENDIENTE');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (6, 'Cantidad insuficiente para futuras ventas.', 'PENDIENTE');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (7, 'Producto próximo a agotarse.', 'ATENDIDA');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (8, 'Se detectó un nivel bajo de inventario.', 'PENDIENTE');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (9, 'Reposición recomendada.', 'PENDIENTE');

INSERT INTO alerta_stock (id_producto, mensaje, estado)
VALUES (10, 'Inventario por debajo del límite permitido.', 'ATENDIDA');

COMMIT;

----------------------------------------
-- 24. HISTORIAL_PRECIO
----------------------------------------

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(1, 11500, 12500, 'admin');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(2, 8000, 8500, 'vendedor1');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(3, 15800, 16800, 'inventario1');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(4, 14500, 15200, 'vendedor2');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(5, 17500, 18500, 'admin');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(6, 20000, 21000, 'supervisor1');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(7, 9200, 9800, 'inventario1');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(8, 10800, 11500, 'vendedor1');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(9, 6800, 7200, 'admin');

INSERT INTO historial_precio(id_producto, precio_anterior, precio_nuevo, usuario_modifica)
VALUES(10, 4000, 4500, 'vendedor2');

COMMIT;

----------------------------------------
-- 25. COTIZACIONES
----------------------------------------

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(1, DATE '2026-08-15', 12500, 1625, 1000, 13125, 'GENERADA', 'cotizacion_001.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(2, DATE '2026-08-16', 8500, 1105, 500, 9105, 'GENERADA', 'cotizacion_002.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(3, DATE '2026-08-17', 16800, 2184, 1500, 17484, 'ACEPTADA', 'cotizacion_003.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(4, DATE '2026-08-18', 15200, 1976, 1200, 15976, 'GENERADA', 'cotizacion_004.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(5, DATE '2026-08-19', 18500, 2405, 2000, 18905, 'VENCIDA', 'cotizacion_005.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(6, DATE '2026-08-20', 21000, 2730, 2500, 21230, 'ACEPTADA', 'cotizacion_006.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(7, DATE '2026-08-21', 9800, 1274, 800, 10274, 'GENERADA', 'cotizacion_007.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(8, DATE '2026-08-22', 11500, 1495, 1000, 11995, 'CANCELADA', 'cotizacion_008.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(9, DATE '2026-08-23', 7200, 936, 500, 7636, 'GENERADA', 'cotizacion_009.pdf');

INSERT INTO cotizacion(id_usuario, fecha_vencimiento, subtotal, impuesto, descuento, total, estado, ruta_pdf)
VALUES(10, DATE '2026-08-24', 4500, 585, 300, 4785, 'ACEPTADA', 'cotizacion_010.pdf');

COMMIT;

----------------------------------------
-- 26. COTIZACION_DETALLE
----------------------------------------

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(1, 1, 1, 12500, 12500);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(2, 2, 1, 8500, 8500);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(3, 3, 1, 16800, 16800);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(4, 4, 1, 15200, 15200);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(5, 5, 1, 18500, 18500);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(6, 6, 1, 21000, 21000);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(7, 7, 1, 9800, 9800);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(8, 8, 1, 11500, 11500);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(9, 9, 1, 7200, 7200);

INSERT INTO cotizacion_detalle(id_cotizacion, id_producto, cantidad, precio_unitario, subtotal)
VALUES(10, 10, 1, 4500, 4500);

COMMIT;


















