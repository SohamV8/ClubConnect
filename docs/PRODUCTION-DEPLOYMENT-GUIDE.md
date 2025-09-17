# 🚀 ClubConnect Production Deployment Guide

This guide provides comprehensive instructions for deploying the ClubConnect microservices application in a production environment.

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Environment Setup](#environment-setup)
3. [Configuration Management](#configuration-management)
4. [Database Setup](#database-setup)
5. [Service Deployment](#service-deployment)
6. [Monitoring & Logging](#monitoring--logging)
7. [Security Configuration](#security-configuration)
8. [Scaling & Performance](#scaling--performance)
9. [Backup & Recovery](#backup--recovery)
10. [Troubleshooting](#troubleshooting)

## 🔧 Prerequisites

### System Requirements
- **OS**: Linux (Ubuntu 20.04+ recommended) or Windows Server 2019+
- **CPU**: 4+ cores
- **RAM**: 8GB+ (16GB recommended)
- **Storage**: 100GB+ SSD
- **Network**: Stable internet connection

### Software Requirements
- **Java**: OpenJDK 17+
- **Maven**: 3.9.4+
- **Docker**: 20.10+
- **Docker Compose**: 2.0+
- **MySQL**: 8.0+
- **Git**: 2.30+

### External Services
- **Git Repository**: For configuration management
- **Monitoring**: Prometheus + Grafana (optional)
- **Logging**: ELK Stack (optional)
- **Load Balancer**: Nginx or HAProxy (optional)

## 🌍 Environment Setup

### 1. Environment Variables

Create a `.env` file in the project root:

```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=3306
DB_USERNAME=root
DB_PASSWORD=your_secure_password
DB_NAME_PREFIX=clubconnect

# Config Server
CONFIG_REPO_URL=https://github.com/your-org/clubconnect-config-repo
CONFIG_BRANCH=main
CONFIG_USERNAME=admin
CONFIG_PASSWORD=your_secure_config_password
ENCRYPT_KEY=your_32_character_encryption_key

# Eureka Server
EUREKA_URL=http://localhost:8761/eureka/

# Service Configuration
SPRING_PROFILES_ACTIVE=prod
ZONE=production
REGION=us-east-1

# Security
JWT_SECRET=your_jwt_secret_key
CORS_ALLOWED_ORIGINS=https://yourdomain.com

# Monitoring
PROMETHEUS_ENABLED=true
GRAFANA_ENABLED=true
```

### 2. Git Repository Setup

Create a Git repository for configuration management:

```bash
# Create repository structure
mkdir clubconnect-config-repo
cd clubconnect-config-repo
git init

# Create directory structure
mkdir -p config/environments/prod
mkdir -p config/environments/staging
mkdir -p config/environments/dev

# Copy configuration files
cp -r ../config-repo/config/* config/

# Commit and push
git add .
git commit -m "Initial configuration"
git remote add origin https://github.com/your-org/clubconnect-config-repo.git
git push -u origin main
```

## 🗄️ Database Setup

### 1. MySQL Production Configuration

Create `/etc/mysql/mysql.conf.d/mysqld.cnf`:

```ini
[mysqld]
# Basic Settings
user = mysql
default-storage-engine = InnoDB
socket = /var/run/mysqld/mysqld.sock
pid-file = /var/run/mysqld/mysqld.pid

# Network Settings
bind-address = 0.0.0.0
port = 3306
max_connections = 200
max_connect_errors = 1000

# Buffer Settings
innodb_buffer_pool_size = 2G
innodb_log_file_size = 256M
innodb_log_buffer_size = 16M
innodb_flush_log_at_trx_commit = 2

# Query Cache
query_cache_type = 1
query_cache_size = 64M
query_cache_limit = 2M

# Logging
log-error = /var/log/mysql/error.log
slow_query_log = 1
slow_query_log_file = /var/log/mysql/slow.log
long_query_time = 2

# Security
local-infile = 0
```

### 2. Database Initialization

```bash
# Start MySQL
sudo systemctl start mysql
sudo systemctl enable mysql

# Create databases
mysql -u root -p < setup-mysql-databases.sql

# Create production user
mysql -u root -p << EOF
CREATE USER 'clubconnect'@'%' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON clubdb.* TO 'clubconnect'@'%';
GRANT ALL PRIVILEGES ON memberdb.* TO 'clubconnect'@'%';
GRANT ALL PRIVILEGES ON eventdb.* TO 'clubconnect'@'%';
GRANT ALL PRIVILEGES ON registrationdb.* TO 'clubconnect'@'%';
FLUSH PRIVILEGES;
EOF
```

## 🚀 Service Deployment

### Option 1: Docker Compose (Recommended)

```bash
# Build and start all services
docker-compose -f docker-compose.prod.yml up -d

# Check service status
docker-compose -f docker-compose.prod.yml ps

# View logs
docker-compose -f docker-compose.prod.yml logs -f
```

### Option 2: Manual Deployment

```bash
# Start services in order
./start-production.bat

# Or manually:
# 1. Config Server
cd config-server && mvn spring-boot:run -Dspring.profiles.active=prod

# 2. Eureka Server
cd eureka-server && mvn spring-boot:run

# 3. API Gateway
cd api-gateway && mvn spring-boot:run

# 4. Microservices
cd club-service && mvn spring-boot:run -Dspring.profiles.active=prod
cd member-service && mvn spring-boot:run -Dspring.profiles.active=prod
cd event-service && mvn spring-boot:run -Dspring.profiles.active=prod
cd registration-service && mvn spring-boot:run -Dspring.profiles.active=prod
```

## 📊 Monitoring & Logging

### 1. Health Checks

All services expose health endpoints:

```bash
# Check service health
curl http://localhost:8080/actuator/health
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health
curl http://localhost:8084/actuator/health
curl http://localhost:8888/actuator/health
```

### 2. Metrics Collection

Services expose Prometheus metrics:

```bash
# View metrics
curl http://localhost:8081/actuator/prometheus
curl http://localhost:8082/actuator/prometheus
curl http://localhost:8083/actuator/prometheus
curl http://localhost:8084/actuator/prometheus
```

### 3. Log Management

Logs are written to:
- Console output
- `logs/{service-name}.log` files
- Docker logs (if using containers)

## 🔒 Security Configuration

### 1. Network Security

```bash
# Configure firewall
sudo ufw enable
sudo ufw allow 22/tcp    # SSH
sudo ufw allow 80/tcp    # HTTP
sudo ufw allow 443/tcp   # HTTPS
sudo ufw allow 8080/tcp  # API Gateway
sudo ufw allow 8761/tcp  # Eureka
sudo ufw allow 8888/tcp  # Config Server
```

### 2. SSL/TLS Configuration

Create SSL certificates:

```bash
# Generate self-signed certificate (for testing)
openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -nodes

# Or use Let's Encrypt (for production)
sudo apt install certbot
sudo certbot certonly --standalone -d yourdomain.com
```

### 3. Service Security

Each service has built-in security:
- Basic authentication
- CORS configuration
- Rate limiting
- Input validation

## 📈 Scaling & Performance

### 1. Horizontal Scaling

Scale services using Docker Compose:

```bash
# Scale specific service
docker-compose -f docker-compose.prod.yml up -d --scale club-service=3

# Scale all services
docker-compose -f docker-compose.prod.yml up -d --scale club-service=2 --scale member-service=2 --scale event-service=2 --scale registration-service=2
```

### 2. Load Balancing

Configure Nginx for load balancing:

```nginx
upstream clubconnect {
    server localhost:8080;
    server localhost:8080 backup;
}

server {
    listen 80;
    server_name yourdomain.com;
    
    location / {
        proxy_pass http://clubconnect;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

### 3. Database Optimization

```sql
-- Create indexes for better performance
CREATE INDEX idx_members_club_id ON members(club_id);
CREATE INDEX idx_events_club_id ON events(club_id);
CREATE INDEX idx_registrations_member_id ON registrations(member_id);
CREATE INDEX idx_registrations_event_id ON registrations(event_id);

-- Optimize tables
OPTIMIZE TABLE clubs, members, events, registrations;
```

## 💾 Backup & Recovery

### 1. Database Backup

```bash
# Create backup script
#!/bin/bash
BACKUP_DIR="/backup/mysql"
DATE=$(date +%Y%m%d_%H%M%S)

mkdir -p $BACKUP_DIR

# Backup all databases
mysqldump -u root -p --all-databases > $BACKUP_DIR/all_databases_$DATE.sql

# Backup specific databases
mysqldump -u root -p clubdb > $BACKUP_DIR/clubdb_$DATE.sql
mysqldump -u root -p memberdb > $BACKUP_DIR/memberdb_$DATE.sql
mysqldump -u root -p eventdb > $BACKUP_DIR/eventdb_$DATE.sql
mysqldump -u root -p registrationdb > $BACKUP_DIR/registrationdb_$DATE.sql

# Compress backups
gzip $BACKUP_DIR/*_$DATE.sql

# Clean old backups (keep 30 days)
find $BACKUP_DIR -name "*.sql.gz" -mtime +30 -delete
```

### 2. Configuration Backup

```bash
# Backup configuration repository
git clone https://github.com/your-org/clubconnect-config-repo.git
tar -czf config-backup-$(date +%Y%m%d).tar.gz clubconnect-config-repo/
```

### 3. Recovery Procedures

```bash
# Restore database
mysql -u root -p < backup_file.sql

# Restore configuration
tar -xzf config-backup-YYYYMMDD.tar.gz
cd clubconnect-config-repo
git push origin main
```

## 🔧 Troubleshooting

### Common Issues

1. **Service Not Starting**
   ```bash
   # Check logs
   docker-compose logs service-name
   
   # Check health
   curl http://localhost:port/actuator/health
   ```

2. **Database Connection Issues**
   ```bash
   # Test connection
   mysql -h localhost -u root -p
   
   # Check MySQL status
   sudo systemctl status mysql
   ```

3. **Config Server Issues**
   ```bash
   # Check config server
   curl http://localhost:8888/actuator/health
   
   # Test configuration endpoint
   curl http://localhost:8888/club-service/prod
   ```

4. **Eureka Registration Issues**
   ```bash
   # Check Eureka dashboard
   curl http://localhost:8761
   
   # Check service registration
   curl http://localhost:8761/eureka/apps
   ```

### Performance Issues

1. **High Memory Usage**
   - Increase JVM heap size: `-Xmx2g -Xms1g`
   - Optimize database queries
   - Enable connection pooling

2. **Slow Response Times**
   - Check database performance
   - Enable caching
   - Optimize network configuration

3. **High CPU Usage**
   - Profile application
   - Optimize algorithms
   - Scale horizontally

## 📞 Support

For production support:
- **Documentation**: [CONFIG-SERVER-README.md](CONFIG-SERVER-README.md)
- **Issues**: Create GitHub issues
- **Monitoring**: Check service health endpoints
- **Logs**: Review application logs

## 🎯 Production Checklist

- [ ] Environment variables configured
- [ ] Database setup and secured
- [ ] SSL certificates installed
- [ ] Firewall configured
- [ ] Monitoring enabled
- [ ] Backup procedures tested
- [ ] Load balancing configured
- [ ] Security measures implemented
- [ ] Performance testing completed
- [ ] Disaster recovery plan in place

---

**Note**: This guide assumes a Linux production environment. Adjust commands for Windows Server as needed.
