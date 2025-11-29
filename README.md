# SubSentry - Subscription & Trial Tracker

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
<!-- ![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB) [PENDING] -->

**SubSentry** is a full-stack web application designed to solve the common financial "leak" of forgotten subscriptions and free trials. It allows users to track their recurring expenses and provides an **automated email notification system** to alert users before a free trial converts into a paid subscription.

---

## Key Features

* **User Authentication:** Secure Sign Up and Login using **JWT (JSON Web Tokens)**.
* **Role-Based Access Control (RBAC):** Distinct permissions for `USER` (manage own data) and `ADMIN` (manage users).
* **Subscription Management:** Full CRUD (Create, Read, Update, Delete) capabilities for subscriptions.
* **Data Isolation:** Users can only access and modify their own subscription data.
* **Automated Alerts:** A background **Scheduler** runs daily to check for trials ending in 3, 2, or 1 days.
* **Email Notifications:** Integrated **Java Mail Sender** to send personalized reminder emails to users.

---

## Tech Stack

* **Backend Framework:** Spring Boot 3.5.7
* **Language:** Java 25
* **Database:** PostgreSQL
* **Security:** Spring Security 6 (Stateless Session management, BCrypt Password Hashing)
* **ORM:** Spring Data JPA
* **Authentication:** JWT (jjwt library)
* **Scheduling:** Spring `@Scheduled`
* **Build Tool:** Gradle

---

## Architecture & Database

The application follows a standard **Controller-Service-Repository** layered architecture.

### Key Entities
1.  **User:** Stores user credentials and profile info.
2.  **Role:** Handles permissions (`ROLE_USER`, `ROLE_ADMIN`).
3.  **Subscription:** Stores details like price, billing cycle, and trial dates.

**Relationships:**
* `User` ↔ `Role` (Many-to-Many)
* `User` ↔ `Subscription` (One-to-Many)

---

## Getting Started

### Prerequisites
* Java Development Kit (JDK) 25
* PostgreSQL installed and running
* Gradle

### 1. Clone the Repository
```bash
git clone git@github.com:raushan17raj/springboot-subscription-tracker.git
cd subsentry
