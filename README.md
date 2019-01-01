# Kafka
Kafka Important and Basic Commands

#### Kafka Default Port 9092 , Zookeeper Default Port 2181

#### Start Zookeeper
```bin/zkServer.sh start   config\zookeeper.properties```

#### Start kafka in background
 ```./bin/kafka-server-start.sh  -daemon  config/server.properties    ///Daemon is used to start kafka in background```    
 
#### Create kafka topic 
 ```bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test```

#### See all Topic 
```./bin/kafka-topics.sh --list --zookeeper localhost:2181 .```

#### Delete Topic 
```./bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic demo .```
