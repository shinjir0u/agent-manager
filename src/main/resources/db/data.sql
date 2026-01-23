INSERT INTO sale_executive_status (status) 
SELECT 'ACTIVE'
WHERE NOT EXISTS (
	SELECT 1 FROM sale_executive_status WHERE status = 'ACTIVE'
);

INSERT INTO sale_executive_status (status) 
SELECT 'TERMINATED'
WHERE NOT EXISTS (
	SELECT 1 FROM sale_executive_status WHERE status = 'TERMINATED'
);

INSERT INTO roles (role) 
SELECT 'ADMIN'
WHERE NOT EXISTS (
	SELECT 1 FROM roles WHERE role = 'ADMIN'
);

INSERT INTO back_office (username, email, password, role)
SELECT 'admin1', 'admin1@example.com', '$2a$10$tUNRABJfB2GNRLUQnvSV1utEcyeooQxuSQ0SB3.Te/KeZupCpZPRm', 1
WHERE NOT EXISTS (
    SELECT 1 FROM back_office WHERE email = 'admin1@example.com'
);

INSERT INTO back_office (username, email, password, role)
SELECT 'admin2', 'admin2@example.com', '$2a$10$./9USLJ6Xj/EkPvEu7ZGsOzCwTuLk1j5uh2Gu6UIZcJcteLw0ss4m', 1
WHERE NOT EXISTS (
    SELECT 1 FROM back_office WHERE email = 'admin2@example.com'
);

INSERT INTO sale_executives (username, email, password, phone_number, status)
SELECT 'dse001', 'dse001@example.com', 'pw123', '09410000001', 1
WHERE NOT EXISTS (
    SELECT 1 FROM sale_executives WHERE email = 'dse001@example.com'
);

INSERT INTO sale_executives (username, email, password, phone_number, status)
SELECT 'dse002', 'dse002@example.com', 'pw456', '09410000002', 1
WHERE NOT EXISTS (
    SELECT 1 FROM sale_executives WHERE email = 'dse002@example.com'
);

INSERT INTO registrations (agent_name, phone_number, registered_at, sale_executive_id)
SELECT 'Agent A', '091234567', NOW(), 1
WHERE NOT EXISTS (
    SELECT 1 FROM registrations WHERE agent_name = 'Agent A'
);

INSERT INTO registrations (agent_name, phone_number, registered_at, sale_executive_id)
SELECT 'Agent B', '092345678', NOW(), 1
WHERE NOT EXISTS (
    SELECT 1 FROM registrations WHERE agent_name = 'Agent B'
);

INSERT INTO registrations (agent_name, phone_number, registered_at, sale_executive_id)
SELECT 'Agent C', '098765432', NOW(), 2
WHERE NOT EXISTS (
    SELECT 1 FROM registrations WHERE agent_name = 'Agent C'
);