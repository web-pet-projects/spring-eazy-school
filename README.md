# 📘 Spring Boot Fullstack Demo – School Portal

This is a **multi-module Spring Boot project** designed to **practice and demonstrate real-world backend development skills** using Spring Framework. It combines multiple core Spring features including **JPA**, **Spring Security**, **MVC**, **Profiles**, **HATEOAS (HAL Explorer)**, and **Actuator** monitoring.

The frontend is adapted from prebuilt sources, but **heavily customized** to add backend features and Spring MVC integration.

---

## 🧱 Project Structure

```
├── app/       # Main application module (students, profile, contact, etc.)
├── admin/     # Admin module (admin dashboard and access control)
├── diaries/   # Feature module for diary content management
```

---

## 🚀 Features

* 📬 **Contact Message Management** – CRUD with message status, timestamp, and reply
* 👤 **User Profile Page** – View/update profile info with session-based access
* 🔐 **Authentication & Authorization**
  * Role-based access (`ROLE_ADMIN`, `ROLE_STUDENT`)
  * Simple `username/password` login with custom `AuthenticationProvider`
* 📑 **Form Validation** – Includes standard and custom validation annotations
* 📆 **Audit Tracking** – Auto-capture `createdBy`, `createdAt`, `updatedBy`, etc.
* 🛠️ **Logging Aspect** – Cross-cutting log using Spring AOP
* 🧩 **Spring HAL Explorer** – Discover REST resources
* 📡 **Spring Boot Actuator** – Health checks and metrics

---

## 🔧 Spring Concepts Covered

| Concept         | Implementation Example                                               |
| --------------- | -------------------------------------------------------------------- |
| Spring MVC      | Controllers for form and data handling                               |
| Spring Security | Custom `UserDetails`, `UserDetailsService`, `AuthenticationProvider` |
| Spring Data JPA | `@Entity`, `@MappedSuperclass`, derived queries                      |
| AOP             | Aspect for method logging                                            |
| Validation      | Hibernate Validator, custom annotation rules                         |
| Profiles        | `application-dev.properties`, `@Profile` beans                       |
| Actuator        | Enabled `/actuator/health`, `/info`, etc.                            |
| HATEOAS (HAL)   | `/profile`, `/contacts` with hypermedia links                        |

---

## 🧠 Highlights

* 🏗️ `BaseEntity` and `AuditListener` pattern for consistent auditing across tables
* 🪝 Proper use of `@ControllerAdvice`, `@ExceptionHandler` for global error handling
* 🧪 Demonstrates custom form validation (`@FieldMatch`) using `ConstraintValidator`
* 📂 Profile-based configuration for **dev**, **uat**, and **prod**
* ⚙️ Integration of `JdbcTemplate` and `RowMapper` for low-level DB operations (bonus knowledge)
* 🧾 Full-stack Spring MVC example with Thymeleaf, no REST-only restriction
* 🛠️ Understanding of session management vs. stateless APIs
* 🌍 Shows how to consume and expose endpoints with modern standards

---

## ☁️ AWS Deployment (Simple EC2 Setup)

1. **Package** the app with Maven:

   ```bash
   mvn clean package
   ```
2. **Provision EC2**, install Java 17+, upload the JAR
3. **Set environment variables**:

   ```bash
   SPRING_PROFILES_ACTIVE=uat
   SERVER_PORT=5000
   SPRING_DATASOURCE_URL=jdbc:postgresql://<rds-url>:5432/yourdb
   SPRING_DATASOURCE_USERNAME=admin
   SPRING_DATASOURCE_PASSWORD=secret
   ```
4. **Run the app**:

   ```bash
   java -jar yourapp.jar
   ```

---

## 🙏 Special Thanks

* Based on training content from [EazyBytes](https://github.com/eazybytes)
* Additional integrations and modules implemented for deeper learning
