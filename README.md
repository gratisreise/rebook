# Rebook Eureka Server

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)

**Rebook 마이크로서비스 아키텍처의 서비스 디스커버리 서버**

Spring Cloud Netflix Eureka 기반 서비스 등록 및 발견 시스템

</div>

---

## 1. 개요

**Rebook Eureka Server**는 중고 도서 거래 플랫폼 Rebook의 마이크로서비스 아키텍처를 지원하는 중앙 서비스 디스커버리 레지스트리입니다. Spring Cloud Netflix Eureka Server를 기반으로 구현된 본 서비스는 **동적 서비스 등록**, **서비스 발견**, **헬스 체크**를 통한 확장 가능하고 복원력 있는 시스템 구조를 제공합니다.


### 서비스 역할

본 서비스는 Rebook 플랫폼 내에서 다음과 같은 역할을 담당합니다:

- **서비스 등록**: 모든 마이크로서비스의 위치 정보를 중앙 집중식으로 관리
- **서비스 발견**: 클라이언트가 다른 서비스의 위치를 동적으로 조회할 수 있도록 지원
- **헬스 체크**: 등록된 서비스의 상태를 주기적으로 모니터링하여 장애 감지
- **자동 로드 밸런싱**: OpenFeign 및 Ribbon과 통합하여 클라이언트 측 로드 밸런싱 지원
- **장애 내성**: Self-Preservation 모드로 일시적인 네트워크 장애 대응

---

## 2. 목차

- [1. 개요](#1-개요)
- [2. 목차](#2-목차)
- [3. 주요 기능](#3-주요-기능)
- [4. 기술 스택](#4-기술-스택)
- [5. 프로젝트 구조](#5-프로젝트-구조)

---



## 3. 주요 기능

### 3.1 서비스 디스커버리

#### 서비스 등록
- ✅ 마이크로서비스 자동 등록 (Eureka Client 통합)
- ✅ 서비스 메타데이터 관리 (호스트, 포트, 헬스 상태)
- ✅ 실시간 서비스 상태 업데이트

#### 서비스 조회
- ✅ 서비스 이름 기반 동적 서비스 탐색
- ✅ 등록된 모든 서비스 목록 조회
- ✅ 서비스별 인스턴스 상태 정보 제공
- ✅ REST API를 통한 레지스트리 정보 접근

#### 헬스 체크 및 장애 감지
- ✅ 주기적인 하트비트 수신 (기본 30초 간격)
- ✅ 응답 없는 서비스 자동 제거 (기본 90초 타임아웃)
- ✅ Self-Preservation 모드 (네트워크 장애 시 서비스 보호)
- ✅ Eureka Dashboard를 통한 실시간 모니터링

### 3.2 클라이언트 통합

- ✅ Spring Cloud OpenFeign 자동 통합
- ✅ Ribbon 클라이언트 측 로드 밸런싱
- ✅ 서비스 이름 기반 동적 라우팅
- ✅ 장애 발생 시 자동 재시도 및 폴백

### 3.3 모니터링 및 관리

- ✅ Eureka Dashboard 웹 UI (포트 8761)
- ✅ Spring Actuator 헬스 체크 엔드포인트
- ✅ Prometheus 메트릭 수집 (`/actuator/prometheus`)

### 3.4 환경별 설정

- ✅ 개발 환경 (dev): Self-Preservation 비활성화, 전체 Actuator 엔드포인트 노출
- ✅ 운영 환경 (prod): Self-Preservation 활성화, 제한된 Actuator 엔드포인트


---

## 4. 기술 스택

### 4.1 백엔드 프레임워크

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Boot** | 3.3.13 | 애플리케이션 프레임워크 |
| **Java** | 17 | 프로그래밍 언어 |
| **Lombok** | - | 보일러플레이트 코드 제거 |

### 4.2 마이크로서비스 인프라 (Spring Cloud)

| 기술 | 버전 | 용도 |
|------|------|------|
| **Netflix Eureka Server** | 2023.0.5 | 서비스 디스커버리 레지스트리 |
| **Spring Cloud** | 2023.0.5 | 마이크로서비스 기반 클라우드 지원 |

### 4.3 모니터링 & 로깅

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Actuator** | - | 헬스체크 및 메트릭 엔드포인트 |
| **Prometheus** | - | 메트릭 수집 및 모니터링 |
| **Micrometer Prometheus** | - | Prometheus 형식 메트릭 변환 |
| **Sentry** | 8.13.2 | 실시간 에러 트래킹 및 알림 |

### 5.4 빌드 & 배포

| 기술 | 버전 | 용도 |
|------|------|------|
| **Gradle** | 8.14.2 | 빌드 자동화 도구 |
| **Docker** | - | 컨테이너화 및 배포 |

---
## 5. 프로젝트 구조

### 주요 디렉토리 설명

| 디렉토리 | 역할 | 주요 기능 |
|---------|------|----------|
| **src/main/java/** | 애플리케이션 소스 | `@EnableEurekaServer`가 적용된 단일 메인 클래스 |
| **src/main/resources/** | 설정 파일 | 프로필별 YAML 설정 (dev, prod) |
| **gradle/** | Gradle Wrapper | Gradle 빌드 도구 실행 파일 |


```
rebook-eureka-server/
├── src/
│   ├── main/
│   │   ├── java/com/example/rebookeurekaserver/
│   │   │   └── RebookEurekaServerApplication.java  # 메인 애플리케이션
│   │   │       @SpringBootApplication
│   │   │       @EnableEurekaServer
│   │   │
│   │   └── resources/
│   │       ├── application.yaml               # 기본 설정
│   │       │   - server.port: 8761
│   │       │   - spring.application.name: rebook-eureka
│   │       │   - spring.profiles.active: prod
│   │       │   - sentry.dsn: ${SENTRY_DSN}
│   │       │
│   │       ├── application-dev.yaml           # 개발 환경 설정
│   │       │   - eureka.server.enable-self-preservation: false
│   │       │   - management.endpoints.web.exposure.include: "*"
│   │       │
│   │       └── application-prod.yaml          # 운영 환경 설정
│   │           - eureka.server.enable-self-preservation: true
│   │           - management.endpoints.web.exposure.include: "prometheus,health,info"
│   │
│   └── test/
│       └── java/com/example/rebookeurekaserver/
│           └── RebookEurekaServerApplicationTests.java
│
├── build.gradle                               # Gradle 빌드 설정
├── settings.gradle                            # Gradle 프로젝트 설정
├── gradlew / gradlew.bat                      # Gradle Wrapper 실행 파일
├── Dockerfile                                 # Docker 멀티 스테이지 빌드
├── CLAUDE.md                                  # Claude Code 가이드
└── README.md                                  # 프로젝트 문서 (본 파일)
```
