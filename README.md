# Spring Cloud Config Repository

마이크로서비스 아키텍처를 위한 중앙집중식 설정 관리 저장소입니다. Spring Cloud Config Server에서 사용하는 설정 파일들을 관리합니다.

## 📁 저장소 구조

```
gratisreise-rebook-yamls/
├── README.md
└── config-repo/
    ├── application.yaml              # 공통 기본 설정
    ├── application-dev.yaml          # 공통 개발환경 설정
    ├── application-prod.yaml         # 공통 운영환경 설정
    ├── book-service.yaml             # 도서 서비스 기본 설정
    ├── book-service-dev.yaml         # 도서 서비스 개발환경
    ├── book-service-prod.yaml        # 도서 서비스 운영환경
    ├── chat-service.yaml             # 채팅 서비스 기본 설정
    ├── chat-service-dev.yaml         # 채팅 서비스 개발환경
    ├── chat-service-prod.yaml        # 채팅 서비스 운영환경
    ├── notification-service.yaml     # 알림 서비스 기본 설정
    ├── notification-service-dev.yaml # 알림 서비스 개발환경
    ├── notification-service-prod.yaml# 알림 서비스 운영환경
    ├── trading-service.yaml          # 거래 서비스 기본 설정
    ├── trading-service-dev.yaml      # 거래 서비스 개발환경
    ├── trading-service-prod.yaml     # 거래 서비스 운영환경
    ├── user-service.yaml             # 사용자 서비스 기본 설정
    ├── user-service-dev.yaml         # 사용자 서비스 개발환경
    └── user-service-prod.yaml        # 사용자 서비스 운영환경
```

## 🌐 서비스 아키텍처

### 마이크로서비스 구성
- **User Service** (포트: 9000) - 사용자 관리
- **Book Service** (포트: 9001) - 도서 관리 
- **Trading Service** (포트: 9002) - 거래 관리
- **Chat Service** (포트: 9003) - 채팅 서비스
- **Notification Service** (포트: 9004) - 알림 서비스

### 인프라 구성요소
- **Eureka Server** - 서비스 디스커버리
- **PostgreSQL** - 메인 데이터베이스
- **MongoDB** - 채팅 데이터 저장
- **Redis** - 캐싱 및 세션 관리
- **RabbitMQ** - 메시지 큐

## ⚙️ 설정 파일 설명

### 📄 공통 설정 (application.yaml)

#### 기본 설정
- **OpenFeign**: HTTP 클라이언트 연결/읽기 타임아웃 5초
- **Sentry**: 에러 모니터링 및 로깅 (암호화된 DSN)

#### 개발환경 (application-dev.yaml)
```yaml
# 주요 설정
- Database: PostgreSQL (Docker 컨테이너)
- JPA: ddl-auto=update (자동 스키마 업데이트)
- Redis: 캐싱 및 세션 관리
- RabbitMQ: 비동기 메시지 처리
- Eureka: 서비스 등록 및 발견
- Actuator: 모든 엔드포인트 노출 (모니터링)
```

#### 운영환경 (application-prod.yaml)
```yaml
# 주요 설정
- JPA: ddl-auto=validate (스키마 검증만)
- RabbitMQ: 암호화된 인증정보
- Eureka: 동적 서비스 등록
- Actuator: health, info, prometheus만 노출
- Logging: INFO 레벨 (보안 강화)
```

### 🏢 서비스별 설정

#### 👤 User Service
**기본 설정**:
- AWS S3 연동 (프로필 이미지 등)

**개발환경**: 
- 포트: 9000

**운영환경**:
- 전용 PostgreSQL DB: `rebookuserdb`
- Redis 캐싱 (암호화된 패스워드)

#### 📚 Book Service
**기본 설정**:
- AWS S3 연동 (도서 이미지)
- Gemini AI API (도서 추천/분석)
- Naver 도서 API (도서 정보 검색)

**개발환경**: 
- 포트: 9001

**운영환경**:
- 전용 PostgreSQL DB: `rebookbookdb`
- Redis 캐싱

#### 💰 Trading Service
**개발환경**: 
- 포트: 9002

**운영환경**:
- 전용 PostgreSQL DB: `rebooktradingdb`
- Redis 캐싱

#### 💬 Chat Service
**기본 설정**: 
- (현재 빈 설정)

**개발환경**:
- 포트: 9003
- MongoDB (Docker 컨테이너)

**운영환경**:
- PostgreSQL DB: `rebookchatdb`
- MongoDB Atlas 클러스터
- Redis 캐싱

#### 🔔 Notification Service
**기본 설정**: 
- (현재 빈 설정)

**개발환경**: 
- 포트: 9004

**운영환경**:
- PostgreSQL DB: `rebooknotidb`
- Redis 캐싱

## 🔐 보안 설정

### 암호화된 속성
모든 민감한 정보는 Spring Cloud Config의 암호화 기능을 사용합니다:

- 데이터베이스 패스워드: `{cipher}...`
- Redis 패스워드: `{cipher}...`
- RabbitMQ 패스워드: `{cipher}...`
- AWS 액세스 키: `{cipher}...`
- API 키들: `{cipher}...`
- Sentry DSN: `{cipher}...`

### 복호화 방법
Config Server에서 JCE(Java Cryptography Extension) 키를 사용하여 자동 복호화됩니다.

## 🚀 사용 방법

### Config Server 연동
각 마이크로서비스는 다음과 같이 Config Server에 연결합니다:

```yaml
# bootstrap.yaml 또는 application.yaml
spring:
  cloud:
    config:
      uri: http://config-server:8888
      name: {service-name}  # user-service, book-service 등
      profile: dev  # 또는 prod
```

### 설정 우선순위
1. `{service-name}-{profile}.yaml` (최우선)
2. `{service-name}.yaml`
3. `application-{profile}.yaml`
4. `application.yaml` (기본값)

## 🗄️ 데이터베이스 구성

### 개발환경
- **공통 DB**: `rebookdb` (모든 서비스 공유)
- **Chat 전용**: MongoDB 컨테이너

### 운영환경
- **User Service**: `rebookuserdb`
- **Book Service**: `rebookbookdb`
- **Trading Service**: `rebooktradingdb`
- **Chat Service**: `rebookchatdb` + MongoDB Atlas
- **Notification Service**: `rebooknotidb`

## 📊 모니터링 & 로깅

### 개발환경
- **Actuator**: 모든 엔드포인트 노출
- **JPA**: SQL 로깅 활성화
- **Root 로깅**: DEBUG 레벨

### 운영환경
- **Actuator**: health, info, prometheus만 노출
- **JPA**: SQL 로깅 제한
- **Root 로깅**: INFO 레벨
- **Sentry**: 에러 추적 및 모니터링

## 🔧 외부 서비스 연동

### AWS S3
- **용도**: 이미지 파일 저장
- **서비스**: User Service, Book Service
- **리전**: ap-northeast-2 (서울)

### Gemini AI
- **용도**: 도서 추천 및 분석
- **서비스**: Book Service

### Naver API
- **용도**: 도서 정보 검색
- **서비스**: Book Service

## 🐳 Docker 환경

### 개발환경 서비스
```yaml
services:
  - rebook-database (PostgreSQL)
  - redis
  - rabbitmq
  - mongodb
  - rebook-eureka
```

### 네트워크 설정
- 모든 서비스는 Docker 네트워크로 연결
- 서비스명으로 내부 통신 (예: `redis`, `rabbitmq`)

## 📝 환경 변수

운영환경에서 다음 환경 변수들이 필요합니다:

```bash
# RabbitMQ
rabbitmq-uri=${RABBITMQ_HOST}

# Eureka
eurekahost=${EUREKA_HOST}

# 암호화 키 (Config Server)
ENCRYPT_KEY=${ENCRYPTION_KEY}
```

## 🔄 설정 업데이트

### 실시간 업데이트
1. Config 저장소 업데이트
2. Spring Cloud Bus를 통한 자동 갱신 또는
3. `/actuator/refresh` 엔드포인트 호출

### 암호화된 값 추가
```bash
# Config Server에서 암호화
curl -X POST http://config-server:8888/encrypt -d "plaintext-value"
```

## 🚨 주의사항

1. **민감 정보**: 모든 패스워드와 API 키는 반드시 암호화하여 저장
2. **프로필 관리**: 개발/운영 환경 설정 분리 유지
3. **포트 충돌**: 각 서비스별 고유 포트 사용
4. **데이터베이스**: 운영환경에서는 서비스별 전용 DB 사용
5. **로깅**: 운영환경에서는 민감 정보 로깅 방지