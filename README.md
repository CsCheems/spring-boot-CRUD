Resumen:

Se desarrolló un sistema CRUD (Crear, Leer, Actualizar, Eliminar) en java para administrar Ciudadanos y Domicilios, se manejó la relación de ambas entidades de 1 a muchos (ciudadano(1) - domicilios(*)).

Características:

Desarrollado en IntelliJ IDEA
Maven
Spring Boot 3.2.5
Java 17
PostgreSQL

Propiedades y configuracion BD:

spring.application.name=springboot-app
server.port=9090

#Configuracion de la base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/ComputerFixers
spring.datasource.username=postgres
spring.datasource.password=99Thg94N3
spring.datasource.driver-class-name=org.postgresql.Driver

#Configuracion de JPA
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true




