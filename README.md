# Modulith Demo

Демо приложение для тестирования различных вариантов реализации модульного монолита.

В ветке **master** находится базовая реализация Spring Boot приложения без бизнес-логики.

В директории **docker** находится **docker-compose.yaml** который поднимает все необходимые зависимости - PostgreSQL, Keycloak и ElasticSearch.

## Примеры проектов

* https://github.com/anton-liauchuk/educational-platform

## Тестируемые варианты

### Java Project Jigsaw

Модульность реализованная на уровне языка

* https://openjdk.org/projects/jigsaw/
* https://www.baeldung.com/project-jigsaw-java-modularity

### Spring Modulith

Экспериментальный проект от спринга

* https://www.baeldung.com/spring-modulith
* https://spring.io/blog/2022/10/21/introducing-spring-modulith
* https://docs.spring.io/spring-modulith/docs/0.1.0-M1/reference/html/#fundamentals.modules

### Spring Events

Событийная архитектура от спринга

* https://www.baeldung.com/spring-events
* https://docs.spring.io/spring-integration/reference/html/event.html

### AxonIQ

Фреймворк реализующий CQRS и Event Sourcing подходы

* https://docs.axoniq.io/reference-guide/
* https://www.baeldung.com/axon-cqrs-event-sourcing


## TODO

1. Как разделить OpenAPI спеку чтобы можно было в каждом модуле хранить только свою часть?
2. Как оптимально раскидать зависимости по модулям?
3. Как сократить количество DTO на примере Query? Реализация находится на уровне application а DTO находится на уровне api
4. Авторизация и пользователи - оставить только на уровне киклока? Или реализовать модуль в приложении в котором будет управление пользователями и который будет взаимодействовать с киклоком?
5. Подмодули с одинаковым названием перетирают друг-друга - https://stackoverflow.com/questions/58602820/gradle-multiprojects-with-same-name-different-paths
   1. Возникает из-за того что зависимость должна соответствовать понятию [артефакта maven](https://maven.apache.org/guides/mini/guide-naming-conventions.html)
   2. Возможное решение - переименовать подмодуль в settings.gradle
   3. Возможное решение - для каждого подмодуля определять уникальный groupId
6. Поправить быстрые решения по типу маппингов в контроллере
7. Тесты
8. Разделить на модули внешнее АПИ - openapi + контроллеры - и внутреннее апи - интерфейсы которые могут использовать другие модули?
