

-- Insert Regular Users
INSERT INTO Users (
    id,
    name,
    surname,
    date_of_birth,
    phone_number,
    gender,
    email,
    password,
    role
)
VALUES (
    10000,
    'John',
    'Doe',
    '1995-05-15',
    '05001234567',
    'Male',
    'john.doe@example.com',
    'password123',
    'USER'
);

INSERT INTO Users (
    id,
    name,
    surname,
    date_of_birth,
    phone_number,
    gender,
    email,
    password,
    role
)
VALUES (
           10001,
    'Jane',
    'Smith',
    '1992-08-25',
    '05007654321',
    'Female',
    'jane.smith@example.com',
    'password123',
    'USER'
);

INSERT INTO Users (
    id,
    name,
    surname,
    date_of_birth,
    phone_number,
    gender,
    email,
    password,
    role
)
VALUES (
           10002,
    'Emily',
    'Clark',
    '1988-12-10',
    '05009876543',
    'Female',
    'emily.clark@example.com',
    'password123',
    'USER'
);

-- Insert Appointments for John Doe (user_id = 2)
INSERT INTO appointment (
    id,
    date,
    time,
    user_id
)
VALUES (
           100000,
    CURRENT_DATE(),
    '10:00:00',
           10002
);

INSERT INTO appointment (
    id,
    date,
    time,
    user_id
)
VALUES (
           100010,
    CURRENT_DATE(),
    '14:30:00',
           10001
);

-- Insert Appointment for Jane Smith (user_id = 3)
INSERT INTO appointment (
    id,
    date,
    time,
    user_id
)
VALUES (
           8468648,
    CURRENT_DATE(),
    '09:00:00',
           10000
);

-- Insert Appointment for Emily Clark (user_id = 4)
INSERT INTO appointment (
    id,
    date,
    time,
    user_id
)
VALUES (
           46864864,
    CURRENT_DATE(),
    '11:30:00',
           10000
);

-- Insert Appointments for John Doe (user_id = 10002)
INSERT INTO appointment (
    id,
    date,
    time,
    user_id
)
VALUES
    (496946, '2023-10-01', '10:00:00', 10002),
    (846846, '2023-10-05', '11:30:00', 10002),
    (97497947, '2023-10-10', '15:00:00', 10002);

-- Insert Appointments for Jane Smith (user_id = 10001)
INSERT INTO appointment (
    id,
    date,
    time,
    user_id
)
VALUES
    (100010000, '2025-05-10', '09:30:00', 10000),
    (100011000, '2025-05-12', '14:30:00', 10001),
    (100012000, '2025-05-12', '16:00:00', 10001);

-- Insert Appointments for Emily Clark (user_id = 10000)
INSERT INTO appointment (
    id,
    date,
    time,
    user_id
)
VALUES
    (139390020, '2025-05-13', '08:00:00', 10000),
    (108594021, '2025-05-16', '13:00:00', 10000),
    (1384022, '2025-05-15', '17:30:00', 10000);
