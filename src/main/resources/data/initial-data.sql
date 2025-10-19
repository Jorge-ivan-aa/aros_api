-- ======================
-- USUARIOS
-- ======================
INSERT INTO users (document, name, email, password, address, phone)
VALUES
    ('1001', 'Carlos Gómez', 'carlos@restaurante.com', '123456', 'Calle 10 #12-30', '3001234567'),
    ('1002', 'María López', 'maria@restaurante.com', '123456', 'Carrera 8 #15-45', '3002345678'),
    ('1003', 'Javier Ruiz', 'javier@restaurante.com', '123456', 'Calle 5 #7-20', '3003456789'),
    ('1004', 'Laura Torres', 'laura@restaurante.com', '123456', 'Av Bolívar #20-15', '3004567890'),
    ('1005', 'Ana Pérez', 'ana@restaurante.com', '123456', 'Calle 3 #9-18', '3005678901');

-- ======================
-- ÁREAS
-- ======================
INSERT INTO areas (name)
VALUES
    ('Cocina'),
    ('Bebidas'),
    ('Postres'),
    ('Parrilla'),
    ('Caja');

-- ======================
-- ASIGNACIÓN USUARIOS-ÁREAS
-- ======================
INSERT INTO user_areas (id_user, id_area)
VALUES
    (1, 1), -- Carlos en Cocina
    (2, 2), -- María en Bebidas
    (3, 3), -- Javier en Postres
    (4, 4), -- Laura en Parrilla
    (5, 5); -- Ana en Caja

-- ======================
-- CATEGORÍAS DE PRODUCTOS
-- ======================
INSERT INTO categories (name)
VALUES
    ('Entradas'),
    ('Platos Fuertes'),
    ('Bebidas'),
    ('Postres'),
    ('Parrilladas');

-- ======================
-- PRODUCTOS
-- ======================
INSERT INTO products (name, description, price, preparation_area, preparation_time)
VALUES
    ('Ensalada César', 'Ensalada fresca con aderezo César', 12000, 1, 5),
    ('Lomo a la Parrilla', 'Lomo de res jugoso con papas criollas', 25000, 4, 20),
    ('Jugo de Mango', 'Jugo natural preparado al momento', 8000, 2, 3),
    ('Brownie con Helado', 'Brownie de chocolate con bola de helado', 10000, 3, 7),
    ('Pollo a la Plancha', 'Pechuga de pollo acompañada con arroz y ensalada', 20000, 1, 15);

-- ======================
-- PRODUCTOS - CATEGORÍAS
-- ======================
INSERT INTO product_categories (id_product, id_category)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 2);

-- ======================
-- MENÚ DEL DÍA
-- ======================
INSERT INTO daymenus (creation)
VALUES
    ('2025-10-19');

-- ======================
-- MENÚ - CATEGORÍAS
-- ======================
INSERT INTO daymenu_categories (id_daymenu, id_category, position)
VALUES
    (1, 1, 1),
    (1, 2, 2),
    (1, 3, 3),
    (1, 4, 4),
    (1, 5, 5);

-- ======================
-- MENÚ - PRODUCTOS
-- ======================
INSERT INTO daymenu_products (id_daymenu_category, id_subproduct)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- ======================
-- MESAS
-- ======================
INSERT INTO tables (name)
VALUES
    ('Mesa 1'),
    ('Mesa 2'),
    ('Mesa 3'),
    ('Mesa 4'),
    ('Mesa 5');

-- ======================
-- PEDIDOS
-- ======================
INSERT INTO orders (status, id_table, total)
VALUES
    ('Pendiente', 1, 37000),
    ('Entregado', 2, 33000),
    ('Pendiente', 3, 45000),
    ('En preparación', 4, 28000),
    ('Cancelado', 5, 0);

-- ======================
-- CLIENT ORDERS
-- ======================
INSERT INTO client_orders (status, id_order, total)
VALUES
    ('Pendiente', 1, 37000),
    ('Entregado', 2, 33000),
    ('Pendiente', 3, 45000),
    ('En preparación', 4, 28000),
    ('Cancelado', 5, 0);

-- ======================
-- DETALLES DE PEDIDOS
-- ======================
INSERT INTO order_details (id_order, id_product, name, price, quantity, observations)
VALUES
    (1, 1, 'Ensalada César', 12000, 1, 'Sin aderezo'),
    (1, 3, 'Jugo de Mango', 8000, 1, 'Poco azúcar'),
    (2, 2, 'Lomo a la Parrilla', 25000, 1, 'Término medio'),
    (3, 4, 'Brownie con Helado', 10000, 1, ''),
    (4, 5, 'Pollo a la Plancha', 20000, 2, 'Sin arroz');

-- ======================
-- SUBPRODUCTOS DE DETALLES
-- ======================
INSERT INTO order_detail_subproducts (id_detail, name, observations)
VALUES
    (1, 'Pan de acompañamiento', 'Tostado'),
    (2, 'Hielo', 'Pocos cubos'),
    (3, 'Papas criollas', ''),
    (4, 'Sirope extra', 'Chocolate'),
    (5, 'Ensalada adicional', 'Con tomate');

-- ======================
-- TOKENS DE SESIÓN
-- ======================
INSERT INTO refresh_tokens (hash, user_id, user_email)
VALUES
    ('token123abc', 1, 'carlos@restaurante.com'),
    ('token456def', 2, 'maria@restaurante.com'),
    ('token789ghi', 3, 'javier@restaurante.com'),
    ('token101jkl', 4, 'laura@restaurante.com'),
    ('token202mno', 5, 'ana@restaurante.com');
