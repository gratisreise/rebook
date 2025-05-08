FROM openjdk:17-jre-slim
COPY build/libs/gratisreise-rebook-config-*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app.jar"]