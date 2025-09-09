-- MySQL Database Setup for ClubConnect Microservices
-- Run this script in MySQL to create the required databases

-- Create databases for each microservice
CREATE DATABASE IF NOT EXISTS memberdb;
CREATE DATABASE IF NOT EXISTS clubdb;
CREATE DATABASE IF NOT EXISTS eventdb;
CREATE DATABASE IF NOT EXISTS registrationdb;

-- Create a user for the application (optional, you can use root)
CREATE USER IF NOT EXISTS 'clubconnect'@'localhost' IDENTIFIED BY 'clubconnect123';
GRANT ALL PRIVILEGES ON memberdb.* TO 'clubconnect'@'localhost';
GRANT ALL PRIVILEGES ON clubdb.* TO 'clubconnect'@'localhost';
GRANT ALL PRIVILEGES ON eventdb.* TO 'clubconnect'@'localhost';
GRANT ALL PRIVILEGES ON registrationdb.* TO 'clubconnect'@'localhost';
FLUSH PRIVILEGES;

-- Show created databases
SHOW DATABASES;

-- Show user privileges
SELECT User, Host FROM mysql.user WHERE User = 'clubconnect';