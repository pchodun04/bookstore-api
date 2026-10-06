# Bookstore API

REST API for an online bookstore and shopping cart, built with Java and Spring Boot.

The main focus of the project is shopping cart management (adding items, accumulating quantities, calculating subtotals and automated removal) using relational data modeling in JPA.

## Features

* User registration and login
* Password change and profile updates
* Book catalog with sorting by title, author, and price
* Search books by title
* Safe quantity updates and auto-deletion when quantity reaches 0
* Custom exception handling with standard HTTP status codes
* Swagger/OpenAPI documentation
* Unit tests with JUnit 5 and Mockito
* Microsoft SQL Server running in Docker

## Stack

* Java 21
* Spring Boot 3
* Spring Web MVC
* Spring Data JPA / Hibernate
* Microsoft SQL Server
* Docker Compose
* Maven
* JUnit 5, Mockito, AssertJ
* Swagger / OpenAPI (springdoc)

## Endpoints

### Users (`/user`)
| Method | Endpoint | Description |
|---|---|---|
| POST | /user | Register user |
| POST | /user/login | Log in |
| GET | /user | List all users |
| GET | /user/{id} | Get user by ID |
| PUT | /user/{id} | Update user name |
| PUT | /user/{id}/password | Change password |
| DELETE | /user/{id} | Delete user |

### Books (`/data`)
| Method | Endpoint | Description |
|---|---|---|
| GET | /data | List books (supports `sortBy=author/title/price`) |
| GET | /data/{title} | Find book by title |
| POST | /data | Add book |
| PUT | /data/{id} | Update book |
| DELETE | /data/{id} | Delete book |

### Cart (`/cart`)
| Method | Endpoint | Description |
|---|---|---|
| GET | /cart/{userId} | View user's cart |
| GET | /cart/{userId}/{bookId} | Get single item details in cart |
| POST | /cart/{userId}/add/{bookId} | Add book to cart / increase quantity |
| POST | /cart/{userId}/remove/{bookId} | Decrease quantity / remove from cart |

All endpoints and request schemas are documented in Swagger UI:  
http://localhost:8080/swagger-ui.html

## Running Locally

### Requirements:
* Java 21+
* Docker Desktop

### 1. Start SQL Server:
```powershell
docker compose up -d
```

### 2. Start the application:
```powershell
cd demo
.\mvnw.cmd spring-boot:run
```
*(or run `start.bat` in the root folder to start Docker and the app in one step)*

The API runs at:  
http://localhost:8080

Swagger UI:  
http://localhost:8080/swagger-ui.html

## Tests

Unit tests covering cart, user, and book service logic:

```powershell
cd demo
.\mvnw.cmd clean test
```
