FROM --platform=$BUILDPLATFORM openjdk:22-slim-bullseye AS builder

WORKDIR /app

COPY . .

RUN ./mvnw -f pom.xml clean package -DskipTests

FROM tomcat:11.0.0-jre21-temurin-jammy

COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]