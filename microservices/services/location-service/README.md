# 🌍 Location Service

> Geographical data management microservice for cities and airports in the Airline Management System

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
- [Usage Examples](#-usage-examples)
- [Configuration](#-configuration)

---

## 🌟 Overview

The **Location Service** manages all geographical data for the Airline Management System, including:

- **Cities**: Major cities where airports are located
- **Airports**: International and domestic airports with IATA codes
- **Geographical Relationships**: Linking airports to cities

This service provides the foundation for flight routing, destination management, and location-based searches.

---

## ✨ Features

### 🏙️ City Management

- **CRUD Operations**: Create, read, update, and delete cities
- **Pagination & Sorting**: Handle large datasets efficiently
- **Search Functionality**: Find cities by name (case-insensitive)
- **Country Filtering**: Filter cities by country code
- **Geocoding Support**: Store latitude/longitude coordinates

### ✈️ Airport Management

- **Complete Airport Data**: Name, IATA code, city association
- **CRUD Operations**: Full airport lifecycle management
- **City Linkage**: Associate airports with their parent cities
- **Airport Lookup**: Find all airports in a specific city

### 🔍 Advanced Search

- Keyword-based searching
- Country-based filtering
- Pagination support for all list endpoints
- Flexible sorting options

---

## 🛠️ Tech Stack

- **Java 21**: Modern Java LTS version
- **Spring Boot 4.0.5**: Application framework
- **Spring Data JPA**: Database abstraction
- **MySQL 8.0+**: Relational database
- **Lombok**: Reduce boilerplate code
- **Jakarta Validation**: Input validation
- **Maven**: Build and dependency management

---

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.9+
- MySQL 8.0+

### Database Setup

Create the database:

```sql
CREATE DATABASE airline_location_db;
```

Tables will be auto-created on first startup.

### Configuration

Update `src/main/resources/application.yaml`:

```yaml
server:
  port: 5004

spring:
  application:
    name: location-service
  datasource:
    url: jdbc:mysql://localhost:3306/airline_location_db
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

The service will start on **http://localhost:5004**

---

## 📋 API Endpoints

### Base URL

```
http://localhost:5004
```

---

## 🏙️ City Endpoints

### 1. Create a City

**Endpoint**: `POST /api/cities`

**Description**: Add a new city to the system

**Request Body**:

```json
{
  "name": "New York",
  "code": "NYC",
  "countryCode": "US",
  "timezone": "America/New_York",
  "geoCode": {
    "latitude": 40.7128,
    "longitude": -74.006
  }
}
```

**Request Fields**:
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `name` | String | ✅ | City name |
| `code` | String | ✅ | City code (unique) |
| `countryCode` | String | ✅ | ISO country code (e.g., US, UK) |
| `timezone` | String | ❌ | Timezone (e.g., America/New_York) |
| `geoCode` | Object | ❌ | Geographical coordinates |
| `geoCode.latitude` | Double | ❌ | Latitude |
| `geoCode.longitude` | Double | ❌ | Longitude |

**Response** (201 Created):

```json
{
  "id": 1,
  "name": "New York",
  "code": "NYC",
  "countryCode": "US",
  "timezone": "America/New_York",
  "geoCode": {
    "latitude": 40.7128,
    "longitude": -74.006
  }
}
```

**Example cURL**:

```bash
curl -X POST http://localhost:5004/api/cities \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New York",
    "code": "NYC",
    "countryCode": "US",
    "timezone": "America/New_York"
  }'
```

---

### 2. Get City by ID

**Endpoint**: `GET /api/cities/{id}`

**Description**: Retrieve detailed information about a specific city

**Path Parameters**:

- `id`: City identifier (Long)

**Response** (200 OK):

```json
{
  "id": 1,
  "name": "New York",
  "code": "NYC",
  "countryCode": "US",
  "timezone": "America/New_York",
  "geoCode": {
    "latitude": 40.7128,
    "longitude": -74.006
  }
}
```

**Example cURL**:

```bash
curl -X GET http://localhost:5004/api/cities/1
```

---

### 3. Get All Cities (Paginated)

**Endpoint**: `GET /api/cities`

**Description**: Retrieve all cities with pagination and sorting

**Query Parameters**:
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | Integer | 0 | Page number (0-indexed) |
| `size` | Integer | 20 | Items per page |
| `sortBy` | String | name | Sort field (name, code, countryCode) |
| `sortDirection` | String | asc | Sort direction (asc or desc) |

**Response** (200 OK):

```json
{
  "content": [
    {
      "id": 1,
      "name": "New York",
      "code": "NYC",
      "countryCode": "US"
    },
    {
      "id": 2,
      "name": "London",
      "code": "LON",
      "countryCode": "UK"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 50,
  "totalPages": 3
}
```

**Example cURL**:

```bash
# Get first page with 10 items, sorted by name
curl -X GET "http://localhost:5004/api/cities?page=0&size=10&sortBy=name&sortDirection=asc"
```

---

### 4. Update City

**Endpoint**: `PUT /api/cities/{id}`

**Description**: Update an existing city

**Path Parameters**:

- `id`: City identifier (Long)

**Request Body**:

```json
{
  "name": "New York City",
  "code": "NYC",
  "countryCode": "US",
  "timezone": "America/New_York"
}
```

**Response** (200 OK):

```json
{
  "id": 1,
  "name": "New York City",
  "code": "NYC",
  "countryCode": "US",
  "timezone": "America/New_York"
}
```

**Example cURL**:

```bash
curl -X PUT http://localhost:5004/api/cities/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New York City",
    "code": "NYC",
    "countryCode": "US"
  }'
```

---

### 5. Delete City

**Endpoint**: `DELETE /api/cities/{id}`

**Description**: Remove a city from the system

**Path Parameters**:

- `id`: City identifier (Long)

**Response** (200 OK):

```json
{
  "message": "City deleted successfully"
}
```

**Example cURL**:

```bash
curl -X DELETE http://localhost:5004/api/cities/1
```

---

### 6. Search Cities

**Endpoint**: `GET /api/cities/search`

**Description**: Search cities by keyword (case-insensitive)

**Query Parameters**:
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `keyword` | String | ✅ | Search term |
| `page` | Integer | ❌ | Page number (default: 0) |
| `size` | Integer | ❌ | Items per page (default: 20) |

**Response** (200 OK):

```json
{
  "content": [
    {
      "id": 1,
      "name": "New York",
      "code": "NYC",
      "countryCode": "US"
    }
  ],
  "totalElements": 1
}
```

**Example cURL**:

```bash
curl -X GET "http://localhost:5004/api/cities/search?keyword=york&page=0&size=10"
```

---

### 7. Get Cities by Country

**Endpoint**: `GET /api/cities/country/{countryCode}`

**Description**: Retrieve all cities in a specific country

**Path Parameters**:

- `countryCode`: ISO country code (String, e.g., US, UK, IN)

**Query Parameters**:
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | Integer | 0 | Page number |
| `size` | Integer | 20 | Items per page |

**Response** (200 OK):

```json
{
  "content": [
    {
      "id": 1,
      "name": "New York",
      "code": "NYC",
      "countryCode": "US"
    },
    {
      "id": 2,
      "name": "Los Angeles",
      "code": "LAX",
      "countryCode": "US"
    }
  ],
  "totalElements": 25
}
```

**Example cURL**:

```bash
curl -X GET "http://localhost:5004/api/cities/country/US?page=0&size=10"
```

---

## ✈️ Airport Endpoints

### 8. Create an Airport

**Endpoint**: `POST /api/airports`

**Description**: Add a new airport to the system

**Request Body**:

```json
{
  "name": "John F. Kennedy International Airport",
  "iataCode": "JFK",
  "cityId": 1,
  "address": {
    "street": "Queens",
    "city": "New York",
    "state": "NY",
    "zipCode": "11430",
    "country": "United States"
  },
  "geoCode": {
    "latitude": 40.6413,
    "longitude": -73.7781
  }
}
```

**Request Fields**:
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `name` | String | ✅ | Airport name |
| `iataCode` | String | ✅ | 3-letter IATA code (e.g., JFK) |
| `cityId` | Long | ✅ | Associated city ID |
| `address` | Object | ❌ | Airport address |
| `geoCode` | Object | ❌ | GPS coordinates |

**Response** (201 Created):

```json
{
  "id": 1,
  "name": "John F. Kennedy International Airport",
  "iataCode": "JFK",
  "cityId": 1,
  "cityName": "New York",
  "address": {
    "street": "Queens",
    "city": "New York",
    "state": "NY",
    "zipCode": "11430",
    "country": "United States"
  },
  "geoCode": {
    "latitude": 40.6413,
    "longitude": -73.7781
  }
}
```

**Example cURL**:

```bash
curl -X POST http://localhost:5004/api/airports \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John F. Kennedy International Airport",
    "iataCode": "JFK",
    "cityId": 1
  }'
```

---

### 9. Get Airport by ID

**Endpoint**: `GET /api/airports/{id}`

**Description**: Retrieve detailed information about a specific airport

**Path Parameters**:

- `id`: Airport identifier (Long)

**Response** (200 OK):

```json
{
  "id": 1,
  "name": "John F. Kennedy International Airport",
  "iataCode": "JFK",
  "cityId": 1,
  "cityName": "New York"
}
```

**Example cURL**:

```bash
curl -X GET http://localhost:5004/api/airports/1
```

---

### 10. Get All Airports

**Endpoint**: `GET /api/airports`

**Description**: Retrieve all airports in the system

**Response** (200 OK):

```json
[
  {
    "id": 1,
    "name": "John F. Kennedy International Airport",
    "iataCode": "JFK",
    "cityId": 1,
    "cityName": "New York"
  },
  {
    "id": 2,
    "name": "Heathrow Airport",
    "iataCode": "LHR",
    "cityId": 2,
    "cityName": "London"
  }
]
```

**Example cURL**:

```bash
curl -X GET http://localhost:5004/api/airports
```

---

### 11. Update Airport

**Endpoint**: `PUT /api/airports/{id}`

**Description**: Update an existing airport

**Path Parameters**:

- `id`: Airport identifier (Long)

**Request Body**:

```json
{
  "name": "JFK International Airport",
  "iataCode": "JFK",
  "cityId": 1
}
```

**Response** (200 OK):

```json
{
  "id": 1,
  "name": "JFK International Airport",
  "iataCode": "JFK",
  "cityId": 1
}
```

**Example cURL**:

```bash
curl -X PUT http://localhost:5004/api/airports/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "JFK International Airport",
    "iataCode": "JFK",
    "cityId": 1
  }'
```

---

### 12. Delete Airport

**Endpoint**: `DELETE /api/airports/{id}`

**Description**: Remove an airport from the system

**Path Parameters**:

- `id`: Airport identifier (Long)

**Response** (200 OK):

```json
{
  "message": "Airport deleted successfully"
}
```

**Example cURL**:

```bash
curl -X DELETE http://localhost:5004/api/airports/1
```

---

### 13. Get Airports by City

**Endpoint**: `GET /api/airports/city/{cityId}`

**Description**: Retrieve all airports in a specific city

**Path Parameters**:

- `cityId`: City identifier (Long)

**Response** (200 OK):

```json
[
  {
    "id": 1,
    "name": "John F. Kennedy International Airport",
    "iataCode": "JFK",
    "cityId": 1,
    "cityName": "New York"
  },
  {
    "id": 2,
    "name": "LaGuardia Airport",
    "iataCode": "LGA",
    "cityId": 1,
    "cityName": "New York"
  }
]
```

**Example cURL**:

```bash
curl -X GET http://localhost:5004/api/airports/city/1
```

---

## 🗄️ Database Schema

### Cities Table

```sql
CREATE TABLE cities (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    country_code VARCHAR(5) NOT NULL,
    timezone VARCHAR(50),
    latitude DOUBLE,
    longitude DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_country_code (country_code),
    INDEX idx_name (name)
);
```

### Airports Table

```sql
CREATE TABLE airports (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    iata_code VARCHAR(3) UNIQUE NOT NULL,
    city_id BIGINT NOT NULL,
    address_street VARCHAR(200),
    address_city VARCHAR(100),
    address_state VARCHAR(50),
    address_zip_code VARCHAR(20),
    address_country VARCHAR(100),
    latitude DOUBLE,
    longitude DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE,
    INDEX idx_city_id (city_id),
    INDEX idx_iata_code (iata_code)
);
```

---

## 💡 Usage Examples

### Scenario 1: Add a Complete Location (City + Airport)

```bash
# Step 1: Create a city
curl -X POST http://localhost:5004/api/cities \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dubai",
    "code": "DXB",
    "countryCode": "AE",
    "timezone": "Asia/Dubai"
  }'

# Response: {"id": 1, "name": "Dubai", ...}

# Step 2: Create an airport in that city
curl -X POST http://localhost:5004/api/airports \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Dubai International Airport",
    "iataCode": "DXB",
    "cityId": 1
  }'
```

### Scenario 2: Find All US Airports

```bash
# Step 1: Get all US cities
curl -X GET "http://localhost:5004/api/cities/country/US?page=0&size=100"

# Step 2: For each city, get its airports
curl -X GET "http://localhost:5004/api/airports/city/1"
curl -X GET "http://localhost:5004/api/airports/city/2"
# ... and so on
```

### Scenario 3: Search for a City

```bash
# Search for cities containing "Los"
curl -X GET "http://localhost:5004/api/cities/search?keyword=Los&page=0&size=10"

# Response includes: Los Angeles, Los Cabos, etc.
```

---

## ⚙️ Configuration

### Application Properties

| Property                        | Default                                         | Description                              |
| ------------------------------- | ----------------------------------------------- | ---------------------------------------- |
| `server.port`                   | 5004                                            | Service port                             |
| `spring.datasource.url`         | jdbc:mysql://localhost:3306/airline_location_db | Database connection URL                  |
| `spring.jpa.hibernate.ddl-auto` | update                                          | Schema generation (create, update, none) |
| `spring.jpa.show-sql`           | true                                            | Log SQL queries                          |

### Environment Variables

```bash
export SERVER_PORT=5004
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/airline_location_db
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=yourpassword
```

---

## 🧪 Testing

### Run Unit Tests

```bash
mvn test
```

### Integration Testing

```bash
# Test city creation
curl -X POST http://localhost:5004/api/cities \
  -H "Content-Type: application/json" \
  -d '{"name":"Test City","code":"TST","countryCode":"US"}'

# Verify it was created
curl -X GET "http://localhost:5004/api/cities/search?keyword=Test"

# Clean up
curl -X DELETE http://localhost:5004/api/cities/{id}
```

---

## 📁 Project Structure

```
location-service/
├── src/
│   ├── main/
│   │   ├── java/com/aryan/
│   │   │   ├── LocationServiceApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── CityController.java        # City APIs
│   │   │   │   ├── AirportController.java     # Airport APIs
│   │   │   │   └── HomeController.java        # Health check
│   │   │   ├── service/
│   │   │   │   ├── CityService.java           # City business logic
│   │   │   │   └── AirportService.java        # Airport business logic
│   │   │   ├── repository/
│   │   │   │   ├── CityRepository.java        # City data access
│   │   │   │   └── AirportRepository.java     # Airport data access
│   │   │   ├── model/
│   │   │   │   ├── City.java                  # City entity
│   │   │   │   └── Airport.java               # Airport entity
│   │   │   └── mapper/
│   │   │       ├── CityMapper.java            # City DTO mapping
│   │   │       └── AirportMapper.java         # Airport DTO mapping
│   │   └── resources/
│   │       └── application.yaml               # Configuration
│   └── test/
└── pom.xml
```

---

## 🔧 Troubleshooting

### Common Issues

**Issue**: Port 5004 already in use

```
Solution: Change port in application.yaml or kill the process
netstat -ano | findstr :5004
```

**Issue**: Cannot delete city (foreign key constraint)

```
Solution: Delete all airports in the city first, then delete the city
```

**Issue**: Duplicate IATA code

```
Solution: IATA codes must be unique. Use a different code or update existing airport
```

---

## 🔗 Related Services

- [User Service](../user-service/README.md) - User authentication and management
- [Airline Core Service](../airline-core-service/README.md) - Airline and aircraft management
- [Common Library](../../common-lib/README.md) - Shared models and utilities

---

## 📞 Support

For questions or issues:

- See main [README](../../../README.md)
- Open an [issue](https://github.com/your-username/airline-management/issues)

---

## 📄 License

Part of the Airline Management System - MIT License

---

<div align="center">

**Location Service** | Powering Global Airline Operations

[⬆ Back to Top](#-location-service)

</div>
