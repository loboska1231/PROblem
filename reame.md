# Як працювати з проектом локально

## 1. Необхідні компоненти

Перед початком переконайся, що встановлено:

- Java **17 або вище**
- **Maven**

## 2. Запуск Docker-сервісів (MongoDB, Keycloak)

Перейди до кореневої директорії проекту (де знаходиться [compose.yaml](compose.yaml)) і виконай:

```bash
docker-compose -f compose.yaml up -d
```

## 3. Запуск Spring Boot застосунку

### Через IntelliJ IDEA:

спочатку записати значення mail-username та mail-password у конфігурацію запуску OrderServiceApplication в поле environment variables,
значення поля secret взяти з файлу [copyria_realm.json](order_service/misc/keycloak/copyria_realm.json).

Відкрий
файл [CarServiceApplication.java](car_service/src/main/java/org/copyria2/car_service/CarServiceApplication.java),
натисни зелений трикутник біля класу або методу `main`.

Після

Відкрий
файл [OrderServiceApplication.java](order_service/src/main/java/org/copyria2/order_service/OrderServiceApplication.java),
натисни зелений трикутник біля класу або методу `main`.

## 5. Зупинка Docker-сервісів

Від папки exam2_copyria (... /exam2_copyria) заходимо у термінал і пишемо
```bash
docker compose up --build 
```

Для зупинки контейнерів та видалення volumes:

```bash
docker-compose down -v
```
---

# MavenPackage (пакування проекту)

### Через IntelliJ IDEA:

У вікно Maven ( M справа )

car-service -> package ,  order-service -> package,

після цього, exam2_copyria -> package. 

У папці /backend буде лежати два .jar файла

