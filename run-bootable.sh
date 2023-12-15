#!/bin/bash

export POSTGRES_HOST=localhost
export POSTGRES_PORT=54321
export POSTGRES_DB=dvdrentalfilm
export POSTGRES_USER=postgres
export POSTGRES_PASSWORD=trust

# Setze den Port, auf dem der Service laufen soll
export HTTP_PORT=8081

java -Djboss.http.port=${HTTP_PORT} \
     -Denv.POSTGRES_HOST=${POSTGRES_HOST} \
     -Denv.POSTGRES_PORT=${POSTGRES_PORT} \
     -Denv.POSTGRES_DB=${POSTGRES_DB} \
     -Denv.POSTGRES_USER=${POSTGRES_USER} \
     -Denv.POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
     -jar target/dvdrental-film-bootable.jar
