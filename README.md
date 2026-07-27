# PayFlow

A UPI-inspired backend payment platform built using Spring Boot Microservices.

The project demonstrates distributed system design, service discovery, API Gateway, JWT authentication, inter-service communication, distributed tracing, containerization, and orchestration.

## Tech Stack

- Java 21
- Spring Boot
- Spring Cloud
- Spring Security
- JWT Authentication
- Eureka Naming Server
- Spring Cloud Gateway
- OpenFeign
- Spring Data JPA
- MySQL
- Docker
- Kubernetes
- Zipkin & Micrometer
- Resilience4J

## Architecture

Client
    │
API Gateway
    │
─────────────────────────────
│     │      │      │
Auth User Account Payment
               │
        Notification

## Services

- Auth Service
- User Service
- Account Service
- Payment Service
- Notification Service
- API Gateway
- Config Server
- Eureka Naming Server

## Features

- JWT Authentication
- User Registration
- Bank Account Management
- Money Transfer
- Transaction History
- Service Discovery
- API Gateway Routing
- Distributed Tracing
- Circuit Breaker
- Retry Mechanism
- Rate Limiting
- Dockerized Deployment
- Kubernetes Deployment

## Future Enhancements

- Kafka
- Redis
- OAuth2
- Prometheus
- Grafana
