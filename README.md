# Rebook User Service

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-336791)
![Redis](https://img.shields.io/badge/Redis-6+-DC382D)
![Keycloak](https://img.shields.io/badge/Keycloak-26.0.5-4D4D4D)
![AWS S3](https://img.shields.io/badge/AWS-S3-FF9900)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)

**Rebook 마이크로서비스 아키텍처의 사용자 관리 및 인증 서비스**

Keycloak 기반 OAuth2 인증, 프로필 관리, 선호도 시스템을 담당하는 핵심 백엔드 서비스

</div>

---

## 1. 개요

**Rebook User Service**는 중고 도서 거래 플랫폼 Rebook의 핵심 백엔드 마이크로서비스로, 사용자 생명주기 전반과 인증을 관리합니다. Spring Boot 기반으로 구현된 본 서비스는 **이중 인증 시스템**, **서비스 디스커버리**, **중앙화된 설정 관리**를 통한 확장 가능한 구조를 제공합니다.


### 서비스 역할

본 서비스는 Rebook 플랫폼 내에서 다음과 같은 역할을 담당합니다:

- **인증 및 권한 관리**: Keycloak 기반 OAuth2/OIDC 인증 및 내부 JWT 토큰 발급
- **사용자 프로필 관리**: 프로필 정보 생성, 수정, 삭제 및 이미지 관리
- **선호도 시스템**: 사용자별 선호 카테고리 관리 및 맞춤 추천
- **독서 기록 관리**: 사용자 도서 상호작용 및 거래 이력 추적
- **서비스 간 통신**: OpenFeign을 통한 마이크로서비스 연동

---

## 2. 목차

- [1. 개요](#1-개요)
- [2. 목차](#2-목차)
- [3. 주요 기능](#3-주요-기능)
- [4. 기술 스택](#4-기술-스택)
- [5. 아키텍처](#5-아키텍처)
- [6. API 문서](#6-api-문서)
- [7. 프로젝트 구조](#7-프로젝트-구조)

---

## 3. 주요 기능

### 3.1 이중 인증 시스템

#### 외부 인증 (Keycloak)
- ✅ OAuth2/OpenID Connect 기반 인증
- ✅ Keycloak 토큰 검증 및 사용자 정보 추출
- ✅ 소셜 로그인 지원 (Google, Kakao, Naver 등)
- ✅ 자동 사용자 생성 (첫 로그인 시 기본 프로필 생성)

#### 내부 JWT 시스템
- ✅ 마이크로서비스 간 통신용 내부 JWT 토큰 생성
- ✅ Access Token + Refresh Token 이중 토큰 관리
- ✅ Redis 기반 Refresh Token 캐싱 (`refresh:` 접두사)
- ✅ 토큰 갱신 API를 통한 자동 재발급

#### 인증 흐름
```
사용자 → Keycloak 인증 → User Service 토큰 검증
      → 내부 JWT 발급 → Redis 캐싱 → API 접근 권한 부여
```

### 3.2 사용자 프로필 관리

#### 프로필 CRUD
- ✅ 사용자 정보 조회 (본인 및 타 사용자)
- ✅ 프로필 수정 (닉네임, 이메일, 이미지)
- ✅ 계정 삭제 (소프트 삭제 또는 영구 삭제)
- ✅ 비밀번호 변경

#### 프로필 이미지 관리
- ✅ AWS S3 기반 이미지 업로드
- ✅ 이미지 URL 자동 생성 및 관리
- ✅ 기존 이미지 교체 시 자동 삭제
- ✅ 기본 프로필 이미지 제공

### 3.3 선호도 및 추천 시스템

#### 선호 카테고리 관리
- ✅ 사용자별 선호 카테고리 등록/삭제
- ✅ 선호 카테고리 목록 조회
- ✅ 복합키(Composite Key) 기반 효율적인 관계 관리

#### 추천 시스템
- ✅ 사용자 선호도 기반 카테고리 추천
- ✅ 맞춤형 도서 추천 지원
- ✅ 독서 이력 기반 추천 개인화

### 3.4 독서 기록 관리

- ✅ 사용자-도서 상호작용 기록 (`UserBook`)
- ✅ 사용자-거래 이력 추적 (`UserTrading`)
- ✅ 복합키 기반 다대다 관계 관리
- ✅ 독서 통계 및 분석 데이터 제공


---

## 4. 기술 스택

### 4.1 백엔드 프레임워크

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Boot** | 3.3.13 | 애플리케이션 프레임워크 |
| **Java** | 17 | 프로그래밍 언어 |
| **Spring Data JPA** | - | ORM 및 데이터 접근 계층 |
| **Spring Validation** | - | 요청 데이터 유효성 검증 |
| **Lombok** | - | 보일러플레이트 코드 제거 |

### 4.2 데이터베이스 & 캐싱

| 기술 | 버전 | 용도 |
|------|------|------|
| **PostgreSQL** | 13+ | 관계형 데이터베이스 (메인 데이터 저장소) |
| **Redis** | 6+ | 분산 캐싱 및 세션 관리 (Refresh Token 저장) |

### 4.3 마이크로서비스 인프라 (Spring Cloud)

| 기술 | 버전 | 용도 |
|------|------|------|
| **Eureka Client** | 2023.0.5 | 서비스 디스커버리 및 등록 |
| **Spring Cloud Config** | 2023.0.5 | 중앙화된 설정 관리 (외부 Config Server) |
| **OpenFeign** | 2023.0.5 | 선언적 HTTP 클라이언트 (서비스 간 통신) |

### 4.4 인증 & 보안

| 기술 | 버전 | 용도 |
|------|------|------|
| **Keycloak** | 26.0.5 | OAuth2/OIDC 인증 제공자 |
| **JJWT** | 0.12.5 | 내부 JWT 토큰 생성 및 검증 |
| **Custom JWT System** | - | 마이크로서비스 간 인증 토큰 관리 |

### 4.5 메시징 & 이벤트

| 기술 | 버전 | 용도 |
|------|------|------|
| **RabbitMQ (AMQP)** | 3.x | 비동기 메시징 및 이벤트 기반 통신 |
| **Spring AMQP** | - | RabbitMQ 통합 및 메시지 컨버터 |

### 4.6 클라우드 & 스토리지

| 기술 | 버전 | 용도 |
|------|------|------|
| **AWS S3** | SDK 2.27.21 | 프로필 이미지 파일 저장소 |

### 4.7 모니터링 & 로깅

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Actuator** | - | 헬스체크 및 메트릭 엔드포인트 |
| **Prometheus** | - | 메트릭 수집 및 모니터링 (Micrometer 연동) |
| **Sentry** | 8.13.2 | 실시간 에러 트래킹 및 알림 |
| **SLF4J & Logback** | - | 애플리케이션 로깅 |

### 4.8 API 문서화

| 기술 | 버전 | 용도 |
|------|------|------|
| **SpringDoc OpenAPI 3** | 2.6.0 | Swagger UI 기반 REST API 문서 자동 생성 |

### 4.9 빌드 & 배포

| 기술 | 버전 | 용도 |
|------|------|------|
| **Gradle** | 8.14.2 | 빌드 자동화 도구 |
| **Docker** | - | 컨테이너화 및 배포 |
| **Jacoco** | - | 테스트 커버리지 분석 |
| **JUnit 5** | - | 단위 테스트 프레임워크 |

---

## 5. 아키텍처

### 5.1 마이크로서비스 아키텍처

```
┌─────────────────┐
│  API Gateway    │ ← 인증 및 라우팅 (X-User-Id 헤더 주입)
└────────┬────────┘
         │
         ├──────────────┬──────────────┬──────────────┐
         │              │              │              │
    ┌────▼────┐    ┌────▼────┐   ┌────▼────┐    ┌────▼────┐
    │  User   │    │ Trading │   │  Book   │    │ Notif.  │
    │ Service │───►│ Service │   │ Service │    │ Service │
    └────┬────┘    └─────────┘   └────┬────┘    └────▲────┘
         │         (Keycloak)          │              │
         │         인증 위임            │(OpenFeign)   │
         │                             │              │
         │         ┌──────────────┐    │              │
         └────────►│  Keycloak    │    └──────────────┘
                   │  (OAuth2)    │  (User Info 조회)
                   └──────────────┘

    ┌─────────────────────────────────────────────┐
    │        Infrastructure Services              │
    ├─────────────────────────────────────────────┤
    │  Eureka  │  Config Server  │  PostgreSQL   │
    │  Redis   │  AWS S3         │  Prometheus   │
    └─────────────────────────────────────────────┘
```

### 5.2 엔티티 관계도 (ERD)

```
┌─────────────────────────────┐
│         Users               │
├─────────────────────────────┤
│ PK  id (Long)               │
│     keycloakId (String)     │
│     nickname (String)       │
│     email (String)          │
│     profileImageUrl (String)│
│     createdAt (LocalDateTime)│
│     updatedAt (LocalDateTime)│
└─────────────────────────────┘
              │
              ├──────────────┬──────────────┐
              │ 1:N          │ 1:N          │ 1:N
              │              │              │
              ▼              ▼              ▼
┌─────────────────────┐ ┌──────────────────┐ ┌─────────────────────┐
│  FavoriteCategory   │ │    UserBook      │ │   UserTrading       │
├─────────────────────┤ ├──────────────────┤ ├─────────────────────┤
│ PK userId (Long)    │ │ PK userId (Long) │ │ PK userId (Long)    │
│ PK categoryId (Long)│ │ PK bookId (Long) │ │ PK tradingId (Long) │
│    createdAt        │ │    createdAt     │ │    createdAt        │
└─────────────────────┘ └──────────────────┘ └─────────────────────┘
```

**엔티티 설계 패턴**:
- **JPA Auditing**: `@EntityListeners(AuditingEntityListener.class)`로 생성/수정 시간 자동 관리
- **복합키 (Composite Key)**: `FavoriteCategoryId`, `UserBookId`, `UserTradingId` 임베디드 클래스로 다대다 관계 표현
- **외부 ID 저장**: `keycloakId`로 Keycloak 사용자와 매핑


### 5.3 인증흐름
#### 일반로그인
![일반로그인](https://rebook-bucket.s3.ap-northeast-2.amazonaws.com/rebook/login_auth.png)

#### 소셜로그인
![소셜로그인](https://rebook-bucket.s3.ap-northeast-2.amazonaws.com/rebook/sociallogin_auth.png)



## 6. API 문서

### 6.1 Swagger UI 접근

애플리케이션 실행 후 아래 URL에서 대화형 API 문서를 확인할 수 있습니다:

```
https://api.rebookcloak.click/webjars/swagger-ui/index.html?urls.primaryName=rebook-book
```

### 6.2 API 엔드포인트 상세

#### 6.2.1 인증 API (`AuthController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **POST** | `/api/auths/login` | Keycloak 토큰으로 로그인 및 내부 JWT 발급 |
| **POST** | `/api/auths/refresh` | Access Token 갱신 (Refresh Token 사용) |
| **GET** | `/api/auths/test` | 헬스 체크 및 서비스 상태 확인 |

#### 6.2.2 사용자 관리 API (`UsersController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **GET** | `/api/users` | 내 프로필 조회 (현재 로그인 사용자) |
| **GET** | `/api/users/{userId}` | 특정 사용자 프로필 조회 |
| **PUT** | `/api/users` | 프로필 수정 (닉네임, 이메일, 이미지) |
| **DELETE** | `/api/users` | 계정 삭제 |
| **PATCH** | `/api/users/me` | 비밀번호 변경 |
| **GET** | `/api/users/categories` | 내 선호 카테고리 조회 |
| **GET** | `/api/users/categories/recommendations/{userId}` | 추천 카테고리 조회 |

#### 6.2.3 선호 카테고리 API (`FavoriteCategoryController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **POST** | `/api/categories` | 선호 카테고리 추가 |
| **DELETE** | `/api/categories/{categoryId}` | 선호 카테고리 삭제 |
| **GET** | `/api/categories` | 내 선호 카테고리 목록 조회 |

#### 6.2.4 독서 관리 API (`ReaderController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **GET** | `/api/readers/books` | 내 도서 상호작용 목록 조회 |
| **GET** | `/api/readers/tradings` | 내 거래 이력 조회 |


## 7. 프로젝트 구조

### 주요 디렉토리 설명

| 디렉토리 | 역할 | 주요 기능 |
|---------|------|----------|
| **advice/** | 전역 예외 처리 | `@RestControllerAdvice`로 모든 컨트롤러 예외 통합 핸들링 |
| **clients/** | 서비스 간 통신 | OpenFeign 클라이언트 정의 (Notification Service 연동) |
| **common/** | 응답 표준화 | 통일된 API 응답 구조 제공 (`CommonResult`, `SingleResult`, `ListResult`) |
| **config/** | 인프라 설정 | Redis, S3, Keycloak, Swagger 등 외부 서비스 연동 설정 |
| **controller/** | REST API | 엔드포인트 정의 및 Swagger 문서화 (`@Tag`, `@Operation`) |
| **enums/** | 상태 관리 | 사용자 권한 enum (ROLE_USER, ROLE_ADMIN) |
| **exception/** | 커스텀 예외 | 도메인별 예외 클래스 (404, 409, 400) |
| **model/entity/** | 도메인 모델 | JPA 엔티티 및 복합키 정의 |
| **model/feigns/** | 서비스 간 DTO | Feign 요청/응답 데이터 전송 객체 |
| **repository/** | 데이터 접근 | Spring Data JPA 리포지토리 인터페이스 |
| **service/** | 비즈니스 로직 | 트랜잭션 관리, 인증, 외부 서비스 연동 |
| **utils/** | 유틸리티 | JWT 생성/검증, Keycloak 토큰 파싱 |


```
rebook-user-service/
├── src/
│   ├── main/
│   │   ├── java/com/example/rebookuserservice/
│   │   │   ├── advice/                        # 전역 예외 처리
│   │   │   │   └── GlobalExceptionHandler.java  (RestControllerAdvice)
│   │   │   │
│   │   │   ├── clients/                       # Feign 클라이언트
│   │   │   │   └── NotificationClient.java     (Notification Service 연동)
│   │   │   │
│   │   │   ├── common/                        # 공통 응답 모델
│   │   │   │   ├── CommonResult.java           (기본 성공 응답)
│   │   │   │   ├── SingleResult.java           (단일 데이터 응답)
│   │   │   │   ├── ListResult.java             (리스트 응답)
│   │   │   │   └── ResponseService.java        (응답 래핑 팩토리)
│   │   │   │
│   │   │   ├── config/                        # 설정 클래스
│   │   │   │   ├── RedisConfig.java            (Redis 연결 설정)
│   │   │   │   ├── S3Config.java               (AWS S3 클라이언트 설정)
│   │   │   │   ├── KeycloakConfig.java         (Keycloak 통합 설정)
│   │   │   │   └── SwaggerConfig.java          (API 문서 설정)
│   │   │   │
│   │   │   ├── controller/                    # REST 컨트롤러
│   │   │   │   ├── AuthController.java         (인증 API)
│   │   │   │   ├── UsersController.java        (사용자 관리 API)
│   │   │   │   ├── FavoriteCategoryController.java (선호 카테고리 API)
│   │   │   │   └── ReaderController.java       (독서 관리 API)
│   │   │   │
│   │   │   ├── enums/                         # 열거형
│   │   │   │   └── Role.java                   (사용자 권한: ROLE_USER, ROLE_ADMIN)
│   │   │   │
│   │   │   ├── exception/                     # 커스텀 예외
│   │   │   │   ├── CMissingDataException.java  (404 데이터 미존재)
│   │   │   │   ├── CDuplicatedDataException.java (409 중복 데이터)
│   │   │   │   └── CInvalidDataException.java  (400 유효하지 않은 입력)
│   │   │   │
│   │   │   ├── model/                         # DTO 및 엔티티
│   │   │   │   ├── entity/                    # JPA 엔티티
│   │   │   │   │   ├── Users.java              (사용자 메인 엔티티)
│   │   │   │   │   ├── FavoriteCategory.java   (선호 카테고리 조인 테이블)
│   │   │   │   │   ├── UserBook.java           (사용자-도서 관계)
│   │   │   │   │   ├── UserTrading.java        (사용자-거래 관계)
│   │   │   │   │   └── compositekey/
│   │   │   │   │       ├── FavoriteCategoryId.java (복합키)
│   │   │   │   │       ├── UserBookId.java     (복합키)
│   │   │   │   │       └── UserTradingId.java  (복합키)
│   │   │   │   └── feigns/                    # Feign DTO
│   │   │   │       └── NotificationUserDto.java (알림 서비스용 DTO)
│   │   │   │
│   │   │   ├── repository/                    # JPA 리포지토리
│   │   │   │   ├── UsersRepository.java        (사용자 데이터 접근)
│   │   │   │   ├── FavoriteCategoryRepository.java (선호 카테고리 접근)
│   │   │   │   ├── UserBookRepository.java     (도서 상호작용 접근)
│   │   │   │   └── UserTradingRepository.java  (거래 이력 접근)
│   │   │   │
│   │   │   ├── service/                       # 비즈니스 로직
│   │   │   │   ├── AuthService.java            (인증 및 토큰 관리)
│   │   │   │   ├── UsersService.java           (사용자 CRUD)
│   │   │   │   ├── KeycloakService.java        (Keycloak 통합)
│   │   │   │   ├── S3Service.java              (AWS S3 파일 업로드)
│   │   │   │   └── RedisService.java           (Redis 캐싱)
│   │   │   │
│   │   │   ├── utils/                         # 유틸리티
│   │   │   │   ├── JwtUtil.java                (내부 JWT 생성/검증)
│   │   │   │   └── KeycloakJwtUtil.java        (Keycloak JWT 검증)
│   │   │   │
│   │   │   └── RebookUserServiceApplication.java (메인 애플리케이션)
│   │   │
│   │   └── resources/
│   │       ├── application.yaml               (Spring Cloud Config 연동)
│   │       ├── application-dev.yaml           (개발 환경 설정)
│   │       └── application-prod.yaml          (운영 환경 설정)
│   │
│   └── test/
│       └── java/com/example/rebookuserservice/
│           └── (테스트 클래스들)
│
├── build.gradle                               # Gradle 빌드 설정
├── Dockerfile                                 # Docker 이미지 빌드 설정
└── README.md                                  # 프로젝트 문서 (본 파일)
```
