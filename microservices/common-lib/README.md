# Common Library (common-lib) 📦

The `common-lib` module serves as the central shared library for the Airline Management System. it contains reusable domain objects, DTOs, and utility classes to ensure consistency across all microservices.

## 🚀 Purpose

- **Consistency**: Centralizes data structures (DTOs, Payloads) to avoid duplication.
- **Maintainability**: Changes to shared models are reflected across all services automatically.
- **Inter-service Communication**: Provides the standard contract for RESTful communication.

## 📂 Structure

```text
com.aryan
├── dto           # Data Transfer Objects (e.g., UserDTO)
├── embeddable    # JPA Embeddable classes (Address, GeoCode)
├── enums         # Shared Enums (UserRole)
├── payload       # API Request and Response structures
│   ├── request   # (e.g., AirportRequest, CityRequest)
│   └── response  # (e.g., AirportResponse, ApiResponse, AuthResponse)
└── service       # Shared service interfaces (e.g., AuthService)
```

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot 4.0.5**
- **Lombok** (for boilerplate reduction)
- **Jakarta Persistence** (for embeddable models)

## 📦 Usage

To use this library in other services, include it as a dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>com.aryan</groupId>
    <artifactId>common-lib</artifactId>
    <version>${project.version}</version>
</dependency>
```

---
*Part of the Airline Management System*
