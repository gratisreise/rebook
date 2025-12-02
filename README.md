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

---

## 1. 개요

**Rebook Trading Service**는 중고 도서 거래 플랫폼 Rebook의 핵심 백엔드 마이크로서비스로, 사용자 간 도서 거래를 관리하고 조율합니다. Spring Boot 기반으로 구현된 본 서비스는 **서비스 디스커버리**, **중앙화된 설정 관리**, **비동기 메시징**을 통한 확장 가능한 구조를 제공합니다.


### 서비스 역할

본 서비스는 Rebook 플랫폼 내에서 다음과 같은 역할을 담당합니다:

- **거래 관리**: 중고 도서 거래 등록, 수정, 삭제 및 상태 관리
- **찜하기 시스템**: 사용자별 관심 거래 북마킹 기능
- **추천 시스템**: Book Service 연동을 통한 개인화 거래 추천
- **알림 발행**: RabbitMQ를 통한 실시간 거래 알림 전송
- **이미지 관리**: AWS S3 기반 거래 이미지 업로드 및 관리

---

## 2. 목차
- [주요 기능](#3-주요-기능)
- [기술 스택](#4-기술-스택)
- [아키텍처](#5-아키텍처)
- [API 문서](#6-api-문서)
- [프로젝트 구조](#7-프로젝트-구조)
---

## 3. 주요 기능

### 3.1 거래 관리

#### 거래 등록 및 수정
- ✅ 중고 도서 거래 등록 (이미지 업로드 포함)
- ✅ 거래 정보 수정 (이미지 교체 가능)
- ✅ 거래 삭제 (소유자 권한 검증)
- ✅ AWS S3 기반 이미지 저장 및 URL 관리

#### 거래 조회
- ✅ 거래 상세 정보 조회 (찜하기 상태 포함)
- ✅ 내 거래 목록 조회 (페이지네이션 지원)
- ✅ 특정 도서의 모든 거래 목록 조회
- ✅ 타 사용자의 거래 목록 조회
- ✅ 사용자 맞춤 거래 추천 (Book Service 연동)

#### 거래 상태 관리
- ✅ 거래 상태 변경 (판매중 → 예약중 → 판매완료)
- ✅ 소유자 권한 검증을 통한 안전한 상태 관리
- ✅ 상태별 거래 필터링 및 조회

### 3.2 찜하기 (북마크) 시스템

- ✅ 거래 찜하기/찜 해제 토글 기능
- ✅ 찜한 거래 목록 조회 (페이지네이션 지원)
- ✅ 거래 조회 시 현재 사용자의 찜하기 상태 표시
- ✅ 복합키(Composite Key) 기반 효율적인 찜하기 관리

### 3.3 추천 시스템

- ✅ 사용자 맞춤 도서 거래 추천
- ✅ Book Service와 OpenFeign 연동을 통한 개인화 추천
- ✅ 추천 도서에 해당하는 거래 목록 자동 필터링

### 3.4 알림 시스템 (Event-Driven)

- ✅ 새로운 거래 등록 시 해당 도서를 찜한 사용자에게 알림 발행
- ✅ 거래 가격 변동 시 찜한 사용자에게 알림 발행
- ✅ RabbitMQ 기반 비동기 메시징 (Notification Service 연동)
- ✅ JSON 메시지 직렬화를 통한 안정적 메시지 전송


---

## 4. 기술 스택

| 카테고리 | 기술 | 버전 |
|---------|------|------|
| **프레임워크** | Spring Boot | 3.3.13 |
| **언어** | Java | 17 |
| **데이터베이스** | PostgreSQL | 14+ |
| **캐싱** | Redis | 6+ |
| **서비스 디스커버리** | Eureka Client | 2023.0.5 |
| **설정 관리** | Spring Cloud Config | 2023.0.5 |
| **서비스 통신** | OpenFeign | 2023.0.5 |
| **메시징** | RabbitMQ (AMQP) | 3.x |
| **스토리지** | AWS S3 | SDK 2.27.21 |
| **모니터링** | Prometheus, Sentry | 8.13.2 |
| **API 문서** | SpringDoc OpenAPI 3 | 2.6.0 |
| **빌드 & 배포** | Gradle, Docker | 8.14.2 |

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
    │ Trading │    │  Book   │   │  User   │    │ Notif.  │
    │ Service │◄───┤ Service │   │ Service │    │ Service │
    └────┬────┘    └─────────┘   └─────────┘    └────▲────┘
         │         (OpenFeign)                        │
         │                                            │
         │         ┌──────────────┐                   │
         └────────►│  RabbitMQ    │───────────────────┘
                   │  (AMQP)      │  (Notification Events)
                   └──────────────┘

    ┌─────────────────────────────────────────────┐
    │        Infrastructure Services              │
    ├─────────────────────────────────────────────┤
    │  Eureka  │  Config Server  │  PostgreSQL   │
    │  Redis   │  AWS S3         │  Prometheus   │
    └─────────────────────────────────────────────┘
```

### 5.2 서비스 통신 패턴

#### 1. 동기 통신 (OpenFeign)
```java
@FeignClient(name = "book-service")
public interface BookClient {
    // Book Service에서 사용자의 추천 도서 ID 목록 조회
    @GetMapping("/api/books/recommendations")
    List<Long> getRecommendedBookIds(@RequestHeader("X-User-Id") String userId);
}
```
- **사용 사례**: 사용자 맞춤 도서 추천 거래 목록 조회
- **로드 밸런싱**: Eureka를 통한 자동 서비스 탐색 및 로드 밸런싱

#### 2. 비동기 통신 (RabbitMQ)
```java
@Service
public class NotificationPublisher {
    // Notification Service로 거래 알림 메시지 발행
    public void publishTradeNotification(NotificationTradeMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
```
- **사용 사례**: 거래 생성/가격 변동 시 찜한 사용자에게 알림 전송
- **메시지 포맷**: JSON 직렬화된 `NotificationTradeMessage` DTO

#### 3. 서비스 디스커버리 (Eureka)
- 모든 마이크로서비스가 Eureka Server에 자동 등록
- 서비스 이름(`book-service`)으로 동적 라우팅
- 헬스체크 기반 자동 장애 감지


### 5.3 엔티티 관계도 (ERD)

```
┌─────────────────────────────┐
│        Trading              │
├─────────────────────────────┤
│ PK  id (Long)               │
│     userId (String)         │
│     bookId (Long)           │
│     state (State enum)      │
│     price (Integer)         │
│     imageUrl (String)       │
│     createdAt (LocalDateTime)│
│     updatedAt (LocalDateTime)│
└─────────────────────────────┘
              │
              │ 1:N
              │
              ▼
┌─────────────────────────────┐
│      TradingUser            │  (찜하기 Join Table)
├─────────────────────────────┤
│ PK  tradingId (Long)        │ ← Composite Key
│ PK  userId (String)         │ ← Composite Key
│     createdAt (LocalDateTime)│
└─────────────────────────────┘
```

**엔티티 설계 패턴**:
- **JPA Auditing**: `@EntityListeners(AuditingEntityListener.class)`로 생성/수정 시간 자동 관리
- **복합키 (Composite Key)**: `TradingUserId` 임베디드 클래스로 사용자-거래 다대다 관계 표현
- **상태 관리**: `State` enum (판매중, 예약중, 판매완료)을 STRING으로 DB 저장


## 6. API 문서

### 6.1 API 엔드포인트 상세

#### 6.1.1 거래 관리 API (`TradingController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **POST** | `/api/tradings` | 거래 등록 |
| **GET** | `/api/tradings/{tradingId}` | 거래 상세 조회 |
| **PATCH** | `/api/tradings/{tradingId}` | 거래 상태 수정 |
| **PUT** | `/api/tradings/{tradingId}` | 거래 정보 수정 |
| **DELETE** | `/api/tradings/{tradingId}` | 거래 삭제 |
| **GET** | `/api/tradings/me` | 내 거래 목록 조회 |
| **GET** | `/api/tradings/books/{bookId}` | 특정 도서 거래 목록 |
| **GET** | `/api/tradings/recommendations` | 추천 거래 목록 |
| **GET** | `/api/tradings/others/{userId}` | 타인의 거래 목록 |

#### 6.1.2 찜하기 API (`TradingUserController`)

| Method | Endpoint | Summary |
|--------|----------|---------|
| **POST** | `/api/tradings/{tradingId}/marks` | 거래 찜하기/취소 (토글) |
| **GET** | `/api/tradings/marks` | 찜한 거래 목록 조회 |


## 7. 프로젝트 구조

### 주요 디렉토리 설명

| 디렉토리 | 역할 | 주요 기능 |
|---------|------|----------|
| **advice/** | 전역 예외 처리 | `@RestControllerAdvice`로 모든 컨트롤러 예외 통합 핸들링 |
| **common/** | 응답 표준화 | 통일된 API 응답 구조 제공 (`CommonResult`, `SingleResult`, `PageResponse`) |
| **config/** | 인프라 설정 | RabbitMQ, S3, JPA Auditing 등 외부 서비스 연동 설정 |
| **controller/** | REST API | 엔드포인트 정의 및 Swagger 문서화 (`@Tag`, `@Operation`) |
| **enums/** | 상태 관리 | 거래 상태 enum (판매중, 예약중, 판매완료) |
| **exception/** | 커스텀 예외 | 도메인별 예외 클래스 (404, 409, 403, 400) |
| **feigns/** | 서비스 간 통신 | OpenFeign을 통한 Book Service 동기 호출 |
| **model/entity/** | 도메인 모델 | JPA 엔티티 및 복합키 정의 |
| **model/** (DTO) | 데이터 전송 | 요청/응답 DTO 및 메시징 메시지 |
| **repository/** | 데이터 접근 | Spring Data JPA 리포지토리 인터페이스 |
| **service/** | 비즈니스 로직 | 트랜잭션 관리, 권한 검증, 외부 서비스 연동 |



### 폴더 구조

```
src/main/java/com/example/rebooktradingservice/
├── advice/                   # 전역 예외 처리
├── common/                   # 응답 표준화
├── config/                   # RabbitMQ, S3, JPA 설정
├── controller/               # REST API 엔드포인트
├── enums/                    # 상태 관리 enum
├── exception/                # 커스텀 예외
├── feigns/                   # OpenFeign 클라이언트 (Book Service)
├── model/
│   ├── entity/              # JPA 엔티티 (Trading, TradingUser)
│   └── message/             # RabbitMQ 메시지 DTO
├── repository/              # Spring Data JPA 리포지토리
└── service/                 # 비즈니스 로직 (Trading, S3, Notification)
```

