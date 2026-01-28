
# Todo Management System - Backend

## Overview
The **Todo Management Backend** is a RESTful API built with **Spring Boot** that provides the core logic for a task management application. It handles user authentication, role-based authorization, and CRUD operations for todo items. The application uses **Spring Security** with **JWT** for secure access and **PostgreSQL** as the persistence layer.

## 🛠 Tech Stack
* **Java Version:** 25
* **Framework:** Spring Boot 4.0.1
* **Database:** PostgreSQL
* **Security:** Spring Security, JWT (JSON Web Tokens)
* **ORM:** Spring Data JPA (Hibernate)
* **Build Tool:** Maven

## ✨ Features
* **User Authentication:** Secure registration and login API with JWT generation.
* **Role-Based Access Control (RBAC):**
    * **ADMIN:** Can create, update, and delete todos.
    * **USER:** Can view todos and mark them as complete/incomplete.
* **Todo Management:** Full CRUD operations for todo tasks.
* **Exception Handling:** Global exception handling for consistent API responses.

## 🚀 Getting Started

### Prerequisites
* Java Development Kit (JDK) 25 or later
* Maven 3.3+ (or use the provided `mvnw` wrapper)
* PostgreSQL installed and running

### 1. Database Configuration
1. Create a PostgreSQL database named `todo_db`.
2. Update the `src/main/resources/application.properties` file if your credentials differ:
```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db
    spring.datasource.username=postgres
    spring.datasource.password=1234
```

### 2. Run the Application
Navigate to the root directory and run the application using Maven:
```bash
# Using Maven Wrapper (Windows)
./mvnw.cmd spring-boot:run

# Using Maven Wrapper (Linux/Mac)
./mvnw spring-boot:run
```

The server will start on `http://localhost:8080`.

## 🔌 API Endpoints

### Authentication

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/auth/register` | Register a new user | Public |
| `POST` | `/api/auth/login` | Login and receive JWT | Public |

### Todos

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/todos` | Get all todos | Admin, User |
| `GET` | `/api/todos/{id}` | Get todo by ID | Admin, User |
| `POST` | `/api/todos` | Create a new todo | Admin |
| `PUT` | `/api/todos/{id}` | Update an existing todo | Admin |
| `DELETE` | `/api/todos/{id}` | Delete a todo | Admin |
| `PATCH` | `/api/todos/{id}/complete` | Mark todo as completed | Admin, User |
| `PATCH` | `/api/todos/{id}/in-complete` | Mark todo as incomplete | Admin, User |

## 🛡️ Security Configuration
* **JWT Secret:** Configured in `application.properties` under `app.jwt-secret`.
* **Token Expiration:** Default is set to 7 days (604800000 ms).
