# Rebook Notification Service

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.x-FF6600)

**Rebook 마이크로서비스 아키텍처의 실시간 알림 서비스**

RabbitMQ 기반 메시지 처리와 Server-Sent Events를 통한 실시간 알림 전송 시스템

</div>

---

## 📋 목차

- [개요](#-개요)
- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [시스템 요구사항](#-시스템-요구사항)
- [API 문서](#-api-문서)
- [아키텍처](#-아키텍처)
- [알림 유형](#-알림-유형)

---

## 📖 개요

Rebook 알림 서비스는 Spring Boot 기반의 마이크로서비스로, 도서 거래 플랫폼의 실시간 알림을 담당합니다. RabbitMQ를 통한 비동기 메시지 처리와 Server-Sent Events(SSE)를 통한 실시간 클라이언트 통신을 제공합니다.

### 핵심 역할

| 기능 | 설명 |
|-----|------|
| 📚 **도서 알림** | 관심 카테고리에 새 도서 등록 시 실시간 알림 |
| 💱 **거래 알림** | 찜한 도서가 거래 가능해질 때 즉시 알림 |
| 💬 **채팅 알림** | 채팅 메시지 도착 시 실시간 알림 전송 |
| 🔔 **SSE 통신** | 지속적 연결을 통한 실시간 푸시 알림 |
| ⚙️ **설정 관리** | 사용자별 알림 수신 설정 관리 |

---

## ✨ 주요 기능

### 1. 실시간 알림 전송
- **Server-Sent Events (SSE)** 기반 실시간 통신
- 사용자별 독립적인 SSE 연결 관리
- 자동 재연결 및 타임아웃 처리
- 끊김 없는 알림 전송 보장

### 2. 메시지 기반 알림 생성
- **RabbitMQ** 를 통한 비동기 메시지 처리
- 3가지 전용 큐 운영 (도서/거래/채팅)
- Topic Exchange 기반 라우팅
- 메시지 손실 방지 및 재시도 메커니즘

### 3. 알림 설정 관리
- 사용자별 알림 수신 설정
- 알림 유형별 on/off 제어
- 자동 설정 생성 및 업데이트
- 읽음/미읽음 상태 추적

### 4. 외부 서비스 연동
- **User Service**: 관심 사용자 조회 (OpenFeign)
- **Chat Service**: 채팅 알림 연동
- **Eureka**: 서비스 디스커버리
- **Spring Cloud Config**: 중앙 설정 관리

---

## 🛠 기술 스택

### 핵심 프레임워크
- **Java 17** - LTS 버전
- **Spring Boot 3.3.13** - 최신 안정 버전
- **Spring Cloud 2023.0.5** - 마이크로서비스 지원

### 주요 의존성

| 카테고리 | 기술 | 버전 | 용도 |
|---------|------|------|------|
| **웹** | Spring Web | 3.3.13 | REST API, SSE |
| **데이터** | Spring Data JPA | 3.3.13 | ORM, 데이터 액세스 |
| **데이터베이스** | PostgreSQL | 16 | 관계형 데이터베이스 |
| **메시징** | Spring AMQP | 3.1.10 | RabbitMQ 연동 |
| **캐싱** | Spring Data Redis | 3.3.13 | 분산 캐싱 |
| **클라우드** | Spring Cloud Config | 4.1.4 | 중앙 설정 관리 |
| **디스커버리** | Netflix Eureka Client | 4.1.4 | 서비스 등록/발견 |
| **HTTP 클라이언트** | OpenFeign | 4.1.4 | 선언적 REST 클라이언트 |
| **모니터링** | Spring Actuator | 3.3.13 | 헬스 체크, 메트릭 |
| **메트릭** | Micrometer Prometheus | 1.14.2 | 메트릭 수집 |
| **에러 트래킹** | Sentry | 7.18.1 | 실시간 에러 모니터링 |
| **API 문서** | SpringDoc OpenAPI | 2.8.2 | Swagger UI 생성 |
| **개발 도구** | Lombok | 1.18.36 | 보일러플레이트 제거 |
| **빌드** | Gradle | 8.14.2 | 빌드 자동화 |

---

## 📚 API 문서

### Swagger UI

애플리케이션 실행 후 다음 URL에서 인터랙티브 API 문서를 확인할 수 있습니다:

```
http://localhost:8080/swagger-ui/index.html
```

### 주요 엔드포인트

#### 알림 관리 API

| Method | Endpoint | 설명 | 파라미터 |
|--------|----------|------|---------|
| `GET` | `/notifications/{userId}` | 사용자 알림 목록 조회 | userId (Long) |
| `GET` | `/notifications/unread/{userId}` | 읽지 않은 알림 조회 | userId (Long) |
| `PUT` | `/notifications/{notificationId}/read` | 알림 읽음 처리 | notificationId (Long) |
| `DELETE` | `/notifications/{notificationId}` | 알림 삭제 | notificationId (Long) |

#### SSE 연결 API

| Method | Endpoint | 설명 | 파라미터 |
|--------|----------|------|---------|
| `GET` | `/notifications/subscribe/{userId}` | SSE 구독 (실시간 알림 수신) | userId (Long) |


#### 알림 설정 API

| Method | Endpoint | 설명 | Body |
|--------|----------|------|------|
| `GET` | `/notification-settings/{userId}` | 알림 설정 조회 | - |
| `PUT` | `/notification-settings` | 알림 설정 업데이트 | NotificationSettingRequest |

#### 모니터링 & 헬스 체크

| Method | Endpoint | 설명 |
|--------|----------|------|
| `GET` | `/actuator/health` | 전체 헬스 체크 |
| `GET` | `/actuator/health/readiness` | 준비 상태 확인 |
| `GET` | `/actuator/health/liveness` | 활성 상태 확인 |
| `GET` | `/actuator/prometheus` | Prometheus 메트릭 |
| `GET` | `/actuator/metrics` | 애플리케이션 메트릭 |

---

## 🏗 아키텍처

### 시스템 아키텍처

```
┌─────────────────────────────────────────────────────────────┐
│                    Rebook 마이크로서비스 생태계                 │
├─────────────────────────────────────────────────────────────┤
│  User Service  │  Book Service  │  Trade Service │ Chat Service  │
└────────┬────────────────┬────────────────┬──────────────┬────┘
         │                │                │              │
         │ (HTTP/Feign)   │                │              │
         │                ▼                ▼              ▼
         │           ┌────────────────────────────────────┐
         │           │        RabbitMQ (Message Broker)   │
         │           │  ┌──────────┬──────────┬─────────┐ │
         │           │  │  Book    │  Trade   │  Chat   │ │
         │           │  │  Queue   │  Queue   │  Queue  │ │
         │           │  └────┬─────┴────┬─────┴────┬────┘ │
         │           └───────┼──────────┼──────────┼──────┘
         │                   │          │          │
         │                   ▼          ▼          ▼
         │         ┌─────────────────────────────────────┐
         │         │   Notification Service              │
         │         │  ┌───────────────────────────────┐  │
         └────────▶│  │  NotificationController       │  │
                   │  │  - REST API                   │  │
                   │  │  - SSE Management             │  │
                   │  └─────────────┬─────────────────┘  │
                   │                │                     │
                   │                ▼                     │
                   │  ┌───────────────────────────────┐  │
                   │  │  NotificationService          │  │
                   │  │  - Message Processing         │  │
                   │  │  - Business Logic             │  │
                   │  │  - SSE Broadcasting           │  │
                   │  └─────────────┬─────────────────┘  │
                   │                │                     │
                   │                ▼                     │
                   │  ┌───────────────────────────────┐  │
                   │  │  NotificationRepository       │  │
                   │  │  - JPA Data Access            │  │
                   │  └─────────────┬─────────────────┘  │
                   └────────────────┼─────────────────────┘
                                    │
                                    ▼
                            ┌──────────────┐
                            │ PostgreSQL   │
                            │  Database    │
                            └──────────────┘

                   ┌─────────────┐
                   │  SSE Client │◀───────── Real-time Push
                   └─────────────┘
```

---

## 🔔 알림 유형

### 1. 도서 알림 (BOOK)

**트리거 조건**: 새 도서가 사용자 관심 카테고리에 등록될 때

- **메시지 소스**: `book.notification.queue`
- **Exchange**: `book.notification.exchange` (Topic)
- **라우팅 키**: `book.notification`
- **대상 사용자**: 해당 카테고리에 관심 등록한 모든 사용자

**메시지 구조**:
```json
{
  "type": "BOOK",
  "bookId": 123,
  "message": "관심 카테고리에 새로운 도서가 등록되었습니다: 클린 코드"
}
```

**워크플로우**:
1. Book Service → RabbitMQ 메시지 발행
2. Notification Service가 메시지 수신
3. User Service에서 관심 카테고리 사용자 조회
4. 각 사용자에게 알림 생성 및 SSE 전송

---

### 2. 거래 알림 (TRADE)

**트리거 조건**: 사용자가 찜한 도서가 거래 가능 상태가 될 때

- **메시지 소스**: `trade.notification.queue`
- **Exchange**: `trade.notification.exchange` (Topic)
- **라우팅 키**: `trade.notification`
- **대상 사용자**: 해당 도서를 찜한 사용자

**워크플로우**:
1. Trade Service → RabbitMQ 메시지 발행
2. Notification Service가 메시지 수신
3. User Service에서 찜한 사용자 조회
4. 각 사용자에게 알림 생성 및 SSE 전송

---

### 3. 채팅 알림 (CHAT)

**트리거 조건**: 사용자에게 새 채팅 메시지가 도착할 때

- **메시지 소스**: `chat.notification.queue`
- **Exchange**: `chat.notification.exchange` (Topic)
- **라우팅 키**: `chat.notification`
- **대상 사용자**: 메시지 수신자

**워크플로우**:
1. Chat Service → RabbitMQ 메시지 발행
2. Notification Service가 메시지 수신
3. 지정된 사용자에게 알림 생성 및 SSE 전송

---


## 📁 프로젝트 구조

```
rebook-notification-service/
├── src/
│   ├── main/
│   │   ├── java/com/notificationservice/
│   │   │   ├── advice/                      # 전역 예외 처리
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── config/                      # 설정 클래스
│   │   │   │   ├── RabbitMQConfig.java
│   │   │   │   ├── SwaggerConfig.java
│   │   │   │   └── WebConfig.java
│   │   │   ├── controller/                  # REST 컨트롤러
│   │   │   │   ├── NotificationController.java
│   │   │   │   └── NotificationSettingController.java
│   │   │   ├── entity/                      # JPA 엔티티
│   │   │   │   ├── Notification.java
│   │   │   │   ├── NotificationSetting.java
│   │   │   │   └── NotificationSettingId.java
│   │   │   ├── exception/                   # 커스텀 예외
│   │   │   │   ├── CMissingDataException.java
│   │   │   │   ├── CInvalidDataException.java
│   │   │   │   └── CNotificationNotFoundException.java
│   │   │   ├── messagequeue/                # RabbitMQ 처리
│   │   │   │   ├── NotificationListener.java
│   │   │   │   └── message/
│   │   │   │       ├── BookNotificationMessage.java
│   │   │   │       ├── TradeNotificationMessage.java
│   │   │   │       └── ChatNotificationMessage.java
│   │   │   ├── model/                       # DTO & 응답 모델
│   │   │   │   ├── request/
│   │   │   │   │   └── NotificationSettingRequest.java
│   │   │   │   ├── response/
│   │   │   │   │   ├── CommonResult.java
│   │   │   │   │   ├── SingleResult.java
│   │   │   │   │   └── ListResult.java
│   │   │   │   └── dto/
│   │   │   │       └── NotificationDto.java
│   │   │   ├── repository/                  # JPA 리포지토리
│   │   │   │   ├── NotificationRepository.java
│   │   │   │   └── NotificationSettingRepository.java
│   │   │   ├── service/                     # 비즈니스 로직
│   │   │   │   ├── NotificationService.java
│   │   │   │   ├── NotificationSettingService.java
│   │   │   │   ├── ResponseService.java
│   │   │   │   └── reader/
│   │   │   │       ├── NotificationReader.java
│   │   │   │       └── NotificationSettingReader.java
│   │   │   ├── client/                      # Feign 클라이언트
│   │   │   │   ├── UserServiceFeignClient.java
│   │   │   │   └── ChatServiceFeignClient.java
│   │   │   └── NotificationServiceApplication.java
│   │   └── resources/
│   │       ├── application.yaml             # 기본 설정
│   │       ├── application-dev.yaml         # 개발 환경
│   │       ├── application-prod.yaml        # 프로덕션
│   │       └── logback-spring.xml           # 로깅 설정
│   └── test/
│       └── java/com/notificationservice/    # 테스트 코드
│           ├── controller/
│           ├── service/
│           └── integration/
├── gradle/                                   # Gradle Wrapper
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── build.gradle                              # Gradle 빌드 설정
├── settings.gradle                           # Gradle 프로젝트 설정
├── gradlew                                   # Gradle Wrapper (Unix)
├── gradlew.bat                               # Gradle Wrapper (Windows)
├── Dockerfile                                # Docker 이미지 빌드
├── docker-compose.yml                        # Docker Compose 설정
├── CLAUDE.md                                 # Claude Code 가이드
└── README.md                                 # 프로젝트 문서
```
