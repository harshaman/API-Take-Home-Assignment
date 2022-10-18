# API-Take-Home-Assignment

Engineering Coding Test

# TASK 1
Comments have been added in the main.js file using key @COMMENT before the remarks.


# TASK 2

## Built With
- Java 11
- Spring Boot
- Apache Maven

## IDE
- IntelliJ

## API Client
- Postman

## Installation
1. Ensure Java 11 and Maven is installed.
2. Download the project and import it in an IDE of your choice (IntelliJ idea or Spring Tool Suite).
3. Ensure the port 8080 is available. If the port is to be updated, add the following property to the application.properties before running the application:
server:
  port: <<AVAILABLE_PORT_NO>>
4. Run the application as Spring Boot application.
5. Hit the Rest Endpoints described in the OpenApi3.yaml to test the application.

## Deployment
1. ``mvn clean package -DskipTests``
1. Create a jar file using the following command: ``java -jar target/DictionaryAPI-0.0.1-SNAPSHOT.jar``. This will generate the jar file in the target directory of the application.
2. Start the application as a service using nohup ``nohup java -jar target/DictionaryAPI-0.0.1-SNAPSHOT.jar &``
