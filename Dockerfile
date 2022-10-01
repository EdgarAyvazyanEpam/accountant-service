FROM adoptopenjdk:11-jre-hotspot
COPY target/accountant-0.0.1-SNAPSHOT.jar /opt/application.jar
ENTRYPOINT ["java", "-jar", "/opt/application.jar"]
