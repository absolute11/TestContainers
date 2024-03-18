FROM openjdk:17
EXPOSE 8081
COPY target/springboot-0.0.1-SNAPSHOT.jar myapp.jar
CMD ["java","-jar","/myapp.jar"]