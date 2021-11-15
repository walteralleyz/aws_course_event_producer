FROM openjdk:11
VOLUME /tmp
ARG TAG=1.3.0
ARG JAR_FILE=target/aws_course-${TAG}.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]