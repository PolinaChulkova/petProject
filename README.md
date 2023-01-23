# PetProject
# Описание
Проект новостного сайта. Пример backend с использованием Spring
# О проекте
Язык программирования: **Java**

Сборщик: **Maven**

Используемые технологии:

- **Spring Boot**
- **PostgreSql**
- **Hibernate**
- **FlyWay**
- **JWT**
- **Swagger**

**Настройки подключения к БД** находятся по пути: src/main/resources/application.properties. По умолчанию там выставлены настройки для подключения к локальной БД, которой можно изменить при необходимости.

Рабочая зона (src/main/java/com.example.petproject/) содержит 6 java каталогов:

- **config** - файлы конфигурации security и swagger;
- **controller** - 5 классов контроллеров;
- **DTO** - набор объектов, передающих данные;
- **model** - модели объектов User, Role, News и Comment;
- **repository** - репозитории к моделям;
- **service** - имплементация репозиториев.

**Документация API**
- http://localhost:8089/swagger-ui.html#/news-controller/searchByKeyUsingGET
