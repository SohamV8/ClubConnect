# Club Management Microservices

A professional microservices architecture for Club Management System built with Spring Boot.

## 🏗️ Architecture

This project follows a microservices pattern with four independent services:

- **Club Service** (Port 8081) - Manages clubs and their information
- **Member Service** (Port 8082) - Handles member management
- **Event Service** (Port 8083) - Manages events and scheduling
- **Registration Service** (Port 8084) - Handles event registrations

## 📁 Project Structure

```
ClubConnect/
├── club-service/
│   └── src/main/java/com/example/clubservice/
│       ├── config/
│       ├── controller/
│       ├── dto/
│       ├── model/
│       ├── repository/
│       └── service/
├── member-service/
│   └── src/main/java/com/example/memberservice/
│       ├── config/
│       ├── controller/
│       ├── dto/
│       ├── model/
│       ├── repository/
│       └── service/
├── event-service/
│   └── src/main/java/com/example/eventservice/
│       ├── config/
│       ├── controller/
│       ├── dto/
│       ├── model/
│       ├── repository/
│       └── service/
├── registration-service/
│   └── src/main/java/com/example/registrationservice/
│       ├── config/
│       ├── controller/
│       ├── dto/
│       ├── model/
│       ├── repository/
│       └── service/
├── run-all-services.bat
├── run-all-services.sh
└── README.md
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running All Services

#### Windows
```bash
run-all-services.bat
```

#### Linux/Mac
```bash
chmod +x run-all-services.sh
./run-all-services.sh
```

### Running Individual Services

```bash
# Club Service
cd club-service
mvn spring-boot:run

# Member Service
cd member-service
mvn spring-boot:run

# Event Service
cd event-service
mvn spring-boot:run

# Registration Service
cd registration-service
mvn spring-boot:run
```

## 🌐 Service Endpoints

### Club Service (Port 8081)
- Base URL: `http://localhost:8081`
- H2 Console: `http://localhost:8081/h2-console`

### Member Service (Port 8082)
- Base URL: `http://localhost:8082`
- H2 Console: `http://localhost:8082/h2-console`

### Event Service (Port 8083)
- Base URL: `http://localhost:8083`
- H2 Console: `http://localhost:8083/h2-console`

### Registration Service (Port 8084)
- Base URL: `http://localhost:8084`
- H2 Console: `http://localhost:8084/h2-console`

## 🗄️ Database

Each service uses an H2 in-memory database:
- **Username**: `sa`
- **Password**: `password`
- **Driver**: `org.h2.Driver`

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: H2 (In-Memory)
- **JPA**: Spring Data JPA
- **Web**: Spring Web

## 📦 Dependencies

Each service includes:
- `spring-boot-starter-web` - REST API support
- `spring-boot-starter-data-jpa` - Database operations
- `h2` - In-memory database

## 🔧 Development

### Adding Business Logic

1. **Models**: Add JPA entities in the `model` package
2. **Repositories**: Create data access interfaces in the `repository` package
3. **Services**: Implement business logic in the `service` package
4. **Controllers**: Expose REST endpoints in the `controller` package
5. **DTOs**: Create data transfer objects in the `dto` package
6. **Configuration**: Add custom configuration in the `config` package

### Building

```bash
# Build all services
mvn clean install

# Build individual service
cd club-service
mvn clean install
```

## 📝 API Documentation

Each service is ready for REST API implementation. The structure follows Spring Boot best practices:

- **GET** endpoints for retrieving data
- **POST** endpoints for creating resources
- **PUT** endpoints for updating resources
- **DELETE** endpoints for removing resources

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions, please open an issue in the GitHub repository.
