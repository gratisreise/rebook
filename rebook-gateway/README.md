# Rebook Gateway

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-6DB33F?style=flat&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-6DB33F?style=flat&logo=spring)](https://spring.io/projects/spring-cloud)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A?style=flat&logo=gradle)](https://gradle.org/)

Rebook 마이크로서비스 생태계를 위한 API Gateway 서비스로, 중앙화된 라우팅, JWT 인증, 서비스 디스커버리 및 모니터링 기능을 제공합니다.

## 📋 목차

- [개요](#개요)
- [주요 기능](#주요-기능)
- [아키텍처](#아키텍처)
- [사전 요구사항](#사전-요구사항)
- [시작하기](#시작하기)
- [설정](#설정)
- [API 라우트](#api-라우트)

## 🎯 개요

Rebook Gateway는 Rebook 마이크로서비스 아키텍처에서 모든 클라이언트 요청의 단일 진입점 역할을 합니다. 다음 기능을 제공합니다:

- **중앙화된 인증**: 모든 다운스트림 서비스를 위한 JWT 기반 인증
- **동적 라우팅**: Eureka를 통한 서비스 디스커버리 및 로드 밸런싱
- **프로토콜 지원**: HTTP/REST, WebSocket, Server-Sent Events (SSE)
- **관찰성**: Prometheus 및 Sentry와 통합된 모니터링
- **CORS 관리**: 설정 가능한 교차 출처 리소스 공유

## ✨ 주요 기능

### 인증 및 보안
- 🔐 HMAC-SHA 서명을 사용한 JWT 토큰 검증
- 🛡️ `X-User-Id` 헤더를 통한 사용자 식별 정보 전파
- 🚪 화이트리스트 공개 엔드포인트 (로그인, 회원가입, 헬스체크)
- 🔒 반응형 Spring Security 설정

### 라우팅 및 서비스 디스커버리
- 🔄 Eureka 통합을 통한 클라이언트 사이드 로드 밸런싱
- 🌐 동적 서비스 디스커버리
- 📡 WebSocket 및 SSE 프로토콜 지원
- 🎯 마이크로서비스로의 경로 기반 라우팅

### 관찰성 및 모니터링
- 📊 Prometheus 메트릭 노출
- 🔍 Sentry 에러 추적 및 알림
- ❤️ 헬스체크 엔드포인트
- 📈 운영 인사이트를 위한 Actuator 엔드포인트

## 🏗️ 아키텍처

```
┌─────────────┐
│  클라이언트  │
└──────┬──────┘
       │
       ▼
┌──────────────────────────────────────────────────┐
│       Rebook Gateway (포트 8080)                  │
│                                                  │
│  ┌────────────────┐      ┌──────────────────┐  │
│  │ CustomFilter   │─────▶│    JwtUtil       │  │
│  │  (JWT 인증)    │      │  (토큰 검증)     │  │
│  └────────────────┘      └──────────────────┘  │
│           │                                      │
│           ▼                                      │
│  ┌────────────────┐      ┌──────────────────┐  │
│  │ SecurityConfig │      │  Eureka Client   │  │
│  │ (화이트리스트) │      │ (디스커버리)     │  │
│  └────────────────┘      └──────────────────┘  │
└──────────────┬───────────────────────────────────┘
               │
       ┌───────┴───────┐
       │ Eureka Server │
       │    :8761      │
       └───────┬────────┘
               │
    ┌──────────┼──────────┬──────────┬──────────┐
    ▼          ▼          ▼          ▼          ▼
┌────────┐ ┌──────┐ ┌─────────┐ ┌──────┐ ┌──────┐
│  User  │ │ Book │ │ Trading │ │ Chat │ │ Noti │
│ Service│ │Service│ │ Service │ │Service│ │Service│
└────────┘ └──────┘ └─────────┘ └──────┘ └──────┘
```

### 요청 흐름

1. **클라이언트 요청** → Gateway가 8080 포트로 요청 수신
2. **인증** → `CustomFilter`가 JWT 토큰 추출 및 검증
3. **사용자 컨텍스트** → 토큰에서 사용자 ID를 추출하여 `X-User-Id` 헤더에 추가
4. **서비스 디스커버리** → Eureka가 서비스 인스턴스 위치 확인
5. **로드 밸런싱** → 정상 서비스 인스턴스로 요청 라우팅
6. **응답** → 서비스 응답을 클라이언트에 반환


#### 보안 화이트리스트

공개 엔드포인트 (인증 불필요):
- `/actuator/**` - 헬스체크 및 메트릭
- `/eureka/**` - 서비스 디스커버리
- `/swagger-ui/**` - API 문서
- `/api/auths/**` - 인증 엔드포인트
- 모든 서비스 엔드포인트 (인증은 CustomFilter에서 처리)

## 🛣️ API 라우트

### 서비스 라우팅 테이블

| 경로 패턴 | 대상 서비스 | 프로토콜 | 인증 |
|----------|------------|---------|------|
| `/api/users/**` | USER-SERVICE | HTTP | 필수 |
| `/api/auths/**` | USER-SERVICE | HTTP | 공개 |
| `/api/books/**` | BOOK-SERVICE | HTTP | 필수 |
| `/api/tradings/**` | TRADING-SERVICE | HTTP | 필수 |
| `/api/notifications/**` | NOTIFICATION-SERVICE | HTTP | 필수 |
| `/api/chats/**` | CHAT-SERVICE | HTTP/SSE | 필수 |
| `/api/ws-chat/**` | CHAT-SERVICE | WebSocket | 선택 |


### 프로젝트 구조

```
src/
├── main/
│   ├── java/com/example/rebookgateway/
│   │   ├── RebookGatewayApplication.java  # 메인 애플리케이션
│   │   ├── CustomFilter.java              # 글로벌 인증 필터
│   │   ├── JwtUtil.java                   # JWT 검증 유틸리티
│   │   └── SecurityConfig.java            # 보안 설정
│   └── resources/
│       ├── application.yaml               # 기본 설정
│       ├── application-dev.yaml           # 개발 프로파일
│       └── application-prod.yaml          # 프로덕션 프로파일
└── test/
    └── java/                               # 테스트 파일 (추가 예정)
```