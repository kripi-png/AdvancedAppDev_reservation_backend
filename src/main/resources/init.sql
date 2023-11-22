-- users
INSERT INTO users (email, password, firstName, lastName, roles) VALUES ("admin@gmail.com", "$2a$10$SzHo3dWv01JcuYlBFPjh0.ZLdN.EC1qUFpPCeegzAhdaHjL99l/Ay", "admin", "admin", "ROLE_ADMIN");

-- apartments
INSERT INTO apartments (ownerId, rentAmount, area, apartmentType)
    VALUES
        (17, 102, 25, 'ONE_ROOM_FLAT'),
        (17, 200, 30, 'TWO_ROOM_FLAT');