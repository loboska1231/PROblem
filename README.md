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

### IntelliJ IDEA:
Відкрийте
файл [CarServiceApplication.java](car_service/src/main/java/org/copyria2/car_service/CarServiceApplication.java),
натисніть зелений трикутник біля класу або методу `main`.

Після

Відкрийте
файл [OrderServiceApplication.java](order_service/src/main/java/org/copyria2/order_service/OrderServiceApplication.java),
натисніть зелений трикутник біля класу або методу `main`.

## 5. Зупинка Docker-сервісів

Від папки exam2_copyria2123 (ЛКМ по по папці exam2_copyria2123 -> Open In -> Terminal,
на Linux відкривається одразу термінал,

а на вінді може запустить PowerShell - і це погано, щоб це змінити, треба зайти у налаштування
_IDE -> tools -> terminal -> shell path_, натиснути на стрілку,

що у полі справа, обрати файл _\cmd.exe_ ).
заходимо у термінал і пишемо
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

У вікно Maven ( M справа ), зайти в _Maven Settings -> Runner_ і поставити галку на skipTests

У вікні Maven натиснути на car_service через ctrl обрати _clean compile_ та натиснути на зелений трикутник (_Run Maven Build_).
Після цього знов у вікно _Maven -> car_service -> package -> Run Maven Build_.

Теж саме зробити із _order_service_.

Після того, як було виконано пакування сервісів java, заходимо знову у вікно Maven, заходимо в _exam2_copyria -> package -> Run Maven Build_

У папці _/backend_ буде лежати два .jar файла

після цього зайти у термінал та написати

```bash
docker-compose up car_service
docker-compose up order_service
```

Після підняття докер-сервісів car/order _service заходимо у вікно _Services_( Alt+8 ), яка знаходиться зліва знизу ( шестикутник із трикутником всередині ).

Перевірити, чи усі сервіси запущені ( натиснути на докер, потім на _Docker-compose: exam2_copyria2123_ => _Running 7/7_ ).
Якщо так, то спочатку зайти у car_service, натиснути на car_service (тут матрешка вийшла бо назва докер-сервісу й докер контейнеру однакова)
зайти у вкладку Log. Якщо остання стрічка виглядає так :
```bash
o.c.car_service.CarServiceApplication    : Started CarServiceApplication in 121.315 seconds (process running for 121.315 )
```

то аналогічно зробити із order_service.

Після цього, перейту у Postman, натиснути на "три колоски" (зліва, зверху) File -> Import,
у поле з файлом перенести файл [java_postman.postman_collection.json](postman/java_postman.postman_collection.json).

Після того, як ви перенесли колекцію запитів постман, натискаєте на колекцію _java_postman_, переходите у вкладку _Authorization_
Гортаєте вниз, натискаєте **спочатку** _Clear cookies_ а потім _Get New Access Token_.

Вводите **_username_** : _kate.smith.admin_ , **_password_** : _admin_ , потім _Sign In_ і у вас буде токен, натисніть _Use Token_. 

Після цього заходите у папку _GET_ колекції _java_postman_ -> папка _orders_ -> запит getOrders  і натискаєте _Send_.

Після цього ви отримуєте помилку 401 Unauthorized, а у Log order_service буде помилка :

```
Caused by: java.lang.IllegalStateException: The Issuer "http://localhost:9090/realms/copyria" provided in the configuration did not match the requested issuer "http://keycloak:8080/realms/copyria"
```


Якщо все працює, то треба запакувати окремі файли у .zip архів.

На момент 04.07.2025 проект запускається, але не дає змогу отримати дані через помилку 401

її вирішення - ?? 
