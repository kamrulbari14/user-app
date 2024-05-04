# User Management System

This project is a user management system developed with Spring Boot.

## Features

- CRUD operations for parents and children users
- Integration with a MySQL database
- Validation of user data
    - A Parent user must have an Address
    - The child cannot have an address and must belong to a Parent
- RESTful API endpoints for managing users
    - Delete user data (with an option to hard delete or soft delete)
    - Create user data
    - Update user data
    - Get user data by ID and
    - Get all users' data by Active status

## Technologies Used

The project utilizes the following technologies and frameworks:

- **Spring Boot**: Java-based framework.
- **Spring Data JPA**: Part of the larger Spring Data project, used for easy implementation of
  JPA-based repositories.
- **MySQL**: Relational database management system used for storing user data.
- **JUnit 5**: Testing framework used for writing and executing unit tests.
- **Mockito**: Java mocking framework used for unit tests.
- **Testcontainers**: Library used for integration testing with Docker containers.
- **ModelMapper**: Java library used for object mapping.
- **Lombok**: Java library used to reduce boilerplate code in entities and DTOs.
- **Jackson**: JSON library used for data binding.
- **Jakarta Persistence API (JPA)**: Java API for managing relational data in Java applications.
- **Maven**: Build automation tool used for managing dependencies and building the project.
- **Git**: Version control system used for tracking changes in the project codebase.

## Entity Relationships

- Each Parent can have multiple Children.
- Each Child belongs to exactly one Parent.
- Each Parent has an Address associated with them.

## Endpoints

The following endpoints are available in the API:

### Parent

- **GET /api/v1/parents?status={ACTIVE/DELETE}**: Retrieve a list of all parent users. If
  the `status` parameter is set to `ACTIVE`, the active user list will be retrieved. If it's set
  to `DELETE` then all the soft deleted user list will be retrieved.
- **GET /api/v1/parents/{userId}**: Retrieve a specific parent user by ID.
- **POST /api/v1/parents**: Create a new parent user.
- **PUT /api/v1/parents/{userId}**: Update an existing parent user.
- **DELETE /api/v1/parents/{userId}?permanent={true/false}**: Delete a parent user. If
  the `permanent` parameter is set to `true`, the user will be permanently deleted from the system.

### Child

- **GET /api/v1/child?status={ACTIVE/DELETE}**: Retrieve a list of all child users. If the `status`
  parameter is set to `ACTIVE`, the active user list will be retrieved. If it's set to `DELETE` then
  all the soft deleted user list will be retrieved.
- **GET /api/v1/child/{userId}**: Retrieve a specific child user by ID.
- **POST /api/v1/child**: Create a new child user.
- **PUT /api/v1/child/{userId}**: Update an existing child user.
- **DELETE /api/v1/child/{userId}?permanent={true/false}**: Delete a child user. If the
  `permanent` parameter is set to `true`, the user will be permanently deleted from the system.

***N.B:*** You can skip the `permanent` and `status` parameter too. By default `permanent` set to
`false` and `status` set to `ACTIVE`.

### Request Body Documentation

##### Create Parent User (POST /api/v1/parents)

**Request Body**:
```json
{
  "firstName": "Kamrul",
  "lastName": "Bari",
  "address": {
    "street": "Road-2",
    "city": "Dhaka",
    "state": "Bangladesh",
    "zip": "1207"
  }
}
```

##### Create Child User (POST /api/v1/child)

**Request Body**:
```json
{
  "firstName": "Child",
  "lastName": "One",
  "parent": {
    "id": 10
  }
}
```

### Error Handling

The API follows RESTful conventions for error responses. In case of any errors, appropriate HTTP
status codes and error messages are returned. Details of the error handling can be found in the
response section of each endpoint.

## Prerequisites

Before running the project, ensure you have the following installed:

- Java Development Kit (JDK) 21
- Maven (Optional, you can run with Maven wrapper too)
- MySQL
- Docker (for running integration test with ***testcontainers***)

## Running the Project

**Most importantly run `docker` on your pc before running the project because some command will 
run testcases, and they will need `docker`.**

1. Clone the repository:

```bash
git clone https://github.com/kamrulbari14/user-app.git
```

2. Edit the ***application.properties*** file according your **MySQL** environment
    - Open the file ***user-app/src/main/resources/application.properties***
    - Change the port in ***spring.datasource.url*** if your mysql is running on different port
    - Change the ***spring.datasource.username*** and ***spring.datasource.password*** according to
      your local MySQL setup
    - Save the changes

3. Now open any ***command prompt*** or ***git bash*** and go to the project directory which is
   user-app. Edit ***/path/to/directory*** according yours.

```bash
cd /path/to/directory/user-app
```

4. Build the project using Maven Wrapper:

```bash
./mvnw clean package
```

5. Run the project using Maven Wrapper: (Also you can run the project in any **IDE**)

```bash
./mvnw spring-boot:run
```

#### N.B: The application will start running on http://localhost:9091. (You can change the port in ***application.properties*** if this port is occupied by any service in your PC)

## Running the Tests

```bash
./mvnw test
```

## Contributors

- [Kamrul Bari](https://github.com/kamrulbari14)
