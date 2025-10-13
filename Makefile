# Variables globales
APP_NAME=api
JAR_FILE=target/$(APP_NAME).jar
MVN=./scripts/mvnw

# -----------------------------
# Comandos principales
# -----------------------------


run:
	$(MVN) spring-boot:run

test:
	$(MVN) test

clean:
	$(MVN) clean

# -----------------------------
# Docker & base de datos
# -----------------------------

up:
	docker compose up -d

down:
	docker compose down

reset_db:
	docker compose down -v 
	docker compose up -d
