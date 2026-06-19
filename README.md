# Task Tracker

Task Tracker — это backend-приложение для управления задачами и отслеживания продуктивности пользователей.

Система позволяет создавать задачи, контролировать их выполнение, получать статистику активности и автоматические уведомления по электронной почте.

---

# Основные возможности

* Регистрация и авторизация пользователей
* Создание, редактирование и удаление задач
* Изменение статуса задач
* Хранение пользовательских сессий
* Отправка email-уведомлений
* Сбор статистики по выполненным задачам
* Асинхронная обработка событий через Kafka
* REST API
* Docker support

---

# Технологии

## Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MapStruct

## Database

* PostgreSQL
* Redis

## Messaging

* Apache Kafka

## Infrastructure

* Docker
* Docker Compose

## Migration

* Liquibase

## Build Tool

* Gradle

---

# Основной функционал

## Авторизация

## Авторизация

Используется JWT-based authentication:

- JWT Access Token;
- JWT Refresh Token;
- Spring Security;
- Stateless authentication;


---

## Управление задачами

Пользователь может:

1. создавать задачи;
2. изменять описание и параметры задач;
3. менять статус задачи;
4. удалять задачи;
5. просматривать список своих задач.

Поддерживаются следующие статусы:

* IN_PROGRESS
* COMPLETED

---

## Статистика

Система автоматически собирает данные о выполнении задач:

* количество выполненных задач;
* количество активных задач;


---

## Email-уведомления

Для отправки уведомлений используется асинхронная обработка через Apache Kafka.

Процесс работы:

1. приложение публикует событие;
2. Kafka принимает сообщение;
3. сервис отправки писем обрабатывает событие;
4. пользователь получает email-уведомление.

---

## Планировщик задач

С помощью Spring Scheduler выполняются фоновые операции:

* обработка статистики;
* формирование ежедневных отчётов;
* отправка уведомлений пользователям.

---

# Запуск проекта

## Клонирование репозитория

```bash
git clone https://github.com/progrohan/task-tracker

cd task-tracker
```

---

## .env

Создайте в корне проекта файл `.env` и заполните его по следующему шаблону:

```env
DB_HOST=
DB_PORT=
DB_NAME=
DB_USERNAME=
DB_PASSWORD=

JWT_SECRET=

MAIL_HOST=
MAIL_PORT=
MAIL_USERNAME=
MAIL_PASSWORD=

KAFKA_BOOTSTRAP_SERVERS=
```

---

## Запуск через Docker

```bash
docker compose up --build
```

---

# Безопасность

Используется:

- Spring Security;
- BCrypt password hashing;
- JWT authentication;
- Access & Refresh Tokens;
- Role-based authorization;
- CORS configuration;
- Protected REST endpoints.

---

# Архитектура

Проект построен по принципам многослойной архитектуры:

* Controller Layer;
* Service Layer;
* Repository Layer;
* Database Layer.

