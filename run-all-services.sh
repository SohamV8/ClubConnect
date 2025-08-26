#!/bin/bash
echo "Starting all Club Management Services..."
echo

echo "Starting Club Service on port 8081..."
cd club-service && mvn spring-boot:run &
CLUB_PID=$!

echo "Starting Member Service on port 8082..."
cd ../member-service && mvn spring-boot:run &
MEMBER_PID=$!

echo "Starting Event Service on port 8083..."
cd ../event-service && mvn spring-boot:run &
EVENT_PID=$!

echo "Starting Registration Service on port 8084..."
cd ../registration-service && mvn spring-boot:run &
REGISTRATION_PID=$!

echo
echo "All services are starting..."
echo "Club Service: http://localhost:8081"
echo "Member Service: http://localhost:8082"
echo "Event Service: http://localhost:8083"
echo "Registration Service: http://localhost:8084"
echo
echo "Press Ctrl+C to stop all services..."

# Wait for all services
wait $CLUB_PID $MEMBER_PID $EVENT_PID $REGISTRATION_PID
