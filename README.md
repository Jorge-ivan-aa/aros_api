# Accrox Aros: Restaurant ordering system (API repository)

AROS es un sistema de gestion de pedidos diseñador para los restaurantes del siglo XXI. Su objetivo es optimizar tiempos de entrega, reducir costos operativos y aumentar la productividad del personal.

# Tecnologias

Al ser un producto empresarial, buscamos plataformas estables, escalables y con respaldo en el mercado. Por ello elegimos:

- Spring Boot (Java 21) como núcleo para el desarrollo de la API REST.
- Swagger / OpenAPI para la documentación interactiva de los endpoints.
- MariaDB (9.4.0) como sistema gestor de bases de datos relacional.
- Docker como gestor de contenedores.

## 🚀 Inicio Rápido

### Requisitos Previos

- **JDK 21** ✅
- **Maven 3.x** ✅
- **Docker & Docker Compose** ✅
- **TaskFile** (>=3.45) - Opcional

### Instalación y Ejecución

1. **Clonar el repositorio**

   ```bash
   git clone <repository-url>
   cd aros_api
   ```

2. **Configurar variables de entorno**

   ```bash
   cp .env.example .env
   # Edita .env con tus credenciales
   ```

3. **Iniciar la base de datos**

   ```bash
   docker-compose up -d
   ```

4. **Ejecutar la aplicación**

   **Opción A - Script automático (Recomendado):**

   ```bash
   ./run.sh
   ```

   **Opción B - Con Maven:**

   ```bash
   mvn spring-boot:run
   ```

   **Opción C - Con Taskfile:**

   ```bash
   task up    # Primera vez
   task run   # Ejecutar
   ```

5. **Acceder a la aplicación**
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - API Docs: http://localhost:8080/v3/api-docs

### Comandos Útiles

```bash
# Con Taskfile
task up          # Iniciar base de datos
task run         # Ejecutar aplicación
task reset_db    # Reiniciar base de datos

# Sin Taskfile
docker-compose up -d              # Iniciar DB
./run.sh                          # Ejecutar app
mvn test                          # Ejecutar tests
mvn clean package -DskipTests     # Compilar
```

📖 **Para más detalles**, consulta [EJECUCION.md](./EJECUCION.md)
