FROM openjdk:19-jdk-slim
WORKDIR /app
COPY build/libs/croco_users-0.0.1-SNAPSHOT.war app.war
EXPOSE 8089
CMD ["java", "-jar", "app.war"]
