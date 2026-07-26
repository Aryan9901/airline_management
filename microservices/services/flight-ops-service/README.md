# ✈️ Flight Operations Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)](https://www.mysql.com/)

> Manages flight operations including flight registration, scheduling, flight instances, and lifecycle management. This service handles the core operational aspects of flight management within the airline system including specific flight instances with seat management and booking windows.

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Database Schema](#-database-schema)
- [API Endpoints](#-api-endpoints)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Usage Examples](#-usage-examples)

---

## 🌟 Overview

The **Flight Operations Service** is responsible for managing all flight-related operations in the airline management system. It handles flight registration, route management, aircraft assignment, flight status tracking, and flight instance management with specific departure times, seat availability, and booking rules.

**Port**: `5006`  
**Database**: `airline_flight_db`  
**Base URL**: `http://localhost:5006`

---

## ✨ Features

### 🛫 Flight Management

- **Flight Registration**: Register new flights with airline and aircraft assignment
- **Route Management**: Define departure and arrival airports
- **Status Tracking**: Monitor flight status (Scheduled, Active, Completed, Cancelled, Delayed)
- **CRUD Operations**: Full create, read, update, delete functionality
- **Airline Authorization**: Flights are tied to specific airlines with access control

### 🎫 Flight Instance Management

- **Instance Creation**: Create specific flight instances with exact departure/arrival times
- **Seat Management**: Track total seats and available seats for each instance
- **Booking Window**: Configure minimum and maximum advance booking days
- **Automated Duration**: Automatic calculation of flight duration
- **Enriched Data**: Returns complete flight, airline, aircraft, and airport details
- **Advanced Filtering**: Search by date, route, flight, and airline

### 🔍 Search & Filter

- **Search by Airline**: Retrieve all flights/instances for a specific airline
- **Filter by Route**: Search flights/instances by departure and/or arrival airport
- **Date-based Search**: Filter flight instances by specific date
- **Flight-based Filter**: Get all instances of a particular flight
- **Pagination Support**: Efficient data retrieval with pagination
- **Flight Lookup**: Get flight/instance details by ID or flight number

### 🔒 Security

- **Airline Authorization**: Header-based airline identification (`X-Airline-Id`)
- **Access Control**: Airlines can only manage their own flights
- **Data Validation**: Input validation using Jakarta Validation

---

## 🗄️ Database Schema

### Flight Entity

| Column                 | Type         | Constraints                 | Description                 |
| ---------------------- | ------------ | --------------------------- | --------------------------- |
| `id`                   | BIGINT       | PRIMARY KEY, AUTO_INCREMENT | Unique flight identifier    |
| `flight_number`        | VARCHAR(255) | NOT NULL, UNIQUE            | Flight number (e.g., AA123) |
| `airline_id`           | BIGINT       | NOT NULL                    | Reference to airline        |
| `aircraft_id`          | BIGINT       | NOT NULL                    | Reference to aircraft       |
| `departure_airport_id` | BIGINT       | NOT NULL                    | Departure airport reference |
| `arrival_airport_id`   | BIGINT       | NOT NULL                    | Arrival airport reference   |
| `status`               | VARCHAR(50)  | ENUM                        | Flight status               |
| `created_at`           | TIMESTAMP    | NOT NULL                    | Creation timestamp          |
| `updated_at`           | TIMESTAMP    | NOT NULL                    | Last update timestamp       |

### FlightInstance Entity

| Column                     | Type        | Constraints                 | Description                       |
| -------------------------- | ----------- | --------------------------- | --------------------------------- |
| `id`                       | BIGINT      | PRIMARY KEY, AUTO_INCREMENT | Unique flight instance identifier |
| `airline_id`               | BIGINT      | NOT NULL                    | Reference to airline              |
| `flight_id`                | BIGINT      | NOT NULL, FOREIGN KEY       | Reference to flight template      |
| `departure_airport_id`     | BIGINT      | NOT NULL                    | Departure airport reference       |
| `arrival_airport_id`       | BIGINT      |                             | Arrival airport reference         |
| `schedule_id`              | BIGINT      | NOT NULL                    | Reference to flight schedule      |
| `departure_date_time`      | TIMESTAMP   | NOT NULL                    | Actual departure date and time    |
| `arrival_date_time`        | TIMESTAMP   | NOT NULL                    | Actual arrival date and time      |
| `total_seats`              | INT         | NOT NULL                    | Total seats available             |
| `available_seats`          | INT         | NOT NULL                    | Currently available seats         |
| `status`                   | VARCHAR(50) | ENUM, NOT NULL              | Flight instance status            |
| `min_advance_booking_days` | INT         |                             | Minimum days before departure     |
| `max_advance_booking_days` | INT         |                             | Maximum days before departure     |
| `is_active`                | BOOLEAN     | DEFAULT TRUE                | Active status flag                |

### Flight Status Enum

- `SCHEDULED`: Flight is scheduled
- `ACTIVE`: Flight is currently in progress
- `COMPLETED`: Flight has been completed
- `CANCELLED`: Flight has been cancelled
- `DELAYED`: Flight is delayed

---

## 🚀 API Endpoints

### Flights API: `/api/flights`

| Method | Endpoint   | Description            | Auth Required |
| ------ | ---------- | ---------------------- | ------------- |
| POST   | `/`        | Create a new flight    | ✅ Airline ID |
| GET    | `/{id}`    | Get flight by ID       | ❌            |
| GET    | `/airline` | Get flights by airline | ✅ Airline ID |
| PUT    | `/{id}`    | Update flight details  | ❌            |
| DELETE | `/{id}`    | Delete a flight        | ✅ Airline ID |

### Flight Instances API: `/api/flight-instances`

| Method | Endpoint | Description                       | Auth Required |
| ------ | -------- | --------------------------------- | ------------- |
| POST   | `/`      | Create a new flight instance      | ✅ Airline ID |
| GET    | `/{id}`  | Get flight instance by ID         | ❌            |
| GET    | `/`      | Get flight instances with filters | ✅ Airline ID |
| PUT    | `/{id}`  | Update flight instance details    | ❌            |
| DELETE | `/{id}`  | Delete a flight instance          | ❌            |

---

### 📝 Detailed API Documentation

## Flight API

#### 1. Create Flight

**Endpoint**: `POST /api/flights`

**Headers**:

```
Content-Type: application/json
X-Airline-Id: <airline_id>
```

**Request Body**:

```json
{
  "flightNumber": "AA123",
  "aircraftId": 1,
  "departureAirportId": 10,
  "arrivalAirportId": 20,
  "status": "SCHEDULED"
}
```

**Response**: `201 Created`

```json
{
  "id": 1,
  "flightNumber": "AA123",
  "airlineId": 5,
  "aircraftId": 1,
  "departureAirportId": 10,
  "arrivalAirportId": 20,
  "status": "SCHEDULED",
  "createdAt": "2026-07-24T10:00:00Z",
  "updatedAt": "2026-07-24T10:00:00Z"
}
```

---

#### 2. Get Flight by ID

**Endpoint**: `GET /api/flights/{id}`

**Response**: `200 OK`

```json
{
  "id": 1,
  "flightNumber": "AA123",
  "airlineId": 5,
  "aircraftId": 1,
  "departureAirportId": 10,
  "arrivalAirportId": 20,
  "status": "SCHEDULED",
  "createdAt": "2026-07-24T10:00:00Z",
  "updatedAt": "2026-07-24T10:00:00Z"
}
```

---

#### 3. Get Flights by Airline

**Endpoint**: `GET /api/flights/airline`

**Headers**:

```
X-Airline-Id: <airline_id>
```

**Query Parameters**:

- `departureAirportId` (optional): Filter by departure airport
- `arrivalAirportId` (optional): Filter by arrival airport
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction (e.g., `flightNumber,asc`)

**Example Request**:

```
GET /api/flights/airline?departureAirportId=10&page=0&size=10
```

**Response**: `200 OK`

```json
{
  "content": [
    {
      "id": 1,
      "flightNumber": "AA123",
      "airlineId": 5,
      "aircraftId": 1,
      "departureAirportId": 10,
      "arrivalAirportId": 20,
      "status": "SCHEDULED",
      "createdAt": "2026-07-24T10:00:00Z",
      "updatedAt": "2026-07-24T10:00:00Z"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

#### 4. Update Flight

**Endpoint**: `PUT /api/flights/{id}`

**Request Body**:

```json
{
  "flightNumber": "AA124",
  "aircraftId": 2,
  "departureAirportId": 10,
  "arrivalAirportId": 20,
  "status": "DELAYED"
}
```

**Response**: `200 OK`

```json
{
  "id": 1,
  "flightNumber": "AA124",
  "airlineId": 5,
  "aircraftId": 2,
  "departureAirportId": 10,
  "arrivalAirportId": 20,
  "status": "DELAYED",
  "createdAt": "2026-07-24T10:00:00Z",
  "updatedAt": "2026-07-24T11:30:00Z"
}
```

---

#### 5. Delete Flight

**Endpoint**: `DELETE /api/flights/{id}`

**Headers**:

```
X-Airline-Id: <airline_id>
```

**Response**: `204 No Content`

---

## Flight Instance API

#### 1. Create Flight Instance

**Endpoint**: `POST /api/flight-instances`

**Headers**:

```
Content-Type: application/json
X-Airline-Id: <airline_id>
```

**Request Body**:

```json
{
  "flightId": 1,
  "scheduleId": 100,
  "departureAirportId": 10,
  "arrivalAirportId": 20,
  "departureDateTime": "2026-08-15T08:30:00",
  "arrivalDateTime": "2026-08-15T12:45:00",
  "totalSeats": 180,
  "availableSeats": 180,
  "status": "SCHEDULED",
  "minAdvanceBookingDays": 1,
  "maxAdvanceBookingDays": 90,
  "isActive": true
}
```

**Response**: `201 Created`

```json
{
  "id": 1,
  "flightId": 1,
  "flightNumber": "AA123",
  "airlineId": 5,
  "airlineName": "American Airlines",
  "airlineLogo": "https://example.com/logo.png",
  "aircraftId": 3,
  "aircraftModal": "Boeing 737",
  "aircraftCode": "B737",
  "departureAirport": {
    "id": 10,
    "name": "John F. Kennedy International Airport",
    "iataCode": "JFK",
    "city": "New York"
  },
  "arrivalAirport": {
    "id": 20,
    "name": "Los Angeles International Airport",
    "iataCode": "LAX",
    "city": "Los Angeles"
  },
  "departureDateTime": "2026-08-15T08:30:00",
  "arrivalDateTime": "2026-08-15T12:45:00",
  "formattedDuration": "4h 15min",
  "totalSeats": 180,
  "availableSeats": 180,
  "status": "SCHEDULED",
  "isActive": true,
  "minAdvanceBookingDays": 1,
  "maxAdvanceBookingDays": 90
}
```

---

#### 2. Get Flight Instance by ID

**Endpoint**: `GET /api/flight-instances/{id}`

**Response**: `200 OK`

```json
{
  "id": 1,
  "flightId": 1,
  "flightNumber": "AA123",
  "airlineId": 5,
  "airlineName": "American Airlines",
  "airlineLogo": "https://example.com/logo.png",
  "aircraftId": 3,
  "aircraftModal": "Boeing 737",
  "aircraftCode": "B737",
  "departureAirport": {
    "id": 10,
    "name": "John F. Kennedy International Airport",
    "iataCode": "JFK",
    "city": "New York"
  },
  "arrivalAirport": {
    "id": 20,
    "name": "Los Angeles International Airport",
    "iataCode": "LAX",
    "city": "Los Angeles"
  },
  "departureDateTime": "2026-08-15T08:30:00",
  "arrivalDateTime": "2026-08-15T12:45:00",
  "formattedDuration": "4h 15min",
  "totalSeats": 180,
  "availableSeats": 150,
  "status": "SCHEDULED",
  "isActive": true,
  "minAdvanceBookingDays": 1,
  "maxAdvanceBookingDays": 90
}
```

---

#### 3. Get Flight Instances with Filters

**Endpoint**: `GET /api/flight-instances`

**Headers**:

```
X-Airline-Id: <airline_id>
```

**Query Parameters**:

- `departureAirportId` (optional): Filter by departure airport
- `arrivalAirportId` (optional): Filter by arrival airport
- `flightId` (optional): Filter by specific flight
- `onDate` (optional): Filter by departure date (format: YYYY-MM-DD)
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction (e.g., `departureDateTime,asc`)

**Example Request**:

```
GET /api/flight-instances?flightId=1&onDate=2026-08-15&page=0&size=10
```

**Response**: `200 OK`

```json
{
  "content": [
    {
      "id": 1,
      "flightId": 1,
      "flightNumber": "AA123",
      "airlineId": 5,
      "airlineName": "American Airlines",
      "departureAirport": {
        "id": 10,
        "name": "John F. Kennedy International Airport",
        "iataCode": "JFK"
      },
      "arrivalAirport": {
        "id": 20,
        "name": "Los Angeles International Airport",
        "iataCode": "LAX"
      },
      "departureDateTime": "2026-08-15T08:30:00",
      "arrivalDateTime": "2026-08-15T12:45:00",
      "formattedDuration": "4h 15min",
      "totalSeats": 180,
      "availableSeats": 150,
      "status": "SCHEDULED",
      "isActive": true
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

#### 4. Update Flight Instance

**Endpoint**: `PUT /api/flight-instances/{id}`

**Request Body**:

```json
{
  "flightId": 1,
  "scheduleId": 100,
  "departureAirportId": 10,
  "arrivalAirportId": 20,
  "departureDateTime": "2026-08-15T09:00:00",
  "arrivalDateTime": "2026-08-15T13:15:00",
  "totalSeats": 180,
  "availableSeats": 145,
  "status": "DELAYED",
  "minAdvanceBookingDays": 1,
  "maxAdvanceBookingDays": 90,
  "isActive": true
}
```

**Response**: `200 OK`

```json
{
  "id": 1,
  "flightId": 1,
  "flightNumber": "AA123",
  "airlineId": 5,
  "airlineName": "American Airlines",
  "departureDateTime": "2026-08-15T09:00:00",
  "arrivalDateTime": "2026-08-15T13:15:00",
  "formattedDuration": "4h 15min",
  "totalSeats": 180,
  "availableSeats": 145,
  "status": "DELAYED",
  "isActive": true
}
```

---

#### 5. Delete Flight Instance

**Endpoint**: `DELETE /api/flight-instances/{id}`

**Response**: `200 OK`

```json
{
  "message": "Flight Instance deleted successfully."
}
```

---

## 🚦 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.9+**
- **MySQL 8.0+**
- **Running Airline Core Service** (for airline and aircraft references)
- **Running Location Service** (for airport references)

### Database Setup

Create the database:

```sql
CREATE DATABASE airline_flight_db;
```

### Configuration

Update `src/main/resources/application.yaml` with your MySQL credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/airline_flight_db
    username: your_mysql_username
    password: your_mysql_password
```

### Build and Run

#### Option 1: Run from root (Recommended)

```bash
cd microservices
mvn spring-boot:run -pl services/flight-ops-service
```

#### Option 2: Run individually

```bash
cd microservices/services/flight-ops-service
mvn clean install
mvn spring-boot:run
```

### Verify Service is Running

```bash
curl http://localhost:5005
```

---

## ⚙️ Configuration

### Application Properties

| Property                        | Default Value                                   | Description              |
| ------------------------------- | ----------------------------------------------- | ------------------------ |
| `server.port`                   | `5005`                                          | Service port             |
| `spring.application.name`       | `flight-ops-service`                            | Service name             |
| `spring.datasource.url`         | `jdbc:mysql://localhost:3306/airline_flight_db` | Database connection URL  |
| `spring.jpa.hibernate.ddl-auto` | `update`                                        | Hibernate DDL mode       |
| `spring.jpa.show-sql`           | `true`                                          | Show SQL queries in logs |

---

## 💡 Usage Examples

### Flight API Examples

#### Create a Flight

```bash
curl -X POST http://localhost:5006/api/flights \
  -H "Content-Type: application/json" \
  -H "X-Airline-Id: 5" \
  -d '{
    "flightNumber": "DL101",
    "aircraftId": 3,
    "departureAirportId": 1,
    "arrivalAirportId": 2,
    "status": "SCHEDULED"
  }'
```

#### Get Flight Details

```bash
curl http://localhost:5006/api/flights/1
```

#### Search Flights by Airline

```bash
curl -H "X-Airline-Id: 5" \
  "http://localhost:5006/api/flights/airline?page=0&size=10"
```

#### Filter Flights by Route

```bash
curl -H "X-Airline-Id: 5" \
  "http://localhost:5006/api/flights/airline?departureAirportId=1&arrivalAirportId=2"
```

#### Update Flight Status

```bash
curl -X PUT http://localhost:5006/api/flights/1 \
  -H "Content-Type: application/json" \
  -d '{
    "flightNumber": "DL101",
    "aircraftId": 3,
    "departureAirportId": 1,
    "arrivalAirportId": 2,
    "status": "ACTIVE"
  }'
```

#### Delete a Flight

```bash
curl -X DELETE http://localhost:5006/api/flights/1 \
  -H "X-Airline-Id: 5"
```

---

### Flight Instance API Examples

#### Create a Flight Instance

```bash
curl -X POST http://localhost:5006/api/flight-instances \
  -H "Content-Type: application/json" \
  -H "X-Airline-Id: 5" \
  -d '{
    "flightId": 1,
    "scheduleId": 100,
    "departureAirportId": 1,
    "arrivalAirportId": 2,
    "departureDateTime": "2026-08-15T08:30:00",
    "arrivalDateTime": "2026-08-15T12:45:00",
    "totalSeats": 180,
    "availableSeats": 180,
    "status": "SCHEDULED",
    "minAdvanceBookingDays": 1,
    "maxAdvanceBookingDays": 90,
    "isActive": true
  }'
```

#### Get Flight Instance Details

```bash
curl http://localhost:5006/api/flight-instances/1
```

#### Search Flight Instances by Airline

```bash
curl -H "X-Airline-Id: 5" \
  "http://localhost:5006/api/flight-instances?page=0&size=10"
```

#### Filter Flight Instances by Date

```bash
curl -H "X-Airline-Id: 5" \
  "http://localhost:5006/api/flight-instances?onDate=2026-08-15"
```

#### Filter Flight Instances by Flight and Route

```bash
curl -H "X-Airline-Id: 5" \
  "http://localhost:5006/api/flight-instances?flightId=1&departureAirportId=1&arrivalAirportId=2"
```

#### Update Flight Instance

```bash
curl -X PUT http://localhost:5006/api/flight-instances/1 \
  -H "Content-Type: application/json" \
  -d '{
    "flightId": 1,
    "scheduleId": 100,
    "departureAirportId": 1,
    "arrivalAirportId": 2,
    "departureDateTime": "2026-08-15T09:00:00",
    "arrivalDateTime": "2026-08-15T13:15:00",
    "totalSeats": 180,
    "availableSeats": 145,
    "status": "DELAYED",
    "minAdvanceBookingDays": 1,
    "maxAdvanceBookingDays": 90,
    "isActive": true
  }'
```

#### Delete a Flight Instance

```bash
curl -X DELETE http://localhost:5006/api/flight-instances/1
```

---

## 🏗️ Project Structure

```
flight-ops-service/
├── src/
│   ├── main/
│   │   ├── java/com/aryan/
│   │   │   ├── config/              # Configuration classes
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── FlightController.java
│   │   │   │   ├── FlightInstanceController.java
│   │   │   │   └── HomeController.java
│   │   │   ├── mapper/              # DTO Mappers
│   │   │   │   └── FlightInstanceMapper.java
│   │   │   ├── model/               # JPA Entities
│   │   │   │   ├── Flight.java
│   │   │   │   └── FlightInstance.java
│   │   │   ├── repository/          # Data Access Layer
│   │   │   │   ├── FlightRepository.java
│   │   │   │   └── FlightInstanceRepository.java
│   │   │   ├── service/             # Business Logic
│   │   │   │   ├── FlightService.java
│   │   │   │   ├── FlightInstanceService.java
│   │   │   │   └── impl/
│   │   │   └── FlightOpsServiceApplication.java
│   │   └── resources/
│   │       └── application.yaml     # Configuration
│   └── test/                        # Test classes
├── pom.xml
└── README.md
```

---

## 🔗 Dependencies

This service depends on:

- **Airline Core Service**: For airline and aircraft validation
- **Location Service**: For airport validation
- **Common Library**: Shared DTOs, enums, and utilities

---

## 🧪 Testing

Run tests:

```bash
mvn test
```

---

## 🐛 Troubleshooting

### Common Issues

**Issue**: Port 5005 already in use

```
Solution: Change the port in application.yaml or stop the conflicting service
```

**Issue**: Foreign key constraint errors

```
Solution: Ensure Airline Core Service and Location Service are running
         and referenced IDs (airlineId, aircraftId, airportIds) exist
```

**Issue**: Database connection refused

```
Solution: Verify MySQL is running and airline_flight_db exists
```

---

## 📄 License

This service is part of the Airline Management System and is distributed under the MIT License.

---

## 🔙 Back to Main Documentation

[📖 Return to Main README](../../../README.md)

---

<div align="center">

**Flight Operations Service** | Part of the Airline Management System

</div>
