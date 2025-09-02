# ClubConnect - Minimalist Microservices

This is a cleaned up, minimalist version of the ClubConnect microservices architecture. All UI components have been removed, and the services now return clean JSON data.

## Services

### 1. Club Service (Port 8081)
- **URL**: http://localhost:8081
- **Endpoints**:
  - `GET /` - Service status
  - `GET /clubs` - Get all clubs
  - `GET /clubs/{id}` - Get club by ID
  - `POST /clubs` - Create new club
  - `PUT /clubs/{id}` - Update club
  - `DELETE /clubs/{id}` - Delete club

### 2. Member Service (Port 8082)
- **URL**: http://localhost:8082
- **Endpoints**:
  - `GET /` - Service status
  - `GET /members` - Get all members

### 3. Event Service (Port 8083)
- **URL**: http://localhost:8083
- **Endpoints**:
  - `GET /` - Service status
  - `GET /events` - Get all events

### 4. Registration Service (Port 8084)
- **URL**: http://localhost:8084
- **Endpoints**:
  - `GET /` - Service status
  - `GET /registrations` - Get all registrations

### 5. Movie Service (Port 9003)
- **URL**: http://localhost:9003
- **Endpoints**:
  - `GET /movies` - Get all movies
  - `GET /movies/movie/{movieId}` - Get movie by ID
  - `GET /movies/movie/search/{movieName}` - Search movies by name
  - `POST /movies` - Add new movie

## Running the Services

### Windows
```bash
run-services.bat
```

### Linux/Mac
```bash
./run-services.sh
```

### Manual Start
```bash
# Club Service
cd club-service && mvn spring-boot:run

# Member Service
cd member-service && mvn spring-boot:run

# Event Service
cd event-service && mvn spring-boot:run

# Registration Service
cd registration-service && mvn spring-boot:run

# Movie Service
cd Movie && mvn spring-boot:run
```

## Testing the Services

All services return JSON responses. You can test them using:

### Browser
- Visit http://localhost:8081 (or any service port)
- You'll see a JSON response with service information

### curl
```bash
# Test service status
curl http://localhost:8081/

# Test club service
curl http://localhost:8081/clubs

# Test movie service
curl http://localhost:9003/movies
```

### Postman
Import the service URLs and test the endpoints with JSON requests.

## Database

- **Club Service**: Uses H2 in-memory database
- **Member Service**: Uses H2 in-memory database  
- **Event Service**: Uses H2 in-memory database
- **Registration Service**: Uses H2 in-memory database
- **Movie Service**: Uses MySQL database (moviedb)

## Features Removed

- All HTML/UI components
- Complex external service integrations
- Unnecessary DTOs and dependencies
- UI-related JavaScript and CSS

## What's Left

- Clean REST API endpoints
- JSON responses
- Basic CRUD operations
- Minimal dependencies
- Simple service architecture
