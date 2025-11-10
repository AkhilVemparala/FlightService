### Overview
The **FlightService** manages all flight-related operations including flight search, creation, and availability.  
It acts as the data provider for BookingService when a user books a ticket.

---

## ⚙️ Tech Stack
| Component | Technology |
|------------|-------------|
| Framework | Spring Boot 3.x |
| Database | MySQL / JPA |
| Discovery | Eureka Client |
| Build Tool | Maven |
| Logging | Logback |

---

## 🏗️ Core Layers
- **Controller:** `FlightController` — manages CRUD endpoints for flights.
- **Service:** `FlightServiceImpl` — handles flight business logic.
- **Repository:** `FlightRepository` — JPA repository for persistence.
- **Entity:** `Flight` — represents flight metadata (route, seats, price).

---

## 🔌 Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| GET | `/flights` | Get all flights |
| GET | `/flights/{id}` | Get flight details |
| POST | `/flights` | Add new flight |
| PUT | `/flights/{id}` | Update flight |
| DELETE | `/flights/{id}` | Delete flight |
| POST | `/flights/search` | Search flights by criteria |

---

## ⚙️ Exception Handling
- `FlightServiceException` — handles runtime issues.
- `GlobalExceptionHandler` — returns unified JSON error format.

---

## 📈 Future Enhancements
- Add caching for flight search results (Redis).
- Publish flight update events via Kafka.
- Integrate seat availability microservice.
