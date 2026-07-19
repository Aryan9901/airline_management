# ✈️ Airline Core Service

> Core business logic microservice for airline and aircraft management in the Airline Management System

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)](https://www.mysql.com/)

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#-database-schema)
- [Business Rules](#-business-rules)
- [Configuration](#-configuration)

---

## 🌟 Overview

The **Airline Core Service** is the heart of the Airline Management System, managing:

- **Airlines**: Registration, approval, and lifecycle management
- **Aircraft**: Fleet management and tracking
- **Ownership**: Linking airlines and aircraft to owners
- **Status Management**: Comprehensive status workflows

This service handles the core business entities and enforces critical business rules.

---

## ✨ Features

### 🏢 Airline Management
- **Airline Registration**: Create new airline companies
- **Admin Approval Workflow**: PENDING → ACTIVE → INACTIVE → BANNED
- **Status Management**: Approve, suspend, or ban airlines
- **Public Directory**: Paginated airline listings
- **Ownership Validation**: Ensure users can only manage their own airlines
- **Dropdown Lists**: Quick airline selection for forms

### ✈️ Aircraft Management
- **Fleet Registration**: Add aircraft to airline fleets
- **Aircraft Specifications**: Model, capacity, manufacturer details
- **Status Tracking**: Active, Maintenance, Retired
- **Owner-based Access**: Users can only manage their own aircraft
- **Complete CRUD**: Full lifecycle management

### 🔒 Security & Ownership
- Header-based authentication (`X-User-Id`)
- Owner validation on all operations
- Role-based operations (Admin vs Airline Owner)

---

## 🛠️ Tech Stack

- **Java 21**: Latest LTS version
- **Spring Boot 4.0.5**: Application framework
- **Spring Data JPA**: ORM and data access
- **MySQL 8.0+**: Relational database
- **Lombok**: Code simplification
- **Jakarta Validation**: Input validation
- **Maven**: Build tool

---

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.9+
- MySQL 8.0+
- User Service running (for authentication)

### Database Setup

Create the database:

```sql
CREATE DATABASE airline_core_db;
```

Tables will be auto-created on first run.

### Configuration

Update `src/main/resources/application.yaml`:

```yaml
server:
  port: 5005

spring:
  application:
    name: airline-core-service
  datasource:
    url: jdbc:mysql://localhost:3306/airline_core_db
    username: your_mysql_username      # Change this
    password: your_mysql_password      # Change this
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

The service will start on **http://localhost:5005**

---

## 📋 API Endpoints

### Base URL
```
http://localhost:5005
```

---

## 🏢 Airline Endpoints

### 1. Register a New Airline

**Endpoint**: `POST /api/airlines`

**Description**: Register a new airline (status defaults to PENDING)

**Headers**:
```
X-User-Id: 1
```

**Request Body**:
```json
{
  "name": "Sky Airlines",
  "code": "SKY",
  "country": "United States",
  "description": "Premier international airline",
  "website": "https://skyairlines.com",
  "contactEmail": "info@skyairlines.com",
  "contactPhone": "+1-800-SKY-LINE",
  "address": {
    "street": "123 Aviation Way",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "United States"
  }
}
```

**Request Fields**:
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `name` | String | ✅ | Airline name |
| `code` | String | ✅ | Airline code (2-3 chars, unique) |
| `country` | String | ✅ | Country of operation |
| `description` | String | ❌ | Airline description |
| `website` | String | ❌ | Official website URL |
| `contactEmail` | String | ❌ | Contact email |
| `contactPhone` | String | ❌ | Contact phone |
| `address` | Object | ❌ | Headquarters address |

**Response** (201 Created):
```json
{
  "id": 1,
  "name": "Sky Airlines",
  "code": "SKY",
  "country": "United States",
  "status": "PENDING",
  "ownerId": 1,
  "description": "Premier international airline",
  "website": "https://skyairlines.com",
  "contactEmail": "info@skyairlines.com"
}
```

**Example cURL**:
```bash
curl -X POST http://localhost:5005/api/airlines \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "name": "Sky Airlines",
    "code": "SKY",
    "country": "United States"
  }'
```

---

### 2. Get Airline by ID

**Endpoint**: `GET /api/airlines/{id}`

**Description**: Get detailed airline information

**Path Parameters**:
- `id`: Airline identifier (Long)

**Response** (200 OK):
```json
{
  "id": 1,
  "name": "Sky Airlines",
  "code": "SKY",
  "country": "United States",
  "status": "ACTIVE",
  "ownerId": 1,
  "website": "https://skyairlines.com"
}
```

**Example cURL**:
```bash
curl -X GET http://localhost:5005/api/airlines/1
```

---

### 3. Get My Airline (Owner)

**Endpoint**: `GET /api/airlines/admin`

**Description**: Get airline owned by the authenticated user

**Headers**:
```
X-User-Id: 1
```

**Response** (200 OK):
```json
{
  "id": 1,
  "name": "Sky Airlines",
  "code": "SKY",
  "country": "United States",
  "status": "ACTIVE",
  "ownerId": 1
}
```

**Example cURL**:
```bash
curl -X GET http://localhost:5005/api/airlines/admin \
  -H "X-User-Id: 1"
```

---

### 4. Get All Airlines (Public)

**Endpoint**: `GET /api/airlines`

**Description**: Get paginated list of all airlines

**Query Parameters**:
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | Integer | 0 | Page number |
| `size` | Integer | 20 | Items per page |
| `sort` | String | name | Sort field |

**Response** (200 OK):
```json
{
  "content": [
    {
      "id": 1,
      "name": "Sky Airlines",
      "code": "SKY",
      "country": "United States",
      "status": "ACTIVE"
    },
    {
      "id": 2,
      "name": "Ocean Air",
      "code": "OCN",
      "country": "Canada",
      "status": "ACTIVE"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 45,
  "totalPages": 3
}
```

**Example cURL**:
```bash
curl -X GET "http://localhost:5005/api/airlines?page=0&size=10"
```

---

### 5. Get Airlines Dropdown

**Endpoint**: `GET /api/airlines/dropdown`

**Description**: Get simplified airline list for dropdown menus (only ACTIVE airlines)

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "name": "Sky Airlines",
    "code": "SKY"
  },
  {
    "id": 2,
    "name": "Ocean Air",
    "code": "OCN"
  }
]
```

**Example cURL**:
```bash
curl -X GET http://localhost:5005/api/airlines/dropdown
```

---

### 6. Update Airline

**Endpoint**: `PUT /api/airlines`

**Description**: Update airline information (owners only)

**Headers**:
```
X-User-Id: 1
```

**Request Body**:
```json
{
  "name": "Sky Airlines International",
  "code": "SKY",
  "country": "United States",
  "description": "Updated description",
  "website": "https://skyairlines.com"
}
```

**Response** (200 OK):
```json
{
  "id": 1,
  "name": "Sky Airlines International",
  "code": "SKY",
  "country": "United States",
  "status": "ACTIVE"
}
```

**Example cURL**:
```bash
curl -X PUT http://localhost:5005/api/airlines \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "name": "Sky Airlines International",
    "code": "SKY",
    "country": "United States"
  }'
```

---

### 7. Delete Airline

**Endpoint**: `DELETE /api/airlines/{id}`

**Description**: Delete an airline (owners only)

**Headers**:
```
X-User-Id: 1
```

**Path Parameters**:
- `id`: Airline identifier (Long)

**Response** (204 No Content):
```json
{
  "message": "Airline Deleted Successfully"
}
```

**Example cURL**:
```bash
curl -X DELETE http://localhost:5005/api/airlines/1 \
  -H "X-User-Id: 1"
```

---

### 8. Approve Airline (Admin)

**Endpoint**: `POST /api/airlines/{id}/approve`

**Description**: Change airline status to ACTIVE (Admin only)

**Path Parameters**:
- `id`: Airline identifier (Long)

**Response** (200 OK):
```json
{
  "id": 1,
  "name": "Sky Airlines",
  "status": "ACTIVE"
}
```

**Example cURL**:
```bash
curl -X POST http://localhost:5005/api/airlines/1/approve
```

---

### 9. Suspend Airline (Admin)

**Endpoint**: `POST /api/airlines/{id}/suspend`

**Description**: Change airline status to INACTIVE (Admin only)

**Path Parameters**:
- `id`: Airline identifier (Long)

**Response** (200 OK):
```json
{
  "id": 1,
  "name": "Sky Airlines",
  "status": "INACTIVE"
}
```

**Example cURL**:
```bash
curl -X POST http://localhost:5005/api/airlines/1/suspend
```

---

### 10. Ban Airline (Admin)

**Endpoint**: `POST /api/airlines/{id}/ban`

**Description**: Change airline status to BANNED (Admin only)

**Path Parameters**:
- `id`: Airline identifier (Long)

**Response** (200 OK):
```json
{
  "id": 1,
  "name": "Sky Airlines",
  "status": "BANNED"
}
```

**Example cURL**:
```bash
curl -X POST http://localhost:5005/api/airlines/1/ban
```

---

## ✈️ Aircraft Endpoints

### 11. Register an Aircraft

**Endpoint**: `POST /api/aircrafts`

**Description**: Add a new aircraft to the fleet

**Headers**:
```
X-User-Id: 1
```

**Request Body**:
```json
{
  "registrationNumber": "N12345",
  "model": "Boeing 737-800",
  "manufacturer": "Boeing",
  "capacity": 189,
  "yearOfManufacture": 2020,
  "status": "ACTIVE",
  "airlineId": 1
}
```

**Request Fields**:
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `registrationNumber` | String | ✅ | Aircraft registration (unique) |
| `model` | String | ✅ | Aircraft model |
| `manufacturer` | String | ✅ | Manufacturer name |
| `capacity` | Integer | ✅ | Passenger capacity |
| `yearOfManufacture` | Integer | ❌ | Manufacturing year |
| `status` | String | ✅ | ACTIVE, MAINTENANCE, RETIRED |
| `airlineId` | Long | ✅ | Airline ID |

**Response** (201 Created):
```json
{
  "id": 1,
  "registrationNumber": "N12345",
  "model": "Boeing 737-800",
  "manufacturer": "Boeing",
  "capacity": 189,
  "yearOfManufacture": 2020,
  "status": "ACTIVE",
  "airlineId": 1,
  "ownerId": 1
}
```

**Example cURL**:
```bash
curl -X POST http://localhost:5005/api/aircrafts \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "registrationNumber": "N12345",
    "model": "Boeing 737-800",
    "manufacturer": "Boeing",
    "capacity": 189,
    "status": "ACTIVE",
    "airlineId": 1
  }'
```

---

### 12. Get Aircraft by ID

**Endpoint**: `GET /api/aircrafts/{id}`

**Description**: Get detailed aircraft information

**Path Parameters**:
- `id`: Aircraft identifier (Long)

**Response** (200 OK):
```json
{
  "id": 1,
  "registrationNumber": "N12345",
  "model": "Boeing 737-800",
  "manufacturer": "Boeing",
  "capacity": 189,
  "yearOfManufacture": 2020,
  "status": "ACTIVE",
  "airlineId": 1
}
```

**Example cURL**:
```bash
curl -X GET http://localhost:5005/api/aircrafts/1
```

---

### 13. Get My Aircraft (Owner)

**Endpoint**: `GET /api/aircrafts`

**Description**: Get all aircraft owned by the authenticated user

**Headers**:
```
X-User-Id: 1
```

**Response** (200 OK):
```json
[
  {
    "id": 1,
    "registrationNumber": "N12345",
    "model": "Boeing 737-800",
    "status": "ACTIVE"
  },
  {
    "id": 2,
    "registrationNumber": "N67890",
    "model": "Airbus A320",
    "status": "MAINTENANCE"
  }
]
```

**Example cURL**:
```bash
curl -X GET http://localhost:5005/api/aircrafts \
  -H "X-User-Id: 1"
```

---

### 14. Update Aircraft

**Endpoint**: `PUT /api/aircrafts/{id}`

**Description**: Update aircraft information (owners only)

**Headers**:
```
X-User-Id: 1
```

**Path Parameters**:
- `id`: Aircraft identifier (Long)

**Request Body**:
```json
{
  "registrationNumber": "N12345",
  "model": "Boeing 737-800 MAX",
  "manufacturer": "Boeing",
  "capacity": 189,
  "status": "MAINTENANCE"
}
```

**Response** (200 OK):
```json
{
  "id": 1,
  "registrationNumber": "N12345",
  "model": "Boeing 737-800 MAX",
  "status": "MAINTENANCE"
}
```

**Example cURL**:
```bash
curl -X PUT http://localhost:5005/api/aircrafts/1 \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "registrationNumber": "N12345",
    "model": "Boeing 737-800 MAX",
    "status": "MAINTENANCE"
  }'
```

---

### 15. Delete Aircraft

**Endpoint**: `DELETE /api/aircrafts/{id}`

**Description**: Remove aircraft from the fleet (owners only)

**Headers**:
```
X-User-Id: 1
```

**Path Parameters**:
- `id`: Aircraft identifier (Long)

**Response** (204 No Content):

**Example cURL**:
```bash
curl -X DELETE http://localhost:5005/api/aircrafts/1 \
  -H "X-User-Id: 1"
```

---

## 🗄️ Database Schema

### Airlines Table

```sql
CREATE TABLE airlines (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    country VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    owner_id BIGINT NOT NULL,
    description TEXT,
    website VARCHAR(255),
    contact_email VARCHAR(100),
    contact_phone VARCHAR(50),
    address_street VARCHAR(200),
    address_city VARCHAR(100),
    address_state VARCHAR(50),
    address_zip_code VARCHAR(20),
    address_country VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_owner_id (owner_id),
    INDEX idx_status (status),
    INDEX idx_code (code)
);
```

### Aircraft Table

```sql
CREATE TABLE aircraft (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    registration_number VARCHAR(50) UNIQUE NOT NULL,
    model VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    year_of_manufacture INT,
    status VARCHAR(20) NOT NULL,
    airline_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (airline_id) REFERENCES airlines(id) ON DELETE CASCADE,
    INDEX idx_airline_id (airline_id),
    INDEX idx_owner_id (owner_id),
    INDEX idx_status (status)
);
```

---

## 📊 Business Rules

### Airline Status Workflow

```
PENDING → ACTIVE → INACTIVE ⇄ BANNED
   ↓                ↓
[Waiting for     [Can be
  Admin          suspended
  Approval]      or banned]
```

**Status Definitions**:
- **PENDING**: Newly registered, awaiting admin approval
- **ACTIVE**: Approved and operational
- **INACTIVE**: Temporarily suspended
- **BANNED**: Permanently banned

### Aircraft Status

```
ACTIVE → MAINTENANCE → RETIRED
  ↑          ↓
  └──────────┘
```

**Status Definitions**:
- **ACTIVE**: In service and operational
- **MAINTENANCE**: Under maintenance, not available
- **RETIRED**: Permanently out of service

### Ownership Rules

✅ **Users can**:
- Register their own airline
- View any airline
- Update only their own airline
- Delete only their own airline
- Manage only their own aircraft

❌ **Users cannot**:
- Modify other users' airlines or aircraft
- Change airline status (admin only)

---

## 💡 Usage Examples

### Complete Airline Setup Flow

```bash
# Step 1: User registers (in User Service)
curl -X POST http://localhost:5001/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "owner@skyairlines.com",
    "password": "secure123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "AIRLINE"
  }'

# Response includes: {"user": {"id": 1}}

# Step 2: Register airline
curl -X POST http://localhost:5005/api/airlines \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "name": "Sky Airlines",
    "code": "SKY",
    "country": "United States"
  }'

# Response: {"id": 1, "status": "PENDING"}

# Step 3: Admin approves airline
curl -X POST http://localhost:5005/api/airlines/1/approve

# Response: {"id": 1, "status": "ACTIVE"}

# Step 4: Add aircraft to fleet
curl -X POST http://localhost:5005/api/aircrafts \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "registrationNumber": "N12345",
    "model": "Boeing 737-800",
    "manufacturer": "Boeing",
    "capacity": 189,
    "status": "ACTIVE",
    "airlineId": 1
  }'
```

---

## ⚙️ Configuration

### Application Properties

| Property | Default | Description |
|----------|---------|-------------|
| `server.port` | 5005 | Service port |
| `spring.datasource.url` | jdbc:mysql://localhost:3306/airline_core_db | Database URL |
| `spring.jpa.hibernate.ddl-auto` | update | Schema generation |
| `spring.jpa.show-sql` | true | Log SQL queries |

---

## 🧪 Testing

### Run Tests

```bash
mvn test
```

### Manual Testing

```bash
# Create airline
curl -X POST http://localhost:5005/api/airlines \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{"name":"Test Air","code":"TST","country":"US"}'

# Get all airlines
curl -X GET http://localhost:5005/api/airlines

# Get dropdown list
curl -X GET http://localhost:5005/api/airlines/dropdown
```

---

## 📁 Project Structure

```
airline-core-service/
├── src/
│   ├── main/
│   │   ├── java/com/aryan/
│   │   │   ├── AirlineCoreServiceApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── AirlineController.java     # Airline APIs
│   │   │   │   └── AircraftController.java    # Aircraft APIs
│   │   │   ├── service/
│   │   │   │   ├── AirlineService.java        # Airline logic
│   │   │   │   └── AircraftService.java       # Aircraft logic
│   │   │   ├── repository/
│   │   │   │   ├── AirlineRepository.java     # Airline data access
│   │   │   │   └── AircraftRepository.java    # Aircraft data access
│   │   │   ├── model/
│   │   │   │   ├── Airline.java               # Airline entity
│   │   │   │   └── Aircraft.java              # Aircraft entity
│   │   │   ├── config/
│   │   │   │   └── AppConfig.java             # App configuration
│   │   │   └── mapper/
│   │   │       ├── AirlineMapper.java         # Airline DTO mapping
│   │   │       └── AircraftMapper.java        # Aircraft DTO mapping
│   │   └── resources/
│   │       └── application.yaml               # Configuration
│   └── test/
└── pom.xml
```

---

## 🔧 Troubleshooting

### Common Issues

**Issue**: Unauthorized - user cannot create airline
```
Solution: Ensure X-User-Id header is provided with valid user ID
```

**Issue**: Cannot delete airline (foreign key constraint)
```
Solution: Delete all aircraft associated with the airline first
```

**Issue**: Status change fails
```
Solution: Only admins can change airline status (approve/suspend/ban)
```

**Issue**: Duplicate airline code
```
Solution: Airline codes must be unique. Choose a different code
```

---

## 🔗 Related Services

- [User Service](../user-service/README.md) - Authentication and user management
- [Location Service](../location-service/README.md) - Cities and airports
- [Common Library](../../common-lib/README.md) - Shared models

---

## 📞 Support

For questions:
- Main [README](../../../README.md)
- [GitHub Issues](https://github.com/your-username/airline-management/issues)

---

## 📄 License

Part of the Airline Management System - MIT License

---

<div align="center">

**Airline Core Service** | Managing Aviation Operations

[⬆ Back to Top](#-airline-core-service)

</div>
