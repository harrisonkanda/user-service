# Users microservice

A small **Spring Boot** service that exposes a read-only HTTP API for user directory data. It uses an **embedded H2 in-memory database** (no PostgreSQL install required); on startup the schema is created via Hibernate and **10 demo users** are loaded automatically. HTTP contracts are documented with **OpenAPI/Swagger** (via [springdoc-openapi](https://springdoc.org/)). **Spring Security is intentionally not used** in this revision.

---

## Capabilities

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/users` | **Paginated** list of users (Spring Data `page`, `size`, `sort`). |
| `GET` | `/api/users/{id}` | **Single user** by numeric primary key. `404` with RFC 9457-style `ProblemDetail` if missing. |

Interactive documentation: **Swagger UI** — after starting the app, open [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html).

Raw OpenAPI JSON: [http://localhost:8082/api-docs](http://localhost:8082/api-docs).

---

## User model

Each user is represented by **nine persisted attributes** (plus surrogate `id`), which stays within the **maximum of ten fields** requested for user information:

| Field | Description |
|-------|-------------|
| `id` | Database-generated primary key. |
| `email` | Unique email address. |
| `fullName` | Display name. |
| `phoneNumber` | Optional phone (E.164-style where applicable). |
| `status` | `ACTIVE` or `INACTIVE`. |
| `country` | Country or primary location. |
| `jobTitle` | Role or title. |
| `createdAt` | UTC creation time. |
| `updatedAt` | UTC last update time. |

JSON property names follow **camelCase** (e.g. `fullName`, `phoneNumber`).

---

## Prerequisites

- **JDK 21** (aligned with `build.gradle`).
- No external database — H2 runs **in-process**; data is **lost when the JVM exits**.

---

## Database behaviour

| Topic | Detail |
|--------|--------|
| Engine | H2, in-memory (`jdbc:h2:mem:usersdb`), PostgreSQL compatibility mode for naming/types. |
| Schema | Created by Hibernate (`spring.jpa.hibernate.ddl-auto: create-drop` — schema recreated on each startup). |
| Demo data | `DemoDataLoader` inserts 10 sample users when the `users` table is empty (disabled for the `test` profile). |

---

## Configuration highlights

| Setting | Default |
|--------|---------|
| Server port | `8082` |
| JDBC URL | In-memory H2 (see `application.yaml`) |
| JPA `ddl-auto` | `create-drop` |
| Max page size | `100` (`spring.data.web.pageable.max-page-size`) |
| Default page size | `20` |

Tests run with profile **`test`** and their own in-memory H2 URL; `DemoDataLoader` is **not** registered, so tests control seed data themselves.

---

## Run locally

```bash
./gradlew bootRun
```

Smoke-test the two endpoints:

```bash
# First page (default size 20)
curl -s "http://localhost:8082/api/users" | jq .

# Custom page size and sort
curl -s "http://localhost:8082/api/users?page=0&size=5&sort=email,asc" | jq .

# Single user (e.g. id 1 after demo seed)
curl -s "http://localhost:8082/api/users/1" | jq .
```

---

## Docker

Build the JAR, then build the image (see `Dockerfile`). The container **does not** need Postgres; map the host port to **`8082`** (container listens on `8082`):

```bash
./gradlew bootJar
docker build -t user-service:local .
docker run --rm -p 8082:8082 user-service:local
```

---

## Build and test

```bash
./gradlew clean build
```

The build runs tests under `src/test/java`. No Spring Security filters are registered.

---

## Project layout (relevant parts)

```
src/main/java/.../user/      # Domain entity, repository, service, REST controller
src/main/java/.../config/    # OpenAPI bean, exception handling, demo data loader
src/main/resources/
  application.yaml           # H2, JPA, springdoc
src/test/resources/
  application-test.yaml      # H2 for tests; demo loader off via profile
```

---

## API documentation quality

- Controller methods carry **OpenAPI** `@Operation`, `@ApiResponses`, `@Parameter`, and `@Tag` annotations.
- Response records (`UserResponse`, `PaginatedUsersResponse`) include **`@Schema` descriptions**.
- A shared **`OpenAPI` bean** describes the service title, version, and support contacts in Swagger UI.

---

## Security stance

This milestone is **deliberately open**: there is **no** `spring-boot-starter-security` dependency and no HTTP basic, JWT, or OAuth integration.

---

## License and contact

Internal/review use — adjust the `OpenApiConfig` contact block and this section before external distribution.
