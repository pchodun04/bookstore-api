@echo off
cd /d "%~dp0"

echo [1/3] Uruchamianie bazy danych w Dockerze...
docker compose -f ..\docker-compose.yml up -d

echo [2/3] Kompilacja aplikacji...
call mvnw.cmd clean package -DskipTests

echo [3/3] Start Bookstore API...
java -jar target\bookstore-api-0.0.1-SNAPSHOT.jar