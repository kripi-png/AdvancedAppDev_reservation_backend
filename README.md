


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

## 1. Database setup
1.1 Use either MySQL Workbench or the CLI to create a database named db_reservation.

1.2  Execute the `/src/main/resources/schema.sql` script to generate the necessary tables for the database.
  - This can be done in MySQL Workbench by selecting the folder icon in the SQL Editor view
  - Alernatively, connect to the databse with MySQL Shell and run command `SOURCE ./path/to/schema.sql`
  - Both approaches may require dropping the tables (or the entire database) and then recreating the database before running the script.

(1.3) Optionally, run the `/src/main/resources/init.sql` script to populate the database with mock data.

## 2. Project Setup

2.1 Clone the repository to your local machine and navigate to the project directory

2.2 Generate secret and public keys for JWT: app.key and app.pub, respectively

2.2.1 Navigate to `/src/main/resources`

2.2.2 Run the following commands
- Note: OpenSSL does not work natively on Windows. Consider using Windows Subsystem for Linux (WSL) to run these commands. If you haven't set up WSL, you can follow [these instructions](https://learn.microsoft.com/en-us/windows/wsl/install) to install and configure it on your Windows machine.
```sh
openssl genrsa -out app.key 2048
openssl rsa -in app.key -pubout -outform PEM -out app.pub
```
The first command generates a 2048-bit secret and saves it to file called `app.key`.
The second command then extracts the public key out of the secret and names that file `app.pub`.  

2.3 To run the project locally, use the following command:
```sh
mvn spring-boot:run
```

2.4 To build the project, run the following commands:
```sh
mvnw clean package
```

2.4.1 Run the generated JAR file with:
```sh
java -jar ./target/[filename].jar
```
Replace [filename] with the actual JAR file name, e.g., reservation-backend-0.0.1-SNAPSHOT.jar.

2.5 Access the backend at http://localhost:8080

## 3. Docker setup
3.1 Build the Docker images and start containers for MySQL and the Java API using
```sh
docker-compose up
```

3.2 Access the backend at http://localhost:8080
