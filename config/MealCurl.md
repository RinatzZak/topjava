##MealRestController

---
#### getAll() - получаем весь список еды
`curl --location --request GET 'http://localhost:8080/topjava/rest/profile/meals/`

GET 'http://localhost:8080/topjava/rest/profile/meals/'

---

#### getMeal() id=100004 - Получаем одно блюдо по его id
`curl --location --request GET http://localhost:8080/topjava/rest/profile/meals/100004`

GET http://localhost:8080/topjava/rest/profile/meals/100004

---

#### delete - удаляем блюдо
`curl --location --request DELETE 'http://localhost:8080/topjava/rest/profile/meals/100015' \
--data-raw ''`

DELETE 'http://localhost:8080/topjava/rest/profile/meals/100015'

---

#### createWithLocation - создание блюда
`curl --location --request POST 'http://localhost:8080/topjava/rest/profile/meals/' \
--data-raw '{
"dateTime": "2022-03-30T13:00:00",
"description": "PostmanFood",
"calories": 1111
}'`

POST 'http://localhost:8080/topjava/rest/profile/meals/'

---

#### update - редактируем блюдо
`curl --location --request PUT 'http://localhost:8080/topjava/rest/profile/meals/100004' \
--data-raw '{
"dateTime": "2020-01-30T13:00:00",
"description": "ОбедNew",
"calories": 1000
}'`

PUT 'http://localhost:8080/topjava/rest/profile/meals/100004'

---

#### getBetween - используем фильтр для получение списка блюд, удовлетворяющего временным промежуткам
`curl --location --request GET 'http://localhost:8080/topjava/rest/profile/meals/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=12%3A00&endTime=14%3A00' \
--data-raw ''`

GET 'http://localhost:8080/topjava/rest/profile/meals/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=12%3A00&endTime=14%3A00'

---
