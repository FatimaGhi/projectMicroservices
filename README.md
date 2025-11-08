# Microservices Project

This project is a **Spring Boot Microservices Architecture** for managing students and their filieres with **JWT Authentication** and **OpenFeign** communication.

---

## Table of Contents
- [Overview](#overview)
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Services](#services)
  - [1. Auth Service](#1-auth-service)
  - [2. Student Service](#2-student-service)
  - [3. Filier Service](#3-filier-service)
- [Database](#database)
- [Security](#security)
- [OpenFeign Communication](#openfeign-communication)
- [Exception Handling](#exception-handling)
- [Swagger Documentation](#swagger-documentation)
- [How to Run](#how-to-run)
- [Notes](#notes)

---

## Overview
This project consists of 3 microservices:

1. **Auth Service:** Handles login, JWT generation (access + refresh tokens), and authentication.
2. **Student Service:** Manages CRUD operations for students.
3. **Filier Service:** Manages CRUD operations for filieres (departments/majors).

All services are independent with their own database but communicate via **OpenFeign**. Admin roles can perform restricted actions like deleting or updating users.

---

## Technologies
- Java 17+
- Spring Boot 3
- Spring Security & OAuth2 JWT
- PostgreSQL
- OpenFeign
- Lombok
- MapStruct
- Swagger/OpenAPI
- Maven

---

## Project Structure


```text
microservices-project/
├── auth/
│   ├── src/main/java/com/example/auth/
│   │   ├── config/         # Security & RSA keys
│   │   ├── controller/     # Login, refresh endpoints
│   │   ├── entities/       # User, Rolle, LoginRequest
│   │   ├── repository/     # UserRepo, RoleRepo
│   │   ├── service/        # AuthService, UserDetailsServiceImpl
│   │   └── AuthApplication.java
│   └── resources/keys/
│
├── student-service/
│   ├── src/main/java/com/example/student/
│   │   ├── configuration/  # Security & RSA keys
│   │   ├── controller/     # StudentController
│   │   ├── dto/            # FiliereDto, RequestDTO, ResponseDTO
│   │   ├── entities/       # Student entity
│   │   ├── interface/      # StudentService, Filiere
│   │   ├── mapper/         # StudentMapper
│   │   ├── repo/           # StudentRepo
│   │   ├── service/        # StudentServiceImpl
│   │   └── shared/         # Exception handlers & response classes
│   └── resources/keys/
│
├── filier-service/
│   ├── src/main/java/com/example/filier/
│   │   ├── controller/     # FilierController
│   │   ├── entities/       # Filier entity
│   │   ├── repository/     # FilierRepo
│   │   ├── service/        # FilierServiceImpl
│   │   └── FilierApplication.java
│   └── resources/

```

## Services

### 1. Auth Service
- **Purpose:** Authenticate users and issue JWT tokens (access + refresh).
- **Endpoints:**
  - `POST /login` – Authenticate and return JWT tokens.
  - `POST /refresh` – Refresh access token using refresh token.
- **Roles:**
  - `ROLE_USER` – Can access general endpoints.
  - `ROLE_ADMIN` – Can manage users and sensitive actions.
- **JWT Keys:** Stored in `resources/keys/`.
- **Tables:**
  - `users`: `id`, `username`, `password`
  - `roles`: `id`, `name`
  - User-Roles relation: One user can have one or multiple roles.

---

### 2. Student Service
- **Purpose:** Manage students.
- **Endpoints:**
  - `GET /students` – Get all students
  - `GET /students/{id}` – Get student by ID
  - `POST /students` – Create student
  - `PUT /students/{id}` – Update student
  - `DELETE /students/{id}` – Delete student (**Admin only**)
- **Communication:** Uses OpenFeign to call Filier Service to get filiere info for each student.
- **Tables:**
  - `students`: `id`, `first_name`, `last_name`, `email`, `filier_id`
- **Exception Handling:** Custom exceptions with `GlobalExceptionResponse.java`.

---

### 3. Filier Service
- **Purpose:** Manage filieres.
- **Endpoints:**
  - `GET /filieres` – Get all filieres
  - `GET /filieres/{id}` – Get filiere by ID
  - `POST /filieres` – Create filiere
  - `PUT /filieres/{id}` – Update filiere
  - `DELETE /filieres/{id}` – Delete filiere
- **Tables:**
  - `filieres`: `id`, `name`, `description`

---

## Database
- Each service uses its **own PostgreSQL database**.
- Example configuration (`application.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<service_db>
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

## Tables Summary

- **Auth:** `users`, `roles`
- **Student:** `students`
- **Filier:** `filieres`

---

## Security

- JWT-based authentication with **access** and **refresh tokens**.
- **Admin role** required for sensitive operations like `DELETE` or `UPDATE`.
- RSA private/public keys for signing and verifying tokens.

**Authentication workflow:**
1. User sends credentials to Auth Service.
2. AuthService validates and returns access + refresh tokens.
3. Student/Filier services validate token before allowing access.

---

## OpenFeign Communication

**Student Service** calls **Filier Service** for filiere information:

```java
@FeignClient(name = "filier-service", url = "http://localhost:8086")
public interface FiliereClient {
    @GetMapping("/filieres/{id}")
    FiliereDto getFiliereById(@PathVariable Long id);
}
```

- **JWT token** must be passed in the request header for authentication.

---

## Exception Handling

- `GlobalExceptionResponse` handles all exceptions globally.
- Example exceptions:
  - `ResourceNotFoundException`
  - `InvalidRequestException`
- Provides consistent API error response format.

---

## Swagger Documentation

- Enabled for all services:
  - **Auth:** [http://localhost:8080/swagger-ui.html](http://localhost:3050/swagger-ui.html)
  - **Student:** [http://localhost:8081/swagger-ui.html](http://localhost:8085/swagger-ui.html)
  - **Filier:** [http://localhost:8082/swagger-ui.html](http://localhost:8086/swagger-ui.html)
- Auto-generates API documentation and testing interface.

---

## How to Run

1. Clone the repository:
```bash
git clone <repo-url>
cd microservices-project
Start PostgreSQL databases for each service.

Build and run services individually:

bash
Copy code
cd auth
mvn clean spring-boot:run

cd ../student-service
mvn clean spring-boot:run

cd ../filier-service
mvn clean spring-boot:run
Use Auth Service /login to get JWT token.

Include JWT token in headers for Student and Filier service requests.

```