#!/bin/bash

export POSTGRES_HOST=localhost
export POSTGRES_PORT=5432
export POSTGRES_DB=dvdrentalfilm
export POSTGRES_USER=postgres
export POSTGRES_PASSWORD=trust


export HTTP_PORT=8081

mvn clean package

if [ -f "target/dvdrental-film-bootable.jar" ]; then
java -Djboss.http.port=${HTTP_PORT} \
     -Denv.POSTGRES_HOST=${POSTGRES_HOST} \
     -Denv.POSTGRES_PORT=${POSTGRES_PORT} \
     -Denv.POSTGRES_DB=${POSTGRES_DB} \
     -Denv.POSTGRES_USER=${POSTGRES_USER} \
     -Denv.POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
     -jar target/dvdrental-film-bootable.jar
else
    echo "Fehler beim Erstellen der JAR-Datei"
    exit 1
fi