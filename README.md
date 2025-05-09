# Gratisreise 리북 설정 서버

Gratisreise 리북 애플리케이션 생태계의 마이크로서비스들을 위한 외부 설정 속성을 관리하는 Spring Cloud Config Server입니다.

## 개요

이 설정 서버는 분산 시스템을 위한 중앙 집중식 설정 관리를 제공합니다. Git 저장소에서 설정 속성을 가져와 REST 엔드포인트를 통해 클라이언트 애플리케이션에 제공합니다.

## 주요 기능

- **중앙 집중식 설정**: 여러 마이크로서비스의 설정을 한 곳에서 관리
- **Git 기반 저장소**: 버전 관리를 위해 Git 저장소에 설정 파일 저장
- **서비스 디스커버리**: 서비스 발견을 위해 Eureka 서버에 등록
- **헬스 모니터링**: 헬스 체크 및 메트릭 엔드포인트 포함
- **Prometheus 통합**: Prometheus 모니터링을 위한 메트릭 내보내기

## 사전 요구사항

- Java 17 이상
- 설정 저장소에 대한 Git 액세스 권한
- Eureka 서버 실행 중 (서비스 등록용)

## 설정

서버는 `application.yaml`을 통해 설정됩니다:

### 주요 설정 속성

- **서버 포트**: `8888`
- **Git 저장소**: `https://github.com/rebook/config-repo`
- **Eureka 서버**: `http://eureka-server:8761/eureka/`
- **기본 브랜치**: `main`

### 환경 변수

다음 환경 변수들을 설정해야 합니다:

- `GIT_USERNAME`: Git 저장소 접근용 사용자명
- `GIT_PASSWORD`: Git 저장소 접근용 비밀번호 또는 개인 액세스 토큰

## 애플리케이션 실행

### Gradle Wrapper 사용

```bash
# Unix/Linux/macOS에서
./gradlew bootRun

# Windows에서
gradlew.bat bootRun
```

### Java 사용

```bash
# 애플리케이션 빌드
./gradlew build

# JAR 파일 실행
java -jar build/libs/gratisreise-rebook-config-*.jar
```

### Docker 사용

```dockerfile
FROM openjdk:17-jre-slim
COPY build/libs/gratisreise-rebook-config-*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## API 엔드포인트

### 설정 엔드포인트

- `GET /{application}/{profile}` - 애플리케이션과 프로파일에 대한 설정 조회
- `GET /{application}/{profile}/{label}` - 특정 브랜치/라벨에 대한 설정 조회
- `GET /{application}-{profile}.json` - JSON 형식으로 설정 조회
- `GET /{application}-{profile}.properties` - Properties 형식으로 설정 조회

### 관리 엔드포인트

- `GET /actuator/health` - 헬스 체크 엔드포인트
- `GET /actuator/info` - 애플리케이션 정보
- `GET /actuator/prometheus` - Prometheus 메트릭

## 개발

### 프로젝트 구조

```
src/
├── main/
│   ├── java/
│   │   └── com/example/rebookconfig/
│   │       └── RebookConfigApplication.java
│   └── resources/
│       └── application.yaml
└── test/
    └── java/
        └── com/example/rebookconfig/
            └── RebookConfigApplicationTests.java
```

## 배포

### 운영 환경 환경 변수

```bash
export GIT_USERNAME=your-git-username
export GIT_PASSWORD=your-git-token
export EUREKA_SERVER_URL=http://your-eureka-server:8761/eureka/
```

### Docker Compose 예시

```yaml
version: '3.8'
services:
  config-server:
    image: gratisreise/rebook-config-server:latest
    ports:
      - "8888:8888"
    environment:
      - GIT_USERNAME=${GIT_USERNAME}
      - GIT_PASSWORD=${GIT_PASSWORD}
    depends_on:
      - eureka-server
    networks:
      - rebook-network
```

## 모니터링

애플리케이션은 여러 모니터링 엔드포인트를 제공합니다:

- **헬스 체크**: `/actuator/health`
- **메트릭**: `/actuator/prometheus`
- **애플리케이션 정보**: `/actuator/info`

## 보안 고려사항

- Git 자격 증명은 설정 파일이 아닌 환경 변수로 저장하세요
- Git 인증에는 비밀번호 대신 개인 액세스 토큰을 사용하세요
- 운영 환경에서는 설정 서버 엔드포인트에 대한 인증 구현을 고려하세요
- 민감한 설정 정보는 암호화하여 저장하세요
