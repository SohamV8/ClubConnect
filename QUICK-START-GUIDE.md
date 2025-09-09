# Quick Start Guide - ClubConnect with MySQL

## 🎯 **Your Configuration**
- **MySQL Password**: 2004
- **Databases**: memberdb, clubdb, eventdb, registrationdb
- **Services**: 6 microservices + Eureka + API Gateway

## 🚀 **Quick Start Commands**

### **Step 1: Start MySQL Server**
```bash
# Windows (if MySQL is installed as service)
net start mysql

# Or start MySQL service from Services.msc
```

### **Step 2: Create Databases and Start All Services**
```bash
# Run this single command to do everything
start-all-services-final.bat
```

### **Step 3: Test Everything**
```bash
# Run this to test all services
test-all-services-final.bat
```

## 🔍 **Manual Commands (if needed)**

### **Create Databases Only**
```bash
create-databases.bat
```

### **Start Services Individually**
```bash
# Terminal 1: Eureka Server
cd eureka-server && mvn spring-boot:run

# Terminal 2: API Gateway
cd api-gateway && mvn spring-boot:run

# Terminal 3: Club Service
cd club-service && mvn spring-boot:run

# Terminal 4: Member Service
cd member-service && mvn spring-boot:run

# Terminal 5: Event Service
cd event-service && mvn spring-boot:run

# Terminal 6: Registration Service
cd registration-service && mvn spring-boot:run
```

### **Test Individual Services**
```bash
# Test Club Service
curl http://localhost:8081/clubs

# Test Member Service
curl http://localhost:8082/members

# Test Event Service
curl http://localhost:8083/events

# Test Registration Service
curl http://localhost:8084/registrations

# Test API Gateway
curl http://localhost:8080/clubs
```

## 🎓 **For Viva Presentation**

### **What to Show:**
1. **Run `start-all-services-final.bat`** - Show automated startup
2. **Open Eureka Dashboard** - http://localhost:8761
3. **Run `test-all-services-final.bat`** - Show all tests working
4. **Check MySQL databases** - Show data persistence

### **Key Points to Explain:**
- **Database per Service**: Each microservice has its own MySQL database
- **Service Discovery**: Eureka manages service registration
- **API Gateway**: Single entry point for all requests
- **Inter-Service Communication**: Services communicate via HTTP/REST
- **Data Persistence**: Data survives service restarts

### **Service URLs:**
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Club Service: http://localhost:8081
- Member Service: http://localhost:8082
- Event Service: http://localhost:8083
- Registration Service: http://localhost:8084

## 🚨 **Troubleshooting**

### **If MySQL Connection Fails:**
```bash
# Test MySQL connection
mysql -u root -p2004 -e "SELECT VERSION();"

# Check if MySQL is running
net start mysql
```

### **If Services Don't Start:**
```bash
# Check if ports are available
netstat -an | findstr ":3306\|:8761\|:8080\|:8081\|:8082\|:8083\|:8084"
```

### **If Services Don't Register with Eureka:**
```bash
# Check Eureka server logs
# Ensure Eureka is running on port 8761
# Check service configuration files
```

## 📊 **Database Schema**

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
