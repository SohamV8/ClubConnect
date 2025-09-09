# ClubConnect Microservices - Viva Presentation Guide

## 🎯 **Project Overview for Viva**

**ClubConnect** is a comprehensive microservices-based club management system built with Spring Boot, featuring service discovery, API gateway, MySQL persistence, and inter-service communication. This system demonstrates modern microservices architecture patterns and best practices.

---

## 🏗️ **System Architecture**

### **Microservices Architecture Diagram**
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Eureka Server │    │   API Gateway   │    │  Club Service   │
│   (Port 8761)   │◄───┤   (Port 8080)   │◄───┤   (Port 8081)   │
│  Service Discovery│    │  Load Balancer  │    │  Club Management│
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
        ┌───────▼──────┐ ┌──────▼──────┐ ┌─────▼──────┐
        │Member Service│ │Event Service│ │Registration│
        │ (Port 8082)  │ │ (Port 8083) │ │  Service   │
        │Member Mgmt   │ │Event Mgmt   │ │ (Port 8084)│
        │              │ │Capacity     │ │Registration│
        └──────────────┘ └─────────────┘ └────────────┘
```

### **Database Architecture**
```
┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│  memberdb   │  │   clubdb    │  │   eventdb   │  │registrationdb│
│ (MySQL)     │  │ (MySQL)     │  │ (MySQL)     │  │ (MySQL)     │
│             │  │             │  │             │  │             │
│ members     │  │ clubs       │  │ events      │  │ registrations│
│ - id        │  │ - id        │  │ - id        │  │ - id        │
│ - name      │  │ - name      │  │ - name      │  │ - memberId  │
│ - email     │  │ - description│  │ - description│  │ - eventId   │
│ - phone     │  │ - category  │  │ - location  │  │ - memberName│
│ - clubName  │  │             │  │ - dateTime  │  │ - eventName │
│ - joinDate  │  │             │  │ - clubName  │  │ - status    │
│ - status    │  │             │  │ - maxCapacity│  │             │
└─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘
```

---

## 🚀 **Quick Start Guide**

### **Prerequisites**
- Java 17 or higher
- Maven 3.6 or higher
- MySQL Server (Password: 2004)
- Windows/Linux/Mac terminal

### **Step 1: Start MySQL Service**
```bash
# Method 1: Using our script
mysql-service-manager.bat

# Method 2: Manual command
net start mysql

# Method 3: Using Services.msc
# Search for "Services" → Find "MySQL" → Start
```

### **Step 2: Start All Services**
```bash
# One-command startup
start-all-services-final.bat

# This will:
# 1. Create MySQL databases
# 2. Start Eureka Server
# 3. Start API Gateway
# 4. Start all microservices
```

### **Step 3: Verify Everything is Running**
```bash
# Check all ports
check-ports.bat

# Test all services
test-all-services-final.bat
```

### **Step 4: View Data in MySQL**
```bash
# View all databases and data
view-databases.bat

# Or use MySQL Workbench:
# 1. Open MySQL Workbench
# 2. Connect to localhost:3306
# 3. Username: root, Password: 2004
# 4. Browse databases: memberdb, clubdb, eventdb, registrationdb
```

---

## 📊 **Service Details & Functionality**

### **1. Eureka Server (Port 8761)**
**Purpose**: Service discovery and registration center

**Key Features**:
- Registers all microservices
- Provides service discovery
- Health monitoring
- Load balancing support

**What to Show**:
- Open http://localhost:8761 in browser
- Show registered services dashboard
- Explain service discovery concept

### **2. API Gateway (Port 8080)**
**Purpose**: Central entry point for all client requests

**Key Features**:
- Routes requests to appropriate services
- Load balancing
- Service discovery integration
- Single point of access

**Configuration**:
```yaml
# Routes configured in application.properties
/clubs/** → Club Service
/members/** → Member Service
/events/** → Event Service
/registrations/** → Registration Service
```

### **3. Club Service (Port 8081)**
**Purpose**: Manages club information and operations

**Database**: `clubdb` (MySQL)
**Main Table**: `clubs`

**Enhanced Features**:
- **DTO Pattern**: Clean API contracts
- **Inter-Service Communication**: Fetches member/event counts
- **Data Enrichment**: Combines data from other services
- **Validation**: Ensures data integrity

**Key Endpoints**:
```bash
GET    /clubs                    # Get all clubs with enriched data
GET    /clubs/{id}              # Get specific club
GET    /clubs/name/{name}       # Get club by name
POST   /clubs                   # Create new club
PUT    /clubs/{id}              # Update club
DELETE /clubs/{id}              # Delete club
GET    /clubs/{name}/statistics # Get club statistics
```

### **4. Member Service (Port 8082)**
**Purpose**: Manages member information and club associations

**Database**: `memberdb` (MySQL)
**Main Table**: `members`

**Enhanced Features**:
- **Club Validation**: Validates club existence before member creation
- **Email Uniqueness**: Prevents duplicate members
- **Data Enrichment**: Fetches club details
- **Inter-Service Communication**: Validates with Club Service

**Key Endpoints**:
```bash
GET    /members                    # Get all members
GET    /members/{id}              # Get specific member
GET    /members/email/{email}     # Get member by email
GET    /members/club/{clubName}   # Get members by club
POST   /members                   # Create new member
PUT    /members/{id}              # Update member
DELETE /members/{id}              # Delete member
GET    /members/{id}/statistics   # Get member statistics
```

### **5. Event Service (Port 8083)**
**Purpose**: Manages events with capacity control and scheduling

**Database**: `eventdb` (MySQL)
**Main Table**: `events`

**Enhanced Features**:
- **Capacity Management**: Real-time capacity tracking
- **Club Validation**: Validates club existence
- **Date Validation**: Ensures future events only
- **Registration Integration**: Updates capacity on registrations

**Key Endpoints**:
```bash
GET    /events                    # Get all events
GET    /events/{id}              # Get specific event
GET    /events/club/{clubName}   # Get events by club
GET    /events/upcoming          # Get upcoming events
POST   /events                   # Create new event
PUT    /events/{id}              # Update event
DELETE /events/{id}              # Delete event
POST   /events/{eventId}/register/{memberId}    # Register member
POST   /events/{eventId}/unregister/{memberId}  # Unregister member
GET    /events/{id}/statistics   # Get event statistics
```

### **6. Registration Service (Port 8084)**
**Purpose**: Links members to events with validation and capacity management

**Database**: `registrationdb` (MySQL)
**Main Table**: `registrations`

**Enhanced Features**:
- **Member & Event Validation**: Validates both exist before registration
- **Capacity Validation**: Prevents overbooking
- **Duplicate Prevention**: Prevents multiple registrations
- **Status Management**: Tracks registration status

**Key Endpoints**:
```bash
GET    /registrations                    # Get all registrations
GET    /registrations/{id}              # Get specific registration
GET    /registrations/member/{memberId} # Get registrations by member
GET    /registrations/event/{eventId}   # Get registrations by event
POST   /registrations                   # Create registration
POST   /registrations/register/{memberId}/{eventId}    # Register member
POST   /registrations/unregister/{memberId}/{eventId}  # Unregister member
PUT    /registrations/{id}/status       # Update registration status
DELETE /registrations/{id}              # Delete registration
GET    /registrations/statistics        # Get registration statistics
```

---

## 🔧 **CRUD Operations Demo Guide**

### **1. Club Management**

#### **Create a Club**
```bash
curl -X POST http://localhost:8081/clubs \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Music Club",
    "description": "Music and performance club",
    "category": "Arts"
  }'
```

#### **Get All Clubs**
```bash
curl http://localhost:8081/clubs
```

#### **Get Club by ID**
```bash
curl http://localhost:8081/clubs/1
```

#### **Update Club**
```bash
curl -X PUT http://localhost:8081/clubs/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Advanced Tech Club",
    "description": "Advanced technology enthusiasts club",
    "category": "Technology"
  }'
```

#### **Delete Club**
```bash
curl -X DELETE http://localhost:8081/clubs/1
```

### **2. Member Management**

#### **Create a Member**
```bash
curl -X POST http://localhost:8082/members \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "999-888-7777",
    "clubName": "Tech Club"
  }'
```

#### **Get All Members**
```bash
curl http://localhost:8082/members
```

#### **Get Members by Club**
```bash
curl http://localhost:8082/members/club/Tech%20Club
```

### **3. Event Management**

#### **Create an Event**
```bash
curl -X POST http://localhost:8083/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Spring Boot Workshop",
    "description": "Learn Spring Boot microservices",
    "location": "Lab 301",
    "dateTime": "2025-09-20T10:00:00",
    "clubName": "Tech Club",
    "maxCapacity": 25
  }'
```

#### **Get All Events**
```bash
curl http://localhost:8083/events
```

#### **Get Upcoming Events**
```bash
curl http://localhost:8083/events/upcoming
```

### **4. Registration Management**

#### **Create Registration**
```bash
curl -X POST http://localhost:8084/registrations \
  -H "Content-Type: application/json" \
  -d '{
    "memberId": 1,
    "eventId": 1,
    "memberName": "Alice Smith",
    "eventName": "Java Workshop"
  }'
```

#### **Get All Registrations**
```bash
curl http://localhost:8084/registrations
```

---

## 🎯 **Viva Presentation Flow**

### **1. Introduction (2 minutes)**
- Explain microservices architecture
- Show the system architecture diagram
- Highlight key benefits: scalability, maintainability, fault tolerance

### **2. System Startup (3 minutes)**
- Start MySQL service using `mysql-service-manager.bat`
- Start all services using `start-all-services-final.bat`
- Show service registration in Eureka dashboard
- Explain service discovery concept

### **3. Database Demonstration (2 minutes)**
- Show MySQL databases created
- Use `view-databases.bat` to show data
- Explain database per service pattern
- Show data persistence across restarts

### **4. Service Functionality Demo (10 minutes)**

#### **Club Service Demo**
- Show enriched club data with member/event counts
- Explain inter-service communication
- Demonstrate CRUD operations

#### **Member Service Demo**
- Show member creation with club validation
- Demonstrate club-based filtering
- Show data enrichment with club details

#### **Event Service Demo**
- Show capacity management
- Demonstrate registration/unregistration
- Show upcoming events filtering

#### **Registration Service Demo**
- Show member-event linking
- Demonstrate validation across services
- Show registration statistics

### **5. API Gateway Demo (2 minutes)**
- Show how API Gateway routes requests
- Demonstrate load balancing
- Explain single point of access concept

### **6. Advanced Features (5 minutes)**
- Show inter-service communication
- Demonstrate error handling and fallbacks
- Show data consistency across services
- Explain DTO pattern and data enrichment

### **7. Technical Architecture (3 minutes)**
- Explain Spring Boot microservices
- Show service discovery with Eureka
- Explain API Gateway routing
- Highlight professional patterns used

---

## 🔍 **Key Technical Concepts to Explain**

### **1. Microservices Architecture**
- **Service Independence**: Each service can be developed, deployed, and scaled independently
- **Database per Service**: Each service has its own MySQL database
- **Inter-Service Communication**: Services communicate via HTTP/REST APIs

### **2. Service Discovery**
- **Eureka Server**: Central registry for all services
- **Client Registration**: Services register themselves with Eureka
- **Service Lookup**: Services can find other services by name

### **3. API Gateway Pattern**
- **Single Entry Point**: All client requests go through the gateway
- **Load Balancing**: Distributes requests across service instances
- **Cross-Cutting Concerns**: Handles authentication, logging, monitoring

### **4. Data Transfer Objects (DTOs)**
- **API Contracts**: Clean separation between internal entities and API responses
- **Data Enrichment**: Combines data from multiple services
- **Versioning**: Easy to evolve API without breaking clients

### **5. Inter-Service Communication**
- **Synchronous**: HTTP/REST calls between services
- **Error Handling**: Graceful degradation when services are unavailable
- **Circuit Breaker**: Prevents cascade failures

### **6. MySQL Integration**
- **Database per Service**: Each microservice has its own database
- **ACID Compliance**: Full transaction support
- **Data Persistence**: Data survives service restarts
- **Schema Auto-creation**: Hibernate creates tables from entities

---

## 🚨 **Common Issues & Solutions**

### **Issue 1: MySQL Service Not Starting**
**Solution**: 
```bash
# Check MySQL service status
sc query mysql

# Start MySQL service
net start mysql

# Or use our script
mysql-service-manager.bat
```

### **Issue 2: Services Not Registering**
**Solution**: 
- Ensure Eureka Server is running first
- Check service configuration files
- Verify service names match

### **Issue 3: Database Connection Failed**
**Solution**: 
```bash
# Test MySQL connection
mysql -u root -p2004 -e "SELECT VERSION();"

# Check if databases exist
mysql -u root -p2004 -e "SHOW DATABASES;"
```

### **Issue 4: Port Already in Use**
**Solution**: 
```bash
# Check what's using the port
netstat -an | findstr ":8081"

# Kill the process if needed
taskkill /PID <process_id> /F
```

---

## 📊 **Sample Data Available**

The system comes with pre-loaded sample data:

### **Clubs**
- Tech Club (Technology)
- Sports Club (Sports)
- Arts Club (Arts)

### **Members**
- Alice Smith (Tech Club)
- Bob Johnson (Sports Club)
- Charlie Brown (Arts Club)
- Diana Prince (Tech Club)
- Ethan Hunt (Sports Club)

### **Events**
- Java Workshop (Tech Club, 30 capacity)
- Basketball Tournament (Sports Club, 50 capacity)
- Art Exhibition (Arts Club, 100 capacity)
- Spring Boot Training (Tech Club, 25 capacity)
- Swimming Meet (Sports Club, 40 capacity)

---

## 🎓 **Viva Questions You Might Face**

### **Q1: Why did you choose microservices architecture?**
**Answer**: Microservices provide better scalability, maintainability, and fault isolation. Each service can be developed and deployed independently, making the system more resilient and easier to manage.

### **Q2: How do services communicate with each other?**
**Answer**: Services communicate via HTTP/REST APIs using service discovery. The API Gateway routes requests, and services use Eureka to find other services by name rather than hardcoded URLs.

### **Q3: What happens if one service fails?**
**Answer**: The system is designed with graceful degradation. If one service fails, other services continue to work. We use try-catch blocks and fallback responses to handle service failures gracefully.

### **Q4: How do you ensure data consistency?**
**Answer**: Each service has its own database, and we use eventual consistency. For critical operations, we validate data across services before making changes.

### **Q5: What design patterns did you implement?**
**Answer**: We implemented several patterns including DTO pattern for data transfer, Repository pattern for data access, Service Orchestration for business logic, and Circuit Breaker pattern for error handling.

### **Q6: Why did you choose MySQL over H2?**
**Answer**: MySQL provides data persistence, ACID compliance, and is production-ready. H2 is only suitable for development and testing. MySQL allows data to survive service restarts and provides better performance for large datasets.

---

## 🏆 **Key Achievements to Highlight**

1. **Professional Architecture**: Clean microservices design with proper separation of concerns
2. **Inter-Service Communication**: Robust communication between services with error handling
3. **Data Enrichment**: Services fetch and combine data from multiple sources
4. **Validation**: Cross-service validation ensures data integrity
5. **Scalability**: Each service can be scaled independently
6. **Maintainability**: Clean code with proper patterns and documentation
7. **Real-world Features**: Capacity management, registration system, statistics
8. **MySQL Integration**: Production-ready database with data persistence
9. **Service Discovery**: Eureka-based service registration and discovery
10. **API Gateway**: Centralized routing and load balancing

---

## 📝 **Quick Reference Commands**

```bash
# Start everything
start-all-services-final.bat

# Check ports
check-ports.bat

# Test services
test-all-services-final.bat

# View databases
view-databases.bat

# Manage MySQL
mysql-service-manager.bat

# Check Eureka
http://localhost:8761

# Test API Gateway
curl http://localhost:8080/clubs
```

---

## 🎯 **Postman Collection**

I've created a complete Postman collection (`postman-collection.json`) that includes:
- All CRUD operations for each service
- Inter-service communication examples
- API Gateway routing tests
- Error handling examples

**To use:**
1. Open Postman
2. Import the `postman-collection.json` file
3. Run the collection to test all endpoints

---

**Good luck with your viva! This system demonstrates modern microservices architecture with MySQL persistence, service discovery, and professional-grade features. Focus on explaining the architecture clearly and showing the inter-service communication smoothly.**