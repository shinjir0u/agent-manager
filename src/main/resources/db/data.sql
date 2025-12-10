INSERT INTO admins (username, email, password)
SELECT 'admin1', 'admin1@example.com', 'hashed_pw'
WHERE NOT EXISTS (
    SELECT 1 FROM admins WHERE email = 'admin1@example.com'
);

INSERT INTO admins (username, email, password)
SELECT 'admin2', 'admin2@example.com', 'hashed_pw2'
WHERE NOT EXISTS (
    SELECT 1 FROM admins WHERE email = 'admin2@example.com'
);

INSERT INTO sale_executives (username, email, password, phone_number)
SELECT 'dse001', 'dse001@example.com', 'pw123', '09410000001'
WHERE NOT EXISTS (
    SELECT 1 FROM sale_executives WHERE email = 'dse001@example.com'
);

INSERT INTO sale_executives (username, email, password, phone_number)
SELECT 'dse002', 'dse002@example.com', 'pw456', '09410000002'
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