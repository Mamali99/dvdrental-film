export postgresql_database=dvdrentalfilm
export postgresql_user=postgres
export postgresql_password=trust
export postgresql_datasource=PostgresDS
export postgresql_service_port=54321


java -Djboss.http.port=8081 -jar target/film-app-bootable.jar

