FROM docker.io/library/postgres:15

# Setzen Sie die Locale für den Container
RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
ENV LANG de_DE.utf8

# Umgebungsvariablen für Postgres setzen
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=film

# Kopieren Sie die heruntergeladene SQL-Datei in den Docker-Container
COPY dvd-rental-film.sql /docker-entrypoint-initdb.d/

# Erstellen Sie ein Skript, das die Konfigurationsdatei anpasst und ausführbar macht
RUN echo '#!/bin/bash\nif [ -d "/var/lib/postgresql/data" ]; then\n  echo "port = 54321" >> /var/lib/postgresql/data/postgresql.conf\nfi' > /docker-entrypoint-initdb.d/update-postgresql-conf.sh
RUN chmod +x /docker-entrypoint-initdb.d/update-postgresql-conf.sh

# Expose the custom PostgreSQL port
EXPOSE 54321