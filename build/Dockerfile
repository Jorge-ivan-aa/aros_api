FROM ubuntu/jre:21-24.04

WORKDIR /app
COPY aros-api-0.1.jar aros-api-0.1.jar
EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "aros-api-0.1.jar"]
