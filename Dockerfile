FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE=target/aws_course-1.1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]