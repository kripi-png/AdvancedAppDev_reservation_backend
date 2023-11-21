#
# Build stage
#
FROM maven:3.8.5-openjdk-17-slim AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
# Add a wait script to wait for MySQL to be ready
ADD https://github.com/vishnubob/wait-for-it/raw/master/wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh", "mysqldb:3306", "--timeout=60", "--", "java", "-jar", "/app/runner.jar"]