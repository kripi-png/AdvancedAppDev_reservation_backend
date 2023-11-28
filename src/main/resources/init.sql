-- users
INSERT INTO users (userId, email, password, firstName, lastName, roles)
VALUES
    (1, "admin@gmail.com", "$2a$10$SzHo3dWv01JcuYlBFPjh0.ZLdN.EC1qUFpPCeegzAhdaHjL99l/Ay", "admin", "admin", "ROLE_ADMIN"),
    (2, 'user2@example.com', '$2a$10$Pb4drVyOnyBuEpf71HKi.eQmRRaIcbQrq28vO6SaVISe3zF2YeTMW', 'John', 'Doe', "ROLE_USER"),
    (3, 'user3@example.com', '$2a$10$Pb4drVyOnyBuEpf71HKi.eQmRRaIcbQrq28vO6SaVISe3zF2YeTMW', 'Alice', 'Smith', "ROLE_USER"),
    (4, 'user4@example.com', '$2a$10$Pb4drVyOnyBuEpf71HKi.eQmRRaIcbQrq28vO6SaVISe3zF2YeTMW', 'Bob', 'Johnson', "ROLE_USER");

-- apartments
INSERT INTO apartments (ownerId, rentAmount, area, apartmentType, streetName, cityName, postalCode, apartmentNumber, roomNormalCount, roomKitchenCount, roomBalconyCount, roomBathroomCount, description)
VALUES
    -- the first apartment is editable by admin user
    (1, 1200.00, 75.5, 'TWO_ROOM_FLAT', '123 Main St', 'Cityville', '12345', 'A1', 2, 1, 1, 2, 'Spacious two-bedroom apartment with a balcony overlooking the city.'),
    (2, 900.00, 50.0, 'ONE_ROOM_FLAT', '456 Elm St', 'Townsville', '67890', 'B2', 1, 0, 0, 1, 'Cozy studio apartment with a view of the park.'),
    (2, 1500.00, 90.0, 'TWO_ROOM_FLAT', '789 Oak St', 'Villagetown', '54321', 'C3', 3, 1, 1, 2, 'Modern three-bedroom apartment with a kitchen island and a large living room.'),
    (4, 800.00, 40.0, 'ONE_ROOM_FLAT', '101 Pine St', 'Hamletville', '98765', 'D4', 1, 1, 0, 1, 'Charming one-bedroom apartment in a quiet neighborhood.');

-- rent entries
INSERT INTO rented_apartments (userId, apartmentId)
VALUES
    (2, 1),
    (3, 2),
    (4, 3),
    (2, 4);

-- applications
-- status is converted to and from ApplicationStatus enum
INSERT INTO applications (message, userId, apartmentId, status)
VALUES
    ('Interested in renting the two-bedroom apartment.', 2, 1, 0),
    ('Looking to lease the cozy studio apartment.', 3, 2, 0),
    ('Interested in the modern three-bedroom apartment.', 4, 3, 2),
    ('Considering the charming one-bedroom apartment.', 2, 4, 0),
    ('Interested in the spacious two-bedroom apartment.', 3, 1, 0),
    ('Looking to rent the cozy studio apartment with a park view.', 4, 2, 0),
    ('Interested in leasing the one-bedroom apartment with a kitchen.', 2, 4, 1),
    ('Considering the modern three-bedroom apartment for long-term rental.', 3, 3, 0);