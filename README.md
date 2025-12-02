# Rebook - 중고책 거래 플랫폼

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-6DB33F?style=flat&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-6DB33F?style=flat&logo=spring)](https://spring.io/projects/spring-cloud)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A?style=flat&logo=gradle)](https://gradle.org/)

Rebook은 Spring Cloud 기반 마이크로서비스 아키텍처로 구축된 중고책 거래 플랫폼입니다. 도서 검색, 리뷰, 거래, 실시간 채팅 등의 기능을 제공합니다.
- [Rebook사이트]( https://rebookk.click)

## 목차

- [시스템 아키텍처](#1-시스템-아키텍처)
- [마이크로서비스 구성](#2-마이크로서비스-구성)
- [기술 스택](#3-기술-스택)
- [개발 가이드](#4-개발-가이드)
- [모니터링](#5-모니터링)
- [보안](#6-보안)

## 1. 시스템 아키텍처
### 소프트웨어 아키텍쳐
![소프트웨어아키텍쳐](https://rebook-bucket.s3.ap-northeast-2.amazonaws.com/rebook/software_architecture.png)

## 2. 마이크로서비스 구성

### 인프라 서비스

| 서비스 | 포트 | 설명 | 상세 문서 |
|--------|------|------|-----------|
| **API Gateway** | 8080 | JWT 인증, 라우팅, CORS 관리를 담당하는 API 게이트웨이 | [📖 상세보기](./rebook-gateway/README.md) |
| **Eureka Server** | 8761 | 서비스 디스커버리 및 등록을 담당하는 중앙 레지스트리 | [📖 상세보기](./rebook-eureka/README.md) |
| **Config Server** | 8888 | Git 기반 중앙 집중식 설정 관리 서버 | [📖 상세보기](./rebook-config/README.md) |

### 비즈니스 서비스

| 서비스 | 포트 | 주요 기능 | 상세 문서 |
|--------|------|-----------|-----------|
| **User Service** | 9000 | • 사용자 인증 및 프로필 관리<br>• Keycloak 통합 인증<br>• AWS S3 프로필 이미지 관리<br>• 관심 카테고리 설정 | [📖 상세보기](./rebook-user/README.md) |
| **Book Service** | 9001 | • 도서 검색 및 등록<br>• Naver Books API 연동<br>• Gemini AI 카테고리 자동 분류<br>• 도서 리뷰 및 평점<br>• 북마크 기능 | [📖 상세보기](./rebook-book/README.md) |
| **Trading Service** | 9002 | • 중고책 거래 등록 및 관리<br>• 거래 상태 추적<br>• 판매자-구매자 매칭<br>• 거래 알림 발송 | [📖 상세보기](./rebook-trading/README.md) |
| **Chat Service** | 9003 | • 실시간 1:1 채팅<br>• WebSocket 지원<br>• MongoDB 메시지 저장<br>• 읽음 상태 관리 | [📖 상세보기](./rebook-chat/README.md) |
| **Notification Service** | 9004 | • 실시간 알림 (SSE)<br>• RabbitMQ 메시지 수신<br>• 알림 설정 관리<br>• 카테고리별 알림 필터링 | [📖 상세보기](./rebook-notification/README.md) |

### 설정 저장소

| 저장소 | 설명 | 상세 문서 |
|--------|------|-----------|
| **Config Repository** | Spring Cloud Config 서버가 사용하는 중앙 집중식 설정 파일 저장소 | [📖 상세보기](./rebook-yamls/README.md) |

## 3. 기술 스택

### Backend Framework
- **Java 17** - 최신 LTS 버전
- **Spring Boot 3.3.13** - 애플리케이션 프레임워크
- **Spring Cloud 2023.0.5** - 마이크로서비스 패턴
- **Gradle 8.14.2** - 빌드 자동화

### 마이크로서비스 기술
- **Netflix Eureka** - 서비스 디스커버리
- **Spring Cloud Gateway** - API 게이트웨이
- **Spring Cloud Config** - 중앙 설정 관리
- **OpenFeign** - 선언적 HTTP 클라이언트

### 데이터베이스
- **PostgreSQL** - 관계형 데이터베이스 (서비스별 독립 DB)
- **MongoDB** - NoSQL 데이터베이스 (채팅 메시지)
- **Redis** - 캐싱 및 세션 관리

### 메시징 & 통합
- **RabbitMQ** - 비동기 메시지 큐
- **Server-Sent Events (SSE)** - 실시간 알림
- **WebSocket** - 실시간 채팅

### 외부 API
- **Naver Books API** - 도서 검색
- **Google Gemini AI** - 자동 카테고리 분류
- **AWS S3** - 이미지 스토리지
- **Keycloak** - 인증 서버

### 모니터링 & 관찰성
- **Spring Actuator** - 헬스 체크 및 메트릭
- **Prometheus** - 메트릭 수집
- **Sentry** - 에러 추적 및 로깅


## 4. 개발 가이드

### 프로젝트 구조

```
rebook/
├── rebook-gateway/          # API Gateway
├── rebook-eureka/           # Service Discovery
├── rebook-config/           # Config Server
├── rebook-user/             # User Service
├── rebook-book/             # Book Service
├── rebook-trading/          # Trading Service
├── rebook-chat/             # Chat Service
├── rebook-notification/     # Notification Service
├── rebook-yamls/            # Config Repository
├── compose.yaml             # 도커 컴포즈 파일
├── compose.yaml             # 프로메테우스 메트릭 정보
└── README.md                # 이 파일
```

## 🔗 추가 리소스

- [**설정 관리 가이드**](./rebook-yamls/README.md)

## 5. 모니터링

### 헬스 체크

**로컬 개발 환경**:
```bash
# 전체 서비스 상태 확인
curl http://localhost:8761  # Eureka Dashboard

# 개별 서비스 헬스 체크
curl http://localhost:8080/actuator/health  # Gateway
curl http://localhost:9000/actuator/health  # User Service
curl http://localhost:9001/actuator/health  # Book Service
```

### 메트릭 수집 및 시각화

**로컬 개발 환경**:
```bash
# grafnan Dash board 메트릭
curl http://localhost:3000
```
## 6. 보안

- JWT 기반 인증 (Gateway에서 검증)
- 운영 환경 설정 암호화 (`{cipher}` 프리픽스)
- CORS 설정 및 관리
- 내부외부 인증, 검증 분리
