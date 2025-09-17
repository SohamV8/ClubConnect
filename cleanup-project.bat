@echo off
echo ==========================================
echo ClubConnect Project Cleanup
echo ==========================================
echo.

echo Step 1: Cleaning Maven target directories...
for /d %%d in (*-service config-server eureka-server api-gateway) do (
    if exist "%%d\target" (
        echo Cleaning %%d\target...
        rmdir /s /q "%%d\target"
    )
)

echo.
echo Step 2: Removing temporary files...
del /q /s *.tmp 2>nul
del /q /s *.log 2>nul
del /q /s *.bak 2>nul
del /q /s *.swp 2>nul
del /q /s *~ 2>nul

echo.
echo Step 3: Removing IDE files...
del /q /s .idea 2>nul
del /q /s .vscode 2>nul
del /q /s .settings 2>nul
del /q /s .project 2>nul
del /q /s .classpath 2>nul
del /q /s *.iml 2>nul

echo.
echo Step 4: Removing OS files...
del /q /s Thumbs.db 2>nul
del /q /s .DS_Store 2>nul
del /q /s desktop.ini 2>nul

echo.
echo Step 5: Creating .gitignore file...
(
echo # Maven
echo target/
echo pom.xml.tag
echo pom.xml.releaseBackup
echo pom.xml.versionsBackup
echo pom.xml.next
echo release.properties
echo dependency-reduced-pom.xml
echo buildNumber.properties
echo .mvn/timing.properties
echo .mvn/wrapper/maven-wrapper.jar
echo.
echo # IDE
echo .idea/
echo *.iml
echo .vscode/
echo .settings/
echo .project
echo .classpath
echo.
echo # OS
echo Thumbs.db
echo .DS_Store
echo desktop.ini
echo.
echo # Logs
echo *.log
echo logs/
echo.
echo # Temporary files
echo *.tmp
echo *.bak
echo *.swp
echo *~
echo.
echo # Spring Boot
echo application-*.properties
echo application-*.yml
echo !application.properties
echo !application.yml
echo.
echo # Database
echo *.db
echo *.sqlite
echo.
echo # Docker
echo .dockerignore
echo.
echo # Environment
echo .env
echo .env.local
echo .env.production
echo.
echo # Node modules (if any)
echo node_modules/
echo npm-debug.log*
echo yarn-debug.log*
echo yarn-error.log*
) > .gitignore

echo.
echo Step 6: Organizing documentation...
if not exist "docs" mkdir docs
move CONFIG-SERVER-README.md docs\ 2>nul
move PRODUCTION-DEPLOYMENT-GUIDE.md docs\ 2>nul
move SERVICE-STATUS-SUMMARY.md docs\ 2>nul

echo.
echo Step 7: Organizing scripts...
if not exist "scripts" mkdir scripts
move start-production.bat scripts\ 2>nul
move start-with-config-server.bat scripts\ 2>nul
move test-all-services.bat scripts\ 2>nul
move test-config-server.bat scripts\ 2>nul
move test-production.bat scripts\ 2>nul
move cleanup-project.bat scripts\ 2>nul

echo.
echo Step 8: Organizing deployment files...
if not exist "deployment" mkdir deployment
move docker-compose.prod.yml deployment\ 2>nul
move setup-mysql-databases.sql deployment\ 2>nul

echo.
echo Step 9: Organizing API documentation...
if not exist "api-docs" mkdir api-docs
move postman-collection.json api-docs\ 2>nul

echo.
echo Step 10: Creating project structure documentation...
(
echo # ClubConnect Microservices Project Structure
echo.
echo ## 📁 Project Organization
echo.
echo ### Core Services
echo - `api-gateway/` - Spring Cloud Gateway for routing and load balancing
echo - `eureka-server/` - Service discovery and registration
echo - `config-server/` - Centralized configuration management
echo - `club-service/` - Club management microservice
echo - `member-service/` - Member management microservice
echo - `event-service/` - Event management microservice
echo - `registration-service/` - Registration management microservice
echo.
echo ### Configuration
echo - `config-repo/` - Configuration repository for Config Server
echo   - `config/` - Service-specific configuration files
echo.
echo ### Documentation
echo - `docs/` - Project documentation
echo   - `CONFIG-SERVER-README.md` - Config Server setup guide
echo   - `PRODUCTION-DEPLOYMENT-GUIDE.md` - Production deployment guide
echo   - `SERVICE-STATUS-SUMMARY.md` - Service status and troubleshooting
echo.
echo ### Scripts
echo - `scripts/` - Automation scripts
echo   - `start-production.bat` - Production startup script
echo   - `test-all-services.bat` - Service testing script
echo   - `cleanup-project.bat` - Project cleanup script
echo.
echo ### Deployment
echo - `deployment/` - Deployment configurations
echo   - `docker-compose.prod.yml` - Production Docker Compose
echo   - `setup-mysql-databases.sql` - Database setup script
echo.
echo ### API Documentation
echo - `api-docs/` - API documentation and collections
echo   - `postman-collection.json` - Postman API collection
echo.
echo ## 🚀 Quick Start
echo.
echo 1. **Start all services:**
echo    ```bash
echo    scripts\start-production.bat
echo    ```
echo.
echo 2. **Test all services:**
echo    ```bash
echo    scripts\test-all-services.bat
echo    ```
echo.
echo 3. **Clean project:**
echo    ```bash
echo    scripts\cleanup-project.bat
echo    ```
echo.
echo ## 📋 Service Ports
echo.
echo - Config Server: 8888
echo - Eureka Server: 8761
echo - API Gateway: 8080
echo - Club Service: 8081
echo - Member Service: 8082
echo - Event Service: 8083
echo - Registration Service: 8084
echo.
echo ## 🔧 Development
echo.
echo Each service is a standalone Spring Boot application with:
echo - Maven build configuration (`pom.xml`)
echo - Docker support (`Dockerfile`)
echo - Configuration files (`application.properties`, `bootstrap.yml`)
echo - Source code in `src/main/java/`
echo.
echo ## 📚 Documentation
echo.
echo See `docs/` directory for detailed documentation on:
echo - Config Server setup and usage
echo - Production deployment procedures
echo - Service status and troubleshooting
) > PROJECT-STRUCTURE.md

echo.
echo ==========================================
echo Project Cleanup Complete!
echo ==========================================
echo.
echo ✅ Removed Maven target directories
echo ✅ Removed temporary files
echo ✅ Removed IDE files
echo ✅ Removed OS files
echo ✅ Created .gitignore file
echo ✅ Organized documentation into docs/
echo ✅ Organized scripts into scripts/
echo ✅ Organized deployment files into deployment/
echo ✅ Organized API docs into api-docs/
echo ✅ Created project structure documentation
echo.
echo 📁 New Project Structure:
echo - docs/ - Documentation
echo - scripts/ - Automation scripts
echo - deployment/ - Deployment configurations
echo - api-docs/ - API documentation
echo - config-repo/ - Configuration repository
echo - [service-name]/ - Individual microservices
echo.
echo 🚀 Next Steps:
echo 1. Run: scripts\start-production.bat
echo 2. Test: scripts\test-all-services.bat
echo 3. Read: docs\PRODUCTION-DEPLOYMENT-GUIDE.md
echo.
pause
