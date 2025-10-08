CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT(20) NOT NULL,
    `name` VARCHAR(80) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(60) NOT NULL,
    `address` TINYTEXT,
    `phone` VARCHAR(12),

    PRIMARY KEY (`id`),
    UNIQUE INDEX unique_index_email (`email`)
);

CREATE TABLE IF NOT EXISTS `areas` (
    `id` BIGINT(20) NOT NULL,
    `name` VARCHAR(100) NOT NULL,

    PRIMARY KEY (`id`),
    UNIQUE INDEX unique_index_name (`name`)
);

CREATE TABLE IF NOT EXISTS `user_areas` (
    `id` BIGINT(20) NOT NULL,
    `id_user` BIGINT(20) NOT NULL,
    `id_area` BIGINT(20) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_area`) REFERENCES `areas` (`id`) ON DELETE CASCADE,
    UNIQUE INDEX unique_index_user_area (`id_user`, `id_area`)
);

CREATE TABLE IF NOT EXISTS `products` (
    `id` BIGINT(20) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `description` TINYTEXT,
    `price` FLOAT(8, 2),
    `preparation_area` BIGINT(20),
    `preparation_time` TINYINT UNSIGNED DEFAULT 0,
    `active` BOOLEAN NOT NULL DEFAULT true,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`preparation_area`) REFERENCES `areas` (`id`) ON DELETE SET NULL,
    UNIQUE INDEX unique_index_name (`name`)
);

CREATE TABLE IF NOT EXISTS `daymenus` (
    `id` BIGINT(20) NOT NULL,
    `creation` DATE NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id`) REFERENCES `products` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `subproducts` (
    `id` BIGINT(20) NOT NULL,
    `id_daymenu` BIGINT(20) NOT NULL,
    `id_subproduct` BIGINT(20) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_daymenu`) REFERENCES `daymenus` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_subproduct`) REFERENCES `products` (`id`) ON DELETE CASCADE,
    UNIQUE INDEX unique_index_product_subproduct (`id_daymenu`, `id_subproduct`)
);

CREATE TABLE IF NOT EXISTS `categories` (
    `id` BIGINT(20) NOT NULL,
    `name` VARCHAR(100) NOT NULL,

    PRIMARY KEY (`id`),
    UNIQUE INDEX unique_index_name (`name`)
);

CREATE TABLE IF NOT EXISTS `product_categories` (
    `id` BIGINT(20) NOT NULL,
    `id_product` BIGINT(20) NOT NULL,
    `id_category` BIGINT(20) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_product`) REFERENCES `products` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_category`) REFERENCES `categories` (`id`) ON DELETE CASCADE,
    UNIQUE INDEX unique_index_product_category (`id_product`, `id_category`)
);

CREATE TABLE IF NOT EXISTS `category_position` (
    `id` BIGINT(20) NOT NULL,
    `id_daymenu` BIGINT(20) NOT NULL,
    `id_category` BIGINT(20) NOT NULL,
    `position` TINYINT(2) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_daymenu`) REFERENCES `daymenus` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_category`) REFERENCES `categories` (`id`) ON DELETE CASCADE,
    UNIQUE INDEX unique_index_daymenu_category (`id_daymenu`, `id_category`)
);

CREATE TABLE IF NOT EXISTS `tables` (
    `id` BIGINT(20) NOT NULL,
    `name` VARCHAR(50) NOT NULL,

    PRIMARY KEY (`id`),
    UNIQUE INDEX unique_index_name (`name`)
);

CREATE TABLE IF NOT EXISTS `orders` (
    `id` BIGINT(20) NOT NULL,
    `status` ENUM('pending', 'completed'),
    `taked_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `id_table` BIGINT(20) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_table`) REFERENCES `tables` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `client_order` (
    `id` BIGINT(20) NOT NULL,
    `status` ENUM('pending', 'completed'),
    `id_order` BIGINT(20) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_order`) REFERENCES `orders` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `order_details` (
    `id` BIGINT(20) NOT NULL,
    `id_order` BIGINT(20) NOT NULL,
    `id_product` BIGINT(20) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `price` VARCHAR(100) NOT NULL,
    `quantity` TINYINT UNSIGNED NOT NULL DEFAULT 1,
    `observations` TINYTEXT,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_order`) REFERENCES `client_order` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_product`) REFERENCES `products` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `order_detail_subproducts` (
    `id` BIGINT(20) NOT NULL,
    `id_detail` BIGINT(20) NOT NULL,
    `name` BIGINT(20) NOT NULL,
    `observations` TINYTEXT,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_detail`) REFERENCES `order_details` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `refresh_tokens` (
    `id` BIGINT(20) NOT NULL,
    `hash` VARCHAR(255) NOT NULL,
    `revoked_at` DATETIME,
    `id_user` BIGINT(20),

    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    UNIQUE INDEX unique_index_hash (`hash`)
);