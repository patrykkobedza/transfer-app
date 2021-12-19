# Welcome to transfer app

Application use in memory database for storing accounts,
which are wiped on each restart (data.sql script used to populate db).
In case of communication failure with external service that provides
exchange rates (which didn't occur even once while testing), 
internal exchange rates are used which are also stored in db.
Swagger-UI allows user friendly testing of app, also app console logs shows detailed info about each transfer.
 
 ## start app as standard spring boot app: `.\mvnw spring-boot:run`
 ## Swagger-UI available at: http://localhost:8080/swagger-ui.html
 ## You can access inmemory db at: http://localhost:8080/h2-console <br/> use JDBC URL: `jdbc:h2:mem:testdb` user: sa, leave password blank
 