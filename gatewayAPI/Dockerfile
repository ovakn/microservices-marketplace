FROM openjdk:22-jdk-oraclelinux8 as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/gatewayApplication.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gatewayApplication.jar"]