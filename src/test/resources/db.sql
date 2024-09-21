-- db.sql
CREATE TABLE IF NOT EXISTS ordered_documents (
                                                 id SERIAL PRIMARY KEY,
                                                 name_of_the_certificate VARCHAR(255),
    type_of_the_certificate VARCHAR(255),
    date_of_creation DATE DEFAULT CURRENT_DATE,
    certificate_number VARCHAR(50) UNIQUE,
    user_id BIGINT
    );

CREATE TABLE IF NOT EXISTS certificate (
                                           id SERIAL PRIMARY KEY,
                                           name_of_the_certificate VARCHAR(255),
    date_of_creation DATE DEFAULT CURRENT_DATE,
    certificate_number VARCHAR(50) UNIQUE,
    user_id BIGINT,
    type_of_the_certificate VARCHAR(255) NOT NULL
    );

INSERT INTO certificate (name_of_the_certificate, certificate_number, user_id, type_of_the_certificate)
VALUES
    ('Сертификат 1', '1', 1, 'Certificate'),
    ('Сертификат 2', '2', 1, 'Certificate'),
    ('Сертификат 3', '3', 1, 'Certificate');
