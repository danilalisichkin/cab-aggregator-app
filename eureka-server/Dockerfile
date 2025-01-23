FROM maven:3.9.9-sapmachine-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true

FROM amazoncorretto:21-alpine AS runtime
WORKDIR /app
ARG JAR_FILE=/app/target/eureka-server-0.0.1-SNAPSHOT.jar
COPY --from=build $JAR_FILE /app/eureka-server.jar

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
