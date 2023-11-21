


# AdvancedAppDev Reservation Backend

## Prerequisites
Before starting, make sure you have the following tools installed on your machine:
- Maven
- MySQL Workbench or MySQL Shell
- Docker (Desktop for Windows)
- Lombok plugin for IDE of choice
  - For IntelliJ IDEA (recommended)
    - Go to "Preferences" > "Plugins" > "Browse repositories" > Search for "Lombok" > Install and restart IntelliJ IDEA.
  - For Eclipse:
    - Download latest Lombok jar from [Maven Central](https://mvnrepository.com/artifact/org.projectlombok/lombok)
    - Run the jarfile with the command `java -jar lombok.x.x.x.jar`
    - Restart eclipse

## Note: If you are going to be working on frontend only, you can skip to step 3

## 1. Database setup
1.1 Use either MySQL Workbench or the CLI to create a database named db_reservation.

1.2  Execute the `/src/main/resources/schema.sql` script to generate the necessary tables for the database.
  - This can be done in MySQL Workbench by selecting the folder icon in the SQL Editor view
  - Alernatively, connect to the databse with MySQL Shell and run command `SOURCE ./path/to/schema.sql`
  - Both approaches may require dropping the tables (or the entire database) and then recreating the database before running the script.

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
### 3.1 Create a docker network for backend/frontend communcation
```shell
docker network create advappdev_reservation-net
```
### 3.2 Initial Setup (First Time or After Pulling New Changes)
When setting up Docker for the first time or after pulling new changes, you must build/rebuild the images.
```shell
docker compose down --remove-orphans
docker compose up -d --build
```
### 3.3 Start the container
When starting the container again, use the following command:
```shell
docker compose up -d
```

### 3.4 Access the Backend
Access the backend at http://localhost:8080.
Note that the page may say that you are **not authorized** to view the contents. **This still means that it works**, however.
