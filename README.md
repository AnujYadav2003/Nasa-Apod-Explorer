# Nasa-Apod-Explorer

A simple Spring Boot + HTML/JavaScript project that displays NASA's Astronomy Picture of the Day (APOD).  

Features include date-based search, today's APOD, recent N days, and in-memory caching using Caffeine.

## Project Structure

```
src/main/java/com/nasa/nasaapod/
├── client/ApodClient.java
├── controller/
│   ├── ApodController.java
│   └── CacheStatsController.java
├── service/ApodService.java
├── config/CacheConfig.java
├── exception/GlobalExceptionHandler.java
└── model/ApodResponse.java

src/main/resources/
├── application.yml
└── static/index.html
```

### Backend (REST APIs)

| Endpoint | Description |
|---------|-------------|
| `GET /api/apod/today` | Get today's APOD |
| `GET /api/apod/date?date=YYYY-MM-DD` | Get APOD for a specific date |
| `GET /api/apod/recent?days=N` | Get last N APOD entries |
| `GET /api/cache/stats` | View cache hit/miss statistics |

### API Calls

```
curl http://localhost:8081/api/apod/today

curl "http://localhost:8081/api/apod/date?date=2024-01-15"

curl "http://localhost:8081/api/apod/recent?days=5"
```

## Requirements

- Java 21+
- Maven 3.9+ (or use the included `mvnw`/`mvnw.cmd`)
- NASA API key (generate at https://api.nasa.gov)
- Internet access to reach the NASA APOD API

## Getting Started

1. Clone this repo and move into the directory.
2. Provide your API key (pick the snippet for your OS):
   - PowerShell: `setx NASA_API_KEY "<your-key>" && refreshenv`
   - Bash/zsh: `export NASA_API_KEY=<your-key>`
3. Launch the service:
   - macOS/Linux: `./mvnw spring-boot:run`
   - Windows: `mvnw.cmd spring-boot:run`
4. Open `http://localhost:8081` in a browser to load the bundled UI.

## Run & Test

- Run locally: `./mvnw spring-boot:run`
- Package an executable jar: `./mvnw clean package`
- Execute automated tests: `./mvnw test`

## Configuration

Key settings live in `src/main/resources/application.yml`:

- `apod.base-url`: NASA APOD endpoint (defaults to `https://api.nasa.gov/planetary/apod`)
- `NASA_API_KEY`: read from the environment; pass as `-DNASA_API_KEY=...` for ad‑hoc runs
- `server.port`: defaults to `8081`; change if you need a different port
- `spring.cache.caffeine.spec`: cache tuning (size + TTL) used by `apodCache`

## Caching (Caffeine)

- In-memory caching using **Caffeine**
- Cache name: `apodCache`
- Max size: **200**
- Expiry: **1 hour**
- Cache stats enabled (hitCount, missCount, evictionCount, etc.)

## Frontend (HTML + JS)

Simple UI located at:

```
src/main/resources/static/index.html
```

UI includes:
- Today's APOD
- Search by date
- View recent N days
- Simple, minimal design

## UI Preview (Placeholder)

![Home screen placeholder] <img width="1004" height="543" alt="nasa1" src="https://github.com/user-attachments/assets/4ae9641a-5db6-4579-9a1f-3d1acd616c3e" />


![nasa2](https://github.com/user-attachments/assets/65a5c539-8432-4a04-93be-8bb6c6a1edd5)
