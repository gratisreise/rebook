# 0단계: 테스트
FROM gradle:7.5-jdk17 AS test
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew test

# 1단계: 빌드 환경
FROM gradle:8.14.2-jdk17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradle bootJar

# 2단계: 실행 환경
FROM openjdk:17-slim AS image
COPY build/libs/rebook-config.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]