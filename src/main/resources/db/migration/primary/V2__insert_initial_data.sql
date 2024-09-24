-- Вставка начальных данных в таблицу SalaryOffset
INSERT INTO salary_offset (name_of_the_document, official_position, date_of_creation, user_id, document_number, sum_of_money)
VALUES
    ('Начисление заработной платы', 'Бухгалтер', CURRENT_DATE, 1, 'SO-2024-001', 32000),
    ('Начисление заработной платы', 'Бухгалтер', CURRENT_DATE, 1, 'SO-2024-002', 35000),
    ('Начисление заработной платы', 'Менеджер', CURRENT_DATE, 2, 'SO-2024-003', 70000),
    ('Начисление заработной платы', 'Менеджер', CURRENT_DATE, 2, 'SO-2024-004', 70000);

-- Вставка данных в таблицу VacationWithoutSalary
INSERT INTO vacation_without_salary (vacation_start_date, vacation_end_date, reason, approval_status, user_id, document_number, date_of_creation, name_of_the_document, official_position)
VALUES
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 1, 'VWS-2024-001', CURRENT_DATE, 'Отпуск без сохранения заработной платы', 'Бухгалтер'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 1, 'VWS-2024-002', CURRENT_DATE, 'Отпуск без сохранения заработной платы', 'Бухгалтер'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 1, 'VWS-2024-003', CURRENT_DATE, 'Отпуск без сохранения заработной платы', 'Бухгалтер'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 2, 'VWS-2024-004', CURRENT_DATE, 'Отпуск без сохранения заработной платы', 'Менеджер'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 2, 'VWS-2024-005', CURRENT_DATE, 'Отпуск без сохранения заработной платы', 'Менеджер');

-- Вставка начальных данных в таблицу VacationWithSalary
INSERT INTO vacation_with_salary (vacation_start_date, vacation_end_date, reason, approval_status, user_id, document_number, date_of_creation, name_of_the_document, official_position)
VALUES
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 1, 'VWO-2024-001', CURRENT_DATE, 'Оплачиваемый отпуск', 'Бухгалтер'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 1, 'VWO-2024-002', CURRENT_DATE, 'Оплачиваемый отпуск', 'Бухгалтер'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 2, 'VWO-2024-003', CURRENT_DATE, 'Оплачиваемый отпуск', 'Менеджер'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 2, 'VWO-2024-004', CURRENT_DATE, 'Оплачиваемый отпуск', 'Менеджер'),
    (CURRENT_DATE, CURRENT_DATE + INTERVAL '7 days', 'Отдых', 'Согласован', 2, 'VWO-2024-005', CURRENT_DATE, 'Оплачиваемый отпуск', 'Менеджер');

-- Вставка начальных данных в таблицу Certificate
INSERT INTO certificate (user_id, name_of_the_certificate, type_of_the_certificate, certificate_number)
VALUES
    (1, 'С места работы', 'Справка', 'SERT-2024-001'),
    (1, 'О доходах', 'Справка', 'SERT-2024-002'),
    (1, 'О неполучении пособия по нетрудоспособности', 'Справка', 'SERT-2024-003'),
    (2, 'С места работы', 'Справка', 'SERT-2024-004'),
    (2, 'О доходах', 'Справка', 'SERT-2024-005');

-- Вставка начальных данных в таблицу TaxCertificate
INSERT INTO tax_certificate (user_id, name_of_the_certificate, type_of_the_certificate, certificate_number)
VALUES
    (1, 'Для декларации', '	2-НДФЛ', 'SERT-2024-001'),
    (1, 'Подтверждение доходов', '	2-НДФЛ', 'SERT-2024-002'),
    (2, 'Подтверждение доходов', '	2-НДФЛ', 'SERT-2024-003'),
    (2, 'Для декларации', '	2-НДФЛ', 'SERT-2024-004'),
    (2, 'Подтверждение доходов', '	2-НДФЛ', 'SERT-2024-005');