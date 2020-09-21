
# jukebox-settings-control
 
Microservice REST API with a single GET endpoint that returns a paginated list of jukeboxes that suppport a given setting id.

This spring boot-app uses Gradle, it is preferable to use the GradleWrapper to buildclean and run this Spring Boot App.

Just clone this repo and in the root directory of this project

(or try to get the docker image https://hub.docker.com/r/algoritmyk/jukebox-control-settings I could not confirm why is not building locally maybe some config issues with my machine :p)

execute in your command line or terminal:

docker run -p 8080:8080 -t algoritmyk/jukebox-control-settings
and go http://localhost:8080/swagger-ui.html

In case you want to build it manually just: 

cd /jukebox-settings-control
and run:
./gradlew clean
./gradlew build
./gradlew bootRun

than once the localhost started you can go to 

https: http://localhost:8080/swagger-ui.html and start playing with the API :)


