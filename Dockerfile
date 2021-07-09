FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
VOLUME /tmp
ADD target/driver-0.0.1-SNAPSHOT.jar driver.jar
ENTRYPOINT ["java","-jar","/driver.jar"]
