INSERT INTO categories (id, name) VALUES (1, 'Sport');
INSERT INTO categories (id, name) VALUES (2, 'Charity');

INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number) VALUES (1, 'Nick', 'Green','nickGreen', 'nick@mail.com', '2222','image 1', 'description 1','2020-09-16 14:00:04.810221','Stryi', '0985554432');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number) VALUES (2, 'Nora', 'White','noraWhite', 'nora@mail.com', '3333','image 2', 'description 2', '2021-09-16 14:00:04.810221','Lviv', '0985553764');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number) VALUES (3, 'Mike', 'Brown','mikeBrown', 'mike@mail.com', '1111','image 3', 'description 3', '2021-10-16 14:00:04.810221','Las Vegas', '0985553964');
INSERT INTO users (id, first_name, last_name, username, email, password, profile_image, description, created_at, location_city, phone_number) VALUES (4, 'George', 'Black','geoBlack', 'george@mail.com', '4444','image 4', 'description 4', '2021-10-16 14:00:04.810221','Berdychiv', '0995553964');
-- INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (4, 'Mike', 'Brown', 'mike@mail.com', '$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO', 1);
--
--
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (1, 'Event 1', 190, '2020-09-16 14:00:04.810221','2020-09-16 14:30:04.810221','2021-09-16 14:30:04.810221', 'Description 1', 2, 'UPCOMING', 'Stryi', 1);
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (2, 'Event 2', 50, '2020-09-16 14:00:11.480271','2020-10-16 14:00:11.480271','2022-09-16 14:30:04.810221', 'Description 2',  1, 'UPCOMING', 'Brovary', 2);
INSERT INTO events (id, title, max_participants, created_at, start_at, expire_at, description, participant_count, state, location, owner_id) VALUES (3, 'Event 3', 22, '2020-09-16 14:00:16.351238', '2020-11-16 14:00:16.351238','2023-09-16 14:30:04.810221', 'Description 3', 1, 'UPCOMING', 'Baranovichi', 3);

INSERT INTO event_photos (id, photo_url, event_id) VALUES (1, 'Photo 1', 1);
INSERT INTO event_photos (id, photo_url, event_id) VALUES (2, 'Photo 2', 2);
INSERT INTO event_photos (id, photo_url, event_id) VALUES (3, 'Photo 3', 3);


INSERT INTO event_categories (event_id, category_id) VALUES (1, 1);
INSERT INTO event_categories (event_id, category_id) VALUES (1, 2);
INSERT INTO event_categories (event_id, category_id) VALUES (2, 1);
INSERT INTO event_categories (event_id, category_id) VALUES (3, 2);



INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (1, '2020-11-16 14:00:04.810221', 'false', 1, 2);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (2, '2020-11-16 14:00:05.810221', 'true', 1, 3);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (3, '2020-11-16 14:00:05.810222', 'true', 1, 4);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (4, '2020-11-16 14:00:05.810221', 'true', 2, 1);
INSERT INTO participants (id, created_at, is_approved, event_id, user_id) VALUES (5, '2020-11-16 14:00:05.810222', 'true', 3, 2);
