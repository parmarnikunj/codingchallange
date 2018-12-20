# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###
* coding challange

### How do I get set up? ###

## Folder structure ##
- player serive
- kafka/kafka -> includes kafka server props
- kafka/zookeeper -> included zookeeper server props

# LOCAL Testing #

### starting zookeeper ###
zookeeper-server-start zookeeper/server.properties

### starting kafka-server ###
kafka-server-start kafka/server.properties

### starting service ###

#### service for player-1 ####
using mac
1. open command prompt and set follwing env variables one by one
	export SERVER_PORT=4000
	export CLIENT_ID=player-1
	export GROUP_ID=player_1_group
	export TOPIC_NAME=player-1-topic
	export CONSUMER_TOPIC=player-2-topic

2. start service for player-1
	java -jar target/player-service.jar

#### service for player-1 ####
1. open command prompt and set follwing env variables one by one
	export SERVER_PORT=4001
	export CLIENT_ID=player-2
	export GROUP_ID=player_2_group
	export TOPIC_NAME=player-2-topic
	export CONSUMER_TOPIC=player-1-topic

2. start service for player-2
	java -jar target/player-service.jar


# Kubernetes SUPPORT #
## Minkube ###
- start local kubernetes cluster on your machine 
	minikube start
	
## HELM ##
1. install halm chart of kafka
 - ```helm install --name my-kafka incubator/kafka```
2. install helm chart of player service with default values player 1  
 - ```helm install -n player-service-1 player-service-helm/```
3. install one more helm chart for player service with specific values for player 2
 - ```helm install -n player-service-2 -f values_player_1.yaml player-service-helm/ ```



# API #

* using websocket client :
 simplest way is by installing following chrom plugin -
 https://chrome.google.com/webstore/detail/simple-websocket-client/pfdhoblngboilpfeibdedpjgfnlcodoo?hl=de
 
 using the chome plugin do following:
 
- start player-1 and 2 in two different tabs
	1. ws://localhost:4000/start	
	2. ws://localhost:4001/start
- enter 56 one of the websocket send message


### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact