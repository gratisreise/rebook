# 1단계: 빌드 (테스트 포함)
FROM gradle:8.14.2-jdk17 AS builder
WORKDIR /app

# Gradle Wrapper, 설정 파일, 소스 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew

# 테스트 실행 및 빌드 (bootJar 생성)
RUN ./gradlew test bootJar --no-daemon

# 2단계: 실행 환경
FROM openjdk:17-slim
WORKDIR /app

# 빌드 단계에서 생성된 jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
