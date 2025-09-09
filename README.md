# ClubConnect - Microservices Architecture

A comprehensive club management system built with Spring Boot microservices architecture, featuring service discovery, API gateway, and multiple specialized services for managing clubs, members, events, and registrations.

## 🏗️ Architecture Overview

ClubConnect follows a microservices architecture pattern with the following components:

- **Eureka Server** (Port 8761) - Service discovery and registration
- **API Gateway** (Port 8080) - Central entry point for all client requests
- **Club Service** (Port 8081) - Manages club information and operations
- **Member Service** (Port 8082) - Handles member management
- **Event Service** (Port 8083) - Manages events and scheduling
- **Registration Service** (Port 8084) - Handles event registrations

## 🚀 Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Database**: H2 In-Memory Database
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Cloud**: Spring Cloud 2023.0.0

## 📋 Features Implemented

### 1. Club Service (Port 8081)
**Full CRUD Operations for Club Management**

- **Models**:
  - `Club`: Core entity with name, description, category
  - `Member`: Associated members with club relationships
  - `Event`: Club events with date/time and location

- **Endpoints**:
  - `GET /` - Service status and information
  - `GET /clubs` - Retrieve all clubs
  - `GET /clubs/{id}` - Get specific club by ID
  - `POST /clubs` - Create new club
  - `PUT /clubs/{id}` - Update existing club
  - `DELETE /clubs/{id}` - Delete club

- **Features**:
  - JPA relationships (One-to-Many with Members and Events)
  - Sample data initialization with Tech, Sports, and Arts clubs
  - H2 database with console access at `/h2-console`

### 2. Member Service (Port 8082)
**Comprehensive Member Management**

- **Model**: `Member` with enhanced fields
  - Basic info: name, email, phone
  - Club association: clubName
  - Status tracking: joinDate, status (ACTIVE/INACTIVE)
  - Unique email constraint

- **Endpoints**:
  - `GET /` - Service status
  - `GET /members` - Get all members (currently returns placeholder)

- **Features**:
  - Sample data with 5 pre-populated members
  - Email uniqueness validation
  - Automatic join date tracking
  - Club-based member filtering capability

### 3. Event Service (Port 8083)
**Event Management with Capacity Control**

- **Model**: `Event` with comprehensive event details
  - Basic info: name, description, location
  - Scheduling: dateTime
  - Club association: clubName
  - Capacity management: maxCapacity, currentCapacity
  - Status tracking: status (UPCOMING/ONGOING/COMPLETED)

- **Endpoints**:
  - `GET /` - Service status
  - `GET /events` - Get all events (currently returns placeholder)

- **Features**:
  - Capacity management with availability checking
  - Sample events with realistic scheduling
  - Club-based event filtering
  - Upcoming events filtering capability

### 4. Registration Service (Port 8084)
**Event Registration Management**

- **Model**: `Registration` linking members to events
  - Member and Event references: memberId, eventId
  - Registration details: registrationDate, status
  - Display names: memberName, eventName

- **Endpoints**:
  - `GET /` - Service status
  - `GET /registrations` - Get all registrations (currently returns placeholder)

- **Features**:
  - Sample registrations linking members to events
  - Status tracking (CONFIRMED/PENDING/CANCELLED)
  - Member and event-based filtering capabilities

### 5. API Gateway (Port 8080)
**Centralized Request Routing**

- **Features**:
  - Service discovery integration with Eureka
  - Automatic routing based on service names
  - Explicit route configuration for each service
  - Load balancing support
  - Health monitoring endpoints

- **Routes**:
  - `/clubs/**` → Club Service
  - `/members/**` → Member Service
  - `/events/**` → Event Service
  - `/registrations/**` → Registration Service

### 6. Eureka Server (Port 8761)
**Service Discovery and Registration**

- **Features**:
  - Service registry for all microservices
  - Health monitoring
  - Service discovery for API Gateway
  - Standalone mode configuration

## 🗄️ Database Schema

Each service uses its own H2 in-memory database:

- **Club Service**: `clubdb` - Stores clubs, members, and events
- **Member Service**: `memberdb` - Member information with club associations
- **Event Service**: `eventdb` - Event details with capacity management
- **Registration Service**: `registrationdb` - Event registration records

### Sample Data

The system initializes with realistic sample data:

**Clubs**: Tech Club, Sports Club, Arts Club
**Members**: 5 sample members with different club affiliations
**Events**: 5 upcoming events across different clubs
**Registrations**: 5 sample registrations linking members to events

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Quick Start

#### Windows
```bash
run-services.bat
```

#### Linux/Mac
```bash
./run-services.sh
```

#### Manual Start
```bash
# Start Eureka Server
cd eureka-server && mvn spring-boot:run

# Start API Gateway
cd api-gateway && mvn spring-boot:run

# Start Club Service
cd club-service && mvn spring-boot:run

# Start Member Service
cd member-service && mvn spring-boot:run

# Start Event Service
cd event-service && mvn spring-boot:run

# Start Registration Service
cd registration-service && mvn spring-boot:run
```

### Service URLs

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Club Service**: http://localhost:8081
- **Member Service**: http://localhost:8082
- **Event Service**: http://localhost:8083
- **Registration Service**: http://localhost:8084

## 🧪 Testing the Services

### Using Browser
Visit any service URL to see JSON status responses.

### Using curl
```bash
# Test service status
curl http://localhost:8081/
curl http://localhost:8082/
curl http://localhost:8083/
curl http://localhost:8084/

# Test club operations
curl http://localhost:8081/clubs
curl -X POST http://localhost:8081/clubs \
  -H "Content-Type: application/json" \
  -d '{"name":"New Club","description":"Test Club","category":"Technology"}'

# Test through API Gateway
curl http://localhost:8080/clubs
curl http://localhost:8080/members
curl http://localhost:8080/events
curl http://localhost:8080/registrations
```

### Using Postman
Import the service URLs and test endpoints with JSON requests.

## 🔧 Configuration

### Database Access
Each service provides H2 console access:
- Club Service: http://localhost:8081/h2-console
- Member Service: http://localhost:8082/h2-console
- Event Service: http://localhost:8083/h2-console
- Registration Service: http://localhost:8084/h2-console

**Default H2 Credentials**:
- JDBC URL: `jdbc:h2:mem:[service]db`
- Username: `sa`
- Password: `password`

### Service Discovery
All services automatically register with Eureka Server and can be discovered by the API Gateway.

## 📊 Current Implementation Status

### ✅ Completed Features
- [x] Microservices architecture setup
- [x] Service discovery with Eureka
- [x] API Gateway with routing
- [x] Complete Club Service with CRUD operations
- [x] Member Service with data models
- [x] Event Service with capacity management
- [x] Registration Service with linking functionality
- [x] Sample data initialization
- [x] H2 database integration
- [x] JPA entity relationships
- [x] RESTful API endpoints
- [x] Service health monitoring

### 🔄 Partially Implemented
- [ ] Member Service endpoints (basic structure ready)
- [ ] Event Service endpoints (basic structure ready)
- [ ] Registration Service endpoints (basic structure ready)

### 🚧 Future Enhancements
- [ ] Complete CRUD operations for all services
- [ ] Inter-service communication
- [ ] Authentication and authorization
- [ ] Data validation and error handling
- [ ] Logging and monitoring
- [ ] Docker containerization
- [ ] CI/CD pipeline
- [ ] Frontend application

## 🏛️ Project Structure

```
ClubConnect/
├── api-gateway/           # Spring Cloud Gateway
├── eureka-server/         # Service Discovery Server
├── club-service/          # Club Management Service
├── member-service/        # Member Management Service
├── event-service/         # Event Management Service
├── registration-service/  # Registration Management Service
├── run-services.bat      # Windows startup script
├── run-services.sh       # Linux/Mac startup script
└── README.md             # This file
```

## 🤝 Contributing

This project demonstrates a clean microservices architecture with Spring Boot. Each service is independently deployable and follows RESTful API principles.

## 📝 License

This project is for educational and demonstration purposes.
