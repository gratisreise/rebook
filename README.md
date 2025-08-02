# Spring Cloud Config 저장소

마이크로서비스 아키텍처를 위한 중앙집중식 설정 관리 저장소입니다. Spring Cloud Config Server에서 사용하는 설정 파일들을 관리합니다.

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

#### OpenFeign 클라이언트 설정
```yaml
feign:
  client:
    config:
      default:
        connectTimeout: 5000  # 연결 타임아웃 5초
        readTimeout: 5000     # 읽기 타임아웃 5초
```

#### Sentry 모니터링 설정
```yaml
sentry:
  dsn: "{cipher}..."        # 암호화된 Sentry DSN
  send-default-pii: true    # 개인정보 전송 활성화 (에러 추적용)
```

**용도**:
- OpenFeign: 마이크로서비스 간 HTTP 통신
- Sentry: 운영환경 에러 추적 및 모니터링

---

### 🔧 개발환경 공통 설정 (application-dev.yaml)

개발 환경에서 사용하는 공통 설정입니다.

#### 데이터베이스 설정
```yaml
spring:
  datasource:
    url: jdbc:postgresql://postgresql:5432/rebookdb
    username: postgres
    password: postgres
    driverClassName: org.postgresql.Driver
```
- **호스트**: `postgresql` (Docker 컨테이너명)
- **데이터베이스**: `rebookdb` (모든 서비스가 공유)
- **인증정보**: 개발용 기본값 사용

#### JPA/Hibernate 설정
```yaml
spring:
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update    # 자동 스키마 업데이트
    show-sql: true        # SQL 로깅 활성화
    properties:
      hibernate:
        format_sql: true  # SQL 포맷팅
```
- **ddl-auto**: `update` - 엔티티 변경 시 자동으로 테이블 스키마 업데이트
- **show-sql**: 개발 편의를 위한 SQL 로깅

#### Redis 캐시 설정
```yaml
spring:
  data:
    redis:
      host: redis
      port: 6379
      password: redis123
```
- **용도**: 세션 관리, API 응답 캐싱, 임시 데이터 저장

#### RabbitMQ 메시지 큐 설정
```yaml
spring:
  rabbitmq:
    host: rabbitmq
    port: 5672
    username: guest
    password: guest
```
- **용도**: 비동기 메시지 처리, 이벤트 기반 통신

#### Eureka 클라이언트 설정
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://rebook-eureka:8761/eureka/
    register-with-eureka: true
    fetch-registry: true
  instance:
    instance-id: ${spring.application.name}:main
```
- **register-with-eureka**: 서비스 자동 등록
- **fetch-registry**: 다른 서비스 정보 조회

#### Actuator 모니터링 설정
```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"  # 모든 엔드포인트 노출
```
- **개발환경**: 모든 Actuator 엔드포인트 접근 가능
- **엔드포인트**: health, info, metrics, env, beans 등

---

### 🏭 운영환경 공통 설정 (application-prod.yaml)

운영 환경에서 사용하는 보안 강화 설정입니다.

#### 서버 포트 설정
```yaml
server:
  port: 8080  # 기본 포트
```

#### 데이터베이스 설정
```yaml
spring:
  datasource:
    url: jdbc:postgresql://34.158.202.64:5432/rebookdb
    username: postgres
    password: "{cipher}..."  # 암호화된 비밀번호
    driverClassName: org.postgresql.Driver
```
- **호스트**: 운영 데이터베이스 IP 주소
- **보안**: 비밀번호 암호화 처리

#### JPA 설정
```yaml
spring:
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update    # ⚠️ 주의: validate 또는 none 권장
    show-sql: false       # SQL 로깅 비활성화
```
- **보안**: SQL 로깅 비활성화로 민감 정보 노출 방지
- **성능**: 불필요한 로깅 제거

#### Redis 설정
```yaml
spring:
  data:
    redis:
      host: redis
      port: 6379
      password: "{cipher}..."  # 암호화된 비밀번호
```

#### RabbitMQ 설정
```yaml
spring:
  rabbitmq:
    host: rabbitmq
    port: 5672
    username: admin
    password: "{cipher}..."  # 암호화된 비밀번호
```
- **보안**: admin 계정 사용, 비밀번호 암호화

#### Actuator 설정
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus  # 필수 엔드포인트만 노출
```
- **보안**: 최소 권한 원칙 적용

#### Springdoc API 문서
```yaml
springdoc:
  api-docs:
    enabled: false  # 운영환경에서 API 문서 비활성화
```

#### Eureka 설정
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://rebook-eureka:8761/eureka/
    register-with-eureka: true
    fetch-registry: true
  instance:
    instance-id: ${spring.application.name}:main
```

---

## 🚀 서비스별 설정 상세

### 👤 User Service

사용자 인증, 프로필 관리, AWS S3 파일 업로드를 담당하는 서비스입니다.

#### 기본 설정 (user-service.yaml)
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB        # 최대 파일 크기
      max-request-size: 10MB     # 최대 요청 크기

aws:
  s3:
    bucket-name: rebookbucket    # S3 버킷명
    region: ap-northeast-2       # 서울 리전
    access-key: "{cipher}..."    # 암호화된 액세스 키
    secret-key: "{cipher}..."    # 암호화된 시크릿 키
```
**기능**:
- 프로필 이미지 업로드
- 사용자 파일 관리
- S3 클라우드 스토리지 연동

#### 개발환경 (user-service-dev.yaml)
```yaml
server:
  port: 9000

spring:
  application:
    name: user-service
```

#### 운영환경 (user-service-prod.yaml)
```yaml
server:
  port: 9000

spring:
  application:
    name: user-service
  datasource:
    url: jdbc:postgresql://34.158.202.64:5432/rebookuserdb
    username: postgres
    password: "{cipher}..."
  data:
    redis:
      host: redis
      port: 6379
      password: "{cipher}..."
```
**주요 특징**:
- 전용 데이터베이스: `rebookuserdb`
- Redis 캐싱 활성화
- 암호화된 인증정보

---

### 📚 Book Service

도서 정보 관리, AI 기반 추천, 외부 API 연동을 담당하는 서비스입니다.

#### 기본 설정 (book-service.yaml)
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB

aws:
  s3:
    bucket-name: rebookbucket
    region: ap-northeast-2
    access-key: "{cipher}..."
    secret-key: "{cipher}..."

gemini:
  api:
    key: "{cipher}..."
    url: https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent

naver:
  api:
    client-id: "{cipher}..."
    client-secret: "{cipher}..."
    url: https://openapi.naver.com/v1/search/book.json
```

**외부 서비스 연동**:
1. **AWS S3**: 도서 표지 이미지 저장
2. **Gemini AI**: 도서 추천, 분석, 요약 기능
3. **Naver Book API**: 도서 정보 검색 및 조회

#### 개발환경 (book-service-dev.yaml)
```yaml
server:
  port: 9001

spring:
  application:
    name: book-service
```

#### 운영환경 (book-service-prod.yaml)
```yaml
server:
  port: 9001

spring:
  application:
    name: book-service
  datasource:
    url: jdbc:postgresql://34.158.202.64:5432/rebookbookdb
    username: postgres
    password: "{cipher}..."
  data:
    redis:
      host: redis
      port: 6379
      password: "{cipher}..."
```
**주요 특징**:
- 전용 데이터베이스: `rebookbookdb`
- Redis 캐싱으로 API 응답 최적화
- 다중 외부 API 연동

---

### 💰 Trading Service

중고책 거래, 판매, 구매를 관리하는 서비스입니다.

#### 기본 설정 (trading-service.yaml)
```yaml
# 현재 기본 설정 없음 (application.yaml 기본값 사용)
```

#### 개발환경 (trading-service-dev.yaml)
```yaml
server:
  port: 9002

spring:
  application:
    name: trading-service
```

#### 운영환경 (trading-service-prod.yaml)
```yaml
server:
  port: 9002

spring:
  application:
    name: trading-service
  datasource:
    url: jdbc:postgresql://34.158.202.64:5432/rebooktradingdb
    username: postgres
    password: "{cipher}..."
  data:
    redis:
      host: redis
      port: 6379
      password: "{cipher}..."
```
**주요 특징**:
- 전용 데이터베이스: `rebooktradingdb`
- 거래 데이터 캐싱
- 결제 시스템 연동 준비

---

### 💬 Chat Service

실시간 채팅, 메시지 저장을 담당하는 서비스입니다.

#### 기본 설정 (chat-service.yaml)
```yaml
# 현재 기본 설정 없음
```

#### 개발환경 (chat-service-dev.yaml)
```yaml
server:
  port: 9003

spring:
  application:
    name: chat-service
  data:
    mongodb:
      host: mongodb
      port: 27017
      database: rebookchat
      authentication-database: admin
```
**MongoDB 연동**:
- 채팅 메시지 저장
- NoSQL 기반 빠른 조회
- Docker 컨테이너 사용

#### 운영환경 (chat-service-prod.yaml)
```yaml
server:
  port: 9003

spring:
  application:
    name: chat-service
  datasource:
    url: jdbc:postgresql://34.158.202.64:5432/rebookchatdb
    username: postgres
    password: "{cipher}..."
  data:
    mongodb:
      uri: "{cipher}mongodb+srv://..."  # MongoDB Atlas
    redis:
      host: redis
      port: 6379
      password: "{cipher}..."
```
**주요 특징**:
- PostgreSQL: 채팅방 메타데이터
- MongoDB Atlas: 클라우드 기반 메시지 저장
- Redis: 실시간 채팅 세션 관리

---

### 🔔 Notification Service

알림 발송, 이메일, 푸시 알림을 담당하는 서비스입니다.

#### 기본 설정 (notification-service.yaml)
```yaml
# 현재 기본 설정 없음
```

#### 개발환경 (notification-service-dev.yaml)
```yaml
server:
  port: 9004

spring:
  application:
    name: notification-service
```

#### 운영환경 (notification-service-prod.yaml)
```yaml
server:
  port: 9004

spring:
  application:
    name: notification-service
  datasource:
    url: jdbc:postgresql://34.158.202.64:5432/rebooknotidb
    username: postgres
    password: "{cipher}..."
  data:
    redis:
      host: redis
      port: 6379
      password: "{cipher}..."
```
**주요 특징**:
- 전용 데이터베이스: `rebooknotidb`
- 알림 큐 관리
- 이메일/푸시 알림 발송

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

#### 환경 변수 설정
```bash
# Config Server 시작 시 필요
export ENCRYPT_KEY="your-encryption-key"
```

### 보안 체크리스트

- [x] 운영환경 모든 인증정보 암호화
- [ ] 개발환경 인증정보 암호화 (권장)
- [x] 환경별 인증정보 분리
- [ ] 정기적인 암호화 키 교체 프로세스
- [x] Actuator 엔드포인트 접근 제한 (운영환경)
- [ ] API 키 교체 절차 문서화
- [x] 민감 정보 로깅 방지 (운영환경)

---

## 📊 데이터베이스 구성

### 개발환경 데이터베이스

#### 공통 데이터베이스
- **호스트**: `postgresql` (Docker 컨테이너)
- **포트**: 5432
- **데이터베이스**: `rebookdb`
- **용도**: 모든 서비스가 공유 사용
- **이점**: 개발 편의성, 빠른 설정

#### MongoDB (Chat Service 전용)
- **호스트**: `mongodb` (Docker 컨테이너)
- **포트**: 27017
- **데이터베이스**: `rebookchat`
- **용도**: 채팅 메시지 저장

### 운영환경 데이터베이스

#### 서비스별 전용 데이터베이스
- **User Service**: `rebookuserdb`
- **Book Service**: `rebookbookdb`
- **Trading Service**: `rebooktradingdb`
- **Chat Service**: `rebookchatdb`
- **Notification Service**: `rebooknotidb`

#### MongoDB Atlas (Chat Service)
- **용도**: 클라우드 기반 메시지 저장
- **연결**: MongoDB Atlas 클러스터
- **보안**: 암호화된 URI

#### 데이터베이스 서버 정보
- **호스트**: 34.158.202.64
- **포트**: 5432
- **엔진**: PostgreSQL
- **보안**: 암호화된 인증정보

---

## 🐳 Docker 환경 설정

### Docker Compose 서비스 구성

#### 개발환경 컨테이너
```yaml
services:
  # 데이터베이스
  postgresql:
    image: postgres:latest
    ports: 5432:5432
    environment:
      POSTGRES_DB: rebookdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres

  # 캐시
  redis:
    image: redis:latest
    ports: 6379:6379
    command: redis-server --requirepass redis123

  # 메시지 큐
  rabbitmq:
    image: rabbitmq:management
    ports:
      - 5672:5672   # AMQP
      - 15672:15672 # Management UI
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest

  # NoSQL 데이터베이스
  mongodb:
    image: mongo:latest
    ports: 27017:27017

  # 서비스 디스커버리
  rebook-eureka:
    build: ./eureka-server
    ports: 8761:8761
```

### Docker 네트워크

모든 서비스는 Docker 내부 네트워크로 연결되어 서비스명으로 통신합니다:

```yaml
# 예시
redis:
  host: redis           # 컨테이너명이 호스트명
rabbitmq:
  host: rabbitmq
postgresql:
  host: postgresql
mongodb:
  host: mongodb
```

---

## 🚀 Config Server 연동 방법

### Spring Cloud Config Client 설정

각 마이크로서비스는 Config Server에서 설정을 가져옵니다.

#### bootstrap.yaml (또는 bootstrap.properties)
```yaml
spring:
  application:
    name: user-service  # 서비스명
  cloud:
    config:
      uri: http://config-server:8888
      fail-fast: true
      retry:
        initial-interval: 1000
        max-attempts: 6
        multiplier: 1.1
  profiles:
    active: dev  # 또는 prod
```

#### application.yaml (대안)
```yaml
spring:
  config:
    import: "optional:configserver:http://config-server:8888"
  application:
    name: user-service
  profiles:
    active: dev
```

### 설정 우선순위

Config Server는 다음 순서로 설정을 로드합니다 (낮은 숫자가 높은 우선순위):

1. `{service-name}-{profile}.yaml` (최우선)
   - 예: `user-service-dev.yaml`
   - 예: `book-service-prod.yaml`

2. `{service-name}.yaml`
   - 예: `user-service.yaml`
   - 서비스별 기본 설정

3. `application-{profile}.yaml`
   - 예: `application-dev.yaml`
   - 환경별 공통 설정

4. `application.yaml` (최하위)
   - 모든 서비스의 기본 설정

### 설정 우선순위 예시

User Service (dev 환경)에서 실제 적용되는 설정:

```yaml
# application.yaml
feign.client.config.default.connectTimeout: 5000

# application-dev.yaml
spring.datasource.url: jdbc:postgresql://postgresql:5432/rebookdb

# user-service.yaml
aws.s3.bucket-name: rebookbucket

# user-service-dev.yaml (최우선)
server.port: 9000
```

---

## 📊 모니터링 & 관찰성

### Actuator 엔드포인트

#### 개발환경 - 모든 엔드포인트 노출
```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

**사용 가능한 엔드포인트**:
- `http://localhost:9000/actuator/health` - 헬스체크
- `http://localhost:9000/actuator/info` - 애플리케이션 정보
- `http://localhost:9000/actuator/metrics` - 메트릭
- `http://localhost:9000/actuator/env` - 환경 변수
- `http://localhost:9000/actuator/beans` - Spring Bean 목록
- `http://localhost:9000/actuator/mappings` - URL 매핑
- `http://localhost:9000/actuator/loggers` - 로거 설정
- `http://localhost:9000/actuator/threaddump` - 스레드 덤프

#### 운영환경 - 최소 엔드포인트만 노출
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
```

**노출 엔드포인트**:
- `health` - 서비스 상태 확인
- `info` - 기본 정보
- `prometheus` - Prometheus 메트릭 수집

### JPA SQL 로깅

#### 개발환경 - SQL 로깅 활성화
```yaml
spring:
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true  # SQL 포맷팅
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

#### 운영환경 - SQL 로깅 비활성화
```yaml
spring:
  jpa:
    show-sql: false
logging:
  level:
    root: INFO
```

### Sentry 에러 추적

모든 환경에서 Sentry를 통한 에러 모니터링이 활성화되어 있습니다.

```yaml
sentry:
  dsn: "{cipher}..."
  send-default-pii: true
```

**기능**:
- 실시간 에러 추적
- 스택 트레이스 분석
- 사용자 컨텍스트 수집
- 성능 모니터링

---

## 🔄 설정 동적 업데이트

### Spring Cloud Bus를 통한 자동 갱신

#### 1. 설정 변경
```bash
# Git 저장소 업데이트
cd rebook-yamls
git add config-repo/user-service-dev.yaml
git commit -m "Update user service config"
git push origin main
```

#### 2. Config Server 갱신
```bash
# Config Server에 새 설정 로드 요청
curl -X POST http://config-server:8888/actuator/refresh
```

#### 3. 특정 서비스 갱신
```bash
# 특정 마이크로서비스 설정 갱신
curl -X POST http://localhost:9000/actuator/refresh
```

#### 4. 모든 서비스 갱신 (Spring Cloud Bus 사용 시)
```bash
# RabbitMQ를 통해 모든 서비스에 갱신 이벤트 전파
curl -X POST http://config-server:8888/actuator/bus-refresh
```

### @RefreshScope 사용

동적으로 갱신 가능한 Bean 설정:

```java
@Component
@RefreshScope
public class ConfigurableComponent {
    @Value("${custom.property}")
    private String customProperty;
}
```

---

## 🧪 설정 검증 및 테스트

### Config Server API로 설정 확인

#### 1. 특정 서비스 설정 조회
```bash
# User Service 개발환경 설정
curl http://config-server:8888/user-service/dev

# Book Service 운영환경 설정
curl http://config-server:8888/book-service/prod
```

#### 2. 설정 JSON 형식으로 조회
```bash
curl http://config-server:8888/user-service/dev/main
```

#### 3. 특정 파일 직접 조회
```bash
# user-service-dev.yaml 파일 조회
curl http://config-server:8888/main/user-service-dev.yaml

# application-prod.yaml 파일 조회
curl http://config-server:8888/main/application-prod.yaml
```

### 암호화 테스트

#### 암호화 테스트
```bash
# 평문을 암호화
curl -X POST http://config-server:8888/encrypt \
  -H "Content-Type: text/plain" \
  -d "mySecretPassword"
```

#### 복호화 테스트
```bash
# 암호화된 값 복호화
curl -X POST http://config-server:8888/decrypt \
  -H "Content-Type: text/plain" \
  -d "{cipher}a0f8ea4d7d74caffe583c19daba3d7b2..."
```

---

## 🚨 보안 및 운영 주의사항

### 🔴 치명적 (CRITICAL) - 즉시 조치 필요

#### 1. 개발환경 인증정보 보안
**현재 상태**:
```yaml
# application-dev.yaml
password: postgres        # 평문 노출
password: redis123        # 평문 노출
username: guest           # 기본값 사용
password: guest           # 기본값 사용
```

**권장 조치**:
```yaml
# 모든 인증정보 암호화
password: "{cipher}..."
username: "{cipher}..."
```

#### 2. 운영환경 JPA 설정 위험
**현재 상태**:
```yaml
# application-prod.yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update  # ⚠️ 데이터 손실 위험
```

**권장 조치**:
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate  # 또는 none
```

**위험도**: 높음 (의도하지 않은 스키마 변경으로 데이터 손실 가능)

#### 3. 하드코딩된 호스트명
**현재 상태**:
```yaml
redis:
  host: redis           # Docker 컨테이너명 하드코딩
rabbitmq:
  host: rabbitmq        # Docker 컨테이너명 하드코딩
```

**권장 조치**:
```yaml
redis:
  host: ${REDIS_HOST:redis}
rabbitmq:
  host: ${RABBITMQ_HOST:rabbitmq}
```

### 🟠 높음 (HIGH) - 우선 개선 필요

#### 4. 데이터베이스 연결 풀 설정 부재
**권장 추가 설정**:
```yaml
spring:
  datasource:
    hikari:
      connection-timeout: 30000
      maximum-pool-size: 10
      minimum-idle: 5
      idle-timeout: 600000
      max-lifetime: 1800000
```

#### 5. Circuit Breaker 패턴 미적용
**권장 추가 설정** (application.yaml):
```yaml
resilience4j:
  circuitbreaker:
    instances:
      default:
        failure-rate-threshold: 50
        wait-duration-in-open-state: 10000
        permitted-number-of-calls-in-half-open-state: 3
        sliding-window-size: 10
  retry:
    instances:
      default:
        max-attempts: 3
        wait-duration: 1000
  timeout:
    instances:
      default:
        timeout-duration: 5s
```

#### 6. 서비스별 기본 설정 누락
**현재 상태**:
- `chat-service.yaml`: 빈 파일
- `notification-service.yaml`: 빈 파일
- `trading-service.yaml`: 빈 파일

**권장 조치**: 각 서비스별 기본 설정 추가

### 🟡 중간 (MEDIUM) - 개선 권장

#### 7. 구조화된 로깅
**권장 추가 설정**:
```yaml
logging:
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
  file:
    name: logs/application.log
  level:
    root: INFO
    com.rebook: DEBUG
```

#### 8. 모니터링 강화
**권장 추가 설정**:
```yaml
management:
  metrics:
    export:
      prometheus:
        enabled: true
    distribution:
      percentiles-histogram:
        http.server.requests: true
```

---

## 📋 개선 우선순위 로드맵

### 즉시 조치 (1-2일)
1. ✅ 운영환경 JPA `ddl-auto` 설정 변경 (`update` → `validate`)
2. ✅ 개발환경 인증정보 암호화
3. ✅ 환경변수 기반 호스트명 설정

### 단기 (1-2주)
4. ⏳ 서비스별 기본 설정 추가 (chat, notification, trading)
5. ⏳ HikariCP 연결 풀 설정
6. ⏳ Resilience4j Circuit Breaker 적용
7. ⏳ 타임아웃 및 재시도 정책 설정

### 중기 (1개월)
8. 📅 구조화된 로깅 시스템 (JSON 로깅, ELK Stack)
9. 📅 Prometheus + Grafana 모니터링 대시보드
10. 📅 운영 문서 작성 (장애 복구, 백업 절차)
11. 📅 암호화 키 교체 프로세스 수립

### 장기 (2-3개월)
12. 🔮 분산 트레이싱 (Spring Cloud Sleuth + Zipkin)
13. 🔮 API Gateway 통합 설정
14. 🔮 서비스 메시 도입 검토 (Istio)
15. 🔮 자동화된 설정 검증 파이프라인

---

## 🔐 보안 점검 체크리스트

### 인증정보 보안
- [x] 운영환경 데이터베이스 비밀번호 암호화
- [x] 운영환경 Redis 비밀번호 암호화
- [x] 운영환경 RabbitMQ 비밀번호 암호화
- [ ] 개발환경 인증정보 암호화
- [ ] 정기적인 비밀번호 교체 프로세스
- [x] AWS 액세스 키 암호화
- [x] API 키 암호화

### 네트워크 보안
- [ ] 방화벽 규칙 설정
- [ ] VPC 격리
- [ ] TLS/SSL 인증서 적용
- [ ] API Gateway 인증/인가

### 접근 제어
- [x] Actuator 엔드포인트 제한 (운영환경)
- [ ] Actuator 인증 설정
- [ ] API 엔드포인트 인증/인가
- [ ] 역할 기반 접근 제어 (RBAC)

### 데이터 보안
- [ ] 민감 데이터 암호화 (at rest)
- [ ] 전송 중 데이터 암호화 (in transit)
- [ ] 개인정보 마스킹
- [ ] 데이터 백업 암호화

### 모니터링 및 감사
- [x] Sentry 에러 추적 활성화
- [ ] 보안 이벤트 로깅
- [ ] 접근 로그 기록
- [ ] 이상 탐지 시스템

---

## 📚 참고 자료

### Spring Cloud Config 문서
- [Spring Cloud Config 공식 문서](https://spring.io/projects/spring-cloud-config)
- [Config Server 설정 가이드](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/)
- [암호화 및 복호화](https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_encryption_and_decryption)

### 마이크로서비스 패턴
- [Circuit Breaker 패턴](https://resilience4j.readme.io/docs/circuitbreaker)
- [Service Discovery with Eureka](https://spring.io/guides/gs/service-registration-and-discovery/)
- [분산 추적 (Distributed Tracing)](https://spring.io/projects/spring-cloud-sleuth)

### 보안 가이드
- [Spring Security 가이드](https://spring.io/guides/topicals/spring-security-architecture/)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [클라우드 보안 모범사례](https://aws.amazon.com/security/best-practices/)

---

## 🆘 문제 해결 (Troubleshooting)

### Config Server 연결 실패

**증상**:
```
Could not locate PropertySource: I/O error on GET request
```

**해결 방법**:
1. Config Server 실행 확인
2. Git 저장소 접근 가능 확인
3. 서비스명과 프로필명 확인

### 암호화/복호화 오류

**증상**:
```
Cannot decrypt: key not installed
```

**해결 방법**:
```bash
# Config Server 시작 시 암호화 키 설정
export ENCRYPT_KEY="your-encryption-key"
java -jar config-server.jar
```

### Eureka 서비스 등록 실패

**증상**:
```
Cannot execute request on any known server
```

**해결 방법**:
1. Eureka Server 실행 확인
2. Eureka 서버 URL 확인
3. 네트워크 연결 확인

### Database 연결 실패

**증상**:
```
org.postgresql.util.PSQLException: Connection refused
```

**해결 방법**:
1. PostgreSQL 서비스 실행 확인
2. 호스트명 및 포트 확인
3. 인증정보 확인
4. 방화벽 규칙 확인

---

## 📞 지원 및 문의

- **프로젝트 저장소**: [GitHub Repository]
- **이슈 트래커**: [GitHub Issues]
- **문서 업데이트**: README.md 수정 후 Pull Request

---

**문서 최종 업데이트**: 2025-10-11
**문서 버전**: 2.0
**작성자**: Claude Code /sc:document
