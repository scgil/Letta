# LETTA: Let's Talk About… #

## Social Event Posting ##

The application is developed as a SPA using Java as the programming language for the business logic.

## Dependencies

This project is designed to be developed in an environment with:

* Maven 3
* Java 8
* MySQL 5.7.6+ o 8+

Additionally, it is recommended to use the latest version of Eclipse IDE for Enterprise Java Developers.

## Execution with Maven

The Maven configuration has been set up to allow various types of execution.

### Running the application with Tomcat and MySQL

The project is configured to run the application without any additional setup, except for having a MySQL server available locally.

The project files db/mysql.sql and 'db/mysql-with-inserts.sql' contain all the queries needed to create the required database and user, with or without sample data, respectively. Therefore, we can initially set up the database with either of the following commands (from the project root):

* Without data: `mysql -u root -p < db/mysql.sql`
* With data:: `mysql -u root -p < db/mysql-with-inserts.sql`

Once the database is set up, we can start the application with the command:

`mvn -Prun -DskipTests=true package cargo:run`

The application will be served at the local URL: http://localhost:9080/Letta

To stop the execution, you can use Ctrl+C.

### Running the application with Tomcat and MySQL with automatic redeployment

During development, it's useful to have the application automatically redeploy each time a change is made. To do this, you can use the following command:

mvn -Prun -DskipTests=true package cargo:start fizzed-watcher:run

The application will be served at the local URL: http://localhost:9080/Letta

To stop the execution, you can use Ctrl+C.

### Build with Unit and Integration Tests

In this build, all backend-related tests will be executed:

* **Unit**: used to test the entities and the DAO and REST layers in isolation.
* **Integration**: used to test the REST and DAO layers in an integrated manner. An in-memory HSQL database is used for this type of testing.

The command to start this build is:

`mvn install`

### Project Structure ###

This project is structured into two main directories with internal subdirectories:

* main: directory containing the code developed for the project.

* test: directory containing the tests developed for the project and the utilities used in them.

Inside **main**, it is important to note the division between two subdirectories, java and webapp.

* java: directory containing the code developed for the backend of the system (Java).
* webapp: directory containing the code developed for the frontend (JavaScript, HTML, and CSS), organized into several subdirectories.

Inside **java**, the following subdivisions relate to different parts of the backend:

* dao: directory containing the DAO (data access object) classes.
* entities: directory containing the domain classes (entities).
* rest: directory containing the developed REST service layer.

Inside **webapp**, the following subdivisions relate to different parts of the frontend:

* icons: directory containing the icons used in the application's view.
* images: directory containing event images.
* js: directory containing the application interface and the DAOs. It is subdivided into two directories: dao and view.
* WEB-INF: directory containing all the resources related to the web application.

Inside the js directory, there are two subdirectories:

* dao: directory containing the DAOs that connect the backend with the frontend.
* view: directory containing the application's interfaces.

