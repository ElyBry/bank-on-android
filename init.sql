-- Инициализация базы данных для банковского приложения
-- Используется база данных: ermakoff

USE ermakoff;

-- Таблица пользователей
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fio VARCHAR(255) NOT NULL,
    passport VARCHAR(50) NOT NULL UNIQUE,
    date_of_birth VARCHAR(20) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    age VARCHAR(10) NOT NULL,
    number VARCHAR(20) DEFAULT '0',
    admin INT DEFAULT 0,
    INDEX idx_login (login),
    INDEX idx_passport (passport)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Таблица карт
CREATE TABLE IF NOT EXISTS cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL UNIQUE,
    holder_name VARCHAR(100) NOT NULL,
    expiry_date VARCHAR(10) NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    is_active BOOLEAN DEFAULT TRUE,
    number VARCHAR(20) DEFAULT '0',
    sbp BOOLEAN DEFAULT FALSE,
    INDEX idx_holder_name (holder_name),
    INDEX idx_card_number (card_number),
    FOREIGN KEY (holder_name) REFERENCES users(login) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Таблица истории переводов
CREATE TABLE IF NOT EXISTS historytransfer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_number_card VARCHAR(16) NOT NULL,
    recipient_number_card VARCHAR(16) NOT NULL,
    transfer_amount DECIMAL(15, 2) NOT NULL,
    date VARCHAR(50) NOT NULL,
    INDEX idx_sender (sender_number_card),
    INDEX idx_recipient (recipient_number_card)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Таблица заявок администратору
CREATE TABLE IF NOT EXISTS admin_banking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login_user VARCHAR(100) NOT NULL,
    problem VARCHAR(255) NOT NULL,
    problem_text TEXT NOT NULL,
    send_date VARCHAR(50) NOT NULL,
    is_successful BOOLEAN DEFAULT FALSE,
    INDEX idx_login_user (login_user),
    FOREIGN KEY (login_user) REFERENCES users(login) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Таблица вкладов
CREATE TABLE IF NOT EXISTS vklads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sum DECIMAL(15, 2) DEFAULT 0.00,
    percent INT NOT NULL,
    getmon DECIMAL(15, 2) DEFAULT 0.00,
    number_vklad VARCHAR(16) NOT NULL UNIQUE,
    create_date DATE NOT NULL,
    end_date DATE NOT NULL,
    user_login VARCHAR(100) NOT NULL,
    INDEX idx_user_login (user_login),
    INDEX idx_number_vklad (number_vklad),
    FOREIGN KEY (user_login) REFERENCES users(login) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Таблица процентных ставок
CREATE TABLE IF NOT EXISTS interest_rates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    term VARCHAR(10) NOT NULL,
    interest DOUBLE NOT NULL,
    INDEX idx_term (term)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Вставка начальных данных для процентных ставок (пример)
INSERT INTO interest_rates (term, interest) VALUES
    ('3', 5.0),
    ('6', 6.0),
    ('12', 7.0),
    ('24', 8.0)
ON DUPLICATE KEY UPDATE term=term;

-- Создание тестового администратора (опционально)
-- Пароль: admin (захеширован с помощью BCrypt)
-- ВНИМАНИЕ: Замените этот хеш на реальный, сгенерированный вашим приложением
-- INSERT INTO users (login, password, fio, passport, date_of_birth, gender, age, number, admin) 
-- VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Администратор', '0000000000', '1990-01-01', 'М', '34', '0', 1)
-- ON DUPLICATE KEY UPDATE login=login;
