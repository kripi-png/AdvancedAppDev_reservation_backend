CREATE TABLE users (
  userId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  firstName VARCHAR(32) NOT NULL,
  lastName VARCHAR(32) NOT NULL,
  roles VARCHAR(255) DEFAULT "ROLE_USER"
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE apartments (
  apartmentId INTEGER NOT NULL AUTO_INCREMENT,
  ownerId INTEGER NOT NULL,
  rentAmount FLOAT NOT NULL,
  area FLOAT NOT NULL,
  apartmentType VARCHAR(45) NOT NULL,
  description TEXT,
  
  -- address
  streetName VARCHAR(45),
  cityName VARCHAR(45),
  postalCode VARCHAR(10),
  apartmentNumber VARCHAR(10),

  -- rooms
  roomNormalCount INTEGER DEFAULT 0,
  roomKitchenCount INTEGER DEFAULT 0,
  roomBalconyCount INTEGER DEFAULT 0,
  roomBathroomCount INTEGER DEFAULT 0,
  
  PRIMARY KEY (apartmentId),
  FOREIGN KEY (ownerId) REFERENCES users (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE rented_apartments (
    id INTEGER NOT NULL AUTO_INCREMENT,
    userId INTEGER NOT NULL,
    apartmentId INTEGER NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
    FOREIGN KEY (apartmentId) REFERENCES apartments (apartmentId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE applications (
  applicationId INTEGER NOT NULL AUTO_INCREMENT,
  message TEXT NOT NULL,
  status VARCHAR(20) DEFAULT "PENDING",
  userId INTEGER NOT NULL,
  apartmentId INTEGER NOT NULL,
  
  PRIMARY KEY (applicationId),
  FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
  FOREIGN KEY (apartmentId) REFERENCES apartments (apartmentId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;