# User Service 👤

The `user-service` manages user identities, security, and profiles within the Airline Management System.

## 🛠️ Features

- **User Profiles**: Management of user data including roles and permissions.
- **Security**: Integration with Spring Security for authentication and authorization.
- **Centralized Identity**: The primary authority for user-related data.

## 📡 API Endpoints

- `GET /`: Service status check.
- `GET /help`: Basic help and information.
- *(Authentication and registration endpoints are currently under development)*

## ⚙️ Configuration

- **Port**: 5001
- **Database**: `airline_location_db` (MySQL)
- **Service Name**: `user-service`

## 🏗️ Technology Stack

- **Spring Boot 4.0.5**
- **Spring Security**
- **Spring Data JPA**
- **MySQL Connector**

---
*Part of the Airline Management System*
