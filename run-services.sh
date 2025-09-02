#!/bin/bash

echo "Starting all ClubConnect services..."

echo ""
echo "Starting Club Service on port 8081..."
gnome-terminal -- bash -c "cd club-service && mvn spring-boot:run; exec bash" &

echo ""
echo "Starting Member Service on port 8082..."
gnome-terminal -- bash -c "cd member-service && mvn spring-boot:run; exec bash" &

echo ""
echo "Starting Event Service on port 8083..."
gnome-terminal -- bash -c "cd event-service && mvn spring-boot:run; exec bash" &

echo ""
echo "Starting Registration Service on port 8084..."
gnome-terminal -- bash -c "cd registration-service && mvn spring-boot:run; exec bash" &

echo ""
echo "Starting Movie Service on port 9003..."
gnome-terminal -- bash -c "cd Movie && mvn spring-boot:run; exec bash" &

echo ""
echo "All services are starting..."
echo ""
echo "Service URLs:"
echo "- Club Service: http://localhost:8081"
echo "- Member Service: http://localhost:8082"
echo "- Event Service: http://localhost:8083"
echo "- Registration Service: http://localhost:8084"
echo "- Movie Service: http://localhost:9003"
echo ""
echo "Press Enter to exit..."
read
