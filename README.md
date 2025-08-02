# Rebook User Service

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-336791)
![Redis](https://img.shields.io/badge/Redis-6+-DC382D)
![Keycloak](https://img.shields.io/badge/Keycloak-26.0.5-4D4D4D)
![AWS S3](https://img.shields.io/badge/AWS-S3-FF9900)

**Rebook 플랫폼의 사용자 관리 및 인증 마이크로서비스**

Keycloak 기반 OAuth2 인증 | 프로필 관리 | 선호도 시스템 | 서비스 간 통신


</div>

---

## 📋 목차

- [개요](#개요)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [API 문서](#api-문서)
- [아키텍처](#아키텍처)
- [개발 가이드](#개발-가이드)

## 개요

**Rebook User Service**는 Rebook 도서 거래 플랫폼의 핵심 마이크로서비스로, 사용자 생명주기 전반을 관리합니다. Keycloak을 통한 안전한 외부 인증과 내부 JWT 토큰 시스템을 결합하여 높은 보안성과 확장성을 제공합니다.

### 핵심 역할

- **인증 및 권한 관리**: Keycloak 기반 OAuth2/OIDC 인증 및 내부 JWT 토큰 발급
- **사용자 프로필**: 프로필 정보 관리, AWS S3 기반 이미지 업로드
- **선호도 시스템**: 사용자별 선호 카테고리 관리 및 맞춤 추천
- **마이크로서비스 통합**: Eureka 서비스 디스커버리 및 Feign 기반 서비스 간 통신

## 주요 기능

### 🔐 이중 인증 시스템 (Dual Authentication)

외부 인증과 내부 토큰 관리를 결합한 하이브리드 인증 아키텍처

```
Keycloak (외부)  →  사용자 인증 및 ID 검증
       ↓
User Service     →  내부 JWT 토큰 발급 (Access + Refresh)
       ↓
Redis Cache      →  리프레시 토큰 캐싱 (세션 관리)
```

- **외부 인증**: Keycloak OAuth2/OpenID Connect
- **내부 토큰**: 마이크로서비스 간 통신용 JWT
- **토큰 갱신**: Redis 기반 리프레시 토큰 자동 갱신

### 👤 사용자 프로필 관리

- **CRUD 작업**: 사용자 정보 생성, 조회, 수정, 삭제
- **프로필 이미지**: AWS S3 기반 이미지 업로드 및 관리
- **계정 정보**: 닉네임, 이메일, 비밀번호 수정
- **자동 생성**: Keycloak 로그인 시 사용자 자동 생성 (기본 닉네임 + 프로필 이미지)

### 📚 선호도 및 추천 시스템

- **선호 카테고리**: 사용자별 선호 카테고리 설정 및 관리
- **맞춤 추천**: 사용자 선호도 기반 카테고리 추천
- **독서 기록**: 사용자 도서 상호작용 및 거래 이력 관리

### 🌐 마이크로서비스 통합

- **서비스 디스커버리**: Netflix Eureka 기반 동적 서비스 등록/발견
- **중앙 설정**: Spring Cloud Config를 통한 외부화된 설정 관리
- **서비스 간 통신**: OpenFeign 선언적 HTTP 클라이언트
- **이벤트 기반**: RabbitMQ를 통한 비동기 메시징

## 기술 스택

### 📦 핵심 프레임워크

| 기술 | 버전 | 용도 |
|------|------|------|
| Java | 17 | 런타임 환경 |
| Spring Boot | 3.3.13 | 애플리케이션 프레임워크 |
| Gradle | 8.14.2 | 빌드 도구 |

### ☁️ Spring Cloud 생태계

| 컴포넌트 | 버전 | 역할 |
|----------|------|------|
| Spring Cloud | 2023.0.5 | 마이크로서비스 인프라 |
| Config Client | - | 중앙 설정 관리 |
| Eureka Client | - | 서비스 디스커버리 |
| OpenFeign | - | 선언적 REST 클라이언트 |

### 💾 데이터 저장 및 캐싱

| 기술 | 목적 | 주요 사용처 |
|------|------|------------|
| PostgreSQL | 관계형 DB | 사용자, 선호도, 거래 데이터 |
| Redis | 인메모리 캐시 | 리프레시 토큰, 세션 관리 |
| AWS S3 | 객체 스토리지 | 프로필 이미지 저장 |
| Spring Data JPA | ORM | 엔티티 매핑 및 쿼리 |

### 🔒 보안 및 인증

| 기술 | 버전 | 역할 |
|------|------|------|
| Keycloak | 26.0.5 | OAuth2/OIDC 인증 제공자 |
| JJWT | 0.12.5 | JWT 생성 및 검증 |
| Custom JWT System | - | 내부 토큰 관리 |

### 📊 모니터링 및 관측성

| 도구 | 용도 |
|------|------|
| Spring Boot Actuator | 헬스체크 및 메트릭 엔드포인트 |
| Micrometer + Prometheus | 애플리케이션 메트릭 수집 |
| Sentry | 실시간 에러 추적 및 알림 |

### 🔧 기타 도구

- **RabbitMQ (AMQP)**: 비동기 메시징 및 이벤트 기반 통신
- **Swagger/OpenAPI**: API 문서 자동 생성 (SpringDoc 2.6.0)
- **Lombok**: 보일러플레이트 코드 제거
- **Jacoco**: 테스트 커버리지 리포트

## API 문서

### 📖 Swagger UI

애플리케이션 실행 후 대화형 API 문서에 접근할 수 있습니다:

```
🌐 http://localhost:8080/swagger-ui.html
```

### 🔑 API 엔드포인트

#### 🔐 인증 (Authentication)

**Base URL**: `/api/auths`

| Method | Endpoint | 설명 | 인증 필요 |
|--------|----------|------|-----------|
| POST | `/login` | Keycloak 토큰으로 로그인 | ❌ |
| POST | `/refresh` | 액세스 토큰 갱신 | ❌ |
| GET | `/test` | 헬스 체크 | ❌ |

<details>
<summary><b>로그인 요청 예시</b></summary>

```bash
curl -X POST http://localhost:8080/api/auths/login \
  -H "Content-Type: application/json" \
  -d '{
    "accessToken": "keycloak_access_token_here"
  }'
```

**응답:**
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "userId": 1,
    "accessToken": "internal_jwt_access_token",
    "refreshToken": "internal_jwt_refresh_token"
  }
}
```
</details>

#### 👤 사용자 관리 (Users)

**Base URL**: `/api/users`

| Method | Endpoint | 설명 | 인증 필요 |
|--------|----------|------|-----------|
| GET | `/` | 내 프로필 조회 | ✅ |
| GET | `/{userId}` | 특정 사용자 조회 | ✅ |
| PUT | `/` | 프로필 수정 (이미지 포함) | ✅ |
| DELETE | `/` | 계정 삭제 | ✅ |
| PATCH | `/me` | 비밀번호 변경 | ✅ |
| GET | `/categories` | 내 선호 카테고리 조회 | ✅ |
| GET | `/categories/recommendations/{userId}` | 추천 카테고리 조회 | ✅ |

<details>
<summary><b>프로필 수정 요청 예시</b></summary>

```bash
curl -X PUT http://localhost:8080/api/users \
  -H "Authorization: Bearer {access_token}" \
  -F "nickname=newNickname" \
  -F "email=new@email.com" \
  -F "file=@/path/to/profile.jpg"
```
</details>

#### 📚 선호 카테고리 (Favorite Categories)

**Base URL**: `/api/categories`

사용자별 선호 카테고리 설정 및 관리

#### 📖 독서 관리 (Readers)

**Base URL**: `/api/readers`

사용자 독서 기록, 선호도 및 도서 상호작용 관리

### 🔒 인증 방식

모든 보호된 엔드포인트는 JWT Bearer 토큰이 필요합니다:

```bash
Authorization: Bearer {your_access_token}
```

**토큰 획득 과정:**
1. Keycloak에서 사용자 인증
2. `/api/auths/login`에 Keycloak 토큰 전송
3. 내부 JWT 액세스/리프레시 토큰 수신
4. 이후 요청에 액세스 토큰 사용

## 아키텍처

### 🏗️ 시스템 아키텍처

```
                          ┌─────────────┐
                          │  Keycloak   │ (외부 인증)
                          └──────┬──────┘
                                 │ OAuth2/OIDC
                                 ▼
┌──────────┐         ┌────────────────────┐         ┌──────────────┐
│ Client   │◄────────┤  User Service      │────────►│ PostgreSQL   │
│ (Web/App)│  JWT    │  (Spring Boot)     │  JPA    │ (사용자 데이터)│
└──────────┘         └────────┬───────────┘         └──────────────┘
                              │
                              │
                              ▼
                   ┌──────────────────────┐
                   │  Eureka Discovery    │
                   │  Config Server       │
                   └──────────────────────┘
```

### 🔐 인증 흐름 (Authentication Flow)

#### 1️⃣ 사용자 로그인

```
┌─────────┐         ┌──────────┐         ┌──────────────┐         ┌──────────┐
│ Client  │         │ Keycloak │         │ User Service │         │PostgreSQL│
└────┬────┘         └─────┬────┘         └──────┬───────┘         └────┬─────┘
     │                    │                      │                      │
     │ 1. 인증 요청        │                      │                      │
     ├───────────────────►│                      │                      │
     │                    │                      │                      │
     │ 2. Keycloak Token  │                      │                      │
     │◄───────────────────┤                      │                      │
     │                    │                      │                      │
     │ 3. POST /api/auths/login (Keycloak Token) │                      │
     ├──────────────────────────────────────────►│                      │
     │                    │                      │                      │
     │                    │ 4. 토큰 검증          │                      │
     │                    │◄─────────────────────┤                      │
     │                    │                      │                      │
     │                    │ 5. 사용자 정보 추출    │                      │
     │                    │──────────────────────┤                      │
     │                    │                      │                      │
     │                    │                      │ 6. 사용자 조회/생성   │
     │                    │                      ├─────────────────────►│
     │                    │                      │                      │
     │                    │                      │ 7. 사용자 데이터      │
     │                    │                      │◄─────────────────────┤
     │                    │                      │                      │
     │                    │ 8. 내부 JWT 생성      │                      │
     │                    │      (Access+Refresh)│                      │
     │                    │                      │                      │
     │                    │ 9. Redis 캐싱         │                      │
     │                    │   (refresh:userId)   │                      │
     │                    │                      │                      │
     │ 10. 응답 (내부 JWT)                        │                      │
     │◄──────────────────────────────────────────┤                      │
```

#### 2️⃣ 토큰 갱신

```
Client → POST /api/auths/refresh (Refresh Token)
         │
         ├─► Redis에서 토큰 검증
         ├─► 유효성 확인
         └─► 새 Access Token 발급
```

## 개발 가이드

### 📁 프로젝트 구조

```
src/main/java/com/example/rebookuserservice/
├── 📂 advice/                    # 전역 예외 핸들러
│   └── GlobalExceptionHandler.java
│
├── 📂 clients/                   # Feign 클라이언트 (서비스 간 통신)
│   └── NotificationClient.java
│
├── 📂 common/                    # 공통 응답 모델
│   ├── CommonResult.java
│   ├── SingleResult.java
│   └── ListResult.java
│
├── 📂 config/                    # 설정 클래스
│   ├── RedisConfig.java         # Redis 설정
│   ├── S3Config.java            # AWS S3 설정
│   ├── KeycloakConfig.java      # Keycloak 설정
│   └── SwaggerConfig.java       # API 문서 설정
│
├── 📂 controller/                # REST API 컨트롤러
│   ├── AuthController.java      # 인증 API
│   ├── UsersController.java     # 사용자 관리 API
│   ├── ReaderController.java    # 독서 관리 API
│   └── FavoriteCategoryController.java
│
├── 📂 enums/                     # 열거형
│   └── Role.java                # 사용자 권한
│
├── 📂 exception/                 # 커스텀 예외
│   ├── CMissingDataException.java
│   ├── CDuplicatedDataException.java
│   └── CInvalidDataException.java
│
├── 📂 model/                     # 데이터 모델
│   ├── 📂 entity/               # JPA 엔티티
│   │   ├── Users.java
│   │   ├── FavoriteCategory.java
│   │   ├── UserBook.java
│   │   └── UserTrading.java
│   └── 📂 feigns/               # Feign 요청/응답 DTO
│
├── 📂 repository/                # JPA 리포지토리
│   ├── UsersRepository.java
│   ├── FavoriteCategoryRepository.java
│   └── ...
│
├── 📂 service/                   # 비즈니스 로직
│   ├── AuthService.java         # 인증 서비스
│   ├── UsersService.java        # 사용자 서비스
│   ├── KeycloakService.java     # Keycloak 통합
│   ├── S3Service.java           # S3 파일 업로드
│   └── RedisService.java        # Redis 캐싱
│
└── 📂 utils/                     # 유틸리티
    ├── JwtUtil.java             # 내부 JWT 생성/검증
    └── KeycloakJwtUtil.java     # Keycloak JWT 검증
```
