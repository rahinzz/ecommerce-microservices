===========================================
              USER SERVICE
===========================================

A Spring Boot microservice responsible for 
managing user operations such as creating 
users, fetching user details, and listing 
all users.

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
│   ├── config
│   │     └── SwaggerConfig.java
│   │
│   ├── controller
│   │     └── UserController.java
│   │
│   ├── dto
│   │     ├── UserDTO.java
│   │     └── UserResponseDTO.java
│   │
│   ├── exceptionhandler
│   │     └── GlobalExceptionHandler.java
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
│   └── data.sql   (optional for sample data)
│
├── src/test/java
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
  "email": "john@example.com"
}

Response:
201 CREATED
{
  "name": "John Doe",
  "email": "john@example.com"
}

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
- Uses @Valid on incoming DTOs.
- Handles:
   • MethodArgumentNotValidException  
   • RuntimeException  
   • Custom validation errors  

GlobalExceptionHandler handles all validation responses.

-------------------------------------------
5. H2 DATABASE SETUP
-------------------------------------------

H2 console enabled.

Access URL:
http://localhost:8080/h2-console

Typical application.properties:
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
6. SWAGGER DOCUMENTATION
-------------------------------------------

Swagger UI:
http://localhost:8080/swagger-ui/index.html

Annotations used:
- @Tag
- @Operation
- @OpenAPIDefinition

-------------------------------------------
7. BUILD & RUN
-------------------------------------------

Build:
mvn clean install

Run:
mvn spring-boot:run

OR using IDE (STS/IntelliJ):
Right-click project → Run As → Spring Boot App

-------------------------------------------
8. FUTURE MICROSERVICES EXPANSION
-------------------------------------------

This repo will eventually include:

project/
   ├── userservice/
   ├── productservice/
   └── apigateway/

Each service will have its own README.

-------------------------------------------
9. VERSION CONTROL GUIDELINES
-------------------------------------------
- Push userservice immediately to GitHub.
- Keep each service modular & independent.
- Use feature branches for future updates.
- Update README as new modules are added.

-------------------------------------------
END OF FILE
===========================================
