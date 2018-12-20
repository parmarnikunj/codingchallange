# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###
* coding challange

### How do I get set up? ###
## Folder structure ##
- player serive
- kafka/kafka -> includes kafka server props
- kafka/zookeeper -> included zookeeper server props

### starting zookeeper ###
zookeeper-server-start zookeeper/server.properties

### starting kafka-server ###
kafka-server-start kafka/server.properties

### starting service ###

### API ###

- start player-1 
	http://localhost:4000/api/v1/play/56
- start player-2
	http://localhost:4001/api/v1/play/56
	
# Kubernetes SUPPORT #
## Minkube ###
- start local kubernetes cluster on your machine 
	minikube start
	
## HELM ##
- install halm chart of kafka
	```
	helm install --name my-kafka incubator/kafka
	
	```
	
- install helm chart of player service with default values player 1  
  	````
	helm install -n player-service-1 player-service-helm/ 
	```
- install one more helm chart for player service with specific values for player 2
	````
	helm install -n player-service-2 -f values_player_1.yaml player-service-helm/ 
	
	```


### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact