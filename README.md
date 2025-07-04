ЦЕЙ РЕПОЗИТОРІЙ СУТО ДЛЯ ТЕСТУ!

# Як працювати з проектом локально

## 1. Необхідні компоненти

Перед початком переконайтеся, що встановлено:

- Java **17 або вище**
- **Maven**

## 2. Запуск Docker-сервісів (database, Keycloak)

Перейди до кореневої директорії проекту (де знаходиться [docker-compose.yaml](docker-compose.yaml)) і виконайте:

```bash
docker-compose up database
docker-compose up zookeeper
docker-compose up kafka-broker
docker-compose up postgres-keycloak
docker-compose up keycloak
```
Можливо те, що один з докер сервісів не запустився, то треба його просто перезапустити
## 3. Запуск Spring Boot застосунку

### Через IntelliJ IDEA:

спочатку записати значення mail-username та mail-password у конфігурацію запуску OrderServiceApplication в поле environment variables,
значення поля secret взяти з файлу [copyria_realm.json](order_service/misc/keycloak/copyria_realm.json).

на момент 04.07.2025 вони вже записані, а файл не додан .

Відкрийте
файл [CarServiceApplication.java](car_service/src/main/java/org/copyria2/car_service/CarServiceApplication.java),
натисніть зелений трикутник біля класу або методу `main`.

Після

Відкрийте
файл [OrderServiceApplication.java](order_service/src/main/java/org/copyria2/order_service/OrderServiceApplication.java),
натисніть зелений трикутник біля класу або методу `main`.

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

У вікно Maven ( M справа ), зайти в Maven Settings -> Runner і постивити галку на skipTests

car-service->clean+compile, car_service -> package.

order-service ->clean+compile, order_service -> package.

після цього, exam2_copyria -> package. 

У папці /backend буде лежати два .jar файла

після цього зайти у термінал та написати

```bash
docker-compose up car_service
docker-compose up order_service
```

Якщо все працює, то треба запакувати окремі файли у .zip архив.

На момент 04.07.2025 проет запускається, але не дає змогу отримати дані через помилку 401

її вирішення - ?? 
