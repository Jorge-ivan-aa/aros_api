# Accrox Aros: Restaurant ordering system (API repository)

AROS es un sistema de gestion de pedidos diseñador para los restaurantes del siglo XXI. Su objetivo es optimizar tiempos de entrega, reducir costos operativos y aumentar la productividad del personal.


# Tecnologias
Al ser un producto empresarial, buscamos plataformas estables, escalables y con respaldo en el mercado. Por ello elegimos:

- Spring Boot (Java 21) como núcleo para el desarrollo de la API REST.
- Swagger / OpenAPI para la documentación interactiva de los endpoints.
- DB (9.4.0) como sistema gestor de bases de datos relacional.
- Docker como contenedor para la base de datos.


### Notas:

- El proyecto requiere las siguientes dependencias:
    1. TaskFile (>=3.45)
    2. Docker (>=28.5.0)
    3. JDK 21
- Antes de iniciar, se debe aplicar el comando `task up`.
- Para ejecutar el proyecto, se debe usar `task run`
- Despues de cualquier cambio en la base de datos, se recomienda usar el comando `task reset_db`.
