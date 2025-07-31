# docker buildx build --platform=linux/amd64 -t nooaahh/rebook-config --push .
# docker build -t nooaahh/rebook-config:latest .
# docker image prune -f

# 1단계: 빌드 (테스트 포함)
FROM gradle:8.14.2-jdk17 AS builder
WORKDIR /app

# Gradle Wrapper, 설정 파일, 소스 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .


RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon

COPY src src

# 테스트 실행 및 빌드 (bootJar 생성)
RUN ./gradlew bootJar --no-daemon

# 2단계: 실행 환경
FROM openjdk:17-slim
WORKDIR /app

# 빌드 단계에서 생성된 jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]
#docker buildx build --platform=linux/amd64,linux/arm64 -t nooaahh/rebook-config --push .