# Rebook Auth Service

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-336791)
![Redis](https://img.shields.io/badge/Redis-6+-DC382D)
![JWT](https://img.shields.io/badge/JWT-0.12.5-000000)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)

**Rebook 마이크로서비스 아키텍처의 인증 및 권한 관리 서비스**

사용자 인증, OAuth2 소셜 로그인, JWT 토큰 관리 기능을 제공하는 핵심 보안 서비스

</div>

---

## 1. 개요

**Rebook Auth Service**는 중고 도서 거래 플랫폼 Rebook의 보안 백엔드 마이크로서비스로, 사용자 인증 및 권한 관리를 담당합니다. Spring Security 기반으로 구현된 본 서비스는 **JWT 토큰 기반 인증**, **다중 OAuth2 프로바이더 지원**, **Redis 캐싱**을 통한 확장 가능한 보안 구조를 제공합니다.

### 서비스 역할

본 서비스는 Rebook 플랫폼 내에서 다음과 같은 역할을 담당합니다:

- **사용자 인증**: 전통적인 username/password 로그인 및 회원가입
- **OAuth2 통합**: Google, Naver, Kakao 소셜 로그인 지원
- **토큰 관리**: JWT 기반 Access Token 및 Refresh Token 발급/갱신
- **세션 관리**: Redis를 활용한 분산 세션 및 토큰 캐싱
- **패스포트 발급**: 인증된 사용자에 대한 접근 권한 관리
- **서비스 간 통신**: User Service, Notification Service와의 연동

---

## 2. 목차

- [주요 기능](#3-주요-기능)
- [기술 스택](#4-기술-스택)
- [아키텍처](#5-아키텍처)
- [API 문서](#6-api-문서)
- [프로젝트 구조](#7-프로젝트-구조)

---

## 3. 주요 기능

### 3.1 인증 시스템

#### 전통적인 로그인
- ✅ Username/Password 기반 회원가입 및 로그인
- ✅ BCrypt 암호화를 통한 안전한 비밀번호 저장
- ✅ `@Password` 커스텀 어노테이션을 통한 비밀번호 유효성 검증
- ✅ Spring Security AuthenticationManager 기반 인증 처리

#### OAuth2 소셜 로그인
- ✅ Google OAuth2 로그인 지원
- ✅ Naver OAuth2 로그인 지원
- ✅ Kakao OAuth2 로그인 지원
- ✅ Factory Pattern 기반 다중 프로바이더 동적 처리

### 3.2 토큰 관리

- ✅ JWT Access Token 발급 (유효기간: 30분)
- ✅ JWT Refresh Token 발급 (유효기간: 7일)
- ✅ Refresh Token 기반 Access Token 재발급
- ✅ Redis를 통한 Refresh Token 캐싱 및 만료 관리

### 3.3 패스포트 시스템

- ✅ 인증된 사용자에 대한 패스포트 발급
- ✅ JWT 토큰 검증을 통한 접근 권한 확인
- ✅ 공통 라이브러리(`passport-common`) 연동

### 3.4 서비스 간 통신

- ✅ User Service와 OpenFeign 연동 (사용자 생성, 조회)
- ✅ Eureka 서비스 디스커버리를 통한 동적 서비스 탐색

---

## 4. 기술 스택

### 4.1 Core

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Boot** | 3.3.13 | 애플리케이션 프레임워크 |
| **Java** | 17 | 프로그래밍 언어 |
| **Spring Security** | - | 인증 및 권한 관리 |
| **Spring Data JPA** | - | ORM 및 데이터 접근 |
| **JJWT** | 0.12.5 | JWT 토큰 생성/검증 |
| **PostgreSQL** | 13+ | 사용자 인증 정보 저장 |
| **Redis** | 6+ | Refresh Token 캐싱 |

### 4.2 Microservices (Spring Cloud)

| 기술 | 버전 | 용도 |
|------|------|------|
| **Eureka Client** | 2023.0.5 | 서비스 디스커버리 |
| **Config Client** | 2023.0.5 | 중앙화된 설정 관리 |
| **OpenFeign** | 2023.0.5 | 서비스 간 통신 (User Service 연동) |

### 4.3 OAuth2 Providers

| Provider | 용도 |
|----------|------|
| **Google OAuth2** | Google 소셜 로그인 |
| **Naver OAuth2** | Naver 소셜 로그인 |
| **Kakao OAuth2** | Kakao 소셜 로그인 |

### 4.4 Monitoring & DevOps

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Actuator** | - | 헬스체크 및 메트릭 |
| **Prometheus** | - | 메트릭 수집 |
| **Sentry** | 8.13.2 | 에러 트래킹 |
| **SpringDoc OpenAPI** | 2.6.0 | API 문서화 (Swagger) |
| **Gradle** | 8.14.2 | 빌드 도구 |
| **JaCoCo** | - | 테스트 커버리지 |

---

## 5. 아키텍처

### 5.1 인증 흐름도

#### 5.1.1 전통적인 로그인 흐름
![basic_login](https://rebook-bucket.s3.ap-northeast-2.amazonaws.com/rebook/basic_login.png)

#### 5.1.2 OAuth2 로그인 흐름
![oauth_login](https://rebook-bucket.s3.ap-northeast-2.amazonaws.com/rebook/oauth_login.png)

### 5.2 Netflix Passport
![netflix_passport](https://rebook-bucket.s3.ap-northeast-2.amazonaws.com/rebook/netflix_passport.png)

### 5.3 엔티티 관계도 (ERD)

```
┌─────────────────────────────┐
│        AuthUser             │
├─────────────────────────────┤
│ PK  id (Long)               │
│     username (String)       │ ← Unique
│     password (String)       │ ← BCrypt 암호화
│     provider (Provider)     │ ← LOCAL, GOOGLE, NAVER, KAKAO
│     providerId (String)     │
│     role (Role)             │ ← USER, ADMIN
│     createdAt (LocalDateTime)│
│     updatedAt (LocalDateTime)│
└─────────────────────────────┘
```

**엔티티 설계 패턴**:
- **JPA Auditing**: `@EntityListeners(AuditingEntityListener.class)`로 생성/수정 시간 자동 관리
- **Provider Enum**: LOCAL(전통 로그인), GOOGLE, NAVER, KAKAO 구분
- **Role Enum**: USER, ADMIN 권한 레벨 관리
- **BCrypt 암호화**: Spring Security의 PasswordEncoder로 안전한 비밀번호 저장


---

## 6. API 문서

### 6.2 API 엔드포인트 상세

#### 6.2.1 인증 API (`AuthController`)

**모든 `/api/auth/**` 엔드포인트는 인증 없이 접근 가능 (SecurityConfig에서 화이트리스트 처리)**

| Method | Endpoint | Summary | Request Body | Response |
|--------|----------|---------|--------------|----------|
| **POST** | `/api/auth/signup` | 회원가입 | `SignUpRequest` (username, password) | `CommonResult` |
| **POST** | `/api/auth/login` | 로그인 | `LoginRequest` (username, password) | `SingleResult<TokenResponse>` |
| **POST** | `/api/auth/oauth/{provider}` | OAuth2 소셜 로그인 | `OAuthRequest` (code, provider) | `SingleResult<TokenResponse>` |
| **POST** | `/api/auth/refresh` | Access Token 갱신 | `RefreshRequest` (refreshToken) | `SingleResult<RefreshResponse>` |
| **GET** | `/api/auth/test` | 헬스 체크 | - | String ("test success") |


**Response Models**:
```json
// TokenResponse
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
}

// RefreshResponse
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
}
```

#### 6.2.2 패스포트 API (`PassportController`)

**인증 필요 (JWT Access Token 헤더에 포함)**

| Method | Endpoint | Summary | Response |
|--------|----------|---------|----------|
| **POST** | `/api/passport` | 패스포트 발급 | Passport 정보 |

---

## 7. 프로젝트 구조

### 디렉토리 역할

| 디렉토리 | 역할 | 주요 기능 |
|---------|------|----------|
| **controller/** | REST API | 인증/패스포트 엔드포인트 정의, Swagger 문서화 (`@Tag`, `@Operation`) |
| **service/** | 비즈니스 로직 | 인증 처리, OAuth Factory Pattern, 토큰 발급/갱신, 트랜잭션 관리 |
| **service/oauth/** | OAuth 통합 | Factory Pattern으로 Google/Naver/Kakao 프로바이더 동적 라우팅 |
| **model/entity/** | JPA 엔티티 | 사용자 인증 엔티티 (`AuthUser`), JPA Auditing으로 생성/수정 시간 관리 |
| **model/dto/** | 데이터 전송 | 요청/응답 DTO, OAuth 프로바이더별 모델, Feign 연동 DTO |
| **repository/** | 데이터 접근 | Spring Data JPA 리포지토리 인터페이스 (AuthRepository) |
| **clients/** | 서비스 간 통신 | OpenFeign을 통한 User Service, Notification Service 동기 호출 |
| **config/** | 인프라 설정 | Spring Security, Redis, Swagger, Passport 공통 라이브러리 설정 |
| **utils/** | 유틸리티 | JWT 생성/검증, Redis 캐시 관리 등 공통 유틸리티 |
| **exception/** | 커스텀 예외 | 도메인별 예외 클래스 (404, 409, 400 등) 및 GlobalExceptionHandler |
| **common/** | 응답 표준화 | 통일된 API 응답 구조 (`CommonResult`, `SingleResult`, `ListResult`) |
| **annotation/** | 커스텀 어노테이션 | `@OAuthServiceType` (Factory 등록), `@Password` (비밀번호 검증) |
| **enums/** | 열거형 | Provider (OAuth 프로바이더), Role (권한 레벨) |

### 구조

```
src/main/java/com/example/rebookauthservice/
├── controller/          # AuthController, PassportController
├── service/             # AuthService, PassportService
│   └── oauth/          # OAuthServiceFactory + Google/Naver/Kakao 구현체
├── model/
│   ├── entity/         # AuthUser (JPA)
│   └── dto/            # 요청/응답 DTO + OAuth 프로바이더별 모델
├── repository/         # AuthRepository (Spring Data JPA)
├── clients/            # UserClient, NotificationClient (Feign)
├── config/             # Security, Redis, Swagger, Passport 설정
├── utils/              # JwtUtil, RedisUtil
├── exception/          # GlobalExceptionHandler + 커스텀 예외
├── common/             # CommonResult, SingleResult, ResponseService
├── annotation/         # @OAuthServiceType, @Password
└── enums/              # Provider, Role
```