FROM openjdk:17-oracle
COPY target/accountant-0.0.1-SNAPSHOT.jar /opt/application.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/opt/application.jar"]
