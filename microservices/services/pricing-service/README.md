# 💰 Pricing Service

> Fare, fare rules, and baggage policy management microservice for the Airline Management System

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)](https://www.mysql.com/)

---

## Overview

The **Pricing Service** manages all pricing-related data for the Airline Management System:

- **Fares**: Pricing tiers per flight and cabin class with benefit bundles
- **Fare Rules**: Refund, change, and cancellation policies per fare
- **Baggage Policies**: Cabin and check-in baggage allowances per fare

**Port**: `5007`  
**Database**: `airline_pricing_db`  
**Base URL**: `http://localhost:5007`

---

## Features

- Create and manage fares with cabin class benefits (seat, boarding, in-flight, flexibility, premium)
- Define fare rules (refundable, changeable, deadlines, fees)
- Define baggage policies (cabin bag, check-in bag, priority baggage)
- Batch fare lookup and lowest-fare-per-flight search
- Airline-scoped retrieval for fare rules and baggage policies

---

## Tech Stack

- Java 21, Spring Boot 4.0.5, Spring Data JPA
- MySQL 8.0+, Lombok, Jakarta Validation

---

## Getting Started

### Database Setup

```sql
CREATE DATABASE airline_pricing_db;
```

### Configuration

Update `src/main/resources/application.yaml`:

```yaml
server:
  port: 5007

spring:
  application:
    name: pricing-service
  datasource:
    url: jdbc:mysql://localhost:3306/airline_pricing_db
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

## API Endpoints

### Fares — `/api/fares`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create a fare |
| GET | `/` | Get all fares |
| GET | `/{id}` | Get fare by ID |
| GET | `/flight/{flightId}/cabin-class/{cabinClassId}` | Get fares by flight and cabin class |
| POST | `/search` | Get lowest fare per flight (by cabin class) |
| POST | `/batch-by-ids` | Get fares by list of IDs |
| PUT | `/{id}` | Update a fare |
| DELETE | `/{id}` | Delete a fare |

### Fare Rules — `/api/fare-rules`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create fare rules |
| GET | `/{id}` | Get fare rules by ID |
| GET | `/fare/{fareId}` | Get fare rules by fare ID |
| GET | `/airline/{airlineId}` | Get all fare rules for an airline |
| PUT | `/{id}` | Update fare rules |
| DELETE | `/{id}` | Delete fare rules |

### Baggage Policies — `/api/baggage-policy`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create a baggage policy |
| GET | `/{id}` | Get baggage policy by ID |
| GET | `/fare/{fareId}` | Get baggage policy by fare ID |
| GET | `/airline/{airlineId}` | Get all baggage policies for an airline |
| PUT | `/{id}` | Update a baggage policy |
| DELETE | `/{id}` | Delete a baggage policy |

---

## Example Requests

### Create a Fare

```bash
curl -X POST http://localhost:5007/api/fares \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Economy Saver",
    "rbdCode": "S",
    "flightId": 1,
    "cabinClassId": 1,
    "cabinClass": "ECONOMY",
    "baseFare": 80.00,
    "taxesAndFees": 15.00,
    "airlineFees": 5.00,
    "currentPrice": 100.00
  }'
```

### Get Lowest Fare Per Flight

```bash
curl -X POST "http://localhost:5007/api/fares/search?cabinClassId=1" \
  -H "Content-Type: application/json" \
  -d '[1, 2, 3]'
```

### Create Fare Rules

```bash
curl -X POST http://localhost:5007/api/fare-rules \
  -H "Content-Type: application/json" \
  -d '{
    "fareId": 1,
    "airlineId": 5,
    "ruleName": "Economy Saver Rules",
    "isRefundable": false,
    "isChangeable": true,
    "changeFee": 50.00,
    "cancellationFee": 100.00,
    "changeDeadlineHours": 24
  }'
```

### Create Baggage Policy

```bash
curl -X POST http://localhost:5007/api/baggage-policy \
  -H "Content-Type: application/json" \
  -d '{
    "fareId": 1,
    "airlineId": 5,
    "name": "Economy Baggage",
    "cabinBaggageMaxWeight": 7.0,
    "cabinBaggagePieces": 1,
    "checkInBaggageMaxWeight": 23.0,
    "checkInBaggagePieces": 1,
    "freeCheckedBagsAllowance": 1
  }'
```

---

## Database Schema

### Key Entities

| Entity | Description |
|--------|-------------|
| `Fare` | Pricing tier for a flight + cabin class combination |
| `FareRules` | Refund and change policies linked to a fare (one-to-one) |
| `BaggagePolicy` | Baggage allowances linked to a fare (one-to-one) |

---

## Project Structure

```
pricing-service/
├── src/main/java/com/aryan/
│   ├── controller/
│   │   ├── FareController.java
│   │   ├── FareRulesController.java
│   │   └── BaggagePolicyController.java
│   ├── service/
│   │   ├── FareService.java
│   │   ├── FareRulesService.java
│   │   ├── BaggagePolicyService.java
│   │   └── impl/
│   ├── model/
│   │   ├── Fare.java
│   │   ├── FareRules.java
│   │   └── BaggagePolicy.java
│   ├── repository/
│   ├── mapper/
│   └── PricingServiceApplication.java
└── pom.xml
```

---

## Dependencies

- **Flight Ops Service**: For flight instance references (`flightId`)
- **Airline Core Service**: For airline references (`airlineId`)
- **Common Library**: Shared DTOs and request/response payloads

---

## Related Services

- [Flight Ops Service](../flight-ops-service/README.md)
- [Airline Core Service](../airline-core-service/README.md)
- [Common Library](../../common-lib/README.md)

---

## License

Part of the Airline Management System — MIT License
