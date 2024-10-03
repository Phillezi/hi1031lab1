INSERT INTO
    available_permissions (name, description)
VALUES
    (
        'administrate',
        'Permission to administer the system'
    ),
    ('update_orders', 'Permission to update orders'),
    ('edit_products', 'Permission to edit products'),
    ('add_products', 'Permission to add products'),
    ('view_inventory', 'Permission to view inventory'), -- Permission for inventory
    ('pack_orders', 'Permission to pack orders'), -- Permission for warehouse staff
    ('view_orders', 'Permission to view all orders'), -- Permission to view orders
    (
        'change_categories',
        'Permission to change product categories'
    ), -- Permission to change categories
    (
        'change_products',
        'Permission to change product details'
    );

-- Permission to change product details
INSERT INTO
    available_roles (name, description)
VALUES
    ('admin', 'Administrator with full rights'),
    (
        'warehouse',
        'Warehouse staff with permission to pack orders and view inventory'
    ),
    (
        'customer',
        'Customer with permission to place orders and view their own information'
    );

INSERT INTO
    permissions_t (role, permission)
VALUES
    ('admin', 'administrate'),
    ('admin', 'edit_products'),
    ('admin', 'add_products'),
    ('admin', 'update_orders'),
    ('admin', 'view_orders'), -- Admin can view all orders
    ('admin', 'change_categories'), -- Admin can change product categories
    ('admin', 'change_products'), -- Admin can change product details
    ('warehouse', 'update_orders'),
    ('warehouse', 'pack_orders'), -- Warehouse staff can pack orders
    ('warehouse', 'view_inventory'), -- Warehouse staff can view inventory
    ('warehouse', 'view_orders') -- Warehouse staff can view orders