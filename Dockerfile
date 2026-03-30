FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Run ./gradlew bootJar first — produces build/libs/application-boot.jar
COPY build/libs/application-boot.jar /app/app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
