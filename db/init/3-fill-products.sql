-- Insert categories
INSERT INTO
    available_categories (name, description)
VALUES
    ('electronics', 'Electronic devices and gadgets');

-- Insert products
INSERT INTO
    products (name, description, price, quantity)
VALUES
    (
        'Laptop',
        'A high-performance laptop',
        9999.99,
        0
    ),
    ('Smartphone', 'A modern smartphone', 15999.99, 15),
    (
        'Tablet',
        'A versatile tablet for work and play',
        5399.99,
        20
    ),
    (
        'Headphones',
        'Noise-canceling headphones',
        2199.99,
        25
    ),
    (
        'Smartwatch',
        'A smartwatch with fitness tracking',
        4299.99,
        30
    ),
    (
        'Desktop PC',
        'A powerful desktop computer',
        21199.99,
        5
    ),
    (
        'Gaming Console',
        'Next-gen gaming console',
        5499.99,
        50
    ),
    ('Smart TV', 'A 55-inch 4K smart TV', 8899.99, 8),
    (
        'Camera',
        'Digital SLR camera with zoom lens',
        9799.99,
        12
    ),
    (
        'Bluetooth Speaker',
        'Portable Bluetooth speaker',
        649.99,
        35
    ),
    ('Drone', 'A drone with 4K camera', 5399.99, 18),
    (
        'External Hard Drive',
        '2TB external hard drive',
        899.99,
        40
    ),
    ('Monitor', '27-inch LED monitor', 249.99, 22),
    ('Keyboard', 'Mechanical keyboard', 2399.99, 60),
    ('Mouse', 'Wireless optical mouse', 1499.99, 55),
    ('Router', 'Wi-Fi 6 router', 179.99, 18),
    ('Printer', 'All-in-one printer', 129.99, 10),
    (
        'Smart Light Bulb',
        'Wi-Fi connected smart light bulb',
        299.99,
        70
    ),
    (
        'AR Headset',
        'Augmented reality headset',
        34999.99,
        5
    ),
    (
        'E-Reader',
        'E-Reader with high-resolution display',
        1499.99,
        25
    );

-- Insert categories for the new products
-- INSERT INTO
--     product_categories (product_id, category)
-- VALUES
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Laptop'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Smartphone'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Tablet'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Headphones'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Smartwatch'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Desktop PC'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Gaming Console'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Smart TV'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Camera'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Bluetooth Speaker'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Drone'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'External Hard Drive'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Monitor'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Keyboard'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Mouse'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Router'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Printer'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'Smart Light Bulb'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'VR Headset'
--         ),
--         'electronics'
--     ),
--     (
--         (
--             SELECT
--                 id
--             FROM
--                 products
--             WHERE
--                 name = 'E-Reader'
--         ),
--         'electronics'
--     );