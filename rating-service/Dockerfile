FROM maven:3.9.9-sapmachine-21 AS build
WORKDIR /build
COPY pom.xml pom.xml
COPY src src
RUN mvn clean package -DskipTests=true

FROM amazoncorretto:21-alpine AS runtime
VOLUME /tmp
ARG JAR_FILE=rating-service-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY --from=build /build/target/${JAR_FILE} rating-service.jar

ENTRYPOINT ["java", "-jar", "rating-service.jar"]
