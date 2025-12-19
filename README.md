# 🛡️ Spring Boot JWT Authentication Project

This project is a **Spring Boot REST API** that implements **JWT-based authentication and authorization** with **User, Category, and Product management**.

It follows **clean architecture** using:

* Controller layer
* Service layer
* Repository layer
* DTOs & Mappers
* Spring Security with JWT

---

## 🚀 Features

* User Registration & Login
* JWT Token Generation & Validation
* Role-based Authentication
* Secure APIs using Spring Security
* Category & Product CRUD APIs
* Stateless Authentication (No Sessions)

---

## 🧱 Project Structure

```
src/main/java/com/categories/product
│
├── controller/
│   ├── UserController
│   ├── CategoryController
│   └── ProductController
│
├── dto/
│   ├── UserRequestDTO
│   ├── UserResponseDTO
│   ├── CategoryRequest
│   ├── CategoryResponse
│   ├── ProductRequest
│   ├── ProductResponse
│   └── ExceptionResponseDTO
│
├── entities/
│   ├── User
│   ├── Role
│   ├── Category
│   └── Product
│
├── mapper/
│   ├── UserMapper
│   ├── CategoryMapper
│   └── ProductMapper
│
├── repositories/
│   ├── UserRepository
│   ├── CategoryRepository
│   └── ProductRepository
│
├── serviceImpl/
│   ├── CustomUserDetailsService
│   ├── CategoryServiceImpl
│   └── ProductServiceImpl
│
├── security/
│   ├── JwtUtil
│   ├── JwtRequestFilter
│   ├── SecurityConfig
│   ├── UserPrincipal
│   ├── JwtAccessDeniedHandler
│   └── JwtAuthenticationEntryPoint
```

---

## 🔐 Security Architecture (JWT)

### Main Security Components

| Component                  | Responsibility                           |
| -------------------------- | ---------------------------------------- |
| `JwtUtil`                  | Generate & validate JWT tokens           |
| `JwtRequestFilter`         | Intercepts every request & validates JWT |
| `SecurityConfig`           | Configures Spring Security rules         |
| `UserPrincipal`            | Adapts User entity for Spring Security   |
| `CustomUserDetailsService` | Loads user from DB for authentication    |

---

## 🔄 Authentication Flow

### 1️⃣ User Login

```
Client → UserController → AuthenticationManager
       → CustomUserDetailsService → UserPrincipal
       → JwtUtil → JWT Token → Client
```

### 2️⃣ Access Protected API

```
Client (JWT)
   ↓
JwtRequestFilter
   ↓
JwtUtil validates token
   ↓
SecurityContextHolder populated
   ↓
Controller executes
```

---

## 👤 Why `UserDetailsService` is Used?

Spring Security **does not know how to read users from your database**.

So:

* You implement `UserDetailsService`
* Override `loadUserByUsername()`
* Fetch user from DB
* Convert it into `UserPrincipal`

```java
return new UserPrincipal(user.get());
```

👉 This makes your `User` understandable to Spring Security.

---

## 🪪 What is `UserPrincipal`?

* Wraps `User` entity
* Implements `UserDetails`
* Provides:

  * Username
  * Password
  * Roles (Authorities)

📌 Think of it as a **passport** for Spring Security.

---

## 🧠 Why Optional & `user.get()`?

```java
Optional<User> user = userRepository.findByUsername(username);
return new UserPrincipal(user.get());
```

* `Optional` avoids `NullPointerException`
* `user.get()` extracts actual `User` object
* That user is wrapped inside `UserPrincipal`

---

## 🔑 API Security Rules

```
/user/register → Public
/user/login    → Public
/api/** (GET)  → Public (Optional)
Others         → JWT Required
```

---

## 🧪 Sample Authorization Header

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## 🛠️ Tech Stack

* Java 17+
* Spring Boot
* Spring Security
* JWT (jjwt)
* Spring Data JPA
* MySQL / PostgreSQL
* Maven

---

## 📌 Key Takeaways

* `SecurityConfig` → Security rules & filters
* `JwtRequestFilter` → Runs on every request
* `JwtUtil` → Token generator & validator
* `UserPrincipal` → Bridge between DB User & Spring Security
* `CustomUserDetailsService` → Loads user from DB

---

## 📖 Author

**Abhishek Brahmbhatt**
Spring Boot & Backend Developer 🚀

---

## ⭐ Final Note

This project follows **industry-standard JWT authentication architecture** and is ideal for:

* Learning Spring Security
* Backend interviews
* Real-world REST APIs

Feel free to extend it with **refresh tokens, OAuth, or RBAC**.
