# Recruitment Management System

An enterprise-level recruitment management system built with Spring Boot 3.x + MyBatis + MySQL, providing complete recruitment process management functionality.

## Tech Stack

- **Backend Framework**: Spring Boot 3.2.x
- **Database**: MySQL 8.0+
- **ORM Framework**: MyBatis 3.5.x
- **Authentication**: JWT Token
- **Build Tool**: Maven 3.9+
- **Documentation**: Swagger UI

## Project Structure

```
RecruitmentSystem/
├── common/                    # Common Module
│   ├── src/main/java/com/lin/common/
│   │   ├── constant/          # Constants
│   │   ├── enumeration/       # Enumerations
│   │   ├── exception/         # Custom Exceptions
│   │   ├── result/            # Response Wrappers
│   │   └── util/              # Utility Classes
│   └── pom.xml
├── pojo/                      # Data Model Module
│   ├── src/main/java/com/lin/pojo/
│   │   ├── dto/               # Request Data Transfer Objects
│   │   ├── entity/            # Database Entities
│   │   └── vo/                # Response View Objects
│   └── pom.xml
├── server/                    # Service Module (Entry Point)
│   ├── src/main/java/com/lin/server/
│   │   ├── config/            # Configuration Classes
│   │   ├── controller/        # REST API Controllers
│   │   ├── filter/            # Filters (JWT Authentication)
│   │   ├── handler/           # Global Exception Handler
│   │   ├── mapper/            # MyBatis Data Access Layer
│   │   ├── service/           # Business Logic Layer
│   │   ├── util/              # Type Handlers
│   │   └── ServerApplication.java  # Boot Class
│   ├── src/main/resources/
│   │   ├── mapper/            # MyBatis XML Mappers
│   │   ├── data/              # Static Resource Directory
│   │   ├── application.yml    # Main Configuration
│   │   └── application-dev.yml # Development Configuration
│   └── pom.xml
├── sql/                       # Database Scripts
│   ├── create_table.sql       # Table Creation Script
│   └── insert_data_dev.sql    # Test Data
└── pom.xml                    # Parent Project Configuration
```

## Running Environment

### Prerequisites

- **JDK**: 21+ (Recommended: OpenJDK 21)
- **MySQL**: 8.0+
- **Maven**: 3.9+

### Environment Configuration

1. **Create Database**

```sql
CREATE DATABASE IF NOT EXISTS recruitment_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. **Configure Database Connection**

Edit `server/src/main/resources/application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/recruitment_db?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## Startup Methods

### Method 1: Maven Package & Run

```bash
# Enter project root directory
cd RecruitmentSystem

# Build project
mvn clean package -DskipTests

# Run server module
java -jar server/target/server-1.0.0.jar
```

### Method 2: Run in IDE

1. Import project into IntelliJ IDEA or Eclipse
2. Select `ServerApplication.java` as the boot class
3. Set Active Profiles to `dev` in run configuration
4. Click run button

### Method 3: Maven Direct Run

```bash
cd RecruitmentSystem/server
mvn spring-boot:run
```

## Access URLs

- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## Database Initialization

### Create Tables

```bash
mysql -u your_username -p recruitment_db < sql/create_table.sql
```

### Insert Test Data (Optional)

```bash
mysql -u your_username -p recruitment_db < sql/insert_data_dev.sql
```

## Business Modules

### 1. Authentication Module (`/api/auth`)

| Function | API | Description |
|----------|-----|-------------|
| User Registration | `POST /api/auth/register` | Register as applicant/admin |
| User Login | `POST /api/auth/login` | Login with credentials, returns JWT Token |
| Send Verification Code | `POST /api/auth/send-code` | Send email verification code |
| Reset Password | `POST /api/auth/reset-password` | Reset password via verification code |
| Change Password | `POST /api/auth/change-password` | Change current user password |

### 2. Resume Management Module (`/api/resumes`)

| Function | API | Description |
|----------|-----|-------------|
| Get My Resume | `GET /api/resumes/my` | Get current user's resume |
| Create Resume | `POST /api/resumes` | Create a new resume |
| Update Resume | `PUT /api/resumes/{id}` | Update resume information |
| Delete Resume | `DELETE /api/resumes/{id}` | Delete resume |
| Get Resume Detail | `GET /api/resumes/{id}` | Get resume detail (admin) |
| Get Resume List | `GET /api/resumes` | Get paginated resume list (admin) |
| Upload Resume PDF | `POST /api/resumes/upload` | Upload PDF resume attachment |

### 3. Job Management Module (`/api/jobs`)

| Function | API | Description |
|----------|-----|-------------|
| Create Job | `POST /api/jobs` | Admin creates job posting |
| Update Job | `PUT /api/jobs/{id}` | Update job information |
| Delete Job | `DELETE /api/jobs/{id}` | Delete job |
| Get Job Detail | `GET /api/jobs/{id}` | Get job detail |
| Get Job List | `GET /api/jobs` | Get paginated job list |
| Search Jobs | `GET /api/jobs/search` | Search jobs by keyword |

### 4. Application Management Module (`/api/applications`)

| Function | API | Description |
|----------|-----|-------------|
| Apply for Job | `POST /api/applications` | Applicant applies for job |
| Get My Applications | `GET /api/applications/my` | Get current user's applications |
| Get Application List | `GET /api/applications` | Get all applications (admin) |
| Update Application Status | `PUT /api/applications/{id}/status` | Update application status |
| Get Application Detail | `GET /api/applications/{id}` | Get application detail |

### 5. Favorite Module (`/api/favorites`)

| Function | API | Description |
|----------|-----|-------------|
| Add Favorite | `POST /api/favorites` | Add job to favorites |
| Remove Favorite | `DELETE /api/favorites/{id}` | Remove from favorites |
| Get Favorites | `GET /api/favorites` | Get all favorites for current user |

### 6. Notification Module (`/api/notifications`)

| Function | API | Description |
|----------|-----|-------------|
| Get Notifications | `GET /api/notifications` | Get all notifications for current user |
| Mark as Read | `PUT /api/notifications/{id}/read` | Mark notification as read |
| Delete Notification | `DELETE /api/notifications/{id}` | Delete notification |

### 7. Interview Management Module (`/api/interviews`)

| Function | API | Description |
|----------|-----|-------------|
| Create Interview | `POST /api/interviews` | Admin creates interview schedule |
| Update Interview | `PUT /api/interviews/{id}` | Update interview information |
| Delete Interview | `DELETE /api/interviews/{id}` | Delete interview |
| Get My Interviews | `GET /api/interviews/my` | Get current user's interviews |
| Get Interview List | `GET /api/interviews` | Get all interviews (admin) |

### 8. Admin Module (`/api/admin`)

| Function | API | Description |
|----------|-----|-------------|
| Get Dashboard | `GET /api/admin/dashboard` | Get system statistics |
| User Management | `GET/POST/PUT/DELETE /api/admin/users` | Manage system users |
| Department Management | `GET/POST/PUT/DELETE /api/admin/departments` | Manage departments |
| System Logs | `GET /api/admin/logs` | View system operation logs |
| System Settings | `GET/PUT /api/admin/settings` | Manage system configuration |

## Data Models

### Core Entities

| Entity | Description |
|--------|-------------|
| `User` | User information (applicant/admin) |
| `Resume` | Resume information |
| `Job` | Job posting |
| `Application` | Job application record |
| `Favorite` | Favorite record |
| `Notification` | Notification message |
| `Interview` | Interview schedule |
| `Department` | Department information |

### Enumerations

| Enum | Description |
|------|-------------|
| `UserRoleEnum` | User role (ADMIN/APPLICANT) |
| `JobStatusEnum` | Job status (OPEN/CLOSED) |
| `ApplicationStatusEnum` | Application status (PENDING/REVIEWING/OFFER/REJECTED) |
| `InterviewMethodEnum` | Interview method (ONLINE/OFFLINE) |
| `InterviewResultEnum` | Interview result (PASS/FAIL/PENDING) |
| `NotificationTypeEnum` | Notification type (APPLICATION/INTERVIEW/SYSTEM) |
| `GenderEnum` | Gender (MALE/FEMALE/OTHER) |

## JWT Authentication

The system uses JWT (JSON Web Token) for authentication:

1. After successful login, server returns `access_token`
2. Subsequent requests must include `Authorization: Bearer {token}` in header
3. The following endpoints are excluded from authentication:
   - `POST /api/auth/login` - Login
   - `POST /api/auth/register` - Register
   - `POST /api/auth/send-code` - Send verification code

## Error Handling

Unified exception response format:

```json
{
  "code": 400,
  "message": "Error description",
  "data": null
}
```

| Error Code | Meaning |
|------------|---------|
| 200 | Success |
| 400 | Bad Request |
| 401 | Unauthorized or Token expired |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Internal Server Error |

## Development Guidelines

### Branch Management

- `main`: Main branch, stable version
- `develop`: Development branch
- `feature/*`: Feature branches

### Code Standards

- Follow Spring Boot coding standards
- Use Lombok to simplify code
- Use custom exception classes for error handling
- Use SLF4J for logging

## License

MIT License
