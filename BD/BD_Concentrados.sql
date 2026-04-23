/*
   Script de Base de Datos para Concentrados Danny
   Desarrollado por: Luis David Averruz y Alberto Alfaro Campos
   Propósito: Gestión de inventarios, usuarios, roles, rutas y ventas.
*/

-- 1. ADMINISTRACIÓN DEL ENTORNO
DROP DATABASE IF EXISTS concentrados_db;
CREATE DATABASE concentrados_db DEFAULT CHARACTER SET utf8mb4;
USE concentrados_db;

-- Limpieza de usuarios (opcional en desarrollo)
DROP USER IF EXISTS 'danny_admin'@'%';
CREATE USER 'danny_admin'@'%' IDENTIFIED BY 'Danny_Clave2026.';
GRANT ALL PRIVILEGES ON concentrados_db.* TO 'danny_admin'@'%';
FLUSH PRIVILEGES;

-- 2. TABLAS DE ESTRUCTURA (CATÁLOGO E INVENTARIO)

CREATE TABLE producto (
  id_producto BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  marca VARCHAR(50),
  especie VARCHAR(50), -- Filtro principal: Perros, Gatos, etc.
  unidad_medida VARCHAR(20),
  precio DOUBLE NOT NULL CHECK (precio >= 0),
  existencias INT NOT NULL CHECK (existencias >= 0),
  ruta_imagen VARCHAR(1024),
  ficha_tecnica VARCHAR(255), -- Para el PDF que mencionas en el doc
  activo BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (id_producto)
) ENGINE = InnoDB;

-- 3. TABLAS DE SEGURIDAD (USUARIOS Y ROLES)

CREATE TABLE usuario (
  id_usuario BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL UNIQUE,
  password VARCHAR(500) NOT NULL, -- Soporte para BCrypt
  nombre VARCHAR(30) NOT NULL,
  apellidos VARCHAR(30) NOT NULL,
  correo VARCHAR(75) UNIQUE,
  telefono VARCHAR(15),
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (id_usuario),
  INDEX ndx_username (username)
) ENGINE = InnoDB;

CREATE TABLE rol (
  id_rol INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(30) NOT NULL, -- ROLE_ADMIN, ROLE_VENDEDOR, ROLE_USER
  id_usuario BIGINT NOT NULL,
  PRIMARY KEY (id_rol),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
) ENGINE = InnoDB;

-- 4. TABLAS DE SEGURIDAD DINÁMICA (RUTAS)

CREATE TABLE ruta (
  id_ruta INT NOT NULL AUTO_INCREMENT,
  ruta VARCHAR(255) NOT NULL,
  id_rol INT DEFAULT NULL,
  requiere_rol BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id_ruta),
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
) ENGINE = InnoDB;

-- 5. TABLAS DE TRANSACCIONES (VENTAS MAESTRO-DETALLE)

CREATE TABLE venta (
  id_venta BIGINT NOT NULL AUTO_INCREMENT,
  id_usuario BIGINT NOT NULL,
  total DOUBLE NOT NULL,
  fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_venta),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE = InnoDB;

CREATE TABLE venta_detalle (
  id_detalle BIGINT NOT NULL AUTO_INCREMENT,
  id_venta BIGINT NOT NULL,
  id_producto BIGINT NOT NULL,
  precio DOUBLE NOT NULL,
  cantidad INT NOT NULL,
  PRIMARY KEY (id_detalle),
  CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES venta(id_venta) ON DELETE CASCADE,
  FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
) ENGINE = InnoDB;

-- 6. CARGA DE DATOS DE PRUEBA (ESTILO TECHSHOP)

-- Usuarios y contraseñas (BCrypt para: admin -> 123, juan -> 456)
INSERT INTO usuario (username, password, nombre, apellidos, correo, activo) VALUES
('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.7uSyLnS', 'Luis David', 'Averruz', 'admin@danny.com', true),
('vendedor', '$2a$10$X8O9jB8p7.8Y9f.6G5H4.OuVGkqRzgVymGe07xd00DMxs.7uSyLnS', 'Dariana', 'Vendedora', 'ventas@danny.com', true),
('juan', '$2a$10$X8O9jB8p7.8Y9f.6G5H4.OuVGkqRzgVymGe07xd00DMxs.7uSyLnS', 'Juan', 'Cliente', 'juan@correo.com', true);

-- Asignación de Roles
INSERT INTO rol (nombre, id_usuario) VALUES 
('ROLE_ADMIN', 1), ('ROLE_VENDEDOR', 1), ('ROLE_USER', 1),
('ROLE_VENDEDOR', 2), ('ROLE_USER', 2),
('ROLE_USER', 3);

-- Productos
INSERT INTO producto (nombre, marca, especie, unidad_medida, precio, existencias, ruta_imagen) VALUES 
('Concentrado Adulto', 'Dogui', 'Perros', 'Saco', 12500, 20, 'https://ejemplo.com/perro.jpg'),
('Concentrado Gatitos', 'Whiskas', 'Gatos', 'Saco', 8500, 15, 'https://ejemplo.com/gato.jpg'),
('Alimento Tilapia', 'Aguas', 'Tilapias', 'Saco', 18000, 30, 'https://ejemplo.com/tilapia.jpg'),
('Concentrado Cerdo', 'Cerditex', 'Cerdos', 'Saco', 22000, 10, 'https://ejemplo.com/cerdo.jpg');

-- Rutas de acceso
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES 
('/', false, NULL),
('/login', false, NULL),
('/js/**', false, NULL),
('/css/**', false, NULL),
('/producto/listado', true, 2), -- Vendedor puede ver lista
('/producto/**', true, 1),        -- Admin tiene acceso total
('/venta/**', true, 2);         -- Vendedor ve ventas

UPDATE usuario SET password = '123' WHERE username = 'admin';
UPDATE usuario SET password = '456' WHERE username = 'vendedor';
UPDATE usuario SET password = '456' WHERE username = 'juan';


SELECT * FROM concentrados_db.producto;