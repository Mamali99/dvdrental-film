# Verwenden des Eclipse Temurin JRE-Images als Basis
FROM eclipse-temurin:20-jre

# Setzen Sie das Arbeitsverzeichnis im Container
WORKDIR /usr/app

# Setzen von Umgebungsvariablen für die Datenbankverbindung
ENV POSTGRESQL_USER=postgres
ENV POSTGRESQL_PASSWORD=film


# Kopieren des Bootable JAR-Files in den Docker-Container
# Stellen Sie sicher, dass das JAR-File unter target/ROOT-bootable.jar liegt
COPY ./target/ROOT-bootable.jar /usr/app/ROOT-bootable.jar

# Expose Port 8081 für den Container
EXPOSE 8081

# Führen Sie den Bootable JAR aus und konfigurieren Sie den WildFly-Server so, dass er auf Port 8081 lauscht
# und geben Sie die Datenbankverbindungsinformationen als Systemeigenschaften weiter
CMD ["java", "-jar", "/usr/app/ROOT-bootable.jar", \
     "-Djboss.http.port=8081", \
     "-Dpostgresql.user=${POSTGRESQL_USER}", \
     "-Dpostgresql.password=${POSTGRESQL_PASSWORD}"]
