--INSERT INTO app_user (id, name, email, password) VALUES
--(101, 'Aarav Sharma', 'aarav@gmail.com', 'Password'),
--(102, 'Vivaan Khanna', 'vivaan.khanna@example.com', '$2a$10$examplePasswordHash2'),
--(103, 'Aditya Verma', 'aditya.verma@example.com', '$2a$10$examplePasswordHash3'),
--(104, 'Vihaan Kapoor', 'vihaan.kapoor@example.com', '$2a$10$examplePasswordHash4'),
--(105, 'Arjun Patel', 'arjun.patel@example.com', '$2a$10$examplePasswordHash5'),
--(106, 'Sai Reddy', 'sai.reddy@example.com', '$2a$10$examplePasswordHash6'),
--(107, 'Ananya Nair', 'ananya.nair@example.com', '$2a$10$examplePasswordHash7'),
--(108, 'Ishaan Thakur', 'ishaan.thakur@example.com', '$2a$10$examplePasswordHash8'),
--(109, 'Aryan Rao', 'aryan.rao@example.com', '$2a$10$examplePasswordHash9'),
--(110, 'Diya Singh', 'diya.singh@example.com', '$2a$10$examplePasswordHash10');
--
--
--
--
--INSERT INTO user_roles (user_id, roles) VALUES
--(101, 'RIDER'),
--(102, 'RIDER'),
--(102, 'DRIVER'),
--(103, 'RIDER'),
--(103, 'DRIVER'),
--(104, 'RIDER'),
--(104, 'DRIVER'),
--(105, 'RIDER'),
--(105, 'DRIVER'),
--(106, 'RIDER'),
--(106, 'DRIVER'),
--(107, 'RIDER'),
--(107, 'DRIVER'),
--(108, 'RIDER'),
--(108, 'DRIVER'),
--(109, 'RIDER'),
--(109, 'DRIVER'),
--(110, 'RIDER'),
--(110, 'DRIVER');
--
--
--
----INSERT INTO rider ( user_id, rating) VALUES
----( 101, 4.9);
----
----INSERT INTO wallet(user_id,balance) VALUES
----(101,100),
----(102,500);
--
--INSERT INTO driver (id, user_id, rating, available, current_location) VALUES
--(102, 102, 4.7, true, ST_GeomFromText('POINT(77.1025 28.7041)', 4326)),
--(103, 103, 4.8, true, ST_GeomFromText('POINT(77.2167 28.6667)', 4326)),
--(104, 104, 4.6, true, ST_GeomFromText('POINT(77.2273 28.6353)', 4326)),
--(105, 105, 4.9, true, ST_GeomFromText('POINT(77.2500 28.5500)', 4326)),
--(106, 106, 4.3, true, ST_GeomFromText('POINT(77.2000 28.6200)', 4326)),
--(107, 107, 4.4, true, ST_GeomFromText('POINT(77.2800 28.5900)', 4326)),
--(108,108, 4.5, true, ST_GeomFromText('POINT(77.2600 28.6800)', 4326)),
--(109, 109, 4.6, true, ST_GeomFromText('POINT(77.2200 28.6400)', 4326)),
--(110, 110, 4.7, true, ST_GeomFromText('POINT(77.2700 28.6700)', 4326));
