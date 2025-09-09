# ClubConnect Microservices - Complete Project Structure Guide

## 🎯 **Project Overview**

**ClubConnect** is a microservices-based club management system that demonstrates modern software architecture patterns. It consists of 6 services working together to provide a complete club management solution.

---

## 📁 **Project Folder Structure**

```
ClubConnect/
├── 📁 eureka-server/           # Service Discovery Server
├── 📁 api-gateway/             # API Gateway & Load Balancer
├── 📁 club-service/            # Club Management Service
├── 📁 member-service/          # Member Management Service
├── 📁 event-service/           # Event Management Service
├── 📁 registration-service/    # Registration Management Service
├── 📄 README-VIVA.md           # Viva Presentation Guide
├── 📄 QUICK-START-GUIDE.md     # Quick Start Instructions
├── 📄 postman-collection.json  # API Testing Collection
└── 📄 Various .bat files       # Automation Scripts
```

---

## 🔧 **Service Details & Roles**

### **1. Eureka Server (Port 8761)**
**Location**: `eureka-server/`
**Role**: Service Discovery & Registration Center

**What it does**:
- Registers all microservices when they start
- Provides service discovery for inter-service communication
- Monitors health of all services
- Acts as a phone book for services

**Key Files**:
- `EurekaServerApplication.java` - Main application class
- `application.properties` - Server configuration

**How to show to teacher**:
1. Start the service: `cd eureka-server && mvn spring-boot:run`
2. Open browser: http://localhost:8761
3. Show the dashboard with registered services
4. Explain: "This is like a phone book for all our services"

### **2. API Gateway (Port 8080)**
**Location**: `api-gateway/`
**Role**: Single Entry Point & Load Balancer

**What it does**:
- Acts as the front door for all client requests
- Routes requests to appropriate microservices
- Provides load balancing
- Handles cross-cutting concerns (logging, authentication)

**Key Files**:
- `ApiGatewayApplication.java` - Main application class
- `application.properties` - Routing configuration

**How to show to teacher**:
1. Show how all requests go through port 8080
2. Demonstrate routing: `curl http://localhost:8080/clubs`
3. Explain: "Instead of clients knowing all service ports, they just talk to the gateway"

### **3. Club Service (Port 8081)**
**Location**: `club-service/`
**Role**: Club Management & Data Orchestration

**What it does**:
- Manages club information (name, description, category)
- Fetches member and event counts from other services
- Provides enriched club data with statistics
- Acts as a central reference for club validation

**Key Files**:
- `ClubServiceApplication.java` - Main application class
- `ClubController.java` - REST API endpoints
- `EnhancedClubService.java` - Business logic with inter-service communication
- `ClubDTO.java` - Data Transfer Object
- `Club.java` - Entity model
- `ClubRepository.java` - Database operations

**Database**: `clubdb` (MySQL)
**Main Table**: `clubs`

**How to show to teacher**:
1. Show CRUD operations: `curl http://localhost:8081/clubs`
2. Show enriched data with member/event counts
3. Explain: "This service manages clubs and fetches data from other services"

### **4. Member Service (Port 8082)**
**Location**: `member-service/`
**Role**: Member Management & Club Validation

**What it does**:
- Manages member information (name, email, phone, club)
- Validates club existence before creating members
- Provides member statistics and club-based filtering
- Enriches member data with club details

**Key Files**:
- `MemberServiceApplication.java` - Main application class
- `MemberController.java` - REST API endpoints
- `EnhancedMemberService.java` - Business logic with club validation
- `MemberDTO.java` - Data Transfer Object
- `Member.java` - Entity model
- `MemberRepository.java` - Database operations

**Database**: `memberdb` (MySQL)
**Main Table**: `members`

**How to show to teacher**:
1. Show member creation with club validation
2. Show club-based filtering: `curl http://localhost:8082/members/club/Tech%20Club`
3. Explain: "This service validates that clubs exist before creating members"

### **5. Event Service (Port 8083)**
**Location**: `event-service/`
**Role**: Event Management & Capacity Control

**What it does**:
- Manages events (name, description, location, date, capacity)
- Controls event capacity and prevents overbooking
- Validates club existence for events
- Provides upcoming events filtering
- Manages event registrations

**Key Files**:
- `EventServiceApplication.java` - Main application class
- `EventController.java` - REST API endpoints
- `EnhancedEventService.java` - Business logic with capacity management
- `EventDTO.java` - Data Transfer Object
- `Event.java` - Entity model
- `EventRepository.java` - Database operations

**Database**: `eventdb` (MySQL)
**Main Table**: `events`

**How to show to teacher**:
1. Show event creation with capacity management
2. Show upcoming events: `curl http://localhost:8083/events/upcoming`
3. Explain: "This service manages events and prevents overbooking"

### **6. Registration Service (Port 8084)**
**Location**: `registration-service/`
**Role**: Member-Event Linking & Validation

**What it does**:
- Links members to events (registrations)
- Validates both member and event existence
- Prevents duplicate registrations
- Manages registration status
- Updates event capacity when registrations change

**Key Files**:
- `RegistrationServiceApplication.java` - Main application class
- `RegistrationController.java` - REST API endpoints
- `EnhancedRegistrationService.java` - Business logic with validation
- `RegistrationDTO.java` - Data Transfer Object
- `Registration.java` - Entity model
- `RegistrationRepository.java` - Database operations

**Database**: `registrationdb` (MySQL)
**Main Table**: `registrations`

**How to show to teacher**:
1. Show registration creation with validation
2. Show member-event linking
3. Explain: "This service connects members to events and validates everything"

---

## 🔗 **Service Communication Flow**

### **How Services Talk to Each Other**

```
Client Request → API Gateway → Target Service → Other Services (if needed) → Database
```

### **Example: Creating a Member**

1. **Client** sends POST request to API Gateway (port 8080)
2. **API Gateway** routes request to Member Service (port 8082)
3. **Member Service** validates club exists by calling Club Service (port 8081)
4. **Club Service** checks database and returns club details
5. **Member Service** creates member in its database
6. **Member Service** returns enriched member data to API Gateway
7. **API Gateway** returns response to client

### **Example: Getting Club Statistics**

1. **Client** sends GET request to API Gateway
2. **API Gateway** routes to Club Service
3. **Club Service** fetches club data from its database
4. **Club Service** calls Member Service to get member count
5. **Club Service** calls Event Service to get event count
6. **Club Service** combines all data and returns enriched response

---

## 🗄️ **Database Architecture**

### **Database per Service Pattern**

Each microservice has its own MySQL database:

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

### **How to Show Databases to Teacher**

1. **Using Command Line**:
   ```bash
   # View all databases
   mysql -u root -p2004 -e "SHOW DATABASES;"
   
   # View specific data
   mysql -u root -p2004 -e "USE memberdb; SELECT * FROM members;"
   ```

2. **Using MySQL Workbench**:
   - Open MySQL Workbench
   - Connect to localhost:3306
   - Username: root, Password: 2004
   - Browse the 4 databases

3. **Using our script**:
   ```bash
   view-databases.bat
   ```

---

## 🚀 **How to Start Everything**

### **Step 1: Start MySQL**
```bash
# Method 1: Using our script
mysql-service-manager.bat

# Method 2: Manual
net start mysql
```

### **Step 2: Start All Services**
```bash
# One command startup
start-all-services-final.bat

# This will:
# 1. Create MySQL databases
# 2. Start Eureka Server
# 3. Start API Gateway
# 4. Start all microservices
```

### **Step 3: Verify Everything**
```bash
# Check all ports
check-ports.bat

# Test all services
test-all-services-final.bat
```

---

## 🎓 **How to Demonstrate to Teacher**

### **1. Architecture Overview (2 minutes)**
- Show the project structure
- Explain microservices concept
- Show the architecture diagram

### **2. Service Discovery (2 minutes)**
- Open Eureka Dashboard: http://localhost:8761
- Show registered services
- Explain service discovery concept

### **3. Database per Service (2 minutes)**
- Show MySQL databases
- Explain why each service has its own database
- Show data persistence

### **4. API Gateway (2 minutes)**
- Show how all requests go through port 8080
- Demonstrate routing to different services
- Explain load balancing

### **5. Inter-Service Communication (5 minutes)**
- Create a club
- Create a member (shows club validation)
- Create an event
- Register member for event
- Show how services talk to each other

### **6. CRUD Operations (3 minutes)**
- Show Create, Read, Update, Delete for each service
- Show data enrichment
- Show error handling

### **7. Advanced Features (2 minutes)**
- Show capacity management
- Show statistics
- Show data consistency

---

## 📊 **Key Files and Their Purposes**

### **Configuration Files**
- `application.properties` - Service configuration (ports, database, Eureka)
- `pom.xml` - Maven dependencies and build configuration

### **Main Application Classes**
- `*ServiceApplication.java` - Spring Boot main class for each service
- `@SpringBootApplication` - Enables auto-configuration

### **Controller Classes**
- `*Controller.java` - REST API endpoints
- `@RestController` - Handles HTTP requests
- `@RequestMapping` - Maps URLs to methods

### **Service Classes**
- `Enhanced*Service.java` - Business logic and inter-service communication
- `@Service` - Spring service component
- Contains methods for CRUD operations and service calls

### **Repository Classes**
- `*Repository.java` - Database operations
- `JpaRepository` - Provides CRUD operations
- Custom query methods for specific needs

### **Model Classes**
- `*.java` - Entity models for database
- `@Entity` - JPA entity
- `@Id`, `@GeneratedValue` - Primary key configuration

### **DTO Classes**
- `*DTO.java` - Data Transfer Objects
- Clean API contracts
- Enriched data from multiple services

### **Configuration Classes**
- `ServiceConfig.java` - Bean configuration
- `DataInitializer.java` - Sample data loading

---

## 🔍 **Service Communication Examples**

### **Example 1: Member Service → Club Service**
```java
// In EnhancedMemberService.java
public MemberDTO createMember(MemberDTO memberDTO) {
    // Validate club exists
    ClubDTO club = getClubByName(memberDTO.getClubName());
    if (club == null) {
        throw new RuntimeException("Club not found");
    }
    // Create member...
}
```

### **Example 2: Club Service → Member Service**
```java
// In EnhancedClubService.java
public ClubDTO getClubById(Long id) {
    // Get club from database
    Club club = clubRepository.findById(id).orElse(null);
    
    // Get member count from Member Service
    List<MemberDTO> members = getMembersByClub(club.getName());
    
    // Enrich club data
    clubDTO.setMemberCount(members.size());
    return clubDTO;
}
```

---

## 🎯 **Key Concepts to Explain**

### **1. Microservices Architecture**
- **Independence**: Each service can be developed, deployed, and scaled independently
- **Technology Diversity**: Each service can use different technologies
- **Fault Isolation**: If one service fails, others continue working

### **2. Service Discovery**
- **Eureka Server**: Central registry for all services
- **Dynamic Registration**: Services register themselves when they start
- **Health Monitoring**: Eureka monitors service health

### **3. API Gateway**
- **Single Entry Point**: All client requests go through one place
- **Load Balancing**: Distributes requests across service instances
- **Cross-Cutting Concerns**: Handles logging, authentication, monitoring

### **4. Database per Service**
- **Data Independence**: Each service owns its data
- **Technology Choice**: Each service can use different databases
- **Scalability**: Each database can be scaled independently

### **5. Inter-Service Communication**
- **HTTP/REST**: Services communicate via HTTP APIs
- **Service Discovery**: Services find each other by name
- **Error Handling**: Graceful degradation when services fail

---

## 🚨 **Troubleshooting Guide**

### **Common Issues**

1. **Services Not Starting**
   - Check if MySQL is running
   - Check if ports are available
   - Check service configuration

2. **Services Not Registering with Eureka**
   - Start Eureka Server first
   - Check Eureka configuration
   - Check service names

3. **Database Connection Failed**
   - Check MySQL service
   - Check password (2004)
   - Check database names

4. **Inter-Service Communication Failing**
   - Check service discovery
   - Check service names
   - Check Eureka dashboard

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

## 🎓 **Viva Presentation Tips**

### **What to Show First**
1. **Project Structure** - Show the folder organization
2. **Eureka Dashboard** - Show service registration
3. **Database View** - Show data persistence
4. **API Gateway** - Show routing
5. **Inter-Service Communication** - Show services talking to each other

### **Key Points to Emphasize**
- **Scalability**: Each service can be scaled independently
- **Maintainability**: Clean separation of concerns
- **Fault Tolerance**: System continues working if one service fails
- **Data Consistency**: Eventual consistency across services
- **Production Ready**: MySQL integration and proper error handling

### **Technical Terms to Use**
- Microservices Architecture
- Service Discovery (Eureka)
- API Gateway Pattern
- Database per Service
- Inter-Service Communication
- Load Balancing
- Fault Tolerance
- Data Enrichment
- DTO Pattern
- Repository Pattern

---

**This project demonstrates a complete microservices architecture with proper service communication, data persistence, and professional-grade features. Good luck with your viva!** 🎉
