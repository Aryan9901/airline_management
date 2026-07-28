# ✈️ Airline Management System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.0-blue)](https://spring.io/projects/spring-cloud)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

> A modern, scalable, and production-ready airline management system built with microservices architecture. Manage airlines, aircraft, airports, cities, and users with enterprise-grade security and performance.

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Services](#-services)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Tech Stack](#-tech-stack)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🌟 Overview

The **Airline Management System** is a comprehensive solution for managing airline operations, including:

- **Airline registration and management**
- **Aircraft fleet tracking**
- **Location management (cities and airports)**
- **User authentication and authorization**
- **Multi-role support (Admin, Airline Owner, Users)**

Built with a **microservices architecture**, each service is independently deployable, scalable, and maintainable.

---

## ✨ Features

### 🏢 Airline Management

- Register and manage airline companies
- Airline status management (Active, Inactive, Pending, Banned)
- Admin approval workflows
- Public airline directory with pagination

### ✈️ Aircraft Management

- Register aircraft with detailed specifications
- Track aircraft status (Active, Maintenance, Retired)
- Link aircraft to airline owners
- Complete CRUD operations

### 🛫 Flight Operations

- Flight registration and management
- Flight instance scheduling with specific departure/arrival times
- Route planning (departure and arrival airports)
- Aircraft assignment to flights
- Flight status tracking (Scheduled, Active, Completed, Cancelled, Delayed)
- Seat management and availability tracking
- Booking window configuration (min/max advance booking days)
- Automated flight duration calculation
- Search and filter by airline, route, date, and flight

### 🌍 Location Management

- **City Management**: Add, update, and search cities worldwide
- **Airport Management**: Manage airports with IATA codes
- **Geographical Linking**: Connect airports to cities
- **Country-based Filtering**: Search by country codes
- **Advanced Search**: Keyword-based searching with pagination

### 👤 User Management

- Secure user registration and authentication
- JWT-based authorization
- Role-based access control (Admin, Airline, User)
- Profile management

### 🔒 Security

- Spring Security integration
- JWT token authentication
- Password encryption
- Protected endpoints with role validation

---

## 🏗️ Architecture

### System Architecture Diagram

```
                    ╔═══════════════════════════════════════════════╗
                    ║        🌐 API Gateway (Future)               ║
                    ║        🔍 Service Discovery (Eureka)         ║
                    ╚═══════════════════════════════════════════════╝
                                      │
            ┌─────────────────────────┼──────────────────────────┐
            │                         │                          │
            ▼                         ▼                          ▼
    ┏━━━━━━━━━━━━━┓          ┏━━━━━━━━━━━━━┓           ┏━━━━━━━━━━━━━┓
    ┃ 👤 User     ┃          ┃ 🌍 Location ┃           ┃ 🏢 Airline  ┃
    ┃  Service    ┃          ┃  Service    ┃           ┃ Core Service┃
    ┃             ┃          ┃             ┃           ┃             ┃
    ┃ Port: 5001  ┃          ┃ Port: 5004  ┃           ┃ Port: 5005  ┃
    ┗━━━━━━━━━━━━━┛          ┗━━━━━━━━━━━━━┛           ┗━━━━━━━━━━━━━┛
            │                         │                          │
            │   ┌─────────────────────┴──────────────┐          │
            │   │                                    │          │
            ▼   ▼                                    ▼          ▼
         ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓      ┏━━━━━━━━━━━━━━━━━┓
         ┃  ✈️  Flight Ops Service     ┃      ┃ 📚 Common Lib   ┃
         ┃                             ┃◄─────┃  (Shared DTOs)  ┃
         ┃  • Flights Management       ┃      ┃                 ┃
         ┃  • Flight Instances         ┃      ┗━━━━━━━━━━━━━━━━━┛
         ┃  • Scheduling               ┃
         ┃  Port: 5006                 ┃
         ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
                       │
                       ▼
    ╔═══════════════════════════════════════════════════════════╗
    ║              💾 MySQL Databases (4 DBs)                   ║
    ╠═══════════════════════════════════════════════════════════╣
    ║  📊 airline_user_db      │  🗺️  airline_location_db       ║
    ║  🏢 airline_core_db      │  ✈️  airline_flight_db         ║
    ╚═══════════════════════════════════════════════════════════╝
```

### Request Flow Example

```
  👨‍💻 Client
    │
    │ 1. POST /auth/signup
    ├──────────────────────────────────────────────────►  👤 User Service
    │                                                         │
    │ ◄─────────────────────────────────────────────────────┤ JWT Token
    │
    │ 2. POST /api/airlines (with JWT)
    ├──────────────────────────────────────────────────►  🏢 Airline Core Service
    │                                                         │
    │ ◄─────────────────────────────────────────────────────┤ Airline Created
    │
    │ 3. POST /api/flights (with X-Airline-Id)
    ├──────────────────────────────────────────────────►  ✈️ Flight Ops Service
    │                                                         │
    │                                                         │ Validates Aircraft
    │                                                         ├─────────► 🏢 Core Service
    │                                                         │
    │                                                         │ Validates Airports
    │                                                         ├─────────► 🌍 Location Service
    │                                                         │
    │ ◄─────────────────────────────────────────────────────┤ Flight Created
    │
    │ 4. POST /api/flight-instances (with X-Airline-Id)
    ├──────────────────────────────────────────────────►  ✈️ Flight Ops Service
    │                                                         │
    │ ◄─────────────────────────────────────────────────────┤ Instance Created
    │                                                           with Full Details
```

### Microservices Architecture Benefits

✅ **Independent Deployment**: Each service can be deployed separately  
✅ **Scalability**: Scale services based on demand  
✅ **Technology Flexibility**: Each service can use different tech stacks  
✅ **Fault Isolation**: Failure in one service doesn't affect others  
✅ **Team Autonomy**: Different teams can work on different services

### Data Flow: Creating a Flight Instance

```
┌─────────────────────────────────────────────────────────────────┐
│  Step 1: User Registration & Authentication                     │
└─────────────────────────────────────────────────────────────────┘
                         │
                    👤 Register User
                         │
                    🔐 Receive JWT Token
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  Step 2: Create Airline                                         │
└─────────────────────────────────────────────────────────────────┘
                         │
              🏢 POST /api/airlines
                         │
              Status: PENDING (awaits admin approval)
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  Step 3: Register Aircraft                                      │
└─────────────────────────────────────────────────────────────────┘
                         │
              ✈️ POST /api/aircrafts
                         │
              Aircraft linked to Airline
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  Step 4: Create Cities & Airports                               │
└─────────────────────────────────────────────────────────────────┘
                         │
              🌍 POST /api/cities
              🛫 POST /api/airports
                         │
              Airports linked to Cities
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  Step 5: Create Flight Template                                 │
└─────────────────────────────────────────────────────────────────┘
                         │
              ✈️ POST /api/flights
                         │
              Flight: AA123 (JFK → LAX)
              Aircraft: Boeing 737
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│  Step 6: Create Flight Instance (Scheduled Flight)              │
└─────────────────────────────────────────────────────────────────┘
                         │
         🎫 POST /api/flight-instances
                         │
         ┌────────────────┴────────────────┐
         │   Flight Instance Details:      │
         │   • Date: Aug 15, 2026          │
         │   • Departure: 08:30 AM         │
         │   • Arrival: 12:45 PM           │
         │   • Duration: 4h 15min          │
         │   • Total Seats: 180            │
         │   • Available: 180              │
         │   • Status: SCHEDULED           │
         └─────────────────────────────────┘
                         │
                         ▼
                  ✅ Ready for Booking!
```

---

## 🚀 Services

### Services Overview

| Service                  | Port | Database              | Description                                  | README                                                             |
| ------------------------ | ---- | --------------------- | -------------------------------------------- | ------------------------------------------------------------------ |
| **User Service**         | 5001 | `airline_user_db`     | Authentication, authorization, user profiles | [📖 View](./microservices/services/user-service/README.md)         |
| **Location Service**     | 5004 | `airline_location_db` | Cities and airports management               | [📖 View](./microservices/services/location-service/README.md)     |
| **Airline Core Service** | 5005 | `airline_core_db`     | Airlines and aircraft management             | [📖 View](./microservices/services/airline-core-service/README.md) |
| **Flight Ops Service**   | 5006 | `airline_flight_db`   | Flight operations, instances, and scheduling | [📖 View](./microservices/services/flight-ops-service/README.md)   |
| **Common Library**       | -    | -                     | Shared DTOs, enums, and utilities            | [📖 View](./microservices/common-lib/README.md)                    |

### Service Capabilities Matrix

```
┌──────────────────────────┬────────┬──────────┬─────────┬────────────┐
│ Feature                  │  User  │ Location │ Airline │   Flight   │
│                          │Service │ Service  │  Core   │    Ops     │
├──────────────────────────┼────────┼──────────┼─────────┼────────────┤
│ 🔐 Authentication        │   ✅   │    ❌    │   ❌    │     ❌     │
│ 👤 User Management       │   ✅   │    ❌    │   ❌    │     ❌     │
│ 🔑 JWT Authorization     │   ✅   │    ❌    │   ❌    │     ❌     │
├──────────────────────────┼────────┼──────────┼─────────┼────────────┤
│ 🌍 City Management       │   ❌   │    ✅    │   ❌    │     ❌     │
│ 🛫 Airport Management    │   ❌   │    ✅    │   ❌    │     ❌     │
│ 🗺️  Geo-based Search    │   ❌   │    ✅    │   ❌    │     ❌     │
├──────────────────────────┼────────┼──────────┼─────────┼────────────┤
│ 🏢 Airline Management    │   ❌   │    ❌    │   ✅    │     ❌     │
│ ✈️  Aircraft Fleet       │   ❌   │    ❌    │   ✅    │     ❌     │
│ 👑 Admin Approval        │   ❌   │    ❌    │   ✅    │     ❌     │
├──────────────────────────┼────────┼──────────┼─────────┼────────────┤
│ 🛩️  Flight Templates     │   ❌   │    ❌    │   ❌    │     ✅     │
│ 🎫 Flight Instances      │   ❌   │    ❌    │   ❌    │     ✅     │
│ 💺 Seat Management       │   ❌   │    ❌    │   ❌    │     ✅     │
│ 📅 Scheduling            │   ❌   │    ❌    │   ❌    │     ✅     │
│ ⏱️  Duration Calculation │   ❌   │    ❌    │   ❌    │     ✅     │
└──────────────────────────┴────────┴──────────┴─────────┴────────────┘
```

---

## 🚦 Getting Started

### Quick Start Guide

```
┌─────────────────────────────────────────────────────────────────┐
│              🚀 5-MINUTE SETUP GUIDE                            │
└─────────────────────────────────────────────────────────────────┘

Step 1: Prerequisites Check
    ✓ Java 21+       ✓ Maven 3.9+      ✓ MySQL 8.0+      ✓ Git

Step 2: Clone & Setup
    📥 git clone <repo-url>

Step 3: Database Setup
    💾 Create 4 MySQL databases

Step 4: Configuration
    ⚙️  Update application.yaml files

Step 5: Build & Run
    🔨 mvn clean install
    ▶️  Run all services

Step 6: Verify
    ✅ Test endpoints

    🎉 Ready to use!
```

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21** or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi))
- **MySQL 8.0+** ([Download](https://dev.mysql.com/downloads/mysql/))
- **Git** ([Download](https://git-scm.com/downloads))

### 📥 Installation

#### 1. Clone the Repository

```bash
git clone https://github.com/your-username/airline-management.git
cd airline-management
```

#### 2. Database Setup

Create four MySQL databases:

```sql
CREATE DATABASE airline_user_db;
CREATE DATABASE airline_location_db;
CREATE DATABASE airline_core_db;
CREATE DATABASE airline_flight_db;
```

**Database Overview:**

```
💾 airline_user_db       → User authentication & profiles
💾 airline_location_db   → Cities & airports
💾 airline_core_db       → Airlines & aircraft fleet
💾 airline_flight_db     → Flights & flight instances
```

#### 3. Configure Database Credentials

Update the `application.yaml` files in each service with your MySQL credentials:

**Location**: `microservices/services/{service-name}/src/main/resources/application.yaml`

```yaml
spring:
  datasource:
    username: your_mysql_username
    password: your_mysql_password
```

#### 4. Build the Project

```bash
cd microservices
mvn clean install
```

This will:

- Build the common library
- Build all microservices
- Run tests
- Create executable JAR files

### ▶️ Running the Services

You have two options to run the services:

#### Option 1: Run from the root (Recommended)

```bash
# From the microservices directory
cd microservices

# Run each service in separate terminals
mvn spring-boot:run -pl services/user-service
mvn spring-boot:run -pl services/location-service
mvn spring-boot:run -pl services/airline-core-service
mvn spring-boot:run -pl services/flight-ops-service
```

#### Option 2: Run individually

```bash
# User Service
cd microservices/services/user-service
mvn spring-boot:run

# Location Service
cd microservices/services/location-service
mvn spring-boot:run

# Airline Core Service
cd microservices/services/airline-core-service
mvn spring-boot:run

# Flight Ops Service
cd microservices/services/flight-ops-service
mvn spring-boot:run
```

### ✅ Verify Services are Running

Check if services are running:

```bash
# User Service
curl http://localhost:5001

# Location Service
curl http://localhost:5004

# Airline Core Service
curl http://localhost:5005

# Flight Ops Service
curl http://localhost:5006
```

---

## 🎯 User Journey & Workflows

### Airline Owner Journey

```
    ┌─────────────────────────────────────────────────────────────┐
    │                    AIRLINE OWNER WORKFLOW                    │
    └─────────────────────────────────────────────────────────────┘

    👤 Step 1: User Registration
    ┌────────────────────────────────────┐
    │  POST /auth/signup                 │
    │  Role: AIRLINE                     │
    │  ✅ Account Created                │
    └────────────────┬───────────────────┘
                     │
                     ▼
    🏢 Step 2: Register Airline
    ┌────────────────────────────────────┐
    │  POST /api/airlines                │
    │  Status: PENDING                   │
    │  ⏳ Awaiting Admin Approval        │
    └────────────────┬───────────────────┘
                     │
                     ▼
    👑 Step 3: Admin Approval
    ┌────────────────────────────────────┐
    │  PUT /api/airlines/{id}/approve    │
    │  Status: ACTIVE                    │
    │  ✅ Airline Activated              │
    └────────────────┬───────────────────┘
                     │
                     ▼
    ✈️  Step 4: Add Aircraft Fleet
    ┌────────────────────────────────────┐
    │  POST /api/aircrafts               │
    │  Model: Boeing 737                 │
    │  Capacity: 180 seats               │
    │  ✅ Aircraft Added                 │
    └────────────────┬───────────────────┘
                     │
                     ▼
    🛩️  Step 5: Create Flight Routes
    ┌────────────────────────────────────┐
    │  POST /api/flights                 │
    │  Route: JFK → LAX                  │
    │  Aircraft: Boeing 737              │
    │  ✅ Flight Template Created        │
    └────────────────┬───────────────────┘
                     │
                     ▼
    🎫 Step 6: Schedule Flight Instances
    ┌────────────────────────────────────┐
    │  POST /api/flight-instances        │
    │  Date: 2026-08-15                  │
    │  Time: 08:30 AM → 12:45 PM         │
    │  Available Seats: 180              │
    │  ✅ Flight Scheduled               │
    └────────────────┬───────────────────┘
                     │
                     ▼
    📊 Step 7: Monitor & Manage
    ┌────────────────────────────────────┐
    │  • Track bookings                  │
    │  • Update flight status            │
    │  • Manage seat availability        │
    │  • View analytics                  │
    └────────────────────────────────────┘
```

### Admin Journey

```
    ┌─────────────────────────────────────────────────────────────┐
    │                      ADMIN WORKFLOW                          │
    └─────────────────────────────────────────────────────────────┘

    🔐 Admin Login
         │
         ▼
    ┌─────────────────────────────────────────┐
    │  Review Pending Airlines                │
    │  GET /api/airlines?status=PENDING       │
    └─────────────────┬───────────────────────┘
                      │
         ┌────────────┴─────────────┐
         │                          │
         ▼                          ▼
    ✅ APPROVE                  ❌ REJECT
    │                          │
    │ PUT /api/airlines/       │ PUT /api/airlines/
    │     {id}/approve         │     {id}/reject
    │                          │
    └────────┬─────────────────┴───────────────┐
             │                                 │
             ▼                                 ▼
    Status: ACTIVE                    Status: BANNED
    Airlines can operate              Airlines cannot operate
```

### System Status Flow

```
    ┌──────────────────────────────────────────────────────────┐
    │              ENTITY STATUS TRANSITIONS                    │
    └──────────────────────────────────────────────────────────┘

    AIRLINE STATUS:
    PENDING ──approve──► ACTIVE ──reject──► BANNED
       ▲                   │                  │
       └───────reactivate──┴──────────────────┘

    AIRCRAFT STATUS:
    ACTIVE ──maintenance──► MAINTENANCE ──repair──► ACTIVE
      │                                                │
      └────────────────retire────────────────►  RETIRED

    FLIGHT STATUS:
    SCHEDULED ──boarding──► ACTIVE ──landing──► COMPLETED
        │                     │
        │                     ├──delay──► DELAYED ──resume──► ACTIVE
        │                     │
        └─────────────cancel──┴──────────► CANCELLED
```

---

## 📋 API Documentation

### Quick Reference

| Service              | Base URL                | Endpoints                                                       |
| -------------------- | ----------------------- | --------------------------------------------------------------- |
| User Service         | `http://localhost:5001` | `/auth/*`, `/api/users/*`                                       |
| Location Service     | `http://localhost:5004` | `/api/cities/*`, `/api/airports/*`                              |
| Airline Core Service | `http://localhost:5005` | `/api/airlines/*`, `/api/aircrafts/*`                           |
| Flight Ops Service   | `http://localhost:5006` | `/api/flights/*`, `/api/schedules/*`, `/api/flight-instances/*` |

For detailed API documentation for each service, please refer to individual service READMEs:

- [User Service API](./microservices/services/user-service/README.md#api-endpoints)
- [Location Service API](./microservices/services/location-service/README.md#api-endpoints)
- [Airline Core Service API](./microservices/services/airline-core-service/README.md#api-endpoints)
- [Flight Ops Service API](./microservices/services/flight-ops-service/README.md#api-endpoints)

### Example API Calls

#### Register a New User

```bash
curl -X POST http://localhost:5001/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER"
  }'
```

#### Create a City

```bash
curl -X POST http://localhost:5004/api/cities \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New York",
    "code": "NYC",
    "countryCode": "US"
  }'
```

#### Register an Airline

```bash
curl -X POST http://localhost:5005/api/airlines \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "name": "Sky Airlines",
    "code": "SKY",
    "country": "USA"
  }'
```

#### Register a Flight

```bash
curl -X POST http://localhost:5006/api/flights \
  -H "Content-Type: application/json" \
  -H "X-Airline-Id: 1" \
  -d '{
    "flightNumber": "SKY123",
    "aircraftId": 1,
    "departureAirportId": 1,
    "arrivalAirportId": 2,
    "status": "SCHEDULED"
  }'
```

#### Create a Flight Instance

```bash
curl -X POST http://localhost:5006/api/flight-instances \
  -H "Content-Type: application/json" \
  -H "X-Airline-Id: 1" \
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

---

## 💾 Database Architecture

### Entity Relationship Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                        DATABASE ARCHITECTURE                     │
└─────────────────────────────────────────────────────────────────┘

    airline_user_db                 airline_location_db
    ┌─────────────┐                 ┌──────────────┐
    │    User     │                 │    City      │
    │─────────────│                 │──────────────│
    │ id          │                 │ id           │
    │ email       │                 │ name         │
    │ password    │                 │ code         │
    │ role        │                 │ countryCode  │
    └─────────────┘                 └──────┬───────┘
                                           │
                                           │ has many
                                           ▼
                                    ┌──────────────┐
                                    │   Airport    │
                                    │──────────────│
                                    │ id           │
                                    │ name         │
                                    │ iataCode     │
                                    │ cityId    FK │
                                    └──────┬───────┘
                                           │
                        ┌──────────────────┼──────────────────┐
                        │                  │                  │
    airline_core_db     │                  │   airline_flight_db
    ┌───────────────┐   │                  │   ┌──────────────┐
    │   Airline     │   │                  │   │    Flight    │
    │───────────────│   │                  │   │──────────────│
    │ id            │   │                  │   │ id           │
    │ name          │───┼──┐               │   │ flightNumber │
    │ code          │   │  │               │   │ airlineId FK │◄──┘
    │ ownerId    FK │◄──┘  │ owns          │   │ aircraftId FK│◄─┐
    │ status        │      │               │   │ depAirportId │  │
    └───────────────┘      │               │   │ arrAirportId │  │
                           │               │   │ status       │  │
    ┌───────────────┐      │               │   └──────┬───────┘  │
    │   Aircraft    │      │               │          │          │
    │───────────────│      │               │          │ has many │
    │ id            │◄─────┘               │          ▼          │
    │ model         │                      │   ┌──────────────┐  │
    │ code          │                      │   │FlightInstance│  │
    │ airlineId  FK │──────────────────────┤   │──────────────│  │
    │ totalSeats    │                      │   │ id           │  │
    │ status        │                      │   │ flightId  FK │──┘
    └───────────────┘                      │   │ airlineId FK │
                                           │   │ scheduleId   │
                                           │   │ depDateTime  │
                                           │   │ arrDateTime  │
                                           │   │ totalSeats   │
                                           │   │ availSeats   │
                                           │   │ status       │
                                           │   └──────────────┘
                                           │
                                           └─────────────────────────┐
                                                                     │
                                                    References       │
                                                    Airports ────────┘
```

### Key Relationships

| Entity         | Relationship        | Related Entity | Type        |
| -------------- | ------------------- | -------------- | ----------- |
| City           | has many            | Airport        | One-to-Many |
| User (Owner)   | owns                | Airline        | One-to-One  |
| Airline        | has many            | Aircraft       | One-to-Many |
| Airline        | operates            | Flight         | One-to-Many |
| Aircraft       | assigned to         | Flight         | Many-to-One |
| Airport        | departure point for | Flight         | Many-to-One |
| Airport        | arrival point for   | Flight         | Many-to-One |
| Flight         | has many            | FlightInstance | One-to-Many |
| FlightInstance | departs from        | Airport        | Many-to-One |
| FlightInstance | arrives at          | Airport        | Many-to-One |

---

## 🛠️ Tech Stack

### Backend

- **Java 21**: Modern Java features and performance
- **Spring Boot 4.0.5**: Application framework
- **Spring Cloud 2025.1.0**: Microservices infrastructure (planned)
- **Spring Security**: Authentication and authorization
- **JWT (jsonwebtoken 0.13.0)**: Token-based authentication

### Database & Persistence

- **MySQL 8.0+**: Relational database
- **Spring Data JPA**: Data access layer
- **Hibernate**: ORM framework

### Tools & Libraries

- **Lombok**: Reduce boilerplate code
- **Maven**: Dependency management and build tool
- **Jakarta Validation**: Input validation

### Architecture

- **RESTful APIs**: HTTP-based communication
- **Microservices**: Independent, scalable services
- **Layered Architecture**: Controller → Service → Repository

---

## 📂 Project Structure

```
airline-management/
├── frontend/                          # (Upcoming) Frontend application
├── microservices/
│   ├── pom.xml                       # Parent POM
│   ├── common-lib/                   # Shared library
│   │   ├── src/main/java/com/aryan/
│   │   │   ├── dto/                  # Data Transfer Objects
│   │   │   ├── enums/                # Enums (Status, Roles)
│   │   │   ├── payload/              # Request/Response objects
│   │   │   └── embeddable/           # JPA Embeddable classes
│   │   └── README.md
│   ├── cloud/                        # (Future) Cloud infrastructure
│   │   ├── gateway/                  # API Gateway
│   │   ├── discovery/                # Service Registry
│   │   └── config-server/            # Configuration Server
│   └── services/
│       ├── user-service/             # Port 5001
│       │   ├── src/main/java/
│       │   │   ├── controller/       # REST Controllers
│       │   │   ├── service/          # Business Logic
│       │   │   ├── repository/       # Data Access
│       │   │   ├── model/            # JPA Entities
│       │   │   ├── config/           # Configuration
│       │   │   └── mapper/           # DTO Mappers
│       │   ├── src/main/resources/
│       │   │   └── application.yaml
│       │   └── README.md
│       ├── location-service/         # Port 5004
│       │   ├── src/main/java/
│       │   └── README.md
│       ├── airline-core-service/     # Port 5005
│       │   ├── src/main/java/
│       │   └── README.md
│       └── flight-ops-service/       # Port 5006
│           ├── src/main/java/
│           └── README.md
├── .gitignore
├── LICENSE
└── README.md                         # You are here
```

---

## 🧪 Testing

Run tests for all services:

```bash
cd microservices
mvn test
```

Run tests for a specific service:

```bash
mvn test -pl services/user-service
```

---

## 🤝 Contributing

We welcome contributions! Here's how you can help:

### How to Contribute

1. **Fork the Project**

   ```bash
   git clone https://github.com/your-username/airline-management.git
   ```

2. **Create a Feature Branch**

   ```bash
   git checkout -b feature/AmazingFeature
   ```

3. **Make Your Changes**
   - Write clean, readable code
   - Follow existing code style
   - Add tests for new features
   - Update documentation

4. **Commit Your Changes**

   ```bash
   git commit -m 'Add some AmazingFeature'
   ```

5. **Push to Your Branch**

   ```bash
   git push origin feature/AmazingFeature
   ```

6. **Open a Pull Request**
   - Describe your changes
   - Link any related issues
   - Wait for review

### Coding Standards

- Follow Java naming conventions
- Write meaningful commit messages
- Add JavaDoc for public methods
- Keep methods small and focused
- Write unit tests for business logic

---

## 🐛 Troubleshooting

### Common Issues

**Issue**: `Port already in use`

```
Solution: Change the port in application.yaml or stop the service using that port
```

**Issue**: `Access denied for user 'root'@'localhost'`

```
Solution: Update MySQL credentials in application.yaml
```

**Issue**: `ClassNotFoundException: com.mysql.cj.jdbc.Driver`

```
Solution: Run mvn clean install to download dependencies
```

**Issue**: Database connection fails

```
Solution: Ensure MySQL is running and databases are created
```

---

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/your-username/airline-management/issues)
- **Email**: your-email@example.com
- **Documentation**: Check individual service READMEs

---

## 🗺️ Roadmap

### Phase 1: Foundation ✅ (Completed)

- [x] User Service with authentication
- [x] Location Service for cities and airports
- [x] Airline Core Service
- [x] Flight Operations Service
- [x] Flight Instance Management
- [x] Common Library with shared components

### Phase 2: Infrastructure 🚧 (In Progress)

```
┌─────────────────────────────────────────────────────────────┐
│                    FUTURE ARCHITECTURE                       │
└─────────────────────────────────────────────────────────────┘

    🌐 API Gateway (Spring Cloud Gateway)
         │
         ├──► 🔍 Service Discovery (Eureka)
         │
         ├──► ⚙️  Config Server (Centralized Configuration)
         │
         └──► 🔒 OAuth2 Authorization Server
                   │
        ┌──────────┼──────────┬─────────────┐
        │          │          │             │
        ▼          ▼          ▼             ▼
     User      Location   Airline        Flight
    Service    Service    Core          Operations
                          Service        Service
```

- [ ] API Gateway implementation
- [ ] Service Discovery (Eureka)
- [ ] Centralized Configuration Server
- [ ] Frontend application (React/Angular)
- [ ] Docker containerization
- [ ] Kubernetes deployment
- [ ] Flight booking service
- [ ] Payment integration
- [ ] Email notifications
- [ ] Swagger/OpenAPI documentation

---

## 📄 License

Distributed under the MIT License. See [`LICENSE`](LICENSE) for more information.

---

## 👏 Acknowledgments

- Spring Boot Team for the amazing framework
- All contributors who help improve this project
- Open source community

---

<div align="center">

**Made with ❤️ by the Airline Management Team**

⭐ Star this repository if you find it helpful!

</div>
