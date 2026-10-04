# 💺 Seat Service

> Seat inventory and seat instance management microservice for the Airline Management System

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)

---

## Overview

The **Seat Service** is responsible for managing seat-level inventory within the Airline Management System.

It is designed to handle:

- Seat instance creation per flight instance
- Seat availability tracking
- Seat assignment and reservation

> **Status**: This service is currently under active development. Core scaffolding is in place.

---

## Planned Features

- Generate seat instances when a flight instance is created
- Track seat availability per cabin class
- Support seat selection and reservation
- Integrate with the booking flow

---

## Tech Stack

- Java 21, Spring Boot 4.0.5
- MySQL 8.0+, Spring Data JPA

---

## Getting Started

### Database Setup

```sql
CREATE DATABASE airline_seat_db;
```

### Configuration

Update `src/main/resources/application.yaml`:

```yaml
server:
  port: 5008

spring:
  application:
    name: seat-service
  datasource:
    url: jdbc:mysql://localhost:3306/airline_seat_db
    username: your_mysql_username
    password: your_mysql_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Run

```bash
mvn spring-boot:run
```

---

## Project Structure

```
seat-service/
├── src/main/java/com/aryan/
│   ├── controller/        # REST controllers (in progress)
│   └── SeatServiceApplication.java
└── pom.xml
```

---

## Dependencies

- **Flight Ops Service**: Seat instances are created per flight instance
- **Airline Core Service**: Aircraft seating configuration
- **Common Library**: Shared DTOs and utilities

---

## Related Services

- [Flight Ops Service](../flight-ops-service/README.md)
- [Pricing Service](../pricing-service/README.md)
- [Common Library](../../common-lib/README.md)

---

## License

Part of the Airline Management System — MIT License
