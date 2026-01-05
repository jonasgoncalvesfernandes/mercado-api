@echo off
title Subindo projeto Spring Boot + Docker

echo ===============================
echo Subindo containers Docker...
echo ===============================
docker-compose up -d

IF %ERRORLEVEL% NEQ 0 (
    echo Erro ao subir o Docker
    pause
    exit /b
)

echo ===============================
echo Subindo Spring Boot...
echo ===============================
call gradlew.bat bootRun

pause
