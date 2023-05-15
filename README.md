# Modulith Demo

Демо приложение для тестирования различных вариантов реализации модульного монолита.

В ветке **master** находится базовая реализация Spring Boot приложения без бизнес-логики.

В директории **docker** находится **docker-compose.yaml** который поднимает все необходимые зависимости - PostgreSQL, Keycloak и ElasticSearch.

## Тестируемые варианты

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
2. 
