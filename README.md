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

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact