# 👤 User Service

> Authentication, authorization, and user management microservice for the Airline Management System

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4.3-green)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-0.13.0-blue)](https://github.com/jwtk/jjwt)

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#-database-schema)
- [Security](#-security)
- [Configuration](#-configuration)
- [Testing](#-testing)

---

## 🌟 Overview

The **User Service** is the authentication and authorization hub for the Airline Management System. It handles:

- User registration (signup)
- User authentication (login)
- JWT token generation and validation
- Role-based access control (Admin, Airline Owner, User)
- User profile management

This service is the security backbone that protects all other microservices.

---

## ✨ Features

### 🔐 Authentication & Authorization

- **Secure Registration**: Password encryption with BCrypt
- **JWT Authentication**: Stateless token-based authentication
- **Role-Based Access**: Three user roles (ADMIN, AIRLINE, USER)
- **Token Validation**: Secure token generation and verification

### 👥 User Management

- **Profile Retrieval**: Get user details by ID or email
- **User Listing**: Retrieve all registered users
- **Role Assignment**: Assign roles during registration

### 🛡️ Security Features

- Password encryption
- HTTP-only security headers
- Protected endpoints
- Cross-service authentication (X-User-Id, X-User-Email headers)

---

## 🛠️ Tech Stack

- **Java 21**: Latest LTS version
- **Spring Boot 4.0.5**: Application framework
- **Spring Security 6.x**: Authentication & authorization
- **Spring Data JPA**: Database access
- **JWT (jjwt 0.13.0)**: Token generation
- **MySQL 8.0+**: Database
- **Lombok**: Code simplification
- **Jakarta Validation**: Input validation

---

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.9+
- MySQL 8.0+

### Database Setup

Create the database:

```sql
CREATE DATABASE airline_user_db;
```

The tables will be created automatically on first run (using `ddl-auto: update`).

### Configuration

Update `src/main/resources/application.yaml`:

```yaml
server:
  port: 5001

spring:
  application:
    name: user-service
  datasource:
    url: jdbc:mysql://localhost:3306/airline_user_db
    username: your_mysql_username # Change this
    password: your_mysql_password # Change this
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Build and Run

```bash
# Build the project
mvn clean install

# Run the service
mvn spring-boot:run
```

The service will start on **http://localhost:5001**

---

## 📋 API Endpoints

### Base URL

```
http://localhost:5001
```

### Authentication Endpoints

#### 1. Register a New User

**Endpoint**: `POST /auth/signup`

**Description**: Create a new user account

**Request Body**:

```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "role": "USER"
}
```

**Request Fields**:
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `email` | String | ✅ | Valid email address |
| `password` | String | ✅ | Password (min 6 characters) |
| `firstName` | String | ✅ | User's first name |
| `lastName` | String | ✅ | User's last name |
| `phoneNumber` | String | ❌ | Phone number |
| `role` | String | ✅ | USER, AIRLINE, or ADMIN |

**Response** (201 Created):

```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "User registered successfully",
  "user": {
    "id": 1,
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "role": "USER"
  }
}
```

**Example cURL**:

```bash
curl -X POST http://localhost:5001/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER"
  }'
```

---

#### 2. Login

**Endpoint**: `POST /auth/login`

**Description**: Authenticate user and receive JWT token

**Request Body**:

```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
```

**Response** (200 OK):

```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful",
  "user": {
    "id": 1,
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER"
  }
}
```

**Example cURL**:

```bash
curl -X POST http://localhost:5001/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "SecurePass123!"
  }'
```

**Error Response** (401 Unauthorized):

```json
{
  "message": "Invalid email or password"
}
```

---

### User Management Endpoints

#### 3. Get User Profile

**Endpoint**: `GET /api/users/profile`

**Description**: Get authenticated user's profile

**Headers**:

```
X-User-Email: john.doe@example.com
```

**Response** (200 OK):

```json
{
  "id": 1,
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "role": "USER"
}
```

**Example cURL**:

```bash
curl -X GET http://localhost:5001/api/users/profile \
  -H "X-User-Email: john.doe@example.com"
```

---

#### 4. Get User by ID

**Endpoint**: `GET /api/users/{userId}`

**Description**: Retrieve user details by user ID

**Path Parameters**:

- `userId`: User identifier (Long)

**Response** (200 OK):

```json
{
  "id": 1,
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "role": "USER"
}
```

**Example cURL**:

```bash
curl -X GET http://localhost:5001/api/users/1
```

---

#### 5. Get All Users

**Endpoint**: `GET /api/users`

**Description**: Retrieve all registered users (Admin only)

**Response** (200 OK):

```json
[
  {
    "id": 1,
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER"
  },
  {
    "id": 2,
    "email": "jane.smith@example.com",
    "firstName": "Jane",
    "lastName": "Smith",
    "role": "AIRLINE"
  }
]
```

**Example cURL**:

```bash
curl -X GET http://localhost:5001/api/users
```

---

## 🗄️ Database Schema

### Users Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Roles

The system supports three user roles:

| Role          | Value     | Description               |
| ------------- | --------- | ------------------------- |
| User          | `USER`    | Regular user (passengers) |
| Airline Owner | `AIRLINE` | Airline company owner     |
| Administrator | `ADMIN`   | System administrator      |

---

## 🔒 Security

### Password Encryption

Passwords are encrypted using **BCrypt** hashing algorithm before storing in the database.

```java
// Password is never stored in plain text
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode(plainPassword);
```

### JWT Token

**Token Generation**:

- Tokens are signed using HS256 algorithm
- Contains user ID, email, and role
- Configurable expiration time

**Token Structure**:

```
Header.Payload.Signature
```

**Token Usage**:

1. Client receives token after successful login/signup
2. Client includes token in Authorization header for protected endpoints
3. Other services validate user by passing `X-User-Id` or `X-User-Email` headers

### Security Configuration

The service uses Spring Security with:

- CORS configuration
- CSRF protection (disabled for stateless API)
- HTTP Basic authentication
- Role-based authorization

---

## ⚙️ Configuration

### Environment Variables (Optional)

You can override configuration using environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/airline_user_db
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=yourpassword
export SERVER_PORT=5001
export JWT_SECRET=your-secret-key
export JWT_EXPIRATION=86400000
```

### Application Properties

Key configuration properties:

| Property                        | Default                                     | Description                |
| ------------------------------- | ------------------------------------------- | -------------------------- |
| `server.port`                   | 5001                                        | Service port               |
| `spring.datasource.url`         | jdbc:mysql://localhost:3306/airline_user_db | Database URL               |
| `spring.jpa.hibernate.ddl-auto` | update                                      | Schema generation strategy |
| `spring.jpa.show-sql`           | true                                        | Show SQL queries in logs   |

---

## 🧪 Testing

### Run Tests

```bash
mvn test
```

### Manual Testing with cURL

**1. Register a user**:

```bash
curl -X POST http://localhost:5001/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"test123","firstName":"Test","lastName":"User","role":"USER"}'
```

**2. Login**:

```bash
curl -X POST http://localhost:5001/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"test123"}'
```

**3. Get profile**:

```bash
curl -X GET http://localhost:5001/api/users/profile \
  -H "X-User-Email: test@test.com"
```

### Testing with Postman

Import the following collection structure:

1. **Signup** - POST `/auth/signup`
2. **Login** - POST `/auth/login`
3. **Get Profile** - GET `/api/users/profile`
4. **Get User by ID** - GET `/api/users/{id}`
5. **Get All Users** - GET `/api/users`

---

## 📁 Project Structure

```
user-service/
├── src/
│   ├── main/
│   │   ├── java/com/aryan/
│   │   │   ├── UserServiceApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java       # Signup & Login
│   │   │   │   ├── UserController.java       # User management
│   │   │   │   └── HomeController.java       # Health check
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java          # Auth business logic
│   │   │   │   └── UserService.java          # User business logic
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java       # Database access
│   │   │   ├── model/
│   │   │   │   └── User.java                 # JPA Entity
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java       # Security configuration
│   │   │   │   └── JwtConfig.java            # JWT configuration
│   │   │   └── mapper/
│   │   │       └── UserMapper.java           # Entity ↔ DTO mapping
│   │   └── resources/
│   │       └── application.yaml              # Configuration
│   └── test/
└── pom.xml
```

---

## 🔧 Troubleshooting

### Common Issues

**Issue**: Port 5001 already in use

```
Solution: Change port in application.yaml or kill the process using the port
lsof -ti:5001 | xargs kill -9  # Mac/Linux
netstat -ano | findstr :5001   # Windows
```

**Issue**: Database connection refused

```
Solution:
1. Ensure MySQL is running
2. Verify database credentials in application.yaml
3. Check if airline_user_db database exists
```

**Issue**: JWT token validation fails

```
Solution:
1. Ensure the same secret key is used for generation and validation
2. Check token expiration time
3. Verify token format (Bearer <token>)
```

---

## 🔗 Related Services

- [Location Service](../location-service/README.md) - Manages cities and airports
- [Airline Core Service](../airline-core-service/README.md) - Manages airlines and aircraft
- [Common Library](../../common-lib/README.md) - Shared DTOs and utilities

---

## 📞 API Support

For issues or questions:

- Check the main [README](../../../README.md)
- Open an [issue](https://github.com/your-username/airline-management/issues)

---

## 📄 License

This service is part of the Airline Management System, licensed under the MIT License.

---

<div align="center">

**User Service** | Part of Airline Management System

[⬆ Back to Top](#-user-service)

</div>
