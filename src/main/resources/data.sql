INSERT INTO Account (owner_Id, currency, balance)
VALUES (1, 'USD', 100.00);
INSERT INTO Account (owner_Id, currency, balance)
VALUES (2, 'USD', 100.00);
INSERT INTO Account (owner_Id, currency, balance)
VALUES (3, 'EUR', 100.00);
INSERT INTO Account (owner_Id, currency, balance)
VALUES (4, 'EUR', 100.00);
INSERT INTO Account (owner_Id, currency, balance)
VALUES (5, 'JPY', 100.00);
INSERT INTO Account (owner_Id, currency, balance)
VALUES (6, 'PLN', 100.00);

INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (1, 0.886, 'USD', 'EUR');
INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (2, 113.65, 'USD', 'JPY');
INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (3, 4.12, 'USD', 'PLN');

INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (4, 1.13, 'EUR', 'USD');
INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (5, 128.28, 'EUR', 'JPY');
INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (6, 4.63, 'EUR', 'PLN');

INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (7, 0.0078, 'JPY', 'EUR');
INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (8, 0.0088, 'JPY', 'USD');
INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (9, 0.0361, 'JPY', 'PLN');

INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (10, 0.216, 'PLN', 'EUR');
INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (11, 0.244, 'PLN', 'USD');
INSERT INTO Exchange_Rate (id, rate, source_code, target_code)
VALUES (12, 27.73, 'PLN', 'JPY');