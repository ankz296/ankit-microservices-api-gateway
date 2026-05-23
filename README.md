<div align="center">

# Hey, I'm Ankit Agrawal 👋

**Senior Backend Engineer** — I build distributed systems that scale.

*Java 17 · Spring Boot 3.5 · Microservices · Apache Kafka · GCP · Kubernetes*

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://linkedin.com/in/ankitagrawal296)
[![Email](https://img.shields.io/badge/Gmail-D14836?style=flat&logo=gmail&logoColor=white)](mailto:ankitagrawal296@gmail.com)
[![Profile Views](https://komarev.com/ghpvc/?username=ankz296&color=185FA5&style=flat)](https://github.com/ankz296)

</div>

---

## 👨‍💻 About Me

8.6 years building production-grade backend systems — from a **PM-launched national financial inclusion platform serving 1.2M+ users** to **GCP-native microservice architectures on GKE** with full observability.

I don't just write code — I think in systems. My focus is on the hard parts:

- ⚡ **Distributed transactions** without 2-phase commit (Kafka Saga + Outbox Pattern)
- 🔐 **Secure API design** — JWT, OAuth2, rate limiting, AES encryption
- 📊 **Full observability** — Prometheus, Grafana, Loki, Jaeger (OTLP distributed tracing)
- ☁️ **Cloud-native deployment** — GKE, Cloud SQL, Artifact Registry, Secret Manager
- 🛡️ **Resilience** — Circuit Breaker, Retry, Bulkhead via Resilience4j

> *"I learn system design by implementing it end to end — not just by drawing diagrams."*

---

## 🛠️ Tech Stack

**Backend**

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.5-6DB33F?style=flat&logo=spring-boot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=flat&logo=spring&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=flat&logo=apache-kafka&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white)

**Databases**

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-4EA94B?style=flat&logo=mongodb&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=flat&logo=mysql&logoColor=white)

**Cloud & DevOps**

![GCP](https://img.shields.io/badge/GCP-4285F4?style=flat&logo=google-cloud&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=flat&logo=kubernetes&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat&logo=amazon-aws&logoColor=white)

**Observability & Security**

![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=flat&logo=prometheus&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800?style=flat&logo=grafana&logoColor=white)
![Datadog](https://img.shields.io/badge/Datadog-632CA6?style=flat&logo=datadog&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat&logo=json-web-tokens&logoColor=white)

---

## 🚀 Featured Project

### [Ecommerce Microservices Platform](https://github.com/ankz296/ecommerce-microservices)

> A production-grade 7-service distributed backend deployed on GCP GKE — built to implement senior-level system design concepts end to end.

```
Client → API Gateway (JWT + OAuth2 + Redis Rate Limit)
              ↓
    ┌─────────┬──────────┬──────────┐
 User Svc  Product Svc  Order Svc  Payment Svc
(PostgreSQL) (MongoDB) (PostgreSQL)(PostgreSQL)
              ↓              ↓
           Apache Kafka (Saga + Outbox + Idempotency)
              ↓
       Notification Svc
              ↓
   Prometheus · Grafana · Loki · Jaeger
```

**What makes it non-trivial:**

| Pattern | Implementation |
|---|---|
| Distributed transactions | Kafka Saga choreography — no 2PC |
| Event reliability | Transactional Outbox Pattern |
| Duplicate prevention | Idempotent consumers (`processed_events` table) |
| Gateway resilience | Resilience4j Circuit Breaker + Retry + Bulkhead |
| Rate limiting | Redis token bucket — per user, horizontally scalable |
| Observability | OTLP distributed tracing across all services via Jaeger |
| Deployment | GKE + Cloud SQL + Artifact Registry + Secret Manager |

---

## 💼 Experience Highlights

| Company | Role | Domain |
|---|---|---|
| **UST** *(current)* | Senior Software Engineer | GCP automation, retail microservices, Datadog observability |
| **Online PSB Loans** | Senior Software Engineer | National loan platform — 1.2M+ users, PM-launched |
| **Wipro** | Senior Software Engineer | Gujarat Govt. IFMS 2.0 — statewide payroll system |
| **EMXCEL Travel** | Software Engineer | Real-time cab booking — 25+ APIs, Firebase push notifications |

---

## 📈 What I'm Currently Focused On

- 🏗️ Deepening distributed systems — AI-powered search, stronger CI/CD
- 📚 System design interview prep for senior/staff roles at product companies
- ☁️ AWS migration patterns alongside GCP

---

<div align="center">

*Open to Senior Backend Engineer / Java Architect roles at product-first companies.*

[![LinkedIn](https://img.shields.io/badge/Let's_Connect-0077B5?style=flat&logo=linkedin&logoColor=white)](https://linkedin.com/in/ankitagrawal296)

</div>
