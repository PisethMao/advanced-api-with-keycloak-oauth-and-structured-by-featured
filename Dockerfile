FROM ghcr.io/graalvm/jdk-community:25i1
WORKDIR workspace
COPY build/libs/spring-web-mvc-1.0.jar /workspace/api.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/workspace/api.jar"]