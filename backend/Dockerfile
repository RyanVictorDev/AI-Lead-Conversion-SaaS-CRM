FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN ./mvnw -q -e -DskipTests package || (apk add maven && mvn -q -e -DskipTests package)

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=build /app/target/saas-crm-backend-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

