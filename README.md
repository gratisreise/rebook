# Rebook Chat Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.2-blue.svg)](https://gradle.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)

WebSocket/STOMP 프로토콜 기반의 실시간 채팅 마이크로서비스입니다. Spring Boot 3.x를 사용하여 구현되었으며, Eureka 서비스 디스커버리, Spring Cloud Config, RabbitMQ 메시지 큐를 통합한 클라우드 네이티브 아키텍처로 설계되었습니다.

## 📋 목차

- [핵심 기능](#-핵심-기능)
- [기술 스택](#-기술-스택)
- [시스템 아키텍처](#-시스템-아키텍처)
- [API 문서](#-api-문서)
- [프로젝트 구조](#-프로젝트-구조)

## ✨ 핵심 기능

### 실시간 메시징

- **WebSocket 기반 양방향 통신**
  - STOMP 프로토콜을 통한 메시지 라우팅
  - SockJS 폴백으로 브라우저 호환성 보장
  - `/ws-chat` 엔드포인트로 연결

- **채팅방 관리**
  - 1:1 채팅방 자동 생성 및 검색
  - 거래(trading) 기반 채팅방 매핑
  - 사용자 간 중복 방지 로직

- **메시지 히스토리**
  - MongoDB 기반 메시지 영구 저장
  - 페이지네이션 지원 (최신순/오래된 순)
  - 효율적인 인덱스 설계로 빠른 조회

- **읽음 상태 관리**
  - 사용자별 마지막 읽은 시간 추적
  - 읽지 않은 메시지 카운트 실시간 계산
  - 복합키(room_id + user_id) 기반 효율적인 상태 관리

- **이벤트 브로드캐스팅**
  - 사용자 입장/퇴장 자동 알림
  - 채팅 메시지 실시간 전송
  - 구독자 기반 메시지 라우팅 (`/topic/room/{roomId}`)

### 알림 시스템

- **비동기 메시지 발행**
  - RabbitMQ를 통한 이벤트 기반 알림
  - `chat.notification.queue` 큐로 알림 메시지 전송
  - 다른 마이크로서비스와의 느슨한 결합

- **실시간 카운트**
  - 사용자별 읽지 않은 메시지 수 조회
  - 채팅방별 미확인 메시지 집계

### 마이크로서비스 통합

- **서비스 디스커버리**
  - Eureka 클라이언트로 자동 서비스 등록
  - 동적 서비스 발견 및 로드 밸런싱

- **중앙 집중식 설정**
  - Spring Cloud Config 서버와 연동
  - 환경별 설정 분리 (dev, prod)
  - 런타임 설정 갱신 지원

- **API 게이트웨이 통합**
  - `X-User-Id` 헤더를 통한 사용자 인증
  - CORS 정책 설정으로 프론트엔드 통합

## 🛠 기술 스택

### Core Framework
| 기술 | 버전 | 용도 |
|------|------|------|
| Spring Boot | 3.3.13 | 애플리케이션 프레임워크 |
| Java | 17 | 프로그래밍 언어 |
| Gradle | 8.14.2 | 빌드 도구 |

### Real-time Communication
| 기술 | 용도 |
|------|------|
| Spring WebSocket | WebSocket 연결 관리 |
| STOMP | 메시지 프로토콜 |
| SockJS | WebSocket 폴백 |

### Data Storage
| 기술 | 용도 |
|------|------|
| PostgreSQL | 채팅방 및 읽음 상태 저장 (관계형 데이터) |
| MongoDB | 채팅 메시지 저장 (문서형 데이터) |
| Redis | 세션 및 캐싱 |

### Messaging & Integration
| 기술 | 용도 |
|------|------|
| RabbitMQ | 비동기 메시지 큐 |
| Spring Cloud Config | 외부 설정 관리 |
| Eureka Client | 서비스 디스커버리 |
| OpenFeign | 선언적 REST 클라이언트 |

### Monitoring & Observability
| 기술 | 용도 |
|------|------|
| Spring Actuator | 헬스 체크 및 메트릭 |
| Sentry | 에러 추적 및 APM |
| Prometheus | 메트릭 수집 |
| Swagger/OpenAPI 3 | API 문서화 |

### Development Tools
| 기술 | 용도 |
|------|------|
| Lombok | 보일러플레이트 코드 제거 |
| Spring DevTools | 개발 중 자동 재시작 |
| JUnit 5 | 테스트 프레임워크 |
| Jacoco | 코드 커버리지 |

## 🏗 시스템 아키텍처

### 마이크로서비스 아키텍처

```
                          ┌─────────────────┐
                          │  Eureka Server  │
                          │  (Discovery)    │
                          └────────┬────────┘
                                   │
                 ┌─────────────────┼─────────────────┐
                 │                 │                 │
        ┌────────▼────────┐ ┌─────▼──────┐ ┌───────▼────────┐
        │  API Gateway    │ │   Config   │ │ Other Services │
        │                 │ │   Server   │ │                │
        └────────┬────────┘ └────────────┘ └────────────────┘
                 │
        ┌────────▼────────┐
        │  Chat Service   │◄─────── HTTP/WebSocket
        │  (This Service) │
        └────┬────┬───┬───┘
             │    │   │
    ┌────────▼┐ ┌▼───▼──────┐ ┌───────────┐
    │PostgreSQL│ │  MongoDB   │ │  RabbitMQ │
    │(Metadata)│ │ (Messages) │ │(Async Msg)│
    └──────────┘ └────────────┘ └───────────┘
```

### 실시간 메시징 플로우

```
┌─────────────┐                    ┌─────────────────┐                  ┌──────────────┐
│   Client    │                    │  Chat Service   │                  │   Storage    │
└──────┬──────┘                    └────────┬────────┘                  └──────┬───────┘
       │                                    │                                   │
       │  1. Connect /ws-chat               │                                   │
       ├───────────────────────────────────►│                                   │
       │  ◄───── WebSocket Connected        │                                   │
       │                                    │                                   │
       │  2. Subscribe /topic/room/{id}     │                                   │
       ├───────────────────────────────────►│                                   │
       │                                    │                                   │
       │  3. Send /app/api/chats/message    │                                   │
       ├───────────────────────────────────►│                                   │
       │                                    │  4. Save to MongoDB                │
       │                                    ├──────────────────────────────────►│
       │                                    │  5. Update PostgreSQL              │
       │                                    ├──────────────────────────────────►│
       │                                    │  6. Publish to RabbitMQ            │
       │                                    ├──────────────────────►┐            │
       │  ◄──── Broadcast Message ──────────┤                       │            │
       │       /topic/room/{id}             │                       ▼            │
       │                                    │                  ┌──────────┐      │
       │                                    │                  │ RabbitMQ │      │
       │                                    │                  │  Queue   │      │
       │                                    │                  └──────────┘      │
```

### 데이터 아키텍처

#### PostgreSQL (관계형 데이터)
- **chat_room**: 채팅방 정보 및 참여자 매핑
- **chat_read_status**: 사용자별 읽음 상태 (복합키)

#### MongoDB (문서형 데이터)
- **chatting** collection: 채팅 메시지 (타입, 내용, 타임스탬프)

#### 데이터 흐름 전략
- 채팅 메시지는 MongoDB에 저장 (유연한 스키마, 빠른 쓰기 성능)
- 채팅방 및 상태 정보는 PostgreSQL에 저장 (관계 무결성, 트랜잭션)


## 📚 API 문서

### Swagger/OpenAPI 문서

애플리케이션 실행 후 다음 URL에서 확인:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### REST API 엔드포인트

#### 채팅방 관리
```
POST   /api/chats/room                # 채팅방 생성
GET    /api/chats/room/{roomId}       # 채팅방 조회
GET    /api/chats/rooms               # 사용자의 채팅방 목록
DELETE /api/chats/room/{roomId}       # 채팅방 삭제
```

#### 메시지 관리
```
GET    /api/chats/messages/{roomId}   # 메시지 히스토리 (페이징)
POST   /api/chats/message             # 메시지 전송 (REST)
```

#### 읽음 상태
```
PUT    /api/chats/read/{roomId}       # 읽음 상태 업데이트
GET    /api/chats/unread/count        # 읽지 않은 메시지 수
```

### WebSocket/STOMP 엔드포인트

#### 연결
```
WebSocket Endpoint: /ws-chat
SockJS Endpoint: /ws-chat (with fallback)
```

#### 구독 (Subscribe)
```
/topic/room/{roomId}                  # 특정 채팅방 메시지 수신
```

#### 발행 (Send)
```
/app/api/chats/message                # 메시지 전송
/app/api/chats/enter                  # 채팅방 입장
/app/api/chats/leave                  # 채팅방 퇴장
```

### 메시지 포맷

#### 메시지 전송 (Client → Server)
```json
{
  "type": "CHAT",
  "roomId": 1,
  "senderId": "user123",
  "message": "안녕하세요!"
}
```

#### 메시지 수신 (Server → Client)
```json
{
  "id": "507f1f77bcf86cd799439011",
  "type": "CHAT",
  "roomId": 1,
  "senderId": "user123",
  "message": "안녕하세요!",
  "sendAt": "2025-10-11T10:30:00Z"
}
```

#### 메시지 타입
- `ENTER`: 사용자 입장
- `CHAT`: 일반 채팅 메시지
- `LEAVE`: 사용자 퇴장

## 📁 프로젝트 구조

```
src/main/java/com/example/rebookchatservice/
├── RebookChatServiceApplication.java     # 애플리케이션 진입점
│
├── controller/                           # 컨트롤러 레이어
│   ├── ChatMessageController.java        # WebSocket 메시지 핸들러 & REST API
│   ├── ChatRoomController.java           # 채팅방 관리 REST API
│   ├── ChatReadStatusController.java     # 읽음 상태 REST API
│   └── TestController.java               # 테스트용 컨트롤러
│
├── service/                              # 서비스 레이어 (비즈니스 로직)
│   ├── ChatMessageService.java           # 메시지 처리 (쓰기)
│   ├── ChatRoomService.java              # 채팅방 관리 (쓰기)
│   ├── ChatReadStatusService.java        # 읽음 상태 관리 (쓰기)
│   ├── ChatMessageReader.java            # 메시지 조회 (읽기 전용)
│   ├── ChatRoomReader.java               # 채팅방 조회 (읽기 전용)
│   └── ChatReadStatusReader.java         # 읽음 상태 조회 (읽기 전용)
│
├── repository/                           # 데이터 접근 레이어
│   ├── ChatMessageRepository.java        # MongoDB 메시지 저장소
│   ├── ChatRoomRepository.java           # PostgreSQL 채팅방 저장소
│   └── ChatReadStatusRepository.java     # PostgreSQL 읽음 상태 저장소
│
├── model/                                # 데이터 모델
│   ├── ChatMessageRequest.java           # 메시지 요청 DTO
│   ├── ChatMessageResponse.java          # 메시지 응답 DTO
│   ├── ChatRoomRequest.java              # 채팅방 요청 DTO
│   ├── ChatRoomResponse.java             # 채팅방 응답 DTO
│   ├── entity/                           # 엔티티 및 문서
│   │   ├── ChatRoom.java                 # 채팅방 JPA 엔티티
│   │   ├── ChatMessage.java              # 메시지 MongoDB 문서
│   │   ├── ChatReadStatus.java           # 읽음 상태 JPA 엔티티
│   │   └── compositekey/
│   │       └── ChatReadStatusId.java     # 복합키 (room_id + user_id)
│   └── message/
│       └── NotificationChatMessage.java  # 알림 메시지 모델
│
├── config/                               # 설정 클래스
│   ├── WebSocketConfig.java              # WebSocket 및 STOMP 설정
│   ├── RabbitConfig.java                 # RabbitMQ 큐 설정
│   └── SwaggerConfig.java                # Swagger/OpenAPI 설정
│
├── common/                               # 공통 유틸리티
│   ├── CommonResult.java                 # 공통 API 응답 베이스
│   ├── SingleResult.java                 # 단일 결과 응답
│   ├── ListResult.java                   # 리스트 결과 응답
│   ├── PageResponse.java                 # 페이지네이션 응답
│   ├── ResponseService.java              # 응답 래퍼 생성 유틸리티
│   └── ResultCode.java                   # 응답 코드 상수
│
├── exception/                            # 커스텀 예외
│   ├── CInvalidDataException.java        # 유효하지 않은 데이터
│   ├── CMissingDataException.java        # 누락된 데이터
│   └── CDuplicatedDataException.java     # 중복 데이터
│
├── advice/                               # 전역 예외 처리
│   └── GlobalExceptionHandler.java       # @RestControllerAdvice
│
└── utils/                                # 유틸리티
    └── NotificationPublisher.java        # RabbitMQ 알림 발행기

src/main/resources/
├── application.yaml                      # 기본 설정
├── application-dev.yaml                  # 개발 환경 설정
└── application-prod.yaml                 # 프로덕션 환경 설정
```