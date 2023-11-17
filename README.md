# AdvancedAppDev Reservation Backend

## Prerequisites
Before starting, make sure you have the following tools installed on your machine:
- Maven
- MySQL Workbench or MySQL Shell
- Docker (Desktop for Windows)

## 1. Database setup
1.1 Use either MySQL Workbench or the CLI to create a database named db_reservation.

1.2  Execute the `/src/main/resources/schema.sql` script to generate the necessary tables for the database.

(1.3) Optionally, run the `/src/main/resources/init.sql` script to populate the database with mock data.

## 2. Project Setup

2.1 Clone the repository to your local machine and navigate to the project directory

2.2 To run the project locally, use the following command:
```sh
mvn spring-boot:run
```

2.3 To build the project, run the following commands:
```sh
mvnw clean package
```

2.3.1 Run the generated JAR file with:
```sh
java -jar ./target/[filename].jar
```
Replace [filename] with the actual JAR file name, e.g., reservation-backend-0.0.1-SNAPSHOT.jar.

2.4 Access the backend at http://localhost:8080

## 3. Docker setup
3.1 Build the Docker images and start containers for MySQL and the Java API using
```sh
docker-compose up
```

3.2 Access the backend at http://localhost:8080
