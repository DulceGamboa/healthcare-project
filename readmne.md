# MediPlus — Healthcare Management System

> Web-based medical management platform with Artificial Intelligence
> Spring Boot 3.2.5 · MySQL 8.0 · Java 17 · OpenAI GPT-3.5

---

## Table of Contents

1. [Description](#description)
2. [Technologies](#technologies)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Project Structure](#project-structure)
7. [API Endpoints](#api-endpoints)
8. [AI Modules](#ai-modules)
9. [Troubleshooting](#troubleshooting)
10. [Author](#author)

---

## Description

MediPlus is a full web healthcare platform that allows managing:

- Patients and their clinical profiles
- Diseases and medical conditions
- Medical appointments with status tracking
- Surgeries and surgical procedures
- Rehabilitation therapies with progress tracking
- Active medications and treatments
- Unified clinical history

It also includes Artificial Intelligence modules powered by OpenAI:
- Personalized diet plan generator
- 3-phase rehabilitation plan generator
- 24/7 virtual medical assistant

---

## Technologies

| Layer      | Technology              | Version  |
|------------|-------------------------|----------|
| Backend    | Java                    | 17       |
| Framework  | Spring Boot             | 3.2.5    |
| Security   | Spring Security + JWT   | 0.11.5   |
| ORM        | Spring Data JPA         | included |
| Database   | MySQL                   | 8.0      |
| Frontend   | HTML5 / CSS3 / JS ES6   | —        |
| AI         | OpenAI API (GPT-3.5)    | —        |
| Build      | Apache Maven            | 3.9+     |

---

## Prerequisites

Make sure you have the following installed:

- Java 17 — adoptium.net
- MySQL 8.0 — dev.mysql.com/downloads
- Maven 3.9+ — maven.apache.org
- Git — git-scm.com
- IntelliJ IDEA (recommended) or VS Code
- OpenAI account (for AI modules) — platform.openai.com

Verify installations:

    java -version
    mysql --version
    git --version

---

## Installation

### Step 1 — Clone the repository

    git clone https://github.com/DulceGamboa/healthcare-project.git
    cd healthcare-project

### Step 2 — Create the MySQL database

    CREATE DATABASE healthcare_db;

Spring Boot will create all tables automatically on startup.

### Step 3 — Configure application.properties

Open src/main/resources/application.properties and set:

    spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_db
    spring.datasource.username=root
    spring.datasource.password=root1234
    spring.jpa.hibernate.ddl-auto=update

### Step 4 — Add the logo

Copy your logo image to:

    src/main/resources/static/img/logo.jpeg

### Step 5 — Configure the OpenAI API Key

Open src/main/resources/static/js/app.js and replace:

    const OPENAI_API_KEY = 'TU_API_KEY_AQUI';

With your real key:

    const OPENAI_API_KEY = 'sk-proj-xxxxxxxxxxxxxxxxxxxxx';

### Step 6 — Start MySQL

Windows:
    net start MySQL80

macOS:
    brew services start mysql

Linux:
    sudo systemctl start mysql

### Step 7 — Run the project

Windows:
    mvnw.cmd spring-boot:run

macOS / Linux:
    ./mvnw spring-boot:run

IntelliJ IDEA:
    Open HealthcareApiApplication.java -> Right click -> Run

Success message in console:
    Tomcat started on port(s): 8080 (http)
    Started HealthcareApiApplication in X.XXX seconds

### Step 8 — Open in the browser

    http://localhost:8080/login.html

1. Click Registrarse and create an account
2. Log in with those credentials
3. You will be redirected to the Dashboard

---

## Configuration

Full application.properties:

    # Database connection
    spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_db
    spring.datasource.username=root
    spring.datasource.password=root1234
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

    # JPA / Hibernate
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=false
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

    # Server
    server.port=8080

---

## Project Structure

    healthcare-project/
    ├── src/main/java/com/healthcare/
    │   ├── HealthcareApiApplication.java
    │   ├── auth/
    │   │   ├── AuthController.java
    │   │   ├── AuthUser.java
    │   │   ├── AuthUserRepository.java
    │   │   ├── JwtFilter.java
    │   │   ├── JwtUtil.java
    │   │   └── SecurityConfig.java
    │   ├── controller/
    │   │   ├── UserController.java
    │   │   ├── DiseaseController.java
    │   │   ├── AppointmentController.java
    │   │   ├── SurgeryController.java
    │   │   ├── RehabilitationController.java
    │   │   └── MedicationController.java
    │   ├── model/
    │   │   ├── User.java
    │   │   ├── Disease.java
    │   │   ├── Appointment.java
    │   │   ├── Surgery.java
    │   │   ├── Rehabilitation.java
    │   │   └── Medication.java
    │   └── repository/
    │       ├── UserRepository.java
    │       ├── DiseaseRepository.java
    │       ├── AppointmentRepository.java
    │       ├── SurgeryRepository.java
    │       ├── RehabilitationRepository.java
    │       └── MedicationRepository.java
    ├── src/main/resources/
    │   ├── application.properties
    │   └── static/
    │       ├── css/styles.css
    │       ├── js/app.js
    │       ├── img/logo.jpeg
    │       ├── login.html
    │       ├── dashboard.html
    │       ├── profile.html
    │       ├── diseases.html
    │       ├── appointments.html
    │       ├── surgery.html
    │       ├── rehabilitation.html
    │       ├── medications.html
    │       ├── nutrition.html
    │       ├── chat.html
    │       └── history.html
    ├── pom.xml
    ├── mvnw
    ├── mvnw.cmd
    ├── README.md
    ├── REQUIREMENTS.txt
    └── USER_GUIDE.txt

---

## API Endpoints

### Authentication (no token required)

| Method | Endpoint           | Description        |
|--------|--------------------|--------------------|
| POST   | /api/auth/register | Register new user  |
| POST   | /api/auth/login    | Login, returns JWT |
| GET    | /api/auth/verify   | Verify token       |

Example login request body:
    {
      "email": "dulce@correo.com",
      "password": "mypassword"
    }

Example login response:
    {
      "token": "eyJhbGciOiJIUzI1NiJ9...",
      "name": "Dulce Gamboa"
    }

### Protected resources (require Authorization: Bearer token header)

| Method | Endpoint                      | Description         |
|--------|-------------------------------|---------------------|
| GET    | /api/users                    | List patients       |
| POST   | /api/users                    | Create patient      |
| DELETE | /api/users/{id}               | Delete patient      |
| GET    | /api/diseases                 | List diseases       |
| POST   | /api/diseases                 | Create disease      |
| DELETE | /api/diseases/{id}            | Delete disease      |
| GET    | /api/appointments             | List appointments   |
| POST   | /api/appointments             | Create appointment  |
| DELETE | /api/appointments/{id}        | Delete appointment  |
| GET    | /api/surgeries                | List surgeries      |
| POST   | /api/surgeries                | Create surgery      |
| DELETE | /api/surgeries/{id}           | Delete surgery      |
| GET    | /api/rehabilitations          | List therapies      |
| POST   | /api/rehabilitations          | Create therapy      |
| DELETE | /api/rehabilitations/{id}     | Delete therapy      |
| GET    | /api/medications              | List medications    |
| POST   | /api/medications              | Create medication   |
| DELETE | /api/medications/{id}         | Delete medication   |

---

## AI Modules

All AI modules require a valid OpenAI API key set in js/app.js.

### Diet and Nutrition
1. Go to Dieta y Nutricion in the sidebar
2. Select a condition chip or type your own condition
3. Add dietary restrictions if needed (e.g. vegetariano, sin gluten)
4. Select the nutritional goal
5. Click Generar Plan de Dieta con IA
6. The plan will include: recommended foods, foods to avoid,
   full meal plan and medical warnings

### Rehabilitation Plan
1. Go to Rehabilitacion in the sidebar
2. Describe the type of injury or condition
3. Select the activity level
4. Add additional notes if needed
5. Click Generar Plan con IA
6. The plan will include 3 phases:
   Phase 1 — Initial (weeks 1-2)
   Phase 2 — Intermediate (weeks 3-6)
   Phase 3 — Advanced (weeks 7+)

### Virtual Assistant
1. Go to Asistente Virtual in the sidebar
2. Type your medical question in the text box
3. Press Enter or click Enviar
4. The assistant responds with general medical information
5. Click Limpiar conversacion to start a new chat

Note: The virtual assistant provides general information only.
It does not replace a real medical professional.

---

## Troubleshooting

Cannot connect to MySQL:
    Verify MySQL is running and that the credentials in
    application.properties are correct. Run:
    net start MySQL80 (Windows)
    brew services start mysql (macOS)
    sudo systemctl start mysql (Linux)

Port 8080 already in use:
    Windows:
    netstat -ano | findstr :8080
    taskkill /PID [number] /F

    macOS/Linux:
    lsof -i :8080
    kill -9 [number]

401 Unauthorized on API calls:
    The JWT token has expired (valid for 24 hours).
    Log out and log in again at login.html.

AI modules not working:
    Check that OPENAI_API_KEY is correctly set in js/app.js.
    Verify your OpenAI account has available credits at
    platform.openai.com.

Logo not showing in sidebar:
    Make sure the file exists at:
    src/main/resources/static/img/logo.jpeg

Page redirects to login unexpectedly:
    Session has expired. Log in again. Sessions last 24 hours.

---

## Author

Dulce Gamboa, Brenda Mendoza and Camila López
Repository: https://github.com/DulceGamboa/healthcare-project

---

MediPlus Healthcare Management System 2026