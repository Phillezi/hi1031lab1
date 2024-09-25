INSERT INTO
    available_permissions (name, description)
VALUES
    ('administrate', NULL),
    ('check_orders', NULL),
    ('update_orders', NULL),
    ('edit_products', NULL),
    ('add_products', NULL);

INSERT INTO
    available_roles (name, description)
VALUES
    ('admin', NULL),
    ('warehouse', NULL),
    ('customer', NULL);

INSERT INTO
    permissions_t (role, permission)
VALUES
    ('admin', 'administrate'),
    ('admin', 'edit_products'),
    ('admin', 'add_products'),
    ('admin', 'check_orders'),
    ('admin', 'update_orders'),
    ('warehouse', 'check_orders'),
    ('warehouse', 'update_orders');