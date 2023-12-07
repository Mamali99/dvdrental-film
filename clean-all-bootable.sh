#!/bin/bash

# Stoppe und entferne alle Container im mein-pod
echo "Stoppe alle Container im mein-pod..."
podman pod stop mein-pod
podman pod rm mein-pod

# Entferne alle custom Images, die mit diesem Projekt assoziiert sind
echo "Entferne alle ftse/* Images..."
podman rmi ftse/film-app:latest
podman rmi ftse/film-db:15
podman rmi ftse/store-app:latest
podman rmi ftse/store-db:15
podman rmi ftse/customer-app:latest
podman rmi ftse/customer-db:15

# Entferne das Basis-Postgres-Image
echo "Entferne das PostgreSQL-Image..."
podman rmi docker.io/library/postgres:15
podman rmi docker.io/library/eclipse-temurin:20-jre



echo "Aufräumen abgeschlossen."
