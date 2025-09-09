@echo off
echo ========================================
echo    Creating MySQL Databases
echo ========================================
echo.

"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p2004 -e "CREATE DATABASE IF NOT EXISTS clubdb;"
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p2004 -e "CREATE DATABASE IF NOT EXISTS memberdb;"
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p2004 -e "CREATE DATABASE IF NOT EXISTS eventdb;"
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p2004 -e "CREATE DATABASE IF NOT EXISTS registrationdb;"

echo.
echo ========================================
echo    Verifying Databases
echo ========================================
echo.

"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p2004 -e "SHOW DATABASES;"

echo.
echo ========================================
echo    Database Creation Complete
echo ========================================
pause
