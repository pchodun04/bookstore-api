@echo off
cd /d "%~dp0"
call mvnw.cmd clean package
java -jar target\demo-0.0.1-SNAPSHOT.jar