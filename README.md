# Airline Management System (AMS) ✈️

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.0-blue)](https://spring.io/projects/spring-cloud)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

A robust, scalable, and modern Microservices-based Airline Management System (GDS) built with Spring Boot, Spring Cloud, and MySQL.

---

## 🏗️ Architecture Overview

The system follows a distributed microservices architecture, ensuring high availability, scalability, and ease of maintenance.

### Key Components

- **[Common Library](./microservices/common-lib)**: Shared domain models, DTOs, payloads, and utilities used across all microservices.
- **[Location Service](./microservices/services/location-service)**: Manages geographical data, including cities and airports.
- **[User Service](./microservices/services/user-service)**: Handles user registration, profiles, and security.
- **[Cloud Infrastructure](./microservices/cloud)**: (In Progress) Dedicated module for Service Discovery (Eureka), Config Server, and API Gateway.

---

## 🚀 Features

### 📍 Location Management

- **City Operations**: CRUD operations for cities with support for pagination, sorting, and search.
- **Airport Operations**: Comprehensive airport management linked to specific cities.
- **Search & Filter**: Advanced searching by name, country code, and city-airport relationships.

### 👤 User Management

- **Security**: Secured using Spring Security.
- **Profile Management**: Scalable user data handling with MySQL.

### 🛠️ Developer Experience

- **Common Lib**: Reusable components to maintain DRY principle across services.
- **Standardized Responses**: Consistent API response structures using `ApiResponse`.

---

## 🛠️ Tech Stack

- **Backend**: Java 21, Spring Boot 4.0.5
- **Microservices**: Spring Cloud 2025.1.0
- **Database**: MySQL 8+
- **Persistence**: Spring Data JPA / Hibernate
- **Tooling**: Lombok, Maven, Jakarta Validation
- **Architecture**: RESTful APIs, Microservices

---

## 📂 Project Structure

```text
.
├── frontend                # (Upcoming) Frontend application
└── microservices
    ├── common-lib          # Shared DTOs, Enums, and Payloads
    ├── cloud               # Infrastructure (Gateway, Registry, Config)
    └── services            # Business Logic Microservices
        ├── location-service # City & Airport Management (Port: 5004)
        └── user-service     # Authentication & Profiles (Port: 5001)
```

---

## 🚦 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.9+**
- **MySQL 8.0+**

### Database Setup

1. Create a MySQL database named `airline_location_db`:
    ```sql
    CREATE DATABASE airline_location_db;
    ```
2. Update the `application.yaml` files in `location-service` and `user-service` with your MySQL credentials.

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/airline-management.git
    cd airline-management
    ```

2. Build the project:
    ```bash
    cd microservices
    mvn clean install
    ```

### Running the Services

You can run each service individually:

```bash
# In separate terminals
mvn spring-boot:run -pl services/location-service
mvn spring-boot:run -pl services/user-service
```

---

## 📋 API Documentation (Brief)

### Location Service (Port 5004)

| Method | Endpoint                  | Description                     |
| :----- | :------------------------ | :------------------------------ |
| `GET`  | `/api/cities`             | List all cities (paginated)     |
| `POST` | `/api/cities`             | Create a new city               |
| `GET`  | `/api/airports`           | List all airports               |
| `GET`  | `/api/airports/city/{id}` | Get airports in a specific city |

### User Service (Port 5001)

| Method | Endpoint | Description        |
| :----- | :------- | :----------------- |
| `GET`  | `/`      | Service Status     |
| `GET`  | `/help`  | Help documentation |

---

## 🤝 Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.
