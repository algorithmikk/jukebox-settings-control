
# jukebox-settings-control
 
REST API with a single GET endpoint that returns a paginated list of jukeboxes that suppport a given setting id.

Hi I built this spring boot-app using Gradle, it is preferable to use the GradleWrapper to buildclean and run this Spring Boot App.

Just clone this repo (dev branch) and in the root directory of this project

(or try to get the docker image https://hub.docker.com/r/algoritmyk/jukebox-control-settings I could not confirm why is not building locally maybe some config issues with my machine :p)

So going back to the essential: if you want to build it manually just: 

cd /jukebox-settings-control
and run:
./gradlew clean
./gradlew build
./gradlew bootRun

than once the localhost started you can go to 

https: http://localhost:8080/swagger-ui.html and start playing with the API :)


