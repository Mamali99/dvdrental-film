# Verwenden des Eclipse Temurin JRE-Images als Basis
FROM docker.io/library/eclipse-temurin:20-jre

# Setzen Sie das Arbeitsverzeichnis im Container
WORKDIR /usr/app

# Standardwerte für Umgebungsvariablen festlegen
ENV POSTGRES_HOST=localhost
ENV POSTGRES_PORT=54321
ENV POSTGRES_DB=dvdrentalfilm
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=trust

# Kopieren des Bootable JAR-Files in den Docker-Container
COPY ./target/dvdrental-film-bootable.jar /usr/app/dvdrental-film-bootable.jar

# Expose Port 8081 für den Container
EXPOSE 8081

# Setzen der WildFly-Konfiguration, um auf alle Netzwerkschnittstellen zu hören und Port 8081 zu verwenden
CMD java -Djboss.http.port=8081 \
     -Djboss.bind.address=0.0.0.0 \
     -Djboss.bind.address.management=0.0.0.0 \
     -Dpostgresql.host=${POSTGRES_HOST} \
     -Dpostgresql.port=${POSTGRES_PORT} \
     -Dpostgresql.database=${POSTGRES_DB} \
     -Dpostgresql.user=${POSTGRES_USER} \
     -Dpostgresql.password=${POSTGRES_PASSWORD} \
     -jar /usr/app/dvdrental-film-bootable.jar
