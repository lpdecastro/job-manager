# Job Manager - Spring Boot REST API Project

Job Manager is a Spring Boot application that provides a set of REST APIs for managing job listings, 
applications, saved jobs, companies, recruiters, and candidates. This project includes authentication and 
authorization using Spring Security with OAuth 2.0 Resource Server and JWT secured with RSA keys.

## Table of Contents
- [1 - Features](#1---features)
- [2 - ER Diagram](#2---er-diagram)
- [3 - API Endpoints](#3---api-endpoints)
- [4 - Installation](#4---installation)
- [5 - API Usage](#5---api-usage)
- [6 - Custom Configurations (Optional)](#6---custom-configurations-optional)
- [7 - Custom RSA Public and Private Keys (Optional)](#7---custom-rsa-public-and-private-keys-optional)
- [8 - Technology Stack](#8---technology-stack)
- [9 - Other Projects](#9---other-projects)

## 1 - Features
- **Authentication**: OAuth 2.0 with JWT and RSA keys for secure registration, login, and role-based access control.
- **Job Listings**: Create, update, delete, and search for job listings.
- **Job Applications**: Submit and manage job applications.
- **Saved Jobs**: Save and manage job listings.
- **Companies**: CRUD operations for company profiles.
- **Recruiters**: CRUD operations for recruiter profiles, including upload/download of photos, resumes, and cover 
  letters in PDF.
- **Candidates**: CRUD operations for candidate profiles, including upload/download of photos, resumes, and cover 
  letters in PDF.

## 2 - ER Diagram
![ER Diagram](assets/er-diagram.png)

## 3 - API Endpoints
You can view the full Postman API documentation here:
[Job Manager](https://documenter.getpostman.com/view/31412288/2sA3Qy58tn)

### Authentication
- **POST** `/rest/auth/register`: Register
- **POST** `/rest/auth/login`: Login
- **POST** `/rest/auth/change-password`: Change password

### Candidates
- **GET** `/rest/candidates/{candidateId}`: Get candidate by ID
- **GET** `/rest/candidates/current`: Get current candidate
- **GET** `/rest/candidates/{candidateId}/downloads/resume/{resumeFilename}`: Download resume
- **PUT** `/rest/candidates/current`: Update current candidate
- **PUT** `/rest/candidates/current/upload/resume`: Upload resume
- **PUT** `/rest/candidates/current/upload/photo`: Upload photo
- **DELETE** `/rest/candidates/{candidateId}`: Delete candidate by ID

### Recruiters
- **GET** `/rest/recruiters/current`: Get current recruiter
- **PUT** `/rest/recruiters/current`: Update current recruiter
- **PUT** `/rest/recruiters/current/upload/photo`: Upload photo
- **DELETE** `/rest/recruiters/{recruiterId}`: Delete recruiter by ID

### Job Listings
- **GET** `/rest/job-listings`: Get all job listings
- **GET** `/rest/job-listings/company/{companyId}`: Get all job listings by company ID
- **GET** `/rest/job-listings/{jobListingId}`: Get job listing by ID
- **POST** `/rest/job-listings`: Create job listing
- **PUT** `/rest/job-listings/{jobListingId}`: Update job listing
- **DELETE** `/rest/job-listings/{jobListingId}`: Delete job listing

### Job Applications
- **GET** `/rest/job-applications/candidates/{candidateId}`: Get all job applications by candidate ID
- **GET** `/rest/job-applications/job-listings/{jobListingId}`: Get all job applications by job listing ID
- **GET** `/rest/job-applications/{jobApplicationId}`: Get job application by ID
- **POST** `/rest/job-applications`: Create job application
- **PUT** `/rest/job-applications/{jobApplicationId}`: Update job application
- **DELETE** `/rest/job-applications/{jobApplicationId}`: Delete job application

### Saved Jobs
- **GET** `/rest/saved-jobs`: Get all saved jobs by current candidate
- **POST** `/rest/saved-jobs`: Add to saved jobs
- **DELETE** `/rest/saved-jobs/{savedJobId}`: Remove from saved jobs

### Companies
- **GET** `/rest/companies`: Get all companies
- **GET** `/rest/companies/{companyId}`: Get company by ID
- **POST** `/rest/companies`: Create company
- **PUT** `/rest/companies/{companyId}`: Update company
- **DELETE** `/rest/companies/{companyId}`: Delete company

## 4 - Installation
### Prerequisites
- Java 17 or higher

### Installation
1. **Clone the repository**
    ```bash
   git clone https://github.com/lpdecastro/job-manager.git
   cd job-manager
   ```
2. **Build the project**
   ```bash
   mvnw clean install
   ```
3. **Run the application**
   ```bash
   mvnw spring-boot:run
   ```
   The application will be accessible at `http://localhost:8080`.

## 5 - API Usage
1. **Register as a Recruiter**

   Open Postman and make a POST request to register as a recruiter:
   ```http
   POST /.rest/auth/register HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   {
      "username": "recruiter",
      "email": "recruiter@yopmail.com",
      "password": "password",
      "role": "RECRUITER"
   }
   ```

2. **Login as a Recruiter**

   Make a POST request to login as a recruiter:
   ```http
   POST /.rest/auth/login HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   {
      "username": "recruiter",
      "password": "password"
   }
   ```
   You will receive a response with an access token:
   ```json
   {
      "accessToken": "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiY2FuZGlkYXRlIiwiZXhwIjoxNzE3Mzc2NjQ5LCJpYXQiOjE3MTczNzMwNDksInNjb3BlIjoiQ0FORElEQVRFIn0.UN1jHeRi0F4_RkvPNl3vAvsVY3jrxKTvu6GePkw4TdpGztfscWtybWzU6OcHLs5854gsLZx1n9Nnp9m8QjcgaNGZL1rHc-SApnoHPy1l5qOB5slbbFYVlBfg1Y8tYF278I6_pRHLX8YYuRojcGAdcnn8JYaxTU-CPbDoQbZFKkQFwg6ElXUr08eZapgxG970a2Ollk46JmjhX01cxyjfAdGtpK8hColSvnZAJOf8ncFACe9r89UUtAtz2JtyaGkyKgeDqjqheU8hiprpPjolRGxEYyMnZmM_L7pAsWBoQoJsKABvIHKwuDiI36IdvHMJjhuq3OWiM0_8D37081c7Mg",
      "tokenType": "Bearer",
      "expiresIn": 3600
   }
   ```

3. **Create New Company and New Job Listing**

   Copy the access token and add an Authorization header of type Bearer token.

   Make a POST request to create a new company:
   ```http
   POST /.rest/companies HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiY2FuZGlkYXRlIiwiZXhwIjoxNzE3Mzc2NjQ5LCJpYXQiOjE3MTczNzMwNDksInNjb3BlIjoiQ0FORElEQVRFIn0.UN1jHeRi0F4_RkvPNl3vAvsVY3jrxKTvu6GePkw4TdpGztfscWtybWzU6OcHLs5854gsLZx1n9Nnp9m8QjcgaNGZL1rHc-SApnoHPy1l5qOB5slbbFYVlBfg1Y8tYF278I6_pRHLX8YYuRojcGAdcnn8JYaxTU-CPbDoQbZFKkQFwg6ElXUr08eZapgxG970a2Ollk46JmjhX01cxyjfAdGtpK8hColSvnZAJOf8ncFACe9r89UUtAtz2JtyaGkyKgeDqjqheU8hiprpPjolRGxEYyMnZmM_L7pAsWBoQoJsKABvIHKwuDiI36IdvHMJjhuq3OWiM0_8D37081c7Mg
   {
      "name": "name",
      "jobLocations": [
         {
            "city": "city",
            "province": "province",
            "zipCode": "1111",
            "country": "PH"
         }
      ]
   }
   ```
   Make a POST request to create a new job listing:
   ```http
   POST /.rest/job-listings HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiY2FuZGlkYXRlIiwiZXhwIjoxNzE3Mzc2NjQ5LCJpYXQiOjE3MTczNzMwNDksInNjb3BlIjoiQ0FORElEQVRFIn0.UN1jHeRi0F4_RkvPNl3vAvsVY3jrxKTvu6GePkw4TdpGztfscWtybWzU6OcHLs5854gsLZx1n9Nnp9m8QjcgaNGZL1rHc-SApnoHPy1l5qOB5slbbFYVlBfg1Y8tYF278I6_pRHLX8YYuRojcGAdcnn8JYaxTU-CPbDoQbZFKkQFwg6ElXUr08eZapgxG970a2Ollk46JmjhX01cxyjfAdGtpK8hColSvnZAJOf8ncFACe9r89UUtAtz2JtyaGkyKgeDqjqheU8hiprpPjolRGxEYyMnZmM_L7pAsWBoQoJsKABvIHKwuDiI36IdvHMJjhuq3OWiM0_8D37081c7Mg
   {
      "title": "title",
      "description": "description",
      "type": "FULL_TIME",
      "remote": "REMOTE",
      "salary": 80000.00,
      "companyId": 1,
      "jobLocationId": 1
   }
   ```
   You will receive a response like below:
   ```json
   {
      "jobListingId": 1,
      "title": "title",
      "description": "description",
      "type": "FULL_TIME",
      "remote": "REMOTE",
      "salary": 80000,
      "createdAt": "2024-06-03T08:21:40.520026",
      "updatedAt": "2024-06-03T08:21:40.520047",
      "company": {
         "companyId": 1,
         "name": "name",
         "logo": null
      },
      "jobLocation": {
         "jobLocationId": 1,
         "city": "city",
         "province": "province",
         "zipCode": "1111",
         "country": "PH"
      }
   }
   ```

4. **Access Other Recruiter Authorized APIs**

   Use the access token to access other recruiter authorized APIs such as managing recruiter profile, companies, 
   job listings, etc.

   You can also register and login as a candidate and access candidate-authorized APIs.

## 6 - Custom Configurations (Optional)
Update the `src/main/resources/application.properties` file to configure the application settings. Here are some
key properties:
```properties
# Database settings for H2 (development)
spring.datasource.url=jdbc:h2:mem:job_manager_db
spring.datasource.username=sa
spring.datasource.password=

# Enable H2 console for in-memory database access
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Defer JPA initialization until the datasource is fully available
spring.jpa.defer-datasource-initialization=true

# Maximum file size for uploaded files
spring.servlet.multipart.max-file-size=5MB

# Security settings
app.rsa.private-key=classpath:/certs/private.pem
app.rsa.public-key=classpath:/certs/public.pem
app.jwt.expires-in=3600

# Directories for storing candidates and recruiters files (e.g. resume, photo, cover letter)
app.data.candidates-directory=data/candidates/
app.data.recruiters-directory=data/recruiters/
```

## 7 - Custom RSA Public and Private Keys (Optional)
Generate a public/private key pair and configure them in the application.
1. **Create RSA key pair**
    ```bash
   openssl genrsa -out keypair.pem 2048
   ```
2. **Extract public key**
    ```bash
   openssl rsa -in keypair.pem -pubout -out public.pem
   ```
3. **Create private key in PKCS#8 format**
    ```bash
   openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
   ```
   Delete the keypair.pem and put the `private.pem` and `public.pem` inside the directories
   specified by `app.rsa.private-key` and `app.rsa.public-key` properties.

## 8 - Technology Stack
- **Java**: Primary programming language used for developing the backend.
- **Spring Boot**: Backend framework for building the application.
- **Spring Security**: For securing the application.
- **Spring Data JPA**: For database interactions.
- **Hibernate**: ORM tool for mapping Java objects to database tables.
- **H2 Database**: In-memory database for development and testing.
- **JSON Web Token**: For securing API endpoints with token-based authentication.
- **Maven**: For project build and dependency management.

## 9 - Other Projects
Check out my other projects:
- [E-Commerce Manager](https://github.com/lpdecastro/ecommerce-manager)
