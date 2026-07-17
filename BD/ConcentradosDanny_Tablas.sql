/* ============================================================
                PROYECTO: CONCENTRADOS DANNY
   ============================================================ */


/* ============================================================
                           1. TABLA ROL
   ============================================================ */

CREATE TABLE rol (
    id_rol NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_rol VARCHAR2(30) NOT NULL
);

/* ============================================================
                    2. Tabla de Usuarios
   ============================================================ */

CREATE TABLE usuario (
    id_usuario NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_rol NUMBER NOT NULL,
    username VARCHAR2(30) NOT NULL UNIQUE,
    password VARCHAR2(25) NOT NULL,
    nombre VARCHAR2(30) NOT NULL,
    apellidos VARCHAR2(50) NOT NULL,
    correo VARCHAR2(50) NOT NULL UNIQUE,
    telefono VARCHAR2(20),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1)),
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol)
        REFERENCES rol(id_rol)
);

/* ============================================================
              3. Tabla de Tokens de Recuperación
   ============================================================ */

CREATE TABLE token_recuperacion (
    id_token NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    token VARCHAR2(255) NOT NULL UNIQUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    fecha_expiracion TIMESTAMP NOT NULL,
    usado NUMBER(1) DEFAULT 0 CHECK (usado IN (0,1)),
    CONSTRAINT fk_token_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

/* ============================================================
                       4. Tabla de Direcciones
   ============================================================ */

CREATE TABLE direccion (
    id_direccion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    direccion VARCHAR2(255) NOT NULL,
    provincia VARCHAR2(50) NOT NULL,
    canton VARCHAR2(50) NOT NULL,
    distrito VARCHAR2(50),
    codigo_postal VARCHAR2(15),
    referencia VARCHAR2(255),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1)),
    CONSTRAINT fk_direccion_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

/* ============================================================
                    5. Tabla de Categorías
   ============================================================ */

CREATE TABLE categoria (
    id_categoria NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_categoria VARCHAR2(100) NOT NULL UNIQUE,
    descripcion VARCHAR2(255),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

/* ============================================================
                   6. Tabla de Especies
   ============================================================ */

CREATE TABLE especie (
    id_especie NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_especie VARCHAR2(50) NOT NULL UNIQUE,
    descripcion VARCHAR2(255),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

/* ============================================================
                    7. Tabla de Productos
   ============================================================ */

CREATE TABLE producto (
    id_producto NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_categoria NUMBER NOT NULL,
    nombre VARCHAR2(100) NOT NULL,
    marca VARCHAR2(70),
    descripcion VARCHAR2(1000),
    unidad_medida VARCHAR2(30),
    precio NUMBER(12,2) NOT NULL CHECK (precio >= 0),
    stock NUMBER NOT NULL CHECK (stock >= 0),
    stock_minimo NUMBER DEFAULT 5 CHECK (stock_minimo >= 0),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1)),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)
);

/* ============================================================
                8. Tabla Relacional Producto-Especie
   ============================================================ */

CREATE TABLE producto_especie (
    id_producto NUMBER NOT NULL,
    id_especie NUMBER NOT NULL,
    PRIMARY KEY (id_producto, id_especie),
    CONSTRAINT fk_producto_especie_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto) ON DELETE CASCADE,
    CONSTRAINT fk_producto_especie_especie FOREIGN KEY (id_especie)
        REFERENCES especie(id_especie) ON DELETE CASCADE
);

/* ============================================================
                      9. Tabla de Lotes
   ============================================================ */

CREATE TABLE lote (
    id_lote NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_producto NUMBER NOT NULL,
    numero_lote VARCHAR2(50) NOT NULL,
    fecha_ingreso DATE DEFAULT SYSDATE NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    cantidad NUMBER NOT NULL CHECK (cantidad >= 0),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1)),
    UNIQUE (id_producto, numero_lote),
    CONSTRAINT fk_lote_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto)
);

/* ============================================================
                  10. Tabla de Zonas de Cobertura
   ============================================================ */

CREATE TABLE zona_cobertura (
    id_zona NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_zona VARCHAR2(100) NOT NULL UNIQUE,
    descripcion VARCHAR2(255),
    costo_envio NUMBER(10,2) DEFAULT 0 CHECK (costo_envio >= 0),
    disponible NUMBER(1) DEFAULT 1 CHECK (disponible IN (0,1))
);

/* ============================================================
                 11. Tabla de Horarios de Entrega
   ============================================================ */

CREATE TABLE horario_entrega (
    id_horario NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_horario VARCHAR2(100) NOT NULL UNIQUE,
    hora_inicio VARCHAR2(5) NOT NULL,
    hora_fin VARCHAR2(5) NOT NULL,
    cupo_maximo NUMBER DEFAULT 10 CHECK (cupo_maximo > 0),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

/* ============================================================
                      12. Tabla de Cupones
   ============================================================ */

CREATE TABLE cupon (
    id_cupon NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo VARCHAR2(50) NOT NULL UNIQUE,
    descuento NUMBER NOT NULL CHECK (descuento BETWEEN 1 AND 100),
    fecha_vence DATE NOT NULL,
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

/* ============================================================
                     13. Tabla de Pedidos   
   ============================================================ */

CREATE TABLE pedido (
    id_pedido NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    id_direccion NUMBER NOT NULL,
    id_horario NUMBER NOT NULL,
    id_zona NUMBER,
    id_cupon NUMBER,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    fecha_entrega DATE NOT NULL,
    hora_entrega VARCHAR2(5) NOT NULL,
    estado VARCHAR2(30) DEFAULT 'PENDIENTE',
    total NUMBER(10,2) DEFAULT 0 CHECK (total >= 0),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario),
    CONSTRAINT fk_pedido_direccion FOREIGN KEY (id_direccion)
        REFERENCES direccion(id_direccion),
    CONSTRAINT fk_pedido_horario FOREIGN KEY (id_horario)
        REFERENCES horario_entrega(id_horario),
    CONSTRAINT fk_pedido_zona FOREIGN KEY (id_zona)
        REFERENCES zona_cobertura(id_zona),
    CONSTRAINT fk_pedido_cupon FOREIGN KEY (id_cupon)
        REFERENCES cupon(id_cupon)
);

/* ============================================================
                    14. Tabla de Pagos
   ============================================================ */

CREATE TABLE pago (
    id_pago NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pedido NUMBER NOT NULL,
    metodo_pago VARCHAR2(50) NOT NULL,
    monto NUMBER(10,2) NOT NULL CHECK (monto > 0),
    estado_pago VARCHAR2(30) DEFAULT 'PENDIENTE',
    referencia_bancaria VARCHAR2(100),
    autorizacion VARCHAR2(100),
    numero_transaccion VARCHAR2(100) UNIQUE,
    fecha_pago TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pago_pedido FOREIGN KEY (id_pedido)
        REFERENCES pedido(id_pedido) ON DELETE CASCADE
);

/* ============================================================
                    15. Tabla de Promociones
   ============================================================ */

CREATE TABLE promocion (
    id_promocion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    descripcion VARCHAR2(255),
    porcentaje_descuento NUMBER NOT NULL CHECK (porcentaje_descuento BETWEEN 1 AND 100),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

/* ============================================================
                  16. Tabla Detalle de Pedido
   ============================================================ */

CREATE TABLE detalle_pedido (
    id_detalle NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pedido NUMBER NOT NULL,
    id_producto NUMBER NOT NULL,
    id_promocion NUMBER,
    cantidad NUMBER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMBER(10,2) NOT NULL CHECK (precio_unitario >= 0),
    descuento NUMBER(10,2) DEFAULT 0 CHECK (descuento >= 0),
    subtotal NUMBER(10,2) NOT NULL CHECK (subtotal >= 0),
    UNIQUE(id_pedido,id_producto),
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (id_pedido)
        REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto),
    CONSTRAINT fk_detalle_promocion FOREIGN KEY (id_promocion)
        REFERENCES promocion(id_promocion)
);


/* ============================================================
              17. Tabla Relacional Producto-Promoción
   ============================================================ */

CREATE TABLE producto_promocion (
    id_producto NUMBER NOT NULL,
    id_promocion NUMBER NOT NULL,
    PRIMARY KEY(id_producto,id_promocion),
    CONSTRAINT fk_producto_promocion_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto) ON DELETE CASCADE,
    CONSTRAINT fk_producto_promocion_promocion FOREIGN KEY (id_promocion)
        REFERENCES promocion(id_promocion) ON DELETE CASCADE
);

/* ============================================================
              18. Tabla de Reglas de Ración
   ============================================================ */

CREATE TABLE regla_racion (
    id_regla NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_especie NUMBER NOT NULL,
    peso_min VARCHAR2(20) NOT NULL,
    peso_max VARCHAR2(20) NOT NULL,
    edad_min VARCHAR2(20) NOT NULL,
    edad_max VARCHAR2(20) NOT NULL,
    cantidad_animales VARCHAR2(30) NOT NULL,
    porcion_diaria VARCHAR2(30) NOT NULL,
    observaciones VARCHAR2(255),
    activo NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1)),
    CONSTRAINT fk_regla_especie FOREIGN KEY (id_especie)
        REFERENCES especie(id_especie)
);

/* ============================================================
                   19. Tabla de Reseñas
   ============================================================ */

CREATE TABLE resena (
    id_resena NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    id_producto NUMBER NOT NULL,
    calificacion NUMBER NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
    comentario VARCHAR2(500),
    fecha_resena TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(id_usuario,id_producto),
    CONSTRAINT fk_resena_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_resena_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto) ON DELETE CASCADE
);

/* ============================================================
                   20. Tabla de Facturas
   ============================================================ */

CREATE TABLE factura (
    id_factura NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pedido NUMBER NOT NULL UNIQUE,
    numero_factura VARCHAR2(50) NOT NULL UNIQUE,
    fecha_emision TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total NUMBER(10,2) NOT NULL,
    estado VARCHAR2(20) DEFAULT 'EMITIDA',
    CONSTRAINT fk_factura_pedido FOREIGN KEY (id_pedido)
        REFERENCES pedido(id_pedido)
);

/* ============================================================
                   21. Tabla Estado de Cuenta
   ============================================================ */

CREATE TABLE estado_cuenta (
    id_estado NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    saldo_actual NUMBER(10,2) DEFAULT 0,
    fecha_generacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_estado_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);

/* ============================================================
                   22. Tabla Lista de Deseos
   ============================================================ */

CREATE TABLE lista_deseos (
    id_lista NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    id_producto NUMBER NOT NULL,
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(id_usuario,id_producto),
    CONSTRAINT fk_lista_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_lista_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto) ON DELETE CASCADE
);

/* ============================================================
                   23. Tabla Alertas de Stock
   ============================================================ */

CREATE TABLE alerta_stock (
    id_alerta NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_producto NUMBER NOT NULL,
    mensaje VARCHAR2(255) NOT NULL,
    fecha_alerta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR2(20) DEFAULT 'PENDIENTE' NOT NULL,
    CONSTRAINT fk_alerta_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto)
);

/* ============================================================
                   24. Tabla Historial de Precios
   ============================================================ */

CREATE TABLE historial_precio (
    id_historial NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_producto NUMBER NOT NULL,
    precio_anterior NUMBER(10,2) NOT NULL,
    precio_nuevo NUMBER(10,2) NOT NULL,
    fecha_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_modifica VARCHAR2(100),
    CONSTRAINT fk_historial_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto) ON DELETE CASCADE
);

/* ============================================================
                   25. Tabla Cotizaciones
   ============================================================ */

CREATE TABLE cotizacion (
    id_cotizacion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario NUMBER NOT NULL,
    fecha_cotizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_vencimiento DATE NOT NULL,
    subtotal NUMBER(10,2) DEFAULT 0,
    impuesto NUMBER(10,2) DEFAULT 0,
    descuento NUMBER(10,2) DEFAULT 0,
    total NUMBER(10,2) DEFAULT 0,
    estado VARCHAR2(20) DEFAULT 'GENERADA',
    ruta_pdf VARCHAR2(500),
    CONSTRAINT fk_cotizacion_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);

/* ============================================================
                   26. Tabla Detalle de Cotización
   ============================================================ */

CREATE TABLE cotizacion_detalle (
    id_detalle NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_cotizacion NUMBER NOT NULL,
    id_producto NUMBER NOT NULL,
    cantidad NUMBER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMBER(10,2) NOT NULL CHECK (precio_unitario >= 0),
    subtotal NUMBER(10,2) NOT NULL CHECK (subtotal >= 0),
    UNIQUE(id_cotizacion,id_producto),
    CONSTRAINT fk_cotizacion_detalle FOREIGN KEY (id_cotizacion)
        REFERENCES cotizacion(id_cotizacion) ON DELETE CASCADE,
    CONSTRAINT fk_cotizacion_producto FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto)
);


