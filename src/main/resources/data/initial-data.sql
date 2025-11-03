-- ======================
-- USUARIOS
-- ======================
INSERT INTO users (document, name, email, password, address, phone)
VALUES
    ('1001', 'Carlos Gómez', 'carlos@restaurante.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg2gLbDd1.8a7.8e5Cne7e7QYjW', 'Calle 10 #12-30', '3001234567'),
    ('1002', 'María López', 'maria@restaurante.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg2gLbDd1.8a7.8e5Cne7e7QYjW', 'Carrera 8 #15-45', '3002345678'),
    ('1003', 'Javier Ruiz', 'javier@restaurante.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg2gLbDd1.8a7.8e5Cne7e7QYjW', 'Calle 5 #7-20', '3003456789'),
    ('1004', 'Laura Torres', 'laura@restaurante.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg2gLbDd1.8a7.8e5Cne7e7QYjW', 'Av Bolívar #20-15', '3004567890'),
    ('1005', 'Ana Pérez', 'ana@restaurante.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg2gLbDd1.8a7.8e5Cne7e7QYjW', 'Calle 3 #9-18', '3005678901'),
    ('1006', 'Pedro Ramírez', 'pedro@restaurante.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg2gLbDd1.8a7.8e5Cne7e7QYjW', 'Calle 15 #22-10', '3006789012'),
    ('1007', 'Sofia Castro', 'sofia@restaurante.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg2gLbDd1.8a7.8e5Cne7e7QYjW', 'Carrera 12 #18-25', '3007890123'),
    ('1008', 'Miguel Ángel', 'miguel@restaurante.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IJsg2gLbDd1.8a7.8e5Cne7e7QYjW', 'Calle 8 #14-30', '3008901234');

-- ======================
-- ÁREAS ESPECÍFICAS
-- ======================
INSERT INTO areas (name)
VALUES
    ('Cocina'),
    ('Bar'),
    ('Servicio'),
    ('Parrilla');

-- ======================
-- ASIGNACIÓN USUARIOS-ÁREAS
-- ======================
INSERT INTO user_areas (id_user, id_area)
VALUES
    (1, 1),  -- Carlos en Cocina
    (2, 2),  -- María en Bar
    (3, 3),  -- Javier en Servicio
    (4, 3),  -- Laura en Servicio
    (5, 1),  -- Ana en Cocina
    (6, 4),  -- Pedro en Parrilla
    (7, 1),  -- Sofia en Cocina
    (8, 3);  -- Miguel en Servicio

-- ======================
-- CATEGORÍAS DE PRODUCTOS COMPLETAS
-- ======================
INSERT INTO categories (name)
VALUES
    ('Sopas'),
    ('Principios'),
    ('Proteínas'),
    ('Bebidas'),
    ('Postres'),
    ('Entradas'),
    ('Platos Fuertes'),
    ('Parrilladas'),
    ('Menú del Día'),
    ('Acompañamientos'),
    ('Ensaladas'),
    ('Cafetería');

-- ======================
-- PRODUCTOS VARIADOS CON PRECIOS EN PESOS COLOMBIANOS
-- ======================
INSERT INTO products (name, description, price, preparation_area, preparation_time, active)
VALUES
    -- SOPAS (Área: Cocina)
    ('Sopa de Pollo', 'Sopa casera con pollo, papa y verduras', 12000, 1, 15, TRUE),
    ('Crema de Espinacas', 'Crema suave de espinacas con queso', 10000, 1, 12, TRUE),
    ('Ajiaco Santafereño', 'Tradicional ajiaco con pollo, papa y guascas', 15000, 1, 25, TRUE),

    -- PRINCIPIOS (Área: Cocina)
    ('Arroz Blanco', 'Arroz blanco cocido al vapor', 4000, 1, 10, TRUE),
    ('Papa Salada', 'Papa cocida con sal al gusto', 5000, 1, 12, TRUE),
    ('Lentejas', 'Lentejas guisadas con verduras', 8000, 1, 20, TRUE),
    ('Puré de Papa', 'Puré cremoso de papa con mantequilla', 6000, 1, 15, TRUE),

    -- PROTEÍNAS (Área: Parrilla/Cocina)
    ('Pechuga a la Plancha', 'Pechuga de pollo a la plancha', 18000, 4, 18, TRUE),
    ('Carne Asada', 'Carne de res asada 200gr', 22000, 4, 20, TRUE),
    ('Pescado Frito', 'Filete de pescado frito crujiente', 20000, 1, 15, TRUE),
    ('Cerdo en Salsa', 'Lomo de cerdo en salsa agridulce', 19000, 1, 22, TRUE),
    ('Pollo Horneado', 'Pollo entero horneado con especias', 25000, 4, 30, TRUE),

    -- BEBIDAS (Área: Bar)
    ('Jugo de Naranja', 'Jugo natural de naranja recién exprimido', 7000, 2, 3, TRUE),
    ('Limonada Natural', 'Limonada fresca con hielo', 6000, 2, 2, TRUE),
    ('Coca Cola', 'Gaseosa Coca Cola 400ml', 5000, 2, 1, TRUE),
    ('Agua Mineral', 'Agua mineral sin gas 500ml', 4000, 2, 1, TRUE),
    ('Cerveza Águila', 'Cerveza nacional botella 330ml', 8000, 2, 1, TRUE),
    ('Vino Tinto', 'Copa de vino tino de la casa', 12000, 2, 2, TRUE),

    -- POSTRES (Área: Cocina)
    ('Flan de Vainilla', 'Flan casero de vainilla con caramelo', 8000, 1, 5, TRUE),
    ('Tres Leches', 'Pastel tres leches tradicional', 9000, 1, 5, TRUE),
    ('Helado de Chocolate', 'Bola de helado de chocolate', 6000, 1, 2, TRUE),
    ('Brownie con Helado', 'Brownie de chocolate con helado de vainilla', 11000, 1, 5, TRUE),

    -- PRODUCTOS DE SERVICIO (Sin tiempo de preparación)
    ('Pan Casero', 'Pan recién horneado', 3000, 3, NULL, TRUE),
    ('Mantequilla', 'Porción de mantequilla', 2000, 3, NULL, TRUE),
    ('Salsa de la Casa', 'Salsa especial de la casa', 2500, 3, NULL, TRUE),
    ('Aguardiente', 'Trago de aguardiente antioqueño', 10000, 2, NULL, TRUE),

    -- MENÚS DEL DÍA (Sin área específica - es un producto compuesto)
    ('Menú del Día Tradicional', 'Menú completo: sopa + principio + proteína + bebida', 25000, NULL, 25, TRUE),
    ('Menú del Día Especial', 'Menú premium con opciones gourmet', 35000, NULL, 30, TRUE);

-- ======================
-- PRODUCTOS - CATEGORÍAS
-- ======================
INSERT INTO product_categories (id_product, id_category)
VALUES
    -- Sopas
    (1, 1), (2, 1), (3, 1),
    -- Principios
    (4, 2), (5, 2), (6, 2), (7, 2),
    -- Proteínas
    (8, 3), (9, 3), (10, 3), (11, 3), (12, 3),
    -- Bebidas
    (13, 4), (14, 4), (15, 4), (16, 4), (17, 4), (18, 4),
    -- Postres
    (19, 5), (20, 5), (21, 5), (22, 5),
    -- Productos de Servicio
    (23, 10), (24, 10), (25, 9), (26, 4),
    -- Menús del Día
    (27, 9), (28, 9);

-- ======================
-- MENÚS DEL DÍA
-- ======================
INSERT INTO daymenus (id, creation)
VALUES
    (27, CURDATE()),  -- Menú del día actual
    (28, DATE_ADD(CURDATE(), INTERVAL 1 DAY));  -- Menú del día siguiente

-- ======================
-- MENÚ DEL DÍA ACTUAL - CATEGORÍAS Y PRODUCTOS
-- ======================
INSERT INTO daymenu_categories (id_daymenu, id_category, position)
VALUES
    (27, 1, 1),  -- Sopas (posición 1)
    (27, 2, 2),  -- Principios (posición 2)
    (27, 3, 3),  -- Proteínas (posición 3)
    (27, 4, 4);  -- Bebidas (posición 4)

-- Asignar productos al menú del día actual (opciones por categoría)
INSERT INTO daymenu_products (id_daymenu_category, id_subproduct)
VALUES
    -- Sopas (2 opciones)
    (1, 1), (1, 2),
    -- Principios (3 opciones)
    (2, 4), (2, 5), (2, 6),
    -- Proteínas (4 opciones)
    (3, 8), (3, 9), (3, 10), (3, 11),
    -- Bebidas (4 opciones)
    (4, 13), (4, 14), (4, 15), (4, 16);

-- ======================
-- MESAS
-- ======================
INSERT INTO tables (name)
VALUES
    ('Mesa 1'),
    ('Mesa 2'),
    ('Mesa 3'),
    ('Mesa 4'),
    ('Mesa 5'),
    ('Mesa 6'),
    ('Mesa 7'),
    ('Mesa 8');

-- ======================
-- PEDIDOS CON DIFERENTES ESTADOS
-- ======================
INSERT INTO orders (status, taked_at, id_table, id_user, total)
VALUES
    -- Pedidos PENDING del día actual
    ('PENDING', NOW(), 1, 3, 37000),
    ('PENDING', NOW(), 2, 3, 45000),
    ('PENDING', NOW(), 3, 3, 28000),

    -- Pedidos COMPLETED del día actual
    ('COMPLETED', NOW(), 4, 3, 52000),
    ('COMPLETED', NOW(), 5, 3, 31000),

    -- Pedidos CANCELLED del día actual
    ('CANCELLED', NOW(), 6, 3, 0),

    -- Pedidos de días anteriores (no deben afectar estado de mesas)
    ('COMPLETED', DATE_SUB(NOW(), INTERVAL 1 DAY), 7, 3, 48000),
    ('PENDING', DATE_SUB(NOW(), INTERVAL 2 DAY), 8, 3, 35000);

-- ======================
-- CLIENT ORDERS
-- ======================
INSERT INTO client_orders (status, id_order, total)
VALUES
    ('PENDING', 1, 37000),
    ('PENDING', 2, 45000),
    ('PENDING', 3, 28000),
    ('COMPLETED', 4, 52000),
    ('COMPLETED', 5, 31000),
    ('CANCELLED', 6, 0),
    ('COMPLETED', 7, 48000),
    ('PENDING', 8, 35000);

-- ======================
-- DETALLES DE PEDIDOS VARIADOS
-- ======================
INSERT INTO order_details (id_order, id_product, name, price, quantity, observations)
VALUES
    -- Pedido 1 (PENDING) - Menú del Día + extras
    (1, 27, 'Menú del Día Tradicional', 25000, 1, 'Sin picante'),
    (1, 19, 'Flan de Vainilla', 8000, 1, ''),
    (1, 23, 'Pan Casero', 3000, 1, 'Tostado'),

    -- Pedido 2 (PENDING) - Carne completa
    (2, 9, 'Carne Asada', 22000, 1, 'Término medio'),
    (2, 4, 'Arroz Blanco', 4000, 1, ''),
    (2, 5, 'Papa Salada', 5000, 1, ''),
    (2, 17, 'Cerveza Águila', 8000, 1, 'Fría'),
    (2, 22, 'Brownie con Helado', 11000, 1, ''),

    -- Pedido 3 (PENDING) - Pechuga + extras
    (3, 8, 'Pechuga a la Plancha', 18000, 1, 'Bien cocida'),
    (3, 14, 'Limonada Natural', 6000, 1, 'Con mucha azúcar'),
    (3, 23, 'Pan Casero', 3000, 2, 'Tostado'),

    -- Pedido 4 (COMPLETED) - Menú Especial + vino
    (4, 28, 'Menú del Día Especial', 35000, 1, ''),
    (4, 18, 'Vino Tinto', 12000, 1, ''),
    (4, 20, 'Tres Leches', 9000, 1, ''),

    -- Pedido 5 (COMPLETED) - Pescado completo
    (5, 10, 'Pescado Frito', 20000, 1, 'Con limón'),
    (5, 7, 'Puré de Papa', 6000, 1, ''),
    (5, 16, 'Agua Mineral', 4000, 1, ''),

    -- Pedido 6 (CANCELLED) - Solo pollo
    (6, 12, 'Pollo Horneado', 25000, 1, ''),

    -- Pedido 7 (COMPLETED - día anterior) - Ajiaco
    (7, 3, 'Ajiaco Santafereño', 15000, 2, 'Con todas las guarniciones'),
    (7, 26, 'Aguardiente', 10000, 1, ''),
    (7, 21, 'Helado de Chocolate', 6000, 2, ''),

    -- Pedido 8 (PENDING - día anterior) - Cerdo
    (8, 11, 'Cerdo en Salsa', 19000, 1, ''),
    (8, 6, 'Lentejas', 8000, 1, ''),
    (8, 15, 'Coca Cola', 5000, 1, '');

-- ======================
-- SUBPRODUCTOS DE DETALLES (para productos normales)
-- ======================
INSERT INTO order_detail_subproducts (id_detail, name, observations)
VALUES
    (2, 'Caramelo extra', 'Por separado'),
    (3, 'Salsa de la casa', 'Extra'),
    (4, 'Papas criollas', 'Bien doradas'),
    (5, 'Ensalada adicional', 'Con tomate'),
    (6, 'Pan de acompañamiento', 'Tostado'),
    (7, 'Sirope extra', 'Chocolate'),
    (8, 'Guarnición especial', 'Verduras al vapor'),
    (9, 'Aderezo ranch', 'Por separado');
