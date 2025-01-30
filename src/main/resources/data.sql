INSERT INTO app_user (id, name, email, password) VALUES
(101, 'Aarav Sharma', 'aarav@gmail.com', 'Password'),
(102, 'Vivaan Khanna', 'vivaan.khanna@example.com', '$2a$10$examplePasswordHash2'),
(103, 'Aditya Verma', 'aditya.verma@example.com', '$2a$10$examplePasswordHash3'),
(104, 'Vihaan Kapoor', 'vihaan.kapoor@example.com', '$2a$10$examplePasswordHash4'),
(105, 'Arjun Patel', 'arjun.patel@example.com', '$2a$10$examplePasswordHash5'),
(106, 'Sai Reddy', 'sai.reddy@example.com', '$2a$10$examplePasswordHash6'),
(107, 'Ananya Nair', 'ananya.nair@example.com', '$2a$10$examplePasswordHash7'),
(108, 'Ishaan Thakur', 'ishaan.thakur@example.com', '$2a$10$examplePasswordHash8'),
(109, 'Aryan Rao', 'aryan.rao@example.com', '$2a$10$examplePasswordHash9'),
(110, 'Diya Singh', 'diya.singh@example.com', '$2a$10$examplePasswordHash10'),
(111, 'Kabir Joshi', 'kabir.joshi@example.com', '$2a$10$examplePasswordHash11'),
(112, 'Krishna Iyer', 'krishna.iyer@example.com', '$2a$10$examplePasswordHash12'),
(113, 'Reyansh Pandey', 'reyansh.pandey@example.com', '$2a$10$examplePasswordHash13'),
(114, 'Ayaan Mehta', 'ayaan.mehta@example.com', '$2a$10$examplePasswordHash14'),
(115, 'Anaya Mishra', 'anaya.mishra@example.com', '$2a$10$examplePasswordHash15'),
(116, 'Navya Kulkarni', 'navya.kulkarni@example.com', '$2a$10$examplePasswordHash16'),
(117, 'Naira Desai', 'naira.desai@example.com', '$2a$10$examplePasswordHash17'),
(118, 'Dhruv Saxena', 'dhruv.saxena@example.com', '$2a$10$examplePasswordHash18'),
(119, 'Sara Bajaj', 'sara.bajaj@example.com', '$2a$10$examplePasswordHash19'),
(120, 'Arnav Malhotra', 'arnav.malhotra@example.com', '$2a$10$examplePasswordHash20'),
(121, 'Rahul Kumar', 'rahul.kumar@example.com', '$2a$10$examplePasswordHash21'),
(122, 'Rohit Gupta', 'rohit.gupta@example.com', '$2a$10$examplePasswordHash22'),
(123, 'Pooja Sharma', 'pooja.sharma@example.com', '$2a$10$examplePasswordHash23'),
(124, 'Karan Singh', 'karan.singh@example.com', '$2a$10$examplePasswordHash24'),
(125, 'Sonia Mehta', 'sonia.mehta@example.com', '$2a$10$examplePasswordHash25'),
(126, 'Anil Kapoor', 'anil.kapoor@example.com', '$2a$10$examplePasswordHash26'),
(127, 'Deepak Verma', 'deepak.verma@example.com', '$2a$10$examplePasswordHash27'),
(128, 'Sneha Jain', 'sneha.jain@example.com', '$2a$10$examplePasswordHash28'),
(129, 'Ajay Mishra', 'ajay.mishra@example.com', '$2a$10$examplePasswordHash29'),
(130, 'Nisha Reddy', 'nisha.reddy@example.com', '$2a$10$examplePasswordHash30'),



INSERT INTO user_roles (user_id, roles) VALUES
(101, 'RIDER'),
(102, 'RIDER'),
(102, 'DRIVER'),
(103, 'RIDER'),
(103, 'DRIVER'),
(104, 'RIDER'),
(104, 'DRIVER'),
(105, 'RIDER'),
(105, 'DRIVER'),
(106, 'RIDER'),
(106, 'DRIVER'),
(107, 'RIDER'),
(107, 'DRIVER'),
(108, 'RIDER'),
(108, 'DRIVER'),
(109, 'RIDER'),
(109, 'DRIVER'),
(110, 'RIDER'),
(110, 'DRIVER'),



INSERT INTO rider ( user_id, rating) VALUES
( 101, 4.9);

INSERT INTO wallet(user_id,balance) VALUES
(101,100),
(102,500);

INSERT INTO driver (id, user_id, rating, available, current_location) VALUES
(102, 102, 4.7, true, ST_GeomFromText('POINT(77.1025 28.7041)', 4326)),
(103, 103, 4.8, true, ST_GeomFromText('POINT(77.2167 28.6667)', 4326)),
(104, 104, 4.6, true, ST_GeomFromText('POINT(77.2273 28.6353)', 4326)),
(105, 105, 4.9, true, ST_GeomFromText('POINT(77.2500 28.5500)', 4326)),
(106, 106, 4.3, true, ST_GeomFromText('POINT(77.2000 28.6200)', 4326)),
(107, 107, 4.4, true, ST_GeomFromText('POINT(77.2800 28.5900)', 4326)),
(108,108, 4.5, true, ST_GeomFromText('POINT(77.2600 28.6800)', 4326)),
(109, 109, 4.6, true, ST_GeomFromText('POINT(77.2200 28.6400)', 4326)),
(110, 110, 4.7, true, ST_GeomFromText('POINT(77.2700 28.6700)', 4326)),
