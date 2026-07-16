
-- 2. Crear el usuario nuevamente
CREATE USER Concentrados IDENTIFIED BY "Proyecto$2026";

-- 3. Asignarle espacio de almacenamiento ilimitado
ALTER USER Concentrados QUOTA UNLIMITED ON DATA;

-- 4. Otorgarle los privilegios de desarrollo necesarios
GRANT CREATE SESSION, CREATE TABLE, CREATE VIEW, CREATE SEQUENCE, CREATE PROCEDURE, CREATE TRIGGER TO Concentrados;