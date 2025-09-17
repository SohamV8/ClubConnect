# Spring Cloud Config Server Integration

This document describes the Spring Cloud Config Server integration for the ClubConnect microservices project.

## 🎯 Overview

The Config Server provides centralized configuration management for all microservices, allowing you to:
- Store configuration files in a Git repository
- Manage environment-specific configurations
- Refresh configurations without restarting services
- Version control your configuration changes

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Config Server │    │  Eureka Server  │    │  API Gateway    │
│   (Port 8888)   │◄───┤   (Port 8761)   │◄───┤   (Port 8080)   │
│  Configuration  │    │ Service Discovery│    │ Load Balancer   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  Club Service   │    │ Member Service  │    │ Event Service   │
│   (Port 8081)   │    │   (Port 8082)   │    │   (Port 8083)   │
│  Gets Config    │    │  Gets Config    │    │  Gets Config    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 ▼
                    ┌─────────────────┐
                    │Registration Svc │
                    │   (Port 8084)   │
                    │  Gets Config    │
                    └─────────────────┘
```

## 🚀 Quick Start

### 1. Start Services in Order

```bash
# Start Config Server first
start-with-config-server.bat
```

Or manually:
```bash
# 1. Config Server (Port 8888)
cd config-server && mvn spring-boot:run

# 2. Eureka Server (Port 8761)
cd eureka-server && mvn spring-boot:run

# 3. API Gateway (Port 8080)
cd api-gateway && mvn spring-boot:run

# 4. All Microservices (Ports 8081-8084)
cd club-service && mvn spring-boot:run
cd member-service && mvn spring-boot:run
cd event-service && mvn spring-boot:run
cd registration-service && mvn spring-boot:run
```

### 2. Test Configuration

```bash
# Run the test script
test-config-server.bat
```

## 📁 Project Structure

```
ClubConnect/
├── config-server/                 # Config Server
│   ├── src/main/java/
│   │   └── com/example/configserver/
│   │       └── ConfigServerApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
├── config-repo/                   # Configuration Repository
│   └── config/
│       ├── club-service.yml
│       ├── member-service.yml
│       ├── event-service.yml
│       └── registration-service.yml
├── club-service/
│   └── src/main/resources/
│       ├── bootstrap.yml          # Config Client bootstrap
│       └── application.properties # Local overrides
├── member-service/
│   └── src/main/resources/
│       ├── bootstrap.yml
│       └── application.properties
├── event-service/
│   └── src/main/resources/
│       ├── bootstrap.yml
│       └── application.properties
├── registration-service/
│   └── src/main/resources/
│       ├── bootstrap.yml
│       └── application.properties
├── start-with-config-server.bat
├── test-config-server.bat
└── CONFIG-SERVER-README.md
```

## 🔧 Configuration

### Config Server (application.yml)

```yaml
server:
  port: 8888

spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        native:
          search-locations: classpath:/config,file:./config,file:./config-repo
```

### Client Services (bootstrap.yml)

```yaml
spring:
  application:
    name: club-service
  cloud:
    config:
      uri: http://localhost:8888
      profile: default
      label: main
      fail-fast: true
```

## 🌐 API Endpoints

### Config Server Endpoints

| Endpoint | Description | Example |
|----------|-------------|---------|
| `/{application}/{profile}` | Get config for application and profile | `/club-service/default` |
| `/{application}/{profile}/{label}` | Get config with specific label | `/club-service/default/main` |
| `/{application}-{profile}.yml` | Get config as YAML | `/club-service-default.yml` |
| `/{application}-{profile}.properties` | Get config as Properties | `/club-service-default.properties` |
| `/actuator/health` | Health check | `/actuator/health` |

### Example Requests

```bash
# Get club service configuration
curl http://localhost:8888/club-service/default

# Get member service configuration as YAML
curl http://localhost:8888/member-service-default.yml

# Get event service configuration as Properties
curl http://localhost:8888/event-service-default.properties
```

## 🔄 Dynamic Configuration Refresh

### 1. Enable Refresh Endpoint

Add `@RefreshScope` to your configuration classes:

```java
@RestController
@RefreshScope
public class ClubController {
    @Value("${club.service.name}")
    private String serviceName;
    
    // ... rest of the controller
}
```

### 2. Refresh Configuration

```bash
# Refresh configuration for a specific service
curl -X POST http://localhost:8081/actuator/refresh

# Refresh all services
curl -X POST http://localhost:8081/actuator/refresh
curl -X POST http://localhost:8082/actuator/refresh
curl -X POST http://localhost:8083/actuator/refresh
curl -X POST http://localhost:8084/actuator/refresh
```

## 🏭 Production Setup

### 1. Git Repository Configuration

Update `config-server/src/main/resources/application.yml`:

```yaml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/your-org/clubconnect-config-repo
          default-label: main
          clone-on-start: true
          force-pull: true
          search-paths:
            - config
            - application-configs
```

### 2. Environment-Specific Configurations

Create environment-specific files:
- `club-service-dev.yml` (Development)
- `club-service-prod.yml` (Production)
- `club-service-test.yml` (Testing)

### 3. Security Configuration

Add security for production:

```yaml
spring:
  security:
    user:
      name: admin
      password: ${CONFIG_SERVER_PASSWORD:admin123}
```

## 🐛 Troubleshooting

### Common Issues

1. **Config Server Not Starting**
   - Check if port 8888 is available
   - Verify Java 17 is installed
   - Check Maven dependencies

2. **Client Services Can't Connect**
   - Ensure Config Server is running first
   - Check bootstrap.yml configuration
   - Verify network connectivity

3. **Configuration Not Loading**
   - Check config file names match application names
   - Verify file format (YAML/Properties)
   - Check Config Server logs

### Debug Commands

```bash
# Check Config Server health
curl http://localhost:8888/actuator/health

# Check specific service configuration
curl http://localhost:8888/club-service/default

# Check service logs
tail -f logs/club-service.log
```

## 📊 Monitoring

### Health Checks

- Config Server: `http://localhost:8888/actuator/health`
- Service Health: `http://localhost:8081/actuator/health`

### Metrics

- Config Server Metrics: `http://localhost:8888/actuator/metrics`
- Service Metrics: `http://localhost:8081/actuator/metrics`

## 🔗 Useful Links

- [Spring Cloud Config Documentation](https://spring.io/projects/spring-cloud-config)
- [Config Server Reference](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)
- [Client Configuration](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_spring_cloud_config_client)

## 📝 Notes

- Config Server must be started before client services
- Configuration files are loaded in this order: bootstrap.yml → application.yml → config server
- Use `@RefreshScope` for dynamic configuration updates
- For production, use Git repository instead of local files
- Consider using Spring Cloud Bus for distributed configuration refresh
