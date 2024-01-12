FROM maven:3.8.3-openjdk-17 as builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/*.jar /app.jar
ENTRYPOINT ["java","-jar","app.jar"]
