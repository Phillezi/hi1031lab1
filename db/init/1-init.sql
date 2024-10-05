CREATE TABLE
    user_t (
        id SERIAL PRIMARY KEY,
        email VARCHAR(255),
        name VARCHAR(255),
        hashed_pw VARCHAR,
        UNIQUE (email)
    );

CREATE TABLE
    available_permissions (
        name VARCHAR(255) PRIMARY KEY,
        description VARCHAR
    );

CREATE TABLE
    available_roles (
        name VARCHAR(255) PRIMARY KEY,
        description VARCHAR
    );

CREATE TABLE
    permissions_t (
        role VARCHAR(255),
        permission VARCHAR(255),
        FOREIGN KEY (role) REFERENCES available_roles (name) ON DELETE CASCADE,
        FOREIGN KEY (permission) REFERENCES available_permissions (name) ON DELETE CASCADE,
        PRIMARY KEY (role, permission)
    );

CREATE TABLE
    roles (
        role VARCHAR(255),
        user_id INT,
        FOREIGN KEY (role) REFERENCES available_roles (name) ON DELETE CASCADE,
        FOREIGN KEY (user_id) REFERENCES user_t (id) ON DELETE CASCADE,
        PRIMARY KEY (user_id, role)
    );

CREATE TABLE
    available_categories (
        name VARCHAR(255) PRIMARY KEY,
        description VARCHAR
    );

CREATE TABLE
    products (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255),
        description VARCHAR,
        price DECIMAL(10, 2),
        quantity INT DEFAULT 1,
        removed BOOLEAN DEFAULT FALSE
    );

CREATE TABLE
    product_images (
        image_url VARCHAR,
        product_id INT,
        FOREIGN KEY (product_id) REFERENCES products (id)
    );

CREATE TABLE
    product_properties (
        key VARCHAR(255),
        value VARCHAR(255),
        product_id INT,
        FOREIGN KEY (product_id) REFERENCES products (id),
        PRIMARY KEY (key, product_id)
    );

CREATE TABLE
    product_categories (
        product_id INT,
        category VARCHAR(255),
        FOREIGN KEY (product_id) REFERENCES products (id),
        FOREIGN KEY (category) REFERENCES available_categories (name),
        PRIMARY KEY (product_id, category)
    );

CREATE TABLE
    available_status (
        name VARCHAR PRIMARY KEY
    );

INSERT INTO available_status (name) VALUES ( 'received' );
INSERT INTO available_status (name) VALUES ( 'packed' );
INSERT INTO available_status (name) VALUES ( 'shipped' );
INSERT INTO available_status (name) VALUES ( 'delivered' );
INSERT INTO available_status (name) VALUES ( 'error' );

CREATE TABLE
    orders (
        id SERIAL PRIMARY KEY,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        delivered_at TIMESTAMP,
        delivery_address VARCHAR,
        customer_id INT,
        FOREIGN KEY (customer_id) REFERENCES user_t (id)
    );

CREATE TABLE
    order_status (
        order_id INT,
        status VARCHAR,
        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (order_id) REFERENCES orders (id),
        FOREIGN KEY (status) REFERENCES available_status (name),
        PRIMARY KEY (order_id, timestamp)
    );

CREATE TABLE
    ordered_products (
        product_id INT,
        order_id INT,
        product_price DECIMAL(10, 2),
        FOREIGN KEY (product_id) REFERENCES products (id),
        FOREIGN KEY (order_id) REFERENCES orders (id),
        PRIMARY KEY (product_id, order_id)
    );