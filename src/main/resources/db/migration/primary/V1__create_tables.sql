-- Создание таблицы SalaryOffset
CREATE TABLE salary_offset (
                               id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                               name_of_the_document VARCHAR(255),
                               official_position VARCHAR(255),
                               date_of_creation DATE,
                               user_id BIGINT,
                               document_number VARCHAR(255),
                               sum_of_money DECIMAL
);

-- Создание таблицы VacationWithoutSalary
CREATE TABLE vacation_without_salary (
                                         id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                         is_with_salary BOOLEAN DEFAULT FALSE,
                                         vacation_start_date DATE,
                                         vacation_end_date DATE,
                                         official_position VARCHAR(255),
                                         reason VARCHAR(255),
                                         approval_status VARCHAR(255),
                                         user_id BIGINT,
                                         document_number VARCHAR(255),
                                         date_of_creation DATE DEFAULT CURRENT_DATE,
                                         name_of_the_document VARCHAR(255)
);

-- Создание таблицы VacationWithSalary
CREATE TABLE vacation_with_salary (
                                      id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                      is_with_salary BOOLEAN DEFAULT TRUE,
                                      vacation_start_date DATE,
                                      vacation_end_date DATE,
                                      reason VARCHAR(255),
                                      approval_status VARCHAR(255),
                                      user_id BIGINT,
                                      official_position VARCHAR(255),
                                      document_number VARCHAR(255),
                                      date_of_creation DATE DEFAULT CURRENT_DATE,
                                      name_of_the_document VARCHAR(255)
);

-- Создание таблицы Certificate
CREATE TABLE certificate (
                             id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                             user_id BIGINT,
                             name_of_the_certificate VARCHAR(255),
                             type_of_the_certificate VARCHAR(255),
                             certificate_number VARCHAR(255),
                             date_of_creation DATE DEFAULT CURRENT_DATE
);

-- Создание таблицы TaxCertificate
CREATE TABLE tax_certificate (
                                 id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                 user_id BIGINT,
                                 name_of_the_certificate VARCHAR(255),
                                 type_of_the_certificate VARCHAR(255),
                                 certificate_number VARCHAR(255),
                                 date_of_creation DATE DEFAULT CURRENT_DATE
);