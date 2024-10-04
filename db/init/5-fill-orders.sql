INSERT INTO orders (customer_id) VALUES (
    (SELECT id FROM user_t LIMIT 1)
);