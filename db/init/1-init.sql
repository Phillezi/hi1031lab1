-- CREATE DATABASE webshop;
--\c webshop;
-- Jag tänker att vi kör snake case här
-- Så slipper vi dumma misstag / ändringar
-- mellan camelCase o casing på saker.
-- Dvs allt lowercase och separerat med _
--
----
--
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
        FOREIGN KEY (product_id) REFERENCES products (id),
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
    orders (
        id SERIAL PRIMARY KEY,
        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        product_id INT,
        customer_id INT,
        FOREIGN KEY (product_id) REFERENCES products (id),
        FOREIGN KEY (customer_id) REFERENCES user_t (id)
    );

CREATE TABLE
    ordered_products (
        product_id INT,
        order_id INT,
        FOREIGN KEY (product_id) REFERENCES products (id),
        FOREIGN KEY (order_id) REFERENCES orders (id),
        PRIMARY KEY (product_id, order_id)
    );

CREATE TABLE
    campaigns (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255),
        description VARCHAR(255),
        start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        end_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '1 week',
        discount_percent INT,
        UNIQUE (name, start_time)
    );

-- either whole product categories are in a campaign or a products
CREATE TABLE
    in_campaign (
        id SERIAL PRIMARY KEY,
        product_id INT,
        campaign_id INT,
        category VARCHAR(255),
        FOREIGN KEY (product_id) REFERENCES products (id),
        FOREIGN KEY (campaign_id) REFERENCES campaigns (id),
        FOREIGN KEY (category) REFERENCES available_categories (name),
        UNIQUE (product_id, campaign_id, category)
    );