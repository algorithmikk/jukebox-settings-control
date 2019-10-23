FROM java:8-jdk-alpine

COPY ./target/jukebox-settings-control-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch jukebox-settings-control-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","jukebox-settings-control-0.0.1-SNAPSHOT.jar"]