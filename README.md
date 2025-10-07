# Accrox Aros: Restaurant orders system (API repository)

AROS es un sistema de gestion de pedidos para los restaurantes del siglo XXI. Su objetivo es optimizar tiempos de entrega, reducir costos operativos y aumentar la productividad del personal.


# Tecnologias
Al ser un producto empresarial, buscamos plataformas estables, escalables y con respaldo en el mercado. Por ello elegimos:

- Spring Boot (Java 21) como núcleo para el desarrollo de la API REST.
- Swagger / OpenAPI para la documentación interactiva de los endpoints.
- MySQL (9.4.0) como sistema gestor de bases de datos relacional.
- Docker como contenedor para la base de datos.


### Notas:

- El proyecto requiere las siguientes dependencias:
    1. Maven
    2. Docker (>=28.5.0)

- Antes de iniciar, se debe aplicar el comando `docker compose up -d` y verificar que el contenedor este ejecutandose correctamente, con `docker ps`.

- Para ejecutar el proyecto con el wraper, el comando a utilizar es `./mvnw spring-boot:run`

