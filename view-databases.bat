@echo off
echo ========================================
echo    ClubConnect MySQL Database Viewer
echo ========================================
echo.

echo Opening MySQL Command Line...
echo Password: 2004
echo.

echo 1. Viewing all databases...
mysql -u root -p2004 -e "SHOW DATABASES;"
echo.

echo 2. Viewing memberdb.members table...
mysql -u root -p2004 -e "USE memberdb; SELECT * FROM members;"
echo.

echo 3. Viewing clubdb.clubs table...
mysql -u root -p2004 -e "USE clubdb; SELECT * FROM clubs;"
echo.

echo 4. Viewing eventdb.events table...
mysql -u root -p2004 -e "USE eventdb; SELECT * FROM events;"
echo.

echo 5. Viewing registrationdb.registrations table...
mysql -u root -p2004 -e "USE registrationdb; SELECT * FROM registrations;"
echo.

echo ========================================
echo    Database View Complete
echo ========================================
echo.
echo To open MySQL Workbench for GUI access:
echo 1. Open MySQL Workbench
echo 2. Connect to localhost:3306
echo 3. Username: root, Password: 2004
echo 4. Browse the databases: memberdb, clubdb, eventdb, registrationdb
echo.
pause
