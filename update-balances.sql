-- Скрипт для установки баланса 50000 на всех картах всех пользователей
USE ermakoff;

UPDATE cards 
SET balance = 50000.00 
WHERE balance IS NOT NULL;

-- Показать результат
SELECT card_number, holder_name, balance 
FROM cards 
ORDER BY holder_name;
