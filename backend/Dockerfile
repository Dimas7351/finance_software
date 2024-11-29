FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/finance_software-1.0-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]