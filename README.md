# Rebook Config Server

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)

**Rebook 마이크로서비스 아키텍처의 중앙 설정 관리 서버**

Spring Cloud Config 기반 분산 설정 관리 시스템

</div>

---

## 📋 목차

- [개요](#개요)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [아키텍처](#아키텍처)

---

## 개요

**Rebook Config Server**는 Gratisreise Rebook 마이크로서비스 생태계를 위한 중앙 집중식 외부 설정 관리 서버입니다. Git 저장소를 백엔드로 사용하여 모든 마이크로서비스의 설정을 중앙에서 관리하고, REST API를 통해 각 서비스에 설정을 제공합니다.

### 핵심 개념

- **중앙 집중식 설정 관리**: 모든 마이크로서비스의 설정을 하나의 Git 저장소에서 관리
- **환경별 설정**: Dev/Prod 프로파일을 통한 환경별 설정 관리
- **서비스 디스커버리**: Eureka 서버에 자동 등록되어 다른 서비스가 발견 가능
- **동적 설정 갱신**: 애플리케이션 재시작 없이 설정 변경 가능

---

## 주요 기능

### 🔧 설정 관리
- Git 기반 버전 관리 시스템
- 다중 프로파일 지원 (dev, prod)
- 암호화된 속성 지원
- 동적 설정 갱신

### 🌐 서비스 통합
- Eureka 서비스 디스커버리 통합
- 마이크로서비스 자동 등록
- 로드 밸런싱 지원

### 📊 모니터링 & 관찰성
- Spring Boot Actuator 헬스 체크
- Prometheus 메트릭 수집
- Sentry 에러 추적 및 로깅
- 프로파일별 엔드포인트 제어

---

## 기술 스택

### Core Framework
- **Spring Boot** 3.3.13
- **Spring Cloud** 2023.0.5
- **Java** 17

### 주요 의존성
```groovy
dependencies {
    implementation 'org.springframework.cloud:spring-cloud-config-server'
    implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'io.micrometer:micrometer-registry-prometheus'
    implementation 'io.sentry:sentry-spring-boot-starter-jakarta:7.22.0'
}
```

### 빌드 도구
- **Gradle** 8.14.2 (Kotlin DSL)

---

## 아키텍처

```
┌─────────────────────────────────────────────────────────────┐
│                     Rebook Ecosystem                         │
│                                                               │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐  │
│  │   Service A  │    │   Service B  │    │   Service C  │  │
│  │              │    │              │    │              │  │
│  │  Config      │    │  Config      │    │  Config      │  │
│  │  Client      │    │  Client      │    │  Client      │  │
│  └──────┬───────┘    └──────┬───────┘    └──────┬───────┘  │
│         │                   │                   │            │
│         └───────────────────┼───────────────────┘            │
│                             │                                │
│                   ┌─────────▼─────────┐                      │
│                   │  Config Server    │                      │
│                   │  (Port 8888)      │                      │
│                   │                   │                      │
│                   │  - Git Backend    │                      │
│                   │  - Encryption     │                      │
│                   │  - Profiles       │                      │
│                   └─────────┬─────────┘                      │
│                             │                                │
│         ┌───────────────────┼───────────────────┐            │
│         │                   │                   │            │
│  ┌──────▼───────┐  ┌────────▼────────┐  ┌──────▼───────┐   │
│  │   Eureka     │  │  Git Repository │  │  Prometheus  │   │
│  │   Server     │  │  (GitHub)       │  │  & Sentry    │   │
│  └──────────────┘  └─────────────────┘  └──────────────┘   │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

### 설정 저장소 구조

```
rebook-yamls/
└── config-repo/
    ├── application.yml              # 공통 설정
    ├── application-dev.yml          # 개발 환경 설정
    ├── application-prod.yml         # 운영 환경 설정
    ├── service-a.yml                # Service A 설정
    ├── service-a-dev.yml
    ├── service-a-prod.yml
    └── ...
```
