# Rebook Eureka Server

Spring Cloud Netflix Eureka를 기반으로 한 서비스 디스커버리 서버입니다. 마이크로서비스 아키텍처에서 서비스 등록 및 발견을 담당합니다.

## 📁 프로젝트 구조

```
gratisreise-rebook-eureka/
├── Dockerfile                 # Docker 빌드 설정
├── gradlew                   # Gradle Wrapper (Unix/Linux)
├── gradlew.bat              # Gradle Wrapper (Windows)
├── gradle/wrapper/          # Gradle Wrapper 설정
└── src/main/
    ├── java/com/example/rebookeurekaserver/
    │   └── RebookEurekaServerApplication.java
    └── resources/
        ├── application.yaml          # 기본 설정
        ├── application-dev.yaml      # 개발환경 설정
        └── application-prod.yaml     # 운영환경 설정
```

## 🚀 시작하기

### 필요 조건

- Java 17 이상
- Gradle 8.14.2 (Gradle Wrapper 포함)
- Docker (선택사항)

### 로컬 실행

1. **프로젝트 클론 후 디렉토리 이동**
   ```bash
   cd gratisreise-rebook-eureka
   ```

2. **Gradle Wrapper 실행 권한 부여 (Unix/Linux)**
   ```bash
   chmod +x ./gradlew
   ```

3. **애플리케이션 실행**
   ```bash
   ./gradlew bootRun
   ```

4. **Eureka 대시보드 접속**
    - URL: http://localhost:8761
    - 등록된 서비스 목록과 상태를 확인할 수 있습니다.

### Docker 실행

1. **Docker 이미지 빌드**
   ```bash
   docker build -t rebook-eureka-server .
   ```

2. **컨테이너 실행**
   ```bash
   docker run -p 8761:8761 rebook-eureka-server
   ```

## ⚙️ 설정

### 기본 설정 (application.yaml)

- **포트**: 8761 (Eureka 기본 포트)
- **서비스명**: rebook-eureka
- **기본 프로필**: dev
- **Eureka 클라이언트 설정**: 자기 자신을 등록하지 않도록 설정

### 환경별 설정

#### 개발환경 (dev)
- **Self-Preservation 모드**: 비활성화
- **모니터링**: 모든 엔드포인트 노출
- 개발 중 빠른 서비스 감지를 위해 Self-Preservation을 비활성화

#### 운영환경 (prod)
- **Self-Preservation 모드**: 활성화
- **모니터링**: prometheus, health, info 엔드포인트만 노출
- 네트워크 장애 시 안정성을 위해 Self-Preservation 활성화

### 프로필 변경

환경 변수 또는 JVM 옵션으로 프로필을 변경할 수 있습니다:

```bash
# 환경 변수
export SPRING_PROFILES_ACTIVE=prod

# JVM 옵션
java -jar -Dspring.profiles.active=prod app.jar
```

## 📊 모니터링

Spring Boot Actuator를 통한 모니터링 엔드포인트가 제공됩니다:

### 개발환경
- 모든 엔드포인트 접근 가능: http://localhost:8761/actuator

### 운영환경
- Health Check: http://localhost:8761/actuator/health
- 애플리케이션 정보: http://localhost:8761/actuator/info
- Prometheus 메트릭: http://localhost:8761/actuator/prometheus

## 🐳 Docker 정보

### Dockerfile 특징
- **Multi-stage 빌드**: 빌드와 실행 환경 분리
- **테스트 포함**: 빌드 시 자동으로 테스트 실행
- **최적화**: 실행 환경은 slim 이미지 사용
- **헬스체크**: curl이 설치되어 컨테이너 상태 확인 가능

### 빌드 단계
1. Gradle 8.14.2 + JDK 17 환경에서 빌드 및 테스트
2. OpenJDK 17 slim 환경에서 실행

## 🔧 개발 가이드

### 빌드
```bash
./gradlew build
```

### 테스트
```bash
./gradlew test
```

### JAR 파일 생성
```bash
./gradlew bootJar
```

## 🌐 서비스 등록

다른 마이크로서비스에서 이 Eureka 서버에 등록하려면:

```yaml
# 클라이언트 서비스의 application.yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## 📝 참고사항

- Eureka Server는 기본적으로 자기 자신을 클라이언트로 등록하지 않도록 설정되어 있습니다
- 개발환경에서는 빠른 서비스 감지를 위해 Self-Preservation이 비활성화되어 있습니다
- 운영환경에서는 네트워크 장애에 대한 안정성을 위해 Self-Preservation이 활성화되어 있습니다
- Docker 컨테이너는 8761 포트를 노출합니다