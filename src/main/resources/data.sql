INSERT INTO categories (id, name) VALUES (10, 'Sport');
INSERT INTO categories (id, name) VALUES (11, 'Charity');

INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number, birth_date, gender) VALUES (10, 'Nick', 'Green','nickGreen', 'nick@mail.com', '2222','image 1', 'description 1','2020-09-16 14:00:04.810221','Stryi', '0985554432', '1990-09-11 14:00:04.810221', 'MALE');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number, birth_date, gender) VALUES (11, 'Nora', 'White','noraWhite', 'nora@mail.com', '3333','image 2', 'description 2', '2021-09-16 14:00:04.810221','Lviv', '0985553764', '1990-09-16 14:00:04.810221', 'FEMALE');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number, birth_date, gender) VALUES (12, 'Mike', 'Brown','mikeBrown', 'mike@mail.com', '1111','image 3', 'description 3', '2021-10-16 14:00:04.810221','Las Vegas', '0985553964', '1990-09-16 14:00:04.810221', 'MALE');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number, birth_date, gender) VALUES (13, 'George', 'Black','geoBlack', 'george@mail.com', '4444','image 4', 'description 4', '2021-10-16 14:00:04.810221','Berdychiv', '0995553964', '1990-09-16 14:00:04.810221', 'MALE');
-- INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (4, 'Mike', 'Brown', 'mike@mail.com', '$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO', 1);
--
--
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (30, 'Гонки на дровах', 190, '2020-09-16 14:00:04.810221','2020-09-16 14:30:04.810221','2021-09-16 14:30:04.810221', 'Стриййські гоки на биках, через місяці, середній рівень, бажано чоловіки', 2, 'UPCOMING', 'Стрий', 10);
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (31, 'Пиво в Броварах', 50, '2020-09-16 14:00:11.480271','2020-10-16 14:00:11.480271','2022-09-16 14:30:04.810221', 'Вживаємо пиво до схочу, кількість обмежена, дрескод - смокінг і спортивки',  1, 'UPCOMING', 'Бровари', 11);
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (32, 'Дурдом 5', 22, '2020-09-16 14:00:16.351238', '2020-11-16 14:00:16.351238','2023-09-16 14:30:04.810221', 'Хто вважає себе псіхом - приходьте', 1, 'UPCOMING', 'Барановічі', 12);

INSERT INTO event_photos (id, photo_url, event_id) VALUES (10, 'Photo 1', 30);
INSERT INTO event_photos (id, photo_url, event_id) VALUES (11, 'Photo 2', 31);
INSERT INTO event_photos (id, photo_url, event_id) VALUES (12, 'Photo 3', 32);


INSERT INTO event_categories (event_id, category_id) VALUES (30, 10);
INSERT INTO event_categories (event_id, category_id) VALUES (30, 11);
INSERT INTO event_categories (event_id, category_id) VALUES (31, 10);
INSERT INTO event_categories (event_id, category_id) VALUES (32, 11);



INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (10, '2020-11-16 14:00:04.810221', 'false', 30, 11);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (11, '2020-11-16 14:00:05.810221', 'true', 30, 12);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (12, '2020-11-16 14:00:05.810222', 'true', 30, 13);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (13, '2020-11-16 14:00:05.810221', 'true', 31, 10);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (14, '2020-11-16 14:00:05.810222', 'true', 32, 11);
