# ========== STAGE 1: Build jar ==========
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom trước để cache dependency
COPY pom.xml .

# Copy source code
COPY src ./src

# Build jar, bỏ test cho nhanh
RUN mvn package -DskipTests

# ========== STAGE 2: Run app ==========
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy jar từ stage build sang
COPY --from=build /app/target/bookstore-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT [ "java" ,"-jar" ,"app.jar"]
