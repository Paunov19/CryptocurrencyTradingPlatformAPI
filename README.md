# CryptocurrencyTradingPlatformAPI

This is a RESTful API-based **Cryptocurrency Trading Platform** built using **Spring Boot** and **MySQL**. The platform offers various features for users to manage their crypto transactions, including buying, selling, and tracking their transaction history. It also allows account reset, balance management, and transaction retrieval.

---

## Features
1. **User Account Management**:
    - User registration and authentication.
    - Account reset functionality (including balance reset).
2. **Transaction Management**:
    - Log and track transactions for buying and selling cryptocurrencies.
    - Calculate profit or loss based on buy and sell prices.
    - Retrieve all transactions for a user.
3. **Cryptocurrency Buy and Sell**:
    - Users can buy and sell real cryptocurrencies at current market prices.
    - Real-time cryptocurrency price retrieval

---

## Setup Instructions

### Prerequisites:
- **Java 17** or later
- **Spring Boot** for the framework
- **Maven** for building the project
- **MySQL** (or your preferred database)
- **Swagger UI** for API documentation
- **IntelliJ IDEA** or any other Java IDE

---

## Steps to Run the Application

### 1. Clone the Repository

git clone https://github.com/Paunov19/CryptocurrencyTradingPlatformAPI.git
cd CryptocurrencyTradingPlatformAPI


**2.Set up the Database**

If MySQL is used, update application.properties with your database configuration(write your MySQL username and password:

spring.datasource.username=  **your_username**

spring.datasource.password=  **your_password**

If PostgreSQL is used, need to update the whole application.properties file with your database configuration(copy and paste it in the file, then write your PostgreSQL username and password):

spring.datasource.url = jdbc:mysql://${DATABASE_URL:localhost}:5432/taskPriorityDB?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8

spring.datasource.username = **your PostgreSQL username**

spring.datasource.password = **your PostgreSQL password**

spring.jpa.show-sql = true

spring.jpa.hibernate.ddl-auto = update

spring.jpa.properties.hibernate.format_sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

**3. Build and Run the Application**

mvn clean install

mvn spring-boot:run

or

run it from your IDE

**4. API Documentation**

Open the Swagger UI in your browser for interactive API documentation:

http://localhost:8080/swagger-ui.html

In Swagger, we can manually test all API endpoints. Each endpoint requires specific request data. Follow the details provided for each request to ensure you send the correct input.

## **External Libraries and Tools Used**
- Spring Boot: Simplifies application setup and development.
- Spring Data JPA: Provides database access and ORM functionalities.
- Lombok: Reduces boilerplate code for models (Getters, Setters, etc.).
- Swagger: Generates API documentation and allows interactive API testing.
- Mockito: Used for writing unit tests and mocking dependencies.
- JUnit 5: Test framework for unit and integration tests.
  
