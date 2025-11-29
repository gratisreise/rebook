# 푸쉬: docker buildx build --platform=linux/amd64 -t nooaahh/rebook-auth-service --push .
# ./gradlew clean build
# 생성: docker build -t nooaahh/rebook-auth-service:latest .
#

FROM eclipse-temurin:17-jdk
COPY build/libs/rebook-auth-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
