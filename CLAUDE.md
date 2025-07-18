# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot microservice (rebook-user-service) that handles user management and authentication for the Rebook platform. The service is part of a larger microservices architecture using Spring Cloud with Eureka for service discovery and Spring Cloud Config for centralized configuration.

## Development Commands

### Build and Run
```bash
# Build the project
./gradlew build

# Run the application
./gradlew bootRun

# Build Docker image
docker build -t rebook-user-service .

# Run with specific profile
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Testing
```bash
# Run all tests
./gradlew test

# Run tests with coverage report
./gradlew test jacocoTestReport

# View coverage report (generated in build/reports/jacoco/test/html/index.html)
open build/reports/jacoco/test/html/index.html
```

### Code Quality
```bash
# Clean build
./gradlew clean build

# Check dependencies
./gradlew dependencies

# View test results
./gradlew test --info
```

## High-Level Architecture

### Microservices Integration
- **Service Discovery**: Uses Netflix Eureka (`@EnableDiscoveryClient`)
- **Configuration Management**: Spring Cloud Config client connecting to rebook-config service
- **Inter-Service Communication**: OpenFeign clients (`@EnableFeignClients`)
- **API Documentation**: Swagger UI available at `/swagger-ui.html`

### Authentication Architecture
The service implements a dual-token authentication system:

1. **External Authentication**: Keycloak integration for OAuth2/OpenID Connect
   - Users authenticate via Keycloak and receive external access tokens
   - `KeycloakJwtUtil` validates and extracts user information from Keycloak tokens

2. **Internal JWT System**: Custom JWT tokens for service-to-service communication
   - `JwtUtil` creates internal access/refresh tokens after Keycloak validation
   - Redis-based refresh token caching with `refresh:` prefix
   - New users are automatically created with default nickname and profile image

### Core Components

#### Controllers
- **AuthController** (`/api/auths`): Login, token refresh, test endpoints
- **UsersController** (`/api/users`): User CRUD operations, profile management
- **ReaderController** (`/api/readers`): User reading preferences and book interactions
- **FavoriteCategoryController** (`/api/categories`): User favorite category management

#### Services & Business Logic
- **AuthService**: Handles login flow, token management, user creation
- **UsersService**: User profile operations, updates, retrieval
- **KeycloakService**: Integration with Keycloak identity provider
- **S3Service**: File upload/download for profile images using AWS S3
- **RedisService**: Caching layer for session management and performance

#### Data Layer
- **JPA Entities**: Users, UserBook, UserTrading, FavoriteCategory with composite keys
- **PostgreSQL**: Primary database for user data persistence
- **Redis**: Session storage and caching layer
- **Composite Keys**: UserBookId, UserTradingId, FavoriteCategoryId for many-to-many relationships

### Configuration Profiles
- **dev**: Development environment, connects to rebook-config:8888
- **prod**: Production environment configuration
- **Default**: Falls back to dev profile

### External Integrations
- **Keycloak**: User authentication and identity management
- **AWS S3**: Profile image storage and management
- **PostgreSQL**: User data persistence
- **Redis**: Session management and caching
- **RabbitMQ**: Message queue integration for event-driven communication
- **Prometheus**: Metrics collection via Micrometer
- **Sentry**: Error tracking and monitoring

### Exception Handling
Centralized exception handling via `GlobalExceptionHandler` with custom exceptions:
- `CMissingDataException`: Missing or invalid data
- `CDuplicatedDataException`: Duplicate data conflicts
- `CInvalidDataException`: Data validation failures

### Response Structure
Standardized API responses using:
- `SingleResult<T>`: Single object responses
- `ListResult<T>`: Collection responses
- `CommonResult`: Base response with status and message
- `ResponseService`: Factory for creating standardized responses

## Key Implementation Notes

### User Creation Flow
1. User authenticates via Keycloak (external system)
2. Service validates Keycloak token and extracts user information
3. If user doesn't exist locally, creates new Users entity with default values
4. Generates internal JWT tokens for subsequent API calls
5. Caches refresh token in Redis for session management

### Database Relationships
- Users can have multiple books (`UserBook` with `UserBookId` composite key)
- Users can have trading relationships (`UserTrading` with `UserTradingId`)
- Users can set favorite categories (`FavoriteCategory` with `FavoriteCategoryId`)

### JWT Token Management
- Access tokens: Short-lived, used for API authentication
- Refresh tokens: Cached in Redis with `refresh:` prefix for renewal
- Token validation includes user ID extraction and Redis cache verification