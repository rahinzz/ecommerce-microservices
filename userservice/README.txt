===========================================
              USER SERVICE
===========================================

A Spring Boot microservice responsible for 
managing user operations such as creating 
users, fetching user details, and listing 
all users.

This service follows clean architecture 
principles with proper validation, 
exception handling, and controller-level 
testing.

This service is part of a larger modular 
microservices architecture under the main 
'project' folder.

-------------------------------------------
1. TECHNOLOGY STACK
-------------------------------------------
- Java 21
- Spring Boot 3.4.12
- Spring Web
- Spring Validation (Jakarta Validation)
- Spring Data JPA
- Hibernate
- H2 In-Memory Database
- JUnit 5
- Mockito
- MockMvc
- Lombok (Optional)
- Swagger/OpenAPI
- Maven

-------------------------------------------
2. PROJECT STRUCTURE
-------------------------------------------

userservice
│
├── src/main/java/com/project/userservice
│   ├── UserserviceApplication.java
│   │
│   ├── constants
│   │     └── AppConstants.java
│   │
│   ├── config
│   │     └── SwaggerConfig.java
│   │
│   ├── controller
│   │     └── UserController.java
│   │
│   ├── dto
│   │     ├── UserDTO.java
│   │     ├── UserResponseDTO.java
│   │     └── ErrorResponseDTO.java
│   │
│   ├── exceptionhandler
│   │     ├── GlobalExceptionHandler.java
│   │     ├── UserAlreadyExistsException.java
│   │     └── UserNotFoundException.java
│   │
│   ├── model
│   │     └── User.java
│   │
│   ├── repository
│   │     └── UserRepository.java
│   │
│   ├── service
│   │     └── UserService.java
│   │
│   └── serviceImpl
│         └── UserServiceImpl.java
│
├── src/main/resources
│   ├── application.properties
│
├── src/test/java/com/project/userservice
│   └── controller
│         └── UserControllerTest.java
│
├── pom.xml
└── README.txt

-------------------------------------------
3. API ENDPOINTS
-------------------------------------------

BASE URL: /api/users

-------------------------------------------
POST /  → Create User
-------------------------------------------
Request Body:
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password"
}

Response:
201 CREATED
{
  "name": "John Doe",
  "email": "john@example.com"
}

409 → User Already Exists  
400 → Validation Error

-------------------------------------------
GET /{id}  → Get User By ID
-------------------------------------------
Response (200 OK):
{
  "name": "John Doe",
  "email": "john@example.com"
}

404 → User Not Found

-------------------------------------------
GET /  → Get All Users
-------------------------------------------
Response (200 OK):
[
  { "name": "John", "email": "john@example.com" }
]

404 → No Users Found

-------------------------------------------
4. VALIDATION
-------------------------------------------
- Uses @Valid on incoming DTOs
- Field-level validation using:
   • @NotBlank
   • @Email
   • @Size

Handled globally via:
- MethodArgumentNotValidException
- UserAlreadyExistsException
- UserNotFoundException
- RuntimeException

GlobalExceptionHandler returns
consistent error responses.

-------------------------------------------
5. TESTING
-------------------------------------------

- Controller tests written using:
   • @WebMvcTest
   • MockMvc
   • Mockito

Test Class:
- UserControllerTest

Test Scenarios:
- Create user 
- Get user by ID
- Get all users
- Invalid request handling

Run Tests:
mvn test

-------------------------------------------
6. H2 DATABASE SETUP
-------------------------------------------

H2 console enabled.

Access URL:
http://localhost:8080/h2-console

application.properties:
-------------------------------------------
spring.datasource.url=jdbc:h2:mem:usersdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
-------------------------------------------

-------------------------------------------
7. SWAGGER DOCUMENTATION
-------------------------------------------

Swagger UI:
http://localhost:8080/swagger-ui/index.html

Annotations used:
- @Tag
- @Operation
- @OpenAPIDefinition

-------------------------------------------
8. BUILD & RUN
-------------------------------------------

Build:
mvn clean install

Run:
mvn spring-boot:run

OR using IDE:
Right-click project → Run As → Spring Boot App

-------------------------------------------
9. FUTURE MICROSERVICES EXPANSION
-------------------------------------------

Planned structure:

project/
   ├── userservice/
   ├── productservice/
   └── apigateway/

Each service will remain:
- Independently deployable
- Independently testable
- Independently documented

-------------------------------------------
END OF FILE
===========================================