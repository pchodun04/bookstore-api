@echo off
cd /d "%~dp0"

echo Uruchamianie bazy danych w Dockerze
docker compose up -d

echo Kompilacja aplikacji
call mvnw.cmd clean package -DskipTests

echo Start Bookstore API
java -jar target\bookstore-api-0.0.1-SNAPSHOT.jar