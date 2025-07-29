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

## 📋 목차

- [개요](#-개요)
- [기술 스택](#-기술-스택)
- [설정](#-설정)

---

## 🎯 개요

Rebook Eureka Server는 마이크로서비스 아키텍처에서 **서비스 디스커버리 패턴**을 구현하는 중앙 레지스트리 서버입니다.

### 주요 기능

- 🔍 **서비스 등록**: 마이크로서비스가 시작할 때 자신을 Eureka에 등록
- 🌐 **서비스 발견**: 다른 서비스의 위치(호스트, 포트)를 동적으로 조회
- 💚 **헬스 체크**: 등록된 서비스의 상태를 주기적으로 확인
- 🛡️ **장애 내성**: Self-Preservation 모드로 일시적 네트워크 장애 대응
- 📊 **대시보드**: 웹 UI를 통한 실시간 서비스 모니터링

### 아키텍처에서의 역할

```
┌─────────────────────────────────────────────────────┐
│                 Rebook Eureka Server                │
│              (Service Discovery Registry)            │
│                  http://localhost:8761               │
└─────────────────────────────────────────────────────┘
           ▲              ▲              ▲
           │              │              │
    ┌──────┴──────┐ ┌────┴─────┐ ┌─────┴──────┐
    │  Service A  │ │ Service B│ │  Service C │
    │  (Register) │ │ (Discover)│ │  (Monitor) │
    └─────────────┘ └──────────┘ └────────────┘
```

---

## 🛠 기술 스택

| 카테고리 | 기술 | 버전 | 용도 |
|---------|------|------|------|
| **Framework** | Spring Boot | 3.3.13 | 애플리케이션 프레임워크 |
| **Cloud** | Spring Cloud | 2023.0.5 | 마이크로서비스 지원 |
| **Discovery** | Netflix Eureka Server | - | 서비스 디스커버리 |
| **Language** | Java | 17 | 개발 언어 |
| **Build** | Gradle | 8.14.2 | 빌드 자동화 |
| **Monitoring** | Spring Actuator | - | 헬스 체크 & 메트릭 |
| **Metrics** | Micrometer Prometheus | - | 메트릭 수집 |
| **Logging** | Sentry | 8.13.2 | 중앙 집중식 로깅 |
| **Dev Tools** | Lombok | - | 보일러플레이트 코드 감소 |
| **Container** | Docker | - | 컨테이너화 |

---

#### 5. Eureka 대시보드 확인
브라우저에서 접속: **http://localhost:8761**

✅ 대시보드에서 확인 가능한 정보:
- 등록된 서비스 목록
- 서비스 인스턴스 상태
- Eureka 서버 메타데이터
- 복제본(Replica) 정보

---

## ⚙️ 설정

### 프로젝트 구조

```
rebook-eureka/
├── 📄 Dockerfile                    # Multi-stage Docker 빌드 설정
├── 📄 build.gradle                  # Gradle 빌드 스크립트
├── 📄 settings.gradle               # Gradle 프로젝트 설정
├── 🔧 gradlew / gradlew.bat        # Gradle Wrapper 실행 파일
├── 📁 gradle/wrapper/               # Gradle Wrapper 설정
└── 📁 src/main/
    ├── 📁 java/com/example/rebookeurekaserver/
    │   └── RebookEurekaServerApplication.java  # Main 애플리케이션
    └── 📁 resources/
        ├── application.yaml          # 기본 설정
        ├── application-dev.yaml      # 개발 환경 설정
        └── application-prod.yaml     # 운영 환경 설정
```

### 📝 설정 파일 상세

#### `application.yaml` (기본 설정)

| 설정 항목 | 값 | 설명 |
|----------|---|------|
| `server.port` | 8761 | Eureka 표준 포트 |
| `spring.application.name` | rebook-eureka | 서비스 식별 이름 |
| `spring.profiles.active` | prod | 기본 활성 프로필 |
| `eureka.client.register-with-eureka` | false | 자기 자신을 등록하지 않음 |
| `eureka.client.fetch-registry` | false | 레지스트리를 가져오지 않음 |
| `sentry.dsn` | ${SENTRY_DSN} | 환경 변수에서 주입 |

> ⚠️ **중요**: `register-with-eureka: false`는 독립 실행형 Eureka 서버의 표준 설정입니다.