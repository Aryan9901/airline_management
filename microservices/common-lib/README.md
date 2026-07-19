# 📦 Common Library

> Shared DTOs, enums, and utility classes used across all microservices in the Airline Management System

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue)](https://maven.apache.org/)

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Purpose](#-purpose)
- [Contents](#-contents)
- [Usage](#-usage)
- [Structure](#-structure)
- [Best Practices](#-best-practices)

---

## 🌟 Overview

The **Common Library** is a shared dependency module that provides reusable components across all microservices in the Airline Management System. It ensures:

- **Consistency**: Standardized data structures across services
- **DRY Principle**: Avoid code duplication
- **Type Safety**: Shared enums and DTOs
- **Maintainability**: Single source of truth for common models

---

## 🎯 Purpose

### Why Common Library?

In a microservices architecture, services often need to share:

- Data Transfer Objects (DTOs) for API communication
- Enumerations for status codes and roles
- Request/Response payloads
- Embedded entities

Without a common library, each service would duplicate these classes, leading to:

- ❌ Code duplication
- ❌ Inconsistencies between services
- ❌ Difficult refactoring
- ❌ Higher maintenance cost

With the common library:

- ✅ Single source of truth
- ✅ Easy updates across all services
- ✅ Consistent data structures
- ✅ Reduced development time

---

## 📦 Contents

### 1. DTOs (Data Transfer Objects)

Located in: `src/main/java/com/aryan/dto/`

**UserDTO.java**

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserRole role;
}
```

**Purpose**: Transfer user data between services without exposing entity internals.

---

### 2. Enums

Located in: `src/main/java/com/aryan/enums/`

#### AirlineStatus.java

```java
public enum AirlineStatus {
    PENDING,    // Awaiting approval
    ACTIVE,     // Operational
    INACTIVE,   // Temporarily suspended
    BANNED      // Permanently banned
}
```

#### AircraftStatus.java

```java
public enum AircraftStatus {
    ACTIVE,      // In service
    MAINTENANCE, // Under maintenance
    RETIRED      // Out of service
}
```

#### UserRole.java

```java
public enum UserRole {
    USER,    // Regular user/passenger
    AIRLINE, // Airline owner
    ADMIN    // System administrator
}
```

**Purpose**: Standardize status codes and roles across all services.

---

### 3. Request Payloads

Located in: `src/main/java/com/aryan/payload/request/`

These classes define the structure for incoming API requests:

- **LoginRequest.java**: User login credentials
- **CityRequest.java**: City creation/update data
- **AirportRequest.java**: Airport creation/update data
- **AirlineRequest.java**: Airline registration data
- **AircraftRequest.java**: Aircraft registration data

**Example - AirlineRequest.java**:

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirlineRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 3)
    private String code;

    @NotBlank(message = "Country is required")
    private String country;

    private String description;
    private String website;
    private Address address;
}
```

---

### 4. Response Payloads

Located in: `src/main/java/com/aryan/payload/response/`

These classes define the structure for outgoing API responses:

- **ApiResponse.java**: Generic success/error message wrapper
- **AuthResponse.java**: Authentication response with JWT token
- **CityResponse.java**: City data response
- **AirportResponse.java**: Airport data response
- **AirlineResponse.java**: Airline data response
- **AircraftResponse.java**: Aircraft data response
- **AirlineDropdownItem.java**: Simplified airline data for dropdowns

**Example - ApiResponse.java**:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
}
```

**Example - AuthResponse.java**:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String jwt;
    private String message;
    private UserDTO user;
}
```

---

### 5. Embeddable Classes

Located in: `src/main/java/com/aryan/embeddable/`

These are JPA embedded entities used within other entities:

**Address.java**

```java
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
```

**GeoCode.java**

```java
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeoCode {
    private Double latitude;
    private Double longitude;
}
```

**Support.java**

```java
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Support {
    private String email;
    private String phone;
    private String website;
}
```

**Purpose**: Reusable embedded objects that can be included in multiple entities.

---

## 🚀 Usage

### Adding Common Library as a Dependency

In any microservice's `pom.xml`:

```xml
<dependency>
    <groupId>com.aryan</groupId>
    <artifactId>common-lib</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Building the Common Library

Before using it in services, build the common library:

```bash
cd microservices/common-lib
mvn clean install
```

This installs the library into your local Maven repository.

### Example Usage in Services

#### Using DTOs

```java
import com.aryan.dto.UserDTO;

@Service
public class UserService {
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        return mapToDTO(user);
    }
}
```

#### Using Enums

```java
import com.aryan.enums.AirlineStatus;
import com.aryan.enums.UserRole;

@Entity
public class Airline {
    @Enumerated(EnumType.STRING)
    private AirlineStatus status = AirlineStatus.PENDING;
}

@Entity
public class User {
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
}
```

#### Using Request Payloads

```java
import com.aryan.payload.request.AirlineRequest;

@PostMapping("/api/airlines")
public ResponseEntity<?> createAirline(
    @Valid @RequestBody AirlineRequest request
) {
    // Process request
}
```

#### Using Response Payloads

```java
import com.aryan.payload.response.AirlineResponse;
import com.aryan.payload.response.ApiResponse;

@GetMapping("/api/airlines/{id}")
public ResponseEntity<AirlineResponse> getAirline(@PathVariable Long id) {
    AirlineResponse response = airlineService.getById(id);
    return ResponseEntity.ok(response);
}

@DeleteMapping("/api/airlines/{id}")
public ResponseEntity<ApiResponse> deleteAirline(@PathVariable Long id) {
    airlineService.delete(id);
    return ResponseEntity.ok(new ApiResponse("Deleted successfully"));
}
```

#### Using Embeddable Classes

```java
import com.aryan.embeddable.Address;
import com.aryan.embeddable.GeoCode;

@Entity
public class Airport {
    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;
}
```

---

## 📁 Structure

```
common-lib/
├── src/
│   └── main/
│       └── java/com/aryan/
│           ├── CommonLibApplication.java      # Main class (not used)
│           ├── dto/
│           │   └── UserDTO.java              # User data transfer
│           ├── enums/
│           │   ├── AircraftStatus.java       # Aircraft statuses
│           │   ├── AirlineStatus.java        # Airline statuses
│           │   └── UserRole.java             # User roles
│           ├── embeddable/
│           │   ├── Address.java              # Address embedded entity
│           │   ├── GeoCode.java              # GPS coordinates
│           │   └── Support.java              # Support contact info
│           └── payload/
│               ├── request/
│               │   ├── AircraftRequest.java  # Aircraft creation
│               │   ├── AirlineRequest.java   # Airline creation
│               │   ├── AirportRequest.java   # Airport creation
│               │   ├── CityRequest.java      # City creation
│               │   └── LoginRequest.java     # Login credentials
│               └── response/
│                   ├── AircraftResponse.java  # Aircraft data
│                   ├── AirlineResponse.java   # Airline data
│                   ├── AirlineDropdownItem.java
│                   ├── AirportResponse.java   # Airport data
│                   ├── ApiResponse.java       # Generic response
│                   ├── AuthResponse.java      # Auth with JWT
│                   └── CityResponse.java      # City data
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

---

## ✅ Best Practices

### When to Add to Common Library

✅ **DO add**:

- DTOs used by multiple services
- Enums shared across services
- Common request/response structures
- Reusable embeddable entities
- Utility classes used everywhere

❌ **DON'T add**:

- Service-specific business logic
- Service-specific entities
- Database configurations
- Service-specific utilities

### Versioning

When making changes to the common library:

1. **Update the version** in `pom.xml`

   ```xml
   <version>0.0.2-SNAPSHOT</version>
   ```

2. **Rebuild and install**

   ```bash
   mvn clean install
   ```

3. **Update services** to use the new version

   ```xml
   <dependency>
       <artifactId>common-lib</artifactId>
       <version>0.0.2-SNAPSHOT</version>
   </dependency>
   ```

4. **Rebuild all services** that depend on it

### Naming Conventions

- **DTOs**: End with `DTO` (e.g., `UserDTO`)
- **Requests**: End with `Request` (e.g., `LoginRequest`)
- **Responses**: End with `Response` (e.g., `AirlineResponse`)
- **Enums**: Use singular nouns (e.g., `UserRole`, not `UserRoles`)
- **Embeddables**: Use descriptive nouns (e.g., `Address`, `GeoCode`)

---

## 🔄 Updating the Common Library

### Step-by-Step Process

1. **Make changes** to the common library
2. **Build the library**:
   ```bash
   cd microservices/common-lib
   mvn clean install
   ```
3. **Rebuild dependent services**:

   ```bash
   cd ../services/user-service
   mvn clean install

   cd ../location-service
   mvn clean install

   cd ../airline-core-service
   mvn clean install
   ```

---

## 🧪 Testing

The common library typically doesn't need tests as it contains:

- POJOs (Plain Old Java Objects)
- DTOs without logic
- Enums

However, if you add utility methods, write unit tests:

```java
@Test
public void testAddressValidation() {
    Address address = new Address();
    address.setCity("New York");
    assertEquals("New York", address.getCity());
}
```

---

## 📚 Dependencies

The common library uses minimal dependencies:

```xml
<dependencies>
    <!-- Lombok for boilerplate reduction -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>

    <!-- Jakarta Validation for annotations -->
    <dependency>
        <groupId>jakarta.validation</groupId>
        <artifactId>jakarta.validation-api</artifactId>
    </dependency>

    <!-- JPA for @Embeddable annotation -->
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
    </dependency>
</dependencies>
```

---

## 🔗 Used By

This library is a dependency for:

- ✅ [User Service](../services/user-service/README.md)
- ✅ [Location Service](../services/location-service/README.md)
- ✅ [Airline Core Service](../services/airline-core-service/README.md)
- ✅ Future microservices

---

## 📞 Support

For questions about the common library:

- Check the main [README](../../README.md)
- Open an [issue](https://github.com/your-username/airline-management/issues)

---

## 📄 License

Part of the Airline Management System - MIT License

---

<div align="center">

**Common Library** | Powering All Microservices

[⬆ Back to Top](#-common-library)

</div>
