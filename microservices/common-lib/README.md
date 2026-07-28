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

### Overview by Category

```
┌─────────────────────────────────────────────────────────────────┐
│                    COMMON LIBRARY COMPONENTS                     │
└─────────────────────────────────────────────────────────────────┘

📝 DTOs (1)              🔢 Enums (4)           📊 Embeddables (3)
   └─ UserDTO               ├─ AirlineStatus      ├─ Address
                            ├─ AircraftStatus     ├─ GeoCode
📥 Requests (8)             ├─ FlightStatus       └─ Support
   ├─ LoginRequest          └─ UserRole
   ├─ CityRequest                                🛠️  Utilities (1)
   ├─ AirportRequest        📤 Responses (10)        └─ MapperUtils
   ├─ AirlineRequest           ├─ ApiResponse
   ├─ AircraftRequest          ├─ AuthResponse
   ├─ FlightRequest            ├─ CityResponse
   ├─ FlightScheduleRequest    ├─ AirportResponse
   └─ FlightInstanceRequest    ├─ AirlineResponse
                               ├─ AirlineDropdownItem
                               ├─ AircraftResponse
                               ├─ FlightResponse
                               ├─ FlightScheduleResponse
                               └─ FlightInstanceResponse
```

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

#### FlightStatus.java

```java
public enum FlightStatus {
    SCHEDULED,   // Flight is scheduled
    BOARDING,    // Passengers boarding
    DEPARTED,    // Left departure gate
    IN_AIR,      // Currently flying
    LANDED,      // Touched down
    ARRIVED,     // At arrival gate
    DELAYED,     // Flight delayed
    CANCELLED,   // Flight cancelled
    DIVERTED,    // Diverted to another airport
    COMPLETED    // Flight completed
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

**Status Lifecycle**:

```
Flight: SCHEDULED → BOARDING → DEPARTED → IN_AIR → LANDED → ARRIVED → COMPLETED
                         ↓           ↓
                    CANCELLED    DELAYED → (resume) → IN_AIR
                                    ↓
                                DIVERTED → LANDED → ARRIVED
```

---

### 3. Request Payloads

Located in: `src/main/java/com/aryan/payload/request/`

These classes define the structure for incoming API requests with Jakarta Validation annotations:

| Request Class                | Purpose                       | Used By              |
| ---------------------------- | ----------------------------- | -------------------- |
| **LoginRequest**             | User login credentials        | User Service         |
| **CityRequest**              | City creation/update          | Location Service     |
| **AirportRequest**           | Airport creation/update       | Location Service     |
| **AirlineRequest**           | Airline registration          | Airline Core Service |
| **AircraftRequest**          | Aircraft registration         | Airline Core Service |
| **FlightRequest** ✨         | Flight creation/update        | Flight Ops Service   |
| **FlightScheduleRequest** ✨ | Flight schedule configuration | Flight Ops Service   |
| **FlightInstanceRequest** ✨ | Flight instance scheduling    | Flight Ops Service   |

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

**Example - FlightRequest.java** ✨ NEW:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {
    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotNull(message = "Aircraft ID is required")
    private Long aircraftId;

    @NotNull(message = "Departure airport ID is required")
    private Long departureAirportId;

    @NotNull(message = "Arrival airport ID is required")
    private Long arrivalAirportId;

    private FlightStatus status;
}
```

**Example - FlightScheduleRequest.java** ✨ NEW:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleRequest {
    @NotNull(message = "Flight Id is required")
    private Long flightId;

    private Long departureAirportId;
    private Long arrivalAirportId;

    @NotNull(message = "Departure Time is required")
    private LocalTime departureTime;

    @NotNull(message = "Arrival Time is required")
    private LocalTime arrivalTime;

    @NotNull(message = "Start Date is required")
    private LocalDate startDate;

    @NotNull(message = "End Date is required")
    private LocalDate endDate;

    private List<DayOfWeek> operatingDays;  // e.g., [MONDAY, WEDNESDAY, FRIDAY]
    private Boolean isActive;
}
```

**Key Features of FlightScheduleRequest**:

- ✅ Define recurring schedules with operating days
- ✅ Support for daily, weekday, weekend, or custom patterns
- ✅ Date range validation (start/end dates)
- ✅ Time-based scheduling (not datetime)
- ✅ Flexible operating days configuration

**Example - FlightInstanceRequest.java** ✨ NEW:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInstanceRequest {
    @NotNull(message = "Flight ID is required")
    private Long flightId;

    private Long airlineId;
    private Long scheduleId;
    private Long departureAirportId;
    private Long arrivalAirportId;

    @NotNull(message = "Departure DateTime is required")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival DateTime is required")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Total Seats is required")
    private Integer totalSeats;

    @PositiveOrZero
    private Integer availableSeats;

    private FlightStatus status;
    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;
    private Boolean isActive;
}
```

---

### 4. Response Payloads

Located in: `src/main/java/com/aryan/payload/response/`

These classes define the structure for outgoing API responses:

| Response Class                | Purpose                             | Used By              |
| ----------------------------- | ----------------------------------- | -------------------- |
| **ApiResponse**               | Generic success/error message       | All Services         |
| **AuthResponse**              | Authentication with JWT token       | User Service         |
| **CityResponse**              | City data                           | Location Service     |
| **AirportResponse**           | Airport data with city info         | Location Service     |
| **AirlineResponse**           | Airline data                        | Airline Core Service |
| **AirlineDropdownItem**       | Simplified airline for dropdowns    | Airline Core Service |
| **AircraftResponse**          | Aircraft data                       | Airline Core Service |
| **FlightResponse** ✨         | Flight template data                | Flight Ops Service   |
| **FlightScheduleResponse** ✨ | Flight schedule with operating days | Flight Ops Service   |
| **FlightInstanceResponse** ✨ | Flight instance with full details   | Flight Ops Service   |

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

**Example - FlightResponse.java** ✨ NEW:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponse {
    private Long id;
    private String flightNumber;
    private Long airlineId;
    private Long aircraftId;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private FlightStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
```

**Example - FlightScheduleResponse.java** ✨ NEW:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleResponse {
    private Long id;
    private Long flightId;
    private String flightNumber;

    private AirportResponse departureAirport;
    private AirportResponse arrivalAirport;

    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<DayOfWeek> operatingDays;  // [MONDAY, WEDNESDAY, FRIDAY]

    private Boolean isActive;
}
```

**Key Features of FlightScheduleResponse**:

- ✅ Enriched with complete airport details
- ✅ Operating days as array for easy processing
- ✅ Time-only fields (no date component)
- ✅ Date range for schedule validity
- ✅ Flight number for easy reference

**Example - FlightInstanceResponse.java** ✨ NEW (Enriched Response):

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightInstanceResponse {
    private Long id;
    private Long flightId;
    private String flightNumber;

    // Airline details
    private Long airlineId;
    private String airlineName;
    private String airlineLogo;

    // Aircraft details
    private Long aircraftId;
    private String aircraftModal;
    private String aircraftCode;

    // Airport details (complete objects)
    private AirportResponse departureAirport;
    private AirportResponse arrivalAirport;

    // Schedule details
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String formattedDuration;  // e.g., "4h 15min"

    // Seat management
    private Integer totalSeats;
    private Integer availableSeats;

    // Status & booking rules
    private FlightStatus status;
    private boolean isActive;
    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;
}
```

**Key Features of FlightInstanceResponse**:

- ✅ Enriched with airline name and logo
- ✅ Complete aircraft information
- ✅ Full airport objects (not just IDs)
- ✅ Automatically calculated duration
- ✅ Real-time seat availability

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

### 6. Utilities ✨ NEW

Located in: `src/main/java/com/aryan/util/`

**MapperUtils.java**

```java
public final class MapperUtils {

    private MapperUtils() {
        // Prevent instantiation
    }

    /**
     * Update a field only if the value is not null
     * Useful for PATCH operations where only provided fields should be updated
     */
    public static <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
```

**Usage Example**:

```java
// In a service update method
public Airline updateAirline(Long id, AirlineRequest request) {
    Airline airline = airlineRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Airline not found"));

    // Update only non-null fields
    MapperUtils.updateIfNotNull(request.getName(), airline::setName);
    MapperUtils.updateIfNotNull(request.getCountry(), airline::setCountry);
    MapperUtils.updateIfNotNull(request.getDescription(), airline::setDescription);

    return airlineRepository.save(airline);
}
```

**Purpose**: Provide reusable utility methods for common operations like partial updates.

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
│           │
│           ├── dto/
│           │   └── UserDTO.java              # User data transfer
│           │
│           ├── enums/
│           │   ├── AircraftStatus.java       # Aircraft statuses
│           │   ├── AirlineStatus.java        # Airline statuses
│           │   ├── FlightStatus.java         # ✨ Flight statuses
│           │   └── UserRole.java             # User roles
│           │
│           ├── embeddable/
│           │   ├── Address.java              # Address embedded entity
│           │   ├── GeoCode.java              # GPS coordinates
│           │   └── Support.java              # Support contact info
│           │
│           ├── util/                         # ✨ NEW
│           │   └── MapperUtils.java          # Utility methods
│           │
│           └── payload/
│               ├── request/
│               │   ├── AircraftRequest.java  # Aircraft creation
│               │   ├── AirlineRequest.java   # Airline creation
│               │   ├── AirportRequest.java   # Airport creation
│               │   ├── CityRequest.java      # City creation
│               │   ├── FlightRequest.java    # ✨ Flight creation
│               │   ├── FlightInstanceRequest.java  # ✨ Flight instance
│               │   └── LoginRequest.java     # Login credentials
│               │
│               └── response/
│                   ├── AircraftResponse.java  # Aircraft data
│                   ├── AirlineResponse.java   # Airline data
│                   ├── AirlineDropdownItem.java
│                   ├── AirportResponse.java   # Airport data
│                   ├── ApiResponse.java       # Generic response
│                   ├── AuthResponse.java      # Auth with JWT
│                   ├── CityResponse.java      # City data
│                   ├── FlightResponse.java    # ✨ Flight data
│                   └── FlightInstanceResponse.java  # ✨ Flight instance
│
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

### Component Count

```
┌──────────────────────┬───────┬────────────────────────────────┐
│ Category             │ Count │ Description                    │
├──────────────────────┼───────┼────────────────────────────────┤
│ DTOs                 │   1   │ User data transfer             │
│ Enums                │   4   │ Status codes & roles           │
│ Embeddables          │   3   │ Reusable embedded entities     │
│ Request Payloads     │   8   │ API request structures         │
│ Response Payloads    │  10   │ API response structures        │
│ Utilities            │   1   │ Helper methods                 │
├──────────────────────┼───────┼────────────────────────────────┤
│ TOTAL                │  27   │ Shared components              │
└──────────────────────┴───────┴────────────────────────────────┘
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

```
┌─────────────────────────────────────────────────────────────┐
│           COMMON LIBRARY UPDATE WORKFLOW                     │
└─────────────────────────────────────────────────────────────┘

1️⃣  Make Changes
    └─ Edit/Add classes in common-lib

2️⃣  Build Library
    └─ cd microservices/common-lib
    └─ mvn clean install

3️⃣  Update Services
    └─ Services automatically pick up changes
    └─ Or update version in pom.xml if needed

4️⃣  Rebuild Services
    └─ cd ../services/{service-name}
    └─ mvn clean install

5️⃣  Test
    └─ Run integration tests
    └─ Verify API endpoints

6️⃣  Deploy
    └─ Restart all services
```

### Migration Guide for Flight Operations

If updating to use FlightStatus and Flight-related components:

**Step 1: Rebuild common-lib**

```bash
cd microservices/common-lib
mvn clean install
```

**Step 2: Import new components in your service**

```java
import com.aryan.enums.FlightStatus;
import com.aryan.payload.request.FlightRequest;
import com.aryan.payload.request.FlightInstanceRequest;
import com.aryan.payload.response.FlightResponse;
import com.aryan.payload.response.FlightInstanceResponse;
import com.aryan.util.MapperUtils;
```

**Step 3: Use FlightStatus in entities**

```java
@Entity
public class Flight {
    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.SCHEDULED;
}
```

**Step 4: Rebuild your service**

```bash

 cd ../services/user-service
   mvn clean install

   cd ../location-service
   mvn clean install

   cd ../airline-core-service
   mvn clean install

    cd ../services/flight-ops-service
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

This library is a dependency for all microservices:

```
┌─────────────────────────────────────────────────────────────┐
│               COMMON LIBRARY DEPENDENCIES                    │
└─────────────────────────────────────────────────────────────┘

    Common Library (v0.0.1-SNAPSHOT)
            │
            ├──────────────┬──────────────┬──────────────┐
            │              │              │              │
            ▼              ▼              ▼              ▼
    ┌──────────────┐ ┌──────────┐ ┌──────────┐ ┌──────────────┐
    │ User Service │ │ Location │ │ Airline  │ │  Flight Ops  │
    │              │ │ Service  │ │   Core   │ │   Service    │
    │  Port: 5001  │ │Port: 5004│ │Port: 5005│ │  Port: 5006  │
    └──────────────┘ └──────────┘ └──────────┘ └──────────────┘
```

### Service-Specific Usage

| Service                   | Components Used                                                                                   |
| ------------------------- | ------------------------------------------------------------------------------------------------- |
| **User Service**          | UserDTO, UserRole, LoginRequest, AuthResponse, ApiResponse                                        |
| **Location Service**      | CityRequest, AirportRequest, CityResponse, AirportResponse                                        |
| **Airline Core Service**  | AirlineRequest, AircraftRequest, AirlineResponse, AircraftResponse, AirlineStatus, AircraftStatus |
| **Flight Ops Service** ✨ | FlightRequest, FlightInstanceRequest, FlightResponse, FlightInstanceResponse, FlightStatus        |

**Links**:

- ✅ [User Service](../services/user-service/README.md)
- ✅ [Location Service](../services/location-service/README.md)
- ✅ [Airline Core Service](../services/airline-core-service/README.md)
- ✅ [Flight Ops Service](../services/flight-ops-service/README.md)
- ✅ Future microservices

---

## 📞 Support

For questions about the common library:

- Check the main [README](../../README.md)
- Open an [issue](https://github.com/your-username/airline-management/issues)

---

## � Quick Reference

### Component Finder

Need to find a specific component? Use this quick reference:

```
┌─────────────────────────────────────────────────────────────┐
│                    QUICK COMPONENT FINDER                    │
└─────────────────────────────────────────────────────────────┘

Looking for...          →  Use this component
─────────────────────────────────────────────────────────────
🔐 Login payload        →  LoginRequest
👤 User data           →  UserDTO, UserRole
🏢 Airline data        →  AirlineRequest/Response, AirlineStatus
✈️  Aircraft data       →  AircraftRequest/Response, AircraftStatus
🌍 Location data       →  CityRequest/Response, AirportRequest/Response
🛩️  Flight template     →  FlightRequest/Response
📅 Flight schedules    →  FlightScheduleRequest/Response
🎫 Flight instance     →  FlightInstanceRequest/Response
📊 Flight statuses     →  FlightStatus enum
🔧 Partial updates     →  MapperUtils.updateIfNotNull()
📍 Address info        →  Address embeddable
🗺️  GPS coordinates    →  GeoCode embeddable
📞 Contact info        →  Support embeddable
✅ Success/Error msg   →  ApiResponse
🔑 JWT token response  →  AuthResponse
```

### Common Patterns

#### Pattern 1: Controller with Request/Response

```java
@PostMapping("/api/flights")
public ResponseEntity<FlightResponse> create(
    @Valid @RequestBody FlightRequest request
) {
    FlightResponse response = flightService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

#### Pattern 2: Using Enums

```java
@Entity
public class Flight {
    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.SCHEDULED;

    public void cancel() {
        this.status = FlightStatus.CANCELLED;
    }
}
```

#### Pattern 3: Partial Updates with MapperUtils

```java
public Flight update(Long id, FlightRequest request) {
    Flight flight = repository.findById(id).orElseThrow();

    MapperUtils.updateIfNotNull(request.getFlightNumber(), flight::setFlightNumber);
    MapperUtils.updateIfNotNull(request.getStatus(), flight::setStatus);

    return repository.save(flight);
}
```

#### Pattern 4: Using Embeddables

```java
@Entity
public class Airport {
    @Embedded
    private Address address;

    @Embedded
    private GeoCode geoCode;
}
```

---

## �📄 License

Part of the Airline Management System - MIT License

---

<div align="center">

**Common Library** | Powering All Microservices

**27 Shared Components** • **4 Services** • **1 Source of Truth**

[⬆ Back to Top](#-common-library)

</div>
