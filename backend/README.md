# Bank Backend API

Backend приложение для банковской системы на Spring Boot.

## Структура проекта

```
backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── bank2/
│       │               ├── BankApplication.java          # Главный класс приложения
│       │               ├── controller/                    # REST контроллеры
│       │               │   ├── AuthController.java
│       │               │   ├── UserController.java
│       │               │   ├── CardController.java
│       │               │   ├── TransferController.java
│       │               │   ├── VkladController.java
│       │               │   ├── ZayavkaController.java
│       │               │   └── InterestRateController.java
│       │               ├── service/                       # Бизнес-логика
│       │               │   ├── UserService.java
│       │               │   ├── CardService.java
│       │               │   ├── TransferService.java
│       │               │   ├── VkladService.java
│       │               │   ├── ZayavkaService.java
│       │               │   ├── InterestRateService.java
│       │               │   └── impl/                     # Реализации сервисов
│       │               ├── repository/                    # Репозитории (JPA)
│       │               │   ├── UserRepository.java
│       │               │   ├── CardRepository.java
│       │               │   ├── VkladRepository.java
│       │               │   ├── ZayavkaRepository.java
│       │               │   ├── HistoryTransferRepository.java
│       │               │   └── InterestRateRepository.java
│       │               ├── entity/                        # JPA сущности
│       │               │   ├── User.java
│       │               │   ├── Card.java
│       │               │   ├── Vklad.java
│       │               │   ├── Zayavka.java
│       │               │   ├── HistoryTransfer.java
│       │               │   └── InterestRate.java
│       │               ├── dto/                          # Data Transfer Objects
│       │               │   ├── LoginRequest.java
│       │               │   ├── RegisterRequest.java
│       │               │   ├── CardCreateRequest.java
│       │               │   ├── TransferRequest.java
│       │               │   ├── VkladCreateRequest.java
│       │               │   ├── ZayavkaCreateRequest.java
│       │               │   └── ApiResponse.java
│       │               ├── config/                       # Конфигурация
│       │               │   ├── DatabaseConfig.java
│       │               │   ├── SecurityConfig.java
│       │               │   └── WebConfig.java
│       │               ├── exception/                     # Обработка исключений
│       │               │   ├── GlobalExceptionHandler.java
│       │               │   ├── ResourceNotFoundException.java
│       │               │   └── BadRequestException.java
│       │               └── util/                         # Утилиты
│       │                   ├── CardNumberGenerator.java
│       │                   └── PasswordEncoder.java
│       └── resources/
│           └── application.properties                    # Конфигурация приложения
└── pom.xml                                               # Maven зависимости
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Регистрация пользователя
- `POST /api/auth/login` - Вход в систему

### Users
- `GET /api/users/{id}` - Получить пользователя по ID
- `GET /api/users/login/{login}` - Получить пользователя по логину

### Cards
- `POST /api/cards` - Создать карту
- `GET /api/cards/{cardNumber}` - Получить карту по номеру
- `GET /api/cards/user/{userLogin}` - Получить все карты пользователя
- `PUT /api/cards/{cardNumber}/activate` - Активировать карту
- `PUT /api/cards/{cardNumber}/deactivate` - Деактивировать карту
- `PUT /api/cards/{cardNumber}/sbp/connect` - Подключить СБП
- `PUT /api/cards/{cardNumber}/sbp/disconnect` - Отключить СБП

### Transfers
- `POST /api/transfers` - Выполнить перевод
- `GET /api/transfers/card/{cardNumber}` - История операций по карте
- `GET /api/transfers/user/{userLogin}` - История операций пользователя

### Vklads (Вклады)
- `POST /api/vklads` - Создать вклад
- `GET /api/vklads/{numberVklad}` - Получить вклад по номеру
- `GET /api/vklads/user/{userLogin}` - Получить все вклады пользователя
- `GET /api/vklads/{numberVklad}/profit` - Рассчитать прибыль по вкладу

### Zayavki (Заявки)
- `POST /api/zayavki` - Создать заявку
- `GET /api/zayavki/user/{userLogin}` - Получить заявки пользователя
- `GET /api/zayavki/pending` - Получить все необработанные заявки
- `GET /api/zayavki/processed` - Получить все обработанные заявки
- `PUT /api/zayavki/{zayavkaId}/process` - Обработать заявку

### Interest Rates (Процентные ставки)
- `GET /api/interest-rates` - Получить все процентные ставки
- `GET /api/interest-rates/{term}` - Получить процентную ставку по сроку
- `POST /api/interest-rates` - Создать или обновить процентную ставку

## Запуск

1. Убедитесь, что MySQL запущен (через docker-compose)
2. Запустите приложение:
```bash
cd backend
mvn spring-boot:run
```

Приложение будет доступно по адресу: `http://localhost:8080`

