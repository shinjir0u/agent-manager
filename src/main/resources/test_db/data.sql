INSERT INTO admins (username, email, password)
VALUES ('admin1', 'admin1@example.com', 'hashed_pw'),
('admin2', 'admin2@example.com', 'hashed_pw2');

INSERT INTO sale_executives (username, email, password, phone_number)
VALUES ('dse001', 'dse001@example.com', 'pw123', '09410000001'),
('dse002', 'dse002@example.com', 'pw456', '09410000002');

INSERT INTO registrations (agent_name, phone_number, registered_at, sale_executive_id)
VALUES ('Agent A', '091234567', NOW(), 1),
('Agent B', '092345678', NOW(), 1),
('Agent C', '098765432', NOW(), 2);
