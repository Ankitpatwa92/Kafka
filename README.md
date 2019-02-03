# Kafka
Kafka Important and Basic Commands

#### Kafka Default Port 9092 , Zookeeper Default Port 2181

#### Start Zookeeper
```bin/zkServer.sh   config\zookeeper.properties```

#### Start kafka in background
 ```./bin/kafka-server-start.sh  -daemon  config/server.properties    ///Daemon is used to start kafka in background```    
 
#### Create kafka topic 
 ```bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test```

#### See all Topic 
```./bin/kafka-topics.sh --list --zookeeper localhost:2181 .```

#### Delete Topic 
```./bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic demo .```

#### Start Producer
```bin/kafka-console-producer.sh --broker-list localhost:9092 --topic topic-name```

#### Start Consumer 
```bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 —topic topic-name  --from-beginning```

#### Alter Topic 
```
bin/kafka-topics.sh —zookeeper localhost:2181 --alter --topic topic_name  --parti-tions count
//Using above command partition count can be changed
```
#### Describe Topic
```
bin/kafka-topics.sh --describe --topic test-topic --zookeeper localhost:2181
```
#### Get offset detail of particluar consumer group
```
bin/kafka-consumer-groups.sh  --bootstrap-server localhost:9092 --describe --group testgroup
```
