# Complete MySQL Setup Guide for ClubConnect Microservices

## 🎯 **Overview**
This guide will help you set up MySQL databases for all microservices and ensure proper inter-service communication.

## 📊 **Databases to Create**

You need to create **4 separate databases** for each microservice:

1. **`memberdb`** - For Member Service (Port 8082)
2. **`clubdb`** - For Club Service (Port 8081)
3. **`eventdb`** - For Event Service (Port 8083)
4. **`registrationdb`** - For Registration Service (Port 8084)

## 🚀 **Step-by-Step MySQL Setup**

### **Step 1: Install MySQL Server**

#### **Windows:**
1. Download MySQL Community Server from: https://dev.mysql.com/downloads/mysql/
2. Run the installer
3. Set root password (remember this!)
4. Start MySQL service

#### **Verify Installation:**
```bash
# Open Command Prompt and test connection
mysql -u root -p
# Enter your root password when prompted
```

### **Step 2: Create Databases**

#### **Method 1: Using MySQL Command Line**
```bash
# Connect to MySQL as root
mysql -u root -p

# Create all databases
CREATE DATABASE memberdb;
CREATE DATABASE clubdb;
CREATE DATABASE eventdb;
CREATE DATABASE registrationdb;

# Verify databases were created
SHOW DATABASES;

# Exit MySQL
EXIT;
```

#### **Method 2: Using MySQL Workbench**
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Run this SQL script:
```sql
CREATE DATABASE IF NOT EXISTS memberdb;
CREATE DATABASE IF NOT EXISTS clubdb;
CREATE DATABASE IF NOT EXISTS eventdb;
CREATE DATABASE IF NOT EXISTS registrationdb;
```

### **Step 3: Create Application User (Optional but Recommended)**

```sql
-- Connect to MySQL as root
mysql -u root -p

-- Create user for the application
CREATE USER 'clubconnect'@'localhost' IDENTIFIED BY 'clubconnect123';

-- Grant privileges to all databases
GRANT ALL PRIVILEGES ON memberdb.* TO 'clubconnect'@'localhost';
GRANT ALL PRIVILEGES ON clubdb.* TO 'clubconnect'@'localhost';
GRANT ALL PRIVILEGES ON eventdb.* TO 'clubconnect'@'localhost';
GRANT ALL PRIVILEGES ON registrationdb.* TO 'clubconnect'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;

-- Verify user creation
SELECT User, Host FROM mysql.user WHERE User = 'clubconnect';

-- Exit MySQL
EXIT;
```

## 🔧 **Step 4: Update All Service Configurations**

### **4.1: Update Member Service**

#### **Update `member-service/pom.xml`:**
```xml
<!-- Remove H2 dependency -->
<!-- <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency> -->

<!-- Add MySQL dependency -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
    <scope>runtime</scope>
</dependency>
```

#### **Update `member-service/src/main/resources/application.properties`:**
```properties
spring.application.name=member-service
server.port=8082
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Database Configuration - MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/memberdb
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### **4.2: Update Club Service**

#### **Update `club-service/pom.xml`:**
```xml
<!-- Remove H2 dependency -->
<!-- <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency> -->

<!-- Add MySQL dependency -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
    <scope>runtime</scope>
</dependency>
```

#### **Update `club-service/src/main/resources/application.properties`:**
```properties
spring.application.name=club-service
server.port=8081
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Database Configuration - MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/clubdb
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### **4.3: Update Event Service**

#### **Update `event-service/pom.xml`:**
```xml
<!-- Remove H2 dependency -->
<!-- <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency> -->

<!-- Add MySQL dependency -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
    <scope>runtime</scope>
</dependency>
```

#### **Update `event-service/src/main/resources/application.properties`:**
```properties
spring.application.name=event-service
server.port=8083
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Database Configuration - MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/eventdb
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### **4.4: Update Registration Service**

#### **Update `registration-service/pom.xml`:**
```xml
<!-- Remove H2 dependency -->
<!-- <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency> -->

<!-- Add MySQL dependency -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
    <scope>runtime</scope>
</dependency>
```

#### **Update `registration-service/src/main/resources/application.properties`:**
```properties
spring.application.name=registration-service
server.port=8084
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Database Configuration - MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/registrationdb
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

## 🚀 **Step 5: Start All Services in Correct Order**

### **5.1: Start Eureka Server First**
```bash
# Terminal 1: Start Eureka Server
cd eureka-server
mvn spring-boot:run
```

**Wait for Eureka to start completely (about 30 seconds)**

### **5.2: Start All Microservices**
```bash
# Terminal 2: Start API Gateway
cd api-gateway
mvn spring-boot:run

# Terminal 3: Start Club Service
cd club-service
mvn spring-boot:run

# Terminal 4: Start Member Service
cd member-service
mvn spring-boot:run

# Terminal 5: Start Event Service
cd event-service
mvn spring-boot:run

# Terminal 6: Start Registration Service
cd registration-service
mvn spring-boot:run
```

### **5.3: Verify Services are Running**
```bash
# Check if ports are listening
netstat -an | findstr ":8761\|:8080\|:8081\|:8082\|:8083\|:8084"

# Check Eureka Dashboard
# Open browser: http://localhost:8761
```

## 🔗 **Step 6: Test Inter-Service Communication**

### **6.1: Test Individual Services**
```bash
# Test Club Service
curl http://localhost:8081/clubs

# Test Member Service
curl http://localhost:8082/members

# Test Event Service
curl http://localhost:8083/events

# Test Registration Service
curl http://localhost:8084/registrations
```

### **6.2: Test API Gateway Routing**
```bash
# Test routing through API Gateway
curl http://localhost:8080/clubs
curl http://localhost:8080/members
curl http://localhost:8080/events
curl http://localhost:8080/registrations
```

### **6.3: Test Inter-Service Communication**
```bash
# Create a club first
curl -X POST http://localhost:8081/clubs \
  -H "Content-Type: application/json" \
  -d '{"name":"Tech Club","description":"Technology enthusiasts","category":"Technology"}'

# Create a member (this will validate with Club Service)
curl -X POST http://localhost:8082/members \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice Smith","email":"alice@example.com","phone":"123-456-7890","clubName":"Tech Club"}'

# Create an event
curl -X POST http://localhost:8083/events \
  -H "Content-Type: application/json" \
  -d '{"name":"Java Workshop","description":"Learn Java","location":"Room 101","dateTime":"2025-09-20T10:00:00","clubName":"Tech Club","maxCapacity":30}'

# Register member for event
curl -X POST http://localhost:8084/registrations \
  -H "Content-Type: application/json" \
  -d '{"memberId":1,"eventId":1,"memberName":"Alice Smith","eventName":"Java Workshop"}'
```

## 🔍 **Step 7: Verify Database Tables Created**

### **Check Each Database:**
```sql
-- Connect to MySQL
mysql -u root -p

-- Check memberdb
USE memberdb;
SHOW TABLES;
DESCRIBE members;

-- Check clubdb
USE clubdb;
SHOW TABLES;
DESCRIBE clubs;

-- Check eventdb
USE eventdb;
SHOW TABLES;
DESCRIBE events;

-- Check registrationdb
USE registrationdb;
SHOW TABLES;
DESCRIBE registrations;
```

## 🎯 **Step 8: Complete Test Script**

Create a file `test-all-services.bat`:

```batch
@echo off
echo ========================================
echo    Testing All ClubConnect Services
echo ========================================
echo.

echo 1. Testing Eureka Dashboard
echo Open: http://localhost:8761
echo.

echo 2. Testing Club Service
curl http://localhost:8081/clubs
echo.
echo.

echo 3. Testing Member Service
curl http://localhost:8082/members
echo.
echo.

echo 4. Testing Event Service
curl http://localhost:8083/events
echo.
echo.

echo 5. Testing Registration Service
curl http://localhost:8084/registrations
echo.
echo.

echo 6. Testing API Gateway
curl http://localhost:8080/clubs
echo.
echo.

echo 7. Testing Inter-Service Communication
echo Creating club...
curl -X POST http://localhost:8081/clubs -H "Content-Type: application/json" -d "{\"name\":\"Tech Club\",\"description\":\"Technology enthusiasts\",\"category\":\"Technology\"}"
echo.
echo.

echo Creating member...
curl -X POST http://localhost:8082/members -H "Content-Type: application/json" -d "{\"name\":\"Alice Smith\",\"email\":\"alice@example.com\",\"phone\":\"123-456-7890\",\"clubName\":\"Tech Club\"}"
echo.
echo.

echo Creating event...
curl -X POST http://localhost:8083/events -H "Content-Type: application/json" -d "{\"name\":\"Java Workshop\",\"description\":\"Learn Java\",\"location\":\"Room 101\",\"dateTime\":\"2025-09-20T10:00:00\",\"clubName\":\"Tech Club\",\"maxCapacity\":30}"
echo.
echo.

echo Registering member for event...
curl -X POST http://localhost:8084/registrations -H "Content-Type: application/json" -d "{\"memberId\":1,\"eventId\":1,\"memberName\":\"Alice Smith\",\"eventName\":\"Java Workshop\"}"
echo.
echo.

echo ========================================
echo    All Tests Complete!
echo ========================================
echo.
pause
```

## 🚨 **Troubleshooting Common Issues**

### **Issue 1: Services Not Starting**
```bash
# Check if MySQL is running
net start mysql

# Check if ports are available
netstat -an | findstr ":3306\|:8761\|:8080\|:8081\|:8082\|:8083\|:8084"
```

### **Issue 2: Database Connection Failed**
```bash
# Test MySQL connection
mysql -u root -p

# Check if databases exist
SHOW DATABASES;

# Check user privileges
SELECT User, Host FROM mysql.user;
```

### **Issue 3: Services Not Registering with Eureka**
```bash
# Check Eureka server logs
# Ensure Eureka is running on port 8761
# Check service configuration files
```

### **Issue 4: Inter-Service Communication Failing**
```bash
# Check service discovery
# Verify service names in application.properties
# Check Eureka dashboard for registered services
```

## 🎓 **For Viva Presentation**

### **What to Demonstrate:**
1. **Database Creation**: Show all 4 databases created
2. **Service Startup**: Start services in correct order
3. **Eureka Registration**: Show services registered in Eureka dashboard
4. **CRUD Operations**: Demonstrate all CRUD operations work
5. **Inter-Service Communication**: Show services talking to each other
6. **Data Persistence**: Show data survives service restarts
7. **API Gateway**: Show routing through gateway

### **Key Points to Explain:**
- **Database per Service**: Each microservice has its own database
- **Service Discovery**: Eureka manages service registration and discovery
- **API Gateway**: Single entry point for all client requests
- **Inter-Service Communication**: Services communicate via HTTP/REST
- **Data Consistency**: Eventual consistency across services
- **Scalability**: Each service can be scaled independently

### **Commands to Remember:**
```bash
# Start services
cd eureka-server && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
cd club-service && mvn spring-boot:run
cd member-service && mvn spring-boot:run
cd event-service && mvn spring-boot:run
cd registration-service && mvn spring-boot:run

# Test services
curl http://localhost:8081/clubs
curl http://localhost:8082/members
curl http://localhost:8083/events
curl http://localhost:8084/registrations

# Check Eureka
http://localhost:8761
```

## 📊 **Database Schema Summary**

| Service | Database | Main Table | Key Fields |
|---------|----------|------------|------------|
| Club Service | clubdb | clubs | id, name, description, category |
| Member Service | memberdb | members | id, name, email, phone, clubName |
| Event Service | eventdb | events | id, name, description, location, dateTime, clubName |
| Registration Service | registrationdb | registrations | id, memberId, eventId, memberName, eventName |

## 🎯 **Success Criteria**

✅ All 4 databases created in MySQL  
✅ All 6 services start without errors  
✅ Services register with Eureka  
✅ API Gateway routes requests correctly  
✅ CRUD operations work on all services  
✅ Inter-service communication works  
✅ Data persists across service restarts  
✅ Eureka dashboard shows all services  

---

**Good luck with your viva! This setup demonstrates a complete microservices architecture with MySQL persistence and proper inter-service communication.**
