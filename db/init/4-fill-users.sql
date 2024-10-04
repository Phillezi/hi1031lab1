INSERT INTO
    user_t (email, name, hashed_pw)
VALUES
    (
        'email@example.com',
        'John Smith',
        '$2b$12$ygBfLb1JXcIBLsiMIR684u6d.THZr2dXikxhSBChiU.olZk1WsQUm'
    );

INSERT INTO
    roles (role, user_id)
VALUES
    (
        'admin',
        (SELECT id FROM user_t WHERE email = 'email@example.com')
    );