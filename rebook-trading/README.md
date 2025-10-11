# Rebook Trading Service

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-336791)
![Redis](https://img.shields.io/badge/Redis-6+-DC382D)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-FF6600)
![AWS S3](https://img.shields.io/badge/AWS-S3-569A31)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)

**Rebook 마이크로서비스 아키텍처의 거래 관리 서비스**

중고 도서 거래 등록, 관리, 찜하기, 알림 발행을 담당하는 핵심 백엔드 서비스

</div>

## 📋 목차

- [프로젝트 소개](#-프로젝트-소개)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [API 문서](#-api-문서)
- [개발 가이드](#-개발-가이드)


## 🎯 프로젝트 소개

Rebook Trading Service는 중고 도서 거래 플랫폼의 핵심 서비스로, 사용자 간 도서 거래를 관리하고 조율합니다. Spring Boot 기반의 마이크로서비스 아키텍처로 구현되어 있으며, 서비스 디스커버리, 중앙화된 설정 관리, 비동기 메시징을 통한 확장 가능한 구조를 제공합니다.

### 핵심 가치

- **확장성**: 마이크로서비스 아키텍처를 통한 수평적 확장
- **안정성**: 분산 환경에서의 트랜잭션 관리 및 에러 처리
- **성능**: Redis 캐싱 및 최적화된 데이터베이스 쿼리
- **보안**: 사용자 권한 검증 및 안전한 파일 저장

## ✨ 주요 기능

### 거래 관리
- ✅ 중고 도서 거래 등록 (이미지 업로드 포함)
- ✅ 거래 상세 조회 및 목록 조회
- ✅ 거래 정보 수정 및 삭제
- ✅ 거래 상태 관리 (판매중, 예약중, 판매완료)
- ✅ 소유자 권한 검증을 통한 안전한 거래 관리

### 찜하기 (북마크)
- ✅ 관심 거래 찜하기/찜 해제
- ✅ 찜한 거래 목록 조회
- ✅ 거래 조회 시 찜하기 상태 표시

### 추천 시스템
- ✅ 사용자 맞춤 도서 거래 추천
- ✅ Book Service와 연동한 개인화 추천

### 알림 시스템
- ✅ 새로운 거래 등록 시 찜한 사용자에게 알림
- ✅ 가격 변동 시 찜한 사용자에게 알림
- ✅ RabbitMQ 기반 비동기 메시징

### 페이지네이션
- ✅ 모든 목록 조회 API에 페이지네이션 지원
- ✅ 커스텀 페이지 응답 구조

## 🛠 기술 스택

### 백엔드 프레임워크
- **Spring Boot** 3.3.13
- **Java** 17
- **Spring Data JPA** - ORM 및 데이터 접근
- **Spring Cloud** - 마이크로서비스 인프라

### 데이터베이스 & 캐싱
- **PostgreSQL** - 메인 데이터베이스
- **Redis** - 분산 캐싱

### 마이크로서비스 인프라
- **Eureka** - 서비스 디스커버리
- **Spring Cloud Config** - 중앙화된 설정 관리
- **OpenFeign** - 선언적 HTTP 클라이언트

### 메시징
- **RabbitMQ** - 비동기 메시징 및 이벤트 발행
- **AMQP** - 메시지 프로토콜

### 클라우드 & 스토리지
- **AWS S3** - 이미지 파일 저장소

### 모니터링 & 로깅
- **Spring Actuator** - 헬스체크 및 메트릭
- **Prometheus** - 메트릭 수집
- **Sentry** - 에러 트래킹 및 모니터링
- **SLF4J & Logback** - 로깅

### API 문서화
- **SpringDoc OpenAPI 3** - Swagger UI 기반 API 문서

### 빌드 & 배포
- **Gradle** 8.14.2
- **Docker** - 컨테이너화
- **Jacoco** - 코드 커버리지

## 🏗 시스템 아키텍처

### 마이크로서비스 아키텍처

```
┌─────────────────┐
│  API Gateway    │
└────────┬────────┘
         │
         ├──────────────┬──────────────┬──────────────┐
         │              │              │              │
    ┌────▼────┐    ┌────▼────┐   ┌────▼────┐    ┌────▼────┐
    │ Trading │    │  Book   │   │  User   │    │ Notif.  │
    │ Service │◄───┤ Service │   │ Service │    │ Service │
    └────┬────┘    └─────────┘   └─────────┘    └────▲────┘
         │                                            │
         │         ┌──────────────┐                  │
         └────────►│  RabbitMQ    │──────────────────┘
                   └──────────────┘

    ┌─────────────────────────────────────────────┐
    │        Infrastructure Services              │
    ├─────────────────────────────────────────────┤
    │  Eureka  │  Config Server  │  PostgreSQL   │
    │  Redis   │  AWS S3         │  Prometheus   │
    └─────────────────────────────────────────────┘
```

### 서비스 통신 패턴

1. **동기 통신 (OpenFeign)**
   - Book Service 호출: 도서 추천 목록 조회

2. **비동기 통신 (RabbitMQ)**
   - Notification Service 발행: 거래 생성/가격 변동 알림

3. **서비스 디스커버리 (Eureka)**
   - 모든 서비스가 Eureka에 등록
   - 동적 서비스 탐색 및 로드 밸런싱

### 계층 구조

```
┌──────────────────────────────────────────┐
│         Controller Layer                 │  ← REST API 엔드포인트
├──────────────────────────────────────────┤
│         Service Layer                    │  ← 비즈니스 로직
│  ┌────────────┬─────────────┬─────────┐ │
│  │  Trading   │  Trading    │  S3     │ │
│  │  Service   │  Reader     │ Service │ │
│  └────────────┴─────────────┴─────────┘ │
├──────────────────────────────────────────┤
│         Repository Layer                 │  ← 데이터 접근
├──────────────────────────────────────────┤
│         Entity Layer                     │  ← 도메인 모델
└──────────────────────────────────────────┘
```



## 📖 API 문서

애플리케이션 실행 후 Swagger UI를 통해 API 문서를 확인할 수 있습니다:

```
http://localhost:8080/swagger-ui.html
```

### 주요 API 엔드포인트

#### 거래 관리

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/tradings` | 거래 등록 | Required |
| GET | `/api/tradings/{tradingId}` | 거래 상세 조회 | Required |
| PUT | `/api/tradings/{tradingId}` | 거래 수정 | Required (Owner) |
| PATCH | `/api/tradings/{tradingId}` | 거래 상태 수정 | Required (Owner) |
| DELETE | `/api/tradings/{tradingId}` | 거래 삭제 | Required (Owner) |
| GET | `/api/tradings/me` | 내 거래 목록 조회 | Required |
| GET | `/api/tradings/books/{bookId}` | 특정 도서 거래 목록 | Required |
| GET | `/api/tradings/recommendations` | 추천 거래 목록 | Required |
| GET | `/api/tradings/others/{userId}` | 타인의 거래 목록 | Required |

#### 찜하기 관리

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/tradings/mark/{tradingId}` | 거래 찜하기/취소 (토글) | Required |
| GET | `/api/tradings/marked` | 찜한 거래 목록 조회 | Required |

---

## 👨‍💻 개발 가이드

### 프로젝트 구조

```
src/main/java/com/example/rebooktradingservice/
├── advice/              # 전역 예외 처리
│   └── GlobalExceptionHandler.java
├── common/              # 공통 응답 모델
│   ├── CommonResult.java
│   ├── SingleResult.java
│   ├── ListResult.java
│   ├── PageResponse.java
│   ├── ResponseService.java
│   └── ResultCode.java
├── config/              # 설정 클래스
│   ├── RabbitConfig.java
│   └── S3Config.java
├── controller/          # REST 컨트롤러
│   ├── TradingController.java
│   └── TradingUserController.java
├── enums/               # 열거형
│   └── State.java
├── exception/           # 커스텀 예외
│   ├── CMissingDataException.java
│   ├── CDuplicatedDataException.java
│   ├── CUnauthorizedException.java
│   └── CInvalidDataException.java
├── feigns/              # Feign 클라이언트
│   └── BookClient.java
├── model/               # DTO 및 엔티티
│   ├── entity/
│   │   ├── Trading.java
│   │   ├── TradingUser.java
│   │   └── compositekey/
│   │       └── TradingUserId.java
│   ├── message/
│   │   └── NotificationTradeMessage.java
│   ├── TradingRequest.java
│   └── TradingResponse.java
├── repository/          # JPA 리포지토리
│   ├── TradingRepository.java
│   └── TradingUserRepository.java
├── service/             # 비즈니스 로직
│   ├── TradingService.java
│   ├── TradingReader.java
│   ├── TradingUserService.java
│   ├── S3Service.java
│   └── NotificationPublisher.java
└── RebookTradingServiceApplication.java
```