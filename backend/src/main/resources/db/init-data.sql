-- Inserciones de ejemplo para desarrollo/pruebas
-- Asegúrate de que las tablas ya existan o confía en JPA para crearlas (ddl-auto: update)

INSERT /*+ IGNORE_ROW_ON_DUPKEY_INDEX(clientes, (id)) */ INTO clientes (id, nombre, email) VALUES (1, 'Juan Pérez', 'juan.perez@correo.com');
INSERT /*+ IGNORE_ROW_ON_DUPKEY_INDEX(clientes, (id)) */ INTO clientes (id, nombre, email) VALUES (2, 'María López', 'maria.lopez@correo.com');

INSERT /*+ IGNORE_ROW_ON_DUPKEY_INDEX(productos, (id)) */ INTO productos (id, nombre, precio, stock) VALUES (1, 'Producto A', 100.00, 50);
INSERT /*+ IGNORE_ROW_ON_DUPKEY_INDEX(productos, (id)) */ INTO productos (id, nombre, precio, stock) VALUES (2, 'Producto B', 250.50, 30);
INSERT /*+ IGNORE_ROW_ON_DUPKEY_INDEX(productos, (id)) */ INTO productos (id, nombre, precio, stock) VALUES (3, 'Producto C', 75.25, 100);

-- Ejemplo de venta con items: si confías en JPA, normalmente no insertas con SQL manual
-- Este bloque es solo ilustrativo; la columna fecha puede requerir formato según Oracle.
-- INSERT INTO ventas (id, fecha, cliente_id) VALUES (1, TO_DATE('2025-06-01 10:30:00','YYYY-MM-DD HH24:MI:SS'), 1);
-- INSERT INTO venta_items (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (1, 1, 1, 2, 100.00);
