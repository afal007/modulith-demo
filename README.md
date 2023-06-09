# Modulith Demo

Демо приложение для тестирования различных вариантов реализации модульного монолита.

В ветке **master** находится наиболее приемлемый вариант на мой взгляд. Модульность на уровне gradle, событийная архитектура на основе AxonIQ фреймворка. В качестве эксперимента использованы Spring Modulith и Java Jigsaw.

В директории **docker** находится **docker-compose.yaml** который поднимает все необходимые зависимости - PostgreSQL, Keycloak и ElasticSearch.

## Как запустить

В проекте использован Spring Boot 3.1.0 и spring-boot-docker-compose - при запуске из Intellij Idea должны автоматически подниматься все докер-контейнеры.

Если нужно отключить автоматическое подхватывание docker-compose можно использовать настройку **spring.docker.compose.enabled=false**. В таком случае для работы приложения необходимо сконфигурировать доступ к БД и подключение к киклоку. Параметры необходимо прописать в **application.yaml** по путям **config.datasource** и **config.keycloak**.

Из-за особенностей Project Jigsaw при запуске из Intellij Idea может вылетать ошибка:

`class com.example.modulith.demo.ModulithDemoApplication$$SpringCGLIB$$0 (in module modulith.demo.bootstrap.main) cannot access class org.springframework.cglib.core.ReflectUtils (in unnamed module @0x35cabb2a) because module modulith.demo.bootstrap.main does not read unnamed module @0x35cabb2a`

Ошибка исправляется добавлением параметра виртуальной машины `--add-reads modulith.demo.bootstrap.main=ALL-UNNAMED`

## API

Свагер доступен по ссылке http://localhost:8080/modulith-demo/swagger-ui.html

В проекте лежит файл с коллекцией запросов для Postman - [Modulith Demo.postman_collection.json](Modulith%20Demo.postman_collection.json)

## Docker контейнеры

Чтобы Docker Compose полностью запустился необходимо выделить докеру минимум 4GB RAM

### PostgreSQL

Прокидывается на стандартный порт 5432. Создается БД **modulith**. Создается БД **keycloak**.

* Строка подключения - **jdbc:postgresql://localhost:5432/postgres**
* Хост для PGAdmin - **host.docker.internal**
* Логин - **dev**
* Пароль -  **pwd**

### PGAdmin

Прокидывается на порт 5050. Инициализируется подключение к PostgreSQL с названием modulith. При подключении необходимо будет ввести пароль для пользователя dev указанный выше.

* http://localhost:5050 
* Пользователь - **user@ya.ru**
* Пароль - **admin**

### Keycloak

Прокидывается на порт 8180. Создается клиент **modulith**. Создаются пользователи **admin** и **test**.

* http://localhost:8180
* Клиент
  * clientId - **modulith**
  * clientSecret - **xOWbdLzfyqgxrg9w2TPx2NvNr8oN5464**
* Администратор
  * username - **admin**
  * password - **admin**
* Пользователь
  * username - **test**
  * password - **12345678**

### Elasticsearch

Прокидывается на порт 9200. Авторизация и SSL отключены в режиме разработки.

* http://localhost:9200

### Kibana

Прокидывается на порт 5601. Авторизация и SSL отключены в режиме разработки.

* http://localhost:5601

## Примеры проектов

* https://github.com/anton-liauchuk/educational-platform

## Тестируемые варианты

### Java Project Jigsaw

Модульность реализованная на уровне языка

* https://openjdk.org/projects/jigsaw/
* https://www.baeldung.com/project-jigsaw-java-modularity

#### Результаты тестирования

Посмотреть можно в ветке ветке **axon-no-server-modulith-jigsaw**. Инструмент позволяет на уровне языка жестко задавать границы модулей. Самый строгий инструмент - отлавливает ошибки на этапе компиляция. Есть поддержка Intellij Idea.

##### Плюсы

* Максимально строгие разграничения по модульности
    * Нужно явно прописывать каждый экспортируемый пакет
    * Нужно явно прописывать каждый импортируемый пакет
    * Попытки доступа с помощью Java Reflection отлавливаются в runtime
* Не нужны дополнительные зависимости - достаточно Java версии > 9

##### Минусы

* Нужно явно прописывать каждую импортируемую зависимость дважды - один раз в build.gradle и второй раз в module-info.java
* Доступ на уровне Java Reflection может сломать всё в runtime - любой фреймворк может сломать приложение
* Если библиотека не поддерживает модульность её использование будет невозможно

### Spring Modulith

Экспериментальный проект от спринга

* https://www.baeldung.com/spring-modulith
* https://spring.io/blog/2022/10/21/introducing-spring-modulith
* https://docs.spring.io/spring-modulith/docs/0.1.0-M1/reference/html/#fundamentals.modules

#### Результаты тестирования

Посмотреть можно в ветке ветке **axon-no-server-modulith-jigsaw**. Инструмент позволяет конфигурировать модули через аннотации на пакетах. Есть возможность верификации правил и генерирования документации в формате UML и adoc.

##### Плюсы

* Автогенерация документации
* Экосистема спринга

##### Минусы

* Очень сырой проект
* Документация не генерируется по заявленным аннотациям - причину выяснить не удалось
* Модульность не поддерживает раскрытие вложенных пакетов - любой подпакет по умолчанию считается внутренним
* В целом модульность реализована на уровне пакетов - модулями считаются подпакеты того пакета в котором определен класс @SpringBootApplication
    * При этом подпакеты подпакетов по-умолчанию считаются внутренностями модуля и их раскрыть никак не получится

### Spring Events

Событийная архитектура от спринга

* https://www.baeldung.com/spring-events
* https://docs.spring.io/spring-integration/reference/html/event.html

#### Результаты тестирования

Посмотреть можно в ветке ветке **spring-boot-events**. Инструмент позволяет реализовать событийную архитектуру на уровне Spring Framework и тем самым уменьшить связность между модулями.

##### Плюсы

* Экосистема спринга
* Практически не требует дополнительной конфигурации
* Очень простая реализация и вследствие низкий порог вхождения

##### Минусы

* ???

### AxonIQ

Фреймворк реализующий CQRS и Event Sourcing подходы

* https://docs.axoniq.io/reference-guide/
* https://www.baeldung.com/axon-cqrs-event-sourcing

#### Результаты тестирования

Посмотреть можно в ветке **master** вариант без Axon Server. В ветке **axon-with-server** вариант с Axon Server. Фреймворк предназначен для реализации событийно-ориентированной архитектуры и принципов CQRS и event-sourcing. Предоставляет собственное решение для распределенной системы которое является шиной для общения между сервисами - Axon Server.

##### Плюсы

* Дает все необходимые инструменты для реализации event-driven архитектуры
* Поощряет использование принципов DDD
* Встраивается в экосистему спринга

##### Минусы

* Достаточно высокий порог вхождения
* Требует достаточно много ручной конфигурации
* Достаточно жирный фреймворк
* Не работает с Spring Dev Tools
* Дополнительная зависимость которую нужно учитывать при обновлении версий спринга

## TODO

1. Как разделить OpenAPI спеку чтобы можно было в каждом модуле хранить только свою часть?
2. Как оптимально раскидать зависимости по модулям?
3. Как сократить количество DTO на примере Query? Реализация находится на уровне application а DTO находится на уровне api
4. Авторизация и пользователи - оставить только на уровне киклока? Или реализовать модуль в приложении в котором будет управление пользователями и который будет взаимодействовать с киклоком?
5. Подмодули с одинаковым названием перетирают друг-друга - https://stackoverflow.com/questions/58602820/gradle-multiprojects-with-same-name-different-paths
    1. Возникает из-за того что зависимость должна соответствовать понятию [артефакта maven](https://maven.apache.org/guides/mini/guide-naming-conventions.html)
    2. Возможное решение - переименовать подмодуль в settings.gradle
    3. Возможное решение - для каждого подмодуля определять уникальный groupId
       1. Возникает проблема с jar-архивами - наименование идет по названию проекта. При сборке происходит конфликт из-за одинаковых названий архивов.
6. Поправить быстрые решения по типу маппингов в контроллере
7. Тесты
8. Разделить на модули внешнее АПИ - openapi + контроллеры - и внутреннее апи - интерфейсы которые могут использовать другие модули?
9. Документация - архитектурная и по модулям
10. Посмотреть что нужно сделать чтобы проект запускался как из идеи так и через gradle bootRun
    1. Сейчас ошибка в gradle bootRun из-за того что путь к docker-compose.yaml не существует
11. ???
