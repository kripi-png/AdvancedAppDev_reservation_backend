CREATE TABLE users (
  userId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(255) NOT NULL,
  roles VARCHAR(255) NOT NULL DEFAULT ""
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE apartments (
  apartmentId INTEGER NOT NULL AUTO_INCREMENT,
  ownerId INTEGER NOT NULL,
  rentAmount FLOAT NOT NULL,
  area FLOAT NOT NULL,
  apartmentType VARCHAR(45) NOT NULL,
  
  -- address
  streetName VARCHAR(45),
  cityName VARCHAR(45),
  postalCode VARCHAR(10),
  apartmentNumber VARCHAR(10),

  -- rooms
  roomNormalCount INTEGER,
  roomKitchenCount INTEGER,
  roomBalconyCount INTEGER,
  roomBathroomCount INTEGER,
  
  PRIMARY KEY (apartmentId),
  FOREIGN KEY (ownerId) REFERENCES users (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE rented_apartments (
    userId INT NOT NULL,
    apartmentId INT NOT NULL,
    -- composite primary key as that is enough to identify a row
    PRIMARY KEY (userId, apartmentId),
    FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
    FOREIGN KEY (apartmentId) REFERENCES apartments (apartmentId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE applications (
  applicationId INTEGER NOT NULL AUTO_INCREMENT,
  message TEXT NOT NULL,
  userId INTEGER NOT NULL,
  apartmentId INTEGER NOT NULL,
  
  PRIMARY KEY (applicationId),
  FOREIGN KEY (userId) REFERENCES users (userId) ON DELETE CASCADE,
  FOREIGN KEY (apartmentId) REFERENCES apartments (apartmentId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;