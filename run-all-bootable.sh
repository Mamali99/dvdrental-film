#!/bin/bash

# Setze das Basisverzeichnis als das übergeordnete Verzeichnis des dvdrental-film Projekts
BASE_DIR=$(dirname "$0")
# Gehe in das Customer-Projektverzeichnis und führe Maven aus
#echo "Baue Customer-Projekt..."
#(cd "$BASE_DIR/../dvdrental-customer" && mvn clean package)

# Gehe in das Store-Projektverzeichnis und führe Maven aus
#echo "Baue Store-Projekt..."
#(cd "$BASE_DIR/../dvdrental-store" && mvn clean package)

# Gehe in das Film-Projektverzeichnis und führe Maven aus
#echo "Baue Film-Projekt..."
#(cd "$BASE_DIR" && mvn clean package)

# Baue die Images für die anderen Projekte
podman build -t ftse/customer-db:15 "$BASE_DIR/../dvdrental-customer/dockerDB"
podman build -t ftse/customer-app "$BASE_DIR/../dvdrental-customer/"

podman build -t ftse/store-db:15 "$BASE_DIR/../dvdrental-store/dockerDB"
podman build -t ftse/store-app "$BASE_DIR/../dvdrental-store/"

# Baue die Images für das Film-Projekt
podman build -t ftse/film-db:15 dockerDB
podman build -t ftse/film-app .

# Erstelle den Pod mit den benötigten Ports
podman pod create --name mein-pod --publish 8081:8081 --publish 8082:8082 --publish 8083:8083

# Starte die Container für die Datenbanken
podman run --pod mein-pod -d --name film-db ftse/film-db:15
podman run --pod mein-pod -d --name store-db ftse/store-db:15
podman run --pod mein-pod -d --name customer-db ftse/customer-db:15


echo "Warte auf die Initialisierung der Datenbank film-db..."
while ! podman exec film-db pg_isready -h localhost -p 54321 > /dev/null 2>&1; do
    sleep 5
done

echo "Warte auf die Initialisierung der Datenbank store-db..."
while ! podman exec store-db pg_isready -h localhost -p 54322 > /dev/null 2>&1; do
    sleep 5
done

echo "Warte auf die Initialisierung der Datenbank customer-db..."
while ! podman exec customer-db pg_isready -h localhost -p 54323 > /dev/null 2>&1; do
    sleep 5
done

echo "Alle Datenbanken sind initialisiert."

# Starte die Container für die Anwendungen
podman run --pod mein-pod -d --name film-app ftse/film-app
podman run --pod mein-pod -d --name store-app ftse/store-app
podman run --pod mein-pod -d --name customer-app ftse/customer-app

echo "Alle Anwendungen sind gestartet."
