INSERT INTO
    user_t (email, name, hashed_pw)
VALUES
    (
        'admin@example.com',
        'John Smith',
        '$2a$10$ItalLAnJwcFD5VtTC.S0I.du.nJDtcUmIKjqrVRVuXVn36XC4Pr7e' -- admin
    );

INSERT INTO
    roles (role, user_id)
VALUES
    (
        'admin',
        (SELECT id FROM user_t WHERE email = 'email@example.com')
    );