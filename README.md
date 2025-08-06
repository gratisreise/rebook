# Spring Cloud Config 저장소

마이크로서비스 아키텍처를 위한 중앙집중식 설정 관리 저장소입니다. Spring Cloud Config Server에서 사용하는 설정 파일들을 관리합니다.

## 📑 목차

- 📁 [저장소 구조](#-저장소-구조)
- 🌐 [마이크로서비스 아키텍처](#-마이크로서비스-아키텍처)
- ⚙️ [설정 파일 상세 분석](#-설정-파일-상세-분석)
  - 📄 [공통 설정 (application.yaml)](#-공통-설정-applicationyaml)
  - 🔧 [개발환경 공통 설정 (application-dev.yaml)](#-개발환경-공통-설정-application-devyaml)
  - 🏭 [운영환경 공통 설정 (application-prod.yaml)](#-운영환경-공통-설정-application-prodyaml)
- 🚀 [서비스별 설정 상세](#-서비스별-설정-상세)
  - 👤 [User Service](#-user-service)
  - 📚 [Book Service](#-book-service)
  - 💰 [Trading Service](#-trading-service)
  - 💬 [Chat Service](#-chat-service)
  - 🔔 [Notification Service](#-notification-service)
- 🔐 [보안 설정](#-보안-설정)

## 📁 저장소 구조

```
rebook-yamls/
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

## 🌐 마이크로서비스 아키텍처

### 서비스 구성
- **User Service** (포트: 9000) - 사용자 인증, 프로필 관리, AWS S3 연동
- **Book Service** (포트: 9001) - 도서 관리, Gemini AI 연동, Naver API 연동
- **Trading Service** (포트: 9002) - 중고책 거래 관리
- **Chat Service** (포트: 9003) - 실시간 채팅, MongoDB 연동
- **Notification Service** (포트: 9004) - 알림 서비스

### 인프라 구성요소
- **Eureka Server** (포트: 8761) - 서비스 디스커버리 및 등록
- **Config Server** (포트: 8888) - 중앙집중식 설정 관리
- **PostgreSQL** (포트: 5432) - 메인 관계형 데이터베이스
- **MongoDB** - 채팅 메시지 저장용 NoSQL 데이터베이스
- **Redis** (포트: 6379) - 캐싱 및 세션 관리
- **RabbitMQ** (포트: 5672) - 비동기 메시지 큐

## ⚙️ 설정 파일 상세 분석

### 📄 공통 설정 (application.yaml)

모든 마이크로서비스에 공통으로 적용되는 기본 설정입니다.

**주요 설정 항목**:
- **OpenFeign 클라이언트**: 마이크로서비스 간 HTTP 통신을 위한 연결/읽기 타임아웃 설정 (각 5초)
- **Sentry 모니터링**: 운영환경 에러 추적 및 모니터링을 위한 DSN 설정 (암호화 적용)

---

### 🔧 개발환경 공통 설정 (application-dev.yaml)

개발 환경에서 사용하는 공통 설정입니다.

**주요 설정 항목**:
- **데이터베이스**: PostgreSQL 연결 설정 (호스트: postgresql, DB: rebookdb, 개발용 기본 인증정보 사용)
- **JPA/Hibernate**: 자동 스키마 업데이트(update), SQL 로깅 활성화, PostgreSQL 방언 사용
- **Redis**: 캐시 서버 연결 (호스트: redis, 포트: 6379, 세션 관리 및 API 캐싱 용도)
- **RabbitMQ**: 메시지 큐 연결 (호스트: rabbitmq, 포트: 5672, 비동기 메시지 처리 용도)
- **Eureka**: 서비스 디스커버리 클라이언트 설정 (자동 등록 및 레지스트리 조회 활성화)
- **Actuator**: 모든 모니터링 엔드포인트 노출 (health, info, metrics, env, beans 등)

---

### 🏭 운영환경 공통 설정 (application-prod.yaml)

운영 환경에서 사용하는 보안 강화 설정입니다.

**주요 설정 항목**:
- **서버 포트**: 기본 포트 8080 설정
- **데이터베이스**: 운영 DB 서버 연결 (IP: 34.158.202.64, 암호화된 비밀번호 사용)
- **JPA/Hibernate**: SQL 로깅 비활성화, validate 모드 권장 (현재 update 사용 중 ⚠️)
- **Redis**: 암호화된 비밀번호로 캐시 서버 연결
- **RabbitMQ**: admin 계정 사용, 암호화된 비밀번호로 메시지 큐 연결
- **Actuator**: 필수 엔드포인트만 노출 (health, info, prometheus) - 보안 강화
- **Springdoc API 문서**: 운영환경에서 비활성화하여 API 정보 노출 방지
- **Eureka**: 서비스 디스커버리 클라이언트 설정

---

## 🚀 서비스별 설정 상세

### 👤 User Service

사용자 인증, 프로필 관리, AWS S3 파일 업로드를 담당하는 서비스입니다.

**기본 설정 (user-service.yaml)**:
- **파일 업로드**: 최대 파일 크기 및 요청 크기 10MB 제한
- **AWS S3**: 서울 리전(ap-northeast-2) rebookbucket 연동, 암호화된 액세스 키 사용
- **주요 기능**: 프로필 이미지 업로드, 사용자 파일 관리, S3 클라우드 스토리지 연동

**개발환경 (user-service-dev.yaml)**:
- 포트 9000, 서비스명 user-service

**운영환경 (user-service-prod.yaml)**:
- 포트 9000, 전용 데이터베이스 rebookuserdb 사용
- Redis 캐싱 활성화, 모든 인증정보 암호화 처리

---

### 📚 Book Service

도서 정보 관리, AI 기반 추천, 외부 API 연동을 담당하는 서비스입니다.

**기본 설정 (book-service.yaml)**:
- **파일 업로드**: 최대 파일 크기 및 요청 크기 10MB 제한
- **AWS S3**: 도서 표지 이미지 저장용 (rebookbucket, 암호화된 키 사용)
- **Gemini AI**: 도서 추천, 분석, 요약 기능을 위한 API 연동 (gemini-1.5-flash 모델)
- **Naver Book API**: 도서 정보 검색 및 조회용 API 연동

**개발환경 (book-service-dev.yaml)**:
- 포트 9001, 서비스명 book-service

**운영환경 (book-service-prod.yaml)**:
- 포트 9001, 전용 데이터베이스 rebookbookdb 사용
- Redis 캐싱으로 API 응답 최적화, 다중 외부 API 연동

---

### 💰 Trading Service

중고책 거래, 판매, 구매를 관리하는 서비스입니다.

**기본 설정 (trading-service.yaml)**:
- 현재 별도 기본 설정 없음 (application.yaml 공통 설정 사용)

**개발환경 (trading-service-dev.yaml)**:
- 포트 9002, 서비스명 trading-service

**운영환경 (trading-service-prod.yaml)**:
- 포트 9002, 전용 데이터베이스 rebooktradingdb 사용
- Redis 캐싱으로 거래 데이터 최적화, 결제 시스템 연동 준비

---

### 💬 Chat Service

실시간 채팅, 메시지 저장을 담당하는 서비스입니다.

**기본 설정 (chat-service.yaml)**:
- 현재 별도 기본 설정 없음 (application.yaml 공통 설정 사용)

**개발환경 (chat-service-dev.yaml)**:
- 포트 9003, 서비스명 chat-service
- **MongoDB**: 로컬 컨테이너 연동 (호스트: mongodb, DB: rebookchat, 채팅 메시지 저장용 NoSQL)

**운영환경 (chat-service-prod.yaml)**:
- 포트 9003, 다중 데이터베이스 구성
- **PostgreSQL**: 채팅방 메타데이터 저장 (rebookchatdb)
- **MongoDB Atlas**: 클라우드 기반 채팅 메시지 저장
- **Redis**: 실시간 채팅 세션 관리

---

### 🔔 Notification Service

알림 발송, 이메일, 푸시 알림을 담당하는 서비스입니다.

**기본 설정 (notification-service.yaml)**:
- 현재 별도 기본 설정 없음 (application.yaml 공통 설정 사용)

**개발환경 (notification-service-dev.yaml)**:
- 포트 9004, 서비스명 notification-service

**운영환경 (notification-service-prod.yaml)**:
- 포트 9004, 전용 데이터베이스 rebooknotidb 사용
- Redis 캐싱으로 알림 큐 관리, 이메일/푸시 알림 발송 기능

---

## 🔐 보안 설정

### 암호화된 속성

Spring Cloud Config의 암호화 기능을 사용하여 모든 민감한 정보를 보호합니다.

#### 암호화 대상
```yaml
# 데이터베이스 비밀번호
password: "{cipher}a0f8ea4d7d74caffe583c19daba3d7b2..."

# Redis 비밀번호
password: "{cipher}df7e39f3b574c8f0257643d70074a44c..."

# RabbitMQ 비밀번호
password: "{cipher}44cc7c7b2ad2dece6031b1968351707..."

# AWS 액세스 키
access-key: "{cipher}..."
secret-key: "{cipher}..."

# API 키
api-key: "{cipher}..."
client-secret: "{cipher}..."

# Sentry DSN
dsn: "{cipher}469414d181b92d00880c16588a8e5298..."

# MongoDB URI
uri: "{cipher}mongodb+srv://..."
```

### 암호화 키 관리

#### Config Server에서 암호화
```bash
# 평문 암호화
curl -X POST http://config-server:8888/encrypt \
  -H "Content-Type: text/plain" \
  -d "plaintext-password"
```

#### Config Server에서 복호화
```bash
# 암호화된 값 복호화 (테스트용)
curl -X POST http://config-server:8888/decrypt \
  -H "Content-Type: text/plain" \
  -d "{cipher}..."
```
