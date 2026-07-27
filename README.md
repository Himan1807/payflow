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

## System Architecture

                 ┌──────────────┐
                 │    Client    │
                 └──────┬───────┘
                        │
                 ┌──────▼───────┐
                 │ API Gateway  │
                 └──────┬───────┘
                        │
      ┌─────────────────┼───────────────────┐
      ▼                 ▼                   ▼
┌──────────┐     ┌──────────┐      ┌─────────────┐
│   Auth   │     │   User   │      │  Payment    │
└──────────┘     └──────────┘      └──────┬──────┘
                                          │
                          ┌───────────────┴───────────────┐
                          ▼                               ▼
                  ┌──────────────┐               ┌────────────────┐
                  │   Account    │               │ Notification   │
                  └──────────────┘               └────────────────┘

────────────────────────────────────────────────────────────────────

 Eureka Server | Config Server | MySQL | Zipkin | Docker | Kubernetes

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

## Roadmap

- [x] Project planning
- [x] System architecture
- [ ] Config Server
- [ ] Eureka Naming Server
- [ ] API Gateway
- [ ] JWT Authentication
- [ ] User Service
- [ ] Account Service
- [ ] Payment Service
- [ ] Notification Service
- [ ] Distributed Tracing (Zipkin)
- [ ] Resilience4J
- [ ] Docker
- [ ] Kubernetes
- [ ] Kafka Integration
- [ ] Redis Caching
- [ ] OAuth2 Integration

## Future Enhancements

- Kafka
- Redis
- OAuth2
- Prometheus
- Grafana

## Author

**Himanshu Singh**
