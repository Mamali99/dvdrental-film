# Verwenden des Eclipse Temurin JRE-Images als Basis
FROM docker.io/library/eclipse-temurin:20-jre

# Setzen Sie das Arbeitsverzeichnis im Container
WORKDIR /usr/app

# Setzen von Umgebungsvariablen für die Datenbankverbindung
ENV POSTGRESQL_USER=postgres
ENV POSTGRESQL_PASSWORD=trust

# Kopieren des Bootable JAR-Files in den Docker-Container
COPY ./target/dvdrental-film-bootable.jar /usr/app/dvdrental-film-bootable.jar


# Expose Port 8081 für den Container
EXPOSE 8081

# Setzen der WildFly-Konfiguration, um auf alle Netzwerkschnittstellen zu hören und Port 8081 zu verwenden
CMD java -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0 -Djboss.http.port=8081 -jar /usr/app/dvdrental-film-bootable.jar -Dpostgresql.user=${POSTGRESQL_USER} -Dpostgresql.password=${POSTGRESQL_PASSWORD}

