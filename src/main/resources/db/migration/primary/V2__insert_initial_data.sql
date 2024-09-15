-- Вставка начальных данных в таблицу SalaryOffset
INSERT INTO salary_offset (name_of_the_document, official_position, date_of_creation, user_id, document_number, sum_of_money)
VALUES
    ('Salary Offset Document 0', 'Position 0', CURRENT_DATE, 1, 'SO00001', 0.00),
    ('Salary Offset Document 1', 'Position 1', CURRENT_DATE, 1, 'SO00002', 1.00),
    ('Salary Offset Document 2', 'Position 2', CURRENT_DATE, 1, 'SO00003', 2.00),
    ('Salary Offset Document 14', 'Position 14', CURRENT_DATE, 1, 'SO00015', 14.00);

-- Вставка данных в таблицу VacationWithoutSalary
INSERT INTO vacation_without_salary (vacation_start_date, vacation_end_date, reason, approval_status, user_id, document_number, date_of_creation, name_of_the_document, official_position)
VALUES
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VWS00001', CURRENT_DATE, 'Vacation Without Salary Document 1', 'Position 1'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VWS00002', CURRENT_DATE, 'Vacation Without Salary Document 2', 'Position 2'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VWS00003', CURRENT_DATE, 'Vacation Without Salary Document 3', 'Position 3'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VWS00004', CURRENT_DATE, 'Vacation Without Salary Document 4', 'Position 4'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VWS00005', CURRENT_DATE, 'Vacation Without Salary Document 5', 'Position 5');

-- Вставка начальных данных в таблицу VacationWithSalary
INSERT INTO vacation_with_salary (vacation_start_date, vacation_end_date, reason, approval_status, user_id, document_number, date_of_creation, name_of_the_document, official_position)
VALUES
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VOS00001', CURRENT_DATE, 'Vacation With Salary Document 1', 'Position 1'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VOS00002', CURRENT_DATE, 'Vacation With Salary Document 2', 'Position 2'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VOS00003', CURRENT_DATE, 'Vacation With Salary Document 3', 'Position 3'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VOS00004', CURRENT_DATE, 'Vacation With Salary Document 4', 'Position 4'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Personal', 'Approved', 1, 'VOS00005', CURRENT_DATE, 'Vacation With Salary Document 5', 'Position 5');

-- Вставка начальных данных в таблицу Certificate
INSERT INTO certificate (user_id, name_of_the_certificate, type_of_the_certificate, certificate_number)
VALUES
    (1, 'Certificate Name 0', 'Certificate', 'CERT00001'),
    (1, 'Certificate Name 1', 'Certificate', 'CERT00002'),
    (1, 'Certificate Name 2', 'Certificate', 'CERT00003'),
    (1, 'Certificate Name 3', 'Certificate', 'CERT00004'),
    (1, 'Certificate Name 4', 'Certificate', 'CERT00005');

-- Вставка начальных данных в таблицу TaxCertificate
INSERT INTO tax_certificate (user_id, name_of_the_certificate, type_of_the_certificate, certificate_number)
VALUES
    (1, 'Certificate Name 0', 'TaxCertificate', 'TXCRT00001'),
    (1, 'Certificate Name 1', 'TaxCertificate', 'TXCRT00002'),
    (1, 'Certificate Name 2', 'TaxCertificate', 'TXCRT00003'),
    (1, 'Certificate Name 3', 'TaxCertificate', 'TXCRT00004'),
    (1, 'Certificate Name 4', 'TaxCertificate', 'TXCRT00005');