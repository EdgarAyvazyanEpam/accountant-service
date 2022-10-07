FROM openjdk:17-oracle
COPY target/accountant-0.0.1-SNAPSHOT.jar accountant-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "accountant-0.0.1-SNAPSHOT.jar"]
