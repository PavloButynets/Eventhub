INSERT INTO categories (id, name) VALUES (10, 'Sport');
INSERT INTO categories (id, name) VALUES (11, 'Charity');

INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number, birth_date, gender) VALUES (10, 'Nick', 'Green','nickGreen', 'nick@mail.com', '2222','image 1', 'description 1','2020-09-16 14:00:04.810221','Stryi', '0985554432', '1990-09-11 14:00:04.810221', 'MALE');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number, birth_date, gender) VALUES (11, 'Nora', 'White','noraWhite', 'nora@mail.com', '3333','image 2', 'description 2', '2021-09-16 14:00:04.810221','Lviv', '0985553764', '1990-09-16 14:00:04.810221', 'FEMALE');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number, birth_date, gender) VALUES (12, 'Mike', 'Brown','mikeBrown', 'mike@mail.com', '1111','image 3', 'description 3', '2021-10-16 14:00:04.810221','Las Vegas', '0985553964', '1990-09-16 14:00:04.810221', 'MALE');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number, birth_date, gender) VALUES (13, 'George', 'Black','geoBlack', 'george@mail.com', '4444','image 4', 'description 4', '2021-10-16 14:00:04.810221','Berdychiv', '0995553964', '1990-09-16 14:00:04.810221', 'MALE');
-- INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (4, 'Mike', 'Brown', 'mike@mail.com', '$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO', 1);
--
--
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (10, 'Event 1', 190, '2020-09-16 14:00:04.810221','2020-09-16 14:30:04.810221','2021-09-16 14:30:04.810221', 'Description 1', 2, 'UPCOMING', 'Stryi', 10);
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (11, 'Event 2', 50, '2020-09-16 14:00:11.480271','2020-10-16 14:00:11.480271','2022-09-16 14:30:04.810221', 'Description 2',  1, 'UPCOMING', 'Brovary', 11);
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (12, 'Event 3', 22, '2020-09-16 14:00:16.351238', '2020-11-16 14:00:16.351238','2023-09-16 14:30:04.810221', 'Description 3', 1, 'UPCOMING', 'Baranovichi', 12);

INSERT INTO event_photos (id, photo_url, event_id) VALUES (10, 'Photo 1', 10);
INSERT INTO event_photos (id, photo_url, event_id) VALUES (11, 'Photo 2', 11);
INSERT INTO event_photos (id, photo_url, event_id) VALUES (12, 'Photo 3', 12);


INSERT INTO event_categories (event_id, category_id) VALUES (10, 10);
INSERT INTO event_categories (event_id, category_id) VALUES (10, 11);
INSERT INTO event_categories (event_id, category_id) VALUES (11, 10);
INSERT INTO event_categories (event_id, category_id) VALUES (12, 11);



INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (10, '2020-11-16 14:00:04.810221', 'false', 10, 11);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (11, '2020-11-16 14:00:05.810221', 'true', 10, 12);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (12, '2020-11-16 14:00:05.810222', 'true', 10, 13);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (13, '2020-11-16 14:00:05.810221', 'true', 11, 10);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (14, '2020-11-16 14:00:05.810222', 'true', 12, 11);
