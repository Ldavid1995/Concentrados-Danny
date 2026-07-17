---------------* TRIGGERS CORREGIDOS *------------------


-- 1. TRG_ALERTA_STOCK
-- Genera la alerta cuando el stock baja hasta el mínimo permitido.

CREATE OR REPLACE TRIGGER trg_alerta_stock
AFTER UPDATE OF stock ON producto
FOR EACH ROW
BEGIN
    IF :NEW.stock <= :NEW.stock_minimo
       AND :OLD.stock > :OLD.stock_minimo THEN

        INSERT INTO alerta_stock (id_producto, mensaje, estado)
        VALUES (
            :NEW.id_producto,
            '¡ALERTA! El producto ' || :NEW.nombre ||
            ' tiene un inventario bajo de ' || :NEW.stock || ' unidades.',
            'PENDIENTE'
        );
    END IF;
END;
/

-- 2. TRG_AUDIT_PRECIOS
-- Registra los cambios de precio en la tabla historial_precio.

CREATE OR REPLACE TRIGGER trg_audit_precios
AFTER UPDATE OF precio ON producto
FOR EACH ROW
BEGIN
    IF :NEW.precio <> :OLD.precio THEN

        INSERT INTO historial_precio
        (id_producto, precio_anterior, precio_nuevo, usuario_modifica)
        VALUES
        (:NEW.id_producto, :OLD.precio, :NEW.precio, USER);

    END IF;
END;
/

-- 3. TRG_ACTUALIZA_STOCK
-- Verifica la existencia disponible y descuenta el stock.

CREATE OR REPLACE TRIGGER trg_actualiza_stock
BEFORE INSERT ON detalle_pedido
FOR EACH ROW
DECLARE
    v_stock producto.stock%TYPE;
BEGIN
    SELECT stock
    INTO v_stock
    FROM producto
    WHERE id_producto = :NEW.id_producto
    FOR UPDATE;

    IF v_stock < :NEW.cantidad THEN
        RAISE_APPLICATION_ERROR(
            -20001,
            'Stock insuficiente. Disponible: ' || v_stock ||
            ', solicitado: ' || :NEW.cantidad
        );
    END IF;

    UPDATE producto
    SET stock = stock - :NEW.cantidad
    WHERE id_producto = :NEW.id_producto;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(
            -20002,
            'No se encontró el producto indicado.'
        );
END;
/

-- 4. TRG_CALCULA_TOTAL_PEDIDO
-- Recalcula el total del pedido sin generar tabla mutante.

CREATE OR REPLACE TRIGGER trg_calcula_total_pedido
FOR INSERT OR UPDATE OR DELETE ON detalle_pedido
COMPOUND TRIGGER

    TYPE t_pedidos IS TABLE OF NUMBER INDEX BY PLS_INTEGER;
    v_pedidos t_pedidos;
    v_contador PLS_INTEGER := 0;

    AFTER EACH ROW IS
        v_id_pedido NUMBER;
    BEGIN
        v_id_pedido := NVL(:NEW.id_pedido, :OLD.id_pedido);

        v_contador := v_contador + 1;
        v_pedidos(v_contador) := v_id_pedido;
    END AFTER EACH ROW;

    AFTER STATEMENT IS
        v_total NUMBER;
    BEGIN
        FOR i IN 1..v_contador LOOP

            SELECT NVL(SUM(subtotal), 0)
            INTO v_total
            FROM detalle_pedido
            WHERE id_pedido = v_pedidos(i);

            UPDATE pedido
            SET total = v_total
            WHERE id_pedido = v_pedidos(i);

        END LOOP;
    END AFTER STATEMENT;

END trg_calcula_total_pedido;
/

-- 5. TRG_ALERTA_VENCIMIENTO_LOTE
-- Genera una alerta para lotes que vencen en 30 días o menos.

CREATE OR REPLACE TRIGGER trg_alerta_vencimiento_lote
AFTER INSERT OR UPDATE OF fecha_vencimiento ON lote
FOR EACH ROW
BEGIN
    IF :NEW.fecha_vencimiento BETWEEN TRUNC(SYSDATE)
                                  AND TRUNC(SYSDATE) + 30 THEN

        INSERT INTO alerta_stock
        (id_producto, mensaje, estado)
        VALUES (
            :NEW.id_producto,
            '¡ALERTA! El lote ' || :NEW.numero_lote ||
            ' vence el ' ||
            TO_CHAR(:NEW.fecha_vencimiento, 'DD/MM/YYYY') ||
            ' y contiene ' || :NEW.cantidad || ' unidades.',
            'PENDIENTE'
        );

    END IF;
END;
/