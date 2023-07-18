# СУПЕР СЧЕТЧИК

Проект "Супер Счетчик" представляет собой веб-приложение, написанное на языке Java, используя Spring Boot и Vaadin. Пользователь может увеличивать счетчик на 1, а также вручную изменять значение счетчика. Значение счетчика сохраняется в базе данных. Приложение создано в качестве тестового задания.

## Используемые технологии и библиотеки

1. Spring Boot
2. Vaadin
3. Spring Data JPA
4. Lombok
5. H2 Database

## Установка и запуск

Для запуска этого приложения у вас должен быть установлен Maven и JDK 8 или выше.

1. Клонировать репозиторий: `git clone https://github.com/iusupart/super-counter.git`
2. Перейти в директорию проекта: `cd super-counter`
3. Запустить проект с помощью Maven: `mvn spring-boot:run`
4. Открыть в браузере: `http://localhost:8080/`

## Структура проекта

- `src/main/java/ru/task/iusupart/testtask` - корневой пакет, содержит основные классы приложения.
    - `model` - содержит классы модели.
    - `repository` - содержит классы репозиториев для работы с базой данных.
    - `view` - содержит классы представления (UI).
- `src/main/resources` - ресурсы приложения.
- `pom.xml` - файл конфигурации Maven.
