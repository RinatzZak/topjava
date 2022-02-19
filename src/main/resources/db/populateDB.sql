DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('19-02-2022 09:11:00', 'Завтрак', 600, 100000),
       ('19-02-2022 12:42:00', 'Обед', 777, 100000),
       ('19-02-2022 16:27:00', 'Кофе', 110, 100000),
       ('19-02-2022 19:53:00', 'Ужин', 521, 100000),
       ('20-02-2022 06:22:00', 'Завтрак', 600, 100000),
       ('20-02-2022 13:00:00', 'Обед', 800, 100000),
       ('20-02-2022 20:58:00', 'Ужин', 500, 100000),
       ('19-02-2022 05:33:00', 'Кофе для Админа', 500, 100001),
       ('19-02-2022 13:12:00', 'Обед для Админа', 900, 100001),
       ('19-02-2022 23:17:00', 'Ужин для Админа', 437, 100001);

