# 🛒 E-Commerce Microservices System

A **Spring Boot–based microservices architecture** demonstrating **secure, scalable backend development**.

---

## 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)

---

## 🏗 Services

| Service | Description |
|--------|-------------|
| API Gateway | Handles routing & security |
| Service Registry (Eureka) | Service discovery |
| Config Server | Centralized configuration |
| Authentication Service | JWT-based authentication & role-based access |
| Product Service | CRUD operations for products |
| Order Service | Handles orders & payments |

---

## 🔧 Architecture

- **API Gateway**: routes requests to correct service  
- **Eureka Service Registry**: discovers running services dynamically  
- **Config Server**: centralizes application configs  
- **JWT Authentication**: secure communication between services  
- Dockerized services for local deployment  


---

## 🚀 How to Run Locally

```bash
# Build and start all services
docker-compose up --build
