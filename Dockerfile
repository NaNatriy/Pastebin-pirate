FROM amazoncorretto:11
RUN mkdir -p /DB
WORKDIR /

COPY target/pastebin-0.0.1-SNAPSHOT.jar /pasta.jar
CMD java -jar pasta.jar