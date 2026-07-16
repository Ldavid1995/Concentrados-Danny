

-------------------TABLAS BASE Y CATÁLOGOS


-- Tabla de especies
CREATE TABLE especie (
    id_especie      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_especie  VARCHAR2(50) NOT NULL UNIQUE
);

-- Tabla de Productos
CREATE TABLE producto (
    id_producto     NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre          VARCHAR2(100) NOT NULL,
    marca           VARCHAR2(50),
    unidad_medida   VARCHAR2(20),
    precio          NUMBER NOT NULL CHECK (precio >= 0),
    existencias     NUMBER NOT NULL CHECK (existencias >= 0),
    stock_minimo    NUMBER DEFAULT 5 NOT NULL CHECK (stock_minimo >= 0), 
    ruta_imagen     VARCHAR2(1024),
    ficha_tecnica   VARCHAR2(255),
    activo          NUMBER(1) DEFAULT 1 CHECK (activo IN (0, 1))
);

-- Relación de muchos a muchos Producto-Especie
CREATE TABLE producto_especie (
    id_producto     NUMBER NOT NULL,
    id_especie      NUMBER NOT NULL,
    CONSTRAINT pk_prod_esp PRIMARY KEY (id_producto, id_especie),
    CONSTRAINT fk_prod_esp_prod FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE,
    CONSTRAINT fk_prod_esp_esp FOREIGN KEY (id_especie) REFERENCES especie(id_especie) ON DELETE CASCADE
);

-- Tabla de alertas de inventario
CREATE TABLE alerta_stock (
    id_alerta       NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_producto     NUMBER NOT NULL,
    mensaje         VARCHAR2(255) NOT NULL,
    fecha_alerta    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alerta_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE
);

-- Tabla de Zonas de Cobertura
CREATE TABLE zona_cobertura (
    id_zona         NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_zona     VARCHAR2(100) NOT NULL UNIQUE,
    descripcion     VARCHAR2(255),
    costo_envio     NUMBER DEFAULT 0 CHECK (costo_envio >= 0),
    activo          NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

-- Tabla de Cupones
CREATE TABLE cupon (
    id_cupon        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo          VARCHAR2(50) NOT NULL UNIQUE,
    descuento       NUMBER NOT NULL CHECK (descuento BETWEEN 1 AND 100),
    fecha_vence     DATE NOT NULL,
    activo          NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

-- Tabla de Promociones u Ofertas
CREATE TABLE promocion (
    id_promocion    NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_promo    VARCHAR2(100) NOT NULL,
    descuento_porc  NUMBER NOT NULL CHECK (descuento_porc BETWEEN 1 AND 100),
    fecha_inicio    DATE NOT NULL,
    fecha_fin       DATE NOT NULL,
    activo          NUMBER(1) DEFAULT 1 CHECK (activo IN (0,1))
);

-- Tabla de Horarios Disponibles para entregas
CREATE TABLE horario_entrega (
    id_horario      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bloque_horario  VARCHAR2(100) NOT NULL UNIQUE,
    cupo_maximo     NUMBER DEFAULT 10
);


-- SEGURIDAD (USUARIOS, ROLES Y TOKENS)


CREATE TABLE usuario (
    id_usuario      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username        VARCHAR2(20) NOT NULL UNIQUE,
    password        VARCHAR2(500) NOT NULL,
    nombre          VARCHAR2(30) NOT NULL,
    apellidos       VARCHAR2(30) NOT NULL,
    correo          VARCHAR2(75) UNIQUE,
    telefono        VARCHAR2(15),
    ruta_imagen     VARCHAR2(1024),
    activo          NUMBER(1) DEFAULT 1 CHECK (activo IN (0, 1))
);

CREATE INDEX ndx_username ON usuario(username);

-- Tabla de tokens de recuperación con fecha de vencimiento
CREATE TABLE token_recuperacion (
    id_token         NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario       NUMBER NOT NULL,
    token            VARCHAR2(255) NOT NULL UNIQUE,
    fecha_expiracion TIMESTAMP NOT NULL,
    usado            NUMBER(1) DEFAULT 0 CHECK (usado IN (0,1)),
    CONSTRAINT fk_token_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE rol (
    id_rol          NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre          VARCHAR2(30) NOT NULL,
    id_usuario      NUMBER NOT NULL,
    CONSTRAINT fk_rol_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE ruta (
    id_ruta         NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ruta            VARCHAR2(255) NOT NULL,
    id_rol          NUMBER DEFAULT NULL,
    requiere_rol    NUMBER(1) DEFAULT 1 CHECK (requiere_rol IN (0, 1)),
    CONSTRAINT fk_ruta_rol FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
);


-- TRANSACCIONES, PEDIDOS Y PAGOS


-- Tabla de Pedidos (Con fecha, hora de entrega y horario)
CREATE TABLE pedido (
    id_pedido       NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario      NUMBER NOT NULL,
    id_horario      NUMBER NOT NULL, 
    id_zona         NUMBER,          
    fecha_pedido    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_entrega   DATE NOT NULL,  
    hora_entrega    VARCHAR2(10),
    direccion       VARCHAR2(255),
    estado_pedido   VARCHAR2(50) DEFAULT 'PENDIENTE',
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_pedido_horario FOREIGN KEY (id_horario) REFERENCES horario_entrega(id_horario),
    CONSTRAINT fk_pedido_zona FOREIGN KEY (id_zona) REFERENCES zona_cobertura(id_zona)
);

-- Tabla de Ventas 
CREATE TABLE venta (
    id_venta        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario      NUMBER NOT NULL,
    id_pedido       NUMBER,
    total           NUMBER NOT NULL CHECK (total >= 0),
    fecha           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_venta_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_venta_pedido FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
);

CREATE TABLE venta_detalle (
    id_detalle      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_venta        NUMBER NOT NULL,
    id_producto     NUMBER NOT NULL,
    precio          NUMBER NOT NULL CHECK (precio >= 0),
    cantidad        NUMBER NOT NULL CHECK (cantidad > 0),
    CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta) REFERENCES venta(id_venta) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Tabla de Pagos con todos los campos solicitados
CREATE TABLE pago (
    id_pago             NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_venta            NUMBER NOT NULL,
    monto               NUMBER NOT NULL CHECK (monto > 0),
    estado_pago         VARCHAR2(50) NOT NULL,        
    referencia_bancaria VARCHAR2(100),                
    autorizacion        VARCHAR2(100),                
    fecha_pago          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pago_venta FOREIGN KEY (id_venta) REFERENCES venta(id_venta) ON DELETE CASCADE
);


-- FACTURAS, CUENTAS, RESEÑAS Y COTIZACIONES


-- Tabla de Facturas
CREATE TABLE factura (
    id_factura      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_venta        NUMBER NOT NULL,
    num_factura     VARCHAR2(50) NOT NULL UNIQUE,
    fecha_emision   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    monto_neto      NUMBER NOT NULL,
    impuesto        NUMBER NOT NULL,
    total           NUMBER NOT NULL,
    CONSTRAINT fk_factura_venta FOREIGN KEY (id_venta) REFERENCES venta(id_venta) ON DELETE CASCADE
);

-- Estados de Cuenta de los Clientes
CREATE TABLE estado_cuenta (
    id_estado       NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario      NUMBER NOT NULL UNIQUE,
    saldo_actual    NUMBER DEFAULT 0,
    limite_credito  NUMBER DEFAULT 0,
    fecha_corte     DATE,
    CONSTRAINT fk_estado_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla de Reseñas de productos
CREATE TABLE resena (
    id_resena       NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario      NUMBER NOT NULL,
    id_producto     NUMBER NOT NULL,
    calificacion    NUMBER CHECK (calificacion BETWEEN 1 AND 5),
    comentario      VARCHAR2(1000),
    fecha_resena    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_resena_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_resena_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE
);

-- Tabla de Lista de Deseos
CREATE TABLE lista_deseos (
    id_lista        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario      NUMBER NOT NULL,
    id_producto     NUMBER NOT NULL,
    fecha_agregado  DATE DEFAULT SYSDATE,
    CONSTRAINT fk_deseo_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_deseo_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE
);

-- Reglas de Ración por especie, peso, edad o cantidad
CREATE TABLE regla_racion (
    id_regla        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_especie      NUMBER NOT NULL,
    peso_min_kg     NUMBER,
    peso_max_kg     NUMBER,
    edad_meses_min  NUMBER,
    edad_meses_max  NUMBER,
    porcion_diaria_gr NUMBER NOT NULL,
    CONSTRAINT fk_regla_especie FOREIGN KEY (id_especie) REFERENCES especie(id_especie) ON DELETE CASCADE
);

-- Tablas de Cotizaciones y su Detalle
CREATE TABLE cotizacion (
    id_cotizacion   NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario      NUMBER NOT NULL,
    fecha_cotiza    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_estimado  NUMBER DEFAULT 0,
    validez_dias    NUMBER DEFAULT 30,
    CONSTRAINT fk_cotizacion_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE cotizacion_detalle (
    id_detalle_cot  NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_cotizacion   NUMBER NOT NULL,
    id_producto     NUMBER NOT NULL,
    cantidad        NUMBER NOT NULL CHECK (cantidad > 0),
    precio_unidad   NUMBER NOT NULL,
    CONSTRAINT fk_det_cotizacion FOREIGN KEY (id_cotizacion) REFERENCES cotizacion(id_cotizacion) ON DELETE CASCADE,
    CONSTRAINT fk_det_cot_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE
);



----------------------------------------INSERTS DE PRUEBA-----------------------------


-- 1. Especies
INSERT INTO especie (nombre_especie) VALUES ('Perros');
INSERT INTO especie (nombre_especie) VALUES ('Gatos');
INSERT INTO especie (nombre_especie) VALUES ('Cerdos');

-- 2. Horarios disponibles 
INSERT INTO horario_entrega (bloque_horario) VALUES ('Mañana (8:00 AM - 12:00 MD)');
INSERT INTO horario_entrega (bloque_horario) VALUES ('Tarde (1:00 PM - 5:00 PM)');

-- 3. Usuarios
INSERT INTO usuario (username, password, nombre, apellidos, correo, activo) 
VALUES ('admin', '123', 'Luis David', 'Averruz', 'admin@danny.com', 1);

INSERT INTO usuario (username, password, nombre, apellidos, correo, activo) 
VALUES ('vendedor', '456', 'Dariana', 'Vendedora', 'ventas@danny.com', 1);

INSERT INTO usuario (username, password, nombre, apellidos, correo, activo) 
VALUES ('juan', '456', 'Juan', 'Cliente', 'juan@correo.com', 1);

-- 4. Roles
INSERT INTO rol (nombre, id_usuario) VALUES ('ROLE_ADMIN', 1);
INSERT INTO rol (nombre, id_usuario) VALUES ('ROLE_VENDEDOR', 2);
INSERT INTO rol (nombre, id_usuario) VALUES ('ROLE_USER', 3);

-- 5. Productos 
INSERT INTO producto (nombre, marca, unidad_medida, precio, existencias, stock_minimo) 
VALUES ('Concentrado Adulto', 'Dogui', 'Saco', 12500, 20, 5);

INSERT INTO producto (nombre, marca, unidad_medida, precio, existencias, stock_minimo) 
VALUES ('Concentrado Gatitos', 'Whiskas', 'Saco', 8500, 15, 4);

-- 6. Relacionar Productos con sus Especies (HU 11)
INSERT INTO producto_especie (id_producto, id_especie) VALUES (1, 1); 
INSERT INTO producto_especie (id_producto, id_especie) VALUES (2, 2); 

COMMIT;

--------------------------------------------------------------------------------------------
CREATE OR REPLACE PACKAGE pkg_productos AS
    -- Procedimiento para crear productos
    PROCEDURE crear_producto(
        p_nombre IN VARCHAR2,
        p_marca IN VARCHAR2,
        p_unidad IN VARCHAR2,
        p_precio IN NUMBER,
        p_existencias IN NUMBER,
        p_stock_min IN NUMBER,
        p_id OUT NUMBER
    );
    
    -- Procedimiento para actualizar productos
    PROCEDURE actualizar_producto(
        p_id IN NUMBER,
        p_nombre IN VARCHAR2,
        p_marca IN VARCHAR2,
        p_precio IN NUMBER,
        p_existencias IN NUMBER
    );

-- Función para obtener el precio de un producto
    FUNCTION obtener_precio(p_id IN NUMBER) RETURN NUMBER;
END pkg_productos;
/

CREATE OR REPLACE PACKAGE BODY pkg_productos AS
    PROCEDURE crear_producto(
        p_nombre IN VARCHAR2,
        p_marca IN VARCHAR2,
        p_unidad IN VARCHAR2,
        p_precio IN NUMBER,
        p_existencias IN NUMBER,
        p_stock_min IN NUMBER,
        p_id OUT NUMBER
    ) AS
    BEGIN
        INSERT INTO producto (nombre, marca, unidad_medida, precio, existencias, stock_minimo, activo)
        VALUES (p_nombre, p_marca, p_unidad, p_precio, p_existencias, p_stock_min, 1)
        RETURNING id_producto INTO p_id;
        COMMIT;
    END crear_producto;

    PROCEDURE actualizar_producto(
        p_id IN NUMBER,
        p_nombre IN VARCHAR2,
        p_marca IN VARCHAR2,
        p_precio IN NUMBER,
        p_existencias IN NUMBER
    ) AS
    BEGIN
        UPDATE producto 
        SET nombre = p_nombre, marca = p_marca, precio = p_precio, existencias = p_existencias
        WHERE id_producto = p_id;
        COMMIT;
    END actualizar_producto;

    FUNCTION obtener_precio(p_id IN NUMBER) RETURN NUMBER AS
        v_precio NUMBER;
    BEGIN
        SELECT precio INTO v_precio FROM producto WHERE id_producto = p_id;
        RETURN v_precio;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
    END obtener_precio;
END pkg_productos;
/

-----------------------------------------------------------------------
CREATE OR REPLACE PACKAGE pkg_usuarios AS
    -- Función para validar credenciales (HU 1)
    FUNCTION verificar_usuario(p_username IN VARCHAR2, p_password IN VARCHAR2) RETURN NUMBER;
    
    -- Procedimiento para generar token de recuperación (HU 1)
    PROCEDURE generar_token(p_id_usuario IN NUMBER, p_token OUT VARCHAR2);
END pkg_usuarios;
/

CREATE OR REPLACE PACKAGE BODY pkg_usuarios AS
    FUNCTION verificar_usuario(p_username IN VARCHAR2, p_password IN VARCHAR2) RETURN NUMBER AS
        v_valido NUMBER := 0;
    BEGIN
        SELECT COUNT(*) INTO v_valido 
        FROM usuario 
        WHERE username = p_username AND password = p_password AND activo = 1;
        RETURN v_valido; -- Retorna 1 si existe, 0 si no
    END verificar_usuario;

    PROCEDURE generar_token(p_id_usuario IN NUMBER, p_token OUT VARCHAR2) AS
    BEGIN
        p_token := SYS_GUID(); -- Genera un token único aleatorio
        INSERT INTO token_recuperacion (id_usuario, token, fecha_expiracion, usado)
        VALUES (p_id_usuario, p_token, SYSDATE + 1, 0); -- Vence en 1 día
        COMMIT;
    END generar_token;
END pkg_usuarios;
/

----------------------------------------------------------------------------------

---------* PROCEDIMIENTOS *------------------
--1.SP_CREAR_PEDIDO
CREATE OR REPLACE PROCEDURE sp_crear_pedido(
    p_id_usuario IN NUMBER,
    p_id_horario IN NUMBER,
    p_id_zona IN NUMBER,
    p_fecha_entrega IN DATE,
    p_hora_entrega IN VARCHAR2,
    p_direccion IN VARCHAR2,
    p_id_pedido OUT NUMBER
) AS
BEGIN
    INSERT INTO pedido (id_usuario, id_horario, id_zona, fecha_entrega, hora_entrega, direccion, estado_pedido)
    VALUES (p_id_usuario, p_id_horario, p_id_zona, p_fecha_entrega, p_hora_entrega, p_direccion, 'PENDIENTE')
    RETURNING id_pedido INTO p_id_pedido;
    COMMIT;
END;
/

--2.SP_PROCESAR_PAGO
CREATE OR REPLACE PROCEDURE sp_procesar_pago(
    p_id_venta IN NUMBER,
    p_monto IN NUMBER,
    p_referencia IN VARCHAR2,
    p_autorizacion IN VARCHAR2,
    p_estado OUT VARCHAR2
) AS
BEGIN
    -- Lógica simulada de aprobación bancaria
    IF p_monto > 0 THEN
        p_estado := 'APROBADO';
    ELSE
        p_estado := 'RECHAZADO';
    END IF;

    INSERT INTO pago (id_venta, monto, estado_pago, referencia_bancaria, autorizacion)
    VALUES (p_id_venta, p_monto, p_estado, p_referencia, p_autorizacion);
    COMMIT;
END;
/

--------------* Funciones *---------------------
--1.FN_CALCULAR_DESCUENTO_CUPON

CREATE OR REPLACE FUNCTION fn_calcular_descuento_cupon(
    p_codigo_cupon IN VARCHAR2,
    p_monto_total IN NUMBER
) RETURN NUMBER AS
    v_porcentaje NUMBER := 0;
    v_descuento NUMBER := 0;
BEGIN
    SELECT descuento INTO v_porcentaje 
    FROM cupon 
    WHERE codigo = p_codigo_cupon AND fecha_vence >= SYSDATE AND activo = 1;
    
    v_descuento := p_monto_total * (v_porcentaje / 100);
    RETURN v_descuento;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0; 
END;
/

--2.FN_CONSULTAR_SALDO_CLIENTE

CREATE OR REPLACE FUNCTION fn_consultar_saldo_cliente(
    p_id_usuario IN NUMBER
) RETURN NUMBER AS
    v_saldo NUMBER := 0;
BEGIN
    SELECT saldo_actual INTO v_saldo 
    FROM estado_cuenta 
    WHERE id_usuario = p_id_usuario;
    
    RETURN v_saldo;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
END;
/

---------------* Triggers *------------------

--1.TRG_ALERTA_STOCK

CREATE OR REPLACE TRIGGER trg_alerta_stock
AFTER UPDATE OF existencias ON producto
FOR EACH ROW
BEGIN
    IF :NEW.existencias <= :NEW.stock_minimo THEN
        INSERT INTO alerta_stock (id_producto, mensaje)
        VALUES (:NEW.id_producto, '¡ALERTA! El producto ' || :NEW.nombre || ' tiene un inventario bajo de: ' || :NEW.existencias || ' unidades.');
    END IF;
END;
/

--2.TRG_AUDIT_PRECIOS

CREATE OR REPLACE TRIGGER trg_audit_precios
BEFORE UPDATE OF precio ON producto
FOR EACH ROW
BEGIN
-- Si el precio varía más del 50%, se puede levantar una excepción o alertar
    IF :NEW.precio < (:OLD.precio * 0.5) THEN
        DBMS_OUTPUT.PUT_LINE('Advertencia: El precio bajó más de la mitad.');
    END IF;
END;
/

----------* Vistas *-------------------------------

--1.VW_PRODUCTOS_POR_ESPECIE

CREATE OR REPLACE VIEW vw_productos_por_especie AS
SELECT p.id_producto, p.nombre, p.marca, p.precio, p.existencias, e.nombre_especie
FROM producto p
JOIN producto_especie pe ON p.id_producto = pe.id_producto
JOIN especie e ON pe.id_especie = e.id_especie;


--2.VW_DETALLE_PEDIDOS_ZONAS

CREATE OR REPLACE VIEW vw_detalle_pedidos_zonas AS
SELECT ped.id_pedido, u.nombre || ' ' || u.apellidos AS cliente, z.nombre_zona, z.costo_envio, ped.fecha_entrega, ped.estado_pedido
FROM pedido ped
JOIN usuario u ON ped.id_usuario = u.id_usuario
JOIN zona_cobertura z ON ped.id_zona = z.id_zona;


-------------------------------------------------------------------------
-------------------* Cursores *----------------------------

CREATE OR REPLACE PROCEDURE sp_reporte_stock_critico AS

    CURSOR c_productos_bajos IS
        SELECT nombre, existencias, stock_minimo 
        FROM producto 
        WHERE existencias <= stock_minimo;
        
    v_nombre producto.nombre%TYPE;
    v_stock producto.existencias%TYPE;
    v_min producto.stock_minimo%TYPE;
BEGIN
    OPEN c_productos_bajos;
    LOOP
        FETCH c_productos_bajos INTO v_nombre, v_stock, v_min;
        EXIT WHEN c_productos_bajos%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('PRODUCTO CRÍTICO: ' || v_nombre || ' | Stock actual: ' || v_stock || ' (Mínimo: ' || v_min || ')');
    END LOOP;
    CLOSE c_productos_bajos;
END;
/

-----------------------------Pruebas-------------------------------






