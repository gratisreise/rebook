# Rebook Config Server

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.13-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.5-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Gradle](https://img.shields.io/badge/Gradle-8.14.2-02303A)

**Rebook 마이크로서비스 아키텍처의 중앙 설정 관리 서버**

Git 기반 분산 설정 관리 및 서비스 디스커버리 통합

</div>

---

## 1. 개요

**Rebook Config Server**는 Gratisreise Rebook 마이크로서비스 생태계를 위한 중앙 집중식 외부 설정 관리 서버입니다. Spring Cloud Config를 기반으로 구현된 본 서비스는 Git 저장소를 백엔드로 사용하여 **중앙화된 설정 관리**, **환경별 프로파일**, **암호화 지원**을 통한 안전하고 확장 가능한 구조를 제공합니다.


### 서비스 역할

본 서비스는 Rebook 플랫폼 내에서 다음과 같은 역할을 담당합니다:

- **중앙 설정 관리**: 모든 마이크로서비스의 설정을 Git 저장소에서 통합 관리
- **환경별 프로파일**: Dev/Prod 환경별 설정 분리 및 동적 제공
- **서비스 디스커버리**: Eureka 통합을 통한 자동 서비스 등록 및 탐색
- **설정 암호화**: 민감 정보 보호를 위한 암호화 지원
- **모니터링 통합**: Prometheus 메트릭 및 Sentry 에러 트래킹

---

## 2. 목차

- [1. 개요](#1-개요)
- [2. 목차](#2-목차)
- [3. 주요 기능](#3-주요-기능)
- [4. 기술 스택](#4-기술-스택)
- [5. 프로젝트 구조](#5-프로젝트-구조)

---

## 3. 주요 기능

### 3.1 설정 관리

#### Git 기반 설정 저장소
- ✅ GitHub 저장소를 설정 백엔드로 사용
- ✅ 버전 관리를 통한 설정 변경 이력 추적
- ✅ 환경 변수를 통한 안전한 Git 인증 (GIT_USERNAME, GIT_PASSWORD)
- ✅ config-repo/ 디렉토리 기반 설정 파일 구조

#### 환경별 프로파일
- ✅ Dev/Prod 프로파일 분리 관리
- ✅ 프로파일별 설정 우선순위 적용
- ✅ 서비스별 개별 설정 파일 지원
- ✅ 공통 설정 (application.yml) 상속

#### 설정 암호화
- ✅ 민감 정보 암호화 지원 (SECRET_KEY 환경 변수)
- ✅ /encrypt, /decrypt 엔드포인트 제공
- ✅ 암호화된 속성값 자동 복호화

### 3.2 서비스 디스커버리 통합

- ✅ Eureka Server 자동 등록 (`rebook-config:origin`)
- ✅ 서비스 헬스체크 및 자동 장애 감지
- ✅ 마이크로서비스 간 동적 서비스 탐색
- ✅ 로드 밸런싱 지원

### 3.3 모니터링 및 관찰성

- ✅ Spring Boot Actuator 헬스체크
- ✅ Prometheus 메트릭 수집 (/actuator/prometheus)
- ✅ Sentry 실시간 에러 트래킹
- ✅ 환경별 Actuator 엔드포인트 제어 (Dev: 전체 공개, Prod: 제한적)

### 3.4 설정 제공 API

- ✅ REST API를 통한 설정 조회
- ✅ 다양한 포맷 지원 (JSON, Properties, YAML)
- ✅ Git 브랜치(label) 기반 설정 제공
- ✅ 클라이언트 자동 설정 갱신 지원


---

## 4. 기술 스택

### 4.1 백엔드 프레임워크

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Boot** | 3.3.13 | 애플리케이션 프레임워크 |
| **Java** | 17 | 프로그래밍 언어 |
| **Spring Cloud Config Server** | 2023.0.5 | 중앙 설정 관리 서버 |
| **Spring Cloud Netflix Eureka Client** | 2023.0.5 | 서비스 디스커버리 클라이언트 |

### 4.2 설정 저장소

| 기술 | 버전 | 용도 |
|------|------|------|
| **Git (GitHub)** | - | 설정 파일 버전 관리 백엔드 |
| **Config Repository** | - | https://github.com/gratisreise/rebook-yamls.git |

### 4.3 모니터링 & 로깅

| 기술 | 버전 | 용도 |
|------|------|------|
| **Spring Actuator** | - | 헬스체크 및 메트릭 엔드포인트 |
| **Prometheus** | - | 메트릭 수집 및 모니터링 |
| **Sentry** | 8.13.2 | 실시간 에러 트래킹 및 알림 |
| **SLF4J & Logback** | - | 애플리케이션 로깅 |

### 4.4 빌드 & 배포

| 기술 | 버전 | 용도 |
|------|------|------|
| **Gradle** | 8.14.2 | 빌드 자동화 도구 |
| **Docker** | - | 컨테이너화 및 배포 |

---

## 5. 프로젝트 구조

### 주요 디렉토리 설명

| 디렉토리 | 역할 | 주요 기능 |
|---------|------|----------|
| **src/main/java/** | 애플리케이션 코드 | Spring Boot 메인 클래스 및 설정 |
| **src/main/resources/** | 설정 파일 | application.yaml, 프로파일별 설정 |
| **build.gradle** | 빌드 설정 | 의존성 및 플러그인 관리 |
| **Dockerfile** | 컨테이너 이미지 | 멀티 스테이지 빌드 설정 |


```
rebook-config/
├── src/
│   ├── main/
│   │   ├── java/com/example/rebookconfig/
│   │   │   └── RebookConfigApplication.java  (메인 애플리케이션)
│   │   │
│   │   └── resources/
│   │       ├── application.yaml               (기본 설정)
│   │       ├── application-dev.yaml           (개발 환경)
│   │       └── application-prod.yaml          (운영 환경)
│   │
│   └── test/
│       └── java/com/example/rebookconfig/
│           └── RebookConfigApplicationTests.java
│
├── build.gradle                               # Gradle 빌드 설정
├── Dockerfile                                 # Docker 멀티 스테이지 빌드
├── CLAUDE.md                                  # Claude Code 가이드
└── README.md                                  # 프로젝트 문서 (본 파일)
```