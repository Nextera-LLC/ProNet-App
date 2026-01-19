FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the already-built jar (created by ./gradlew build)
COPY build/libs/*.jar app.jar

EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
