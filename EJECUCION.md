# 🚀 Guía de Ejecución - AROS API

## Requisitos Previos

- Java 21 ✅
- Maven 3.x ✅
- Docker y Docker Compose ✅
- MariaDB 11.2 (mediante Docker) ✅

## Configuración Inicial

### 1. Configurar variables de entorno

Copia el archivo de ejemplo y configura tus credenciales:

```bash
cp .env.example .env
```

Edita `.env` con tus valores:

```bash
# Database Configuration
DB_NAME=aros_db
DB_ROOT_PASSWORD=tu_password_seguro
DB_PORT=3301
DB_HOST=localhost

# Application Security
ADMIN_DOCUMENT=tu_email@ejemplo.com
ADMIN_PASSWORD_HASH='$2a$10$...'  # Hash bcrypt de tu password
TOKEN_SECRET=tu_secret_seguro_aqui

# Application Configuration
LOGGING_LEVEL_ROOT=WARN
LOGGING_LEVEL_APP=INFO
```

### 2. Iniciar la base de datos

```bash
docker-compose up -d
```

Verificar que esté corriendo:

```bash
docker ps | grep aros_db
```

## Formas de Ejecutar la Aplicación

### Opción 1: Script automático (Recomendado) 🌟

```bash
./run.sh
```

Este script:

- Verifica que la base de datos esté corriendo
- Compila la aplicación
- Ejecuta el JAR generado

### Opción 2: Maven (Modo desarrollo)

```bash
mvn spring-boot:run
```

⚠️ **Nota**: Esta opción incluye spring-boot-devtools que causa reinicios automáticos al detectar cambios.

### Opción 3: JAR directo (Modo producción)

```bash
# Compilar
mvn clean package -DskipTests

# Ejecutar
java -jar target/aros-api-0.1.jar
```

### Opción 4: Con tests

```bash
mvn clean install
mvn spring-boot:run
```

## Verificar que la aplicación esté corriendo

### Endpoints disponibles:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **Health Check**: http://localhost:8080/actuator/health (si está habilitado)

### Probar con curl:

```bash
# Verificar que el servidor responde
curl http://localhost:8080/api/v1/auth/health

# O cualquier endpoint público
curl http://localhost:8080/swagger-ui/index.html
```

## Solución de Problemas Comunes

### ❌ Error: "Unable to connect to database"

**Solución**: Verifica que Docker esté corriendo y que la base de datos esté activa:

```bash
docker-compose up -d
docker logs aros_db
```

### ❌ Error: "Port 8080 already in use"

**Solución**: Detén el proceso que está usando el puerto:

```bash
# Encontrar el proceso
lsof -i :8080

# O detener la aplicación anterior
pkill -f "aros-api"
```

### ❌ Aplicación se reinicia continuamente

**Solución**: Esto es por spring-boot-devtools. Puedes:

1. Usar `./run.sh` en lugar de `mvn spring-boot:run`
2. O desactivar devtools en `application.properties`:
   ```properties
   spring.devtools.restart.enabled=false
   ```

### ❌ Error: "Cannot find .env file"

**Solución**: Copia el archivo de ejemplo:

```bash
cp .env.example .env
# Luego edita .env con tus valores
```

## Detener la Aplicación

### Si usaste `./run.sh` o `java -jar`:

```bash
# Presiona Ctrl+C en la terminal
```

### Si está en background:

```bash
pkill -f "aros-api"
```

### Detener la base de datos:

```bash
docker-compose down
```

## Comandos Útiles

```bash
# Ver logs de la base de datos
docker logs -f aros_db

# Reiniciar la base de datos
docker-compose restart

# Limpiar y reconstruir
mvn clean install

# Ejecutar tests
mvn test

# Ejecutar tests de un archivo específico
mvn test -Dtest=LoginTokenUseCaseTest
```

## Estructura del Proyecto

```
aros_api/
├── src/
│   ├── main/
│   │   ├── java/accrox/aros/api/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── db/migration/        # Flyway migrations
│   │       └── data/                # Datos iniciales
│   └── test/
├── target/                          # Archivos compilados
├── .env                            # Variables de entorno (no versionado)
├── .env.example                    # Plantilla de variables
├── compose.yml                     # Docker Compose config
├── pom.xml                         # Maven config
└── run.sh                          # Script de ejecución
```

## Estado Actual ✅

Tu aplicación está funcionando correctamente:

- ✅ Compilación exitosa
- ✅ Conexión a base de datos funcional
- ✅ API respondiendo en puerto 8080
- ✅ Swagger UI disponible
- ✅ Tests pasando

---

**¿Necesitas ayuda?** Revisa los logs en la terminal o contacta al equipo de desarrollo.
