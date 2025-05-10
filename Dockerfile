# 테스트
FROM gradle:7.5-jdk17 AS test
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew test

# 빌드 환경
FROM gradle:8.14.2-jdk17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# 실행 환경
FROM openjdk:17-slim AS run
RUN apt-get update && apt-get install -y curl
COPY build/libs/rebook-eureka.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]