# 🌱 Reto-Soy-Yo - RESTful API for Self-Care Challenges


A RESTful API designed to create and follow daily self-care challenges.  
This project provides an accessible and sustainable approach to building healthy habits through **micro-challenges** that are small, achievable, and adaptable.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)

---

## 📋 Table of Contents

- [🎯 Project Overview](#project-overview)
- [✨ Features](#features)
- [🚀 Getting Started](#getting-started)
- [⚙️ Installation](#installation)
- [🏃‍♂️ Running the Application](#running-the-application)
- [🐳 Docker Setup](#docker-setup)
- [🌐 API Endpoints](#api-endpoints)
- [🔧 Technologies](#technologies)
- [📊 Architecture](#architecture)
- [🧪 Tests](#tests)
- [🔄 Automation](#automation)
- [🤝 Contributing](#contributing)
- [👩‍💻 Author](#author)

---

## 🎯 Project Overview

Maintaining self-care routines in modern life is a significant challenge. People often rely on unsustainable methods such as fragmented notes, reminders, or applications that lack flexibility.

**Reto-Soy-Yo** addresses this issue by offering a digital platform to manage achievable micro-challenges that promote physical and mental well-being.

**Specific Objectives:**

- Provide registration and login with **role-based authentication (Admin/User) via JWT**.
- Allow Administrators to **create, edit, and delete challenges**.
- Enable Users to **join challenges and record daily progress**.
- Ensure **data integrity through validations and DTOs**.
- Achieve a **minimum of 70% test coverage** for unit and integration testing.
- Implement **automation with Docker and GitHub Actions**.

---

## ✨ Features

- 👤 User registration and login with secure **JWT authentication**.
- 🔐 Role-based access control:
    - **Admin** → Manage challenges (CRUD)
    - **User** → Join challenges and track progress
- 🧗 Join daily challenges with **flexible participation**
- ✅ Track progress with **daily completion updates**
- 📊 View progress history for **personal monitoring**

**Future Enhancements:**

- 🔔 Daily notifications and reminders
- 🤖 Personalized challenge recommendations

---

## 🚀 Getting Started

### Prerequisites

Ensure the following are installed:

- **Java 21+** - [Download](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/mysql/)
- **Docker (optional)** - [Download](https://www.docker.com/get-started)
- **Git** - [Download](https://git-scm.com/downloads)

### Quick Start

```bash
# Clone the repository
git clone https://github.com/PaolaAPL17/Reto-Soy-Yo.git
cd Reto-Soy-Yo

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run

```

The API will be available at `http://localhost:8080`

---
## ⚙️ Installation

### 1. Clone the Repository
```bash
git clone https://github.com/PaolaAPL17/Reto-Soy-Yo.git
cd Reto-Soy-Yo
```
### 2. Configure Database
Create a MySQL database and edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/reto_soy_yo_db
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true

jwt:
  secret: your_jwt_secret_key
  expiration: 86400000 # 24 hours

server:
  port: 8080
```

### 3. Build the Project
```bash
./mvnw clean install

```
## 🏃‍♂️ Running the Application
```bash
./mvnw spring-boot:run
```
## 🐳 Docker Setup
### Build Docker image locally
``` bash
docker build -t reto-soy-yo-api .
docker run -p 8080:8080 --name reto-soy-yo-api reto-soy-yo-api
```
### Use Pre-Built Image:
``` bash
docker pull paolaapl17/reto-soy-yo:latest
docker run -p 8080:8080 paolaapl17/reto-soy-yo:latest
```
The API will be available at 👉 `http://localhost:8080/api/`

## 🌐 API Endpoints
### 🔐 Authentication
| Method   | Endpoint              | Description                      | Auth | Role |
|----------|----------------------|----------------------------------|------|------|
| **POST** | `/api/auth/register`  | Register a new user              | ![No](https://img.shields.io/badge/Auth-No-red) | N/A |
| **POST** | `/api/auth/login`     | Login with username and password | ![No](https://img.shields.io/badge/Auth-No-red) | N/A |

### 👥 User Management
| Method     | Endpoint          | Description          | Auth                                         | Role                                                                                                            |
|------------|------------------|---------------------|----------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| **GET**    | `/api/users`      | List all users       | ![Si](https://img.shields.io/badge/Auth-Si-green) | ![Admin](https://img.shields.io/badge/Role-ADMIN-purple) |
| **GET**    | `/api/users/me`   | Get own user profile | ![Si](https://img.shields.io/badge/Auth-Si-green) | ![Admin](https://img.shields.io/badge/Role-ADMIN-purple) ![User](https://img.shields.io/badge/Role-USER-blue) |

### 🏆 Challenges
| Method     | Endpoint                | Description                | Auth                                         | Role                              |
|------------|------------------------|----------------------------|----------------------------------------------|----------------------------------|
| **POST**   | `/api/retos`           | Create a challenge (CRUD)  | ![Si](https://img.shields.io/badge/Auth-Si-green) | ![Admin](https://img.shields.io/badge/Role-ADMIN-purple) |
| **GET**    | `/api/retos`           | Retrieve all challenges    | ![Si](https://img.shields.io/badge/Auth-Si-green) | ![All](https://img.shields.io/badge/Role-ALL-blue) |
| **POST**   | `/api/retos/{id}/join` | Join a challenge           | ![Si](https://img.shields.io/badge/Auth-Si-green) | ![User](https://img.shields.io/badge/Role-USER-blue) |

### 📈 Progress
| Method     | Endpoint        | Description            | Auth                                         | Role                |
|------------|----------------|------------------------|----------------------------------------------|--------------------|
| **POST**   | `/api/progreso` | Record daily progress  | ![Si](https://img.shields.io/badge/Auth-Si-green) | ![User](https://img.shields.io/badge/Role-USER-blue) |
| **GET**    | `/api/progreso` | View progress history  | ![Si](https://img.shields.io/badge/Auth-Si-green) | ![User](https://img.shields.io/badge/Role-USER-blue) |


### 🔧 Technologies
- **Java 21** - Core programming language
- **Spring Boot 3.5.4** - Application framework
- **Spring Security + JWT** - Provides authentication and authorization
- **Spring Data JPA + Hibernate** - Data persistence and ORM
- **MySQL 8.0+** - Relational database
- **Docker** - Containerization
- **GitHub Actions (CI/CD)** - Continuous integration and deployment
- **JUnit 5 + Mockito** - Unit and integration testing

---

### 📊 Architecture
**Main Entities**
- **User** → id, name, email, role (User/Admin)
- **Challenge (Reto)** → id, title, type, duration, level
- **Progress** → id, date, completed, user_id, challenge_id

**Relationships**
- **User ↔ Challenge** → Many-to-Many
- **Progress** links users and challenges by date

### 📁 Project Structure
```
reto-soy-yo/
├── src/
│ ├── main/
│ │ ├── java/com/paola/reto_soy_yo/
│ │ │ ├── config/ # CORS and security configurations
│ │ │ ├── controllers/ # REST controllers (Auth, Users, Challenges, Progress)
│ │ │ ├── dtos/ # Request and response DTOs
│ │ │ ├── exceptions/ # Custom exceptions and handlers
│ │ │ ├── models/ # Entity classes (User, Challenge, Progress)
│ │ │ ├── repositories/ # Spring Data JPA repositories
│ │ │ ├── security/ # JWT and authentication config
│ │ │ ├── services/ # Business logic services
│ │ │ └── RetoSoyYoApplication.java # Main Spring Boot application
│ │ └── resources/
│ │ ├── application.yml
│ │ └── data.sql
│ └── test/
│ └── java/com/paola/reto_soy_yo/
│ ├── controllers/ # Controller unit tests
│ ├── services/ # Service unit tests
│ └── RetoSoyYoApplicationTests.java
├── pom.xml # Maven project descriptor
├── mvnw / mvnw.cmd # Maven wrapper scripts
├── Dockerfile # Docker image configuration
├── .gitignore / .gitattributes
├── README.md
```

---

### 🧪 Tests
- **Unit Tests** - Test business logic in services
- **Integration Tests** - Test controller endpoints
- **Coverage** - Minimum 70% test coverage
- **Run Tests**
- ``` bash
    - ./mvnw test
    - ./mvnw test jacoco:report
  ```
- **Coverage Report** - `target/site/jacoco/index.html`

---

### 🔄 Automation
- **GitHub Actions** - Automates CI/CD pipelines
- **Automated test execution** - Runs unit and integration tests
- **Docker image generation** - Builds container images automatically
- **Conventional commit validation** - Ensures commit message standards

---

### 🤝 Contributing
- **feat:** Add new features
- **fix:** Bug fixes
- **docs:** Documentation updates
- **style:** Code style changes
- **refactor:** Code refactoring
- **test:** Add or update tests
- **chore:** Maintenance tasks

---

### 👩‍💻 Author
- **Paola A. P. L.** - Backend & DevOps Developer
    - GitHub: [PaolaAPL17](https://github.com/PaolaAPL17)
    - ![Profile Image](https://github.com/PaolaAPL17.png 
       <table> <tr> <td align="center"><a href="https://github.com/PaolaAPL17"> <img src="https://github.com/PaolaAPL17.png" width="100px;" alt="PaolaAPL17"/> <br /> <sub><b>PaolaAPL17</b></sub> </a> <br /> <sub>Developer</sub> </td> </tr> </table>