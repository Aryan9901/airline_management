# Location Service 📍

The `location-service` is responsible for managing the geographical data of the Airline Management System, including cities and airports worldwide.

## 🛠️ Features

- **City Management**: Create, update, and search for cities with pagination and sorting.
- **Airport Management**: Manage airport details linked to specific cities.
- **Advanced Search**: Search cities by name or country code, and list airports by city ID.
- **Validation**: Uses Jakarta Validation for robust data integrity.

## 📡 API Endpoints

### Cities (`/api/cities`)
- `POST /`: Create a new city.
- `GET /`: Get all cities (paginated).
- `GET /{id}`: Get city details by ID.
- `PUT /{id}`: Update city information.
- `DELETE /{id}`: Remove a city.
- `GET /search?keyword=...`: Search cities by name.
- `GET /country/{code}`: Get cities by ISO country code.

### Airports (`/api/airports`)
- `POST /`: Create a new airport.
- `GET /`: List all airports.
- `GET /{id}`: Get airport details.
- `PUT /{id}`: Update airport.
- `DELETE /{id}`: Delete airport.
- `GET /city/{cityId}`: List all airports in a specific city.

## ⚙️ Configuration

- **Port**: 5004
- **Database**: `airline_location_db` (MySQL)
- **Service Name**: `location-service`

## 🏗️ Architecture

- **Controller**: REST API Layer
- **Service**: Business Logic implementation
- **Mapper**: Entity-to-DTO conversion
- **Repository**: Spring Data JPA interface
- **Model**: Database entities (City, Airport)

---
*Part of the Airline Management System*
