# Eurovision Project

A REST API for managing Eurovision Song Contest participation, jury/public voting, and results.

## Tech Stack

- **Backend:** Java 21, Spring Boot
- **Database:** MySQL 8.4
- **Auth:** JWT
- **Docs:** Swagger UI (SpringDoc OpenAPI)
- **Containerization:** Docker + Docker Compose

## Diagrams

- [Entity-Relationship Diagram (ERD)](diagrams/ERD-Eurovision.pdf)
- [Extended Entity-Relationship Model (EERM)](diagrams/EERM-Eurovision.pdf)

## Getting Started

### Prerequisites

- Docker & Docker Compose

### Run

```bash
docker-compose up
```

App runs on `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui/index.html`

### Environment Variables

| Variable | Description |
|---|---|
| `MYSQL_ROOT_PASSWORD` | MySQL root password |
| `MYSQL_DATABASE` | Database name |
| `MYSQL_USER` | DB user |
| `MYSQL_PASSWORD` | DB password |
| `JWT_SECRET` | JWT signing secret (32+ chars) |
| `SERVER_URL` | Base URL for Swagger UI |

## API Overview

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT |

### Voting
| Method | Endpoint | Description |
|---|---|---|
| POST | `/votes/jury` | Submit a jury vote |
| POST | `/votes/public` | Submit a public vote |

### Shows & Songs
| Method | Endpoint | Description |
|---|---|---|
| GET | `/shows` | List all shows |
| GET | `/shows/{id}/songs` | Songs in a show |
| GET | `/shows/{id}/countries` | Countries in a show |
| GET | `/songs` | List all songs |

### Results
| Method | Endpoint | Description |
|---|---|---|
| GET | `/results/{showId}` | Get results for a show |

### Admin
| Method | Endpoint | Description |
|---|---|---|
| POST | `/admin/shows/{id}/start` | Open voting for a show |
| POST | `/admin/shows/{id}/stop` | Close voting for a show |

## Project Structure

```
src/main/java/.../eurovisionproject/
├── controller/     # REST endpoints
├── service/        # Business logic
├── entities/       # JPA models
├── repositories/   # Data access
├── security/       # JWT filter & utilities
├── dto/            # Request/response DTOs
└── init/           # Database seeder
```
