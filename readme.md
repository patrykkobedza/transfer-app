##Welcome to transfer app

Application use in memory database for storing accounts,
 which are wiped on each restart (data.sql script used to populate db).
 In case of communication failure with external service that provides
 exchange rates (which didn't occur even once while testing), 
 internal exchange rates are used which are also stored in db.
 Swagger-UI allows user friendly testing of app, also app logs shows detailed info about each transfer.
 
 ##Swagger-UI available at http://localhost:8080/swagger-ui.html
 
 ## start app as standard spring boot app: <br/>.\mvnw spring-boot:run 