#!/bin/bash

# Скрипт для инициализации базы данных
# Использование: ./init-database.sh

echo "Инициализация базы данных..."

# Проверка, запущен ли контейнер
if ! docker ps | grep -q mysql-app; then
    echo "Контейнер MySQL не запущен. Запускаю контейнер..."
    docker-compose up -d
    echo "Ожидание запуска MySQL..."
    sleep 10
fi

# Выполнение SQL скрипта
echo "Выполнение SQL скрипта..."
docker exec -i mysql-app mysql -utestsforusers -ptestsforusers ermakoff < init.sql

if [ $? -eq 0 ]; then
    echo "База данных успешно инициализирована!"
else
    echo "Ошибка при инициализации базы данных."
    exit 1
fi
