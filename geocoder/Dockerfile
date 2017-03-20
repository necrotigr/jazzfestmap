FROM lwieske/java-8:jdk-8u121-slim
LABEL version="1.0" description="Geocoder"
VOLUME /tmp
EXPOSE 9998
ADD geocoder-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar", "-Xms128m", "-Xmx256m", "/app.jar"]