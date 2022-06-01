## HISHAB Lucky Dice Game
<p>============================================</p>

### How to deploy the game using docker
<p>-----------------------------------------------------------------------------------</p>

- Clone project to your local machine
- Install maven dependencies by either using your IDE or ```mvn install ``` command
- Build docker image by running this command ``` docker build -t {name:version} .``` e.g ``` docker build -t dice-game-spring:1.0 .``` 
- Run docker image by running this command ```docker run -p 9060:9060 {name-of-the-image}``` e.g ```docker run -p 9060:9060 dice-game-spring:1.0```
- After running all of this command, application will be up and running on port 9060

After application is up and running you can access swagger ui 
for API documentation using this link [Swagger-UI](http://127.0.0.1:9060/swagger-ui/index.html)