#!/bin/bash

# Script para ejecutar la aplicación Spring Boot de AROS

echo "🚀 Iniciando AROS API..."
echo ""

# Verificar si la base de datos está corriendo
if ! sudo docker ps | grep -q aros_db; then
    echo "⚠️  La base de datos no está corriendo. Iniciando..."
    sudo docker-compose up -d
    echo "⏳ Esperando que la base de datos esté lista..."
    sleep 5
else
    echo "✅ Base de datos corriendo"
fi

# Verificar archivo .env
if [ ! -f .env ]; then
    echo "❌ Error: No se encontró el archivo .env"
    echo "   Copia .env.example a .env y configura las variables"
    exit 1
fi

echo "📦 Compilando aplicación..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Compilación exitosa"
    echo "🌐 Iniciando servidor..."
    echo ""
    echo "   La aplicación estará disponible en: http://localhost:8080"
    echo "   Swagger UI: http://localhost:8080/swagger-ui/index.html"
    echo ""
    echo "   Presiona Ctrl+C para detener"
    echo ""
    
    java -jar target/aros-api-0.1.jar
else
    echo "❌ Error en la compilación"
    exit 1
fi
